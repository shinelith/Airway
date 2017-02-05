package com.shinestudio.app.airway.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinestudio.app.airway.R;
import com.shinestudio.app.airway.Text;
import com.shinestudio.app.airway.db.Airport;

import it.gmariotti.cardslib.library.internal.Card;

public class AirportCard extends Card {
    private static final int LAYOUT_ID = R.layout.card_airport;
    public static int TYPE = 0x200;

    private TextView tvAirportName;
    private TextView tvIcao;
    private TextView tvIata;
    private TextView tvLocation;
    private TextView tvRunwayLongest;

    private Airport airport;

    public AirportCard(Context context, Airport airport) {
        super(context, LAYOUT_ID);
        this.airport = airport;
        setType(TYPE);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        tvAirportName = (TextView) parent.findViewById(R.id.card_airport_name);
        tvIcao = (TextView) parent.findViewById(R.id.card_icao);
        tvIata = (TextView) parent.findViewById(R.id.card_iata);
        tvLocation = (TextView) parent.findViewById(R.id.card_location);
        tvRunwayLongest = (TextView) parent.findViewById(R.id.card_runway_length);

        if (airport != null) {
            tvAirportName.setText(Text.str(airport.getName()));
            tvIcao.setText(Text.str(airport.getIcao()));
            tvIata.setText(Text.str(airport.getIata()));
            String country = Text.str(airport.getCountry());
            String city = Text.str(airport.getCity());
            if (!city.isEmpty()) {
                country = ", " + country;
            }
            tvLocation.setText(city + country);
            String length = Text.strHeaderFooter(airport.getLongest_runway_length().toString(), null, getContext().getString(R.string.unit_ft));
            tvRunwayLongest.setText(length);
        }
    }

    public Airport getAirport() {
        return airport;
    }

}
