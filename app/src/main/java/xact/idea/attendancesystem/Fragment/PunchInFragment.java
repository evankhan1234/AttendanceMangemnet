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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Adapter.PunchInAdapter;
import xact.idea.attendancesystem.Adapter.UnitDepartmentAdapter;
import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.Entity.UserListEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;


public class PunchInFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    private View mRoot;
    RecyclerView rcl_punch_in_list;
    ArrayList<String> arrayList = new ArrayList<>();
    PunchInAdapter mAdapters;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
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

        mService = Common.getApi();
        for(int j = 0; j < 20; j++){

            arrayList.add("House "+j);
        }


      //  mAdapters = new PunchInAdapter(mActivity, arrayList);

        rcl_punch_in_list.setAdapter(mAdapters);
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        showLoadingProgress(mActivity);
        compositeDisposable.add(mService.getUserActivity().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<UserActivityEntity>>() {
            @Override
            public void accept(ArrayList<UserActivityEntity> carts) throws Exception {

                mAdapters = new PunchInAdapter(mActivity, carts);

                rcl_punch_in_list.setAdapter(mAdapters);
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
