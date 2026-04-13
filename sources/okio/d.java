package okio;

import java.io.OutputStream;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import org.jetbrains.annotations.NotNull;

/* compiled from: BufferedSink.kt */
public interface d extends b0, WritableByteChannel {
    @NotNull
    c buffer();

    @NotNull
    d emit();

    @NotNull
    d emitCompleteSegments();

    void flush();

    @NotNull
    c getBuffer();

    @NotNull
    OutputStream outputStream();

    @NotNull
    d write(@NotNull e0 e0Var, long j);

    @NotNull
    d write(@NotNull f fVar);

    @NotNull
    d write(@NotNull byte[] bArr);

    @NotNull
    d write(@NotNull byte[] bArr, int i, int i2);

    long writeAll(@NotNull e0 e0Var);

    @NotNull
    d writeByte(int i);

    @NotNull
    d writeDecimalLong(long j);

    @NotNull
    d writeHexadecimalUnsignedLong(long j);

    @NotNull
    d writeInt(int i);

    @NotNull
    d writeIntLe(int i);

    @NotNull
    d writeLong(long j);

    @NotNull
    d writeLongLe(long j);

    @NotNull
    d writeShort(int i);

    @NotNull
    d writeShortLe(int i);

    @NotNull
    d writeString(@NotNull String str, int i, int i2, @NotNull Charset charset);

    @NotNull
    d writeString(@NotNull String str, @NotNull Charset charset);

    @NotNull
    d writeUtf8(@NotNull String str);

    @NotNull
    d writeUtf8(@NotNull String str, int i, int i2);

    @NotNull
    d writeUtf8CodePoint(int i);
}
