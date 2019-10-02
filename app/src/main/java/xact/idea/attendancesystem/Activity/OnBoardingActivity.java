package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import xact.idea.attendancesystem.Fragment.OnboardingFragment;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.ViewPager.OnboardingAdapter;

public class OnBoardingActivity extends AppCompatActivity {
    private int pagePosition = 0;
    //  private ImageButton nextBtn;
    private Button finishBtn;
    public ViewPager viewPager;
    private ImageView[] indicators;
    private ImageView indicator01, indicator02, indicator03,indicator04,indicator05;
    private static Locale myLocale;
    public TextView tv_brightness;
    private OnboardingAdapter adapter = null;
    protected static final String[] CONTENT2 = new String[]{"Attendance", "Leave", "Punch In","Punch Out","Set Up","Exit"};
    protected static final String[] CON = new String[]{"Attendance System", "Leave System", "Punch In System", "Punch Out System", "Set UpSystem","Exit" +
            ""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        finishBtn = (Button) findViewById(R.id.btn_finish);

        indicator01 = (ImageView) findViewById(R.id.indicator_01);
        indicator02 = (ImageView) findViewById(R.id.indicator_02);
        indicator03 = (ImageView) findViewById(R.id.indicator_03);
        indicator04 = (ImageView) findViewById(R.id.indicator_04);
        indicator05 = (ImageView) findViewById(R.id.indicator_05);

        indicators = new ImageView[] {indicator01, indicator02, indicator03,indicator04,indicator05};

        updateIndicator(pagePosition);

        final int pageColor01 = ContextCompat.getColor(this, R.color.white);
        final int pageColor02 = ContextCompat.getColor(this, R.color.white);
        final int pageColor03 = ContextCompat.getColor(this, R.color.white);
        final int pageColor04 = ContextCompat.getColor(this, R.color.white);
        final int pageColor05 = ContextCompat.getColor(this, R.color.white);


        final int[] pageColorList = new int[] {pageColor01, pageColor02, pageColor03,pageColor04,pageColor05,pageColor05};

        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();  //used to update the page color

        // tv_brightness = (TextView) findViewById(R.id.tv_brightness);

        viewPager = (ViewPager) findViewById(R.id.onboarding_viewpager);

        adapter = new OnboardingAdapter(getSupportFragmentManager(),CONTENT2,CON);
        // Set Adapter on ViewPager
        viewPager.setAdapter(adapter);
        //loadLocale();
        // Set PageTransformer on ViewPager
        //viewPager.setPageTransformer(false, new OnboardingPageTransformer());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                // Update Page Background Color
                int pageColorUpdate = (Integer) argbEvaluator.evaluate(
                        positionOffset,
                        pageColorList[position],
                        pageColorList[position == 5 ? position : position + 1]  //If there's no last page, do not increment
                );
                viewPager.setBackgroundColor(pageColorUpdate);

            }




            @Override
            public void onPageSelected(int position) {
                pagePosition = position;
                updateIndicator(pagePosition);
                Log.e("onPageSelected",""+position);
                //set the page color when selected
                switch (position) {
                    case 0:
                        viewPager.setBackgroundColor(pageColor01);
                        break;
                    case 1:
                        viewPager.setBackgroundColor(pageColor02);
                        break;
                    case 2:
                        viewPager.setBackgroundColor(pageColor03);
                        break;

                    case 3:
                        viewPager.setBackgroundColor(pageColor04);
                        break;
                    case 4:
                        viewPager.setBackgroundColor(pageColor05);
                        break;
                    case 5:
                        goMainScreen();
                        break;
                }


                //   nextBtn.setVisibility(position == 4 ? View.GONE : View.VISIBLE);
                //  finishBtn.setVisibility(position == 4 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OnBoardingActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
    private void updateIndicator(int pagePosition) {
        for(int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == pagePosition ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }
    private void goMainScreen() {
        Intent i;
        i = new Intent(OnBoardingActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
