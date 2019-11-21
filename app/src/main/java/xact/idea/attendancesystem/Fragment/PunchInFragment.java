package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Adapter.PunchInAdapterForAdmin;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Database.Model.UserList;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;


public class PunchInFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    private View mRoot;
    RecyclerView rcl_punch_in_list;
    ArrayList<String> arrayList = new ArrayList<>();
    PunchInAdapterForAdmin mAdapters;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    static  EditText edit_start_date;
   static EditText edit_end_date;
    Button btn_yes;
    String UserId;
    String FullName;
    String UnitName;
    String OfficeExt;
    String DepartmentName;
    String Designation;

    TextView text_name;
    TextView text_unit;
    TextView text_department;
    TextView text_designation;
    TextView text_office_text;
    ProgressBar progress_bar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_unity, container, false);
        mActivity = getActivity();
        correctSizeUtil = correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(mRoot);
        rcl_punch_in_list = mRoot.findViewById(R.id.rcl_punch_in_list);
        LinearLayoutManager lm = new LinearLayoutManager(mActivity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcl_punch_in_list.setLayoutManager(lm);

        mService = Common.getApi();
        for (int j = 0; j < 20; j++) {

            arrayList.add("House " + j);
        }


        //  mAdapters = new PunchInAdapter(mActivity, arrayList);

        rcl_punch_in_list.setAdapter(mAdapters);
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        //loadData();
        initView();
        Load();
    }

    private void initView() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            UserId = bundle.getString("UserId", null);
            FullName = bundle.getString("FullName", null);
            UnitName = bundle.getString("UnitName", null);
            OfficeExt = bundle.getString("OfficeExt", null);
            DepartmentName = bundle.getString("DepartmentName", null);
            Designation = bundle.getString("Designation", null);
            Log.e("FullName", "FullName" + FullName);
            Log.e("UserId", "UserId" + UserId);
        }
        text_name = mRoot.findViewById(R.id.text_name);
        progress_bar = mRoot.findViewById(R.id.progress_bar);
        text_unit = mRoot.findViewById(R.id.text_unit);
        text_department = mRoot.findViewById(R.id.text_department);
        text_designation = mRoot.findViewById(R.id.text_designation);
        text_office_text = mRoot.findViewById(R.id.text_office_text);
        edit_start_date = mRoot.findViewById(R.id.edit_start_date);

        edit_end_date = mRoot.findViewById(R.id.edit_end_date);
        btn_yes = mRoot.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_start_date.getText().toString().matches("")) {
                    Toast.makeText(mActivity, "You did not enter a Start Date", Toast.LENGTH_SHORT).show();

                }
                else if (edit_end_date.getText().toString().matches("")){
                    Toast.makeText(mActivity, "You did not enter a End Date", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadDataByDate();
                }


            }
        });
        edit_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new DatePickerFromFragment();

                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });
        edit_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new DatePickerFromFragment.DatePickerToFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });
        text_name.setText(FullName);
        UnitName = UnitName != null ? UnitName : "N/A";
        DepartmentName = DepartmentName != null ? DepartmentName : "N/A";
        Designation = Designation != null ? Designation : "N/A";
        OfficeExt = OfficeExt != null ? OfficeExt : "N/A";


        text_unit.setText(UnitName);
        text_department.setText(DepartmentName);
        text_designation.setText(Designation);
        text_office_text.setText(OfficeExt);

    }
    static Calendar now = Calendar.getInstance();
    public static class DatePickerFromFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            try {
                calendar.setTime(sdf.parse(edit_start_date.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);

            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = formatter.format(chosenDate);
            EditText startTime2 = (EditText) getActivity().findViewById(R.id.edit_start_date);
            startTime2.setText(formattedDate);


        }


        public static class DatePickerToFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                final Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                try {
                    calendar.setTime(sdf.parse(edit_end_date.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);

                return dpd;
            }

            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(year, month, day, 0, 0, 0);
                Date chosenDate = cal.getTime();
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = formatter.format(chosenDate);
                EditText endTime2 = (EditText) getActivity().findViewById(R.id.edit_end_date);
                endTime2.setText(formattedDate);
            }
        }


    }

    private void  Load(){
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());

        String currentDate=formatter.format(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        String lastDate=formatter.format(c.getTime());
        Log.e("sdfsdf","sdfds"+lastDate);
        edit_start_date.setText(lastDate);
        edit_end_date.setText(currentDate);
        String startDate=edit_start_date.getText().toString();
        String endDate=edit_end_date.getText().toString();
        Date date1 = null;
        Date date2 = null;
        try {
            date1=new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
            date2=new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        progress_bar.setVisibility(View.VISIBLE);
        compositeDisposable.add(Common.userActivityRepository.getUserActivityItemByDate(date1,date2,UserId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<UserActivity>>() {
            @Override
            public void accept(List<UserActivity> userActivities) throws Exception {
               displayUnitItems(userActivities);
                progress_bar.setVisibility(View.GONE);
            }
        }));
    }
    private void loadDataByDate() {
        progress_bar.setVisibility(View.VISIBLE);
        String startDate=edit_start_date.getText().toString();
        String endDate=edit_end_date.getText().toString();
        Date date1 = null;
        Date date2 = null;
        try {
             date1=new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
             date2=new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        compositeDisposable.add(Common.userActivityRepository.getUserActivityItemByDate(date1,date2,UserId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<UserActivity>>() {
            @Override
            public void accept(List<UserActivity> userActivities) throws Exception {
                displayUnitItems(userActivities);
                progress_bar.setVisibility(View.GONE);

            }
        }));

    }


    private void displayUnitItems(List<UserActivity> userActivities) {
        showLoadingProgress(mActivity);
        mAdapters = new PunchInAdapterForAdmin(mActivity, userActivities);

        rcl_punch_in_list.setAdapter(mAdapters);
        dismissLoadingProgress();

    }
//    private void loadData() {
//        showLoadingProgress(mActivity);
//        compositeDisposable.add(mService.getUserActivity().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<UserActivityEntity>>() {
//            @Override
//            public void accept(ArrayList<UserActivityEntity> carts) throws Exception {
//
//                mAdapters = new PunchInAdapter(mActivity, carts);
//
//                rcl_punch_in_list.setAdapter(mAdapters);
//                dismissLoadingProgress();
//            }
//        }));
//
//
//    }

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
