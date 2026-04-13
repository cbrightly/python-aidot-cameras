package androidx.work.impl.model;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.List;

public final class SystemIdInfoDao_Impl implements SystemIdInfoDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<SystemIdInfo> __insertionAdapterOfSystemIdInfo;
    private final SharedSQLiteStatement __preparedStmtOfRemoveSystemIdInfo;

    public SystemIdInfoDao_Impl(RoomDatabase __db2) {
        this.__db = __db2;
        this.__insertionAdapterOfSystemIdInfo = new EntityInsertionAdapter<SystemIdInfo>(__db2) {
            public String createQuery() {
                return "INSERT OR REPLACE INTO `SystemIdInfo` (`work_spec_id`,`system_id`) VALUES (?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, SystemIdInfo value) {
                String str = value.workSpecId;
                if (str == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, str);
                }
                stmt.bindLong(2, (long) value.systemId);
            }
        };
        this.__preparedStmtOfRemoveSystemIdInfo = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "DELETE FROM SystemIdInfo where work_spec_id=?";
            }
        };
    }

    public void insertSystemIdInfo(SystemIdInfo systemIdInfo) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfSystemIdInfo.insert(systemIdInfo);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void removeSystemIdInfo(String workSpecId) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfRemoveSystemIdInfo.acquire();
        if (workSpecId == null) {
            _stmt.bindNull(1);
        } else {
            _stmt.bindString(1, workSpecId);
        }
        this.__db.beginTransaction();
        try {
            _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfRemoveSystemIdInfo.release(_stmt);
        }
    }

    public SystemIdInfo getSystemIdInfo(String workSpecId) {
        SystemIdInfo _result;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT `SystemIdInfo`.`work_spec_id` AS `work_spec_id`, `SystemIdInfo`.`system_id` AS `system_id` FROM SystemIdInfo WHERE work_spec_id=?", 1);
        if (workSpecId == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, workSpecId);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            int _cursorIndexOfWorkSpecId = CursorUtil.getColumnIndexOrThrow(_cursor, "work_spec_id");
            int _cursorIndexOfSystemId = CursorUtil.getColumnIndexOrThrow(_cursor, "system_id");
            if (_cursor.moveToFirst()) {
                _result = new SystemIdInfo(_cursor.getString(_cursorIndexOfWorkSpecId), _cursor.getInt(_cursorIndexOfSystemId));
            } else {
                _result = null;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public List<String> getWorkSpecIds() {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT DISTINCT work_spec_id FROM SystemIdInfo", 0);
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
