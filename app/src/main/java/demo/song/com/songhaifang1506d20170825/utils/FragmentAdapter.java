package demo.song.com.songhaifang1506d20170825.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * data:2017/8/25 0025.
 * Created by ：宋海防  song on
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    List<Fragment> lists;
    public void setFragment(List<Fragment> list){
        lists=list;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = lists.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return lists.size();
    }
}
