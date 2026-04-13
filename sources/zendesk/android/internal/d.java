package zendesk.android.internal;

import android.content.Context;
import com.squareup.moshi.r;
import java.io.File;
import kotlinx.coroutines.o0;
import okhttp3.z;
import retrofit2.t;
import zendesk.android.internal.k;
import zendesk.android.internal.network.e;
import zendesk.android.internal.network.h;
import zendesk.android.internal.network.i;
import zendesk.android.internal.network.j;
import zendesk.android.settings.internal.f;
import zendesk.android.settings.internal.g;

/* compiled from: DaggerZendeskComponent */
public final class d implements f {
    private final d a;
    /* access modifiers changed from: private */
    public javax.inject.a<g> b;
    /* access modifiers changed from: private */
    public javax.inject.a<Context> c;
    /* access modifiers changed from: private */
    public javax.inject.a<zendesk.android.internal.network.b> d;
    private javax.inject.a<zendesk.android.internal.network.a> e;
    private javax.inject.a<File> f;
    private javax.inject.a<z> g;
    private javax.inject.a<r> h;
    private javax.inject.a<retrofit2.converter.moshi.a> i;
    /* access modifiers changed from: private */
    public javax.inject.a<t> j;
    private javax.inject.a<zendesk.android.settings.internal.a> k;
    private javax.inject.a<g> l;
    private javax.inject.a<f> m;
    /* access modifiers changed from: private */
    public javax.inject.a<zendesk.android.events.internal.a> n;
    /* access modifiers changed from: private */
    public javax.inject.a<o0> o;
    /* access modifiers changed from: private */
    public javax.inject.a<h> p;

    private d(p zendeskModuleParam, zendesk.android.internal.network.c networkModuleParam, zendesk.android.settings.internal.b settingsModuleParam) {
        this.a = this;
        m(zendeskModuleParam, networkModuleParam, settingsModuleParam);
    }

    public static b l() {
        return new b();
    }

    private void m(p zendeskModuleParam, zendesk.android.internal.network.c networkModuleParam, zendesk.android.settings.internal.b settingsModuleParam) {
        this.b = dagger.internal.c.a(q.b(zendeskModuleParam));
        javax.inject.a<Context> a2 = dagger.internal.c.a(r.b(zendeskModuleParam));
        this.c = a2;
        javax.inject.a<zendesk.android.internal.network.b> a3 = dagger.internal.c.a(h.a(networkModuleParam, this.b, a2));
        this.d = a3;
        this.e = dagger.internal.a.a(e.a(networkModuleParam, this.b, a3));
        javax.inject.a<File> a4 = dagger.internal.a.a(zendesk.android.internal.network.d.b(networkModuleParam, this.c));
        this.f = a4;
        this.g = dagger.internal.a.a(i.a(networkModuleParam, this.e, a4));
        javax.inject.a<r> a5 = dagger.internal.a.a(zendesk.android.internal.network.g.a(networkModuleParam));
        this.h = a5;
        javax.inject.a<retrofit2.converter.moshi.a> a6 = dagger.internal.a.a(zendesk.android.internal.network.f.a(networkModuleParam, a5));
        this.i = a6;
        javax.inject.a<t> a7 = dagger.internal.a.a(j.a(networkModuleParam, this.b, this.g, a6));
        this.j = a7;
        javax.inject.a<zendesk.android.settings.internal.a> a8 = dagger.internal.a.a(zendesk.android.settings.internal.c.a(settingsModuleParam, a7));
        this.k = a8;
        javax.inject.a<g> a9 = dagger.internal.a.a(zendesk.android.settings.internal.e.a(settingsModuleParam, a8, this.h, this.b));
        this.l = a9;
        this.m = dagger.internal.a.a(zendesk.android.settings.internal.d.a(settingsModuleParam, a9));
        this.n = dagger.internal.a.a(u.a(zendeskModuleParam));
        this.o = dagger.internal.c.a(s.a(zendeskModuleParam));
        this.p = dagger.internal.a.a(t.a(zendeskModuleParam));
    }

    public k.a a() {
        return new c();
    }

    public f b() {
        return this.m.get();
    }

    public zendesk.android.events.internal.a d() {
        return this.n.get();
    }

    public o0 c() {
        return this.o.get();
    }

    /* compiled from: DaggerZendeskComponent */
    public static final class b {
        private p a;
        private zendesk.android.internal.network.c b;
        private zendesk.android.settings.internal.b c;

        private b() {
        }

        public b b(p zendeskModule) {
            this.a = (p) dagger.internal.b.b(zendeskModule);
            return this;
        }

        public f a() {
            dagger.internal.b.a(this.a, p.class);
            if (this.b == null) {
                this.b = new zendesk.android.internal.network.c();
            }
            if (this.c == null) {
                this.c = new zendesk.android.settings.internal.b();
            }
            return new d(this.a, this.b, this.c);
        }
    }

    /* compiled from: DaggerZendeskComponent */
    public static final class c implements k.a {
        private final d a;
        private l b;

        private c(d zendeskComponent) {
            this.a = zendeskComponent;
        }

        /* renamed from: b */
        public c a(l module) {
            this.b = (l) dagger.internal.b.b(module);
            return this;
        }

        public k build() {
            dagger.internal.b.a(this.b, l.class);
            return new C0501d(this.b, new zendesk.android.pageviewevents.internal.c());
        }
    }

    /* renamed from: zendesk.android.internal.d$d  reason: collision with other inner class name */
    /* compiled from: DaggerZendeskComponent */
    public static final class C0501d implements k {
        private final d a;
        private final C0501d b;
        private javax.inject.a<zendesk.conversationkit.android.b> c;
        private javax.inject.a<zendesk.android.pageviewevents.internal.h> d;
        private javax.inject.a<zendesk.android.pageviewevents.internal.a> e;
        private javax.inject.a<zendesk.android.pageviewevents.internal.b> f;
        private javax.inject.a<zendesk.android.pageviewevents.c> g;
        private javax.inject.a<zendesk.android.c> h;

        private C0501d(d zendeskComponent, l zendeskInitializedModuleParam, zendesk.android.pageviewevents.internal.c pageViewModuleParam) {
            this.b = this;
            this.a = zendeskComponent;
            b(zendeskInitializedModuleParam, pageViewModuleParam);
        }

        private void b(l zendeskInitializedModuleParam, zendesk.android.pageviewevents.internal.c pageViewModuleParam) {
            this.c = dagger.internal.c.a(m.b(zendeskInitializedModuleParam));
            this.d = dagger.internal.a.a(zendesk.android.pageviewevents.internal.g.a(pageViewModuleParam, this.a.c, this.a.p));
            this.e = dagger.internal.a.a(zendesk.android.pageviewevents.internal.d.a(pageViewModuleParam, this.a.j));
            javax.inject.a<zendesk.android.pageviewevents.internal.b> a2 = dagger.internal.a.a(zendesk.android.pageviewevents.internal.f.a(pageViewModuleParam, this.a.b, this.c, this.d, this.e, this.a.d));
            this.f = a2;
            this.g = dagger.internal.a.a(zendesk.android.pageviewevents.internal.e.a(pageViewModuleParam, a2, this.a.p));
            this.h = dagger.internal.a.a(n.a(zendeskInitializedModuleParam, this.a.o, this.a.n, this.g));
        }

        public zendesk.android.c a() {
            return this.h.get();
        }
    }
}
