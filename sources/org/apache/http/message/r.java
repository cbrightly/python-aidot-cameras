package org.apache.http.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.d;
import org.apache.http.g;

/* compiled from: HeaderGroup */
public class r implements Cloneable, Serializable {
    private static final long serialVersionUID = 2608834160639271617L;
    private final d[] EMPTY = new d[0];
    private final List<d> headers = new ArrayList(16);

    public void clear() {
        this.headers.clear();
    }

    public void addHeader(d header) {
        if (header != null) {
            this.headers.add(header);
        }
    }

    public void removeHeader(d header) {
        if (header != null) {
            this.headers.remove(header);
        }
    }

    public void updateHeader(d header) {
        if (header != null) {
            for (int i = 0; i < this.headers.size(); i++) {
                if (this.headers.get(i).getName().equalsIgnoreCase(header.getName())) {
                    this.headers.set(i, header);
                    return;
                }
            }
            this.headers.add(header);
        }
    }

    public void setHeaders(d[] headers2) {
        clear();
        if (headers2 != null) {
            Collections.addAll(this.headers, headers2);
        }
    }

    public d getCondensedHeader(String name) {
        d[] hdrs = getHeaders(name);
        if (hdrs.length == 0) {
            return null;
        }
        if (hdrs.length == 1) {
            return hdrs[0];
        }
        org.apache.http.util.d valueBuffer = new org.apache.http.util.d(128);
        valueBuffer.append(hdrs[0].getValue());
        for (int i = 1; i < hdrs.length; i++) {
            valueBuffer.append(", ");
            valueBuffer.append(hdrs[i].getValue());
        }
        return new b(name.toLowerCase(Locale.ROOT), valueBuffer.toString());
    }

    public d[] getHeaders(String name) {
        List<Header> headersFound = null;
        for (int i = 0; i < this.headers.size(); i++) {
            d header = this.headers.get(i);
            if (header.getName().equalsIgnoreCase(name)) {
                if (headersFound == null) {
                    headersFound = new ArrayList<>();
                }
                headersFound.add(header);
            }
        }
        return headersFound != null ? (d[]) headersFound.toArray(new d[headersFound.size()]) : this.EMPTY;
    }

    public d getFirstHeader(String name) {
        for (int i = 0; i < this.headers.size(); i++) {
            d header = this.headers.get(i);
            if (header.getName().equalsIgnoreCase(name)) {
                return header;
            }
        }
        return null;
    }

    public d getLastHeader(String name) {
        for (int i = this.headers.size() - 1; i >= 0; i--) {
            d header = this.headers.get(i);
            if (header.getName().equalsIgnoreCase(name)) {
                return header;
            }
        }
        return null;
    }

    public d[] getAllHeaders() {
        List<d> list = this.headers;
        return (d[]) list.toArray(new d[list.size()]);
    }

    public boolean containsHeader(String name) {
        for (int i = 0; i < this.headers.size(); i++) {
            if (this.headers.get(i).getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public g iterator() {
        return new l(this.headers, (String) null);
    }

    public g iterator(String name) {
        return new l(this.headers, name);
    }

    public r copy() {
        r clone = new r();
        clone.headers.addAll(this.headers);
        return clone;
    }

    public Object clone() {
        return super.clone();
    }

    public String toString() {
        return this.headers.toString();
    }
}
