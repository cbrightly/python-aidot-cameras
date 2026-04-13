package org.apache.http.conn;

import java.net.Socket;
import org.apache.http.h;
import org.apache.http.l;
import org.apache.http.m;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: OperatedClientConnection */
public interface s extends h, m {
    void U(boolean z, HttpParams httpParams);

    void b0(Socket socket, l lVar);

    void e0(Socket socket, l lVar, boolean z, HttpParams httpParams);

    boolean isSecure();

    Socket q();
}
