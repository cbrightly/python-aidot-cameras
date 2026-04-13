package io.ktor.request;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import io.ktor.http.e;
import io.ktor.util.collections.b;
import io.ktor.util.s;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: RequestCookies.kt */
public class g {
    private final b<n<?, String>, String> a = new b<>((s) null, (Map) null, 3, (DefaultConstructorMarker) null);
    @NotNull
    private final kotlin.g b = i.b(new a(this));
    @NotNull
    private final d c;

    public g(@NotNull d request) {
        k.f(request, Progress.REQUEST);
        this.c = request;
    }

    /* compiled from: RequestCookies.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Map<String, ? extends String>> {
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(g gVar) {
            super(0);
            this.this$0 = gVar;
        }

        @NotNull
        public final Map<String, String> invoke() {
            return this.this$0.a();
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Map<String, String> a() {
        List<String> cookieHeaders = this.c.getHeaders().c(HttpHeaders.HEAD_KEY_COOKIE);
        if (cookieHeaders == null) {
            return l0.f();
        }
        HashMap map = new HashMap(cookieHeaders.size());
        for (String cookieHeader : cookieHeaders) {
            map.putAll(e.b(cookieHeader, false, 2, (Object) null));
        }
        return map;
    }
}
