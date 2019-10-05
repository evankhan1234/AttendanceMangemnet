package xact.idea.attendancesystem.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import xact.idea.attendancesystem.Activity.MainActivity;
import xact.idea.attendancesystem.Entity.UserListEntity;
import xact.idea.attendancesystem.Interface.ClickInterface;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

public class UnitDepartmentAdapter extends RecyclerView.Adapter<UnitDepartmentAdapter.PlaceTagListiewHolder> {


    private Activity mActivity = null;
    private List<UserListEntity> messageEntities;
    ClickInterface  clickInterface;

    public UnitDepartmentAdapter(Activity activity, List<UserListEntity> messageEntitie,ClickInterface  clickInterfaces) {
        mActivity = activity;
        messageEntities = messageEntitie;
        //mClick = mClicks;
        clickInterface=clickInterfaces;
    }


    @Override
    public UnitDepartmentAdapter.PlaceTagListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unit_dept_list_layout, null);
        CorrectSizeUtil.getInstance(mActivity).correctSize(view);


        return new UnitDepartmentAdapter.PlaceTagListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UnitDepartmentAdapter.PlaceTagListiewHolder holder, final int position) {

        Log.e("SDFsf", "SDfs" + messageEntities.get(position));

        Glide.with(mActivity).load(messageEntities.get(position).UserIcon).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.backwhite)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        holder.user_icon.setImageDrawable(resource);
                    }
                });
        holder.text_name.setText(messageEntities.get(position).FullName);
        holder.text_email.setText(messageEntities.get(position).Email);
        holder.text_office_text.setText(messageEntities.get(position).OfficeExt);
        holder.text_phone_number.setText(messageEntities.get(position).PhoneNumber);

        holder.text_department.setText(messageEntities.get(position).Designation);
        holder.text_unit.setText(messageEntities.get(position).UnitName);
        holder.text_department.setText(messageEntities.get(position).DepartmentName);

        holder.img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
        holder.img_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(messageEntities.get(position).PhoneNumber);
            }
        });
        holder.img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+messageEntities.get(position).PhoneNumber));
                mActivity. startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickInterface.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.e("evan", "sd" + messageEntities.size());
        return messageEntities.size();
    }

    public class PlaceTagListiewHolder extends RecyclerView.ViewHolder {
        private CircleImageView user_icon;
        private TextView text_name;
        private TextView text_office_text;
        private TextView text_phone_number;
        private TextView text_email;
        private TextView text_unit;
        private TextView text_designation;
        private TextView text_department;
        private ImageView img_phone;
        private ImageView img_sms;
        private ImageView img_email;


        public PlaceTagListiewHolder(View itemView) {
            super(itemView);
            user_icon = itemView.findViewById(R.id.user_icon);
            text_name = itemView.findViewById(R.id.text_name);
            text_office_text = itemView.findViewById(R.id.text_office_text);
            text_phone_number = itemView.findViewById(R.id.text_phone_number);
            text_email = itemView.findViewById(R.id.text_email);
            text_unit = itemView.findViewById(R.id.text_unit);
            text_designation = itemView.findViewById(R.id.text_designation);
            text_department = itemView.findViewById(R.id.text_department);
            img_phone = itemView.findViewById(R.id.img_phone);
            img_sms = itemView.findViewById(R.id.img_sms);
            img_email = itemView.findViewById(R.id.img_email);


        }
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
            mActivity.finish();
            //   Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mActivity, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSMS(String number) {
          // The number on which you want to send SMS
        mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }
}