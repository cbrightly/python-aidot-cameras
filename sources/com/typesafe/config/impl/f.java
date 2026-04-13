package com.typesafe.config.impl;

import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.typesafe.config.ConfigException;
import com.typesafe.config.impl.m;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ConfigImpl */
public class f {
    private static final com.typesafe.config.f a;
    private static final b b;
    private static final b c;
    private static final j d;
    private static final d0 e;
    private static final e0 f;

    /* compiled from: ConfigImpl */
    public static class b {
        static final a a = f.d();
    }

    static {
        f0 l = f0.l("hardcoded value");
        a = l;
        b = new b(l, true);
        c = new b(l, false);
        d = new j(l);
        e = new d0(l, Collections.emptyList());
        f = e0.empty(l);
    }

    /* access modifiers changed from: private */
    public static a d() {
        Map<String, String> env = System.getenv();
        Map<String, AbstractConfigValue> m = new HashMap<>();
        for (Map.Entry<String, String> entry : env.entrySet()) {
            String key = entry.getKey();
            m.put(key, new m.a(f0.l("env var " + key), entry.getValue()));
        }
        return new e0(f0.l("env variables"), m, a0.RESOLVED, false);
    }

    static a b() {
        try {
            return b.a;
        } catch (ExceptionInInitializerError e2) {
            throw g.b(e2);
        }
    }

    /* compiled from: ConfigImpl */
    public static class a {
        private static String a = "loads";
        private static String b = "substitutions";
        private static final Map<String, Boolean> c;
        private static final boolean d;
        private static final boolean e;

        static {
            Map<String, Boolean> a2 = a();
            c = a2;
            d = a2.get(a).booleanValue();
            e = a2.get(b).booleanValue();
        }

        private static Map<String, Boolean> a() {
            Map<String, Boolean> result = new HashMap<>();
            result.put(a, false);
            result.put(b, false);
            String s = System.getProperty("config.trace");
            if (s == null) {
                return result;
            }
            for (String k : s.split(",")) {
                if (k.equals(a)) {
                    result.put(a, true);
                } else if (k.equals(b)) {
                    result.put(b, true);
                } else {
                    System.err.println("config.trace property contains unknown trace topic '" + k + "'");
                }
            }
            return result;
        }

        static boolean b() {
            return e;
        }
    }

    public static boolean g() {
        try {
            return a.b();
        } catch (ExceptionInInitializerError e2) {
            throw g.b(e2);
        }
    }

    public static void f(String message) {
        System.err.println(message);
    }

    public static void e(int indentLevel, String message) {
        while (indentLevel > 0) {
            System.err.print(JustifyTextView.TWO_CHINESE_BLANK);
            indentLevel--;
        }
        System.err.println(message);
    }

    static ConfigException.NotResolved c(s what, ConfigException.NotResolved original) {
        String newMessage = what.k() + " has not been resolved, you need to call Config#resolve(), see API docs for Config#resolve()";
        if (newMessage.equals(original.getMessage())) {
            return original;
        }
        return new ConfigException.NotResolved(newMessage, original);
    }
}
