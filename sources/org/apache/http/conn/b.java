package org.apache.http.conn;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.scheme.j;

@Deprecated
/* compiled from: ClientConnectionManager */
public interface b {
    void a(long j, TimeUnit timeUnit);

    e c(org.apache.http.conn.routing.b bVar, Object obj);

    void d(q qVar, long j, TimeUnit timeUnit);

    j e();

    void shutdown();
}
