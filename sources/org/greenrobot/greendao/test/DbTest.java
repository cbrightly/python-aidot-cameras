package org.greenrobot.greendao.test;

import android.app.Application;
import android.app.Instrumentation;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import java.util.Random;
import junit.framework.TestCase;
import org.greenrobot.greendao.DaoLog;
import org.greenrobot.greendao.DbUtils;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;

public abstract class DbTest extends AndroidTestCase {
    public static final String DB_NAME = "greendao-unittest-db.temp";
    private Application application;
    protected Database db;
    protected final boolean inMemory;
    protected final Random random;

    public DbTest() {
        this(true);
    }

    public DbTest(boolean inMemory2) {
        this.inMemory = inMemory2;
        this.random = new Random();
    }

    /* access modifiers changed from: protected */
    public void setUp() {
        DbTest.super.setUp();
        this.db = createDatabase();
    }

    public <T extends Application> T createApplication(Class<T> appClass) {
        TestCase.assertNull("Application already created", this.application);
        try {
            T app = Instrumentation.newApplication(appClass, getContext());
            app.onCreate();
            this.application = app;
            return app;
        } catch (Exception e) {
            throw new RuntimeException("Could not create application " + appClass, e);
        }
    }

    public void terminateApplication() {
        TestCase.assertNotNull("Application not yet created", this.application);
        this.application.onTerminate();
        this.application = null;
    }

    public <T extends Application> T getApplication() {
        TestCase.assertNotNull("Application not yet created", this.application);
        return this.application;
    }

    /* access modifiers changed from: protected */
    public Database createDatabase() {
        SQLiteDatabase sqLiteDatabase;
        if (this.inMemory) {
            sqLiteDatabase = SQLiteDatabase.create((SQLiteDatabase.CursorFactory) null);
        } else {
            getContext().deleteDatabase(DB_NAME);
            sqLiteDatabase = getContext().openOrCreateDatabase(DB_NAME, 0, (SQLiteDatabase.CursorFactory) null);
        }
        return new StandardDatabase(sqLiteDatabase);
    }

    /* access modifiers changed from: protected */
    public void tearDown() {
        if (this.application != null) {
            terminateApplication();
        }
        this.db.close();
        if (!this.inMemory) {
            getContext().deleteDatabase(DB_NAME);
        }
        DbTest.super.tearDown();
    }

    /* access modifiers changed from: protected */
    public void logTableDump(String tablename) {
        Database database = this.db;
        if (database instanceof StandardDatabase) {
            DbUtils.logTableDump(((StandardDatabase) database).getSQLiteDatabase(), tablename);
            return;
        }
        DaoLog.w("Table dump unsupported for " + this.db);
    }
}
