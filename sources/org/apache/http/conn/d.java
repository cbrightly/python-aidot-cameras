package org.apache.http.conn;

import java.net.InetAddress;
import org.apache.http.l;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;

@Deprecated
/* compiled from: ClientConnectionOperator */
public interface d {
    void a(s sVar, l lVar, InetAddress inetAddress, f fVar, HttpParams httpParams);

    void b(s sVar, l lVar, f fVar, HttpParams httpParams);

    s createConnection();
}
