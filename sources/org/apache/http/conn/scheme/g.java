package org.apache.http.conn.scheme;

import java.net.Socket;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: SchemeLayeredSocketFactory */
public interface g extends k {
    Socket b(Socket socket, String str, int i, HttpParams httpParams);
}
