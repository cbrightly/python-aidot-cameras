package com.yanzhenjie.andserver.util;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: Executors */
public class c {
    private static c a;
    private static Handler b;
    private final ExecutorService c = Executors.newCachedThreadPool();

    public static c b() {
        if (a == null) {
            synchronized (c.class) {
                if (a == null) {
                    a = new c();
                }
            }
        }
        return a;
    }

    private c() {
        b = new Handler(Looper.getMainLooper());
    }

    public void a(Runnable runnable) {
        this.c.execute(runnable);
    }

    public void c(Runnable command) {
        b.post(command);
    }
}
