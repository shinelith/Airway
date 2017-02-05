package com.shinestudio.app.airway.widget;

import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import java.util.ArrayList;


public class TabHostPagerBinder implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private ArrayList<FragmentTabItem> mTabList;

    public TabHostPagerBinder(TabHost tabHost, ViewPager pager, ArrayList<FragmentTabItem> mTabList) {
        this.mTabHost = tabHost;
        this.mViewPager = pager;
        this.mTabList = mTabList;
    }

    @Override
    public void onTabChanged(String tabId) {
        // TabHost 与 ViewPager 同步
        int size = mTabList.size();
        for (int i = 0; i < size; i++) {
            if (mTabList.get(i).text.equals(tabId)) {
                mViewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mTabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
