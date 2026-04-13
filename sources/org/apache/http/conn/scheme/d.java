package org.apache.http.conn.scheme;

import java.net.Socket;

@Deprecated
/* compiled from: LayeredSocketFactoryAdaptor */
public class d extends n implements c {
    private final b b;

    d(b factory) {
        super(factory);
        this.b = factory;
    }

    public Socket c(Socket socket, String host, int port, boolean autoClose) {
        return this.b.d(socket, host, port, autoClose);
    }
}
