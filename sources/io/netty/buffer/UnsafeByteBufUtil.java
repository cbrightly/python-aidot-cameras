package io.netty.buffer;

import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import org.glassfish.grizzly.http.server.util.MappingData;

public final class UnsafeByteBufUtil {
    static final boolean BIG_ENDIAN_NATIVE_ORDER = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    private static final boolean UNALIGNED = PlatformDependent.isUnaligned();
    private static final byte ZERO = 0;

    static byte getByte(long address) {
        return PlatformDependent.getByte(address);
    }

    static short getShort(long address) {
        if (!UNALIGNED) {
            return (short) ((PlatformDependent.getByte(address) << 8) | (PlatformDependent.getByte(1 + address) & 255));
        }
        short v = PlatformDependent.getShort(address);
        return BIG_ENDIAN_NATIVE_ORDER ? v : Short.reverseBytes(v);
    }

    static int getUnsignedMedium(long address) {
        short s;
        if (!UNALIGNED) {
            return ((PlatformDependent.getByte(address) & 255) << MappingData.PATH) | ((PlatformDependent.getByte(1 + address) & 255) << 8) | (PlatformDependent.getByte(2 + address) & 255);
        }
        int i = (PlatformDependent.getByte(address) & 255) << MappingData.PATH;
        if (BIG_ENDIAN_NATIVE_ORDER) {
            s = PlatformDependent.getShort(1 + address);
        } else {
            s = Short.reverseBytes(PlatformDependent.getShort(1 + address));
        }
        return i | (s & 65535);
    }

    static int getInt(long address) {
        if (!UNALIGNED) {
            return (PlatformDependent.getByte(address) << 24) | ((PlatformDependent.getByte(1 + address) & 255) << MappingData.PATH) | ((PlatformDependent.getByte(2 + address) & 255) << 8) | (PlatformDependent.getByte(3 + address) & 255);
        }
        int v = PlatformDependent.getInt(address);
        return BIG_ENDIAN_NATIVE_ORDER ? v : Integer.reverseBytes(v);
    }

    static long getLong(long address) {
        if (!UNALIGNED) {
            return (((long) PlatformDependent.getByte(address)) << 56) | ((((long) PlatformDependent.getByte(1 + address)) & 255) << 48) | ((((long) PlatformDependent.getByte(2 + address)) & 255) << 40) | ((((long) PlatformDependent.getByte(3 + address)) & 255) << 32) | ((((long) PlatformDependent.getByte(4 + address)) & 255) << 24) | ((((long) PlatformDependent.getByte(5 + address)) & 255) << 16) | ((((long) PlatformDependent.getByte(6 + address)) & 255) << 8) | (((long) PlatformDependent.getByte(7 + address)) & 255);
        }
        long v = PlatformDependent.getLong(address);
        return BIG_ENDIAN_NATIVE_ORDER ? v : Long.reverseBytes(v);
    }

    static void setByte(long address, int value) {
        PlatformDependent.putByte(address, (byte) value);
    }

    static void setShort(long address, int value) {
        short s;
        if (UNALIGNED) {
            if (BIG_ENDIAN_NATIVE_ORDER) {
                s = (short) value;
            } else {
                s = Short.reverseBytes((short) value);
            }
            PlatformDependent.putShort(address, s);
            return;
        }
        PlatformDependent.putByte(address, (byte) (value >>> 8));
        PlatformDependent.putByte(1 + address, (byte) value);
    }

    static void setMedium(long address, int value) {
        short s;
        PlatformDependent.putByte(address, (byte) (value >>> 16));
        if (UNALIGNED) {
            long j = 1 + address;
            if (BIG_ENDIAN_NATIVE_ORDER) {
                s = (short) value;
            } else {
                s = Short.reverseBytes((short) value);
            }
            PlatformDependent.putShort(j, s);
            return;
        }
        PlatformDependent.putByte(1 + address, (byte) (value >>> 8));
        PlatformDependent.putByte(2 + address, (byte) value);
    }

    static void setInt(long address, int value) {
        if (UNALIGNED) {
            PlatformDependent.putInt(address, BIG_ENDIAN_NATIVE_ORDER ? value : Integer.reverseBytes(value));
            return;
        }
        PlatformDependent.putByte(address, (byte) (value >>> 24));
        PlatformDependent.putByte(1 + address, (byte) (value >>> 16));
        PlatformDependent.putByte(2 + address, (byte) (value >>> 8));
        PlatformDependent.putByte(3 + address, (byte) value);
    }

