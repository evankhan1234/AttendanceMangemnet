package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Body;
import xact.idea.attendancesystem.Database.DataSources.DepartmentRepository;
import xact.idea.attendancesystem.Database.DataSources.UnitRepository;
import xact.idea.attendancesystem.Database.Local.DepartmentDataSource;
import xact.idea.attendancesystem.Database.Local.MainDatabase;
import xact.idea.attendancesystem.Database.Local.UnitDataSource;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Database.Model.UserList;
import xact.idea.attendancesystem.Entity.LoginEntity;
import xact.idea.attendancesystem.Entity.LoginPostEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.Constant;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.DLog;
import xact.idea.attendancesystem.Utils.SharedPreferenceUtil;
import xact.idea.attendancesystem.Utils.Utils;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;

public class LoginActivity extends AppCompatActivity {
    AppCompatImageView btn_show_password;
    TextInputEditText edt_password;
    TextInputEditText edt_email;
    boolean test=true;
    Button btn_login;
    TextInputLayout input_password;
    TextInputLayout input_email;
    TextView text_login;
    TextView text_password;
    TextView text_signup;
    View mLoading = null;
    private Context mContext = null;
    IRetrofitApi mService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mService = Common.getApiXact();
        CorrectSizeUtil.getInstance(this).correctSize();
        CorrectSizeUtil.getInstance(this).correctSize(findViewById(R.id.rlt_root));
        InitView();
        initLoading();
        initDB();
    }
    private void initDB() {
        Common.mainDatabase = MainDatabase.getInstance(this);
        Common.departmentRepository = DepartmentRepository.getInstance(DepartmentDataSource.getInstance(Common.mainDatabase.departmentDao()));
        Common.unitRepository = UnitRepository.getInstance(UnitDataSource.getInstance(Common.mainDatabase.unitDao()));

    }
    private void InitView() {
        btn_show_password=findViewById(R.id.btn_show_password);
        input_password=findViewById(R.id.input_password);
        input_email=findViewById(R.id.input_email);
        btn_login=findViewById(R.id.btn_login);
        edt_password=findViewById(R.id.edt_password);
        edt_email=findViewById(R.id.edt_email);
        text_login=findViewById(R.id.text_login);
        text_password=findViewById(R.id.text_password);
        text_signup=findViewById(R.id.text_signup);


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "avenir_45_book.ttf");

        text_login.setTypeface(custom_font);
        text_password.setTypeface(custom_font);
        text_signup.setTypeface(custom_font);
        btn_login.setTypeface(custom_font);

        edt_password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                btn_show_password.setImageDrawable(getResources().getDrawable(R.drawable.show_password));
                //  edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        btn_show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int cursorPosition = edt_password.getSelectionStart();

                if(test){
                    Log.e("show","show");

                    test=false;
                    edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btn_show_password.setImageDrawable(getResources().getDrawable(R.drawable.hidden_password));

                }
                else{
                    Log.e("hidden","hidden");
                    btn_show_password.setImageDrawable(getResources().getDrawable(R.drawable.show_password));
                    test=true;
                    edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                edt_password.setSelection(cursorPosition);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //input_password.setError("You need to enter a name");
                if (verifyInput()) {
                 doLogin();

                }
            }
        });

//        text_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
//                startActivity(i);
//            }
//        });

    }
    public void initLoading() {
        mLoading = findViewById(R.id.rlt_loading);

        if (mLoading != null) {
            mLoading.setOnClickListener(null);
        }
    }
    public void showLoading() {
        if (mLoading != null) {
            mLoading.setVisibility(View.VISIBLE);
        }

    }
    public void hideLoading() {
        if (mLoading != null) {
            mLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      //  Constant.test = "test";
                      //  finish();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void doLogin(){
        showLoadingProgress(this);
        final String email = edt_email.getText().toString().trim();
        final String password = edt_password.getText().toString();
        LoginPostEntity loginPostEntity= new LoginPostEntity();
        loginPostEntity.user_name=email;
        loginPostEntity.user_pass=password;

        String md5Password=getHashPassWordMD5(password);
        UserList userList;
         userList=Common.userListRepository.getUserListByEmail(email);
        if (userList!=null){
            SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.TYPE_USER_ID, userList.FullName + "");
            SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.USER_EMAIL, userList.Email + "");
            SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.USER_PIC, userList.ProfilePhoto + "");
            SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.TYPE_ADMIN, String.valueOf(userList.AdminStatus) + "");
            SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.USER_ID, userList.UserId + "");
            goMainScreen();
            dismissLoadingProgress();
        }
        else {
            Toast.makeText(mContext, "Username and Password Incorrect", Toast.LENGTH_SHORT).show();
            dismissLoadingProgress();
        }
