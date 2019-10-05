package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Activity.MainActivity;
import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.Entity.UserDetailsEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;


public class ProfileDetailsFragment extends Fragment {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    ImageButton imageViewHeaderBack;
    LinearLayout cover;
    CircleImageView img_avatar;
    EditText edit_name;
    EditText edit_gender;
    EditText edit_dob;
    EditText edit_email;
    EditText edit_phone;
    EditText edit_address;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_details, container, false);
        mActivity=getActivity();
        correctSizeUtil= correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(view);
        img_avatar=view.findViewById(R.id.img_avatar);
        edit_name=view.findViewById(R.id.edit_name);
        edit_gender=view.findViewById(R.id.edit_gender);
        edit_dob=view.findViewById(R.id.edit_dob);
        edit_email=view.findViewById(R.id.edit_email);
        edit_phone=view.findViewById(R.id.edit_phone);
        edit_address=view.findViewById(R.id.edit_address);
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
        mService = Common.getApi();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        showLoadingProgress(mActivity);
        compositeDisposable.add(mService.getProfileDetails().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<UserDetailsEntity>() {
            @Override
            public void accept(UserDetailsEntity userDetailsEntity) throws Exception {
                Glide.with(mActivity).load(userDetailsEntity.IconPath).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.backwhite)
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                img_avatar.setImageDrawable(resource);
                            }
                        });
                edit_name.setText(userDetailsEntity.Fullname);
                edit_dob.setText(userDetailsEntity.DateOfBirth);
                edit_address.setText(userDetailsEntity.Address);
                edit_email.setText(userDetailsEntity.Email);
                edit_phone.setText(userDetailsEntity.Phone);
                edit_gender.setText(userDetailsEntity.Gender);
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

}
