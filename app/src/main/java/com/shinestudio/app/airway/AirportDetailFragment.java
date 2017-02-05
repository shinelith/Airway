package com.shinestudio.app.airway;


import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shinestudio.app.airway.card.AeronauticalChartCard;
import com.shinestudio.app.airway.card.NearbyNavaidCard;
import com.shinestudio.app.airway.card.RunwayCard;
import com.shinestudio.app.airway.db.Airport;
import com.shinestudio.app.airway.db.AirportDao;
import com.shinestudio.app.airway.db.DaoMaster;
import com.shinestudio.app.airway.db.DaoSession;
import com.shinestudio.app.airway.db.DataSource;
import com.shinestudio.app.airway.db.Runway;
import com.shinestudio.app.airway.db.RunwayDao;
import com.shinestudio.app.airway.db.SpatiaQuery;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import jsqlite.Database;

public class AirportDetailFragment extends Fragment {
    public static final String TAG = "AirportDetailFragment";

    private String airportIcao;
    private Airport airport;
    private List<Runway> runways;
    private SpatiaQuery.NearbyNavaidData navaids;

    private TextView tvName;
    private TextView tvLocation;
    private TextView tvIcao;
    private TextView tvIata;
    private TextView tvRwy;
    private TextView tvTa;
    private TextView tvTl;
    private TextView tvEl;
    private TextView tvLatitude;
    private TextView tvLongitude;
    private ImageButton btnBack;

// 2015-2-25 去掉headimg显示
// private ImageView imHeaderImg;

    private CardListView cardListView;
    private ArrayList<Card> cards;
    private NearbyNavaidCard nearbyNavaidCard;
    private CardArrayAdapter myCardArrayAdapter;
    private LoadNearbayTask loadNearbayTask;

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AirportDetailFragment.this.getActivity().finish();
        }
    };

// 2015-2-25 去掉headimg显示
//    private HeaderStikkyAnimator animator = new HeaderStikkyAnimator() {
//        @Override
//        public AnimatorBuilder getAnimatorBuilder() {
//            View image = getHeader().findViewById(R.id.header_image);
//            return new AnimatorBuilder()
//                    .applyVerticalParallax(image, 0.5f);
//        }
//    };

    public static AirportDetailFragment newInstance(String icao) {
        AirportDetailFragment fragment = new AirportDetailFragment();
        fragment.airportIcao = icao;
        return fragment;
    }

    public AirportDetailFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        cards = new ArrayList<Card>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_airport_detail_noimg, container, false);
//      2015-2-25 去掉headimg显示
//      imHeaderImg = (ImageView) fragmentView.findViewById(R.id.header_image);
        tvName = (TextView) fragmentView.findViewById(R.id.name);
        tvLocation = (TextView) fragmentView.findViewById(R.id.location);
        tvIcao = (TextView) fragmentView.findViewById(R.id.icao);
        tvIata = (TextView) fragmentView.findViewById(R.id.iata);
        tvRwy = (TextView) fragmentView.findViewById(R.id.rwy);
        tvTa = (TextView) fragmentView.findViewById(R.id.ta);
        tvTl = (TextView) fragmentView.findViewById(R.id.tl);
        tvEl = (TextView) fragmentView.findViewById(R.id.el);
        tvLatitude = (TextView) fragmentView.findViewById(R.id.latitude);
        tvLongitude = (TextView) fragmentView.findViewById(R.id.longitude);
        btnBack = (ImageButton) fragmentView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(onBackClickListener);

        cardListView = (CardListView) fragmentView.findViewById(R.id.listview);
        myCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
        cardListView.setAdapter(myCardArrayAdapter);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            if (airport == null || runways == null) {
                loadAirportAndRunways();
            }
            refreshUI();
            if (navaids == null) {
                loadNearbyNavaids();
            } else {
                refreshNavaid();
            }
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            //TODO: no database found
        }
    }

    private void refreshUI() {
        String location = Text.str(airport.getCity()) + Text.strHeaderFooter(airport.getCountry(), ",", null);
        tvName.setText(Text.str(airport.getName()));
        tvLocation.setText(location);
        tvIcao.setText(Text.str(airport.getIcao()));
        tvRwy.setText(Integer.toString(runways.size()));
        tvIata.setText(Text.strHeaderFooter(airport.getIata(), "/", null));
        tvTa.setText(Text.strE(airport.getTran_alt().toString()));
        tvTl.setText(Text.strE(airport.getTran_level().toString()));
        tvEl.setText(Text.strE(airport.getElevation().toString()));
        tvLatitude.setText(Text.strE(airport.getLatitude().toString()));
        tvLongitude.setText(Text.strE(airport.getLongitude().toString()));
        // add runway infomation card
        for (Runway runway : runways) {
            RunwayCard runwayCard = new RunwayCard(this.getActivity(), runway);
            cards.add(runwayCard);
        }
        // add skyvector map
        AeronauticalChartCard acCard = new AeronauticalChartCard(this.getActivity(), airport);
        cards.add(acCard);
        myCardArrayAdapter.notifyDataSetChanged();
    }

    private void refreshNavaid() {
        nearbyNavaidCard = new NearbyNavaidCard(AirportDetailFragment.this.getActivity(), navaids); //vor merge ndb
        cards.add(nearbyNavaidCard);
        myCardArrayAdapter.notifyDataSetChanged();
    }

    private void loadAirportAndRunways() throws SQLiteCantOpenDatabaseException {
        String dbPath = DataSource.getInstance().getNavDbFilePath();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        AirportDao airportDao = daoSession.getAirportDao();
        RunwayDao runwayDao = daoSession.getRunwayDao();

        QueryBuilder queryAirport = airportDao.queryBuilder();
        queryAirport.where(AirportDao.Properties.Icao.eq(airportIcao));
        airport = (Airport) queryAirport.list().get(0);
        QueryBuilder requestRunway = runwayDao.queryBuilder();
        runways = requestRunway.where(RunwayDao.Properties.Airport_id.eq(airport.getId())).list();
        daoSession.clear();
        db.close();
    }

    private void loadNearbyNavaids() {
        loadNearbayTask = new LoadNearbayTask();
        loadNearbayTask.execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//      2015-2-25 去掉headimg显示
//      StikkyHeaderBuilder.stickTo(cardListView)
//                .setHeader(R.id.header, (ViewGroup) getView())
//                .minHeightHeaderRes(R.dimen.min_height_textheader_materiallike)
//                .animator(animator)
//                .build();
    }
