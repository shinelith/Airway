package com.shinestudio.app.airway.db;

import java.util.ArrayList;
import java.util.List;

import jsqlite.Database;
import jsqlite.Stmt;

public class SpatiaQuery {

    public static class NearbyNavaidData {
        public int size;
        public List<Navaid> navaids;
        public List<Double> distance;

        public NearbyNavaidData merge(NearbyNavaidData another) {
            if (navaids != null && another.navaids != null) {
                navaids.addAll(another.navaids);
            }
            if (distance != null && another.distance != null) {
                distance.addAll(another.distance);
            }
            return this;
        }

    }

    public static NearbyNavaidData queryNearbyVOR(Database db, String icao, int limit) {
        return queryNearbyNavaid(db, icao, true, null, limit);
    }

    public static NearbyNavaidData queryNearbyNDB(Database db, String icao, int limit) {
        return queryNearbyNavaid(db, icao, false, false, limit);
    }

    private static NearbyNavaidData queryNearbyNavaid(Database db, String icao, Boolean isVOR, Boolean isDME, int limit) {
        NearbyNavaidData result = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT n._id,n.geohash,n.identifier,n.name,n.frequency,n.vor,n.dme,n.range,n.latitude,n.longitude,n.elevation,n.country_code,");
        sb.append("DISTANCE(a.geom,n.geom,1)/1852 AS distance");
        sb.append(" FROM airports AS a , navaids AS n  WHERE distance < 100 AND a.ICAO = '");
        sb.append(icao).append("'");
        if (isVOR != null) {
            if (isVOR) {
                sb.append(" AND n.VOR =").append(1);
            } else {
                sb.append(" AND n.VOR =").append(0);
            }
        }
        if (isDME != null) {
            if (isDME) {
                sb.append(" AND n.DME =").append(1);
            } else {
                sb.append(" AND n.DME =").append(0);
            }
        }
        sb.append(" ORDER BY distance ASC LIMIT ").append(limit).append(";");
        try {
            Stmt stmt = db.prepare(sb.toString());
            result = new NearbyNavaidData();
            result.navaids = new ArrayList<Navaid>();
            result.distance = new ArrayList<Double>();

            while (stmt.step()) {
                long id = stmt.column_long(0);
                String geohash = stmt.column_string(1);
                String identifier = stmt.column_string(2);
                String name = stmt.column_string(3);
                Double frequency = stmt.column_double(4);
                boolean vor = stmt.column_int(5) == 1 ? true : false;
                boolean dme = stmt.column_int(6) == 1 ? true : false;
                int range = stmt.column_int(7);
                double latitude = stmt.column_double(8);
                double longitude = stmt.column_double(9);
                int elevation = stmt.column_int(10);
                String country_code = stmt.column_string(11);
                double distance = stmt.column_double(12);

                Navaid navaid = new Navaid(id, geohash, identifier, name, frequency, vor, dme, range, latitude, longitude, elevation, country_code);
                result.navaids.add(navaid);
                result.distance.add(distance);
            }
        } catch (jsqlite.Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

}
