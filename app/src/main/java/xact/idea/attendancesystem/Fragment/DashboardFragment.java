package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.icu.lang.UScript;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;
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
import xact.idea.attendancesystem.Activity.MainActivity;
import xact.idea.attendancesystem.Adapter.DepartmentAdapter;
import xact.idea.attendancesystem.Adapter.PunchInAdapter;
import xact.idea.attendancesystem.Adapter.PunchInAdapterForAdmin;
import xact.idea.attendancesystem.Adapter.UnitAdapter;
import xact.idea.attendancesystem.Database.Model.Department;
import xact.idea.attendancesystem.Database.Model.SetUp;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Entity.AttendanceEntity;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.Entity.UserActivityListEntity;
import xact.idea.attendancesystem.Entity.UserTotalLeaveEntity;
import xact.idea.attendancesystem.Interface.DepartmentClickInterface;
import xact.idea.attendancesystem.Interface.UnitClickInterface;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.Constant;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;


public class DashboardFragment extends Fragment {
    static Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
   static View view;
    long presentEmp, absentEmp, leaveEmp;
    static List<Integer> ydata = new ArrayList<>();
    static List<Integer> ydataAttendance = new ArrayList<>();
    static  private String[] xdata = {"Present", "Absent", "Leave"};
    static  private String[] xdataAttendance = {"On-Time", "Late"};
    static EditText edit_content;
    static PieChart pieChart;
    static PieChart pieAttendanceStatus;
    static RadioButton radioPresent;
    static  RadioButton radioAbsent;
    static  RadioButton radioPresentLate;
    static  RadioButton radioleave;
    static  RadioButton radioPresentOnTime;
    static RadioButton radioAll;
    static RadioButton radioAllPresent;
    static HorizontalScrollView scr_category_present;
    IRetrofitApi mService;
    static CompositeDisposable compositeDisposable = new CompositeDisposable();
    static private RecyclerView rcl_this_unit_list;
    static private RecyclerView rcl_this_department_list;
    static  private RecyclerView rcl_approval_in_list;
    static   private  TextView text_date_current;
    static  private PunchInAdapter mAdapters = null;
    static  private UnitAdapter mUnitAdapter = null;
    static private DepartmentAdapter mDepartmentAdapter = null;
    static  List<DepartmentListEntity> departmentListEntityList = new ArrayList<>();
    static RadioGroup rg;
    static RadioGroup radioPresents;
    static  List<UnitListEntity> unitListEntityList = new ArrayList<>();
    static List<AttendanceEntity> userActivities = new ArrayList<>();
    static  int unitValue;
    static int departmentValue;
    static SetUp setUp;
    static double inTime;
    static double graceTime;
    static double total;
    static String currentDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mActivity = getActivity();
        correctSizeUtil = correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(view);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        currentDate = formatter.format(date);
        initView();
        check();
//        UserActivity userActivity = new UserActivity();
//
//
//            userActivity.UserId="7";
//            userActivity.WorkingDate="2019-11-14";
//            userActivity.PunchInLocation="Dhaka";
//
//            String str = "09:40";
//            if (str==null || str.equals("")){
//                userActivity.PunchInTime= 0.0;
//            }else {
//                String firstFourChars = "";     //substring containing first 4 characters
//
//
//                firstFourChars = str.substring(0, 5);
//
//                int index = 2;
//                char ch = '.';
//
//                StringBuilder string = new StringBuilder(firstFourChars);
//                string.setCharAt(index, ch);
//                userActivity.PunchInTime= Double.parseDouble(string.toString());
//
//            }
//
//            // userActivity.PunchInTime= Double.parseDouble(str);
//            userActivity.PunchOutLocation="Banani";
//            userActivity.PunchOutTime="05:00";
//            userActivity.Duration="8:00";
//            userActivity.PunchInTimeLate="09:40";
//            Common.userActivityRepository.insertToUserActivity(userActivity);

