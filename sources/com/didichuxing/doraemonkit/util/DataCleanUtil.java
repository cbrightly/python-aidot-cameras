package com.didichuxing.doraemonkit.util;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;
import com.blankj.utilcode.util.r;
import java.io.File;

public class DataCleanUtil {
    private DataCleanUtil() {
    }

    public static void cleanInternalCache(Context context) {
        FileUtil.deleteDirectory(context.getCacheDir());
    }

    public static void cleanDatabases(Context context) {
        FileUtil.deleteDirectory(new File(context.getFilesDir().getParent() + "/databases"));
    }

    public static void cleanSharedPreference(Context context) {
        FileUtil.deleteDirectory(new File(context.getFilesDir().getParent() + "/shared_prefs"));
    }

    public static void cleanFiles(Context context) {
        FileUtil.deleteDirectory(context.getFilesDir());
    }

    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            FileUtil.deleteDirectory(context.getExternalCacheDir());
        }
    }

    public static void cleanCustomCache(String filePath) {
        FileUtil.deleteDirectory(new File(filePath));
    }

    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath != null) {
            for (String filePath : filepath) {
                cleanCustomCache(filePath);
            }
        }
    }

    public static long getApplicationDataSize(Context context) {
        return FileUtil.getDirectorySize(new File(r.c()));
    }

    public static String getApplicationDataSizeStr(Context context) {
        return Formatter.formatFileSize(context, getApplicationDataSize(context));
    }
}
