package com.downloader.core;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: DefaultExecutorSupplier */
public class b implements e {
    private static final int a = ((Runtime.getRuntime().availableProcessors() * 2) + 1);
    private final c b = new c(a, new g(10));
    private final Executor c = Executors.newSingleThreadExecutor();
    private final Executor d = new f();

    b() {
    }

    public c a() {
        return this.b;
    }

    public Executor c() {
        return this.c;
    }

    public Executor b() {
        return this.d;
    }
}
