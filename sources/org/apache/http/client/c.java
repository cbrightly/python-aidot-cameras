package org.apache.http.client;

import java.util.Map;
import java.util.Queue;
import org.apache.http.auth.a;
import org.apache.http.d;
import org.apache.http.l;
import org.apache.http.protocol.f;
import org.apache.http.q;

/* compiled from: AuthenticationStrategy */
public interface c {
    void a(l lVar, org.apache.http.auth.c cVar, f fVar);

    void b(l lVar, org.apache.http.auth.c cVar, f fVar);

    Map<String, d> c(l lVar, q qVar, f fVar);

    Queue<a> d(Map<String, d> map, l lVar, q qVar, f fVar);

    boolean e(l lVar, q qVar, f fVar);
}
