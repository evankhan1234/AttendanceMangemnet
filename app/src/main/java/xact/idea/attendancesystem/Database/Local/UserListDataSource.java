package xact.idea.attendancesystem.Database.Local;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.DataSources.IUserListDataSource;
import xact.idea.attendancesystem.Database.Model.UserList;

public class UserListDataSource implements IUserListDataSource {
    private UserListDao userListDao;
    private static UserListDataSource instance;

    public UserListDataSource(UserListDao UnitDao){
        this.userListDao=UnitDao;
    }
    public static UserListDataSource getInstance(UserListDao userListDao){
        if(instance==null)
            instance = new UserListDataSource(userListDao);
        return instance;

    }
    @Override
    public Flowable<List<UserList>> getUserListItems() {
        return userListDao.getUserListItems();
    }

    @Override
    public Flowable<List<UserList>> getUserListItemById(int cartItemId) {
        return userListDao.getUserListItemById(cartItemId);
    }

    @Override
    public void emptyCart() {
        userListDao.emptyUserList();
    }

    @Override
    public int size() {
        return userListDao.value();
    }

    @Override
    public UserList login(String Username,String Password) {
        return userListDao.login(Username,Password);
    }

    @Override
    public UserList getUserListById(int UserId) {
        return userListDao.getUserListById(UserId);
    }

    @Override
    public Flowable<List<UserList>> getUserListByUnit( int unitId) {
        return userListDao.getUserListByUnit( unitId);
    }

    @Override
    public Flowable<List<UserList>> getUserListByDepartment(int departmentId) {
        return userListDao.getUserListByDepartment( departmentId);
    }

    @Override
    public Flowable<List<UserList>> getUserListByUnitDepartment( int departmentId, int unitId) {
        return userListDao.getUserListByUnitDepartment( departmentId, unitId);
    }

    @Override
    public void insertToUserList(UserList... UserLists) {

        userListDao.insertToUserList(UserLists);
    }

    @Override
    public void updateUserList(UserList... UserLists) {
        userListDao.updateUserList(UserLists);
    }

    @Override
    public void deleteUserListItem(UserList... UserLists) {
        userListDao.deleteUserListItem(UserLists);
    }
}
