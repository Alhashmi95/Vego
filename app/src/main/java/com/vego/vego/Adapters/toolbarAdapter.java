package com.vego.vego.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vego.vego.Fragment.ChatFragment;
import com.vego.vego.Fragment.FragmentHome;
import com.vego.vego.Fragment.TrackProgressFragmentUser;

import java.util.ArrayList;
import java.util.List;

public class toolbarAdapter extends FragmentPagerAdapter {
    private static final List<Fragment> fragmentList = new ArrayList<>();
    private static final List<String> fragmentListTitles = new ArrayList<>();
    private int count = 6;

    private String[] tabs = new String[]{"Tab1", "Tab2", "Tab3", "Tab4", "Tab5", "Tab6"};
    Context mContext;

    public toolbarAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitles.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentListTitles.add(title);
    }
    public static List<Fragment> getFragmentList(){
        return fragmentList;
    }
    public static List<String> getFragmentTitles(){
        return fragmentListTitles;
    }
}
