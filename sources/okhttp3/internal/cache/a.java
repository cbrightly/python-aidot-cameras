package okhttp3.internal.cache;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.a0;
import okhttp3.b0;
import okhttp3.c;
import okhttp3.d0;
import okhttp3.e;
import okhttp3.e0;
import okhttp3.internal.cache.c;
import okhttp3.internal.http.f;
import okhttp3.internal.http.h;
import okhttp3.r;
import okhttp3.u;
import okhttp3.w;
import okio.d;
import okio.f0;
import okio.g;
import okio.p;
import org.glassfish.grizzly.http.server.Constants;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CacheInterceptor.kt */
public final class a implements w {
    public static final C0460a b = new C0460a((DefaultConstructorMarker) null);
    @Nullable
    private final c c;

    public a(@Nullable c cache) {
        this.c = cache;
    }

    @NotNull
    public d0 intercept(@NotNull w.a chain) {
        r listener;
        e0 a;
        e0 a2;
        k.f(chain, "chain");
        e call = chain.call();
        c cVar = this.c;
        d0 cacheCandidate = cVar != null ? cVar.c(chain.g()) : null;
        c strategy = new c.b(System.currentTimeMillis(), chain.g(), cacheCandidate).b();
        b0 networkRequest = strategy.b();
        d0 cacheResponse = strategy.a();
        okhttp3.c cVar2 = this.c;
        if (cVar2 != null) {
            cVar2.r(strategy);
        }
        okhttp3.internal.connection.e eVar = (okhttp3.internal.connection.e) (!(call instanceof okhttp3.internal.connection.e) ? null : call);
        if (eVar == null || (listener = eVar.m()) == null) {
            listener = r.a;
        }
        if (!(cacheCandidate == null || cacheResponse != null || (a2 = cacheCandidate.a()) == null)) {
            okhttp3.internal.b.j(a2);
        }
        if (networkRequest == null && cacheResponse == null) {
            d0 it = new d0.a().r(chain.g()).p(a0.HTTP_1_1).g(TypedValues.PositionType.TYPE_PERCENT_HEIGHT).m("Unsatisfiable Request (only-if-cached)").b(okhttp3.internal.b.c).s(-1).q(System.currentTimeMillis()).c();
            listener.A(call, it);
            return it;
        } else if (networkRequest == null) {
            if (cacheResponse == null) {
                k.n();
            }
            d0 it2 = cacheResponse.v().d(b.f(cacheResponse)).c();
            listener.b(call, it2);
            return it2;
        } else {
            if (cacheResponse != null) {
                listener.a(call, cacheResponse);
            } else if (this.c != null) {
                listener.c(call);
            }
            try {
                d0 networkResponse = chain.a(networkRequest);
                if (!(networkResponse != null || cacheCandidate == null || a == null)) {
                }
                if (cacheResponse != null) {
                    if (networkResponse == null || networkResponse.j() != 304) {
                        e0 a3 = cacheResponse.a();
                        if (a3 != null) {
                            okhttp3.internal.b.j(a3);
                        }
                    } else {
                        d0.a v = cacheResponse.v();
                        C0460a aVar = b;
                        d0 response = v.k(aVar.c(cacheResponse.s(), networkResponse.s())).s(networkResponse.P()).q(networkResponse.I()).d(aVar.f(cacheResponse)).n(aVar.f(networkResponse)).c();
                        e0 a4 = networkResponse.a();
                        if (a4 == null) {
                            k.n();
                        }
                        a4.close();
                        okhttp3.c cVar3 = this.c;
                        if (cVar3 == null) {
                            k.n();
                        }
                        cVar3.o();
                        this.c.s(cacheResponse, response);
                        listener.b(call, response);
                        return response;
                    }
                }
                if (networkResponse == null) {
                    k.n();
                }
                d0.a v2 = networkResponse.v();
                C0460a aVar2 = b;
                d0 response2 = v2.d(aVar2.f(cacheResponse)).n(aVar2.f(networkResponse)).c();
                if (this.c != null) {
                    if (okhttp3.internal.http.e.c(response2) && c.a.a(response2, networkRequest)) {
                        d0 a5 = a(this.c.j(response2), response2);
                        d0 d0Var = a5;
                        if (cacheResponse != null) {
                            listener.c(call);
                        }
                        return a5;
                    } else if (f.a.a(networkRequest.h())) {
                        try {
                            this.c.l(networkRequest);
                        } catch (IOException e) {
                        }
                    }
                }
                return response2;
            } finally {
                if (!(cacheCandidate == null || (a = cacheCandidate.a()) == null)) {
                    okhttp3.internal.b.j(a);
                }
            }
        }
    }

