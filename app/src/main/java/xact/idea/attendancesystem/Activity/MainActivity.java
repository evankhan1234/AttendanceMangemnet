package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Database.DataSources.DepartmentRepository;
import xact.idea.attendancesystem.Database.DataSources.UnitRepository;
import xact.idea.attendancesystem.Database.Local.DepartmentDataSource;
import xact.idea.attendancesystem.Database.Local.MainDatabase;
import xact.idea.attendancesystem.Database.Local.UnitDataSource;
import xact.idea.attendancesystem.Database.Model.Department;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
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
    private ImageButton btn_header_application_create;
    private LinearLayout linear;
    private RelativeLayout relative;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear=findViewById(R.id.linear);
        mService=Common.getApi();
        btn_footer_setup_user=findViewById(R.id.btn_footer_setup_user);
        tv_user_setup_menus=findViewById(R.id.tv_user_setup_menus);
        relative=findViewById(R.id.relative);
        btn_header_application=findViewById(R.id.btn_header_application);
        btn_header_application_create=findViewById(R.id.btn_header_application_create);
        title=findViewById(R.id.title);
        btn_header_back_=findViewById(R.id.btn_header_back_);
        view_header_details=findViewById(R.id.view_header_details);
        details_title=findViewById(R.id.details_title);
        btn_footer_home=findViewById(R.id.btn_footer_home);
        btn_footer_setUp=findViewById(R.id.btn_footer_setup);
        btn_footer_user_activity=findViewById(R.id.btn_footer_user);
        btn_footer_more=findViewById(R.id.btn_footer_more);
        rlt_header=findViewById(R.id.rlt_header);
        rlt_header_details=findViewById(R.id.rlt_header_details);
        tv_home_menu=findViewById(R.id.tv_home_menu);
        tv_setup_menu=findViewById(R.id.tv_setup_menus);
        tv_user_activity_menu=findViewById(R.id.tv_home_menu);
        tv_more_menu=findViewById(R.id.tv_more_menus);
        mContext = this;
        CorrectSizeUtil.getInstance(this).correctSize();
        CorrectSizeUtil.getInstance(this).correctSize(findViewById(R.id.rlt_root));
        afterClickTabItem(Constant.FRAG_HOME, null);
        btn_footer_home.setSelected(true);
        tv_home_menu.setSelected(true);
        btn_header_back_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onBack();
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
        if (SharedPreferenceUtil.getUserID(MainActivity.this).equals("evankhan1234@gmail.com")){

        }
        else{
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
        DepartmentData();
        unitListData();
    }
    private void initDB() {
        Common.mainDatabase = MainDatabase.getInstance(this);
        Common.departmentRepository = DepartmentRepository.getInstance(DepartmentDataSource.getInstance(Common.mainDatabase.departmentDao()));
        Common.unitRepository = UnitRepository.getInstance(UnitDataSource.getInstance(Common.mainDatabase.unitDao()));

    }
    private void DepartmentData(){

        showLoadingProgress(this);
        compositeDisposable.add(mService.getDepartmentList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<DepartmentListEntity>>() {
            @Override
            public void accept(ArrayList<DepartmentListEntity> carts) throws Exception {
                // departmentListEntityList=carts;
                Department department = new Department();

                for (DepartmentListEntity departmentListEntity:carts){
                    department.Id=departmentListEntity.Id;
                    department.DepartmentName=departmentListEntity.DepartmentName;
                    department.UnitId=departmentListEntity.UnitId;
                    Common.departmentRepository.insertToDepartment(department);
                }
                dismissLoadingProgress();
                //   progressBar.setVisibility(View.GONE);
            }
        }));


    }
    private void unitListData(){
        showLoadingProgress(this);

        compositeDisposable.add(mService.getUnitList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<UnitListEntity>>() {
            @Override
            public void accept(ArrayList<UnitListEntity> unitListEntities) throws Exception {
                Unit unit = new Unit();
                for (UnitListEntity unitListEntity:unitListEntities){
                    unit.Id=unitListEntity.Id;
                    unit.UnitName=unitListEntity.UnitName;
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
    private void onBackForPunch(){
        Fragment f = getVisibleFragment();
        Log.e("frag","frag"+f);
        if (f != null)
        {
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
    private void onBack(){
        Fragment f = getVisibleFragment();
        Log.e("frag","frag"+f);
        if (f != null)
        {
            if (f instanceof MoreFragment) {

                if (f instanceof PunchInFragment){
                    Toast.makeText(mContext, "true", Toast.LENGTH_SHORT).show();
                }
                int handle = ((MoreFragment) f).handleBackPress(1);
                if (handle == 0) {
                    finish();
                }
                else if (handle == 1) {
                    int handles = ((MoreFragment) f).handleBackPress(2);
                    if (handles==2){
                        hideHeaderDetail();
                    }

                } else if (handle == 2) {
                    hideHeaderDetail();
                } else {
                    // do not hide header
                }

            }
            else if (f instanceof LeaveFragment){
                int handle = ((LeaveFragment) f).handleBackPress();
                if (handle == 0) {
                    finish();
                } else if (handle == 2) {
                    hideHeaderDetail();
                } else {
                    // do not hide header
                }
            }
            else if (f instanceof LeaveApplicationFragment){
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

            }
            else if (getSupportFragmentManager().findFragmentByTag(AboutUsFragment.class.getSimpleName()) != null) {
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
        if (name.equals("Approval")){
            rlt_header.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            //card_view.setVisibility(View.GONE);
            view_header_details.setVisibility(View.VISIBLE);
            rlt_header_details.setVisibility(View.VISIBLE);
            details_title.setText("Leave Summary");
            btn_header_application_create.setVisibility(View.VISIBLE);
        }
        else if (name.equals("Application")){
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

    }
    public void btn_setup_user_clicked(View view) {
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = true;
        setUpFooter(SET_UP_USER_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_SET_UP_USER, null);
        // checkToGetTicket(false);
        title.setText("Status");
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


    }
    public void btn_user_clicked(View view) {
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = false;
        setUpFooter(USER_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_USER_ACTIVTY, null);
        // checkToGetTicket(false);
        title.setText("User Activity");
        btn_header_application_create.setVisibility(View.GONE);
        btn_header_application.setVisibility(View.GONE);
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
    }
    public void onLeaveApplication(){

        Fragment f = getVisibleFragment();
        Log.e("frag","frag"+f);
        if (f != null)
        {
            if (f instanceof LeaveFragment) {

                if (SharedPreferenceUtil.getUserID(MainActivity.this).equals("evankhan1234@gmail.com")){
                   // newFrag = new SetUpFragment();

                    int handle = ((LeaveFragment) f).leaveApproval();
                    if (handle == 0) {
                        finish();
                    } else if (handle == 2) {
                        hideHeaderDetailForLeave("Application");
                    }
                    else {
                        // do not hide header
                    }
                }
                else{

                    int handle = ((LeaveFragment) f).leaveApplication();
                    if (handle == 0) {
                        finish();
                    } else if (handle == 2) {
                        hideHeaderDetailForLeave("Application");
                    }
                    else {
                        // do not hide header
                    }
                }


            }

        } else {
            // finish();
        }
    }
    public void onLeaveApplicationCreate(){

        Fragment f = getVisibleFragment();
        Log.e("frag","frag"+f);
        if (f != null)
        {
            if (f instanceof LeaveApplicationApprovalFragment) {
                int handle = ((LeaveApplicationApprovalFragment) f).leaveApplication();
                if (handle == 0) {
                    finish();
                } else if (handle == 2) {
                    hideHeaderDetailForLeave("Approval");
                }
                else {
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
                if (SharedPreferenceUtil.getUserID(MainActivity.this).equals("evankhan1234@gmail.com")){
                    newFrag = new DashboardFragment();
                }
              else{
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

        if (titles.equals("no"))
        {
            rlt_header.setVisibility(View.VISIBLE);
            // / rlt_header_details.setVisibility(View.VISIBLE);
            //view_header_details.setVisibility(View.VISIBLE);

            title.setVisibility(View.VISIBLE);
        }
       else  if (titles.equals("test"))
        {
            rlt_header.setVisibility(View.GONE);
          rlt_header_details.setVisibility(View.GONE);
            view_header_details.setVisibility(View.GONE);
            btn_header_application_create.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
        }
        else if (titles.equals("rrr")){
            rlt_header.setVisibility(View.GONE);
            rlt_header_details.setVisibility(View.VISIBLE);
            view_header_details.setVisibility(View.VISIBLE);
            btn_header_application_create.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
        }






    }
    public void ShowText(String name)
    {
        details_title.setText(name);
    }

}
