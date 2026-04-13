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

public final class DependencyDao_Impl implements DependencyDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<Dependency> __insertionAdapterOfDependency;

    public DependencyDao_Impl(RoomDatabase __db2) {
        this.__db = __db2;
        this.__insertionAdapterOfDependency = new EntityInsertionAdapter<Dependency>(__db2) {
            public String createQuery() {
                return "INSERT OR IGNORE INTO `Dependency` (`work_spec_id`,`prerequisite_id`) VALUES (?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, Dependency value) {
                String str = value.workSpecId;
                if (str == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, str);
                }
                String str2 = value.prerequisiteId;
                if (str2 == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, str2);
                }
            }
        };
    }

    public void insertDependency(Dependency dependency) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfDependency.insert(dependency);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public boolean hasCompletedAllPrerequisites(String id) {
        boolean _result = true;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT COUNT(*)=0 FROM dependency WHERE work_spec_id=? AND prerequisite_id IN (SELECT id FROM workspec WHERE state!=2)", 1);
        if (id == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            if (!_cursor.moveToFirst()) {
                _result = false;
            } else if (_cursor.getInt(0) == 0) {
                _result = false;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public List<String> getPrerequisites(String id) {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT prerequisite_id FROM dependency WHERE work_spec_id=?", 1);
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

    public List<String> getDependentWorkIds(String id) {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT work_spec_id FROM dependency WHERE prerequisite_id=?", 1);
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

    public boolean hasDependents(String id) {
        boolean _result = true;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT COUNT(*)>0 FROM dependency WHERE prerequisite_id=?", 1);
        if (id == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            if (!_cursor.moveToFirst()) {
                _result = false;
            } else if (_cursor.getInt(0) == 0) {
                _result = false;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }
}
