package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import xact.idea.attendancesystem.Activity.MainActivity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;


public class ProfileDetailsFragment extends Fragment {

    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    ImageButton imageViewHeaderBack;
    LinearLayout cover;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_details, container, false);
        mActivity=getActivity();
        correctSizeUtil= correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(view);
        cover=view.findViewById(R.id.cover);
        imageViewHeaderBack=view.findViewById(R.id.btn_header_back);
        imageViewHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = getFragmentManager().findFragmentByTag(ProfileDetailsFragment.class.getSimpleName());
                if (f != null) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
                    transaction.remove(f);
                    transaction.commit();
                    getChildFragmentManager().popBackStack();
                     ((MainActivity) getActivity()).showHeaderDetail("no");
//                    Log.e("22","22");
//
//                    Log.e("CONTACT_ADMIN","call");

                } else {

                }
            }
        });
        Glide.with(mActivity).load("https://images.pexels.com/photos/212324/pexels-photo-212324.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(mActivity.getResources(), resource);
                cover.setBackground(drawable);
            }
        });
        return view;
    }

}
