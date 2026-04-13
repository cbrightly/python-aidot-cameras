package io.netty.util.internal.logging;

import io.netty.util.internal.StringUtil;
import java.io.Serializable;

public abstract class AbstractInternalLogger implements InternalLogger, Serializable {
    private static final long serialVersionUID = -6382972526573193470L;
    private final String name;

    protected AbstractInternalLogger(String name2) {
        if (name2 != null) {
            this.name = name2;
            return;
        }
        throw new NullPointerException("name");
    }

    public String name() {
        return this.name;
    }

    /* renamed from: io.netty.util.internal.logging.AbstractInternalLogger$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$util$internal$logging$InternalLogLevel;

        static {
            int[] iArr = new int[InternalLogLevel.values().length];
            $SwitchMap$io$netty$util$internal$logging$InternalLogLevel = iArr;
            try {
                iArr[InternalLogLevel.TRACE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$util$internal$logging$InternalLogLevel[InternalLogLevel.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$util$internal$logging$InternalLogLevel[InternalLogLevel.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$netty$util$internal$logging$InternalLogLevel[InternalLogLevel.WARN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$io$netty$util$internal$logging$InternalLogLevel[InternalLogLevel.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public boolean isEnabled(InternalLogLevel level) {
        switch (AnonymousClass1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[level.ordinal()]) {
            case 1:
                return isTraceEnabled();
            case 2:
                return isDebugEnabled();
            case 3:
                return isInfoEnabled();
            case 4:
                return isWarnEnabled();
            case 5:
                return isErrorEnabled();
            default:
                throw new Error();
        }
    }

    public void log(InternalLogLevel level, String msg, Throwable cause) {
        switch (AnonymousClass1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[level.ordinal()]) {
            case 1:
                trace(msg, cause);
                return;
            case 2:
                debug(msg, cause);
                return;
            case 3:
                info(msg, cause);
                return;
            case 4:
                warn(msg, cause);
                return;
            case 5:
                error(msg, cause);
                return;
            default:
                throw new Error();
        }
    }

    public void log(InternalLogLevel level, String msg) {
        switch (AnonymousClass1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[level.ordinal()]) {
            case 1:
                trace(msg);
                return;
            case 2:
                debug(msg);
                return;
            case 3:
                info(msg);
                return;
            case 4:
                warn(msg);
                return;
            case 5:
                error(msg);
                return;
            default:
                throw new Error();
        }
    }

    public void log(InternalLogLevel level, String format, Object arg) {
        switch (AnonymousClass1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[level.ordinal()]) {
            case 1:
                trace(format, arg);
                return;
            case 2:
                debug(format, arg);
                return;
            case 3:
                info(format, arg);
                return;
            case 4:
                warn(format, arg);
                return;
            case 5:
                error(format, arg);
                return;
            default:
                throw new Error();
        }
    }

    public void log(InternalLogLevel level, String format, Object argA, Object argB) {
        switch (AnonymousClass1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[level.ordinal()]) {
            case 1:
                trace(format, argA, argB);
                return;
            case 2:
                debug(format, argA, argB);
                return;
            case 3:
                info(format, argA, argB);
                return;
            case 4:
                warn(format, argA, argB);
                return;
            case 5:
                error(format, argA, argB);
                return;
            default:
                throw new Error();
        }
    }

    public void log(InternalLogLevel level, String format, Object... arguments) {
        switch (AnonymousClass1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[level.ordinal()]) {
            case 1:
                trace(format, arguments);
                return;
            case 2:
                debug(format, arguments);
                return;
            case 3:
                info(format, arguments);
                return;
            case 4:
                warn(format, arguments);
                return;
            case 5:
                error(format, arguments);
                return;
            default:
                throw new Error();
        }
    }

    /* access modifiers changed from: protected */
    public Object readResolve() {
        return InternalLoggerFactory.getInstance(name());
    }

    public String toString() {
        return StringUtil.simpleClassName((Object) this) + '(' + name() + ')';
    }
}
