package io.ktor.http;

import io.ktor.http.c;
import io.ktor.util.a0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.k0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.n;
import kotlin.sequences.h;
import kotlin.sequences.o;
import kotlin.t;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileContentType.kt */
public final class j {
    private static final g a = i.b(a.INSTANCE);
    private static final g b = i.b(b.INSTANCE);

    private static final Map<String, List<c>> b() {
        return (Map) a.getValue();
    }

    @NotNull
    public static final List<c> a(@NotNull c.b $this$fromFileExtension, @NotNull String ext) {
        k.f($this$fromFileExtension, "$this$fromFileExtension");
        k.f(ext, "ext");
        String current = a0.c(x.w0(ext, "."));
        while (true) {
            if (!(current.length() > 0)) {
                return q.g();
            }
            List type = b().get(current);
            if (type != null) {
                return type;
            }
            current = x.P0(current, ".", "");
        }
    }

    /* compiled from: FileContentType.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Map<String, List<? extends c>>> {
        public static final a INSTANCE = new a();

        a() {
            super(0);
        }

        @NotNull
        public final Map<String, List<c>> invoke() {
            Map $this$apply = io.ktor.util.j.a();
            $this$apply.putAll(j.c(y.L(x.a())));
            return $this$apply;
        }
    }

    /* compiled from: FileContentType.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<Map<c, ? extends List<? extends String>>> {
        public static final b INSTANCE = new b();

        b() {
            super(0);
        }

        /* compiled from: FileContentType.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<n<? extends String, ? extends c>, n<? extends c, ? extends String>> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            @NotNull
            public final n<c, String> invoke(@NotNull n<String, c> $dstr$first$second) {
                k.f($dstr$first$second, "<name for destructuring parameter 0>");
                return t.a($dstr$first$second.component2(), $dstr$first$second.component1());
            }
        }

        @NotNull
        public final Map<c, List<String>> invoke() {
            return j.c(o.w(y.L(x.a()), a.INSTANCE));
        }
    }

    @NotNull
    public static final c d(@NotNull List<c> $this$selectDefault) {
        k.f($this$selectDefault, "$this$selectDefault");
        c contentType = (c) y.U($this$selectDefault);
        if (contentType == null) {
            contentType = c.a.t.c();
        }
        return (!k.a(contentType.f(), "text") || d.a(contentType) != null) ? contentType : d.b(contentType, kotlin.text.c.a);
    }

    @NotNull
    public static final <A, B> Map<A, List<B>> c(@NotNull h<? extends n<? extends A, ? extends B>> $this$groupByPairs) {
        k.f($this$groupByPairs, "$this$groupByPairs");
        Map linkedHashMap = new LinkedHashMap();
        for (Object next : $this$groupByPairs) {
            Object key$iv$iv = ((n) next).getFirst();
            Map $this$getOrPut$iv$iv$iv = linkedHashMap;
            Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
            if (value$iv$iv$iv == null) {
                Object answer$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                value$iv$iv$iv = answer$iv$iv$iv;
            }
            ((List) value$iv$iv$iv).add(next);
        }
        Map $this$mapValues$iv = linkedHashMap;
        Map destination$iv$iv = new LinkedHashMap(k0.b($this$mapValues$iv.size()));
        for (Object element$iv$iv$iv : $this$mapValues$iv.entrySet()) {
            Object key = ((Map.Entry) element$iv$iv$iv).getKey();
            Iterable<n> $this$map$iv = (Iterable) ((Map.Entry) element$iv$iv$iv).getValue();
            Map $this$mapValues$iv2 = $this$mapValues$iv;
            Collection destination$iv$iv2 = new ArrayList(r.r($this$map$iv, 10));
            for (n it : $this$map$iv) {
                destination$iv$iv2.add(it.getSecond());
                h<? extends n<? extends A, ? extends B>> hVar = $this$groupByPairs;
            }
            destination$iv$iv.put(key, destination$iv$iv2);
            h<? extends n<? extends A, ? extends B>> hVar2 = $this$groupByPairs;
            $this$mapValues$iv = $this$mapValues$iv2;
        }
        return destination$iv$iv;
    }

    @NotNull
    public static final c e(@NotNull String $this$toContentType) {
        k.f($this$toContentType, "$this$toContentType");
        try {
            return c.e.b($this$toContentType);
        } catch (Throwable e) {
            throw new IllegalArgumentException("Failed to parse " + $this$toContentType, e);
        }
    }
}
