package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class PunchInOutPostEntity {
    @SerializedName("user_id")
    public int  user_id;
    @SerializedName("punch_date_time")
    public String  punch_date_time;
    @SerializedName("unit_id")
    public int  unit_id;
    @SerializedName("punch_type")
    public String  punch_type;
    @SerializedName("work_station")
    public String  work_station;
    @SerializedName("comment")
    public String  comment;
}
