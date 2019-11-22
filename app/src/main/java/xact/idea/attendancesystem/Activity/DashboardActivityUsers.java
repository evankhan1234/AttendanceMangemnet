package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

public class DashboardActivityUsers extends AppCompatActivity {
    TextView tv_date;
    LinearLayout linear_more;
    LinearLayout linear_web;
    LinearLayout linear_leave;
    LinearLayout linear_myself;
    LinearLayout linear_users;
    LinearLayout linear_punch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_users);
        CorrectSizeUtil.getInstance(this).correctSize();
        CorrectSizeUtil.getInstance(this).correctSize(findViewById(R.id.root_rlt_dashboard));
        initView();
    }
    private void initView() {
        linear_more =findViewById(R.id.linear_more);
        linear_web =findViewById(R.id.linear_web);
        linear_leave =findViewById(R.id.linear_leave);
        linear_myself =findViewById(R.id.linear_myself);
        linear_users =findViewById(R.id.linear_users);
        linear_punch =findViewById(R.id.linear_punch);
        TextView tv_store =findViewById(R.id.tv_store);
        tv_date=findViewById(R.id.tv_date);
        tv_store.setSelected(true);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        Date date = new Date(System.currentTimeMillis());
        tv_date.setText(formatter.format(date));

        linear_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivityUsers.this, MainActivity.class);
                intent.putExtra("EXTRA_SESSION", "myself");
                startActivity(intent);

            }
        });
        linear_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivityUsers.this, MainActivity.class);
                intent.putExtra("EXTRA_SESSION", "home");
                startActivity(intent);
            }
        });
        linear_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivityUsers.this, MainActivity.class);
                intent.putExtra("EXTRA_SESSION", "users");
                startActivity(intent);
            }
        });
        linear_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivityUsers.this, MainActivity.class);
                intent.putExtra("EXTRA_SESSION", "leave");
                startActivity(intent);
            }
        });
        linear_myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivityUsers.this, MainActivity.class);
                intent.putExtra("EXTRA_SESSION", "punch");
                startActivity(intent);
            }
        });
        linear_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivityUsers.this, WebActivity.class);
                startActivity(intent);
            }
        });
    }
}
