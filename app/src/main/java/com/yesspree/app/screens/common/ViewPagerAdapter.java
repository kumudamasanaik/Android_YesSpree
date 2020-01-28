package com.yesspree.app.screens.common;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FuGenX-14 on 30-04-2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment>mFragmentList=new ArrayList<>();
    private List<String>mFragmentTitleList=new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addArrayList(List<String> itemList) {
        this.mFragmentTitleList.clear();
        this.mFragmentTitleList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFragments(Fragment fragment, String fragmentTitle) {
         mFragmentList.add(fragment);
        mFragmentTitleList.add(fragmentTitle);
    }
}
