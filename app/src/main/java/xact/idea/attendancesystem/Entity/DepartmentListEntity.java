package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class DepartmentListEntity {
    @SerializedName("Id")
    public int  Id;
    @SerializedName("DepartmentName")
    public String  DepartmentName;
    @SerializedName("UnitId")
    public int  UnitId;

    @Override
    public String toString() {
        return DepartmentName==null?"":DepartmentName;
    }
}
