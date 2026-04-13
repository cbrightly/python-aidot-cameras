package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.internal.j;
import java.net.ProtocolException;
import okio.b0;
import okio.c;
import okio.f0;

/* compiled from: RetryableSink */
public final class n implements b0 {
    private boolean c;
    private final int d;
    private final c f;

    public n(int limit) {
        this.f = new c();
        this.d = limit;
    }

    public n() {
        this(-1);
    }

    public void close() {
        if (!this.c) {
            this.c = true;
            if (this.f.e1() < ((long) this.d)) {
                throw new ProtocolException("content-length promised " + this.d + " bytes, but received " + this.f.e1());
            }
        }
    }

    public void write(c source, long byteCount) {
        if (!this.c) {
            j.a(source.e1(), 0, byteCount);
            if (this.d == -1 || this.f.e1() <= ((long) this.d) - byteCount) {
                this.f.write(source, byteCount);
                return;
            }
            throw new ProtocolException("exceeded content-length limit of " + this.d + " bytes");
        }
        throw new IllegalStateException("closed");
    }

    public void flush() {
    }

    public f0 timeout() {
        return f0.a;
    }

    public long a() {
        return this.f.e1();
    }

    public void c(b0 socketOut) {
        c buffer = new c();
        c cVar = this.f;
        cVar.j(buffer, 0, cVar.e1());
        socketOut.write(buffer, buffer.e1());
    }
}
