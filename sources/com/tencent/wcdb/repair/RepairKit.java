package com.tencent.wcdb.repair;

import android.database.Cursor;
import com.tencent.wcdb.database.SQLiteCipherSpec;
import com.tencent.wcdb.support.a;

public class RepairKit implements a.C0224a {
    private long a;
    private c b;
    private b c;
    private RepairCursor d;

    public interface b {
        int a(String str, int i, Cursor cursor);
    }

    public static class c {
        public void a() {
            throw null;
        }
    }

    private static native void nativeCancel(long j);

    private static native void nativeFini(long j);

    private static native void nativeFreeMaster(long j);

    private static native long nativeInit(String str, byte[] bArr, SQLiteCipherSpec sQLiteCipherSpec, byte[] bArr2);

    private static native int nativeIntegrityFlags(long j);

    private static native String nativeLastError();

    private static native long nativeLoadMaster(String str, byte[] bArr, String[] strArr, byte[] bArr2);

    private static native long nativeMakeMaster(String[] strArr);

    private native int nativeOutput(long j, long j2, long j3, int i);

    private static native boolean nativeSaveMaster(long j, String str, byte[] bArr);

    public void a() {
        c cVar = this.b;
        if (cVar == null) {
            long j = this.a;
            if (j != 0) {
                nativeFini(j);
                this.a = 0;
                return;
            }
            return;
        }
        cVar.a();
        throw null;
    }

    public void onCancel() {
        long j = this.a;
        if (j != 0) {
            nativeCancel(j);
        }
    }

    private int onProgress(String table, int root, long cursorPtr) {
        if (this.c == null) {
            return 0;
        }
        if (this.d == null) {
            this.d = new RepairCursor();
        }
        RepairCursor repairCursor = this.d;
        repairCursor.p4 = cursorPtr;
        return this.c.a(table, root, repairCursor);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        a();
        super.finalize();
    }

    public static class RepairCursor extends com.tencent.wcdb.a {
        long p4;

        private static native byte[] nativeGetBlob(long j, int i);

        private static native int nativeGetColumnCount(long j);

        private static native double nativeGetDouble(long j, int i);

        private static native long nativeGetLong(long j, int i);

        private static native String nativeGetString(long j, int i);

        private static native int nativeGetType(long j, int i);

        private RepairCursor() {
        }

        public int getCount() {
            throw new UnsupportedOperationException();
        }

        public String[] getColumnNames() {
            throw new UnsupportedOperationException();
        }

        public int getColumnCount() {
            return nativeGetColumnCount(this.p4);
        }

        public int getType(int column) {
            return nativeGetType(this.p4, column);
        }

        public String getString(int column) {
            return nativeGetString(this.p4, column);
        }

        public short getShort(int column) {
            return (short) ((int) getLong(column));
        }

        public int getInt(int column) {
            return (int) getLong(column);
        }

        public long getLong(int column) {
            return nativeGetLong(this.p4, column);
        }

        public float getFloat(int column) {
            return (float) getDouble(column);
        }

        public double getDouble(int column) {
            return nativeGetDouble(this.p4, column);
        }

        public byte[] getBlob(int column) {
            return nativeGetBlob(this.p4, column);
        }

        public boolean isNull(int column) {
            return getType(column) == 0;
        }
    }
}
