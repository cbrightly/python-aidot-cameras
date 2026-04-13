package org.glassfish.grizzly.http.util;

/* compiled from: MimeHeaders */
public class NamesIterator extends BaseIterator {
    String next;

    NamesIterator(MimeHeaders headers, boolean trailersOnly) {
        super(headers);
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
            }
            this.next = this.headers.getName(i).toString();
            int j = 0;
            while (true) {
                if (j >= this.pos) {
                    break;
                } else if (this.headers.getName(j).equalsIgnoreCase(this.next)) {
                    this.next = null;
                    break;
                } else {
                    j++;
                }
            }
            if (this.next != null) {
                break;
            }
            this.pos++;
        }
        this.pos++;
    }

    public boolean hasNext() {
        return this.next != null;
    }

    public String next() {
        this.currentPos = this.pos - 1;
        String current = this.next;
        findNext();
        return current;
    }
}
