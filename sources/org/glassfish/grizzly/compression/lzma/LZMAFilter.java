package org.glassfish.grizzly.compression.lzma;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class LZMAFilter extends BaseFilter {
    private final LZMADecoder decoder = new LZMADecoder();
    private final LZMAEncoder encoder = new LZMAEncoder();

    public NextAction handleRead(FilterChainContext ctx) {
        Connection connection = ctx.getConnection();
        Buffer input = (Buffer) ctx.getMessage();
        TransformationResult<Buffer, Buffer> result = this.decoder.transform(connection, input);
        Buffer remainder = result.getExternalRemainder();
        if (remainder == null) {
            input.tryDispose();
        } else {
            input.shrink();
        }
        try {
            switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$TransformationResult$Status[result.getStatus().ordinal()]) {
                case 1:
                    ctx.setMessage(result.getMessage());
                    this.decoder.finish(connection);
                    NextAction invokeAction = ctx.getInvokeAction(remainder);
                    result.recycle();
                    return invokeAction;
                case 2:
                    return ctx.getStopAction(remainder);
                case 3:
                    throw new IllegalStateException("LZMA decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
                default:
                    throw new IllegalStateException("Unexpected status: " + result.getStatus());
            }
        } finally {
            result.recycle();
        }
        result.recycle();
    }

    /* renamed from: org.glassfish.grizzly.compression.lzma.LZMAFilter$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$TransformationResult$Status;

        static {
            int[] iArr = new int[TransformationResult.Status.values().length];
            $SwitchMap$org$glassfish$grizzly$TransformationResult$Status = iArr;
            try {
                iArr[TransformationResult.Status.COMPLETE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$TransformationResult$Status[TransformationResult.Status.INCOMPLETE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$TransformationResult$Status[TransformationResult.Status.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        Connection connection = ctx.getConnection();
        Buffer input = (Buffer) ctx.getMessage();
        TransformationResult<Buffer, Buffer> result = this.encoder.transform(connection, input);
        if (!input.hasRemaining()) {
            input.tryDispose();
        } else {
            input.shrink();
        }
        try {
            switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$TransformationResult$Status[result.getStatus().ordinal()]) {
                case 1:
                    this.encoder.finish(connection);
                    break;
                case 2:
                    break;
                case 3:
                    throw new IllegalStateException("LZMA encode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
                default:
                    throw new IllegalStateException("Unexpected status: " + result.getStatus());
            }
            Buffer readyBuffer = result.getMessage();
            if (readyBuffer != null) {
                ctx.setMessage(readyBuffer);
                return ctx.getInvokeAction();
            }
            NextAction stopAction = ctx.getStopAction();
            result.recycle();
            return stopAction;
        } finally {
            result.recycle();
        }
    }

    public NextAction handleClose(FilterChainContext ctx) {
        Connection connection = ctx.getConnection();
        this.decoder.release(connection);
        this.encoder.release(connection);
        return super.handleClose(ctx);
    }
}
