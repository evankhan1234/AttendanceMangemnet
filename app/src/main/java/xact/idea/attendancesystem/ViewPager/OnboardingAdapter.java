package xact.idea.attendancesystem.ViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import xact.idea.attendancesystem.Fragment.OnboardingFragment;

public class OnboardingAdapter extends FragmentPagerAdapter {
    private String[] content;
    private String[] content2;
    public OnboardingAdapter(FragmentManager fm, String[] data,String[] data2) {
        super(fm);
        content=data;
        content2=data2;
    }

    @Override
    public Fragment getItem(int position) {
        return OnboardingFragment.newInstance(position,content[position],content2[position]);
    }

    @Override
    public int getCount() {
        return 6;
    }
}