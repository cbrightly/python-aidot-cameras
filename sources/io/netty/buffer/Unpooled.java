package io.netty.buffer;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public final class Unpooled {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ByteBufAllocator ALLOC;
    public static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
    public static final ByteBuf EMPTY_BUFFER;
    public static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;

    static {
        UnpooledByteBufAllocator unpooledByteBufAllocator = UnpooledByteBufAllocator.DEFAULT;
        ALLOC = unpooledByteBufAllocator;
        ByteBuf buffer = unpooledByteBufAllocator.buffer(0, 0);
        EMPTY_BUFFER = buffer;
        if (!(buffer instanceof EmptyByteBuf)) {
            throw new AssertionError("EMPTY_BUFFER must be an EmptyByteBuf.");
        }
    }

    public static ByteBuf buffer() {
        return ALLOC.heapBuffer();
    }

    public static ByteBuf directBuffer() {
        return ALLOC.directBuffer();
    }

    public static ByteBuf buffer(int initialCapacity) {
        return ALLOC.heapBuffer(initialCapacity);
    }

    public static ByteBuf directBuffer(int initialCapacity) {
        return ALLOC.directBuffer(initialCapacity);
    }

    public static ByteBuf buffer(int initialCapacity, int maxCapacity) {
        return ALLOC.heapBuffer(initialCapacity, maxCapacity);
    }

    public static ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
        return ALLOC.directBuffer(initialCapacity, maxCapacity);
    }

    public static ByteBuf wrappedBuffer(byte[] array) {
        if (array.length == 0) {
            return EMPTY_BUFFER;
        }
        return new UnpooledHeapByteBuf(ALLOC, array, array.length);
    }

    public static ByteBuf wrappedBuffer(byte[] array, int offset, int length) {
        if (length == 0) {
            return EMPTY_BUFFER;
        }
        if (offset == 0 && length == array.length) {
            return wrappedBuffer(array);
        }
        return wrappedBuffer(array).slice(offset, length);
    }

    public static ByteBuf wrappedBuffer(ByteBuffer buffer) {
        if (!buffer.hasRemaining()) {
            return EMPTY_BUFFER;
        }
        if (!buffer.isDirect() && buffer.hasArray()) {
            return wrappedBuffer(buffer.array(), buffer.arrayOffset() + buffer.position(), buffer.remaining()).order(buffer.order());
        }
        if (PlatformDependent.hasUnsafe()) {
            if (!buffer.isReadOnly()) {
                return new UnpooledUnsafeDirectByteBuf(ALLOC, buffer, buffer.remaining());
            }
            if (buffer.isDirect()) {
                return new ReadOnlyUnsafeDirectByteBuf(ALLOC, buffer);
            }
            return new ReadOnlyByteBufferBuf(ALLOC, buffer);
        } else if (buffer.isReadOnly()) {
            return new ReadOnlyByteBufferBuf(ALLOC, buffer);
        } else {
            return new UnpooledDirectByteBuf(ALLOC, buffer, buffer.remaining());
        }
    }

    public static ByteBuf wrappedBuffer(long memoryAddress, int size, boolean doFree) {
        return new WrappedUnpooledUnsafeDirectByteBuf(ALLOC, memoryAddress, size, doFree);
    }

    public static ByteBuf wrappedBuffer(ByteBuf buffer) {
        if (buffer.isReadable()) {
            return buffer.slice();
        }
        buffer.release();
        return EMPTY_BUFFER;
    }

    public static ByteBuf wrappedBuffer(byte[]... arrays) {
        return wrappedBuffer(16, arrays);
    }

    public static ByteBuf wrappedBuffer(ByteBuf... buffers) {
        return wrappedBuffer(16, buffers);
    }

    public static ByteBuf wrappedBuffer(ByteBuffer... buffers) {
        return wrappedBuffer(16, buffers);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0035  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static io.netty.buffer.ByteBuf wrappedBuffer(int r6, byte[]... r7) {
        /*
            int r0 = r7.length
            r1 = 0
            switch(r0) {
                case 0: goto L_0x001a;
                case 1: goto L_0x000e;
                default: goto L_0x0005;
            }
        L_0x0005:
            java.util.ArrayList r0 = new java.util.ArrayList
            int r2 = r7.length
            r0.<init>(r2)
            int r2 = r7.length
            r3 = r1
            goto L_0x001b
        L_0x000e:
            r0 = r7[r1]
            int r0 = r0.length
            if (r0 == 0) goto L_0x003d
            r0 = r7[r1]
            io.netty.buffer.ByteBuf r0 = wrappedBuffer((byte[]) r0)
            return r0
        L_0x001a:
            goto L_0x003d
        L_0x001b:
            if (r3 >= r2) goto L_0x002f
            r4 = r7[r3]
            if (r4 != 0) goto L_0x0022
            goto L_0x002f
        L_0x0022:
            int r5 = r4.length
            if (r5 <= 0) goto L_0x002c
            io.netty.buffer.ByteBuf r5 = wrappedBuffer((byte[]) r4)
            r0.add(r5)
        L_0x002c:
            int r3 = r3 + 1
            goto L_0x001b
        L_0x002f:
            boolean r2 = r0.isEmpty()
            if (r2 != 0) goto L_0x003d
            io.netty.buffer.CompositeByteBuf r2 = new io.netty.buffer.CompositeByteBuf
            io.netty.buffer.ByteBufAllocator r3 = ALLOC
            r2.<init>((io.netty.buffer.ByteBufAllocator) r3, (boolean) r1, (int) r6, (java.lang.Iterable<io.netty.buffer.ByteBuf>) r0)
            return r2
        L_0x003d:
            io.netty.buffer.ByteBuf r0 = EMPTY_BUFFER
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.buffer.Unpooled.wrappedBuffer(int, byte[][]):io.netty.buffer.ByteBuf");
    }

    public static ByteBuf wrappedBuffer(int maxNumComponents, ByteBuf... buffers) {
        switch (buffers.length) {
            case 0:
                break;
            case 1:
                ByteBuf buffer = buffers[0];
                if (!buffer.isReadable()) {
                    buffer.release();
                    break;
                } else {
                    return wrappedBuffer(buffer.order(BIG_ENDIAN));
                }
            default:
                for (int i = 0; i < buffers.length; i++) {
                    ByteBuf buf = buffers[i];
                    if (buf.isReadable()) {
                        return new CompositeByteBuf(ALLOC, false, maxNumComponents, buffers, i, buffers.length);
                    }
                    buf.release();
                }
                break;
        }
        return EMPTY_BUFFER;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static io.netty.buffer.ByteBuf wrappedBuffer(int r6, java.nio.ByteBuffer... r7) {
        /*
            int r0 = r7.length
            r1 = 0
            switch(r0) {
                case 0: goto L_0x0023;
                case 1: goto L_0x000e;
                default: goto L_0x0005;
            }
        L_0x0005:
            java.util.ArrayList r0 = new java.util.ArrayList
            int r2 = r7.length
            r0.<init>(r2)
            int r2 = r7.length
            r3 = r1
            goto L_0x0024
        L_0x000e:
            r0 = r7[r1]
            boolean r0 = r0.hasRemaining()
            if (r0 == 0) goto L_0x004f
            r0 = r7[r1]
            java.nio.ByteOrder r1 = BIG_ENDIAN
            java.nio.ByteBuffer r0 = r0.order(r1)
            io.netty.buffer.ByteBuf r0 = wrappedBuffer((java.nio.ByteBuffer) r0)
            return r0
        L_0x0023:
            goto L_0x004f
        L_0x0024:
            if (r3 >= r2) goto L_0x0041
            r4 = r7[r3]
            if (r4 != 0) goto L_0x002b
            goto L_0x0041
        L_0x002b:
            int r5 = r4.remaining()
            if (r5 <= 0) goto L_0x003e
            java.nio.ByteOrder r5 = BIG_ENDIAN
            java.nio.ByteBuffer r5 = r4.order(r5)
            io.netty.buffer.ByteBuf r5 = wrappedBuffer((java.nio.ByteBuffer) r5)
            r0.add(r5)
        L_0x003e:
            int r3 = r3 + 1
            goto L_0x0024
        L_0x0041:
            boolean r2 = r0.isEmpty()
            if (r2 != 0) goto L_0x004f
            io.netty.buffer.CompositeByteBuf r2 = new io.netty.buffer.CompositeByteBuf
            io.netty.buffer.ByteBufAllocator r3 = ALLOC
            r2.<init>((io.netty.buffer.ByteBufAllocator) r3, (boolean) r1, (int) r6, (java.lang.Iterable<io.netty.buffer.ByteBuf>) r0)
            return r2
        L_0x004f:
            io.netty.buffer.ByteBuf r0 = EMPTY_BUFFER
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.buffer.Unpooled.wrappedBuffer(int, java.nio.ByteBuffer[]):io.netty.buffer.ByteBuf");
    }

    public static CompositeByteBuf compositeBuffer() {
        return compositeBuffer(16);
    }

    public static CompositeByteBuf compositeBuffer(int maxNumComponents) {
        return new CompositeByteBuf(ALLOC, false, maxNumComponents);
    }

    public static ByteBuf copiedBuffer(byte[] array) {
        if (array.length == 0) {
            return EMPTY_BUFFER;
        }
        return wrappedBuffer((byte[]) array.clone());
    }

    public static ByteBuf copiedBuffer(byte[] array, int offset, int length) {
        if (length == 0) {
            return EMPTY_BUFFER;
        }
        byte[] copy = new byte[length];
        System.arraycopy(array, offset, copy, 0, length);
        return wrappedBuffer(copy);
    }

    public static ByteBuf copiedBuffer(ByteBuffer buffer) {
        int length = buffer.remaining();
        if (length == 0) {
            return EMPTY_BUFFER;
        }
        byte[] copy = new byte[length];
        ByteBuffer duplicate = buffer.duplicate();
        duplicate.get(copy);
        return wrappedBuffer(copy).order(duplicate.order());
    }

    public static ByteBuf copiedBuffer(ByteBuf buffer) {
        int readable = buffer.readableBytes();
        if (readable <= 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf copy = buffer(readable);
        copy.writeBytes(buffer, buffer.readerIndex(), readable);
        return copy;
    }

    public static ByteBuf copiedBuffer(byte[]... arrays) {
        switch (arrays.length) {
            case 0:
                return EMPTY_BUFFER;
            case 1:
                if (arrays[0].length == 0) {
                    return EMPTY_BUFFER;
                }
                return copiedBuffer(arrays[0]);
            default:
                int length = 0;
                int length2 = arrays.length;
                int i = 0;
                while (i < length2) {
                    byte[] a = arrays[i];
                    if (Integer.MAX_VALUE - length >= a.length) {
                        length += a.length;
                        i++;
                    } else {
                        throw new IllegalArgumentException("The total length of the specified arrays is too big.");
                    }
                }
                if (length == 0) {
                    return EMPTY_BUFFER;
                }
                byte[] mergedArray = new byte[length];
                int j = 0;
                for (byte[] a2 : arrays) {
                    System.arraycopy(a2, 0, mergedArray, j, a2.length);
                    j += a2.length;
                }
                return wrappedBuffer(mergedArray);
        }
    }

    public static ByteBuf copiedBuffer(ByteBuf... buffers) {
        switch (buffers.length) {
            case 0:
                return EMPTY_BUFFER;
            case 1:
                return copiedBuffer(buffers[0]);
            default:
                ByteOrder order = null;
                int length = 0;
                for (ByteBuf b : buffers) {
                    int bLen = b.readableBytes();
                    if (bLen > 0) {
                        if (Integer.MAX_VALUE - length >= bLen) {
                            length += bLen;
                            if (order == null) {
                                order = b.order();
                            } else if (!order.equals(b.order())) {
                                throw new IllegalArgumentException("inconsistent byte order");
                            }
                        } else {
                            throw new IllegalArgumentException("The total length of the specified buffers is too big.");
                        }
                    }
                }
                if (length == 0) {
                    return EMPTY_BUFFER;
                }
                byte[] mergedArray = new byte[length];
                int j = 0;
                for (ByteBuf b2 : buffers) {
                    int bLen2 = b2.readableBytes();
                    b2.getBytes(b2.readerIndex(), mergedArray, j, bLen2);
                    j += bLen2;
                }
                return wrappedBuffer(mergedArray).order(order);
        }
    }

    public static ByteBuf copiedBuffer(ByteBuffer... buffers) {
        switch (buffers.length) {
            case 0:
                return EMPTY_BUFFER;
            case 1:
                return copiedBuffer(buffers[0]);
            default:
                ByteOrder order = null;
                int length = 0;
                for (ByteBuffer b : buffers) {
                    int bLen = b.remaining();
                    if (bLen > 0) {
                        if (Integer.MAX_VALUE - length >= bLen) {
                            length += bLen;
                            if (order == null) {
                                order = b.order();
                            } else if (!order.equals(b.order())) {
                                throw new IllegalArgumentException("inconsistent byte order");
                            }
                        } else {
                            throw new IllegalArgumentException("The total length of the specified buffers is too big.");
                        }
                    }
                }
                if (length == 0) {
                    return EMPTY_BUFFER;
                }
                byte[] mergedArray = new byte[length];
                int j = 0;
                for (ByteBuffer duplicate : buffers) {
                    ByteBuffer b2 = duplicate.duplicate();
                    int bLen2 = b2.remaining();
                    b2.get(mergedArray, j, bLen2);
                    j += bLen2;
                }
                return wrappedBuffer(mergedArray).order(order);
        }
    }

    public static ByteBuf copiedBuffer(CharSequence string, Charset charset) {
        if (string == null) {
            throw new NullPointerException(TypedValues.Custom.S_STRING);
        } else if (string instanceof CharBuffer) {
            return copiedBuffer((CharBuffer) string, charset);
        } else {
            return copiedBuffer(CharBuffer.wrap(string), charset);
        }
    }

    public static ByteBuf copiedBuffer(CharSequence string, int offset, int length, Charset charset) {
        if (string == null) {
            throw new NullPointerException(TypedValues.Custom.S_STRING);
        } else if (length == 0) {
            return EMPTY_BUFFER;
        } else {
            if (!(string instanceof CharBuffer)) {
                return copiedBuffer(CharBuffer.wrap(string, offset, offset + length), charset);
            }
            CharBuffer buf = (CharBuffer) string;
            if (buf.hasArray()) {
                return copiedBuffer(buf.array(), buf.arrayOffset() + buf.position() + offset, length, charset);
            }
            CharBuffer buf2 = buf.slice();
            buf2.limit(length);
            buf2.position(offset);
            return copiedBuffer(buf2, charset);
        }
    }

    public static ByteBuf copiedBuffer(char[] array, Charset charset) {
        if (array != null) {
            return copiedBuffer(array, 0, array.length, charset);
        }
        throw new NullPointerException("array");
    }

    public static ByteBuf copiedBuffer(char[] array, int offset, int length, Charset charset) {
        if (array == null) {
            throw new NullPointerException("array");
        } else if (length == 0) {
            return EMPTY_BUFFER;
        } else {
            return copiedBuffer(CharBuffer.wrap(array, offset, length), charset);
        }
    }

    private static ByteBuf copiedBuffer(CharBuffer buffer, Charset charset) {
        return ByteBufUtil.encodeString0(ALLOC, true, buffer, charset);
    }

    public static ByteBuf unmodifiableBuffer(ByteBuf buffer) {
        ByteOrder endianness = buffer.order();
        ByteOrder byteOrder = BIG_ENDIAN;
        if (endianness == byteOrder) {
            return new ReadOnlyByteBuf(buffer);
        }
        return new ReadOnlyByteBuf(buffer.order(byteOrder)).order(LITTLE_ENDIAN);
    }

    public static ByteBuf copyInt(int value) {
        ByteBuf buf = buffer(4);
        buf.writeInt(value);
        return buf;
    }

    public static ByteBuf copyInt(int... values) {
        if (values == null || values.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(values.length * 4);
        for (int v : values) {
            buffer.writeInt(v);
        }
        return buffer;
    }

    public static ByteBuf copyShort(int value) {
        ByteBuf buf = buffer(2);
        buf.writeShort(value);
        return buf;
    }

    public static ByteBuf copyShort(short... values) {
        if (values == null || values.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(values.length * 2);
        for (short v : values) {
            buffer.writeShort(v);
        }
        return buffer;
    }

    public static ByteBuf copyShort(int... values) {
        if (values == null || values.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(values.length * 2);
        for (int v : values) {
            buffer.writeShort(v);
        }
        return buffer;
    }

    public static ByteBuf copyMedium(int value) {
        ByteBuf buf = buffer(3);
        buf.writeMedium(value);
        return buf;
    }

    public static ByteBuf copyMedium(int... values) {
        if (values == null || values.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(values.length * 3);
        for (int v : values) {
            buffer.writeMedium(v);
        }
        return buffer;
    }

    public static ByteBuf copyLong(long value) {
        ByteBuf buf = buffer(8);
        buf.writeLong(value);
        return buf;
    }

    public static ByteBuf copyLong(long... values) {
        if (values == null || values.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(values.length * 8);
        for (long v : values) {
            buffer.writeLong(v);
        }
        return buffer;
    }

    public static ByteBuf copyBoolean(boolean value) {
        ByteBuf buf = buffer(1);
        buf.writeBoolean(value);
        return buf;
    }

    public static ByteBuf copyBoolean(boolean... values) {
        if (values == null || values.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(values.length);
        for (boolean v : values) {
            buffer.writeBoolean(v);
        }
        return buffer;
    }

    public static ByteBuf copyFloat(float value) {
        ByteBuf buf = buffer(4);
        buf.writeFloat(value);
        return buf;
    }

    public static ByteBuf copyFloat(float... values) {
        if (values == null || values.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(values.length * 4);
        for (float v : values) {
            buffer.writeFloat(v);
        }
        return buffer;
    }

    public static ByteBuf copyDouble(double value) {
        ByteBuf buf = buffer(8);
        buf.writeDouble(value);
        return buf;
    }

    public static ByteBuf copyDouble(double... values) {
        if (values == null || values.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(values.length * 8);
        for (double v : values) {
            buffer.writeDouble(v);
        }
        return buffer;
    }

    public static ByteBuf unreleasableBuffer(ByteBuf buf) {
        return new UnreleasableByteBuf(buf);
    }

    public static ByteBuf unmodifiableBuffer(ByteBuf... buffers) {
        return new FixedCompositeByteBuf(ALLOC, buffers);
    }

    private Unpooled() {
    }
}
