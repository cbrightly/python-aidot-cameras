package meshsdk.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

public class RandomOffsetBeanDao extends AbstractDao<RandomOffsetBean, Long> {
    public static final String TABLENAME = "RANDOM_OFFSET_BEAN";

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", true, "_id");
        public static final Property Mac = new Property(2, String.class, "mac", false, "MAC");
        public static final Property MeshUUID = new Property(1, String.class, "meshUUID", false, "MESH_UUID");
        public static final Property Offset = new Property(3, Integer.TYPE, "offset", false, "OFFSET");
    }

    public RandomOffsetBeanDao(DaoConfig config) {
        super(config);
    }

    public RandomOffsetBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"RANDOM_OFFSET_BEAN\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"MESH_UUID\" TEXT,\"MAC\" TEXT,\"OFFSET\" INTEGER NOT NULL );");
    }

    public static void dropTable(Database db, boolean ifExists) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("\"RANDOM_OFFSET_BEAN\"");
        db.execSQL(sb.toString());
    }

    /* access modifiers changed from: protected */
    public final void bindValues(DatabaseStatement stmt, RandomOffsetBean entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id.longValue());
        }
        String meshUUID = entity.getMeshUUID();
        if (meshUUID != null) {
            stmt.bindString(2, meshUUID);
        }
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(3, mac);
        }
        stmt.bindLong(4, (long) entity.getOffset());
    }

    /* access modifiers changed from: protected */
    public final void bindValues(SQLiteStatement stmt, RandomOffsetBean entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id.longValue());
        }
        String meshUUID = entity.getMeshUUID();
        if (meshUUID != null) {
            stmt.bindString(2, meshUUID);
        }
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(3, mac);
        }
        stmt.bindLong(4, (long) entity.getOffset());
    }

    public Long readKey(Cursor cursor, int offset) {
        if (cursor.isNull(offset + 0)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(offset + 0));
    }

    public RandomOffsetBean readEntity(Cursor cursor, int offset) {
        String str = null;
        Long valueOf = cursor.isNull(offset + 0) ? null : Long.valueOf(cursor.getLong(offset + 0));
        String string = cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1);
        if (!cursor.isNull(offset + 2)) {
            str = cursor.getString(offset + 2);
        }
        return new RandomOffsetBean(valueOf, string, str, cursor.getInt(offset + 3));
    }

    public void readEntity(Cursor cursor, RandomOffsetBean entity, int offset) {
        String str = null;
        entity.setId(cursor.isNull(offset + 0) ? null : Long.valueOf(cursor.getLong(offset + 0)));
        entity.setMeshUUID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        if (!cursor.isNull(offset + 2)) {
            str = cursor.getString(offset + 2);
        }
        entity.setMac(str);
        entity.setOffset(cursor.getInt(offset + 3));
    }

    /* access modifiers changed from: protected */
    public final Long updateKeyAfterInsert(RandomOffsetBean entity, long rowId) {
        entity.setId(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    public Long getKey(RandomOffsetBean entity) {
        if (entity != null) {
            return entity.getId();
        }
        return null;
    }

    public boolean hasKey(RandomOffsetBean entity) {
        return entity.getId() != null;
    }

    /* access modifiers changed from: protected */
    public final boolean isEntityUpdateable() {
        return true;
    }
}
