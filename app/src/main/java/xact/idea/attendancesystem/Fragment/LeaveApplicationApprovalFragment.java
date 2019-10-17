package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;


public class LeaveApplicationApprovalFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    View mRootView;
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
