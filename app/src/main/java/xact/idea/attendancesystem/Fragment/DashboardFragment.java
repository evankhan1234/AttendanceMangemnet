package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Adapter.DepartmentAdapter;
import xact.idea.attendancesystem.Adapter.PunchInAdapter;
import xact.idea.attendancesystem.Adapter.PunchInAdapterForAdmin;
import xact.idea.attendancesystem.Adapter.UnitAdapter;
import xact.idea.attendancesystem.Adapter.UnitDepartmentAdapter;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.Entity.UserTotalLeaveEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
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
    HorizontalScrollView scr_category_present;
    IRetrofitApi mService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView rcl_this_unit_list;
    private RecyclerView rcl_this_department_list;
    private RecyclerView rcl_approval_in_list;
    private PunchInAdapterForAdmin mAdapters = null;
    private UnitAdapter mUnitAdapter = null;
    private DepartmentAdapter mDepartmentAdapter = null;
    List<DepartmentListEntity> departmentListEntityList  = new ArrayList<>();;
    List<UnitListEntity> unitListEntityList  = new ArrayList<>();
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
        return view;
    }

    private void initView() {

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
    private void loadDataActivity() {
        showLoadingProgress(mActivity);
        compositeDisposable.add(mService.getUserActivity().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<UserActivityEntity>>() {
            @Override
            public void accept(ArrayList<UserActivityEntity> carts) throws Exception {

                mAdapters = new PunchInAdapterForAdmin(mActivity, carts);

                rcl_approval_in_list.setAdapter(mAdapters);
                dismissLoadingProgress();
            }
        }));


    }
    private void unitListData(){

        showLoadingProgress(mActivity);
        compositeDisposable.add(mService.getUnitList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<UnitListEntity>>() {
            @Override
            public void accept(ArrayList<UnitListEntity> carts) throws Exception {
                unitListEntityList=carts;
                mUnitAdapter = new UnitAdapter(mActivity, unitListEntityList);

                rcl_this_unit_list.setAdapter(mUnitAdapter);
                dismissLoadingProgress();

//                unitListEntityArrayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, unitListEntityList);
//                unitListEntityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerUnit.setAdapter(unitListEntityArrayAdapter);
            }
        }));
    }
    private void DepartmentListData(){
        showLoadingProgress(mActivity);
        compositeDisposable.add(mService.getDepartmentList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<DepartmentListEntity>>() {
            @Override
            public void accept(ArrayList<DepartmentListEntity> carts) throws Exception {
                departmentListEntityList=carts;
//                departmentListEntityArrayAdapter = new ArrayAdapter<DepartmentListEntity>(getActivity(), android.R.layout.simple_spinner_item, departmentListEntityList);
//                departmentListEntityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerDepartments.setAdapter(departmentListEntityArrayAdapter);
                mDepartmentAdapter = new DepartmentAdapter(mActivity, departmentListEntityList);

                rcl_this_department_list.setAdapter(mDepartmentAdapter);
                dismissLoadingProgress();
            }
        }));
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
        unitListData();
        DepartmentListData();
        loadDataActivity();
    }

    private void loadLeaveTotal() {
        showLoadingProgress(mActivity);
        compositeDisposable.add(mService.getTotalLeave().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<UserTotalLeaveEntity>() {
            @Override
            public void accept(UserTotalLeaveEntity carts) throws Exception {
                ydata.add(Integer.valueOf(carts.Total));
                ydata.add(Integer.valueOf(carts.Sick));
                ydata.add(Integer.valueOf(carts.Casual));
                ydataAttendance.add(Integer.valueOf(carts.Casual));
                ydataAttendance.add(Integer.valueOf(carts.Total));


//                text_sick_leave.setText(String.valueOf(carts.Sick));
//                text_casual_leave.setText(String.valueOf(carts.Casual));
                addDataSet();
                addDataSetAttendance();
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
