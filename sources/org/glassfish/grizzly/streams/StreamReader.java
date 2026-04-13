package org.glassfish.grizzly.streams;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.utils.conditions.Condition;

public interface StreamReader extends Stream {
    int available();

    <E> GrizzlyFuture<E> decode(Transformer<Stream, E> transformer);

    <E> GrizzlyFuture<E> decode(Transformer<Stream, E> transformer, CompletionHandler<E> completionHandler);

    Buffer getBufferWindow();

    boolean hasAvailable();

    boolean isClosed();

    boolean isSupportBufferWindow();

    GrizzlyFuture<Integer> notifyAvailable(int i);

    GrizzlyFuture<Integer> notifyAvailable(int i, CompletionHandler<Integer> completionHandler);

    GrizzlyFuture<Integer> notifyCondition(Condition condition);

    GrizzlyFuture<Integer> notifyCondition(Condition condition, CompletionHandler<Integer> completionHandler);

    boolean readBoolean();

    void readBooleanArray(boolean[] zArr);

    byte readByte();

    void readByteArray(byte[] bArr);

    void readByteArray(byte[] bArr, int i, int i2);

    void readBytes(Buffer buffer);

    char readChar();

    void readCharArray(char[] cArr);

    double readDouble();

    void readDoubleArray(double[] dArr);

    float readFloat();

    void readFloatArray(float[] fArr);

    int readInt();

    void readIntArray(int[] iArr);

    long readLong();

    void readLongArray(long[] jArr);

    short readShort();

    void readShortArray(short[] sArr);

    void skip(int i);

    Buffer takeBufferWindow();
}
