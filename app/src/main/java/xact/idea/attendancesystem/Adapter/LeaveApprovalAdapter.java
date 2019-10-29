package xact.idea.attendancesystem.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import xact.idea.attendancesystem.Activity.LoginActivity;
import xact.idea.attendancesystem.Entity.LeaveApprovalListEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.Constant;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.CustomDialog;
import xact.idea.attendancesystem.Utils.SharedPreferenceUtil;

public class LeaveApprovalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Activity mActivity = null;
    private List<LeaveApprovalListEntity> messageEntities;

    public LeaveApprovalAdapter(Activity activity, List<LeaveApprovalListEntity> messageEntitie) {
        mActivity = activity;
        messageEntities = messageEntitie;
        //mClick = mClicks;
    }

    private static int TYPE_ALL = 1;
    private static int TYPE_NEW = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_NEW) {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave_approval_list, null);
            CorrectSizeUtil.getInstance(mActivity).correctSize(view);


            return new LeaveApprovalAdapter.LeaveApprovalListiewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave_approval_pending_list, null);
            CorrectSizeUtil.getInstance(mActivity).correctSize(view);


            return new LeaveApprovalAdapter.LeaveApprovalListForPendingiewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof LeaveApprovalListiewHolder)
        {
//            if (Constant.LEAVE==null){
//
//            }
//            else  if (Constant.LEAVE.equals("NEW"))
//            {
//                ((LeaveApprovalListiewHolder)holder).img_accept.setVisibility(View.VISIBLE);
//                ((LeaveApprovalListiewHolder)holder).img_attempt.setVisibility(View.VISIBLE);
//                ((LeaveApprovalListiewHolder)holder).img_cancel.setVisibility(View.VISIBLE);
//                // holder.img_sms.setVisibility(View.VISIBLE);
//            }
//            else  if (Constant.LEAVE.equals("APPROVED"))
//            {
//                holder.img_accept.setVisibility(View.GONE);
//                holder.img_attempt.setVisibility(View.VISIBLE);
//                holder.img_cancel.setVisibility(View.GONE);
//                // holder.img_sms.setVisibility(View.VISIBLE);
//                holder.img_sms.setText("Approved");
//                holder.img_sms.setBackground(null);
//                holder.img_sms.setWidth(100);
//            }
//            else  if (Constant.LEAVE.equals("PENDING"))
//            {
//                holder.img_accept.setVisibility(View.VISIBLE);
//                holder.img_attempt.setVisibility(View.VISIBLE);
//                holder.img_cancel.setVisibility(View.GONE);
//
//                holder.img_sms.setText("Pending");
//                holder.img_sms.setText("Approved");
//                holder.img_sms.setBackground(null);
//                holder.img_sms.setWidth(100);
//                // holder.img_sms.setVisibility(View.VISIBLE);
//            }
//            else  if (Constant.LEAVE.equals("DENIED"))
//            {
//                holder.img_accept.setVisibility(View.VISIBLE);
//                holder.img_attempt.setVisibility(View.GONE);
//                holder.img_cancel.setVisibility(View.GONE);
//                // holder.img_sms.setVisibility(View.VISIBLE);
//                holder.img_sms.setText("Denied");
//            }
//            else  if (Constant.LEAVE.equals("DELETED"))
//            {
//                holder.img_accept.setVisibility(View.GONE);
//                holder.img_attempt.setVisibility(View.GONE);
//                holder.img_cancel.setVisibility(View.GONE);
//                //  holder.img_sms.setVisibility(View.VISIBLE);
//                holder.img_sms.setText("Deleted By User");
//                holder.img_sms.setBackground(null);
//                holder.img_sms.setWidth(100);
//            }
//            else  if (Constant.LEAVE.equals("ALL"))
//            {
////            holder.img_accept.setVisibility(View.VISIBLE);
////            holder.img_attempt.setVisibility(View.VISIBLE);
////            holder.img_cancel.setVisibility(View.VISIBLE);
//                //  holder.img_sms.setVisibility(View.VISIBLE);
//            }
            ((LeaveApprovalListiewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CustomDialog infoDialog = new CustomDialog(mActivity, R.style.CustomDialogTheme);
                    LayoutInflater inflator = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflator.inflate(R.layout.layout_pop_up_approval, null);

                    infoDialog.setContentView(v);
                    infoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    RelativeLayout main_root = infoDialog.findViewById(R.id.main_root);
                    Button btn_yes = infoDialog.findViewById(R.id.btn_yes);
                    TextView txt_name = infoDialog.findViewById(R.id.txt_name);
                    final CircleImageView image = infoDialog.findViewById(R.id.img_avatar);
                    CorrectSizeUtil.getInstance(mActivity).correctSize(main_root);
                    //correctSizeUtil = correctSizeUtil.getInstance(getActivity());
                    //  CorrectSizeUtil.setWidthOriginal(1080);
                    // correctSizeUtil.correctSize(view);
                    Glide.with(mActivity).load(messageEntities.get(position).UserIcon).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.backwhite)
                            .into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    image.setImageDrawable(resource);
                                }
                            });
                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            infoDialog.dismiss();
                        }
                    });
                    txt_name.setText(messageEntities.get(position).FullName);
                    infoDialog.show();
                }
            });
            Log.e("SDFsf","SDfs"+messageEntities.get(position));
            Glide.with(mActivity).load(messageEntities.get(position).UserIcon).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.backwhite)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            ((LeaveApprovalListiewHolder)holder).user_icon.setImageDrawable(resource);
                        }
                    });
            ((LeaveApprovalListiewHolder)holder).img_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CustomDialog infoDialog = new CustomDialog(mActivity, R.style.CustomDialogTheme);
                    LayoutInflater inflator = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflator.inflate(R.layout.layout_pop_up_leave_approval, null);

                    infoDialog.setContentView(v);
                    infoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    RelativeLayout main_root = infoDialog.findViewById(R.id.main_root);
                    //Button btn_yes = infoDialog.findViewById(R.id.btn_yes);
                    Button btn_send = infoDialog.findViewById(R.id.btn_send);
                    TextView text_invitation = infoDialog.findViewById(R.id.text_invitation);
                    text_invitation.setText("Are you want to accept??");
                    CorrectSizeUtil.getInstance((Activity) mActivity).correctSize(main_root);

                    btn_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            infoDialog.dismiss();
                        }
                    });
                    //correctSizeUtil = correctSizeUtil.getInstance(getActivity());
                    //  CorrectSizeUtil.setWidthOriginal(1080);
                    // correctSizeUtil.correctSize(view);

                    infoDialog.show();
                }
            });
            ((LeaveApprovalListiewHolder)holder).img_attempt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CustomDialog infoDialog = new CustomDialog(mActivity, R.style.CustomDialogTheme);
                    LayoutInflater inflator = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflator.inflate(R.layout.layout_pop_up_leave_approval, null);

                    infoDialog.setContentView(v);
                    infoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    RelativeLayout main_root = infoDialog.findViewById(R.id.main_root);
                    //Button btn_yes = infoDialog.findViewById(R.id.btn_yes);
                    Button btn_send = infoDialog.findViewById(R.id.btn_send);
                    TextView text_invitation = infoDialog.findViewById(R.id.text_invitation);
                    text_invitation.setText("Are you want to attempt??");
                    CorrectSizeUtil.getInstance((Activity) mActivity).correctSize(main_root);

                    btn_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            infoDialog.dismiss();
                        }
                    });
                    //correctSizeUtil = correctSizeUtil.getInstance(getActivity());
                    //  CorrectSizeUtil.setWidthOriginal(1080);
                    // correctSizeUtil.correctSize(view);

                    infoDialog.show();
                }
            });
            ((LeaveApprovalListiewHolder)holder).img_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CustomDialog infoDialog = new CustomDialog(mActivity, R.style.CustomDialogTheme);
                    LayoutInflater inflator = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflator.inflate(R.layout.layout_pop_up_leave_approval, null);

                    infoDialog.setContentView(v);
                    infoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    RelativeLayout main_root = infoDialog.findViewById(R.id.main_root);
                    //Button btn_yes = infoDialog.findViewById(R.id.btn_yes);
                    Button btn_send = infoDialog.findViewById(R.id.btn_send);
                    TextView text_invitation = infoDialog.findViewById(R.id.text_invitation);
                    text_invitation.setText("Are you want to cancel??");
                    CorrectSizeUtil.getInstance((Activity) mActivity).correctSize(main_root);

                    btn_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            infoDialog.dismiss();
                        }
                    });
                    //correctSizeUtil = correctSizeUtil.getInstance(getActivity());
                    //  CorrectSizeUtil.setWidthOriginal(1080);
                    // correctSizeUtil.correctSize(view);

                    infoDialog.show();
                }
            });
            ((LeaveApprovalListiewHolder)holder).text_name.setText(messageEntities.get(position).FullName);
            ((LeaveApprovalListiewHolder)holder).text_backup_name.setText("Back Up: "+messageEntities.get(position).BackUp);
            ((LeaveApprovalListiewHolder)holder).text_leave_application_date.setText(messageEntities.get(position).ApplicationDate);
            ((LeaveApprovalListiewHolder)holder).text_reason.setText(messageEntities.get(position).Reason);
        //    ((LeaveApprovalListiewHolder)holder).text_leave_type.setText(messageEntities.get(position).LeaveType);
