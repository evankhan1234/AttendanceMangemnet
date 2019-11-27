package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
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
import xact.idea.attendancesystem.Database.Model.SetUp;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Database.Model.UserList;
import xact.idea.attendancesystem.Entity.AllUserListEntity;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.LeaveSummaryEntity;
import xact.idea.attendancesystem.Entity.LoginPostEntity;
import xact.idea.attendancesystem.Entity.SetUpDataEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Entity.UserActivityListEntity;
import xact.idea.attendancesystem.Entity.UserActivityPostEntity;
import xact.idea.attendancesystem.Entity.UserListEntity;
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
    private ImageButton btn_header_back;
    private ImageButton btn_header_application;
    private ImageButton btn_header_sync;
    private ImageButton btn_header_application_create;
    private LinearLayout linear;
    private RelativeLayout relative;
    private DrawerLayout drawer_layout;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    IRetrofitApi mServiceXact;
    RelativeLayout rlt_root;
    ImageView btn_close_drawer;
    CircleImageView imageView;
    TextView text_username;
    TextView text_email;
    TextView text_phone_number;
    TextView text_designation;
    TextView text_department;
    TextView text_unit;
    TextView text;
    RelativeLayout relativelayoutPunch;
    RelativeLayout relativelayout;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress_bar = findViewById(R.id.progress_bar);
        text_phone_number = findViewById(R.id.text_phone_number);
        text = findViewById(R.id.text);
        text_designation = findViewById(R.id.text_designation);
        text_department =findViewById(R.id.text_department);
        text_unit = findViewById(R.id.text_unit);
        linear = findViewById(R.id.linear);
        relativelayoutPunch = findViewById(R.id.relativelayoutPunch);
        relativelayout = findViewById(R.id.relativelayout);
        text_email = findViewById(R.id.text_email);
        imageView = findViewById(R.id.imageView);
        text_username = findViewById(R.id.text_username);
        btn_close_drawer = findViewById(R.id.btn_close_drawer);
        drawer_layout = findViewById(R.id.drawer_layout);
        mService = Common.getApi();
        mServiceXact = Common.getApiXact();
        rlt_root = findViewById(R.id.rlt_root);
        btn_footer_setup_user = findViewById(R.id.btn_footer_setup_user);
        tv_user_setup_menus = findViewById(R.id.tv_user_setup_menus);
        relative = findViewById(R.id.relative);
        btn_header_sync = findViewById(R.id.btn_header_sync);
        btn_header_application = findViewById(R.id.btn_header_application);
        btn_header_application_create = findViewById(R.id.btn_header_application_create);
        title = findViewById(R.id.title);
        btn_header_back_ = findViewById(R.id.btn_header_back_);
        btn_header_back = findViewById(R.id.btn_header_back);
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

        String sessionId = getIntent().getStringExtra("EXTRA_SESSION");


        setFooter(sessionId);



        btn_header_back_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onBack();
            }
        });

        btn_header_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.broadcastIntent(MainActivity.this, rlt_root)) {
                    //Toast.makeText(mContext, "Connected ", Toast.LENGTH_SHORT).show();
                    if (Constant.SYNC.equals("Admin")) {
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
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                try {
                                    mcurrentDate.setTime(sdf.parse(edit_date_from.getText().toString()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                final int mYear = mcurrentDate.get(Calendar.YEAR);
                                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                        Calendar cal = Calendar.getInstance();

                                        cal.setTimeInMillis(0);
                                        cal.set(mYear, mMonth, mDay, 0, 0, 0);
                                        Date chosenDate = cal.getTime();
                                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                        String formattedDate = formatter.format(chosenDate);

                                        String a;
                                        String b;
                                        int in = selectedmonth + 1;
                                        String length = Integer.toString(in);
                                        int lengths = length.length();
                                        String lengthday = Integer.toString(selectedday);
                                        int lengthsday = lengthday.length();
                                        if (lengthsday < 2) {
                                            a = "0";
                                        } else {
                                            a = "";
                                        }
                                        if (lengths < 2) {
                                            b = "0";
                                        } else {
                                            b = "";
                                        }

//                                        for (int i=0;i<=in;i++){
//
//                                            a="0";
//                                        }
//                                        for (int i=0;i<=selectedday;i++){
//
//                                            b="0";
//                                        }
                                        edit_date_from.setText(a + selectedday + "-" + b + in + "-" + selectedyear);
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
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                try {
                                    mcurrentDate.setTime(sdf.parse(edit_date_to.getText().toString()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                final int mYear = mcurrentDate.get(Calendar.YEAR);
                                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                        Calendar cal = Calendar.getInstance();
                                        cal.setTimeInMillis(0);
                                        cal.set(mYear, mMonth, mDay, 0, 0, 0);
                                        Date chosenDate = cal.getTime();
                                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                        String formattedDate = formatter.format(chosenDate);

                                        String a;
                                        String b;
                                        int in = selectedmonth + 1;
                                        String length = Integer.toString(in);
                                        int lengths = length.length();
                                        String lengthday = Integer.toString(selectedday);
                                        int lengthsday = lengthday.length();
                                        if (lengthsday < 2) {
                                            a = "0";
                                        } else {
                                            a = "";
                                        }
                                        if (lengths < 2) {
                                            b = "0";
                                        } else {
                                            b = "";
                                        }

                                        edit_date_to.setText(a + selectedday + "-" + b + in + "-" + selectedyear);
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
                                        date1 = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
                                        date2 = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    Common.userActivityRepository.emptyUserActivityDateWise(date1, date2);


                                    syncUserActivityData(edit_date_from.getText().toString(), edit_date_to.getText().toString(), "");
                                    infoDialog.dismiss();

                                    //Common.userActivityRepository.emptyCart();

                                }

                            }
                        });

                        infoDialog.show();
                    } else if (Constant.SYNC.equals("Status")) {
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
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                try {
                                    mcurrentDate.setTime(sdf.parse(edit_date_from.getText().toString()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                final int mYear = mcurrentDate.get(Calendar.YEAR);
                                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                        Calendar cal = Calendar.getInstance();

                                        cal.setTimeInMillis(0);
                                        cal.set(mYear, mMonth, mDay, 0, 0, 0);
                                        Date chosenDate = cal.getTime();
                                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                        String formattedDate = formatter.format(chosenDate);

                                        String a;
                                        String b;
                                        int in = selectedmonth + 1;
                                        String length = Integer.toString(in);
                                        int lengths = length.length();
                                        String lengthday = Integer.toString(selectedday);
                                        int lengthsday = lengthday.length();
                                        if (lengthsday < 2) {
                                            a = "0";
                                        } else {
                                            a = "";
                                        }
                                        if (lengths < 2) {
                                            b = "0";
                                        } else {
                                            b = "";
                                        }
                                        edit_date_from.setText(a + selectedday + "-" + b + in + "-" + selectedyear);
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
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                try {
                                    mcurrentDate.setTime(sdf.parse(edit_date_to.getText().toString()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                final int mYear = mcurrentDate.get(Calendar.YEAR);
                                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                        Calendar cal = Calendar.getInstance();

                                        cal.setTimeInMillis(0);
                                        cal.set(mYear, mMonth, mDay, 0, 0, 0);
                                        Date chosenDate = cal.getTime();
                                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                        String formattedDate = formatter.format(chosenDate);

                                        String a;
                                        String b;
                                        int in = selectedmonth + 1;
                                        String length = Integer.toString(in);
                                        int lengths = length.length();
                                        String lengthday = Integer.toString(selectedday);
                                        int lengthsday = lengthday.length();
                                        if (lengthsday < 2) {
                                            a = "0";
                                        } else {
                                            a = "";
                                        }
                                        if (lengths < 2) {
                                            b = "0";
                                        } else {
                                            b = "";
                                        }
                                        edit_date_to.setText(a + selectedday + "-" + b + in + "-" + selectedyear);
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
                                        date1 = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
                                        date2 = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    Common.userActivityRepository.emptyUserActivityDateWiseId(date1, date2, SharedPreferenceUtil.getUser(MainActivity.this));


                                    syncUserActivityData(edit_date_from.getText().toString(), edit_date_to.getText().toString(), "");
                                    infoDialog.dismiss();

                                }

                            }
                        });

                        infoDialog.show();
                    } else if (Constant.SYNC.equals("UserActivitY")) {
                     //   Common.userListRepository.emptyCart();
                        getUserData();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(rlt_root, "No Internet", Snackbar.LENGTH_LONG);
                    snackbar.show();
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
            linear.setWeightSum(5f);
            relative.setVisibility(View.VISIBLE);
        } else {
            linear.setWeightSum(5f);
            relative.setVisibility(View.VISIBLE);
        }
        btn_header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(GravityCompat.START);

            }
        });
        btn_close_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(GravityCompat.START);
                drawer_layout.closeDrawer(GravityCompat.START);
            }
        });
        //String s=SharedPreferenceUtil.getPic(this);
        if (SharedPreferenceUtil.getPic(this).equals("null")) {
            Glide.with(this).load("https://www.hardiagedcare.com.au/wp-content/uploads/2019/02/default-avatar-profile-icon-vector-18942381.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.backwhite)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            imageView.setImageDrawable(resource);
                        }
                    });

        } else {
            Glide.with(this).load(SharedPreferenceUtil.getPic(this)).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.backwhite)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            imageView.setImageDrawable(resource);
                        }
                    });
        }
        UserList userList = Common.userListRepository.getUserListById(Integer.parseInt(SharedPreferenceUtil.getUser(this)));
        if (userList==null){

        }
        else {
            try {

                text_username.setText(userList.FullName);
                text_email.setText(userList.Email);
                text_phone_number.setText(userList.PersonalMobileNumber);
                text_designation.setText(userList.Designation);
                text_department.setText(userList.DepartmentName);
                text_unit.setText(userList.UnitName);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showInfoDialog(MainActivity.this);
            }
        });
        relativelayoutPunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfoDialog();
            }
        });
        if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {

            tv_more_menu.setText("Myself");
            btn_footer_more.setImageResource(R.drawable.img_footer_setup_selector);

        } else {

            tv_more_menu.setText("MORE");
            btn_footer_more.setImageResource(R.drawable.img_footer_more_selector);
        }
    }

    private void setFooter(String value) {
        if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
//            tv_user_setup_menus.setText("Attendance");
//            title.setText("Home");
//            tv_home_menu.setText("Home");
        } else {
            tv_user_setup_menus.setText("Myself");
            title.setText("Attendance");
            tv_home_menu.setText("Attendance");
        }
        if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
            btn_header_sync.setVisibility(View.VISIBLE);
            Constant.SYNC = "Admin";
        } else {
            Constant.SYNC = "Status";
        }
        switch (value) {
            case "home":
                if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                    tv_user_setup_menus.setSelected(true);
                    btn_footer_setup_user.setSelected(true);
                    btn_header_sync.setVisibility(View.VISIBLE);
                    tv_user_setup_menus.setText("Attendance");
                    title.setText("Attendance");
                    afterClickTabItem(Constant.FRAG_SET_UP_USER, null);
                } else {
                    btn_footer_home.setSelected(true);
                    tv_home_menu.setSelected(true);
                    afterClickTabItem(Constant.FRAG_HOME, null);
                }

                break;
            case "punch":

                if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {

                  startActivity(new Intent(MainActivity.this,PunchActivity.class));
                  finish();

                } else {

                    tv_user_setup_menus.setSelected(true);
                    btn_footer_setup_user.setSelected(true);
                    afterClickTabItem(Constant.FRAG_SET_UP_USER, null);
                }

                break;
            case "users":

                tv_user_activity_menu.setSelected(true);
                btn_footer_user_activity.setSelected(true);
                afterClickTabItem(Constant.FRAG_USER_ACTIVTY, null);
                break;
            case "leave":
                btn_header_application.setVisibility(View.VISIBLE);
                btn_header_sync.setVisibility(View.GONE);
                tv_setup_menu.setSelected(true);
                btn_footer_setUp.setSelected(true);
                afterClickTabItem(Constant.FRAG_SET_UP, null);
                break;
            case "myself":
                if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {

                   title.setText("Myself");
                } else {

                    title.setText("More");

                }
                tv_more_menu.setSelected(true);
                btn_footer_more.setSelected(true);
                afterClickTabItem(Constant.FRAG_MORE, null);
                break;
            default:
                btn_footer_home.setSelected(true);
                tv_home_menu.setSelected(true);
                afterClickTabItem(Constant.FRAG_HOME, null);
        }
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
                if (Utils.broadcastIntent(MainActivity.this, rlt_root)) {
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
                    AllData();
                    DashboardFragment.shows();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(rlt_root, "No Internet", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                infoDialog.dismiss();

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

    private void AllData() {



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Fragment f = getVisibleFragment();
            Log.e("frag", "frag" + f);
            if (f != null) {
                if (f instanceof PunchFragment)
                {
                    int handle = ((PunchFragment) f).handleBackPress();
                    if (handle == 0) {
                        finish();
                    }

                    else if (handle == 2) {
                        DashboardFragment test = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName());
                        if (test != null && test.isVisible())
                        {
                            Log.e("DashboardFragment", "DashboardFragment" );
                            if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                                tv_user_setup_menus.setText("Attendance");
                                title.setText("Home");
                                tv_home_menu.setText("Home");
                            } else {
                                tv_user_setup_menus.setText("Myself");
                                title.setText("Attendance");
                                tv_home_menu.setText("Attendance");
                            }
                            btn_footer_home.setSelected(true);
                            tv_home_menu.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        SetUpFragment test1 = (SetUpFragment) getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName());
                        if (test1 != null && test1.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("UsersActivity");
                            Log.e("sds", "s" );
                            tv_user_activity_menu.setSelected(true);
                            btn_footer_user_activity.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                        }
                        else {
                            //Whatever
                        }
                        PunchFragment tets2 = (PunchFragment) getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName());
                        if (tets2 != null && tets2.isVisible()){
                            DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName());
                            SetUpFragment setUpFragment = (SetUpFragment) getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName());
                            LeaveApplicationApprovalFragment leaveApplicationApprovalFragment = (LeaveApplicationApprovalFragment) getSupportFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
                            HomeFragment homeFragment= (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                            MoreFragment moreFragment= (MoreFragment) getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getSimpleName());


                            if (dashboardFragment!=null || setUpFragment!=null|| leaveApplicationApprovalFragment!=null || homeFragment!=null || moreFragment!=null){
                                Log.e("false","false");
                            }
                            else {
                                Log.e("true","true");
                                finish();
                            }

//                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1"))
//                        {
//                            tv_user_setup_menus.setText("Attendance");
//                            title.setText("Home");
//                            tv_home_menu.setText("Home");
//                        } else {
//                            tv_user_setup_menus.setText("Myself");
//                            title.setText("Attendance");
//                            tv_home_menu.setText("Attendance");
//                        }
//                            Log.e("sds", "s" );
//                            tv_user_setup_menus.setSelected(true);
//                            btn_footer_setup_user.setSelected(true);
//                            btn_footer_home.setSelected(true);
//                            tv_home_menu.setSelected(true);
//                            tv_user_activity_menu.setSelected(false);
//                            btn_footer_user_activity.setSelected(false);
//                            tv_setup_menu.setSelected(false);
//                            btn_footer_setUp.setSelected(false);
//                            tv_more_menu.setSelected(false);
//                            btn_footer_more.setSelected(false);
                      //   finish();

                        }
                        else {
                            //Whatever
                        }
                        LeaveApplicationApprovalFragment tets3 = (LeaveApplicationApprovalFragment) getSupportFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
                        if (tets3 != null && tets3.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("Leave");
                            Log.e("sds", "s" );
                            tv_setup_menu.setSelected(true);
                            btn_footer_setUp.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                        }
                        else {
                            //Whatever
                        }

                        HomeFragment tets4= (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                        if (tets4 != null && tets4.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                            tv_more_menu.setSelected(true);
                            btn_footer_more.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                            tv_user_setup_menus.setSelected(true);
                            btn_footer_setup_user.setSelected(true);
                        }
                            title.setText("Myself");
                            Log.e("sds", "s" );


                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        MoreFragment tets5= (MoreFragment) getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getSimpleName());
                        if (tets5 != null && tets5.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("More");
                            Log.e("sds", "s" );
                            tv_more_menu.setSelected(true);
                            btn_footer_more.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                        }
                        else {
                            //Whatever
                        }
                        hideHeaderDetail();
                        //hideHeaderDetailBack("UsersActivity");
                    } else {
                        // do not hide header
                    }
                }
               else if (f instanceof HomeFragment)
                {
                    int handle = ((HomeFragment) f).handleBackPress();
                    if (handle == 0) {
                        finish();
                    }

                    else if (handle == 2) {
                        DashboardFragment test = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName());
                        if (test != null && test.isVisible())
                        {
                            Log.e("sds", "s" );
                            if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                                tv_user_setup_menus.setText("Attendance");
                                title.setText("Home");
                                tv_home_menu.setText("Home");
                            } else {
                                tv_user_setup_menus.setText("Myself");
                                title.setText("Attendance");
                                tv_home_menu.setText("Attendance");
                            }
                            btn_footer_home.setSelected(true);
                            tv_home_menu.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        SetUpFragment test1 = (SetUpFragment) getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName());
                        if (test1 != null && test1.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("UsersActivity");
                            Log.e("sds", "s" );
                            tv_user_activity_menu.setSelected(true);
                            btn_footer_user_activity.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        PunchFragment tets2 = (PunchFragment) getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName());
                        if (tets2 != null && tets2.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");
                            title.setText("Home");
                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");
                            title.setText("Attendance");
                            tv_home_menu.setText("Attendance");
                        }
                            Log.e("sds", "s" );
                            tv_user_setup_menus.setSelected(true);
                            btn_footer_setup_user.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        LeaveApplicationApprovalFragment tets3 = (LeaveApplicationApprovalFragment) getSupportFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
                        if (tets3 != null && tets3.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("Leave");
                            Log.e("sds", "s" );
                            tv_setup_menu.setSelected(true);
                            btn_footer_setUp.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }

                        HomeFragment tets4= (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                        if (tets4 != null && tets4.isVisible())
                        {
                            DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName());
                            SetUpFragment setUpFragment = (SetUpFragment) getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName());
                            LeaveApplicationApprovalFragment leaveApplicationApprovalFragment = (LeaveApplicationApprovalFragment) getSupportFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
                            PunchFragment punchFragment= (PunchFragment) getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName());
                            MoreFragment moreFragment= (MoreFragment) getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getSimpleName());


                            if (dashboardFragment!=null || setUpFragment!=null|| leaveApplicationApprovalFragment!=null || punchFragment!=null || moreFragment!=null){
                                Log.e("false","false");
                            }
                            else {
                                Log.e("true","true");
                                finish();
                            }
