package org.apache.http.impl.cookie;

import org.apache.http.cookie.i;
import org.apache.http.cookie.k;
import org.apache.http.protocol.f;

/* compiled from: IgnoreSpecProvider */
public class u implements k {
    private volatile i a;

    public i a(f context) {
        if (this.a == null) {
            synchronized (this) {
                if (this.a == null) {
                    this.a = new s();
                }
            }
        }
        return this.a;
    }
}
