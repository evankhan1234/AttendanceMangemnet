package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;


public class OnboardingFragment extends Fragment {

    private static final String BACKGROUND_COLOR = "background_color";
    private static final String PAGE = "page";

    private int backgroundColor;
    private int page;

    private WebView webview;

    private Activity mActivity ;
    private TextView title;
    private TextView description;

    private String titleText;
    private String descriptionText;


    private String mContent = "???";
    private String mConten2 = "???";

    TextView intro;
    TextView intro_1_description;
    public static OnboardingFragment newInstance(int page,String content,String conten2) {
        OnboardingFragment fragment = new OnboardingFragment();
        fragment.mContent=content;
        fragment.mConten2=conten2;
        Bundle b = new Bundle();
        b.putInt(PAGE, page);

        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backgroundColor = getArguments().getInt(BACKGROUND_COLOR);
        page = getArguments().getInt(PAGE);

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = getActivity().getLayoutInflater().inflate(R.layout.fragment_onboarding, container, false);

        CorrectSizeUtil.getInstance(getActivity()).correctSize(root);
        // Set the background color of the root view to the color specified in newInstance()
        intro = (TextView) root.findViewById(R.id.intro);
        intro_1_description = (TextView) root.findViewById(R.id.intro_1_description);
        intro.setText(mContent);
        intro_1_description.setText(mConten2);
        // Set the current page index as the View's tag (used for PageTransformer)
        root.setTag(page);
        mActivity = getActivity();

        return root;
    }
}
