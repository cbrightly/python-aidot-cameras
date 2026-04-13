package io.ktor.features;

import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.slf4j.e;

/* compiled from: ContentNegotiation.kt */
public final class h {

    /* compiled from: Comparisons.kt */
    public static final class a<T> implements Comparator<T> {
        public final int compare(T a, T b) {
            return kotlin.comparisons.a.c(Double.valueOf(((i) b).c()), Double.valueOf(((i) a).c()));
        }
    }

    /* compiled from: Comparisons.kt */
    public static final class b<T> implements Comparator<T> {
        final /* synthetic */ Comparator c;

        public b(Comparator comparator) {
            this.c = comparator;
        }

        public final int compare(T a, T b) {
            int previousCompare = this.c.compare(a, b);
            if (previousCompare != 0) {
                return previousCompare;
            }
            io.ktor.http.c contentType = ((i) a).b();
            int asterisks = 0;
            if (k.a(contentType.f(), e.ANY_MARKER)) {
                asterisks = 0 + 2;
            }
            if (k.a(contentType.e(), e.ANY_MARKER)) {
                asterisks++;
            }
            Integer valueOf = Integer.valueOf(asterisks);
            io.ktor.http.c contentType2 = ((i) b).b();
            int asterisks2 = 0;
            if (k.a(contentType2.f(), e.ANY_MARKER)) {
                asterisks2 = 0 + 2;
            }
            if (k.a(contentType2.e(), e.ANY_MARKER)) {
                asterisks2++;
            }
            return kotlin.comparisons.a.c(valueOf, Integer.valueOf(asterisks2));
        }
    }

    /* compiled from: Comparisons.kt */
    public static final class c<T> implements Comparator<T> {
        final /* synthetic */ Comparator c;

        public c(Comparator comparator) {
            this.c = comparator;
        }

        public final int compare(T a, T b) {
            int previousCompare = this.c.compare(a, b);
            return previousCompare != 0 ? previousCompare : kotlin.comparisons.a.c(Integer.valueOf(((i) b).b().b().size()), Integer.valueOf(((i) a).b().b().size()));
        }
    }

    public static /* synthetic */ Charset c(io.ktor.application.b bVar, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = kotlin.text.c.a;
        }
        return b(bVar, charset);
    }

    @NotNull
    public static final Charset b(@NotNull io.ktor.application.b $this$suitableCharset, @NotNull Charset defaultCharset) {
        k.f($this$suitableCharset, "$this$suitableCharset");
        k.f(defaultCharset, "defaultCharset");
        for (io.ktor.http.k a2 : io.ktor.request.e.a($this$suitableCharset.getRequest())) {
            String charset = a2.a();
            if (k.a(charset, e.ANY_MARKER)) {
                return defaultCharset;
            }
            if (Charset.isSupported(charset)) {
                Charset forName = Charset.forName(charset);
                k.b(forName, "Charset.forName(charset)");
                return forName;
            }
        }
        return defaultCharset;
    }

    @NotNull
    public static final List<i> a(@NotNull List<i> list) {
        k.f(list, "$this$sortedByQuality");
        return y.u0(list, new c(new b(new a())));
    }
}
