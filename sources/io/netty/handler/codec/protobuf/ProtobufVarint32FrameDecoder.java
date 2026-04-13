package io.netty.handler.codec.protobuf;

import com.google.protobuf.CodedInputStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

public class ProtobufVarint32FrameDecoder extends ByteToMessageDecoder {
    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        in.markReaderIndex();
        byte[] buf = new byte[5];
        int i = 0;
        while (i < buf.length) {
            if (!in.isReadable()) {
                in.resetReaderIndex();
                return;
            }
            buf[i] = in.readByte();
            if (buf[i] >= 0) {
                int length = CodedInputStream.newInstance(buf, 0, i + 1).readRawVarint32();
                if (length < 0) {
                    throw new CorruptedFrameException("negative length: " + length);
                } else if (in.readableBytes() < length) {
                    in.resetReaderIndex();
                    return;
                } else {
                    out.add(in.readSlice(length).retain());
                    return;
                }
            } else {
                i++;
            }
        }
        throw new CorruptedFrameException("length wider than 32-bit");
    }
}
