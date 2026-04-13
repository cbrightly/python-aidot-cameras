package io.netty.util.internal.logging;

import org.apache.commons.logging.h;

@Deprecated
public class CommonsLoggerFactory extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new CommonsLoggerFactory();

    public InternalLogger newInstance(String name) {
        return new CommonsLogger(h.o(name), name);
    }
}
