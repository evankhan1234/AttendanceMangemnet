package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LeaveSummaryEntity {
    @SerializedName("UserId")
    public int  UserId;
    @SerializedName("FullName")
    public String  FullName;
    @SerializedName("UserIcon")
    public String  UserIcon;
    @SerializedName("EntityLeave")
    public  EntityLeave entityLeaves;
    @SerializedName("RemainingLeave")
    public RemainingLeave remainingLeaves;

    public class EntityLeave{
        @SerializedName("Halfday")
        public int  Halfday;
        @SerializedName("Casual")
        public int  Casual;
        @SerializedName("Sick")
        public int  Sick;
        @SerializedName("UnPaid")
        public int  UnPaid;
    }
    public class RemainingLeave{
        @SerializedName("Casual")
        public int  Casual;
        @SerializedName("Sick")
        public int  Sick;
    }

}
