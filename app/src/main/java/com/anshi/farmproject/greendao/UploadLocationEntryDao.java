package com.anshi.farmproject.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "UPLOAD_LOCATION_ENTRY".
*/
public class UploadLocationEntryDao extends AbstractDao<UploadLocationEntry, Long> {

    public static final String TABLENAME = "UPLOAD_LOCATION_ENTRY";

    /**
     * Properties of entity UploadLocationEntry.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UploadNumber = new Property(0, Long.class, "uploadNumber", true, "_id");
        public final static Property RealNumber = new Property(1, String.class, "realNumber", false, "REAL_NUMBER");
        public final static Property DealType = new Property(2, String.class, "dealType", false, "DEAL_TYPE");
        public final static Property GroupNumber = new Property(3, int.class, "groupNumber", false, "GROUP_NUMBER");
        public final static Property AddressName = new Property(4, String.class, "addressName", false, "ADDRESS_NAME");
        public final static Property VillageName = new Property(5, String.class, "villageName", false, "VILLAGE_NAME");
        public final static Property DealTypePosition = new Property(6, int.class, "dealTypePosition", false, "DEAL_TYPE_POSITION");
        public final static Property VillagePosition = new Property(7, int.class, "villagePosition", false, "VILLAGE_POSITION");
        public final static Property Radius = new Property(8, double.class, "radius", false, "RADIUS");
        public final static Property AroundIvPath = new Property(9, String.class, "aroundIvPath", false, "AROUND_IV_PATH");
        public final static Property NumberIvPath = new Property(10, String.class, "numberIvPath", false, "NUMBER_IV_PATH");
        public final static Property DealTime = new Property(11, String.class, "dealTime", false, "DEAL_TIME");
        public final static Property OwnTown = new Property(12, String.class, "ownTown", false, "OWN_TOWN");
        public final static Property WokerName = new Property(13, String.class, "wokerName", false, "WOKER_NAME");
        public final static Property Latitude = new Property(14, double.class, "latitude", false, "LATITUDE");
        public final static Property Longtitude = new Property(15, double.class, "longtitude", false, "LONGTITUDE");
        public final static Property DetailAddress = new Property(16, String.class, "detailAddress", false, "DETAIL_ADDRESS");
    }


    public UploadLocationEntryDao(DaoConfig config) {
        super(config);
    }
    
    public UploadLocationEntryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"UPLOAD_LOCATION_ENTRY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: uploadNumber
                "\"REAL_NUMBER\" TEXT," + // 1: realNumber
                "\"DEAL_TYPE\" TEXT," + // 2: dealType
                "\"GROUP_NUMBER\" INTEGER NOT NULL ," + // 3: groupNumber
                "\"ADDRESS_NAME\" TEXT," + // 4: addressName
                "\"VILLAGE_NAME\" TEXT," + // 5: villageName
                "\"DEAL_TYPE_POSITION\" INTEGER NOT NULL ," + // 6: dealTypePosition
                "\"VILLAGE_POSITION\" INTEGER NOT NULL ," + // 7: villagePosition
                "\"RADIUS\" REAL NOT NULL ," + // 8: radius
                "\"AROUND_IV_PATH\" TEXT," + // 9: aroundIvPath
                "\"NUMBER_IV_PATH\" TEXT," + // 10: numberIvPath
                "\"DEAL_TIME\" TEXT," + // 11: dealTime
                "\"OWN_TOWN\" TEXT," + // 12: ownTown
                "\"WOKER_NAME\" TEXT," + // 13: wokerName
                "\"LATITUDE\" REAL NOT NULL ," + // 14: latitude
                "\"LONGTITUDE\" REAL NOT NULL ," + // 15: longtitude
                "\"DETAIL_ADDRESS\" TEXT);"); // 16: detailAddress
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"UPLOAD_LOCATION_ENTRY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UploadLocationEntry entity) {
        stmt.clearBindings();
 
        Long uploadNumber = entity.getUploadNumber();
        if (uploadNumber != null) {
            stmt.bindLong(1, uploadNumber);
        }
 
        String realNumber = entity.getRealNumber();
        if (realNumber != null) {
            stmt.bindString(2, realNumber);
        }
 
        String dealType = entity.getDealType();
        if (dealType != null) {
            stmt.bindString(3, dealType);
        }
        stmt.bindLong(4, entity.getGroupNumber());
 
        String addressName = entity.getAddressName();
        if (addressName != null) {
            stmt.bindString(5, addressName);
        }
 
        String villageName = entity.getVillageName();
        if (villageName != null) {
            stmt.bindString(6, villageName);
        }
        stmt.bindLong(7, entity.getDealTypePosition());
        stmt.bindLong(8, entity.getVillagePosition());
        stmt.bindDouble(9, entity.getRadius());
 
        String aroundIvPath = entity.getAroundIvPath();
        if (aroundIvPath != null) {
            stmt.bindString(10, aroundIvPath);
        }
 
        String numberIvPath = entity.getNumberIvPath();
        if (numberIvPath != null) {
            stmt.bindString(11, numberIvPath);
        }
 
        String dealTime = entity.getDealTime();
        if (dealTime != null) {
            stmt.bindString(12, dealTime);
        }
 
        String ownTown = entity.getOwnTown();
        if (ownTown != null) {
            stmt.bindString(13, ownTown);
        }
 
        String wokerName = entity.getWokerName();
        if (wokerName != null) {
            stmt.bindString(14, wokerName);
        }
        stmt.bindDouble(15, entity.getLatitude());
        stmt.bindDouble(16, entity.getLongtitude());
 
        String detailAddress = entity.getDetailAddress();
        if (detailAddress != null) {
            stmt.bindString(17, detailAddress);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UploadLocationEntry entity) {
        stmt.clearBindings();
 
        Long uploadNumber = entity.getUploadNumber();
        if (uploadNumber != null) {
            stmt.bindLong(1, uploadNumber);
        }
 
        String realNumber = entity.getRealNumber();
        if (realNumber != null) {
            stmt.bindString(2, realNumber);
        }
 
        String dealType = entity.getDealType();
        if (dealType != null) {
            stmt.bindString(3, dealType);
        }
        stmt.bindLong(4, entity.getGroupNumber());
 
        String addressName = entity.getAddressName();
        if (addressName != null) {
            stmt.bindString(5, addressName);
        }
 
        String villageName = entity.getVillageName();
        if (villageName != null) {
            stmt.bindString(6, villageName);
        }
        stmt.bindLong(7, entity.getDealTypePosition());
        stmt.bindLong(8, entity.getVillagePosition());
        stmt.bindDouble(9, entity.getRadius());
 
        String aroundIvPath = entity.getAroundIvPath();
        if (aroundIvPath != null) {
            stmt.bindString(10, aroundIvPath);
        }
 
        String numberIvPath = entity.getNumberIvPath();
        if (numberIvPath != null) {
            stmt.bindString(11, numberIvPath);
        }
 
        String dealTime = entity.getDealTime();
        if (dealTime != null) {
            stmt.bindString(12, dealTime);
        }
 
        String ownTown = entity.getOwnTown();
        if (ownTown != null) {
            stmt.bindString(13, ownTown);
        }
 
        String wokerName = entity.getWokerName();
        if (wokerName != null) {
            stmt.bindString(14, wokerName);
        }
        stmt.bindDouble(15, entity.getLatitude());
        stmt.bindDouble(16, entity.getLongtitude());
 
        String detailAddress = entity.getDetailAddress();
        if (detailAddress != null) {
            stmt.bindString(17, detailAddress);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UploadLocationEntry readEntity(Cursor cursor, int offset) {
        UploadLocationEntry entity = new UploadLocationEntry( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // uploadNumber
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // realNumber
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // dealType
            cursor.getInt(offset + 3), // groupNumber
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // addressName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // villageName
            cursor.getInt(offset + 6), // dealTypePosition
            cursor.getInt(offset + 7), // villagePosition
            cursor.getDouble(offset + 8), // radius
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // aroundIvPath
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // numberIvPath
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // dealTime
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // ownTown
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // wokerName
            cursor.getDouble(offset + 14), // latitude
            cursor.getDouble(offset + 15), // longtitude
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16) // detailAddress
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UploadLocationEntry entity, int offset) {
        entity.setUploadNumber(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRealNumber(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDealType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGroupNumber(cursor.getInt(offset + 3));
        entity.setAddressName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setVillageName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDealTypePosition(cursor.getInt(offset + 6));
        entity.setVillagePosition(cursor.getInt(offset + 7));
        entity.setRadius(cursor.getDouble(offset + 8));
        entity.setAroundIvPath(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setNumberIvPath(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setDealTime(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setOwnTown(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setWokerName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setLatitude(cursor.getDouble(offset + 14));
        entity.setLongtitude(cursor.getDouble(offset + 15));
        entity.setDetailAddress(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UploadLocationEntry entity, long rowId) {
        entity.setUploadNumber(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UploadLocationEntry entity) {
        if(entity != null) {
            return entity.getUploadNumber();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UploadLocationEntry entity) {
        return entity.getUploadNumber() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
