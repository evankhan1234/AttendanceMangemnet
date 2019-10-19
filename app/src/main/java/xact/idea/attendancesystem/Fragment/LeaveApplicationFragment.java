package xact.idea.attendancesystem.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;


public class LeaveApplicationFragment extends Fragment {
    Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    View mRootView;
    EditText edit_leave_from;
    EditText edit_leave_to;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView= inflater.inflate(R.layout.fragment_leave_application, container, false);
        mActivity=getActivity();
        correctSizeUtil= correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(mRootView);
        initView();
        return mRootView;
    }

    private void initView() {
        edit_leave_from=mRootView.findViewById(R.id.edit_leave_from);
        edit_leave_to=mRootView.findViewById(R.id.edit_leave_to);
        edit_leave_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new DatePickerFromFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });
        edit_leave_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new DatePickerFromFragment.DatePickerToFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });
    }

    public int handleBackPress() {
        if (getFragmentManager().findFragmentByTag(LeaveApplicationFragment.class.getSimpleName()) != null) {
            LeaveApplicationFragment f = (LeaveApplicationFragment) getFragmentManager()
                    .findFragmentByTag(LeaveApplicationFragment.class.getSimpleName());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right);
            transaction.remove(f);
            transaction.commit();
            getFragmentManager().popBackStack();


            return 2;

        }
        return 2;
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
            EditText startTime2 = (EditText) getActivity().findViewById(R.id.edit_leave_from);
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
                EditText endTime2 = (EditText) getActivity().findViewById(R.id.edit_leave_to);
                endTime2.setText(formattedDate);
            }
        }


    }
}
