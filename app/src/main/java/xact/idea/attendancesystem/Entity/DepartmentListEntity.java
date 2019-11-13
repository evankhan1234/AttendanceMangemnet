package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DepartmentListEntity {

    @SerializedName("rowCount")
    public int  rowCount;

    @SerializedName("data")
    public ArrayList<Data>  data;


    public class Data{
        @SerializedName("Id")
        public int  Id;
        @SerializedName("DepartmentName")
        public String  DepartmentName;
        @SerializedName("DeptShortName")
        public String  DeptShortName;
        @SerializedName("UnitId")
        public int  UnitId;
        @SerializedName("Unitname")
        public String  Unitname;
        @SerializedName("UnitShortName")
        public String  UnitShortName;

        @Override
        public String toString() {
            return DepartmentName==null?"":DepartmentName;
        }
    }
}
