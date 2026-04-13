package io.netty.util.internal.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

public class Log4J2Logger extends ExtendedLoggerWrapper implements InternalLogger {
    private static final long serialVersionUID = 5485418394879791397L;

    Log4J2Logger(Logger logger) {
        super((ExtendedLogger) logger, logger.getName(), logger.getMessageFactory());
    }

    public String name() {
        return getName();
    }

    public boolean isEnabled(InternalLogLevel level) {
        return isEnabled(toLevel(level));
    }

    public void log(InternalLogLevel level, String msg) {
        log(toLevel(level), msg);
    }

    public void log(InternalLogLevel level, String format, Object arg) {
        log(toLevel(level), format, arg);
    }

    public void log(InternalLogLevel level, String format, Object argA, Object argB) {
        log(toLevel(level), format, argA, argB);
    }

    public void log(InternalLogLevel level, String format, Object... arguments) {
        log(toLevel(level), format, arguments);
    }

    public void log(InternalLogLevel level, String msg, Throwable t) {
        log(toLevel(level), msg, t);
    }

    /* renamed from: io.netty.util.internal.logging.Log4J2Logger$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$util$internal$logging$InternalLogLevel;

        static {
            int[] iArr = new int[InternalLogLevel.values().length];
            $SwitchMap$io$netty$util$internal$logging$InternalLogLevel = iArr;
            try {
                iArr[InternalLogLevel.INFO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$util$internal$logging$InternalLogLevel[InternalLogLevel.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$util$internal$logging$InternalLogLevel[InternalLogLevel.WARN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$netty$util$internal$logging$InternalLogLevel[InternalLogLevel.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$io$netty$util$internal$logging$InternalLogLevel[InternalLogLevel.TRACE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public Level toLevel(InternalLogLevel level) {
        switch (AnonymousClass1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[level.ordinal()]) {
            case 1:
                return Level.INFO;
            case 2:
                return Level.DEBUG;
            case 3:
                return Level.WARN;
            case 4:
                return Level.ERROR;
            case 5:
                return Level.TRACE;
            default:
                throw new Error();
        }
    }
}
