package org.greenrobot.eventbus;

import java.util.logging.Level;

/* compiled from: BackgroundPoster */
public final class b implements Runnable, k {
    private final j c = new j();
    private final c d;
    private volatile boolean f;

    b(c eventBus) {
        this.d = eventBus;
    }

    public void a(p subscription, Object event) {
        i pendingPost = i.a(subscription, event);
        synchronized (this) {
            this.c.a(pendingPost);
            if (!this.f) {
                this.f = true;
                this.d.d().execute(this);
            }
        }
    }

    public void run() {
        while (true) {
            try {
                i pendingPost = this.c.c(1000);
                if (pendingPost == null) {
                    synchronized (this) {
                        pendingPost = this.c.b();
                        if (pendingPost == null) {
                            this.f = false;
                            this.f = false;
                            return;
                        }
                    }
                }
                this.d.g(pendingPost);
            } catch (InterruptedException e) {
                try {
                    f e2 = this.d.e();
                    Level level = Level.WARNING;
                    e2.a(level, Thread.currentThread().getName() + " was interruppted", e);
                    return;
                } finally {
                    this.f = false;
                }
            }
        }
    }
}
