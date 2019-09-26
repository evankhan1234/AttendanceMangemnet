package xact.idea.attendancesystem.Utils;

import android.content.Context;
import android.util.Log;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.lang.reflect.Method;
import java.util.Map;

public class CustomStringRequest extends StringRequest {

    private Context mContext = null;
    private String userId = "";
    private String authorization = "";

    public CustomStringRequest(int method, String url, Response.Listener<String> listener,
                               Response.ErrorListener errorListener, Context context) {
        super(method, url, listener, errorListener);
        mContext = context;
    }

    public CustomStringRequest(int method, String url, Response.Listener<String> listener,
                               Response.ErrorListener errorListener, Context context, String userId, String authorization) {
        super(method, url, listener, errorListener);
        mContext = context;
        this.userId = userId;
        this.authorization = authorization;
    }

    public CustomStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Context context) {
        super(Method.GET, url, listener, errorListener);
        mContext = context;
    }

    public CustomStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Context context, String userId, String authorization) {
        super(Method.GET, url, listener, errorListener);
        mContext = context;
        this.userId = userId;
        this.authorization = authorization;
    }


//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        if ((userId + authorization).length() > 0) {
//
//            Log.e("Default Header","call 1");
//            return Utils.getDefaultHeaders(userId, authorization);
//        }
//        Log.e("Default Header","call 2");
//        return Utils.getDefaultHeaders(userId, authorization);
//    }
}