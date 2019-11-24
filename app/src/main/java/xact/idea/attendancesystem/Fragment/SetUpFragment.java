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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import xact.idea.attendancesystem.Adapter.DepartmentAdapter;
import xact.idea.attendancesystem.Adapter.UnitAdapter;
import xact.idea.attendancesystem.Adapter.UnitDepartmentAdapter;
import xact.idea.attendancesystem.Database.Model.Department;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Database.Model.UserList;
import xact.idea.attendancesystem.Entity.AttendanceEntity;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Entity.UserListEntity;
import xact.idea.attendancesystem.Interface.ClickInterface;
import xact.idea.attendancesystem.Interface.DepartmentClickInterface;
import xact.idea.attendancesystem.Interface.UnitClickInterface;
import xact.idea.attendancesystem.Interface.UserListClickInterface;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.Constant;
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
    private RecyclerView rcl_this_unit_list;
    private RecyclerView rcl_this_department_list;
    private UnitDepartmentAdapter mAdapters = null;
    private UnitAdapter mUnitAdapter = null;
    private DepartmentAdapter mDepartmentAdapter = null;
    int unitValue;
    int departmentValue;
    Button btn_1;
    Button btn_2;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    List<UserList> userListEntities = new ArrayList<>();
    List<DepartmentListEntity> departmentListEntityList  = new ArrayList<>();;
    List<Department> departmentListEntityLists  = new ArrayList<>();;
    List<UnitListEntity> unitListEntityList  = new ArrayList<>();
    List<Unit> unitListEntityLists  = new ArrayList<>();
    ArrayAdapter<UnitListEntity> unitListEntityArrayAdapter;
    ArrayAdapter<DepartmentListEntity> departmentListEntityArrayAdapter;
    Spinner spinnerDepartments;
    Spinner spinnerUnit;
    EditText edit_content;
    ProgressBar progress_bar;
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
//        spinnerDepartments = mRoot.findViewById(R.id.spinnerDepartments);
//        spinnerUnit = mRoot.findViewById(R.id.spinnerUnit);
        progress_bar = mRoot.findViewById(R.id.progress_bar);
        btn_2 = mRoot.findViewById(R.id.btn_2);
        btn_1 = mRoot.findViewById(R.id.btn_1);
        edit_content = mRoot.findViewById(R.id.edit_content);
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
        rcl_this_unit_list = mRoot.findViewById(R.id.rcl_this_unit_list);
        rcl_this_department_list = mRoot.findViewById(R.id.rcl_this_department_list);

        LinearLayoutManager lm = new LinearLayoutManager(mActivity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcl_this_place_list.setLayoutManager(lm);

        LinearLayoutManager lm1 = new LinearLayoutManager(mActivity);
        lm1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcl_this_unit_list.setLayoutManager(lm1);

        LinearLayoutManager lm2 = new LinearLayoutManager(mActivity);
        lm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcl_this_department_list.setLayoutManager(lm2);

        edit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAdapters.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        for (int j = 0; j < 7; j++) {

            arrayList.add("House " + j);
        }



//
//        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                if (position > 0) {
//                    Log.e("sp_div", "" + position);
//                    setDepartment(unitListEntityList.get(position).Id);
//                } else {
//
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        return mRoot;
    }



//        private void setDepartment(final int unitId) {
//
//
//        //sp_dis.setSelection(getDisIndex(((NewMemberActivity)mActivity).groupInfoMember.getDisId()),false);
//        spinnerDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////if(position>0)
//                {
//                    //Toast.makeText(mActivity, String.valueOf(unitId)+String.valueOf(departmentListEntityList.get(position).Id), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();
        loadUnitItems();
        loadDepartmentItems();
