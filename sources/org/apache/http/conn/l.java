package org.apache.http.conn;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.routing.b;
import org.apache.http.h;
import org.apache.http.protocol.f;

/* compiled from: HttpClientConnectionManager */
public interface l {
    void a(long j, TimeUnit timeUnit);

    h c(b bVar, Object obj);

    void g(h hVar, b bVar, f fVar);

    void i(h hVar, b bVar, f fVar);

    void j();

    void l(h hVar, Object obj, long j, TimeUnit timeUnit);

    void m(h hVar, b bVar, int i, f fVar);

    void shutdown();
}
