package com.shinestudio.app.airway.db;

import android.os.Environment;

import java.io.File;

public class DataSource {
    private static DataSource me;
    private static final String LOCAL_NAV_DB = "routedata.sqlite";

    public static DataSource getInstance() {
        if (me == null) {
            me = new DataSource();
        }
        return me;
    }

    public String getNavDbFilePath() {
        File dbFile = Environment.getExternalStorageDirectory();
        String dbPath = dbFile.getAbsolutePath() + "/airway/" + LOCAL_NAV_DB;
        return dbPath;
    }

}
