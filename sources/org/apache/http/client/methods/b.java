package org.apache.http.client.methods;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.http.conn.e;
import org.apache.http.conn.g;
import org.apache.http.message.r;
import org.apache.http.o;
import org.apache.http.params.HttpParams;

/* compiled from: AbstractExecutionAwareRequest */
public abstract class b extends org.apache.http.message.a implements g, a, Cloneable, o {
    private final AtomicBoolean f = new AtomicBoolean(false);
    private final AtomicReference<org.apache.http.concurrent.a> q = new AtomicReference<>((Object) null);

    protected b() {
    }

    /* compiled from: AbstractExecutionAwareRequest */
    public class a implements org.apache.http.concurrent.a {
        final /* synthetic */ e c;

        a(e eVar) {
            this.c = eVar;
        }

        public boolean cancel() {
            this.c.a();
            return true;
        }
    }

    @Deprecated
    public void e(e connRequest) {
        p(new a(connRequest));
    }

    /* renamed from: org.apache.http.client.methods.b$b  reason: collision with other inner class name */
    /* compiled from: AbstractExecutionAwareRequest */
    public class C0092b implements org.apache.http.concurrent.a {
        final /* synthetic */ g c;

        C0092b(g gVar) {
            this.c = gVar;
        }

        public boolean cancel() {
            try {
                this.c.c();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

    @Deprecated
    public void d(g releaseTrigger) {
        p(new C0092b(releaseTrigger));
    }

    public void b() {
        org.apache.http.concurrent.a cancellable;
        if (this.f.compareAndSet(false, true) && (cancellable = this.q.getAndSet((Object) null)) != null) {
            cancellable.cancel();
        }
    }

    public boolean n() {
        return this.f.get();
    }

    public void p(org.apache.http.concurrent.a cancellable) {
        if (!this.f.get()) {
            this.q.set(cancellable);
        }
    }

    public Object clone() {
        b clone = (b) super.clone();
        clone.c = (r) org.apache.http.client.utils.a.a(this.c);
        clone.d = (HttpParams) org.apache.http.client.utils.a.a(this.d);
        return clone;
    }
}
