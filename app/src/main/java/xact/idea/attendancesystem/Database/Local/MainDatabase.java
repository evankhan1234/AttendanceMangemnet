package xact.idea.attendancesystem.Database.Local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import xact.idea.attendancesystem.Database.DataSources.Converters;
import xact.idea.attendancesystem.Database.Model.Department;
import xact.idea.attendancesystem.Database.Model.EntityLeave;
import xact.idea.attendancesystem.Database.Model.LeaveSummary;
import xact.idea.attendancesystem.Database.Model.RemainingLeave;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Database.Model.UserActivity;
import xact.idea.attendancesystem.Database.Model.UserList;

@Database(entities = {Department.class, Unit.class, LeaveSummary.class, EntityLeave.class, RemainingLeave.class, UserList.class, UserActivity.class},version = 2)
@TypeConverters({Converters.class})
public abstract class MainDatabase extends RoomDatabase {

   public abstract DepartmentDao departmentDao();
    public abstract UnitDao unitDao();
    public abstract UserListDao userListDao();
    public abstract UserActivityDao userActivityDao();
    public abstract LeaveSummaryDao leaveSummaryDao();
    public abstract EntityLeaveDao entityLeaveDao();
    public abstract RemainingLeaveDao remainingLeaveDao();
    private static MainDatabase instance;

    public static MainDatabase getInstance(Context context){
        instance= Room.databaseBuilder(context,MainDatabase.class,"XactIdea").allowMainThreadQueries().build();

        return instance;

    }


}
