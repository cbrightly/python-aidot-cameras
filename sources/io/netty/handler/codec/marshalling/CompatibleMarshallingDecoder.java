package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.marshalling.LimitingByteInput;
import java.util.List;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

public class CompatibleMarshallingDecoder extends ReplayingDecoder<Void> {
    private boolean discardingTooLongFrame;
    protected final int maxObjectSize;
    protected final UnmarshallerProvider provider;

    public CompatibleMarshallingDecoder(UnmarshallerProvider provider2, int maxObjectSize2) {
        this.provider = provider2;
        this.maxObjectSize = maxObjectSize2;
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
        if (this.discardingTooLongFrame) {
            buffer.skipBytes(actualReadableBytes());
            checkpoint();
            return;
        }
        Unmarshaller unmarshaller = this.provider.getUnmarshaller(ctx);
        ByteInput input = new ChannelBufferByteInput(buffer);
        if (this.maxObjectSize != Integer.MAX_VALUE) {
            input = new LimitingByteInput(input, (long) this.maxObjectSize);
        }
        try {
            unmarshaller.start(input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            out.add(obj);
            unmarshaller.close();
        } catch (LimitingByteInput.TooBigObjectException e) {
            this.discardingTooLongFrame = true;
            throw new TooLongFrameException();
        } catch (Throwable th) {
            unmarshaller.close();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void decodeLast(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
        switch (buffer.readableBytes()) {
            case 0:
                return;
            case 1:
                if (buffer.getByte(buffer.readerIndex()) == 121) {
                    buffer.skipBytes(1);
                    return;
                }
                break;
        }
        decode(ctx, buffer, out);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof TooLongFrameException) {
            ctx.close();
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }
}
