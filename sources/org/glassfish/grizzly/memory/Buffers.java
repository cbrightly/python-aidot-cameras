package org.glassfish.grizzly.memory;

import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Appender;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.localization.LogMessages;

public class Buffers {
    private static final Appender<Buffer> APPENDER_DISPOSABLE = new BuffersAppender(true);
    private static final Appender<Buffer> APPENDER_NOT_DISPOSABLE = new BuffersAppender(false);
    public static final Buffer EMPTY_BUFFER = new ByteBufferWrapper(ByteBuffer.allocate(0)) {
        public void dispose() {
        }
    };
    public static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.allocate(0);
    public static final ByteBuffer[] EMPTY_BYTE_BUFFER_ARRAY = new ByteBuffer[0];
    private static final Logger LOGGER = Grizzly.logger(Buffers.class);

    public static Appender<Buffer> getBufferAppender(boolean isCompositeBufferDisposable) {
        return isCompositeBufferDisposable ? APPENDER_DISPOSABLE : APPENDER_NOT_DISPOSABLE;
    }

    public static class BuffersAppender implements Appender<Buffer> {
        private final boolean isCompositeBufferDisposable;

        public BuffersAppender(boolean isCompositeBufferDisposable2) {
            this.isCompositeBufferDisposable = isCompositeBufferDisposable2;
        }

        public Buffer append(Buffer element1, Buffer element2) {
            return Buffers.appendBuffers((MemoryManager) null, element1, element2, this.isCompositeBufferDisposable);
        }
    }

    public static Buffer wrap(MemoryManager memoryManager, String s) {
        return wrap(memoryManager, s, Charset.defaultCharset());
    }

