package org.apache.httpcore.impl.io;

import java.net.Socket;
import org.apache.httpcore.ConnectionClosedException;
import org.apache.httpcore.config.b;
import org.apache.httpcore.impl.f;
import org.apache.httpcore.m;
import org.apache.httpcore.message.t;
import org.apache.httpcore.message.u;
import org.apache.httpcore.n;
import org.apache.httpcore.util.d;

/* compiled from: DefaultHttpRequestParser */
public class g extends a<m> {
    private final n g;
    private final d h;

    public g(org.apache.httpcore.io.g buffer, t lineParser, n requestFactory, b constraints) {
        super(buffer, lineParser, constraints);
        this.g = requestFactory != null ? requestFactory : f.a;
        this.h = new d(128);
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public m b(Socket socket, org.apache.httpcore.io.g sessionBuffer) {
        this.h.clear();
        if (sessionBuffer.a(this.h) != -1) {
            return this.g.a(socket, this.d.b(this.h, new u(0, this.h.length())));
        }
        throw new ConnectionClosedException("Client closed connection");
    }
}
