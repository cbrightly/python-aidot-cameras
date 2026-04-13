package net.sqlcipher;

import android.database.CharArrayBuffer;

public abstract class AbstractWindowedCursor extends AbstractCursor {
    protected CursorWindow mWindow;

    public byte[] getBlob(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (!isFieldUpdated(columnIndex)) {
                return this.mWindow.getBlob(this.mPos, columnIndex);
            }
            byte[] bArr = (byte[]) getUpdatedField(columnIndex);
            return bArr;
        }
    }

    public String getString(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (!isFieldUpdated(columnIndex)) {
                return this.mWindow.getString(this.mPos, columnIndex);
            }
            String str = (String) getUpdatedField(columnIndex);
            return str;
        }
    }

    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                super.copyStringToBuffer(columnIndex, buffer);
            }
        }
        this.mWindow.copyStringToBuffer(this.mPos, columnIndex, buffer);
    }

    public short getShort(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (!isFieldUpdated(columnIndex)) {
                return this.mWindow.getShort(this.mPos, columnIndex);
            }
            short shortValue = ((Number) getUpdatedField(columnIndex)).shortValue();
            return shortValue;
        }
    }

    public int getInt(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (!isFieldUpdated(columnIndex)) {
                return this.mWindow.getInt(this.mPos, columnIndex);
            }
            int intValue = ((Number) getUpdatedField(columnIndex)).intValue();
            return intValue;
        }
    }

    public long getLong(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (!isFieldUpdated(columnIndex)) {
                return this.mWindow.getLong(this.mPos, columnIndex);
            }
            long longValue = ((Number) getUpdatedField(columnIndex)).longValue();
            return longValue;
        }
    }

    public float getFloat(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (!isFieldUpdated(columnIndex)) {
                return this.mWindow.getFloat(this.mPos, columnIndex);
            }
            float floatValue = ((Number) getUpdatedField(columnIndex)).floatValue();
            return floatValue;
        }
    }

    public double getDouble(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (!isFieldUpdated(columnIndex)) {
                return this.mWindow.getDouble(this.mPos, columnIndex);
            }
            double doubleValue = ((Number) getUpdatedField(columnIndex)).doubleValue();
            return doubleValue;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0016, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isNull(int r3) {
        /*
            r2 = this;
            r2.checkPosition()
            java.util.HashMap<java.lang.Long, java.util.Map<java.lang.String, java.lang.Object>> r0 = r2.mUpdatedRows
            monitor-enter(r0)
            boolean r1 = r2.isFieldUpdated(r3)     // Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x0017
            java.lang.Object r1 = r2.getUpdatedField(r3)     // Catch:{ all -> 0x0021 }
            if (r1 != 0) goto L_0x0014
            r1 = 1
            goto L_0x0015
        L_0x0014:
            r1 = 0
        L_0x0015:
            monitor-exit(r0)     // Catch:{ all -> 0x0021 }
            return r1
        L_0x0017:
            monitor-exit(r0)     // Catch:{ all -> 0x0021 }
            net.sqlcipher.CursorWindow r0 = r2.mWindow
            int r1 = r2.mPos
            boolean r0 = r0.isNull(r1, r3)
            return r0
        L_0x0021:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0021 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.AbstractWindowedCursor.isNull(int):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isBlob(int r4) {
        /*
            r3 = this;
            r3.checkPosition()
            java.util.HashMap<java.lang.Long, java.util.Map<java.lang.String, java.lang.Object>> r0 = r3.mUpdatedRows
            monitor-enter(r0)
            boolean r1 = r3.isFieldUpdated(r4)     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x001c
            java.lang.Object r1 = r3.getUpdatedField(r4)     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x0019
            boolean r2 = r1 instanceof byte[]     // Catch:{ all -> 0x0026 }
            if (r2 == 0) goto L_0x0017
            goto L_0x0019
        L_0x0017:
            r2 = 0
            goto L_0x001a
        L_0x0019:
            r2 = 1
        L_0x001a:
            monitor-exit(r0)     // Catch:{ all -> 0x0026 }
            return r2
        L_0x001c:
            monitor-exit(r0)     // Catch:{ all -> 0x0026 }
            net.sqlcipher.CursorWindow r0 = r3.mWindow
            int r1 = r3.mPos
            boolean r0 = r0.isBlob(r1, r4)
            return r0
        L_0x0026:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0026 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.AbstractWindowedCursor.isBlob(int):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isString(int r4) {
        /*
            r3 = this;
            r3.checkPosition()
            java.util.HashMap<java.lang.Long, java.util.Map<java.lang.String, java.lang.Object>> r0 = r3.mUpdatedRows
            monitor-enter(r0)
            boolean r1 = r3.isFieldUpdated(r4)     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x001c
            java.lang.Object r1 = r3.getUpdatedField(r4)     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x0019
            boolean r2 = r1 instanceof java.lang.String     // Catch:{ all -> 0x0026 }
            if (r2 == 0) goto L_0x0017
            goto L_0x0019
        L_0x0017:
            r2 = 0
            goto L_0x001a
        L_0x0019:
            r2 = 1
        L_0x001a:
            monitor-exit(r0)     // Catch:{ all -> 0x0026 }
            return r2
        L_0x001c:
            monitor-exit(r0)     // Catch:{ all -> 0x0026 }
            net.sqlcipher.CursorWindow r0 = r3.mWindow
            int r1 = r3.mPos
            boolean r0 = r0.isString(r1, r4)
            return r0
        L_0x0026:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0026 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.AbstractWindowedCursor.isString(int):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001e, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isLong(int r4) {
        /*
            r3 = this;
            r3.checkPosition()
            java.util.HashMap<java.lang.Long, java.util.Map<java.lang.String, java.lang.Object>> r0 = r3.mUpdatedRows
            monitor-enter(r0)
            boolean r1 = r3.isFieldUpdated(r4)     // Catch:{ all -> 0x0029 }
            if (r1 == 0) goto L_0x001f
            java.lang.Object r1 = r3.getUpdatedField(r4)     // Catch:{ all -> 0x0029 }
            if (r1 == 0) goto L_0x001c
            boolean r2 = r1 instanceof java.lang.Integer     // Catch:{ all -> 0x0029 }
            if (r2 != 0) goto L_0x001a
            boolean r2 = r1 instanceof java.lang.Long     // Catch:{ all -> 0x0029 }
            if (r2 == 0) goto L_0x001c
        L_0x001a:
            r2 = 1
            goto L_0x001d
        L_0x001c:
            r2 = 0
        L_0x001d:
            monitor-exit(r0)     // Catch:{ all -> 0x0029 }
            return r2
        L_0x001f:
            monitor-exit(r0)     // Catch:{ all -> 0x0029 }
            net.sqlcipher.CursorWindow r0 = r3.mWindow
            int r1 = r3.mPos
            boolean r0 = r0.isLong(r1, r4)
            return r0
        L_0x0029:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0029 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.AbstractWindowedCursor.isLong(int):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001e, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isFloat(int r4) {
        /*
            r3 = this;
            r3.checkPosition()
            java.util.HashMap<java.lang.Long, java.util.Map<java.lang.String, java.lang.Object>> r0 = r3.mUpdatedRows
            monitor-enter(r0)
            boolean r1 = r3.isFieldUpdated(r4)     // Catch:{ all -> 0x0029 }
            if (r1 == 0) goto L_0x001f
            java.lang.Object r1 = r3.getUpdatedField(r4)     // Catch:{ all -> 0x0029 }
            if (r1 == 0) goto L_0x001c
            boolean r2 = r1 instanceof java.lang.Float     // Catch:{ all -> 0x0029 }
            if (r2 != 0) goto L_0x001a
            boolean r2 = r1 instanceof java.lang.Double     // Catch:{ all -> 0x0029 }
            if (r2 == 0) goto L_0x001c
        L_0x001a:
            r2 = 1
            goto L_0x001d
        L_0x001c:
            r2 = 0
        L_0x001d:
            monitor-exit(r0)     // Catch:{ all -> 0x0029 }
            return r2
        L_0x001f:
            monitor-exit(r0)     // Catch:{ all -> 0x0029 }
            net.sqlcipher.CursorWindow r0 = r3.mWindow
            int r1 = r3.mPos
            boolean r0 = r0.isFloat(r1, r4)
            return r0
        L_0x0029:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0029 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.AbstractWindowedCursor.isFloat(int):boolean");
    }

    public int getType(int columnIndex) {
        checkPosition();
        return this.mWindow.getType(this.mPos, columnIndex);
    }

    /* access modifiers changed from: protected */
    public void checkPosition() {
        super.checkPosition();
        if (this.mWindow == null) {
            throw new StaleDataException("Access closed cursor");
        }
    }

    public CursorWindow getWindow() {
        return this.mWindow;
    }

    public void setWindow(CursorWindow window) {
        CursorWindow cursorWindow = this.mWindow;
        if (cursorWindow != null) {
            cursorWindow.close();
        }
        this.mWindow = window;
    }

    public boolean hasWindow() {
        return this.mWindow != null;
    }
}
