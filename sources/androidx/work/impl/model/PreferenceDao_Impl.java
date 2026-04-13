package androidx.work.impl.model;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.concurrent.Callable;

public final class PreferenceDao_Impl implements PreferenceDao {
    /* access modifiers changed from: private */
    public final RoomDatabase __db;
    private final EntityInsertionAdapter<Preference> __insertionAdapterOfPreference;

    public PreferenceDao_Impl(RoomDatabase __db2) {
        this.__db = __db2;
        this.__insertionAdapterOfPreference = new EntityInsertionAdapter<Preference>(__db2) {
            public String createQuery() {
                return "INSERT OR REPLACE INTO `Preference` (`key`,`long_value`) VALUES (?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, Preference value) {
                String str = value.mKey;
                if (str == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, str);
                }
                Long l = value.mValue;
                if (l == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindLong(2, l.longValue());
                }
            }
        };
    }

    public void insertPreference(Preference preference) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfPreference.insert(preference);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public Long getLongValue(String key) {
        Long _result;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT long_value FROM Preference where `key`=?", 1);
        if (key == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, key);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            if (!_cursor.moveToFirst()) {
                _result = null;
            } else if (_cursor.isNull(0)) {
                _result = null;
            } else {
                _result = Long.valueOf(_cursor.getLong(0));
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public LiveData<Long> getObservableLongValue(String key) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT long_value FROM Preference where `key`=?", 1);
        if (key == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, key);
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"Preference"}, false, new Callable<Long>() {
            public Long call() {
                Long _result;
                Cursor _cursor = DBUtil.query(PreferenceDao_Impl.this.__db, _statement, false, (CancellationSignal) null);
                try {
                    if (!_cursor.moveToFirst()) {
                        _result = null;
                    } else if (_cursor.isNull(0)) {
                        _result = null;
                    } else {
                        _result = Long.valueOf(_cursor.getLong(0));
                    }
                    return _result;
                } finally {
                    _cursor.close();
                }
            }

            /* access modifiers changed from: protected */
            public void finalize() {
                _statement.release();
            }
        });
    }
}
