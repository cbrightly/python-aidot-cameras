package io.ktor.http;

import io.ktor.util.v;
import java.util.List;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Headers.kt */
public interface o extends v {
    public static final a a = a.b;

    /* compiled from: Headers.kt */
    public static final class b {
        public static void a(o oVar, @NotNull p<? super String, ? super List<String>, x> pVar) {
            k.f(pVar, "body");
            v.b.a(oVar, pVar);
        }

        @Nullable
        public static String b(o oVar, @NotNull String str) {
            k.f(str, "name");
            return v.b.b(oVar, str);
        }
    }

    /* compiled from: Headers.kt */
    public static final class a {
        @NotNull
        private static final o a = g.d;
        static final /* synthetic */ a b = new a();

        private a() {
        }

        @NotNull
        public final o a() {
            return a;
        }
    }
}