//        unitListData();
//        DepartmentListData();
    }

    private DepartmentClickInterface mClickDepartment = new DepartmentClickInterface() {
        @Override
        public void onItemClick(int position) {
            departmentValue=position;
            Data();

            //  Toast.makeText(mActivity, "Department "+departmentValue+"Unit"+unitValue, Toast.LENGTH_SHORT).show();
//           Common.departmentRepository.emptyCart();
//            DepartmentListData();
            // loadLeaveTotal();

        }
    };
    private UnitClickInterface mClickUnit = new UnitClickInterface() {
        @Override
        public void onItemClick(int position) {
            unitValue=position;
            Data();
            // Toast.makeText(mActivity, "Department "+departmentValue+"Unit"+unitValue, Toast.LENGTH_SHORT).show();
            //   loadLeaveTotal();


        }
    };
        private UserListClickInterface mClick = new UserListClickInterface() {
        @Override
        public void onItemClick(UserList position) {
            FragmentTransaction  transaction;
            if (Constant.VALUE.equals("users")) {
                transaction = getChildFragmentManager().beginTransaction();
                Log.e("tree","Fd");

            }
            else  if (Constant.VALUE.equals("value")) {

                transaction = getFragmentManager().beginTransaction();
            }
            else {
                transaction = getFragmentManager().beginTransaction();
            }




            Bundle bundle = new Bundle();
             bundle.putString("UserId",position.UserId);
             bundle.putString("FullName",position.FullName);
             bundle.putString("OfficeExt",position.OfficeExt);
             bundle.putString("UnitName",position.UnitName);
             bundle.putString("DepartmentName",position.DepartmentName);
             bundle.putString("Designation",position.Designation);
             bundle.putString("Picture",position.ProfilePhoto);
             bundle.putString("Email",position.Email);
             bundle.putString("Number",position.PersonalMobileNumber);
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

    public int handleBackPress() {

        Log.e("evan","evan"+getFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName()));
            if (getFragmentManager().findFragmentByTag(SetUpFragment.class.getSimpleName()) != null) {
                SetUpFragment f = (SetUpFragment) getFragmentManager()
                        .findFragmentByTag(SetUpFragment.class.getSimpleName());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
                transaction.remove(f);
                transaction.commit();
                getFragmentManager().popBackStack();


               return 2;

            }

        return 2;

    }
    public int test(){
        Log.e("evan","evan"+getChildFragmentManager().findFragmentByTag(PunchInFragment.class.getSimpleName()));
       if (getChildFragmentManager().findFragmentByTag(PunchInFragment.class.getSimpleName()) != null) {
           PunchInFragment f = (PunchInFragment) getChildFragmentManager()
                        .findFragmentByTag(PunchInFragment.class.getSimpleName());
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
                transaction.remove(f);
                transaction.commit();
           getChildFragmentManager().popBackStack();


               return 2;

            }
        return 2;
    }
    private void loadUnitItems() {
        progress_bar.setVisibility( View.VISIBLE);
        compositeDisposable.add(Common.unitRepository.getUnitItems().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<Unit>>() {
            @Override
            public void accept(List<Unit> units) throws Exception {
                displayUnitItems(units);
                progress_bar.setVisibility( View.GONE);
            }
        }));

    }


    private void displayUnitItems(List<Unit> units) {

        mUnitAdapter = new UnitAdapter(mActivity, units,mClickUnit);

        rcl_this_unit_list.setAdapter(mUnitAdapter);


    }
    private void loadDepartmentItems() {
        progress_bar.setVisibility( View.VISIBLE);
        compositeDisposable.add(Common.departmentRepository.getCartItems().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<Department>>() {
            @Override
            public void accept(List<Department> departments) throws Exception {
                displayDepartmentItems(departments);
                progress_bar.setVisibility( View.GONE);
            }
        }));

    }


    private void displayDepartmentItems(List<Department> departments) {

        mDepartmentAdapter = new DepartmentAdapter(mActivity, departments,mClickDepartment);

        rcl_this_department_list.setAdapter(mDepartmentAdapter);

    }
    private void loadData() {

        progress_bar.setVisibility( View.VISIBLE);

        try {
            compositeDisposable.add(Common.userListRepository.getUserListItems().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<UserList>>() {
                @Override
                public void accept(List<UserList> userLists) throws Exception {
                    userListEntities=userLists;
                    displayUserList(userLists);
                    progress_bar.setVisibility( View.GONE);
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
            progress_bar.setVisibility( View.GONE);
        }

    }

    private void Data(){
        progress_bar.setVisibility( View.VISIBLE);
        if (unitValue>0 && departmentValue>0){
            compositeDisposable.add(Common.userListRepository.getUserListByUnitDepartment(departmentValue,unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<UserList>>() {
                @Override
                public void accept(List<UserList> userLists) throws Exception {
                    userListEntities=userLists;
                    displayUserList(userLists);
                    progress_bar.setVisibility( View.GONE);
                }
            }));
        }else if (unitValue>0){
            compositeDisposable.add(Common.userListRepository.getUserListByUnit(unitValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<UserList>>() {
                @Override
                public void accept(List<UserList> userLists) throws Exception {
                    userListEntities=userLists;
                    displayUserList(userLists);
                    progress_bar.setVisibility( View.GONE);
                }
            }));
        }
        else if (departmentValue>0){
            compositeDisposable.add(Common.userListRepository.getUserListByDepartment(departmentValue).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<UserList>>() {
                @Override
                public void accept(List<UserList> userLists) throws Exception {
                    userListEntities=userLists;
                    displayUserList(userLists);
                    progress_bar.setVisibility( View.GONE);
                }
            }));
        }
        else {
            loadData();
            progress_bar.setVisibility( View.GONE);
        }
    }

    private void displayUserList(List<UserList> userLists) {

        mAdapters = new UnitDepartmentAdapter(mActivity, userLists,mClick);

        rcl_this_place_list.setAdapter(mAdapters);


    }
//    private void loadData() {
//        showLoadingProgress(mActivity);
//        compositeDisposable.add(mService.getUser().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<UserListEntity>>() {
//            @Override
//            public void accept(ArrayList<UserListEntity> carts) throws Exception {
//                userListEntities=carts;
//                mAdapters = new UnitDepartmentAdapter(mActivity, carts,mClick);
//
//                rcl_this_place_list.setAdapter(mAdapters);
//                dismissLoadingProgress();
//            }
//        }));
//
//
//    }
//    private void unitListData(){
//
//        showLoadingProgress(mActivity);
//        compositeDisposable.add(mService.getUnitList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<UnitListEntity>>() {
//            @Override
//            public void accept(ArrayList<UnitListEntity> carts) throws Exception {
//                unitListEntityList=carts;
//                mUnitAdapter = new UnitAdapter(mActivity, unitListEntityLists,mClickUnit);
//
//                rcl_this_unit_list.setAdapter(mUnitAdapter);
//                dismissLoadingProgress();
//
////                unitListEntityArrayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, unitListEntityList);
////                unitListEntityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                spinnerUnit.setAdapter(unitListEntityArrayAdapter);
//            }
//        }));
//    }
//    private void DepartmentListData(){
//        showLoadingProgress(mActivity);
//        compositeDisposable.add(mService.getDepartmentList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ArrayList<DepartmentListEntity>>() {
//            @Override
//            public void accept(ArrayList<DepartmentListEntity> carts) throws Exception {
//                departmentListEntityList=carts;
////                departmentListEntityArrayAdapter = new ArrayAdapter<DepartmentListEntity>(getActivity(), android.R.layout.simple_spinner_item, departmentListEntityList);
////                departmentListEntityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                spinnerDepartments.setAdapter(departmentListEntityArrayAdapter);
//                mDepartmentAdapter = new DepartmentAdapter(mActivity, departmentListEntityLists,mClickDepartment);
//
//                rcl_this_department_list.setAdapter(mDepartmentAdapter);
//                dismissLoadingProgress();
//            }
//        }));
//    }
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
