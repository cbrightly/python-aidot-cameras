package androidx.room;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class RoomOpenHelper extends SupportSQLiteOpenHelper.Callback {
    @Nullable
    private DatabaseConfiguration mConfiguration;
    @NonNull
    private final Delegate mDelegate;
    @NonNull
    private final String mIdentityHash;
    @NonNull
    private final String mLegacyHash;

    public RoomOpenHelper(@NonNull DatabaseConfiguration configuration, @NonNull Delegate delegate, @NonNull String identityHash, @NonNull String legacyHash) {
        super(delegate.version);
        this.mConfiguration = configuration;
        this.mDelegate = delegate;
        this.mIdentityHash = identityHash;
        this.mLegacyHash = legacyHash;
    }

    public RoomOpenHelper(@NonNull DatabaseConfiguration configuration, @NonNull Delegate delegate, @NonNull String legacyHash) {
        this(configuration, delegate, "", legacyHash);
    }

    public void onConfigure(SupportSQLiteDatabase db) {
        super.onConfigure(db);
    }

    public void onCreate(SupportSQLiteDatabase db) {
        boolean isEmptyDatabase = hasEmptySchema(db);
        this.mDelegate.createAllTables(db);
        if (!isEmptyDatabase) {
            ValidationResult result = this.mDelegate.onValidateSchema(db);
            if (!result.isValid) {
                throw new IllegalStateException("Pre-packaged database has an invalid schema: " + result.expectedFoundMsg);
            }
        }
        updateIdentity(db);
        this.mDelegate.onCreate(db);
    }

    public void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
        List<Migration> migrations;
        boolean migrated = false;
        DatabaseConfiguration databaseConfiguration = this.mConfiguration;
        if (!(databaseConfiguration == null || (migrations = databaseConfiguration.migrationContainer.findMigrationPath(oldVersion, newVersion)) == null)) {
            this.mDelegate.onPreMigrate(db);
            for (Migration migration : migrations) {
                migration.migrate(db);
            }
            ValidationResult result = this.mDelegate.onValidateSchema(db);
            if (result.isValid) {
                this.mDelegate.onPostMigrate(db);
                updateIdentity(db);
                migrated = true;
            } else {
                throw new IllegalStateException("Migration didn't properly handle: " + result.expectedFoundMsg);
            }
        }
        if (!migrated) {
            DatabaseConfiguration databaseConfiguration2 = this.mConfiguration;
            if (databaseConfiguration2 == null || databaseConfiguration2.isMigrationRequired(oldVersion, newVersion)) {
                throw new IllegalStateException("A migration from " + oldVersion + " to " + newVersion + " was required but not found. Please provide the necessary Migration path via RoomDatabase.Builder.addMigration(Migration ...) or allow for destructive migrations via one of the RoomDatabase.Builder.fallbackToDestructiveMigration* methods.");
            }
            this.mDelegate.dropAllTables(db);
            this.mDelegate.createAllTables(db);
        }
    }

    public void onDowngrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void onOpen(SupportSQLiteDatabase db) {
        super.onOpen(db);
        checkIdentity(db);
        this.mDelegate.onOpen(db);
        this.mConfiguration = null;
    }

    /* JADX INFO: finally extract failed */
    private void checkIdentity(SupportSQLiteDatabase db) {
        if (hasRoomMasterTable(db)) {
            String identityHash = null;
            Cursor cursor = db.query((SupportSQLiteQuery) new SimpleSQLiteQuery(RoomMasterTable.READ_QUERY));
            try {
                if (cursor.moveToFirst()) {
                    identityHash = cursor.getString(0);
                }
                cursor.close();
                if (!this.mIdentityHash.equals(identityHash) && !this.mLegacyHash.equals(identityHash)) {
                    throw new IllegalStateException("Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number.");
                }
            } catch (Throwable th) {
                cursor.close();
                throw th;
            }
        } else {
            ValidationResult result = this.mDelegate.onValidateSchema(db);
            if (result.isValid) {
                this.mDelegate.onPostMigrate(db);
                updateIdentity(db);
                return;
            }
            throw new IllegalStateException("Pre-packaged database has an invalid schema: " + result.expectedFoundMsg);
        }
    }

    private void updateIdentity(SupportSQLiteDatabase db) {
        createMasterTableIfNotExists(db);
        db.execSQL(RoomMasterTable.createInsertQuery(this.mIdentityHash));
    }

    private void createMasterTableIfNotExists(SupportSQLiteDatabase db) {
        db.execSQL(RoomMasterTable.CREATE_QUERY);
    }

    private static boolean hasRoomMasterTable(SupportSQLiteDatabase db) {
        Cursor cursor = db.query("SELECT 1 FROM sqlite_master WHERE type = 'table' AND name='room_master_table'");
        try {
            boolean z = false;
            if (cursor.moveToFirst() && cursor.getInt(0) != 0) {
                z = true;
            }
            return z;
        } finally {
            cursor.close();
        }
    }

    private static boolean hasEmptySchema(SupportSQLiteDatabase db) {
        Cursor cursor = db.query("SELECT count(*) FROM sqlite_master WHERE name != 'android_metadata'");
        try {
            boolean z = false;
            if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
                z = true;
            }
            return z;
        } finally {
            cursor.close();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static abstract class Delegate {
        public final int version;

        /* access modifiers changed from: protected */
        public abstract void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase);

        /* access modifiers changed from: protected */
        public abstract void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase);

        /* access modifiers changed from: protected */
        public abstract void onCreate(SupportSQLiteDatabase supportSQLiteDatabase);

        /* access modifiers changed from: protected */
        public abstract void onOpen(SupportSQLiteDatabase supportSQLiteDatabase);

        public Delegate(int version2) {
            this.version = version2;
        }

        /* access modifiers changed from: protected */
        @Deprecated
        public void validateMigration(SupportSQLiteDatabase db) {
            throw new UnsupportedOperationException("validateMigration is deprecated");
        }

        /* access modifiers changed from: protected */
        @NonNull
        public ValidationResult onValidateSchema(@NonNull SupportSQLiteDatabase db) {
            validateMigration(db);
            return new ValidationResult(true, (String) null);
        }

        /* access modifiers changed from: protected */
        public void onPreMigrate(SupportSQLiteDatabase database) {
        }

        /* access modifiers changed from: protected */
        public void onPostMigrate(SupportSQLiteDatabase database) {
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static class ValidationResult {
        @Nullable
        public final String expectedFoundMsg;
        public final boolean isValid;

        public ValidationResult(boolean isValid2, @Nullable String expectedFoundMsg2) {
            this.isValid = isValid2;
            this.expectedFoundMsg = expectedFoundMsg2;
        }
    }
}
