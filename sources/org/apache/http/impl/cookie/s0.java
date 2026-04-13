package org.apache.http.impl.cookie;

import org.apache.http.cookie.b;

/* compiled from: RFC6265StrictSpec */
public class s0 extends p0 {
    static final String[] g = {"EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy"};

    s0(b... handlers) {
        super(handlers);
    }

    public String toString() {
        return "rfc6265-strict";
    }
}
