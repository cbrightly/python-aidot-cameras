package org.hamcrest;

import java.io.IOException;

/* compiled from: StringDescription */
public class e extends a {
    private final Appendable a;

    public e() {
        this(new StringBuilder());
    }

    public e(Appendable out) {
        this.a = out;
    }

    public static String l(d selfDescribing) {
        return new e().a(selfDescribing).toString();
    }

    public static String k(d selfDescribing) {
        return l(selfDescribing);
    }

    /* access modifiers changed from: protected */
    public void e(String str) {
        try {
            this.a.append(str);
        } catch (IOException e) {
            throw new RuntimeException("Could not write description", e);
        }
    }

    /* access modifiers changed from: protected */
    public void d(char c) {
        try {
            this.a.append(c);
        } catch (IOException e) {
            throw new RuntimeException("Could not write description", e);
        }
    }

    public String toString() {
        return this.a.toString();
    }
}
