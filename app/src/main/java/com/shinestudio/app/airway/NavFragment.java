package com.shinestudio.app.airway;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

public class NavFragment extends Fragment {
    private ImageButton btn_swap;
    private ImageButton btn_find;
    private TextView departure;
    private TextView destination;

    private ArrayList<Card> cards = new ArrayList<Card>();
    private CardListView cardListView;
    private CardArrayAdapter myCardArrayAdapter;

    public static NavFragment newInstance() {
        NavFragment fragment = new NavFragment();
        return fragment;
    }

    public NavFragment() {
    }

    private View.OnClickListener onSwapClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String departureValue = departure.getText().toString();
            String destinationValue = destination.getText().toString();
            if (!departureValue.isEmpty() || !destinationValue.isEmpty()) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in);
                departure.startAnimation(animation);
                destination.startAnimation(animation);
                departure.setText(destinationValue);
                destination.setText(departureValue);
            }
        }
    };

    private View.OnClickListener onDeptDestOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == departure) {
                AirportSelectorActivity.show(NavFragment.this, AirportSelectorActivity.REQUEST_DEPARTURE_ICAO);
            } else if (v == destination) {
                AirportSelectorActivity.show(NavFragment.this, AirportSelectorActivity.REQUEST_DESTINATION_ICAO);
            }
        }
    };

    private View.OnClickListener onFindClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(NavFragment.this.getActivity(), RouterActivity.class);
            NavFragment.this.startActivity(i);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_nav, container, false);
        btn_swap = (ImageButton) fragmentView.findViewById(R.id.btn_swap);
        btn_find = (ImageButton) fragmentView.findViewById(R.id.btn_find);
        departure = (TextView) fragmentView.findViewById(R.id.et_departure);
        destination = (TextView) fragmentView.findViewById(R.id.et_destination);
        cardListView = (CardListView) fragmentView.findViewById(R.id.card_list_view);

        cardListView.setOnScrollListener(new MyScroll());
        myCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
        cardListView.setAdapter(myCardArrayAdapter);
        btn_find.setOnClickListener(onFindClickListener);
        btn_swap.setOnClickListener(onSwapClickListener);
        departure.setOnClickListener(onDeptDestOnClickListener);
        destination.setOnClickListener(onDeptDestOnClickListener);
        setRetainInstance(true);

        cardListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
        cardListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean isTop = false;
            private View header;

            private void removeHeader(AbsListView view) {
                if (header != null) {
                    cardListView.removeHeaderView(header);
                    int firstItemPosition = view.getFirstVisiblePosition(); //第一个元素
                    cardListView.setSelectionFromTop(firstItemPosition, view.getChildAt(1).getTop()); //列表整体偏移量 1本来是第0个 因为header还没有被移除
                    header = null;
                }
            }

            private void addHeader() {
                header = LayoutInflater.from(NavFragment.this.getActivity()).inflate(R.layout.part_nav_setting, null);
                cardListView.addHeaderView(header);
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == SCROLL_STATE_IDLE) {
//                    int firstPosition = view.getFirstVisiblePosition();
//                    if (firstPosition == 0) {
//                        int[] listView = new int[4];
//                        int[] item = new int[4];
//                        view.getLocationOnScreen(listView);
//                        view.getChildAt(0).getLocationOnScreen(item);
//                        if (listView[1] == item[1]) {
//                            if (isTop) {
//                                //第二次到达UP位置
//                                if (header == null) {
//                                    addHeader();
//                                }
//                            } else {
//                                isTop = true;     //标记第一次到达UP位置
//                            }
//                        } else {
//                            isTop = false;
//                        }
//                    } else {
//                        isTop = false;
//                        removeHeader(view);
//                    }
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }
        });

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 0; i < 20; i++) {
            Card card = new Card(getActivity());
            card.setTitle(Integer.toString(i));
//            cards.add(card);
        }

        View header = LayoutInflater.from(NavFragment.this.getActivity()).inflate(R.layout.part_nav_setting, null);
        cardListView.addHeaderView(header);
        cardListView.setSelectionFromTop(0, header.getHeight());
        myCardArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            if (AirportSelectorActivity.REQUEST_DEPARTURE_ICAO == requestCode) {
                String icao = data.getStringExtra(AirportSelectorActivity.RESULT_ICAO);
                departure.setText(icao);
            } else if (AirportSelectorActivity.REQUEST_DESTINATION_ICAO == requestCode) {
                String icao = data.getStringExtra(AirportSelectorActivity.RESULT_ICAO);
                destination.setText(icao);
            }
        }
    }

    private class MyScroll implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }
}
