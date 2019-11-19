package xact.idea.attendancesystem.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "SetUp")
public class SetUp {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ids")
    public int ids;
    @SerializedName("EXPECTED_DURATION")
    public String  EXPECTED_DURATION;
    @SerializedName("OFFICE_IN_TIME")
    public String  OFFICE_IN_TIME;
    @SerializedName("OFFICE_OUT_TIME")
    public String  OFFICE_OUT_TIME;
    @SerializedName("HALFDAY_DURATION")
    public String  HALFDAY_DURATION;
    @SerializedName("GRACE_TIME")
    public String  GRACE_TIME;
    @SerializedName("ENTITLED_LEAVE_CASUAL")
    public String  ENTITLED_LEAVE_CASUAL;
    @SerializedName("ENTITLED_LEAVE_SICK")
    public String  ENTITLED_LEAVE_SICK;
    @SerializedName("ENTITLED_LEAVE_TOTAL")
    public String  ENTITLED_LEAVE_TOTAL;
}
