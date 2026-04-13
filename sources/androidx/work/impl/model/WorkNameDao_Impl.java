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

public final class WorkNameDao_Impl implements WorkNameDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<WorkName> __insertionAdapterOfWorkName;

    public WorkNameDao_Impl(RoomDatabase __db2) {
        this.__db = __db2;
        this.__insertionAdapterOfWorkName = new EntityInsertionAdapter<WorkName>(__db2) {
            public String createQuery() {
                return "INSERT OR IGNORE INTO `WorkName` (`name`,`work_spec_id`) VALUES (?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, WorkName value) {
                String str = value.name;
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

    public void insert(WorkName workName) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfWorkName.insert(workName);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public List<String> getWorkSpecIdsWithName(String name) {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT work_spec_id FROM workname WHERE name=?", 1);
        if (name == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, name);
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

    public List<String> getNamesForWorkSpecId(String workSpecId) {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT name FROM workname WHERE work_spec_id=?", 1);
        if (workSpecId == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, workSpecId);
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
