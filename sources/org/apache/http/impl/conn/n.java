package org.apache.http.impl.conn;

import java.io.InterruptedIOException;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.http.conn.r;
import org.apache.http.entity.e;
import org.apache.http.impl.c;
import org.apache.http.io.d;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.q;

/* compiled from: DefaultManagedHttpClientConnection */
public class n extends c implements r, f {
    private final Map<String, Object> a2 = new ConcurrentHashMap();
    private final String p1;
    private volatile boolean p2;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public n(String id, int buffersize, int fragmentSizeHint, CharsetDecoder chardecoder, CharsetEncoder charencoder, org.apache.http.config.c constraints, e incomingContentStrategy, e outgoingContentStrategy, org.apache.http.io.f<o> requestWriterFactory, d<q> responseParserFactory) {
        super(buffersize, fragmentSizeHint, chardecoder, charencoder, constraints, incomingContentStrategy, outgoingContentStrategy, requestWriterFactory, responseParserFactory);
        this.p1 = id;
    }

    public String getId() {
        return this.p1;
    }

    public void shutdown() {
        this.p2 = true;
        super.shutdown();
    }

    public Object getAttribute(String id) {
        return this.a2.get(id);
    }

    public void setAttribute(String id, Object obj) {
        this.a2.put(id, obj);
    }

    public void O0(Socket socket) {
        if (!this.p2) {
            super.O0(socket);
        } else {
            socket.close();
            throw new InterruptedIOException("Connection already shutdown");
        }
    }

    public Socket q() {
        return super.q();
    }

    public SSLSession S0() {
        Socket socket = super.q();
        if (socket instanceof SSLSocket) {
            return ((SSLSocket) socket).getSession();
        }
        return null;
    }
}
