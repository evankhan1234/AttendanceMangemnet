package xact.idea.attendancesystem.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xact.idea.attendancesystem.Database.Model.Department;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Interface.ClickInterface;
import xact.idea.attendancesystem.Interface.DepartmentClickInterface;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentListiewHolder> {


    private Activity mActivity = null;
    private List<Department> messageEntities;
    int row_index = -1;
    DepartmentClickInterface clickInterface;
    public DepartmentAdapter(Activity activity, List<Department> messageEntitie, DepartmentClickInterface  clickInterfaces) {
        mActivity = activity;
        messageEntities = messageEntitie;
        //mClick = mClicks;
        clickInterface=clickInterfaces;
    }


    @Override
    public DepartmentAdapter.DepartmentListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_department, null);
        CorrectSizeUtil.getInstance(mActivity).correctSize(view);


        return new DepartmentAdapter.DepartmentListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DepartmentAdapter.DepartmentListiewHolder holder, final int position) {


        Log.e("Evan", "SDfs" + messageEntities.get(position));
        holder.btn_department.setHint(messageEntities.get(position).DepartmentName);
        holder.btn_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();
                clickInterface.onItemClick( messageEntities.get(position).Id);
                //holder.btn_department.setPadding(20,0,20,0);
            }
        });
        if (row_index == position) {
            holder.btn_department.setBackground(mActivity.getResources().getDrawable(R.drawable.oval_background_department));
            holder.btn_department.setHintTextColor(mActivity.getResources().getColor(R.color.white));
        } else {
            holder.btn_department.setBackground(mActivity.getResources().getDrawable(R.drawable.oval_background));
            holder.btn_department.setHintTextColor(mActivity.getResources().getColor(R.color.black));
        }


    }

    @Override
    public int getItemCount() {
        Log.e("evan", "sd" + messageEntities.size());
        return messageEntities.size();
    }

    public class DepartmentListiewHolder extends RecyclerView.ViewHolder {

        private Button btn_department;


        public DepartmentListiewHolder(View itemView) {
            super(itemView);

            btn_department = itemView.findViewById(R.id.btn_department);


        }
    }
}