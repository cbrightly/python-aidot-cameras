package org.glassfish.grizzly.http.util;

/* compiled from: MimeHeaders */
public final class ValuesIterator extends BaseIterator {
    final String name;
    DataChunk next;

    ValuesIterator(MimeHeaders headers, String name2, boolean trailersOnly) {
        super(headers);
        this.name = name2;
        this.pos = trailersOnly ? headers.mark : 0;
        this.size = headers.size();
        findNext();
    }

    /* access modifiers changed from: protected */
    public void findNext() {
        this.next = null;
        while (true) {
            int i = this.pos;
            if (i >= this.size) {
                break;
            } else if (this.headers.getName(i).equalsIgnoreCase(this.name)) {
                this.next = this.headers.getValue(this.pos);
                break;
            } else {
                this.pos++;
            }
        }
        this.pos++;
    }

    public boolean hasNext() {
        return this.next != null;
    }

    public String next() {
        this.currentPos = this.pos - 1;
        String current = this.next.toString();
        findNext();
        return current;
    }
}
