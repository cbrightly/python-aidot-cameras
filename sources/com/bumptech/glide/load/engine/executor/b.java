package com.bumptech.glide.load.engine.executor;

import android.os.Build;
import android.os.StrictMode;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/* compiled from: RuntimeCompat */
public final class b {
    static int a() {
        int cpus = Runtime.getRuntime().availableProcessors();
        if (Build.VERSION.SDK_INT < 17) {
            return Math.max(b(), cpus);
        }
        return cpus;
    }

    private static int b() {
        File[] cpus = null;
        StrictMode.ThreadPolicy originalPolicy = StrictMode.allowThreadDiskReads();
        try {
            cpus = new File("/sys/devices/system/cpu/").listFiles(new a(Pattern.compile("cpu[0-9]+")));
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(originalPolicy);
            throw th;
        }
        StrictMode.setThreadPolicy(originalPolicy);
        return Math.max(1, cpus != null ? cpus.length : 0);
    }

    /* compiled from: RuntimeCompat */
    public class a implements FilenameFilter {
        final /* synthetic */ Pattern a;

        a(Pattern pattern) {
            this.a = pattern;
        }

        public boolean accept(File file, String s) {
            return this.a.matcher(s).matches();
        }
    }
}
