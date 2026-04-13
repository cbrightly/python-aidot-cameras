package io.netty.util.internal.logging;

import org.slf4j.c;
import org.slf4j.helpers.d;

public class Slf4JLoggerFactory extends InternalLoggerFactory {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final InternalLoggerFactory INSTANCE = new Slf4JLoggerFactory();

    @Deprecated
    public Slf4JLoggerFactory() {
    }

    Slf4JLoggerFactory(boolean failIfNOP) {
        if (!failIfNOP) {
            throw new AssertionError();
        } else if (c.h() instanceof d) {
            throw new NoClassDefFoundError("NOPLoggerFactory not supported");
        }
    }

    public InternalLogger newInstance(String name) {
        return new Slf4JLogger(c.j(name));
    }
}