    static void setLong(long address, long value) {
        if (UNALIGNED) {
            PlatformDependent.putLong(address, BIG_ENDIAN_NATIVE_ORDER ? value : Long.reverseBytes(value));
            return;
        }
        PlatformDependent.putByte(address, (byte) ((int) (value >>> 56)));
        PlatformDependent.putByte(1 + address, (byte) ((int) (value >>> 48)));
        PlatformDependent.putByte(2 + address, (byte) ((int) (value >>> 40)));
        PlatformDependent.putByte(3 + address, (byte) ((int) (value >>> 32)));
        PlatformDependent.putByte(4 + address, (byte) ((int) (value >>> 24)));
        PlatformDependent.putByte(5 + address, (byte) ((int) (value >>> 16)));
        PlatformDependent.putByte(6 + address, (byte) ((int) (value >>> 8)));
        PlatformDependent.putByte(7 + address, (byte) ((int) value));
    }

    static byte getByte(byte[] array, int index) {
        return PlatformDependent.getByte(array, index);
    }

    static short getShort(byte[] array, int index) {
        if (!UNALIGNED) {
            return (short) ((PlatformDependent.getByte(array, index) << 8) | (PlatformDependent.getByte(array, index + 1) & 255));
        }
        short v = PlatformDependent.getShort(array, index);
        return BIG_ENDIAN_NATIVE_ORDER ? v : Short.reverseBytes(v);
    }

    static int getUnsignedMedium(byte[] array, int index) {
        short s;
        if (!UNALIGNED) {
            return ((PlatformDependent.getByte(array, index) & 255) << MappingData.PATH) | ((PlatformDependent.getByte(array, index + 1) & 255) << 8) | (PlatformDependent.getByte(array, index + 2) & 255);
        }
        int i = (PlatformDependent.getByte(array, index) & 255) << MappingData.PATH;
        if (BIG_ENDIAN_NATIVE_ORDER) {
            s = PlatformDependent.getShort(array, index + 1);
        } else {
            s = Short.reverseBytes(PlatformDependent.getShort(array, index + 1));
        }
        return i | (s & 65535);
    }

    static int getInt(byte[] array, int index) {
        if (!UNALIGNED) {
            return (PlatformDependent.getByte(array, index) << 24) | ((PlatformDependent.getByte(array, index + 1) & 255) << MappingData.PATH) | ((PlatformDependent.getByte(array, index + 2) & 255) << 8) | (PlatformDependent.getByte(array, index + 3) & 255);
        }
        int v = PlatformDependent.getInt(array, index);
        return BIG_ENDIAN_NATIVE_ORDER ? v : Integer.reverseBytes(v);
    }

    static long getLong(byte[] array, int index) {
        if (!UNALIGNED) {
            return (((long) PlatformDependent.getByte(array, index)) << 56) | ((((long) PlatformDependent.getByte(array, index + 1)) & 255) << 48) | ((((long) PlatformDependent.getByte(array, index + 2)) & 255) << 40) | ((((long) PlatformDependent.getByte(array, index + 3)) & 255) << 32) | ((((long) PlatformDependent.getByte(array, index + 4)) & 255) << 24) | ((((long) PlatformDependent.getByte(array, index + 5)) & 255) << 16) | ((((long) PlatformDependent.getByte(array, index + 6)) & 255) << 8) | (((long) PlatformDependent.getByte(array, index + 7)) & 255);
        }
        long v = PlatformDependent.getLong(array, index);
        return BIG_ENDIAN_NATIVE_ORDER ? v : Long.reverseBytes(v);
    }

    static void setByte(byte[] array, int index, int value) {
        PlatformDependent.putByte(array, index, (byte) value);
    }

    static void setShort(byte[] array, int index, int value) {
        short s;
        if (UNALIGNED) {
            if (BIG_ENDIAN_NATIVE_ORDER) {
                s = (short) value;
            } else {
                s = Short.reverseBytes((short) value);
            }
            PlatformDependent.putShort(array, index, s);
            return;
        }
        PlatformDependent.putByte(array, index, (byte) (value >>> 8));
        PlatformDependent.putByte(array, index + 1, (byte) value);
    }

