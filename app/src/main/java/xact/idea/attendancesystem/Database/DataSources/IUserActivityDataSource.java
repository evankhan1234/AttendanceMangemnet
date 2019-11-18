package xact.idea.attendancesystem.Database.DataSources;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Entity.AttendanceEntity;

public interface IUserActivityDataSource {
    Flowable<List<UserActivity>> getUserActivityItems();


    Flowable<List<UserActivity>> getUserActivityItemById(int cartItemId);



    void emptyCart();
    int size();
    Flowable<List<AttendanceEntity>> getPresentList(String date);
    Flowable<List<AttendanceEntity>> getAbsentList(String date);
    Flowable<List<AttendanceEntity>> getLateList(String date,double value);
    Flowable<List<AttendanceEntity>> getList();
    Flowable<List<AttendanceEntity>> getOnTimeList(String date,double value);
    Flowable<List<UserActivity>> getUserActivityItemByDate(Date from, Date to,String UserId);
    Flowable<List<AttendanceEntity>> getListUnitId(int unitId);
    Flowable<List<AttendanceEntity>> getListDepartmentId(int departmentId);
    Flowable<List<AttendanceEntity>> getListUnitIdDepartmentId(int unitId,int departmentId);
    void updatedById(String userId,String PunchOutLocation,String PunchOutTime,String Duration,String Date);
    void emptyUserActivityDateWiseId(Date from,Date to,String userId);
    UserActivity getUserActivity(String userId,String Date);
    Flowable<List<AttendanceEntity>> getPresentUnitList(String date,int unitId);

    Flowable<List<AttendanceEntity>> getPresentDepartmentList(String date,int departmentId);

    Flowable<List<AttendanceEntity>> getPresentUnitDepartmentList(String date,int unitId,int departmentId);

    Flowable<List<AttendanceEntity>> getAbsentUnitList(String date,int unitId);

    Flowable<List<AttendanceEntity>> getAbsentDepartmentList(String date,int departmentId);

    Flowable<List<AttendanceEntity>> getAbsentUnitDepartmentList(String date,int departmentId,int unitId);

    Flowable<List<AttendanceEntity>> getLateUnitList(String date,double value,int unitId);

    Flowable<List<AttendanceEntity>> getLateDepartmentList(String date,double value,int departmentId);

    Flowable<List<AttendanceEntity>> getLateUnitDepartmentList(String date,double value,int departmentId,int unitId);

    Flowable<List<AttendanceEntity>> getOnTimeUnitList(String date,double value,int unitId);

    Flowable<List<AttendanceEntity>> getOnTimeDepartmentList(String date,double value,int departmentId);

    Flowable<List<AttendanceEntity>> getOnTimeUnitDepartmentList(String date,double value,int departmentId,int unitId);
    void emptyUserActivityDateWise(Date from,Date to);
    void insertToUserActivity(UserActivity... UserActivitys);


    void updateUserActivity(UserActivity... UserActivitys);


    void deleteUserActivityItem(UserActivity... UserActivitys);
}
