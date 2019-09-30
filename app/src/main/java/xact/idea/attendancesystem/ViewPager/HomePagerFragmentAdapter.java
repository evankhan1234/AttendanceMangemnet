package xact.idea.attendancesystem.ViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import xact.idea.attendancesystem.Fragment.DepartmentFragment;
import xact.idea.attendancesystem.Fragment.UnityFragment;

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

    UnityFragment unityFragment = null;
    DepartmentFragment departmentFragment = null;


    @Override
    public Fragment getItem(int position)
    {
        if (position == 0)
        {
            if (unityFragment == null) {
                unityFragment = new UnityFragment();
            }
            return unityFragment;
        } else if (position == 1)
        {
            if (departmentFragment == null) {
                departmentFragment = new DepartmentFragment();
            }
            return departmentFragment;

        }
        else {
            if (unityFragment == null) {
                unityFragment = new UnityFragment();
            }
            return unityFragment;
        }
    }

}
