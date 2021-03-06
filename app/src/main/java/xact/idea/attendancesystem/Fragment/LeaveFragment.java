package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Adapter.LeaveApprovalAdapter;
import xact.idea.attendancesystem.Adapter.LeaveSummaryListAdapter;
import xact.idea.attendancesystem.Adapter.PunchInAdapter;
import xact.idea.attendancesystem.Database.Model.EntityLeave;
import xact.idea.attendancesystem.Database.Model.LeaveSummary;
import xact.idea.attendancesystem.Database.Model.RemainingLeave;
import xact.idea.attendancesystem.Entity.LeaveApprovalListEntity;
import xact.idea.attendancesystem.Entity.LeaveEntity;
import xact.idea.attendancesystem.Entity.LeaveSummaryEntity;
import xact.idea.attendancesystem.Entity.UserTotalLeaveEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;


public class LeaveFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;

    View mRootView;
    LeaveSummaryListAdapter mAdapters;
    RecyclerView rcl_leave_summary_list;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    TextView text_casual_leave;
    TextView text_sick_leave;
    TextView text_total_leave;
    static List<LeaveSummary> leaveSummaries= new ArrayList<>();
    static List<EntityLeave> entityLeaves= new ArrayList<>();
  static   List<RemainingLeave> remainingLeaves= new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView= inflater.inflate(R.layout.fragment_leave, container, false);
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
        loadLeaveItems();

        loadLeaveTotal();
    }

    private void initView() {
        mService = Common.getApi();
        rcl_leave_summary_list=mRootView.findViewById(R.id.rcl_leave_summary_list);
        text_casual_leave=mRootView.findViewById(R.id.text_casual_leave);
        text_sick_leave=mRootView.findViewById(R.id.text_sick_leave);
        text_total_leave=mRootView.findViewById(R.id.text_total_leave);
        LinearLayoutManager lm = new LinearLayoutManager(mActivity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcl_leave_summary_list.setLayoutManager(lm);
//        for(int j = 0; j < 5; j++){
//
//            arrayList.add("House "+j);
//        }
//        mAdapters = new LeaveSummaryListAdapter(mActivity, arrayList);
//
//        rcl_leave_summary_list.setAdapter(mAdapters);
    }
    private void loadLeaveItems() {
        showLoadingProgress(mActivity);
        compositeDisposable.add(Common.leaveSummaryRepository.getLeaveSummary().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<LeaveEntity>>() {
            @Override
            public void accept(List<LeaveEntity> units) throws Exception {
                //displayUnitItems(units);
               // leaveSummaries=units;
                load(units);
                Gson gson= new Gson();
                Log.e("LeaveEntity","LeaveEntity"+gson.toJson(units));
                dismissLoadingProgress();
            }
        }));


    }
    private void load(List<LeaveEntity> units) {
        mAdapters = new LeaveSummaryListAdapter(mActivity, units);

        rcl_leave_summary_list.setAdapter(mAdapters);


    }
    private void loadLeaveTotal() {
        showLoadingProgress(mActivity);
        compositeDisposable.add(mService.getTotalLeave().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<UserTotalLeaveEntity>() {
            @Override
            public void accept(UserTotalLeaveEntity carts) throws Exception {
                text_total_leave.setText(String.valueOf(carts.Total));
                text_sick_leave.setText(String.valueOf(carts.Sick));
                text_casual_leave.setText(String.valueOf(carts.Casual));

                dismissLoadingProgress();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dismissLoadingProgress();
            }
        }));


    }
    public int handleBackPress() {
        if (getFragmentManager().findFragmentByTag(LeaveFragment.class.getSimpleName()) != null) {
            LeaveFragment f = (LeaveFragment) getFragmentManager()
                    .findFragmentByTag(LeaveFragment.class.getSimpleName());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
            transaction.remove(f);
            transaction.commit();
            getFragmentManager().popBackStack();


            return 2;

        }
        return 2;
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
    public int  leaveApproval(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment f = new LeaveApplicationFragment();

        Log.e("test1", "test1" + f.getClass().getSimpleName());
        //String test = f.getClass().getSimpleName();

       // f.setArguments(bundle);
        transaction.setCustomAnimations(R.anim.right_to_left, R.anim.stand_by, R.anim.stand_by, R.anim.left_to_right);
        transaction.add(R.id.rlt_detail_fragment, f, f.getClass().getSimpleName());
        transaction.addToBackStack(f.getClass().getSimpleName());
        transaction.commit();
        return 2;
    }
    public int  leaveApplication(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment f = new LeaveApplicationFragment();

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
