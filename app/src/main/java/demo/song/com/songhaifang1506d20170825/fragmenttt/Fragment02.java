package demo.song.com.songhaifang1506d20170825.fragmenttt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demo.song.com.songhaifang1506d20170825.R;

/**
 * data:2017/8/25 0025.
 * Created by ：宋海防  song on
 */
public class Fragment02 extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment01,container,false);
        
        return view;
    }
}
