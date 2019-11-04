package xact.idea.attendancesystem.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LeaveApproval")
public class LeaveApproval {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ids")
    public int ids;
    @ColumnInfo(name = "UserId")
    public int  UserId;
    @ColumnInfo(name = "FullName")
    public String  FullName;
    @ColumnInfo(name = "LeaveType")
    public String  LeaveType;
    @ColumnInfo(name = "BackUp")
    public String  BackUp;
    @ColumnInfo(name = "Reason")
    public String  Reason;
    @ColumnInfo(name = "ApplicationDate")
    public String  ApplicationDate;
    @ColumnInfo(name = "UserIcon")
    public String  UserIcon;
    @ColumnInfo(name = "Type")
    public String  Type;
}
