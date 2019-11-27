package xact.idea.attendancesystem.Adapter;

import android.Manifest;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.ClipData;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import xact.idea.attendancesystem.Activity.MainActivity;
import xact.idea.attendancesystem.Database.Model.UserList;
import xact.idea.attendancesystem.Entity.UserListEntity;
import xact.idea.attendancesystem.Filter.CustomFilterPunchAdmin;
import xact.idea.attendancesystem.Filter.CustomFilterUserList;
import xact.idea.attendancesystem.Fragment.BottomSheetFragment;
import xact.idea.attendancesystem.Interface.ClickInterface;
import xact.idea.attendancesystem.Interface.UserListClickInterface;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

public class UnitDepartmentAdapter extends RecyclerView.Adapter<UnitDepartmentAdapter.PlaceTagListiewHolder> implements Filterable {

    private BottomSheetDialog mBottomSheetDialog;
    private BottomSheetBehavior mDialogBehavior;
    private BottomSheetBehavior mBehavior;
    CustomFilterUserList filter;
    private Activity mActivity = null;
    public List<UserList> messageEntities;
    public List<UserList> messageEntitiesFilter;
    UserListClickInterface clickInterface;

    public UnitDepartmentAdapter(Activity activity, List<UserList> messageEntitie, UserListClickInterface clickInterfaces) {
        mActivity = activity;
        this.messageEntities = messageEntitie;
        this.messageEntitiesFilter = messageEntitie;
        //mClick = mClicks;
        clickInterface = clickInterfaces;
    }


    @Override
    public UnitDepartmentAdapter.PlaceTagListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unit_dept_list_layout, null);
        CorrectSizeUtil.getInstance(mActivity).correctSize(view);


        return new UnitDepartmentAdapter.PlaceTagListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UnitDepartmentAdapter.PlaceTagListiewHolder holder, final int position) {
        // UserList messageEntitie= messageEntities.get(position);
        Log.e("messageEntities", "SDfs" + messageEntities.get(position).UserId);

        if (messageEntities.get(position).ProfilePhoto != null) {
            Glide.with(mActivity).load(messageEntities.get(position).ProfilePhoto).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.backwhite)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            holder.user_icon.setImageDrawable(resource);
                        }
                    });
        } else {
            Glide.with(mActivity).load("https://www.hardiagedcare.com.au/wp-content/uploads/2019/02/default-avatar-profile-icon-vector-18942381.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.backwhite)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            holder.user_icon.setImageDrawable(resource);
                        }
                    });
        }

        holder.text_name.setText(messageEntities.get(position).FullName);
        holder.text_email.setText(messageEntities.get(position).Email);
        if (messageEntities.get(position).OfficeExt != null) {
            String offtext = messageEntities.get(position).OfficeExt.replaceAll("\\n","");
            holder.text_office_text.setText("(" + offtext + ")");
        } else {

        }

        holder.text_phone_number.setText(messageEntities.get(position).PersonalMobileNumber);

        if (messageEntities.get(position).Designation != null) {
            holder.text_department.setText(messageEntities.get(position).Designation);
        } else {
            holder.text_department.setText("N/A");
        }

        if (messageEntities.get(position).UnitName != null) {
            holder.text_unit.setText(messageEntities.get(position).UnitName);
        } else {
            holder.text_unit.setText("N/A");
        }

        if (messageEntities.get(position).DepartmentName != null) {
            holder.text_department.setText(messageEntities.get(position).DepartmentName);
        } else {
            holder.text_department.setText("N/A");
        }


        holder.img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
        holder.img_add_contact.setOnClickListener(new View.OnClickListener() {
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
                    row.put(ContactsContract.CommonDataKinds.Phone.NUMBER, messageEntities.get(position).PersonalMobileNumber);
                    row.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                    data.add(row);


                    intent.putExtra(ContactsContract.Intents.Insert.NAME, messageEntities.get(position).FullName);
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data);
                    mActivity.startActivity(intent);
                }

            }
        });
        holder.img_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(messageEntities.get(position).PersonalMobileNumber);
            }
        });
        holder.img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + messageEntities.get(position).PersonalMobileNumber));
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    mActivity.startActivity(intent);
                }


            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) mActivity).getSupportFragmentManager();
                BottomSheetFragment.newInstance(mActivity, messageEntities.get(position)).show(manager, "dialog");


                //Toast.makeText(mActivity, messageEntities.get(position).FullName, Toast.LENGTH_SHORT).show();
            }
        });
        holder.img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickInterface.onItemClick(messageEntities.get(position));
                //Toast.makeText(mActivity, messageEntities.get(position).FullName, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.e("evan", "sd" + messageEntities.size());
        return messageEntities.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilterUserList(messageEntitiesFilter, this);
        }
        return filter;
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
        private ImageView img_next;
        private ImageView img_add_contact;


        public PlaceTagListiewHolder(View itemView) {
            super(itemView);
            img_add_contact = itemView.findViewById(R.id.img_add_contact);
            img_next = itemView.findViewById(R.id.img_next);
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

            //   Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mActivity, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSMS(String number) {
        // The number on which you want to send SMS
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.SEND_SMS}, 123);
        } else {
            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
        }
    }


}