package com.blankj.utilcode.util;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;

/* compiled from: FileUtils */
public final class j {
    private static final String a = System.getProperty("line.separator");

    public static File l(String filePath) {
        if (h0.E(filePath)) {
            return null;
        }
        return new File(filePath);
    }

    public static boolean y(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        return z(file.getAbsolutePath());
    }

    public static boolean z(String filePath) {
        File file = l(filePath);
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        return A(filePath);
    }

    private static boolean A(String filePath) {
        if (Build.VERSION.SDK_INT < 29) {
            return false;
        }
        try {
            AssetFileDescriptor afd = f0.a().getContentResolver().openAssetFileDescriptor(Uri.parse(filePath), "r");
            if (afd == null) {
                return false;
            }
            try {
                afd.close();
                return true;
            } catch (IOException e) {
                return true;
            }
        } catch (FileNotFoundException e2) {
            return false;
        }
    }

    public static boolean C(String filePath, String newName) {
        return B(l(filePath), newName);
    }

    public static boolean B(File file, String newName) {
        if (file == null || !file.exists() || h0.E(newName)) {
            return false;
        }
        if (newName.equals(file.getName())) {
            return true;
        }
        File newFile = new File(file.getParent() + File.separator + newName);
        if (newFile.exists() || !file.renameTo(newFile)) {
            return false;
        }
        return true;
    }

    public static boolean v(String dirPath) {
        return u(l(dirPath));
    }

    public static boolean u(File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static boolean x(String filePath) {
        return w(l(filePath));
    }

    public static boolean w(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static boolean b(String dirPath) {
        return a(l(dirPath));
    }

    public static boolean a(File file) {
        return file != null && (!file.exists() ? file.mkdirs() : file.isDirectory());
    }

    public static boolean c(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!a(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean e(String filePath) {
        return d(l(filePath));
    }

    public static boolean d(File file) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            return h(file);
        }
        return i(file);
    }

    private static boolean h(File dir) {
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        return false;
                    }
                } else if (file.isDirectory() && !h(file)) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    private static boolean i(File file) {
        return file != null && (!file.exists() || (file.isFile() && file.delete()));
    }

    public static boolean g(String dirPath) {
        return f(l(dirPath));
    }

    /* compiled from: FileUtils */
    public static final class a implements FileFilter {
        a() {
        }

        public boolean accept(File pathname) {
            return true;
        }
    }

    public static boolean f(File dir) {
        return j(dir, new a());
    }

    public static boolean j(File dir, FileFilter filter) {
        if (dir == null || filter == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (!(files == null || files.length == 0)) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) {
                            return false;
                        }
                    } else if (file.isDirectory() && !h(file)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static long p(String filePath) {
        return o(l(filePath));
    }

    public static long o(File file) {
        if (file == null) {
            return -1;
        }
        return file.lastModified();
    }

    public static long t(File file) {
        if (file == null) {
            return 0;
        }
        if (file.isDirectory()) {
            return k(file);
        }
        return q(file);
    }

    private static long k(File dir) {
        long j;
        if (!u(dir)) {
            return -1;
        }
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    j = k(file);
                } else {
                    j = file.length();
                }
                len += j;
            }
        }
        return len;
    }

    private static long q(File file) {
        if (!w(file)) {
            return -1;
        }
        return file.length();
    }

    public static String r(File file) {
        if (file == null) {
            return "";
        }
        return s(file.getAbsolutePath());
    }

    public static String s(String filePath) {
        if (h0.E(filePath)) {
            return "";
        }
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    public static String m(File file) {
        if (file == null) {
            return "";
        }
        return n(file.getPath());
    }

    public static String n(String filePath) {
        if (h0.E(filePath)) {
            return "";
        }
        int lastPoi = filePath.lastIndexOf(46);
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) {
            return "";
        }
        return filePath.substring(lastPoi + 1);
    }
}
