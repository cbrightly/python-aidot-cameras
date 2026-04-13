package okhttp3.internal.ws;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.Closeable;
import java.io.IOException;
import java.util.Random;
import kotlin.jvm.internal.k;
import okio.c;
import okio.d;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WebSocketWriter.kt */
public final class h implements Closeable {
    @NotNull
    private final Random a1;
    private final boolean a2;
    private final c c = new c();
    private final c d;
    private boolean f;
    @NotNull
    private final d p0;
    private final boolean p1;
    private final long p2;
    private a q;
    private final byte[] x;
    private final c.a y;
    private final boolean z;

    public h(boolean isClient, @NotNull d sink, @NotNull Random random, boolean perMessageDeflate, boolean noContextTakeover, long minimumDeflateSize) {
        k.f(sink, "sink");
        k.f(random, "random");
        this.z = isClient;
        this.p0 = sink;
        this.a1 = random;
        this.p1 = perMessageDeflate;
        this.a2 = noContextTakeover;
        this.p2 = minimumDeflateSize;
        this.d = sink.getBuffer();
        c.a aVar = null;
        this.x = isClient ? new byte[4] : null;
        this.y = isClient ? new c.a() : aVar;
    }

    public final void i(@NotNull f payload) {
        k.f(payload, "payload");
        c(9, payload);
    }

    public final void j(@NotNull f payload) {
        k.f(payload, "payload");
        c(10, payload);
    }

    public final void a(int code, @Nullable f reason) {
        f payload = f.EMPTY;
        if (!(code == 0 && reason == null)) {
            if (code != 0) {
                f.a.c(code);
            }
            c $this$run = new c();
            $this$run.writeShort(code);
            if (reason != null) {
                $this$run.write(reason);
            }
            payload = $this$run.D0();
        }
        try {
            c(8, payload);
        } finally {
            this.f = true;
        }
    }

    private final void c(int opcode, f payload) {
        if (!this.f) {
            int length = payload.size();
            if (((long) length) <= 125) {
                this.d.writeByte(opcode | 128);
                int b1 = length;
                if (this.z) {
                    this.d.writeByte(b1 | 128);
                    Random random = this.a1;
                    byte[] bArr = this.x;
                    if (bArr == null) {
                        k.n();
                    }
                    random.nextBytes(bArr);
                    this.d.write(this.x);
                    if (length > 0) {
                        long payloadStart = this.d.e1();
                        this.d.write(payload);
                        c cVar = this.d;
                        c.a aVar = this.y;
                        if (aVar == null) {
                            k.n();
                        }
                        cVar.u(aVar);
                        this.y.i(payloadStart);
                        f.a.b(this.y, this.x);
                        this.y.close();
                    }
                } else {
                    this.d.writeByte(b1);
                    this.d.write(payload);
                }
                this.p0.flush();
                return;
            }
            throw new IllegalArgumentException("Payload size must be less than or equal to 125".toString());
        }
        throw new IOException("closed");
    }

    public final void g(int formatOpcode, @NotNull f data) {
        k.f(data, "data");
        if (!this.f) {
            this.c.write(data);
            int b0 = formatOpcode | 128;
            if (this.p1 && ((long) data.size()) >= this.p2) {
                a messageDeflater = this.q;
                if (messageDeflater == null) {
                    messageDeflater = new a(this.a2);
                    this.q = messageDeflater;
                }
                messageDeflater.a(this.c);
                b0 |= 64;
            }
            long dataSize = this.c.e1();
            this.d.writeByte(b0);
            int b1 = 0;
            if (this.z) {
                b1 = 0 | 128;
            }
            if (dataSize <= 125) {
                this.d.writeByte(b1 | ((int) dataSize));
            } else if (dataSize <= 65535) {
                this.d.writeByte(b1 | 126);
                this.d.writeShort((int) dataSize);
            } else {
                this.d.writeByte(b1 | NeedPermissionEvent.PER_IPC_SPEAK_PERM);
                this.d.writeLong(dataSize);
            }
            if (this.z) {
                Random random = this.a1;
                byte[] bArr = this.x;
                if (bArr == null) {
                    k.n();
                }
                random.nextBytes(bArr);
                this.d.write(this.x);
                if (dataSize > 0) {
                    c cVar = this.c;
                    c.a aVar = this.y;
                    if (aVar == null) {
                        k.n();
                    }
                    cVar.u(aVar);
                    this.y.i(0);
                    f.a.b(this.y, this.x);
                    this.y.close();
                }
            }
            this.d.write(this.c, dataSize);
            this.p0.emit();
            return;
        }
        throw new IOException("closed");
    }

    public void close() {
        a aVar = this.q;
        if (aVar != null) {
            aVar.close();
        }
    }
}
