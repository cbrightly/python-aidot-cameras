package com.yanzhenjie.andserver;

import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;

/* compiled from: Server */
public interface e {

    /* compiled from: Server */
    public interface a<T extends a, S extends e> {
        T a(int i, TimeUnit timeUnit);

        T b(SSLContext sSLContext);

        S build();

        T c(d dVar);

        T d(b bVar);

        T e(int i);
    }

    /* compiled from: Server */
    public interface b {
        void a();

        void onException(Exception exc);

        void onStarted();
    }

    void a();

    boolean isRunning();

    void shutdown();
}
