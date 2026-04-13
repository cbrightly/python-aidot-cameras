package org.apache.http.impl.io;

import java.net.Socket;
import org.apache.http.params.HttpParams;
import org.apache.http.util.a;

@Deprecated
/* compiled from: SocketOutputBuffer */
public class u extends d {
    public u(Socket socket, int buffersize, HttpParams params) {
        a.i(socket, "Socket");
        int n = buffersize;
        n = n < 0 ? socket.getSendBufferSize() : n;
        f(socket.getOutputStream(), n < 1024 ? 1024 : n, params);
    }
}
