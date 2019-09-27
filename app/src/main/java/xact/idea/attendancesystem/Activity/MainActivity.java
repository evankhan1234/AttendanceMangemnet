package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;

public class MainActivity extends AppCompatActivity {
    private Context mContext = null;
    AppCompatImageView btn_footer_coupons;
    TextView tv_coupon_menus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_footer_coupons=findViewById(R.id.btn_footer_coupons);
        tv_coupon_menus=findViewById(R.id.tv_coupon_menus);
        mContext = this;
        CorrectSizeUtil.getInstance(this).correctSize();
        CorrectSizeUtil.getInstance(this).correctSize(findViewById(R.id.rlt_root));
    }
    public void btn_home_clicked(View view) {
        setUpFooter(1);
    }
    private void setUnselectAllmenu() {

//        tv_iot_menu.setSelected(false);
//        tv_chat_menu.setSelected(false);
//        tv_coupon_menu.setSelected(false);
//        tv_my_page_menu.setSelected(false);
//
//        btn_footer_home.setSelected(false);
//        btn_iot_notice.setSelected(false);
//        btn_footer_chat.setSelected(false);
//        btn_footer_coupons.setSelected(false);
//        btn_footer_my_page.setSelected(false);
    }
    public void setUpFooter(int type) {
        setUnselectAllmenu();
        switch (type) {
            case 0:
                btn_footer_coupons.setSelected(true);
                tv_coupon_menus.setSelected(true);
                break;
//            case 1:
//                tv_iot_menu.setSelected(true);
//                btn_iot_notice.setSelected(true);
//                break;
//            case 2:
//                tv_chat_menu.setSelected(true);
//                btn_footer_chat.setSelected(true);
//                break;
//            case 3:
//                tv_coupon_menu.setSelected(true);
//                btn_footer_coupons.setSelected(true);
//                break;
//            case 4:
//                tv_my_page_menu.setSelected(true);
//                btn_footer_my_page.setSelected(true);
//                break;
        }

    }
}
