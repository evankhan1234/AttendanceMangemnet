package xact.idea.attendancesystem.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Entity.AttendanceEntity;

@Dao

public interface UserActivityDao {
    @Query("SELECT * FROM UserActivity")
    Flowable<List<UserActivity>> getUserActivityItems();


    @Query("SELECT * FROM UserActivity WHERE UserId=:UserActivityItemId ORDER BY Date DESC LIMIT 30")
    Flowable<List<UserActivity>> getUserActivityItemById(int UserActivityItemId);
    @Query("SELECT * FROM UserActivity WHERE Date BETWEEN :from AND :to AND UserId=:UserId")
    Flowable<List<UserActivity>> getUserActivityItemByDate(Date from,Date to,String UserId);


    @Query("Select *  FROM UserActivity")
    int value();
    @Ignore
    @Query("Update UserActivity SET PunchOutLocation=:PunchOutLocation,PunchOutTime=:PunchOutTime,Duration=:Duration where UserId=:userId AND WorkingDate=:Date")
    void updatedById(String userId,String PunchOutLocation,String PunchOutTime,String Duration,String Date);

    @Query("DELETE  FROM UserActivity")
    void emptyUserActivity();
    @Query("DELETE FROM UserActivity WHERE Date BETWEEN :from AND :to")
    void emptyUserActivityDateWise(Date from,Date to);
    @Query("DELETE FROM UserActivity WHERE UserId=:userId AND Date BETWEEN :from AND :to")
    void emptyUserActivityDateWiseId(Date from,Date to,String userId);

    @Insert
    void insertToUserActivity(UserActivity...UserActivitys);

    @Update
    void updateUserActivity(UserActivity...UserActivitys);

    @Delete
    void deleteUserActivityItem(UserActivity...UserActivitys);

    @Query("SELECT * from UserActivity where UserId=:userId AND WorkingDate=:Date")
        //@Query("SELECT * from UserActivity as c Inner  JOIN Favorite as f ON c.Id = f.id  WHERE f.id=:favoriteid")
    UserActivity getUserActivity(String userId,String Date);

    @Query("SELECT  c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId  where f.UnitId=:unitId Group by f.FullName ")
    Flowable<List<AttendanceEntity>> getListUnitId(int unitId);
    @Query("SELECT  c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId  where f.DepartmentId=:departmentId Group by f.FullName ")
    Flowable<List<AttendanceEntity>> getListDepartmentId(int departmentId);
    @Query("SELECT  c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId  where f.DepartmentId=:departmentId  AND f.UnitId=:unitId Group by f.FullName ")
    Flowable<List<AttendanceEntity>> getListUnitIdDepartmentId(int unitId,int departmentId);
    @Query("SELECT c.[WorkingDate],c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date\n" +
            "UNION\n" +
            "SELECT c.WorkingDate, c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner JOIN UserList as f ON c.UserId = f.UserId where c.UserId not in (SELECT a.UserId from UserActivity as a Inner JOIN UserList as f ON a.UserId = f.UserId  where WorkingDate=:date) Group by f.FullName  ORDER BY c.WorkingDate")
    Flowable<List<AttendanceEntity>> getList(String date);
    @Query("SELECT c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date Group by f.FullName")
    Flowable<List<AttendanceEntity>> getPresentList(String date);
    @Query("SELECT c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner JOIN UserList as f ON c.UserId = f.UserId where c.UserId not in (SELECT a.UserId from UserActivity as a Inner JOIN UserList as f ON a.UserId = f.UserId  where WorkingDate=:date) Group by f.FullName")
    Flowable<List<AttendanceEntity>> getAbsentList(String date);
    @Query("SELECT c.PunchInTime,c.PunchInTimeLate,c.PunchInTimeLate,c.PunchOutTime,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND c.PunchInTime>:value Group by f.FullName")
    Flowable<List<AttendanceEntity>> getLateList(String date,double value);
    @Query("SELECT c.PunchInTime,c.PunchInTimeLate,c.PunchInTimeLate,c.PunchOutTime,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND c.PunchInTime<=:value Group by f.FullName")
    Flowable<List<AttendanceEntity>> getOnTimeList(String date,double value);



