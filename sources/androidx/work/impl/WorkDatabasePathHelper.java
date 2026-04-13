package androidx.work.impl;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.Logger;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class WorkDatabasePathHelper {
    private static final String[] DATABASE_EXTRA_FILES = {"-journal", "-shm", "-wal"};
    private static final String TAG = Logger.tagWithPrefix("WrkDbPathHelper");
    private static final String WORK_DATABASE_NAME = "androidx.work.workdb";

    private WorkDatabasePathHelper() {
    }

    @NonNull
    public static String getWorkDatabaseName() {
        return WORK_DATABASE_NAME;
    }

    public static void migrateDatabase(@NonNull Context context) {
        String message;
        File defaultDatabasePath = getDefaultDatabasePath(context);
        if (Build.VERSION.SDK_INT >= 23 && defaultDatabasePath.exists()) {
            Logger.get().debug(TAG, "Migrating WorkDatabase to the no-backup directory", new Throwable[0]);
            Map<File, File> paths = migrationPaths(context);
            for (File source : paths.keySet()) {
                File destination = paths.get(source);
                if (source.exists() && destination != null) {
                    if (destination.exists()) {
                        Logger.get().warning(TAG, String.format("Over-writing contents of %s", new Object[]{destination}), new Throwable[0]);
                    }
                    if (source.renameTo(destination)) {
                        message = String.format("Migrated %s to %s", new Object[]{source, destination});
                    } else {
                        message = String.format("Renaming %s to %s failed", new Object[]{source, destination});
                    }
                    Logger.get().debug(TAG, message, new Throwable[0]);
                }
            }
        }
    }

    @VisibleForTesting
    @NonNull
    public static Map<File, File> migrationPaths(@NonNull Context context) {
        Map<File, File> paths = new HashMap<>();
        if (Build.VERSION.SDK_INT >= 23) {
            File databasePath = getDefaultDatabasePath(context);
            File migratedPath = getDatabasePath(context);
            paths.put(databasePath, migratedPath);
            for (String extra : DATABASE_EXTRA_FILES) {
                paths.put(new File(databasePath.getPath() + extra), new File(migratedPath.getPath() + extra));
            }
        }
        return paths;
    }

    @VisibleForTesting
    @NonNull
    public static File getDefaultDatabasePath(@NonNull Context context) {
        return context.getDatabasePath(WORK_DATABASE_NAME);
    }

    @VisibleForTesting
    @NonNull
    public static File getDatabasePath(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            return getDefaultDatabasePath(context);
        }
        return getNoBackupPath(context, WORK_DATABASE_NAME);
    }

    @RequiresApi(23)
    private static File getNoBackupPath(@NonNull Context context, @NonNull String filePath) {
        return new File(context.getNoBackupFilesDir(), filePath);
    }
}
