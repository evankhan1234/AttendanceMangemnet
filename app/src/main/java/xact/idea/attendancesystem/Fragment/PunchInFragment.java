package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    EditText  edit_start_date;
    EditText  edit_end_date;
    Button btn_yes;
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
        initView();
    }

    private void initView() {

        edit_start_date =  mRoot.findViewById(R.id.edit_start_date);

        edit_end_date =  mRoot.findViewById(R.id.edit_end_date);
        btn_yes =  mRoot.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
        edit_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new HomeFragment.DatePickerFromFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });
        edit_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new HomeFragment.DatePickerFromFragment.DatePickerToFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });

    }
    public static class DatePickerFromFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = formatter.format(chosenDate);
            EditText startTime2 = (EditText) getActivity().findViewById(R.id.edit_start_date);
            startTime2.setText(formattedDate);

        }


        public static class DatePickerToFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                return dpd;
            }

            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(year, month, day, 0, 0, 0);
                Date chosenDate = cal.getTime();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = formatter.format(chosenDate);
                EditText endTime2 = (EditText) getActivity().findViewById(R.id.edit_end_date);
                endTime2.setText(formattedDate);
            }
        }


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
