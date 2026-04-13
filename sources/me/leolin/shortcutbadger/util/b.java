package me.leolin.shortcutbadger.util;

import android.database.Cursor;

/* compiled from: CloseHelper */
public class b {
    public static void a(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}
