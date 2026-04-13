package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.f;
import com.typesafe.config.i;
import com.typesafe.config.impl.PathParser;
import com.typesafe.config.k;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: PathParser */
public final class u {
    static f a = f0.l("path parameter");

    /* compiled from: PathParser */
    public static class a {
        StringBuilder a;
        boolean b;

        a(String initial, boolean canBeEmpty) {
            this.b = canBeEmpty;
            this.a = new StringBuilder(initial);
        }

        public String toString() {
            return "Element(" + this.a.toString() + "," + this.b + ")";
        }
    }

    static s d(String path) {
        s speculated = g(path);
        if (speculated != null) {
            return speculated;
        }
        StringReader reader = new StringReader(path);
        try {
            Iterator<h0> d = Tokenizer.d(a, reader, i.CONF);
            d.next();
            return e(d, a, path);
        } finally {
            reader.close();
        }
    }

    protected static s e(Iterator<h0> expression, f origin, String originalText) {
        return f(expression, origin, originalText, (ArrayList<h0>) null, i.CONF);
    }

    protected static s f(Iterator<h0> expression, f origin, String originalText, ArrayList<h0> pathTokens, i flavor) {
        String text;
        List<PathParser.Element> buf = new ArrayList<>();
        buf.add(new a("", false));
        if (expression.hasNext()) {
            while (expression.hasNext()) {
                h0 t = expression.next();
                if (pathTokens != null) {
                    pathTokens.add(t);
                }
                if (!j0.c(t)) {
                    if (j0.g(t, k.STRING)) {
                        a(buf, true, j0.b(t).transformToString());
                    } else if (t != j0.b) {
                        if (j0.f(t)) {
                            AbstractConfigValue v = j0.b(t);
                            if (pathTokens != null) {
                                pathTokens.remove(pathTokens.size() - 1);
                                pathTokens.addAll(h(t, flavor));
                            }
                            text = v.transformToString();
                        } else if (j0.e(t)) {
                            if (pathTokens != null) {
                                pathTokens.remove(pathTokens.size() - 1);
                                pathTokens.addAll(h(t, flavor));
                            }
                            text = j0.a(t);
                        } else {
                            throw new ConfigException.BadPath(origin, originalText, "Token not allowed in path expression: " + t + " (you can double-quote this token if you really want it here)");
                        }
                        a(buf, false, text);
                    } else {
                        continue;
                    }
                }
            }
            t pb = new t();
            Iterator<PathParser.Element> it = buf.iterator();
            while (it.hasNext()) {
                a e = (a) it.next();
                if (e.a.length() != 0 || e.b) {
                    pb.a(e.a.toString());
                } else {
                    throw new ConfigException.BadPath(origin, originalText, "path has a leading, trailing, or two adjacent period '.' (use quoted \"\" empty string if you want an empty element)");
                }
            }
            return pb.d();
        }
        throw new ConfigException.BadPath(origin, originalText, "Expecting a field name or path here, but got nothing");
    }

    private static Collection<h0> h(h0 t, i flavor) {
        String tokenText = t.e();
        if (tokenText.equals(".")) {
            return Collections.singletonList(t);
        }
        String[] splitToken = tokenText.split("\\.");
        ArrayList<Token> splitTokens = new ArrayList<>();
        for (String s : splitToken) {
            if (flavor == i.CONF) {
                splitTokens.add(j0.s(t.d(), s));
            } else {
                splitTokens.add(j0.q(t.d(), s, "\"" + s + "\""));
            }
            splitTokens.add(j0.s(t.d(), "."));
        }
        if (tokenText.charAt(tokenText.length() - 1) != '.') {
            splitTokens.remove(splitTokens.size() - 1);
        }
        return splitTokens;
    }

    private static void a(List<a> buf, boolean wasQuoted, String newText) {
        int i = wasQuoted ? -1 : newText.indexOf(46);
        a current = buf.get(buf.size() - 1);
        if (i < 0) {
            current.a.append(newText);
            if (wasQuoted && current.a.length() == 0) {
                current.b = true;
                return;
            }
            return;
        }
        current.a.append(newText.substring(0, i));
        buf.add(new a("", false));
        a(buf, false, newText.substring(i + 1));
    }

    private static boolean c(String s) {
        boolean lastWasDot = true;
        int len = s.length();
        if (s.isEmpty() || s.charAt(0) == '.' || s.charAt(len - 1) == '.') {
            return true;
        }
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if ((c >= 'a' && c <= 'z') || ((c >= 'A' && c <= 'Z') || c == '_')) {
                lastWasDot = false;
            } else if (c == '.') {
                if (lastWasDot) {
                    return true;
                }
                lastWasDot = true;
            } else if (c != '-' || lastWasDot) {
                return true;
            }
        }
        if (lastWasDot) {
            return true;
        }
        return false;
    }

    private static s b(s tail, String s, int end) {
        int splitAt = s.lastIndexOf(46, end - 1);
        new ArrayList<>().add(j0.s((f) null, s));
        s withOneMoreElement = new s(s.substring(splitAt + 1, end), tail);
        if (splitAt < 0) {
            return withOneMoreElement;
        }
        return b(withOneMoreElement, s, splitAt);
    }

    private static s g(String path) {
        String s = g.i(path);
        if (c(s)) {
            return null;
        }
        return b((s) null, s, s.length());
    }
}
