package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import okhttp3.internal.http2.c;
import okio.d0;
import okio.e;
import okio.e0;
import okio.f;
import okio.f0;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: Http2Reader.kt */
public final class g implements Closeable {
    /* access modifiers changed from: private */
    @NotNull
    public static final Logger c;
    public static final a d = new a((DefaultConstructorMarker) null);
    private final b f;
    private final c.a q;
    private final e x;
    private final boolean y;

    /* compiled from: Http2Reader.kt */
    public interface c {
        void a(boolean z, @NotNull l lVar);

        void b(int i, long j);

        void c(boolean z, int i, int i2, @NotNull List<b> list);

        void d(int i, @NotNull a aVar);

        void e(int i, @NotNull a aVar, @NotNull f fVar);

        void f(int i, int i2, @NotNull List<b> list);

        void h(boolean z, int i, int i2);

        void l();

        void m(boolean z, int i, @NotNull e eVar, int i2);

        void n(int i, int i2, int i3, boolean z);
    }

    public g(@NotNull e source, boolean client) {
        k.f(source, "source");
        this.x = source;
        this.y = client;
        b bVar = new b(source);
        this.f = bVar;
        this.q = new c.a(bVar, 4096, 0, 4, (DefaultConstructorMarker) null);
    }

    public final void g(@NotNull c handler) {
        k.f(handler, "handler");
        if (!this.y) {
            e eVar = this.x;
            f fVar = d.a;
            f connectionPreface = eVar.m0((long) fVar.size());
            Logger logger = c;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(okhttp3.internal.b.q("<< CONNECTION " + connectionPreface.hex(), new Object[0]));
            }
            if (true ^ k.a(fVar, connectionPreface)) {
                throw new IOException("Expected a connection header but was " + connectionPreface.utf8());
            }
        } else if (!c(true, handler)) {
            throw new IOException("Required SETTINGS preface not received");
        }
    }

    public final boolean c(boolean requireSettings, @NotNull c handler) {
        k.f(handler, "handler");
        try {
            this.x.k0(9);
            int length = okhttp3.internal.b.H(this.x);
            if (length <= 16384) {
                int type = okhttp3.internal.b.b(this.x.readByte(), 255);
                int flags = okhttp3.internal.b.b(this.x.readByte(), 255);
                int streamId = this.x.readInt() & Integer.MAX_VALUE;
                Logger logger = c;
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine(d.e.c(true, streamId, length, type, flags));
                }
                if (!requireSettings || type == 4) {
                    switch (type) {
                        case 0:
                            i(handler, length, flags, streamId);
                            return true;
                        case 1:
                            m(handler, length, flags, streamId);
                            return true;
                        case 2:
                            r(handler, length, flags, streamId);
                            return true;
                        case 3:
                            t(handler, length, flags, streamId);
                            return true;
                        case 4:
                            u(handler, length, flags, streamId);
                            return true;
                        case 5:
                            s(handler, length, flags, streamId);
                            return true;
                        case 6:
                            n(handler, length, flags, streamId);
                            return true;
                        case 7:
                            j(handler, length, flags, streamId);
                            return true;
                        case 8:
                            v(handler, length, flags, streamId);
                            return true;
                        default:
                            this.x.skip((long) length);
                            return true;
                    }
                } else {
                    throw new IOException("Expected a SETTINGS frame but was " + d.e.b(type));
                }
            } else {
                throw new IOException("FRAME_SIZE_ERROR: " + length);
            }
        } catch (EOFException e) {
            return false;
        }
    }

    private final void m(c handler, int length, int flags, int streamId) {
        if (streamId != 0) {
            int padding = 0;
            boolean endStream = (flags & 1) != 0;
            if ((flags & 8) != 0) {
                padding = okhttp3.internal.b.b(this.x.readByte(), 255);
            }
            int headerBlockLength = length;
            if ((flags & 32) != 0) {
                o(handler, streamId);
                headerBlockLength -= 5;
            }
            handler.c(endStream, streamId, -1, l(d.b(headerBlockLength, flags, padding), padding, flags, streamId));
            return;
        }
        throw new IOException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0");
    }

    private final List<b> l(int length, int padding, int flags, int streamId) {
        this.f.i(length);
        b bVar = this.f;
        bVar.j(bVar.a());
        this.f.l(padding);
        this.f.g(flags);
        this.f.m(streamId);
        this.q.k();
        return this.q.e();
    }

    private final void i(c handler, int length, int flags, int streamId) {
        if (streamId != 0) {
            int padding = 0;
            boolean gzipped = true;
            boolean inFinished = (flags & 1) != 0;
            if ((flags & 32) == 0) {
                gzipped = false;
            }
            if (!gzipped) {
                if ((flags & 8) != 0) {
                    padding = okhttp3.internal.b.b(this.x.readByte(), 255);
                }
                handler.m(inFinished, streamId, this.x, d.b(length, flags, padding));
                this.x.skip((long) padding);
                return;
            }
            throw new IOException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA");
        }
        throw new IOException("PROTOCOL_ERROR: TYPE_DATA streamId == 0");
    }

    private final void r(c handler, int length, int flags, int streamId) {
        if (length != 5) {
            throw new IOException("TYPE_PRIORITY length: " + length + " != 5");
        } else if (streamId != 0) {
            o(handler, streamId);
        } else {
            throw new IOException("TYPE_PRIORITY streamId == 0");
        }
    }

    private final void o(c handler, int streamId) {
        int w1 = this.x.readInt();
        handler.n(streamId, Integer.MAX_VALUE & w1, okhttp3.internal.b.b(this.x.readByte(), 255) + 1, (((int) IjkMediaMeta.AV_CH_WIDE_LEFT) & w1) != 0);
    }

    private final void t(c handler, int length, int flags, int streamId) {
        if (length != 4) {
            throw new IOException("TYPE_RST_STREAM length: " + length + " != 4");
        } else if (streamId != 0) {
            int errorCodeInt = this.x.readInt();
            a errorCode = a.Companion.a(errorCodeInt);
            if (errorCode != null) {
                handler.d(streamId, errorCode);
                return;
            }
            throw new IOException("TYPE_RST_STREAM unexpected error code: " + errorCodeInt);
        } else {
            throw new IOException("TYPE_RST_STREAM streamId == 0");
        }
    }

    private final void u(c handler, int length, int flags, int streamId) {
        int value;
        if (streamId != 0) {
            throw new IOException("TYPE_SETTINGS streamId != 0");
        } else if ((flags & 1) != 0) {
            if (length == 0) {
                handler.l();
                return;
            }
            throw new IOException("FRAME_SIZE_ERROR ack frame should be empty!");
        } else if (length % 6 == 0) {
            l settings = new l();
            kotlin.ranges.g k = n.k(n.l(0, length), 6);
            int i = k.a();
            int b2 = k.b();
            int e = k.e();
            if (e < 0 ? i >= b2 : i <= b2) {
                while (true) {
                    int id = okhttp3.internal.b.c(this.x.readShort(), 65535);
                    value = this.x.readInt();
                    switch (id) {
                        case 2:
                            if (!(value == 0 || value == 1)) {
                                throw new IOException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1");
                            }
                        case 3:
                            id = 4;
                            break;
                        case 4:
                            id = 7;
                            if (value < 0) {
                                throw new IOException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1");
                            }
                            break;
                        case 5:
                            if (value < 16384 || value > 16777215) {
                                break;
                            }
                    }
                    settings.h(id, value);
                    if (i != b2) {
                        i += e;
                    }
                }
                throw new IOException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: " + value);
            }
            handler.a(false, settings);
        } else {
            throw new IOException("TYPE_SETTINGS length % 6 != 0: " + length);
        }
    }

    private final void s(c handler, int length, int flags, int streamId) {
        if (streamId != 0) {
            int padding = (flags & 8) != 0 ? okhttp3.internal.b.b(this.x.readByte(), 255) : 0;
            handler.f(streamId, this.x.readInt() & Integer.MAX_VALUE, l(d.b(length - 4, flags, padding), padding, flags, streamId));
            return;
        }
        throw new IOException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0");
    }

    private final void n(c handler, int length, int flags, int streamId) {
        if (length != 8) {
            throw new IOException("TYPE_PING length != 8: " + length);
        } else if (streamId == 0) {
            handler.h((flags & 1) != 0, this.x.readInt(), this.x.readInt());
        } else {
            throw new IOException("TYPE_PING streamId != 0");
        }
    }

    private final void j(c handler, int length, int flags, int streamId) {
        if (length < 8) {
            throw new IOException("TYPE_GOAWAY length < 8: " + length);
        } else if (streamId == 0) {
            int lastStreamId = this.x.readInt();
            int errorCodeInt = this.x.readInt();
            int opaqueDataLength = length - 8;
            a errorCode = a.Companion.a(errorCodeInt);
            if (errorCode != null) {
                f debugData = f.EMPTY;
                if (opaqueDataLength > 0) {
                    debugData = this.x.m0((long) opaqueDataLength);
                }
                handler.e(lastStreamId, errorCode, debugData);
                return;
            }
            throw new IOException("TYPE_GOAWAY unexpected error code: " + errorCodeInt);
        } else {
            throw new IOException("TYPE_GOAWAY streamId != 0");
        }
    }

    private final void v(c handler, int length, int flags, int streamId) {
        if (length == 4) {
            long increment = okhttp3.internal.b.d(this.x.readInt(), 2147483647L);
            if (increment != 0) {
                handler.b(streamId, increment);
                return;
            }
            throw new IOException("windowSizeIncrement was 0");
        }
        throw new IOException("TYPE_WINDOW_UPDATE length !=4: " + length);
    }

    public void close() {
        this.x.close();
    }

    /* compiled from: Http2Reader.kt */
    public static final class b implements e0 {
        private int c;
        private int d;
        private int f;
        private int q;
        private int x;
        private final e y;

        public /* synthetic */ okio.g cursor() {
            return d0.a(this);
        }

        public b(@NotNull e source) {
            k.f(source, "source");
            this.y = source;
        }

        public final void j(int i) {
            this.c = i;
        }

        public final void g(int i) {
            this.d = i;
        }

        public final void m(int i) {
            this.f = i;
        }

        public final int a() {
            return this.q;
        }

        public final void i(int i) {
            this.q = i;
        }

        public final void l(int i) {
            this.x = i;
        }

        public long read(@NotNull okio.c sink, long byteCount) {
            k.f(sink, "sink");
            while (true) {
                int i = this.q;
                if (i == 0) {
                    this.y.skip((long) this.x);
                    this.x = 0;
                    if ((this.d & 4) != 0) {
                        return -1;
                    }
                    c();
                } else {
                    long read = this.y.read(sink, Math.min(byteCount, (long) i));
                    if (read == -1) {
                        return -1;
                    }
                    this.q -= (int) read;
                    return read;
                }
            }
        }

        @NotNull
        public f0 timeout() {
            return this.y.timeout();
        }

        public void close() {
        }

        private final void c() {
            int previousStreamId = this.f;
            int H = okhttp3.internal.b.H(this.y);
            this.q = H;
            this.c = H;
            int type = okhttp3.internal.b.b(this.y.readByte(), 255);
            this.d = okhttp3.internal.b.b(this.y.readByte(), 255);
            a aVar = g.d;
            if (aVar.a().isLoggable(Level.FINE)) {
                aVar.a().fine(d.e.c(true, this.f, this.c, type, this.d));
            }
            int readInt = this.y.readInt() & Integer.MAX_VALUE;
            this.f = readInt;
            if (type != 9) {
                throw new IOException(type + " != TYPE_CONTINUATION");
            } else if (readInt != previousStreamId) {
                throw new IOException("TYPE_CONTINUATION streamId changed");
            }
        }
    }

    /* compiled from: Http2Reader.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final Logger a() {
            return g.c;
        }

        public final int b(int length, int flags, int padding) {
            int result = length;
            if ((flags & 8) != 0) {
                result--;
            }
            if (padding <= result) {
                return result - padding;
            }
            throw new IOException("PROTOCOL_ERROR padding " + padding + " > remaining length " + result);
        }
    }

    static {
        Logger logger = Logger.getLogger(d.class.getName());
        k.b(logger, "Logger.getLogger(Http2::class.java.name)");
        c = logger;
    }
}
