package androidx.room;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public class DatabaseConfiguration {
    public final boolean allowDestructiveMigrationOnDowngrade;
    public final boolean allowMainThreadQueries;
    @Nullable
    public final List<RoomDatabase.Callback> callbacks;
    @NonNull
    public final Context context;
    @Nullable
    public final String copyFromAssetPath;
    @Nullable
    public final File copyFromFile;
    public final RoomDatabase.JournalMode journalMode;
    private final Set<Integer> mMigrationNotRequiredFrom;
    @NonNull
    public final RoomDatabase.MigrationContainer migrationContainer;
    public final boolean multiInstanceInvalidation;
    @Nullable
    public final String name;
    @NonNull
    public final Executor queryExecutor;
    public final boolean requireMigration;
    @NonNull
    public final SupportSQLiteOpenHelper.Factory sqliteOpenHelperFactory;
    @NonNull
    public final Executor transactionExecutor;

    @Deprecated
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public DatabaseConfiguration(@NonNull Context context2, @Nullable String name2, @NonNull SupportSQLiteOpenHelper.Factory sqliteOpenHelperFactory2, @NonNull RoomDatabase.MigrationContainer migrationContainer2, @Nullable List<RoomDatabase.Callback> callbacks2, boolean allowMainThreadQueries2, RoomDatabase.JournalMode journalMode2, @NonNull Executor queryExecutor2, boolean requireMigration2, @Nullable Set<Integer> migrationNotRequiredFrom) {
        this(context2, name2, sqliteOpenHelperFactory2, migrationContainer2, callbacks2, allowMainThreadQueries2, journalMode2, queryExecutor2, queryExecutor2, false, requireMigration2, false, migrationNotRequiredFrom, (String) null, (File) null);
    }

    @Deprecated
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public DatabaseConfiguration(@NonNull Context context2, @Nullable String name2, @NonNull SupportSQLiteOpenHelper.Factory sqliteOpenHelperFactory2, @NonNull RoomDatabase.MigrationContainer migrationContainer2, @Nullable List<RoomDatabase.Callback> callbacks2, boolean allowMainThreadQueries2, RoomDatabase.JournalMode journalMode2, @NonNull Executor queryExecutor2, @NonNull Executor transactionExecutor2, boolean multiInstanceInvalidation2, boolean requireMigration2, boolean allowDestructiveMigrationOnDowngrade2, @Nullable Set<Integer> migrationNotRequiredFrom) {
        this(context2, name2, sqliteOpenHelperFactory2, migrationContainer2, callbacks2, allowMainThreadQueries2, journalMode2, queryExecutor2, transactionExecutor2, multiInstanceInvalidation2, requireMigration2, allowDestructiveMigrationOnDowngrade2, migrationNotRequiredFrom, (String) null, (File) null);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public DatabaseConfiguration(@NonNull Context context2, @Nullable String name2, @NonNull SupportSQLiteOpenHelper.Factory sqliteOpenHelperFactory2, @NonNull RoomDatabase.MigrationContainer migrationContainer2, @Nullable List<RoomDatabase.Callback> callbacks2, boolean allowMainThreadQueries2, RoomDatabase.JournalMode journalMode2, @NonNull Executor queryExecutor2, @NonNull Executor transactionExecutor2, boolean multiInstanceInvalidation2, boolean requireMigration2, boolean allowDestructiveMigrationOnDowngrade2, @Nullable Set<Integer> migrationNotRequiredFrom, @Nullable String copyFromAssetPath2, @Nullable File copyFromFile2) {
        this.sqliteOpenHelperFactory = sqliteOpenHelperFactory2;
        this.context = context2;
        this.name = name2;
        this.migrationContainer = migrationContainer2;
        this.callbacks = callbacks2;
        this.allowMainThreadQueries = allowMainThreadQueries2;
        this.journalMode = journalMode2;
        this.queryExecutor = queryExecutor2;
        this.transactionExecutor = transactionExecutor2;
        this.multiInstanceInvalidation = multiInstanceInvalidation2;
        this.requireMigration = requireMigration2;
        this.allowDestructiveMigrationOnDowngrade = allowDestructiveMigrationOnDowngrade2;
        this.mMigrationNotRequiredFrom = migrationNotRequiredFrom;
        this.copyFromAssetPath = copyFromAssetPath2;
        this.copyFromFile = copyFromFile2;
    }

    @Deprecated
    public boolean isMigrationRequiredFrom(int version) {
        return isMigrationRequired(version, version + 1);
    }

    public boolean isMigrationRequired(int fromVersion, int toVersion) {
        Set<Integer> set;
        if ((fromVersion > toVersion) && this.allowDestructiveMigrationOnDowngrade) {
            return false;
        }
        if (!this.requireMigration || ((set = this.mMigrationNotRequiredFrom) != null && set.contains(Integer.valueOf(fromVersion)))) {
            return false;
        }
        return true;
    }
}
