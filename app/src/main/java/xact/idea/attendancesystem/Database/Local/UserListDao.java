package xact.idea.attendancesystem.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.UserList;
import xact.idea.attendancesystem.Entity.AttendanceEntity;

@Dao
public interface UserListDao {
    @Query("SELECT * FROM UserList")
    Flowable<List<UserList>> getUserListItems();

    @Query("SELECT * FROM UserList WHERE ids=:UserListItemId")
    Flowable<List<UserList>> getUserListItemById(int UserListItemId);
    @Query("SELECT * FROM UserList WHERE UserId=:UserId")
    UserList getUserListById(int UserId);

    @Query("SELECT * FROM UserList WHERE  Email=:Email")
    UserList getUserListByEmail(String Email);
    @Query("Select Count(ids)  FROM UserList")
    int value();
    @Query("Select  * FROM UserList where Email=:Email AND Password=:Password")
    UserList login(String Email,String Password);

    @Query("DELETE  FROM UserList")
    void emptyUserList();

    @Insert
    void insertToUserList(UserList...UserLists);

    @Update
    void updateUserList(UserList...UserLists);

    @Delete
    void deleteUserListItem(UserList...UserLists);

    @Query("SELECT * from UserList")
        //@Query("SELECT * from UserList as c Inner  JOIN Favorite as f ON c.Id = f.id  WHERE f.id=:favoriteid")
    Flowable<List<UserList>> getUserList();
    @Query("SELECT * from UserList where  UnitId=:unitId")
    Flowable<List<UserList>> getUserListByUnit( int unitId);
    @Query("SELECT * from UserList where  DepartmentId=:departmentId")
    Flowable<List<UserList>> getUserListByDepartment(int departmentId);
    @Query("SELECT * from UserList where   DepartmentId=:departmentId AND UnitId=:unitId")
    Flowable<List<UserList>> getUserListByUnitDepartment( int departmentId, int unitId);
}
