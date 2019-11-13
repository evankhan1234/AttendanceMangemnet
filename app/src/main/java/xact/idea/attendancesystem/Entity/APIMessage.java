package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class APIMessage {
    @SerializedName("status_code")
    public int  status_code;
    @SerializedName("message")
    public String  message;
}
