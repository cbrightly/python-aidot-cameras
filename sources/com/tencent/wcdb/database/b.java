package com.tencent.wcdb.database;

import java.io.Closeable;

/* compiled from: SQLiteClosable */
public abstract class b implements Closeable {
    private int c = 1;

    /* access modifiers changed from: protected */
    public abstract void c();

    public void a() {
        synchronized (this) {
            int i = this.c;
            if (i > 0) {
                this.c = i + 1;
            } else {
                throw new IllegalStateException("attempt to re-open an already-closed object: " + this);
            }
        }
    }

    public void g() {
        boolean refCountIsZero;
        synchronized (this) {
            boolean z = true;
            int i = this.c - 1;
            this.c = i;
            if (i != 0) {
                z = false;
            }
            refCountIsZero = z;
        }
        if (refCountIsZero) {
            c();
        }
    }

    public void close() {
        g();
    }
}
