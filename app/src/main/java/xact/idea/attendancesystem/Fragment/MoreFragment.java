package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Activity.LoginActivity;
import xact.idea.attendancesystem.Activity.MainActivity;
import xact.idea.attendancesystem.Database.DataSources.DepartmentRepository;
import xact.idea.attendancesystem.Database.DataSources.UnitRepository;
import xact.idea.attendancesystem.Database.DataSources.UserActivityRepository;
import xact.idea.attendancesystem.Database.DataSources.UserListRepository;
import xact.idea.attendancesystem.Database.Local.DepartmentDataSource;
import xact.idea.attendancesystem.Database.Local.MainDatabase;
import xact.idea.attendancesystem.Database.Local.UnitDataSource;
import xact.idea.attendancesystem.Database.Local.UserActivityDataSource;
import xact.idea.attendancesystem.Database.Local.UserListDataSource;
import xact.idea.attendancesystem.Database.Model.Department;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Database.Model.UserList;
import xact.idea.attendancesystem.Entity.AllUserListEntity;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Entity.UserActivityListEntity;
import xact.idea.attendancesystem.Entity.UserActivityPostEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.Utils;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;


public class MoreFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    RelativeLayout relativelayout;
    RelativeLayout relativelayout1;
    RelativeLayout relativelayoutPunch;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    IRetrofitApi mServiceXact;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_more, container, false);
        mActivity=getActivity();
        correctSizeUtil= correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(view);
        relativelayoutPunch=view.findViewById(R.id.relativelayoutPunch);
        relativelayout=view.findViewById(R.id.relativelayout);
        relativelayout1=view.findViewById(R.id.relativelayout1);
        mServiceXact=Common.getApiXact();
        relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showInfoDialog(mActivity);

            }
        });
        relativelayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                momentDetailsFragmnett();

            }
        });
        relativelayoutPunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.userActivityRepository.emptyCart();
                Common.departmentRepository.emptyCart();
                Common.unitRepository.emptyCart();
                Common.userListRepository.emptyCart();
                Common.userActivityRepository.emptyCart();

                getUserData();
                DepartmentData();
                unitListData();
                UserActivityData();

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initDB();
    }
    private void UserActivityData() {

        showLoadingProgress(mActivity);
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
    public void momentDetailsFragmnett() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        Fragment f = new AboutUsFragment();

        transaction.setCustomAnimations(R.anim.right_to_left, R.anim.stand_by, R.anim.stand_by, R.anim.left_to_right);
        transaction.add(R.id.rlt_detail_fragment, f, f.getClass().getSimpleName());
        transaction.addToBackStack(null);
        transaction.commit();
//
       // ((MainActivity) getActivity()).showHeaderDetail("About Us");
        ((MainActivity) getActivity()).ShowText("About Us");
        ((MainActivity) getActivity()).showHeaderDetail("rrr");
        // disableNestedScroll();
    }
    public void punchDetailsFragmnett() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        Fragment f = new PunchFragment();

        transaction.setCustomAnimations(R.anim.right_to_left, R.anim.stand_by, R.anim.stand_by, R.anim.left_to_right);
        transaction.add(R.id.rlt_detail_fragment, f, f.getClass().getSimpleName());
       // transaction.addToBackStack(null);
        transaction.commit();
//
        // ((MainActivity) getActivity()).showHeaderDetail("About Us");
        ((MainActivity) getActivity()).ShowText("Punch");
        ((MainActivity) getActivity()).showHeaderDetail("rrr");
        // disableNestedScroll();
    }
    public int handleBackPress(int value) {
        if (value==1){
            if (getChildFragmentManager().findFragmentByTag(AboutUsFragment.class.getSimpleName()) != null) {
                AboutUsFragment f = (AboutUsFragment) getChildFragmentManager()
                        .findFragmentByTag(AboutUsFragment.class.getSimpleName());
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
                transaction.remove(f);
                transaction.commit();
                getChildFragmentManager().popBackStack();


                return 2;

            }
        }
        else if (value==2){

            if (getChildFragmentManager().findFragmentByTag(PunchFragment.class.getSimpleName()) != null) {
                PunchFragment f = (PunchFragment) getChildFragmentManager()
                        .findFragmentByTag(PunchFragment.class.getSimpleName());
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
                transaction.remove(f);
                transaction.commit();
                getChildFragmentManager().popBackStack();


                return 2;

            }
//           else  if (getChildFragmentManager().findFragmentByTag(AboutUsFragment.class.getSimpleName()) != null) {
//                PunchInFragment f = (PunchInFragment) getChildFragmentManager()
//                        .findFragmentByTag(PunchInFragment.class.getSimpleName());
//                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
//                transaction.remove(f);
//                transaction.commit();
//                getChildFragmentManager().popBackStack();
//
//
//                return 2;
//
//            }
        }


        return value;
    }
    private void initDB() {
        Common.mainDatabase = MainDatabase.getInstance(mActivity);
        Common.departmentRepository = DepartmentRepository.getInstance(DepartmentDataSource.getInstance(Common.mainDatabase.departmentDao()));
        Common.unitRepository = UnitRepository.getInstance(UnitDataSource.getInstance(Common.mainDatabase.unitDao()));
        Common.userListRepository = UserListRepository.getInstance(UserListDataSource.getInstance(Common.mainDatabase.userListDao()));
        Common.userActivityRepository = UserActivityRepository.getInstance(UserActivityDataSource.getInstance(Common.mainDatabase.userActivityDao()));

    }
    private void getUserData(){

        showLoadingProgress(mActivity);
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

        showLoadingProgress(mActivity);
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
        showLoadingProgress(mActivity);

        compositeDisposable.add(mServiceXact.getUnitList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<UnitListEntity>() {
            @Override
            public void accept(UnitListEntity unitListEntities) throws Exception {
                Unit unit = new Unit();

                for (UnitListEntity.Data unitList: unitListEntities.data){

                    unit.Id=unitList.Id;
                    unit.UnitName=unitList.UnitName;
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
