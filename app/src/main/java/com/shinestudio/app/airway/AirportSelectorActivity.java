package com.shinestudio.app.airway;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shinestudio.app.airway.card.AirportCard;
import com.shinestudio.app.airway.db.Airport;

import it.gmariotti.cardslib.library.internal.Card;

public class AirportSelectorActivity extends Activity {
    public static final String RESULT_ICAO = "icao";
    public static final int REQUEST_DEPARTURE_ICAO = 0x01;
    public static final int REQUEST_DESTINATION_ICAO = 0x02;
    private AirportSelectorFragment airportSelectorFragment;

    private Card.OnCardClickListener onAirportCardClickListener = new Card.OnCardClickListener() {
        @Override
        public void onClick(Card card, View view) {
            Airport airport = ((AirportCard) card).getAirport();
            Intent i = new Intent();
            i.putExtra(RESULT_ICAO, airport.getIcao());
            AirportSelectorActivity.this.setResult(RESULT_OK, i);
            AirportSelectorActivity.this.finish();
        }
    };

    public static void show(Activity activity, int requestCode) {
        Intent i = new Intent(activity, AirportSelectorActivity.class);
        activity.startActivityForResult(i, requestCode);
    }

    public static void show(Fragment fragment, int requestCode) {
        Intent i = new Intent(fragment.getActivity(), AirportSelectorActivity.class);
        fragment.startActivityForResult(i, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        airportSelectorFragment = new AirportSelectorFragment();
        airportSelectorFragment.setOnAirportCardClickListener(onAirportCardClickListener);
        getFragmentManager().beginTransaction().replace(android.R.id.content, airportSelectorFragment).commit();
    }


}
