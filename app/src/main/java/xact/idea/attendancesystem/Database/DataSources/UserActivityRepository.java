package xact.idea.attendancesystem.Database.DataSources;

import java.util.Date;
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
    public Flowable<List<UserActivity>> getUserActivityItemByDate(Date from, Date to,String UserId) {
        return iDepartmentDataSource.getUserActivityItemByDate(from, to,UserId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getListUnitId(int unitId) {
        return iDepartmentDataSource.getListUnitId(unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getListDepartmentId(int departmentId) {
        return iDepartmentDataSource.getListDepartmentId(departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getListUnitIdDepartmentId(int unitId, int departmentId) {
        return iDepartmentDataSource.getListUnitIdDepartmentId(unitId, departmentId);
    }

    @Override
    public void updatedById(String userId, String PunchOutLocation, String PunchOutTime, String Duration,String Date) {
         iDepartmentDataSource.updatedById(userId, PunchOutLocation, PunchOutTime, Duration,Date);
    }

    @Override
    public void emptyUserActivityDateWiseId(Date from, Date to, String userId) {
        iDepartmentDataSource.emptyUserActivityDateWiseId(from, to, userId);
    }

    @Override
    public UserActivity getUserActivity(String userId, String Date) {
        return iDepartmentDataSource.getUserActivity(userId, Date);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getPresentUnitList(String date, int unitId) {
        return iDepartmentDataSource.getPresentUnitList(date, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getPresentDepartmentList(String date, int departmentId) {
        return iDepartmentDataSource.getPresentDepartmentList(date, departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getPresentUnitDepartmentList(String date, int unitId, int departmentId) {
        return iDepartmentDataSource.getPresentUnitDepartmentList(date, unitId, departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getAbsentUnitList(String date, int unitId) {
        return iDepartmentDataSource.getAbsentUnitList(date, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getAbsentDepartmentList(String date, int departmentId) {
        return iDepartmentDataSource.getAbsentDepartmentList(date, departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getAbsentUnitDepartmentList(String date, int departmentId, int unitId) {
        return iDepartmentDataSource.getAbsentUnitDepartmentList(date, departmentId, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getLateUnitList(String date, double value, int unitId) {
        return iDepartmentDataSource.getLateUnitList(date, value, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getLateDepartmentList(String date, double value, int departmentId) {
        return iDepartmentDataSource.getLateDepartmentList(date, value, departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getLateUnitDepartmentList(String date, double value, int departmentId, int unitId) {
        return iDepartmentDataSource.getLateUnitDepartmentList(date, value, departmentId, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getOnTimeUnitList(String date, double value, int unitId) {
        return iDepartmentDataSource.getOnTimeUnitList(date, value, unitId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getOnTimeDepartmentList(String date, double value, int departmentId) {
        return iDepartmentDataSource.getOnTimeDepartmentList(date, value, departmentId);
    }

    @Override
    public Flowable<List<AttendanceEntity>> getOnTimeUnitDepartmentList(String date, double value, int departmentId, int unitId) {
        return iDepartmentDataSource.getOnTimeUnitDepartmentList(date, value, departmentId, unitId);
    }

    @Override
    public void emptyUserActivityDateWise(Date from, Date to) {
        iDepartmentDataSource.emptyUserActivityDateWise(from, to);
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
