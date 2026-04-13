package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.DomainNameMapping;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.IDN;
import java.util.List;
import java.util.Locale;

public class SniHandler extends ByteToMessageDecoder {
    private static final Selection EMPTY_SELECTION = new Selection((SslContext) null, (String) null);
    private static final int MAX_SSL_RECORDS = 4;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) SniHandler.class);
    private boolean handshakeFailed;
    private final DomainNameMapping<SslContext> mapping;
    private volatile Selection selection = EMPTY_SELECTION;

    public SniHandler(DomainNameMapping<? extends SslContext> mapping2) {
        if (mapping2 != null) {
            this.mapping = mapping2;
            return;
        }
        throw new NullPointerException("mapping");
    }

    public String hostname() {
        return this.selection.hostname;
    }

    public SslContext sslContext() {
        return this.selection.context;
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) {
        ChannelHandlerContext channelHandlerContext = ctx;
        ByteBuf byteBuf = in;
        if (!this.handshakeFailed) {
            int writerIndex = in.writerIndex();
            int i = 0;
            while (true) {
                int i2 = 4;
                if (i < 4) {
                    try {
                        int readerIndex = in.readerIndex();
                        int readableBytes = writerIndex - readerIndex;
                        if (readableBytes >= 5) {
                            switch (byteBuf.getUnsignedByte(readerIndex)) {
                                case 20:
                                case 21:
                                    int len = SslUtils.getEncryptedPacketLength(byteBuf, readerIndex);
                                    if (len == -2) {
                                        this.handshakeFailed = true;
                                        NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
                                        byteBuf.skipBytes(in.readableBytes());
                                        SslUtils.notifyHandshakeFailure(channelHandlerContext, e, true);
                                        throw e;
                                    } else if (len == -1) {
                                        return;
                                    } else {
                                        if ((writerIndex - readerIndex) - 5 >= len) {
                                            byteBuf.skipBytes(len);
                                            i++;
                                        } else {
                                            return;
                                        }
                                    }
                                case 22:
                                    if (byteBuf.getUnsignedByte(readerIndex + 1) != 3) {
                                        break;
                                    } else {
                                        int packetLength = byteBuf.getUnsignedShort(readerIndex + 3) + 5;
                                        if (readableBytes >= packetLength) {
                                            int endOffset = readerIndex + packetLength;
                                            int offset = readerIndex + 43;
                                            if (endOffset - offset < 6) {
                                                break;
                                            } else {
                                                int offset2 = offset + byteBuf.getUnsignedByte(offset) + 1;
                                                int offset3 = offset2 + byteBuf.getUnsignedShort(offset2) + 2;
                                                int offset4 = offset3 + byteBuf.getUnsignedByte(offset3) + 1;
                                                int extensionsLength = byteBuf.getUnsignedShort(offset4);
                                                int offset5 = offset4 + 2;
                                                int extensionsLimit = offset5 + extensionsLength;
                                                if (extensionsLimit > endOffset) {
                                                    break;
                                                } else {
                                                    while (true) {
                                                        int readableBytes2 = readableBytes;
                                                        if (extensionsLimit - offset5 < i2) {
                                                            break;
                                                        } else {
                                                            int extensionType = byteBuf.getUnsignedShort(offset5);
                                                            int offset6 = offset5 + 2;
                                                            int offset7 = offset6 + 2;
                                                            int i3 = extensionsLimit - offset7;
                                                            int endOffset2 = endOffset;
                                                            int endOffset3 = byteBuf.getUnsignedShort(offset6);
                                                            if (i3 < endOffset3) {
                                                                break;
                                                            } else if (extensionType == 0) {
                                                                int offset8 = offset7 + 2;
                                                                int i4 = extensionType;
                                                                if (extensionsLimit - offset8 >= 3) {
                                                                    int unsignedByte = byteBuf.getUnsignedByte(offset8);
                                                                    int offset9 = offset8 + 1;
                                                                    if (unsignedByte != 0) {
                                                                        int serverNameType = unsignedByte;
                                                                        break;
                                                                    } else {
                                                                        int serverNameLength = byteBuf.getUnsignedShort(offset9);
                                                                        int offset10 = offset9 + 2;
                                                                        short s = unsignedByte;
                                                                        int serverNameType2 = serverNameLength;
                                                                        if (extensionsLimit - offset10 < serverNameType2) {
                                                                            break;
                                                                        } else {
                                                                            int i5 = serverNameType2;
                                                                            String hostname = byteBuf.toString(offset10, serverNameType2, CharsetUtil.UTF_8);
                                                                            try {
                                                                                String str = hostname;
                                                                                try {
                                                                                    select(channelHandlerContext, IDN.toASCII(hostname, 1).toLowerCase(Locale.US));
                                                                                    return;
                                                                                } catch (Throwable th) {
                                                                                    t = th;
                                                                                }
                                                                            } catch (Throwable th2) {
                                                                                t = th2;
                                                                                String str2 = hostname;
                                                                                PlatformDependent.throwException(t);
                                                                                return;
                                                                            }
                                                                        }
                                                                    }
                                                                } else {
                                                                    break;
                                                                }
                                                            } else {
                                                                offset5 = offset7 + endOffset3;
                                                                readableBytes = readableBytes2;
                                                                endOffset = endOffset2;
                                                                i2 = 4;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            return;
                                        }
                                    }
                                default:
                                    int i6 = readableBytes;
                                    break;
                            }
                        } else {
                            return;
                        }
                    } catch (NotSslRecordException e2) {
                        throw e2;
                    } catch (Exception e3) {
                        InternalLogger internalLogger = logger;
                        if (internalLogger.isDebugEnabled()) {
                            internalLogger.debug("Unexpected client hello packet: " + ByteBufUtil.hexDump(in), (Throwable) e3);
                        }
                    }
                }
            }
            select(channelHandlerContext, (String) null);
        }
    }

    private void select(ChannelHandlerContext ctx, String hostname) {
        SslHandler sslHandler = null;
        this.selection = new Selection(this.mapping.map(hostname), hostname);
        try {
            sslHandler = this.selection.context.newHandler(ctx.alloc());
            ctx.pipeline().replace((ChannelHandler) this, SslHandler.class.getName(), (ChannelHandler) sslHandler);
        } catch (Throwable cause) {
            this.selection = EMPTY_SELECTION;
            if (sslHandler != null) {
                ReferenceCountUtil.safeRelease(sslHandler.engine());
            }
            PlatformDependent.throwException(cause);
        }
    }

    public static final class Selection {
        final SslContext context;
        final String hostname;

        Selection(SslContext context2, String hostname2) {
            this.context = context2;
            this.hostname = hostname2;
        }
    }
}
