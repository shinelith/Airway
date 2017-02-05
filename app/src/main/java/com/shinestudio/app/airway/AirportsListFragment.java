package com.shinestudio.app.airway;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shinestudio.app.airway.card.AirportCard;
import com.shinestudio.app.airway.db.Airport;
import com.shinestudio.app.airway.db.AirportDao;
import com.shinestudio.app.airway.db.DaoMaster;
import com.shinestudio.app.airway.db.DaoSession;
import com.shinestudio.app.airway.db.DataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

public class AirportsListFragment extends Fragment {
    public static final int SEARCH_MODE_BYCOUNTRY = 0x00;
    public static final int SEARCH_MODE_FUZZY = 0x01;

    private String keyWord;
    private int searchMode;

    private CardListView cardListView;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private CardArrayAdapter myCardArrayAdapter;
    private Card.OnCardClickListener onAirportCardClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_cardview, container, false);
        cardListView = (CardListView) fragmentView.findViewById(R.id.card_list_view);
        myCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
        cardListView.setAdapter(myCardArrayAdapter);
        setRetainInstance(true);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            loadAirportList();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            //TODO: no database found
        }
    }

    public void loadAirportList() throws SQLiteCantOpenDatabaseException {
        if (keyWord != null && !keyWord.isEmpty()) {
            String dbPath = DataSource.getInstance().getNavDbFilePath();
            SQLiteDatabase db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            AirportDao airportDao = daoSession.getAirportDao();
            cards.clear();
            if (SEARCH_MODE_FUZZY == searchMode) {
                //初始化查询权值
                HashMap<String, Integer> weight = new HashMap<String, Integer>();
                weight.put(AirportDao.Properties.Icao.columnName, 1);
                weight.put(AirportDao.Properties.Iata.columnName, 1);
                weight.put(AirportDao.Properties.City.columnName, 1);
                weight.put(AirportDao.Properties.Name.columnName, 1);
                weight.put(AirportDao.Properties.Country.columnName, 1);
                int keywordLength = keyWord.length();


                //根据关键字的长度排列权值、选择合适的搜索范围
                if (keywordLength == 1) {
                    return;
                } else if (keywordLength == 3) {
                    weight.put(AirportDao.Properties.Iata.columnName, 5);
                } else if (keywordLength == 4) {
                    weight.put(AirportDao.Properties.Icao.columnName, 5);
                } else if (keywordLength > 4) {
                    weight.remove(AirportDao.Properties.Icao.columnName);
                    weight.remove(AirportDao.Properties.Iata.columnName);
                    weight.put(AirportDao.Properties.City.columnName, 1);
                    weight.put(AirportDao.Properties.Name.columnName, 1);
                    weight.put(AirportDao.Properties.Country.columnName, 1);
                }
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT * FROM ( SELECT *,SUM( ");
                Iterator i = weight.keySet().iterator();
                boolean isFirstTime = true;
                while (i.hasNext()) {
                    String key = (String) i.next();
                    if (!isFirstTime) {
                        sql.append(" + ");
                    } else {
                        isFirstTime = false;
                    }
                    sql.append("(CASE WHEN LIKE('" + keyWord + "%'," + key + ")>0 THEN " + weight.get(key) + " ELSE 0 END)");
                }

                sql.append(") AS rn FROM ").append(AirportDao.TABLENAME).append(" GROUP BY icao ) WHERE rn > 0 ORDER BY rn DESC;");
                Cursor cur = db.rawQuery(sql.toString(), null);
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    Airport airport = airportDao.readEntity(cur, 0);
                    AirportCard airportCard = new AirportCard(getActivity(), airport);
                    airportCard.setOnClickListener(onAirportCardClickListener);
                    cards.add(airportCard);
                }

            }

            if (SEARCH_MODE_BYCOUNTRY == searchMode) {
                List<Airport> list = airportDao.queryBuilder().where(AirportDao.Properties.Country.eq(keyWord)).list();
                for (Airport airport : list) {
                    AirportCard airportCard = new AirportCard(getActivity(), airport);
                    airportCard.setOnClickListener(onAirportCardClickListener);
                    cards.add(airportCard);
                }
            }

            myCardArrayAdapter.notifyDataSetChanged();
            cardListView.setSelection(0);
        }
    }

    public void setSearchMode(int searchMode) {
        this.searchMode = searchMode;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setOnAirportCardClickListener(Card.OnCardClickListener onAirportCardClickListener) {
        this.onAirportCardClickListener = onAirportCardClickListener;
    }
}
