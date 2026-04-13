package io.netty.handler.codec.compression;

import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

public class JZlibDecoder extends ZlibDecoder {
    private byte[] dictionary;
    private volatile boolean finished;
    private final Inflater z;

    public JZlibDecoder() {
        this(ZlibWrapper.ZLIB);
    }

    public JZlibDecoder(ZlibWrapper wrapper) {
        Inflater inflater = new Inflater();
        this.z = inflater;
        if (wrapper != null) {
            int resultCode = inflater.init(ZlibUtil.convertWrapperType(wrapper));
            if (resultCode != 0) {
                ZlibUtil.fail(inflater, "initialization failure", resultCode);
                return;
            }
            return;
        }
        throw new NullPointerException("wrapper");
    }

    public JZlibDecoder(byte[] dictionary2) {
        Inflater inflater = new Inflater();
        this.z = inflater;
        if (dictionary2 != null) {
            this.dictionary = dictionary2;
            int resultCode = inflater.inflateInit(JZlib.W_ZLIB);
            if (resultCode != 0) {
                ZlibUtil.fail(inflater, "initialization failure", resultCode);
                return;
            }
            return;
        }
        throw new NullPointerException("dictionary");
    }

    public boolean isClosed() {
        return this.finished;
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int oldNextInIndex;
        ByteBuf decompressed;
        if (this.finished) {
            in.skipBytes(in.readableBytes());
            return;
        }
        int inputLength = in.readableBytes();
        if (inputLength != 0) {
            try {
                this.z.avail_in = inputLength;
                if (in.hasArray()) {
                    this.z.next_in = in.array();
                    this.z.next_in_index = in.arrayOffset() + in.readerIndex();
                } else {
                    byte[] array = new byte[inputLength];
                    in.getBytes(in.readerIndex(), array);
                    this.z.next_in = array;
                    this.z.next_in_index = 0;
                }
                oldNextInIndex = this.z.next_in_index;
                decompressed = ctx.alloc().heapBuffer(inputLength << 1);
                while (true) {
                    decompressed.ensureWritable(this.z.avail_in << 1);
                    this.z.avail_out = decompressed.writableBytes();
                    this.z.next_out = decompressed.array();
                    this.z.next_out_index = decompressed.arrayOffset() + decompressed.writerIndex();
                    int oldNextOutIndex = this.z.next_out_index;
                    int resultCode = this.z.inflate(2);
                    int outputLength = this.z.next_out_index - oldNextOutIndex;
                    if (outputLength > 0) {
                        decompressed.writerIndex(decompressed.writerIndex() + outputLength);
                    }
                    switch (resultCode) {
                        case -5:
                            if (this.z.avail_in > 0) {
                                continue;
                            }
                            break;
                        case 0:
                            continue;
                        case 1:
                            this.finished = true;
                            this.z.inflateEnd();
                            break;
                        case 2:
                            byte[] bArr = this.dictionary;
                            if (bArr != null) {
                                int resultCode2 = this.z.inflateSetDictionary(bArr, bArr.length);
                                if (resultCode2 == 0) {
                                    break;
                                } else {
                                    ZlibUtil.fail(this.z, "failed to set the dictionary", resultCode2);
                                    break;
                                }
                            } else {
                                ZlibUtil.fail(this.z, "decompression failure", resultCode);
                                continue;
                            }
                        default:
                            ZlibUtil.fail(this.z, "decompression failure", resultCode);
                            continue;
                    }
                }
                in.skipBytes(this.z.next_in_index - oldNextInIndex);
                if (decompressed.isReadable()) {
                    out.add(decompressed);
                } else {
                    decompressed.release();
                }
                this.z.next_in = null;
                this.z.next_out = null;
            } catch (Throwable th) {
                this.z.next_in = null;
                this.z.next_out = null;
                throw th;
            }
        }
    }
}
