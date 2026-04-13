package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.Transformer;

public abstract class AbstractCodecFilter<K, L> extends BaseFilter implements CodecFilter<K, L> {
    private final Transformer<K, L> decoder;
    private final Transformer<L, K> encoder;

    public AbstractCodecFilter(Transformer<K, L> decoder2, Transformer<L, K> encoder2) {
        this.decoder = decoder2;
        this.encoder = encoder2;
    }

    public NextAction handleRead(FilterChainContext ctx) {
        Connection connection = ctx.getConnection();
        K message = ctx.getMessage();
        TransformationResult<K, L> result = this.decoder.transform(connection, message);
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$TransformationResult$Status[result.getStatus().ordinal()]) {
            case 1:
                K remainder = result.getExternalRemainder();
                boolean hasRemaining = this.decoder.hasInputRemaining(connection, remainder);
                this.decoder.release(connection);
                ctx.setMessage(result.getMessage());
                return hasRemaining ? ctx.getInvokeAction(remainder) : ctx.getInvokeAction();
            case 2:
                return ctx.getStopAction(message);
            case 3:
                throw new TransformationException(getClass().getName() + " transformation error: (" + result.getErrorCode() + ") " + result.getErrorDescription());
            default:
                return ctx.getInvokeAction();
        }
    }

    /* renamed from: org.glassfish.grizzly.filterchain.AbstractCodecFilter$1  reason: invalid class name */
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
        L message = ctx.getMessage();
        TransformationResult<L, K> result = this.encoder.transform(connection, message);
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$TransformationResult$Status[result.getStatus().ordinal()]) {
            case 1:
                ctx.setMessage(result.getMessage());
                L remainder = result.getExternalRemainder();
                boolean hasRemaining = this.encoder.hasInputRemaining(connection, remainder);
                this.encoder.release(connection);
                return hasRemaining ? ctx.getInvokeAction(remainder) : ctx.getInvokeAction();
            case 2:
                return ctx.getStopAction(message);
            case 3:
                throw new TransformationException(getClass().getName() + " transformation error: (" + result.getErrorCode() + ") " + result.getErrorDescription());
            default:
                return ctx.getInvokeAction();
        }
    }

    public Transformer<K, L> getDecoder() {
        return this.decoder;
    }

    public Transformer<L, K> getEncoder() {
        return this.encoder;
    }
}
