package xact.idea.attendancesystem.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "Unit")
public class Unit {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ids")
    public int ids;
    @ColumnInfo(name = "Id")
    public int Id;
    @ColumnInfo(name = "UnitName")
    public String UnitName;
    @ColumnInfo(name = "ShortName")
    public String ShortName;

    @Override
    public String toString() {
        return UnitName;
    }
}
