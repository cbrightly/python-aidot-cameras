package io.netty.handler.codec.base64;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteOrder;
import org.glassfish.grizzly.http.server.util.MappingData;

public final class Base64 {
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    private static final byte WHITE_SPACE_ENC = -5;

    private static byte[] alphabet(Base64Dialect dialect) {
        if (dialect != null) {
            return dialect.alphabet;
        }
        throw new NullPointerException("dialect");
    }

    /* access modifiers changed from: private */
    public static byte[] decodabet(Base64Dialect dialect) {
        if (dialect != null) {
            return dialect.decodabet;
        }
        throw new NullPointerException("dialect");
    }

    private static boolean breakLines(Base64Dialect dialect) {
        if (dialect != null) {
            return dialect.breakLinesByDefault;
        }
        throw new NullPointerException("dialect");
    }

    public static ByteBuf encode(ByteBuf src) {
        return encode(src, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf src, Base64Dialect dialect) {
        return encode(src, breakLines(dialect), dialect);
    }

    public static ByteBuf encode(ByteBuf src, boolean breakLines) {
        return encode(src, breakLines, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf src, boolean breakLines, Base64Dialect dialect) {
        if (src != null) {
            ByteBuf dest = encode(src, src.readerIndex(), src.readableBytes(), breakLines, dialect);
            src.readerIndex(src.writerIndex());
            return dest;
        }
        throw new NullPointerException("src");
    }

    public static ByteBuf encode(ByteBuf src, int off, int len) {
        return encode(src, off, len, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf src, int off, int len, Base64Dialect dialect) {
        return encode(src, off, len, breakLines(dialect), dialect);
    }

    public static ByteBuf encode(ByteBuf src, int off, int len, boolean breakLines) {
        return encode(src, off, len, breakLines, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf src, int off, int len, boolean breakLines, Base64Dialect dialect) {
        return encode(src, off, len, breakLines, dialect, src.alloc());
    }

    public static ByteBuf encode(ByteBuf src, int off, int len, boolean breakLines, Base64Dialect dialect, ByteBufAllocator allocator) {
        int i = len;
        if (src == null) {
            ByteBufAllocator byteBufAllocator = allocator;
            throw new NullPointerException("src");
        } else if (dialect != null) {
            ByteBuf dest = allocator.buffer(encodedBufferSize(len, breakLines)).order(src.order());
            byte[] alphabet = alphabet(dialect);
            int len2 = i - 2;
            int d = 0;
            int e = 0;
            int lineLength = 0;
            while (d < len2) {
                encode3to4(src, d + off, 3, dest, e, alphabet);
                lineLength += 4;
                if (breakLines && lineLength == 76) {
                    dest.setByte(e + 4, 10);
                    e++;
                    lineLength = 0;
                }
                d += 3;
                e += 4;
            }
            if (d < i) {
                encode3to4(src, d + off, i - d, dest, e, alphabet);
                e += 4;
            }
            if (e > 1 && dest.getByte(e - 1) == 10) {
                e--;
            }
            return dest.slice(0, e);
        } else {
            ByteBufAllocator byteBufAllocator2 = allocator;
            throw new NullPointerException("dialect");
        }
    }

    private static void encode3to4(ByteBuf src, int srcOffset, int numSigBytes, ByteBuf dest, int destOffset, byte[] alphabet) {
        int inBuff;
        int inBuff2;
        int i = 0;
        if (src.order() == ByteOrder.BIG_ENDIAN) {
            switch (numSigBytes) {
                case 1:
                    inBuff2 = toInt(src.getByte(srcOffset));
                    break;
                case 2:
                    inBuff2 = toIntBE(src.getShort(srcOffset));
                    break;
                default:
                    if (numSigBytes > 0) {
                        i = toIntBE(src.getMedium(srcOffset));
                    }
                    inBuff2 = i;
                    break;
            }
            encode3to4BigEndian(inBuff2, numSigBytes, dest, destOffset, alphabet);
            return;
        }
        switch (numSigBytes) {
            case 1:
                inBuff = toInt(src.getByte(srcOffset));
                break;
            case 2:
                inBuff = toIntLE(src.getShort(srcOffset));
                break;
            default:
                if (numSigBytes > 0) {
                    i = toIntLE(src.getMedium(srcOffset));
                }
                inBuff = i;
                break;
        }
        encode3to4LittleEndian(inBuff, numSigBytes, dest, destOffset, alphabet);
    }

    static int encodedBufferSize(int len, boolean breakLines) {
        long len43 = (((long) len) << 2) / 3;
        long ret = (3 + len43) & -4;
        if (breakLines) {
            ret += len43 / 76;
        }
        if (ret < 2147483647L) {
            return (int) ret;
        }
        return Integer.MAX_VALUE;
    }

    private static int toInt(byte value) {
        return (value & EQUALS_SIGN_ENC) << MappingData.PATH;
    }

    private static int toIntBE(short value) {
        return ((65280 & value) << 8) | ((value & 255) << 8);
    }

    private static int toIntLE(short value) {
        return ((value & 255) << 16) | (65280 & value);
    }

    private static int toIntBE(int mediumValue) {
        return (16711680 & mediumValue) | (65280 & mediumValue) | (mediumValue & 255);
    }

    private static int toIntLE(int mediumValue) {
        return ((mediumValue & 255) << 16) | (65280 & mediumValue) | ((16711680 & mediumValue) >>> 16);
    }

    private static void encode3to4BigEndian(int inBuff, int numSigBytes, ByteBuf dest, int destOffset, byte[] alphabet) {
        switch (numSigBytes) {
            case 1:
                dest.setInt(destOffset, (alphabet[inBuff >>> 18] << 24) | (alphabet[(inBuff >>> 12) & 63] << MappingData.PATH) | 15616 | 61);
                return;
            case 2:
                dest.setInt(destOffset, (alphabet[inBuff >>> 18] << 24) | (alphabet[(inBuff >>> 12) & 63] << MappingData.PATH) | (alphabet[(inBuff >>> 6) & 63] << 8) | 61);
                return;
            case 3:
                dest.setInt(destOffset, (alphabet[inBuff >>> 18] << 24) | (alphabet[(inBuff >>> 12) & 63] << MappingData.PATH) | (alphabet[(inBuff >>> 6) & 63] << 8) | alphabet[inBuff & 63]);
                return;
            default:
                return;
        }
    }

    private static void encode3to4LittleEndian(int inBuff, int numSigBytes, ByteBuf dest, int destOffset, byte[] alphabet) {
        switch (numSigBytes) {
            case 1:
                dest.setInt(destOffset, 1023410176 | alphabet[inBuff >>> 18] | (alphabet[(inBuff >>> 12) & 63] << 8) | 3997696);
                return;
            case 2:
                dest.setInt(destOffset, 1023410176 | alphabet[inBuff >>> 18] | (alphabet[(inBuff >>> 12) & 63] << 8) | (alphabet[(inBuff >>> 6) & 63] << MappingData.PATH));
                return;
            case 3:
                dest.setInt(destOffset, alphabet[inBuff >>> 18] | (alphabet[(inBuff >>> 12) & 63] << 8) | (alphabet[(inBuff >>> 6) & 63] << MappingData.PATH) | (alphabet[inBuff & 63] << 24));
                return;
            default:
                return;
        }
    }

    public static ByteBuf decode(ByteBuf src) {
        return decode(src, Base64Dialect.STANDARD);
    }

    public static ByteBuf decode(ByteBuf src, Base64Dialect dialect) {
        if (src != null) {
            ByteBuf dest = decode(src, src.readerIndex(), src.readableBytes(), dialect);
            src.readerIndex(src.writerIndex());
            return dest;
        }
        throw new NullPointerException("src");
    }

    public static ByteBuf decode(ByteBuf src, int off, int len) {
        return decode(src, off, len, Base64Dialect.STANDARD);
    }

    public static ByteBuf decode(ByteBuf src, int off, int len, Base64Dialect dialect) {
        return decode(src, off, len, dialect, src.alloc());
    }

    public static ByteBuf decode(ByteBuf src, int off, int len, Base64Dialect dialect, ByteBufAllocator allocator) {
        if (src == null) {
            throw new NullPointerException("src");
        } else if (dialect != null) {
            return new Decoder().decode(src, off, len, allocator, dialect);
        } else {
            throw new NullPointerException("dialect");
        }
    }

    static int decodedBufferSize(int len) {
        return len - (len >>> 2);
    }

    public static final class Decoder implements ByteBufProcessor {
        private final byte[] b4;
        private int b4Posn;
        private byte[] decodabet;
        private ByteBuf dest;
        private int outBuffPosn;
        private byte sbiCrop;
        private byte sbiDecode;

        private Decoder() {
            this.b4 = new byte[4];
        }

        /* access modifiers changed from: package-private */
        public ByteBuf decode(ByteBuf src, int off, int len, ByteBufAllocator allocator, Base64Dialect dialect) {
            this.dest = allocator.buffer(Base64.decodedBufferSize(len)).order(src.order());
            this.decodabet = Base64.decodabet(dialect);
            try {
                src.forEachByte(off, len, this);
                return this.dest.slice(0, this.outBuffPosn);
            } catch (Throwable cause) {
                this.dest.release();
                PlatformDependent.throwException(cause);
                return null;
            }
        }

        public boolean process(byte value) {
            byte b = (byte) (value & Byte.MAX_VALUE);
            this.sbiCrop = b;
            byte[] bArr = this.decodabet;
            byte b2 = bArr[b];
            this.sbiDecode = b2;
            if (b2 < -5) {
                throw new IllegalArgumentException("invalid bad Base64 input character: " + ((short) (value & Base64.EQUALS_SIGN_ENC)) + " (decimal)");
            } else if (b2 < -1) {
                return true;
            } else {
                byte[] bArr2 = this.b4;
                int i = this.b4Posn;
                int i2 = i + 1;
                this.b4Posn = i2;
                bArr2[i] = b;
                if (i2 <= 3) {
                    return true;
                }
                int i3 = this.outBuffPosn;
                this.outBuffPosn = i3 + decode4to3(bArr2, this.dest, i3, bArr);
                this.b4Posn = 0;
                if (this.sbiCrop == 61) {
                    return false;
                }
                return true;
            }
        }

        private static int decode4to3(byte[] src, ByteBuf dest2, int destOffset, byte[] decodabet2) {
            int decodedValue;
            int decodedValue2;
            byte src0 = src[0];
            byte src1 = src[1];
            byte src2 = src[2];
            if (src2 == 61) {
                try {
                    dest2.setByte(destOffset, ((decodabet2[src0] & Base64.EQUALS_SIGN_ENC) << 2) | ((decodabet2[src1] & Base64.EQUALS_SIGN_ENC) >>> 4));
                    return 1;
                } catch (IndexOutOfBoundsException e) {
                    throw new IllegalArgumentException("not encoded in Base64");
                }
            } else {
                byte src3 = src[3];
                if (src3 == 61) {
                    byte b1 = decodabet2[src1];
                    try {
                        if (dest2.order() == ByteOrder.BIG_ENDIAN) {
                            decodedValue2 = ((((decodabet2[src0] & 63) << 2) | ((b1 & 240) >> 4)) << 8) | ((b1 & 15) << 4) | ((decodabet2[src2] & 252) >>> 2);
                        } else {
                            decodedValue2 = ((decodabet2[src0] & 63) << 2) | ((b1 & 240) >> 4) | ((((decodabet2[src2] & 252) >>> 2) | ((b1 & 15) << 4)) << 8);
                        }
                        dest2.setShort(destOffset, decodedValue2);
                        return 2;
                    } catch (IndexOutOfBoundsException e2) {
                        throw new IllegalArgumentException("not encoded in Base64");
                    }
                } else {
                    try {
                        if (dest2.order() == ByteOrder.BIG_ENDIAN) {
                            decodedValue = ((decodabet2[src0] & 63) << 18) | ((decodabet2[src1] & Base64.EQUALS_SIGN_ENC) << 12) | ((decodabet2[src2] & Base64.EQUALS_SIGN_ENC) << 6) | (decodabet2[src3] & 255);
                        } else {
                            byte b12 = decodabet2[src1];
                            byte b2 = decodabet2[src2];
                            decodedValue = ((decodabet2[src0] & 63) << 2) | ((b12 & 15) << 12) | ((b12 & 240) >>> 4) | ((b2 & 3) << 22) | ((b2 & 252) << 6) | ((decodabet2[src3] & Base64.EQUALS_SIGN_ENC) << MappingData.PATH);
                        }
                        dest2.setMedium(destOffset, decodedValue);
                        return 3;
                    } catch (IndexOutOfBoundsException e3) {
                        throw new IllegalArgumentException("not encoded in Base64");
                    }
                }
            }
        }
    }

    private Base64() {
    }
}
