package io.ktor.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.g0;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MapApplicationConfig.kt */
public class c implements a {
    @NotNull
    private final Map<String, String> a;
    @NotNull
    private final String b;

    private c(Map<String, String> map, String path) {
        this.a = map;
        this.b = path;
    }

    public c() {
        this(new LinkedHashMap(), "");
    }

    @Nullable
    public b a(@NotNull String path) {
        k.f(path, "path");
        String key = d.b(this.b, path);
        if (this.a.containsKey(key) || this.a.containsKey(d.b(key, "size"))) {
            return new a(this.a, key);
        }
        return null;
    }

    /* compiled from: MapApplicationConfig.kt */
    public static final class a implements b {
        @NotNull
        private final Map<String, String> a;
        @NotNull
        private final String b;

        public a(@NotNull Map<String, String> map, @NotNull String path) {
            k.f(map, "map");
            k.f(path, "path");
            this.a = map;
            this.b = path;
        }

        @NotNull
        public String a() {
            String str = this.a.get(this.b);
            if (str == null) {
                k.n();
            }
            return str;
        }

        @NotNull
        public List<String> b() {
            String size = this.a.get(d.b(this.b, "size"));
            if (size != null) {
                Iterable $this$mapTo$iv$iv = n.l(0, Integer.parseInt(size));
                ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                Iterator it = $this$mapTo$iv$iv.iterator();
                while (it.hasNext()) {
                    String str = this.a.get(d.b(this.b, String.valueOf(((g0) it).nextInt())));
                    if (str == null) {
                        k.n();
                    }
                    arrayList.add(str);
                }
                return arrayList;
            }
            throw new ApplicationConfigurationException("Property " + this.b + ".size not found.");
        }
    }
}
