package androidx.room.util;

import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.os.Build;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class DBUtil {
    @NonNull
    @Deprecated
    public static Cursor query(RoomDatabase db, SupportSQLiteQuery sqLiteQuery, boolean maybeCopy) {
        return query(db, sqLiteQuery, maybeCopy, (CancellationSignal) null);
    }

    @NonNull
    public static Cursor query(@NonNull RoomDatabase db, @NonNull SupportSQLiteQuery sqLiteQuery, boolean maybeCopy, @Nullable CancellationSignal signal) {
        int rowsInWindow;
        Cursor cursor = db.query(sqLiteQuery, signal);
        if (maybeCopy && (cursor instanceof AbstractWindowedCursor)) {
            AbstractWindowedCursor windowedCursor = (AbstractWindowedCursor) cursor;
            int rowsInCursor = windowedCursor.getCount();
            if (windowedCursor.hasWindow()) {
                rowsInWindow = windowedCursor.getWindow().getNumRows();
            } else {
                rowsInWindow = rowsInCursor;
            }
            if (Build.VERSION.SDK_INT < 23 || rowsInWindow < rowsInCursor) {
                return CursorUtil.copyAndClose(windowedCursor);
            }
        }
        return cursor;
    }

    /* JADX INFO: finally extract failed */
    public static void dropFtsSyncTriggers(SupportSQLiteDatabase db) {
        List<String> existingTriggers = new ArrayList<>();
        Cursor cursor = db.query("SELECT name FROM sqlite_master WHERE type = 'trigger'");
        while (cursor.moveToNext()) {
            try {
                existingTriggers.add(cursor.getString(0));
            } catch (Throwable th) {
                cursor.close();
                throw th;
            }
        }
        cursor.close();
        for (String triggerName : existingTriggers) {
            if (triggerName.startsWith("room_fts_content_sync_")) {
                db.execSQL("DROP TRIGGER IF EXISTS " + triggerName);
            }
        }
    }

    public static int readVersion(@NonNull File databaseFile) {
        FileChannel input = null;
        try {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            input = new FileInputStream(databaseFile).getChannel();
            input.tryLock(60, 4, true);
            input.position(60);
            if (input.read(buffer) == 4) {
                buffer.rewind();
                int i = buffer.getInt();
                input.close();
                return i;
            }
            throw new IOException("Bad database header, unable to read 4 bytes at offset 60");
        } catch (Throwable th) {
            if (input != null) {
                input.close();
            }
            throw th;
        }
    }

    @Nullable
    public static CancellationSignal createCancellationSignal() {
        if (Build.VERSION.SDK_INT >= 16) {
            return new CancellationSignal();
        }
        return null;
    }

    private DBUtil() {
    }
}
