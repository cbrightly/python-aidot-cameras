package io.ktor.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.y;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StringValues.kt */
public interface v {
    public static final a c = a.b;

    void a(@NotNull p<? super String, ? super List<String>, x> pVar);

    boolean b();

    @Nullable
    List<String> c(@NotNull String str);

    @NotNull
    Set<Map.Entry<String, List<String>>> entries();

    @Nullable
    String get(@NotNull String str);

    boolean isEmpty();

    /* compiled from: StringValues.kt */
    public static final class a {
        @NotNull
        private static final v a = new x(false, (Map) null, 3, (DefaultConstructorMarker) null);
        static final /* synthetic */ a b = new a();

        private a() {
        }
    }

    /* compiled from: StringValues.kt */
    public static final class b {
        @Nullable
        public static String b(v $this, @NotNull String name) {
            k.f(name, "name");
            List<String> c = $this.c(name);
            if (c != null) {
                return (String) y.U(c);
            }
            return null;
        }

        public static void a(v $this, @NotNull p<? super String, ? super List<String>, x> body) {
            k.f(body, "body");
            for (Map.Entry $dstr$k$v : $this.entries()) {
                body.invoke((String) $dstr$k$v.getKey(), (List) $dstr$k$v.getValue());
            }
        }
    }
}
