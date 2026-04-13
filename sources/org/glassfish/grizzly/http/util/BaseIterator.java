package org.glassfish.grizzly.http.util;

import java.util.Iterator;

/* compiled from: MimeHeaders */
public abstract class BaseIterator implements Iterator<String> {
    int currentPos;
    protected final MimeHeaders headers;
    int pos;
    int size;

    /* access modifiers changed from: protected */
    public abstract void findNext();

    public BaseIterator(MimeHeaders headers2) {
        this.headers = headers2;
    }

    public void remove() {
        int i = this.currentPos;
        if (i >= 0) {
            this.headers.removeHeader(i);
            this.pos = this.currentPos;
            this.currentPos = -1;
            this.size--;
            findNext();
            return;
        }
        throw new IllegalStateException("No current element");
    }
}
