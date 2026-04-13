package com.didichuxing.doraemonkit.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.Formatter;
import androidx.core.content.FileProvider;
import com.yanzhenjie.andserver.util.h;
import java.io.File;
import java.util.Locale;
import net.sqlcipher.database.SQLiteDatabase;

public class FileUtil {
    public static final String DB = "db";
    public static final String JPG = "jpg";
    public static final String SHARED_PREFS = "shared_prefs";
    private static final String TAG = "FileUtil";
    public static final String TXT = "txt";
    public static final String XML = ".xml";

    private FileUtil() {
    }

    public static String getFileSize(Context context, File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        return Formatter.formatFileSize(context, file.length());
    }

    public static String getSuffix(File file) {
        if (file == null || !file.exists()) {
            return "";
        }
        return file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase(Locale.getDefault());
    }

    public static void systemShare(Context context, File file) {
        if (file != null && file.exists()) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.addFlags(1);
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            try {
                Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".debugfileprovider", file);
                intent.setDataAndType(uri, context.getContentResolver().getType(uri));
                intent.putExtra("android.intent.extra.STREAM", uri);
                if (intent.resolveActivity(context.getPackageManager()) == null) {
                    intent.setDataAndType(uri, h.ALL_VALUE);
                }
                context.startActivity(intent);
            } catch (Exception e) {
                LogHelper.e(TAG, "The selected file can't be shared: " + file.toString());
            }
        }
    }

    public static boolean isImage(File file) {
        if (file == null) {
            return false;
        }
        String suffix = getSuffix(file);
        if (JPG.equals(suffix) || "jpeg".equals(suffix) || "png".equals(suffix) || "bmp".equals(suffix)) {
            return true;
        }
        return false;
    }

    public static boolean isVideo(File file) {
        if (file == null) {
            return false;
        }
        String suffix = getSuffix(file);
        if ("3gp".equals(suffix) || "mp4".equals(suffix) || "mkv".equals(suffix) || "webm".equals(suffix)) {
            return true;
        }
        return false;
    }

    public static boolean isDB(File file) {
        if (file == null) {
            return false;
        }
        return DB.equals(getSuffix(file));
    }

    public static boolean isSp(File file) {
        File parentFile = file.getParentFile();
        if (parentFile == null || !parentFile.getName().equals(SHARED_PREFS) || !file.getName().contains(XML)) {
            return false;
        }
        return true;
    }

    public static void deleteDirectory(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteDirectory(f);
            }
            file.delete();
            return;
        }
        file.delete();
    }

    public static long getDirectorySize(File directory) {
        long j;
        long size = 0;
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            return 0;
        }
        for (File file : listFiles) {
            if (file.isDirectory()) {
                j = getDirectorySize(file);
            } else {
                j = file.length();
            }
            size += j;
        }
        return size;
    }
}
