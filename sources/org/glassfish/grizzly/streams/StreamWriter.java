package org.glassfish.grizzly.streams;

import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.Transformer;

public interface StreamWriter extends Stream {
    GrizzlyFuture<Integer> close(CompletionHandler<Integer> completionHandler);

    <E> GrizzlyFuture<Stream> encode(Transformer<E, Buffer> transformer, E e);

    <E> GrizzlyFuture<Stream> encode(Transformer<E, Buffer> transformer, E e, CompletionHandler<Stream> completionHandler);

    GrizzlyFuture<Integer> flush();

    GrizzlyFuture<Integer> flush(CompletionHandler<Integer> completionHandler);

    Connection getConnection();

    long getTimeout(TimeUnit timeUnit);

    boolean isClosed();

    void setTimeout(long j, TimeUnit timeUnit);

    void writeBoolean(boolean z);

    void writeBooleanArray(boolean[] zArr);

    void writeBuffer(Buffer buffer);

    void writeByte(byte b);

    void writeByteArray(byte[] bArr);

    void writeByteArray(byte[] bArr, int i, int i2);

    void writeChar(char c);

    void writeCharArray(char[] cArr);

    void writeDouble(double d);

    void writeDoubleArray(double[] dArr);

    void writeFloat(float f);

    void writeFloatArray(float[] fArr);

    void writeInt(int i);

    void writeIntArray(int[] iArr);

    void writeLong(long j);

    void writeLongArray(long[] jArr);

    void writeShort(short s);

    void writeShortArray(short[] sArr);
}