    static void setMedium(byte[] array, int index, int value) {
        short s;
        PlatformDependent.putByte(array, index, (byte) (value >>> 16));
        if (UNALIGNED) {
            int i = index + 1;
            if (BIG_ENDIAN_NATIVE_ORDER) {
                s = (short) value;
            } else {
                s = Short.reverseBytes((short) value);
            }
            PlatformDependent.putShort(array, i, s);
            return;
        }
        PlatformDependent.putByte(array, index + 1, (byte) (value >>> 8));
        PlatformDependent.putByte(array, index + 2, (byte) value);
    }

    static void setInt(byte[] array, int index, int value) {
        if (UNALIGNED) {
            PlatformDependent.putInt(array, index, BIG_ENDIAN_NATIVE_ORDER ? value : Integer.reverseBytes(value));
            return;
        }
        PlatformDependent.putByte(array, index, (byte) (value >>> 24));
        PlatformDependent.putByte(array, index + 1, (byte) (value >>> 16));
        PlatformDependent.putByte(array, index + 2, (byte) (value >>> 8));
        PlatformDependent.putByte(array, index + 3, (byte) value);
    }

    static void setLong(byte[] array, int index, long value) {
        if (UNALIGNED) {
            PlatformDependent.putLong(array, index, BIG_ENDIAN_NATIVE_ORDER ? value : Long.reverseBytes(value));
            return;
        }
        PlatformDependent.putByte(array, index, (byte) ((int) (value >>> 56)));
        PlatformDependent.putByte(array, index + 1, (byte) ((int) (value >>> 48)));
        PlatformDependent.putByte(array, index + 2, (byte) ((int) (value >>> 40)));
        PlatformDependent.putByte(array, index + 3, (byte) ((int) (value >>> 32)));
        PlatformDependent.putByte(array, index + 4, (byte) ((int) (value >>> 24)));
        PlatformDependent.putByte(array, index + 5, (byte) ((int) (value >>> 16)));
        PlatformDependent.putByte(array, index + 6, (byte) ((int) (value >>> 8)));
        PlatformDependent.putByte(array, index + 7, (byte) ((int) value));
    }

    static void setZero(byte[] array, int index, int length) {
        if (length != 0) {
            PlatformDependent.setMemory(array, index, (long) length, (byte) 0);
        }
    }

    static ByteBuf copy(AbstractByteBuf buf, long addr, int index, int length) {
        buf.checkIndex(index, length);
        ByteBuf copy = buf.alloc().directBuffer(length, buf.maxCapacity());
        if (length != 0) {
            if (copy.hasMemoryAddress()) {
                PlatformDependent.copyMemory(addr, copy.memoryAddress(), (long) length);
                copy.setIndex(0, length);
            } else {
                copy.writeBytes((ByteBuf) buf, index, length);
            }
        }
        return copy;
    }

    static int setBytes(AbstractByteBuf buf, long addr, int index, InputStream in, int length) {
        buf.checkIndex(index, length);
        ByteBuf tmpBuf = buf.alloc().heapBuffer(length);
        try {
            byte[] tmp = tmpBuf.array();
            int offset = tmpBuf.arrayOffset();
            int readBytes = in.read(tmp, offset, length);
            if (readBytes > 0) {
                PlatformDependent.copyMemory(tmp, offset, addr, (long) readBytes);
            }
            return readBytes;
        } finally {
            tmpBuf.release();
        }
    }

    static void getBytes(AbstractByteBuf buf, long addr, int index, ByteBuf dst, int dstIndex, int length) {
        AbstractByteBuf abstractByteBuf = buf;
        int i = index;
        ByteBuf byteBuf = dst;
        int i2 = dstIndex;
        int i3 = length;
        buf.checkIndex(i, i3);
        ObjectUtil.checkNotNull(byteBuf, "dst");
        if (MathUtil.isOutOfBounds(i2, i3, dst.capacity())) {
            throw new IndexOutOfBoundsException("dstIndex: " + i2);
        } else if (dst.hasMemoryAddress()) {
            PlatformDependent.copyMemory(addr, dst.memoryAddress() + ((long) i2), (long) i3);
        } else if (dst.hasArray()) {
            PlatformDependent.copyMemory(addr, dst.array(), dst.arrayOffset() + i2, (long) i3);
        } else {
            byteBuf.setBytes(i2, (ByteBuf) buf, i, i3);
        }
    }

