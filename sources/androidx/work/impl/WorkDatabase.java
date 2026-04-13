package androidx.work.impl;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.work.Data;
import androidx.work.impl.WorkDatabaseMigrations;
import androidx.work.impl.model.Dependency;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.Preference;
import androidx.work.impl.model.PreferenceDao;
import androidx.work.impl.model.RawWorkInfoDao;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.model.WorkName;
import androidx.work.impl.model.WorkNameDao;
import androidx.work.impl.model.WorkProgress;
import androidx.work.impl.model.WorkProgressDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkTag;
import androidx.work.impl.model.WorkTagDao;
import androidx.work.impl.model.WorkTypeConverters;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Database(entities = {Dependency.class, WorkSpec.class, WorkTag.class, SystemIdInfo.class, WorkName.class, WorkProgress.class, Preference.class}, version = 12)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
@TypeConverters({Data.class, WorkTypeConverters.class})
public abstract class WorkDatabase extends RoomDatabase {
    private static final String PRUNE_SQL_FORMAT_PREFIX = "DELETE FROM workspec WHERE state IN (2, 3, 5) AND (period_start_time + minimum_retention_duration) < ";
    private static final String PRUNE_SQL_FORMAT_SUFFIX = " AND (SELECT COUNT(*)=0 FROM dependency WHERE     prerequisite_id=id AND     work_spec_id NOT IN         (SELECT id FROM workspec WHERE state IN (2, 3, 5)))";
    private static final long PRUNE_THRESHOLD_MILLIS = TimeUnit.DAYS.toMillis(1);

    @NonNull
    public abstract DependencyDao dependencyDao();

    @NonNull
    public abstract PreferenceDao preferenceDao();

    @NonNull
    public abstract RawWorkInfoDao rawWorkInfoDao();

    @NonNull
    public abstract SystemIdInfoDao systemIdInfoDao();

    @NonNull
    public abstract WorkNameDao workNameDao();

    @NonNull
    public abstract WorkProgressDao workProgressDao();

    @NonNull
    public abstract WorkSpecDao workSpecDao();

    @NonNull
    public abstract WorkTagDao workTagDao();

    @NonNull
    public static WorkDatabase create(@NonNull final Context context, @NonNull Executor queryExecutor, boolean useTestDatabase) {
        RoomDatabase.Builder<WorkDatabase> builder;
        Class<WorkDatabase> cls = WorkDatabase.class;
        if (useTestDatabase) {
            builder = Room.inMemoryDatabaseBuilder(context, cls).allowMainThreadQueries();
        } else {
            builder = Room.databaseBuilder(context, cls, WorkDatabasePathHelper.getWorkDatabaseName());
            builder.openHelperFactory(new SupportSQLiteOpenHelper.Factory() {
                @NonNull
                public SupportSQLiteOpenHelper create(@NonNull SupportSQLiteOpenHelper.Configuration configuration) {
                    SupportSQLiteOpenHelper.Configuration.Builder configBuilder = SupportSQLiteOpenHelper.Configuration.builder(context);
                    configBuilder.name(configuration.name).callback(configuration.callback).noBackupDirectory(true);
                    return new FrameworkSQLiteOpenHelperFactory().create(configBuilder.build());
                }
            });
        }
        return builder.setQueryExecutor(queryExecutor).addCallback(generateCleanupCallback()).addMigrations(WorkDatabaseMigrations.MIGRATION_1_2).addMigrations(new WorkDatabaseMigrations.RescheduleMigration(context, 2, 3)).addMigrations(WorkDatabaseMigrations.MIGRATION_3_4).addMigrations(WorkDatabaseMigrations.MIGRATION_4_5).addMigrations(new WorkDatabaseMigrations.RescheduleMigration(context, 5, 6)).addMigrations(WorkDatabaseMigrations.MIGRATION_6_7).addMigrations(WorkDatabaseMigrations.MIGRATION_7_8).addMigrations(WorkDatabaseMigrations.MIGRATION_8_9).addMigrations(new WorkDatabaseMigrations.WorkMigration9To10(context)).addMigrations(new WorkDatabaseMigrations.RescheduleMigration(context, 10, 11)).addMigrations(WorkDatabaseMigrations.MIGRATION_11_12).fallbackToDestructiveMigration().build();
    }

    static RoomDatabase.Callback generateCleanupCallback() {
        return new RoomDatabase.Callback() {
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                db.beginTransaction();
                try {
                    db.execSQL(WorkDatabase.getPruneSQL());
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }
        };
    }

    @NonNull
    static String getPruneSQL() {
        return PRUNE_SQL_FORMAT_PREFIX + getPruneDate() + PRUNE_SQL_FORMAT_SUFFIX;
    }

    static long getPruneDate() {
        return System.currentTimeMillis() - PRUNE_THRESHOLD_MILLIS;
    }
}