//                            if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
//                            tv_user_setup_menus.setText("Attendance");
//
//                            tv_home_menu.setText("Home");
//                        } else {
//                            tv_user_setup_menus.setText("Myself");
//
//                            tv_home_menu.setText("Attendance");
//                        }
//                            title.setText("Myself");
//                            Log.e("sds", "s" );
//                            tv_more_menu.setSelected(true);
//                            btn_footer_more.setSelected(true);
//                            btn_footer_home.setSelected(true);
//                            tv_home_menu.setSelected(true);
//                            tv_user_setup_menus.setSelected(false);
//                            btn_footer_setup_user.setSelected(false);
//                            tv_user_activity_menu.setSelected(false);
//                            btn_footer_user_activity.setSelected(false);
//                            tv_setup_menu.setSelected(false);
//                            btn_footer_setUp.setSelected(false);
                          //  finish();


                        }
                        else {
                            //Whatever
                        }
                        MoreFragment tets5= (MoreFragment) getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getSimpleName());
                        if (tets5 != null && tets5.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("More");
                            Log.e("sds", "s" );
                            tv_more_menu.setSelected(true);
                            btn_footer_more.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        hideHeaderDetail();
                        //hideHeaderDetailBack("UsersActivity");
                    } else {
                        // do not hide header
                    }
                }
                else if (f instanceof SetUpFragment) {
                    int handle = ((SetUpFragment) f).handleBackPress();
                    if (handle == 0) {
                        finish();
                    }

                    else if (handle == 2) {
                        DashboardFragment test = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName());
                        if (test != null && test.isVisible())
                        {
                            Log.e("sds", "s" );
                            if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                                tv_user_setup_menus.setText("Attendance");
                                title.setText("Home");
                                tv_home_menu.setText("Home");
                            } else {
                                tv_user_setup_menus.setText("Myself");
                                title.setText("Attendance");
                                tv_home_menu.setText("Attendance");
                            }
                            btn_footer_home.setSelected(true);
                            tv_home_menu.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        SetUpFragment test1 = (SetUpFragment) getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName());
                        if (test1 != null && test1.isVisible())
                        {
                            DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName());
                            PunchFragment punchFragment = (PunchFragment) getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName());
                            LeaveApplicationApprovalFragment leaveApplicationApprovalFragment = (LeaveApplicationApprovalFragment) getSupportFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
                            HomeFragment homeFragment= (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                            MoreFragment moreFragment= (MoreFragment) getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getSimpleName());


                            if (dashboardFragment!=null || punchFragment!=null|| leaveApplicationApprovalFragment!=null || homeFragment!=null || moreFragment!=null){
                                Log.e("false","false");
                            }
                            else {
                                Log.e("true","true");
                                finish();
                            }
