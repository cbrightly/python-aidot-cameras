package com.alibaba.android.arouter.core;

import android.content.Context;
import com.alibaba.android.arouter.exception.HandlerException;
import com.alibaba.android.arouter.facade.service.InterceptorService;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class InterceptorServiceImpl implements InterceptorService {
    /* access modifiers changed from: private */
    public static boolean a;
    /* access modifiers changed from: private */
    public static final Object b = new Object();

    public void e(com.alibaba.android.arouter.facade.a postcard, com.alibaba.android.arouter.facade.callback.a callback) {
        if (com.alibaba.android.arouter.utils.c.b(b.e)) {
            k();
            if (!a) {
                callback.b(new HandlerException("Interceptors initialization takes too much time."));
            } else {
                a.b.execute(new a(postcard, callback));
            }
        } else {
            callback.a(postcard);
        }
    }

    public class a implements Runnable {
        final /* synthetic */ com.alibaba.android.arouter.facade.a c;
        final /* synthetic */ com.alibaba.android.arouter.facade.callback.a d;

        a(com.alibaba.android.arouter.facade.a aVar, com.alibaba.android.arouter.facade.callback.a aVar2) {
            this.c = aVar;
            this.d = aVar2;
        }

        public void run() {
            com.alibaba.android.arouter.thread.a interceptorCounter = new com.alibaba.android.arouter.thread.a(b.f.size());
            try {
                InterceptorServiceImpl.a(0, interceptorCounter, this.c);
                interceptorCounter.await((long) this.c.y(), TimeUnit.SECONDS);
                if (interceptorCounter.getCount() > 0) {
                    this.d.b(new HandlerException("The interceptor processing timed out."));
                } else if (this.c.x() != null) {
                    this.d.b((Throwable) this.c.x());
                } else {
                    this.d.a(this.c);
                }
            } catch (Exception e) {
                this.d.b(e);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void a(int index, com.alibaba.android.arouter.thread.a counter, com.alibaba.android.arouter.facade.a postcard) {
        if (index < b.f.size()) {
            b.f.get(index).g(postcard, new b(counter, index, postcard));
        }
    }

    public static final class b implements com.alibaba.android.arouter.facade.callback.a {
        final /* synthetic */ com.alibaba.android.arouter.thread.a a;
        final /* synthetic */ int b;
        final /* synthetic */ com.alibaba.android.arouter.facade.a c;

        b(com.alibaba.android.arouter.thread.a aVar, int i, com.alibaba.android.arouter.facade.a aVar2) {
            this.a = aVar;
            this.b = i;
            this.c = aVar2;
        }

        public void a(com.alibaba.android.arouter.facade.a postcard) {
            this.a.countDown();
            InterceptorServiceImpl.a(this.b + 1, this.a, postcard);
        }

        public void b(Throwable exception) {
            this.c.J(exception == null ? new HandlerException("No message.") : exception);
            this.a.a();
        }
    }

    public class c implements Runnable {
        final /* synthetic */ Context c;

        c(Context context) {
            this.c = context;
        }

        public void run() {
            if (com.alibaba.android.arouter.utils.c.b(b.e)) {
                for (Map.Entry<Integer, Class<? extends IInterceptor>> entry : b.e.entrySet()) {
                    Class<? extends IInterceptor> interceptorClass = entry.getValue();
                    try {
                        IInterceptor iInterceptor = (IInterceptor) interceptorClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                        iInterceptor.init(this.c);
                        b.f.add(iInterceptor);
                    } catch (Exception ex) {
                        throw new HandlerException("ARouter::ARouter init interceptor error! name = [" + interceptorClass.getName() + "], reason = [" + ex.getMessage() + "]");
                    }
                }
                boolean unused = InterceptorServiceImpl.a = true;
                com.alibaba.android.arouter.launcher.a.c.c("ARouter::", "ARouter interceptors init over.");
                synchronized (InterceptorServiceImpl.b) {
                    InterceptorServiceImpl.b.notifyAll();
                }
            }
        }
    }

    public void init(Context context) {
        a.b.execute(new c(context));
    }

    private static void k() {
        synchronized (b) {
            while (!a) {
                try {
                    b.wait(10000);
                } catch (InterruptedException e) {
                    throw new HandlerException("ARouter::Interceptor init cost too much time error! reason = [" + e.getMessage() + "]");
                }
            }
        }
    }
}
