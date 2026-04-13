package org.glassfish.grizzly.compression.zip;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.memory.Buffers;

public class GZipFilter extends BaseFilter {
    private final GZipDecoder decoder;
    private final GZipEncoder encoder;

    public GZipFilter() {
        this(512, 512);
    }

    public GZipFilter(int inBufferSize, int outBufferSize) {
        this.decoder = new GZipDecoder(inBufferSize);
        this.encoder = new GZipEncoder(outBufferSize);
    }

    public NextAction handleClose(FilterChainContext ctx) {
        Connection connection = ctx.getConnection();
        this.decoder.release(connection);
        this.encoder.release(connection);
        return super.handleClose(ctx);
    }

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
                    NextAction invokeAction = ctx.getInvokeAction(remainder);
                    result.recycle();
                    return invokeAction;
                case 2:
                    return ctx.getStopAction(remainder);
                case 3:
                    throw new IllegalStateException("GZip decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
                default:
                    throw new IllegalStateException("Unexpected status: " + result.getStatus());
            }
        } finally {
            result.recycle();
        }
        result.recycle();
    }

    /* renamed from: org.glassfish.grizzly.compression.zip.GZipFilter$1  reason: invalid class name */
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
        input.dispose();
        try {
            switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$TransformationResult$Status[result.getStatus().ordinal()]) {
                case 1:
                case 2:
                    Buffer finishBuffer = this.encoder.finish(connection);
                    Buffer resultBuffer = Buffers.appendBuffers(connection.getMemoryManager(), result.getMessage(), finishBuffer);
                    if (resultBuffer != null) {
                        ctx.setMessage(resultBuffer);
                        return ctx.getInvokeAction();
                    }
                    NextAction stopAction = ctx.getStopAction();
                    result.recycle();
                    return stopAction;
                case 3:
                    throw new IllegalStateException("GZip decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
                default:
                    throw new IllegalStateException("Unexpected status: " + result.getStatus());
            }
        } finally {
            result.recycle();
        }
        result.recycle();
    }
}
