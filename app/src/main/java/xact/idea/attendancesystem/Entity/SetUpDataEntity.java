package xact.idea.attendancesystem.Entity;

import com.google.gson.annotations.SerializedName;

public class SetUpDataEntity extends APIMessage{
    @SerializedName("data")
    public Data  data;

    public class Data{
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
}
