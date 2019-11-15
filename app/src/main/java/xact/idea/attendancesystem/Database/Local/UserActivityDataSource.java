package xact.idea.attendancesystem.Database.Local;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.DataSources.IUserActivityDataSource;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Entity.AttendanceEntity;

public class UserActivityDataSource implements IUserActivityDataSource {
    private UserActivityDao userActivityDao;
    private static UserActivityDataSource instance;

    public UserActivityDataSource(UserActivityDao userActivityDao){
        this.userActivityDao=userActivityDao;
    }
    public static UserActivityDataSource getInstance(UserActivityDao userActivityDao){
        if(instance==null)
            instance = new UserActivityDataSource(userActivityDao);
        return instance;

    }
    @Override
    public Flowable<List<UserActivity>> getUserActivityItems() {
        return userActivityDao.getUserActivityItems();
    }

    @Override
    public Flowable<List<UserActivity>> getUserActivityItemById(int cartItemId) {
        return userActivityDao.getUserActivityItemById(cartItemId);
    }

    @Override
    public void emptyCart() {
        userActivityDao.emptyUserActivity();
    }

    @Override
    public int size() {
        return userActivityDao.value();
    }

    @Override
    public Flowable<List<AttendanceEntity>> getPresentList(String date) {
        return userActivityDao.getPresentList(date);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getAbsentList(String date) {
        return userActivityDao.getAbsentList(date);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getLateList(String date, double value) {
        return userActivityDao.getLateList(date, value);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getList() {
        return userActivityDao.getList();
    }

    @Override
    public Flowable<List<AttendanceEntity>> getOnTimeList(String date, double value) {
        return userActivityDao.getOnTimeList(date, value);
    }

    @Override
    public Flowable<List<UserActivity>> getUserActivityItemByDate(Date from, Date to,String UserId) {
        return userActivityDao.getUserActivityItemByDate(from, to,UserId);
    }

    @Override
    public void emptyUserActivityDateWise(Date from, Date to) {
        userActivityDao.emptyUserActivityDateWise(from, to);
    }

    @Override
    public void insertToUserActivity(UserActivity... UserActivitys) {
        userActivityDao.insertToUserActivity(UserActivitys);
    }

    @Override
    public void updateUserActivity(UserActivity... UserActivitys) {
        userActivityDao.updateUserActivity(UserActivitys);
    }

    @Override
    public void deleteUserActivityItem(UserActivity... UserActivitys) {
        userActivityDao.deleteUserActivityItem(UserActivitys);
    }
}
