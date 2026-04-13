package org.apache.http.impl.conn.tsccm;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.d;
import org.apache.http.conn.s;
import org.apache.http.util.a;

@Deprecated
/* compiled from: BasicPoolEntry */
public class b extends org.apache.http.impl.conn.b {
    private final long f;
    private long g;
    private final long h;
    private long i;

    public b(d op, org.apache.http.conn.routing.b route, long connTTL, TimeUnit timeunit) {
        super(op, route);
        a.i(route, "HTTP route");
        long currentTimeMillis = System.currentTimeMillis();
        this.f = currentTimeMillis;
        if (connTTL > 0) {
            this.h = currentTimeMillis + timeunit.toMillis(connTTL);
        } else {
            this.h = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        }
        this.i = this.h;
    }

    /* access modifiers changed from: protected */
    public final s h() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public final org.apache.http.conn.routing.b i() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void e() {
        super.e();
    }

    public long j() {
        return this.g;
    }

    public void l(long time, TimeUnit timeunit) {
        long newExpiry;
        long currentTimeMillis = System.currentTimeMillis();
        this.g = currentTimeMillis;
        if (time > 0) {
            newExpiry = currentTimeMillis + timeunit.toMillis(time);
        } else {
            newExpiry = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        }
        this.i = Math.min(this.h, newExpiry);
    }

    public boolean k(long now) {
        return now >= this.i;
    }
}
