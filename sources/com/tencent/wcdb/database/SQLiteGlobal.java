package com.tencent.wcdb.database;

import android.os.Environment;
import android.os.StatFs;

public final class SQLiteGlobal {
    public static final int a;

    private static native int nativeReleaseMemory();

    private static native void nativeSetDefaultPageSize(int i);

    static {
        int pageSize;
        if (!WCDBInitializationProbe.libLoaded) {
            System.loadLibrary("wcdb");
        }
        try {
            pageSize = new StatFs(Environment.getDataDirectory().getAbsolutePath()).getBlockSize();
        } catch (RuntimeException e) {
            pageSize = 4096;
        }
        a = pageSize;
        nativeSetDefaultPageSize(pageSize);
    }

    public static void a() {
    }

    private SQLiteGlobal() {
    }
}
