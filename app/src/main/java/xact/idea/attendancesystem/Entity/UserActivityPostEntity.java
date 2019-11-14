package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class UserActivityPostEntity {
    @SerializedName("user_id")
    public String  user_id;
    @SerializedName("from_date")
    public String  from_date;
    @SerializedName("to_date")
    public String  to_date;
}