    public static Buffer wrap(MemoryManager memoryManager, String s, Charset charset) {
        try {
            return wrap(memoryManager, s.getBytes(charset.name()));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Buffer wrap(MemoryManager memoryManager, byte[] array) {
        return wrap(memoryManager, array, 0, array.length);
    }

    public static Buffer wrap(MemoryManager memoryManager, byte[] array, int offset, int length) {
        if (memoryManager == null) {
            memoryManager = getDefaultMemoryManager();
        }
        if (memoryManager instanceof WrapperAware) {
            return ((WrapperAware) memoryManager).wrap(array, offset, length);
        }
        Buffer buffer = memoryManager.allocate(length);
        buffer.put(array, offset, length);
        buffer.flip();
        return buffer;
    }

    public static Buffer wrap(MemoryManager memoryManager, ByteBuffer byteBuffer) {
        if (memoryManager instanceof WrapperAware) {
            return ((WrapperAware) memoryManager).wrap(byteBuffer);
        }
        if (byteBuffer.hasArray()) {
            return wrap(memoryManager, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
        }
        throw new IllegalStateException("Can not wrap ByteBuffer");
    }

    public static ByteBuffer slice(ByteBuffer chunk, int size) {
        chunk.limit(chunk.position() + size);
        ByteBuffer view = chunk.slice();
        chunk.position(chunk.limit());
        chunk.limit(chunk.capacity());
        return view;
    }

    public static ByteBuffer slice(ByteBuffer byteBuffer, int position, int limit) {
        int oldPos = byteBuffer.position();
        int oldLimit = byteBuffer.limit();
        setPositionLimit(byteBuffer, position, limit);
        ByteBuffer slice = byteBuffer.slice();
        setPositionLimit(byteBuffer, oldPos, oldLimit);
        return slice;
    }

    public static String toStringContent(ByteBuffer byteBuffer, Charset charset, int position, int limit) {
        if (charset == null) {
            charset = Charset.defaultCharset();
        }
        int oldPosition = byteBuffer.position();
        int oldLimit = byteBuffer.limit();
        setPositionLimit(byteBuffer, position, limit);
        try {
            return charset.decode(byteBuffer).toString();
        } finally {
            setPositionLimit(byteBuffer, oldPosition, oldLimit);
        }
    }

    public static void setPositionLimit(Buffer buffer, int position, int limit) {
        buffer.limit(limit);
        buffer.position(position);
    }

    public static void setPositionLimit(ByteBuffer buffer, int position, int limit) {
        buffer.limit(limit);
        buffer.position(position);
    }

    public static void put(ByteBuffer srcBuffer, int srcOffset, int length, ByteBuffer dstBuffer) {
        if (dstBuffer.remaining() < length) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_BUFFERS_OVERFLOW_EXCEPTION(srcBuffer, Integer.valueOf(srcOffset), Integer.valueOf(length), dstBuffer));
            throw new BufferOverflowException();
        } else if (!srcBuffer.hasArray() || !dstBuffer.hasArray()) {
            for (int i = srcOffset; i < srcOffset + length; i++) {
                dstBuffer.put(srcBuffer.get(i));
            }
        } else {
            System.arraycopy(srcBuffer.array(), srcBuffer.arrayOffset() + srcOffset, dstBuffer.array(), dstBuffer.arrayOffset() + dstBuffer.position(), length);
            dstBuffer.position(dstBuffer.position() + length);
        }
    }

    public static void put(Buffer src, int position, int length, Buffer dstBuffer) {
        if (dstBuffer.remaining() < length) {
            throw new BufferOverflowException();
        } else if (!src.isComposite()) {
            ByteBuffer srcByteBuffer = src.toByteBuffer();
            if (srcByteBuffer.hasArray()) {
                dstBuffer.put(srcByteBuffer.array(), srcByteBuffer.arrayOffset() + position, length);
                return;
            }
            for (int i = 0; i < length; i++) {
                dstBuffer.put(srcByteBuffer.get(position + i));
            }
        } else {
            ByteBufferArray array = src.toByteBufferArray(position, position + length);
            ByteBuffer[] srcByteBuffers = (ByteBuffer[]) array.getArray();
            for (int i2 = 0; i2 < array.size(); i2++) {
                ByteBuffer srcByteBuffer2 = srcByteBuffers[i2];
                int initialPosition = srcByteBuffer2.position();
                int srcByteBufferLen = srcByteBuffer2.remaining();
                if (srcByteBuffer2.hasArray()) {
                    dstBuffer.put(srcByteBuffer2.array(), srcByteBuffer2.arrayOffset() + initialPosition, srcByteBufferLen);
                } else {
                    for (int j = 0; j < srcByteBufferLen; j++) {
                        dstBuffer.put(srcByteBuffer2.get(initialPosition + j));
                    }
                }
            }
            array.restore();
            array.recycle();
        }
    }

    public static void get(ByteBuffer srcBuffer, byte[] dstBytes, int dstOffset, int length) {
        if (!srcBuffer.hasArray()) {
            srcBuffer.get(dstBytes, dstOffset, length);
        } else if (length <= srcBuffer.remaining()) {
            System.arraycopy(srcBuffer.array(), srcBuffer.arrayOffset() + srcBuffer.position(), dstBytes, dstOffset, length);
            srcBuffer.position(srcBuffer.position() + length);
        } else {
            throw new BufferUnderflowException();
        }
    }

    public static void put(byte[] srcBytes, int srcOffset, int length, ByteBuffer dstBuffer) {
        if (!dstBuffer.hasArray()) {
            dstBuffer.put(srcBytes, srcOffset, length);
        } else if (length <= dstBuffer.remaining()) {
            System.arraycopy(srcBytes, srcOffset, dstBuffer.array(), dstBuffer.arrayOffset() + dstBuffer.position(), length);
            dstBuffer.position(dstBuffer.position() + length);
        } else {
            throw new BufferOverflowException();
        }
    }

    public static Buffer appendBuffers(MemoryManager memoryManager, Buffer buffer1, Buffer buffer2) {
        return appendBuffers(memoryManager, buffer1, buffer2, false);
    }

    public static Buffer appendBuffers(MemoryManager memoryManager, Buffer buffer1, Buffer buffer2, boolean isCompositeBufferDisposable) {
        if (buffer1 == null) {
            return buffer2;
        }
        if (buffer2 == null) {
            return buffer1;
        }
        if (buffer1.order() != buffer2.order()) {
            LOGGER.fine("Appending buffers with different ByteOrder.The result Buffer's order will be the same as the first Buffer's ByteOrder");
            buffer2.order(buffer1.order());
        }
        if (buffer1.isComposite() && buffer1.capacity() == buffer1.limit()) {
            ((CompositeBuffer) buffer1).append(buffer2);
            return buffer1;
        } else if (!buffer2.isComposite() || buffer2.position() != 0) {
            CompositeBuffer compositeBuffer = CompositeBuffer.newBuffer(memoryManager);
            compositeBuffer.order(buffer1.order());
            compositeBuffer.append(buffer1);
            compositeBuffer.append(buffer2);
            compositeBuffer.allowBufferDispose(isCompositeBufferDisposable);
            return compositeBuffer;
        } else {
            ((CompositeBuffer) buffer2).prepend(buffer1);
            return buffer2;
        }
    }

    public static void fill(Buffer buffer, byte b) {
        fill(buffer, buffer.position(), buffer.limit(), b);
    }

    public static void fill(Buffer buffer, int position, int limit, byte b) {
        if (!buffer.isComposite()) {
            fill(buffer.toByteBuffer(), position, limit, b);
            return;
        }
        ByteBufferArray array = buffer.toByteBufferArray(position, limit);
        ByteBuffer[] byteBuffers = (ByteBuffer[]) array.getArray();
        int size = array.size();
        for (int i = 0; i < size; i++) {
            fill(byteBuffers[i], b);
        }
        array.restore();
        array.recycle();
    }

    public static void fill(ByteBuffer byteBuffer, byte b) {
        fill(byteBuffer, byteBuffer.position(), byteBuffer.limit(), b);
    }

    public static void fill(ByteBuffer byteBuffer, int position, int limit, byte b) {
        if (byteBuffer.hasArray()) {
            int arrayOffset = byteBuffer.arrayOffset();
            Arrays.fill(byteBuffer.array(), arrayOffset + position, arrayOffset + limit, b);
            return;
        }
        for (int i = position; i < limit; i++) {
            byteBuffer.put(i, b);
        }
    }

    public static Buffer cloneBuffer(Buffer srcBuffer) {
        return cloneBuffer(srcBuffer, srcBuffer.position(), srcBuffer.limit());
    }

    public static Buffer cloneBuffer(Buffer srcBuffer, int position, int limit) {
        int srcLength = limit - position;
        if (srcLength == 0) {
            return wrap(getDefaultMemoryManager(), EMPTY_BYTE_BUFFER);
        }
        Buffer clone = getDefaultMemoryManager().allocate(srcLength);
        clone.put(srcBuffer, position, srcLength);
        clone.order(srcBuffer.order());
        return clone.flip();
    }

    public static long readFromFileChannel(FileChannel fileChannel, Buffer buffer) {
        long bytesRead;
        if (!buffer.isComposite()) {
            ByteBuffer bb = buffer.toByteBuffer();
            int oldPos = bb.position();
            bytesRead = (long) fileChannel.read(bb);
            bb.position(oldPos);
        } else {
            ByteBufferArray array = buffer.toByteBufferArray();
            bytesRead = fileChannel.read((ByteBuffer[]) array.getArray(), 0, array.size());
            array.restore();
            array.recycle();
        }
        if (bytesRead > 0) {
            buffer.position(buffer.position() + ((int) bytesRead));
        }
        return bytesRead;
    }

    public static long writeToFileChannel(FileChannel fileChannel, Buffer buffer) {
        long bytesWritten;
        if (!buffer.isComposite()) {
            ByteBuffer bb = buffer.toByteBuffer();
            int oldPos = bb.position();
            bytesWritten = (long) fileChannel.write(bb);
            bb.position(oldPos);
        } else {
            ByteBufferArray array = buffer.toByteBufferArray();
            bytesWritten = fileChannel.write((ByteBuffer[]) array.getArray(), 0, array.size());
            array.restore();
            array.recycle();
        }
        if (bytesWritten > 0) {
            buffer.position(buffer.position() + ((int) bytesWritten));
        }
        return bytesWritten;
    }

    public String toStringContent(Buffer buffer, int headBytesCount, int tailBytesCount) {
        if (buffer == null) {
            return null;
        }
        return toStringContent(buffer, headBytesCount, tailBytesCount, Charset.defaultCharset());
    }

    public String toStringContent(Buffer buffer, int headBytesCount, int tailBytesCount, Charset charset) {
        if (buffer == null) {
            return null;
        }
        if (headBytesCount < 0 || tailBytesCount < 0) {
            throw new IllegalArgumentException("count can't be negative");
        }
        String toString = buffer.toString();
        StringBuilder sb = new StringBuilder(toString.length() + headBytesCount + tailBytesCount + 5);
        sb.append(toString);
        if (buffer.remaining() <= headBytesCount + tailBytesCount) {
            sb.append('[');
            sb.append(buffer.toStringContent(charset));
            sb.append(']');
        } else {
            sb.append('[');
            if (headBytesCount > 0) {
                sb.append(buffer.toStringContent(charset, buffer.position(), buffer.position() + headBytesCount));
            }
            sb.append("...");
            if (tailBytesCount > 0) {
                sb.append(buffer.toStringContent(charset, buffer.limit() - tailBytesCount, buffer.limit()));
            }
            sb.append(']');
        }
        return sb.toString();
    }

    public static void dumpBuffer(Appendable appendable, Buffer buffer) {
        dumpBuffer0(new Formatter(appendable), appendable, buffer);
    }

    private static void dumpBuffer0(Formatter formatter, Appendable appendable, Buffer buffer) {
        if (buffer.isComposite()) {
            BufferArray bufferArray = buffer.toBufferArray();
            int size = bufferArray.size();
            Buffer[] buffers = (Buffer[]) bufferArray.getArray();
            formatter.format("%s\n", new Object[]{buffer.toString()});
            for (int i = 0; i < size; i++) {
                dumpBuffer0(formatter, appendable, buffers[i]);
            }
            formatter.format("End CompositeBuffer (%d)", new Object[]{Integer.valueOf(System.identityHashCode(buffer))});
            return;
        }
        dumpBuffer0(formatter, buffer);
    }

    private static void dumpBuffer0(Formatter formatter, Buffer buffer) {
        Formatter formatter2 = formatter;
        Buffer buffer2 = buffer;
        formatter2.format("%s\n", new Object[]{buffer.toString()});
        int line = 0;
        int i = 0;
        int len = buffer.remaining() / 16;
        while (i < len) {
            byte b0 = buffer2.get(line);
            byte b1 = buffer2.get(line + 1);
            byte b2 = buffer2.get(line + 2);
            byte b3 = buffer2.get(line + 3);
            byte b4 = buffer2.get(line + 4);
            byte b5 = buffer2.get(line + 5);
            byte b6 = buffer2.get(line + 6);
            byte b7 = buffer2.get(line + 7);
            byte b8 = buffer2.get(line + 8);
            byte b9 = buffer2.get(line + 9);
            byte b10 = buffer2.get(line + 10);
            int len2 = len;
            byte b11 = buffer2.get(line + 11);
            int i2 = i;
            byte b12 = buffer2.get(line + 12);
            byte b13 = buffer2.get(line + 13);
            byte b14 = buffer2.get(line + 14);
            byte b15 = buffer2.get(line + 15);
            String str = DumpStrings.DUMP_STRINGS[15];
            Object[] objArr = {Integer.valueOf(line), Byte.valueOf(b0), Byte.valueOf(b1), Byte.valueOf(b2), Byte.valueOf(b3), Byte.valueOf(b4), Byte.valueOf(b5), Byte.valueOf(b6), Byte.valueOf(b7), Byte.valueOf(b8), Byte.valueOf(b9), Byte.valueOf(b10), Byte.valueOf(b11), Byte.valueOf(b12), Byte.valueOf(b13), Byte.valueOf(b14), Byte.valueOf(b15), Character.valueOf(getChar(b0)), Character.valueOf(getChar(b1)), Character.valueOf(getChar(b2)), Character.valueOf(getChar(b3)), Character.valueOf(getChar(b4)), Character.valueOf(getChar(b5)), Character.valueOf(getChar(b6)), Character.valueOf(getChar(b7)), Character.valueOf(getChar(b8)), Character.valueOf(getChar(b9)), Character.valueOf(getChar(b10)), Character.valueOf(getChar(b11)), Character.valueOf(getChar(b12)), Character.valueOf(getChar(b13)), Character.valueOf(getChar(b14)), Character.valueOf(getChar(b15))};
            byte b = b15;
            formatter2 = formatter;
            String str2 = str;
            byte b16 = b9;
            formatter2.format(str2, objArr);
            i = i2 + 1;
            line += 16;
            buffer2 = buffer;
            len = len2;
        }
        int i3 = i;
        int i4 = len;
        int remaining = buffer.remaining() % 16;
        if (remaining > 0) {
            Object[] args = new Object[((remaining << 1) + 1)];
            args[0] = Integer.valueOf(remaining + line);
            int i5 = 0;
            int aIdx = 1;
            while (i5 < remaining) {
                int b17 = buffer.get(line + i5);
                args[aIdx] = Integer.valueOf(b17 & 255);
                args[aIdx + remaining] = Character.valueOf(getChar(b17));
                i5++;
                aIdx++;
            }
            Buffer buffer3 = buffer;
            formatter2.format(DumpStrings.DUMP_STRINGS[remaining - 1], args);
            return;
        }
        Buffer buffer4 = buffer;
    }

    private static char getChar(int val) {
        char c = (char) val;
        if (Character.isWhitespace(c) || Character.isISOControl(c)) {
            return '.';
        }
        return c;
    }

    private static MemoryManager getDefaultMemoryManager() {
        return MemoryManager.DEFAULT_MEMORY_MANAGER;
    }

    public static final class DumpStrings {
        /* access modifiers changed from: private */
        public static final String[] DUMP_STRINGS = {"%10d   %02x                                                         %c\n", "%10d   %02x %02x                                                      %c%c\n", "%10d   %02x %02x  %02x                                                  %c%c%c\n", "%10d   %02x %02x  %02x %02x                                               %c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x                                           %c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x                                        %c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x                                    %c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x                                 %c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x                           %c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x                        %c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x                    %c%c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x %02x                 %c%c%c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x %02x  %02x             %c%c%c%c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x %02x  %02x %02x          %c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x %02x  %02x %02x  %02x      %c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", "%10d   %02x %02x  %02x %02x  %02x %02x  %02x %02x    %02x %02x  %02x %02x  %02x %02x  %02x %02x   %c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n"};

        private DumpStrings() {
        }
    }
}