//        holder.text_date.setText(messageEntities.get(position).Date);
//        holder.text_punchIn_location.setText(messageEntities.get(position).PunchInLocation);
//        holder.text_punchIn_time.setText(messageEntities.get(position).PunchInTime);
//        holder.text_punchOut_location.setText(messageEntities.get(position).PunchOutLocation);
//        holder.text_punchOut_time.setText(messageEntities.get(position).PunchOutTime);
//        holder.text_duration.setText(messageEntities.get(position).Duration);
           }
        else if(holder instanceof LeaveApprovalListForPendingiewHolder){
            ((LeaveApprovalListForPendingiewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CustomDialog infoDialog = new CustomDialog(mActivity, R.style.CustomDialogTheme);
                    LayoutInflater inflator = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflator.inflate(R.layout.layout_pop_up_approval, null);

                    infoDialog.setContentView(v);
                    infoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    RelativeLayout main_root = infoDialog.findViewById(R.id.main_root);
                    final CircleImageView image = infoDialog.findViewById(R.id.img_avatar);
                    final Button btn_yes = infoDialog.findViewById(R.id.btn_yes);
                    TextView txt_name = infoDialog.findViewById(R.id.txt_name);
                    CorrectSizeUtil.getInstance(mActivity).correctSize(main_root);
                    //correctSizeUtil = correctSizeUtil.getInstance(getActivity());
                    //  CorrectSizeUtil.setWidthOriginal(1080);
                    // correctSizeUtil.correctSize(view);
                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            infoDialog.dismiss();
                        }
                    });
                    txt_name.setText(messageEntities.get(position).FullName);
                    Glide.with(mActivity).load(messageEntities.get(position).UserIcon).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.backwhite)
                            .into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    image.setImageDrawable(resource);
                                }
                            });
                    infoDialog.show();
                }
            });
            if (messageEntities.get(position).Type.equals("PENDING")) {
                ((LeaveApprovalListForPendingiewHolder)holder).img_sms.setText("Pending");
                ((LeaveApprovalListForPendingiewHolder)holder).img_accept.setVisibility(View.VISIBLE);
                ((LeaveApprovalListForPendingiewHolder)holder).linear.setVisibility(View.VISIBLE);
                ((LeaveApprovalListForPendingiewHolder)holder).img_attempt.setVisibility(View.VISIBLE);
            }
            else if (messageEntities.get(position).Type.equals("DENIED")) {
                ((LeaveApprovalListForPendingiewHolder)holder).img_accept.setVisibility(View.VISIBLE);
                ((LeaveApprovalListForPendingiewHolder)holder).linear.setVisibility(View.VISIBLE);
                ((LeaveApprovalListForPendingiewHolder)holder).img_attempt.setVisibility(View.GONE);
                ((LeaveApprovalListForPendingiewHolder)holder).img_sms.setText("Denied");
            }
            else if (messageEntities.get(position).Type.equals("DELETED")) {
                ((LeaveApprovalListForPendingiewHolder)holder).img_accept.setVisibility(View.GONE);
                ((LeaveApprovalListForPendingiewHolder)holder).linear.setVisibility(View.GONE);
                ((LeaveApprovalListForPendingiewHolder)holder).img_attempt.setVisibility(View.GONE);
                ((LeaveApprovalListForPendingiewHolder)holder).img_sms.setText("Deleted By User");
                ((LeaveApprovalListForPendingiewHolder)holder).img_sms.setTextColor(mActivity.getResources().getColor(R.color.first1));
            }
            else if (messageEntities.get(position).Type.equals("APPROVED")) {
                ((LeaveApprovalListForPendingiewHolder)holder).img_accept.setVisibility(View.GONE);
                ((LeaveApprovalListForPendingiewHolder)holder).linear.setVisibility(View.GONE);
                ((LeaveApprovalListForPendingiewHolder)holder).img_attempt.setVisibility(View.VISIBLE);
                ((LeaveApprovalListForPendingiewHolder)holder).img_sms.setText("Approved");
            }
            Glide.with(mActivity).load(messageEntities.get(position).UserIcon).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.backwhite)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            ((LeaveApprovalListForPendingiewHolder)holder).user_icon.setImageDrawable(resource);
                        }
                    });
            ((LeaveApprovalListForPendingiewHolder)holder).img_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CustomDialog infoDialog = new CustomDialog(mActivity, R.style.CustomDialogTheme);
                    LayoutInflater inflator = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflator.inflate(R.layout.layout_pop_up_leave_approval, null);

                    infoDialog.setContentView(v);
                    infoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    RelativeLayout main_root = infoDialog.findViewById(R.id.main_root);
                    //Button btn_yes = infoDialog.findViewById(R.id.btn_yes);
                    Button btn_send = infoDialog.findViewById(R.id.btn_send);
                    TextView text_invitation = infoDialog.findViewById(R.id.text_invitation);
                    text_invitation.setText("Are you want to accept??");
                    CorrectSizeUtil.getInstance((Activity) mActivity).correctSize(main_root);

                    btn_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            infoDialog.dismiss();
                        }
                    });
                    //correctSizeUtil = correctSizeUtil.getInstance(getActivity());
                    //  CorrectSizeUtil.setWidthOriginal(1080);
                    // correctSizeUtil.correctSize(view);

                    infoDialog.show();
                }
            });
            ((LeaveApprovalListForPendingiewHolder)holder).img_attempt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CustomDialog infoDialog = new CustomDialog(mActivity, R.style.CustomDialogTheme);
                    LayoutInflater inflator = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflator.inflate(R.layout.layout_pop_up_leave_approval, null);

                    infoDialog.setContentView(v);
                    infoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    RelativeLayout main_root = infoDialog.findViewById(R.id.main_root);
                    //Button btn_yes = infoDialog.findViewById(R.id.btn_yes);
                    Button btn_send = infoDialog.findViewById(R.id.btn_send);
                    TextView text_invitation = infoDialog.findViewById(R.id.text_invitation);
                    text_invitation.setText("Are you want to attempt??");
                    CorrectSizeUtil.getInstance((Activity) mActivity).correctSize(main_root);

                    btn_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            infoDialog.dismiss();
                        }
                    });
                    //correctSizeUtil = correctSizeUtil.getInstance(getActivity());
                    //  CorrectSizeUtil.setWidthOriginal(1080);
                    // correctSizeUtil.correctSize(view);

                    infoDialog.show();
                }
            });
            ((LeaveApprovalListForPendingiewHolder)holder).text_name.setText(messageEntities.get(position).FullName);
            ((LeaveApprovalListForPendingiewHolder)holder).text_backup_name.setText("Back Up: "+messageEntities.get(position).BackUp);
            ((LeaveApprovalListForPendingiewHolder)holder).text_leave_application_date.setText(messageEntities.get(position).ApplicationDate);
            ((LeaveApprovalListForPendingiewHolder)holder).text_reason.setText(messageEntities.get(position).Reason);
           // ((LeaveApprovalListForPendingiewHolder)holder).text_leave_type.setText(messageEntities.get(position).LeaveType);
        }

    }
    @Override
    public int getItemCount() {
        Log.e("evan", "sd" + messageEntities.size());
        return messageEntities.size();
    }
    public class LeaveApprovalListiewHolder extends RecyclerView.ViewHolder {
        private CircleImageView user_icon;
        private TextView text_name;
        private TextView text_leave_type;
        private TextView text_leave_application_date;
        private TextView text_backup_name;
        private TextView text_reason;
        private TextView img_sms;
        private ImageView img_accept;
        private ImageView img_attempt;
        private ImageView img_cancel;



        public LeaveApprovalListiewHolder(View itemView) {
            super(itemView);
            user_icon = itemView.findViewById(R.id.user_icon);
            text_name = itemView.findViewById(R.id.text_name);
            img_accept = itemView.findViewById(R.id.img_accept);
            img_attempt = itemView.findViewById(R.id.img_attempt);
            img_cancel = itemView.findViewById(R.id.img_cancel);
       //     text_leave_type = itemView.findViewById(R.id.text_leave_type);
            text_leave_application_date = itemView.findViewById(R.id.text_leave_application_date);
            text_backup_name = itemView.findViewById(R.id.text_backup_name);
            text_reason = itemView.findViewById(R.id.text_reason);
            img_sms = itemView.findViewById(R.id.img_sms);
//            text_date = itemView.findViewById(R.id.text_date);
//            text_punchIn_location = itemView.findViewById(R.id.text_punchIn_location);
//            text_punchIn_time = itemView.findViewById(R.id.text_punchIn_time);
//            text_punchOut_location = itemView.findViewById(R.id.text_punchOut_location);
//            text_punchOut_time = itemView.findViewById(R.id.text_punchOut_time);
            //  text_department = itemView.findViewById(R.id.text_department);



        }

    }

    public class LeaveApprovalListForPendingiewHolder extends RecyclerView.ViewHolder {
        private CircleImageView user_icon;
        private TextView text_name;
        private TextView text_leave_type;
        private TextView text_leave_application_date;
        private TextView text_backup_name;
        private TextView text_reason;
        private TextView img_sms;
        private ImageView img_accept;
        private ImageView img_attempt;
        private ImageView img_cancel;
        private LinearLayout linear;



        public LeaveApprovalListForPendingiewHolder(View itemView) {
            super(itemView);
            user_icon = itemView.findViewById(R.id.user_icon);
            text_name = itemView.findViewById(R.id.text_name);
            img_accept = itemView.findViewById(R.id.img_accept);
            img_attempt = itemView.findViewById(R.id.img_attempt);
        //    img_cancel = itemView.findViewById(R.id.img_cancel);
        //    text_leave_type = itemView.findViewById(R.id.text_leave_type);
            text_leave_application_date = itemView.findViewById(R.id.text_leave_application_date);
            text_backup_name = itemView.findViewById(R.id.text_backup_name);
            text_reason = itemView.findViewById(R.id.text_reason);
            img_sms = itemView.findViewById(R.id.img_sms);
            linear = itemView.findViewById(R.id.linear);
//            text_date = itemView.findViewById(R.id.text_date);
//            text_punchIn_location = itemView.findViewById(R.id.text_punchIn_location);
//            text_punchIn_time = itemView.findViewById(R.id.text_punchIn_time);
//            text_punchOut_location = itemView.findViewById(R.id.text_punchOut_location);
//            text_punchOut_time = itemView.findViewById(R.id.text_punchOut_time);
            //  text_department = itemView.findViewById(R.id.text_department);



        }
    }
    public int getItemViewType(int position) {
        Log.e("sdfdsf","Sdfs"+messageEntities.get(position).Type);
        if (messageEntities.get(position).Type.equals("")) {
            return TYPE_NEW;

        }
        else if (messageEntities.get(position).Type.equals("APPROVED")) {
            return TYPE_ALL;

        }
        else if (messageEntities.get(position).Type.equals("NEW")) {
            return TYPE_NEW;

        }
        else if (messageEntities.get(position).Type.equals("NEW")) {
            return TYPE_NEW;

        }
        else {
            return TYPE_ALL;
        }
    }
}