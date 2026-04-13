package com.tencent.wcdb.database;

import com.tencent.wcdb.database.SQLiteConnection;
import com.tencent.wcdb.support.a;

public class SQLiteDirectQuery extends h {
    private static final int[] a2 = {3, 1, 2, 3, 4, 0};
    private final a p2;

    private static native byte[] nativeGetBlob(long j, int i);

    private static native double nativeGetDouble(long j, int i);

    private static native long nativeGetLong(long j, int i);

    private static native String nativeGetString(long j, int i);

    private static native int nativeGetType(long j, int i);

    private static native int nativeStep(long j, int i);

    /* access modifiers changed from: protected */
    public void c() {
        synchronized (this) {
            SQLiteConnection.d dVar = this.a1;
            if (dVar != null) {
                dVar.p(this.p2);
                this.a1.q((String) null);
            }
        }
        super.c();
    }
}
