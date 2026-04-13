package io.netty.buffer;

import androidx.core.view.ViewCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import io.netty.util.CharsetUtil;
import io.netty.util.Recycler;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Locale;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public final class ByteBufUtil {
    private static final FastThreadLocal<CharBuffer> CHAR_BUFFERS = new FastThreadLocal<CharBuffer>() {
        /* access modifiers changed from: protected */
        public CharBuffer initialValue() {
            return CharBuffer.allocate(1024);
        }
    };
    static final ByteBufAllocator DEFAULT_ALLOCATOR;
    private static final ByteBufProcessor FIND_NON_ASCII = new ByteBufProcessor() {
        public boolean process(byte value) {
            return value >= 0;
        }
    };
    private static final int MAX_BYTES_PER_CHAR_UTF8 = ((int) CharsetUtil.encoder(CharsetUtil.UTF_8).maxBytesPerChar());
    private static final int MAX_CHAR_BUFFER_SIZE;
    /* access modifiers changed from: private */
    public static final int THREAD_LOCAL_BUFFER_SIZE;
    private static final byte WRITE_UTF_UNKNOWN = 63;
    private static final InternalLogger logger;

    static {
        ByteBufAllocator alloc;
        InternalLogger instance = InternalLoggerFactory.getInstance((Class<?>) ByteBufUtil.class);
        logger = instance;
        String allocType = SystemPropertyUtil.get("io.netty.allocator.type", "unpooled").toLowerCase(Locale.US).trim();
        if ("unpooled".equals(allocType)) {
            alloc = UnpooledByteBufAllocator.DEFAULT;
            instance.debug("-Dio.netty.allocator.type: {}", (Object) allocType);
        } else if ("pooled".equals(allocType)) {
            alloc = PooledByteBufAllocator.DEFAULT;
            instance.debug("-Dio.netty.allocator.type: {}", (Object) allocType);
        } else {
            alloc = UnpooledByteBufAllocator.DEFAULT;
            instance.debug("-Dio.netty.allocator.type: unpooled (unknown: {})", (Object) allocType);
        }
        DEFAULT_ALLOCATOR = alloc;
        int i = SystemPropertyUtil.getInt("io.netty.threadLocalDirectBufferSize", 65536);
        THREAD_LOCAL_BUFFER_SIZE = i;
        instance.debug("-Dio.netty.threadLocalDirectBufferSize: {}", (Object) Integer.valueOf(i));
        int i2 = SystemPropertyUtil.getInt("io.netty.maxThreadLocalCharBufferSize", 16384);
        MAX_CHAR_BUFFER_SIZE = i2;
        instance.debug("-Dio.netty.maxThreadLocalCharBufferSize: {}", (Object) Integer.valueOf(i2));
    }

    public static String hexDump(ByteBuf buffer) {
        return hexDump(buffer, buffer.readerIndex(), buffer.readableBytes());
    }

    public static String hexDump(ByteBuf buffer, int fromIndex, int length) {
        return HexUtil.hexDump(buffer, fromIndex, length);
    }

    public static String hexDump(byte[] array) {
        return hexDump(array, 0, array.length);
    }

    public static String hexDump(byte[] array, int fromIndex, int length) {
        return HexUtil.hexDump(array, fromIndex, length);
    }

    public static byte decodeHexByte(CharSequence s, int pos) {
        return StringUtil.decodeHexByte(s, pos);
    }

    public static byte[] decodeHexDump(CharSequence hexDump) {
        return StringUtil.decodeHexDump(hexDump, 0, hexDump.length());
    }

    public static byte[] decodeHexDump(CharSequence hexDump, int fromIndex, int length) {
        return StringUtil.decodeHexDump(hexDump, fromIndex, length);
    }

    public static int hashCode(ByteBuf buffer) {
        int aLen = buffer.readableBytes();
        int intCount = aLen >>> 2;
        int byteCount = aLen & 3;
        int hashCode = 1;
        int arrayIndex = buffer.readerIndex();
        if (buffer.order() == ByteOrder.BIG_ENDIAN) {
            for (int i = intCount; i > 0; i--) {
                hashCode = (hashCode * 31) + buffer.getInt(arrayIndex);
                arrayIndex += 4;
            }
        } else {
            for (int i2 = intCount; i2 > 0; i2--) {
                hashCode = (hashCode * 31) + swapInt(buffer.getInt(arrayIndex));
                arrayIndex += 4;
            }
        }
        int i3 = byteCount;
        while (i3 > 0) {
            hashCode = (hashCode * 31) + buffer.getByte(arrayIndex);
            i3--;
            arrayIndex++;
        }
        if (hashCode == 0) {
            return 1;
        }
        return hashCode;
    }

    public static boolean equals(ByteBuf bufferA, ByteBuf bufferB) {
        int aLen = bufferA.readableBytes();
        if (aLen != bufferB.readableBytes()) {
            return false;
        }
        int longCount = aLen >>> 3;
        int byteCount = aLen & 7;
        int aIndex = bufferA.readerIndex();
        int bIndex = bufferB.readerIndex();
        if (bufferA.order() == bufferB.order()) {
            for (int i = longCount; i > 0; i--) {
                if (bufferA.getLong(aIndex) != bufferB.getLong(bIndex)) {
                    return false;
                }
                aIndex += 8;
                bIndex += 8;
            }
        } else {
            for (int i2 = longCount; i2 > 0; i2--) {
                if (bufferA.getLong(aIndex) != swapLong(bufferB.getLong(bIndex))) {
                    return false;
                }
                aIndex += 8;
                bIndex += 8;
            }
        }
        for (int i3 = byteCount; i3 > 0; i3--) {
            if (bufferA.getByte(aIndex) != bufferB.getByte(bIndex)) {
                return false;
            }
            aIndex++;
            bIndex++;
        }
        return true;
    }

    public static int compare(ByteBuf bufferA, ByteBuf bufferB) {
        long res;
        ByteBuf byteBuf = bufferA;
        ByteBuf byteBuf2 = bufferB;
        int aLen = bufferA.readableBytes();
        int bLen = bufferB.readableBytes();
        int minLength = Math.min(aLen, bLen);
        int uintCount = minLength >>> 2;
        int byteCount = minLength & 3;
        int aIndex = bufferA.readerIndex();
        int bIndex = bufferB.readerIndex();
        if (uintCount > 0) {
            boolean bufferAIsBigEndian = bufferA.order() == ByteOrder.BIG_ENDIAN;
            int uintCountIncrement = uintCount << 2;
            if (bufferA.order() == bufferB.order()) {
                if (bufferAIsBigEndian) {
                    res = compareUintBigEndian(byteBuf, byteBuf2, aIndex, bIndex, uintCountIncrement);
                } else {
                    res = compareUintLittleEndian(byteBuf, byteBuf2, aIndex, bIndex, uintCountIncrement);
                }
            } else if (bufferAIsBigEndian) {
                res = compareUintBigEndianA(byteBuf, byteBuf2, aIndex, bIndex, uintCountIncrement);
            } else {
                res = compareUintBigEndianB(byteBuf, byteBuf2, aIndex, bIndex, uintCountIncrement);
            }
            if (res != 0) {
                int i = minLength;
                int i2 = uintCount;
                return (int) Math.min(2147483647L, Math.max(-2147483648L, res));
            }
            int i3 = uintCount;
            aIndex += uintCountIncrement;
            bIndex += uintCountIncrement;
        } else {
            int i4 = uintCount;
        }
        int aEnd = aIndex + byteCount;
        while (aIndex < aEnd) {
            int comp = byteBuf.getUnsignedByte(aIndex) - byteBuf2.getUnsignedByte(bIndex);
            if (comp != 0) {
                return comp;
            }
            aIndex++;
            bIndex++;
        }
        return aLen - bLen;
    }

    private static long compareUintBigEndian(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
        int aEnd = aIndex + uintCountIncrement;
        while (aIndex < aEnd) {
            long comp = bufferA.getUnsignedInt(aIndex) - bufferB.getUnsignedInt(bIndex);
            if (comp != 0) {
                return comp;
            }
            aIndex += 4;
            bIndex += 4;
        }
        return 0;
    }

    private static long compareUintLittleEndian(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
        int aEnd = aIndex + uintCountIncrement;
        while (aIndex < aEnd) {
            long comp = (((long) swapInt(bufferA.getInt(aIndex))) & 4294967295L) - (4294967295L & ((long) swapInt(bufferB.getInt(bIndex))));
            if (comp != 0) {
                return comp;
            }
            aIndex += 4;
            bIndex += 4;
        }
        return 0;
    }

    private static long compareUintBigEndianA(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
        int aEnd = aIndex + uintCountIncrement;
        while (aIndex < aEnd) {
            long comp = bufferA.getUnsignedInt(aIndex) - (((long) swapInt(bufferB.getInt(bIndex))) & 4294967295L);
            if (comp != 0) {
                return comp;
            }
            aIndex += 4;
            bIndex += 4;
        }
        return 0;
    }

    private static long compareUintBigEndianB(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
        int aEnd = aIndex + uintCountIncrement;
        while (aIndex < aEnd) {
            long comp = (((long) swapInt(bufferA.getInt(aIndex))) & 4294967295L) - bufferB.getUnsignedInt(bIndex);
            if (comp != 0) {
                return comp;
            }
            aIndex += 4;
            bIndex += 4;
        }
        return 0;
    }

    public static int indexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
        if (fromIndex <= toIndex) {
            return firstIndexOf(buffer, fromIndex, toIndex, value);
        }
        return lastIndexOf(buffer, fromIndex, toIndex, value);
    }

    public static short swapShort(short value) {
        return Short.reverseBytes(value);
    }

    public static int swapMedium(int value) {
        int swapped = ((value << 16) & 16711680) | (65280 & value) | ((value >>> 16) & 255);
        if ((8388608 & swapped) != 0) {
            return swapped | ViewCompat.MEASURED_STATE_MASK;
        }
        return swapped;
    }

    public static int swapInt(int value) {
        return Integer.reverseBytes(value);
    }

    public static long swapLong(long value) {
        return Long.reverseBytes(value);
    }

    public static ByteBuf readBytes(ByteBufAllocator alloc, ByteBuf buffer, int length) {
        boolean release = true;
        ByteBuf dst = alloc.buffer(length);
        try {
            buffer.readBytes(dst);
            release = false;
            return dst;
        } finally {
            if (release) {
                dst.release();
            }
        }
    }

    private static int firstIndexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
        int fromIndex2 = Math.max(fromIndex, 0);
        if (fromIndex2 >= toIndex || buffer.capacity() == 0) {
            return -1;
        }
        return buffer.forEachByte(fromIndex2, toIndex - fromIndex2, new IndexOfProcessor(value));
    }

    private static int lastIndexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
        int fromIndex2 = Math.min(fromIndex, buffer.capacity());
        if (fromIndex2 < 0 || buffer.capacity() == 0) {
            return -1;
        }
        return buffer.forEachByteDesc(toIndex, fromIndex2 - toIndex, new IndexOfProcessor(value));
    }

    public static ByteBuf writeUtf8(ByteBufAllocator alloc, CharSequence seq) {
        ByteBuf buf = alloc.buffer(seq.length() * MAX_BYTES_PER_CHAR_UTF8);
        writeUtf8(buf, seq);
        return buf;
    }

    public static int writeUtf8(ByteBuf buf, CharSequence seq) {
        int len = seq.length();
        buf.ensureWritable(MAX_BYTES_PER_CHAR_UTF8 * len);
        while (!(buf instanceof AbstractByteBuf)) {
            if (buf instanceof WrappedByteBuf) {
                buf = buf.unwrap();
            } else {
                byte[] bytes = seq.toString().getBytes(CharsetUtil.UTF_8);
                buf.writeBytes(bytes);
                return bytes.length;
            }
        }
        return writeUtf8((AbstractByteBuf) buf, seq, len);
    }

    private static int writeUtf8(AbstractByteBuf buffer, CharSequence seq, int len) {
        int oldWriterIndex = buffer.writerIndex;
        int writerIndex = oldWriterIndex;
        int i = 0;
        while (i < len) {
            char c = seq.charAt(i);
            if (c < 128) {
                buffer._setByte(writerIndex, (byte) c);
                writerIndex++;
            } else if (c < 2048) {
                int writerIndex2 = writerIndex + 1;
                buffer._setByte(writerIndex, (byte) ((c >> 6) | Opcodes.CHECKCAST));
                writerIndex = writerIndex2 + 1;
                buffer._setByte(writerIndex2, (byte) (128 | (c & '?')));
            } else {
                char c2 = '?';
                if (!StringUtil.isSurrogate(c)) {
                    int writerIndex3 = writerIndex + 1;
                    buffer._setByte(writerIndex, (byte) ((c >> 12) | 224));
                    int writerIndex4 = writerIndex3 + 1;
                    buffer._setByte(writerIndex3, (byte) ((63 & (c >> 6)) | 128));
                    buffer._setByte(writerIndex4, (byte) (128 | (c & '?')));
                    writerIndex = writerIndex4 + 1;
                } else if (!Character.isHighSurrogate(c)) {
                    buffer._setByte(writerIndex, 63);
                    writerIndex++;
                } else {
                    i++;
                    try {
                        char c22 = seq.charAt(i);
                        if (!Character.isLowSurrogate(c22)) {
                            int writerIndex5 = writerIndex + 1;
                            buffer._setByte(writerIndex, 63);
                            writerIndex = writerIndex5 + 1;
                            if (!Character.isHighSurrogate(c22)) {
                                c2 = c22;
                            }
                            buffer._setByte(writerIndex5, c2);
                        } else {
                            int codePoint = Character.toCodePoint(c, c22);
                            int writerIndex6 = writerIndex + 1;
                            buffer._setByte(writerIndex, (byte) ((codePoint >> 18) | 240));
                            int writerIndex7 = writerIndex6 + 1;
                            buffer._setByte(writerIndex6, (byte) (((codePoint >> 12) & 63) | 128));
                            int writerIndex8 = writerIndex7 + 1;
                            buffer._setByte(writerIndex7, (byte) ((63 & (codePoint >> 6)) | 128));
                            writerIndex = writerIndex8 + 1;
                            buffer._setByte(writerIndex8, (byte) (128 | (codePoint & 63)));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        buffer._setByte(writerIndex, 63);
                        writerIndex++;
                    }
                }
            }
            i++;
        }
        buffer.writerIndex = writerIndex;
        return writerIndex - oldWriterIndex;
    }

    public static ByteBuf writeAscii(ByteBufAllocator alloc, CharSequence seq) {
        ByteBuf buf = alloc.buffer(seq.length());
        writeAscii(buf, seq);
        return buf;
    }

    public static int writeAscii(ByteBuf buf, CharSequence seq) {
        int len = seq.length();
        buf.ensureWritable(len);
        while (!(buf instanceof AbstractByteBuf)) {
            if (buf instanceof WrappedByteBuf) {
                buf = buf.unwrap();
            } else {
                byte[] bytes = seq.toString().getBytes(CharsetUtil.US_ASCII);
                buf.writeBytes(bytes);
                return bytes.length;
            }
        }
        writeAscii((AbstractByteBuf) buf, seq, len);
        return len;
    }

    private static void writeAscii(AbstractByteBuf buffer, CharSequence seq, int len) {
        int writerIndex = buffer.writerIndex;
        int i = 0;
        while (i < len) {
            buffer._setByte(writerIndex, (byte) seq.charAt(i));
            i++;
            writerIndex++;
        }
        buffer.writerIndex = writerIndex;
    }

    public static ByteBuf encodeString(ByteBufAllocator alloc, CharBuffer src, Charset charset) {
        return encodeString0(alloc, false, src, charset);
    }

    static ByteBuf encodeString0(ByteBufAllocator alloc, boolean enforceHeap, CharBuffer src, Charset charset) {
        ByteBuf dst;
        CharsetEncoder encoder = CharsetUtil.encoder(charset);
        int length = (int) (((double) src.remaining()) * ((double) encoder.maxBytesPerChar()));
        if (enforceHeap) {
            dst = alloc.heapBuffer(length);
        } else {
            dst = alloc.buffer(length);
        }
        try {
            ByteBuffer dstBuf = dst.internalNioBuffer(dst.readerIndex(), length);
            int pos = dstBuf.position();
            CoderResult cr = encoder.encode(src, dstBuf, true);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
            CoderResult cr2 = encoder.flush(dstBuf);
            if (!cr2.isUnderflow()) {
                cr2.throwException();
            }
            dst.writerIndex((dst.writerIndex() + dstBuf.position()) - pos);
            if (0 != 0) {
                dst.release();
            }
            return dst;
        } catch (CharacterCodingException x) {
            throw new IllegalStateException(x);
        } catch (Throwable th) {
            if (1 != 0) {
                dst.release();
            }
            throw th;
        }
    }

    static String decodeString(ByteBuf src, int readerIndex, int len, Charset charset) {
        if (len == 0) {
            return "";
        }
        CharsetDecoder decoder = CharsetUtil.decoder(charset);
        int maxLength = (int) (((double) len) * ((double) decoder.maxCharsPerByte()));
        FastThreadLocal<CharBuffer> fastThreadLocal = CHAR_BUFFERS;
        CharBuffer dst = fastThreadLocal.get();
        if (dst.length() < maxLength) {
            dst = CharBuffer.allocate(maxLength);
            if (maxLength <= MAX_CHAR_BUFFER_SIZE) {
                fastThreadLocal.set(dst);
            }
        } else {
            dst.clear();
        }
        if (src.nioBufferCount() == 1) {
            decodeString(decoder, src.nioBuffer(readerIndex, len), dst);
        } else {
            ByteBuf buffer = src.alloc().heapBuffer(len);
            try {
                buffer.writeBytes(src, readerIndex, len);
                decodeString(decoder, buffer.internalNioBuffer(buffer.readerIndex(), len), dst);
            } finally {
                buffer.release();
            }
        }
        return dst.flip().toString();
    }

    private static void decodeString(CharsetDecoder decoder, ByteBuffer src, CharBuffer dst) {
        try {
            CoderResult cr = decoder.decode(src, dst, true);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
            CoderResult cr2 = decoder.flush(dst);
            if (!cr2.isUnderflow()) {
                cr2.throwException();
            }
        } catch (CharacterCodingException x) {
            throw new IllegalStateException(x);
        }
    }

    public static String prettyHexDump(ByteBuf buffer) {
        return prettyHexDump(buffer, buffer.readerIndex(), buffer.readableBytes());
    }

    public static String prettyHexDump(ByteBuf buffer, int offset, int length) {
        return HexUtil.prettyHexDump(buffer, offset, length);
    }

    public static void appendPrettyHexDump(StringBuilder dump, ByteBuf buf) {
        appendPrettyHexDump(dump, buf, buf.readerIndex(), buf.readableBytes());
    }

    public static void appendPrettyHexDump(StringBuilder dump, ByteBuf buf, int offset, int length) {
        HexUtil.appendPrettyHexDump(dump, buf, offset, length);
    }

    public static final class HexUtil {
        private static final char[] BYTE2CHAR = new char[256];
        private static final String[] BYTE2HEX = new String[256];
        private static final String[] BYTEPADDING = new String[16];
        private static final String[] HEXDUMP_ROWPREFIXES = new String[4096];
        private static final char[] HEXDUMP_TABLE = new char[1024];
        private static final String[] HEXPADDING = new String[16];

        private HexUtil() {
        }

        static {
            char[] DIGITS = "0123456789abcdef".toCharArray();
            for (int i = 0; i < 256; i++) {
                char[] cArr = HEXDUMP_TABLE;
                cArr[i << 1] = DIGITS[(i >>> 4) & 15];
                cArr[(i << 1) + 1] = DIGITS[i & 15];
            }
            int i2 = 0;
            while (true) {
                String[] strArr = HEXPADDING;
                if (i2 >= strArr.length) {
                    break;
                }
                int padding = strArr.length - i2;
                StringBuilder buf = new StringBuilder(padding * 3);
                for (int j = 0; j < padding; j++) {
                    buf.append("   ");
                }
                HEXPADDING[i2] = buf.toString();
                i2++;
            }
            int i3 = 0;
            while (true) {
                String[] strArr2 = HEXDUMP_ROWPREFIXES;
                if (i3 >= strArr2.length) {
                    break;
                }
                StringBuilder buf2 = new StringBuilder(12);
                buf2.append(StringUtil.NEWLINE);
                buf2.append(Long.toHexString((((long) (i3 << 4)) & 4294967295L) | IjkMediaMeta.AV_CH_WIDE_RIGHT));
                buf2.setCharAt(buf2.length() - 9, '|');
                buf2.append('|');
                strArr2[i3] = buf2.toString();
                i3++;
            }
            int i4 = 0;
            while (true) {
                String[] strArr3 = BYTE2HEX;
                if (i4 >= strArr3.length) {
                    break;
                }
                strArr3[i4] = ' ' + StringUtil.byteToHexStringPadded(i4);
                i4++;
            }
            int i5 = 0;
            while (true) {
                String[] strArr4 = BYTEPADDING;
                if (i5 >= strArr4.length) {
                    break;
                }
                int padding2 = strArr4.length - i5;
                StringBuilder buf3 = new StringBuilder(padding2);
                for (int j2 = 0; j2 < padding2; j2++) {
                    buf3.append(' ');
                }
                BYTEPADDING[i5] = buf3.toString();
                i5++;
            }
            int i6 = 0;
            while (true) {
                char[] cArr2 = BYTE2CHAR;
                if (i6 < cArr2.length) {
                    if (i6 <= 31 || i6 >= 127) {
                        cArr2[i6] = '.';
                    } else {
                        cArr2[i6] = (char) i6;
                    }
                    i6++;
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: private */
        public static String hexDump(ByteBuf buffer, int fromIndex, int length) {
            if (length < 0) {
                throw new IllegalArgumentException("length: " + length);
            } else if (length == 0) {
                return "";
            } else {
                int endIndex = fromIndex + length;
                char[] buf = new char[(length << 1)];
                int srcIdx = fromIndex;
                int dstIdx = 0;
                while (srcIdx < endIndex) {
                    System.arraycopy(HEXDUMP_TABLE, buffer.getUnsignedByte(srcIdx) << 1, buf, dstIdx, 2);
                    srcIdx++;
                    dstIdx += 2;
                }
                return new String(buf);
            }
        }

        /* access modifiers changed from: private */
        public static String hexDump(byte[] array, int fromIndex, int length) {
            if (length < 0) {
                throw new IllegalArgumentException("length: " + length);
            } else if (length == 0) {
                return "";
            } else {
                int endIndex = fromIndex + length;
                char[] buf = new char[(length << 1)];
                int srcIdx = fromIndex;
                int dstIdx = 0;
                while (srcIdx < endIndex) {
                    System.arraycopy(HEXDUMP_TABLE, (array[srcIdx] & 255) << 1, buf, dstIdx, 2);
                    srcIdx++;
                    dstIdx += 2;
                }
                return new String(buf);
            }
        }

        /* access modifiers changed from: private */
        public static String prettyHexDump(ByteBuf buffer, int offset, int length) {
            if (length == 0) {
                return "";
            }
            StringBuilder buf = new StringBuilder(((length / 16) + (length % 15 == 0 ? 0 : 1) + 4) * 80);
            appendPrettyHexDump(buf, buffer, offset, length);
            return buf.toString();
        }

        /* access modifiers changed from: private */
        public static void appendPrettyHexDump(StringBuilder dump, ByteBuf buf, int offset, int length) {
            if (MathUtil.isOutOfBounds(offset, length, buf.capacity())) {
                throw new IndexOutOfBoundsException("expected: 0 <= offset(" + offset + ") <= offset + length(" + length + ") <= buf.capacity(" + buf.capacity() + ')');
            } else if (length != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("         +-------------------------------------------------+");
                String str = StringUtil.NEWLINE;
                sb.append(str);
                sb.append("         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |");
                sb.append(str);
                sb.append("+--------+-------------------------------------------------+----------------+");
                dump.append(sb.toString());
                int startIndex = offset;
                int fullRows = length >>> 4;
                int remainder = length & 15;
                for (int row = 0; row < fullRows; row++) {
                    int rowStartIndex = (row << 4) + startIndex;
                    appendHexDumpRowPrefix(dump, row, rowStartIndex);
                    int rowEndIndex = rowStartIndex + 16;
                    for (int j = rowStartIndex; j < rowEndIndex; j++) {
                        dump.append(BYTE2HEX[buf.getUnsignedByte(j)]);
                    }
                    dump.append(" |");
                    for (int j2 = rowStartIndex; j2 < rowEndIndex; j2++) {
                        dump.append(BYTE2CHAR[buf.getUnsignedByte(j2)]);
                    }
                    dump.append('|');
                }
                if (remainder != 0) {
                    int rowStartIndex2 = (fullRows << 4) + startIndex;
                    appendHexDumpRowPrefix(dump, fullRows, rowStartIndex2);
                    int rowEndIndex2 = rowStartIndex2 + remainder;
                    for (int j3 = rowStartIndex2; j3 < rowEndIndex2; j3++) {
                        dump.append(BYTE2HEX[buf.getUnsignedByte(j3)]);
                    }
                    dump.append(HEXPADDING[remainder]);
                    dump.append(" |");
                    for (int j4 = rowStartIndex2; j4 < rowEndIndex2; j4++) {
                        dump.append(BYTE2CHAR[buf.getUnsignedByte(j4)]);
                    }
                    dump.append(BYTEPADDING[remainder]);
                    dump.append('|');
                }
                dump.append(StringUtil.NEWLINE + "+--------+-------------------------------------------------+----------------+");
            }
        }

        private static void appendHexDumpRowPrefix(StringBuilder dump, int row, int rowStartIndex) {
            String[] strArr = HEXDUMP_ROWPREFIXES;
            if (row < strArr.length) {
                dump.append(strArr[row]);
                return;
            }
            dump.append(StringUtil.NEWLINE);
            dump.append(Long.toHexString((((long) rowStartIndex) & 4294967295L) | IjkMediaMeta.AV_CH_WIDE_RIGHT));
            dump.setCharAt(dump.length() - 9, '|');
            dump.append('|');
        }
    }

    public static ByteBuf threadLocalDirectBuffer() {
        if (THREAD_LOCAL_BUFFER_SIZE <= 0) {
            return null;
        }
        if (PlatformDependent.hasUnsafe()) {
            return ThreadLocalUnsafeDirectByteBuf.newInstance();
        }
        return ThreadLocalDirectByteBuf.newInstance();
    }

    public static final class ThreadLocalUnsafeDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
        private static final Recycler<ThreadLocalUnsafeDirectByteBuf> RECYCLER = new Recycler<ThreadLocalUnsafeDirectByteBuf>() {
            /* access modifiers changed from: protected */
            public ThreadLocalUnsafeDirectByteBuf newObject(Recycler.Handle handle) {
                return new ThreadLocalUnsafeDirectByteBuf(handle);
            }
        };
        private final Recycler.Handle handle;

        static ThreadLocalUnsafeDirectByteBuf newInstance() {
            ThreadLocalUnsafeDirectByteBuf buf = RECYCLER.get();
            buf.setRefCnt(1);
            return buf;
        }

        private ThreadLocalUnsafeDirectByteBuf(Recycler.Handle handle2) {
            super((ByteBufAllocator) UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.handle = handle2;
        }

        /* access modifiers changed from: protected */
        public void deallocate() {
            if (capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
                super.deallocate();
                return;
            }
            clear();
            RECYCLER.recycle(this, this.handle);
        }
    }

    public static final class ThreadLocalDirectByteBuf extends UnpooledDirectByteBuf {
        private static final Recycler<ThreadLocalDirectByteBuf> RECYCLER = new Recycler<ThreadLocalDirectByteBuf>() {
            /* access modifiers changed from: protected */
            public ThreadLocalDirectByteBuf newObject(Recycler.Handle handle) {
                return new ThreadLocalDirectByteBuf(handle);
            }
        };
        private final Recycler.Handle handle;

        static ThreadLocalDirectByteBuf newInstance() {
            ThreadLocalDirectByteBuf buf = RECYCLER.get();
            buf.setRefCnt(1);
            return buf;
        }

        private ThreadLocalDirectByteBuf(Recycler.Handle handle2) {
            super((ByteBufAllocator) UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.handle = handle2;
        }

        /* access modifiers changed from: protected */
        public void deallocate() {
            if (capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
                super.deallocate();
                return;
            }
            clear();
            RECYCLER.recycle(this, this.handle);
        }
    }

    public static class IndexOfProcessor implements ByteBufProcessor {
        private final byte byteToFind;

        public IndexOfProcessor(byte byteToFind2) {
            this.byteToFind = byteToFind2;
        }

        public boolean process(byte value) {
            return value != this.byteToFind;
        }
    }

    public static boolean isText(ByteBuf buf, Charset charset) {
        return isText(buf, buf.readerIndex(), buf.readableBytes(), charset);
    }

    public static boolean isText(ByteBuf buf, int index, int length, Charset charset) {
        ByteBuf heapBuffer;
        ObjectUtil.checkNotNull(buf, "buf");
        ObjectUtil.checkNotNull(charset, "charset");
        int maxIndex = buf.readerIndex() + buf.readableBytes();
        if (index < 0 || length < 0 || index > maxIndex - length) {
            throw new IndexOutOfBoundsException("index: " + index + " length: " + length);
        } else if (charset.equals(CharsetUtil.UTF_8)) {
            return isUtf8(buf, index, length);
        } else {
            if (charset.equals(CharsetUtil.US_ASCII)) {
                return isAscii(buf, index, length);
            }
            CodingErrorAction codingErrorAction = CodingErrorAction.REPORT;
            CharsetDecoder decoder = CharsetUtil.decoder(charset, codingErrorAction, codingErrorAction);
            try {
                if (buf.nioBufferCount() == 1) {
                    decoder.decode(buf.nioBuffer(index, length));
                } else {
                    heapBuffer = buf.alloc().heapBuffer(length);
                    heapBuffer.writeBytes(buf, index, length);
                    decoder.decode(heapBuffer.internalNioBuffer(heapBuffer.readerIndex(), length));
                    heapBuffer.release();
                }
                return true;
            } catch (CharacterCodingException e) {
                return false;
            } catch (Throwable th) {
                heapBuffer.release();
                throw th;
            }
        }
    }

    private static boolean isAscii(ByteBuf buf, int index, int length) {
        return buf.forEachByte(index, length, FIND_NON_ASCII) == -1;
    }

    private static boolean isUtf8(ByteBuf buf, int index, int length) {
        int endIndex = index + length;
        while (index < endIndex) {
            int index2 = index + 1;
            byte index3 = buf.getByte(index);
            if ((index3 & OTACommand.RESP_ID_VERSION) == 0) {
                index = index2;
            } else if ((index3 & 224) == 192) {
                if (index2 >= endIndex) {
                    return false;
                }
                int index4 = index2 + 1;
                if ((buf.getByte(index2) & 192) != 128 || (index3 & 255) < 194) {
                    return false;
                }
                index = index4;
            } else if ((index3 & 240) == 224) {
                if (index2 > endIndex - 2) {
                    return false;
                }
                int index5 = index2 + 1;
                byte b2 = buf.getByte(index2);
                int index6 = index5 + 1;
                byte b3 = buf.getByte(index5);
                if ((b2 & 192) != 128 || (b3 & 192) != 128) {
                    return false;
                }
                if ((index3 & 15) == 0 && (b2 & 255) < 160) {
                    return false;
                }
                if ((index3 & 15) == 13 && (b2 & 255) > 159) {
                    return false;
                }
                index = index6;
            } else if ((index3 & 248) != 240 || index2 > endIndex - 3) {
                return false;
            } else {
                int index7 = index2 + 1;
                byte b22 = buf.getByte(index2);
                int index8 = index7 + 1;
                byte b32 = buf.getByte(index7);
                int index9 = index8 + 1;
                byte b4 = buf.getByte(index8);
                if ((b22 & 192) != 128 || (b32 & 192) != 128 || (b4 & 192) != 128 || (index3 & 255) > 244 || (((index3 & 255) == 240 && (b22 & 255) < 144) || ((index3 & 255) == 244 && (b22 & 255) > 143))) {
                    return false;
                }
                index = index9;
            }
        }
        return true;
    }

    private ByteBufUtil() {
    }
}
