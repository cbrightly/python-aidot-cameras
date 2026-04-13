package org.apache.http.conn.socket;

import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.http.l;
import org.apache.http.protocol.f;

/* compiled from: ConnectionSocketFactory */
public interface a {
    Socket i(f fVar);

    Socket k(int i, Socket socket, l lVar, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, f fVar);
}
