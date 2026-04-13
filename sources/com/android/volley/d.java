package com.android.volley;

import android.os.Handler;
import java.util.concurrent.Executor;

/* compiled from: ExecutorDelivery */
public class d implements l {
    private final Executor a;

    public d(Handler handler) {
        this.a = new a(handler);
    }

    /* compiled from: ExecutorDelivery */
    public class a implements Executor {
        final /* synthetic */ Handler c;

        a(Handler handler) {
            this.c = handler;
        }

        public void execute(Runnable command) {
            this.c.post(command);
        }
    }

    public void a(i<?> request, k<?> response) {
        b(request, response, (Runnable) null);
    }

    public void b(i<?> request, k<?> response, Runnable runnable) {
        request.markDelivered();
        request.addMarker("post-response");
        this.a.execute(new b(request, response, runnable));
    }

    public void c(i<?> request, VolleyError error) {
        request.addMarker("post-error");
        this.a.execute(new b(request, k.a(error), (Runnable) null));
    }

    /* compiled from: ExecutorDelivery */
    public static class b implements Runnable {
        private final i c;
        private final k d;
        private final Runnable f;

        public b(i request, k response, Runnable runnable) {
            this.c = request;
            this.d = response;
            this.f = runnable;
        }

        public void run() {
            if (this.c.isCanceled()) {
                this.c.finish("canceled-at-delivery");
                return;
            }
            if (this.d.b()) {
                this.c.deliverResponse(this.d.a);
            } else {
                this.c.deliverError(this.d.c);
            }
            if (this.d.d) {
                this.c.addMarker("intermediate-response");
            } else {
                this.c.finish("done");
            }
            Runnable runnable = this.f;
            if (runnable != null) {
                runnable.run();
            }
        }
    }
}
