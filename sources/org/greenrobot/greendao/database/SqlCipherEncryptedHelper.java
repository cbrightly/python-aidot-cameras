package org.greenrobot.greendao.database;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

public class SqlCipherEncryptedHelper extends SQLiteOpenHelper implements DatabaseOpenHelper.EncryptedHelper {
    private final DatabaseOpenHelper delegate;

    public SqlCipherEncryptedHelper(DatabaseOpenHelper delegate2, Context context, String name, int version, boolean loadLibs) {
        super(context, name, (SQLiteDatabase.CursorFactory) null, version);
        this.delegate = delegate2;
        if (loadLibs) {
            SQLiteDatabase.loadLibs(context);
        }
    }

    private Database wrap(SQLiteDatabase sqLiteDatabase) {
        return new EncryptedDatabase(sqLiteDatabase);
    }

    public void onCreate(SQLiteDatabase db) {
        this.delegate.onCreate(wrap(db));
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.delegate.onUpgrade(wrap(db), oldVersion, newVersion);
    }

    public void onOpen(SQLiteDatabase db) {
        this.delegate.onOpen(wrap(db));
    }

    public Database getEncryptedReadableDb(String password) {
        return wrap(getReadableDatabase(password));
    }

    public Database getEncryptedReadableDb(char[] password) {
        return wrap(getReadableDatabase(password));
    }

    public Database getEncryptedWritableDb(String password) {
        return wrap(getWritableDatabase(password));
    }

    public Database getEncryptedWritableDb(char[] password) {
        return wrap(getWritableDatabase(password));
    }
}
