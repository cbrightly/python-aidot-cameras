package com.blankj.utilcode.util;

import android.os.Build;
import android.os.Environment;
import java.io.File;

/* compiled from: PathUtils */
public final class r {
    private static final char a = File.separatorChar;

    public static String c() {
        if (Build.VERSION.SDK_INT < 24) {
            return f0.a().getApplicationInfo().dataDir;
        }
        return a(f0.a().getDataDir());
    }

    public static String d() {
        return a(f0.a().getFilesDir());
    }

    public static String b() {
        if (!h0.D()) {
            return "";
        }
        return a(Environment.getExternalStorageDirectory());
    }

    private static String a(File file) {
        if (file == null) {
            return "";
        }
        return file.getAbsolutePath();
    }
}
