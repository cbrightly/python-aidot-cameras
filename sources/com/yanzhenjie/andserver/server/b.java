package com.yanzhenjie.andserver.server;

import android.content.Context;
import com.yanzhenjie.andserver.c;
import com.yanzhenjie.andserver.d;
import com.yanzhenjie.andserver.e;
import com.yanzhenjie.andserver.server.a;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import org.apache.httpcore.protocol.j;

/* compiled from: WebServer */
public class b extends a<C0235b> {
    private Context j;
    private String k;

    public static C0235b e(Context context, String group) {
        return new C0235b(context, group);
    }

    private b(C0235b builder) {
        super(builder);
        this.j = builder.h;
        this.k = builder.i;
    }

    /* access modifiers changed from: protected */
    public j d() {
        c handler = new c(this.j);
        try {
            new com.yanzhenjie.andserver.b(this.j).a(handler, this.k);
            return handler;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    /* renamed from: com.yanzhenjie.andserver.server.b$b  reason: collision with other inner class name */
    /* compiled from: WebServer */
    public static class C0235b extends a.c<C0235b, b> implements e.a<C0235b, b> {
        /* access modifiers changed from: private */
        public Context h;
        /* access modifiers changed from: private */
        public String i;

        public /* bridge */ /* synthetic */ e.a a(int i2, TimeUnit timeUnit) {
            return (e.a) super.j(i2, timeUnit);
        }

        public /* bridge */ /* synthetic */ e.a b(SSLContext sSLContext) {
            return (e.a) super.h(sSLContext);
        }

        public /* bridge */ /* synthetic */ e.a c(d dVar) {
            return (e.a) super.i(dVar);
        }

        public /* bridge */ /* synthetic */ e.a d(e.b bVar) {
            return (e.a) super.f(bVar);
        }

        public /* bridge */ /* synthetic */ e.a e(int i2) {
            return (e.a) super.g(i2);
        }

        private C0235b(Context context, String group) {
            this.h = context;
            this.i = group;
        }

        /* renamed from: m */
        public b build() {
            return new b(this);
        }
    }
}
