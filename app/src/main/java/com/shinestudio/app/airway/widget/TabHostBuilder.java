package com.shinestudio.app.airway.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.shinestudio.app.airway.R;

import java.util.ArrayList;

public class TabHostBuilder {
    private Context context;
    private LayoutInflater inflater;
    private TabHost.TabContentFactory factory;

    public TabHostBuilder(final Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        factory = new TabHost.TabContentFactory() {
            //创建一个空白的View
            @Override
            public View createTabContent(String tag) {
                return new View(context);
            }
        };
    }

    public void setupTabHost(TabHost tabHost, ArrayList<FragmentTabItem> tabList) {
        if (tabHost != null) {
            for (FragmentTabItem item : tabList) {
                tabHost.addTab(buildTabSpec(tabHost, item));
            }
        }
    }

    private TabHost.TabSpec buildTabSpec(TabHost tabHost, FragmentTabItem item) {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(item.text);
        View tabSpecView = inflater.inflate(R.layout.tab_item, null);
        ImageView icon = (ImageView) tabSpecView.findViewById(android.R.id.icon);
        icon.setImageDrawable(context.getResources().getDrawable(item.resId));
        tabSpec.setIndicator(tabSpecView);
        tabSpec.setContent(factory);
        return tabSpec;
    }
}