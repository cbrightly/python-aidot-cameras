package com.yanzhenjie.andserver.util;

import org.slf4j.e;

/* compiled from: Patterns */
public interface k {
    public static final String a;
    public static final String b;
    public static final String c;
    public static final String d;
    public static final String e;
    public static final String f;
    public static final String g;

    static {
        String format = String.format("[a-zA-Z0-9_\\-\\.]%s", new Object[]{e.ANY_MARKER});
        a = format;
        String format2 = String.format("[a-zA-Z0-9_\\-\\.]%s", new Object[]{e.ANY_NON_NULL_MARKER});
        b = format2;
        c = String.format("((/%s)|((/%s)+))|((/%s)+/)", new Object[]{format, format2, format2});
        String format3 = String.format("[a-zA-Z0-9_\\-\\.]%s", new Object[]{e.ANY_NON_NULL_MARKER});
        d = format3;
        e = String.format("(%s)(=)(%s)", new Object[]{format3, "(.)*"});
        f = String.format("!%s", new Object[]{format3});
        g = String.format("(%s)(!=)(%s)", new Object[]{format3, format2});
    }
}
