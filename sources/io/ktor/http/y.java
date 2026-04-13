package io.ktor.http;

import io.ktor.util.v;
import java.util.List;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Parameters.kt */
public interface y extends v {
    public static final a b = a.b;

    /* compiled from: Parameters.kt */
    public static final class b {
        public static void a(y yVar, @NotNull p<? super String, ? super List<String>, x> pVar) {
            k.f(pVar, "body");
            v.b.a(yVar, pVar);
        }

        @Nullable
        public static String b(y yVar, @NotNull String str) {
            k.f(str, "name");
            return v.b.b(yVar, str);
        }
    }

    /* compiled from: Parameters.kt */
    public static final class a {
        @NotNull
        private static final y a = h.d;
        static final /* synthetic */ a b = new a();

        private a() {
        }

        @NotNull
        public final y a() {
            return a;
        }
    }
}
