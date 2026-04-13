package org.apache.http.conn;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.routing.b;
import org.apache.http.h;
import org.apache.http.l;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;

@Deprecated
/* compiled from: ManagedClientConnection */
public interface q extends h, p, r, g {
    void A0(boolean z, HttpParams httpParams);

    void J0(l lVar, boolean z, HttpParams httpParams);

    void O(long j, TimeUnit timeUnit);

    void R(b bVar, f fVar, HttpParams httpParams);

    b e();

    void i0();

    void v0();

    void y0(Object obj);

    void z0(f fVar, HttpParams httpParams);
}
