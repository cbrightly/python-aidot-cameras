package androidx.work.impl.model;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import androidx.work.Data;
import java.util.ArrayList;
import java.util.List;

public final class WorkProgressDao_Impl implements WorkProgressDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<WorkProgress> __insertionAdapterOfWorkProgress;
    private final SharedSQLiteStatement __preparedStmtOfDelete;
    private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

    public WorkProgressDao_Impl(RoomDatabase __db2) {
        this.__db = __db2;
        this.__insertionAdapterOfWorkProgress = new EntityInsertionAdapter<WorkProgress>(__db2) {
            public String createQuery() {
                return "INSERT OR REPLACE INTO `WorkProgress` (`work_spec_id`,`progress`) VALUES (?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, WorkProgress value) {
                String str = value.mWorkSpecId;
                if (str == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, str);
                }
                byte[] _tmp = Data.toByteArrayInternal(value.mProgress);
                if (_tmp == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindBlob(2, _tmp);
                }
            }
        };
        this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "DELETE from WorkProgress where work_spec_id=?";
            }
        };
        this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "DELETE FROM WorkProgress";
            }
        };
    }

    public void insert(WorkProgress progress) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfWorkProgress.insert(progress);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void delete(String workSpecId) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfDelete.acquire();
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
            this.__preparedStmtOfDelete.release(_stmt);
        }
    }

    public void deleteAll() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfDeleteAll.acquire();
        this.__db.beginTransaction();
        try {
            _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteAll.release(_stmt);
        }
    }

    public Data getProgressForWorkSpecId(String workSpecId) {
        Data _result;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT progress FROM WorkProgress WHERE work_spec_id=?", 1);
        if (workSpecId == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, workSpecId);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            if (_cursor.moveToFirst()) {
                _result = Data.fromByteArray(_cursor.getBlob(0));
            } else {
                _result = null;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public List<Data> getProgressForWorkSpecIds(List<String> workSpecIds) {
        StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("SELECT progress FROM WorkProgress WHERE work_spec_id IN (");
        int _inputSize = workSpecIds.size();
        StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
        _stringBuilder.append(")");
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_stringBuilder.toString(), _inputSize + 0);
        int _argIndex = 1;
        for (String _item : workSpecIds) {
            if (_item == null) {
                _statement.bindNull(_argIndex);
            } else {
                _statement.bindString(_argIndex, _item);
            }
            _argIndex++;
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            List<Data> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                _result.add(Data.fromByteArray(_cursor.getBlob(0)));
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }
}
