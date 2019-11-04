package xact.idea.attendancesystem.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.LeaveSummary;
import xact.idea.attendancesystem.Entity.LeaveEntity;

@Dao
public interface LeaveSummaryDao {
    @Query("SELECT * FROM LeaveSummary")
    Flowable<List<LeaveSummary>> getLeaveSummaryItems();

    @Query("SELECT * FROM LeaveSummary WHERE ids=:LeaveSummaryItemId")
    Flowable<List<LeaveSummary>> getLeaveSummaryItemById(int LeaveSummaryItemId);


    @Query("DELETE  FROM LeaveSummary")
    void emptyLeaveSummary();

    @Insert
    void insertToLeaveSummary(LeaveSummary...LeaveSummarys);

    @Update
    void updateLeaveSummary(LeaveSummary...LeaveSummarys);

    @Delete
    void deleteLeaveSummaryItem(LeaveSummary...LeaveSummarys);

    //@Query("SELECT * from LeaveSummary")
    @Query("SELECT c.FullName,c.UserIcon,c.UserId,f.Casual,f.Halfday,f.Sick,f.UnPaid,r.RemainingSick,r.RemainingCasual from LeaveSummary as c Inner  JOIN EntityLeave as f ON c.ids = f. ids inner join RemainingLeave as r on f.ids=r.ids ")
    Flowable<List<LeaveEntity>> getLeaveSummary();
}
