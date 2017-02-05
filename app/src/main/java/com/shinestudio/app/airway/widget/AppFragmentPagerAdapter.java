package com.shinestudio.app.airway.widget;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AppFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<FragmentTabItem> mTabList;

    public AppFragmentPagerAdapter(FragmentManager fm, ArrayList<FragmentTabItem> tlist) {
        super(fm);
        this.mTabList = tlist;
    }

    @Override
    public Fragment getItem(int position) {
        return (mTabList == null || mTabList.size() == 0) ? null
                : mTabList.get(position).fragment;
    }

    @Override
    public int getCount() {
        return mTabList == null ? 0 : mTabList.size();
    }

    /**
     * AppFragmentPagerAdapter 已知问题 ViewPager在屏幕旋转后，fragment被销毁，但ArrayList并未更新。所以使用ArrayList时仍然是旋转之前的Fragment的方法。
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mTabList.get(position).fragment = fragment;
        return fragment;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<FragmentTabItem> getmTabList() {
        return mTabList;
    }

    public void setmTabList(ArrayList<FragmentTabItem> mTabList) {
        this.mTabList = mTabList;
    }
}