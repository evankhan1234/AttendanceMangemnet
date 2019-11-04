package xact.idea.attendancesystem.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RemainingLeave")
public class RemainingLeave{
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ids")
    public int ids;
    @ColumnInfo(name = "RemainingCasual")
    public int  Casual;
    @ColumnInfo(name = "RemainingSick")
    public int  Sick;
}
