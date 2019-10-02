package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import xact.idea.attendancesystem.Adapter.UnitDepartmentAdapter;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.Utils;
import xact.idea.attendancesystem.ViewPager.HomePagerFragmentAdapter;


public class SetUpFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    private View mRoot;
    private LinearLayout lnl_category;
    RelativeLayout rlt_fragment_root;
    public static RelativeLayout rlt_home_category;
    View homeCategoryBackground;
    View CategoryLineColor;
    int[] rainbow;
    ArrayList<String> arrayList = new ArrayList<>();
    private RecyclerView rcl_this_place_list;
    private UnitDepartmentAdapter mAdapters = null;
    Button btn_1;
    Button btn_2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot= inflater.inflate(R.layout.fragment_set_up, container, false);
        mActivity=getActivity();
        correctSizeUtil= correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(mRoot);
     //   initView();
        btn_2=mRoot.findViewById(R.id.btn_2);
        btn_1=mRoot.findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_1.setBackground(mActivity.getResources().getDrawable(R.drawable.round_button_green));
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_2.setBackground(mActivity.getResources().getDrawable(R.drawable.round_button_green));
            }
        });
        rcl_this_place_list=mRoot.findViewById(R.id.rcl_this_place_list);

        LinearLayoutManager lm = new LinearLayoutManager(mActivity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcl_this_place_list.setLayoutManager(lm);


        for(int j = 0; j < 7; j++){

            arrayList.add("House "+j);
        }


        mAdapters = new UnitDepartmentAdapter(mActivity, arrayList);

        rcl_this_place_list.setAdapter(mAdapters);

        return mRoot;
    }



}
