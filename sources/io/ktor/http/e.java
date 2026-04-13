package io.ktor.http;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import io.netty.util.internal.StringUtil;
import java.util.Map;
import java.util.Set;
import kotlin.collections.l0;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.n;
import kotlin.sequences.o;
import kotlin.t;
import kotlin.text.f;
import kotlin.text.h;
import kotlin.text.j;
import kotlin.text.w;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Cookie.kt */
public final class e {
    private static final Set<String> a = o0.g("max-age", "expires", SerializableCookie.DOMAIN, "path", "secure", "httponly", "$x-enc");
    private static final j b = new j("(^|;)\\s*([^()<>@;:/\\\\\"\\[\\]\\?=\\{\\}\\s]+)\\s*(=\\s*(\"[^\"]*\"|[^;]*))?");
    private static final Set<Character> c = o0.g(';', Character.valueOf(StringUtil.COMMA), Character.valueOf(StringUtil.DOUBLE_QUOTE));

    public static /* synthetic */ Map b(String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return a(str, z);
    }

    /* compiled from: Cookie.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<h, n<? extends String, ? extends String>> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final n<String, String> invoke(@NotNull h it) {
            String str;
            String a;
            k.f(it, "it");
            f fVar = it.d().get(2);
            String str2 = "";
            if (fVar == null || (str = fVar.a()) == null) {
                str = str2;
            }
            f fVar2 = it.d().get(4);
            if (!(fVar2 == null || (a = fVar2.a()) == null)) {
                str2 = a;
            }
            return t.a(str, str2);
        }
    }

    @NotNull
    public static final Map<String, String> a(@NotNull String cookiesHeader, boolean skipEscaped) {
        k.f(cookiesHeader, "cookiesHeader");
        return l0.r(o.w(o.o(o.w(j.findAll$default(b, cookiesHeader, 0, 2, (Object) null), a.INSTANCE), new b(skipEscaped)), c.INSTANCE));
    }

    /* compiled from: Cookie.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<n<? extends String, ? extends String>, Boolean> {
        final /* synthetic */ boolean $skipEscaped;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(boolean z) {
            super(1);
            this.$skipEscaped = z;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((n<String, String>) (n) obj));
        }

        public final boolean invoke(@NotNull n<String, String> it) {
            k.f(it, "it");
            return !this.$skipEscaped || !w.N(it.getFirst(), "$", false, 2, (Object) null);
        }
    }

    /* compiled from: Cookie.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<n<? extends String, ? extends String>, n<? extends String, ? extends String>> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        @NotNull
        public final n<String, String> invoke(@NotNull n<String, String> it) {
            k.f(it, "it");
            if (!w.N(it.getSecond(), "\"", false, 2, (Object) null) || !w.x(it.getSecond(), "\"", false, 2, (Object) null)) {
                return it;
            }
            return n.copy$default(it, (Object) null, x.y0(it.getSecond(), "\""), 1, (Object) null);
        }
    }
}
