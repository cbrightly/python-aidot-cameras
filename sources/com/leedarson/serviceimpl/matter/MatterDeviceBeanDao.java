package com.leedarson.serviceimpl.matter;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.leedarson.serviceimpl.bean.MatterDeviceBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

public class MatterDeviceBeanDao extends AbstractDao<MatterDeviceBean, Long> {
    public static final String TABLENAME = "MATTER_DEVICE_BEAN";
    public static ChangeQuickRedirect changeQuickRedirect;

    public static class Properties {
        public static final Property FabricId = new Property(1, String.class, "fabricId", false, "FABRIC_ID");
        public static final Property HouseId = new Property(3, String.class, "houseId", false, "HOUSE_ID");
        public static final Property Id = new Property(0, Long.class, "id", true, "_id");
        public static final Property NodeId = new Property(2, Long.TYPE, "nodeId", false, "NODE_ID");
    }

    public /* bridge */ /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        Class[] clsArr = {SQLiteStatement.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{sQLiteStatement, obj}, this, changeQuickRedirect, false, 8439, clsArr, Void.TYPE).isSupported) {
            bindValues(sQLiteStatement, (MatterDeviceBean) obj);
        }
    }

    public /* bridge */ /* synthetic */ void bindValues(DatabaseStatement databaseStatement, Object obj) {
        Class[] clsArr = {DatabaseStatement.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{databaseStatement, obj}, this, changeQuickRedirect, false, 8440, clsArr, Void.TYPE).isSupported) {
            bindValues(databaseStatement, (MatterDeviceBean) obj);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8437, new Class[]{Object.class}, Object.class);
        return proxy.isSupported ? proxy.result : getKey((MatterDeviceBean) obj);
    }

    public /* bridge */ /* synthetic */ boolean hasKey(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8436, new Class[]{Object.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : hasKey((MatterDeviceBean) obj);
    }

    public /* bridge */ /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        Object[] objArr = {cursor, obj, new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8441, new Class[]{Cursor.class, Object.class, Integer.TYPE}, Void.TYPE).isSupported) {
            readEntity(cursor, (MatterDeviceBean) obj, i);
        }
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, new Long(j)}, this, changeQuickRedirect, false, 8438, new Class[]{Object.class, Long.TYPE}, Object.class);
        return proxy.isSupported ? proxy.result : updateKeyAfterInsert((MatterDeviceBean) obj, j);
    }

    public MatterDeviceBeanDao(DaoConfig config) {
        super(config);
    }

    public MatterDeviceBeanDao(DaoConfig config, b daoSession) {
        super(config, daoSession);
    }

    public static void createTable(Database db, boolean ifNotExists) {
        if (!PatchProxy.proxy(new Object[]{db, new Byte(ifNotExists ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 8426, new Class[]{Database.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            String constraint = ifNotExists ? "IF NOT EXISTS " : "";
            db.execSQL("CREATE TABLE " + constraint + "\"MATTER_DEVICE_BEAN\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"FABRIC_ID\" TEXT,\"NODE_ID\" INTEGER NOT NULL ,\"HOUSE_ID\" TEXT);");
        }
    }

    public static void dropTable(Database db, boolean ifExists) {
        if (!PatchProxy.proxy(new Object[]{db, new Byte(ifExists ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 8427, new Class[]{Database.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            StringBuilder sb = new StringBuilder();
            sb.append("DROP TABLE ");
            sb.append(ifExists ? "IF EXISTS " : "");
            sb.append("\"MATTER_DEVICE_BEAN\"");
            db.execSQL(sb.toString());
        }
    }

    public final void bindValues(DatabaseStatement stmt, MatterDeviceBean entity) {
        if (!PatchProxy.proxy(new Object[]{stmt, entity}, this, changeQuickRedirect, false, 8428, new Class[]{DatabaseStatement.class, MatterDeviceBean.class}, Void.TYPE).isSupported) {
            stmt.clearBindings();
            Long id = entity.getId();
            if (id != null) {
                stmt.bindLong(1, id.longValue());
            }
            String fabricId = entity.getFabricId();
            if (fabricId != null) {
                stmt.bindString(2, fabricId);
            }
            stmt.bindLong(3, entity.getNodeId());
            String houseId = entity.getHouseId();
            if (houseId != null) {
                stmt.bindString(4, houseId);
            }
        }
    }

    public final void bindValues(SQLiteStatement stmt, MatterDeviceBean entity) {
        if (!PatchProxy.proxy(new Object[]{stmt, entity}, this, changeQuickRedirect, false, 8429, new Class[]{SQLiteStatement.class, MatterDeviceBean.class}, Void.TYPE).isSupported) {
            stmt.clearBindings();
            Long id = entity.getId();
            if (id != null) {
                stmt.bindLong(1, id.longValue());
            }
            String fabricId = entity.getFabricId();
            if (fabricId != null) {
                stmt.bindString(2, fabricId);
            }
            stmt.bindLong(3, entity.getNodeId());
            String houseId = entity.getHouseId();
            if (houseId != null) {
                stmt.bindString(4, houseId);
            }
        }
    }

    public Long readKey(Cursor cursor, int offset) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{cursor, new Integer(offset)}, this, changeQuickRedirect, false, 8430, new Class[]{Cursor.class, Integer.TYPE}, Long.class);
        if (proxy.isSupported) {
            return (Long) proxy.result;
        }
        if (cursor.isNull(offset + 0)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(offset + 0));
    }

    public MatterDeviceBean readEntity(Cursor cursor, int offset) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{cursor, new Integer(offset)}, this, changeQuickRedirect, false, 8431, new Class[]{Cursor.class, Integer.TYPE}, MatterDeviceBean.class);
        if (proxy.isSupported) {
            return (MatterDeviceBean) proxy.result;
        }
        return new MatterDeviceBean(cursor.isNull(offset + 0) ? null : Long.valueOf(cursor.getLong(offset + 0)), cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), cursor.getLong(offset + 2), cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
    }

    public void readEntity(Cursor cursor, MatterDeviceBean entity, int offset) {
        if (!PatchProxy.proxy(new Object[]{cursor, entity, new Integer(offset)}, this, changeQuickRedirect, false, 8432, new Class[]{Cursor.class, MatterDeviceBean.class, Integer.TYPE}, Void.TYPE).isSupported) {
            String str = null;
            entity.setId(cursor.isNull(offset + 0) ? null : Long.valueOf(cursor.getLong(offset + 0)));
            entity.setFabricId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
            entity.setNodeId(cursor.getLong(offset + 2));
            if (!cursor.isNull(offset + 3)) {
                str = cursor.getString(offset + 3);
            }
            entity.setHouseId(str);
        }
    }

    public final Long updateKeyAfterInsert(MatterDeviceBean entity, long rowId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{entity, new Long(rowId)}, this, changeQuickRedirect, false, 8433, new Class[]{MatterDeviceBean.class, Long.TYPE}, Long.class);
        if (proxy.isSupported) {
            return (Long) proxy.result;
        }
        entity.setId(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    public Long getKey(MatterDeviceBean entity) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{entity}, this, changeQuickRedirect, false, 8434, new Class[]{MatterDeviceBean.class}, Long.class);
        if (proxy.isSupported) {
            return (Long) proxy.result;
        }
        if (entity != null) {
            return entity.getId();
        }
        return null;
    }

    public boolean hasKey(MatterDeviceBean entity) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{entity}, this, changeQuickRedirect, false, 8435, new Class[]{MatterDeviceBean.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return entity.getId() != null;
    }

    public final boolean isEntityUpdateable() {
        return true;
    }
}
