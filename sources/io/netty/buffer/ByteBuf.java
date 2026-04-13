package io.netty.buffer;

import io.netty.util.ReferenceCounted;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

public abstract class ByteBuf implements ReferenceCounted, Comparable<ByteBuf> {
    public abstract ByteBufAllocator alloc();

    public abstract byte[] array();

    public abstract int arrayOffset();

    public abstract int bytesBefore(byte b);

    public abstract int bytesBefore(int i, byte b);

    public abstract int bytesBefore(int i, int i2, byte b);

    public abstract int capacity();

    public abstract ByteBuf capacity(int i);

    public abstract ByteBuf clear();

    public abstract int compareTo(ByteBuf byteBuf);

    public abstract ByteBuf copy();

    public abstract ByteBuf copy(int i, int i2);

    public abstract ByteBuf discardReadBytes();

    public abstract ByteBuf discardSomeReadBytes();

    public abstract ByteBuf duplicate();

    public abstract int ensureWritable(int i, boolean z);

    public abstract ByteBuf ensureWritable(int i);

    public abstract boolean equals(Object obj);

    public abstract int forEachByte(int i, int i2, ByteBufProcessor byteBufProcessor);

    public abstract int forEachByte(ByteBufProcessor byteBufProcessor);

    public abstract int forEachByteDesc(int i, int i2, ByteBufProcessor byteBufProcessor);

    public abstract int forEachByteDesc(ByteBufProcessor byteBufProcessor);

    public abstract boolean getBoolean(int i);

    public abstract byte getByte(int i);