//                            if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
//                            tv_user_setup_menus.setText("Attendance");
//
//                            tv_home_menu.setText("Home");
//                        } else {
//                            tv_user_setup_menus.setText("Myself");
//
//                            tv_home_menu.setText("Attendance");
//                        }
//                            title.setText("UsersActivity");
//                            Log.e("sds", "s" );
//                            tv_user_activity_menu.setSelected(true);
//                            btn_footer_user_activity.setSelected(true);
//                            btn_footer_home.setSelected(true);
//                            tv_home_menu.setSelected(true);
//                            tv_user_setup_menus.setSelected(false);
//                            btn_footer_setup_user.setSelected(false);
//                            tv_setup_menu.setSelected(false);
//                            btn_footer_setUp.setSelected(false);
//                            tv_more_menu.setSelected(false);
//                            btn_footer_more.setSelected(false);
                            //finish();

                        }
                        else {
                            //Whatever
                        }
                        PunchFragment tets2 = (PunchFragment) getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName());
                        if (tets2 != null && tets2.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");
                            title.setText("Home");
                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");
                            title.setText("Attendance");
                            tv_home_menu.setText("Attendance");
                        }
                            Log.e("sds", "s" );
                            tv_user_setup_menus.setSelected(true);
                            btn_footer_setup_user.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        LeaveApplicationApprovalFragment tets3 = (LeaveApplicationApprovalFragment) getSupportFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
                        if (tets3 != null && tets3.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("Leave");
                            Log.e("sds", "s" );
                            tv_setup_menu.setSelected(true);
                            btn_footer_setUp.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }

                        HomeFragment tets4= (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                        if (tets4 != null && tets4.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("Myself");
                            Log.e("sds", "s" );
                            tv_more_menu.setSelected(true);
                            btn_footer_more.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);


                        }
                        else {
                            //Whatever
                        }
                        MoreFragment tets5= (MoreFragment) getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getSimpleName());
                        if (tets5 != null && tets5.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("More");
                            Log.e("sds", "s" );
                            tv_more_menu.setSelected(true);
                            btn_footer_more.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        hideHeaderDetail();
                        //hideHeaderDetailBack("UsersActivity");
                    } else {
                        // do not hide header
                    }
