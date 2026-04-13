package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.internal.b;
import okhttp3.internal.http2.c;
import okio.c;
import okio.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: Http2Writer.kt */
public final class i implements Closeable {
    private static final Logger c = Logger.getLogger(d.class.getName());
    public static final a d = new a((DefaultConstructorMarker) null);
    private final c f;
    private final boolean p0;
    private int q = 16384;
    private boolean x;
    @NotNull
    private final c.b y;
    private final d z;

    public i(@NotNull d sink, boolean client) {
        k.f(sink, "sink");
        this.z = sink;
        this.p0 = client;
        okio.c cVar = new okio.c();
        this.f = cVar;
        this.y = new c.b(0, false, cVar, 3, (DefaultConstructorMarker) null);
    }

    public final synchronized void G() {
        if (this.x) {
            throw new IOException("closed");
        } else if (this.p0) {
            Logger logger = c;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(b.q(">> CONNECTION " + d.a.hex(), new Object[0]));
            }
            this.z.write(d.a);
            this.z.flush();
        }
    }

    public final synchronized void a(@NotNull l peerSettings) {
        k.f(peerSettings, "peerSettings");
        if (!this.x) {
            this.q = peerSettings.e(this.q);
            if (peerSettings.b() != -1) {
                this.y.e(peerSettings.b());
            }
            g(0, 0, 4, 1);
            this.z.flush();
        } else {
            throw new IOException("closed");
        }
    }

    public final synchronized void f(int streamId, int promisedStreamId, @NotNull List<b> requestHeaders) {
        k.f(requestHeaders, "requestHeaders");
        if (!this.x) {
            this.y.g(requestHeaders);
            long byteCount = this.f.e1();
            int length = (int) Math.min(((long) this.q) - 4, byteCount);
            g(streamId, length + 4, 5, byteCount == ((long) length) ? 4 : 0);
            this.z.writeInt(Integer.MAX_VALUE & promisedStreamId);
            this.z.write(this.f, (long) length);
            if (byteCount > ((long) length)) {
                n(streamId, byteCount - ((long) length));
            }
        } else {
            throw new IOException("closed");
        }
    }

    public final synchronized void flush() {
        if (!this.x) {
            this.z.flush();
        } else {
            throw new IOException("closed");
        }
    }

    public final synchronized void l(int streamId, @NotNull a errorCode) {
        k.f(errorCode, "errorCode");
        if (!this.x) {
            if (errorCode.getHttpCode() != -1) {
                g(streamId, 4, 3, 0);
                this.z.writeInt(errorCode.getHttpCode());
                this.z.flush();
            } else {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        } else {
            throw new IOException("closed");
        }
    }

    public final int j0() {
        return this.q;
    }

    public final synchronized void K(boolean outFinished, int streamId, @Nullable okio.c source, int byteCount) {
        if (!this.x) {
            int flags = 0;
            if (outFinished) {
                flags = 0 | 1;
            }
            c(streamId, flags, source, byteCount);
        } else {
            throw new IOException("closed");
        }
    }

    public final void c(int streamId, int flags, @Nullable okio.c buffer, int byteCount) {
        g(streamId, byteCount, 0, flags);
        if (byteCount > 0) {
            d dVar = this.z;
            if (buffer == null) {
                k.n();
            }
            dVar.write(buffer, (long) byteCount);
        }
    }

    public final synchronized void m(@NotNull l settings) {
        int id;
        k.f(settings, "settings");
        if (!this.x) {
            g(0, settings.i() * 6, 4, 0);
            for (int i = 0; i < 10; i++) {
                if (settings.f(i)) {
                    switch (i) {
                        case 4:
                            id = 3;
                            break;
                        case 7:
                            id = 4;
                            break;
                        default:
                            id = i;
                            break;
                    }
                    this.z.writeShort(id);
                    this.z.writeInt(settings.a(i));
                }
            }
            this.z.flush();
        } else {
            throw new IOException("closed");
        }
    }

    public final synchronized void h(boolean ack, int payload1, int payload2) {
        if (!this.x) {
            g(0, 8, 6, ack ? 1 : 0);
            this.z.writeInt(payload1);
            this.z.writeInt(payload2);
            this.z.flush();
        } else {
            throw new IOException("closed");
        }
    }

    public final synchronized void i(int lastGoodStreamId, @NotNull a errorCode, @NotNull byte[] debugData) {
        k.f(errorCode, "errorCode");
        k.f(debugData, "debugData");
        if (!this.x) {
            boolean z2 = false;
            if (errorCode.getHttpCode() != -1) {
                g(0, debugData.length + 8, 7, 0);
                this.z.writeInt(lastGoodStreamId);
                this.z.writeInt(errorCode.getHttpCode());
                if (debugData.length == 0) {
                    z2 = true;
                }
                if (!z2) {
                    this.z.write(debugData);
                }
                this.z.flush();
            } else {
                throw new IllegalArgumentException("errorCode.httpCode == -1".toString());
            }
        } else {
            throw new IOException("closed");
        }
    }

    public final synchronized void b(int streamId, long windowSizeIncrement) {
        if (!this.x) {
            if (windowSizeIncrement != 0 && windowSizeIncrement <= 2147483647L) {
                g(streamId, 4, 8, 0);
                this.z.writeInt((int) windowSizeIncrement);
                this.z.flush();
            } else {
                throw new IllegalArgumentException(("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: " + windowSizeIncrement).toString());
            }
        } else {
            throw new IOException("closed");
        }
    }

    public final void g(int streamId, int length, int type, int flags) {
        Logger logger = c;
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(d.e.c(false, streamId, length, type, flags));
        }
        boolean z2 = true;
        if (length <= this.q) {
            if ((((int) IjkMediaMeta.AV_CH_WIDE_LEFT) & streamId) != 0) {
                z2 = false;
            }
            if (z2) {
                b.Y(this.z, length);
                this.z.writeByte(type & 255);
                this.z.writeByte(flags & 255);
                this.z.writeInt(Integer.MAX_VALUE & streamId);
                return;
            }
            throw new IllegalArgumentException(("reserved bit set: " + streamId).toString());
        }
        throw new IllegalArgumentException(("FRAME_SIZE_ERROR length > " + this.q + ": " + length).toString());
    }

    public synchronized void close() {
        this.x = true;
        this.z.close();
    }

    private final void n(int streamId, long byteCount) {
        long byteCount2 = byteCount;
        while (byteCount2 > 0) {
            long length = Math.min((long) this.q, byteCount2);
            byteCount2 -= length;
            g(streamId, (int) length, 9, byteCount2 == 0 ? 4 : 0);
            this.z.write(this.f, length);
        }
    }

    public final synchronized void j(boolean outFinished, int streamId, @NotNull List<b> headerBlock) {
        k.f(headerBlock, "headerBlock");
        if (!this.x) {
            this.y.g(headerBlock);
            long byteCount = this.f.e1();
            long length = Math.min((long) this.q, byteCount);
            int flags = byteCount == length ? 4 : 0;
            if (outFinished) {
                flags |= 1;
            }
            g(streamId, (int) length, 1, flags);
            this.z.write(this.f, length);
            if (byteCount > length) {
                n(streamId, byteCount - length);
            }
        } else {
            throw new IOException("closed");
        }
    }

    /* compiled from: Http2Writer.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
