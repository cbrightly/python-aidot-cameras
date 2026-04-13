package net.sqlcipher.database;

import androidx.sqlite.db.SupportSQLiteOpenHelper;

public class SupportFactory implements SupportSQLiteOpenHelper.Factory {
    private final boolean clearPassphrase;
    private final SQLiteDatabaseHook hook;
    private final byte[] passphrase;

    public SupportFactory(byte[] passphrase2) {
        this(passphrase2, (SQLiteDatabaseHook) null);
    }

    public SupportFactory(byte[] passphrase2, SQLiteDatabaseHook hook2) {
        this(passphrase2, hook2, true);
    }

    public SupportFactory(byte[] passphrase2, SQLiteDatabaseHook hook2, boolean clearPassphrase2) {
        this.passphrase = passphrase2;
        this.hook = hook2;
        this.clearPassphrase = clearPassphrase2;
    }

    public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration configuration) {
        return new SupportHelper(configuration, this.passphrase, this.hook, this.clearPassphrase);
    }
}
