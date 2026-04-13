package androidx.work.impl;

import android.os.Build;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.DependencyDao_Impl;
import androidx.work.impl.model.PreferenceDao;
import androidx.work.impl.model.PreferenceDao_Impl;
import androidx.work.impl.model.RawWorkInfoDao;
import androidx.work.impl.model.RawWorkInfoDao_Impl;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.model.SystemIdInfoDao_Impl;
import androidx.work.impl.model.WorkNameDao;
import androidx.work.impl.model.WorkNameDao_Impl;
import androidx.work.impl.model.WorkProgressDao;
import androidx.work.impl.model.WorkProgressDao_Impl;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkSpecDao_Impl;
import androidx.work.impl.model.WorkTagDao;
import androidx.work.impl.model.WorkTagDao_Impl;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.leedarson.bean.Constants;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public final class WorkDatabase_Impl extends WorkDatabase {
    private volatile DependencyDao _dependencyDao;
    private volatile PreferenceDao _preferenceDao;
    private volatile RawWorkInfoDao _rawWorkInfoDao;
    private volatile SystemIdInfoDao _systemIdInfoDao;
    private volatile WorkNameDao _workNameDao;
    private volatile WorkProgressDao _workProgressDao;
    private volatile WorkSpecDao _workSpecDao;
    private volatile WorkTagDao _workTagDao;

    /* access modifiers changed from: protected */
    public SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(12) {
            public void createAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `Dependency` (`work_spec_id` TEXT NOT NULL, `prerequisite_id` TEXT NOT NULL, PRIMARY KEY(`work_spec_id`, `prerequisite_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE , FOREIGN KEY(`prerequisite_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                _db.execSQL("CREATE INDEX IF NOT EXISTS `index_Dependency_work_spec_id` ON `Dependency` (`work_spec_id`)");
                _db.execSQL("CREATE INDEX IF NOT EXISTS `index_Dependency_prerequisite_id` ON `Dependency` (`prerequisite_id`)");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `WorkSpec` (`id` TEXT NOT NULL, `state` INTEGER NOT NULL, `worker_class_name` TEXT NOT NULL, `input_merger_class_name` TEXT, `input` BLOB NOT NULL, `output` BLOB NOT NULL, `initial_delay` INTEGER NOT NULL, `interval_duration` INTEGER NOT NULL, `flex_duration` INTEGER NOT NULL, `run_attempt_count` INTEGER NOT NULL, `backoff_policy` INTEGER NOT NULL, `backoff_delay_duration` INTEGER NOT NULL, `period_start_time` INTEGER NOT NULL, `minimum_retention_duration` INTEGER NOT NULL, `schedule_requested_at` INTEGER NOT NULL, `run_in_foreground` INTEGER NOT NULL, `out_of_quota_policy` INTEGER NOT NULL, `required_network_type` INTEGER, `requires_charging` INTEGER NOT NULL, `requires_device_idle` INTEGER NOT NULL, `requires_battery_not_low` INTEGER NOT NULL, `requires_storage_not_low` INTEGER NOT NULL, `trigger_content_update_delay` INTEGER NOT NULL, `trigger_max_content_delay` INTEGER NOT NULL, `content_uri_triggers` BLOB, PRIMARY KEY(`id`))");
                _db.execSQL("CREATE INDEX IF NOT EXISTS `index_WorkSpec_schedule_requested_at` ON `WorkSpec` (`schedule_requested_at`)");
                _db.execSQL("CREATE INDEX IF NOT EXISTS `index_WorkSpec_period_start_time` ON `WorkSpec` (`period_start_time`)");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `WorkTag` (`tag` TEXT NOT NULL, `work_spec_id` TEXT NOT NULL, PRIMARY KEY(`tag`, `work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                _db.execSQL("CREATE INDEX IF NOT EXISTS `index_WorkTag_work_spec_id` ON `WorkTag` (`work_spec_id`)");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `SystemIdInfo` (`work_spec_id` TEXT NOT NULL, `system_id` INTEGER NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `WorkName` (`name` TEXT NOT NULL, `work_spec_id` TEXT NOT NULL, PRIMARY KEY(`name`, `work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                _db.execSQL("CREATE INDEX IF NOT EXISTS `index_WorkName_work_spec_id` ON `WorkName` (`work_spec_id`)");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `WorkProgress` (`work_spec_id` TEXT NOT NULL, `progress` BLOB NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `Preference` (`key` TEXT NOT NULL, `long_value` INTEGER, PRIMARY KEY(`key`))");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c103703e120ae8cc73c9248622f3cd1e')");
            }

            public void dropAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("DROP TABLE IF EXISTS `Dependency`");
                _db.execSQL("DROP TABLE IF EXISTS `WorkSpec`");
                _db.execSQL("DROP TABLE IF EXISTS `WorkTag`");
                _db.execSQL("DROP TABLE IF EXISTS `SystemIdInfo`");
                _db.execSQL("DROP TABLE IF EXISTS `WorkName`");
                _db.execSQL("DROP TABLE IF EXISTS `WorkProgress`");
                _db.execSQL("DROP TABLE IF EXISTS `Preference`");
                if (WorkDatabase_Impl.this.mCallbacks != null) {
                    int _size = WorkDatabase_Impl.this.mCallbacks.size();
                    for (int _i = 0; _i < _size; _i++) {
                        ((RoomDatabase.Callback) WorkDatabase_Impl.this.mCallbacks.get(_i)).onDestructiveMigration(_db);
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onCreate(SupportSQLiteDatabase _db) {
                if (WorkDatabase_Impl.this.mCallbacks != null) {
                    int _size = WorkDatabase_Impl.this.mCallbacks.size();
                    for (int _i = 0; _i < _size; _i++) {
                        ((RoomDatabase.Callback) WorkDatabase_Impl.this.mCallbacks.get(_i)).onCreate(_db);
                    }
                }
            }

            public void onOpen(SupportSQLiteDatabase _db) {
                SupportSQLiteDatabase unused = WorkDatabase_Impl.this.mDatabase = _db;
                _db.execSQL("PRAGMA foreign_keys = ON");
                WorkDatabase_Impl.this.internalInitInvalidationTracker(_db);
                if (WorkDatabase_Impl.this.mCallbacks != null) {
                    int _size = WorkDatabase_Impl.this.mCallbacks.size();
                    for (int _i = 0; _i < _size; _i++) {
                        ((RoomDatabase.Callback) WorkDatabase_Impl.this.mCallbacks.get(_i)).onOpen(_db);
                    }
                }
            }

            public void onPreMigrate(SupportSQLiteDatabase _db) {
                DBUtil.dropFtsSyncTriggers(_db);
            }

            public void onPostMigrate(SupportSQLiteDatabase _db) {
            }

            /* access modifiers changed from: protected */
            public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
                SupportSQLiteDatabase supportSQLiteDatabase = _db;
                HashMap<String, TableInfo.Column> _columnsDependency = new HashMap<>(2);
                _columnsDependency.put("work_spec_id", new TableInfo.Column("work_spec_id", "TEXT", true, 1, (String) null, 1));
                _columnsDependency.put("prerequisite_id", new TableInfo.Column("prerequisite_id", "TEXT", true, 2, (String) null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysDependency = new HashSet<>(2);
                _foreignKeysDependency.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList(new String[]{"work_spec_id"}), Arrays.asList(new String[]{"id"})));
                _foreignKeysDependency.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList(new String[]{"prerequisite_id"}), Arrays.asList(new String[]{"id"})));
                HashSet<TableInfo.Index> _indicesDependency = new HashSet<>(2);
                _indicesDependency.add(new TableInfo.Index("index_Dependency_work_spec_id", false, Arrays.asList(new String[]{"work_spec_id"})));
                _indicesDependency.add(new TableInfo.Index("index_Dependency_prerequisite_id", false, Arrays.asList(new String[]{"prerequisite_id"})));
                TableInfo _infoDependency = new TableInfo("Dependency", _columnsDependency, _foreignKeysDependency, _indicesDependency);
                TableInfo _existingDependency = TableInfo.read(supportSQLiteDatabase, "Dependency");
                if (!_infoDependency.equals(_existingDependency)) {
                    return new RoomOpenHelper.ValidationResult(false, "Dependency(androidx.work.impl.model.Dependency).\n Expected:\n" + _infoDependency + "\n Found:\n" + _existingDependency);
                }
                HashMap<String, TableInfo.Column> _columnsWorkSpec = new HashMap<>(25);
                _columnsWorkSpec.put("id", new TableInfo.Column("id", "TEXT", true, 1, (String) null, 1));
                _columnsWorkSpec.put(Constants.ACTION_STATE, new TableInfo.Column(Constants.ACTION_STATE, "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("worker_class_name", new TableInfo.Column("worker_class_name", "TEXT", true, 0, (String) null, 1));
                _columnsWorkSpec.put("input_merger_class_name", new TableInfo.Column("input_merger_class_name", "TEXT", false, 0, (String) null, 1));
                _columnsWorkSpec.put("input", new TableInfo.Column("input", "BLOB", true, 0, (String) null, 1));
                _columnsWorkSpec.put("output", new TableInfo.Column("output", "BLOB", true, 0, (String) null, 1));
                _columnsWorkSpec.put("initial_delay", new TableInfo.Column("initial_delay", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("interval_duration", new TableInfo.Column("interval_duration", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("flex_duration", new TableInfo.Column("flex_duration", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("run_attempt_count", new TableInfo.Column("run_attempt_count", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("backoff_policy", new TableInfo.Column("backoff_policy", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("backoff_delay_duration", new TableInfo.Column("backoff_delay_duration", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("period_start_time", new TableInfo.Column("period_start_time", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("minimum_retention_duration", new TableInfo.Column("minimum_retention_duration", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("schedule_requested_at", new TableInfo.Column("schedule_requested_at", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("run_in_foreground", new TableInfo.Column("run_in_foreground", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("out_of_quota_policy", new TableInfo.Column("out_of_quota_policy", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("required_network_type", new TableInfo.Column("required_network_type", "INTEGER", false, 0, (String) null, 1));
                _columnsWorkSpec.put("requires_charging", new TableInfo.Column("requires_charging", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("requires_device_idle", new TableInfo.Column("requires_device_idle", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("requires_battery_not_low", new TableInfo.Column("requires_battery_not_low", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("requires_storage_not_low", new TableInfo.Column("requires_storage_not_low", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("trigger_content_update_delay", new TableInfo.Column("trigger_content_update_delay", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("trigger_max_content_delay", new TableInfo.Column("trigger_max_content_delay", "INTEGER", true, 0, (String) null, 1));
                _columnsWorkSpec.put("content_uri_triggers", new TableInfo.Column("content_uri_triggers", "BLOB", false, 0, (String) null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysWorkSpec = new HashSet<>(0);
                HashSet<TableInfo.Index> _indicesWorkSpec = new HashSet<>(2);
                HashMap<String, TableInfo.Column> hashMap = _columnsDependency;
                _indicesWorkSpec.add(new TableInfo.Index("index_WorkSpec_schedule_requested_at", false, Arrays.asList(new String[]{"schedule_requested_at"})));
                _indicesWorkSpec.add(new TableInfo.Index("index_WorkSpec_period_start_time", false, Arrays.asList(new String[]{"period_start_time"})));
                TableInfo _infoWorkSpec = new TableInfo("WorkSpec", _columnsWorkSpec, _foreignKeysWorkSpec, _indicesWorkSpec);
                TableInfo _existingWorkSpec = TableInfo.read(supportSQLiteDatabase, "WorkSpec");
                if (!_infoWorkSpec.equals(_existingWorkSpec)) {
                    return new RoomOpenHelper.ValidationResult(false, "WorkSpec(androidx.work.impl.model.WorkSpec).\n Expected:\n" + _infoWorkSpec + "\n Found:\n" + _existingWorkSpec);
                }
                HashMap<String, TableInfo.Column> _columnsWorkTag = new HashMap<>(2);
                _columnsWorkTag.put(Progress.TAG, new TableInfo.Column(Progress.TAG, "TEXT", true, 1, (String) null, 1));
                _columnsWorkTag.put("work_spec_id", new TableInfo.Column("work_spec_id", "TEXT", true, 2, (String) null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysWorkTag = new HashSet<>(1);
                _foreignKeysWorkTag.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList(new String[]{"work_spec_id"}), Arrays.asList(new String[]{"id"})));
                TableInfo tableInfo = _infoWorkSpec;
                HashSet<TableInfo.Index> _indicesWorkTag = new HashSet<>(1);
                TableInfo tableInfo2 = _existingWorkSpec;
                HashSet<TableInfo.ForeignKey> hashSet = _foreignKeysDependency;
                TableInfo tableInfo3 = _infoDependency;
                _indicesWorkTag.add(new TableInfo.Index("index_WorkTag_work_spec_id", false, Arrays.asList(new String[]{"work_spec_id"})));
                TableInfo _infoWorkTag = new TableInfo("WorkTag", _columnsWorkTag, _foreignKeysWorkTag, _indicesWorkTag);
                TableInfo _existingWorkTag = TableInfo.read(supportSQLiteDatabase, "WorkTag");
                if (!_infoWorkTag.equals(_existingWorkTag)) {
                    return new RoomOpenHelper.ValidationResult(false, "WorkTag(androidx.work.impl.model.WorkTag).\n Expected:\n" + _infoWorkTag + "\n Found:\n" + _existingWorkTag);
                }
                HashMap<String, TableInfo.Column> _columnsSystemIdInfo = new HashMap<>(2);
                _columnsSystemIdInfo.put("work_spec_id", new TableInfo.Column("work_spec_id", "TEXT", true, 1, (String) null, 1));
                HashSet<TableInfo.Index> hashSet2 = _indicesWorkTag;
                _columnsSystemIdInfo.put("system_id", new TableInfo.Column("system_id", "INTEGER", true, 0, (String) null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysSystemIdInfo = new HashSet<>(1);
                _foreignKeysSystemIdInfo.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList(new String[]{"work_spec_id"}), Arrays.asList(new String[]{"id"})));
                TableInfo tableInfo4 = _infoWorkTag;
                HashSet<TableInfo.Index> _indicesSystemIdInfo = new HashSet<>(0);
                TableInfo tableInfo5 = _existingWorkTag;
                TableInfo _infoSystemIdInfo = new TableInfo("SystemIdInfo", _columnsSystemIdInfo, _foreignKeysSystemIdInfo, _indicesSystemIdInfo);
                TableInfo _existingSystemIdInfo = TableInfo.read(supportSQLiteDatabase, "SystemIdInfo");
                if (!_infoSystemIdInfo.equals(_existingSystemIdInfo)) {
                    StringBuilder sb = new StringBuilder();
                    HashSet<TableInfo.ForeignKey> hashSet3 = _foreignKeysSystemIdInfo;
                    sb.append("SystemIdInfo(androidx.work.impl.model.SystemIdInfo).\n Expected:\n");
                    sb.append(_infoSystemIdInfo);
                    sb.append("\n Found:\n");
                    sb.append(_existingSystemIdInfo);
                    return new RoomOpenHelper.ValidationResult(false, sb.toString());
                }
                HashSet<TableInfo.Index> hashSet4 = _indicesSystemIdInfo;
                HashMap<String, TableInfo.Column> _columnsWorkName = new HashMap<>(2);
                TableInfo tableInfo6 = _existingSystemIdInfo;
                _columnsWorkName.put("name", new TableInfo.Column("name", "TEXT", true, 1, (String) null, 1));
                _columnsWorkName.put("work_spec_id", new TableInfo.Column("work_spec_id", "TEXT", true, 2, (String) null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysWorkName = new HashSet<>(1);
                _foreignKeysWorkName.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList(new String[]{"work_spec_id"}), Arrays.asList(new String[]{"id"})));
                HashMap<String, TableInfo.Column> hashMap2 = _columnsSystemIdInfo;
                HashSet<TableInfo.Index> _indicesWorkName = new HashSet<>(1);
                HashSet<TableInfo.Index> hashSet5 = _indicesDependency;
                TableInfo tableInfo7 = _existingDependency;
                HashMap<String, TableInfo.Column> hashMap3 = _columnsWorkSpec;
                _indicesWorkName.add(new TableInfo.Index("index_WorkName_work_spec_id", false, Arrays.asList(new String[]{"work_spec_id"})));
                TableInfo _infoWorkName = new TableInfo("WorkName", _columnsWorkName, _foreignKeysWorkName, _indicesWorkName);
                TableInfo _existingWorkName = TableInfo.read(supportSQLiteDatabase, "WorkName");
                if (!_infoWorkName.equals(_existingWorkName)) {
                    return new RoomOpenHelper.ValidationResult(false, "WorkName(androidx.work.impl.model.WorkName).\n Expected:\n" + _infoWorkName + "\n Found:\n" + _existingWorkName);
                }
                HashMap<String, TableInfo.Column> _columnsWorkProgress = new HashMap<>(2);
                _columnsWorkProgress.put("work_spec_id", new TableInfo.Column("work_spec_id", "TEXT", true, 1, (String) null, 1));
                HashMap<String, TableInfo.Column> hashMap4 = _columnsWorkName;
                _columnsWorkProgress.put("progress", new TableInfo.Column("progress", "BLOB", true, 0, (String) null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysWorkProgress = new HashSet<>(1);
                _foreignKeysWorkProgress.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList(new String[]{"work_spec_id"}), Arrays.asList(new String[]{"id"})));
                HashSet<TableInfo.Index> _indicesWorkProgress = new HashSet<>(0);
                TableInfo _infoWorkProgress = new TableInfo("WorkProgress", _columnsWorkProgress, _foreignKeysWorkProgress, _indicesWorkProgress);
                TableInfo _existingWorkProgress = TableInfo.read(supportSQLiteDatabase, "WorkProgress");
                if (!_infoWorkProgress.equals(_existingWorkProgress)) {
                    HashSet<TableInfo.ForeignKey> hashSet6 = _foreignKeysWorkProgress;
                    HashSet<TableInfo.ForeignKey> hashSet7 = _foreignKeysWorkName;
                    StringBuilder sb2 = new StringBuilder();
                    HashSet<TableInfo.Index> hashSet8 = _indicesWorkProgress;
                    sb2.append("WorkProgress(androidx.work.impl.model.WorkProgress).\n Expected:\n");
                    sb2.append(_infoWorkProgress);
                    sb2.append("\n Found:\n");
                    sb2.append(_existingWorkProgress);
                    return new RoomOpenHelper.ValidationResult(false, sb2.toString());
                }
                HashSet<TableInfo.ForeignKey> hashSet9 = _foreignKeysWorkName;
                HashSet<TableInfo.Index> hashSet10 = _indicesWorkProgress;
                HashMap<String, TableInfo.Column> _columnsPreference = new HashMap<>(2);
                _columnsPreference.put(CacheEntity.KEY, new TableInfo.Column(CacheEntity.KEY, "TEXT", true, 1, (String) null, 1));
                _columnsPreference.put("long_value", new TableInfo.Column("long_value", "INTEGER", false, 0, (String) null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysPreference = new HashSet<>(0);
                HashSet<TableInfo.Index> hashSet11 = _indicesWorkName;
                TableInfo tableInfo8 = _infoWorkName;
                TableInfo _infoPreference = new TableInfo("Preference", _columnsPreference, _foreignKeysPreference, new HashSet<>(0));
                TableInfo _existingPreference = TableInfo.read(supportSQLiteDatabase, "Preference");
                if (!_infoPreference.equals(_existingPreference)) {
                    HashMap<String, TableInfo.Column> hashMap5 = _columnsPreference;
                    StringBuilder sb3 = new StringBuilder();
                    HashSet<TableInfo.ForeignKey> hashSet12 = _foreignKeysPreference;
                    sb3.append("Preference(androidx.work.impl.model.Preference).\n Expected:\n");
                    sb3.append(_infoPreference);
                    sb3.append("\n Found:\n");
                    sb3.append(_existingPreference);
                    return new RoomOpenHelper.ValidationResult(false, sb3.toString());
                }
                HashSet<TableInfo.ForeignKey> hashSet13 = _foreignKeysPreference;
                return new RoomOpenHelper.ValidationResult(true, (String) null);
            }
        }, "c103703e120ae8cc73c9248622f3cd1e", "49f946663a8deb7054212b8adda248c6")).build());
    }

    /* access modifiers changed from: protected */
    public InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap<>(0), new HashMap<>(0), "Dependency", "WorkSpec", "WorkTag", "SystemIdInfo", "WorkName", "WorkProgress", "Preference");
    }

    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
        boolean _supportsDeferForeignKeys = Build.VERSION.SDK_INT >= 21;
        if (!_supportsDeferForeignKeys) {
            try {
                _db.execSQL("PRAGMA foreign_keys = FALSE");
            } catch (Throwable th) {
                super.endTransaction();
                if (!_supportsDeferForeignKeys) {
                    _db.execSQL("PRAGMA foreign_keys = TRUE");
                }
                _db.query("PRAGMA wal_checkpoint(FULL)").close();
                if (!_db.inTransaction()) {
                    _db.execSQL("VACUUM");
                }
                throw th;
            }
        }
        super.beginTransaction();
        if (_supportsDeferForeignKeys) {
            _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
        }
        _db.execSQL("DELETE FROM `Dependency`");
        _db.execSQL("DELETE FROM `WorkSpec`");
        _db.execSQL("DELETE FROM `WorkTag`");
        _db.execSQL("DELETE FROM `SystemIdInfo`");
        _db.execSQL("DELETE FROM `WorkName`");
        _db.execSQL("DELETE FROM `WorkProgress`");
        _db.execSQL("DELETE FROM `Preference`");
        super.setTransactionSuccessful();
        super.endTransaction();
        if (!_supportsDeferForeignKeys) {
            _db.execSQL("PRAGMA foreign_keys = TRUE");
        }
        _db.query("PRAGMA wal_checkpoint(FULL)").close();
        if (!_db.inTransaction()) {
            _db.execSQL("VACUUM");
        }
    }

    public WorkSpecDao workSpecDao() {
        WorkSpecDao workSpecDao;
        if (this._workSpecDao != null) {
            return this._workSpecDao;
        }
        synchronized (this) {
            if (this._workSpecDao == null) {
                this._workSpecDao = new WorkSpecDao_Impl(this);
            }
            workSpecDao = this._workSpecDao;
        }
        return workSpecDao;
    }

    public DependencyDao dependencyDao() {
        DependencyDao dependencyDao;
        if (this._dependencyDao != null) {
            return this._dependencyDao;
        }
        synchronized (this) {
            if (this._dependencyDao == null) {
                this._dependencyDao = new DependencyDao_Impl(this);
            }
            dependencyDao = this._dependencyDao;
        }
        return dependencyDao;
    }

    public WorkTagDao workTagDao() {
        WorkTagDao workTagDao;
        if (this._workTagDao != null) {
            return this._workTagDao;
        }
        synchronized (this) {
            if (this._workTagDao == null) {
                this._workTagDao = new WorkTagDao_Impl(this);
            }
            workTagDao = this._workTagDao;
        }
        return workTagDao;
    }

    public SystemIdInfoDao systemIdInfoDao() {
        SystemIdInfoDao systemIdInfoDao;
        if (this._systemIdInfoDao != null) {
            return this._systemIdInfoDao;
        }
        synchronized (this) {
            if (this._systemIdInfoDao == null) {
                this._systemIdInfoDao = new SystemIdInfoDao_Impl(this);
            }
            systemIdInfoDao = this._systemIdInfoDao;
        }
        return systemIdInfoDao;
    }

    public WorkNameDao workNameDao() {
        WorkNameDao workNameDao;
        if (this._workNameDao != null) {
            return this._workNameDao;
        }
        synchronized (this) {
            if (this._workNameDao == null) {
                this._workNameDao = new WorkNameDao_Impl(this);
            }
            workNameDao = this._workNameDao;
        }
        return workNameDao;
    }

    public WorkProgressDao workProgressDao() {
        WorkProgressDao workProgressDao;
        if (this._workProgressDao != null) {
            return this._workProgressDao;
        }
        synchronized (this) {
            if (this._workProgressDao == null) {
                this._workProgressDao = new WorkProgressDao_Impl(this);
            }
            workProgressDao = this._workProgressDao;
        }
        return workProgressDao;
    }

    public PreferenceDao preferenceDao() {
        PreferenceDao preferenceDao;
        if (this._preferenceDao != null) {
            return this._preferenceDao;
        }
        synchronized (this) {
            if (this._preferenceDao == null) {
                this._preferenceDao = new PreferenceDao_Impl(this);
            }
            preferenceDao = this._preferenceDao;
        }
        return preferenceDao;
    }

    public RawWorkInfoDao rawWorkInfoDao() {
        RawWorkInfoDao rawWorkInfoDao;
        if (this._rawWorkInfoDao != null) {
            return this._rawWorkInfoDao;
        }
        synchronized (this) {
            if (this._rawWorkInfoDao == null) {
                this._rawWorkInfoDao = new RawWorkInfoDao_Impl(this);
            }
            rawWorkInfoDao = this._rawWorkInfoDao;
        }
        return rawWorkInfoDao;
    }
}
