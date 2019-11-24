package xact.idea.attendancesystem.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import xact.idea.attendancesystem.Database.Model.UserList;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;


public class BottomSheetFragment extends BottomSheetDialogFragment {
   static Activity mActivity;
    CorrectSizeUtil correctSizeUtil;
    private BottomSheetBehavior mBehavior;
    static UserList userList;
    private CircleImageView user_icon;
    private TextView text_name;
    private TextView text_emergency_contact_number;
    private TextView text_blood_group;
    private TextView text_office_text;
    private TextView text_phone_number;
    private TextView text_email;
    private TextView text_unit;
    private TextView text_designation;
    private TextView text_department;
    private ImageView img_phone;
    private ImageView img_sms;
    private ImageView img_email;
    private ImageView img_add_contact;


    public BottomSheetFragment() {
        // Required empty public constructor
    }

    public static BottomSheetFragment newInstance(Activity activity,UserList string) {
        BottomSheetFragment f = new BottomSheetFragment();
        mActivity = activity;
        userList = string;
        return f;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);

        text_blood_group = view.findViewById(R.id.text_blood_group);
        text_emergency_contact_number = view.findViewById(R.id.text_emergency_contact_number);
        img_add_contact = view.findViewById(R.id.img_add_contact);
        user_icon = view.findViewById(R.id.user_icon);
        text_name = view.findViewById(R.id.text_name);
        text_office_text = view.findViewById(R.id.text_office_text);
        text_phone_number = view.findViewById(R.id.text_phone_number);
        text_email = view.findViewById(R.id.text_email);
        text_unit = view.findViewById(R.id.text_unit);
        text_designation = view.findViewById(R.id.text_designation);
        text_department = view.findViewById(R.id.text_department);
        img_phone = view.findViewById(R.id.img_phone);
        img_sms = view.findViewById(R.id.img_sms);
        img_email = view.findViewById(R.id.img_email);

        correctSizeUtil = correctSizeUtil.getInstance(getActivity());
        correctSizeUtil.setWidthOriginal(1080);
        correctSizeUtil.correctSize(view);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        text_name.setText(userList.FullName);
        if (userList.ProfilePhoto!=null){
            Glide.with(mActivity).load(userList.ProfilePhoto).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.backwhite)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                           user_icon.setImageDrawable(resource);
                        }
                    });
        }else {
            Glide.with(mActivity).load("https://www.hardiagedcare.com.au/wp-content/uploads/2019/02/default-avatar-profile-icon-vector-18942381.jpg").diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.backwhite)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                           user_icon.setImageDrawable(resource);
                        }
                    });
        }

       text_name.setText(userList.FullName);
       text_email.setText(userList.Email);
        if (userList.OfficeExt!=null){
           text_office_text.setText("("+userList.OfficeExt+")");
        }
        else {

        }

       text_phone_number.setText(userList.PersonalMobileNumber);
       text_blood_group.setText("Blood Group: "+userList.PersonalMobileNumber);

        if (userList.EmergencyContactPerson!=null){
            text_emergency_contact_number.setText("Emergency Contact Number: "+userList.EmergencyContactPerson);
        }
        else {
            text_emergency_contact_number.setText("Emergency Contact Number: N/A");
        }
        if (userList.Designation!=null){
           text_department.setText(userList.Designation);
        }
        else {
           text_department.setText("N/A");
        }

        if (userList.UnitName!=null){
           text_unit.setText(userList.UnitName);
        }
        else {
           text_unit.setText("N/A");
        }

        if (userList.DepartmentName!=null){
           text_department.setText(userList.DepartmentName);
        }
        else {
           text_department.setText("N/A");
        }




       img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
       img_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(userList.PersonalMobileNumber);
            }
        });
       img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+userList.PersonalMobileNumber));
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    mActivity. startActivity(intent);
                }
            }
        });
     img_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_CONTACTS}, 1565);
                } else {
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    ArrayList<ContentValues> data = new ArrayList<>();

                    ContentValues row = new ContentValues();
                    row.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                    row.put(ContactsContract.CommonDataKinds.Phone.NUMBER, userList.PersonalMobileNumber);
                    row.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                    data.add(row);


                    intent.putExtra(ContactsContract.Intents.Insert.NAME, userList.FullName);
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data);
                    mActivity.startActivity(intent);
                }

            }
        });
        return dialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view= inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
//        correctSizeUtil = correctSizeUtil.getInstance(getActivity());
//        correctSizeUtil.setWidthOriginal(1080);
//        correctSizeUtil.correctSize(view);
//        return view;
//    }
    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            mActivity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            //   Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mActivity, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSMS(String number) {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.SEND_SMS},123);
        }
                else
        {
            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
        }
        // The number on which you want to send SMS

    }

}
