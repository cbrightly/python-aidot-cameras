package org.apache.http.client.methods;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.http.client.utils.c;
import org.apache.http.client.utils.e;
import org.apache.http.entity.f;
import org.apache.http.j;
import org.apache.http.k;
import org.apache.http.message.r;
import org.apache.http.o;
import org.apache.http.u;
import org.apache.http.v;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: RequestBuilder */
public class q {
    private String a;
    private Charset b;
    private v c;
    private URI d;
    private r e;
    private j f;
    private List<u> g;
    private org.apache.http.client.config.a h;

    q(String method) {
        this.b = org.apache.http.b.a;
        this.a = method;
    }

    q() {
        this((String) null);
    }

    public static q b(o request) {
        org.apache.http.util.a.i(request, "HTTP request");
        return new q().c(request);
    }

    private q c(o request) {
        if (request == null) {
            return this;
        }
        this.a = request.r().getMethod();
        this.c = request.r().getProtocolVersion();
        if (this.e == null) {
            this.e = new r();
        }
        this.e.clear();
        this.e.setHeaders(request.v());
        this.g = null;
        this.f = null;
        if (request instanceof k) {
            j originalEntity = ((k) request).a();
            f contentType = f.get(originalEntity);
            if (contentType == null || !contentType.getMimeType().equals(f.APPLICATION_FORM_URLENCODED.getMimeType())) {
                this.f = originalEntity;
            } else {
                try {
                    List<u> i = e.i(originalEntity);
                    if (!i.isEmpty()) {
                        this.g = i;
                    }
                } catch (IOException e2) {
                }
            }
        }
        if (request instanceof p) {
            this.d = ((p) request).t();
        } else {
            this.d = URI.create(request.r().getUri());
        }
        if (request instanceof d) {
            this.h = ((d) request).getConfig();
        } else {
            this.h = null;
        }
        return this;
    }

    public q d(URI uri) {
        this.d = uri;
        return this;
    }

    public p a() {
        b request;
        URI uriNotNull = this.d;
        if (uriNotNull == null) {
            uriNotNull = URI.create("/");
        }
        j entityCopy = this.f;
        List<u> list = this.g;
        if (list != null && !list.isEmpty()) {
            if (entityCopy != null || (!Constants.POST.equalsIgnoreCase(this.a) && !"PUT".equalsIgnoreCase(this.a))) {
                try {
                    uriNotNull = new c(uriNotNull).o(this.b).a(this.g).b();
                } catch (URISyntaxException e2) {
                }
            } else {
                List<u> list2 = this.g;
                Charset charset = this.b;
                if (charset == null) {
                    charset = org.apache.http.protocol.e.a;
                }
                entityCopy = new org.apache.http.client.entity.e(list2, charset);
            }
        }
        if (entityCopy == null) {
            request = new b(this.a);
        } else {
            a request2 = new a(this.a);
            request2.l(entityCopy);
            a aVar = request2;
            request = request2;
        }
        request.h(this.c);
        request.k(uriNotNull);
        r rVar = this.e;
        if (rVar != null) {
            request.u0(rVar.getAllHeaders());
        }
        request.f(this.h);
        return request;
    }

    /* compiled from: RequestBuilder */
    public static class b extends m {
        private final String p0;

        b(String method) {
            this.p0 = method;
        }

        public String getMethod() {
            return this.p0;
        }
    }

    /* compiled from: RequestBuilder */
    public static class a extends f {
        private final String a1;

        a(String method) {
            this.a1 = method;
        }

        public String getMethod() {
            return this.a1;
        }
    }
}
