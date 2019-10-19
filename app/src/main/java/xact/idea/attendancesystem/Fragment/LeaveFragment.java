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

import java.util.ArrayList;

import xact.idea.attendancesystem.Adapter.LeaveSummaryListAdapter;
import xact.idea.attendancesystem.Adapter.PunchInAdapter;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;


public class LeaveFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    ArrayList<String> arrayList = new ArrayList<>();
    View mRootView;
    LeaveSummaryListAdapter mAdapters;
    RecyclerView rcl_leave_summary_list;
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
    }

    private void initView() {
        rcl_leave_summary_list=mRootView.findViewById(R.id.rcl_leave_summary_list);
        LinearLayoutManager lm = new LinearLayoutManager(mActivity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcl_leave_summary_list.setLayoutManager(lm);
        for(int j = 0; j < 5; j++){

            arrayList.add("House "+j);
        }
        mAdapters = new LeaveSummaryListAdapter(mActivity, arrayList);

        rcl_leave_summary_list.setAdapter(mAdapters);
    }
    public int  leaveApproval(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment f = new LeaveApplicationApprovalFragment();

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
