package demo.song.com.songhaifang1506d20170825;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import demo.song.com.songhaifang1506d20170825.fragmenttt.Fragment02;
import demo.song.com.songhaifang1506d20170825.fragmenttt.Fragment03;
import demo.song.com.songhaifang1506d20170825.fragmenttt.Fragment04;
import demo.song.com.songhaifang1506d20170825.fragmenttt.Fragment05;
import demo.song.com.songhaifang1506d20170825.fragmenttt.Fragment06;
import demo.song.com.songhaifang1506d20170825.fragmenttt.Fragment07;
import demo.song.com.songhaifang1506d20170825.fragmenttt.Fragment08;
import demo.song.com.songhaifang1506d20170825.fragmenttt.Fragment09;
import demo.song.com.songhaifang1506d20170825.fragmenttt.Fragment10;
import demo.song.com.songhaifang1506d20170825.utils.FragmentAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout mytab;
    private ViewPager myviewpager;
    private List<Fragment> list;
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTab();
    }

    private void initTab() {
        list = new ArrayList<>();
        list.add(new Fragment01());
        list.add(new Fragment02());
        list.add(new Fragment03());
        list.add(new Fragment04());
        list.add(new Fragment05());
        list.add(new Fragment06());
        list.add(new Fragment07());
        list.add(new Fragment08());
        list.add(new Fragment09());
        list.add(new Fragment10());
        adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.setFragment(list);
        myviewpager.setAdapter(adapter);

        for (int i = 0; i < list.size() ; i++) {
            mytab.addTab(mytab.newTab());
        }
        mytab.setupWithViewPager(myviewpager);
        mytab.getTabAt(0).setText("1");
        mytab.getTabAt(1).setText("2");
        mytab.getTabAt(2).setText("3");
        mytab.getTabAt(3).setText("4");
        mytab.getTabAt(4).setText("5");
        mytab.getTabAt(5).setText("6");
        mytab.getTabAt(6).setText("7");
        mytab.getTabAt(7).setText("8");
        mytab.getTabAt(8).setText("9");
        mytab.getTabAt(9).setText("10");
    }

    private void initView() {
        mytab = (TabLayout) findViewById(R.id.mytab);
        myviewpager = (ViewPager) findViewById(R.id.myviewpager);
    }
}
