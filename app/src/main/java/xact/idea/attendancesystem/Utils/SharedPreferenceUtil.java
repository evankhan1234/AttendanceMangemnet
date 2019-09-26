package xact.idea.attendancesystem.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by WIN-HAIVM on 6/12/17.
 */

public class SharedPreferenceUtil {

    private static final String mSharedName = "premo_prefs";
    public static final String TYPE_TOKEN = "token";
    public static final String TYPE_USER_ID = "userid";
    public static final String TYPE_USER_NAME = "username";
    public static void saveShared(Context c, String type, String val) {
        SharedPreferences.Editor ed = c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).edit();
        ed.putString(type, val);
        ed.commit();
    }


    public static void clearUserData(Context c) {
        SharedPreferences.Editor ed = c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).edit();
      //  ed.remove(TYPE_USER_LOGIN);

        ed.commit();

        SharedPreferences.Editor ed1 = c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).edit();
        ed1.clear();
        ed1.commit();
    }

    public static void removeShared(Context c, String type) {
        SharedPreferences.Editor ed = c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).edit();
        ed.remove(type);
        ed.commit();
    }

    public static String getShared(Context c, String type) {
        return c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).getString(type, "");
    }
    public static String getToken(Context c) {
        String val = getShared(c, TYPE_TOKEN);
        return val;
    }
    public static String getUserID(Context c) {
        String val = getShared(c, TYPE_USER_ID);
        return val;
    }public static String getUserName(Context c) {
        String val = getShared(c, TYPE_USER_NAME);
        return val;
    }


}