package xact.idea.attendancesystem.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "UserList")
public class UserList {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ids")
    public int ids;
    @ColumnInfo(name = "UserId")
    public String  UserId;
    @ColumnInfo(name = "OfficeExt")
    public String  OfficeExt;
    @ColumnInfo(name = "FullName")
    public String  FullName;
    @ColumnInfo(name = "Email")
    public String  Email;
    @ColumnInfo(name = "Password")
    public String  Password;
    @ColumnInfo(name = "AdminStatus")
    public int  AdminStatus;
    @ColumnInfo(name = "Designation")
    public String  Designation;
    @ColumnInfo(name = "JoiningDate")
    public String  JoiningDate;
    @ColumnInfo(name = "CorporateMobileNumber")
    public String  CorporateMobileNumber;

    @ColumnInfo(name = "PersonalMobileNumber")
    public String  PersonalMobileNumber;
    @ColumnInfo(name = "EmergencyContactPerson")
    public String  EmergencyContactPerson;

    @ColumnInfo(name = "RelationWithContactPerson")
    public String  RelationWithContactPerson;
    @ColumnInfo(name = "BloodGroup")
    public String  BloodGroup;
    @ColumnInfo(name = "ProfilePhoto")
    public String  ProfilePhoto;
    @ColumnInfo(name = "UnitId")
    public String  UnitId;
    @ColumnInfo(name = "UnitName")
    public String  UnitName;
    @ColumnInfo(name = "UnitShortName")
    public String  UnitShortName;
    @ColumnInfo(name = "DepartmentId")
    public String  DepartmentId;
    @ColumnInfo(name = "DepartmentName")
    public String  DepartmentName;

    @ColumnInfo(name = "DepartmentShortName")
    public String  DepartmentShortName;
}
