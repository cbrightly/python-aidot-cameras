package org.apache.http.conn.scheme;

import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: SchemeSocketFactory */
public interface k {
    boolean a(Socket socket);

    Socket g(Socket socket, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpParams httpParams);

    Socket j(HttpParams httpParams);
}
