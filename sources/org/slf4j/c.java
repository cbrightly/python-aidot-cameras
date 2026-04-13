package org.slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.event.SubstituteLoggingEvent;
import org.slf4j.helpers.d;
import org.slf4j.helpers.g;
import org.slf4j.helpers.h;
import org.slf4j.helpers.i;
import org.slf4j.impl.StaticLoggerBinder;

/* compiled from: LoggerFactory */
public final class c {
    static volatile int a = 0;
    static final h b = new h();
    static final d c = new d();
    static boolean d = i.f("slf4j.detectLoggerNameMismatch");
    private static final String[] e = {"1.6", "1.7"};
    private static String f = "org/slf4j/impl/StaticLoggerBinder.class";

    private c() {
    }

    private static final void o() {
        a();
        if (a == 3) {
            u();
        }
    }

    private static boolean m(String msg) {
        if (msg == null) {
            return false;
        }
        if (!msg.contains("org/slf4j/impl/StaticLoggerBinder") && !msg.contains("org.slf4j.impl.StaticLoggerBinder")) {
            return false;
        }
        return true;
    }

    private static final void a() {
        Set<URL> staticLoggerBinderPathSet = null;
        try {
            if (!l()) {
                staticLoggerBinderPathSet = f();
                t(staticLoggerBinderPathSet);
            }
            StaticLoggerBinder.getSingleton();
            a = 3;
            s(staticLoggerBinderPathSet);
        } catch (NoClassDefFoundError ncde) {
            if (m(ncde.getMessage())) {
                a = 4;
                i.c("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".");
                i.c("Defaulting to no-operation (NOP) logger implementation");
                i.c("See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.");
            } else {
                e(ncde);
                throw ncde;
            }
        } catch (NoSuchMethodError nsme) {
            String msg = nsme.getMessage();
            if (msg != null && msg.contains("org.slf4j.impl.StaticLoggerBinder.getSingleton()")) {
                a = 2;
                i.c("slf4j-api 1.6.x (or later) is incompatible with this binding.");
                i.c("Your binding is version 1.5.5 or earlier.");
                i.c("Upgrade your binding to version 1.6.x.");
            }
            throw nsme;
        } catch (Exception e2) {
            e(e2);
            throw new IllegalStateException("Unexpected initialization failure", e2);
        } catch (Throwable th) {
            p();
            throw th;
        }
        p();
    }

    private static void p() {
        g();
        q();
        b.b();
    }

    private static void g() {
        h hVar = b;
        synchronized (hVar) {
            hVar.e();
            for (g substLogger : hVar.d()) {
                substLogger.g(j(substLogger.getName()));
            }
        }
    }

    static void e(Throwable t) {
        a = 2;
        i.d("Failed to instantiate SLF4J LoggerFactory", t);
    }

    private static void q() {
        LinkedBlockingQueue<org.slf4j.event.d> c2 = b.c();
        int queueSize = c2.size();
        int count = 0;
        List<SubstituteLoggingEvent> eventList = new ArrayList<>(128);
        while (c2.drainTo(eventList, 128) != 0) {
            Iterator<SubstituteLoggingEvent> it = eventList.iterator();
            while (it.hasNext()) {
                org.slf4j.event.d event = (org.slf4j.event.d) it.next();
                r(event);
                int count2 = count + 1;
                if (count == 0) {
                    b(event, queueSize);
                }
                count = count2;
            }
            eventList.clear();
        }
    }

    private static void b(org.slf4j.event.d event, int queueSize) {
        if (event.a().c()) {
            c(queueSize);
        } else if (!event.a().d()) {
            d();
        }
    }

    private static void r(org.slf4j.event.d event) {
        if (event != null) {
            g substLogger = event.a();
            String loggerName = substLogger.getName();
            if (substLogger.e()) {
                throw new IllegalStateException("Delegate logger cannot be null at this state.");
            } else if (!substLogger.d()) {
                if (substLogger.c()) {
                    substLogger.f(event);
                } else {
                    i.c(loggerName);
                }
            }
        }
    }

    private static void d() {
        i.c("The following set of substitute loggers may have been accessed");
        i.c("during the initialization phase. Logging calls during this");
        i.c("phase were not honored. However, subsequent logging calls to these");
        i.c("loggers will work as normally expected.");
        i.c("See also http://www.slf4j.org/codes.html#substituteLogger");
    }

    private static void c(int eventCount) {
        i.c("A number (" + eventCount + ") of logging calls during the initialization phase have been intercepted and are");
        i.c("now being replayed. These are subject to the filtering rules of the underlying logging system.");
        i.c("See also http://www.slf4j.org/codes.html#replay");
    }

    private static final void u() {
        try {
            String requested = StaticLoggerBinder.REQUESTED_API_VERSION;
            boolean match = false;
            for (String aAPI_COMPATIBILITY_LIST : e) {
                if (requested.startsWith(aAPI_COMPATIBILITY_LIST)) {
                    match = true;
                }
            }
            if (!match) {
                i.c("The requested version " + requested + " by your slf4j binding is not compatible with " + Arrays.asList(e).toString());
                i.c("See http://www.slf4j.org/codes.html#version_mismatch for further details.");
            }
        } catch (NoSuchFieldError e2) {
        } catch (Throwable e3) {
            i.d("Unexpected problem occured during version sanity check", e3);
        }
    }

    static Set<URL> f() {
        Enumeration<URL> paths;
        Set<URL> staticLoggerBinderPathSet = new LinkedHashSet<>();
        try {
            ClassLoader loggerFactoryClassLoader = c.class.getClassLoader();
            if (loggerFactoryClassLoader == null) {
                paths = ClassLoader.getSystemResources(f);
            } else {
                paths = loggerFactoryClassLoader.getResources(f);
            }
            while (paths.hasMoreElements()) {
                staticLoggerBinderPathSet.add(paths.nextElement());
            }
        } catch (IOException ioe) {
            i.d("Error getting resources from path", ioe);
        }
        return staticLoggerBinderPathSet;
    }

    private static boolean k(Set<URL> binderPathSet) {
        return binderPathSet.size() > 1;
    }

    private static void t(Set<URL> binderPathSet) {
        if (k(binderPathSet)) {
            i.c("Class path contains multiple SLF4J bindings.");
            for (URL path : binderPathSet) {
                i.c("Found binding in [" + path + "]");
            }
            i.c("See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.");
        }
    }

    private static boolean l() {
        String vendor = i.g("java.vendor.url");
        if (vendor == null) {
            return false;
        }
        return vendor.toLowerCase().contains("android");
    }

    private static void s(Set<URL> binderPathSet) {
        if (binderPathSet != null && k(binderPathSet)) {
            i.c("Actual binding is of type [" + StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr() + "]");
        }
    }

    public static b j(String name) {
        return h().a(name);
    }

    public static b i(Class<?> clazz) {
        Class<?> autoComputedCallingClass;
        b logger = j(clazz.getName());
        if (d && (autoComputedCallingClass = i.a()) != null && n(clazz, autoComputedCallingClass)) {
            i.c(String.format("Detected logger name mismatch. Given name: \"%s\"; computed name: \"%s\".", new Object[]{logger.getName(), autoComputedCallingClass.getName()}));
            i.c("See http://www.slf4j.org/codes.html#loggerNameMismatch for an explanation");
        }
        return logger;
    }

    private static boolean n(Class<?> clazz, Class<?> autoComputedCallingClass) {
        return !autoComputedCallingClass.isAssignableFrom(clazz);
    }

    public static a h() {
        if (a == 0) {
            synchronized (c.class) {
                if (a == 0) {
                    a = 1;
                    o();
                }
            }
        }
        switch (a) {
            case 1:
                return b;
            case 2:
                throw new IllegalStateException("org.slf4j.LoggerFactory in failed state. Original exception was thrown EARLIER. See also http://www.slf4j.org/codes.html#unsuccessfulInit");
            case 3:
                return StaticLoggerBinder.getSingleton().getLoggerFactory();
            case 4:
                return c;
            default:
                throw new IllegalStateException("Unreachable code");
        }
    }
}
