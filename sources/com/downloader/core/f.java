package com.downloader.core;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

/* compiled from: MainThreadExecutor */
public class f implements Executor {
    private final Handler c = new Handler(Looper.getMainLooper());

    public void execute(Runnable runnable) {
        this.c.post(runnable);
    }
}
