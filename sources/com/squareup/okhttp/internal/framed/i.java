package com.squareup.okhttp.internal.framed;

import com.squareup.okhttp.internal.framed.b;
import com.squareup.okhttp.internal.framed.h;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.d0;
import okio.e;
import okio.e0;
import okio.f;
import okio.f0;
import okio.g;
import org.glassfish.grizzly.http.server.util.MappingData;

/* compiled from: Http2 */
public final class i implements p {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger(b.class.getName());
    /* access modifiers changed from: private */
    public static final f b = f.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");

    public b a(e source, boolean client) {
        return new c(source, 4096, client);
    }

    public c b(okio.d sink, boolean client) {
        return new d(sink, client);
    }

    /* compiled from: Http2 */
    public static final class c implements b {
        private final e c;
        private final a d;
        private final boolean f;
        final h.a q;

        c(e source, int headerTableSize, boolean client) {
            this.c = source;
            this.f = client;
            a aVar = new a(source);
            this.d = aVar;
            this.q = new h.a(headerTableSize, aVar);
        }

        public void c0() {
            if (!this.f) {
                f connectionPreface = this.c.m0((long) i.b.size());
                if (i.a.isLoggable(Level.FINE)) {
                    i.a.fine(String.format("<< CONNECTION %s", new Object[]{connectionPreface.hex()}));
                }
                if (!i.b.equals(connectionPreface)) {
                    throw i.k("Expected a connection header but was %s", connectionPreface.utf8());
                }
            }
        }

