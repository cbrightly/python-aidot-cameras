package org.glassfish.grizzly.http.util;

/* compiled from: MimeHeaders */
public final class MimeHeaderField {
    private boolean isSerialized;
    protected final DataChunk nameB = DataChunk.newInstance();
    protected final DataChunk valueB = DataChunk.newInstance();

    public void recycle() {
        this.isSerialized = false;
        this.nameB.recycle();
        this.valueB.recycle();
    }

    public DataChunk getName() {
        return this.nameB;
    }

    public DataChunk getValue() {
        return this.valueB;
    }

    public boolean isSerialized() {
        return this.isSerialized;
    }

    public void setSerialized(boolean isSerialized2) {
        this.isSerialized = isSerialized2;
    }
}
