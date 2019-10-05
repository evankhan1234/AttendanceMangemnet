package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class UserDetailsEntity {
    @SerializedName("Fullname")
    public String  Fullname;
    @SerializedName("Gender")
    public String  Gender;
    @SerializedName("DateOfBirth")
    public String  DateOfBirth;
    @SerializedName("Email")
    public String  Email;
    @SerializedName("Phone")
    public String  Phone;
    @SerializedName("Address")
    public String  Address;
    @SerializedName("IconPath")
    public String  IconPath;

}
