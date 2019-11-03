package xact.idea.attendancesystem.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Interface.UnitClickInterface;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UnitListiewHolder> {


    private Activity mActivity = null;
    private List<Unit> messageEntities;
    int row_index=-1;
    UnitClickInterface unitClickInterface;
    public UnitAdapter(Activity activity, List<Unit> messageEntitie,UnitClickInterface unitClickInterfaces) {
        mActivity = activity;
        messageEntities = messageEntitie;
        //mClick = mClicks;
        unitClickInterface=unitClickInterfaces;
    }


    @Override
    public UnitAdapter.UnitListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_unit, null);
        CorrectSizeUtil.getInstance(mActivity).correctSize(view);


        return new UnitAdapter.UnitListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UnitAdapter.UnitListiewHolder holder, final int position) {

     //   int row_index;
        Log.e("SDFsf","SDfs"+messageEntities.get(position));
        holder.btn_unit.setHint(messageEntities.get(position).UnitName);
        holder.btn_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
                unitClickInterface.onItemClick(messageEntities.get(position).Id);

            }
        });
        if(row_index==position){
            holder.btn_unit.setBackground(mActivity.getResources().getDrawable(R.drawable.btn_more_background));
            holder.btn_unit.setTextColor(mActivity.getResources().getColor(R.color.white));
        }
        else
        {
            holder.btn_unit.setBackground(mActivity.getResources().getDrawable(R.drawable.btn_more_background_unit));
            holder.btn_unit.setTextColor(mActivity.getResources().getColor(R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        Log.e("evan", "sd" + messageEntities.size());
        return messageEntities.size();
    }
    public class UnitListiewHolder extends RecyclerView.ViewHolder {

        private Button btn_unit;




        public UnitListiewHolder(View itemView) {
            super(itemView);

            btn_unit = itemView.findViewById(R.id.btn_unit);



        }
    }
}