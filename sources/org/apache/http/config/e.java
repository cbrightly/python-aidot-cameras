package org.apache.http.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.util.a;

/* compiled from: RegistryBuilder */
public final class e<I> {
    private final Map<String, I> a = new HashMap();

    public static <I> e<I> b() {
        return new e<>();
    }

    e() {
    }

    public e<I> c(String id, I item) {
        a.e(id, "ID");
        a.i(item, "Item");
        this.a.put(id.toLowerCase(Locale.ROOT), item);
        return this;
    }

    public d<I> a() {
        return new d<>(this.a);
    }

    public String toString() {
        return this.a.toString();
    }
}
