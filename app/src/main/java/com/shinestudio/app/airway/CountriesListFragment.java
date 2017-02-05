package com.shinestudio.app.airway;

import android.app.Fragment;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shinestudio.app.airway.card.CountryCard;
import com.shinestudio.app.airway.db.Country;
import com.shinestudio.app.airway.db.CountryDao;
import com.shinestudio.app.airway.db.DaoMaster;
import com.shinestudio.app.airway.db.DaoSession;
import com.shinestudio.app.airway.db.DataSource;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

public class CountriesListFragment extends Fragment {
    private CardListView cardListView;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private CardArrayAdapter myCardArrayAdapter;
    private Card.OnCardClickListener onCardClickListener;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View listHeaderView = getActivity().getLayoutInflater().inflate(R.layout.part_countries_list_header, null);
        cardListView.addHeaderView(listHeaderView);
        try {
            loadCountriesList();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            // TODO: no database found
        }
    }

    public void loadCountriesList() throws SQLiteCantOpenDatabaseException {
        String dbPath = DataSource.getInstance().getNavDbFilePath();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        CountryDao countryDao = daoSession.getCountryDao();
        QueryBuilder qb = countryDao.queryBuilder();
        List<Country> list = qb.list();
        cards.clear();
        if (list != null) {
            for (Country c : list) {
                CountryCard routerCard = new CountryCard(getActivity(), c);
                routerCard.setOnClickListener(onCardClickListener);
                cards.add(routerCard);
            }
        }
        db.close();
        myCardArrayAdapter.notifyDataSetChanged();
    }

    public void setOnCardClickListener(Card.OnCardClickListener onCardClickListener) {
        this.onCardClickListener = onCardClickListener;
    }
}
