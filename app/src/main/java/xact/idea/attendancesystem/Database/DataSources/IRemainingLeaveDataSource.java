package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.RemainingLeave;

public interface IRemainingLeaveDataSource {
    Flowable<List<RemainingLeave>> getRemainingLeaveItems();


    Flowable<List<RemainingLeave>> getRemainingLeaveItemById(int RemainingLeaveItemId);


    Flowable<List<RemainingLeave>> getRemainingLeave(int favoriteid);

    void emptyRemainingLeave();


    void insertToRemainingLeave(RemainingLeave... RemainingLeaves);


    void updateRemainingLeave(RemainingLeave... RemainingLeaves);


    void deleteRemainingLeaveItem(RemainingLeave... RemainingLeaves);
}
