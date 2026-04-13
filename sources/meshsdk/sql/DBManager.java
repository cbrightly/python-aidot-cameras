package meshsdk.sql;

import android.content.Context;
import meshsdk.sql.DaoMaster;

public class DBManager {
    public static String DB_NAME = "bleMesh.db";
    private static final boolean ENCRYPTED = false;
    private static DaoSession daoSession;
    private static DBManager sInstance;

    private DBManager() {
    }

    public static synchronized DBManager getInstance(Context context) {
        DBManager dBManager;
        synchronized (DBManager.class) {
            if (sInstance == null) {
                initGreenDao(context);
                sInstance = new DBManager();
            }
            dBManager = sInstance;
        }
        return dBManager;
    }

    private static void initGreenDao(Context context) {
        daoSession = new DaoMaster(new DaoMaster.DevOpenHelper(context, DB_NAME).getWritableDb()).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
