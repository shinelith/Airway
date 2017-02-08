package com.shinestudio.app.airway;

import android.app.Activity;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.shinestudio.app.airway.db.Airport;
import com.shinestudio.app.airway.db.AirportDao;
import com.shinestudio.app.airway.db.DaoMaster;
import com.shinestudio.app.airway.db.DaoSession;
import com.shinestudio.app.airway.db.DataSource;
import com.shinestudio.app.airway.widget.AppFragmentPagerAdapter;
import com.shinestudio.app.airway.widget.FragmentTabItem;
import com.shinestudio.app.airway.widget.TabHostBuilder;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

public class RouterActivity extends Activity {
    public static final String EXTRA_DEPARTURE = "departureIcao";
    public static final String EXTRA_DESTINATION = "destinationIcao";
    //    private TabHost mTabHost;
    private ViewPager mViewPager;
    private AppFragmentPagerAdapter appFragmentPagerAdapter;
    private TabHostBuilder mTabHostBuilder;
    private ArrayList<FragmentTabItem> mTabList = new ArrayList<FragmentTabItem>();
    private RouterWayFragment routerWayFragment;
    private String departureIcao;
    private String destinationIcao;

    private Airport departureAirport;
    private Airport destinationAirport;

    private TextView t_departureIcao;
    private TextView t_departureIata;
    private TextView t_departureName;
    private TextView t_departureLocation;
    private TextView t_destinationIcao;
    private TextView t_destinationIata;
    private TextView t_destinationName;
    private TextView t_destinationLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_router);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.departureIcao = extras.getString(EXTRA_DEPARTURE);
            this.destinationIcao = extras.getString(EXTRA_DESTINATION);
        }

//        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        t_departureIcao = (TextView) findViewById(R.id.departureicao);
        t_departureIata = (TextView) findViewById(R.id.departureiata);
        t_departureName = (TextView) findViewById(R.id.departurename);
        t_departureLocation = (TextView) findViewById(R.id.departurelocation);
        t_destinationIcao = (TextView) findViewById(R.id.destinationicao);
        t_destinationIata = (TextView) findViewById(R.id.destinationiata);
        t_destinationName = (TextView) findViewById(R.id.destinationname);
        t_destinationLocation = (TextView) findViewById(R.id.destinationlocation);


        //实例化要显示的fragment
        routerWayFragment = RouterWayFragment.newInstance();
        Bundle arguments = new Bundle();
        arguments.putString(RouterWayFragment.EXTRA_DEPARTURE, departureIcao);
        arguments.putString(RouterWayFragment.EXTRA_DESTINATION, destinationIcao);
        routerWayFragment.setArguments(arguments);

        mTabList.add(new FragmentTabItem("airway", routerWayFragment, R.drawable.tab_router));
//        mTabList.add(new FragmentTabItem("route", RouteFragment.newInstance(), R.drawable.tab_router));
//        mTabList.add(new FragmentTabItem("fuel", RouteFragment.newInstance(), R.drawable.tab_router));

        //设置TabHost
        mTabHostBuilder = new TabHostBuilder(this);
//        mTabHost.setup();
//        mTabHostBuilder.setupTabHost(mTabHost, mTabList);

        //设置ViewPager
        appFragmentPagerAdapter = new AppFragmentPagerAdapter(this.getFragmentManager(), mTabList);
        mViewPager.setAdapter(appFragmentPagerAdapter);
        mViewPager.setCurrentItem(0);

//        TabHostPagerBinder pagerBinder = new TabHostPagerBinder(mTabHost, mViewPager, mTabList);
//        mTabHost.setOnTabChangedListener(pagerBinder);
//        mViewPager.setOnPageChangeListener(pagerBinder);

        loadAirports();
        t_departureIcao.setText(departureAirport.getIcao());
        t_departureIata.setText(departureAirport.getIata());
        t_departureName.setText(departureAirport.getName());
        String departLocation = Text.str(departureAirport.getCity()) + Text.strHeaderFooter(departureAirport.getCountry(), ",", null);
        t_departureLocation.setText(departLocation);

        t_destinationIcao.setText(destinationAirport.getIcao());
        t_destinationIata.setText(destinationAirport.getIata());
        t_destinationName.setText(destinationAirport.getName());
        String destinationLocation = Text.str(destinationAirport.getCity()) + Text.strHeaderFooter(destinationAirport.getCountry(), ",", null);
        t_destinationLocation.setText(destinationLocation);

    }


    private void loadAirports() throws SQLiteCantOpenDatabaseException {
        String dbPath = DataSource.getInstance().getNavDbFilePath();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        AirportDao airportDao = daoSession.getAirportDao();

        QueryBuilder queryAirport = airportDao.queryBuilder();
        queryAirport.where(AirportDao.Properties.Icao.eq(departureIcao));
        this.departureAirport = (Airport) queryAirport.list().get(0);

        queryAirport = airportDao.queryBuilder();
        queryAirport.where(AirportDao.Properties.Icao.eq(destinationIcao));
        this.destinationAirport = (Airport) queryAirport.list().get(0);

        daoSession.clear();
        db.close();
    }
}
