package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import java.util.Arrays;
import java.util.List;
import me.leolin.shortcutbadger.a;
import me.leolin.shortcutbadger.util.b;

/* compiled from: SamsungHomeBadger */
public class e implements a {
    private static final String[] a = {"_id", "class"};
    private DefaultBadger b;

    public e() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.b = new DefaultBadger();
        }
    }

    public void b(Context context, ComponentName componentName, int badgeCount) {
        DefaultBadger defaultBadger = this.b;
        if (defaultBadger == null || !defaultBadger.c(context)) {
            Uri mUri = Uri.parse("content://com.sec.badge/apps?notify=true");
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = null;
            try {
                cursor = contentResolver.query(mUri, a, "package=?", new String[]{componentName.getPackageName()}, (String) null);
                if (cursor != null) {
                    String entryActivityName = componentName.getClassName();
                    boolean entryActivityExist = false;
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(0);
                        contentResolver.update(mUri, c(componentName, badgeCount, false), "_id=?", new String[]{String.valueOf(id)});
                        if (entryActivityName.equals(cursor.getString(cursor.getColumnIndex("class")))) {
                            entryActivityExist = true;
                        }
                    }
                    if (!entryActivityExist) {
                        contentResolver.insert(mUri, c(componentName, badgeCount, true));
                    }
                }
            } finally {
                b.a(cursor);
            }
        } else {
            this.b.b(context, componentName, badgeCount);
        }
    }

    private ContentValues c(ComponentName componentName, int badgeCount, boolean isInsert) {
        ContentValues contentValues = new ContentValues();
        if (isInsert) {
            contentValues.put("package", componentName.getPackageName());
            contentValues.put("class", componentName.getClassName());
        }
        contentValues.put("badgecount", Integer.valueOf(badgeCount));
        return contentValues;
    }

    public List<String> a() {
        return Arrays.asList(new String[]{"com.sec.android.app.launcher", "com.sec.android.app.twlauncher"});
    }
}
