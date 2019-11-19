package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    View view;
    long presentEmp, absentEmp, leaveEmp;
    List<Integer> ydata = new ArrayList<>();
    List<Integer> ydataAttendance = new ArrayList<>();
    private String[] xdata = {"Present", "Absent","Leave"};
    private String[] xdataAttendance = {"On-Time","Late"};
    EditText edit_content;
    PieChart pieChart;
    PieChart pieAttendanceStatus;
    RadioButton radioPresent;
    RadioButton radioAbsent;
    RadioButton radioPresentLate;
    RadioButton radioleave;
    RadioButton radioPresentOnTime;
    RadioButton radioAll;
    HorizontalScrollView scr_category_present;
    IRetrofitApi mService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView rcl_this_unit_list;
    private RecyclerView rcl_this_department_list;
    private RecyclerView rcl_approval_in_list;
    private PunchInAdapter mAdapters = null;
    private UnitAdapter mUnitAdapter = null;
    private DepartmentAdapter mDepartmentAdapter = null;
    List<DepartmentListEntity> departmentListEntityList  = new ArrayList<>();;
    List<UnitListEntity> unitListEntityList  = new ArrayList<>();
    List<AttendanceEntity> userActivities= new ArrayList<>();
     int unitValue;
     int departmentValue;
    SetUp setUp;
    double inTime;
    double graceTime;
    double total;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_dashboard, container, false);
        mActivity=getActivity();
        correctSizeUtil= correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(view);
        initView();
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
       setUp= Common.setUpDataRepository.getSetUpItems();
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
            inTime=Double.parseDouble(string.toString());

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
            graceTime=Double.parseDouble(string.toString());


            total=inTime+graceTime;

        }
        Log.e("total","total"+total);
        return view;
    }

    private void initView() {

        radioPresentOnTime = view.findViewById(R.id.radioPresentOnTime);
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
        pieChart = view. findViewById(R.id.pieEmployeeStatus);
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



        ////
        pieAttendanceStatus = view. findViewById(R.id.pieAttendanceStatus);
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


                if (unitValue>0 && departmentValue>0){
                    loadDataActivityUnitDepartmentWise("present");

                }else if (unitValue>0){
                    loadDataActivityUnitDepartmentWise("present");
                }
                else if (departmentValue>0){
                    loadDataActivityUnitDepartmentWise("present");
                }
                else {
                    loadDataActivity("present");
                }

            }
        });

        radioAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scr_category_present.setVisibility(View.GONE);
                if (unitValue>0 && departmentValue>0){
                    loadDataActivityUnitDepartmentWise("all");
                }else if (unitValue>0){
                    loadDataActivityUnitDepartmentWise("all");
                }
                else if (departmentValue>0){
                    loadDataActivityUnitDepartmentWise("all");
                }
                else {
                    loadDataActivity("all");
                }
            }
        });
        radioAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scr_category_present.setVisibility(View.GONE);
                if (unitValue>0 && departmentValue>0){
                    loadDataActivityUnitDepartmentWise("absent");
                }else if (unitValue>0){
                    loadDataActivityUnitDepartmentWise("absent");
                }
                else if (departmentValue>0){
                    loadDataActivityUnitDepartmentWise("absent");
                }
                else {
                    loadDataActivity("absent");
                }
            }
        });
        radioPresentLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (unitValue>0 && departmentValue>0){
                    loadDataActivityUnitDepartmentWise("late");
                }else if (unitValue>0){
                    loadDataActivityUnitDepartmentWise("late");
                }
                else if (departmentValue>0){
                    loadDataActivityUnitDepartmentWise("late");
                }
                else {
                    loadDataActivity("late");
                }
            }
        });
        radioPresentOnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (unitValue>0 && departmentValue>0){
                    loadDataActivityUnitDepartmentWise("ontime");
                }else if (unitValue>0){
                    loadDataActivityUnitDepartmentWise("ontime");
                }
                else if (departmentValue>0){
                    loadDataActivityUnitDepartmentWise("ontime");
                }
                else {
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
    private DepartmentClickInterface mClickDepartment = new DepartmentClickInterface() {
        @Override
        public void onItemClick(int position) {
            departmentValue=position;
            loadUnitDepartmentWise();
            EmployeeStausUnitDepartmentWise();

          //  Toast.makeText(mActivity, "Department "+departmentValue+"Unit"+unitValue, Toast.LENGTH_SHORT).show();
//           Common.departmentRepository.emptyCart();
//            DepartmentListData();
           // loadLeaveTotal();

        }
    };
    private UnitClickInterface mClickUnit = new UnitClickInterface() {
        @Override
        public void onItemClick(int position) {
            unitValue=position;
            loadUnitDepartmentWise();
            EmployeeStausUnitDepartmentWise();
           // Toast.makeText(mActivity, "Department "+departmentValue+"Unit"+unitValue, Toast.LENGTH_SHORT).show();
         //   loadLeaveTotal();


        }
    };
    private void addDataSet() {

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

    private void loadUnitDepartmentWise(){
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
    private void EmployeeStausUnitDepartmentWise(){





        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String currentDate=formatter.format(date);
        System.out.println(formatter.format(date));


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
    private void loadDataActivityUnitDepartmentWise(String value) {


        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String currentDate=formatter.format(date);
        System.out.println(formatter.format(date));
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
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }else if (unitValue>0){
                compositeDisposable.add(Common.userActivityRepository.getAbsentUnitList(currentDate,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }
            else if (departmentValue>0){
                compositeDisposable.add(Common.userActivityRepository.getAbsentDepartmentList(currentDate,departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                    @Override
                    public void accept(List<AttendanceEntity> userActivities) throws Exception {
                        display(userActivities,"");
                        dismissLoadingProgress();
                    }
                }));
            }
        }



    }

    private void loadDataActivity(String value) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String currentDate=formatter.format(date);
        System.out.println(formatter.format(date));
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
            compositeDisposable.add(Common.userActivityRepository.getList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
                @Override
                public void accept(List<AttendanceEntity> userActivities) throws Exception {
                    Log.e("data","data"+new Gson().toJson(userActivities));
                    display(userActivities,"all");

                    dismissLoadingProgress();
                }
            }));
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

    int present;
    int absent;
    int late;
    int ontime;
    int presentWise;
    int absentWise;
    int lateWise;
    int ontimeWise;
    private void EmployeeStaus(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String currentDate=formatter.format(date);

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
        compositeDisposable.add(Common.userActivityRepository.getLateList(currentDate,9.30).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
            @Override
            public void accept(List<AttendanceEntity> userActivities) throws Exception {
                lateWise=userActivities.size();
            }
        }));
        compositeDisposable.add(Common.userActivityRepository.getOnTimeList(currentDate,9.30).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<AttendanceEntity>>() {
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
                addDataSet();
                addDataSetAttendance();
            }
        }, 3000);

    }


    private void display(List<AttendanceEntity> userActivitie,String name) {
        userActivities.clear();
        userActivities=userActivitie;
        mAdapters = new PunchInAdapter(mActivity, userActivities,name);
        rcl_approval_in_list.setAdapter(mAdapters);
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
    private void loadUnitItems() {

        compositeDisposable.add(Common.unitRepository.getUnitItems().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<Unit>>() {
            @Override
            public void accept(List<Unit> units) throws Exception {
                displayUnitItems(units);
            }
        }));

    }


    private void displayUnitItems(List<Unit> units) {
        showLoadingProgress(mActivity);
        mUnitAdapter = new UnitAdapter(mActivity, units,mClickUnit);

        rcl_this_unit_list.setAdapter(mUnitAdapter);
        dismissLoadingProgress();

    }
    private void loadDepartmentItems() {

        compositeDisposable.add(Common.departmentRepository.getCartItems().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<Department>>() {
            @Override
            public void accept(List<Department> departments) throws Exception {
                displayDepartmentItems(departments);
            }
        }));

    }


    private void displayDepartmentItems(List<Department> departments) {
        showLoadingProgress(mActivity);
        mDepartmentAdapter = new DepartmentAdapter(mActivity, departments,mClickDepartment);

        rcl_this_department_list.setAdapter(mDepartmentAdapter);
        dismissLoadingProgress();
    }
    private void addDataSetAttendance() {

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
    public class MyValueFormatter implements ValueFormatter {


        @Override
        public String getFormattedValue(float value) {
            return "" + ((int) value);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLeaveTotal();
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
