package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class LeaveEntity {
    @SerializedName("UserId")
    public int  UserId;
    @SerializedName("FullName")
    public String  FullName;
    @SerializedName("UserIcon")
    public String  UserIcon;

    @SerializedName("Halfday")
    public int  Halfday;
    @SerializedName("Casual")
    public int  Casual;
    @SerializedName("Sick")
    public int  Sick;
    @SerializedName("UnPaid")
    public int  UnPaid;
    @SerializedName("RemainingCasual")
    public int  RemainingCasual;
    @SerializedName("RemainingSick")
    public int  RemainingSick;


}
