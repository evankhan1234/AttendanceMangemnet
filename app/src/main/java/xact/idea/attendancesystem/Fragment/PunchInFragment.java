package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xact.idea.attendancesystem.Adapter.PunchInAdapter;
import xact.idea.attendancesystem.Adapter.UnitDepartmentAdapter;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;


public class PunchInFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    private View mRoot;
    RecyclerView rcl_punch_in_list;
    ArrayList<String> arrayList = new ArrayList<>();
    PunchInAdapter mAdapters;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot= inflater.inflate(R.layout.fragment_unity, container, false);
        mActivity=getActivity();
        correctSizeUtil= correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(mRoot);
        rcl_punch_in_list=mRoot.findViewById(R.id.rcl_punch_in_list);
        LinearLayoutManager lm = new LinearLayoutManager(mActivity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcl_punch_in_list.setLayoutManager(lm);


        for(int j = 0; j < 20; j++){

            arrayList.add("House "+j);
        }


        mAdapters = new PunchInAdapter(mActivity, arrayList);

        rcl_punch_in_list.setAdapter(mAdapters);
        return mRoot;
    }

}
