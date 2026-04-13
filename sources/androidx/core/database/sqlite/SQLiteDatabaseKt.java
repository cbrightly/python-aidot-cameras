package androidx.core.database.sqlite;

import android.database.sqlite.SQLiteDatabase;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.j;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: SQLiteDatabase.kt */
public final class SQLiteDatabaseKt {
    public static /* synthetic */ Object transaction$default(SQLiteDatabase $this$transaction_u24default, boolean exclusive, l body, int i, Object obj) {
        if ((i & 1) != 0) {
            exclusive = true;
        }
        k.e($this$transaction_u24default, "<this>");
        k.e(body, "body");
        if (exclusive) {
            $this$transaction_u24default.beginTransaction();
        } else {
            $this$transaction_u24default.beginTransactionNonExclusive();
        }
        try {
            Object result = body.invoke($this$transaction_u24default);
            $this$transaction_u24default.setTransactionSuccessful();
            return result;
        } finally {
            j.b(1);
            $this$transaction_u24default.endTransaction();
            j.a(1);
        }
    }

    public static final <T> T transaction(@NotNull SQLiteDatabase $this$transaction, boolean exclusive, @NotNull l<? super SQLiteDatabase, ? extends T> body) {
        k.e($this$transaction, "<this>");
        k.e(body, "body");
        if (exclusive) {
            $this$transaction.beginTransaction();
        } else {
            $this$transaction.beginTransactionNonExclusive();
        }
        try {
            Object result = body.invoke($this$transaction);
            $this$transaction.setTransactionSuccessful();
            return result;
        } finally {
            j.b(1);
            $this$transaction.endTransaction();
            j.a(1);
        }
    }
}
