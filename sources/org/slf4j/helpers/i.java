package org.slf4j.helpers;

import java.io.PrintStream;

/* compiled from: Util */
public final class i {
    private static b a;
    private static boolean b = false;

    private i() {
    }

    public static String g(String key) {
        if (key != null) {
            try {
                return System.getProperty(key);
            } catch (SecurityException e) {
                return null;
            }
        } else {
            throw new IllegalArgumentException("null input");
        }
    }

    public static boolean f(String key) {
        String value = g(key);
        if (value == null) {
            return false;
        }
        return value.equalsIgnoreCase("true");
    }

    /* compiled from: Util */
    public static final class b extends SecurityManager {
        private b() {
        }

        /* access modifiers changed from: protected */
        public Class<?>[] getClassContext() {
            return super.getClassContext();
        }
    }

    private static b b() {
        b bVar = a;
        if (bVar != null) {
            return bVar;
        }
        if (b) {
            return null;
        }
        b e = e();
        a = e;
        b = true;
        return e;
    }

    private static b e() {
        try {
            return new b();
        } catch (SecurityException e) {
            return null;
        }
    }

    public static Class<?> a() {
        b securityManager = b();
        if (securityManager == null) {
            return null;
        }
        Class<?>[] trace = securityManager.getClassContext();
        String thisClassName = i.class.getName();
        int i = 0;
        while (i < trace.length && !thisClassName.equals(trace[i].getName())) {
            i++;
        }
        if (i < trace.length && i + 2 < trace.length) {
            return trace[i + 2];
        }
        throw new IllegalStateException("Failed to find org.slf4j.helpers.Util or its caller in the stack; this should not happen");
    }

    public static final void d(String msg, Throwable t) {
        System.err.println(msg);
        System.err.println("Reported exception:");
        t.printStackTrace();
    }

    public static final void c(String msg) {
        PrintStream printStream = System.err;
        printStream.println("SLF4J: " + msg);
    }
}
