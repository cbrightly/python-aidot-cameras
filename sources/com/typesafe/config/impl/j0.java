package com.typesafe.config.impl;

import com.google.maps.android.BuildConfig;
import com.meituan.robust.Constants;
import com.typesafe.config.ConfigException;
import com.typesafe.config.impl.m;
import com.typesafe.config.k;
import java.util.List;

/* compiled from: Tokens */
public final class j0 {
    static final h0 a = h0.c(i0.START, "start of file", "");
    static final h0 b = h0.c(i0.END, "end of file", "");
    static final h0 c = h0.c(i0.COMMA, "','", ",");
    static final h0 d = h0.c(i0.EQUALS, "'='", "=");
    static final h0 e = h0.c(i0.COLON, "':'", ":");
    static final h0 f = h0.c(i0.OPEN_CURLY, "'{'", "{");
    static final h0 g = h0.c(i0.CLOSE_CURLY, "'}'", "}");
    static final h0 h = h0.c(i0.OPEN_SQUARE, "'['", Constants.ARRAY_TYPE);
    static final h0 i = h0.c(i0.CLOSE_SQUARE, "']'", "]");
    static final h0 j = h0.c(i0.PLUS_EQUALS, "'+='", "+=");

    /* compiled from: Tokens */
    public static class g extends h0 {
        private final AbstractConfigValue e;

        g(AbstractConfigValue value, String origText) {
            super(i0.VALUE, value.origin(), origText);
            this.e = value;
        }

        /* access modifiers changed from: package-private */
        public AbstractConfigValue f() {
            return this.e;
        }

        public String toString() {
            if (f().resolveStatus() == a0.RESOLVED) {
                return "'" + f().unwrapped() + "' (" + this.e.valueType().name() + ")";
            }
            return "'<unresolved value>' (" + this.e.valueType().name() + ")";
        }

        /* access modifiers changed from: protected */
        public boolean a(Object other) {
            return other instanceof g;
        }

        public boolean equals(Object other) {
            return super.equals(other) && ((g) other).e.equals(this.e);
        }

        public int hashCode() {
            return ((super.hashCode() + 41) * 41) + this.e.hashCode();
        }
    }

    /* compiled from: Tokens */
    public static class c extends h0 {
        c(com.typesafe.config.f origin) {
            super(i0.NEWLINE, origin);
        }

        public String toString() {
            return "'\\n'@" + b();
        }

        /* access modifiers changed from: protected */
        public boolean a(Object other) {
            return other instanceof c;
        }

        public boolean equals(Object other) {
            return super.equals(other) && ((c) other).b() == b();
        }

        public int hashCode() {
            return ((super.hashCode() + 41) * 41) + b();
        }

        public String e() {
            return "\n";
        }
    }

    /* compiled from: Tokens */
    public static class f extends h0 {
        private final String e;

        f(com.typesafe.config.f origin, String s) {
            super(i0.UNQUOTED_TEXT, origin);
            this.e = s;
        }

        /* access modifiers changed from: package-private */
        public String f() {
            return this.e;
        }

        public String toString() {
            return "'" + this.e + "'";
        }

        /* access modifiers changed from: protected */
        public boolean a(Object other) {
            return other instanceof f;
        }

        public boolean equals(Object other) {
            return super.equals(other) && ((f) other).e.equals(this.e);
        }

        public int hashCode() {
            return ((super.hashCode() + 41) * 41) + this.e.hashCode();
        }

        public String e() {
            return this.e;
        }
    }

    /* compiled from: Tokens */
    public static class b extends h0 {
        private final String e;

        b(com.typesafe.config.f origin, String s) {
            super(i0.IGNORED_WHITESPACE, origin);
            this.e = s;
        }

        public String toString() {
            return "'" + this.e + "' (WHITESPACE)";
        }

        /* access modifiers changed from: protected */
        public boolean a(Object other) {
            return other instanceof b;
        }

        public boolean equals(Object other) {
            return super.equals(other) && ((b) other).e.equals(this.e);
        }

        public int hashCode() {
            return ((super.hashCode() + 41) * 41) + this.e.hashCode();
        }

        public String e() {
            return this.e;
        }
    }

    /* compiled from: Tokens */
    public static class d extends h0 {
        private final String e;
        private final String f;
        private final boolean g;
        private final Throwable h;

        d(com.typesafe.config.f origin, String what, String message, boolean suggestQuotes, Throwable cause) {
            super(i0.PROBLEM, origin);
            this.e = what;
            this.f = message;
            this.g = suggestQuotes;
            this.h = cause;
        }

        public String toString() {
            return '\'' + this.e + '\'' + " (" + this.f + ")";
        }

        /* access modifiers changed from: protected */
        public boolean a(Object other) {
            return other instanceof d;
        }

        public boolean equals(Object other) {
            return super.equals(other) && ((d) other).e.equals(this.e) && ((d) other).f.equals(this.f) && ((d) other).g == this.g && g.a(((d) other).h, this.h);
        }

