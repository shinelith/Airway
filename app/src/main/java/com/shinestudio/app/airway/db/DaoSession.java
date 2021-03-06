package com.shinestudio.app.airway.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.shinestudio.app.airway.db.Airport;
import com.shinestudio.app.airway.db.Runway;
import com.shinestudio.app.airway.db.Navaid;
import com.shinestudio.app.airway.db.Waypoint;
import com.shinestudio.app.airway.db.Airline;
import com.shinestudio.app.airway.db.Route;
import com.shinestudio.app.airway.db.Country;

import com.shinestudio.app.airway.db.AirportDao;
import com.shinestudio.app.airway.db.RunwayDao;
import com.shinestudio.app.airway.db.NavaidDao;
import com.shinestudio.app.airway.db.WaypointDao;
import com.shinestudio.app.airway.db.AirlineDao;
import com.shinestudio.app.airway.db.RouteDao;
import com.shinestudio.app.airway.db.CountryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig airportDaoConfig;
    private final DaoConfig runwayDaoConfig;
    private final DaoConfig navaidDaoConfig;
    private final DaoConfig waypointDaoConfig;
    private final DaoConfig airlineDaoConfig;
    private final DaoConfig routeDaoConfig;
    private final DaoConfig countryDaoConfig;

    private final AirportDao airportDao;
    private final RunwayDao runwayDao;
    private final NavaidDao navaidDao;
    private final WaypointDao waypointDao;
    private final AirlineDao airlineDao;
    private final RouteDao routeDao;
    private final CountryDao countryDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        airportDaoConfig = daoConfigMap.get(AirportDao.class).clone();
        airportDaoConfig.initIdentityScope(type);

        runwayDaoConfig = daoConfigMap.get(RunwayDao.class).clone();
        runwayDaoConfig.initIdentityScope(type);

        navaidDaoConfig = daoConfigMap.get(NavaidDao.class).clone();
        navaidDaoConfig.initIdentityScope(type);

        waypointDaoConfig = daoConfigMap.get(WaypointDao.class).clone();
        waypointDaoConfig.initIdentityScope(type);

        airlineDaoConfig = daoConfigMap.get(AirlineDao.class).clone();
        airlineDaoConfig.initIdentityScope(type);

        routeDaoConfig = daoConfigMap.get(RouteDao.class).clone();
        routeDaoConfig.initIdentityScope(type);

        countryDaoConfig = daoConfigMap.get(CountryDao.class).clone();
        countryDaoConfig.initIdentityScope(type);

        airportDao = new AirportDao(airportDaoConfig, this);
        runwayDao = new RunwayDao(runwayDaoConfig, this);
        navaidDao = new NavaidDao(navaidDaoConfig, this);
        waypointDao = new WaypointDao(waypointDaoConfig, this);
        airlineDao = new AirlineDao(airlineDaoConfig, this);
        routeDao = new RouteDao(routeDaoConfig, this);
        countryDao = new CountryDao(countryDaoConfig, this);

        registerDao(Airport.class, airportDao);
        registerDao(Runway.class, runwayDao);
        registerDao(Navaid.class, navaidDao);
        registerDao(Waypoint.class, waypointDao);
        registerDao(Airline.class, airlineDao);
        registerDao(Route.class, routeDao);
        registerDao(Country.class, countryDao);
    }
    
    public void clear() {
        airportDaoConfig.getIdentityScope().clear();
        runwayDaoConfig.getIdentityScope().clear();
        navaidDaoConfig.getIdentityScope().clear();
        waypointDaoConfig.getIdentityScope().clear();
        airlineDaoConfig.getIdentityScope().clear();
        routeDaoConfig.getIdentityScope().clear();
        countryDaoConfig.getIdentityScope().clear();
    }

    public AirportDao getAirportDao() {
        return airportDao;
    }

    public RunwayDao getRunwayDao() {
        return runwayDao;
    }

    public NavaidDao getNavaidDao() {
        return navaidDao;
    }

    public WaypointDao getWaypointDao() {
        return waypointDao;
    }

    public AirlineDao getAirlineDao() {
        return airlineDao;
    }

    public RouteDao getRouteDao() {
        return routeDao;
    }

    public CountryDao getCountryDao() {
        return countryDao;
    }

}
