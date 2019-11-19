package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Activity.SpalashActivity;
import xact.idea.attendancesystem.Adapter.UnitAdapter;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Entity.PunchInOutPostEntity;
import xact.idea.attendancesystem.Entity.PunchInOutResponseEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Entity.UserActivityListEntity;
import xact.idea.attendancesystem.Entity.UserActivityPostEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.SharedPreferenceUtil;
import xact.idea.attendancesystem.Utils.Utils;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;


public class PunchFragment extends Fragment {
    static Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    View mRootView;
    Spinner spinnerUnit;
    TextView text_unit;
   static Button btn_punch_in;
    static  Button btn_punch_out;
    TextView text_enter_time;
    TextView text_in_comment;
    TextView text_out_comment;
    EditText edit_comments;
    TextView text_duration;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    private List<Unit> unitArrayList= new ArrayList<>();
    ArrayAdapter<Unit> unitListEntityArrayAdapter;
    int mUnitId;
    String mUnitStation;
    String mUnitName;
    LinearLayout rlt_root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_punch, container, false);
        mActivity = getActivity();
        correctSizeUtil = correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(mRootView);
        mService=Common.getApiXact();
        initView();
        return mRootView;
    }

    private void initView() {
        text_out_comment=mRootView.findViewById(R.id.text_out_comment);
        text_in_comment=mRootView.findViewById(R.id.text_in_comment);
        rlt_root=mRootView.findViewById(R.id.rlt_root);
        text_duration=mRootView.findViewById(R.id.text_duration);
        text_unit=mRootView.findViewById(R.id.text_unit);
        text_enter_time=mRootView.findViewById(R.id.text_enter_time);
        btn_punch_in=mRootView.findViewById(R.id.btn_punch_in);
        btn_punch_out=mRootView.findViewById(R.id.btn_punch_out);
        edit_comments=mRootView.findViewById(R.id.edit_comments);
        spinnerUnit=mRootView.findViewById(R.id.spinner_unit);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },4000);

        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("sp_units", "" + unitArrayList.get(position).UnitName);
                mUnitId=unitArrayList.get(position).Id;
                mUnitStation=unitArrayList.get(position).ShortName;
                mUnitName=unitArrayList.get(position).UnitName;



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_punch_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.broadcastIntent(mActivity, rlt_root)){
                    //Toast.makeText(mContext, "Connected ", Toast.LENGTH_SHORT).show();
                    UserActivityData("in");
                }
                else {
                    Snackbar snackbar = Snackbar
                            .make(rlt_root, "No Internet", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


            }
        });
        btn_punch_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(mActivity);
                builder1.setMessage("Are you sure to Update Punch OUT?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (Utils.broadcastIntent(mActivity, rlt_root)){
                                    //Toast.makeText(mContext, "Connected ", Toast.LENGTH_SHORT).show();
                                    UserActivityData("out");
                                }
                                else {
                                    Snackbar snackbar = Snackbar
                                            .make(rlt_root, "No Internet", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
    }

    public static void show(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        final String currentDate=formatter.format(date);
        UserActivity userActivitys=Common.userActivityRepository.getUserActivity(SharedPreferenceUtil.getUser(mActivity),currentDate);
        if (userActivitys!=null){
            if (userActivitys.PunchInTimeLate!=null){
                btn_punch_out.setFocusable(true);
                btn_punch_out.setAlpha(1);
                btn_punch_in.setFocusable(false);
                btn_punch_in.setAlpha(0.5f);
            }
//            else {
//                btn_punch_in.setFocusable(true);
//                btn_punch_in.setAlpha(1);
//                btn_punch_out.setFocusable(false);
//                btn_punch_out.setAlpha(0.5f);
//            }

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        unitListData();
        show();
    }
    private void UserActivityData(String type) {

      //  showLoadingProgress(mActivity);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String currentDate=formatter.format(date);
        PunchInOutPostEntity punchInOutPostEntity = new PunchInOutPostEntity();
        punchInOutPostEntity.user_id= Integer.parseInt(SharedPreferenceUtil.getUser(mActivity));
        punchInOutPostEntity.unit_id=mUnitId;
        punchInOutPostEntity.comment=edit_comments.getText().toString();
        punchInOutPostEntity.punch_type=type;
        punchInOutPostEntity.work_station=mUnitStation;
        punchInOutPostEntity.punch_date_time=currentDate;
        if (type.equals("in")){
            compositeDisposable.add(mService.postPunch(punchInOutPostEntity).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PunchInOutResponseEntity>() {
                @Override
                public void accept(PunchInOutResponseEntity carts) throws Exception {
                    // departmentListEntityList=carts;if
                    if (carts.exe_status){
                        Toast.makeText(mActivity, "Successfully Punch In", Toast.LENGTH_SHORT).show();
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date(System.currentTimeMillis());
                        String currentDate=formatter.format(date);
                        SimpleDateFormat formatters= new SimpleDateFormat("HH:mm");
                        Date dates = new Date(System.currentTimeMillis());
                        String currentTime=formatters.format(dates);
                        btn_punch_out.setFocusable(true);
                        btn_punch_out.setAlpha(1);
                        btn_punch_in.setFocusable(false);
                        btn_punch_in.setAlpha(0.5f);
                        String text_time = "<font color=#B3202020>You've entered at: </font> <font color=#4983D4>"+currentTime+"</font>";
                        String text_units = "<font color=#B3202020>You're working at: </font> <font color=#4983D4>"+mUnitName+"</font>";
                        String text_comment = "<font color=#B3202020>In Comment : </font> <font color=#4983D4>"+edit_comments.getText().toString()+"</font>";
                        text_enter_time.setText(Html.fromHtml(text_time));
                        text_unit.setText(Html.fromHtml(text_units));
                        text_in_comment.setText(Html.fromHtml(text_comment));

                        UserActivity userActivity = new UserActivity();

                        userActivity.UserId = SharedPreferenceUtil.getUser(mActivity);
                        userActivity.InComment = edit_comments.getText().toString();
                        userActivity.WorkingDate = currentDate;
                        userActivity.PunchInLocation = "Mobile";
                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
                        userActivity.Date = date1;
                        Log.e("dates", "date" + date1);

                        String str = currentTime;
                        if (str == null || str.equals("")) {
                            userActivity.PunchInTime = 0.0;
                        } else {
                            String firstFourChars = "";     //substring containing first 4 characters


                            firstFourChars = str.substring(0, 5);

                            int index = 2;
                            char ch = '.';

                            StringBuilder string = new StringBuilder(firstFourChars);
                            string.setCharAt(index, ch);
                            userActivity.PunchInTime = Double.parseDouble(string.toString());

                        }

                        // userActivity.PunchInTime= Double.parseDouble(str);
                        userActivity.PunchOutLocation = "";
                        userActivity.PunchOutTime = "";
                        userActivity.Duration = "";
                        userActivity.PunchInTimeLate = currentTime;
                        Common.userActivityRepository.insertToUserActivity(userActivity);

                        dismissLoadingProgress();
                    }
                    else {
                        Toast.makeText(mActivity, "Something Wrong", Toast.LENGTH_SHORT).show();
                        dismissLoadingProgress();
                    }


                    //   progressBar.setVisibility(View.GONE);
                }
            }));
        }
        else if (type.equals("out")){
            compositeDisposable.add(mService.postPunch(punchInOutPostEntity).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PunchInOutResponseEntity>() {
                @Override
                public void accept(PunchInOutResponseEntity carts) throws Exception {

                    if (carts.exe_status){
                        Toast.makeText(mActivity, "Successfully Punch Out", Toast.LENGTH_SHORT).show();


                        SimpleDateFormat formatters= new SimpleDateFormat("HH:mm");
                        Date dates = new Date(System.currentTimeMillis());
                        String currentTime=formatters.format(dates);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date(System.currentTimeMillis());
                        String currentDate=formatter.format(date);
                        UserActivity userActivitys=Common.userActivityRepository.getUserActivity(SharedPreferenceUtil.getUser(mActivity),currentDate);
                        String text_time = "<font color=#B3202020>You've entered at: </font> <font color=#4983D4>"+userActivitys.PunchInTimeLate+"</font><font color=#B3202020> And left at: </font> <font color=#4983D4>"+currentTime+"</font>";
                        String text_units = "<font color=#B3202020>You're working at: </font> <font color=#4983D4>"+mUnitName+"</font>";

                        text_enter_time.setText(Html.fromHtml(text_time));
                        text_unit.setText(Html.fromHtml(text_units));

                        try {
                            Date  date1 = simpleDateFormat.parse(userActivitys.PunchInTimeLate);
                            Date date2 = simpleDateFormat.parse(currentTime);
                            long difference = date2.getTime() - date1.getTime();
                            int  days = (int) (difference / (1000*60*60*24));
                            int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
                            int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
                            String duration=hours+":"+min;
                            String text_comment = "<font color=#B3202020>Out Comment : </font> <font color=#4983D4>"+edit_comments.getText().toString()+"</font>";
                            String text_in_comments;
                            if (userActivitys.InComment==null){
                                 text_in_comments = "<font color=#B3202020>In Comment : </font> <font color=#4983D4>"+"N/A"+"</font>";
                            }
                            else {
                                 text_in_comments = "<font color=#B3202020>In Comment : </font> <font color=#4983D4>"+userActivitys.InComment+"</font>";
                            }

                            String text_durations = "<font color=#B3202020>Duration </font> <font color=#4983D4>"+duration+"</font>";
                            text_out_comment.setText(Html.fromHtml(text_comment));
                            text_in_comment.setText(Html.fromHtml(text_in_comments));
                            text_duration.setText(Html.fromHtml(text_durations));
                            Common.userActivityRepository.updatedById(SharedPreferenceUtil.getUser(mActivity),"N/A",currentTime,duration,currentDate);
                            Log.e("duration","duration"+hours+":"+min);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dismissLoadingProgress();
                    }
                    else {
                        Toast.makeText(mActivity, "Something Wrong", Toast.LENGTH_SHORT).show();
                        dismissLoadingProgress();
                    }

                    //   progressBar.setVisibility(View.GONE);
                }
            }));
        }



    }
    private void unitListData(){
        showLoadingProgress(mActivity);
      compositeDisposable.add(Common.unitRepository.getUnitItems().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<Unit>>() {
            @Override
            public void accept(List<Unit> units) throws Exception {
                displayUnitItems(units);
                dismissLoadingProgress();
            }
        }));

   }

    private void displayUnitItems(List<Unit> units) {
        unitArrayList=units;
        unitListEntityArrayAdapter = new ArrayAdapter<Unit>(mActivity, android.R.layout.simple_spinner_item, unitArrayList);
        unitListEntityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(unitListEntityArrayAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }
}
