package xact.idea.attendancesystem.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import xact.idea.attendancesystem.Entity.LeaveSummaryEntity;
import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

public class LeaveSummaryListAdapter extends RecyclerView.Adapter<LeaveSummaryListAdapter.LeaveSummaryListiewHolder> {


    private Activity mActivity = null;
    private List<LeaveSummaryEntity> messageEntities;

    public LeaveSummaryListAdapter(Activity activity, List<LeaveSummaryEntity> messageEntitie) {
        mActivity = activity;
        messageEntities = messageEntitie;
        //mClick = mClicks;
    }


    @Override
    public LeaveSummaryListAdapter.LeaveSummaryListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave_summary_list, null);
        CorrectSizeUtil.getInstance(mActivity).correctSize(view);


        return new LeaveSummaryListAdapter.LeaveSummaryListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LeaveSummaryListAdapter.LeaveSummaryListiewHolder holder, final int position) {

        Glide.with(mActivity).load(messageEntities.get(position).UserIcon).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.backwhite)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        holder.user_icon.setImageDrawable(resource);
                    }
                });
        holder.text_name.setText(messageEntities.get(position).FullName);
        holder.text_half_day.setText(String.valueOf(messageEntities.get(position).entityLeaves.Halfday));
        holder.text_casual.setText(String.valueOf(messageEntities.get(position).entityLeaves.Casual));
        holder.text_sick.setText(String.valueOf(messageEntities.get(position).entityLeaves.Sick));
        holder.text_unpaid.setText(String.valueOf(messageEntities.get(position).entityLeaves.UnPaid));
        holder.text_remaining_casual.setText(String.valueOf(messageEntities.get(position).remainingLeaves.Casual));
        holder.text_remaining_sick.setText(String.valueOf(messageEntities.get(position).remainingLeaves.Sick));
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
    public class LeaveSummaryListiewHolder extends RecyclerView.ViewHolder {
        private CircleImageView user_icon;
        private TextView text_name;
        private TextView text_half_day;
        private TextView text_casual;
        private TextView text_sick;
        private TextView text_unpaid;
        private TextView text_remaining_casual;
        private TextView text_remaining_sick;



        public LeaveSummaryListiewHolder(View itemView) {
            super(itemView);
            user_icon = itemView.findViewById(R.id.user_icon);
            text_name = itemView.findViewById(R.id.text_name);
            text_half_day = itemView.findViewById(R.id.text_half_day);
            text_casual = itemView.findViewById(R.id.text_casual);
            text_sick = itemView.findViewById(R.id.text_sick);
            text_unpaid = itemView.findViewById(R.id.text_unpaid);
            text_remaining_casual = itemView.findViewById(R.id.text_remaining_casual);
            text_remaining_sick = itemView.findViewById(R.id.text_remaining_sick);
//            text_date = itemView.findViewById(R.id.text_date);
//            text_punchIn_location = itemView.findViewById(R.id.text_punchIn_location);
//            text_punchIn_time = itemView.findViewById(R.id.text_punchIn_time);
//            text_punchOut_location = itemView.findViewById(R.id.text_punchOut_location);
//            text_punchOut_time = itemView.findViewById(R.id.text_punchOut_time);
            //  text_department = itemView.findViewById(R.id.text_department);



        }
    }
}