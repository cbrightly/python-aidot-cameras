package org.apache.http.impl.cookie;

import org.apache.http.cookie.i;
import org.apache.http.cookie.k;
import org.apache.http.protocol.f;

/* compiled from: NetscapeDraftSpecProvider */
public class b0 implements k {
    private final String[] a;
    private volatile i b;

    public b0(String[] datepatterns) {
        this.a = datepatterns;
    }

    public b0() {
        this((String[]) null);
    }

    public i a(f context) {
        if (this.b == null) {
            synchronized (this) {
                if (this.b == null) {
                    this.b = new z(this.a);
                }
            }
        }
        return this.b;
    }
}
