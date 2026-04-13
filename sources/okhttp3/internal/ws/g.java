package okhttp3.internal.ws;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.k;
import okhttp3.internal.b;
import okio.c;
import okio.e;
import okio.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: WebSocketReader.kt */
public final class g implements Closeable {
    private final boolean A4;
    private c a1;
    private final c.a a2;
    private boolean c;
    private int d;
    private long f;
    private final c p0 = new c();
    private final byte[] p1;
    private final boolean p2;
    @NotNull
    private final e p3;
    private final a p4;
    private boolean q;
    private boolean x;
    private boolean y;
    private final c z = new c();
    private final boolean z4;

    /* compiled from: WebSocketReader.kt */
    public interface a {
        void b(@NotNull f fVar);

        void c(@NotNull String str);

        void d(@NotNull f fVar);

        void e(@NotNull f fVar);

        void g(int i, @NotNull String str);
    }

    public g(boolean isClient, @NotNull e source, @NotNull a frameCallback, boolean perMessageDeflate, boolean noContextTakeover) {
        k.f(source, "source");
        k.f(frameCallback, "frameCallback");
        this.p2 = isClient;
        this.p3 = source;
        this.p4 = frameCallback;
        this.z4 = perMessageDeflate;
        this.A4 = noContextTakeover;
        c.a aVar = null;
        this.p1 = isClient ? null : new byte[4];
        this.a2 = !isClient ? new c.a() : aVar;
    }

    public final void a() {
        g();
        if (this.x) {
            c();
        } else {
            j();
        }
    }

    /* JADX INFO: finally extract failed */
    private final void g() {
        String str;
        if (!this.c) {
            long timeoutBefore = this.p3.timeout().h();
            this.p3.timeout().b();
            try {
                int b0 = b.b(this.p3.readByte(), 255);
                this.p3.timeout().g(timeoutBefore, TimeUnit.NANOSECONDS);
                int i = b0 & 15;
                this.d = i;
                boolean isMasked = true;
                boolean z2 = (b0 & 128) != 0;
                this.q = z2;
                boolean z3 = (b0 & 8) != 0;
                this.x = z3;
                if (!z3 || z2) {
                    boolean reservedFlag1 = (b0 & 64) != 0;
                    switch (i) {
                        case 1:
                        case 2:
                            if (!reservedFlag1) {
                                this.y = false;
                                break;
                            } else if (this.z4) {
                                this.y = true;
                                break;
                            } else {
                                throw new ProtocolException("Unexpected rsv1 flag");
                            }
                        default:
                            if (reservedFlag1) {
                                throw new ProtocolException("Unexpected rsv1 flag");
                            }
                            break;
                    }
                    if (!((b0 & 32) != 0)) {
                        if (!((b0 & 16) != 0)) {
                            int b1 = b.b(this.p3.readByte(), 255);
                            if ((b1 & 128) == 0) {
                                isMasked = false;
                            }
                            if (isMasked == this.p2) {
                                if (this.p2) {
                                    str = "Server-sent frames must not be masked.";
                                } else {
                                    str = "Client-sent frames must be masked.";
                                }
                                throw new ProtocolException(str);
                            }
                            long j = (long) (b1 & NeedPermissionEvent.PER_IPC_SPEAK_PERM);
                            this.f = j;
                            if (j == ((long) 126)) {
                                this.f = (long) b.c(this.p3.readShort(), 65535);
                            } else if (j == ((long) NeedPermissionEvent.PER_IPC_SPEAK_PERM)) {
                                long readLong = this.p3.readLong();
                                this.f = readLong;
                                if (readLong < 0) {
                                    throw new ProtocolException("Frame length 0x" + b.O(this.f) + " > 0x7FFFFFFFFFFFFFFF");
                                }
                            }
                            if (this.x && this.f > 125) {
                                throw new ProtocolException("Control frame must be less than 125B.");
                            } else if (isMasked) {
                                e eVar = this.p3;
                                byte[] bArr = this.p1;
                                if (bArr == null) {
                                    k.n();
                                }
                                eVar.readFully(bArr);
                            }
                        } else {
                            throw new ProtocolException("Unexpected rsv3 flag");
                        }
                    } else {
                        throw new ProtocolException("Unexpected rsv2 flag");
                    }
                } else {
                    throw new ProtocolException("Control frames must be final.");
                }
            } catch (Throwable th) {
                this.p3.timeout().g(timeoutBefore, TimeUnit.NANOSECONDS);
                throw th;
            }
        } else {
            throw new IOException("closed");
        }
    }

    private final void c() {
        long j = this.f;
        if (j > 0) {
            this.p3.L(this.z, j);
            if (!this.p2) {
                c cVar = this.z;
                c.a aVar = this.a2;
                if (aVar == null) {
                    k.n();
                }
                cVar.u(aVar);
                this.a2.i(0);
                f fVar = f.a;
                c.a aVar2 = this.a2;
                byte[] bArr = this.p1;
                if (bArr == null) {
                    k.n();
                }
                fVar.b(aVar2, bArr);
                this.a2.close();
            }
        }
        switch (this.d) {
            case 8:
                int code = 1005;
                String reason = "";
                long bufferSize = this.z.e1();
                if (bufferSize != 1) {
                    if (bufferSize != 0) {
                        code = this.z.readShort();
                        reason = this.z.a1();
                        String codeExceptionMessage = f.a.a(code);
                        if (codeExceptionMessage != null) {
                            throw new ProtocolException(codeExceptionMessage);
                        }
                    }
                    this.p4.g(code, reason);
                    this.c = true;
                    return;
                }
                throw new ProtocolException("Malformed close payload length of 1.");
            case 9:
                this.p4.d(this.z.D0());
                return;
            case 10:
                this.p4.e(this.z.D0());
                return;
            default:
                throw new ProtocolException("Unknown control opcode: " + b.N(this.d));
        }
    }

    private final void j() {
        int opcode = this.d;
        if (opcode == 1 || opcode == 2) {
            i();
            if (this.y) {
                c messageInflater = this.a1;
                if (messageInflater == null) {
                    messageInflater = new c(this.A4);
                    this.a1 = messageInflater;
                }
                messageInflater.a(this.p0);
            }
            if (opcode == 1) {
                this.p4.c(this.p0.a1());
            } else {
                this.p4.b(this.p0.D0());
            }
        } else {
            throw new ProtocolException("Unknown opcode: " + b.N(opcode));
        }
    }

    private final void l() {
        while (!this.c) {
            g();
            if (this.x) {
                c();
            } else {
                return;
            }
        }
    }

    private final void i() {
        while (!this.c) {
            long j = this.f;
            if (j > 0) {
                this.p3.L(this.p0, j);
                if (!this.p2) {
                    c cVar = this.p0;
                    c.a aVar = this.a2;
                    if (aVar == null) {
                        k.n();
                    }
                    cVar.u(aVar);
                    this.a2.i(this.p0.e1() - this.f);
                    f fVar = f.a;
                    c.a aVar2 = this.a2;
                    byte[] bArr = this.p1;
                    if (bArr == null) {
                        k.n();
                    }
                    fVar.b(aVar2, bArr);
                    this.a2.close();
                }
            }
            if (!this.q) {
                l();
                if (this.d != 0) {
                    throw new ProtocolException("Expected continuation opcode. Got: " + b.N(this.d));
                }
            } else {
                return;
            }
        }
        throw new IOException("closed");
    }

    public void close() {
        c cVar = this.a1;
        if (cVar != null) {
            cVar.close();
        }
    }
}
