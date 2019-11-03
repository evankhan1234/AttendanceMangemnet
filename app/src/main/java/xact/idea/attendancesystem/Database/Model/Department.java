package xact.idea.attendancesystem.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Department")
public class Department {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ids")
    public int ids;
    @ColumnInfo(name = "Id")
    public int Id;
    @ColumnInfo(name = "DepartmentName")
    public String DepartmentName;
    @ColumnInfo(name = "UnitId")
    public int UnitId;
}
