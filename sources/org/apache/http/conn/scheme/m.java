package org.apache.http.conn.scheme;

import java.net.InetAddress;
import java.net.Socket;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: SocketFactory */
public interface m {
    boolean a(Socket socket);

    Socket e(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams);

    Socket f();
}