//                    if (getFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName()) != null) {
//                        Log.e("dfdg","fdsf");
//                        SetUpFragment f1 = (SetUpFragment) getSupportFragmentManager()
//                                .findFragmentByTag(SetUpFragment.class.getSimpleName());
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                        transaction.remove(f1);
//                        transaction.commit();
//                        getSupportFragmentManager().popBackStack();
//
//                        hideHeaderDetail();
//                        // return 2;
//
//                    }
                }
                else if (f instanceof LeaveApplicationApprovalFragment)
                {
                    int handle = ((LeaveApplicationApprovalFragment) f).handleBackPress();
                    if (handle == 0) {
                        finish();
                    }

                    else if (handle == 2) {
                        DashboardFragment test = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName());
                        if (test != null && test.isVisible())
                        {
                            Log.e("sds", "s" );
                            if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                                tv_user_setup_menus.setText("Attendance");
                                title.setText("Home");
                                tv_home_menu.setText("Home");
                            } else {
                                tv_user_setup_menus.setText("Myself");
                                title.setText("Attendance");
                                tv_home_menu.setText("Attendance");
                            }
                            btn_footer_home.setSelected(true);
                            tv_home_menu.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        SetUpFragment test1 = (SetUpFragment) getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName());
                        if (test1 != null && test1.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("UsersActivity");
                            Log.e("sds", "s" );
                            tv_user_activity_menu.setSelected(true);
                            btn_footer_user_activity.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        PunchFragment tets2 = (PunchFragment) getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName());
                        if (tets2 != null && tets2.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");
                            title.setText("Home");
                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");
                            title.setText("Attendance");
                            tv_home_menu.setText("Attendance");
                        }
                            Log.e("sds", "s" );
                            tv_user_setup_menus.setSelected(true);
                            btn_footer_setup_user.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        LeaveApplicationApprovalFragment tets3 = (LeaveApplicationApprovalFragment) getSupportFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
                        if (tets3 != null && tets3.isVisible())
                        {
                            DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName());
                            PunchFragment punchFragment = (PunchFragment) getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName());
                            SetUpFragment setUpFragment = (SetUpFragment) getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName());
                            HomeFragment homeFragment= (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                            MoreFragment moreFragment= (MoreFragment) getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getSimpleName());


                            if (dashboardFragment!=null || punchFragment!=null|| setUpFragment!=null || homeFragment!=null || moreFragment!=null){
                                Log.e("false","false");
                            }
                            else {
                                Log.e("true","true");
                                finish();
                            }
