package org.glassfish.grizzly;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.memory.BufferArray;
import org.glassfish.grizzly.memory.ByteBufferArray;

public interface Buffer extends Comparable<Buffer>, WritableMessage {
    void allowBufferDispose(boolean z);

    boolean allowBufferDispose();

    byte[] array();

    int arrayOffset();

    Buffer asReadOnlyBuffer();

    int capacity();

    Buffer clear();

    Buffer compact();

    void dispose();

    void dumpHex(Appendable appendable);

    Buffer duplicate();

    Buffer flip();

    byte get();

    byte get(int i);

    Buffer get(ByteBuffer byteBuffer);

    Buffer get(ByteBuffer byteBuffer, int i, int i2);

    Buffer get(byte[] bArr);

    Buffer get(byte[] bArr, int i, int i2);

    char getChar();

    char getChar(int i);

    double getDouble();

    double getDouble(int i);

    float getFloat();

    float getFloat(int i);

    int getInt();

    int getInt(int i);

    long getLong();

    long getLong(int i);

    short getShort();

    short getShort(int i);

    boolean hasArray();

    boolean hasRemaining();

    boolean isComposite();

    boolean isDirect();

    boolean isReadOnly();

    int limit();

    Buffer limit(int i);

    Buffer mark();

    ByteOrder order();

    Buffer order(ByteOrder byteOrder);

    int position();

    Buffer position(int i);

    Buffer prepend(Buffer buffer);

    Buffer put(byte b);

    Buffer put(int i, byte b);

    Buffer put(ByteBuffer byteBuffer);

    Buffer put(ByteBuffer byteBuffer, int i, int i2);

    Buffer put(Buffer buffer);

    Buffer put(Buffer buffer, int i, int i2);

    Buffer put(byte[] bArr);

    Buffer put(byte[] bArr, int i, int i2);

    Buffer put8BitString(String str);

    Buffer putChar(char c);

    Buffer putChar(int i, char c);

    Buffer putDouble(double d);

    Buffer putDouble(int i, double d);

    Buffer putFloat(float f);

    Buffer putFloat(int i, float f);

    Buffer putInt(int i);

    Buffer putInt(int i, int i2);

    Buffer putLong(int i, long j);

    Buffer putLong(long j);

    Buffer putShort(int i, short s);

    Buffer putShort(short s);

    int remaining();

    Buffer reset();

    Buffer rewind();

    void shrink();

    Buffer slice();

    Buffer slice(int i, int i2);

    Buffer split(int i);

    BufferArray toBufferArray();

    BufferArray toBufferArray(int i, int i2);

    BufferArray toBufferArray(BufferArray bufferArray);

    BufferArray toBufferArray(BufferArray bufferArray, int i, int i2);

    ByteBuffer toByteBuffer();

    ByteBuffer toByteBuffer(int i, int i2);

    ByteBufferArray toByteBufferArray();

    ByteBufferArray toByteBufferArray(int i, int i2);

    ByteBufferArray toByteBufferArray(ByteBufferArray byteBufferArray);

    ByteBufferArray toByteBufferArray(ByteBufferArray byteBufferArray, int i, int i2);

    String toStringContent();

    String toStringContent(Charset charset);

    String toStringContent(Charset charset, int i, int i2);

    void trim();

    boolean tryDispose();

    Object underlying();
}
