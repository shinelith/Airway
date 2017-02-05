package com.shinestudio.app.airway.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

public class InCardListLayout extends LinearLayout {
    private OnClickListener onClickListener = null;

    public InCardListLayout(Context context) {
        super(context);
    }

    public InCardListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InCardListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InCardListLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, OnClickListener onClickListener) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.onClickListener = onClickListener;
    }

    public void setAdapter(Adapter adapter) {
        this.removeAllViews();
        if (adapter != null) {
            int size = adapter.getCount();
            for (int i = 0; i < size; i++) {
                View view = adapter.getView(i, null, this);
                view.setOnClickListener(onClickListener);
                this.addView(view);
            }
        }
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