    public abstract int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2);

    public abstract ByteBuf getBytes(int i, ByteBuf byteBuf);

    public abstract ByteBuf getBytes(int i, ByteBuf byteBuf, int i2);

    public abstract ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3);

    public abstract ByteBuf getBytes(int i, OutputStream outputStream, int i2);

    public abstract ByteBuf getBytes(int i, ByteBuffer byteBuffer);

    public abstract ByteBuf getBytes(int i, byte[] bArr);

    public abstract ByteBuf getBytes(int i, byte[] bArr, int i2, int i3);

    public abstract char getChar(int i);

    public abstract double getDouble(int i);

    public abstract float getFloat(int i);

    public abstract int getInt(int i);

    public abstract long getLong(int i);

    public abstract int getMedium(int i);

    public abstract short getShort(int i);

    public abstract short getUnsignedByte(int i);

    public abstract long getUnsignedInt(int i);

    public abstract int getUnsignedMedium(int i);

    public abstract int getUnsignedShort(int i);

    public abstract boolean hasArray();

    public abstract boolean hasMemoryAddress();

    public abstract int hashCode();

    public abstract int indexOf(int i, int i2, byte b);

    public abstract ByteBuffer internalNioBuffer(int i, int i2);

    public abstract boolean isDirect();

    public abstract boolean isReadable();

    public abstract boolean isReadable(int i);

    public abstract boolean isWritable();

    public abstract boolean isWritable(int i);

    public abstract ByteBuf markReaderIndex();

    public abstract ByteBuf markWriterIndex();

    public abstract int maxCapacity();

    public abstract int maxWritableBytes();

    public abstract long memoryAddress();

    public abstract ByteBuffer nioBuffer();

    public abstract ByteBuffer nioBuffer(int i, int i2);

    public abstract int nioBufferCount();

    public abstract ByteBuffer[] nioBuffers();

    public abstract ByteBuffer[] nioBuffers(int i, int i2);

    public abstract ByteBuf order(ByteOrder byteOrder);

    public abstract ByteOrder order();

    public abstract boolean readBoolean();

    public abstract byte readByte();

    public abstract int readBytes(GatheringByteChannel gatheringByteChannel, int i);

    public abstract ByteBuf readBytes(int i);

    public abstract ByteBuf readBytes(ByteBuf byteBuf);

    public abstract ByteBuf readBytes(ByteBuf byteBuf, int i);

    public abstract ByteBuf readBytes(ByteBuf byteBuf, int i, int i2);

    public abstract ByteBuf readBytes(OutputStream outputStream, int i);

    public abstract ByteBuf readBytes(ByteBuffer byteBuffer);

    public abstract ByteBuf readBytes(byte[] bArr);

    public abstract ByteBuf readBytes(byte[] bArr, int i, int i2);

    public abstract char readChar();

    public abstract double readDouble();

    public abstract float readFloat();

    public abstract int readInt();

    public abstract long readLong();

    public abstract int readMedium();

    public abstract short readShort();

    public abstract ByteBuf readSlice(int i);

    public abstract short readUnsignedByte();

    public abstract long readUnsignedInt();

    public abstract int readUnsignedMedium();

    public abstract int readUnsignedShort();

    public abstract int readableBytes();

    public abstract int readerIndex();

    public abstract ByteBuf readerIndex(int i);

    public abstract ByteBuf resetReaderIndex();

    public abstract ByteBuf resetWriterIndex();

    public abstract ByteBuf retain();

    public abstract ByteBuf retain(int i);

    public abstract ByteBuf setBoolean(int i, boolean z);

    public abstract ByteBuf setByte(int i, int i2);

    public abstract int setBytes(int i, InputStream inputStream, int i2);

    public abstract int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2);

    public abstract ByteBuf setBytes(int i, ByteBuf byteBuf);

    public abstract ByteBuf setBytes(int i, ByteBuf byteBuf, int i2);

    public abstract ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3);

    public abstract ByteBuf setBytes(int i, ByteBuffer byteBuffer);

    public abstract ByteBuf setBytes(int i, byte[] bArr);

    public abstract ByteBuf setBytes(int i, byte[] bArr, int i2, int i3);

    public abstract ByteBuf setChar(int i, int i2);

    public abstract ByteBuf setDouble(int i, double d);

    public abstract ByteBuf setFloat(int i, float f);

    public abstract ByteBuf setIndex(int i, int i2);

    public abstract ByteBuf setInt(int i, int i2);

    public abstract ByteBuf setLong(int i, long j);

    public abstract ByteBuf setMedium(int i, int i2);

    public abstract ByteBuf setShort(int i, int i2);

    public abstract ByteBuf setZero(int i, int i2);

    public abstract ByteBuf skipBytes(int i);

    public abstract ByteBuf slice();

    public abstract ByteBuf slice(int i, int i2);

    public abstract String toString();

    public abstract String toString(int i, int i2, Charset charset);

    public abstract String toString(Charset charset);

    public abstract ByteBuf unwrap();

    public abstract int writableBytes();

    public abstract ByteBuf writeBoolean(boolean z);

    public abstract ByteBuf writeByte(int i);

    public abstract int writeBytes(InputStream inputStream, int i);

    public abstract int writeBytes(ScatteringByteChannel scatteringByteChannel, int i);

    public abstract ByteBuf writeBytes(ByteBuf byteBuf);

    public abstract ByteBuf writeBytes(ByteBuf byteBuf, int i);

    public abstract ByteBuf writeBytes(ByteBuf byteBuf, int i, int i2);

    public abstract ByteBuf writeBytes(ByteBuffer byteBuffer);

    public abstract ByteBuf writeBytes(byte[] bArr);

    public abstract ByteBuf writeBytes(byte[] bArr, int i, int i2);

    public abstract ByteBuf writeChar(int i);

    public abstract ByteBuf writeDouble(double d);

    public abstract ByteBuf writeFloat(float f);

    public abstract ByteBuf writeInt(int i);

    public abstract ByteBuf writeLong(long j);

    public abstract ByteBuf writeMedium(int i);

    public abstract ByteBuf writeShort(int i);

    public abstract ByteBuf writeZero(int i);

    public abstract int writerIndex();

    public abstract ByteBuf writerIndex(int i);
}
