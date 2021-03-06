package com.shinestudio.app.airway.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table waypoints.
 */
public class Waypoint {

    private Long id;
    private String geohash;
    private String name;
    private Double latitude;
    private Double longitude;
    private String country_code;

    public Waypoint() {
    }

    public Waypoint(Long id) {
        this.id = id;
    }

    public Waypoint(Long id, String geohash, String name, Double latitude, Double longitude, String country_code) {
        this.id = id;
        this.geohash = geohash;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country_code = country_code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

}
