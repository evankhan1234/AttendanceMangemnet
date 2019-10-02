package xact.idea.attendancesystem.Utils;

import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Retrofit.RetrofitClient;

public class Common {
    public static final String BASE_URL="http://10.0.2.2:8888/drinkshop/";
    //   private static final String BASE_URL="http://192.168.1.244:8888/drinkshop/";

    public static IRetrofitApi getApi(){
        return RetrofitClient.getClient(BASE_URL).create(IRetrofitApi.class);
    }
}
