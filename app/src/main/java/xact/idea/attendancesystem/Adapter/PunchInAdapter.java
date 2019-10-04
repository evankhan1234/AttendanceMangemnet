package xact.idea.attendancesystem.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.List;

import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

public class PunchInAdapter extends RecyclerView.Adapter<PunchInAdapter.PlaceTagListiewHolder> {


    private Activity mActivity = null;
    private List<UserActivityEntity> messageEntities;

    public PunchInAdapter(Activity activity, List<UserActivityEntity> messageEntitie) {
        mActivity = activity;
        messageEntities = messageEntitie;
        //mClick = mClicks;
    }


    @Override
    public PunchInAdapter.PlaceTagListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_punch_in_list, null);
        CorrectSizeUtil.getInstance(mActivity).correctSize(view);


        return new PunchInAdapter.PlaceTagListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(PunchInAdapter.PlaceTagListiewHolder holder, final int position) {

        Log.e("SDFsf","SDfs"+messageEntities.get(position));
        holder.text_date.setText(messageEntities.get(position).Date);
        holder.text_punchIn_location.setText(messageEntities.get(position).PunchInLocation);
        holder.text_punchIn_time.setText(messageEntities.get(position).PunchInTime);
        holder.text_punchOut_location.setText(messageEntities.get(position).PunchOutLocation);
        holder.text_punchOut_time.setText(messageEntities.get(position).PunchOutTime);
        holder.text_duration.setText(messageEntities.get(position).Duration);

    }

    @Override
    public int getItemCount() {
        Log.e("evan", "sd" + messageEntities.size());
        return messageEntities.size();
    }
    public class PlaceTagListiewHolder extends RecyclerView.ViewHolder {

        private TextView text_duration;
        private TextView text_date;
        private TextView text_punchIn_location;
        private TextView text_punchIn_time;
        private TextView text_punchOut_location;
        private TextView text_punchOut_time;
        private TextView text_department;



        public PlaceTagListiewHolder(View itemView) {
            super(itemView);

            text_duration = itemView.findViewById(R.id.text_duration);
            text_date = itemView.findViewById(R.id.text_date);
            text_punchIn_location = itemView.findViewById(R.id.text_punchIn_location);
            text_punchIn_time = itemView.findViewById(R.id.text_punchIn_time);
            text_punchOut_location = itemView.findViewById(R.id.text_punchOut_location);
            text_punchOut_time = itemView.findViewById(R.id.text_punchOut_time);
          //  text_department = itemView.findViewById(R.id.text_department);



        }
    }
}