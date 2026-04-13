package org.apache.httpcore.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.apache.httpcore.Header;
import org.apache.httpcore.e;
import org.apache.httpcore.g;
import org.apache.httpcore.util.d;

/* compiled from: HeaderGroup */
public class q implements Cloneable, Serializable {
    private static final e[] c = new e[0];
    private static final long serialVersionUID = 2608834160639271617L;
    private final List<e> headers = new ArrayList(16);

    public void clear() {
        this.headers.clear();
    }

    public void addHeader(e header) {
        if (header != null) {
            this.headers.add(header);
        }
    }

    public void removeHeader(e header) {
        if (header != null) {
            this.headers.remove(header);
        }
    }

    public void updateHeader(e header) {
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

    public void setHeaders(e[] headers2) {
        clear();
        if (headers2 != null) {
            Collections.addAll(this.headers, headers2);
        }
    }

    public e getCondensedHeader(String name) {
        e[] hdrs = getHeaders(name);
        if (hdrs.length == 0) {
            return null;
        }
        if (hdrs.length == 1) {
            return hdrs[0];
        }
        d valueBuffer = new d(128);
        valueBuffer.append(hdrs[0].getValue());
        for (int i = 1; i < hdrs.length; i++) {
            valueBuffer.append(", ");
            valueBuffer.append(hdrs[i].getValue());
        }
        return new b(name.toLowerCase(Locale.ROOT), valueBuffer.toString());
    }

    public e[] getHeaders(String name) {
        List<Header> headersFound = null;
        for (int i = 0; i < this.headers.size(); i++) {
            e header = this.headers.get(i);
            if (header.getName().equalsIgnoreCase(name)) {
                if (headersFound == null) {
                    headersFound = new ArrayList<>();
                }
                headersFound.add(header);
            }
        }
        return headersFound != null ? (e[]) headersFound.toArray(new e[headersFound.size()]) : c;
    }

    public e getFirstHeader(String name) {
        for (int i = 0; i < this.headers.size(); i++) {
            e header = this.headers.get(i);
            if (header.getName().equalsIgnoreCase(name)) {
                return header;
            }
        }
        return null;
    }

    public e getLastHeader(String name) {
        for (int i = this.headers.size() - 1; i >= 0; i--) {
            e header = this.headers.get(i);
            if (header.getName().equalsIgnoreCase(name)) {
                return header;
            }
        }
        return null;
    }

    public e[] getAllHeaders() {
        List<e> list = this.headers;
        return (e[]) list.toArray(new e[list.size()]);
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
        return new k(this.headers, (String) null);
    }

    public g iterator(String name) {
        return new k(this.headers, name);
    }

    public q copy() {
        q clone = new q();
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
