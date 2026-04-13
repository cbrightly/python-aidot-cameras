package com.squareup.okhttp.internal.framed;

import androidx.core.view.ViewCompat;
import com.squareup.okhttp.internal.framed.b;
import com.squareup.okhttp.internal.j;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.util.List;
import java.util.zip.Deflater;
import okio.b0;
import okio.c;
import okio.d;
import okio.e;
import okio.f;
import okio.h;
import okio.p;

/* compiled from: Spdy3 */
public final class o implements p {
    static final byte[] a;

    static {
        try {
            a = "\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004head\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006delete\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000\u000eaccept-charset\u0000\u0000\u0000\u000faccept-encoding\u0000\u0000\u0000\u000faccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-control\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000\u0000\u000econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000\u000bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expect\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocation\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000bretry-after\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trailer\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-cookie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authoritative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service UnavailableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Thu, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml,application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.".getBytes(j.c.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }

    public b a(e source, boolean client) {
        return new a(source, client);
    }

    public c b(d sink, boolean client) {
        return new b(sink, client);
    }

    /* compiled from: Spdy3 */
    public static final class a implements b {
        private final e c;
        private final boolean d;
        private final k f;

        a(e source, boolean client) {
            this.c = source;
            this.f = new k(source);
            this.d = client;
        }

        public void c0() {
        }

        public boolean S(b.a handler) {
            boolean inFinished = false;
            try {
                int w1 = this.c.readInt();
                int w2 = this.c.readInt();
                int flags = (-16777216 & w2) >>> 24;
                int length = 16777215 & w2;
                if ((Integer.MIN_VALUE & w1) != 0) {
                    int version = (2147418112 & w1) >>> 16;
                    int type = 65535 & w1;
                    if (version == 3) {
                        switch (type) {
                            case 1:
                                n(handler, flags, length);
                                return true;
                            case 2:
                                m(handler, flags, length);
                                return true;
                            case 3:
                                j(handler, flags, length);
                                return true;
                            case 4:
                                l(handler, flags, length);
                                return true;
                            case 6:
                                i(handler, flags, length);
                                return true;
                            case 7:
                                c(handler, flags, length);
                                return true;
                            case 8:
                                g(handler, flags, length);
                                return true;
                            case 9:
                                o(handler, flags, length);
                                return true;
                            default:
                                this.c.skip((long) length);
                                return true;
                        }
                    } else {
                        throw new ProtocolException("version != 3: " + version);
                    }
                } else {
                    int streamId = Integer.MAX_VALUE & w1;
                    if ((flags & 1) != 0) {
                        inFinished = true;
                    }
                    handler.m(inFinished, streamId, this.c, length);
                    return true;
                }
            } catch (IOException e) {
                return false;
            }
        }

        private void n(b.a handler, int flags, int length) {
            int streamId = this.c.readInt() & Integer.MAX_VALUE;
            int associatedStreamId = Integer.MAX_VALUE & this.c.readInt();
            this.c.readShort();
            List<f> f2 = this.f.f(length - 10);
            boolean outFinished = false;
            boolean inFinished = (flags & 1) != 0;
            if ((flags & 2) != 0) {
                outFinished = true;
            }
            handler.p(outFinished, inFinished, streamId, associatedStreamId, f2, g.SPDY_SYN_STREAM);
        }

        private void m(b.a handler, int flags, int length) {
            handler.p(false, (flags & 1) != 0, Integer.MAX_VALUE & this.c.readInt(), -1, this.f.f(length - 4), g.SPDY_REPLY);
        }

        private void j(b.a handler, int flags, int length) {
            if (length == 8) {
                int streamId = this.c.readInt() & Integer.MAX_VALUE;
                int errorCodeInt = this.c.readInt();
                a errorCode = a.fromSpdy3Rst(errorCodeInt);
                if (errorCode != null) {
                    handler.k(streamId, errorCode);
                } else {
                    throw a("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(errorCodeInt));
                }
            } else {
                throw a("TYPE_RST_STREAM length: %d != 8", Integer.valueOf(length));
            }
        }

        private void g(b.a handler, int flags, int length) {
            List<f> f2 = this.f.f(length - 4);
            handler.p(false, false, Integer.MAX_VALUE & this.c.readInt(), -1, f2, g.SPDY_HEADERS);
        }

        private void o(b.a handler, int flags, int length) {
            if (length == 8) {
                int streamId = this.c.readInt() & Integer.MAX_VALUE;
                long increment = (long) (Integer.MAX_VALUE & this.c.readInt());
                if (increment != 0) {
                    handler.b(streamId, increment);
                } else {
                    throw a("windowSizeIncrement was 0", Long.valueOf(increment));
                }
            } else {
                throw a("TYPE_WINDOW_UPDATE length: %d != 8", Integer.valueOf(length));
            }
        }

        private void i(b.a handler, int flags, int length) {
            boolean ack = true;
            if (length == 4) {
                int id = this.c.readInt();
                if (this.d != ((id & 1) == 1)) {
                    ack = false;
                }
                handler.h(ack, id, 0);
                return;
            }
            throw a("TYPE_PING length: %d != 4", Integer.valueOf(length));
        }

        private void c(b.a handler, int flags, int length) {
            if (length == 8) {
                int lastGoodStreamId = this.c.readInt() & Integer.MAX_VALUE;
                int errorCodeInt = this.c.readInt();
                a errorCode = a.fromSpdyGoAway(errorCodeInt);
                if (errorCode != null) {
                    handler.q(lastGoodStreamId, errorCode, f.EMPTY);
                } else {
                    throw a("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(errorCodeInt));
                }
            } else {
                throw a("TYPE_GOAWAY length: %d != 8", Integer.valueOf(length));
            }
        }

        private void l(b.a handler, int flags, int length) {
            int numberOfEntries = this.c.readInt();
            boolean clearPrevious = false;
            if (length == (numberOfEntries * 8) + 4) {
                n settings = new n();
                for (int i = 0; i < numberOfEntries; i++) {
                    int w1 = this.c.readInt();
                    settings.l(16777215 & w1, (-16777216 & w1) >>> 24, this.c.readInt());
                }
                if ((flags & 1) != 0) {
                    clearPrevious = true;
                }
                handler.o(clearPrevious, settings);
                return;
            }
            throw a("TYPE_SETTINGS length: %d != 4 + 8 * %d", Integer.valueOf(length), Integer.valueOf(numberOfEntries));
        }

        private static IOException a(String message, Object... args) {
            throw new IOException(String.format(message, args));
        }

        public void close() {
            this.f.c();
        }
    }

    /* compiled from: Spdy3 */
    public static final class b implements c {
        private final d c;
        private final c d;
        private final d f;
        private final boolean q;
        private boolean x;

        b(d sink, boolean client) {
            this.c = sink;
            this.q = client;
            Deflater deflater = new Deflater();
            deflater.setDictionary(o.a);
            c cVar = new c();
            this.d = cVar;
            this.f = p.c(new h((b0) cVar, deflater));
        }

        public void B0(n peerSettings) {
        }

        public void f(int streamId, int promisedStreamId, List<f> list) {
        }

        public synchronized void G() {
        }

        public synchronized void flush() {
            if (!this.x) {
                this.c.flush();
            } else {
                throw new IOException("closed");
            }
        }

        public synchronized void T0(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<f> headerBlock) {
            if (!this.x) {
                c(headerBlock);
                int length = (int) (this.d.e1() + 10);
                int i = inFinished ? 2 : 0;
                this.c.writeInt(-2147287040 | (65535 & 1));
                this.c.writeInt((((int) ((i | outFinished) & 255)) << true) | (16777215 & length));
                this.c.writeInt(streamId & Integer.MAX_VALUE);
                this.c.writeInt(Integer.MAX_VALUE & associatedStreamId);
                this.c.writeShort(((0 & 7) << 13) | ((0 & 31) << 8) | (0 & 255));
                this.c.writeAll(this.d);
                this.c.flush();
            } else {
                throw new IOException("closed");
            }
        }

        public synchronized void k(int streamId, a errorCode) {
            if (this.x) {
                throw new IOException("closed");
            } else if (errorCode.spdyRstCode != -1) {
                this.c.writeInt(-2147287040 | (65535 & 3));
                this.c.writeInt(((0 & 255) << 24) | (16777215 & 8));
                this.c.writeInt(Integer.MAX_VALUE & streamId);
                this.c.writeInt(errorCode.spdyRstCode);
                this.c.flush();
            } else {
                throw new IllegalArgumentException();
            }
        }

        public int j0() {
            return 16383;
        }

        public synchronized void K(boolean outFinished, int streamId, c source, int byteCount) {
            a(streamId, outFinished ? 1 : 0, source, byteCount);
        }

        /* access modifiers changed from: package-private */
        public void a(int streamId, int flags, c buffer, int byteCount) {
            if (this.x) {
                throw new IOException("closed");
            } else if (((long) byteCount) <= 16777215) {
                this.c.writeInt(Integer.MAX_VALUE & streamId);
                this.c.writeInt(((flags & 255) << 24) | (16777215 & byteCount));
                if (byteCount > 0) {
                    this.c.write(buffer, (long) byteCount);
                }
            } else {
                throw new IllegalArgumentException("FRAME_TOO_LARGE max size is 16Mib: " + byteCount);
            }
        }

        private void c(List<f> headerBlock) {
            this.f.writeInt(headerBlock.size());
            int size = headerBlock.size();
            for (int i = 0; i < size; i++) {
                f name = headerBlock.get(i).h;
                this.f.writeInt(name.size());
                this.f.write(name);
                f value = headerBlock.get(i).i;
                this.f.writeInt(value.size());
                this.f.write(value);
            }
            this.f.flush();
        }

        public synchronized void W0(n settings) {
            if (!this.x) {
                int size = settings.m();
                this.c.writeInt(-2147287040 | (65535 & 4));
                this.c.writeInt(((0 & 255) << 24) | (((size * 8) + 4) & ViewCompat.MEASURED_SIZE_MASK));
                this.c.writeInt(size);
                for (int i = 0; i <= 10; i++) {
                    if (settings.i(i)) {
                        this.c.writeInt(((settings.b(i) & 255) << 24) | (i & ViewCompat.MEASURED_SIZE_MASK));
                        this.c.writeInt(settings.c(i));
                    }
                }
                this.c.flush();
            } else {
                throw new IOException("closed");
            }
        }

        public synchronized void h(boolean reply, int payload1, int payload2) {
            if (!this.x) {
                boolean payloadIsReply = false;
                if (this.q != ((payload1 & 1) == 1)) {
                    payloadIsReply = true;
                }
                if (reply == payloadIsReply) {
                    this.c.writeInt(-2147287040 | (65535 & 6));
                    this.c.writeInt(((0 & 255) << 24) | (16777215 & 4));
                    this.c.writeInt(payload1);
                    this.c.flush();
                } else {
                    throw new IllegalArgumentException("payload != reply");
                }
            } else {
                throw new IOException("closed");
            }
        }

        public synchronized void C(int lastGoodStreamId, a errorCode, byte[] ignored) {
            if (this.x) {
                throw new IOException("closed");
            } else if (errorCode.spdyGoAwayCode != -1) {
                this.c.writeInt(-2147287040 | (65535 & 7));
                this.c.writeInt(((0 & 255) << 24) | (16777215 & 8));
                this.c.writeInt(lastGoodStreamId);
                this.c.writeInt(errorCode.spdyGoAwayCode);
                this.c.flush();
            } else {
                throw new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
            }
        }

        public synchronized void b(int streamId, long increment) {
            if (this.x) {
                throw new IOException("closed");
            } else if (increment == 0 || increment > 2147483647L) {
                throw new IllegalArgumentException("windowSizeIncrement must be between 1 and 0x7fffffff: " + increment);
            } else {
                this.c.writeInt(-2147287040 | (65535 & 9));
                this.c.writeInt(((0 & 255) << 24) | (16777215 & 8));
                this.c.writeInt(streamId);
                this.c.writeInt((int) increment);
                this.c.flush();
            }
        }

        public synchronized void close() {
            this.x = true;
            j.b(this.c, this.f);
        }
    }
}
