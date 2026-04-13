package com.didichuxing.doraemonkit.kit.network.room_db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.google.android.gms.actions.SearchIntents;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;
import java.util.HashSet;

public final class DokitDatabase_Impl extends DokitDatabase {
    private volatile MockApiDao _mockApiDao;

    /* access modifiers changed from: protected */
    public SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
            public void createAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `mock_intercept_api` (`id` TEXT NOT NULL, `mock_api_name` TEXT, `path` TEXT, `method` TEXT, `query` TEXT, `body` TEXT, `fromType` TEXT, `selected_scene_name` TEXT, `selected_scene_id` TEXT, `is_open` INTEGER NOT NULL, PRIMARY KEY(`id`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `mock_template_api` (`id` TEXT NOT NULL, `mock_api_name` TEXT, `path` TEXT, `method` TEXT, `query` TEXT, `body` TEXT, `fromType` TEXT, `is_open` INTEGER NOT NULL, `str_response` TEXT, `response_from` INTEGER NOT NULL, PRIMARY KEY(`id`))");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"b694b1ea0f5793ea98c0784434d20c1e\")");
            }

            public void dropAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("DROP TABLE IF EXISTS `mock_intercept_api`");
                _db.execSQL("DROP TABLE IF EXISTS `mock_template_api`");
            }

            /* access modifiers changed from: protected */
            public void onCreate(SupportSQLiteDatabase _db) {
                if (DokitDatabase_Impl.this.mCallbacks != null) {
                    int _size = DokitDatabase_Impl.this.mCallbacks.size();
                    for (int _i = 0; _i < _size; _i++) {
                        ((RoomDatabase.Callback) DokitDatabase_Impl.this.mCallbacks.get(_i)).onCreate(_db);
                    }
                }
            }

            public void onOpen(SupportSQLiteDatabase _db) {
                SupportSQLiteDatabase unused = DokitDatabase_Impl.this.mDatabase = _db;
                DokitDatabase_Impl.this.internalInitInvalidationTracker(_db);
                if (DokitDatabase_Impl.this.mCallbacks != null) {
                    int _size = DokitDatabase_Impl.this.mCallbacks.size();
                    for (int _i = 0; _i < _size; _i++) {
                        ((RoomDatabase.Callback) DokitDatabase_Impl.this.mCallbacks.get(_i)).onOpen(_db);
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void validateMigration(SupportSQLiteDatabase _db) {
                SupportSQLiteDatabase supportSQLiteDatabase = _db;
                HashMap<String, TableInfo.Column> _columnsMockInterceptApi = new HashMap<>(10);
                _columnsMockInterceptApi.put("id", new TableInfo.Column("id", "TEXT", true, 1));
                _columnsMockInterceptApi.put("mock_api_name", new TableInfo.Column("mock_api_name", "TEXT", false, 0));
                _columnsMockInterceptApi.put("path", new TableInfo.Column("path", "TEXT", false, 0));
                _columnsMockInterceptApi.put(FirebaseAnalytics.Param.METHOD, new TableInfo.Column(FirebaseAnalytics.Param.METHOD, "TEXT", false, 0));
                _columnsMockInterceptApi.put(SearchIntents.EXTRA_QUERY, new TableInfo.Column(SearchIntents.EXTRA_QUERY, "TEXT", false, 0));
                _columnsMockInterceptApi.put("body", new TableInfo.Column("body", "TEXT", false, 0));
                _columnsMockInterceptApi.put("fromType", new TableInfo.Column("fromType", "TEXT", false, 0));
                _columnsMockInterceptApi.put("selected_scene_name", new TableInfo.Column("selected_scene_name", "TEXT", false, 0));
                _columnsMockInterceptApi.put("selected_scene_id", new TableInfo.Column("selected_scene_id", "TEXT", false, 0));
                _columnsMockInterceptApi.put("is_open", new TableInfo.Column("is_open", "INTEGER", true, 0));
                HashSet<TableInfo.ForeignKey> _foreignKeysMockInterceptApi = new HashSet<>(0);
                HashSet<TableInfo.Index> _indicesMockInterceptApi = new HashSet<>(0);
                TableInfo _infoMockInterceptApi = new TableInfo("mock_intercept_api", _columnsMockInterceptApi, _foreignKeysMockInterceptApi, _indicesMockInterceptApi);
                TableInfo _existingMockInterceptApi = TableInfo.read(supportSQLiteDatabase, "mock_intercept_api");
                HashMap<String, TableInfo.Column> hashMap = _columnsMockInterceptApi;
                if (_infoMockInterceptApi.equals(_existingMockInterceptApi)) {
                    HashSet<TableInfo.ForeignKey> hashSet = _foreignKeysMockInterceptApi;
                    HashSet<TableInfo.Index> hashSet2 = _indicesMockInterceptApi;
                    HashMap<String, TableInfo.Column> _columnsMockTemplateApi = new HashMap<>(10);
                    TableInfo tableInfo = _existingMockInterceptApi;
                    _columnsMockTemplateApi.put("id", new TableInfo.Column("id", "TEXT", true, 1));
                    _columnsMockTemplateApi.put("mock_api_name", new TableInfo.Column("mock_api_name", "TEXT", false, 0));
                    _columnsMockTemplateApi.put("path", new TableInfo.Column("path", "TEXT", false, 0));
                    _columnsMockTemplateApi.put(FirebaseAnalytics.Param.METHOD, new TableInfo.Column(FirebaseAnalytics.Param.METHOD, "TEXT", false, 0));
                    _columnsMockTemplateApi.put(SearchIntents.EXTRA_QUERY, new TableInfo.Column(SearchIntents.EXTRA_QUERY, "TEXT", false, 0));
                    _columnsMockTemplateApi.put("body", new TableInfo.Column("body", "TEXT", false, 0));
                    _columnsMockTemplateApi.put("fromType", new TableInfo.Column("fromType", "TEXT", false, 0));
                    _columnsMockTemplateApi.put("is_open", new TableInfo.Column("is_open", "INTEGER", true, 0));
                    _columnsMockTemplateApi.put("str_response", new TableInfo.Column("str_response", "TEXT", false, 0));
                    _columnsMockTemplateApi.put("response_from", new TableInfo.Column("response_from", "INTEGER", true, 0));
                    TableInfo _infoMockTemplateApi = new TableInfo("mock_template_api", _columnsMockTemplateApi, new HashSet<>(0), new HashSet<>(0));
                    TableInfo _existingMockTemplateApi = TableInfo.read(supportSQLiteDatabase, "mock_template_api");
                    if (!_infoMockTemplateApi.equals(_existingMockTemplateApi)) {
                        throw new IllegalStateException("Migration didn't properly handle mock_template_api(com.didichuxing.doraemonkit.kit.network.room_db.MockTemplateApiBean).\n Expected:\n" + _infoMockTemplateApi + "\n Found:\n" + _existingMockTemplateApi);
                    }
                    return;
                }
                HashSet<TableInfo.ForeignKey> hashSet3 = _foreignKeysMockInterceptApi;
                throw new IllegalStateException("Migration didn't properly handle mock_intercept_api(com.didichuxing.doraemonkit.kit.network.room_db.MockInterceptApiBean).\n Expected:\n" + _infoMockInterceptApi + "\n Found:\n" + _existingMockInterceptApi);
            }
        }, "b694b1ea0f5793ea98c0784434d20c1e", "ddf2c7e64b1dc76d9e5278d540bac320")).build());
    }

    /* access modifiers changed from: protected */
    public InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, "mock_intercept_api", "mock_template_api");
    }

    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            _db.execSQL("DELETE FROM `mock_intercept_api`");
            _db.execSQL("DELETE FROM `mock_template_api`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            _db.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!_db.inTransaction()) {
                _db.execSQL("VACUUM");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public MockApiDao mockApiDao() {
        MockApiDao mockApiDao;
        if (this._mockApiDao != null) {
            return this._mockApiDao;
        }
        synchronized (this) {
            if (this._mockApiDao == null) {
                this._mockApiDao = new MockApiDao_Impl(this);
            }
            mockApiDao = this._mockApiDao;
        }
        return mockApiDao;
    }
}
