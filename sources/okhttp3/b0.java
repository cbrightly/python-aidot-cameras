package okhttp3;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.text.w;
import okhttp3.internal.b;
import okhttp3.internal.http.f;
import okhttp3.u;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: Request.kt */
public final class b0 {
    private d a;
    @NotNull
    private final v b;
    @NotNull
    private final String c;
    @NotNull
    private final u d;
    @Nullable
    private final c0 e;
    @NotNull
    private final Map<Class<?>, Object> f;

    public b0(@NotNull v url, @NotNull String method, @NotNull u headers, @Nullable c0 body, @NotNull Map<Class<?>, ? extends Object> tags) {
        k.f(url, "url");
        k.f(method, FirebaseAnalytics.Param.METHOD);
        k.f(headers, "headers");
        k.f(tags, "tags");
        this.b = url;
        this.c = method;
        this.d = headers;
        this.e = body;
        this.f = tags;
    }

    @NotNull
    public final v l() {
        return this.b;
    }

    @NotNull
    public final String h() {
        return this.c;
    }

    @NotNull
    public final u f() {
        return this.d;
    }

    @Nullable
    public final c0 a() {
        return this.e;
    }

    @NotNull
    public final Map<Class<?>, Object> c() {
        return this.f;
    }

    public final boolean g() {
        return this.b.k();
    }

    @Nullable
    public final String d(@NotNull String name) {
        k.f(name, "name");
        return this.d.a(name);
    }

    @NotNull
    public final List<String> e(@NotNull String name) {
        k.f(name, "name");
        return this.d.i(name);
    }

    @Nullable
    public final Object j() {
        return k(Object.class);
    }

    @Nullable
    public final <T> T k(@NotNull Class<? extends T> type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        return type.cast(this.f.get(type));
    }

    @NotNull
    public final a i() {
        return new a(this);
    }

    @NotNull
    public final d b() {
        d result = this.a;
        if (result != null) {
            return result;
        }
        d result2 = d.c.b(this.d);
        this.a = result2;
        return result2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        $this$buildString.append("Request{method=");
        $this$buildString.append(this.c);
        $this$buildString.append(", url=");
        $this$buildString.append(this.b);
        if (this.d.size() != 0) {
            $this$buildString.append(", headers=[");
            int index = 0;
            for (Object item$iv : this.d) {
                int index$iv = index + 1;
                if (index < 0) {
                    q.q();
                }
                n $dstr$name$value = (n) item$iv;
                String name = (String) $dstr$name$value.component1();
                String value = (String) $dstr$name$value.component2();
                if (index > 0) {
                    $this$buildString.append(", ");
                }
                $this$buildString.append(name);
                $this$buildString.append(':');
                $this$buildString.append(value);
                index = index$iv;
            }
            $this$buildString.append(']');
        }
        if (!this.f.isEmpty()) {
            $this$buildString.append(", tags=");
            $this$buildString.append(this.f);
        }
        $this$buildString.append('}');
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    /* compiled from: Request.kt */
    public static class a {
        @Nullable
        private v a;
        @NotNull
        private String b;
        @NotNull
        private u.a c;
        @Nullable
        private c0 d;
        @NotNull
        private Map<Class<?>, Object> e;

        public a() {
            this.e = new LinkedHashMap();
            this.b = Constants.GET;
            this.c = new u.a();
        }

        public a(@NotNull b0 request) {
            Map<Class<?>, Object> map;
            k.f(request, Progress.REQUEST);
            this.e = new LinkedHashMap();
            this.a = request.l();
            this.b = request.h();
            this.d = request.a();
            if (request.c().isEmpty()) {
                map = new LinkedHashMap<>();
            } else {
                map = l0.u(request.c());
            }
            this.e = map;
            this.c = request.f().f();
        }

        @NotNull
        public a q(@NotNull v url) {
            k.f(url, "url");
            this.a = url;
            return this;
        }

        @NotNull
        public a p(@NotNull String url) {
            String finalUrl;
            k.f(url, "url");
            if (w.L(url, "ws:", true)) {
                StringBuilder sb = new StringBuilder();
                sb.append("http:");
                String substring = url.substring(3);
                k.b(substring, "(this as java.lang.String).substring(startIndex)");
                sb.append(substring);
                finalUrl = sb.toString();
            } else if (w.L(url, "wss:", true)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("https:");
                String substring2 = url.substring(4);
                k.b(substring2, "(this as java.lang.String).substring(startIndex)");
                sb2.append(substring2);
                finalUrl = sb2.toString();
            } else {
                finalUrl = url;
            }
            return q(v.b.d(finalUrl));
        }

        @NotNull
        public a g(@NotNull String name, @NotNull String value) {
            k.f(name, "name");
            k.f(value, "value");
            this.c.j(name, value);
            return this;
        }

        @NotNull
        public a a(@NotNull String name, @NotNull String value) {
            k.f(name, "name");
            k.f(value, "value");
            this.c.a(name, value);
            return this;
        }

        @NotNull
        public a m(@NotNull String name) {
            k.f(name, "name");
            this.c.i(name);
            return this;
        }

        @NotNull
        public a h(@NotNull u headers) {
            k.f(headers, "headers");
            this.c = headers.f();
            return this;
        }

        @NotNull
        public a c(@NotNull d cacheControl) {
            k.f(cacheControl, "cacheControl");
            String value = cacheControl.toString();
            if (value.length() == 0) {
                return m(HttpHeaders.HEAD_KEY_CACHE_CONTROL);
            }
            return g(HttpHeaders.HEAD_KEY_CACHE_CONTROL, value);
        }

        @NotNull
        public a e() {
            return i(Constants.GET, (c0) null);
        }

        @NotNull
        public a f() {
            return i(Constants.HEAD, (c0) null);
        }

        @NotNull
        public a k(@NotNull c0 body) {
            k.f(body, "body");
            return i(Constants.POST, body);
        }

        @NotNull
        public a d(@Nullable c0 body) {
            return i("DELETE", body);
        }

        @NotNull
        public a l(@NotNull c0 body) {
            k.f(body, "body");
            return i("PUT", body);
        }

        @NotNull
        public a j(@NotNull c0 body) {
            k.f(body, "body");
            return i("PATCH", body);
        }

        @NotNull
        public a i(@NotNull String method, @Nullable c0 body) {
            k.f(method, FirebaseAnalytics.Param.METHOD);
            if (method.length() > 0) {
                if (body == null) {
                    if (!(true ^ f.e(method))) {
                        throw new IllegalArgumentException(("method " + method + " must have a request body.").toString());
                    }
                } else if (!f.b(method)) {
                    throw new IllegalArgumentException(("method " + method + " must not have a request body.").toString());
                }
                this.b = method;
                this.d = body;
                return this;
            }
            throw new IllegalArgumentException("method.isEmpty() == true".toString());
        }

        @NotNull
        public a o(@Nullable Object tag) {
            return n(Object.class, tag);
        }

        @NotNull
        public <T> a n(@NotNull Class<? super T> type, @Nullable T tag) {
            k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
            if (tag == null) {
                this.e.remove(type);
            } else {
                if (this.e.isEmpty()) {
                    this.e = new LinkedHashMap();
                }
                Map<Class<?>, Object> map = this.e;
                Object cast = type.cast(tag);
                if (cast == null) {
                    k.n();
                }
                map.put(type, cast);
            }
            return this;
        }

        @NotNull
        public b0 b() {
            v vVar = this.a;
            if (vVar != null) {
                return new b0(vVar, this.b, this.c.f(), this.d, b.S(this.e));
            }
            throw new IllegalStateException("url == null".toString());
        }
    }
}
