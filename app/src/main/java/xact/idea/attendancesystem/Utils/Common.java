package xact.idea.attendancesystem.Utils;

import xact.idea.attendancesystem.Database.DataSources.DepartmentRepository;
import xact.idea.attendancesystem.Database.DataSources.UnitRepository;
import xact.idea.attendancesystem.Database.Local.MainDatabase;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Retrofit.RetrofitClient;

public class Common {
    public static MainDatabase mainDatabase;
    public static DepartmentRepository departmentRepository;
    public static UnitRepository unitRepository;
    public static final String BASE_URL="https://api.myjson.com/bins/";
    //   private static final String BASE_URL="http://192.168.1.244:8888/drinkshop/";

    public static IRetrofitApi getApi(){
        return RetrofitClient.getClient(BASE_URL).create(IRetrofitApi.class);
    }
}