//                            if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
//                            tv_user_setup_menus.setText("Attendance");
//
//                            tv_home_menu.setText("Home");
//                        } else {
//                            tv_user_setup_menus.setText("Myself");
//
//                            tv_home_menu.setText("Attendance");
//                        }
//                            title.setText("Leave");
//                            Log.e("sds", "s" );
//                            tv_setup_menu.setSelected(true);
//                            btn_footer_setUp.setSelected(true);
//                            btn_footer_home.setSelected(true);
//                            tv_home_menu.setSelected(true);
//                            tv_user_setup_menus.setSelected(false);
//                            btn_footer_setup_user.setSelected(false);
//                            tv_user_activity_menu.setSelected(false);
//                            btn_footer_user_activity.setSelected(false);
//                            tv_more_menu.setSelected(false);
//                            btn_footer_more.setSelected(false);
                       //     finish();

                        }
                        else {
                            //Whatever
                        }

                        HomeFragment tets4= (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                        if (tets4 != null && tets4.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("Myself");
                            Log.e("sds", "s" );
                            tv_more_menu.setSelected(true);
                            btn_footer_more.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);


                        }
                        else {
                            //Whatever
                        }
                        MoreFragment tets5= (MoreFragment) getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getSimpleName());
                        if (tets5 != null && tets5.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("More");
                            Log.e("sds", "s" );
                            tv_more_menu.setSelected(true);
                            btn_footer_more.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        hideHeaderDetail();
                        //hideHeaderDetailBack("UsersActivity");
                    } else {
                        // do not hide header
                    }
//                    if (getFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName()) != null) {
//                        Log.e("dfdg","fdsf");
//                        SetUpFragment f1 = (SetUpFragment) getSupportFragmentManager()
//                                .findFragmentByTag(SetUpFragment.class.getSimpleName());
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                        transaction.remove(f1);
//                        transaction.commit();
//                        getSupportFragmentManager().popBackStack();
//
//                        hideHeaderDetail();
//                        // return 2;
//
//                    }
                }
                else if (f instanceof MoreFragment)
                {
                    int handle = ((MoreFragment) f).handleBackPress();
                    if (handle == 0) {
                        finish();
                    }

                    else if (handle == 2) {
                        DashboardFragment test = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName());
                        if (test != null && test.isVisible())
                        {
                            Log.e("sds", "s" );
                            if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                                tv_user_setup_menus.setText("Attendance");
                                title.setText("Home");
                                tv_home_menu.setText("Home");
                            } else {
                                tv_user_setup_menus.setText("Myself");
                                title.setText("Attendance");
                                tv_home_menu.setText("Attendance");
                            }
                            btn_footer_home.setSelected(true);
                            tv_home_menu.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        SetUpFragment test1 = (SetUpFragment) getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName());
                        if (test1 != null && test1.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("UsersActivity");
                            Log.e("sds", "s" );
                            tv_user_activity_menu.setSelected(true);
                            btn_footer_user_activity.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        PunchFragment tets2 = (PunchFragment) getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName());
                        if (tets2 != null && tets2.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");
                            title.setText("Home");
                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");
                            title.setText("Attendance");
                            tv_home_menu.setText("Attendance");
                        }
                            Log.e("sds", "s" );
                            tv_user_setup_menus.setSelected(true);
                            btn_footer_setup_user.setSelected(true);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        LeaveApplicationApprovalFragment tets3 = (LeaveApplicationApprovalFragment) getSupportFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
                        if (tets3 != null && tets3.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("Leave");
                            Log.e("sds", "s" );
                            tv_setup_menu.setSelected(true);
                            btn_footer_setUp.setSelected(true);

                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                        }
                        else {
                            //Whatever
                        }

                        HomeFragment tets4= (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                        if (tets4 != null && tets4.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("Myself");
                            Log.e("sds", "s" );
                            tv_more_menu.setSelected(true);
                            btn_footer_more.setSelected(true);

                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        MoreFragment tets5= (MoreFragment) getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getSimpleName());
                        if (tets5 != null && tets5.isVisible())
                        {
                            DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName());
                            PunchFragment punchFragment = (PunchFragment) getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName());
                            LeaveApplicationApprovalFragment leaveApplicationApprovalFragment = (LeaveApplicationApprovalFragment) getSupportFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
                            HomeFragment homeFragment= (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                            SetUpFragment setUpFragment= (SetUpFragment) getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName());


                            if (dashboardFragment!=null || punchFragment!=null|| leaveApplicationApprovalFragment!=null || homeFragment!=null || setUpFragment!=null){
                                Log.e("false","false");
                            }
                            else {
                                Log.e("true","true");
                                finish();
                            }
//                            if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
//                            tv_user_setup_menus.setText("Attendance");
//
//                            tv_home_menu.setText("Home");
//                        } else {
//                            tv_user_setup_menus.setText("Myself");
//
//                            tv_home_menu.setText("Attendance");
//                        }
//                            title.setText("More");
//                            Log.e("sds", "s" );
//                            tv_more_menu.setSelected(true);
//                            btn_footer_more.setSelected(true);
//                            btn_footer_home.setSelected(true);
//                            tv_home_menu.setSelected(true);
//                            tv_user_setup_menus.setSelected(false);
//                            btn_footer_setup_user.setSelected(false);
//                            tv_user_activity_menu.setSelected(false);
//                            btn_footer_user_activity.setSelected(false);
//                            tv_setup_menu.setSelected(false);
//                            btn_footer_setUp.setSelected(false);
                          //  finish();

                        }
                        else {
                            //Whatever
                        }
                        hideHeaderDetail();
                        //hideHeaderDetailBack("UsersActivity");
                    } else {
                        // do not hide header
                    }
