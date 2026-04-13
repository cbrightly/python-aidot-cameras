package org.apache.http.impl.conn;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.apache.commons.logging.a;
import org.apache.http.config.c;
import org.apache.http.entity.e;
import org.apache.http.io.d;
import org.apache.http.io.f;
import org.apache.http.o;
import org.apache.http.q;

/* compiled from: LoggingManagedHttpClientConnection */
public class u extends n {
    private final a p3;
    private final a p4;
    private final e0 z4;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public u(String id, a log, a headerlog, a wirelog, int buffersize, int fragmentSizeHint, CharsetDecoder chardecoder, CharsetEncoder charencoder, c constraints, e incomingContentStrategy, e outgoingContentStrategy, f<o> requestWriterFactory, d<q> responseParserFactory) {
        super(id, buffersize, fragmentSizeHint, chardecoder, charencoder, constraints, incomingContentStrategy, outgoingContentStrategy, requestWriterFactory, responseParserFactory);
        this.p3 = log;
        this.p4 = headerlog;
        String str = id;
        this.z4 = new e0(wirelog, id);
    }

    public void close() {
        if (super.isOpen()) {
            if (this.p3.isDebugEnabled()) {
                a aVar = this.p3;
                aVar.debug(getId() + ": Close connection");
            }
            super.close();
        }
    }

    public void y(int timeout) {
        if (this.p3.isDebugEnabled()) {
            a aVar = this.p3;
            aVar.debug(getId() + ": set socket timeout to " + timeout);
        }
        super.y(timeout);
    }

    public void shutdown() {
        if (this.p3.isDebugEnabled()) {
            a aVar = this.p3;
            aVar.debug(getId() + ": Shutdown connection");
        }
        super.shutdown();
    }

    /* access modifiers changed from: protected */
    public InputStream o(Socket socket) {
        InputStream in = super.o(socket);
        if (this.z4.a()) {
            return new t(in, this.z4);
        }
        return in;
    }

    /* access modifiers changed from: protected */
    public OutputStream r(Socket socket) {
        OutputStream out = super.r(socket);
        if (this.z4.a()) {
            return new v(out, this.z4);
        }
        return out;
    }

    /* access modifiers changed from: protected */
    public void z(q response) {
        if (response != null && this.p4.isDebugEnabled()) {
            this.p4.debug(getId() + " << " + response.j().toString());
            for (org.apache.http.d header : response.v()) {
                this.p4.debug(getId() + " << " + header.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void x(o request) {
        if (request != null && this.p4.isDebugEnabled()) {
            this.p4.debug(getId() + " >> " + request.r().toString());
            for (org.apache.http.d header : request.v()) {
                this.p4.debug(getId() + " >> " + header.toString());
            }
        }
    }
}
