package xact.idea.attendancesystem.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "UserActivity")
public class UserActivity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ids")
    public int ids;
    @ColumnInfo(name = "UserId")
    public String  UserId;
    @ColumnInfo(name = "WorkingDate")
    public String  WorkingDate;
    @ColumnInfo(name = "PunchInLocation")
    public String  PunchInLocation;
    @ColumnInfo(name = "PunchInTime")
    public double  PunchInTime;
    @ColumnInfo(name = "PunchInTimeLate")
    public String  PunchInTimeLate;
    @ColumnInfo(name = "PunchOutLocation")
    public String  PunchOutLocation;
    @ColumnInfo(name = "PunchOutTime")
    public String  PunchOutTime;
    @ColumnInfo(name = "Duration")
    public String  Duration;
}
