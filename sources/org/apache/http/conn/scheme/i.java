package org.apache.http.conn.scheme;

import java.net.Socket;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: SchemeLayeredSocketFactoryAdaptor */
public class i extends l implements g {
    private final c b;

    i(c factory) {
        super(factory);
        this.b = factory;
    }

    public Socket b(Socket socket, String target, int port, HttpParams params) {
        return this.b.c(socket, target, port, true);
    }
}
