package com.shinestudio.app.airway.widget;

import android.app.Fragment;

public class FragmentTabItem {
    public String text;
    public Fragment fragment;
    public int resId;

    public FragmentTabItem(String text, Fragment fragment, int resId) {
        this.text = text;
        this.fragment = fragment;
        this.resId = resId;
    }
}