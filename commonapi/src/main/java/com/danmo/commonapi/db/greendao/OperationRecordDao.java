package com.danmo.commonapi.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.danmo.commonapi.db.entity.OperationRecord;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "OPERATION_RECORD".
*/
public class OperationRecordDao extends AbstractDao<OperationRecord, Void> {

    public static final String TABLENAME = "OPERATION_RECORD";

    /**
     * Properties of entity OperationRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Locker_id = new Property(0, int.class, "locker_id", false, "LOCKER_ID");
        public final static Property Locker = new Property(1, String.class, "locker", false, "LOCKER");
        public final static Property Bin_id = new Property(2, int.class, "bin_id", false, "BIN_ID");
        public final static Property Bin_name = new Property(3, String.class, "bin_name", false, "BIN_NAME");
        public final static Property Item_id = new Property(4, int.class, "item_id", false, "ITEM_ID");
        public final static Property Item_name = new Property(5, String.class, "item_name", false, "ITEM_NAME");
        public final static Property Part_name = new Property(6, String.class, "part_name", false, "PART_NAME");
        public final static Property Serial_number = new Property(7, String.class, "serial_number", false, "SERIAL_NUMBER");
        public final static Property Username = new Property(8, String.class, "username", false, "USERNAME");
        public final static Property User_id = new Property(9, String.class, "user_id", false, "USER_ID");
        public final static Property Qty_changed = new Property(10, int.class, "qty_changed", false, "QTY_CHANGED");
        public final static Property Qty_current = new Property(11, int.class, "qty_current", false, "QTY_CURRENT");
        public final static Property Qty_total = new Property(12, int.class, "qty_total", false, "QTY_TOTAL");
        public final static Property Type_of_service = new Property(13, int.class, "type_of_service", false, "TYPE_OF_SERVICE");
        public final static Property Operation_time = new Property(14, String.class, "operation_time", false, "OPERATION_TIME");
    }


    public OperationRecordDao(DaoConfig config) {
        super(config);
    }
    
    public OperationRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"OPERATION_RECORD\" (" + //
                "\"LOCKER_ID\" INTEGER NOT NULL ," + // 0: locker_id
                "\"LOCKER\" TEXT," + // 1: locker
                "\"BIN_ID\" INTEGER NOT NULL ," + // 2: bin_id
                "\"BIN_NAME\" TEXT," + // 3: bin_name
                "\"ITEM_ID\" INTEGER NOT NULL ," + // 4: item_id
                "\"ITEM_NAME\" TEXT," + // 5: item_name
                "\"PART_NAME\" TEXT," + // 6: part_name
                "\"SERIAL_NUMBER\" TEXT," + // 7: serial_number
                "\"USERNAME\" TEXT," + // 8: username
                "\"USER_ID\" TEXT," + // 9: user_id
                "\"QTY_CHANGED\" INTEGER NOT NULL ," + // 10: qty_changed
                "\"QTY_CURRENT\" INTEGER NOT NULL ," + // 11: qty_current
                "\"QTY_TOTAL\" INTEGER NOT NULL ," + // 12: qty_total
                "\"TYPE_OF_SERVICE\" INTEGER NOT NULL ," + // 13: type_of_service
                "\"OPERATION_TIME\" TEXT);"); // 14: operation_time
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"OPERATION_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, OperationRecord entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getLocker_id());
 
        String locker = entity.getLocker();
        if (locker != null) {
            stmt.bindString(2, locker);
        }
        stmt.bindLong(3, entity.getBin_id());
 
        String bin_name = entity.getBin_name();
        if (bin_name != null) {
            stmt.bindString(4, bin_name);
        }
        stmt.bindLong(5, entity.getItem_id());
 
        String item_name = entity.getItem_name();
        if (item_name != null) {
            stmt.bindString(6, item_name);
        }
 
        String part_name = entity.getPart_name();
        if (part_name != null) {
            stmt.bindString(7, part_name);
        }
 
        String serial_number = entity.getSerial_number();
        if (serial_number != null) {
            stmt.bindString(8, serial_number);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(9, username);
        }
 
        String user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindString(10, user_id);
        }
        stmt.bindLong(11, entity.getQty_changed());
        stmt.bindLong(12, entity.getQty_current());
        stmt.bindLong(13, entity.getQty_total());
        stmt.bindLong(14, entity.getType_of_service());
 
        String operation_time = entity.getOperation_time();
        if (operation_time != null) {
            stmt.bindString(15, operation_time);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, OperationRecord entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getLocker_id());
 
        String locker = entity.getLocker();
        if (locker != null) {
            stmt.bindString(2, locker);
        }
        stmt.bindLong(3, entity.getBin_id());
 
        String bin_name = entity.getBin_name();
        if (bin_name != null) {
            stmt.bindString(4, bin_name);
        }
        stmt.bindLong(5, entity.getItem_id());
 
        String item_name = entity.getItem_name();
        if (item_name != null) {
            stmt.bindString(6, item_name);
        }
 
        String part_name = entity.getPart_name();
        if (part_name != null) {
            stmt.bindString(7, part_name);
        }
 
        String serial_number = entity.getSerial_number();
        if (serial_number != null) {
            stmt.bindString(8, serial_number);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(9, username);
        }
 
        String user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindString(10, user_id);
        }
        stmt.bindLong(11, entity.getQty_changed());
        stmt.bindLong(12, entity.getQty_current());
        stmt.bindLong(13, entity.getQty_total());
        stmt.bindLong(14, entity.getType_of_service());
 
        String operation_time = entity.getOperation_time();
        if (operation_time != null) {
            stmt.bindString(15, operation_time);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public OperationRecord readEntity(Cursor cursor, int offset) {
        OperationRecord entity = new OperationRecord( //
            cursor.getInt(offset + 0), // locker_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // locker
            cursor.getInt(offset + 2), // bin_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // bin_name
            cursor.getInt(offset + 4), // item_id
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // item_name
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // part_name
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // serial_number
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // username
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // user_id
            cursor.getInt(offset + 10), // qty_changed
            cursor.getInt(offset + 11), // qty_current
            cursor.getInt(offset + 12), // qty_total
            cursor.getInt(offset + 13), // type_of_service
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // operation_time
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, OperationRecord entity, int offset) {
        entity.setLocker_id(cursor.getInt(offset + 0));
        entity.setLocker(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBin_id(cursor.getInt(offset + 2));
        entity.setBin_name(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setItem_id(cursor.getInt(offset + 4));
        entity.setItem_name(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPart_name(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSerial_number(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUsername(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setUser_id(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setQty_changed(cursor.getInt(offset + 10));
        entity.setQty_current(cursor.getInt(offset + 11));
        entity.setQty_total(cursor.getInt(offset + 12));
        entity.setType_of_service(cursor.getInt(offset + 13));
        entity.setOperation_time(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(OperationRecord entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(OperationRecord entity) {
        return null;
    }

    @Override
    public boolean hasKey(OperationRecord entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
