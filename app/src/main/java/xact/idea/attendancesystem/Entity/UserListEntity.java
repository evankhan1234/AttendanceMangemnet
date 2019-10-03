package xact.idea.attendancesystem.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserListEntity implements Parcelable {
    @SerializedName("UserId")
    public int  UserId;
    @SerializedName("FullName")
    public String  FullName;
    @SerializedName("Email")
    public String  Email;
    @SerializedName("OfficeExt")
    public String  OfficeExt;
    @SerializedName("PhoneNumber")
    public String  PhoneNumber;
    @SerializedName("Designation")
    public String  Designation;

    @SerializedName("UnitName")
    public String  UnitName;
    @SerializedName("DepartmentName")
    public String  DepartmentName;
    @SerializedName("UserIcon")
    public String  UserIcon;

    protected UserListEntity(Parcel in) {
        UserId = in.readInt();
        FullName = in.readString();
        Email = in.readString();
        OfficeExt = in.readString();
        PhoneNumber = in.readString();
        Designation = in.readString();
        UnitName = in.readString();
        DepartmentName = in.readString();
        UserIcon = in.readString();
    }

    public static final Creator<UserListEntity> CREATOR = new Creator<UserListEntity>() {
        @Override
        public UserListEntity createFromParcel(Parcel in) {
            return new UserListEntity(in);
        }

        @Override
        public UserListEntity[] newArray(int size) {
            return new UserListEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(UserId);
        parcel.writeString(FullName);
        parcel.writeString(Email);
        parcel.writeString(OfficeExt);
        parcel.writeString(PhoneNumber);
        parcel.writeString(Designation);
        parcel.writeString(UnitName);
        parcel.writeString(DepartmentName);
        parcel.writeString(UserIcon);
    }
}
