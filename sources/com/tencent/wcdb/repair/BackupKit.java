package com.tencent.wcdb.repair;

import com.tencent.wcdb.support.a;

public class BackupKit implements a.C0224a {
    private long a;

    private static native void nativeCancel(long j);

    private static native void nativeFinish(long j);

    private static native long nativeInit(String str, byte[] bArr, int i);

    private static native String nativeLastError(long j);

    private static native int nativeRun(long j, long j2, String[] strArr);

    private static native int nativeStatementCount(long j);

    public void onCancel() {
        long j = this.a;
        if (j != 0) {
            nativeCancel(j);
        }
    }

    public void a() {
        long j = this.a;
        if (j != 0) {
            nativeFinish(j);
            this.a = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        a();
        super.finalize();
    }
}
