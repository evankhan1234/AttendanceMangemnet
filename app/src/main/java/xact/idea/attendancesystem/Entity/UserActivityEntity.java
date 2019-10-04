package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class UserActivityEntity {
    @SerializedName("Date")
    public String  Date;
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
