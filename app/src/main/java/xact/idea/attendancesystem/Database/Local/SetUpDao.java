package xact.idea.attendancesystem.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.SetUp;

@Dao
public interface SetUpDao {
    @Query("SELECT * FROM SetUp")
    SetUp getSetUpItems();

    @Query("SELECT * FROM SetUp WHERE ids=:SetUpItemId")
    Flowable<List<SetUp>> getSetUpItemById(int SetUpItemId);




    @Query("DELETE  FROM SetUp")
    void emptySetUp();

    @Query("Select Count(ids)  FROM SetUp")
    int value();

    @Insert
    void insertToSetUp(SetUp...SetUps);

    @Update
    void updateSetUp(SetUp...SetUps);

    @Delete
    void deleteSetUpItem(SetUp...SetUps);

    @Query("SELECT * from SetUp")
        //@Query("SELECT * from SetUp as c Inner  JOIN Favorite as f ON c.Id = f.id  WHERE f.id=:favoriteid")
    Flowable<List<SetUp>> getSetUp();
}
