package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.UserList;

public interface IUserListDataSource {
    Flowable<List<UserList>> getUserListItems();


    Flowable<List<UserList>> getUserListItemById(int cartItemId);



    void emptyCart();
    int size();
    UserList login(String Username,String Password);
    UserList getUserListById(int UserId);

    void insertToUserList(UserList... UserLists);


    void updateUserList(UserList... UserLists);


    void deleteUserListItem(UserList... UserLists);
}