        public int hashCode() {
            int hashCode = this.e.hashCode();
            int hashCode2 = this.f.hashCode();
            int h2 = (Boolean.valueOf(this.g).hashCode() + ((hashCode2 + ((hashCode + ((super.hashCode() + 41) * 41)) * 41)) * 41)) * 41;
            Throwable th = this.h;
            if (th != null) {
                return (th.hashCode() + h2) * 41;
            }
            return h2;
        }
    }

    /* compiled from: Tokens */
    public static abstract class a extends h0 {
        /* access modifiers changed from: private */
        public final String e;

        a(com.typesafe.config.f origin, String text) {
            super(i0.COMMENT, origin);
            this.e = text;
        }

        /* renamed from: com.typesafe.config.impl.j0$a$a  reason: collision with other inner class name */
        /* compiled from: Tokens */
        public static final class C0228a extends a {
            C0228a(com.typesafe.config.f origin, String text) {
                super(origin, text);
            }

            public String e() {
                return "//" + this.e;
            }
        }

        /* compiled from: Tokens */
        public static final class b extends a {
            b(com.typesafe.config.f origin, String text) {
                super(origin, text);
            }

            public String e() {
                return "#" + this.e;
            }
        }

        public String toString() {
            return "'#" + this.e + "' (COMMENT)";
        }

        /* access modifiers changed from: protected */
        public boolean a(Object other) {
            return other instanceof a;
        }

        public boolean equals(Object other) {
            return super.equals(other) && ((a) other).e.equals(this.e);
        }

        public int hashCode() {
            return (this.e.hashCode() + ((super.hashCode() + 41) * 41)) * 41;
        }
    }

    /* compiled from: Tokens */
    public static class e extends h0 {
        private final boolean e;
        private final List<h0> f;

        e(com.typesafe.config.f origin, boolean optional, List<h0> expression) {
            super(i0.SUBSTITUTION, origin);
            this.e = optional;
            this.f = expression;
        }

        public String e() {
            StringBuilder sb = new StringBuilder();
            sb.append("${");
            sb.append(this.e ? "?" : "");
            sb.append(Tokenizer.c(this.f.iterator()));
            sb.append("}");
            return sb.toString();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (h0 t : this.f) {
                sb.append(t.toString());
            }
            return "'${" + sb.toString() + "}'";
        }

        /* access modifiers changed from: protected */
        public boolean a(Object other) {
            return other instanceof e;
        }

        public boolean equals(Object other) {
            return super.equals(other) && ((e) other).f.equals(this.f);
        }

        public int hashCode() {
            return ((super.hashCode() + 41) * 41) + this.f.hashCode();
        }
    }

    static boolean f(h0 token) {
        return token instanceof g;
    }

    static AbstractConfigValue b(h0 token) {
        if (token instanceof g) {
            return ((g) token).f();
        }
        throw new ConfigException.BugOrBroken("tried to get value of non-value token " + token);
    }

    static boolean g(h0 t, k valueType) {
        return f(t) && b(t).valueType() == valueType;
    }

    static boolean e(h0 token) {
        return token instanceof f;
    }

    static String a(h0 token) {
        if (token instanceof f) {
            return ((f) token).f();
        }
        throw new ConfigException.BugOrBroken("tried to get unquoted text from " + token);
    }

    static boolean c(h0 token) {
        return token instanceof b;
    }

    static boolean d(h0 token) {
        return token instanceof e;
    }

    static h0 m(com.typesafe.config.f origin) {
        return new c(origin);
    }

    static h0 p(com.typesafe.config.f origin, String what, String message, boolean suggestQuotes, Throwable cause) {
        return new d(origin, what, message, suggestQuotes, cause);
    }

    static h0 i(com.typesafe.config.f origin, String text) {
        return new a.C0228a(origin, text);
    }

    static h0 j(com.typesafe.config.f origin, String text) {
        return new a.b(origin, text);
    }

    static h0 s(com.typesafe.config.f origin, String s) {
        return new f(origin, s);
    }

    static h0 l(com.typesafe.config.f origin, String s) {
        return new b(origin, s);
    }

    static h0 r(com.typesafe.config.f origin, boolean optional, List<h0> expression) {
        return new e(origin, optional, expression);
    }

    static h0 t(AbstractConfigValue value, String origText) {
        return new g(value, origText);
    }

    static h0 q(com.typesafe.config.f origin, String value, String origText) {
        return t(new m.a(origin, value), origText);
    }

    static h0 k(com.typesafe.config.f origin, double value, String origText) {
        return t(k.newNumber(origin, value, origText), origText);
    }

    static h0 n(com.typesafe.config.f origin, long value, String origText) {
        return t(k.newNumber(origin, value, origText), origText);
    }

    static h0 o(com.typesafe.config.f origin) {
        return t(new j(origin), BuildConfig.TRAVIS);
    }

    static h0 h(com.typesafe.config.f origin, boolean value) {
        b bVar = new b(origin, value);
        return t(bVar, "" + value);
    }
}
