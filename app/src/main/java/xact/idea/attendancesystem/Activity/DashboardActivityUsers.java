package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Database.DataSources.DepartmentRepository;
import xact.idea.attendancesystem.Database.DataSources.EntityLeaveRepository;
import xact.idea.attendancesystem.Database.DataSources.LeaveSummaryRepository;
import xact.idea.attendancesystem.Database.DataSources.RemainingLeaveRepository;
import xact.idea.attendancesystem.Database.DataSources.UnitRepository;
import xact.idea.attendancesystem.Database.DataSources.UserActivityRepository;
import xact.idea.attendancesystem.Database.Local.DepartmentDataSource;
import xact.idea.attendancesystem.Database.Local.EntityLeaveDataSource;
import xact.idea.attendancesystem.Database.Local.LeaveSummaryDataSource;
import xact.idea.attendancesystem.Database.Local.MainDatabase;
import xact.idea.attendancesystem.Database.Local.RemainingLeaveDataSource;
import xact.idea.attendancesystem.Database.Local.UnitDataSource;
import xact.idea.attendancesystem.Database.Local.UserActivityDataSource;
import xact.idea.attendancesystem.Database.Model.Department;
import xact.idea.attendancesystem.Database.Model.SetUp;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Database.Model.UserList;
import xact.idea.attendancesystem.Entity.AllUserListEntity;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.SetUpDataEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Entity.UserActivityListEntity;
import xact.idea.attendancesystem.Entity.UserActivityPostEntity;
import xact.idea.attendancesystem.Fragment.DashboardFragment;
import xact.idea.attendancesystem.Fragment.PunchFragment;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.Constant;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.CustomDialog;
import xact.idea.attendancesystem.Utils.SharedPreferenceUtil;
import xact.idea.attendancesystem.Utils.Utils;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;

