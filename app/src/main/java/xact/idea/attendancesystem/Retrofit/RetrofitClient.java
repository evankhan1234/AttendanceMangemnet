package xact.idea.attendancesystem.Retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xact.idea.attendancesystem.Utils.Utils;

public class RetrofitClient {
    Context context;
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)

                .readTimeout(100,TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit == null) {

            retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(client).
                    addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

        }
        return retrofit;
    }
}
