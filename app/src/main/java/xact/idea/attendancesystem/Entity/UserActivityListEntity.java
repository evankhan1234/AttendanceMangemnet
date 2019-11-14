package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserActivityListEntity {
    @SerializedName("data")
    public ArrayList<Data> data;

    public class Data{
        @SerializedName("UserId")
        public String  UserId;
        @SerializedName("WorkingDate")
        public String  WorkingDate;
        @SerializedName("PunchInLocation")
        public String  PunchInLocation;
        @SerializedName("PunchInTime")
        public String  PunchInTime;
        @SerializedName("PunchOutLocation")
        public String  PunchOutLocation;
        @SerializedName("PunchOutTime")
        public String  PunchOutTime;
        @SerializedName("Duration")
        public String  Duration;
    }
}
