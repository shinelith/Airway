package com.shinestudio.app.airway.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinestudio.app.airway.R;
import com.shinestudio.app.airway.Text;
import com.shinestudio.app.airway.db.Runway;

import it.gmariotti.cardslib.library.internal.Card;

public class RunwayCard extends Card {
    private static final int LAYOUT_ID = R.layout.card_runway;

    private TextView tvName;
    private TextView tvFre;
    private TextView tvHdg;
    private TextView tvLength;
    private TextView tvWidth;
    private TextView tvAsl;
    private TextView tvLatitude;
    private TextView tvLongitude;
    private TextView tvHkgKey;
    private TextView tvGsa;

    private Runway runway;

    public RunwayCard(Context context, Runway runway) {
        super(context, LAYOUT_ID);
        this.runway = runway;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        tvName = (TextView) parent.findViewById(R.id.name);
        tvFre = (TextView) parent.findViewById(R.id.fre);
        tvHdg = (TextView) parent.findViewById(R.id.hdg);
        tvLength = (TextView) parent.findViewById(R.id.length);
        tvWidth = (TextView) parent.findViewById(R.id.width);
        tvAsl = (TextView) parent.findViewById(R.id.el);
        tvLatitude = (TextView) parent.findViewById(R.id.latitude);
        tvLongitude = (TextView) parent.findViewById(R.id.longitude);
        tvHkgKey = (TextView) parent.findViewById(R.id.hdgkey);
        tvGsa = (TextView) parent.findViewById(R.id.gsa);

        if (runway != null) {
            tvName.setText(runway.getName());
            tvFre.setText(runway.getIls_frequency().toString());
            tvHdg.setText(Text.heading(runway.getHeading()));
            tvLength.setText(runway.getLength().toString());
            tvWidth.setText(runway.getWidth().toString());
            tvAsl.setText(runway.getElevation().toString());
            tvLatitude.setText(runway.getLatitude().toString());
            tvLongitude.setText(runway.getLongitude().toString());
            tvGsa.setText(runway.getGlideslope_angle().toString() + getContext().getString(R.string.unit_degree));
            View ilsLayout = parent.findViewById(R.id.ils_layout);
            ilsLayout.setVisibility(View.GONE);
            if (runway.getIls() == 1) {
                ilsLayout.setVisibility(View.VISIBLE);
                tvHkgKey.setText(getContext().getText(R.string.card_detail_hdgils));
            }
        }
    }

    public Runway getRunway() {
        return runway;
    }

}