    private final d0 a(b cacheRequest, d0 response) {
        if (cacheRequest == null) {
            return response;
        }
        okio.b0 cacheBodyUnbuffered = cacheRequest.body();
        e0 a = response.a();
        if (a == null) {
            k.n();
        }
        b cacheWritingSource = new b(a.source(), cacheRequest, p.c(cacheBodyUnbuffered));
        return response.v().b(new h(d0.r(response, "Content-Type", (String) null, 2, (Object) null), response.a().contentLength(), p.d(cacheWritingSource))).c();
    }

    /* compiled from: CacheInterceptor.kt */
    public static final class b implements okio.e0 {
        private boolean c;
        final /* synthetic */ okio.e d;
        final /* synthetic */ b f;
        final /* synthetic */ d q;

        public /* synthetic */ g cursor() {
            return okio.d0.a(this);
        }

        b(okio.e $captured_local_variable$0, b $captured_local_variable$1, d $captured_local_variable$2) {
            this.d = $captured_local_variable$0;
            this.f = $captured_local_variable$1;
            this.q = $captured_local_variable$2;
        }

        public long read(@NotNull okio.c sink, long byteCount) {
            k.f(sink, "sink");
            try {
                long bytesRead = this.d.read(sink, byteCount);
                if (bytesRead == -1) {
                    if (!this.c) {
                        this.c = true;
                        this.q.close();
                    }
                    return -1;
                }
                sink.j(this.q.getBuffer(), sink.e1() - bytesRead, bytesRead);
                this.q.emitCompleteSegments();
                return bytesRead;
            } catch (IOException e) {
                if (!this.c) {
                    this.c = true;
                    this.f.b();
                }
                throw e;
            }
        }

        @NotNull
        public f0 timeout() {
            return this.d.timeout();
        }

        public void close() {
            if (!this.c && !okhttp3.internal.b.p(this, 100, TimeUnit.MILLISECONDS)) {
                this.c = true;
                this.f.b();
            }
            this.d.close();
        }
    }

    /* renamed from: okhttp3.internal.cache.a$a  reason: collision with other inner class name */
    /* compiled from: CacheInterceptor.kt */
    public static final class C0460a {
        private C0460a() {
        }

        public /* synthetic */ C0460a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* access modifiers changed from: private */
        public final d0 f(d0 response) {
            if ((response != null ? response.a() : null) != null) {
                return response.v().b((e0) null).c();
            }
            return response;
        }

        /* access modifiers changed from: private */
        public final u c(u cachedHeaders, u networkHeaders) {
            u.a result = new u.a();
            int size = cachedHeaders.size();
            for (int index = 0; index < size; index++) {
                String fieldName = cachedHeaders.b(index);
                String value = cachedHeaders.h(index);
                if ((!kotlin.text.w.y("Warning", fieldName, true) || !kotlin.text.w.N(value, "1", false, 2, (Object) null)) && (d(fieldName) || !e(fieldName) || networkHeaders.a(fieldName) == null)) {
                    result.d(fieldName, value);
                }
            }
            int size2 = networkHeaders.size();
            for (int index2 = 0; index2 < size2; index2++) {
                String fieldName2 = networkHeaders.b(index2);
                if (!d(fieldName2) && e(fieldName2)) {
                    result.d(fieldName2, networkHeaders.h(index2));
                }
            }
            return result.f();
        }

        private final boolean e(String fieldName) {
            if (kotlin.text.w.y("Connection", fieldName, true) || kotlin.text.w.y("Keep-Alive", fieldName, true) || kotlin.text.w.y("Proxy-Authenticate", fieldName, true) || kotlin.text.w.y("Proxy-Authorization", fieldName, true) || kotlin.text.w.y("TE", fieldName, true) || kotlin.text.w.y("Trailers", fieldName, true) || kotlin.text.w.y(Constants.TRANSFERENCODING, fieldName, true) || kotlin.text.w.y(UpgradeRequest.UPGRADE, fieldName, true)) {
                return false;
            }
            return true;
        }

        private final boolean d(String fieldName) {
            if (kotlin.text.w.y("Content-Length", fieldName, true) || kotlin.text.w.y(HttpHeaders.HEAD_KEY_CONTENT_ENCODING, fieldName, true) || kotlin.text.w.y("Content-Type", fieldName, true)) {
                return true;
            }
            return false;
        }
    }
}
