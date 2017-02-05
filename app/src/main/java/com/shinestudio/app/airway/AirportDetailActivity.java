package com.shinestudio.app.airway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AirportDetailActivity extends Activity {
    public static final String EXTRA_AIRPORT_ICAO = "icao";

    private AirportDetailFragment airportDetailFragment;
    private String airportIcao;

    public static void show(Activity activity, String airportIcao) {
        Intent intent = new Intent(activity, AirportDetailActivity.class);
        intent.putExtra(EXTRA_AIRPORT_ICAO, airportIcao);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            airportIcao = savedInstanceState.getString(EXTRA_AIRPORT_ICAO);
        } else {
            airportIcao = getIntent().getStringExtra(EXTRA_AIRPORT_ICAO);
        }
        airportDetailFragment = (AirportDetailFragment) getFragmentManager().findFragmentByTag(AirportDetailFragment.TAG);
        if (airportDetailFragment == null) {
            airportDetailFragment = AirportDetailFragment.newInstance(airportIcao);
            this.getFragmentManager().beginTransaction().replace(android.R.id.content, airportDetailFragment, AirportDetailFragment.TAG).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_AIRPORT_ICAO, airportIcao);
    }
}
