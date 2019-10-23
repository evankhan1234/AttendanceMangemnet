package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class UserTotalLeaveEntity {
    public int  Casual;
    @SerializedName("Sick")
    public int  Sick;
    @SerializedName("Total")
    public int  Total;
}
