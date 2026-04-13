package com.leedarson.serviceimpl.database.manager;

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
public class b extends AbstractDaoMaster {
    public static ChangeQuickRedirect changeQuickRedirect;

    public /* bridge */ /* synthetic */ AbstractDaoSession newSession() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7859, new Class[0], AbstractDaoSession.class);
        return proxy.isSupported ? (AbstractDaoSession) proxy.result : a();
    }

    public /* bridge */ /* synthetic */ AbstractDaoSession newSession(IdentityScopeType identityScopeType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{identityScopeType}, this, changeQuickRedirect, false, 7858, new Class[]{IdentityScopeType.class}, AbstractDaoSession.class);
        return proxy.isSupported ? (AbstractDaoSession) proxy.result : b(identityScopeType);
    }

    public static void createAllTables(Database db, boolean ifNotExists) {
        if (!PatchProxy.proxy(new Object[]{db, new Byte(ifNotExists ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 7853, new Class[]{Database.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            NoteDao.createTable(db, ifNotExists);
        }
    }

    public static void dropAllTables(Database db, boolean ifExists) {
        if (!PatchProxy.proxy(new Object[]{db, new Byte(ifExists ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 7854, new Class[]{Database.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            NoteDao.dropTable(db, ifExists);
        }
    }

    public b(Database db) {
        super(db, 1);
        registerDaoClass(NoteDao.class);
    }

    public c a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7856, new Class[0], c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        return new c(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    public c b(IdentityScopeType type) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{type}, this, changeQuickRedirect, false, 7857, new Class[]{IdentityScopeType.class}, c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        return new c(this.db, type, this.daoConfigMap);
    }

    /* renamed from: com.leedarson.serviceimpl.database.manager.b$b  reason: collision with other inner class name */
    /* compiled from: DaoMaster */
    public static abstract class C0137b extends DatabaseOpenHelper {
        public static ChangeQuickRedirect changeQuickRedirect;

        public C0137b(Context context, String name) {
            super(context, name, 1);
        }

        public void onCreate(Database db) {
            if (!PatchProxy.proxy(new Object[]{db}, this, changeQuickRedirect, false, 7861, new Class[]{Database.class}, Void.TYPE).isSupported) {
                Log.i("greenDAO", "Creating tables for schema version 1");
                b.createAllTables(db, false);
            }
        }
    }

    /* compiled from: DaoMaster */
    public static class a extends C0137b {
        public static ChangeQuickRedirect changeQuickRedirect;

        public a(Context context, String name) {
            super(context, name);
        }

        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Object[] objArr = {db, new Integer(oldVersion), new Integer(newVersion)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7860, new Class[]{Database.class, cls, cls}, Void.TYPE).isSupported) {
                Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
                b.dropAllTables(db, true);
                onCreate(db);
            }
        }
    }
}
