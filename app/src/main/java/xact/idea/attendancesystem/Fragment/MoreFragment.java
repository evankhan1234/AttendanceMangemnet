package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_more, container, false);
        mActivity=getActivity();
        correctSizeUtil= correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(view);
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
    public int handleBackPress() {
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
        return 2;
    }
}
