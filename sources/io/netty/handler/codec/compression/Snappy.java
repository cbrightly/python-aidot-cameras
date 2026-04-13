package io.netty.handler.codec.compression;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public class Snappy {
    private static final int COPY_1_BYTE_OFFSET = 1;
    private static final int COPY_2_BYTE_OFFSET = 2;
    private static final int COPY_4_BYTE_OFFSET = 3;
    private static final int LITERAL = 0;
    private static final int MAX_HT_SIZE = 16384;
    private static final int MIN_COMPRESSIBLE_BYTES = 15;
    private static final int NOT_ENOUGH_INPUT = -1;
    private static final int PREAMBLE_NOT_FULL = -1;
    private State state = State.READY;
    private byte tag;
    private int written;

    public enum State {
        READY,
        READING_PREAMBLE,
        READING_TAG,
        READING_LITERAL,
        READING_COPY
    }

    Snappy() {
    }

    public void reset() {
        this.state = State.READY;
        this.tag = 0;
        this.written = 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0063, code lost:
        encodeLiteral(r0, r1, r3 - r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0068, code lost:
        r7 = r3;
        r11 = findMatchingLength(r0, r13 + 4, r3 + 4, r2) + 4;
        r3 = r3 + r11;
        encodeCopy(r1, r7 - r13, r11);
        r0.readerIndex(r20.readerIndex() + r11);
        r15 = r3 - 1;
        r9 = r3;
        r16 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0088, code lost:
        if (r3 < (r2 - 4)) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x008a, code lost:
        r7 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x008c, code lost:
        r7 = hash(r0, r15, r6);
        r18 = r8;
        r5[r7] = (short) ((r3 - r4) - 1);
        r8 = hash(r0, r15 + 1, r6);
        r13 = r4 + r5[r8];
        r17 = r7;
        r5[r8] = (short) (r3 - r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00b4, code lost:
        if (r0.getInt(r15 + 1) == r0.getInt(r13)) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00c1, code lost:
        r8 = r18;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void encode(io.netty.buffer.ByteBuf r20, io.netty.buffer.ByteBuf r21, int r22) {
        /*
            r19 = this;
            r0 = r20
            r1 = r21
            r2 = r22
            r3 = 0
        L_0x0007:
            int r4 = r3 * 7
            int r4 = r2 >>> r4
            r5 = r4 & -128(0xffffffffffffff80, float:NaN)
            if (r5 == 0) goto L_0x0019
            r5 = r4 & 127(0x7f, float:1.78E-43)
            r5 = r5 | 128(0x80, float:1.794E-43)
            r1.writeByte(r5)
            int r3 = r3 + 1
            goto L_0x0007
        L_0x0019:
            r1.writeByte(r4)
            int r3 = r20.readerIndex()
            r4 = r3
            short[] r5 = getHashTable(r22)
            int r6 = r5.length
            int r6 = java.lang.Integer.numberOfLeadingZeros(r6)
            int r6 = r6 + 1
            r7 = r3
            int r8 = r2 - r3
            r9 = 15
            if (r8 < r9) goto L_0x00c9
            int r3 = r3 + 1
            int r8 = hash(r0, r3, r6)
            r9 = r7
        L_0x003b:
            r7 = 32
            r10 = r3
        L_0x003e:
            r3 = r10
            r11 = r8
            int r12 = r7 + 1
            int r7 = r7 >> 5
            int r10 = r3 + r7
            int r13 = r2 + -4
            if (r10 <= r13) goto L_0x004d
            r7 = r9
            goto L_0x00c9
        L_0x004d:
            int r8 = hash(r0, r10, r6)
            short r13 = r5[r11]
            int r13 = r13 + r4
            int r14 = r3 - r4
            short r14 = (short) r14
            r5[r11] = r14
            int r7 = r0.getInt(r3)
            int r11 = r0.getInt(r13)
            if (r7 != r11) goto L_0x00c4
            int r7 = r3 - r9
            encodeLiteral(r0, r1, r7)
        L_0x0068:
            r7 = r3
            int r11 = r13 + 4
            int r14 = r3 + 4
            int r11 = findMatchingLength(r0, r11, r14, r2)
            int r11 = r11 + 4
            int r3 = r3 + r11
            int r14 = r7 - r13
            encodeCopy(r1, r14, r11)
            int r15 = r20.readerIndex()
            int r15 = r15 + r11
            r0.readerIndex(r15)
            int r15 = r3 + -1
            r9 = r3
            r16 = r7
            int r7 = r2 + -4
            if (r3 < r7) goto L_0x008c
            r7 = r9
            goto L_0x00c9
        L_0x008c:
            int r7 = hash(r0, r15, r6)
            int r17 = r3 - r4
            r18 = r8
            int r8 = r17 + -1
            short r8 = (short) r8
            r5[r7] = r8
            int r8 = r15 + 1
            int r8 = hash(r0, r8, r6)
            short r17 = r5[r8]
            int r13 = r4 + r17
            r17 = r7
            int r7 = r3 - r4
            short r7 = (short) r7
            r5[r8] = r7
            int r7 = r15 + 1
            int r7 = r0.getInt(r7)
            int r8 = r0.getInt(r13)
            if (r7 == r8) goto L_0x00c1
            int r7 = r15 + 2
            int r8 = hash(r0, r7, r6)
            int r3 = r3 + 1
            goto L_0x003b
        L_0x00c1:
            r8 = r18
            goto L_0x0068
        L_0x00c4:
            r18 = r8
            r7 = r12
            goto L_0x003e
        L_0x00c9:
            if (r7 >= r2) goto L_0x00d0
            int r8 = r2 - r7
            encodeLiteral(r0, r1, r8)
        L_0x00d0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.Snappy.encode(io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf, int):void");
    }

    private static int hash(ByteBuf in, int index, int shift) {
        return (in.getInt(index) * 506832829) >>> shift;
    }

    private static short[] getHashTable(int inputSize) {
        int htSize = 256;
        while (htSize < 16384 && htSize < inputSize) {
            htSize <<= 1;
        }
        return new short[htSize];
    }

    private static int findMatchingLength(ByteBuf in, int minIndex, int inIndex, int maxIndex) {
        int matched = 0;
        while (inIndex <= maxIndex - 4 && in.getInt(inIndex) == in.getInt(minIndex + matched)) {
            inIndex += 4;
            matched += 4;
        }
        while (inIndex < maxIndex && in.getByte(minIndex + matched) == in.getByte(inIndex)) {
            inIndex++;
            matched++;
        }
        return matched;
    }

    private static int bitsToEncode(int value) {
        int highestOneBit = Integer.highestOneBit(value);
        int bitLength = 0;
        while (true) {
            int i = highestOneBit >> 1;
            highestOneBit = i;
            if (i == 0) {
                return bitLength;
            }
            bitLength++;
        }
    }

    static void encodeLiteral(ByteBuf in, ByteBuf out, int length) {
        if (length < 61) {
            out.writeByte((length - 1) << 2);
        } else {
            int bytesToEncode = (bitsToEncode(length - 1) / 8) + 1;
            out.writeByte((bytesToEncode + 59) << 2);
            for (int i = 0; i < bytesToEncode; i++) {
                out.writeByte(((length - 1) >> (i * 8)) & 255);
            }
        }
        out.writeBytes(in, length);
    }

    private static void encodeCopyWithOffset(ByteBuf out, int offset, int length) {
        if (length >= 12 || offset >= 2048) {
            out.writeByte(((length - 1) << 2) | 2);
            out.writeByte(offset & 255);
            out.writeByte((offset >> 8) & 255);
            return;
        }
        out.writeByte(((length - 4) << 2) | 1 | ((offset >> 8) << 5));
        out.writeByte(offset & 255);
    }

    private static void encodeCopy(ByteBuf out, int offset, int length) {
        while (length >= 68) {
            encodeCopyWithOffset(out, offset, 64);
            length -= 64;
        }
        if (length > 64) {
            encodeCopyWithOffset(out, offset, 60);
            length -= 60;
        }
        encodeCopyWithOffset(out, offset, length);
    }

    /* renamed from: io.netty.handler.codec.compression.Snappy$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$Snappy$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$io$netty$handler$codec$compression$Snappy$State = iArr;
            try {
                iArr[State.READY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$Snappy$State[State.READING_PREAMBLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$Snappy$State[State.READING_TAG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$Snappy$State[State.READING_LITERAL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$Snappy$State[State.READING_COPY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public void decode(ByteBuf in, ByteBuf out) {
        while (in.isReadable()) {
            switch (AnonymousClass1.$SwitchMap$io$netty$handler$codec$compression$Snappy$State[this.state.ordinal()]) {
                case 1:
                    this.state = State.READING_PREAMBLE;
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    int literalWritten = decodeLiteral(this.tag, in, out);
                    if (literalWritten != -1) {
                        this.state = State.READING_TAG;
                        this.written += literalWritten;
                        continue;
                    } else {
                        return;
                    }
                case 5:
                    byte b = this.tag;
                    switch (b & 3) {
                        case 1:
                            int decodeWritten = decodeCopyWith1ByteOffset(b, in, out, this.written);
                            if (decodeWritten != -1) {
                                this.state = State.READING_TAG;
                                this.written += decodeWritten;
                                break;
                            } else {
                                return;
                            }
                        case 2:
                            int decodeWritten2 = decodeCopyWith2ByteOffset(b, in, out, this.written);
                            if (decodeWritten2 != -1) {
                                this.state = State.READING_TAG;
                                this.written += decodeWritten2;
                                break;
                            } else {
                                return;
                            }
                        case 3:
                            int decodeWritten3 = decodeCopyWith4ByteOffset(b, in, out, this.written);
                            if (decodeWritten3 != -1) {
                                this.state = State.READING_TAG;
                                this.written += decodeWritten3;
                                break;
                            } else {
                                return;
                            }
                        default:
                            continue;
                    }
            }
            int uncompressedLength = readPreamble(in);
            if (uncompressedLength != -1) {
                if (uncompressedLength == 0) {
                    this.state = State.READY;
                    return;
                }
                out.ensureWritable(uncompressedLength);
                this.state = State.READING_TAG;
                if (in.isReadable() != 0) {
                    byte readByte = in.readByte();
                    this.tag = readByte;
                    switch (readByte & 3) {
                        case 0:
                            this.state = State.READING_LITERAL;
                            break;
                        case 1:
                        case 2:
                        case 3:
                            this.state = State.READING_COPY;
                            break;
                        default:
                            continue;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private static int readPreamble(ByteBuf in) {
        int length = 0;
        int byteIndex = 0;
        while (in.isReadable()) {
            int current = in.readUnsignedByte();
            int byteIndex2 = byteIndex + 1;
            length |= (current & NeedPermissionEvent.PER_IPC_SPEAK_PERM) << (byteIndex * 7);
            if ((current & 128) == 0) {
                return length;
            }
            if (byteIndex2 < 4) {
                byteIndex = byteIndex2;
            } else {
                throw new DecompressionException("Preamble is greater than 4 bytes");
            }
        }
        return 0;
    }

    static int decodeLiteral(byte tag2, ByteBuf in, ByteBuf out) {
        int length;
        in.markReaderIndex();
        switch ((tag2 >> 2) & 63) {
            case 60:
                if (in.isReadable() != 0) {
                    length = in.readUnsignedByte();
                    break;
                } else {
                    return -1;
                }
            case 61:
                if (in.readableBytes() >= 2) {
                    length = ByteBufUtil.swapShort(in.readShort());
                    break;
                } else {
                    return -1;
                }
            case 62:
                if (in.readableBytes() >= 3) {
                    length = ByteBufUtil.swapMedium(in.readUnsignedMedium());
                    break;
                } else {
                    return -1;
                }
            case 63:
                if (in.readableBytes() >= 4) {
                    length = ByteBufUtil.swapInt(in.readInt());
                    break;
                } else {
                    return -1;
                }
            default:
                length = (tag2 >> 2) & 63;
                break;
        }
        int length2 = length + 1;
        if (in.readableBytes() < length2) {
            in.resetReaderIndex();
            return -1;
        }
        out.writeBytes(in, length2);
        return length2;
    }

    private static int decodeCopyWith1ByteOffset(byte tag2, ByteBuf in, ByteBuf out, int writtenSoFar) {
        if (!in.isReadable()) {
            return -1;
        }
        int initialIndex = out.writerIndex();
        int length = ((tag2 & 28) >> 2) + 4;
        int offset = (((tag2 & 224) << 8) >> 5) | in.readUnsignedByte();
        validateOffset(offset, writtenSoFar);
        out.markReaderIndex();
        if (offset < length) {
            for (int copies = length / offset; copies > 0; copies--) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, offset);
            }
            if (length % offset != 0) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, length % offset);
            }
        } else {
            out.readerIndex(initialIndex - offset);
            out.readBytes(out, length);
        }
        out.resetReaderIndex();
        return length;
    }

    private static int decodeCopyWith2ByteOffset(byte tag2, ByteBuf in, ByteBuf out, int writtenSoFar) {
        if (in.readableBytes() < 2) {
            return -1;
        }
        int initialIndex = out.writerIndex();
        int length = ((tag2 >> 2) & 63) + 1;
        int offset = ByteBufUtil.swapShort(in.readShort());
        validateOffset(offset, writtenSoFar);
        out.markReaderIndex();
        if (offset < length) {
            for (int copies = length / offset; copies > 0; copies--) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, offset);
            }
            if (length % offset != 0) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, length % offset);
            }
        } else {
            out.readerIndex(initialIndex - offset);
            out.readBytes(out, length);
        }
        out.resetReaderIndex();
        return length;
    }

    private static int decodeCopyWith4ByteOffset(byte tag2, ByteBuf in, ByteBuf out, int writtenSoFar) {
        if (in.readableBytes() < 4) {
            return -1;
        }
        int initialIndex = out.writerIndex();
        int length = ((tag2 >> 2) & 63) + 1;
        int offset = ByteBufUtil.swapInt(in.readInt());
        validateOffset(offset, writtenSoFar);
        out.markReaderIndex();
        if (offset < length) {
            for (int copies = length / offset; copies > 0; copies--) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, offset);
            }
            if (length % offset != 0) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, length % offset);
            }
        } else {
            out.readerIndex(initialIndex - offset);
            out.readBytes(out, length);
        }
        out.resetReaderIndex();
        return length;
    }

    private static void validateOffset(int offset, int chunkSizeSoFar) {
        if (offset > 32767) {
            throw new DecompressionException("Offset exceeds maximum permissible value");
        } else if (offset <= 0) {
            throw new DecompressionException("Offset is less than minimum permissible value");
        } else if (offset > chunkSizeSoFar) {
            throw new DecompressionException("Offset exceeds size of chunk");
        }
    }

    public static int calculateChecksum(ByteBuf data) {
        return calculateChecksum(data, data.readerIndex(), data.readableBytes());
    }

    public static int calculateChecksum(ByteBuf data, int offset, int length) {
        Crc32c crc32 = new Crc32c();
        try {
            if (data.hasArray()) {
                crc32.update(data.array(), data.arrayOffset() + offset, length);
            } else {
                byte[] array = new byte[length];
                data.getBytes(offset, array);
                crc32.update(array, 0, length);
            }
            return maskChecksum((int) crc32.getValue());
        } finally {
            crc32.reset();
        }
    }

    static void validateChecksum(int expectedChecksum, ByteBuf data) {
        validateChecksum(expectedChecksum, data, data.readerIndex(), data.readableBytes());
    }

    static void validateChecksum(int expectedChecksum, ByteBuf data, int offset, int length) {
        int actualChecksum = calculateChecksum(data, offset, length);
        if (actualChecksum != expectedChecksum) {
            throw new DecompressionException("mismatching checksum: " + Integer.toHexString(actualChecksum) + " (expected: " + Integer.toHexString(expectedChecksum) + ')');
        }
    }

    static int maskChecksum(int checksum) {
        return ((checksum >> 15) | (checksum << 17)) - 1568478504;
    }
}
