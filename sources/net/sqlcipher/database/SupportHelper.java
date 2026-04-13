package net.sqlcipher.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import net.sqlcipher.database.SQLiteDatabase;

public class SupportHelper implements SupportSQLiteOpenHelper {
    private final boolean clearPassphrase;
    private byte[] passphrase;
    private SQLiteOpenHelper standardHelper;

    SupportHelper(SupportSQLiteOpenHelper.Configuration configuration, byte[] passphrase2, SQLiteDatabaseHook hook, boolean clearPassphrase2) {
        SQLiteDatabase.loadLibs(configuration.context);
        this.passphrase = passphrase2;
        this.clearPassphrase = clearPassphrase2;
        final SupportSQLiteOpenHelper.Configuration configuration2 = configuration;
        this.standardHelper = new SQLiteOpenHelper(configuration.context, configuration.name, (SQLiteDatabase.CursorFactory) null, configuration.callback.version, hook) {
            public void onCreate(SQLiteDatabase db) {
                configuration2.callback.onCreate(db);
            }

            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                configuration2.callback.onUpgrade(db, oldVersion, newVersion);
            }

            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                configuration2.callback.onDowngrade(db, oldVersion, newVersion);
            }

            public void onOpen(SQLiteDatabase db) {
                configuration2.callback.onOpen(db);
            }

            public void onConfigure(SQLiteDatabase db) {
                configuration2.callback.onConfigure(db);
            }
        };
    }

    public String getDatabaseName() {
        return this.standardHelper.getDatabaseName();
    }

    public void setWriteAheadLoggingEnabled(boolean enabled) {
        this.standardHelper.setWriteAheadLoggingEnabled(enabled);
    }

    public SupportSQLiteDatabase getWritableDatabase() {
        try {
            SQLiteDatabase result = this.standardHelper.getWritableDatabase(this.passphrase);
            if (this.clearPassphrase && this.passphrase != null) {
                int i = 0;
                while (true) {
                    byte[] bArr = this.passphrase;
                    if (i >= bArr.length) {
                        break;
                    }
                    bArr[i] = 0;
                    i++;
                }
            }
            return result;
        } catch (SQLiteException ex) {
            byte[] bArr2 = this.passphrase;
            if (bArr2 != null) {
                boolean isCleared = true;
                for (byte b : bArr2) {
                    isCleared = isCleared && b == 0;
                }
                if (isCleared) {
                    throw new IllegalStateException("The passphrase appears to be cleared. This happens bydefault the first time you use the factory to open a database, so we can remove thecleartext passphrase from memory. If you close the database yourself, please use afresh SupportFactory to reopen it. If something else (e.g., Room) closed thedatabase, and you cannot control that, use SupportFactory boolean constructor option to opt out of the automatic password clearing step. See the project README for more information.", ex);
                }
            }
            throw ex;
        }
    }

    public SupportSQLiteDatabase getReadableDatabase() {
        return getWritableDatabase();
    }

    public void close() {
        this.standardHelper.close();
    }
}
