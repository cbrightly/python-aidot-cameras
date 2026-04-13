package okhttp3.internal.http;

import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.List;
import kotlin.jvm.internal.k;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.internal.connection.c;
import okhttp3.internal.connection.e;
import okhttp3.j;
import okhttp3.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealInterceptorChain.kt */
public final class g implements w.a {
    private int a;
    @NotNull
    private final e b;
    private final List<w> c;
    private final int d;
    @Nullable
    private final c e;
    @NotNull
    private final b0 f;
    private final int g;
    private final int h;
    private final int i;

    public g(@NotNull e call, @NotNull List<? extends w> interceptors, int index, @Nullable c exchange, @NotNull b0 request, int connectTimeoutMillis, int readTimeoutMillis, int writeTimeoutMillis) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(interceptors, "interceptors");
        k.f(request, Progress.REQUEST);
        this.b = call;
        this.c = interceptors;
        this.d = index;
        this.e = exchange;
        this.f = request;
        this.g = connectTimeoutMillis;
        this.h = readTimeoutMillis;
        this.i = writeTimeoutMillis;
    }

    @NotNull
    public final e e() {
        return this.b;
    }

    @Nullable
    public final c h() {
        return this.e;
    }

    @NotNull
    public final b0 j() {
        return this.f;
    }

    public final int f() {
        return this.g;
    }

    public final int i() {
        return this.h;
    }

    public final int k() {
        return this.i;
    }

    public static /* synthetic */ g d(g gVar, int i2, c cVar, b0 b0Var, int i3, int i4, int i5, int i6, Object obj) {
        c cVar2;
        b0 b0Var2;
        int i7;
        int i8;
        int i9;
        if ((i6 & 1) != 0) {
            i2 = gVar.d;
        }
        if ((i6 & 2) != 0) {
            cVar2 = gVar.e;
        } else {
            cVar2 = cVar;
        }
        if ((i6 & 4) != 0) {
            b0Var2 = gVar.f;
        } else {
            b0Var2 = b0Var;
        }
        if ((i6 & 8) != 0) {
            i7 = gVar.g;
        } else {
            i7 = i3;
        }
        if ((i6 & 16) != 0) {
            i8 = gVar.h;
        } else {
            i8 = i4;
        }
        if ((i6 & 32) != 0) {
            i9 = gVar.i;
        } else {
            i9 = i5;
        }
        return gVar.c(i2, cVar2, b0Var2, i7, i8, i9);
    }

    @NotNull
    public final g c(int index, @Nullable c exchange, @NotNull b0 request, int connectTimeoutMillis, int readTimeoutMillis, int writeTimeoutMillis) {
        b0 b0Var = request;
        k.f(request, Progress.REQUEST);
        return new g(this.b, this.c, index, exchange, request, connectTimeoutMillis, readTimeoutMillis, writeTimeoutMillis);
    }

    @Nullable
    public j b() {
        c cVar = this.e;
        if (cVar != null) {
            return cVar.h();
        }
        return null;
    }

    public int l() {
        return this.h;
    }

    @NotNull
    public okhttp3.e call() {
        return this.b;
    }

    @NotNull
    public b0 g() {
        return this.f;
    }

    @NotNull
    public d0 a(@NotNull b0 request) {
        k.f(request, Progress.REQUEST);
        boolean z = false;
        if (this.d < this.c.size()) {
            this.a++;
            c cVar = this.e;
            if (cVar != null) {
                if (cVar.j().g(request.l())) {
                    if (!(this.a == 1)) {
                        throw new IllegalStateException(("network interceptor " + this.c.get(this.d - 1) + " must call proceed() exactly once").toString());
                    }
                } else {
                    throw new IllegalStateException(("network interceptor " + this.c.get(this.d - 1) + " must retain the same host and port").toString());
                }
            }
            g next = d(this, this.d + 1, (c) null, request, 0, 0, 0, 58, (Object) null);
            w interceptor = this.c.get(this.d);
            d0 response = interceptor.intercept(next);
            if (response != null) {
                if (this.e != null) {
                    if (!(this.d + 1 >= this.c.size() || next.a == 1)) {
                        throw new IllegalStateException(("network interceptor " + interceptor + " must call proceed() exactly once").toString());
                    }
                }
                if (response.a() != null) {
                    z = true;
                }
                if (z) {
                    return response;
                }
                throw new IllegalStateException(("interceptor " + interceptor + " returned a response with no body").toString());
            }
            throw new NullPointerException("interceptor " + interceptor + " returned null");
        }
        throw new IllegalStateException("Check failed.".toString());
    }
}
