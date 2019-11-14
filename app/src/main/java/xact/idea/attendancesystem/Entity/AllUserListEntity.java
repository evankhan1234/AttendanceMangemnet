package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllUserListEntity extends APIMessage{


    @SerializedName("data")
    public ArrayList<Data> data;

    public class Data{
        @SerializedName("UserId")
        public String  UserId;
        @SerializedName("FullName")
        public String  FullName;
        @SerializedName("Email")
        public String  Email;
        @SerializedName("OfficeExt")
        public String  OfficeExt;
        @SerializedName("Password")
        public String  Password;
        @SerializedName("AdminStatus")
        public int  AdminStatus;
        @SerializedName("Designation")
        public String  Designation;
        @SerializedName("JoiningDate")
        public String  JoiningDate;
        @SerializedName("CorporateMobileNumber")
        public String  CorporateMobileNumber;

        @SerializedName("PersonalMobileNumber")
        public String  PersonalMobileNumber;
        @SerializedName("EmergencyContactPerson")
        public String  EmergencyContactPerson;

        @SerializedName("RelationWithContactPerson")
        public String  RelationWithContactPerson;
        @SerializedName("BloodGroup")
        public String  BloodGroup;
        @SerializedName("ProfilePhoto")
        public String  ProfilePhoto;
        @SerializedName("UnitId")
        public String  UnitId;
        @SerializedName("UnitName")
        public String  UnitName;
        @SerializedName("UnitShortName")
        public String  UnitShortName;
        @SerializedName("DepartmentId")
        public String  DepartmentId;
        @SerializedName("DepartmentName")
        public String  DepartmentName;

        @SerializedName("DepartmentShortName")
        public String  DepartmentShortName;
    }


}