//    2015-2-25 去掉headimg显示
//    private AsyncTask<String, Void, Void> getImg = new AsyncTask<String, Void, Void>() {
//        private Drawable countryImg = null;
//
//
//        @Override
//        protected Void doInBackground(String... params) {
//            String api_url = "http://pixabay.com/api/?username=donot&key=f36dd96c4961ae6ad6a9&q=" + params[0] + "&image_type=photo&orientation=horizontal&per_page=5";
//
//            InputStream is = null;
//            String json = null;
//            try {
//                URL url = new URL(api_url);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                int responseCode = conn.getResponseCode();
//                if (responseCode == 200) {
//                    is = conn.getInputStream();
//                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
//                    byte[] buffer = new byte[1024];
//                    int len = 0;
//                    while ((len = is.read(buffer)) != -1) {
//                        bout.write(buffer, 0, len);
//                    }
//                    bout.close();
//                    is.close();
//                    json = new String(bout.toByteArray());
//                    JSONObject jsonObject = new JSONObject(json);
//                    JSONArray hits = jsonObject.getJSONArray("hits");
//                    JSONObject img = hits.getJSONObject(0);
//                    String img_url = img.getString("webformatURL");
//                    System.out.println(img_url);
//                    InputStream img_is = new URL(img_url).openConnection().getInputStream();
//                    countryImg = Drawable.createFromStream(img_is, "img");
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            if (countryImg != null) {
//                imHeaderImg.setImageDrawable(countryImg);
//            }
//        }
//    };


    private class LoadNearbayTask extends AsyncTask<Void, Void, Void> {
        private SpatiaQuery.NearbyNavaidData vors = null;
        private SpatiaQuery.NearbyNavaidData ndbs = null;
        private ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            progressBar = new ProgressBar(AirportDetailFragment.this.getActivity());
            cardListView.addFooterView(progressBar);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Database sdb = new Database();
            try {
                String dbPath = DataSource.getInstance().getNavDbFilePath();
                sdb.open(dbPath, jsqlite.Constants.SQLITE_OPEN_READWRITE | jsqlite.Constants.SQLITE_OPEN_CREATE);
                if (!isCancelled()) {
                    vors = SpatiaQuery.queryNearbyVOR(sdb, airportIcao, 4);
                }
                if (!isCancelled()) {
                    ndbs = SpatiaQuery.queryNearbyNDB(sdb, airportIcao, 4);
                }
                navaids = vors.merge(ndbs);
            } catch (jsqlite.Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    sdb.close();
                } catch (jsqlite.Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            navaids = null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (cardListView != null) {
                cardListView.removeFooterView(progressBar);
            }
            if (!isCancelled() && navaids != null) {
                refreshNavaid();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (loadNearbayTask != null) {
            loadNearbayTask.cancel(true);
        }
    }

}
