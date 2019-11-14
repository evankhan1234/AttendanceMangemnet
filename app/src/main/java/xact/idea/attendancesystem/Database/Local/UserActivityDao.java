package xact.idea.attendancesystem.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Entity.AttendanceEntity;
import xact.idea.attendancesystem.Entity.LeaveEntity;

@Dao
public interface UserActivityDao {
    @Query("SELECT * FROM UserActivity")
    Flowable<List<UserActivity>> getUserActivityItems();

    @Query("SELECT * FROM UserActivity WHERE ids=:UserActivityItemId")
    Flowable<List<UserActivity>> getUserActivityItemById(int UserActivityItemId);


    @Query("Select *  FROM UserActivity")
    int value();


    @Query("DELETE  FROM UserActivity")
    void emptyUserActivity();

    @Insert
    void insertToUserActivity(UserActivity...UserActivitys);

    @Update
    void updateUserActivity(UserActivity...UserActivitys);

    @Delete
    void deleteUserActivityItem(UserActivity...UserActivitys);

    @Query("SELECT * from UserActivity")
        //@Query("SELECT * from UserActivity as c Inner  JOIN Favorite as f ON c.Id = f.id  WHERE f.id=:favoriteid")
    Flowable<List<UserActivity>> getUserActivity();

    @Query("SELECT  c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId Group by f.FullName ")
    Flowable<List<AttendanceEntity>> getList();
    @Query("SELECT c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date Group by f.FullName ")
    Flowable<List<AttendanceEntity>> getPresentList(String date);
    @Query("SELECT c.PunchInTime,c.PunchOutTime,c.PunchInTimeLate,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate NOT IN  (:date) Group by f.FullName")
    Flowable<List<AttendanceEntity>> getAbsentList(String date);
    @Query("SELECT c.PunchInTime,c.PunchInTimeLate,c.PunchInTimeLate,c.PunchOutTime,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND c.PunchInTime>:value Group by f.FullName")
    Flowable<List<AttendanceEntity>> getLateList(String date,double value);
    @Query("SELECT c.PunchInTime,c.PunchInTimeLate,c.PunchInTimeLate,c.PunchOutTime,c.Duration,c.PunchInLocation,f.FullName from UserActivity as c Inner  JOIN UserList as f ON c.UserId = f.UserId where WorkingDate=:date AND c.PunchInTime<=:value Group by f.FullName")
    Flowable<List<AttendanceEntity>> getOnTimeList(String date,double value);
}
