package org.greenrobot.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/* compiled from: HandlerPoster */
public class e extends Handler implements k {
    private final j c = new j();
    private final int d;
    private final c f;
    private boolean q;

    protected e(c eventBus, Looper looper, int maxMillisInsideHandleMessage) {
        super(looper);
        this.f = eventBus;
        this.d = maxMillisInsideHandleMessage;
    }

    public void a(p subscription, Object event) {
        i pendingPost = i.a(subscription, event);
        synchronized (this) {
            this.c.a(pendingPost);
            if (!this.q) {
                this.q = true;
                if (!sendMessage(obtainMessage())) {
                    throw new EventBusException("Could not send handler message");
                }
            }
        }
    }

    public void handleMessage(Message msg) {
        try {
            long started = SystemClock.uptimeMillis();
            do {
                i pendingPost = this.c.b();
                if (pendingPost == null) {
                    synchronized (this) {
                        pendingPost = this.c.b();
                        if (pendingPost == null) {
                            this.q = false;
                            this.q = false;
                            return;
                        }
                    }
                }
                this.f.g(pendingPost);
            } while (SystemClock.uptimeMillis() - started < ((long) this.d));
            if (sendMessage(obtainMessage())) {
                this.q = true;
                return;
            }
            throw new EventBusException("Could not send handler message");
        } catch (Throwable th) {
            this.q = false;
            throw th;
        }
    }
}
