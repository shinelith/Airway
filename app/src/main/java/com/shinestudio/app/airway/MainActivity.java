package com.shinestudio.app.airway;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;

import com.shinestudio.app.airway.card.AirportCard;
import com.shinestudio.app.airway.db.Airport;
import com.shinestudio.app.airway.widget.AppFragmentPagerAdapter;
import com.shinestudio.app.airway.widget.FragmentTabItem;
import com.shinestudio.app.airway.widget.TabHostBuilder;
import com.shinestudio.app.airway.widget.TabHostPagerBinder;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;


public class MainActivity extends Activity {
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private AppFragmentPagerAdapter appFragmentPagerAdapter;
    private TabHostBuilder mTabHostBuilder;
    private ArrayList<FragmentTabItem> mTabList = new ArrayList<FragmentTabItem>();

    private AirportSelectorFragment airportDetail;
    private NavFragment nav;

    private Card.OnCardClickListener onAirportCardClickListener = new Card.OnCardClickListener() {
        @Override
        public void onClick(Card card, View view) {
            Airport airport = ((AirportCard) card).getAirport();
            AirportDetailActivity.show(MainActivity.this, airport.getIcao());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        //实例化要显示的fragment


        airportDetail = AirportSelectorFragment.newInstance();
        airportDetail.setOnAirportCardClickListener(onAirportCardClickListener);
        nav = NavFragment.newInstance();

        mTabList.add(new FragmentTabItem("nav", nav, R.drawable.tab_router));
        mTabList.add(new FragmentTabItem("airport", airportDetail, R.drawable.tab_airport));

        //设置TabHost
        mTabHostBuilder = new TabHostBuilder(this);
        mTabHost.setup();
        mTabHostBuilder.setupTabHost(mTabHost, mTabList);

        //设置ViewPager
        appFragmentPagerAdapter = new AppFragmentPagerAdapter(this.getFragmentManager(), mTabList);
        mViewPager.setAdapter(appFragmentPagerAdapter);
        mViewPager.setCurrentItem(0);

        TabHostPagerBinder pagerBinder = new TabHostPagerBinder(mTabHost, mViewPager, mTabList);
        mTabHost.setOnTabChangedListener(pagerBinder);
        mViewPager.setOnPageChangeListener(pagerBinder);
    }

    @Override
    public void onBackPressed() {
        ArrayList<FragmentTabItem> tabList = appFragmentPagerAdapter.getmTabList();
        Fragment fragment = tabList.get(mViewPager.getCurrentItem()).fragment;
        if (fragment instanceof AirportSelectorFragment) {
            if (((AirportSelectorFragment) fragment).onBackPress()) {
                return;
            }
        }
        super.onBackPressed();
    }

}
