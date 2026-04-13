package io.netty.util.internal.logging;

public abstract class InternalLoggerFactory {
    private static volatile InternalLoggerFactory defaultFactory;

    /* access modifiers changed from: protected */
    public abstract InternalLogger newInstance(String str);

    private static InternalLoggerFactory newDefaultFactory(String name) {
        try {
            InternalLoggerFactory f = new Slf4JLoggerFactory(true);
            f.newInstance(name).debug("Using SLF4J as the default logging framework");
            return f;
        } catch (Throwable th) {
            InternalLoggerFactory f2 = JdkLoggerFactory.INSTANCE;
            f2.newInstance(name).debug("Using java.util.logging as the default logging framework");
            return f2;
        }
    }

    public static InternalLoggerFactory getDefaultFactory() {
        if (defaultFactory == null) {
            defaultFactory = newDefaultFactory(InternalLoggerFactory.class.getName());
        }
        return defaultFactory;
    }

    public static void setDefaultFactory(InternalLoggerFactory defaultFactory2) {
        if (defaultFactory2 != null) {
            defaultFactory = defaultFactory2;
            return;
        }
        throw new NullPointerException("defaultFactory");
    }

    public static InternalLogger getInstance(Class<?> clazz) {
        return getInstance(clazz.getName());
    }

    public static InternalLogger getInstance(String name) {
        return getDefaultFactory().newInstance(name);
    }
}
