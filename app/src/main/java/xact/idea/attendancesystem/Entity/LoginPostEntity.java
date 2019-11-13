package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class LoginPostEntity {
    @SerializedName("user_name")
    public String  user_name;
    @SerializedName("user_pass")
    public String  user_pass;
}
