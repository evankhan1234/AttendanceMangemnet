package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Entity.PunchInOutPostEntity;
import xact.idea.attendancesystem.Entity.PunchInOutResponseEntity;
import xact.idea.attendancesystem.Fragment.PunchFragment;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.SharedPreferenceUtil;
import xact.idea.attendancesystem.Utils.Utils;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;

public class PunchActivity extends AppCompatActivity {
    static Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    View mRootView;
    Spinner spinnerUnit;
    static TextView text_unit;
    static Button btn_punch_in;
    static Button btn_punch_out;
    static TextView text_enter_time;
    static TextView text_in_comment;
    TextView text_out_comment;
    EditText edit_comments;
    TextView text_duration;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    private List<Unit> unitArrayList = new ArrayList<>();
    ArrayAdapter<Unit> unitListEntityArrayAdapter;
    int mUnitId;
    String mUnitStation;
    String mUnitName;

    ImageButton btn_header_back_;
    LinearLayout rlt_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch);
        mActivity=this;

        CorrectSizeUtil.getInstance(this).correctSize();
        CorrectSizeUtil.getInstance(this).correctSize(findViewById(R.id.rlt_root));
        mService = Common.getApiXact();
        initView();
    }
    private void initView() {
        rlt_root = findViewById(R.id.rlt_root);
        text_out_comment = findViewById(R.id.text_out_comment);
        btn_header_back_ = findViewById(R.id.btn_header_back_);
        text_in_comment = findViewById(R.id.text_in_comment);

        text_duration = findViewById(R.id.text_duration);
        text_unit = findViewById(R.id.text_unit);
        text_enter_time = findViewById(R.id.text_enter_time);
        btn_punch_in = findViewById(R.id.btn_punch_in);
        btn_punch_out = findViewById(R.id.btn_punch_out);
        edit_comments = findViewById(R.id.edit_comments);
        spinnerUnit = findViewById(R.id.spinner_unit);

        btn_header_back_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PunchActivity.this,DashboardActivity.class));
                finish();
            }
        });
        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("sp_units", "" + unitArrayList.get(position).UnitName);
                mUnitId = unitArrayList.get(position).Id;
                mUnitStation = unitArrayList.get(position).ShortName;
                mUnitName = unitArrayList.get(position).UnitName;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_punch_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.broadcastIntent(mActivity, rlt_root)) {
                    //Toast.makeText(mContext, "Connected ", Toast.LENGTH_SHORT).show();
                    UserActivityData("in");
                } else {
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
                                if (Utils.broadcastIntent(mActivity, rlt_root)) {
                                    //Toast.makeText(mContext, "Connected ", Toast.LENGTH_SHORT).show();
                                    UserActivityData("out");
                                } else {
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



    public static void show() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        final String currentDate = formatter.format(date);
        UserActivity userActivitys = Common.userActivityRepository.getUserActivity(SharedPreferenceUtil.getUser(mActivity), currentDate);
        if (userActivitys != null) {
            if (userActivitys.PunchInTimeLate != null) {
                btn_punch_out.setFocusable(true);
                btn_punch_out.setAlpha(1);
                btn_punch_in.setFocusable(false);
                btn_punch_in.setClickable(false);
                btn_punch_in.setAlpha(0.5f);
                String text_time = "<font color=#B3202020>You've entered at: </font> <font color=#4983D4>" + userActivitys.PunchInTimeLate + "</font>";
                String text_units = "<font color=#B3202020>You're working at: </font> <font color=#4983D4>" + userActivitys.UnitName + "</font>";
                String text_comment = "<font color=#B3202020>In Comment : </font> <font color=#4983D4>" + userActivitys.InComment + "</font>";
                text_enter_time.setText(Html.fromHtml(text_time));
                text_unit.setText(Html.fromHtml(text_units));
                text_in_comment.setText(Html.fromHtml(text_comment));

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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date);
        PunchInOutPostEntity punchInOutPostEntity = new PunchInOutPostEntity();
        punchInOutPostEntity.user_id = Integer.parseInt(SharedPreferenceUtil.getUser(mActivity));
        punchInOutPostEntity.unit_id = mUnitId;
        punchInOutPostEntity.comment = edit_comments.getText().toString();
        punchInOutPostEntity.punch_type = type;
        punchInOutPostEntity.work_station = mUnitStation;
        punchInOutPostEntity.punch_date_time = currentDate;
        if (type.equals("in")) {
            compositeDisposable.add(mService.postPunch(punchInOutPostEntity).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PunchInOutResponseEntity>() {
                @Override
                public void accept(PunchInOutResponseEntity carts) throws Exception {
                    // departmentListEntityList=carts;if
                    if (carts.exe_status) {
                        Toast.makeText(mActivity, "Successfully Punch In", Toast.LENGTH_SHORT).show();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = new Date(System.currentTimeMillis());
                        String currentDate = formatter.format(date);
                        SimpleDateFormat formatters = new SimpleDateFormat("hh:mm a");
                        Date dates = new Date(System.currentTimeMillis());
                        String currentTime = formatters.format(dates);
                        btn_punch_out.setFocusable(true);
                        btn_punch_out.setAlpha(1);
                        btn_punch_in.setFocusable(false);
                        btn_punch_in.setAlpha(0.5f);
                        String text_time = "<font color=#B3202020>You've entered at: </font> <font color=#4983D4>" + currentTime + "</font>";
                        String text_units = "<font color=#B3202020>You're working at: </font> <font color=#4983D4>" + mUnitName + "</font>";
                        String text_comment = "<font color=#B3202020>In Comment : </font> <font color=#4983D4>" + edit_comments.getText().toString() + "</font>";
                        text_enter_time.setText(Html.fromHtml(text_time));
                        text_unit.setText(Html.fromHtml(text_units));
                        text_in_comment.setText(Html.fromHtml(text_comment));

                        UserActivity userActivity = new UserActivity();

                        userActivity.UserId = SharedPreferenceUtil.getUser(mActivity);
                        userActivity.InComment = edit_comments.getText().toString();
                        userActivity.UnitName = mUnitName;
                        userActivity.WorkingDate = currentDate;
                        userActivity.PunchInLocation = mUnitName;
                        Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(currentDate);
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
                        userActivity.PunchOutLocation = mUnitName;
                        userActivity.PunchOutTime = "";
                        userActivity.Duration = "";
                        userActivity.PunchInTimeLate = currentTime;
                        Common.userActivityRepository.insertToUserActivity(userActivity);

                        dismissLoadingProgress();
                    } else {
                        Toast.makeText(mActivity, "Something Wrong", Toast.LENGTH_SHORT).show();
                        dismissLoadingProgress();
                    }


                    //   progressBar.setVisibility(View.GONE);
                }
            }));
        } else if (type.equals("out")) {
            compositeDisposable.add(mService.postPunch(punchInOutPostEntity).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PunchInOutResponseEntity>() {
                @Override
                public void accept(PunchInOutResponseEntity carts) throws Exception {

                    if (carts.exe_status) {
                        Toast.makeText(mActivity, "Successfully Punch Out", Toast.LENGTH_SHORT).show();


                        SimpleDateFormat formatters = new SimpleDateFormat("hh:mm a");
                        Date dates = new Date(System.currentTimeMillis());
                        String currentTime = formatters.format(dates);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = new Date(System.currentTimeMillis());
                        String currentDate = formatter.format(date);
                        UserActivity userActivitys = Common.userActivityRepository.getUserActivity(SharedPreferenceUtil.getUser(mActivity), currentDate);
                        String text_time = null;
                        try {
                            text_time = "<font color=#B3202020>You've entered at: </font> <font color=#4983D4>" + userActivitys.PunchInTimeLate + "</font><font color=#B3202020> And left at: </font> <font color=#4983D4>" + currentTime + "</font>";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String text_units = "<font color=#B3202020>You're working at: </font> <font color=#4983D4>" + mUnitName + "</font>";

                        text_enter_time.setText(Html.fromHtml(text_time));
                        text_unit.setText(Html.fromHtml(text_units));

                        try {
                            Date date1 = simpleDateFormat.parse(userActivitys.PunchInTimeLate);
                            Log.e("da1", "da1" + date1.toString());
                            Date date2 = simpleDateFormat.parse(currentTime);
                            long difference = date2.getTime() - date1.getTime();
                            int days = (int) (difference / (1000 * 60 * 60 * 24));
                            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
                            String duration = hours + ":" + min;
                            String text_comment = "<font color=#B3202020>Out Comment : </font> <font color=#4983D4>" + edit_comments.getText().toString() + "</font>";
                            String text_in_comments;
                            if (userActivitys.InComment == null) {
                                text_in_comments = "<font color=#B3202020>In Comment : </font> <font color=#4983D4>" + "N/A" + "</font>";
                            } else {
                                text_in_comments = "<font color=#B3202020>In Comment : </font> <font color=#4983D4>" + userActivitys.InComment + "</font>";
                            }

                            String text_durations = "<font color=#B3202020>Duration </font> <font color=#4983D4>" + duration + "</font>";
                            text_out_comment.setText(Html.fromHtml(text_comment));
                            text_in_comment.setText(Html.fromHtml(text_in_comments));
                            text_duration.setText(Html.fromHtml(text_durations));
                            Common.userActivityRepository.updatedById(SharedPreferenceUtil.getUser(mActivity), "N/A", currentTime, duration, currentDate);
                            Log.e("duration", "duration" + hours + ":" + min);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dismissLoadingProgress();
                    } else {
                        Toast.makeText(mActivity, "Something Wrong", Toast.LENGTH_SHORT).show();
                        dismissLoadingProgress();
                    }

                    //   progressBar.setVisibility(View.GONE);
                }
            }));
        }


    }

    private void unitListData() {
        showLoadingProgress(mActivity);
        compositeDisposable.add(Common.unitRepository.getUnitItems().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<Unit>>() {
            @Override
            public void accept(List<Unit> units) throws Exception {
                try {
                    units.remove(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                displayUnitItems(units);
                dismissLoadingProgress();
            }
        }));

    }

    private void displayUnitItems(List<Unit> units) {
        unitArrayList = units;
        unitListEntityArrayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, unitArrayList);
        unitListEntityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(unitListEntityArrayAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }
}
