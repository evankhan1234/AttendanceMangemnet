package xact.idea.attendancesystem.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import xact.idea.attendancesystem.Activity.LoginActivity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.CustomDialog;
import xact.idea.attendancesystem.Utils.SharedPreferenceUtil;

public class LeaveApprovalAdapter extends RecyclerView.Adapter<LeaveApprovalAdapter.LeaveApprovalListiewHolder> {


    private Activity mActivity = null;
    private List<String> messageEntities;

    public LeaveApprovalAdapter(Activity activity, List<String> messageEntitie) {
        mActivity = activity;
        messageEntities = messageEntitie;
        //mClick = mClicks;
    }


    @Override
    public LeaveApprovalAdapter.LeaveApprovalListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave_approval_list, null);
        CorrectSizeUtil.getInstance(mActivity).correctSize(view);


        return new LeaveApprovalAdapter.LeaveApprovalListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LeaveApprovalAdapter.LeaveApprovalListiewHolder holder, final int position) {

        Log.e("SDFsf","SDfs"+messageEntities.get(position));
        Glide.with(mActivity).load("https://www.hindustantimes.com/rf/image_size_960x540/HT/p2/2019/03/04/Pictures/_146f44ea-3e38-11e9-92c7-2b8d3185a4e0.jpg").diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.backwhite)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        holder.user_icon.setImageDrawable(resource);
                    }
                });
        holder.img_accept.setOnClickListener(new View.OnClickListener() {
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
        holder.img_attempt.setOnClickListener(new View.OnClickListener() {
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
        holder.img_cancel.setOnClickListener(new View.OnClickListener() {
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
//        holder.text_date.setText(messageEntities.get(position).Date);
//        holder.text_punchIn_location.setText(messageEntities.get(position).PunchInLocation);
//        holder.text_punchIn_time.setText(messageEntities.get(position).PunchInTime);
//        holder.text_punchOut_location.setText(messageEntities.get(position).PunchOutLocation);
//        holder.text_punchOut_time.setText(messageEntities.get(position).PunchOutTime);
//        holder.text_duration.setText(messageEntities.get(position).Duration);



    }

    @Override
    public int getItemCount() {
        Log.e("evan", "sd" + messageEntities.size());
        return messageEntities.size();
    }
    public class LeaveApprovalListiewHolder extends RecyclerView.ViewHolder {
        private CircleImageView user_icon;
        private TextView text_name;
        private TextView text_date;
        private TextView text_punchIn_location;
        private TextView text_punchIn_time;
        private TextView text_punchOut_location;
        private TextView text_punchOut_time;
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
//            text_date = itemView.findViewById(R.id.text_date);
//            text_punchIn_location = itemView.findViewById(R.id.text_punchIn_location);
//            text_punchIn_time = itemView.findViewById(R.id.text_punchIn_time);
//            text_punchOut_location = itemView.findViewById(R.id.text_punchOut_location);
//            text_punchOut_time = itemView.findViewById(R.id.text_punchOut_time);
            //  text_department = itemView.findViewById(R.id.text_department);



        }
    }
}