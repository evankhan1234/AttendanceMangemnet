package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class UnitListEntity {
    @SerializedName("Id")
    public int  Id;
    @SerializedName("UnitName")
    public String  UnitName;
    @Override
    public String toString() {
        return UnitName==null?"":UnitName;
    }
}
