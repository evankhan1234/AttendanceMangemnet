package xact.idea.attendancesystem.Database.Local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import xact.idea.attendancesystem.Database.Model.Department;
import xact.idea.attendancesystem.Database.Model.Unit;

@Database(entities = {Department.class, Unit.class},version = 1)
public abstract class MainDatabase extends RoomDatabase {

   public abstract DepartmentDao departmentDao();
    public abstract UnitDao unitDao();
    private static MainDatabase instance;

    public static MainDatabase getInstance(Context context){
        instance= Room.databaseBuilder(context,MainDatabase.class,"XactIdea").allowMainThreadQueries().build();

        return instance;

    }


}
