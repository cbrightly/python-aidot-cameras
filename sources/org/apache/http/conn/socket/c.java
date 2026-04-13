package org.apache.http.conn.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.http.l;
import org.apache.http.protocol.f;

/* compiled from: PlainConnectionSocketFactory */
public class c implements a {
    public static final c a = new c();

    public static c a() {
        return a;
    }

    public Socket i(f context) {
        return new Socket();
    }

    public Socket k(int connectTimeout, Socket socket, l host, InetSocketAddress remoteAddress, InetSocketAddress localAddress, f context) {
        Socket sock = socket != null ? socket : i(context);
        if (localAddress != null) {
            sock.bind(localAddress);
        }
        try {
            sock.connect(remoteAddress, connectTimeout);
            return sock;
        } catch (IOException ex) {
            try {
                sock.close();
            } catch (IOException e) {
            }
            throw ex;
        }
    }
}
