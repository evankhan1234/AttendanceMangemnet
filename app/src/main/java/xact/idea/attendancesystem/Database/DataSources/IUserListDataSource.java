package xact.idea.attendancesystem.Database.DataSources;

import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.UserList;

public interface IUserListDataSource {
    Flowable<List<UserList>> getUserListItems();


    Flowable<List<UserList>> getUserListItemById(int cartItemId);

    UserList getUserListByEmail(String Email);


    void emptyCart();
    int size();
    UserList login(String Username,String Password);
    UserList getUserListById(int UserId);
    Flowable<List<UserList>> getUserListByUnit( int unitId);
    Flowable<List<UserList>> getUserListByDepartment(int departmentId);
    Flowable<List<UserList>> getUserListByUnitDepartment( int departmentId, int unitId);
    void insertToUserList(UserList... UserLists);


    void updateUserList(UserList... UserLists);


    void deleteUserListItem(UserList... UserLists);
}
