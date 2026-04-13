package com.tencent.wcdb;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import com.tencent.wcdb.support.Log;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* compiled from: AbstractCursor */
public abstract class a implements e {
    private ContentObserver a1;
    private final DataSetObservable a2 = new DataSetObservable();
    @Deprecated
    protected HashMap<Long, Map<String, Object>> c = new HashMap<>();
    protected int d = -1;
    @Deprecated
    protected int f = -1;
    private final Object p0 = new Object();
    private boolean p1;
    private final ContentObservable p2 = new ContentObservable();
    private Bundle p3 = Bundle.EMPTY;
    @Deprecated
    protected Long q = null;
    protected boolean x;
    protected ContentResolver y;
    private Uri z;

    public abstract String[] getColumnNames();

    public abstract int getCount();

    public abstract long getLong(int i);

    public abstract String getString(int i);

    public int getType(int column) {
        return 3;
    }

    public byte[] getBlob(int column) {
        throw new UnsupportedOperationException("getBlob is not supported");
    }

    public int getColumnCount() {
        return getColumnNames().length;
    }

    public void deactivate() {
        g();
    }

    /* access modifiers changed from: protected */
    public void g() {
        ContentObserver contentObserver = this.a1;
        if (contentObserver != null) {
            this.y.unregisterContentObserver(contentObserver);
            this.p1 = false;
        }
        this.a2.notifyInvalidated();
    }

    public boolean requery() {
        ContentObserver contentObserver = this.a1;
        if (contentObserver != null && !this.p1) {
            this.y.registerContentObserver(this.z, true, contentObserver);
            this.p1 = true;
        }
        this.a2.notifyChanged();
        return true;
    }

    public boolean isClosed() {
        return this.x;
    }

    public void close() {
        this.x = true;
        this.p2.unregisterAll();
        g();
    }

    public boolean onMove(int oldPosition, int newPosition) {
        return true;
    }

    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        String result = getString(columnIndex);
        if (result != null) {
            char[] data = buffer.data;
            if (data == null || data.length < result.length()) {
                buffer.data = result.toCharArray();
            } else {
                result.getChars(0, result.length(), data, 0);
            }
            buffer.sizeCopied = result.length();
            return;
        }
        buffer.sizeCopied = 0;
    }

    public final int getPosition() {
        return this.d;
    }

    public boolean moveToPosition(int position) {
        int count = getCount();
        if (position >= count) {
            this.d = count;
            return false;
        } else if (position < 0) {
            this.d = -1;
            return false;
        } else {
            int i = this.d;
            if (position == i) {
                return true;
            }
            boolean result = onMove(i, position);
            if (!result) {
                this.d = -1;
            } else {
                this.d = position;
                int i2 = this.f;
                if (i2 != -1) {
                    this.q = Long.valueOf(getLong(i2));
                }
            }
            return result;
        }
    }

    public final boolean move(int offset) {
        return moveToPosition(this.d + offset);
    }

    public final boolean moveToFirst() {
        return moveToPosition(0);
    }

    public final boolean moveToLast() {
        return moveToPosition(getCount() - 1);
    }

    public final boolean moveToNext() {
        return moveToPosition(this.d + 1);
    }

    public final boolean moveToPrevious() {
        return moveToPosition(this.d - 1);
    }

    public final boolean isFirst() {
        return this.d == 0 && getCount() != 0;
    }

    public final boolean isLast() {
        int cnt = getCount();
        return this.d == cnt + -1 && cnt != 0;
    }

    public final boolean isBeforeFirst() {
        if (getCount() == 0 || this.d == -1) {
            return true;
        }
        return false;
    }

    public final boolean isAfterLast() {
        if (getCount() == 0 || this.d == getCount()) {
            return true;
        }
        return false;
    }

    public int getColumnIndex(String columnName) {
        int periodIndex = columnName.lastIndexOf(46);
        if (periodIndex != -1) {
            Exception e = new Exception();
            Log.b("Cursor", "requesting column name with table name -- " + columnName, e);
            columnName = columnName.substring(periodIndex + 1);
        }
        String[] columnNames = getColumnNames();
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            if (columnNames[i].equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }

    public int getColumnIndexOrThrow(String columnName) {
        int index = getColumnIndex(columnName);
        if (index >= 0) {
            return index;
        }
        throw new IllegalArgumentException("column '" + columnName + "' does not exist");
    }

    public String getColumnName(int columnIndex) {
        return getColumnNames()[columnIndex];
    }

    public void registerContentObserver(ContentObserver observer) {
        this.p2.registerObserver(observer);
    }

    public void unregisterContentObserver(ContentObserver observer) {
        if (!this.x) {
            this.p2.unregisterObserver(observer);
        }
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        this.a2.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        this.a2.unregisterObserver(observer);
    }

    /* access modifiers changed from: protected */
    public void c(boolean selfChange) {
        synchronized (this.p0) {
            this.p2.dispatchChange(selfChange);
            Uri uri = this.z;
            if (uri != null && selfChange) {
                this.y.notifyChange(uri, this.a1);
            }
        }
    }

    public void setNotificationUri(ContentResolver cr, Uri notifyUri) {
        synchronized (this.p0) {
            this.z = notifyUri;
            this.y = cr;
            ContentObserver contentObserver = this.a1;
            if (contentObserver != null) {
                cr.unregisterContentObserver(contentObserver);
            }
            C0222a aVar = new C0222a(this);
            this.a1 = aVar;
            this.y.registerContentObserver(this.z, true, aVar);
            this.p1 = true;
        }
    }

    public Uri getNotificationUri() {
        return this.z;
    }

    public boolean getWantsAllOnMoveCalls() {
        return false;
    }

    public void setExtras(Bundle extras) {
        this.p3 = extras == null ? Bundle.EMPTY : extras;
    }

    public Bundle getExtras() {
        return this.p3;
    }

    public Bundle respond(Bundle extras) {
        return Bundle.EMPTY;
    }

    /* access modifiers changed from: protected */
    public void a() {
        if (-1 == this.d || getCount() == this.d) {
            throw new CursorIndexOutOfBoundsException(this.d, getCount());
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        ContentObserver contentObserver = this.a1;
        if (contentObserver != null && this.p1) {
            this.y.unregisterContentObserver(contentObserver);
        }
        try {
            if (!this.x) {
                close();
            }
        } catch (Exception e) {
        }
    }

    /* renamed from: com.tencent.wcdb.a$a  reason: collision with other inner class name */
    /* compiled from: AbstractCursor */
    public static class C0222a extends ContentObserver {
        WeakReference<a> a;

        public C0222a(a cursor) {
            super((Handler) null);
            this.a = new WeakReference<>(cursor);
        }

        public boolean deliverSelfNotifications() {
            return false;
        }

        public void onChange(boolean selfChange) {
            a cursor = (a) this.a.get();
            if (cursor != null) {
                cursor.c(false);
            }
        }
    }
}
