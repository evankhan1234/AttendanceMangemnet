package xact.idea.attendancesystem.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

public class UnitDepartmentAdapter extends RecyclerView.Adapter<UnitDepartmentAdapter.PlaceTagListiewHolder> {


    private Activity mActivity = null;
    private List<String> messageEntities;

    public UnitDepartmentAdapter(Activity activity, List<String> messageEntitie) {
        mActivity = activity;
        messageEntities = messageEntitie;
        //mClick = mClicks;
    }


    @Override
    public UnitDepartmentAdapter.PlaceTagListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unit_dept_list_layout, null);
        CorrectSizeUtil.getInstance(mActivity).correctSize(view);


        return new UnitDepartmentAdapter.PlaceTagListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(UnitDepartmentAdapter.PlaceTagListiewHolder holder, final int position) {

Log.e("SDFsf","SDfs"+messageEntities.get(position));

    }

    @Override
    public int getItemCount() {
        Log.e("evan", "sd" + messageEntities.size());
        return messageEntities.size();
    }
    public class PlaceTagListiewHolder extends RecyclerView.ViewHolder {
        private CircularImageView user_icon;
        private TextView text_name;
        private TextView text_office_text;
        private TextView text_phone_number;
        private TextView text_email;
        private TextView text_unit;
        private TextView text_designation;
        private TextView text_department;



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



        }
    }
}