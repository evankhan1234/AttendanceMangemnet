package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class PunchInOutResponseEntity extends APIMessage {
    @SerializedName("exe_status")
    public boolean  exe_status;
}
