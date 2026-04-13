package io.netty.handler.codec;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.util.Signal;

public class DecoderResult {
    protected static final Signal SIGNAL_SUCCESS;
    protected static final Signal SIGNAL_UNFINISHED;
    public static final DecoderResult SUCCESS;
    public static final DecoderResult UNFINISHED;
    private final Throwable cause;

    static {
        Class<DecoderResult> cls = DecoderResult.class;
        Signal valueOf = Signal.valueOf(cls.getName() + ".UNFINISHED");
        SIGNAL_UNFINISHED = valueOf;
        Signal valueOf2 = Signal.valueOf(cls.getName() + ".SUCCESS");
        SIGNAL_SUCCESS = valueOf2;
        UNFINISHED = new DecoderResult(valueOf);
        SUCCESS = new DecoderResult(valueOf2);
    }

    public static DecoderResult failure(Throwable cause2) {
        if (cause2 != null) {
            return new DecoderResult(cause2);
        }
        throw new NullPointerException("cause");
    }

    protected DecoderResult(Throwable cause2) {
        if (cause2 != null) {
            this.cause = cause2;
            return;
        }
        throw new NullPointerException("cause");
    }

    public boolean isFinished() {
        return this.cause != SIGNAL_UNFINISHED;
    }

    public boolean isSuccess() {
        return this.cause == SIGNAL_SUCCESS;
    }

    public boolean isFailure() {
        Throwable th = this.cause;
        return (th == SIGNAL_SUCCESS || th == SIGNAL_UNFINISHED) ? false : true;
    }

    public Throwable cause() {
        if (isFailure()) {
            return this.cause;
        }
        return null;
    }

    public String toString() {
        if (!isFinished()) {
            return "unfinished";
        }
        if (isSuccess()) {
            return FirebaseAnalytics.Param.SUCCESS;
        }
        String cause2 = cause().toString();
        StringBuilder sb = new StringBuilder(cause2.length() + 17);
        sb.append("failure(");
        sb.append(cause2);
        sb.append(')');
        return sb.toString();
    }
}
