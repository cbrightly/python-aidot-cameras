package org.greenrobot.greendao.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.greenrobot.greendao.DaoException;

public abstract class DatabaseOpenHelper extends SQLiteOpenHelper {
    private final Context context;
    private EncryptedHelper encryptedHelper;
    private boolean loadSQLCipherNativeLibs;
    private final String name;
    private final int version;

    public interface EncryptedHelper {
        Database getEncryptedReadableDb(String str);

        Database getEncryptedReadableDb(char[] cArr);

        Database getEncryptedWritableDb(String str);

        Database getEncryptedWritableDb(char[] cArr);
    }

    public DatabaseOpenHelper(Context context2, String name2, int version2) {
        this(context2, name2, (SQLiteDatabase.CursorFactory) null, version2);
    }

    public DatabaseOpenHelper(Context context2, String name2, SQLiteDatabase.CursorFactory factory, int version2) {
        super(context2, name2, factory, version2);
        this.loadSQLCipherNativeLibs = true;
        this.context = context2;
        this.name = name2;
        this.version = version2;
    }

    @SuppressLint({"NewApi"})
    public DatabaseOpenHelper(Context context2, String name2, SQLiteDatabase.CursorFactory factory, int version2, DatabaseErrorHandler errorHandler) {
        super(context2, name2, factory, version2, errorHandler);
        this.loadSQLCipherNativeLibs = true;
        this.context = context2;
        this.name = name2;
        this.version = version2;
    }

    public void setLoadSQLCipherNativeLibs(boolean loadSQLCipherNativeLibs2) {
        this.loadSQLCipherNativeLibs = loadSQLCipherNativeLibs2;
    }

    public Database getWritableDb() {
        return wrap(getWritableDatabase());
    }

    public Database getReadableDb() {
        return wrap(getReadableDatabase());
    }

    /* access modifiers changed from: protected */
    public Database wrap(SQLiteDatabase sqLiteDatabase) {
        return new StandardDatabase(sqLiteDatabase);
    }

    public void onCreate(SQLiteDatabase db) {
        onCreate(wrap(db));
    }

    public void onCreate(Database db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(wrap(db), oldVersion, newVersion);
    }

    public void onUpgrade(Database db, int oldVersion, int newVersion) {
    }

    public void onOpen(SQLiteDatabase db) {
        onOpen(wrap(db));
    }

    public void onOpen(Database db) {
    }

    private EncryptedHelper checkEncryptedHelper() {
        if (this.encryptedHelper == null) {
            try {
                Class.forName("net.sqlcipher.database.SQLiteOpenHelper");
                try {
                    this.encryptedHelper = (EncryptedHelper) Class.forName("org.greenrobot.greendao.database.SqlCipherEncryptedHelper").getConstructor(new Class[]{DatabaseOpenHelper.class, Context.class, String.class, Integer.TYPE, Boolean.TYPE}).newInstance(new Object[]{this, this.context, this.name, Integer.valueOf(this.version), Boolean.valueOf(this.loadSQLCipherNativeLibs)});
                } catch (Exception e) {
                    throw new DaoException((Throwable) e);
                }
            } catch (ClassNotFoundException e2) {
                throw new DaoException("Using an encrypted database requires SQLCipher, make sure to add it to dependencies: https://greenrobot.org/greendao/documentation/database-encryption/");
            }
        }
        return this.encryptedHelper;
    }

    public Database getEncryptedWritableDb(String password) {
        return checkEncryptedHelper().getEncryptedWritableDb(password);
    }

    public Database getEncryptedWritableDb(char[] password) {
        return checkEncryptedHelper().getEncryptedWritableDb(password);
    }

    public Database getEncryptedReadableDb(String password) {
        return checkEncryptedHelper().getEncryptedReadableDb(password);
    }

    public Database getEncryptedReadableDb(char[] password) {
        return checkEncryptedHelper().getEncryptedReadableDb(password);
    }
}
