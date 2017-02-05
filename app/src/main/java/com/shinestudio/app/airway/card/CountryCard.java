package com.shinestudio.app.airway.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinestudio.app.airway.R;
import com.shinestudio.app.airway.db.Country;
import com.shinestudio.app.airway.internationalization.LanguageString;

import it.gmariotti.cardslib.library.internal.Card;

public class CountryCard extends Card {
    private static int LAYOUT_ID = R.layout.card_country;
    public static int TYPE = 0x100;

    private TextView tvCountryName;
    private TextView tvAirportCount;
    private Country country;
    private LanguageString languageString;

    public CountryCard(Context context, Country country) {
        super(context, LAYOUT_ID);
        this.country = country;
        setType(TYPE);
        languageString = LanguageString.getInstance(context.getApplicationContext());
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        tvCountryName = (TextView) parent.findViewById(R.id.card_country_name);
        tvAirportCount = (TextView) parent.findViewById(R.id.card_airport_count);

        if (country != null) {
            tvCountryName.setText(languageString.getString(country.getCountry()));
            tvAirportCount.setText(country.getAirports().toString());
        }
    }

    public Country getCountry() {
        return country;
    }
}
