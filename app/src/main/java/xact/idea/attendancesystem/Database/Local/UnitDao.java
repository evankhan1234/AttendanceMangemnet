package xact.idea.attendancesystem.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.Unit;

@Dao
public interface UnitDao {
    @Query("SELECT * FROM Unit")
    Flowable<List<Unit>> getUnitItems();

    @Query("SELECT * FROM Unit WHERE id=:UnitItemId")
    Flowable<List<Unit>> getUnitItemById(int UnitItemId);




    @Query("DELETE  FROM Unit")
    void emptyUnit();

    @Query("Select Count(ids)  FROM Unit")
    int value();

    @Insert
    void insertToUnit(Unit...Units);

    @Update
    void updateUnit(Unit...Units);

    @Delete
    void deleteUnitItem(Unit...Units);

    @Query("SELECT * from Unit")
        //@Query("SELECT * from Unit as c Inner  JOIN Favorite as f ON c.Id = f.id  WHERE f.id=:favoriteid")
    Flowable<List<Unit>> getUnit();
}
