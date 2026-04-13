package meshsdk.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 2;

    public static void createAllTables(Database db, boolean ifNotExists) {
        MeshDictBeanDao.createTable(db, ifNotExists);
        RandomOffsetBeanDao.createTable(db, ifNotExists);
    }

    public static void dropAllTables(Database db, boolean ifExists) {
        MeshDictBeanDao.dropTable(db, ifExists);
        RandomOffsetBeanDao.dropTable(db, ifExists);
    }

    public static DaoSession newDevSession(Context context, String name) {
        return new DaoMaster(new DevOpenHelper(context, name).getWritableDb()).newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this((Database) new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, 2);
        registerDaoClass(MeshDictBeanDao.class);
        registerDaoClass(RandomOffsetBeanDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(this.db, type, this.daoConfigMap);
    }

    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, 2);
        }

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory, 2);
        }

        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version 2");
            DaoMaster.createAllTables(db, false);
        }
    }

    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            DaoMaster.dropAllTables(db, true);
            onCreate(db);
        }
    }
}
