package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.UserList;

public class UserListRepository implements IUserListDataSource {
    public IUserListDataSource iDepartmentDataSource;
    public UserListRepository(IUserListDataSource userListDataSource){
        this.iDepartmentDataSource=userListDataSource;
    }
    private static  UserListRepository instance;

    public static UserListRepository getInstance(IUserListDataSource iDepartmentDataSource){
        if(instance==null)
            instance= new UserListRepository(iDepartmentDataSource);
        return instance;

    }
    @Override
    public Flowable<List<UserList>> getUserListItems() {
        return iDepartmentDataSource.getUserListItems();
    }

    @Override
    public Flowable<List<UserList>> getUserListItemById(int cartItemId) {
        return iDepartmentDataSource.getUserListItemById(cartItemId);
    }

    @Override
    public void emptyCart() {
        iDepartmentDataSource.emptyCart();
    }

    @Override
    public int size() {
        return iDepartmentDataSource.size();
    }

    @Override
    public UserList login(String Username,String Password) {
        return iDepartmentDataSource.login(Username,Password);
    }

    @Override
    public UserList getUserListById(int UserId) {
        return iDepartmentDataSource.getUserListById(UserId);
    }

    @Override
    public void insertToUserList(UserList... UserLists) {
        iDepartmentDataSource.insertToUserList(UserLists);
    }

    @Override
    public void updateUserList(UserList... UserLists) {
        iDepartmentDataSource.updateUserList(UserLists);
    }

    @Override
    public void deleteUserListItem(UserList... UserLists) {
        iDepartmentDataSource.deleteUserListItem(UserLists);
    }
}
