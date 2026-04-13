package androidx.room.paging;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.paging.PositionalDataSource;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public abstract class LimitOffsetDataSource<T> extends PositionalDataSource<T> {
    private final String mCountQuery;
    private final RoomDatabase mDb;
    private final boolean mInTransaction;
    private final String mLimitOffsetQuery;
    private final InvalidationTracker.Observer mObserver;
    private final RoomSQLiteQuery mSourceQuery;

    /* access modifiers changed from: protected */
    public abstract List<T> convertRows(Cursor cursor);

    protected LimitOffsetDataSource(RoomDatabase db, SupportSQLiteQuery query, boolean inTransaction, String... tables) {
        this(db, RoomSQLiteQuery.copyFrom(query), inTransaction, tables);
    }

    protected LimitOffsetDataSource(RoomDatabase db, RoomSQLiteQuery query, boolean inTransaction, String... tables) {
        this.mDb = db;
        this.mSourceQuery = query;
        this.mInTransaction = inTransaction;
        this.mCountQuery = "SELECT COUNT(*) FROM ( " + query.getSql() + " )";
        this.mLimitOffsetQuery = "SELECT * FROM ( " + query.getSql() + " ) LIMIT ? OFFSET ?";
        AnonymousClass1 r0 = new InvalidationTracker.Observer(tables) {
            public void onInvalidated(@NonNull Set<String> set) {
                LimitOffsetDataSource.this.invalidate();
            }
        };
        this.mObserver = r0;
        db.getInvalidationTracker().addWeakObserver(r0);
    }

    public int countItems() {
        RoomSQLiteQuery sqLiteQuery = RoomSQLiteQuery.acquire(this.mCountQuery, this.mSourceQuery.getArgCount());
        sqLiteQuery.copyArgumentsFrom(this.mSourceQuery);
        Cursor cursor = this.mDb.query(sqLiteQuery);
        try {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
            cursor.close();
            sqLiteQuery.release();
            return 0;
        } finally {
            cursor.close();
            sqLiteQuery.release();
        }
    }

    public boolean isInvalid() {
        this.mDb.getInvalidationTracker().refreshVersionsSync();
        return LimitOffsetDataSource.super.isInvalid();
    }

    public void loadInitial(@NonNull PositionalDataSource.LoadInitialParams params, @NonNull PositionalDataSource.LoadInitialCallback<T> callback) {
        List<T> list = Collections.emptyList();
        int firstLoadPosition = 0;
        RoomSQLiteQuery sqLiteQuery = null;
        Cursor cursor = null;
        this.mDb.beginTransaction();
        try {
            int totalCount = countItems();
            if (totalCount != 0) {
                firstLoadPosition = computeInitialLoadPosition(params, totalCount);
                sqLiteQuery = getSQLiteQuery(firstLoadPosition, computeInitialLoadSize(params, firstLoadPosition, totalCount));
                cursor = this.mDb.query(sqLiteQuery);
                List<T> rows = convertRows(cursor);
                this.mDb.setTransactionSuccessful();
                list = rows;
            }
            callback.onResult(list, firstLoadPosition, totalCount);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            this.mDb.endTransaction();
            if (sqLiteQuery != null) {
                sqLiteQuery.release();
            }
        }
    }

    public void loadRange(@NonNull PositionalDataSource.LoadRangeParams params, @NonNull PositionalDataSource.LoadRangeCallback<T> callback) {
        callback.onResult(loadRange(params.startPosition, params.loadSize));
    }

    @NonNull
    public List<T> loadRange(int startPosition, int loadCount) {
        RoomSQLiteQuery sqLiteQuery = getSQLiteQuery(startPosition, loadCount);
        if (this.mInTransaction) {
            this.mDb.beginTransaction();
            Cursor cursor = null;
            try {
                cursor = this.mDb.query(sqLiteQuery);
                List<T> rows = convertRows(cursor);
                this.mDb.setTransactionSuccessful();
                return rows;
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                this.mDb.endTransaction();
                sqLiteQuery.release();
            }
        } else {
            Cursor cursor2 = this.mDb.query(sqLiteQuery);
            try {
                return convertRows(cursor2);
            } finally {
                cursor2.close();
                sqLiteQuery.release();
            }
        }
    }

    private RoomSQLiteQuery getSQLiteQuery(int startPosition, int loadCount) {
        RoomSQLiteQuery sqLiteQuery = RoomSQLiteQuery.acquire(this.mLimitOffsetQuery, this.mSourceQuery.getArgCount() + 2);
        sqLiteQuery.copyArgumentsFrom(this.mSourceQuery);
        sqLiteQuery.bindLong(sqLiteQuery.getArgCount() - 1, (long) loadCount);
        sqLiteQuery.bindLong(sqLiteQuery.getArgCount(), (long) startPosition);
        return sqLiteQuery;
    }
}
