package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import xact.idea.attendancesystem.Activity.LoginActivity;
import xact.idea.attendancesystem.Activity.MainActivity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.Utils;


public class MoreFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    RelativeLayout relativelayout;
    RelativeLayout relativelayout1;
    RelativeLayout relativelayoutPunch;

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
                punchDetailsFragmnett();

            }
        });
        return view;
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

}
