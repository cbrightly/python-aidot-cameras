package com.tencent.wcdb;

import android.os.Binder;
import android.os.IBinder;
import net.sqlcipher.IBulkCursor;

/* compiled from: BulkCursorNative */
public abstract class c extends Binder implements i {
    public static i a(IBinder obj) {
        if (obj == null) {
            return null;
        }
        i in = (i) obj.queryLocalInterface(IBulkCursor.descriptor);
        if (in != null) {
            return in;
        }
        return new d(obj);
    }
}