public class DashboardActivityUsers extends AppCompatActivity {
    TextView tv_date;
    LinearLayout linear_more;
    LinearLayout linear_web;
    LinearLayout linear_leave;
    LinearLayout linear_myself;
    LinearLayout linear_users;
    LinearLayout linear_punch;
    LinearLayout linear_sync;
    LinearLayout linear_logout;
    RelativeLayout root_rlt_dashboard;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    private Context mContext = null;
    ProgressBar progress_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_users);
        mContext = this;

        CorrectSizeUtil.getInstance(this).correctSize();
        CorrectSizeUtil.getInstance(this).correctSize(findViewById(R.id.root_rlt_dashboard));
        initView();

    }

    private void initView() {
        mService = Common.getApiXact();
        root_rlt_dashboard = findViewById(R.id.root_rlt_dashboard);
        progress_bar = findViewById(R.id.progress_bar);
        linear_logout = findViewById(R.id.linear_logout);
        linear_sync = findViewById(R.id.linear_sync);
        linear_more = findViewById(R.id.linear_more);
        linear_web = findViewById(R.id.linear_web);
        linear_leave = findViewById(R.id.linear_leave);
        linear_myself = findViewById(R.id.linear_myself);
        linear_users = findViewById(R.id.linear_users);
        linear_punch = findViewById(R.id.linear_punch);
        TextView tv_store = findViewById(R.id.tv_store);
        tv_date = findViewById(R.id.tv_date);
        tv_store.setSelected(true);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        Date date = new Date(System.currentTimeMillis());
        tv_date.setText(formatter.format(date));

        linear_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.VALUE = "users";
                Intent intent = new Intent(DashboardActivityUsers.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXTRA_SESSION", "myself");
                startActivity(intent);

            }
        });
        linear_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.VALUE = "users";
                Intent intent = new Intent(DashboardActivityUsers.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXTRA_SESSION", "home");
                startActivity(intent);
            }
        });
        linear_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.VALUE = "value";
                Intent intent = new Intent(DashboardActivityUsers.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXTRA_SESSION", "users");
                startActivity(intent);
            }
        });
        linear_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.VALUE = "users";
                Intent intent = new Intent(DashboardActivityUsers.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXTRA_SESSION", "leave");
                startActivity(intent);
            }
        });
        linear_myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.VALUE = "users";
                Intent intent = new Intent(DashboardActivityUsers.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXTRA_SESSION", "punch");
                startActivity(intent);
            }
        });
        linear_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivityUsers.this, WebActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        linear_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showInfoDialog(DashboardActivityUsers.this);
            }
        });
        linear_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfoDialog();
            }
        });
    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Constant.test = "test";
                        finish();

                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            finish();
        }
    }
    public void showInfoDialogForSync() {
        // showLoadingProgress(DashboardActivity.this);
        progress_bar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                final CustomDialog infoDialog = new CustomDialog(mContext, R.style.CustomDialogTheme);
                LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflator.inflate(R.layout.layout_pop_up_sync_, null);

                infoDialog.setContentView(v);
                infoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                RelativeLayout main_root = infoDialog.findViewById(R.id.main_root);

                Button btn_yes = infoDialog.findViewById(R.id.btn_ok);

                // btn_yes.setBackgroundTintList(getResources().getColorStateList(R.color.reject));
                CorrectSizeUtil.getInstance((Activity) mContext).correctSize(main_root);

                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        infoDialog.dismiss();


                    }
                });

                infoDialog.show();

                progress_bar.setVisibility(View.GONE);

            }
        }, 5000);

    }
    public void showInfoDialog() {

        final CustomDialog infoDialog = new CustomDialog(mContext, R.style.CustomDialogTheme);
        LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.layout_pop_up_nav, null);

        infoDialog.setContentView(v);
        infoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout main_root = infoDialog.findViewById(R.id.main_root);
        TextView tv_info = infoDialog.findViewById(R.id.tv_info);
        Button btn_yes = infoDialog.findViewById(R.id.btn_ok);
        Button btn_no = infoDialog.findViewById(R.id.btn_cancel);
        btn_yes.setBackgroundTintList(getResources().getColorStateList(R.color.reject));
        CorrectSizeUtil.getInstance((Activity) mContext).correctSize(main_root);
        tv_info.setText("Are you want to Sync All Data?");
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.broadcastIntent(DashboardActivityUsers.this, root_rlt_dashboard)) {
                    //Toast.makeText(mContext, "Connected ", Toast.LENGTH_SHORT).show();
                    // Common.userActivityRepository.emptyCart();
                    //   Common.departmentRepository.emptyCart();
                    //  Common.unitRepository.emptyCart();
                    //  Common.userListRepository.emptyCart();


                    getUserData();
                    DepartmentData();
                    unitListData();
                    setUpData();
                    UserActivityData();
                    //AllData();
                    DashboardFragment.shows();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(root_rlt_dashboard, "No Internet", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                infoDialog.dismiss();
                showInfoDialogForSync();

            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    private void getUserData() {

        showLoadingProgress(DashboardActivityUsers.this);
        compositeDisposable.add(mService.getUserList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<AllUserListEntity>() {
            @Override
            public void accept(AllUserListEntity carts) throws Exception {
                // departmentListEntityList=carts;
                Common.userListRepository.emptyCart();
                UserList userList = new UserList();

                for (AllUserListEntity.Data data : carts.data) {
                    userList.UserId = data.UserId;
                    userList.FullName = data.FullName;
                    userList.Email = data.Email;
                    userList.OfficeExt = data.OfficeExt;
                    userList.Password = data.Password;
                    userList.AdminStatus = data.AdminStatus;
                    userList.Designation = data.Designation;
                    userList.JoiningDate = data.JoiningDate;
                    userList.CorporateMobileNumber = data.CorporateMobileNumber;
                    userList.PersonalMobileNumber = data.PersonalMobileNumber;
                    userList.EmergencyContactPerson = data.EmergencyContactPerson;
                    userList.RelationWithContactPerson = data.RelationWithContactPerson;
                    userList.BloodGroup = data.BloodGroup;
                    userList.ProfilePhoto = data.ProfilePhoto;
                    userList.UnitId = data.UnitId;
                    userList.UnitName = data.UnitName;
                    userList.UnitShortName = data.UnitShortName;
                    userList.DepartmentId = data.DepartmentId;
                    userList.DepartmentName = data.DepartmentName;
                    userList.DepartmentShortName = data.DepartmentShortName;
                    Common.userListRepository.insertToUserList(userList);
                }

                dismissLoadingProgress();

                //   progressBar.setVisibility(View.GONE);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dismissLoadingProgress();
            }
        }));

    }

    private void DepartmentData() {

        showLoadingProgress(DashboardActivityUsers.this);

        compositeDisposable.add(mService.getDepartmentList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<DepartmentListEntity>() {
            @Override
            public void accept(DepartmentListEntity carts) throws Exception {
                // departmentListEntityList=carts;
                Common.departmentRepository.emptyCart();
                Department departments = new Department();
                departments.Id = -1;
                departments.DepartmentName = "ALL";
                departments.UnitId = 1;
                Common.departmentRepository.insertToDepartment(departments);
                Department department = new Department();

                for (DepartmentListEntity.Data departmentListEntity : carts.data) {
                    department.Id = departmentListEntity.Id;
                    department.DepartmentName = departmentListEntity.DepartmentName;
                    department.UnitId = departmentListEntity.UnitId;
                    Common.departmentRepository.insertToDepartment(department);
                }

                dismissLoadingProgress();
                //   progressBar.setVisibility(View.GONE);
            }
        }));


    }

    private void unitListData() {
        showLoadingProgress(DashboardActivityUsers.this);


        compositeDisposable.add(mService.getUnitList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<UnitListEntity>() {
            @Override
            public void accept(UnitListEntity unitListEntities) throws Exception {
                Common.unitRepository.emptyCart();
                Unit units = new Unit();
                units.Id = -1;
                units.UnitName = "ALL";
                units.ShortName = "A";
                Common.unitRepository.insertToUnit(units);
                Unit unit = new Unit();

                for (UnitListEntity.Data unitList : unitListEntities.data) {

                    unit.Id = unitList.Id;
                    unit.UnitName = unitList.UnitName;
                    unit.ShortName = unitList.ShortName;
                    Common.unitRepository.insertToUnit(unit);
                }

                dismissLoadingProgress();
                //   progressBar.setVisibility(View.GONE);

//                unitListEntityArrayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, unitListEntityList);
//                unitListEntityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerUnit.setAdapter(unitListEntityArrayAdapter);
            }

        }));
    }

    private void setUpData() {
        showLoadingProgress(DashboardActivityUsers.this);

        compositeDisposable.add(mService.getSetUpData().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<SetUpDataEntity>() {
            @Override
            public void accept(SetUpDataEntity unitListEntities) throws Exception {
                //  Common.setUpDataRepository.emptyCart();
                SetUp setUp = new SetUp();

                setUp.EXPECTED_DURATION = unitListEntities.data.EXPECTED_DURATION;
                setUp.OFFICE_IN_TIME = unitListEntities.data.OFFICE_IN_TIME;
                setUp.OFFICE_OUT_TIME = unitListEntities.data.OFFICE_OUT_TIME;
                setUp.GRACE_TIME = unitListEntities.data.GRACE_TIME;
                setUp.HALFDAY_DURATION = unitListEntities.data.HALFDAY_DURATION;
                setUp.ENTITLED_LEAVE_CASUAL = unitListEntities.data.ENTITLED_LEAVE_CASUAL;
                setUp.ENTITLED_LEAVE_SICK = unitListEntities.data.ENTITLED_LEAVE_SICK;
                setUp.ENTITLED_LEAVE_TOTAL = unitListEntities.data.ENTITLED_LEAVE_TOTAL;

                dismissLoadingProgress();
                //   progressBar.setVisibility(View.GONE);

//                unitListEntityArrayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, unitListEntityList);
//                unitListEntityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerUnit.setAdapter(unitListEntityArrayAdapter);
            }

        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDB();

        if (Constant.test == null) {

        } else if (Constant.test.equals("test")) {
            Log.e("evan", "SDFs");
            Constant.test = "Dsds";
            finish();
        }
        if (Common.userActivityRepository.size() > 0) {

        } else {
//            DepartmentData();
//            unitListData();
            if (Utils.broadcastIntent(DashboardActivityUsers.this, root_rlt_dashboard)) {
                //Toast.makeText(mContext, "Connected ", Toast.LENGTH_SHORT).show();
                //   load();
                UserActivityData();
            } else {
                Snackbar snackbar = Snackbar
                        .make(root_rlt_dashboard, "No Internet", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }

    }

    private void initDB() {
        Common.mainDatabase = MainDatabase.getInstance(this);
        Common.departmentRepository = DepartmentRepository.getInstance(DepartmentDataSource.getInstance(Common.mainDatabase.departmentDao()));
        Common.unitRepository = UnitRepository.getInstance(UnitDataSource.getInstance(Common.mainDatabase.unitDao()));
        Common.leaveSummaryRepository = LeaveSummaryRepository.getInstance(LeaveSummaryDataSource.getInstance(Common.mainDatabase.leaveSummaryDao()));
        Common.entityLeaveRepository = EntityLeaveRepository.getInstance(EntityLeaveDataSource.getInstance(Common.mainDatabase.entityLeaveDao()));
        Common.remainingLeaveRepository = RemainingLeaveRepository.getInstance(RemainingLeaveDataSource.getInstance(Common.mainDatabase.remainingLeaveDao()));
        Common.userActivityRepository = UserActivityRepository.getInstance(UserActivityDataSource.getInstance(Common.mainDatabase.userActivityDao()));
    }

    private void UserActivityData() {

        showLoadingProgress(this);
        UserActivityPostEntity userActivityPostEntity = new UserActivityPostEntity();
        compositeDisposable.add(mService.getUserActivityList(userActivityPostEntity).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<UserActivityListEntity>() {
            @Override
            public void accept(UserActivityListEntity carts) throws Exception {
                // departmentListEntityList=carts;
                Common.userActivityRepository.emptyCart();
                UserActivity userActivity = new UserActivity();

                for (UserActivityListEntity.Data userActivityListEntity : carts.data) {
                    userActivity.UserId = userActivityListEntity.UserId;

                    userActivity.PunchInLocation = userActivityListEntity.PunchInLocation;
                    //   String sDate1 = userActivityListEntity.WorkingDate;

                    String input = userActivityListEntity.WorkingDate;
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = new Date(System.currentTimeMillis());
                    final String currentDate = formatter.format(date);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    c.add(Calendar.MONTH, -1);
                    String lastDate = formatter.format(c.getTime());
                    if (input.equals("--")) {
                        input = lastDate;
                    }

                    //input string
                    String firstFourCharss = "";     //substring containing first 4 characters


                    try {
                        firstFourCharss = input.substring(4, 5);
                        if (firstFourCharss.equals("-")) {

                            String firstFourThree = input.substring(8, 10);

                            String firstFourTwo_ = input.substring(4, 8);
                            String firstFourOne = input.substring(0, 4);

                            userActivity.WorkingDate = firstFourThree + firstFourTwo_ + firstFourOne;
                            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(firstFourThree + firstFourTwo_ + firstFourOne);
                            userActivity.Date = date1;
                        } else {

                            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(input);
                            userActivity.Date = date1;
                            userActivity.WorkingDate = userActivityListEntity.WorkingDate;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    if (firstFourCharss.equals("-")){
//
//                        String firstFourOne=input.substring(6,10);
//
//                        String firstFourTwo_=input.substring(2,6);
//                        String firstFourThree=input.substring(0,2);
//
//                        userActivity.WorkingDate = firstFourOne+firstFourTwo_+firstFourThree;
//                        Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(firstFourOne+firstFourTwo_+firstFourThree);
//                        userActivity.Date = date1;
//                    }
//                    else
//                    {
//
//                        Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(input);
//                        userActivity.Date = date1;
//                        userActivity.WorkingDate = userActivityListEntity.WorkingDate;
//                    }


                    String str = userActivityListEntity.PunchInTime;
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
                    //  String value1;
                    if (userActivityListEntity.PunchOutTime == null) {
                        userActivityListEntity.PunchOutTime = "";
                    }
                    if (userActivityListEntity.PunchInTime == null) {
                        userActivityListEntity.PunchInTime = "";
                    }
                    if (userActivityListEntity.PunchOutTime.equals("0")) {
                        userActivityListEntity.PunchOutTime = "";
                    }
                    if (userActivityListEntity.PunchInTime.equals("0")) {
                        userActivityListEntity.PunchInTime = "";
                    }
                    if (userActivityListEntity.PunchOutTime.equals("") ) {
//                        String strTime = userActivityListEntity.PunchInTime;
//                        String endTime = userActivityListEntity.PunchOutTime;
//                        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
//                        try {
//                            Date dates = dateFormat.parse(strTime);
//                            Date enddates = dateFormat.parse(endTime);
//                            SimpleDateFormat formatters= new SimpleDateFormat("hh:mm a");
//
//                            String currentTime=formatters.format(dates);
//                            String endTimes=formatters.format(enddates);
//                            long difference = enddates.getTime() - dates.getTime();
//                            int  days = (int) (difference / (1000*60*60*24));
//                            int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
//                            int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
//                            Log.e("currentTime","currentTime"+currentTime);
//                            Log.e("endTime","endTime"+endTimes);
//                            Log.e("difference","difference"+days +":"+min);

//                        if (userActivityListEntity.PunchInTime!=null)
//                        {
//                            String strTime = userActivityListEntity.PunchInTime;
//
//                            DateFormat dateFormat = new SimpleDateFormat("hh:mm");
//
//                            Date dates = dateFormat.parse(strTime);
//
//                            SimpleDateFormat formatters = new SimpleDateFormat("hh:mm a");
//
//
//                            String currentTime = formatters.format(dates);
//                            userActivity.PunchOutTime = userActivityListEntity.PunchOutTime;
//                            userActivity.Duration = userActivityListEntity.Duration;
//                            userActivity.PunchInTimeLate = currentTime;
//
//                        }
//                        else {
//                            userActivity.PunchOutTime = userActivityListEntity.PunchOutTime;
//                            userActivity.Duration = userActivityListEntity.Duration;
//                            userActivity.PunchInTimeLate = userActivityListEntity.PunchInTime;
//                        }
                        if (!userActivityListEntity.PunchInTime.equals("")){

                            String str1 = userActivityListEntity.PunchInTime;
                            if (str1 == null || str1.equals("")) {
                                userActivity.PunchInTime = 0.0;
                            } else {
                                String firstFourChars = "";     //substring containing first 4 characters


                                firstFourChars = str1.substring(0, 5);

                                int index = 2;
                                char ch = '.';

                                StringBuilder string = new StringBuilder(firstFourChars);
                                string.setCharAt(index, ch);
                                //  userActivity.PunchInTimeLate = string.toString();
                                double d= Double.parseDouble(string.toString());
                                if (d<12){
                                    userActivity.PunchInTimeLate = string.toString() +" AM";
                                }
                                else {
                                    userActivity.PunchInTimeLate = string.toString() +" PM";
                                }

                            }
//                            double d= Double.parseDouble(userActivityListEntity.PunchInTime);
//                            if (d<12){
//                                userActivity.PunchInTimeLate = userActivityListEntity.PunchInTime +"AM";
//                            }
//                            else {
//                                userActivity.PunchInTimeLate = userActivityListEntity.PunchInTime +"PM";
//                            }
                            userActivity.PunchOutTime = userActivityListEntity.PunchOutTime;
                            userActivity.Duration = userActivityListEntity.Duration;
                        }
                        else {
                            userActivity.PunchInTimeLate = userActivityListEntity.PunchInTime;
                            userActivity.PunchOutTime = userActivityListEntity.PunchOutTime;
                            userActivity.Duration = userActivityListEntity.Duration;
                        }



                    }
                    if (userActivityListEntity.PunchInTime.equals("") ) {
//                        String strTime = userActivityListEntity.PunchInTime;
//                        String endTime = userActivityListEntity.PunchOutTime;
//                        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
//                        try {
//                            Date dates = dateFormat.parse(strTime);
//                            Date enddates = dateFormat.parse(endTime);
//                            SimpleDateFormat formatters= new SimpleDateFormat("hh:mm a");
//
//                            String currentTime=formatters.format(dates);
//                            String endTimes=formatters.format(enddates);
//                            long difference = enddates.getTime() - dates.getTime();
//                            int  days = (int) (difference / (1000*60*60*24));
//                            int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
//                            int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
//                            Log.e("currentTime","currentTime"+currentTime);
//                            Log.e("endTime","endTime"+endTimes);
//                            Log.e("difference","difference"+days +":"+min);

//                        if (userActivityListEntity.PunchInTime!=null)
//                        {
//                            String strTime = userActivityListEntity.PunchInTime;
//
//                            DateFormat dateFormat = new SimpleDateFormat("hh:mm");
//
//                            Date dates = dateFormat.parse(strTime);
//
//                            SimpleDateFormat formatters = new SimpleDateFormat("hh:mm a");
//
//
//                            String currentTime = formatters.format(dates);
//                            userActivity.PunchOutTime = userActivityListEntity.PunchOutTime;
//                            userActivity.Duration = userActivityListEntity.Duration;
//                            userActivity.PunchInTimeLate = currentTime;
//
//                        }
//                        else {
//                            userActivity.PunchOutTime = userActivityListEntity.PunchOutTime;
//                            userActivity.Duration = userActivityListEntity.Duration;
//                            userActivity.PunchInTimeLate = userActivityListEntity.PunchInTime;
//                        }
                        if (!userActivityListEntity.PunchOutTime.equals("")){
                            String str2 = userActivityListEntity.PunchOutTime;
                            if (str2 == null || str2.equals("")) {
                                userActivity.PunchInTime = 0.0;
                            } else {
                                String firstFourChars = "";     //substring containing first 4 characters


                                firstFourChars = str2.substring(0, 5);

                                int index = 2;
                                char ch = '.';

                                StringBuilder string = new StringBuilder(firstFourChars);
                                string.setCharAt(index, ch);
                                //  userActivity.PunchInTimeLate = string.toString();
                                double d= Double.parseDouble(string.toString());
                                if (d<12){
                                    userActivity.PunchOutTime = string.toString() +" AM";
                                }
                                else {
                                    userActivity.PunchOutTime = string.toString() +" PM";
                                }

                            }
                            userActivity.PunchInTimeLate = userActivityListEntity.PunchInTime;
                            userActivity.Duration = userActivityListEntity.Duration;
                        }
                        else {
                            userActivity.PunchInTimeLate = userActivityListEntity.PunchInTime;
                            userActivity.PunchOutTime = userActivityListEntity.PunchOutTime;
                            userActivity.Duration = userActivityListEntity.Duration;
                        }

                    } else if (userActivityListEntity.PunchInTime != null && userActivityListEntity.PunchOutTime != null) {
                        String strTime = userActivityListEntity.PunchInTime;
                        String endTime = userActivityListEntity.PunchOutTime;
                        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                        try {
                            Date dates = dateFormat.parse(strTime);
                            Date enddates = dateFormat.parse(endTime);
                            SimpleDateFormat formatters = new SimpleDateFormat("hh:mm a");
                            SimpleDateFormat formatte = new SimpleDateFormat("hh:mm a");

                            String currentTime = formatters.format(dates);
                            String endTimes = formatters.format(enddates);
//                            Date da1= formatte.parse(currentTime);
//                            Date end= formatte.parse(endTimes);
                            Date da1e = formatte.parse(currentTime);
                            Log.e("da1", "da1" + da1e.toString());
                            Date end = formatte.parse(endTimes);
                            long difference = end.getTime() - da1e.getTime();
                            int days = (int) (difference / (1000 * 60 * 60 * 24));
                            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
                            Log.e("currentTime", "currentTime" + currentTime);
                            Log.e("endTime", "endTime" + endTimes);
                            Log.e("difference", "difference" + days + ":" + min);

                            userActivity.PunchOutTime = endTimes;
                            userActivity.Duration = hours + ":" + min;
                            userActivity.PunchInTimeLate = currentTime;

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        userActivity.PunchOutTime = userActivityListEntity.PunchOutTime;
                        userActivity.Duration = userActivityListEntity.Duration;
                        userActivity.PunchInTimeLate = userActivityListEntity.PunchInTime;
                    }

                    userActivity.PunchOutLocation = userActivityListEntity.PunchOutLocation;
                    Common.userActivityRepository.insertToUserActivity(userActivity);
                    // userActivity.PunchInTime= Double.parseDouble(str);


                }
                if (SharedPreferenceUtil.getAdmin(DashboardActivityUsers.this).equals("0")) {
                 //   PunchFragment.show();
                }

                dismissLoadingProgress();
                //   progressBar.setVisibility(View.GONE);
            }
        }));


    }
}
