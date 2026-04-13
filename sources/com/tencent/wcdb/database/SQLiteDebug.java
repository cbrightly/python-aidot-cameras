package com.tencent.wcdb.database;

import android.annotation.SuppressLint;
import com.tencent.wcdb.support.Log;
import java.util.ArrayList;

public final class SQLiteDebug {
    private static volatile int a;
    private static volatile ArrayList<IOTraceStats> b;

    public static class PagerStats {
        public ArrayList<DbStats> dbStats;
        public int largestMemAlloc;
        public int memoryUsed;
        public int pageCacheOverflow;
    }

    private static native void nativeGetIOTraceStats(long j, ArrayList<IOTraceStats> arrayList);

    private static native int nativeGetLastErrorLine();

    private static native void nativeGetPagerStats(PagerStats pagerStats);

    private static native void nativeSetIOTraceFlags(int i);

    static {
        SQLiteGlobal.a();
    }

    private SQLiteDebug() {
    }

    public static final boolean c(long elapsedTimeMillis) {
        return elapsedTimeMillis > 300;
    }

    public static class DbStats {
        public String cache;
        public String dbName;
        public long dbSize;
        public int lookaside;
        public long pageSize;

        public DbStats(String dbName2, long pageCount, long pageSize2, int lookaside2, int hits, int misses, int cachesize) {
            this.dbName = dbName2;
            this.pageSize = pageSize2 / 1024;
            this.dbSize = (pageCount * pageSize2) / 1024;
            this.lookaside = lookaside2;
            this.cache = hits + "/" + misses + "/" + cachesize;
        }
    }

    public static class IOTraceStats {
        public String dbName;
        public String journalMode;
        public long lastJournalReadOffset;
        public byte[] lastJournalReadPage;
        public long lastJournalWriteOffset;
        public byte[] lastJournalWritePage;
        public long lastReadOffset;
        public byte[] lastReadPage;
        public long lastWriteOffset;
        public byte[] lastWritePage;
        public long pageCount;
        public long pageSize;
        public String path;

        @SuppressLint({"DefaultLocale"})
        public String toString() {
            return String.format("[%s | %s] pageSize: %d, pageCount: %d, journal: %s, lastRead: %d, lastWrite: %d, lastJournalRead: %d, lastJournalWrite: %d", new Object[]{this.dbName, this.path, Long.valueOf(this.pageSize), Long.valueOf(this.pageCount), this.journalMode, Long.valueOf(this.lastReadOffset), Long.valueOf(this.lastWriteOffset), Long.valueOf(this.lastJournalReadOffset), Long.valueOf(this.lastJournalWriteOffset)});
        }
    }

    static void a(SQLiteConnection connection) {
        try {
            a = nativeGetLastErrorLine();
            ArrayList<IOTraceStats> stats = new ArrayList<>();
            long ptr = connection.x((String) null);
            if (ptr != 0) {
                nativeGetIOTraceStats(ptr, stats);
                connection.p((Exception) null);
            }
            b = stats;
        } catch (RuntimeException e) {
            Log.a("WCDB.SQLiteDebug", "Cannot collect I/O trace statistics: " + e.getMessage());
        }
    }

    static void b(SQLiteDatabase db) {
        try {
            a = nativeGetLastErrorLine();
            ArrayList<IOTraceStats> stats = new ArrayList<>();
            long ptr = db.i("collectIoStat", false, false);
            if (ptr != 0) {
                nativeGetIOTraceStats(ptr, stats);
            }
            db.P0(ptr, (Exception) null);
            b = stats;
        } catch (RuntimeException e) {
            Log.a("WCDB.SQLiteDebug", "Cannot collect I/O trace statistics: " + e.getMessage());
        }
    }
}
