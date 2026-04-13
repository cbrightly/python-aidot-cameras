package io.reactivex.android.schedulers;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import io.reactivex.disposables.c;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;

/* compiled from: HandlerScheduler */
public final class b extends r {
    private final Handler b;
    private final boolean c;

    b(Handler handler, boolean async) {
        this.b = handler;
        this.c = async;
    }

    @SuppressLint({"NewApi"})
    public io.reactivex.disposables.b c(Runnable run, long delay, TimeUnit unit) {
        if (run == null) {
            throw new NullPointerException("run == null");
        } else if (unit != null) {
            C0295b scheduled = new C0295b(this.b, io.reactivex.plugins.a.t(run));
            Message message = Message.obtain(this.b, scheduled);
            if (this.c) {
                message.setAsynchronous(true);
            }
            this.b.sendMessageDelayed(message, unit.toMillis(delay));
            return scheduled;
        } else {
            throw new NullPointerException("unit == null");
        }
    }

    public r.c a() {
        return new a(this.b, this.c);
    }

    /* compiled from: HandlerScheduler */
    public static final class a extends r.c {
        private final Handler c;
        private final boolean d;
        private volatile boolean f;

        a(Handler handler, boolean async) {
            this.c = handler;
            this.d = async;
        }

        @SuppressLint({"NewApi"})
        public io.reactivex.disposables.b c(Runnable run, long delay, TimeUnit unit) {
            if (run == null) {
                throw new NullPointerException("run == null");
            } else if (unit == null) {
                throw new NullPointerException("unit == null");
            } else if (this.f) {
                return c.a();
            } else {
                C0295b scheduled = new C0295b(this.c, io.reactivex.plugins.a.t(run));
                Message message = Message.obtain(this.c, scheduled);
                message.obj = this;
                if (this.d) {
                    message.setAsynchronous(true);
                }
                this.c.sendMessageDelayed(message, unit.toMillis(delay));
                if (!this.f) {
                    return scheduled;
                }
                this.c.removeCallbacks(scheduled);
                return c.a();
            }
        }

        public void dispose() {
            this.f = true;
            this.c.removeCallbacksAndMessages(this);
        }

        public boolean isDisposed() {
            return this.f;
        }
    }

    /* renamed from: io.reactivex.android.schedulers.b$b  reason: collision with other inner class name */
    /* compiled from: HandlerScheduler */
    public static final class C0295b implements Runnable, io.reactivex.disposables.b {
        private final Handler c;
        private final Runnable d;
        private volatile boolean f;

        C0295b(Handler handler, Runnable delegate) {
            this.c = handler;
            this.d = delegate;
        }

        public void run() {
            try {
                this.d.run();
            } catch (Throwable t) {
                io.reactivex.plugins.a.q(t);
            }
        }

        public void dispose() {
            this.c.removeCallbacks(this);
            this.f = true;
        }

        public boolean isDisposed() {
            return this.f;
        }
    }
}
