package zendesk.okhttp;

import java.text.Normalizer;
import java.util.Set;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.text.j;
import kotlin.x;
import kotlinx.coroutines.i;
import kotlinx.coroutines.o0;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HeaderInterceptor.kt */
public final class a implements w {
    @NotNull
    private final Set<n<String, l<d<? super String>, Object>>> b;

    public a(@NotNull Set<? extends n<String, ? extends l<? super d<? super String>, ? extends Object>>> headers) {
        k.e(headers, "headers");
        this.b = headers;
    }

    @NotNull
    public d0 intercept(@NotNull w.a chain) {
        k.e(chain, "chain");
        b0.a newRequest = chain.g().i();
        for (n next : this.b) {
            String headerName = (String) next.component1();
            String headerValue = null;
            String p0 = (String) i.b((g) null, new C0553a((l) next.component2(), (d<? super C0553a>) null), 1, (Object) null);
            if (p0 != null && (true ^ kotlin.text.w.A(p0))) {
                headerValue = p0;
            }
            if (headerValue != null) {
                newRequest.a(headerName, a(headerValue));
            }
        }
        return chain.a(newRequest.b());
    }

    @f(c = "zendesk.okhttp.HeaderInterceptor$intercept$headerValue$1", f = "HeaderInterceptor.kt", l = {30}, m = "invokeSuspend")
    /* renamed from: zendesk.okhttp.a$a  reason: collision with other inner class name */
    /* compiled from: HeaderInterceptor.kt */
    public static final class C0553a extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super String>, Object> {
        final /* synthetic */ l<d<? super String>, Object> $headerValueProvider;
        int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0553a(l<? super d<? super String>, ? extends Object> lVar, d<? super C0553a> dVar) {
            super(2, dVar);
            this.$headerValueProvider = lVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new C0553a(this.$headerValueProvider, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super String> dVar) {
            return ((C0553a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    l<d<? super String>, Object> lVar = this.$headerValueProvider;
                    this.label = 1;
                    Object invoke = lVar.invoke(this);
                    if (invoke == d) {
                        return d;
                    }
                    return invoke;
                case 1:
                    kotlin.p.b($result);
                    return $result;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    private final String a(String headerValue) {
        String normalize = Normalizer.normalize(headerValue, Normalizer.Form.NFD);
        k.d(normalize, "normalize(headerValue, Normalizer.Form.NFD)");
        return new j("[^\\p{ASCII}]").replace((CharSequence) normalize, "");
    }
}
