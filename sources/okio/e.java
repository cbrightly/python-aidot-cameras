package okio;

import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import org.jetbrains.annotations.NotNull;

/* compiled from: BufferedSource.kt */
public interface e extends e0, ReadableByteChannel {
    @NotNull
    f D0();

    long F(@NotNull f fVar);

    void L(@NotNull c cVar, long j);

    long M(byte b, long j, long j2);

    long M0(@NotNull b0 b0Var);

    long N(@NotNull f fVar);

    @NotNull
    String Q(long j);

    boolean V(long j, @NotNull f fVar);

    long X0();

    @NotNull
    InputStream Y0();

    int Z0(@NotNull s sVar);

    @NotNull
    c buffer();

    @NotNull
    String d0();

    @NotNull
    byte[] g0(long j);

    @NotNull
    c getBuffer();

    void k0(long j);

    @NotNull
    f m0(long j);

    @NotNull
    e peek();

    @NotNull
    byte[] q0();

    boolean r0();

    byte readByte();

    void readFully(@NotNull byte[] bArr);

    int readInt();

    long readLong();

    short readShort();

    boolean request(long j);

    long s0();

    void skip(long j);

    @NotNull
    String x0(@NotNull Charset charset);
}
