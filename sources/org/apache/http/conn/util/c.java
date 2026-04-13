package org.apache.http.conn.util;

import java.util.Collections;
import java.util.List;
import org.apache.http.util.a;

/* compiled from: PublicSuffixList */
public final class c {
    private final a a;
    private final List<String> b;
    private final List<String> c;

    public c(a type, List<String> rules, List<String> exceptions) {
        this.a = (a) a.i(type, "Domain type");
        this.b = Collections.unmodifiableList((List) a.i(rules, "Domain suffix rules"));
        this.c = Collections.unmodifiableList(exceptions != null ? exceptions : Collections.emptyList());
    }

    public a c() {
        return this.a;
    }

    public List<String> b() {
        return this.b;
    }

    public List<String> a() {
        return this.c;
    }
}
