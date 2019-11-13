package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class LoginEntity extends APIMessage {
    @SerializedName("data")
    public Data  data;
    @SerializedName("message")
    public String  message;

    public class Data {
        @SerializedName("id")
        public String  id;
        @SerializedName("name")
        public String  name;
        @SerializedName("email")
        public String  email;
        @SerializedName("password")
        public String  password;
        @SerializedName("admin")
        public String  admin;
        @SerializedName("mobile")
        public String  mobile;
        @SerializedName("remember_token")
        public String  remember_token;
        @SerializedName("active_status")
        public String  active_status;



    }

}
