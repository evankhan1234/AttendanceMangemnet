package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import xact.idea.attendancesystem.Fragment.HomeFragment;
import xact.idea.attendancesystem.Fragment.MoreFragment;
import xact.idea.attendancesystem.Fragment.SetUpFragment;
import xact.idea.attendancesystem.Fragment.UserActivityFragment;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Utils.Constant;
import xact.idea.attendancesystem.Utils.CorrectSizeUtil;
import xact.idea.attendancesystem.Utils.Utils;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private FragmentManager mFragManager;
    private FragmentTransaction fragTransaction = null;
    public Fragment mCurrentFrag;
    private Context mContext = null;
    private AppCompatImageView btn_footer_home;
    private AppCompatImageView btn_footer_setUp;
    private AppCompatImageView btn_footer_user_activity;
    private AppCompatImageView btn_footer_more;
    private TextView tv_home_menu;
    private TextView tv_setup_menu;
    private TextView tv_user_activity_menu;
    private TextView tv_more_menu;
    private TextView title;
    public static final int HOME_BTN = 0;
    public static final int SET_UP_BTN = 1;
    public static final int USER_BTN = 2;
    public static final int MORE_BTN = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title=findViewById(R.id.title);
        btn_footer_home=findViewById(R.id.btn_footer_home);
        btn_footer_setUp=findViewById(R.id.btn_footer_setup);
        btn_footer_user_activity=findViewById(R.id.btn_footer_user);
        btn_footer_more=findViewById(R.id.btn_footer_more);

        tv_home_menu=findViewById(R.id.tv_home_menu);
        tv_setup_menu=findViewById(R.id.tv_setup_menus);
        tv_user_activity_menu=findViewById(R.id.tv_user_menu);
        tv_more_menu=findViewById(R.id.tv_more_menus);
        mContext = this;
        CorrectSizeUtil.getInstance(this).correctSize();
        CorrectSizeUtil.getInstance(this).correctSize(findViewById(R.id.rlt_root));
        afterClickTabItem(Constant.FRAG_HOME, null);
        btn_footer_home.setSelected(true);
        tv_home_menu.setSelected(true);
    }
    private void setUnselectAllmenu() {
        tv_home_menu.setSelected(false);
        tv_setup_menu.setSelected(false);
        tv_user_activity_menu.setSelected(false);
        tv_more_menu.setSelected(false);


        btn_footer_home.setSelected(false);
        btn_footer_setUp.setSelected(false);
        btn_footer_user_activity.setSelected(false);
        btn_footer_more.setSelected(false);

    }

    public void setUpFooter(int type) {
        setUnselectAllmenu();
        switch (type) {
            case 0:
                tv_home_menu.setSelected(true);
                btn_footer_home.setSelected(true);
                break;
            case 1:
                tv_setup_menu.setSelected(true);
                btn_footer_setUp.setSelected(true);
                break;
            case 2:
                tv_user_activity_menu.setSelected(true);
                btn_footer_user_activity.setSelected(true);
                break;
            case 3:
                tv_more_menu.setSelected(true);
                btn_footer_more.setSelected(true);
                break;

        }

    }

//    }
    public void btn_home_clicked(View view) {
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = true;
        setUpFooter(HOME_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_HOME, null);
        // checkToGetTicket(false);
        title.setText("Home");

    }

    public void btn_setup_clicked(View view) {
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = false;
        setUpFooter(SET_UP_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_SET_UP, null);
        // checkToGetTicket(false);
        title.setText("Set Up");


    }
    public void btn_user_clicked(View view) {
        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = false;
        setUpFooter(USER_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_USER_ACTIVTY, null);
        // checkToGetTicket(false);
        title.setText("User Activity");
    }
    public void btn_more_clicked(View view) {

        Log.e(TAG, "Home Button Clicked");
        Utils.is_home = false;
        setUpFooter(MORE_BTN);
        //show the initial home page
        afterClickTabItem(Constant.FRAG_MORE, null);
        // checkToGetTicket(false);
        title.setText("More");
    }

    public void afterClickTabItem(int fragId, Object obj) {
        addFragment(fragId, false);
    }
    public void addFragment(int fragId, boolean isHasAnimation) {
        // init fragment manager
        mFragManager = getSupportFragmentManager();
        // create transaction
        fragTransaction = mFragManager.beginTransaction();

        // init argument


        //check if there is any backstack if yes then remove it
        int count = mFragManager.getBackStackEntryCount();
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            mFragManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }


        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag.getTag() != null && mCurrentFrag.getTag().equals(String.valueOf(fragId))) {
            return;
        }

        Fragment newFrag = null;
        // identify which fragment will be called
        switch (fragId) {
            case Constant.FRAG_HOME:
                newFrag = new HomeFragment();
                break;
            case Constant.FRAG_SET_UP:
                newFrag = new SetUpFragment();
                //   newFrag = new AlertFragment();
                //  SharedPreferenceUtil.saveShared(getApplicationContext(), Constant.UNREAD_NOTICE, "0");
                //  setUnreadMessage();
                break;
            case Constant.FRAG_USER_ACTIVTY:
                newFrag = new UserActivityFragment();
                // newFrag = new ChatCategoryFragment();
                //setUpHeader(Constant.FRAG_CHAT);

                break;
            case Constant.FRAG_MORE:
                newFrag = new MoreFragment();
                // newFrag = new ChatCategoryFragment();
                //setUpHeader(Constant.FRAG_CHAT);

                break;

            default:
                break;
        }


        // param 1: container id, param 2: new fragment, param 3: fragment id
        fragTransaction.replace(R.id.main_container, newFrag, String.valueOf(fragId));
        // prevent showed when user press back fabReview
        fragTransaction.addToBackStack(String.valueOf(fragId));
        fragTransaction.commit();

    }
}
