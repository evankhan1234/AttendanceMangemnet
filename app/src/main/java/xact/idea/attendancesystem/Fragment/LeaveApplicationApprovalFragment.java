package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Adapter.LeaveApprovalAdapter;
import xact.idea.attendancesystem.Adapter.LeaveSummaryListAdapter;
import xact.idea.attendancesystem.Adapter.PunchInAdapter;
import xact.idea.attendancesystem.Entity.LeaveApprovalListEntity;
import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.Constant;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;


public class LeaveApplicationApprovalFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    View mRootView;
    RecyclerView rcl_approval_in_list;
    LeaveApprovalAdapter mAdapters;
    ArrayList<LeaveApprovalListEntity> arrayList = new ArrayList<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    RadioButton radioNew;
    RadioButton radioApproved;
    RadioButton radioPending;
    RadioButton radioDenied;
    RadioButton radioDeleted;
    RadioButton radioAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         mRootView=inflater.inflate(R.layout.fragment_leave_application_approval, container, false);
        mActivity=getActivity();
        correctSizeUtil= correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(mRootView);
        return mRootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        initView();
        load("");
    }

    private void initView() {
        mService = Common.getApi();
        rcl_approval_in_list=mRootView.findViewById(R.id.rcl_approval_in_list);
        radioNew=mRootView.findViewById(R.id.radioNew);
        radioApproved=mRootView.findViewById(R.id.radioApproved);
        radioPending=mRootView.findViewById(R.id.radioPending);
        radioDenied=mRootView.findViewById(R.id.radioDenied);
        radioDeleted=mRootView.findViewById(R.id.radioDeleted);
        radioAll=mRootView.findViewById(R.id.radioAll);
        LinearLayoutManager lm = new LinearLayoutManager(mActivity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcl_approval_in_list.setLayoutManager(lm);
        radioNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load("NEW");
              //  Constant.LEAVE="NEW";
            }
        });
        radioApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load("APPROVED");
                //Constant.LEAVE="APPROVED";
            }
        });
        radioPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load("PENDING");
             //   Constant.LEAVE="PENDING";
            }
        });
        radioDenied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load("DENIED");
                //Constant.LEAVE="DENIED";
            }
        });
        radioDeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load("DELETED");
                //Constant.LEAVE="DELETED";
            }
        });
        radioAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load("ALL");
               // Constant.LEAVE="ALL";
            }
        });
//        for(int j = 0; j < 5; j++){
//
//            arrayList.add("House "+j);
//        }
//        mAdapters = new LeaveApprovalAdapter(mActivity, arrayList);
//
//        rcl_approval_in_list.setAdapter(mAdapters);
    }
    private void load(final String value) {
        showLoadingProgress(mActivity);
        arrayList.clear();
        compositeDisposable.add(mService.getLeaveApproval().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<LeaveApprovalListEntity>>() {
            @Override
            public void accept(ArrayList<LeaveApprovalListEntity> carts) throws Exception {

                for (LeaveApprovalListEntity leaveApprovalListEntitys : carts) {

                    String test = leaveApprovalListEntitys.ApplicationDate;
                    LeaveApprovalListEntity leaveApprovalListEntity = new LeaveApprovalListEntity();
                    Log.e("test", "test" + test);
                    leaveApprovalListEntity.ApplicationDate = leaveApprovalListEntitys.ApplicationDate;
                    // Toast.makeText(mActivity, leaveApprovalListEntity.ApplicationDate, Toast.LENGTH_SHORT).show();
                    leaveApprovalListEntity.BackUp = leaveApprovalListEntitys.BackUp;
                    leaveApprovalListEntity.FullName = leaveApprovalListEntitys.FullName;
                    leaveApprovalListEntity.LeaveType = leaveApprovalListEntitys.LeaveType;
                    leaveApprovalListEntity.UserIcon = leaveApprovalListEntitys.UserIcon;
                    leaveApprovalListEntity.Reason = leaveApprovalListEntitys.Reason;
                    leaveApprovalListEntity.Type = value;
                    arrayList.add(leaveApprovalListEntity);
                }

                mAdapters = new LeaveApprovalAdapter(mActivity, arrayList);

                rcl_approval_in_list.setAdapter(mAdapters);
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
    public int handleBackPress() {

        Log.e("evan","evan"+getFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName()));
        if (getFragmentManager().findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName()) != null) {
            LeaveApplicationApprovalFragment f = (LeaveApplicationApprovalFragment) getFragmentManager()
                    .findFragmentByTag(LeaveApplicationApprovalFragment.class.getSimpleName());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
            transaction.remove(f);
            transaction.commit();
            getFragmentManager().popBackStack();


            return 2;

        }

        return 2;

    }
    public int  leaveApplication(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment f = new LeaveFragment();

        Log.e("test1", "test1" + f.getClass().getSimpleName());
        //String test = f.getClass().getSimpleName();

        // f.setArguments(bundle);
        transaction.setCustomAnimations(R.anim.right_to_left, R.anim.stand_by, R.anim.stand_by, R.anim.left_to_right);
        transaction.add(R.id.rlt_detail_fragment, f, f.getClass().getSimpleName());
        transaction.addToBackStack(f.getClass().getSimpleName());
        transaction.commit();
        return 2;
    }
}
