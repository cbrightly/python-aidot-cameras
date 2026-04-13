package io.netty.handler.codec.compression;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.Arrays;
import java.util.List;

public class SnappyFramedDecoder extends ByteToMessageDecoder {
    private static final int MAX_UNCOMPRESSED_DATA_SIZE = 65540;
    private static final byte[] SNAPPY = {115, 78, 97, 80, 112, 89};
    private boolean corrupted;
    private final Snappy snappy;
    private boolean started;
    private final boolean validateChecksums;

    public enum ChunkType {
        STREAM_IDENTIFIER,
        COMPRESSED_DATA,
        UNCOMPRESSED_DATA,
        RESERVED_UNSKIPPABLE,
        RESERVED_SKIPPABLE
    }

    public SnappyFramedDecoder() {
        this(false);
    }

    public SnappyFramedDecoder(boolean validateChecksums2) {
        this.snappy = new Snappy();
        this.validateChecksums = validateChecksums2;
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int oldWriterIndex;
        if (this.corrupted) {
            in.skipBytes(in.readableBytes());
            return;
        }
        try {
            int idx = in.readerIndex();
            int inSize = in.readableBytes();
            if (inSize >= 4) {
                int chunkTypeVal = in.getUnsignedByte(idx);
                ChunkType chunkType = mapChunkType((byte) chunkTypeVal);
                int chunkLength = ByteBufUtil.swapMedium(in.getUnsignedMedium(idx + 1));
                switch (AnonymousClass1.$SwitchMap$io$netty$handler$codec$compression$SnappyFramedDecoder$ChunkType[chunkType.ordinal()]) {
                    case 1:
                        byte[] bArr = SNAPPY;
                        if (chunkLength != bArr.length) {
                            throw new DecompressionException("Unexpected length of stream identifier: " + chunkLength);
                        } else if (inSize >= bArr.length + 4) {
                            byte[] identifier = new byte[chunkLength];
                            in.skipBytes(4).readBytes(identifier);
                            if (Arrays.equals(identifier, bArr)) {
                                this.started = true;
                                return;
                            }
                            throw new DecompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
                        } else {
                            return;
                        }
                    case 2:
                        if (!this.started) {
                            throw new DecompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
                        } else if (inSize >= chunkLength + 4) {
                            in.skipBytes(chunkLength + 4);
                            return;
                        } else {
                            return;
                        }
                    case 3:
                        throw new DecompressionException("Found reserved unskippable chunk type: 0x" + Integer.toHexString(chunkTypeVal));
                    case 4:
                        if (!this.started) {
                            throw new DecompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
                        } else if (chunkLength > 65540) {
                            throw new DecompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
                        } else if (inSize >= chunkLength + 4) {
                            in.skipBytes(4);
                            if (this.validateChecksums) {
                                Snappy.validateChecksum(ByteBufUtil.swapInt(in.readInt()), in, in.readerIndex(), chunkLength - 4);
                            } else {
                                in.skipBytes(4);
                            }
                            out.add(in.readSlice(chunkLength - 4).retain());
                            return;
                        } else {
                            return;
                        }
                    case 5:
                        if (!this.started) {
                            throw new DecompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
                        } else if (inSize >= chunkLength + 4) {
                            in.skipBytes(4);
                            int checksum = ByteBufUtil.swapInt(in.readInt());
                            ByteBuf uncompressed = ctx.alloc().buffer(0);
                            if (this.validateChecksums) {
                                oldWriterIndex = in.writerIndex();
                                in.writerIndex((in.readerIndex() + chunkLength) - 4);
                                this.snappy.decode(in, uncompressed);
                                in.writerIndex(oldWriterIndex);
                                Snappy.validateChecksum(checksum, uncompressed, 0, uncompressed.writerIndex());
                            } else {
                                this.snappy.decode(in.readSlice(chunkLength - 4), uncompressed);
                            }
                            out.add(uncompressed);
                            this.snappy.reset();
                            return;
                        } else {
                            return;
                        }
                    default:
                        return;
                }
                this.corrupted = true;
                throw e;
            }
        } catch (Exception e) {
            this.corrupted = true;
            throw e;
        } catch (Throwable th) {
            in.writerIndex(oldWriterIndex);
            throw th;
        }
    }

    /* renamed from: io.netty.handler.codec.compression.SnappyFramedDecoder$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$SnappyFramedDecoder$ChunkType;

        static {
            int[] iArr = new int[ChunkType.values().length];
            $SwitchMap$io$netty$handler$codec$compression$SnappyFramedDecoder$ChunkType = iArr;
            try {
                iArr[ChunkType.STREAM_IDENTIFIER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$SnappyFramedDecoder$ChunkType[ChunkType.RESERVED_SKIPPABLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$SnappyFramedDecoder$ChunkType[ChunkType.RESERVED_UNSKIPPABLE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$SnappyFramedDecoder$ChunkType[ChunkType.UNCOMPRESSED_DATA.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$SnappyFramedDecoder$ChunkType[ChunkType.COMPRESSED_DATA.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private static ChunkType mapChunkType(byte type) {
        if (type == 0) {
            return ChunkType.COMPRESSED_DATA;
        }
        if (type == 1) {
            return ChunkType.UNCOMPRESSED_DATA;
        }
        if (type == -1) {
            return ChunkType.STREAM_IDENTIFIER;
        }
        if ((type & OTACommand.RESP_ID_VERSION) == 128) {
            return ChunkType.RESERVED_SKIPPABLE;
        }
        return ChunkType.RESERVED_UNSKIPPABLE;
    }
}
