package com.shinestudio.app.airway;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.shinestudio.app.airway.widget.AppFragmentPagerAdapter;
import com.shinestudio.app.airway.widget.FragmentTabItem;
import com.shinestudio.app.airway.widget.TabHostBuilder;

import java.util.ArrayList;

public class RouterActivity extends Activity {
    //    private TabHost mTabHost;
    private ViewPager mViewPager;
    private AppFragmentPagerAdapter appFragmentPagerAdapter;
    private TabHostBuilder mTabHostBuilder;
    private ArrayList<FragmentTabItem> mTabList = new ArrayList<FragmentTabItem>();
    private RouterWayFragment routerWayFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_router);


//        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        //实例化要显示的fragment
        routerWayFragment = RouterWayFragment.newInstance();
        mTabList.add(new FragmentTabItem("airway", routerWayFragment, R.drawable.tab_router));
//        mTabList.add(new FragmentTabItem("route", RouteFragment.newInstance(), R.drawable.tab_router));
//        mTabList.add(new FragmentTabItem("fuel", RouteFragment.newInstance(), R.drawable.tab_router));

        //设置TabHost
        mTabHostBuilder = new TabHostBuilder(this);
//        mTabHost.setup();
//        mTabHostBuilder.setupTabHost(mTabHost, mTabList);

        //设置ViewPager
        appFragmentPagerAdapter = new AppFragmentPagerAdapter(this.getFragmentManager(), mTabList);
        mViewPager.setAdapter(appFragmentPagerAdapter);
        mViewPager.setCurrentItem(0);

//        TabHostPagerBinder pagerBinder = new TabHostPagerBinder(mTabHost, mViewPager, mTabList);
//        mTabHost.setOnTabChangedListener(pagerBinder);
//        mViewPager.setOnPageChangeListener(pagerBinder);

    }


}
