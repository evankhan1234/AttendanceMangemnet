package xact.idea.attendancesystem.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.entity.ContentType;
import ch.boye.httpclientandroidlib.entity.mime.MultipartEntityBuilder;
import ch.boye.httpclientandroidlib.entity.mime.content.FileBody;
import xact.idea.attendancesystem.View.CommentDataLayout;


public class MultipartSendImageRequest extends Request<String> {

    private Context mContext = null;
    private HttpEntity mHttpEntity;

    private Response.Listener mListener;

    /**
     * @param context
     * @param url
     * @param method
     * @param llContent
     * @param listener
     * @param errorListener
     * @author MinhTD
     * @function send text and image for comment
     */
    public MultipartSendImageRequest(Context context, String url, int method, LinearLayout llContent,
                                     Response.Listener<String> listener,
                                     Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mContext = context;
        mListener = listener;
        mHttpEntity = buildCommentMultipartEntity(llContent);
    }

    public MultipartSendImageRequest(Context context, String url, int method, File imageFile, String param, Response.Listener<String> listener,
                                     Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mContext = context;
        mListener = listener;
        mHttpEntity = buildSingleSendImageEntity(imageFile, param);
    }

    private HttpEntity buildCommentMultipartEntity(LinearLayout llContent) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        for (int i = 0; i < llContent.getChildCount(); i++) {
            CommentDataLayout layout = (CommentDataLayout) llContent.getChildAt(i);
            String data = layout.mEditContent.getText().toString();
            builder.addTextBody(Constant.Params.TEXT_BODY + i, data, ContentType.TEXT_PLAIN.withCharset("UTF-8"));
            if (layout.getFile() != null) {
                FileBody fileBody = new FileBody(layout.getFile());
                builder.addPart(Constant.Params.FILE_BODY + i, fileBody);
                Log.e(Constant.Params.FILE_BODY + i, layout.getFile().getName());
            }
        }
        return builder.build();
    }

    private HttpEntity buildSingleSendImageEntity(File imageFile, String param) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        FileBody fileBody = new FileBody(imageFile);
        builder.addPart(param, fileBody);
        Log.e(param, imageFile.getName());
        return builder.build();
    }

    @Override
    public String getBodyContentType() {
        return mHttpEntity.getContentType().getValue();
    }

//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        return Utils.getDefaultHeaders(mContext);
//    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mHttpEntity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
            Log.e("Write exception", e.toString());
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}