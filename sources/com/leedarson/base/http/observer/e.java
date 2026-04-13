package com.leedarson.base.http.observer;

import java.util.concurrent.ThreadFactory;

/* compiled from: lambda */
public final /* synthetic */ class e implements ThreadFactory {
    public static final /* synthetic */ e c = new e();

    private /* synthetic */ e() {
    }

    public final Thread newThread(Runnable runnable) {
        return l.g(runnable);
    }
}
