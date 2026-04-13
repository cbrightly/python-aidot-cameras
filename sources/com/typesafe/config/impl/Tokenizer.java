package com.typesafe.config.impl;

import com.google.maps.android.BuildConfig;
import com.tencent.bugly.Bugly;
import com.typesafe.config.ConfigException;
import com.typesafe.config.f;
import com.typesafe.config.i;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class Tokenizer {

    public static class ProblemException extends Exception {
        private static final long serialVersionUID = 1;
        private final h0 problem;

        ProblemException(h0 problem2) {
            this.problem = problem2;
        }

        /* access modifiers changed from: package-private */
        public h0 problem() {
            return this.problem;
        }
    }

    /* access modifiers changed from: private */
    public static String b(int codepoint) {
        if (codepoint == 10) {
            return "newline";
        }
        if (codepoint == 9) {
            return "tab";
        }
        if (codepoint == -1) {
            return "end of file";
        }
        if (g.c(codepoint)) {
            return String.format("control character 0x%x", new Object[]{Integer.valueOf(codepoint)});
        }
        return String.format("%c", new Object[]{Integer.valueOf(codepoint)});
    }

    static Iterator<h0> d(f origin, Reader input, i flavor) {
        return new a(origin, input, flavor != i.JSON);
    }

    static String c(Iterator<h0> tokens) {
        StringBuilder renderedText = new StringBuilder();
        while (tokens.hasNext()) {
            renderedText.append(tokens.next().e());
        }
        return renderedText.toString();
    }

    public static class a implements Iterator<h0> {
        private final f0 c;
        private final Reader d;
        private final LinkedList<Integer> f = new LinkedList<>();
        private final boolean p0;
        private int q = 1;
        private f x;
        private final Queue<h0> y;
        private final C0227a z;

        /* renamed from: com.typesafe.config.impl.Tokenizer$a$a  reason: collision with other inner class name */
        public static class C0227a {
            private StringBuilder a = new StringBuilder();
            private boolean b = false;

            C0227a() {
            }

            /* access modifiers changed from: package-private */
            public void a(int c) {
                this.a.appendCodePoint(c);
            }

            /* access modifiers changed from: package-private */
            public h0 b(h0 t, f baseOrigin, int lineNumber) {
                if (a.e(t)) {
                    return d(baseOrigin, lineNumber);
                }
                return e(baseOrigin, lineNumber);
            }

            private h0 e(f baseOrigin, int lineNumber) {
                this.b = false;
                return c(baseOrigin, lineNumber);
            }

            private h0 d(f baseOrigin, int lineNumber) {
                h0 t = c(baseOrigin, lineNumber);
                if (!this.b) {
                    this.b = true;
                }
                return t;
            }

            private h0 c(f baseOrigin, int lineNumber) {
                h0 t;
                if (this.a.length() <= 0) {
                    return null;
                }
                if (this.b) {
                    t = j0.s(a.h(baseOrigin, lineNumber), this.a.toString());
                } else {
                    t = j0.l(a.h(baseOrigin, lineNumber), this.a.toString());
                }
                this.a.setLength(0);
                return t;
            }
        }

        a(f origin, Reader input, boolean allowComments) {
            f0 f0Var = (f0) origin;
            this.c = f0Var;
            this.d = input;
            this.p0 = allowComments;
            this.x = f0Var.p(1);
            LinkedList linkedList = new LinkedList();
            this.y = linkedList;
            linkedList.add(j0.a);
            this.z = new C0227a();
        }

        private int k() {
            if (!this.f.isEmpty()) {
                return this.f.pop().intValue();
            }
            try {
                return this.d.read();
            } catch (IOException e) {
                f0 f0Var = this.c;
                throw new ConfigException.IO(f0Var, "read error: " + e.getMessage(), e);
            }
        }

        private void B(int c2) {
            if (this.f.size() <= 2) {
                this.f.push(Integer.valueOf(c2));
                return;
            }
            throw new ConfigException.BugOrBroken("bug: putBack() three times, undesirable look-ahead");
        }

        static boolean f(int c2) {
            return g.d(c2);
        }

        static boolean g(int c2) {
            return c2 != 10 && g.d(c2);
        }

        private boolean D(int c2) {
            if (c2 == -1 || !this.p0) {
                return false;
            }
            if (c2 == 35) {
                return true;
            }
            if (c2 != 47) {
                return false;
            }
            int maybeSecondSlash = k();
            B(maybeSecondSlash);
            if (maybeSecondSlash == 47) {
                return true;
            }
            return false;
        }

        private int j(C0227a saver) {
            while (true) {
                int c2 = k();
                if (c2 == -1) {
                    return -1;
                }
                if (!g(c2)) {
                    return c2;
                }
                saver.a(c2);
            }
        }

        private ProblemException o(String message) {
            return q("", message, (Throwable) null);
        }

        private ProblemException p(String what, String message) {
            return q(what, message, (Throwable) null);
        }

        private ProblemException r(String what, String message, boolean suggestQuotes) {
            return s(what, message, suggestQuotes, (Throwable) null);
        }

        private ProblemException q(String what, String message, Throwable cause) {
            return m(this.x, what, message, cause);
        }

        private ProblemException s(String what, String message, boolean suggestQuotes, Throwable cause) {
            return n(this.x, what, message, suggestQuotes, cause);
        }

        private static ProblemException m(f origin, String what, String message, Throwable cause) {
            return n(origin, what, message, false, cause);
        }

        private static ProblemException n(f origin, String what, String message, boolean suggestQuotes, Throwable cause) {
            if (what != null && message != null) {
                return new ProblemException(j0.p(origin, what, message, suggestQuotes, cause));
            }
            throw new ConfigException.BugOrBroken("internal error, creating bad ProblemException");
        }

        private static ProblemException l(f origin, String message) {
            return m(origin, "", message, (Throwable) null);
        }

        /* access modifiers changed from: private */
        public static f h(f baseOrigin, int lineNumber) {
            return ((f0) baseOrigin).p(lineNumber);
        }

        private h0 t(int firstChar) {
            int c2;
            boolean doubleSlash = false;
            if (firstChar == 47) {
                if (k() == 47) {
                    doubleSlash = true;
                } else {
                    throw new ConfigException.BugOrBroken("called pullComment but // not seen");
                }
            }
            StringBuilder sb = new StringBuilder();
            while (true) {
                c2 = k();
                if (c2 == -1 || c2 == 10) {
                    B(c2);
                } else {
                    sb.appendCodePoint(c2);
                }
            }
            B(c2);
            if (doubleSlash) {
                return j0.i(this.x, sb.toString());
            }
            return j0.j(this.x, sb.toString());
        }

        private h0 A() {
            f origin = this.x;
            StringBuilder sb = new StringBuilder();
            int c2 = k();
            while (c2 != -1 && "$\"{}[]:=,+#`^?!@*&\\".indexOf(c2) < 0 && !f(c2) && !D(c2)) {
                sb.appendCodePoint(c2);
                if (sb.length() == 4) {
                    String s = sb.toString();
                    if (s.equals("true")) {
                        return j0.h(origin, true);
                    }
                    if (s.equals(BuildConfig.TRAVIS)) {
                        return j0.o(origin);
                    }
                } else if (sb.length() == 5 && sb.toString().equals(Bugly.SDK_IS_DEV)) {
                    return j0.h(origin, false);
                }
                c2 = k();
            }
            B(c2);
            return j0.s(origin, sb.toString());
        }

        private h0 w(int firstChar) {
            StringBuilder sb = new StringBuilder();
            sb.appendCodePoint(firstChar);
            boolean containedDecimalOrE = false;
            int c2 = k();
            while (c2 != -1 && "0123456789eE+-.".indexOf(c2) >= 0) {
                if (c2 == 46 || c2 == 101 || c2 == 69) {
                    containedDecimalOrE = true;
                }
                sb.appendCodePoint(c2);
                c2 = k();
            }
            B(c2);
            String s = sb.toString();
            if (!containedDecimalOrE) {
                return j0.n(this.x, Long.parseLong(s), s);
            }
            try {
                return j0.k(this.x, Double.parseDouble(s), s);
            } catch (NumberFormatException e) {
                char[] charArray = s.toCharArray();
                int length = charArray.length;
                int i = 0;
                while (i < length) {
                    char u = charArray[i];
                    if ("$\"{}[]:=,+#`^?!@*&\\".indexOf(u) < 0) {
                        i++;
                    } else {
                        String a = Tokenizer.b(u);
                        throw r(a, "Reserved character '" + Tokenizer.b(u) + "' is not allowed outside quotes", true);
                    }
                }
                return j0.s(this.x, s);
            }
        }

        private void u(StringBuilder sb, StringBuilder sbOrig) {
            int escaped = k();
            if (escaped != -1) {
                sbOrig.appendCodePoint(92);
                sbOrig.appendCodePoint(escaped);
                switch (escaped) {
                    case 34:
                        sb.append(StringUtil.DOUBLE_QUOTE);
                        return;
                    case 47:
                        sb.append('/');
                        return;
                    case 92:
                        sb.append('\\');
                        return;
                    case 98:
                        sb.append(8);
                        return;
                    case 102:
                        sb.append(12);
                        return;
                    case 110:
                        sb.append(10);
                        return;
                    case 114:
                        sb.append(StringUtil.CARRIAGE_RETURN);
                        return;
                    case 116:
                        sb.append(9);
                        return;
                    case 117:
                        char[] a = new char[4];
                        int i = 0;
                        while (i < 4) {
                            int c2 = k();
                            if (c2 != -1) {
                                a[i] = (char) c2;
                                i++;
                            } else {
                                throw o("End of input but expecting 4 hex digits for \\uXXXX escape");
                            }
                        }
                        String digits = new String(a);
                        sbOrig.append(a);
                        try {
                            sb.appendCodePoint(Integer.parseInt(digits, 16));
                            return;
                        } catch (NumberFormatException e) {
                            throw q(digits, String.format("Malformed hex digits after \\u escape in string: '%s'", new Object[]{digits}), e);
                        }
                    default:
                        throw p(Tokenizer.b(escaped), String.format("backslash followed by '%s', this is not a valid escape sequence (quoted strings use JSON escaping, so use double-backslash \\\\ for literal backslash)", new Object[]{Tokenizer.b(escaped)}));
                }
            } else {
                throw o("End of input but backslash in string had nothing after it");
            }
        }

        private void d(StringBuilder sb, StringBuilder sbOrig) {
            int consecutiveQuotes = 0;
            while (true) {
                int c2 = k();
                if (c2 == 34) {
                    consecutiveQuotes++;
                } else if (consecutiveQuotes >= 3) {
                    sb.setLength(sb.length() - 3);
                    B(c2);
                    return;
                } else {
                    consecutiveQuotes = 0;
                    if (c2 == -1) {
                        throw o("End of input but triple-quoted string was still open");
                    } else if (c2 == 10) {
                        int i = this.q + 1;
                        this.q = i;
                        this.x = this.c.p(i);
                    }
                }
                sb.appendCodePoint(c2);
                sbOrig.appendCodePoint(c2);
            }
        }

        private h0 y() {
            StringBuilder sb = new StringBuilder();
            StringBuilder sbOrig = new StringBuilder();
            sbOrig.appendCodePoint(34);
            while (true) {
                int c2 = k();
                if (c2 == -1) {
                    throw o("End of input but string quote was still open");
                } else if (c2 == 92) {
                    u(sb, sbOrig);
                } else if (c2 == 34) {
                    sbOrig.appendCodePoint(c2);
                    if (sb.length() == 0) {
                        int third = k();
                        if (third == 34) {
                            sbOrig.appendCodePoint(third);
                            d(sb, sbOrig);
                        } else {
                            B(third);
                        }
                    }
                    return j0.q(this.x, sb.toString(), sbOrig.toString());
                } else if (!g.c(c2)) {
                    sb.appendCodePoint(c2);
                    sbOrig.appendCodePoint(c2);
                } else {
                    String a = Tokenizer.b(c2);
                    throw p(a, "JSON does not allow unescaped " + Tokenizer.b(c2) + " in quoted strings, use a backslash escape");
                }
            }
        }

        private h0 x() {
            int c2 = k();
            if (c2 == 61) {
                return j0.j;
            }
            String a = Tokenizer.b(c2);
            throw r(a, "'+' not followed by =, '" + Tokenizer.b(c2) + "' not allowed after '+'", true);
        }

        private h0 z() {
            f origin = this.x;
            int c2 = k();
            if (c2 == 123) {
                boolean optional = false;
                int c3 = k();
                if (c3 == 63) {
                    optional = true;
                } else {
                    B(c3);
                }
                C0227a saver = new C0227a();
                List<Token> expression = new ArrayList<>();
                while (true) {
                    h0 t = v(saver);
                    if (t == j0.g) {
                        return j0.r(origin, optional, expression);
                    }
                    if (t != j0.b) {
                        h0 whitespace = saver.b(t, origin, this.q);
                        if (whitespace != null) {
                            expression.add(whitespace);
                        }
                        expression.add(t);
                    } else {
                        throw l(origin, "Substitution ${ was not closed with a }");
                    }
                }
            } else {
                String a = Tokenizer.b(c2);
                throw r(a, "'$' not followed by {, '" + Tokenizer.b(c2) + "' not allowed after '$'", true);
            }
        }

        private h0 v(C0227a saver) {
            h0 t;
            int c2 = j(saver);
            if (c2 == -1) {
                return j0.b;
            }
            if (c2 == 10) {
                h0 line = j0.m(this.x);
                int i = this.q + 1;
                this.q = i;
                this.x = this.c.p(i);
                return line;
            }
            if (D(c2)) {
                t = t(c2);
            } else {
                switch (c2) {
                    case 34:
                        t = y();
                        break;
                    case 36:
                        t = z();
                        break;
                    case 43:
                        t = x();
                        break;
                    case 44:
                        t = j0.c;
                        break;
                    case 58:
                        t = j0.e;
                        break;
                    case 61:
                        t = j0.d;
                        break;
                    case 91:
                        t = j0.h;
                        break;
                    case 93:
                        t = j0.i;
                        break;
                    case 123:
                        t = j0.f;
                        break;
                    case 125:
                        t = j0.g;
                        break;
                    default:
                        t = null;
                        break;
                }
                if (t == null) {
                    if ("0123456789-".indexOf(c2) >= 0) {
                        t = w(c2);
                    } else if ("$\"{}[]:=,+#`^?!@*&\\".indexOf(c2) < 0) {
                        B(c2);
                        t = A();
                    } else {
                        String a = Tokenizer.b(c2);
                        throw r(a, "Reserved character '" + Tokenizer.b(c2) + "' is not allowed outside quotes", true);
                    }
                }
            }
            if (t != null) {
                return t;
            }
            throw new ConfigException.BugOrBroken("bug: failed to generate next token");
        }

        /* access modifiers changed from: private */
        public static boolean e(h0 t) {
            if (j0.d(t) || j0.e(t) || j0.f(t)) {
                return true;
            }
            return false;
        }

        private void C() {
            h0 t = v(this.z);
            h0 whitespace = this.z.b(t, this.c, this.q);
            if (whitespace != null) {
                this.y.add(whitespace);
            }
            this.y.add(t);
        }

        public boolean hasNext() {
            return !this.y.isEmpty();
        }

        /* renamed from: i */
        public h0 next() {
            h0 t = this.y.remove();
            if (this.y.isEmpty() && t != j0.b) {
                try {
                    C();
                } catch (ProblemException e) {
                    this.y.add(e.problem());
                }
                if (this.y.isEmpty()) {
                    throw new ConfigException.BugOrBroken("bug: tokens queue should not be empty here");
                }
            }
            return t;
        }

        public void remove() {
            throw new UnsupportedOperationException("Does not make sense to remove items from token stream");
        }
    }
}
