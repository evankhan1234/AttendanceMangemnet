package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UnitListEntity {
    @SerializedName("rowCount")
    public int  rowCount;

    @SerializedName("data")
    public ArrayList<Data> data;


    public class Data{

        @SerializedName("Id")
        public int  Id;
        @SerializedName("UnitName")
        public String  UnitName;
        @SerializedName("ShortName")
        public String  ShortName;
        @Override
        public String toString() {
            return UnitName==null?"":UnitName;
        }
    }
}
