package io.netty.handler.codec;

import io.netty.util.internal.ObjectUtil;

public final class ProtocolDetectionResult<T> {
    private static final ProtocolDetectionResult INVALID = new ProtocolDetectionResult(ProtocolDetectionState.INVALID, (Object) null);
    private static final ProtocolDetectionResult NEEDS_MORE_DATE = new ProtocolDetectionResult(ProtocolDetectionState.NEEDS_MORE_DATA, (Object) null);
    private final T result;
    private final ProtocolDetectionState state;

    public static <T> ProtocolDetectionResult<T> needsMoreData() {
        return NEEDS_MORE_DATE;
    }

    public static <T> ProtocolDetectionResult<T> invalid() {
        return INVALID;
    }

    public static <T> ProtocolDetectionResult<T> detected(T protocol) {
        return new ProtocolDetectionResult<>(ProtocolDetectionState.DETECTED, ObjectUtil.checkNotNull(protocol, "protocol"));
    }

    private ProtocolDetectionResult(ProtocolDetectionState state2, T result2) {
        this.state = state2;
        this.result = result2;
    }

    public ProtocolDetectionState state() {
        return this.state;
    }

    public T detectedProtocol() {
        return this.result;
    }
}
