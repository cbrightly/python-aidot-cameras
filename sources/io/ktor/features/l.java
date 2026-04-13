package io.ktor.features;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.w2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.d;

/* compiled from: CallLogging.kt */
public final class l implements w2<Map<String, ? extends String>> {
    private final Map<String, String> c;

    public l(@NotNull Map<String, String> mdc) {
        k.f(mdc, "mdc");
        this.c = l0.j(b(), mdc);
    }

    public <R> R fold(R initial, @NotNull p<? super R, ? super g.b, ? extends R> operation) {
        k.f(operation, "operation");
        return w2.a.a(this, initial, operation);
    }

    @Nullable
    public <E extends g.b> E get(@NotNull g.c<E> key) {
        k.f(key, CacheEntity.KEY);
        return w2.a.b(this, key);
    }

    @NotNull
    public g minusKey(@NotNull g.c<?> key) {
        k.f(key, CacheEntity.KEY);
        return w2.a.c(this, key);
    }

    @NotNull
    public g plus(@NotNull g context) {
        k.f(context, "context");
        return w2.a.d(this, context);
    }

    @NotNull
    public g.c<?> getKey() {
        return a.c;
    }

    /* renamed from: e */
    public void v(@NotNull g context, @NotNull Map<String, String> oldState) {
        k.f(context, "context");
        k.f(oldState, "oldState");
        d(oldState);
    }

    @NotNull
    /* renamed from: f */
    public Map<String, String> P(@NotNull g context) {
        k.f(context, "context");
        Map mdcCopy = b();
        d(this.c);
        return mdcCopy;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = kotlin.collections.l0.q(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.Map<java.lang.String, java.lang.String> b() {
        /*
            r1 = this;
            java.util.Map r0 = org.slf4j.d.c()
            if (r0 == 0) goto L_0x000d
            java.util.Map r0 = kotlin.collections.l0.q(r0)
            if (r0 == 0) goto L_0x000d
            goto L_0x0011
        L_0x000d:
            java.util.Map r0 = kotlin.collections.l0.f()
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.features.l.b():java.util.Map");
    }

    private final void d(Map<String, String> oldState) {
        d.b();
        for (Map.Entry $dstr$k$v : oldState.entrySet()) {
            d.d((String) $dstr$k$v.getKey(), (String) $dstr$k$v.getValue());
        }
    }

    /* compiled from: CallLogging.kt */
    public static final class a implements g.c<l> {
        public static final a c = new a();

        private a() {
        }
    }
}
