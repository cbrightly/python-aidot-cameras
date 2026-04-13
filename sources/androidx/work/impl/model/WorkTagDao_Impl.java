package androidx.work.impl.model;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.List;

public final class WorkTagDao_Impl implements WorkTagDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<WorkTag> __insertionAdapterOfWorkTag;

    public WorkTagDao_Impl(RoomDatabase __db2) {
        this.__db = __db2;
        this.__insertionAdapterOfWorkTag = new EntityInsertionAdapter<WorkTag>(__db2) {
            public String createQuery() {
                return "INSERT OR IGNORE INTO `WorkTag` (`tag`,`work_spec_id`) VALUES (?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, WorkTag value) {
                String str = value.tag;
                if (str == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, str);
                }
                String str2 = value.workSpecId;
                if (str2 == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, str2);
                }
            }
        };
    }

    public void insert(WorkTag workTag) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfWorkTag.insert(workTag);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public List<String> getWorkSpecIdsWithTag(String tag) {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT work_spec_id FROM worktag WHERE tag=?", 1);
        if (tag == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, tag);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            List<String> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                _result.add(_cursor.getString(0));
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public List<String> getTagsForWorkSpecId(String id) {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT DISTINCT tag FROM worktag WHERE work_spec_id=?", 1);
        if (id == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            List<String> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                _result.add(_cursor.getString(0));
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }
}
