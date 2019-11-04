package xact.idea.attendancesystem.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "LeaveSummary")
public class LeaveSummary {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ids")
    public int ids;
    @ColumnInfo(name = "UserId")
    public int  UserId;
    @ColumnInfo(name = "FullName")
    public String  FullName;
    @ColumnInfo(name = "UserIcon")
    public String  UserIcon;



}

