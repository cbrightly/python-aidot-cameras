package com.squareup.okhttp.internal;

import com.squareup.okhttp.a;
import com.squareup.okhttp.internal.http.q;
import com.squareup.okhttp.internal.io.b;
import com.squareup.okhttp.j;
import com.squareup.okhttp.k;
import com.squareup.okhttp.p;
import com.squareup.okhttp.t;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

/* compiled from: Internal */
public abstract class d {
    public static final Logger a = Logger.getLogger(t.class.getName());
    public static d b;

    public abstract void a(p.b bVar, String str);

    public abstract void b(k kVar, SSLSocket sSLSocket, boolean z);

    public abstract boolean c(j jVar, b bVar);

    public abstract b d(j jVar, a aVar, q qVar);

    public abstract e e(t tVar);

    public abstract void f(j jVar, b bVar);

    public abstract i g(j jVar);
}
