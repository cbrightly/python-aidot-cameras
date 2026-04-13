package org.greenrobot.greendao.database;

import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

public class EncryptedDatabase implements Database {
    private final SQLiteDatabase delegate;

    public EncryptedDatabase(SQLiteDatabase delegate2) {
        this.delegate = delegate2;
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return this.delegate.rawQuery(sql, selectionArgs);
    }

    public void execSQL(String sql) {
        this.delegate.execSQL(sql);
    }

    public void beginTransaction() {
        this.delegate.beginTransaction();
    }

    public void endTransaction() {
        this.delegate.endTransaction();
    }

    public boolean inTransaction() {
        return this.delegate.inTransaction();
    }

    public void setTransactionSuccessful() {
        this.delegate.setTransactionSuccessful();
    }

    public void execSQL(String sql, Object[] bindArgs) {
        this.delegate.execSQL(sql, bindArgs);
    }

    public DatabaseStatement compileStatement(String sql) {
        return new EncryptedDatabaseStatement(this.delegate.compileStatement(sql));
    }

    public boolean isDbLockedByCurrentThread() {
        return this.delegate.isDbLockedByCurrentThread();
    }

    public boolean isOpen() {
        return this.delegate.isOpen();
    }

    public void close() {
        this.delegate.close();
    }

    public Object getRawDatabase() {
        return this.delegate;
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return this.delegate;
    }
}
