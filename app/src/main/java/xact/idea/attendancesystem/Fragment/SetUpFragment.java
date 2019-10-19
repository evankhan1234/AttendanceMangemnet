package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Activity.MainActivity;
import xact.idea.attendancesystem.Adapter.UnitDepartmentAdapter;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Entity.UserListEntity;
import xact.idea.attendancesystem.Interface.ClickInterface;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.DLog;
import xact.idea.attendancesystem.Utils.Utils;
import xact.idea.attendancesystem.ViewPager.HomePagerFragmentAdapter;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;


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
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    ArrayList<UserListEntity> userListEntities = new ArrayList<>();
    List<DepartmentListEntity> departmentListEntityList  = new ArrayList<>();;
    List<UnitListEntity> unitListEntityList  = new ArrayList<>();
    ArrayAdapter<UnitListEntity> unitListEntityArrayAdapter;
    ArrayAdapter<DepartmentListEntity> departmentListEntityArrayAdapter;
    Spinner spinnerDepartments;
    Spinner spinnerUnit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_set_up, container, false);
        mActivity = getActivity();
        correctSizeUtil = correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(mRoot);
        //   initView();
        mService = Common.getApi();
        spinnerDepartments = mRoot.findViewById(R.id.spinnerDepartments);
        spinnerUnit = mRoot.findViewById(R.id.spinnerUnit);
        btn_2 = mRoot.findViewById(R.id.btn_2);
        btn_1 = mRoot.findViewById(R.id.btn_1);
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
        rcl_this_place_list = mRoot.findViewById(R.id.rcl_this_place_list);

        LinearLayoutManager lm = new LinearLayoutManager(mActivity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcl_this_place_list.setLayoutManager(lm);


        for (int j = 0; j < 7; j++) {

            arrayList.add("House " + j);
        }


        unitListData();
        DepartmentListData();

        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    Log.e("sp_div", "" + position);
                    setDepartment(unitListEntityList.get(position).Id);
                } else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return mRoot;
    }



        private void setDepartment(final int unitId) {


        //sp_dis.setSelection(getDisIndex(((NewMemberActivity)mActivity).groupInfoMember.getDisId()),false);
        spinnerDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//if(position>0)
                {
                    //Toast.makeText(mActivity, String.valueOf(unitId)+String.valueOf(departmentListEntityList.get(position).Id), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();

    }


        private ClickInterface mClick = new ClickInterface() {
        @Override
        public void onItemClick(int position) {


            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putParcelable(PunchInFragment.class.getSimpleName(), userListEntities.get(position));
          //  bundle.putInt("position",pos);
            Fragment f = new PunchInFragment();
            f.setArguments(bundle);
            transaction.setCustomAnimations(R.anim.right_to_left, R.anim.stand_by, R.anim.stand_by, R.anim.left_to_right);
            transaction.add(R.id.rlt_detail_fragment, f, f.getClass().getSimpleName());
            transaction.addToBackStack(f.getClass().getSimpleName());
            transaction.commit();
            ((MainActivity) getActivity()).ShowText("Details");
            ((MainActivity) getActivity()).showHeaderDetail("rrr");


        }
    };

    private void loadData() {
        showLoadingProgress(mActivity);
        compositeDisposable.add(mService.getUser().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<UserListEntity>>() {
            @Override
            public void accept(ArrayList<UserListEntity> carts) throws Exception {
                userListEntities=carts;
                mAdapters = new UnitDepartmentAdapter(mActivity, carts,mClick);

                rcl_this_place_list.setAdapter(mAdapters);
                dismissLoadingProgress();
            }
        }));


    }
    private void unitListData(){

        compositeDisposable.add(mService.getUnitList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<UnitListEntity>>() {
            @Override
            public void accept(ArrayList<UnitListEntity> carts) throws Exception {
                unitListEntityList=carts;


                unitListEntityArrayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, unitListEntityList);
                unitListEntityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUnit.setAdapter(unitListEntityArrayAdapter);
            }
        }));
    }
    private void DepartmentListData(){

        compositeDisposable.add(mService.getDepartmentList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<DepartmentListEntity>>() {
            @Override
            public void accept(ArrayList<DepartmentListEntity> carts) throws Exception {
                departmentListEntityList=carts;
                departmentListEntityArrayAdapter = new ArrayAdapter<DepartmentListEntity>(getActivity(), android.R.layout.simple_spinner_item, departmentListEntityList);
                departmentListEntityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDepartments.setAdapter(departmentListEntityArrayAdapter);
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
