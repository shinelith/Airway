package com.shinestudio.app.airway.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.shinestudio.app.airway.db.Airline;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table airlines.
*/
public class AirlineDao extends AbstractDao<Airline, Long> {

    public static final String TABLENAME = "airlines";

    /**
     * Properties of entity Airline.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Iata = new Property(2, String.class, "iata", false, "IATA");
        public final static Property Icao = new Property(3, String.class, "icao", false, "ICAO");
        public final static Property Callsign = new Property(4, String.class, "callsign", false, "CALLSIGN");
        public final static Property Country = new Property(5, String.class, "country", false, "COUNTRY");
    };


    public AirlineDao(DaoConfig config) {
        super(config);
    }
    
    public AirlineDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'airlines' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'IATA' TEXT," + // 2: iata
                "'ICAO' TEXT," + // 3: icao
                "'CALLSIGN' TEXT," + // 4: callsign
                "'COUNTRY' TEXT);"); // 5: country
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'airlines'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Airline entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String iata = entity.getIata();
        if (iata != null) {
            stmt.bindString(3, iata);
        }
 
        String icao = entity.getIcao();
        if (icao != null) {
            stmt.bindString(4, icao);
        }
 
        String callsign = entity.getCallsign();
        if (callsign != null) {
            stmt.bindString(5, callsign);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(6, country);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Airline readEntity(Cursor cursor, int offset) {
        Airline entity = new Airline( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // iata
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // icao
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // callsign
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // country
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Airline entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIata(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIcao(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCallsign(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCountry(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Airline entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Airline entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}