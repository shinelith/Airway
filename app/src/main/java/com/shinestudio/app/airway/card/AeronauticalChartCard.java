package com.shinestudio.app.airway.card;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.shinestudio.app.airway.R;
import com.shinestudio.app.airway.db.Airport;
import com.shinestudio.app.airway.extdata.SkyVector;

import it.gmariotti.cardslib.library.internal.Card;

public class AeronauticalChartCard extends Card implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private static final int LAYOUT_ID = R.layout.card_aeronautical_chart;

    private WebView webView;
    private Airport airport;
    private ImageButton btn_refresh;
    private ImageButton btn_export;
    private ImageButton btn_more;
    private View emptyLayout;
    private View chartLayout;

    private PopupMenu popupMenu;
    private SkyVector skyVector;

    public AeronauticalChartCard(Context context, Airport airport) {
        super(context, LAYOUT_ID);
        this.airport = airport;
        skyVector = new SkyVector();
        vfr(); // VFR is default
    }

    @Override
    public View getInnerView(Context context, ViewGroup parent) {
        View view = super.getInnerView(context, parent);

        emptyLayout = view.findViewById(R.id.empty_layout);
        chartLayout = view.findViewById(R.id.chart_layout);
        webView = (WebView) view.findViewById(R.id.webView);
        btn_refresh = (ImageButton) view.findViewById(R.id.btn_refresh);
        btn_export = (ImageButton) view.findViewById(R.id.btn_export);
        btn_more = (ImageButton) view.findViewById(R.id.btn_more);

        btn_refresh.setOnClickListener(this);
        btn_export.setOnClickListener(this);
        btn_more.setOnClickListener(this);
        popupMenu = new PopupMenu(this.getContext(), btn_more);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_aero_chart, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });

        refreshChartLayout();
        return view;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
    }

    private boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    private void refreshChartLayout() {
        if (isNetworkConnected(this.getContext())) {
            chartLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            webViewLoad();
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            chartLayout.setVisibility(View.GONE);
        }
    }

    private void vfr() {
        skyVector.getVFR(airport.getLatitude().toString(), airport.getLongitude().toString());
    }

    private void ifr() {
        skyVector.getIFR(airport.getLatitude().toString(), airport.getLongitude().toString());
    }

    private void webViewLoad() {
        webView.loadData(skyVector.getHtmlCode(), "text/html", "UTF-8");
    }

    @Override
    public void onClick(View v) {
        if (v == btn_refresh) {
            refreshChartLayout();
        } else if (v == btn_export) {
            skyVector.showInWeb(this.getContext());
        } else if (v == btn_more) {
            popupMenu.show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_aero_chart_ifr:
                ifr();
                webViewLoad();
                break;
            case R.id.menu_aero_chart_vfr:
                vfr();
                webViewLoad();
                break;
            default:
        }
        return false;
    }
}
