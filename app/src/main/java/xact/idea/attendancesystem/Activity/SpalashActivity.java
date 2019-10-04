package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.Constant;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.SharedPreferenceUtil;

public class SpalashActivity extends AppCompatActivity {
    private Context mContext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash);

        mContext = this;
        CorrectSizeUtil.getInstance(this).correctSize();
        CorrectSizeUtil.getInstance(this).correctSize(findViewById(R.id.rlt_root));
    }

    @Override
    protected void onResume() {
        super.onResume();
        goNextScreen();
    }

    private void goNextScreen() {
        if (SharedPreferenceUtil.getUserID(SpalashActivity.this).equals("") || SharedPreferenceUtil.getUserID(SpalashActivity.this)==null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    goToLoginPage();
                }
            }, Constant.SPLASH_TIME);
        } else if (SharedPreferenceUtil.getUserID(SpalashActivity.this).equals("1")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    goToLoginPage();
                }
            }, Constant.SPLASH_TIME);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToMainPage();
                }
            }, Constant.SPLASH_TIME);
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                goToLoginPage();
//            }
//        }, Constant.SPLASH_TIME);
    }

    private void goToLoginPage() {

        Intent i = new Intent(SpalashActivity.this, OnBoardingActivity.class);
        startActivity(i);
        finish();
    }
    private void goToMainPage() {

        Intent i = new Intent(SpalashActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
