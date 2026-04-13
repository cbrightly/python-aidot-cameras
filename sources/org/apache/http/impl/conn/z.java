package org.apache.http.impl.conn;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.conn.n;
import org.apache.http.conn.r;
import org.apache.http.conn.routing.b;
import org.apache.http.entity.e;
import org.apache.http.impl.entity.c;
import org.apache.http.impl.io.j;
import org.apache.http.io.d;
import org.apache.http.io.f;
import org.apache.http.o;
import org.apache.http.q;

/* compiled from: ManagedHttpClientConnectionFactory */
public class z implements n<b, r> {
    private static final AtomicLong a = new AtomicLong();
    public static final z b = new z();
    private final a c;
    private final a d;
    private final a e;
    private final f<o> f;
    private final d<q> g;
    private final e h;
    private final e i;

    public z(f<o> requestWriterFactory, d<q> responseParserFactory, e incomingContentStrategy, e outgoingContentStrategy) {
        this.c = h.n(n.class);
        this.d = h.o("org.apache.http.headers");
        this.e = h.o("org.apache.http.wire");
        this.f = requestWriterFactory != null ? requestWriterFactory : j.a;
        this.g = responseParserFactory != null ? responseParserFactory : l.a;
        this.h = incomingContentStrategy != null ? incomingContentStrategy : c.a;
        this.i = outgoingContentStrategy != null ? outgoingContentStrategy : org.apache.http.impl.entity.d.a;
    }

    public z(f<o> requestWriterFactory, d<q> responseParserFactory) {
        this(requestWriterFactory, responseParserFactory, (e) null, (e) null);
    }

    public z() {
        this((f<o>) null, (d<q>) null);
    }

    /* renamed from: b */
    public r a(b route, org.apache.http.config.a config) {
        org.apache.http.config.a cconfig = config != null ? config : org.apache.http.config.a.c;
        CharsetDecoder chardecoder = null;
        CharsetEncoder charencoder = null;
        Charset charset = cconfig.c();
        CodingErrorAction malformedInputAction = cconfig.e() != null ? cconfig.e() : CodingErrorAction.REPORT;
        CodingErrorAction unmappableInputAction = cconfig.h() != null ? cconfig.h() : CodingErrorAction.REPORT;
        if (charset != null) {
            chardecoder = charset.newDecoder();
            chardecoder.onMalformedInput(malformedInputAction);
            chardecoder.onUnmappableCharacter(unmappableInputAction);
            charencoder = charset.newEncoder();
            charencoder.onMalformedInput(malformedInputAction);
            charencoder.onUnmappableCharacter(unmappableInputAction);
        }
        return new u("http-outgoing-" + Long.toString(a.getAndIncrement()), this.c, this.d, this.e, cconfig.b(), cconfig.d(), chardecoder, charencoder, cconfig.f(), this.h, this.i, this.f, this.g);
    }
}
