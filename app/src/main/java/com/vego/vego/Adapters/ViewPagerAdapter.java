package com.vego.vego.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> FragmmentListTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return FragmmentListTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FragmmentListTitles.get(position);
    }
    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        FragmmentListTitles.add(title);
    }
}
