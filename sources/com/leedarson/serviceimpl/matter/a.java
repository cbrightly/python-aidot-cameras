package com.leedarson.serviceimpl.matter;

import android.content.Context;
import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

/* compiled from: DaoMaster */
public class a extends AbstractDaoMaster {
    public static ChangeQuickRedirect changeQuickRedirect;

    public /* bridge */ /* synthetic */ AbstractDaoSession newSession() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8422, new Class[0], AbstractDaoSession.class);
        return proxy.isSupported ? (AbstractDaoSession) proxy.result : a();
    }

    public /* bridge */ /* synthetic */ AbstractDaoSession newSession(IdentityScopeType identityScopeType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{identityScopeType}, this, changeQuickRedirect, false, 8421, new Class[]{IdentityScopeType.class}, AbstractDaoSession.class);
        return proxy.isSupported ? (AbstractDaoSession) proxy.result : b(identityScopeType);
    }

    public static void createAllTables(Database db, boolean ifNotExists) {
        if (!PatchProxy.proxy(new Object[]{db, new Byte(ifNotExists ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 8416, new Class[]{Database.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            MatterDeviceBeanDao.createTable(db, ifNotExists);
        }
    }

    public static void dropAllTables(Database db, boolean ifExists) {
        if (!PatchProxy.proxy(new Object[]{db, new Byte(ifExists ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 8417, new Class[]{Database.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            MatterDeviceBeanDao.dropTable(db, ifExists);
        }
    }

    public a(Database db) {
        super(db, 1);
        registerDaoClass(MatterDeviceBeanDao.class);
    }

    public b a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8419, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        return new b(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    public b b(IdentityScopeType type) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{type}, this, changeQuickRedirect, false, 8420, new Class[]{IdentityScopeType.class}, b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        return new b(this.db, type, this.daoConfigMap);
    }

    /* compiled from: DaoMaster */
    public static abstract class b extends DatabaseOpenHelper {
        public static ChangeQuickRedirect changeQuickRedirect;

        public b(Context context, String name) {
            super(context, name, 1);
        }

        public void onCreate(Database db) {
            if (!PatchProxy.proxy(new Object[]{db}, this, changeQuickRedirect, false, 8424, new Class[]{Database.class}, Void.TYPE).isSupported) {
                Log.i("greenDAO", "Creating tables for schema version 1");
                a.createAllTables(db, false);
            }
        }
    }

    /* renamed from: com.leedarson.serviceimpl.matter.a$a  reason: collision with other inner class name */
    /* compiled from: DaoMaster */
    public static class C0151a extends b {
        public static ChangeQuickRedirect changeQuickRedirect;

        public C0151a(Context context, String name) {
            super(context, name);
        }

        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Object[] objArr = {db, new Integer(oldVersion), new Integer(newVersion)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8423, new Class[]{Database.class, cls, cls}, Void.TYPE).isSupported) {
                Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
                a.dropAllTables(db, true);
                onCreate(db);
            }
        }
    }
}
