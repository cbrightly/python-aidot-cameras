package meshsdk.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.leedarson.bean.Constants;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

public class MeshDictBeanDao extends AbstractDao<MeshDictBean, Long> {
    public static final String TABLENAME = "MESH_DICT_BEAN";

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", true, "_id");
        public static final Property LocalAddr;
        public static final Property MeshUUID = new Property(1, String.class, "meshUUID", false, "MESH_UUID");
        public static final Property ProvisionerUUID = new Property(2, String.class, "provisionerUUID", false, "PROVISIONER_UUID");
        public static final Property SeqNum;
        public static final Property State;

        static {
            Class cls = Integer.TYPE;
            LocalAddr = new Property(3, cls, "localAddr", false, "LOCAL_ADDR");
            Class cls2 = cls;
            SeqNum = new Property(4, cls2, "seqNum", false, "SEQ_NUM");
            State = new Property(5, cls2, Constants.ACTION_STATE, false, "STATE");
        }
    }

    public MeshDictBeanDao(DaoConfig config) {
        super(config);
    }

    public MeshDictBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"MESH_DICT_BEAN\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"MESH_UUID\" TEXT,\"PROVISIONER_UUID\" TEXT,\"LOCAL_ADDR\" INTEGER NOT NULL ,\"SEQ_NUM\" INTEGER NOT NULL ,\"STATE\" INTEGER NOT NULL );");
    }

    public static void dropTable(Database db, boolean ifExists) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("\"MESH_DICT_BEAN\"");
        db.execSQL(sb.toString());
    }

    /* access modifiers changed from: protected */
    public final void bindValues(DatabaseStatement stmt, MeshDictBean entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id.longValue());
        }
        String meshUUID = entity.getMeshUUID();
        if (meshUUID != null) {
            stmt.bindString(2, meshUUID);
        }
        String provisionerUUID = entity.getProvisionerUUID();
        if (provisionerUUID != null) {
            stmt.bindString(3, provisionerUUID);
        }
        stmt.bindLong(4, (long) entity.getLocalAddr());
        stmt.bindLong(5, (long) entity.getSeqNum());
        stmt.bindLong(6, (long) entity.getState());
    }

    /* access modifiers changed from: protected */
    public final void bindValues(SQLiteStatement stmt, MeshDictBean entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id.longValue());
        }
        String meshUUID = entity.getMeshUUID();
        if (meshUUID != null) {
            stmt.bindString(2, meshUUID);
        }
        String provisionerUUID = entity.getProvisionerUUID();
        if (provisionerUUID != null) {
            stmt.bindString(3, provisionerUUID);
        }
        stmt.bindLong(4, (long) entity.getLocalAddr());
        stmt.bindLong(5, (long) entity.getSeqNum());
        stmt.bindLong(6, (long) entity.getState());
    }

    public Long readKey(Cursor cursor, int offset) {
        if (cursor.isNull(offset + 0)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(offset + 0));
    }

    public MeshDictBean readEntity(Cursor cursor, int offset) {
        return new MeshDictBean(cursor.isNull(offset + 0) ? null : Long.valueOf(cursor.getLong(offset + 0)), cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), cursor.getInt(offset + 3), cursor.getInt(offset + 4), cursor.getInt(offset + 5));
    }

    public void readEntity(Cursor cursor, MeshDictBean entity, int offset) {
        String str = null;
        entity.setId(cursor.isNull(offset + 0) ? null : Long.valueOf(cursor.getLong(offset + 0)));
        entity.setMeshUUID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        if (!cursor.isNull(offset + 2)) {
            str = cursor.getString(offset + 2);
        }
        entity.setProvisionerUUID(str);
        entity.setLocalAddr(cursor.getInt(offset + 3));
        entity.setSeqNum(cursor.getInt(offset + 4));
        entity.setState(cursor.getInt(offset + 5));
    }

    /* access modifiers changed from: protected */
    public final Long updateKeyAfterInsert(MeshDictBean entity, long rowId) {
        entity.setId(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    public Long getKey(MeshDictBean entity) {
        if (entity != null) {
            return entity.getId();
        }
        return null;
    }

    public boolean hasKey(MeshDictBean entity) {
        return entity.getId() != null;
    }

    /* access modifiers changed from: protected */
    public final boolean isEntityUpdateable() {
        return true;
    }
}
