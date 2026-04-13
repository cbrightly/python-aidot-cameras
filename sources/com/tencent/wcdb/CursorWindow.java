package com.tencent.wcdb;

import android.content.res.Resources;
import android.database.CharArrayBuffer;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.tencent.wcdb.database.b;

public class CursorWindow extends b implements Parcelable {
    public static final Parcelable.Creator<CursorWindow> CREATOR = new a();
    private static int d;
    public long f;
    private int q;
    private final String x;

    private static native boolean nativeAllocRow(long j);

    private static native void nativeClear(long j);

    private static native void nativeCopyStringToBuffer(long j, int i, int i2, CharArrayBuffer charArrayBuffer);

    private static native long nativeCreate(String str, int i);

    private static native void nativeDispose(long j);

    private static native void nativeFreeLastRow(long j);

    private static native byte[] nativeGetBlob(long j, int i, int i2);

    private static native double nativeGetDouble(long j, int i, int i2);

    private static native long nativeGetLong(long j, int i, int i2);

    private static native int nativeGetNumRows(long j);

    private static native String nativeGetString(long j, int i, int i2);

    private static native int nativeGetType(long j, int i, int i2);

    private static native boolean nativePutBlob(long j, byte[] bArr, int i, int i2);

    private static native boolean nativePutDouble(long j, double d2, int i, int i2);

    private static native boolean nativePutLong(long j, long j2, int i, int i2);

    private static native boolean nativePutNull(long j, int i, int i2);

    private static native boolean nativePutString(long j, String str, int i, int i2);

    private static native boolean nativeSetNumColumns(long j, int i);

    /* synthetic */ CursorWindow(Parcel x0, a x1) {
        this(x0);
    }

    static {
        int id = Resources.getSystem().getIdentifier("config_cursorWindowSize", TypedValues.Custom.S_INT, "android");
        if (id != 0) {
            d = Resources.getSystem().getInteger(id) * 1024;
        } else {
            d = 2097152;
        }
    }

    public CursorWindow(String name) {
        this.q = 0;
        String str = (name == null || name.length() == 0) ? "<unnamed>" : name;
        this.x = str;
        long nativeCreate = nativeCreate(str, d);
        this.f = nativeCreate;
        if (nativeCreate == 0) {
            throw new CursorWindowAllocationException("Cursor window allocation of " + (d / 1024) + " kb failed. ");
        }
    }

    private CursorWindow(Parcel source) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    private void dispose() {
        long j = this.f;
        if (j != 0) {
            nativeDispose(j);
            this.f = 0;
        }
    }

    public String r() {
        return this.x;
    }

    public void clear() {
        a();
        try {
            this.q = 0;
            nativeClear(this.f);
        } finally {
            g();
        }
    }

    public int u() {
        return this.q;
    }

    public void z(int pos) {
        this.q = pos;
    }

    public int s() {
        a();
        try {
            return nativeGetNumRows(this.f);
        } finally {
            g();
        }
    }

    public int x(int row, int column) {
        a();
        try {
            return nativeGetType(this.f, row - this.q, column);
        } finally {
            g();
        }
    }

    public byte[] j(int row, int column) {
        a();
        try {
            return nativeGetBlob(this.f, row - this.q, column);
        } finally {
            g();
        }
    }

    public String v(int row, int column) {
        a();
        try {
            return nativeGetString(this.f, row - this.q, column);
        } finally {
            g();
        }
    }

    public void i(int row, int column, CharArrayBuffer buffer) {
        if (buffer != null) {
            a();
            try {
                nativeCopyStringToBuffer(this.f, row - this.q, column, buffer);
            } finally {
                g();
            }
        } else {
            throw new IllegalArgumentException("CharArrayBuffer should not be null");
        }
    }

    public long o(int row, int column) {
        a();
        try {
            return nativeGetLong(this.f, row - this.q, column);
        } finally {
            g();
        }
    }

    public double l(int row, int column) {
        a();
        try {
            return nativeGetDouble(this.f, row - this.q, column);
        } finally {
            g();
        }
    }

    public short t(int row, int column) {
        return (short) ((int) o(row, column));
    }

    public int n(int row, int column) {
        return (int) o(row, column);
    }

    public float m(int row, int column) {
        return (float) l(row, column);
    }

    public static final class a implements Parcelable.Creator<CursorWindow> {
        a() {
        }

        /* renamed from: a */
        public CursorWindow createFromParcel(Parcel source) {
            return new CursorWindow(source, (a) null);
        }

        /* renamed from: b */
        public CursorWindow[] newArray(int size) {
            return new CursorWindow[size];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public void c() {
        dispose();
    }

    public String toString() {
        return r() + " {" + Long.toHexString(this.f) + "}";
    }
}
