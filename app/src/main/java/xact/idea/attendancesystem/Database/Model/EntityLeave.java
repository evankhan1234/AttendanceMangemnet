package xact.idea.attendancesystem.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "EntityLeave")
public class EntityLeave{
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ids")
    public int ids;
    @ColumnInfo(name = "Halfday")
    public int  Halfday;
    @ColumnInfo(name = "Casual")
    public int  Casual;
    @ColumnInfo(name = "Sick")
    public int  Sick;
    @ColumnInfo(name = "UnPaid")
    public int  UnPaid;
}
