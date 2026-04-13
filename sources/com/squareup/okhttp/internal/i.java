package com.squareup.okhttp.internal;

import com.squareup.okhttp.z;
import java.util.LinkedHashSet;
import java.util.Set;

/* compiled from: RouteDatabase */
public final class i {
    private final Set<z> a = new LinkedHashSet();

    public synchronized void b(z failedRoute) {
        this.a.add(failedRoute);
    }

    public synchronized void a(z route) {
        this.a.remove(route);
    }

    public synchronized boolean c(z route) {
        return this.a.contains(route);
    }
}
