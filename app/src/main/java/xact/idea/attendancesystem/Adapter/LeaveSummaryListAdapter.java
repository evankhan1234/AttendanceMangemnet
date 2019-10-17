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
import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

public class LeaveSummaryListAdapter extends RecyclerView.Adapter<LeaveSummaryListAdapter.LeaveSummaryListiewHolder> {


    private Activity mActivity = null;
    private List<String> messageEntities;

    public LeaveSummaryListAdapter(Activity activity, List<String> messageEntitie) {
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

        Log.e("SDFsf","SDfs"+messageEntities.get(position));
        Glide.with(mActivity).load("https://www.hindustantimes.com/rf/image_size_960x540/HT/p2/2019/03/04/Pictures/_146f44ea-3e38-11e9-92c7-2b8d3185a4e0.jpg").diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.backwhite)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        holder.user_icon.setImageDrawable(resource);
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
    public class LeaveSummaryListiewHolder extends RecyclerView.ViewHolder {
        private CircleImageView user_icon;
        private TextView text_name;
        private TextView text_date;
        private TextView text_punchIn_location;
        private TextView text_punchIn_time;
        private TextView text_punchOut_location;
        private TextView text_punchOut_time;
        private TextView text_department;



        public LeaveSummaryListiewHolder(View itemView) {
            super(itemView);
            user_icon = itemView.findViewById(R.id.user_icon);
            text_name = itemView.findViewById(R.id.text_name);
//            text_date = itemView.findViewById(R.id.text_date);
//            text_punchIn_location = itemView.findViewById(R.id.text_punchIn_location);
//            text_punchIn_time = itemView.findViewById(R.id.text_punchIn_time);
//            text_punchOut_location = itemView.findViewById(R.id.text_punchOut_location);
//            text_punchOut_time = itemView.findViewById(R.id.text_punchOut_time);
            //  text_department = itemView.findViewById(R.id.text_department);



        }
    }
}