package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Entity.AttendanceEntity;

public class UserActivityRepository implements IUserActivityDataSource {
    public IUserActivityDataSource iDepartmentDataSource;
    public UserActivityRepository(IUserActivityDataSource userActivityDataSource){
        this.iDepartmentDataSource=userActivityDataSource;
    }
    private static  UserActivityRepository instance;

    public static UserActivityRepository getInstance(IUserActivityDataSource userActivityDataSource){
        if(instance==null)
            instance= new UserActivityRepository(userActivityDataSource);
        return instance;

    }

    @Override
    public Flowable<List<UserActivity>> getUserActivityItems() {
        return iDepartmentDataSource.getUserActivityItems();
    }

    @Override
    public Flowable<List<UserActivity>> getUserActivityItemById(int cartItemId) {
        return iDepartmentDataSource.getUserActivityItemById(cartItemId);
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
    public Flowable<List<AttendanceEntity>> getPresentList(String date) {
        return iDepartmentDataSource.getPresentList(date);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getAbsentList(String date) {
        return iDepartmentDataSource.getAbsentList(date);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getLateList(String date, double value) {
        return iDepartmentDataSource.getLateList(date,value);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getList() {
        return iDepartmentDataSource.getList();
    }

    @Override
    public Flowable<List<AttendanceEntity>> getOnTimeList(String date, double value) {
        return iDepartmentDataSource.getOnTimeList(date, value);
    }

    @Override
    public void insertToUserActivity(UserActivity... UserActivitys) {
        iDepartmentDataSource.insertToUserActivity(UserActivitys);
    }

    @Override
    public void updateUserActivity(UserActivity... UserActivitys) {
        iDepartmentDataSource.updateUserActivity(UserActivitys);
    }

    @Override
    public void deleteUserActivityItem(UserActivity... UserActivitys) {
        iDepartmentDataSource.deleteUserActivityItem(UserActivitys);
    }
}