        //loAD();
        //  Toast.makeText(mActivity, String.valueOf( Common.userActivityRepository.size()), Toast.LENGTH_SHORT).show();
        setUp = Common.setUpDataRepository.getSetUpItems();
        String str = setUp.OFFICE_IN_TIME;
        String str_grace = setUp.GRACE_TIME;
        if (str == null || str.equals("")) {
            inTime = 0.0;
        } else {
            String firstFourChars = "";     //substring containing first 4 characters


            firstFourChars = str.substring(0, 5);

            int index = 2;
            char ch = '.';

            StringBuilder string = new StringBuilder(firstFourChars);
            string.setCharAt(index, ch);
            inTime = Double.parseDouble(string.toString());

        }
        if (str_grace == null || str_grace.equals("")) {
            graceTime = 0.0;
        } else {
            String firstFourChars = "";     //substring containing first 4 characters


            firstFourChars = str_grace.substring(0, 5);

            int index = 2;
            char ch = '.';

            StringBuilder string = new StringBuilder(firstFourChars);
            string.setCharAt(index, ch);
            graceTime = Double.parseDouble(string.toString());


            total = inTime + graceTime;

        }
        Log.e("total", "total" + total);
        return view;
    }
    public int handleBackPress() {

        Log.e("evan","evan"+getFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName()));
        if (getFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName()) != null) {
            DashboardFragment f = (DashboardFragment) getFragmentManager()
                    .findFragmentByTag(DashboardFragment.class.getSimpleName());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
            transaction.remove(f);
            transaction.commit();
            getFragmentManager().popBackStack();


            return 2;

        }

        return 2;

    }
    private void initView() {
        radioAllPresent =view. findViewById(R.id.radioAllPresent);
         rg =view. findViewById(R.id.radioSex);
        radioPresents =view. findViewById(R.id.radioPresents);
        radioPresentOnTime = view.findViewById(R.id.radioPresentOnTime);
        text_date_current = view.findViewById(R.id.text_date_current);
        radioPresentLate = view.findViewById(R.id.radioPresentLate);
        radioAll = view.findViewById(R.id.radioAll);
        radioAbsent = view.findViewById(R.id.radioAbsent);
        radioleave = view.findViewById(R.id.radioleave);
        edit_content = view.findViewById(R.id.edit_content);
        radioPresent = view.findViewById(R.id.radioPresent);
        scr_category_present = view.findViewById(R.id.scr_category_present);
        rcl_approval_in_list = view.findViewById(R.id.rcl_approval_in_list);
        rcl_this_unit_list = view.findViewById(R.id.rcl_this_unit_list);
        rcl_this_department_list = view.findViewById(R.id.rcl_this_department_list);

        LinearLayoutManager lm = new LinearLayoutManager(mActivity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcl_approval_in_list.setLayoutManager(lm);

        LinearLayoutManager lm1 = new LinearLayoutManager(mActivity);
        lm1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcl_this_unit_list.setLayoutManager(lm1);

        LinearLayoutManager lm2 = new LinearLayoutManager(mActivity);
        lm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcl_this_department_list.setLayoutManager(lm2);
        mService = Common.getApi();
        pieChart = view.findViewById(R.id.pieEmployeeStatus);
        pieChart.setDrawHoleEnabled(true);

        pieChart.setDescription("Employee Status");
        pieChart.setDescriptionTextSize(13);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setCenterText("Status");
        pieChart.setCenterTextSize(10);

        pieChart.setTransparentCircleRadius(50f);
        Legend l = pieChart.getLegend();
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        text_date_current.setText(currentDate);


        ////
        pieAttendanceStatus = view.findViewById(R.id.pieAttendanceStatus);
        pieAttendanceStatus.setDrawHoleEnabled(true);

        pieAttendanceStatus.setDescription("Attendance Status");
        pieAttendanceStatus.setDescriptionTextSize(13);
        pieAttendanceStatus.setRotationEnabled(true);
        pieAttendanceStatus.setHoleRadius(25f);
        pieAttendanceStatus.setCenterText("Status");
        pieAttendanceStatus.setCenterTextSize(10);

        pieAttendanceStatus.setTransparentCircleRadius(50f);
        Legend l1 = pieAttendanceStatus.getLegend();
        l1.setXEntrySpace(7);
        l1.setYEntrySpace(5);
        l1.setForm(Legend.LegendForm.CIRCLE);
        l1.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);

        radioPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scr_category_present.setVisibility(View.VISIBLE);


                if (unitValue > 0 && departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("present");

                } else if (unitValue > 0) {
                    loadDataActivityUnitDepartmentWise("present");
                } else if (departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("present");
                } else {
                    loadDataActivity("present");
                }

            }
        });
        radioAllPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scr_category_present.setVisibility(View.VISIBLE);


                if (unitValue > 0 && departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("present");

                } else if (unitValue > 0) {
                    loadDataActivityUnitDepartmentWise("present");
                } else if (departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("present");
                } else {
                    loadDataActivity("present");
                }

            }
        });

        radioAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scr_category_present.setVisibility(View.GONE);
                if (unitValue > 0 && departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("all");
                } else if (unitValue > 0) {
                    loadDataActivityUnitDepartmentWise("all");
                } else if (departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("all");
                } else {
                    loadDataActivity("all");
                }
            }
        });
        radioAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scr_category_present.setVisibility(View.GONE);
                if (unitValue > 0 && departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("absent");
                } else if (unitValue > 0) {
                    loadDataActivityUnitDepartmentWise("absent");
                } else if (departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("absent");
                } else {
                    loadDataActivity("absent");
                }
            }
        });
        radioPresentLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (unitValue > 0 && departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("late");
                } else if (unitValue > 0) {
                    loadDataActivityUnitDepartmentWise("late");
                } else if (departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("late");
                } else {
                    loadDataActivity("late");
                }
            }
        });
        radioPresentOnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (unitValue > 0 && departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("ontime");
                } else if (unitValue > 0) {
                    loadDataActivityUnitDepartmentWise("ontime");
                } else if (departmentValue > 0) {
                    loadDataActivityUnitDepartmentWise("ontime");
                } else {
                    loadDataActivity("ontime");
                }
            }
        });
        radioleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scr_category_present.setVisibility(View.GONE);
            }
        });
        text_date_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new DatePickerToFragment();

                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });
        edit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAdapters.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public  static class DatePickerToFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            try {
                calendar.setTime(sdf.parse(text_date_current.getText().toString()));
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
            TextView endTime2 = (TextView) getActivity().findViewById(R.id.text_date_current);
            endTime2.setText(formattedDate);
            text_date_current.setText(formattedDate);
            currentDate=formattedDate;
            radioAll.setChecked(true);
            scr_category_present.setVisibility(View.GONE);
            loadUnitItems();
            //DepartmentListData();
            loadDataActivity("all");
            loadDepartmentItems();
            EmployeeStaus();
        }



}
    private static DepartmentClickInterface mClickDepartment = new DepartmentClickInterface() {
        @Override
        public void onItemClick(int position) {
            departmentValue=position;
            int selectedRadioButtonID = rg.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = view. findViewById(selectedRadioButtonID);
            String selectedRadioButtonText = selectedRadioButton.getText().toString();
         //   Toast.makeText(mActivity, selectedRadioButtonText, Toast.LENGTH_SHORT).show();
            int selectedRadioButtonIDPresent = radioPresents.getCheckedRadioButtonId();
            RadioButton selectedRadioPresentsButton = view. findViewById(selectedRadioButtonIDPresent);
            String selectedRadioPresentButtonText = selectedRadioPresentsButton.getText().toString();

            switch(selectedRadioButtonText){
                case "All":

                    loadUnitDepartmentWise();
                    EmployeeStausUnitDepartmentWise();
                    // do operations specific to this selection
                    break;
                case "Present":
                    EmployeeStausUnitDepartmentWise();
                    if (unitValue > 0 && departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("present");

                    } else if (unitValue > 0) {
                        loadDataActivityUnitDepartmentWise("present");
                    } else if (departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("present");
                    } else {
                        loadDataActivity("present");
                    }
                    break;
                case "Absent":
                    EmployeeStausUnitDepartmentWise();
                    if (unitValue > 0 && departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("absent");
                    } else if (unitValue > 0) {
                        loadDataActivityUnitDepartmentWise("absent");
                    } else if (departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("absent");
                    } else {
                        loadDataActivity("absent");
                    }
                    break;
                case "Leave":
                    // do operations specific to this selection
                    break;
            }


            switch(selectedRadioPresentButtonText){

                case "On-Time":
                    EmployeeStausUnitDepartmentWise();
                    if (unitValue > 0 && departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("ontime");
                    } else if (unitValue > 0) {
                        loadDataActivityUnitDepartmentWise("ontime");
                    } else if (departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("ontime");
                    } else {
                        loadDataActivity("ontime");
                    }
                    break;
                case "Late":
                    EmployeeStausUnitDepartmentWise();
                    if (unitValue > 0 && departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("late");
                    } else if (unitValue > 0) {
                        loadDataActivityUnitDepartmentWise("late");
                    } else if (departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("late");
                    } else {
                        loadDataActivity("late");
                    }
                    break;

            }

            //  Toast.makeText(mActivity, "Department "+departmentValue+"Unit"+unitValue, Toast.LENGTH_SHORT).show();
//           Common.departmentRepository.emptyCart();
//            DepartmentListData();
           // loadLeaveTotal();

        }
    };
    private static UnitClickInterface mClickUnit = new UnitClickInterface() {
        @Override
        public void onItemClick(int position) {
            unitValue=position;

            int selectedRadioButtonID = rg.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = view. findViewById(selectedRadioButtonID);
            String selectedRadioButtonText = selectedRadioButton.getText().toString();
          //  Toast.makeText(mActivity, selectedRadioButtonText, Toast.LENGTH_SHORT).show();
            int selectedRadioButtonIDPresent = radioPresents.getCheckedRadioButtonId();
            RadioButton selectedRadioPresentsButton = view. findViewById(selectedRadioButtonIDPresent);
            String selectedRadioPresentButtonText = selectedRadioPresentsButton.getText().toString();
            switch(selectedRadioButtonText){
                case "All":

                    loadUnitDepartmentWise();
                    EmployeeStausUnitDepartmentWise();
                    // do operations specific to this selection
                    break;
                case "Present":

                    if (unitValue > 0 && departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("present");

                    } else if (unitValue > 0) {
                        loadDataActivityUnitDepartmentWise("present");
                    } else if (departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("present");
                    } else {
                        loadDataActivity("present");
                    }
                    break;
                case "Absent":
                    if (unitValue > 0 && departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("absent");
                    } else if (unitValue > 0) {
                        loadDataActivityUnitDepartmentWise("absent");
                    } else if (departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("absent");
                    } else {
                        loadDataActivity("absent");
                    }
                    break;
                case "Leave":
                    // do operations specific to this selection
                    break;
            }
            switch(selectedRadioPresentButtonText){

                case "On-Time":
                    EmployeeStausUnitDepartmentWise();
                    if (unitValue > 0 && departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("ontime");
                    } else if (unitValue > 0) {
                        loadDataActivityUnitDepartmentWise("ontime");
                    } else if (departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("ontime");
                    } else {
                        loadDataActivity("ontime");
                    }
                    break;
                case "Late":
                    EmployeeStausUnitDepartmentWise();
                    if (unitValue > 0 && departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("late");
                    } else if (unitValue > 0) {
                        loadDataActivityUnitDepartmentWise("late");
                    } else if (departmentValue > 0) {
                        loadDataActivityUnitDepartmentWise("late");
                    } else {
                        loadDataActivity("late");
                    }
                    break;

            }



           // Toast.makeText(mActivity, "Department "+departmentValue+"Unit"+unitValue, Toast.LENGTH_SHORT).show();
         //   loadLeaveTotal();


        }
    };
    private static void addDataSet() {

        ArrayList<Entry> YEntry = new ArrayList<>();

        for (int i = 0; i < ydata.size(); i++) {
            YEntry.add(new Entry(ydata.get(i), i));

        }
        ArrayList<String> XEntry = new ArrayList<>();
        for (int j = 0; j < xdata.length; j++) {

            XEntry.add(xdata[j]);
        }
        PieDataSet pie = new PieDataSet(YEntry, "");
        pie.setSliceSpace(3);
        pie.setSelectionShift(5);
        pie.setValueTextSize(35);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#21a839"));
        colors.add(Color.RED);
        colors.add(Color.parseColor("#eab259"));


        pie.setColors(colors);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setTextSize(13);
        pie.setValueFormatter(new MyValueFormatter());
        PieData pieData = null;
        try {
            pieData = new PieData(xdata, pie);
            pieData.setValueTextSize(10f);
            pieData.setValueTextColor(Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    private static void loadUnitDepartmentWise(){
        showLoadingProgress(mActivity);

        if (unitValue>0 && departmentValue>0){
            compositeDisposable.add(Common.userActivityRepository.getListUnitIdDepartmentId(unitValue,departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                @Override
                public void accept(List<AttendanceEntity> userActivities) throws Exception {
                    display(userActivities,"");
                    dismissLoadingProgress();

                }
            }));
        }else if (unitValue>0){
            compositeDisposable.add(Common.userActivityRepository.getListUnitId(unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                @Override
                public void accept(List<AttendanceEntity> userActivities) throws Exception {
                    display(userActivities,"");
                    dismissLoadingProgress();
                }
            }));
        }
        else if (departmentValue>0){
            compositeDisposable.add(Common.userActivityRepository.getListDepartmentId(departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                @Override
                public void accept(List<AttendanceEntity> userActivities) throws Exception {
                    display(userActivities,"");
                    dismissLoadingProgress();
                }
            }));
        }
        else {
            loadDataActivity("all");
            dismissLoadingProgress();
        }


    }
    private static void EmployeeStausUnitDepartmentWise(){








            if (unitValue>0 && departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getPresentUnitDepartmentList(currentDate,unitValue,departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        present=userActivities.size();
                    }
                }));
            }else if (unitValue>0){
                compositeDisposable.add(Common.userActivityRepository.getPresentUnitList(currentDate,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        present=userActivities.size();
                    }
                }));
            }
            else if (departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getPresentDepartmentList(currentDate,departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        present=userActivities.size();
                    }
                }));
            }
            else {
                compositeDisposable.add(Common.userActivityRepository.getPresentList(currentDate).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        present=userActivities.size();
                    }
                }));
            }






            if (unitValue>0 && departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getAbsentUnitDepartmentList(currentDate,departmentValue,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        absent=userActivities.size();
                    }
                }));
            }else if (unitValue>0){
                compositeDisposable.add(Common.userActivityRepository.getAbsentUnitList(currentDate,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        absent=userActivities.size();
                    }
                }));
            }
            else if (departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getAbsentDepartmentList(currentDate,departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        absent=userActivities.size();
                    }
                }));
            }
            else {

                compositeDisposable.add(Common.userActivityRepository.getAbsentList(currentDate).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        absent=userActivities.size();
                    }
                }));

            }





            if (unitValue > 0 && departmentValue > 0) {
                compositeDisposable.add(Common.userActivityRepository.getLateUnitDepartmentList(currentDate, total, departmentValue, unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        late=userActivities.size();
                    }
                }));
            } else if (unitValue > 0) {
                compositeDisposable.add(Common.userActivityRepository.getLateUnitList(currentDate, total, unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        late=userActivities.size();
                    }
                }));
            } else if (departmentValue > 0) {
                compositeDisposable.add(Common.userActivityRepository.getLateDepartmentList(currentDate, total, departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        late=userActivities.size();
                    }
                }));
            }
            else {

                compositeDisposable.add(Common.userActivityRepository.getLateList(currentDate,total).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        late=userActivities.size();
                    }
                }));
            }


            if (unitValue > 0 && departmentValue > 0) {
                compositeDisposable.add(Common.userActivityRepository.getOnTimeUnitDepartmentList(currentDate, total,  departmentValue,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        ontime=userActivities.size();
                    }
                }));
            } else if (unitValue > 0) {
                compositeDisposable.add(Common.userActivityRepository.getOnTimeUnitList(currentDate, total, unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        ontime=userActivities.size();
                    }
                }));
            } else if (departmentValue > 0) {
                compositeDisposable.add(Common.userActivityRepository.getOnTimeDepartmentList(currentDate, total, departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        ontime=userActivities.size();
                    }
                }));


        }
            else {


                compositeDisposable.add(Common.userActivityRepository.getOnTimeList(currentDate,total).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        ontime=userActivities.size();
                    }
                }));
            }
        ydata.clear();
        ydataAttendance.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ydata.add(Integer.valueOf(present));
                ydata.add(Integer.valueOf(absent));
                ydata.add(Integer.valueOf(0));
                ydataAttendance.add(Integer.valueOf(ontime));
                ydataAttendance.add(Integer.valueOf(late));
                addDataSet();
                addDataSetAttendance();
            }
        }, 300);



    }
    private static void loadDataActivityUnitDepartmentWise(String value) {


        if (value.equals("present")){
            showLoadingProgress(mActivity);
            if (unitValue>0 && departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getPresentUnitDepartmentList(currentDate,unitValue,departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }else if (unitValue>0){
                compositeDisposable.add(Common.userActivityRepository.getPresentUnitList(currentDate,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }
            else if (departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getPresentDepartmentList(currentDate,departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }



        }
        else if (value.equals("late")){
            showLoadingProgress(mActivity);
            if (unitValue>0 && departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getLateUnitDepartmentList(currentDate,total,departmentValue,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }else if (unitValue>0){
                compositeDisposable.add(Common.userActivityRepository.getLateUnitList(currentDate,total,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }
            else if (departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getLateDepartmentList(currentDate,total,departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }

        }
        else if (value.equals("all")){
            showLoadingProgress(mActivity);
            if (unitValue>0 && departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getListUnitIdDepartmentId(unitValue,departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }else if (unitValue>0){
                compositeDisposable.add(Common.userActivityRepository.getListUnitId(unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }
            else if (departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getListDepartmentId(departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }
            else {
                loadDataActivity("all");
            }

        }
        else if (value.equals("ontime")){
            showLoadingProgress(mActivity);
            if (unitValue>0 && departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getOnTimeUnitDepartmentList(currentDate,total,departmentValue,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }else if (unitValue>0){
                compositeDisposable.add(Common.userActivityRepository.getOnTimeUnitList(currentDate,total,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }
            else if (departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getOnTimeDepartmentList(currentDate,total,departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }

        }
        else if (value.equals("absent")){
            showLoadingProgress(mActivity);
            if (unitValue>0 && departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getAbsentUnitDepartmentList(currentDate,departmentValue,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"absent");
                        dismissLoadingProgress();
                    }
                }));
            }else if (unitValue>0){
                compositeDisposable.add(Common.userActivityRepository.getAbsentUnitList(currentDate,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"absent");
                        dismissLoadingProgress();
                    }
                }));
            }
            else if (departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getAbsentDepartmentList(currentDate,departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"absent");
                        dismissLoadingProgress();
                    }
                }));
            }
        }



    }
    private void check(){
//        String strTime = "19:48";
//        String endTime = "19:54";
//        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
//        try {
//            Date dates = dateFormat.parse(strTime);
//            Date enddates = dateFormat.parse(endTime);
//            SimpleDateFormat formatters= new SimpleDateFormat("hh:mm a");
//
//            String currentTime=formatters.format(dates);
//            String endTimes=formatters.format(enddates);
//            long difference = enddates.getTime() - dates.getTime();
//            int  days = (int) (difference / (1000*60*60*24));
//            int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
//            int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
//            Log.e("currentTime","currentTime"+currentTime);
//            Log.e("endTime","endTime"+endTimes);
//            Log.e("difference","difference"+days +":"+min);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }
    List<AttendanceEntity> users= new ArrayList<>();
    private static void loadDataActivity(String value) {

        if (value.equals("present")){
            showLoadingProgress(mActivity);
            compositeDisposable.add(Common.userActivityRepository.getPresentList(currentDate).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                @Override
                public void accept(List<AttendanceEntity> userActivities) throws Exception {
                    display(userActivities,"present");
                    dismissLoadingProgress();
                }
            }));
        }
        else if (value.equals("late")){
            showLoadingProgress(mActivity);
            compositeDisposable.add(Common.userActivityRepository.getLateList(currentDate,total).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                @Override
                public void accept(List<AttendanceEntity> userActivities) throws Exception {
                    display(userActivities,"late");

                    dismissLoadingProgress();
                }
            }));
        }
        else if (value.equals("all")){
            showLoadingProgress(mActivity);
            compositeDisposable.add(Common.userActivityRepository.getList(currentDate).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                @Override
                public void accept(List<AttendanceEntity> userActivities) throws Exception {

                    display(userActivities,"all");
                    dismissLoadingProgress();
                }
            }));

//            showLoadingProgress(mActivity);
//            compositeDisposable.add(Common.userActivityRepository.getPresentList(currentDate).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
//                @Override
//                public void accept(List<AttendanceEntity> userActivities) throws Exception {
//                    users.addAll(userActivities);
//                    dismissLoadingProgress();
//                    showLoadingProgress(mActivity);
//                    compositeDisposable.add(Common.userActivityRepository.getAbsentList(currentDate).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
//                        @Override
//                        public void accept(List<AttendanceEntity> userActivities) throws Exception {
//
//                            users.addAll(userActivities);
//                            display(users,"all");
//                            dismissLoadingProgress();
//
//                        }
//                    }));
//                }
//            }));


        }
        else if (value.equals("ontime")){
            showLoadingProgress(mActivity);
            compositeDisposable.add(Common.userActivityRepository.getOnTimeList(currentDate,total).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                @Override
                public void accept(List<AttendanceEntity> userActivities) throws Exception {
                    display(userActivities,"ontime");
                    dismissLoadingProgress();
                }
            }));
        }
        else if (value.equals("absent")){
            showLoadingProgress(mActivity);
            compositeDisposable.add(Common.userActivityRepository.getAbsentList(currentDate).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                @Override
                public void accept(List<AttendanceEntity> userActivities) throws Exception {

                    display(userActivities,"absent");
                    dismissLoadingProgress();
                }
            }));
        }



    }

    static int present;
    static int absent;
    static int late;
    static  int ontime;
    static   int presentWise;
    static int absentWise;
    static  int lateWise;
    static int ontimeWise;
    private static void EmployeeStaus(){

        ydata.clear();
        ydataAttendance.clear();
        compositeDisposable.add(Common.userActivityRepository.getPresentList(currentDate).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
            @Override
            public void accept(List<AttendanceEntity> userActivities) throws Exception {
                presentWise=userActivities.size();
            }
        }));
        compositeDisposable.add(Common.userActivityRepository.getAbsentList(currentDate).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
            @Override
            public void accept(List<AttendanceEntity> userActivities) throws Exception {
                absentWise=userActivities.size();
            }
        }));
        compositeDisposable.add(Common.userActivityRepository.getLateList(currentDate,total).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
            @Override
            public void accept(List<AttendanceEntity> userActivities) throws Exception {
                lateWise=userActivities.size();
            }
        }));
        compositeDisposable.add(Common.userActivityRepository.getOnTimeList(currentDate,total).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
            @Override
            public void accept(List<AttendanceEntity> userActivities) throws Exception {
                ontimeWise=userActivities.size();
            }
        }));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                ydata.add(Integer.valueOf(presentWise));
                ydata.add(Integer.valueOf(absentWise));
                ydata.add(Integer.valueOf(0));
                ydataAttendance.add(Integer.valueOf(ontimeWise));
                ydataAttendance.add(Integer.valueOf(lateWise));
                try {
                    addDataSet();
                    addDataSetAttendance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);

    }


    private static void display(List<AttendanceEntity> userActivitie, String name) {
        userActivities.clear();
        userActivities=userActivitie;
        mAdapters = new PunchInAdapter(mActivity, userActivities,name,currentDate);
        try {
            rcl_approval_in_list.setAdapter(mAdapters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //EmployeeStaus();

    }
//    private void DepartmentListData(){
//
//        compositeDisposable.add(mService.getDepartmentList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<DepartmentListEntity>>() {
//            @Override
//            public void accept(ArrayList<DepartmentListEntity> carts) throws Exception {
//               // departmentListEntityList=carts;
//                Department department = new Department();
//                for (DepartmentListEntity departmentListEntity:carts){
//                    department.Id=departmentListEntity.Id;
//                    department.DepartmentName=departmentListEntity.DepartmentName;
//                    department.UnitId=departmentListEntity.UnitId;
//                    Common.departmentRepository.insertToDepartment(department);
//                }
//
//            }
//        }));
//
//    }
    private static void loadUnitItems() {

        compositeDisposable.add(Common.unitRepository.getUnitItems().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<Unit>>() {
            @Override
            public void accept(List<Unit> units) throws Exception {
                displayUnitItems(units);
            }
        }));

    }


    private static void displayUnitItems(List<Unit> units) {
        showLoadingProgress(mActivity);
        mUnitAdapter = new UnitAdapter(mActivity, units,mClickUnit);

        try {
            rcl_this_unit_list.setAdapter(mUnitAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismissLoadingProgress();

    }
    private static void loadDepartmentItems() {

        compositeDisposable.add(Common.departmentRepository.getCartItems().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<Department>>() {
            @Override
            public void accept(List<Department> departments) throws Exception {
                displayDepartmentItems(departments);
            }
        }));

    }


    private static void displayDepartmentItems(List<Department> departments) {
        showLoadingProgress(mActivity);
        mDepartmentAdapter = new DepartmentAdapter(mActivity, departments,mClickDepartment);

        try {
            rcl_this_department_list.setAdapter(mDepartmentAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismissLoadingProgress();
    }
    private static void addDataSetAttendance() {

        ArrayList<Entry> YEntry = new ArrayList<>();

        for (int i = 0; i < ydataAttendance.size(); i++) {
            YEntry.add(new Entry(ydataAttendance.get(i), i));

        }
        ArrayList<String> XEntry = new ArrayList<>();
        for (int j = 0; j < xdataAttendance.length; j++) {

            XEntry.add(xdataAttendance[j]);
        }
        PieDataSet pie = new PieDataSet(YEntry, "");
        pie.setSliceSpace(3);
        pie.setSelectionShift(5);
        pie.setValueTextSize(45);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#21a839"));
        colors.add(Color.RED);



        pie.setColors(colors);
        Legend legend = pieAttendanceStatus.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setTextSize(13);
        pie.setValueFormatter(new MyValueFormatter());
        PieData pieData = null;
        try {
            pieData = new PieData(xdataAttendance, pie);
            pieData.setValueTextSize(10f);
            pieData.setValueTextColor(Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pieAttendanceStatus.setData(pieData);
        pieAttendanceStatus.invalidate();

    }
    public static class MyValueFormatter implements ValueFormatter {


        @Override
        public String getFormattedValue(float value) {
            return "" + ((int) value);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
       // loadLeaveTotal();
       loadUnitItems();
        //DepartmentListData();
        loadDataActivity("all");
        loadDepartmentItems();
        EmployeeStaus();
    }

    public static  void shows(){
        loadUnitItems();
        //DepartmentListData();
        loadDataActivity("all");
        loadDepartmentItems();
        EmployeeStaus();
    }
    private void loadLeaveTotal() {
        showLoadingProgress(mActivity);
        compositeDisposable.add(mService.getTotalLeave().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(
                new Consumer<UserTotalLeaveEntity>() {
                    @Override
                    public void accept(UserTotalLeaveEntity carts) {




//                text_sick_leave.setText(String.valueOf(carts.Sick));
//                text_casual_leave.setText(String.valueOf(carts.Casual));

                        dismissLoadingProgress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissLoadingProgress();
                    }
                }));


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
