package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.util.concurrent.EventExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

public class JdkZlibEncoder extends ZlibEncoder {
    private static final byte[] gzipHeader = {31, -117, 8, 0, 0, 0, 0, 0, 0, 0};
    private final CRC32 crc;
    private volatile ChannelHandlerContext ctx;
    private final Deflater deflater;
    private volatile boolean finished;
    private final ZlibWrapper wrapper;
    private boolean writeHeader;

    public JdkZlibEncoder() {
        this(6);
    }

    public JdkZlibEncoder(int compressionLevel) {
        this(ZlibWrapper.ZLIB, compressionLevel);
    }

    public JdkZlibEncoder(ZlibWrapper wrapper2) {
        this(wrapper2, 6);
    }

    public JdkZlibEncoder(ZlibWrapper wrapper2, int compressionLevel) {
        this.crc = new CRC32();
        boolean z = true;
        this.writeHeader = true;
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        } else if (wrapper2 != null) {
            ZlibWrapper zlibWrapper = ZlibWrapper.ZLIB_OR_NONE;
            if (wrapper2 != zlibWrapper) {
                this.wrapper = wrapper2;
                this.deflater = new Deflater(compressionLevel, wrapper2 == ZlibWrapper.ZLIB ? false : z);
                return;
            }
            throw new IllegalArgumentException("wrapper '" + zlibWrapper + "' is not allowed for compression.");
        } else {
            throw new NullPointerException("wrapper");
        }
    }

    public JdkZlibEncoder(byte[] dictionary) {
        this(6, dictionary);
    }

    public JdkZlibEncoder(int compressionLevel, byte[] dictionary) {
        this.crc = new CRC32();
        this.writeHeader = true;
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        } else if (dictionary != null) {
            this.wrapper = ZlibWrapper.ZLIB;
            Deflater deflater2 = new Deflater(compressionLevel);
            this.deflater = deflater2;
            deflater2.setDictionary(dictionary);
        } else {
            throw new NullPointerException("dictionary");
        }
    }

    public ChannelFuture close() {
        return close(ctx().newPromise());
    }

    public ChannelFuture close(final ChannelPromise promise) {
        ChannelHandlerContext ctx2 = ctx();
        EventExecutor executor = ctx2.executor();
        if (executor.inEventLoop()) {
            return finishEncode(ctx2, promise);
        }
        final ChannelPromise p = ctx2.newPromise();
        executor.execute(new Runnable() {
            public void run() {
                JdkZlibEncoder jdkZlibEncoder = JdkZlibEncoder.this;
                jdkZlibEncoder.finishEncode(jdkZlibEncoder.ctx(), p).addListener(new ChannelPromiseNotifier(promise));
            }
        });
        return p;
    }

    /* access modifiers changed from: private */
    public ChannelHandlerContext ctx() {
        ChannelHandlerContext ctx2 = this.ctx;
        if (ctx2 != null) {
            return ctx2;
        }
        throw new IllegalStateException("not added to a pipeline");
    }

    public boolean isClosed() {
        return this.finished;
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext ctx2, ByteBuf uncompressed, ByteBuf out) {
        int offset;
        byte[] inAry;
        if (this.finished) {
            out.writeBytes(uncompressed);
            return;
        }
        int len = uncompressed.readableBytes();
        if (len != 0) {
            if (uncompressed.hasArray()) {
                inAry = uncompressed.array();
                offset = uncompressed.arrayOffset() + uncompressed.readerIndex();
                uncompressed.skipBytes(len);
            } else {
                inAry = new byte[len];
                uncompressed.readBytes(inAry);
                offset = 0;
            }
            if (this.writeHeader) {
                this.writeHeader = false;
                if (this.wrapper == ZlibWrapper.GZIP) {
                    out.writeBytes(gzipHeader);
                }
            }
            if (this.wrapper == ZlibWrapper.GZIP) {
                this.crc.update(inAry, offset, len);
            }
            this.deflater.setInput(inAry, offset, len);
            while (!this.deflater.needsInput()) {
                deflate(out);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final ByteBuf allocateBuffer(ChannelHandlerContext ctx2, ByteBuf msg, boolean preferDirect) {
        int sizeEstimate = ((int) Math.ceil(((double) msg.readableBytes()) * 1.001d)) + 12;
        if (this.writeHeader) {
            switch (AnonymousClass4.$SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[this.wrapper.ordinal()]) {
                case 1:
                    sizeEstimate += gzipHeader.length;
                    break;
                case 2:
                    sizeEstimate += 2;
                    break;
            }
        }
        return ctx2.alloc().heapBuffer(sizeEstimate);
    }

    /* renamed from: io.netty.handler.codec.compression.JdkZlibEncoder$4  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper;

        static {
            int[] iArr = new int[ZlibWrapper.values().length];
            $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper = iArr;
            try {
                iArr[ZlibWrapper.GZIP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[ZlibWrapper.ZLIB.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public void close(final ChannelHandlerContext ctx2, final ChannelPromise promise) {
        ChannelFuture f = finishEncode(ctx2, ctx2.newPromise());
        f.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture f) {
                ctx2.close(promise);
            }
        });
        if (!f.isDone()) {
            ctx2.executor().schedule((Runnable) new Runnable() {
                public void run() {
                    ctx2.close(promise);
                }
            }, 10, TimeUnit.SECONDS);
        }
    }

    /* access modifiers changed from: private */
    public ChannelFuture finishEncode(ChannelHandlerContext ctx2, ChannelPromise promise) {
        if (this.finished) {
            promise.setSuccess();
            return promise;
        }
        this.finished = true;
        ByteBuf footer = ctx2.alloc().heapBuffer();
        if (this.writeHeader && this.wrapper == ZlibWrapper.GZIP) {
            this.writeHeader = false;
            footer.writeBytes(gzipHeader);
        }
        this.deflater.finish();
        while (!this.deflater.finished()) {
            deflate(footer);
            if (!footer.isWritable()) {
                ctx2.write(footer);
                footer = ctx2.alloc().heapBuffer();
            }
        }
        if (this.wrapper == ZlibWrapper.GZIP) {
            int crcValue = (int) this.crc.getValue();
            int uncBytes = this.deflater.getTotalIn();
            footer.writeByte(crcValue);
            footer.writeByte(crcValue >>> 8);
            footer.writeByte(crcValue >>> 16);
            footer.writeByte(crcValue >>> 24);
            footer.writeByte(uncBytes);
            footer.writeByte(uncBytes >>> 8);
            footer.writeByte(uncBytes >>> 16);
            footer.writeByte(uncBytes >>> 24);
        }
        this.deflater.end();
        return ctx2.writeAndFlush(footer, promise);
    }

    private void deflate(ByteBuf out) {
        int numBytes;
        do {
            int writerIndex = out.writerIndex();
            numBytes = this.deflater.deflate(out.array(), out.arrayOffset() + writerIndex, out.writableBytes(), 2);
            out.writerIndex(writerIndex + numBytes);
        } while (numBytes > 0);
    }

    public void handlerAdded(ChannelHandlerContext ctx2) {
        this.ctx = ctx2;
    }
}
