package xact.idea.attendancesystem.Database.DataSources;

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
    void insertToUserActivity(UserActivity... UserActivitys);


    void updateUserActivity(UserActivity... UserActivitys);


    void deleteUserActivityItem(UserActivity... UserActivitys);
}
