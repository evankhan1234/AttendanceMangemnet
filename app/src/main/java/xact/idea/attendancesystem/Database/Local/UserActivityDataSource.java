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
    public Flowable<List<AttendanceEntity>> getListUnitId(int unitId) {
        return userActivityDao.getListUnitId(unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getListDepartmentId(int departmentId) {
        return userActivityDao.getListDepartmentId(departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getListUnitIdDepartmentId(int unitId, int departmentId) {
        return userActivityDao.getListUnitIdDepartmentId(unitId, departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getPresentUnitList(String date, int unitId) {
        return userActivityDao.getPresentUnitList(date, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getPresentDepartmentList(String date, int departmentId) {
        return userActivityDao.getPresentDepartmentList(date, departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getPresentUnitDepartmentList(String date, int unitId, int departmentId) {
        return userActivityDao.getPresentUnitDepartmentList(date, unitId, departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getAbsentUnitList(String date, int unitId) {
        return userActivityDao.getAbsentUnitList(date, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getAbsentDepartmentList(String date, int departmentId) {
        return userActivityDao.getAbsentDepartmentList(date, departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getAbsentUnitDepartmentList(String date, int departmentId, int unitId) {
        return userActivityDao.getAbsentUnitDepartmentList(date, departmentId, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getLateUnitList(String date, double value, int unitId) {
        return userActivityDao.getLateUnitList(date, value, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getLateDepartmentList(String date, double value, int departmentId) {
        return userActivityDao.getLateDepartmentList(date, value, departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getLateUnitDepartmentList(String date, double value, int departmentId, int unitId) {
        return userActivityDao.getLateUnitDepartmentList(date, value, departmentId, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getOnTimeUnitList(String date, double value, int unitId) {
        return userActivityDao.getOnTimeUnitList(date, value, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getOnTimeDepartmentList(String date, double value, int departmentId) {
        return userActivityDao.getOnTimeDepartmentList(date, value, departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getOnTimeUnitDepartmentList(String date, double value, int departmentId, int unitId) {
        return userActivityDao.getOnTimeUnitDepartmentList(date, value, departmentId, unitId);
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
