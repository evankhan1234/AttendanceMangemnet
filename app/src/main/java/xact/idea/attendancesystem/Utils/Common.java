package xact.idea.attendancesystem.Utils;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import xact.idea.attendancesystem.Database.DataSources.DepartmentRepository;
import xact.idea.attendancesystem.Database.DataSources.EntityLeaveRepository;
import xact.idea.attendancesystem.Database.DataSources.LeaveSummaryRepository;
import xact.idea.attendancesystem.Database.DataSources.RemainingLeaveRepository;
import xact.idea.attendancesystem.Database.DataSources.SetUpDataRepository;
import xact.idea.attendancesystem.Database.DataSources.UnitRepository;
import xact.idea.attendancesystem.Database.DataSources.UserActivityRepository;
import xact.idea.attendancesystem.Database.DataSources.UserListRepository;
import xact.idea.attendancesystem.Database.Local.MainDatabase;
import xact.idea.attendancesystem.Reciver.MyReceiver;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Retrofit.RetrofitClient;

public abstract class Common {
    public static MainDatabase mainDatabase;
    public static DepartmentRepository departmentRepository;
    public static UserListRepository userListRepository;
    public static UserActivityRepository userActivityRepository;
    public static UnitRepository unitRepository;
    public static SetUpDataRepository setUpDataRepository;
    public static LeaveSummaryRepository leaveSummaryRepository;
    public static EntityLeaveRepository entityLeaveRepository;
    public static RemainingLeaveRepository remainingLeaveRepository;
    public static final String BASE_URL="https://api.myjson.com/bins/";
    public static final String BASE_URL_XACT="http://emp.xactidea.com/mobile-app/api/";

    public static IRetrofitApi getApi(){
        return RetrofitClient.getClient(BASE_URL).create(IRetrofitApi.class);
    }
    public static IRetrofitApi getApiXact(){
        return RetrofitClient.getClient(BASE_URL_XACT).create(IRetrofitApi.class);
    }
}