    @Query("SELECT c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND f.UnitId=:unitId Group by f.FullName ")
    Flowable<List<AttendanceEntity>> getPresentUnitList(String date,int unitId);
    @Query("SELECT c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND f.DepartmentId=:departmentId Group by f.FullName ")
    Flowable<List<AttendanceEntity>> getPresentDepartmentList(String date,int departmentId);
    @Query("SELECT c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND f.UnitId=:unitId AND f.DepartmentId=:departmentId Group by f.FullName ")
    Flowable<List<AttendanceEntity>> getPresentUnitDepartmentList(String date,int unitId,int departmentId);

    @Query("SELECT c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner JOIN UserList as f ON c.UserId = f.UserId where c.UserId not in (SELECT a.UserId from UserActivity as a Inner JOIN UserList as f ON a.UserId = f.UserId  where WorkingDate=:date) AND f.UnitId=:unitId  Group by f.FullName")
    Flowable<List<AttendanceEntity>> getAbsentUnitList(String date,int unitId);
    @Query("SELECT c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner JOIN UserList as f ON c.UserId = f.UserId where c.UserId not in (SELECT a.UserId from UserActivity as a Inner JOIN UserList as f ON a.UserId = f.UserId  where WorkingDate=:date) AND f.DepartmentId=:departmentId  Group by f.FullName")
    Flowable<List<AttendanceEntity>> getAbsentDepartmentList(String date,int departmentId);
    @Query("SELECT c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner JOIN UserList as f ON c.UserId = f.UserId where c.UserId not in (SELECT a.UserId from UserActivity as a Inner JOIN UserList as f ON a.UserId = f.UserId  where WorkingDate=:date) AND f.UnitId=:unitId AND f.DepartmentId=:departmentId   Group by f.FullName")
    Flowable<List<AttendanceEntity>> getAbsentUnitDepartmentList(String date,int departmentId,int unitId);
    @Query("SELECT c.PunchInTime,c.PunchInTimeLate,c.PunchInTimeLate,c.PunchOutTime,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND c.PunchInTime>:value AND f.UnitId=:unitId Group by f.FullName")
    Flowable<List<AttendanceEntity>> getLateUnitList(String date,double value,int unitId);
    @Query("SELECT c.PunchInTime,c.PunchInTimeLate,c.PunchInTimeLate,c.PunchOutTime,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND c.PunchInTime>:value AND f.DepartmentId=:departmentId Group by f.FullName")
    Flowable<List<AttendanceEntity>> getLateDepartmentList(String date,double value,int departmentId);
    @Query("SELECT c.PunchInTime,c.PunchInTimeLate,c.PunchInTimeLate,c.PunchOutTime,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND c.PunchInTime>:value AND f.UnitId=:unitId AND f.DepartmentId=:departmentId Group by f.FullName")
    Flowable<List<AttendanceEntity>> getLateUnitDepartmentList(String date,double value,int departmentId,int unitId);
    @Query("SELECT c.PunchInTime,c.PunchInTimeLate,c.PunchInTimeLate,c.PunchOutTime,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND c.PunchInTime<=:value AND f.UnitId=:unitId Group by f.FullName")
    Flowable<List<AttendanceEntity>> getOnTimeUnitList(String date,double value,int unitId);
    @Query("SELECT c.PunchInTime,c.PunchInTimeLate,c.PunchInTimeLate,c.PunchOutTime,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND c.PunchInTime<=:value AND f.DepartmentId=:departmentId Group by f.FullName")
    Flowable<List<AttendanceEntity>> getOnTimeDepartmentList(String date,double value,int departmentId);
    @Query("SELECT c.PunchInTime,c.PunchInTimeLate,c.PunchInTimeLate,c.PunchOutTime,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND c.PunchInTime<=:value  AND f.UnitId=:unitId AND f.DepartmentId=:departmentId Group by f.FullName")
    Flowable<List<AttendanceEntity>> getOnTimeUnitDepartmentList(String date,double value,int departmentId,int unitId);
}
