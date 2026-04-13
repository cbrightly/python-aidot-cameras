package com.leedarson.serviceimpl.database.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

public class NoteDao extends AbstractDao<e, Long> {
    public static final String TABLENAME = "NOTE";
    public static ChangeQuickRedirect changeQuickRedirect;

    public static class Properties {
        public static final Property a = new Property(0, Long.class, "_id", true, "_id");
        public static final Property b = new Property(1, String.class, "source", false, "SOURCE");
        public static final Property c = new Property(2, String.class, "url", false, "URL");
    }

    public /* bridge */ /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        Class[] clsArr = {SQLiteStatement.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{sQLiteStatement, obj}, this, changeQuickRedirect, false, 7885, clsArr, Void.TYPE).isSupported) {
            a(sQLiteStatement, (e) obj);
        }
    }

    public /* bridge */ /* synthetic */ void bindValues(DatabaseStatement databaseStatement, Object obj) {
        Class[] clsArr = {DatabaseStatement.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{databaseStatement, obj}, this, changeQuickRedirect, false, 7886, clsArr, Void.TYPE).isSupported) {
            b(databaseStatement, (e) obj);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7883, new Class[]{Object.class}, Object.class);
        return proxy.isSupported ? proxy.result : c((e) obj);
    }

    public /* bridge */ /* synthetic */ boolean hasKey(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7882, new Class[]{Object.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : d((e) obj);
    }

    public /* bridge */ /* synthetic */ Object readEntity(Cursor cursor, int i) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{cursor, new Integer(i)}, this, changeQuickRedirect, false, 7889, new Class[]{Cursor.class, Integer.TYPE}, Object.class);
        return proxy.isSupported ? proxy.result : e(cursor, i);
    }

    public /* bridge */ /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        Object[] objArr = {cursor, obj, new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7887, new Class[]{Cursor.class, Object.class, Integer.TYPE}, Void.TYPE).isSupported) {
            f(cursor, (e) obj, i);
        }
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, new Long(j)}, this, changeQuickRedirect, false, 7884, new Class[]{Object.class, Long.TYPE}, Object.class);
        return proxy.isSupported ? proxy.result : g((e) obj, j);
    }

    public NoteDao(DaoConfig config, c daoSession) {
        super(config, daoSession);
    }

    public static void createTable(Database db, boolean ifNotExists) {
        if (!PatchProxy.proxy(new Object[]{db, new Byte(ifNotExists ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 7872, new Class[]{Database.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            String constraint = ifNotExists ? "IF NOT EXISTS " : "";
            db.execSQL("CREATE TABLE " + constraint + "\"NOTE\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"SOURCE\" TEXT,\"URL\" TEXT NOT NULL );");
        }
    }

    public static void dropTable(Database db, boolean ifExists) {
        if (!PatchProxy.proxy(new Object[]{db, new Byte(ifExists ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 7873, new Class[]{Database.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            StringBuilder sb = new StringBuilder();
            sb.append("DROP TABLE ");
            sb.append(ifExists ? "IF EXISTS " : "");
            sb.append("\"NOTE\"");
            db.execSQL(sb.toString());
        }
    }

    public final void b(DatabaseStatement stmt, e entity) {
        if (!PatchProxy.proxy(new Object[]{stmt, entity}, this, changeQuickRedirect, false, 7874, new Class[]{DatabaseStatement.class, e.class}, Void.TYPE).isSupported) {
            stmt.clearBindings();
            Long _id = entity.c();
            if (_id != null) {
                stmt.bindLong(1, _id.longValue());
            }
            String source = entity.a();
            if (source != null) {
                stmt.bindString(2, source);
            }
            stmt.bindString(3, entity.b());
        }
    }

    public final void a(SQLiteStatement stmt, e entity) {
        if (!PatchProxy.proxy(new Object[]{stmt, entity}, this, changeQuickRedirect, false, 7875, new Class[]{SQLiteStatement.class, e.class}, Void.TYPE).isSupported) {
            stmt.clearBindings();
            Long _id = entity.c();
            if (_id != null) {
                stmt.bindLong(1, _id.longValue());
            }
            String source = entity.a();
            if (source != null) {
                stmt.bindString(2, source);
            }
            stmt.bindString(3, entity.b());
        }
    }

    public Long readKey(Cursor cursor, int offset) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{cursor, new Integer(offset)}, this, changeQuickRedirect, false, 7876, new Class[]{Cursor.class, Integer.TYPE}, Long.class);
        if (proxy.isSupported) {
            return (Long) proxy.result;
        }
        if (cursor.isNull(offset + 0)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(offset + 0));
    }

    public e e(Cursor cursor, int offset) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{cursor, new Integer(offset)}, this, changeQuickRedirect, false, 7877, new Class[]{Cursor.class, Integer.TYPE}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        String str = null;
        Long valueOf = cursor.isNull(offset + 0) ? null : Long.valueOf(cursor.getLong(offset + 0));
        if (!cursor.isNull(offset + 1)) {
            str = cursor.getString(offset + 1);
        }
        return new e(valueOf, str, cursor.getString(offset + 2));
    }

    public void f(Cursor cursor, e entity, int offset) {
        if (!PatchProxy.proxy(new Object[]{cursor, entity, new Integer(offset)}, this, changeQuickRedirect, false, 7878, new Class[]{Cursor.class, e.class, Integer.TYPE}, Void.TYPE).isSupported) {
            String str = null;
            entity.f(cursor.isNull(offset + 0) ? null : Long.valueOf(cursor.getLong(offset + 0)));
            if (!cursor.isNull(offset + 1)) {
                str = cursor.getString(offset + 1);
            }
            entity.d(str);
            entity.e(cursor.getString(offset + 2));
        }
    }

    public final Long g(e entity, long rowId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{entity, new Long(rowId)}, this, changeQuickRedirect, false, 7879, new Class[]{e.class, Long.TYPE}, Long.class);
        if (proxy.isSupported) {
            return (Long) proxy.result;
        }
        entity.f(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    public Long c(e entity) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{entity}, this, changeQuickRedirect, false, 7880, new Class[]{e.class}, Long.class);
        if (proxy.isSupported) {
            return (Long) proxy.result;
        }
        if (entity != null) {
            return entity.c();
        }
        return null;
    }

    public boolean d(e entity) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{entity}, this, changeQuickRedirect, false, 7881, new Class[]{e.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return entity.c() != null;
    }

    public final boolean isEntityUpdateable() {
        return true;
    }
}