    static void getBytes(AbstractByteBuf buf, long addr, int index, byte[] dst, int dstIndex, int length) {
        buf.checkIndex(index, length);
        ObjectUtil.checkNotNull(dst, "dst");
        if (MathUtil.isOutOfBounds(dstIndex, length, dst.length)) {
            throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
        } else if (length != 0) {
            PlatformDependent.copyMemory(addr, dst, dstIndex, (long) length);
        }
    }

    static void getBytes(AbstractByteBuf buf, long addr, int index, ByteBuffer dst) {
        buf.checkIndex(index, dst.remaining());
        if (dst.remaining() != 0) {
            if (dst.isDirect()) {
                if (!dst.isReadOnly()) {
                    PlatformDependent.copyMemory(addr, PlatformDependent.directBufferAddress(dst) + ((long) dst.position()), (long) dst.remaining());
                    dst.position(dst.position() + dst.remaining());
                    return;
                }
                throw new ReadOnlyBufferException();
            } else if (dst.hasArray()) {
                PlatformDependent.copyMemory(addr, dst.array(), dst.arrayOffset() + dst.position(), (long) dst.remaining());
                dst.position(dst.position() + dst.remaining());
            } else {
                dst.put(buf.nioBuffer());
            }
        }
    }

    static void setBytes(AbstractByteBuf buf, long addr, int index, ByteBuf src, int srcIndex, int length) {
        AbstractByteBuf abstractByteBuf = buf;
        int i = index;
        ByteBuf byteBuf = src;
        int i2 = srcIndex;
        int i3 = length;
        buf.checkIndex(i, i3);
        ObjectUtil.checkNotNull(byteBuf, "src");
        if (MathUtil.isOutOfBounds(i2, i3, src.capacity())) {
            throw new IndexOutOfBoundsException("srcIndex: " + i2);
        } else if (i3 == 0) {
        } else {
            if (src.hasMemoryAddress()) {
                PlatformDependent.copyMemory(src.memoryAddress() + ((long) i2), addr, (long) i3);
            } else if (src.hasArray()) {
                PlatformDependent.copyMemory(src.array(), src.arrayOffset() + i2, addr, (long) i3);
            } else {
                byteBuf.getBytes(i2, (ByteBuf) buf, i, i3);
            }
        }
    }

    static void setBytes(AbstractByteBuf buf, long addr, int index, byte[] src, int srcIndex, int length) {
        buf.checkIndex(index, length);
        if (length != 0) {
            PlatformDependent.copyMemory(src, srcIndex, addr, (long) length);
        }
    }

    static void setBytes(AbstractByteBuf buf, long addr, int index, ByteBuffer src) {
        buf.checkIndex(index, src.remaining());
        int length = src.remaining();
        if (length != 0) {
            if (src.isDirect()) {
                PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(src) + ((long) src.position()), addr, (long) src.remaining());
                src.position(src.position() + length);
            } else if (src.hasArray()) {
                long j = addr;
                PlatformDependent.copyMemory(src.array(), src.position() + src.arrayOffset(), j, (long) length);
                src.position(src.position() + length);
            } else {
                ByteBuf tmpBuf = buf.alloc().heapBuffer(length);
                try {
                    byte[] tmp = tmpBuf.array();
                    src.get(tmp, tmpBuf.arrayOffset(), length);
                    PlatformDependent.copyMemory(tmp, tmpBuf.arrayOffset(), addr, (long) length);
                } finally {
                    tmpBuf.release();
                }
            }
        }
    }

    static void getBytes(AbstractByteBuf buf, long addr, int index, OutputStream out, int length) {
        buf.checkIndex(index, length);
        if (length != 0) {
            ByteBuf tmpBuf = buf.alloc().heapBuffer(length);
            try {
                byte[] tmp = tmpBuf.array();
                int offset = tmpBuf.arrayOffset();
                PlatformDependent.copyMemory(addr, tmp, offset, (long) length);
                out.write(tmp, offset, length);
            } finally {
                tmpBuf.release();
            }
        }
    }

    static void setZero(long addr, int length) {
        if (length != 0) {
            PlatformDependent.setMemory(addr, (long) length, (byte) 0);
        }
    }

    static UnpooledUnsafeDirectByteBuf newUnsafeDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
        if (PlatformDependent.useDirectBufferNoCleaner()) {
            return new UnpooledUnsafeNoCleanerDirectByteBuf(alloc, initialCapacity, maxCapacity);
        }
        return new UnpooledUnsafeDirectByteBuf(alloc, initialCapacity, maxCapacity);
    }

    private UnsafeByteBufUtil() {
    }
}