//                    if (getFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName()) != null) {
//                        Log.e("dfdg","fdsf");
//                        SetUpFragment f1 = (SetUpFragment) getSupportFragmentManager()
//                                .findFragmentByTag(SetUpFragment.class.getSimpleName());
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                        transaction.remove(f1);
//                        transaction.commit();
//                        getSupportFragmentManager().popBackStack();
//
//                        hideHeaderDetail();
//                        // return 2;
//
//                    }
                }
                else if (f instanceof DashboardFragment)
                {
                    int handle = ((DashboardFragment) f).handleBackPress();
                    if (handle == 0) {
                        finish();
                    }

                    else if (handle == 2)
                    {
                        DashboardFragment test = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName());
                        if (test != null && test.isVisible())
                        {
                            MoreFragment moreFragment = (MoreFragment) getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getSimpleName());
                            PunchFragment punchFragment = (PunchFragment) getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName());
                            LeaveApplicationApprovalFragment leaveApplicationApprovalFragment = (LeaveApplicationApprovalFragment) getSupportFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
                            HomeFragment homeFragment= (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                            SetUpFragment setUpFragment= (SetUpFragment) getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName());


                            if (moreFragment!=null || punchFragment!=null|| leaveApplicationApprovalFragment!=null || homeFragment!=null || setUpFragment!=null){
                                Log.e("false","false");
                            }
                            else {
                                Log.e("true","true");
                                finish();
                            }
//                            Log.e("sds", "s" );
//                            if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
//                                tv_user_setup_menus.setText("Attendance");
//                                title.setText("Home");
//                                tv_home_menu.setText("Home");
//                            } else {
//                                tv_user_setup_menus.setText("Myself");
//                                title.setText("Attendance");
//                                tv_home_menu.setText("Attendance");
//                            }
//                            btn_footer_home.setSelected(true);
//                            tv_home_menu.setSelected(true);
//                            tv_user_setup_menus.setSelected(false);
//                            btn_footer_setup_user.setSelected(false);
//                            tv_user_activity_menu.setSelected(false);
//                            btn_footer_user_activity.setSelected(false);
//                            tv_setup_menu.setSelected(false);
//                            btn_footer_setUp.setSelected(false);
//                            tv_more_menu.setSelected(false);
//                            btn_footer_more.setSelected(false);
                     finish();

                        }
                        else {
                            //Whatever
                        }
                        SetUpFragment test1 = (SetUpFragment) getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName());
                        if (test1 != null && test1.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("UsersActivity");
                            Log.e("sds", "s" );
                            tv_user_activity_menu.setSelected(true);
                            btn_footer_user_activity.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                        }
                        else {
                            //Whatever
                        }
                        PunchFragment tets2 = (PunchFragment) getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName());
                        if (tets2 != null && tets2.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");
                            title.setText("Home");
                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");
                            title.setText("Attendance");
                            tv_home_menu.setText("Attendance");
                        }
                            Log.e("sds", "s" );
                            tv_user_setup_menus.setSelected(true);
                            btn_footer_setup_user.setSelected(true);

                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                        }
                        else {
                            //Whatever
                        }
                        LeaveApplicationApprovalFragment tets3 = (LeaveApplicationApprovalFragment) getSupportFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
                        if (tets3 != null && tets3.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("Leave");
                            Log.e("sds", "s" );
                            tv_setup_menu.setSelected(true);
                            btn_footer_setUp.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_more_menu.setSelected(false);
                            btn_footer_more.setSelected(false);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                        }
                        else {
                            //Whatever
                        }

                        HomeFragment tets4= (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                        if (tets4 != null && tets4.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("Myself");
                            Log.e("sds", "s" );
                            tv_more_menu.setSelected(true);
                            btn_footer_more.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);

                        }
                        else {
                            //Whatever
                        }
                        MoreFragment tets5= (MoreFragment) getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getSimpleName());
                        if (tets5 != null && tets5.isVisible())
                        {if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                            tv_user_setup_menus.setText("Attendance");

                            tv_home_menu.setText("Home");
                        } else {
                            tv_user_setup_menus.setText("Myself");

                            tv_home_menu.setText("Attendance");
                        }
                            title.setText("More");
                            Log.e("sds", "s" );
                            tv_more_menu.setSelected(true);
                            btn_footer_more.setSelected(true);
                            tv_user_setup_menus.setSelected(false);
                            btn_footer_setup_user.setSelected(false);
                            tv_user_activity_menu.setSelected(false);
                            btn_footer_user_activity.setSelected(false);
                            tv_setup_menu.setSelected(false);
                            btn_footer_setUp.setSelected(false);
                            btn_footer_home.setSelected(false);
                            tv_home_menu.setSelected(false);
                        }
                        else {
                            //Whatever
                        }
                        hideHeaderDetail();
                        //hideHeaderDetailBack("UsersActivity");
                    } else {
                       finish();
                    }
//                    if (getFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName()) != null) {
//                        Log.e("dfdg","fdsf");
//                        SetUpFragment f1 = (SetUpFragment) getSupportFragmentManager()
//                                .findFragmentByTag(SetUpFragment.class.getSimpleName());
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                        transaction.remove(f1);
//                        transaction.commit();
//                        getSupportFragmentManager().popBackStack();
//
//                        hideHeaderDetail();
//                        // return 2;
//
//                    }
                }
                else {
                    finish();
                }
            }
            //finish();
