package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


public class DaoGen {
    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1000, "com.shinestudio.app.airway.db");
        airport_info(schema);
        new DaoGenerator().generateAll(schema, "./app/src/main/java/");
    }


    private static void airport_info(Schema schema) {
        Entity airport = schema.addEntity("Airport");
        airport.setTableName("airports");
        airport.addIdProperty().autoincrement();
        airport.addStringProperty("geohash");
        airport.addStringProperty("icao");
        airport.addStringProperty("iata");
        airport.addStringProperty("name");
        airport.addStringProperty("city");
        airport.addStringProperty("country");
        airport.addIntProperty("timezone");
        airport.addDoubleProperty("latitude");
        airport.addDoubleProperty("longitude");
        airport.addIntProperty("elevation");
        airport.addIntProperty("tran_alt");
        airport.addIntProperty("tran_level");
        airport.addIntProperty("longest_runway_length");

        Entity runway = schema.addEntity("Runway");
        runway.setTableName("runways");
        runway.addIntProperty("airport_id");
        runway.addStringProperty("geohash");
        runway.addStringProperty("name");
        runway.addIntProperty("heading");
        runway.addIntProperty("length");
        runway.addIntProperty("width");
        runway.addIntProperty("ils");
        runway.addDoubleProperty("ils_frequency");
        runway.addIntProperty("ils_heading");
        runway.addDoubleProperty("latitude");
        runway.addDoubleProperty("longitude");
        runway.addIntProperty("elevation");
        runway.addDoubleProperty("glideslope_angle");
        runway.addDoubleProperty("overflying_height");
        runway.addIntProperty("surface_type");
        runway.addIntProperty("status");

        Entity navaid = schema.addEntity("Navaid");
        navaid.setTableName("navaids");
        navaid.addIdProperty().primaryKey().autoincrement();
        navaid.addStringProperty("geohash");
        navaid.addStringProperty("identifier");
        navaid.addStringProperty("name");
        navaid.addDoubleProperty("frequency");
        navaid.addBooleanProperty("vor");
        navaid.addBooleanProperty("dme");
        navaid.addIntProperty("range");
        navaid.addDoubleProperty("latitude");
        navaid.addDoubleProperty("longitude");
        navaid.addIntProperty("elevation");
        navaid.addStringProperty("country_code");

        Entity waypoint = schema.addEntity("Waypoint");
        waypoint.setTableName("waypoints");
        waypoint.addIdProperty().primaryKey().autoincrement();
        waypoint.addStringProperty("geohash");
        waypoint.addStringProperty("name");
        waypoint.addDoubleProperty("latitude");
        waypoint.addDoubleProperty("longitude");
        waypoint.addStringProperty("country_code");

        Entity airline = schema.addEntity("Airline");
        airline.setTableName("airlines");
        airline.addIdProperty().primaryKey().autoincrement();
        airline.addStringProperty("name");
        airline.addStringProperty("iata");
        airline.addStringProperty("icao");
        airline.addStringProperty("callsign");
        airline.addStringProperty("country");

        Entity route = schema.addEntity("Route");
        route.setTableName("routes");
        route.addIntProperty("airline_id");
        route.addStringProperty("source_code");
        route.addStringProperty("destination_code");
        route.addIntProperty("stops");
        route.addStringProperty("equipment");

        Entity country = schema.addEntity("Country");
        country.setTableName("countrys");
        country.addStringProperty("country");
        country.addIntProperty("airports");
    }
}
