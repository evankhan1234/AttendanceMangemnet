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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import xact.idea.attendancesystem.Adapter.LeaveApprovalAdapter;
import xact.idea.attendancesystem.Adapter.LeaveSummaryListAdapter;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;


public class LeaveApplicationApprovalFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    View mRootView;
    RecyclerView rcl_approval_in_list;
    LeaveApprovalAdapter mAdapters;
    ArrayList<String> arrayList = new ArrayList<>();
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
    }

    private void initView() {
        rcl_approval_in_list=mRootView.findViewById(R.id.rcl_approval_in_list);
        LinearLayoutManager lm = new LinearLayoutManager(mActivity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcl_approval_in_list.setLayoutManager(lm);
        for(int j = 0; j < 5; j++){

            arrayList.add("House "+j);
        }
        mAdapters = new LeaveApprovalAdapter(mActivity, arrayList);

        rcl_approval_in_list.setAdapter(mAdapters);
    }
    public int handleBackPress() {
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

}