//            if (getSupportFragmentManager().findFragmentByTag(PunchInFragment.class.getSimpleName()) != null) {
//                PunchInFragment f1 = (PunchInFragment) getSupportFragmentManager()
//                        .findFragmentByTag(PunchInFragment.class.getSimpleName());
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                transaction.remove(f1);
//                transaction.commit();
//                getSupportFragmentManager().popBackStack();
//
//                hideHeaderDetail();
//                // return 2;
//
//            } else if (getSupportFragmentManager().findFragmentByTag(AboutUsFragment.class.getSimpleName()) != null) {
//                AboutUsFragment f1 = (AboutUsFragment) getSupportFragmentManager()
//                        .findFragmentByTag(PunchInFragment.class.getSimpleName());
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                transaction.remove(f1);
//                transaction.commit();
//                getSupportFragmentManager().popBackStack();
//
//                hideHeaderDetail();
//                // return 2;
//
//            }
//            else if (getSupportFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName()) != null) {
//                PunchFragment f1 = (PunchFragment) getSupportFragmentManager()
//                        .findFragmentByTag(PunchFragment.class.getSimpleName());
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                transaction.remove(f1);
//                transaction.commit();
//                getSupportFragmentManager().popBackStack();
//
//                hideHeaderDetail();
//                // return 2;
//
//            }
//            else if (getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName()) != null) {
//                HomeFragment f1 = (HomeFragment) getSupportFragmentManager()
//                        .findFragmentByTag(HomeFragment.class.getSimpleName());
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                transaction.remove(f1);
//                transaction.commit();
//                getSupportFragmentManager().popBackStack();
//
//                hideHeaderDetail();
//                // return 2;
//
//            }
//            else if (getSupportFragmentManager().findFragmentByTag(ProfileDetailsFragment.class.getSimpleName()) != null) {
//                ProfileDetailsFragment f1 = (ProfileDetailsFragment) getSupportFragmentManager()
//                        .findFragmentByTag(ProfileDetailsFragment.class.getSimpleName());
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                transaction.remove(f1);
//                transaction.commit();
//                getSupportFragmentManager().popBackStack();
//
//                hideHeaderDetail();
//                // return 2;
//
//            }
//            else if (getSupportFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName()) != null) {
//                SetUpFragment f1 = (SetUpFragment) getSupportFragmentManager()
//                        .findFragmentByTag(SetUpFragment.class.getSimpleName());
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                transaction.remove(f1);
//                transaction.commit();
//                getSupportFragmentManager().popBackStack();
//
//                hideHeaderDetail();
//                // return 2;
//
//            }
//            else if (getSupportFragmentManager().findFragmentByTag(DashboardFragment.class.getSimpleName()) != null) {
//                DashboardFragment f1 = (DashboardFragment) getSupportFragmentManager()
//                        .findFragmentByTag(DashboardFragment.class.getSimpleName());
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                transaction.remove(f1);
//                transaction.commit();
//                getSupportFragmentManager().popBackStack();
//
//                hideHeaderDetail();
//                // return 2;
//
//            }
        }


    }
        private class DownloadZipFileTask extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                return null;
            }

            @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        initDB();

        if (Common.userActivityRepository.size() > 0) {

        } else {
//            DepartmentData();
//            unitListData();
            if (Utils.broadcastIntent(MainActivity.this, rlt_root)) {
                //Toast.makeText(mContext, "Connected ", Toast.LENGTH_SHORT).show();
             //   load();
                UserActivityData();
            } else {
                Snackbar snackbar = Snackbar
                        .make(rlt_root, "No Internet", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }

    }

    private void getUserData() {

        showLoadingProgress(MainActivity.this);
        compositeDisposable.add(mServiceXact.getUserList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<AllUserListEntity>() {
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
    DownloadZipFileTask downloadZipFileTask;
    private void syncUserActivityData(String startDate, String endDate, String userID) {
        showLoadingProgress(this);
        String firstFourCharss = "";     //substring containing first 4 characters
        String firstFourChars2 = "";     //substring containing first 4 characters


        String date1 = null;
        String date2 = null;
        firstFourCharss = startDate.substring(2, 3);
        firstFourChars2 = endDate.substring(2, 3);
        if (firstFourCharss.equals("-")) {

            String firstFourOne = startDate.substring(6, 10);

            String firstFourTwo_ = startDate.substring(2, 6);
            String firstFourThree = startDate.substring(0, 2);

            date1 = firstFourOne + firstFourTwo_ + firstFourThree;

        }
        if (firstFourChars2.equals("-")) {

            String firstFourOne = endDate.substring(6, 10);

            String firstFourTwo_ = endDate.substring(2, 6);
            String firstFourThree = endDate.substring(0, 2);

            date2 = firstFourOne + firstFourTwo_ + firstFourThree;

        }
        UserActivityPostEntity userActivityPostEntity = new UserActivityPostEntity();
        userActivityPostEntity.from_date = date1;
        userActivityPostEntity.to_date = date2;
        userActivityPostEntity.user_id = userID;
        compositeDisposable.add(mServiceXact.getUserActivityList(userActivityPostEntity).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe
                (
                new Consumer<UserActivityListEntity>() {
            @Override
            public void accept(UserActivityListEntity carts) throws Exception
            {
                // departmentListEntityList=carts;

               // new DownloadZipFileTask().execute();
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
                    if (userActivityListEntity.PunchInTime.equals("") || userActivityListEntity.PunchOutTime.equals("")) {
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

                        userActivity.PunchOutTime = userActivityListEntity.PunchOutTime;
                        userActivity.Duration = userActivityListEntity.Duration;
                        userActivity.PunchInTimeLate = userActivityListEntity.PunchInTime;


                    } else if (userActivityListEntity.PunchInTime != null && userActivityListEntity.PunchOutTime != null) {
                        String strTime = userActivityListEntity.PunchInTime;
                        String endTime = userActivityListEntity.PunchOutTime;
                        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                        try {
                            Date dates = dateFormat.parse(strTime);
                            Date enddates = dateFormat.parse(endTime);
                            SimpleDateFormat formatters = new SimpleDateFormat("hh:mm a");

                            String currentTime = formatters.format(dates);
                            String endTimes = formatters.format(enddates);
                            long difference = enddates.getTime() - dates.getTime();
                            int days = (int) (difference / (1000 * 60 * 60 * 24));
                            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
                            Log.e("currentTime", "currentTime" + currentTime);
                            Log.e("endTime", "endTime" + endTimes);
                            Log.e("difference", "difference" + days + ":" + min);

                            userActivity.PunchOutTime = endTimes;
                            userActivity.Duration = days + ":" + min;
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
//                if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("0")) {
//                    PunchFragment.show();
//                }

                dismissLoadingProgress();
                //   progressBar.setVisibility(View.GONE);
            }
        }, new Consumer<Throwable>()
                        {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dismissLoadingProgress();
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
                            SimpleDateFormat formatte= new SimpleDateFormat("hh:mm a");

                            String currentTime = formatters.format(dates);
                            String endTimes = formatters.format(enddates);
//                            Date da1= formatte.parse(currentTime);
//                            Date end= formatte.parse(endTimes);
                            Date  da1e = formatte.parse(currentTime);
                            Log.e("da1", "da1" + da1e.toString());
                            Date end = formatte.parse(endTimes);
                            long difference = end.getTime() - da1e.getTime();
                            int  days = (int) (difference / (1000*60*60*24));
                            int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
                            int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
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
                if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("0")) {
                    PunchFragment.show();
                }

                dismissLoadingProgress();
                //   progressBar.setVisibility(View.GONE);
            }
        }));


    }

    private void DepartmentData() {

        showLoadingProgress(MainActivity.this);

        compositeDisposable.add(mServiceXact.getDepartmentList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<DepartmentListEntity>() {
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
        showLoadingProgress(MainActivity.this);


        compositeDisposable.add(mServiceXact.getUnitList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<UnitListEntity>() {
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
        showLoadingProgress(MainActivity.this);

        compositeDisposable.add(mServiceXact.getSetUpData().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<SetUpDataEntity>() {
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

            }
            else   if (f instanceof SetUpFragment) {



                int handle = ((SetUpFragment) f).test();
                if (handle == 0) {
                    finish();
                } else if (handle == 2) {
                  //  afterClickTabItem(3,null);
                    hideHeaderDetail();
                } else {
                    // do not hide header
                }

            }
            else   if (f instanceof PunchInFragment) {


                int handle = ((PunchInFragment) f).handleBackPress();
                if (handle == 0) {
                    finish();
                } else if (handle == 2) {
                    hideHeaderDetail();
                } else {
                    // do not hide header
                }

            }
            else if (f instanceof LeaveFragment) {
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
//            if (getSupportFragmentManager().findFragmentByTag(PunchInFragment.class.getSimpleName()) != null) {
//                PunchInFragment f1 = (PunchInFragment) getSupportFragmentManager()
//                        .findFragmentByTag(PunchInFragment.class.getSimpleName());
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                transaction.remove(f1);
//                transaction.commit();
//                getSupportFragmentManager().popBackStack();
//
//                hideHeaderDetail();
//                // return 2;
//
//            } else
           if (getSupportFragmentManager().findFragmentByTag(AboutUsFragment.class.getSimpleName()) != null) {
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
    public void hideHeaderDetailBack(String value) {
        switch (value){
            case "UsersActivity":
//                title.setText("UsersActivity");
//                tv_user_activity_menu.setSelected(true);
//                btn_footer_user_activity.setSelected(true);
        }
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
        Constant.VALUE="users";
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = true;
        setUpFooter(HOME_BTN);
        //show the initial home page

        // checkToGetTicket(false);
        if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {

            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
            finish();

        } else {
            tv_user_setup_menus.setText("Myself");
            afterClickTabItem(Constant.FRAG_HOME, null);
        }
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
        Constant.VALUE="users";
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = true;
        setUpFooter(SET_UP_USER_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_SET_UP_USER, null);
        // checkToGetTicket(false);

        if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
            title.setText("Punch");
            btn_header_sync.setVisibility(View.GONE);
        } else {
            Constant.SYNC = "Status";
            title.setText("Myself");
            btn_header_sync.setVisibility(View.VISIBLE);
        }

        rlt_header_details.setVisibility(View.GONE);
        view_header_details.setVisibility(View.GONE);
        btn_header_application.setVisibility(View.GONE);
        rlt_header.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        btn_header_application_create.setVisibility(View.GONE);
    }

    public void btn_setup_clicked(View view) {
        Constant.VALUE="users";
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
        Constant.VALUE="users";
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = false;
        setUpFooter(USER_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_USER_ACTIVTY, null);
        // checkToGetTicket(false);
        title.setText("Users Activity");
        rlt_header.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        btn_header_application_create.setVisibility(View.GONE);
        btn_header_application.setVisibility(View.GONE);
        rlt_header_details.setVisibility(View.GONE);
        view_header_details.setVisibility(View.GONE);
        btn_header_sync.setVisibility(View.VISIBLE);
        Constant.SYNC = "UserActivitY";
    }

    public void btn_more_clicked(View view) {
        Constant.VALUE="users";
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = false;
        setUpFooter(MORE_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_MORE, null);
        // checkToGetTicket(false);

        btn_header_application.setVisibility(View.GONE);
        btn_header_application_create.setVisibility(View.GONE);
        if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
            title.setText("Myself");
            tv_more_menu.setText("Myself");
            Constant.SYNC = "Status";
            btn_footer_more.setImageResource(R.drawable.img_footer_setup_selector);
            btn_header_sync.setVisibility(View.VISIBLE);
            rlt_header_details.setVisibility(View.GONE);
            view_header_details.setVisibility(View.GONE);
            rlt_header.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
        } else {
            title.setText("MORE");
            tv_more_menu.setText("MORE");
            btn_header_sync.setVisibility(View.GONE);
            btn_footer_more.setImageResource(R.drawable.img_footer_more_selector);
            rlt_header_details.setVisibility(View.GONE);
            view_header_details.setVisibility(View.GONE);
            rlt_header.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);


        }
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
//        int count = mFragManager.getBackStackEntryCount();
//        if (count != 0) {
//            //this will clear the back stack and displays no animation on the screen
//            mFragManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        }


        // check current fragment is wanted fragment
//        if (mCurrentFrag != null && mCurrentFrag.getTag() != null && mCurrentFrag.getTag().equals(String.valueOf(fragId))) {
//            return;
//        }

        Fragment newFrag = null;
        // identify which fragment will be called
        switch (fragId) {
            case Constant.FRAG_HOME:
                if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                   // newFrag = new DashboardFragment();
                } else {
                    Constant.SYNC = "UserActivitY";
                    newFrag = new PunchFragment();

                }
                break;
            case Constant.FRAG_SET_UP:
                Toast.makeText(mContext, "Under Construction", Toast.LENGTH_SHORT).show();
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
                if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {

                    newFrag = new HomeFragment();
                } else {

                    newFrag = new MoreFragment();

                }

                // newFrag = new ChatCategoryFragment();
                //setUpHeader(Constant.FRAG_CHAT);

                break;
            case Constant.FRAG_SET_UP_USER:
                if (SharedPreferenceUtil.getAdmin(MainActivity.this).equals("1")) {
                    newFrag = new DashboardFragment();
                } else {

                    newFrag = new HomeFragment();

                }

                // newFrag = new ChatCategoryFragment();
                //setUpHeader(Constant.FRAG_CHAT);

                break;

            default:
                break;
        }


        // param 1: container id, param 2: new fragment, param 3: fragment id
        fragTransaction.add(R.id.main_container, newFrag,  newFrag.getClass().getSimpleName());
        // prevent showed when user press back fabReview
        fragTransaction.addToBackStack(newFrag.getClass().getSimpleName());
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
