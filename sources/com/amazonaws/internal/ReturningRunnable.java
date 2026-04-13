package com.amazonaws.internal;

import com.amazonaws.async.Callback;

public abstract class ReturningRunnable<R> {
    /* access modifiers changed from: private */
    public final String operationDescription;

    public abstract R run();

    public ReturningRunnable() {
        this.operationDescription = null;
    }

    public ReturningRunnable(String operationDescription2) {
        this.operationDescription = operationDescription2;
    }

    public R await() {
        return run();
    }

    public void async(final Callback<R> callback) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    callback.onResult(ReturningRunnable.this.run());
                } catch (Exception e) {
                    if (ReturningRunnable.this.operationDescription == null) {
                        callback.onError(e);
                    } else {
                        callback.onError(new Exception(ReturningRunnable.this.operationDescription, e));
                    }
                }
            }
        }).start();
    }
}
