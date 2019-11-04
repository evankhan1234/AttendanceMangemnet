package xact.idea.attendancesystem.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.RemainingLeave;
@Dao
public interface RemainingLeaveDao {
    @Query("SELECT * FROM RemainingLeave")
    Flowable<List<RemainingLeave>> getRemainingLeaveItems();

    @Query("SELECT * FROM RemainingLeave WHERE ids=:RemainingLeaveItemId")
    Flowable<List<RemainingLeave>> getRemainingLeaveItemById(int RemainingLeaveItemId);




    @Query("DELETE  FROM RemainingLeave")
    void emptyRemainingLeave();

    @Insert
    void insertToRemainingLeave(RemainingLeave...RemainingLeaves);

    @Update
    void updateRemainingLeave(RemainingLeave...RemainingLeaves);

    @Delete
    void deleteRemainingLeaveItem(RemainingLeave...RemainingLeaves);

    @Query("SELECT * from RemainingLeave")
        //@Query("SELECT * from RemainingLeave as c Inner  JOIN Favorite as f ON c.Id = f.id  WHERE f.id=:favoriteid")
    Flowable<List<RemainingLeave>> getRemainingLeave();
}
