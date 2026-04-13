package com.didichuxing.doraemonkit.okgo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.didichuxing.doraemonkit.okgo.DokitOkGo;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_CACHE_NAME = "okgo.db";
    private static final int DB_CACHE_VERSION = 1;
    static final String TABLE_CACHE = "cache";
    static final String TABLE_COOKIE = "cookie";
    static final String TABLE_DOWNLOAD = "download";
    static final String TABLE_UPLOAD = "upload";
    static final Lock lock = new ReentrantLock();
    private TableEntity cacheTableEntity;
    private TableEntity cookieTableEntity;
    private TableEntity downloadTableEntity;
    private TableEntity uploadTableEntity;

    DBHelper() {
        this(DokitOkGo.getInstance().getContext());
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DBHelper(Context context) {
        super(context, DB_CACHE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.cacheTableEntity = new TableEntity(TABLE_CACHE);
        this.cookieTableEntity = new TableEntity("cookie");
        this.downloadTableEntity = new TableEntity(TABLE_DOWNLOAD);
        this.uploadTableEntity = new TableEntity(TABLE_UPLOAD);
        this.cacheTableEntity.addColumn(new ColumnEntity(CacheEntity.KEY, "VARCHAR", true, true)).addColumn(new ColumnEntity(CacheEntity.LOCAL_EXPIRE, "INTEGER")).addColumn(new ColumnEntity(CacheEntity.HEAD, "BLOB")).addColumn(new ColumnEntity("data", "BLOB"));
        this.cookieTableEntity.addColumn(new ColumnEntity(SerializableCookie.HOST, "VARCHAR")).addColumn(new ColumnEntity("name", "VARCHAR")).addColumn(new ColumnEntity(SerializableCookie.DOMAIN, "VARCHAR")).addColumn(new ColumnEntity("cookie", "BLOB")).addColumn(new ColumnEntity(SerializableCookie.HOST, "name", SerializableCookie.DOMAIN));
        TableEntity addColumn = this.downloadTableEntity.addColumn(new ColumnEntity(Progress.TAG, "VARCHAR", true, true)).addColumn(new ColumnEntity("url", "VARCHAR")).addColumn(new ColumnEntity(Progress.FOLDER, "VARCHAR")).addColumn(new ColumnEntity(Progress.FILE_PATH, "VARCHAR")).addColumn(new ColumnEntity(Progress.FILE_NAME, "VARCHAR")).addColumn(new ColumnEntity(Progress.FRACTION, "VARCHAR")).addColumn(new ColumnEntity(Progress.TOTAL_SIZE, "INTEGER")).addColumn(new ColumnEntity(Progress.CURRENT_SIZE, "INTEGER")).addColumn(new ColumnEntity("status", "INTEGER")).addColumn(new ColumnEntity(Progress.PRIORITY, "INTEGER"));
        String str = Progress.PRIORITY;
        TableEntity addColumn2 = addColumn.addColumn(new ColumnEntity(Progress.DATE, "INTEGER"));
        String str2 = Progress.DATE;
        TableEntity addColumn3 = addColumn2.addColumn(new ColumnEntity(Progress.REQUEST, "BLOB"));
        String str3 = Progress.REQUEST;
        TableEntity addColumn4 = addColumn3.addColumn(new ColumnEntity(Progress.EXTRA1, "BLOB"));
        String str4 = Progress.EXTRA1;
        TableEntity addColumn5 = addColumn4.addColumn(new ColumnEntity(Progress.EXTRA2, "BLOB"));
        String str5 = Progress.EXTRA2;
        addColumn5.addColumn(new ColumnEntity(Progress.EXTRA3, "BLOB"));
        this.uploadTableEntity.addColumn(new ColumnEntity(Progress.TAG, "VARCHAR", true, true)).addColumn(new ColumnEntity("url", "VARCHAR")).addColumn(new ColumnEntity(Progress.FOLDER, "VARCHAR")).addColumn(new ColumnEntity(Progress.FILE_PATH, "VARCHAR")).addColumn(new ColumnEntity(Progress.FILE_NAME, "VARCHAR")).addColumn(new ColumnEntity(Progress.FRACTION, "VARCHAR")).addColumn(new ColumnEntity(Progress.TOTAL_SIZE, "INTEGER")).addColumn(new ColumnEntity(Progress.CURRENT_SIZE, "INTEGER")).addColumn(new ColumnEntity("status", "INTEGER")).addColumn(new ColumnEntity(str, "INTEGER")).addColumn(new ColumnEntity(str2, "INTEGER")).addColumn(new ColumnEntity(str3, "BLOB")).addColumn(new ColumnEntity(str4, "BLOB")).addColumn(new ColumnEntity(str5, "BLOB")).addColumn(new ColumnEntity(Progress.EXTRA3, "BLOB"));
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.cacheTableEntity.buildTableString());
        db.execSQL(this.cookieTableEntity.buildTableString());
        db.execSQL(this.downloadTableEntity.buildTableString());
        db.execSQL(this.uploadTableEntity.buildTableString());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DBUtils.isNeedUpgradeTable(db, this.cacheTableEntity)) {
            db.execSQL("DROP TABLE IF EXISTS cache");
        }
        if (DBUtils.isNeedUpgradeTable(db, this.cookieTableEntity)) {
            db.execSQL("DROP TABLE IF EXISTS cookie");
        }
        if (DBUtils.isNeedUpgradeTable(db, this.downloadTableEntity)) {
            db.execSQL("DROP TABLE IF EXISTS download");
        }
        if (DBUtils.isNeedUpgradeTable(db, this.uploadTableEntity)) {
            db.execSQL("DROP TABLE IF EXISTS upload");
        }
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
