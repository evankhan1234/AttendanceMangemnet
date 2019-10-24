package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class LeaveApprovalListEntity {
    @SerializedName("UserId")
    public int  UserId;
    @SerializedName("FullName")
    public String  FullName;
    @SerializedName("LeaveType")
    public String  LeaveType;
    @SerializedName("BackUp")
    public String  BackUp;
    @SerializedName("Reason")
    public String  Reason;
    @SerializedName("ApplicationDate")
    public String  ApplicationDate;
    @SerializedName("UserIcon")
    public String  UserIcon;
    @SerializedName("Type")
    public String  Type;
}
