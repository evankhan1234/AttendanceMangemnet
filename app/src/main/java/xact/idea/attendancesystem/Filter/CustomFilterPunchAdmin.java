package xact.idea.attendancesystem.Filter;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import xact.idea.attendancesystem.Adapter.PunchInAdapter;
import xact.idea.attendancesystem.Adapter.PunchInAdapterForAdmin;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Entity.AttendanceEntity;
import xact.idea.attendancesystem.Entity.UserActivityEntity;

public class CustomFilterPunchAdmin extends Filter {
    private List<AttendanceEntity> data = null;
    private PunchInAdapter adapter;

    public CustomFilterPunchAdmin(List<AttendanceEntity> filterList, PunchInAdapter adapter) {
        this.adapter = adapter;
        this.data = filterList;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results = new Filter.FilterResults();

        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<AttendanceEntity> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).FullName.toUpperCase().contains(constraint)) {
                    filteredPlayers.add(data.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = data.size();
            results.values = data;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.messageEntities = (ArrayList<AttendanceEntity>) results.values;
        adapter.notifyDataSetChanged();
    }
}
