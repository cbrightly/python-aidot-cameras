package com.tencent.wcdb;

import android.util.Pair;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteException;
import com.tencent.wcdb.database.m;
import com.tencent.wcdb.support.Log;
import java.io.File;
import java.util.List;

/* compiled from: DefaultDatabaseErrorHandler */
public final class h implements f {
    private static final String[] a = {"", "-journal", "-wal", ".sm", ".bak", "-vfslog", "-vfslo1"};
    private final boolean b;

    public h(boolean noCorruptionBackup) {
        this.b = noCorruptionBackup;
    }

    public void a(SQLiteDatabase dbObj) {
        Log.a("WCDB.DefaultDatabaseErrorHandler", "Corruption reported by sqlite on database: " + dbObj.getPath());
        if (!dbObj.isOpen()) {
            b(dbObj.getPath());
            return;
        }
        List<Pair<String, String>> attachedDbs = null;
        try {
            attachedDbs = dbObj.getAttachedDbs();
        } catch (SQLiteException e) {
        }
        m trace = dbObj.u();
        if (trace != null) {
            trace.b(dbObj);
        }
        try {
            dbObj.close();
            if (attachedDbs != null) {
                for (Pair<String, String> p : attachedDbs) {
                    b((String) p.second);
                }
                return;
            }
        } catch (SQLiteException e2) {
            if (attachedDbs != null) {
                for (Pair<String, String> p2 : attachedDbs) {
                    b((String) p2.second);
                }
                return;
            }
        } catch (Throwable th) {
            if (attachedDbs != null) {
                for (Pair<String, String> p3 : attachedDbs) {
                    b((String) p3.second);
                }
            } else {
                b(dbObj.getPath());
            }
            throw th;
        }
        b(dbObj.getPath());
    }

    private void b(String fileName) {
        if (!fileName.equalsIgnoreCase(net.sqlcipher.database.SQLiteDatabase.MEMORY) && fileName.trim().length() != 0) {
            Log.a("WCDB.DefaultDatabaseErrorHandler", "Remove database file: " + fileName);
            int i = 0;
            if (!this.b) {
                File originFile = new File(fileName);
                File corruptedDir = new File(originFile.getParentFile(), "corrupted");
                if (!corruptedDir.mkdirs()) {
                    Log.a("WCDB.DefaultDatabaseErrorHandler", "Could not create directory for corrupted database. Corruption backup may be unavailable.");
                }
                String corruptedPath = corruptedDir.getPath() + "/" + originFile.getName();
                String[] strArr = a;
                int length = strArr.length;
                while (i < length) {
                    String suffix = strArr[i];
                    d(fileName + suffix, corruptedPath + suffix);
                    i++;
                }
                return;
            }
            String[] strArr2 = a;
            int length2 = strArr2.length;
            while (i < length2) {
                c(fileName + strArr2[i]);
                i++;
            }
        }
    }

    private static boolean d(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        boolean ret = oldFile.renameTo(new File(newPath));
        if (!ret) {
            oldFile.delete();
        }
        return ret;
    }

    private static boolean c(String path) {
        return new File(path).delete();
    }
}
