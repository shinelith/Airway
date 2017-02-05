package com.shinestudio.app.airway.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinestudio.app.airway.R;
import com.shinestudio.app.airway.db.Navaid;
import com.shinestudio.app.airway.db.SpatiaQuery;
import com.shinestudio.app.airway.widget.InCardListLayout;

import it.gmariotti.cardslib.library.internal.Card;

public class NearbyNavaidCard extends Card {
    private static final int LAYOUT_ID = R.layout.card_nearby_navaid;
    private InCardListLayout listLayout;
    private NavaidAdapter adapter;
    private SpatiaQuery.NearbyNavaidData data;

    public NearbyNavaidCard(Context context, SpatiaQuery.NearbyNavaidData data) {
        super(context, LAYOUT_ID);
        this.data = data;
        adapter = new NavaidAdapter(context, R.layout.part_navaid);
    }

    @Override
    public View getInnerView(Context context, ViewGroup parent) {
        View view = super.getInnerView(context, parent);
        listLayout = (InCardListLayout) view.findViewById(R.id.in_card_list);
        listLayout.setAdapter(adapter);
        return view;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
    }

    private class NavaidAdapter extends ArrayAdapter<Navaid> {
        private int layoutResId;

        private NavaidAdapter(Context context, int resource) {
            super(context, 0, data.navaids);
            layoutResId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(layoutResId, parent, false);
            ImageView navIcon = (ImageView) view.findViewById(R.id.icon);
            TextView navId = (TextView) view.findViewById(R.id.identifier);
            TextView navFrequency = (TextView) view.findViewById(R.id.frequency);
            TextView navName = (TextView) view.findViewById(R.id.name);
            TextView navDistance = (TextView) view.findViewById(R.id.distance);

            Navaid navaid = data.navaids.get(position);
            double distance = data.distance.get(position);
            navId.setText(navaid.getIdentifier());
            navFrequency.setText(navaid.getFrequency().toString());
            navName.setText(navaid.getName());
            navDistance.setText(String.format("%.1f", distance));

            boolean isVor = navaid.getVor();
            boolean isDme = navaid.getDme();

            if (isVor && isDme) {
                navIcon.setImageResource(R.drawable.vor_dme);
            } else if (isVor && !isDme) {
                navIcon.setImageResource(R.drawable.vor);
            } else if (!isVor && !isDme) {
                navIcon.setImageResource(R.drawable.ndb);
            }

            return view;
        }
    }
}
