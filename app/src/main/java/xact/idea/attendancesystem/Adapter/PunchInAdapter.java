package xact.idea.attendancesystem.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.siyamed.shapeimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Entity.AttendanceEntity;
import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.Filter.CustomFilterPunchAdmin;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

public class PunchInAdapter extends RecyclerView.Adapter<PunchInAdapter.PlaceTagListiewHolder> implements Filterable {


    private CustomFilterPunchAdmin filter;
    private Activity mActivity = null;
    public List<AttendanceEntity> messageEntities;
    private String names;
    private String dates;

    public PunchInAdapter(Activity activity, List<AttendanceEntity> messageEntitie,String name,String currentDate) {
        mActivity = activity;
        messageEntities = messageEntitie;
        //mClick = mClicks;
        names=name;
        dates=currentDate;
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
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        String currentDate=formatter.format(date);
        if (names.equals("absent")||names.equals("leave")) {
            holder.text_date.setText(messageEntities.get(position).FullName);
            holder.text_punchIn_location.setText("N/A");
            holder.text_punchIn_time.setText("N/A");
            holder.text_punchOut_location.setText("N/A");
            holder.text_punchOut_time.setText("N/A");
            holder.text_duration.setText("N/A");



        }
        else if (names.equals("all")) {
//            holder.text_date.setText(messageEntities.get(position).FullName);
//            holder.text_punchIn_location.setText(messageEntities.get(position).PunchInLocation);
//            holder.text_punchIn_time.setText(messageEntities.get(position).PunchInTimeLate);
//            holder.text_punchOut_location.setText(messageEntities.get(position).PunchInLocation);
//            holder.text_punchOut_time.setText(messageEntities.get(position).PunchOutTime);
//            holder.text_duration.setText(messageEntities.get(position).Duration);
            if (messageEntities.get(position).WorkingDate!=null){
                if (messageEntities.get(position).WorkingDate.equals(dates)){
                    holder.text_date.setText(messageEntities.get(position).FullName);
                    holder.text_punchIn_location.setText(messageEntities.get(position).PunchInLocation);
                    holder.text_date.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                    holder.text_punchIn_location.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                    holder.text_punchOut_time.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                    holder.text_punchOut_location.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                    holder.text_punchIn_location.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                    holder.text_punchIn_time.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                    holder.text_duration.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                    holder.text_punchIn_time.setText(messageEntities.get(position).PunchInTimeLate);
                    holder.text_punchOut_location.setText(messageEntities.get(position).PunchInLocation);
                    holder.text_punchOut_time.setText(messageEntities.get(position).PunchOutTime);
                    holder.text_duration.setText(messageEntities.get(position).Duration);
                }
                else {
                    holder.text_date.setText(messageEntities.get(position).FullName);
                    holder.text_punchIn_location.setText("N/A");
                    holder.text_punchIn_time.setText("N/A");
                    holder.text_punchOut_location.setText("N/A");
                    holder.text_punchOut_time.setText("N/A");
                    holder.text_duration.setText("N/A");
                }
            }

//            if (messageEntities.get(position).PunchInLocation!=null){
//
//            }
//            else {
//                holder.text_punchIn_location.setText("N/A");
//            }
//            if (messageEntities.get(position).PunchInTimeLate!=null){
//                holder.text_punchIn_time.setText(messageEntities.get(position).PunchInTimeLate);
//            }
//            else {
//                holder.text_punchIn_time.setText("N/A");
//            }
//
//            if (messageEntities.get(position).PunchOutTime!=null){
//                holder.text_punchOut_time.setText(messageEntities.get(position).PunchOutTime);
//            }
//            else {
//                holder.text_punchOut_time.setText("N/A");
//            }
//            if (messageEntities.get(position).Duration!=null){
//                holder.text_duration.setText(messageEntities.get(position).Duration);
//            }
//            else {
//                holder.text_duration.setText("N/A");
//            }








        }else {
            holder.text_date.setText(messageEntities.get(position).FullName);
            holder.text_punchIn_location.setText(messageEntities.get(position).PunchInLocation);
            holder.text_punchIn_time.setText(messageEntities.get(position).PunchInTimeLate);
            holder.text_punchOut_location.setText(messageEntities.get(position).PunchInLocation);
            holder.text_punchOut_time.setText(messageEntities.get(position).PunchOutTime);
            holder.text_duration.setText(messageEntities.get(position).Duration);
        }


    }

    @Override
    public int getItemCount() {

        return messageEntities.size();
    }


    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilterPunchAdmin(messageEntities, this);
        }
        return filter;
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