package xact.idea.attendancesystem.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.EntityLeave;

@Dao
public interface EntityLeaveDao {
    @Query("SELECT * FROM EntityLeave")
    Flowable<List<EntityLeave>> getEntityLeaveItems();

    @Query("SELECT * FROM EntityLeave WHERE ids=:EntityLeaveItemId")
    Flowable<List<EntityLeave>> getEntityLeaveItemById(int EntityLeaveItemId);




    @Query("DELETE  FROM EntityLeave")
    void emptyEntityLeave();

    @Insert
    void insertToEntityLeave(EntityLeave...EntityLeaves);

    @Update
    void updateEntityLeave(EntityLeave...EntityLeaves);

    @Delete
    void deleteEntityLeaveItem(EntityLeave...EntityLeaves);

    @Query("SELECT * from EntityLeave")
        //@Query("SELECT * from EntityLeave as c Inner  JOIN Favorite as f ON c.Id = f.id  WHERE f.id=:favoriteid")
    Flowable<List<EntityLeave>> getEntityLeave();
}
