package androidx.room;

import androidx.annotation.RestrictTo;
import androidx.sqlite.db.SupportSQLiteStatement;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public abstract class EntityDeletionOrUpdateAdapter<T> extends SharedSQLiteStatement {
    /* access modifiers changed from: protected */
    public abstract void bind(SupportSQLiteStatement supportSQLiteStatement, T t);

    /* access modifiers changed from: protected */
    public abstract String createQuery();

    public EntityDeletionOrUpdateAdapter(RoomDatabase database) {
        super(database);
    }

    public final int handle(T entity) {
        SupportSQLiteStatement stmt = acquire();
        try {
            bind(stmt, entity);
            return stmt.executeUpdateDelete();
        } finally {
            release(stmt);
        }
    }

    public final int handleMultiple(Iterable<? extends T> entities) {
        SupportSQLiteStatement stmt = acquire();
        int total = 0;
        try {
            for (T entity : entities) {
                bind(stmt, entity);
                total += stmt.executeUpdateDelete();
            }
            return total;
        } finally {
            release(stmt);
        }
    }

    public final int handleMultiple(T[] entities) {
        SupportSQLiteStatement stmt = acquire();
        int total = 0;
        try {
            for (T entity : entities) {
                bind(stmt, entity);
                total += stmt.executeUpdateDelete();
            }
            return total;
        } finally {
            release(stmt);
        }
    }
}
