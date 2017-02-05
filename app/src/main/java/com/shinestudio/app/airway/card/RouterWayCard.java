package com.shinestudio.app.airway.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shinestudio.app.airway.R;
import com.shinestudio.app.airway.widget.InCardListLayout;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;

public class RouterWayCard extends Card {
    private static final int LAYOUT_ID = R.layout.card_route_way;
    private ArrayList<String> wayData;
    private InCardListLayout listLayout;
    private RouteWayAdapter adapter;

    public RouterWayCard(Context context, ArrayList<String> data) {
        super(context, LAYOUT_ID);
        wayData = data;
    }

    @Override
    public View getInnerView(Context context, ViewGroup parent) {
        View view = super.getInnerView(context, parent);
        listLayout = (InCardListLayout) view.findViewById(R.id.in_card_list);
        adapter = new RouteWayAdapter(context, R.layout.part_router_way);
        listLayout.setAdapter(adapter);
        return view;
    }

    private class RouteWayAdapter extends ArrayAdapter<String> {
        private int layoutResId;

        private RouteWayAdapter(Context context, int resource) {
            super(context, 0, wayData);
            layoutResId = resource;
        }

        @Override
        public long getItemId(int position) {
            int size = wayData.size();
            return size / 2 + size % 2;
        }

        @Override
        public int getCount() {
            return wayData.size() / 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(RouterWayCard.this.getContext()).inflate(layoutResId, parent, false);
            TextView mVia = (TextView) v.findViewById(R.id.route_way_via);
            TextView mTo = (TextView) v.findViewById(R.id.route_way_to);
            View layout = v.findViewById(R.id.route_way_layout);

            int left = position * 2;
            int right = left + 1;
            String via = wayData.get(left);
            String to;
            try {
                to = wayData.get(right);
            } catch (RuntimeException e) {
                to = "";
            }
            mVia.setText(via);
            mTo.setText(to);

            if (position % 2 == 0) {
                layout.setBackground(null);
            } else {
                layout.setBackgroundResource(R.color.odd_item_color);
            }
            return v;
        }
    }


}
