package com.tencent.wcdb;

import android.database.Cursor;

/* compiled from: Cursor */
public interface e extends Cursor {
    void close();

    String getString(int i);

    boolean moveToNext();
}
