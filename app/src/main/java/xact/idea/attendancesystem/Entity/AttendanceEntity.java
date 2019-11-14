package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class AttendanceEntity {
    @SerializedName("FullName")
    public String  FullName;
    @SerializedName("UserId")
    public String  UserId;
    @SerializedName("WorkingDate")
    public String  WorkingDate;
    @SerializedName("PunchInLocation")
    public String  PunchInLocation;
    @SerializedName("PunchInTimeLate")
    public String  PunchInTimeLate;
    @SerializedName("PunchInTime")
    public String  PunchInTime;
    @SerializedName("PunchOutLocation")
    public String  PunchOutLocation;
    @SerializedName("PunchOutTime")
    public String  PunchOutTime;
    @SerializedName("Duration")
    public String  Duration;
}
