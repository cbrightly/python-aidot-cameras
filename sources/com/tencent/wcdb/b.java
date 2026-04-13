package com.tencent.wcdb;

import android.database.CharArrayBuffer;

/* compiled from: AbstractWindowedCursor */
public abstract class b extends a {
    protected CursorWindow p4;

    public byte[] getBlob(int columnIndex) {
        a();
        return this.p4.j(this.d, columnIndex);
    }

    public String getString(int columnIndex) {
        a();
        return this.p4.v(this.d, columnIndex);
    }

    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        a();
        this.p4.i(this.d, columnIndex, buffer);
    }

    public short getShort(int columnIndex) {
        a();
        return this.p4.t(this.d, columnIndex);
    }

    public int getInt(int columnIndex) {
        a();
        return this.p4.n(this.d, columnIndex);
    }

    public long getLong(int columnIndex) {
        a();
        return this.p4.o(this.d, columnIndex);
    }

    public float getFloat(int columnIndex) {
        a();
        return this.p4.m(this.d, columnIndex);
    }

    public double getDouble(int columnIndex) {
        a();
        return this.p4.l(this.d, columnIndex);
    }

    public boolean isNull(int columnIndex) {
        a();
        return this.p4.x(this.d, columnIndex) == 0;
    }

    public int getType(int columnIndex) {
        a();
        return this.p4.x(this.d, columnIndex);
    }

    /* access modifiers changed from: protected */
    public void a() {
        super.a();
        if (this.p4 == null) {
            throw new StaleDataException("Attempting to access a closed CursorWindow.Most probable cause: cursor is deactivated prior to calling this method.");
        }
    }

    /* access modifiers changed from: protected */
    public void j() {
        CursorWindow cursorWindow = this.p4;
        if (cursorWindow != null) {
            cursorWindow.close();
            this.p4 = null;
        }
    }

    /* access modifiers changed from: protected */
    public void i(String name) {
        CursorWindow cursorWindow = this.p4;
        if (cursorWindow == null) {
            this.p4 = new CursorWindow(name);
        } else {
            cursorWindow.clear();
        }
    }

    /* access modifiers changed from: protected */
    public void g() {
        super.g();
        j();
    }
}
