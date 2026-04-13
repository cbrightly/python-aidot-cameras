package io.ktor.utils.io.core;

import java.io.Closeable;
import java.nio.ByteBuffer;
import org.jetbrains.annotations.NotNull;

/* compiled from: Input.kt */
public interface w extends Closeable {
    long C0(long j);

    void close();

    long n0(@NotNull ByteBuffer byteBuffer, long j, long j2, long j3, long j4);

    byte readByte();

    boolean w0();
}
