package com.tencent.wcdb.database;

public class ChunkedCursorWindow extends b {
    long d;

    private static native void nativeClear(long j);

    private static native long nativeCreate(int i);

    private static native void nativeDispose(long j);

    private static native void nativeEndRow(long j, long j2);

    private static native byte[] nativeGetBlob(long j, int i);

    private static native double nativeGetDouble(long j, int i);

    private static native long nativeGetLong(long j, int i);

    private static native int nativeGetNumChunks(long j);

    private static native long nativeGetRow(long j, int i);

    private static native String nativeGetString(long j, int i);

    private static native int nativeGetType(long j, int i);

    private static native long nativeRemoveChunk(long j, int i);

    private static native boolean nativeSetNumColumns(long j, int i);

    private void dispose() {
        long j = this.d;
        if (j != 0) {
            nativeDispose(j);
            this.d = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    /* access modifiers changed from: protected */
    public void c() {
        dispose();
    }
}
