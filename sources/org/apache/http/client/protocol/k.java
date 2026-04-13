package org.apache.http.client.protocol;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.io.InputStream;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import org.apache.http.HttpException;
import org.apache.http.client.entity.c;
import org.apache.http.config.e;
import org.apache.http.d;
import org.apache.http.j;
import org.apache.http.protocol.f;
import org.apache.http.q;
import org.apache.http.s;
import org.glassfish.grizzly.http.GZipContentEncoding;

/* compiled from: ResponseContentEncoding */
public class k implements s {
    private static final c c = new a();
    private static final c d = new b();
    private final org.apache.http.config.b<c> f;
    private final boolean q;

    /* compiled from: ResponseContentEncoding */
    public static final class a implements c {
        a() {
        }

        public InputStream a(InputStream instream) {
            return new GZIPInputStream(instream);
        }
    }

    /* compiled from: ResponseContentEncoding */
    public static final class b implements c {
        b() {
        }

        public InputStream a(InputStream instream) {
            return new org.apache.http.client.entity.b(instream);
        }
    }

    public k(org.apache.http.config.b<c> decoderRegistry, boolean ignoreUnknown) {
        org.apache.http.config.b<c> bVar;
        if (decoderRegistry != null) {
            bVar = decoderRegistry;
        } else {
            e b2 = e.b();
            c cVar = c;
            bVar = b2.c(GZipContentEncoding.NAME, cVar).c("x-gzip", cVar).c("deflate", d).a();
        }
        this.f = bVar;
        this.q = ignoreUnknown;
    }

    public k(org.apache.http.config.b<c> decoderRegistry) {
        this(decoderRegistry, true);
    }

    public k() {
        this((org.apache.http.config.b<c>) null);
    }

    public void a(q response, f context) {
        d ceheader;
        q qVar = response;
        j entity = response.a();
        if (a.g(context).s().p() && entity != null && entity.getContentLength() != 0 && (ceheader = entity.getContentEncoding()) != null) {
            for (org.apache.http.e codec : ceheader.getElements()) {
                String codecname = codec.getName().toLowerCase(Locale.ROOT);
                c decoderFactory = this.f.lookup(codecname);
                if (decoderFactory != null) {
                    qVar.l(new org.apache.http.client.entity.a(response.a(), decoderFactory));
                    qVar.s("Content-Length");
                    qVar.s(HttpHeaders.HEAD_KEY_CONTENT_ENCODING);
                    qVar.s("Content-MD5");
                } else if (!"identity".equals(codecname) && !this.q) {
                    throw new HttpException("Unsupported Content-Encoding: " + codec.getName());
                }
            }
        }
    }
}