        public boolean S(b.a handler) {
            try {
                this.c.k0(9);
                int length = i.m(this.c);
                if (length < 0 || length > 16384) {
                    throw i.k("FRAME_SIZE_ERROR: %s", Integer.valueOf(length));
                }
                byte type = (byte) (this.c.readByte() & 255);
                byte flags = (byte) (this.c.readByte() & 255);
                int streamId = this.c.readInt() & Integer.MAX_VALUE;
                if (i.a.isLoggable(Level.FINE)) {
                    i.a.fine(b.b(true, streamId, length, type, flags));
                }
                switch (type) {
                    case 0:
                        a(handler, length, flags, streamId);
                        break;
                    case 1:
                        i(handler, length, flags, streamId);
                        break;
                    case 2:
                        m(handler, length, flags, streamId);
                        break;
                    case 3:
                        o(handler, length, flags, streamId);
                        break;
                    case 4:
                        r(handler, length, flags, streamId);
                        break;
                    case 5:
                        n(handler, length, flags, streamId);
                        break;
                    case 6:
                        j(handler, length, flags, streamId);
                        break;
                    case 7:
                        c(handler, length, flags, streamId);
                        break;
                    case 8:
                        s(handler, length, flags, streamId);
                        break;
                    default:
                        this.c.skip((long) length);
                        break;
                }
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        private void i(b.a handler, int length, byte flags, int streamId) {
            short padding = 0;
            if (streamId != 0) {
                boolean endStream = (flags & 1) != 0;
                if ((flags & 8) != 0) {
                    padding = (short) (this.c.readByte() & 255);
                }
                if ((flags & 32) != 0) {
                    l(handler, streamId);
                    length -= 5;
                }
                handler.p(false, endStream, streamId, -1, g(i.l(length, flags, padding), padding, flags, streamId), g.HTTP_20_HEADERS);
                return;
            }
            throw i.k("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
        }

        private List<f> g(int length, short padding, byte flags, int streamId) {
            a aVar = this.d;
            aVar.x = length;
            aVar.d = length;
            aVar.y = padding;
            aVar.f = flags;
            aVar.q = streamId;
            this.q.l();
            return this.q.e();
        }

        private void a(b.a handler, int length, byte flags, int streamId) {
            boolean gzipped = true;
            short padding = 0;
            boolean inFinished = (flags & 1) != 0;
            if ((flags & 32) == 0) {
                gzipped = false;
            }
            if (!gzipped) {
                if ((flags & 8) != 0) {
                    padding = (short) (this.c.readByte() & 255);
                }
                handler.m(inFinished, streamId, this.c, i.l(length, flags, padding));
                this.c.skip((long) padding);
                return;
            }
            throw i.k("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
        }

        private void m(b.a handler, int length, byte flags, int streamId) {
            if (length != 5) {
                throw i.k("TYPE_PRIORITY length: %d != 5", Integer.valueOf(length));
            } else if (streamId != 0) {
                l(handler, streamId);
            } else {
                throw i.k("TYPE_PRIORITY streamId == 0", new Object[0]);
            }
        }

        private void l(b.a handler, int streamId) {
            int w1 = this.c.readInt();
            handler.n(streamId, Integer.MAX_VALUE & w1, (this.c.readByte() & 255) + 1, (Integer.MIN_VALUE & w1) != 0);
        }

        private void o(b.a handler, int length, byte flags, int streamId) {
            if (length != 4) {
                throw i.k("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(length));
            } else if (streamId != 0) {
                int errorCodeInt = this.c.readInt();
                a errorCode = a.fromHttp2(errorCodeInt);
                if (errorCode != null) {
                    handler.k(streamId, errorCode);
                } else {
                    throw i.k("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(errorCodeInt));
                }
            } else {
                throw i.k("TYPE_RST_STREAM streamId == 0", new Object[0]);
            }
        }

        private void r(b.a handler, int length, byte flags, int streamId) {
            if (streamId != 0) {
                throw i.k("TYPE_SETTINGS streamId != 0", new Object[0]);
            } else if ((flags & 1) != 0) {
                if (length == 0) {
                    handler.l();
                    return;
                }
                throw i.k("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
            } else if (length % 6 == 0) {
                n settings = new n();
                for (int i = 0; i < length; i += 6) {
                    short id = this.c.readShort();
                    int value = this.c.readInt();
                    switch (id) {
                        case 1:
                        case 6:
                            break;
                        case 2:
                            if (!(value == 0 || value == 1)) {
                                throw i.k("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                            }
                        case 3:
                            id = 4;
                            break;
                        case 4:
                            id = 7;
                            if (value >= 0) {
                                break;
                            } else {
                                throw i.k("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                            }
                        case 5:
                            if (value >= 16384 && value <= 16777215) {
                                break;
                            } else {
                                throw i.k("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(value));
                            }
                            break;
                        default:
                            throw i.k("PROTOCOL_ERROR invalid settings id: %s", Short.valueOf(id));
                    }
                    settings.l(id, 0, value);
                }
                handler.o(false, settings);
                if (settings.d() >= 0) {
                    this.q.g(settings.d());
                }
            } else {
                throw i.k("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(length));
            }
        }

        private void n(b.a handler, int length, byte flags, int streamId) {
            short padding = 0;
            if (streamId != 0) {
                if ((flags & 8) != 0) {
                    padding = (short) (this.c.readByte() & 255);
                }
                handler.f(streamId, this.c.readInt() & Integer.MAX_VALUE, g(i.l(length - 4, flags, padding), padding, flags, streamId));
                return;
            }
            throw i.k("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
        }

        private void j(b.a handler, int length, byte flags, int streamId) {
            boolean ack = false;
            if (length != 8) {
                throw i.k("TYPE_PING length != 8: %s", Integer.valueOf(length));
            } else if (streamId == 0) {
                int payload1 = this.c.readInt();
                int payload2 = this.c.readInt();
                if ((flags & 1) != 0) {
                    ack = true;
                }
                handler.h(ack, payload1, payload2);
            } else {
                throw i.k("TYPE_PING streamId != 0", new Object[0]);
            }
        }

        private void c(b.a handler, int length, byte flags, int streamId) {
            if (length < 8) {
                throw i.k("TYPE_GOAWAY length < 8: %s", Integer.valueOf(length));
            } else if (streamId == 0) {
                int lastStreamId = this.c.readInt();
                int errorCodeInt = this.c.readInt();
                int opaqueDataLength = length - 8;
                a errorCode = a.fromHttp2(errorCodeInt);
                if (errorCode != null) {
                    f debugData = f.EMPTY;
                    if (opaqueDataLength > 0) {
                        debugData = this.c.m0((long) opaqueDataLength);
                    }
                    handler.q(lastStreamId, errorCode, debugData);
                    return;
                }
                throw i.k("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(errorCodeInt));
            } else {
                throw i.k("TYPE_GOAWAY streamId != 0", new Object[0]);
            }
        }

        private void s(b.a handler, int length, byte flags, int streamId) {
            if (length == 4) {
                long increment = ((long) this.c.readInt()) & 2147483647L;
                if (increment != 0) {
                    handler.b(streamId, increment);
                } else {
                    throw i.k("windowSizeIncrement was 0", Long.valueOf(increment));
                }
            } else {
                throw i.k("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(length));
            }
        }

        public void close() {
            this.c.close();
        }
    }

    /* compiled from: Http2 */
    public static final class d implements c {
        private final okio.d c;
        private final boolean d;
        private final okio.c f;
        private final h.b q;
        private int x = 16384;
        private boolean y;

        d(okio.d sink, boolean client) {
            this.c = sink;
            this.d = client;
            okio.c cVar = new okio.c();
            this.f = cVar;
            this.q = new h.b(cVar);
        }

        public synchronized void flush() {
            if (!this.y) {
                this.c.flush();
            } else {
                throw new IOException("closed");
            }
        }

        public synchronized void B0(n peerSettings) {
            if (!this.y) {
                this.x = peerSettings.g(this.x);
                c(0, 0, (byte) 4, (byte) 1);
                this.c.flush();
            } else {
                throw new IOException("closed");
            }
        }

        public synchronized void G() {
            if (this.y) {
                throw new IOException("closed");
            } else if (this.d) {
                if (i.a.isLoggable(Level.FINE)) {
                    i.a.fine(String.format(">> CONNECTION %s", new Object[]{i.b.hex()}));
                }
                this.c.write(i.b.toByteArray());
                this.c.flush();
            }
        }

        public synchronized void T0(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<f> headerBlock) {
            if (!inFinished) {
                try {
                    if (!this.y) {
                        g(outFinished, streamId, headerBlock);
                    } else {
                        throw new IOException("closed");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public synchronized void f(int streamId, int promisedStreamId, List<f> requestHeaders) {
            if (!this.y) {
                this.q.b(requestHeaders);
                long byteCount = this.f.e1();
                byte flags = 4;
                int length = (int) Math.min((long) (this.x - 4), byteCount);
                if (byteCount != ((long) length)) {
                    flags = 0;
                }
                c(streamId, length + 4, (byte) 5, flags);
                this.c.writeInt(Integer.MAX_VALUE & promisedStreamId);
                this.c.write(this.f, (long) length);
                if (byteCount > ((long) length)) {
                    i(streamId, byteCount - ((long) length));
                }
            } else {
                throw new IOException("closed");
            }
        }

        /* access modifiers changed from: package-private */
        public void g(boolean outFinished, int streamId, List<f> headerBlock) {
            if (!this.y) {
                this.q.b(headerBlock);
                long byteCount = this.f.e1();
                int length = (int) Math.min((long) this.x, byteCount);
                byte flags = byteCount == ((long) length) ? (byte) 4 : 0;
                if (outFinished) {
                    flags = (byte) (flags | 1);
                }
                c(streamId, length, (byte) 1, flags);
                this.c.write(this.f, (long) length);
                if (byteCount > ((long) length)) {
                    i(streamId, byteCount - ((long) length));
                    return;
                }
                return;
            }
            throw new IOException("closed");
        }

        private void i(int streamId, long byteCount) {
            while (byteCount > 0) {
                int length = (int) Math.min((long) this.x, byteCount);
                byteCount -= (long) length;
                c(streamId, length, (byte) 9, byteCount == 0 ? (byte) 4 : 0);
                this.c.write(this.f, (long) length);
            }
        }

        public synchronized void k(int streamId, a errorCode) {
            if (this.y) {
                throw new IOException("closed");
            } else if (errorCode.httpCode != -1) {
                c(streamId, 4, (byte) 3, (byte) 0);
                this.c.writeInt(errorCode.httpCode);
                this.c.flush();
            } else {
                throw new IllegalArgumentException();
            }
        }

        public int j0() {
            return this.x;
        }

        public synchronized void K(boolean outFinished, int streamId, okio.c source, int byteCount) {
            if (!this.y) {
                byte flags = 0;
                if (outFinished) {
                    flags = (byte) (0 | 1);
                }
                a(streamId, flags, source, byteCount);
            } else {
                throw new IOException("closed");
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int streamId, byte flags, okio.c buffer, int byteCount) {
            c(streamId, byteCount, (byte) 0, flags);
            if (byteCount > 0) {
                this.c.write(buffer, (long) byteCount);
            }
        }

        public synchronized void W0(n settings) {
            if (!this.y) {
                c(0, settings.m() * 6, (byte) 4, (byte) 0);
                for (int i = 0; i < 10; i++) {
                    if (settings.i(i)) {
                        int id = i;
                        if (id == 4) {
                            id = 3;
                        } else if (id == 7) {
                            id = 4;
                        }
                        this.c.writeShort(id);
                        this.c.writeInt(settings.c(i));
                    }
                }
                this.c.flush();
            } else {
                throw new IOException("closed");
            }
        }

        public synchronized void h(boolean ack, int payload1, int payload2) {
            if (!this.y) {
                c(0, 8, (byte) 6, ack ? (byte) 1 : 0);
                this.c.writeInt(payload1);
                this.c.writeInt(payload2);
                this.c.flush();
            } else {
                throw new IOException("closed");
            }
        }

        public synchronized void C(int lastGoodStreamId, a errorCode, byte[] debugData) {
            if (this.y) {
                throw new IOException("closed");
            } else if (errorCode.httpCode != -1) {
                c(0, debugData.length + 8, (byte) 7, (byte) 0);
                this.c.writeInt(lastGoodStreamId);
                this.c.writeInt(errorCode.httpCode);
                if (debugData.length > 0) {
                    this.c.write(debugData);
                }
                this.c.flush();
            } else {
                throw i.j("errorCode.httpCode == -1", new Object[0]);
            }
        }

        public synchronized void b(int streamId, long windowSizeIncrement) {
            if (this.y) {
                throw new IOException("closed");
            } else if (windowSizeIncrement == 0 || windowSizeIncrement > 2147483647L) {
                throw i.j("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(windowSizeIncrement));
            } else {
                c(streamId, 4, (byte) 8, (byte) 0);
                this.c.writeInt((int) windowSizeIncrement);
                this.c.flush();
            }
        }

        public synchronized void close() {
            this.y = true;
            this.c.close();
        }

        /* access modifiers changed from: package-private */
        public void c(int streamId, int length, byte type, byte flags) {
            if (i.a.isLoggable(Level.FINE)) {
                i.a.fine(b.b(false, streamId, length, type, flags));
            }
            int i = this.x;
            if (length > i) {
                throw i.j("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(i), Integer.valueOf(length));
            } else if ((Integer.MIN_VALUE & streamId) == 0) {
                i.n(this.c, length);
                this.c.writeByte(type & 255);
                this.c.writeByte(flags & 255);
                this.c.writeInt(Integer.MAX_VALUE & streamId);
            } else {
                throw i.j("reserved bit set: %s", Integer.valueOf(streamId));
            }
        }
    }

    /* access modifiers changed from: private */
    public static IllegalArgumentException j(String message, Object... args) {
        throw new IllegalArgumentException(String.format(message, args));
    }

    /* access modifiers changed from: private */
    public static IOException k(String message, Object... args) {
        throw new IOException(String.format(message, args));
    }

    /* compiled from: Http2 */
    public static final class a implements e0 {
        private final e c;
        int d;
        byte f;
        int q;
        int x;
        short y;

        public /* synthetic */ g cursor() {
            return d0.a(this);
        }

        public a(e source) {
            this.c = source;
        }

        public long read(okio.c sink, long byteCount) {
            while (true) {
                int i = this.x;
                if (i == 0) {
                    this.c.skip((long) this.y);
                    this.y = 0;
                    if ((this.f & 4) != 0) {
                        return -1;
                    }
                    a();
                } else {
                    long read = this.c.read(sink, Math.min(byteCount, (long) i));
                    if (read == -1) {
                        return -1;
                    }
                    this.x = (int) (((long) this.x) - read);
                    return read;
                }
            }
        }

        public f0 timeout() {
            return this.c.timeout();
        }

        public void close() {
        }

        private void a() {
            int previousStreamId = this.q;
            int f2 = i.m(this.c);
            this.x = f2;
            this.d = f2;
            byte type = (byte) (this.c.readByte() & 255);
            this.f = (byte) (this.c.readByte() & 255);
            if (i.a.isLoggable(Level.FINE)) {
                i.a.fine(b.b(true, this.q, this.d, type, this.f));
            }
            int readInt = this.c.readInt() & Integer.MAX_VALUE;
            this.q = readInt;
            if (type != 9) {
                throw i.k("%s != TYPE_CONTINUATION", Byte.valueOf(type));
            } else if (readInt != previousStreamId) {
                throw i.k("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }
    }

    /* access modifiers changed from: private */
    public static int l(int length, byte flags, short padding) {
        if ((flags & 8) != 0) {
            length--;
        }
        if (padding <= length) {
            return (short) (length - padding);
        }
        throw k("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(padding), Integer.valueOf(length));
    }

    /* compiled from: Http2 */
    public static final class b {
        private static final String[] a = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
        private static final String[] b = new String[64];
        private static final String[] c = new String[256];

        b() {
        }

        static String b(boolean inbound, int streamId, int length, byte type, byte flags) {
            String[] strArr = a;
            String formattedType = type < strArr.length ? strArr[type] : String.format("0x%02x", new Object[]{Byte.valueOf(type)});
            String formattedFlags = a(type, flags);
            Object[] objArr = new Object[5];
            objArr[0] = inbound ? "<<" : ">>";
            objArr[1] = Integer.valueOf(streamId);
            objArr[2] = Integer.valueOf(length);
            objArr[3] = formattedType;
            objArr[4] = formattedFlags;
            return String.format("%s 0x%08x %5d %-13s %s", objArr);
        }

        static String a(byte type, byte flags) {
            String result;
            if (flags == 0) {
                return "";
            }
            switch (type) {
                case 2:
                case 3:
                case 7:
                case 8:
                    return c[flags];
                case 4:
                case 6:
                    return flags == 1 ? "ACK" : c[flags];
                default:
                    String[] strArr = b;
                    if (flags < strArr.length) {
                        result = strArr[flags];
                    } else {
                        result = c[flags];
                    }
                    if (type == 5 && (flags & 4) != 0) {
                        return result.replace("HEADERS", "PUSH_PROMISE");
                    }
                    if (type != 0 || (flags & 32) == 0) {
                        return result;
                    }
                    return result.replace("PRIORITY", "COMPRESSED");
            }
        }

        static {
            int i = 0;
            while (true) {
                String[] strArr = c;
                if (i >= strArr.length) {
                    break;
                }
                strArr[i] = String.format("%8s", new Object[]{Integer.toBinaryString(i)}).replace(' ', '0');
                i++;
            }
            String[] strArr2 = b;
            strArr2[0] = "";
            strArr2[1] = "END_STREAM";
            int[] prefixFlags = {1};
            strArr2[8] = "PADDED";
            for (int prefixFlag : prefixFlags) {
                String[] strArr3 = b;
                strArr3[prefixFlag | 8] = strArr3[prefixFlag] + "|PADDED";
            }
            String[] strArr4 = b;
            strArr4[4] = "END_HEADERS";
            strArr4[32] = "PRIORITY";
            strArr4[36] = "END_HEADERS|PRIORITY";
            for (int frameFlag : new int[]{4, 32, 36}) {
                for (int prefixFlag2 : prefixFlags) {
                    String[] strArr5 = b;
                    strArr5[prefixFlag2 | frameFlag] = strArr5[prefixFlag2] + '|' + strArr5[frameFlag];
                    strArr5[prefixFlag2 | frameFlag | 8] = strArr5[prefixFlag2] + '|' + strArr5[frameFlag] + "|PADDED";
                }
            }
            int i2 = 0;
            while (true) {
                String[] strArr6 = b;
                if (i2 < strArr6.length) {
                    if (strArr6[i2] == null) {
                        strArr6[i2] = c[i2];
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static int m(e source) {
        return ((source.readByte() & 255) << MappingData.PATH) | ((source.readByte() & 255) << 8) | (source.readByte() & 255);
    }

    /* access modifiers changed from: private */
    public static void n(okio.d sink, int i) {
        sink.writeByte((i >>> 16) & 255);
        sink.writeByte((i >>> 8) & 255);
        sink.writeByte(i & 255);
    }
}
