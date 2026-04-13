package org.apache.http.client;

import org.apache.http.client.methods.p;
import org.apache.http.conn.b;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;
import org.apache.http.q;

public interface HttpClient {
    <T> T execute(p pVar, l<? extends T> lVar);

    <T> T execute(p pVar, l<? extends T> lVar, f fVar);

    <T> T execute(l lVar, o oVar, l<? extends T> lVar2);

    <T> T execute(l lVar, o oVar, l<? extends T> lVar2, f fVar);

    q execute(p pVar);

    q execute(p pVar, f fVar);

    q execute(l lVar, o oVar);

    q execute(l lVar, o oVar, f fVar);

    @Deprecated
    b getConnectionManager();

    @Deprecated
    HttpParams getParams();
}
