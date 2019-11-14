package xact.idea.attendancesystem.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.Department;
@Dao
public interface DepartmentDao {

    @Query("SELECT * FROM Department")
    Flowable<List<Department>> getDepartmentItems();

    @Query("SELECT * FROM Department WHERE id=:DepartmentItemId")
    Flowable<List<Department>> getDepartmentItemById(int DepartmentItemId);

    @Query("Select Count(ids)  FROM Department")
    int value();


    @Query("DELETE  FROM Department")
    void emptyDepartment();

    @Insert
    void insertToDepartment(Department...Departments);

    @Update
    void updateDepartment(Department...Departments);

    @Delete
    void deleteDepartmentItem(Department...Departments);

    @Query("SELECT * from Department")
    //@Query("SELECT * from Department as c Inner  JOIN Favorite as f ON c.Id = f.id  WHERE f.id=:favoriteid")
    Flowable<List<Department>> getDepartment();
}
