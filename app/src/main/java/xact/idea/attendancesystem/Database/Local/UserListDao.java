package xact.idea.attendancesystem.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.UserList;

@Dao
public interface UserListDao {
    @Query("SELECT * FROM UserList")
    Flowable<List<UserList>> getUserListItems();

    @Query("SELECT * FROM UserList WHERE ids=:UserListItemId")
    Flowable<List<UserList>> getUserListItemById(int UserListItemId);
    @Query("SELECT * FROM UserList WHERE UserId=:UserId")
    UserList getUserListById(int UserId);


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
}
