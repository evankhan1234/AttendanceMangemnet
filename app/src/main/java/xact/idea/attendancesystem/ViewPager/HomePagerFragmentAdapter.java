package xact.idea.attendancesystem.ViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import xact.idea.attendancesystem.Fragment.PunchOutFragment;
import xact.idea.attendancesystem.Fragment.PunchInFragment;

public class HomePagerFragmentAdapter extends FragmentPagerAdapter {

    // Edit by giangnt, use for hide Note tab
    private final int HOME_PAGER = 2;

    public HomePagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return HOME_PAGER;
    }

    PunchInFragment unityFragment = null;
    PunchOutFragment departmentFragment = null;


    @Override
    public Fragment getItem(int position)
    {
        if (position == 0)
        {
            if (unityFragment == null) {
                unityFragment = new PunchInFragment();
            }
            return unityFragment;
        } else if (position == 1)
        {
            if (departmentFragment == null) {
                departmentFragment = new PunchOutFragment();
            }
            return departmentFragment;

        }
        else {
            if (unityFragment == null) {
                unityFragment = new PunchInFragment();
            }
            return unityFragment;
        }
    }

}