//        compositeDisposable.add(Common.userListRepository.login(email,password).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<Unit>>() {
//            @Override
//            public void accept(List<Unit> units) throws Exception {
//                displayUnitItems(units);
//            }
//        }));
//        compositeDisposable.add(mService.Login(loginPostEntity).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<LoginEntity>() {
//            @Override
//            public void accept(LoginEntity loginEntity) throws Exception {
//                int code=loginEntity.status_code;
//
//                switch (code){
//                    case 200:
//                        SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.TYPE_USER_ID, edt_email.getText().toString() + "");
//                        goMainScreen();
//                        dismissLoadingProgress();
//                        break;
//                    case 203:
//                        Toast.makeText(mContext, loginEntity.message, Toast.LENGTH_SHORT).show();
//                        Log.e("pass","pass"+getHashPassWordMD5(password));
//                        dismissLoadingProgress();
//                        break;
////                        SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.TYPE_USER_ID, edt_email.getText().toString() + "");
////                        goMainScreen();
//                }
//
//
//
//            }
//
//
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//
//                Log.e("Exception","Exception"+throwable.getMessage());
//            }
//        }));

    }
    private String getHashPassWordMD5(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
//    private void doLogin(final String token) {
//        final String email = edt_email.getText().toString().trim();
//        final String password = edt_password.getText().toString();
//
//        // showLoading();;
//        showLoadingProgress(mContext);
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        String url = Constant.API.LOGIN;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(final String response) {
//                        if (this != null) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                               //     DLog.e("getLoginData", response + "");
//                                    // findViewById(R.id.rlt_auto_login_from_scheme).setVisibility(View.GONE);
//                                    handleLoginResult(response);
//                                    // hideLoading();
//                                    dismissLoadingProgress();
//
//                                }
//                            });
//                        } else {
//                            // ignore
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(final VolleyError error) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                     //   DLog.e("error getLoginData", error.toString() + "");
//                        //   findViewById(R.id.rlt_auto_login_from_scheme).setVisibility(View.GONE);
//                      //  handlerErrorResponse(error);
//                        //hideLoading();
//                        dismissLoadingProgress();
//
//                    }
//                });
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params =new HashMap<>();
//                if (token.length() == 0) {
//                    params.put(Constant.Params.EMAIL, email + "");
//                    params.put(Constant.Params.PASSWORD, password + "");
//                } else {
//                    params.put(Constant.Params.ACTION, Constant.Val.VALUE_LOGIN + "");
//                    params.put(Constant.Params.TOKEN, token + "");
//                }
//
//                Log.e("login param",""+params);
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                return Utils.getDefaultHeaders();
//            }
//
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                Constant.TIME_OUT,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        queue.add(stringRequest);
//
//    }

    private void handleLoginResult(String result) {
//        try {
//            Gson gson = new Gson();
//            UserGsonEntity entity = gson.fromJson(result, UserGsonEntity.class);
//            if (handleBaseResult(entity))
//            {
//                DLog.e("User_id", entity.body.id + "");
//
//                SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.TYPE_USER_ID, entity.body.id + "");
//                SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.TYPE_USER_NAME, entity.body.nick_name + "");
//                SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.TYPE_NAME,
//                        entity.body.name + "");
//                SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.TYPE_TOKEN, "93f6c0270decf24c715d2a5d26018ec4e5f87bec500dff53e392366c19cf64c39bde8d15fbf4b86a6089df699781a57fdaf97bc63bd4ad679687c00f0f3bcf3b"+ "");
//                SharedPreferenceUtil.saveShared(LoginActivity.this, SharedPreferenceUtil.TYPE_ICON_PATH,
//                        entity.body.icon_path + "");
//                goMainScreen();
//            } // no else
//        } catch (Exception e) {
//            e.printStackTrace();
//            // showTitleAlertDialog(getResources().getString(R.string.err_title), getResources().getString(R.string.err_message), null);
//        }
    }

    private void goMainScreen() {

        Intent i = new Intent(this, LoadingActivity.class);
        i.putExtra("LoginActivity","LoginActivity");
        startActivity(i);
        finish();
    }


    private boolean verifyInput() {
        boolean res = true;
        String err_message = "";
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString();
        if (email.length() == 0) {
            input_email.setError("Enter Email address");
            res = false;
        }
        if (res) {
            if (!Utils.isValidEmail(email)) {
                input_email.setError(" Email address format is not correct");
                res = false;
            }
        }
        if (res) {
            if (password.length() == 0) {
                input_password.setError("Enter Password");
                res = false;
            }
        }

        //---------------------

        return res;
    }




}
