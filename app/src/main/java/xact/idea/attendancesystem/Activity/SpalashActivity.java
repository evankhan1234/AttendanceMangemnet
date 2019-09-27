package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.Constant;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                goToLoginPage();
            }
        }, Constant.SPLASH_TIME);
    }

    private void goToLoginPage() {

        Intent i = new Intent(SpalashActivity.this, OnBoardingActivity.class);
        startActivity(i);
        finish();
    }

}
