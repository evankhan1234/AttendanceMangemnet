package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Adapter.LeaveSummaryListAdapter;
import xact.idea.attendancesystem.Database.DataSources.DepartmentRepository;
import xact.idea.attendancesystem.Database.DataSources.EntityLeaveRepository;
import xact.idea.attendancesystem.Database.DataSources.ILeaveSummaryDataSource;
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
import xact.idea.attendancesystem.Database.Model.EntityLeave;
import xact.idea.attendancesystem.Database.Model.LeaveSummary;
import xact.idea.attendancesystem.Database.Model.RemainingLeave;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Database.Model.UserList;
import xact.idea.attendancesystem.Entity.AllUserListEntity;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.LeaveSummaryEntity;
import xact.idea.attendancesystem.Entity.LoginPostEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Entity.UserActivityListEntity;
import xact.idea.attendancesystem.Entity.UserActivityPostEntity;
import xact.idea.attendancesystem.Fragment.AboutUsFragment;
import xact.idea.attendancesystem.Fragment.DashboardFragment;
import xact.idea.attendancesystem.Fragment.HomeFragment;
import xact.idea.attendancesystem.Fragment.LeaveApplicationApprovalFragment;
import xact.idea.attendancesystem.Fragment.LeaveApplicationFragment;
import xact.idea.attendancesystem.Fragment.LeaveFragment;
import xact.idea.attendancesystem.Fragment.MoreFragment;
import xact.idea.attendancesystem.Fragment.ProfileDetailsFragment;
import xact.idea.attendancesystem.Fragment.PunchFragment;
import xact.idea.attendancesystem.Fragment.PunchInFragment;
import xact.idea.attendancesystem.Fragment.SetUpFragment;
import xact.idea.attendancesystem.Fragment.UserActivityFragment;
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

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private FragmentManager mFragManager;
    private FragmentTransaction fragTransaction = null;
    public Fragment mCurrentFrag;
    private Context mContext = null;
    private AppCompatImageView btn_footer_home;
    private AppCompatImageView btn_footer_setUp;
    private AppCompatImageView btn_footer_user_activity;
    private AppCompatImageView btn_footer_more;
    private AppCompatImageView btn_footer_setup_user;
    private TextView tv_home_menu;
    private TextView tv_setup_menu;
    private TextView tv_user_activity_menu;
    private TextView tv_more_menu;
    private TextView tv_user_setup_menus;
    private TextView title;
    public static final int HOME_BTN = 0;
    public static final int SET_UP_BTN = 1;
    public static final int SET_UP_USER_BTN = 4;
    public static final int USER_BTN = 2;
    public static final int MORE_BTN = 3;
    private RelativeLayout rlt_header;
    private RelativeLayout rlt_header_details;
    private TextView details_title;
    private View view_header_details;
    private ImageButton btn_header_back_;
    private ImageButton btn_header_application;
    private ImageButton btn_header_sync;
    private ImageButton btn_header_application_create;
    private LinearLayout linear;
    private RelativeLayout relative;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    IRetrofitApi mServiceXact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear = findViewById(R.id.linear);
        mService = Common.getApi();
        mServiceXact = Common.getApiXact();
        btn_footer_setup_user = findViewById(R.id.btn_footer_setup_user);
        tv_user_setup_menus = findViewById(R.id.tv_user_setup_menus);
        relative = findViewById(R.id.relative);
        btn_header_sync = findViewById(R.id.btn_header_sync);
        btn_header_application = findViewById(R.id.btn_header_application);
        btn_header_application_create = findViewById(R.id.btn_header_application_create);
        title = findViewById(R.id.title);
        btn_header_back_ = findViewById(R.id.btn_header_back_);
        view_header_details = findViewById(R.id.view_header_details);
        details_title = findViewById(R.id.details_title);
        btn_footer_home = findViewById(R.id.btn_footer_home);
        btn_footer_setUp = findViewById(R.id.btn_footer_setup);
        btn_footer_user_activity = findViewById(R.id.btn_footer_user);
        btn_footer_more = findViewById(R.id.btn_footer_more);
        rlt_header = findViewById(R.id.rlt_header);
        rlt_header_details = findViewById(R.id.rlt_header_details);
        tv_home_menu = findViewById(R.id.tv_home_menu);
        tv_setup_menu = findViewById(R.id.tv_setup_menus);
        tv_user_activity_menu = findViewById(R.id.tv_home_menu);
        tv_more_menu = findViewById(R.id.tv_more_menus);
        mContext = this;
        CorrectSizeUtil.getInstance(this).correctSize();
        CorrectSizeUtil.getInstance(this).correctSize(findViewById(R.id.rlt_root));
        afterClickTabItem(Constant.FRAG_HOME, null);
        btn_footer_home.setSelected(true);
        tv_home_menu.setSelected(true);
        Constant.SYNC="Admin";
        if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
            btn_header_sync.setVisibility(View.VISIBLE);
        }
        btn_header_back_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onBack();
            }
        });
        btn_header_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.SYNC.equals("Admin")){
                    final CustomDialog infoDialog = new CustomDialog(MainActivity.this, R.style.CustomDialogTheme);
                    LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflator.inflate(R.layout.layout_pop_up_sync_dashboard, null);

                    infoDialog.setContentView(v);
                    infoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    RelativeLayout main_root = infoDialog.findViewById(R.id.main_root);
                    Button btn_yes = infoDialog.findViewById(R.id.btn_yes);
                    final EditText edit_date_from = infoDialog.findViewById(R.id.edit_date_from);
                    final EditText edit_date_to = infoDialog.findViewById(R.id.edit_date_to);

                    CorrectSizeUtil.getInstance(MainActivity.this).correctSize(main_root);
                    edit_date_from.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Calendar mcurrentDate = Calendar.getInstance();
                            final int mYear = mcurrentDate.get(Calendar.YEAR);
                            final int mMonth = mcurrentDate.get(Calendar.MONTH);
                            final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTimeInMillis(0);
                                    cal.set(mYear, mMonth, mDay, 0, 0, 0);
                                    Date chosenDate = cal.getTime();
                                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    String formattedDate = formatter.format(chosenDate);

                                    edit_date_from.setText(selectedyear+"-"+selectedmonth+"-"+selectedday);
                                }
                            }, mYear, mMonth, mDay);
                            mDatePicker.setTitle("Select date");
                            mDatePicker.show();

                        }

                    });
                    edit_date_to.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Calendar mcurrentDate = Calendar.getInstance();
                            final int mYear = mcurrentDate.get(Calendar.YEAR);
                            final int mMonth = mcurrentDate.get(Calendar.MONTH);
                            final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTimeInMillis(0);
                                    cal.set(mYear, mMonth, mDay, 0, 0, 0);
                                    Date chosenDate = cal.getTime();
                                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    String formattedDate = formatter.format(chosenDate);

                                    edit_date_to.setText(selectedyear+"-"+selectedmonth+"-"+selectedday);
                                }
                            }, mYear, mMonth, mDay);
                            mDatePicker.setTitle("Select date");
                            mDatePicker.show();
                        }
                    });
                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (edit_date_from.getText().toString().matches("")) {
                                Toast.makeText(MainActivity.this, "You did not enter a Start Date", Toast.LENGTH_SHORT).show();

                            } else if (edit_date_to.getText().toString().matches("")) {
                                Toast.makeText(MainActivity.this, "You did not enter a End Date", Toast.LENGTH_SHORT).show();
                            } else {
                                String startDate = edit_date_from.getText().toString();
                                String endDate = edit_date_to.getText().toString();
                                Date date1 = null;
                                Date date2 = null;
                                try {
                                    date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                                    date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Common.userActivityRepository.emptyUserActivityDateWise(date1, date2);
                                //Common.userActivityRepository.emptyCart();
                                syncUserActivityData(edit_date_from.getText().toString(), edit_date_to.getText().toString());
                                infoDialog.dismiss();
                            }

                        }
                    });

                    infoDialog.show();
                }else if (Constant.SYNC.equals("UserActivitY")){
                    Common.userListRepository.emptyCart();
                    getUserData();
                }



            }
        });
        btn_header_application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onLeaveApplicationCreate();

            }
        });
        btn_header_application_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //     Toast.makeText(mContext, "dfdf", Toast.LENGTH_SHORT).show();
                onLeaveApplication();

            }
        });
        if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {

        } else {
            linear.setWeightSum(5f);
            relative.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initDB();

        if (Common.userActivityRepository.size() > 0) {

        } else {
//            DepartmentData();
//            unitListData();
            load();
            UserActivityData();
        }

    }
    private void getUserData(){

        showLoadingProgress(this);
        compositeDisposable.add(mServiceXact.getUserList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<AllUserListEntity>() {
            @Override
            public void accept(AllUserListEntity carts) throws Exception {
                // departmentListEntityList=carts;
                UserList userList = new UserList();

                for (AllUserListEntity.Data data: carts.data){
                    userList.UserId=data.UserId;
                    userList.FullName=data.FullName;
                    userList.Email=data.Email;
                    userList.OfficeExt=data.OfficeExt;
                    userList.Password=data.Password;
                    userList.AdminStatus=data.AdminStatus;
                    userList.Designation=data.Designation;
                    userList.JoiningDate=data.JoiningDate;
                    userList.CorporateMobileNumber=data.CorporateMobileNumber;
                    userList.PersonalMobileNumber=data.PersonalMobileNumber;
                    userList.EmergencyContactPerson=data.EmergencyContactPerson;
                    userList.RelationWithContactPerson=data.RelationWithContactPerson;
                    userList.BloodGroup=data.BloodGroup;
                    userList.ProfilePhoto=data.ProfilePhoto;
                    userList.UnitId=data.UnitId;
                    userList.UnitName=data.UnitName;
                    userList.UnitShortName=data.UnitShortName;
                    userList.DepartmentId=data.DepartmentId;
                    userList.DepartmentName=data.DepartmentName;
                    userList.DepartmentShortName=data.DepartmentShortName;
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
    private void syncUserActivityData(String startDate, String endDate) {
        showLoadingProgress(this);
        UserActivityPostEntity userActivityPostEntity = new UserActivityPostEntity();
        userActivityPostEntity.from_date = startDate;
        userActivityPostEntity.to_date = endDate;
        compositeDisposable.add(mServiceXact.getUserActivityList(userActivityPostEntity).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<UserActivityListEntity>() {
            @Override
            public void accept(UserActivityListEntity carts) throws Exception {
                // departmentListEntityList=carts;
                UserActivity userActivity = new UserActivity();

                for (UserActivityListEntity.Data userActivityListEntity : carts.data) {
                    userActivity.UserId = userActivityListEntity.UserId;
                    userActivity.WorkingDate = userActivityListEntity.WorkingDate;
                    userActivity.PunchInLocation = userActivityListEntity.PunchInLocation;
                    String sDate1 = userActivityListEntity.WorkingDate;
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
                    userActivity.Date = date1;
                    Log.e("dates", "date" + date1);

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

                    // userActivity.PunchInTime= Double.parseDouble(str);
                    userActivity.PunchOutLocation = userActivityListEntity.PunchOutLocation;
                    userActivity.PunchOutTime = userActivityListEntity.PunchOutTime;
                    userActivity.Duration = userActivityListEntity.Duration;
                    userActivity.PunchInTimeLate = userActivityListEntity.PunchInTime;
                    Common.userActivityRepository.insertToUserActivity(userActivity);

                }

                dismissLoadingProgress();
                //   progressBar.setVisibility(View.GONE);
            }
        }));
    }

    private void UserActivityData() {

        showLoadingProgress(this);
        UserActivityPostEntity userActivityPostEntity = new UserActivityPostEntity();
        compositeDisposable.add(mServiceXact.getUserActivityList(userActivityPostEntity).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<UserActivityListEntity>() {
            @Override
            public void accept(UserActivityListEntity carts) throws Exception {
                // departmentListEntityList=carts;
                UserActivity userActivity = new UserActivity();

                for (UserActivityListEntity.Data userActivityListEntity : carts.data) {
                    userActivity.UserId = userActivityListEntity.UserId;
                    userActivity.WorkingDate = userActivityListEntity.WorkingDate;
                    userActivity.PunchInLocation = userActivityListEntity.PunchInLocation;
                    String sDate1 = userActivityListEntity.WorkingDate;
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
                    userActivity.Date = date1;
                    Log.e("dates", "date" + date1);

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

                    // userActivity.PunchInTime= Double.parseDouble(str);
                    userActivity.PunchOutLocation = userActivityListEntity.PunchOutLocation;
                    userActivity.PunchOutTime = userActivityListEntity.PunchOutTime;
                    userActivity.Duration = userActivityListEntity.Duration;
                    userActivity.PunchInTimeLate = userActivityListEntity.PunchInTime;
                    Common.userActivityRepository.insertToUserActivity(userActivity);

                }

                dismissLoadingProgress();
                //   progressBar.setVisibility(View.GONE);
            }
        }));


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


    private void load() {
        showLoadingProgress(this);
        compositeDisposable.add(mService.getLeaveSummary().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<LeaveSummaryEntity>>() {
            @Override
            public void accept(ArrayList<LeaveSummaryEntity> carts) throws Exception {
                LeaveSummary leaveSummary = new LeaveSummary();
                EntityLeave entityLeave = new EntityLeave();
                RemainingLeave remainingLeave = new RemainingLeave();
                for (LeaveSummaryEntity leaveSummaryEntity : carts) {
                    leaveSummary.FullName = leaveSummaryEntity.FullName;
                    leaveSummary.UserIcon = leaveSummaryEntity.UserIcon;
                    leaveSummary.UserId = leaveSummaryEntity.UserId;
                    entityLeave.Casual = leaveSummaryEntity.entityLeaves.Casual;
                    entityLeave.Halfday = leaveSummaryEntity.entityLeaves.Halfday;
                    entityLeave.Sick = leaveSummaryEntity.entityLeaves.Sick;
                    entityLeave.UnPaid = leaveSummaryEntity.entityLeaves.UnPaid;
                    remainingLeave.Casual = leaveSummaryEntity.remainingLeaves.Casual;
                    remainingLeave.Sick = leaveSummaryEntity.remainingLeaves.Sick;

                    Common.leaveSummaryRepository.insertToLeaveSummary(leaveSummary);
                    Common.entityLeaveRepository.insertToEntityLeave(entityLeave);
                    Common.remainingLeaveRepository.insertToRemainingLeave(remainingLeave);
                }
//                mAdapters = new LeaveSummaryListAdapter(mActivity, carts);
//
//                rcl_leave_summary_list.setAdapter(mAdapters);
                dismissLoadingProgress();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dismissLoadingProgress();
            }
        }));


    }

    private void onBackForPunch() {
        Fragment f = getVisibleFragment();
        Log.e("frag", "frag" + f);
        if (f != null) {
            if (f instanceof MoreFragment) {
                int handle = ((MoreFragment) f).handleBackPress(2);
                if (handle == 0) {
                    finish();
                } else if (handle == 2) {
                    hideHeaderDetail();
                } else {
                    // do not hide header
                }
            }

        }
    }

    private void onBack() {
        Fragment f = getVisibleFragment();
        Log.e("frag", "frag" + f);
        if (f != null) {
            if (f instanceof MoreFragment) {

                if (f instanceof PunchInFragment) {
                    Toast.makeText(mContext, "true", Toast.LENGTH_SHORT).show();
                }
                int handle = ((MoreFragment) f).handleBackPress(1);
                if (handle == 0) {
                    finish();
                } else if (handle == 1) {
                    int handles = ((MoreFragment) f).handleBackPress(2);
                    if (handles == 2) {
                        hideHeaderDetail();
                    }

                } else if (handle == 2) {
                    hideHeaderDetail();
                } else {
                    // do not hide header
                }

            } else if (f instanceof LeaveFragment) {
                int handle = ((LeaveFragment) f).handleBackPress();
                if (handle == 0) {
                    finish();
                } else if (handle == 2) {
                    hideHeaderDetail();
                } else {
                    // do not hide header
                }
            } else if (f instanceof LeaveApplicationFragment) {
                int handle = ((LeaveApplicationFragment) f).handleBackPress();
                if (handle == 0) {
                    finish();
                } else if (handle == 2) {
                    hideHeaderDetailForApplication();
                } else {
                    // do not hide header
                }
            }
            // else if ()
            if (getSupportFragmentManager().findFragmentByTag(PunchInFragment.class.getSimpleName()) != null) {
                PunchInFragment f1 = (PunchInFragment) getSupportFragmentManager()
                        .findFragmentByTag(PunchInFragment.class.getSimpleName());
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
                transaction.remove(f1);
                transaction.commit();
                getSupportFragmentManager().popBackStack();

                hideHeaderDetail();
                // return 2;

            } else if (getSupportFragmentManager().findFragmentByTag(AboutUsFragment.class.getSimpleName()) != null) {
                AboutUsFragment f1 = (AboutUsFragment) getSupportFragmentManager()
                        .findFragmentByTag(PunchInFragment.class.getSimpleName());
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
                transaction.remove(f1);
                transaction.commit();
                getSupportFragmentManager().popBackStack();

                hideHeaderDetail();
                // return 2;

            }
//
//                int handle = ((SetUpFragment) f).handleBackPress();
//                if (handle == 0) {
//                    finish();
//                } else if (handle == 2) {
//                    hideHeaderDetail();
//                } else {
//                    // do not hide header
//                }


        } else {
            finish();
        }
    }

    public void hideHeaderDetail() {
        rlt_header.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        //card_view.setVisibility(View.GONE);
        view_header_details.setVisibility(View.GONE);
        rlt_header_details.setVisibility(View.GONE);


    }

    public void hideHeaderDetailForApplication() {
        rlt_header.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        //card_view.setVisibility(View.GONE);
        view_header_details.setVisibility(View.VISIBLE);
        rlt_header_details.setVisibility(View.VISIBLE);
        btn_header_application_create.setVisibility(View.VISIBLE);
        details_title.setText("Leave Summary");

    }

    public void hideHeaderDetailForLeave(String name) {
        if (name.equals("Approval")) {
            rlt_header.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            //card_view.setVisibility(View.GONE);
            view_header_details.setVisibility(View.VISIBLE);
            rlt_header_details.setVisibility(View.VISIBLE);
            details_title.setText("Leave Summary");
            btn_header_application_create.setVisibility(View.VISIBLE);
        } else if (name.equals("Application")) {
            rlt_header.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            //card_view.setVisibility(View.GONE);
            view_header_details.setVisibility(View.VISIBLE);
            rlt_header_details.setVisibility(View.VISIBLE);
            details_title.setText("Leave Application");
            btn_header_application_create.setVisibility(View.GONE);
        }


    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        Collections.reverse(fragments);
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    private void setUnselectAllmenu() {
        tv_home_menu.setSelected(false);
        tv_setup_menu.setSelected(false);
        tv_user_activity_menu.setSelected(false);
        tv_more_menu.setSelected(false);
        tv_user_setup_menus.setSelected(false);


        btn_footer_home.setSelected(false);
        btn_footer_setUp.setSelected(false);
        btn_footer_user_activity.setSelected(false);
        btn_footer_more.setSelected(false);
        btn_footer_setup_user.setSelected(false);

    }

    public void setUpFooter(int type) {
        setUnselectAllmenu();
        switch (type) {
            case 0:
                tv_home_menu.setSelected(true);
                btn_footer_home.setSelected(true);
                break;
            case 1:
                tv_setup_menu.setSelected(true);
                btn_footer_setUp.setSelected(true);
                break;
            case 2:
                tv_user_activity_menu.setSelected(true);
                btn_footer_user_activity.setSelected(true);
                break;
            case 3:
                tv_more_menu.setSelected(true);
                btn_footer_more.setSelected(true);
                break;
            case 4:
                tv_user_setup_menus.setSelected(true);
                btn_footer_setup_user.setSelected(true);
                break;

        }

    }

    //    }
    public void btn_home_clicked(View view) {
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = true;
        setUpFooter(HOME_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_HOME, null);
        // checkToGetTicket(false);
        title.setText("Home");
        rlt_header_details.setVisibility(View.GONE);
        view_header_details.setVisibility(View.GONE);

        btn_header_application.setVisibility(View.GONE);
        btn_header_application_create.setVisibility(View.GONE);
        rlt_header.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        btn_header_sync.setVisibility(View.VISIBLE);
        if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
            btn_header_sync.setVisibility(View.VISIBLE);
        } else {
            btn_header_sync.setVisibility(View.GONE);


        }


    }

    public void btn_setup_user_clicked(View view) {
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = true;
        setUpFooter(SET_UP_USER_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_SET_UP_USER, null);
        // checkToGetTicket(false);
        title.setText("Status");
        btn_header_sync.setVisibility(View.GONE);
        rlt_header_details.setVisibility(View.GONE);
        view_header_details.setVisibility(View.GONE);
        btn_header_application.setVisibility(View.GONE);
        rlt_header.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        btn_header_application_create.setVisibility(View.GONE);
    }

    public void btn_setup_clicked(View view) {
        // Toast.makeText(mContext, "Not implement Yet", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = false;
        setUpFooter(SET_UP_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_SET_UP, null);
        // checkToGetTicket(false);
        title.setText("Leave");
        rlt_header_details.setVisibility(View.GONE);
        view_header_details.setVisibility(View.GONE);
        rlt_header.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        btn_header_application.setVisibility(View.VISIBLE);
        btn_header_sync.setVisibility(View.GONE);

    }

    public void btn_user_clicked(View view) {
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = false;
        setUpFooter(USER_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_USER_ACTIVTY, null);
        // checkToGetTicket(false);
        title.setText("User Activity");
        rlt_header.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        btn_header_application_create.setVisibility(View.GONE);
        btn_header_application.setVisibility(View.GONE);
        rlt_header_details.setVisibility(View.GONE);
        view_header_details.setVisibility(View.GONE);
        btn_header_sync.setVisibility(View.VISIBLE);
        Constant.SYNC="UserActivitY";
    }

    public void btn_more_clicked(View view) {

        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = false;
        setUpFooter(MORE_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_MORE, null);
        // checkToGetTicket(false);
        title.setText("More");
        btn_header_application.setVisibility(View.GONE);
        btn_header_application_create.setVisibility(View.GONE);
        btn_header_sync.setVisibility(View.GONE);
        rlt_header_details.setVisibility(View.GONE);
        view_header_details.setVisibility(View.GONE);
    }

    public void onLeaveApplication() {

        Fragment f = getVisibleFragment();
        Log.e("frag", "frag" + f);
        if (f != null) {
            if (f instanceof LeaveFragment) {

                if (SharedPreferenceUtil.getUserID(MainActivity.this).equals("evankhan1234@gmail.com")) {
                    // newFrag = new SetUpFragment();

                    int handle = ((LeaveFragment) f).leaveApproval();
                    if (handle == 0) {
                        finish();
                    } else if (handle == 2) {
                        hideHeaderDetailForLeave("Application");
                    } else {
                        // do not hide header
                    }
                } else {

                    int handle = ((LeaveFragment) f).leaveApplication();
                    if (handle == 0) {
                        finish();
                    } else if (handle == 2) {
                        hideHeaderDetailForLeave("Application");
                    } else {
                        // do not hide header
                    }
                }


            }

        } else {
            // finish();
        }
    }

    public void onLeaveApplicationCreate() {

        Fragment f = getVisibleFragment();
        Log.e("frag", "frag" + f);
        if (f != null) {
            if (f instanceof LeaveApplicationApprovalFragment) {
                int handle = ((LeaveApplicationApprovalFragment) f).leaveApplication();
                if (handle == 0) {
                    finish();
                } else if (handle == 2) {
                    hideHeaderDetailForLeave("Approval");
                } else {
                    // do not hide header
                }


            }

        } else {
            // finish();
        }
    }

    public void hideHeaderDetails() {
        rlt_header.setVisibility(View.GONE);
        btn_header_application.setVisibility(View.GONE);
        //title.setVisibility(View.GONE);

        rlt_header_details.setVisibility(View.VISIBLE);
        view_header_details.setVisibility(View.VISIBLE);
        //rlt_header_moments.setVisibility(View.GONE);

    }

    public void afterClickTabItem(int fragId, Object obj) {
        addFragment(fragId, false);
    }

    public void addFragment(int fragId, boolean isHasAnimation) {
        // init fragment manager
        mFragManager = getSupportFragmentManager();
        // create transaction
        fragTransaction = mFragManager.beginTransaction();

        // init argument


        //check if there is any backstack if yes then remove it
        int count = mFragManager.getBackStackEntryCount();
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            mFragManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }


        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag.getTag() != null && mCurrentFrag.getTag().equals(String.valueOf(fragId))) {
            return;
        }

        Fragment newFrag = null;
        // identify which fragment will be called
        switch (fragId) {
            case Constant.FRAG_HOME:
                if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                    newFrag = new DashboardFragment();
                } else {
                    Constant.SYNC="UserActivitY";
                    newFrag = new PunchFragment();

                }
                break;
            case Constant.FRAG_SET_UP:
                newFrag = new LeaveApplicationApprovalFragment();

                //   newFrag = new AlertFragment();
                //  SharedPreferenceUtil.saveShared(getApplicationContext(), Constant.UNREAD_NOTICE, "0");
                //  setUnreadMessage();
                break;
            case Constant.FRAG_USER_ACTIVTY:
                newFrag = new SetUpFragment();
                // newFrag = new ChatCategoryFragment();
                //setUpHeader(Constant.FRAG_CHAT);

                break;
            case Constant.FRAG_MORE:
                newFrag = new MoreFragment();
                // newFrag = new ChatCategoryFragment();
                //setUpHeader(Constant.FRAG_CHAT);

                break;
            case Constant.FRAG_SET_UP_USER:
                newFrag = new HomeFragment();
                // newFrag = new ChatCategoryFragment();
                //setUpHeader(Constant.FRAG_CHAT);

                break;

            default:
                break;
        }


        // param 1: container id, param 2: new fragment, param 3: fragment id
        fragTransaction.replace(R.id.main_container, newFrag, String.valueOf(fragId));
        // prevent showed when user press back fabReview
        fragTransaction.addToBackStack(String.valueOf(fragId));
        fragTransaction.commit();

    }

    public void showHeaderDetail(String titles) {

        if (titles.equals("no")) {
            rlt_header.setVisibility(View.VISIBLE);
            // / rlt_header_details.setVisibility(View.VISIBLE);
            //view_header_details.setVisibility(View.VISIBLE);

            title.setVisibility(View.VISIBLE);
        } else if (titles.equals("test")) {
            rlt_header.setVisibility(View.GONE);
            rlt_header_details.setVisibility(View.GONE);
            view_header_details.setVisibility(View.GONE);
            btn_header_application_create.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
        } else if (titles.equals("rrr")) {
            rlt_header.setVisibility(View.GONE);
            rlt_header_details.setVisibility(View.VISIBLE);
            view_header_details.setVisibility(View.VISIBLE);
            btn_header_application_create.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
        }


    }

    public void ShowText(String name) {
        details_title.setText(name);
    }

}
