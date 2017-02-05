package com.shinestudio.app.airway.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.shinestudio.app.airway.db.Route;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table routes.
*/
public class RouteDao extends AbstractDao<Route, Void> {

    public static final String TABLENAME = "routes";

    /**
     * Properties of entity Route.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Airline_id = new Property(0, Integer.class, "airline_id", false, "AIRLINE_ID");
        public final static Property Source_code = new Property(1, String.class, "source_code", false, "SOURCE_CODE");
        public final static Property Destination_code = new Property(2, String.class, "destination_code", false, "DESTINATION_CODE");
        public final static Property Stops = new Property(3, Integer.class, "stops", false, "STOPS");
        public final static Property Equipment = new Property(4, String.class, "equipment", false, "EQUIPMENT");
    };


    public RouteDao(DaoConfig config) {
        super(config);
    }
    
    public RouteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'routes' (" + //
                "'AIRLINE_ID' INTEGER," + // 0: airline_id
                "'SOURCE_CODE' TEXT," + // 1: source_code
                "'DESTINATION_CODE' TEXT," + // 2: destination_code
                "'STOPS' INTEGER," + // 3: stops
                "'EQUIPMENT' TEXT);"); // 4: equipment
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'routes'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Route entity) {
        stmt.clearBindings();
 
        Integer airline_id = entity.getAirline_id();
        if (airline_id != null) {
            stmt.bindLong(1, airline_id);
        }
 
        String source_code = entity.getSource_code();
        if (source_code != null) {
            stmt.bindString(2, source_code);
        }
 
        String destination_code = entity.getDestination_code();
        if (destination_code != null) {
            stmt.bindString(3, destination_code);
        }
 
        Integer stops = entity.getStops();
        if (stops != null) {
            stmt.bindLong(4, stops);
        }
 
        String equipment = entity.getEquipment();
        if (equipment != null) {
            stmt.bindString(5, equipment);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public Route readEntity(Cursor cursor, int offset) {
        Route entity = new Route( //
            cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // airline_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // source_code
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // destination_code
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // stops
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // equipment
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Route entity, int offset) {
        entity.setAirline_id(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setSource_code(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDestination_code(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setStops(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setEquipment(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(Route entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(Route entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
