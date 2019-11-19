package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.internal.Util;
import xact.idea.attendancesystem.Database.DataSources.DepartmentRepository;
import xact.idea.attendancesystem.Database.DataSources.SetUpDataRepository;
import xact.idea.attendancesystem.Database.DataSources.UnitRepository;
import xact.idea.attendancesystem.Database.DataSources.UserActivityRepository;
import xact.idea.attendancesystem.Database.DataSources.UserListRepository;
import xact.idea.attendancesystem.Database.Local.DepartmentDataSource;
import xact.idea.attendancesystem.Database.Local.MainDatabase;
import xact.idea.attendancesystem.Database.Local.SetUpDataSource;
import xact.idea.attendancesystem.Database.Local.UnitDataSource;
import xact.idea.attendancesystem.Database.Local.UserActivityDataSource;
import xact.idea.attendancesystem.Database.Local.UserListDataSource;
import xact.idea.attendancesystem.Database.Model.Department;
import xact.idea.attendancesystem.Database.Model.SetUp;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Database.Model.UserList;
import xact.idea.attendancesystem.Entity.AllUserListEntity;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.SetUpDataEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Reciver.MyReceiver;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.Constant;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.SharedPreferenceUtil;
import xact.idea.attendancesystem.Utils.Utils;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;

public class SpalashActivity extends AppCompatActivity {
    private Context mContext = null;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MyReceiver myReceiver= new  MyReceiver();

    //   private static final String BASE_URL="http://192.168.1.244:8888/drinkshop/";

    IRetrofitApi mServiceXact;
    RelativeLayout rlt_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash);


        mContext = this;
        CorrectSizeUtil.getInstance(this).correctSize();
        CorrectSizeUtil.getInstance(this).correctSize(findViewById(R.id.rlt_root));
        rlt_root=findViewById(R.id.rlt_root);
        initDB();

        mServiceXact=Common.getApiXact();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Common.userListRepository.size()>0 ||Common.unitRepository.size()>0 ||Common.departmentRepository.size()>0 ){
                    //loadUnitItems();
                    goNextScreen();

                   // broadcastIntent();
                }
                else {
                    if (Utils.broadcastIntent(SpalashActivity.this, rlt_root)){
                        //Toast.makeText(mContext, "Connected ", Toast.LENGTH_SHORT).show();
                        getUserData();
                        DepartmentData();
                        unitListData();
                        setUpData();
                        goNextScreen();
                    }
                    else {
                        Snackbar snackbar = Snackbar
                                .make(rlt_root, "No Internet", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                }


            }
        }, 200);
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  goNextScreen();
    }

    private void goNextScreen() {
        if (SharedPreferenceUtil.getUserID(SpalashActivity.this).equals("") || SharedPreferenceUtil.getUserID(SpalashActivity.this)==null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    goToLoginPage();
                }
            }, Constant.SPLASH_TIME);
        } else if (SharedPreferenceUtil.getUserID(SpalashActivity.this).equals("1")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    goToLoginPage();
                }
            }, Constant.SPLASH_TIME);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToMainPage();
                }
            }, Constant.SPLASH_TIME);
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                goToLoginPage();
//            }
//        }, Constant.SPLASH_TIME);
    }

    private void goToLoginPage() {

        Intent i = new Intent(SpalashActivity.this, OnBoardingActivity.class);
        startActivity(i);
        finish();
    }
    private void goToMainPage() {

        Intent i = new Intent(SpalashActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    private void initDB() {
        Common.mainDatabase = MainDatabase.getInstance(this);
        Common.departmentRepository = DepartmentRepository.getInstance(DepartmentDataSource.getInstance(Common.mainDatabase.departmentDao()));
        Common.unitRepository = UnitRepository.getInstance(UnitDataSource.getInstance(Common.mainDatabase.unitDao()));
        Common.setUpDataRepository = SetUpDataRepository.getInstance(SetUpDataSource.getInstance(Common.mainDatabase.setUpDao()));
        Common.userListRepository = UserListRepository.getInstance(UserListDataSource.getInstance(Common.mainDatabase.userListDao()));
        Common.userActivityRepository = UserActivityRepository.getInstance(UserActivityDataSource.getInstance(Common.mainDatabase.userActivityDao()));

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
    private void DepartmentData(){

        showLoadingProgress(this);
        compositeDisposable.add(mServiceXact.getDepartmentList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<DepartmentListEntity>() {
            @Override
            public void accept(DepartmentListEntity carts) throws Exception {
                // departmentListEntityList=carts;
                Department department = new Department();

                for (DepartmentListEntity.Data departmentListEntity: carts.data){
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

        compositeDisposable.add(mServiceXact.getUnitList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<UnitListEntity>() {
            @Override
            public void accept(UnitListEntity unitListEntities) throws Exception {
                Unit unit = new Unit();

                for (UnitListEntity.Data unitList: unitListEntities.data){

                    unit.Id=unitList.Id;
                    unit.UnitName=unitList.UnitName;
                    unit.ShortName=unitList.ShortName;
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
    private void setUpData(){
        showLoadingProgress(this);

        compositeDisposable.add(mServiceXact.getSetUpData().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<SetUpDataEntity>() {
            @Override
            public void accept(SetUpDataEntity unitListEntities) throws Exception {
                SetUp setUp = new SetUp();

               setUp.EXPECTED_DURATION=unitListEntities.data.EXPECTED_DURATION;
               setUp.OFFICE_IN_TIME=unitListEntities.data.OFFICE_IN_TIME;
               setUp.OFFICE_OUT_TIME=unitListEntities.data.OFFICE_OUT_TIME;
               setUp.GRACE_TIME=unitListEntities.data.GRACE_TIME;
               setUp.HALFDAY_DURATION=unitListEntities.data.HALFDAY_DURATION;
               setUp.ENTITLED_LEAVE_CASUAL=unitListEntities.data.ENTITLED_LEAVE_CASUAL;
               setUp.ENTITLED_LEAVE_SICK=unitListEntities.data.ENTITLED_LEAVE_SICK;
               setUp.ENTITLED_LEAVE_TOTAL=unitListEntities.data.ENTITLED_LEAVE_TOTAL;
               Common.setUpDataRepository.insertToSetUp(setUp);

                dismissLoadingProgress();
                //   progressBar.setVisibility(View.GONE);

//                unitListEntityArrayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, unitListEntityList);
//                unitListEntityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerUnit.setAdapter(unitListEntityArrayAdapter);
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
