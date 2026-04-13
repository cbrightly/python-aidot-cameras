package com.yanzhenjie.andserver.http;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.http.cookie.c;
import com.yanzhenjie.andserver.util.MultiValueMap;
import com.yanzhenjie.andserver.util.d;
import com.yanzhenjie.andserver.util.g;
import com.yanzhenjie.andserver.util.h;
import com.yanzhenjie.andserver.util.i;
import com.yanzhenjie.andserver.util.j;
import com.yanzhenjie.andserver.util.l;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;
import org.apache.httpcore.e;
import org.apache.httpcore.m;
import org.apache.httpcore.x;
import org.glassfish.grizzly.http.GZipContentEncoding;

/* compiled from: StandardRequest */
public class k implements c {
    private static final com.yanzhenjie.andserver.http.cookie.b a = new c();
    private m b;
    private a c;
    private com.yanzhenjie.andserver.c d;
    private x e;
    private com.yanzhenjie.andserver.http.session.c f;
    private m g;
    private boolean h;
    private j<String, String> i;
    private boolean j;
    private List<h> k;
    private boolean l;
    private j<String, String> m;
    private boolean n;

    public k(m request, a context, com.yanzhenjie.andserver.c handler, com.yanzhenjie.andserver.http.session.c sessionManager) {
        this.b = request;
        this.c = context;
        this.d = handler;
        this.e = request.r();
        this.f = sessionManager;
    }

    @NonNull
    public b getMethod() {
        return b.reverse(this.e.getMethod());
    }

    @NonNull
    public String t() {
        j();
        return this.g.toString();
    }

    private void j() {
        if (!this.h) {
            String requestLine = this.e.getUri();
            if (TextUtils.isEmpty(requestLine)) {
                requestLine = "/";
            }
            this.g = m.d("scheme://host:ip" + requestLine).g();
            this.h = true;
        }
    }

    public void k(String path) {
        j();
        this.g = this.g.a().h(path).g();
    }

    @NonNull
    public String getPath() {
        j();
        return this.g.c();
    }

    @NonNull
    public List<String> e() {
        i();
        return new LinkedList(this.i.keySet());
    }

    @NonNull
    public List<String> d(@NonNull String name) {
        i();
        List<String> values = (List) this.i.get(name);
        return (values == null || values.isEmpty()) ? Collections.emptyList() : values;
    }

    private void i() {
        if (!this.j) {
            j();
            this.i = this.g.b();
            this.j = true;
        }
    }

    @NonNull
    public List<String> x() {
        e[] headers = this.b.v();
        if (headers == null || headers.length == 0) {
            return Collections.emptyList();
        }
        List<String> nameList = new ArrayList<>();
        for (e header : headers) {
            nameList.add(header.getName());
        }
        return nameList;
    }

    @Nullable
    public String getHeader(@NonNull String name) {
        e header = this.b.u(name);
        if (header == null) {
            return null;
        }
        return header.getValue();
    }

    @NonNull
    public List<String> c(@NonNull String name) {
        e[] headers = this.b.c(name);
        if (headers == null || headers.length == 0) {
            return Collections.emptyList();
        }
        List<String> valueList = new ArrayList<>();
        for (e header : headers) {
            valueList.add(header.getValue());
        }
        return valueList;
    }

    public long A(@NonNull String name) {
        e header = this.b.u(name);
        if (header == null) {
            return -1;
        }
        String value = header.getValue();
        long date = d.b(value);
        if (date != -1) {
            return date;
        }
        throw new IllegalStateException(String.format("The %s cannot be converted to date.", new Object[]{value}));
    }

    @NonNull
    public List<h> v() {
        f();
        return this.k;
    }

    private void f() {
        if (!this.l) {
            this.k = new ArrayList();
            e[] headers = this.b.c("Accept");
            if (headers != null && headers.length > 0) {
                for (e header : headers) {
                    this.k.addAll(h.parseMediaTypes(header.getValue()));
                }
            }
            if (this.k.isEmpty()) {
                this.k.add(h.ALL);
            }
            this.l = true;
        }
    }

    @Nullable
    public h getContentType() {
        String contentType = getHeader("Content-Type");
        if (TextUtils.isEmpty(contentType)) {
            return null;
        }
        return h.valueOf(contentType);
    }

    @NonNull
    public List<String> w() {
        g();
        List<String> paramNames = new LinkedList<>(this.m.keySet());
        List<String> names = e();
        if (!names.isEmpty()) {
            paramNames.addAll(names);
        }
        return paramNames;
    }

    @NonNull
    public List<String> y(@NonNull String name) {
        g();
        List<String> values = (List) this.m.get(name);
        if (values == null || values.isEmpty()) {
            return d(name);
        }
        return values;
    }

    private void g() {
        if (!this.n) {
            if (!getMethod().allowBody()) {
                this.m = new g();
                return;
            }
            if (h.APPLICATION_FORM_URLENCODED.includes(getContentType())) {
                try {
                    f body = z();
                    this.m = h(body == null ? "" : body.b());
                } catch (Exception e2) {
                }
            }
            if (this.m == null) {
                this.m = new g();
            }
            this.n = true;
        }
    }

    @Nullable
    public f z() {
        org.apache.httpcore.j entity;
        if (getMethod().allowBody()) {
            m mVar = this.b;
            if (!(mVar instanceof org.apache.httpcore.k) || (entity = ((org.apache.httpcore.k) mVar).a()) == null) {
                return null;
            }
            return new b(entity);
        }
        throw new UnsupportedOperationException("This method does not allow body.");
    }

    @Nullable
    public g u(@NonNull String path) {
        return this.d.i(this, path);
    }

    @Nullable
    public Object getAttribute(@NonNull String id) {
        return this.c.getAttribute(id);
    }

    public void setAttribute(@NonNull String id, @Nullable Object obj) {
        this.c.setAttribute(id, obj);
    }

    @NonNull
    private static j<String, String> h(@NonNull String input) {
        MultiValueMap<String, String> parameters = new g<>();
        StringTokenizer tokenizer = new StringTokenizer(input, "&");
        while (tokenizer.hasMoreElements()) {
            String element = tokenizer.nextToken();
            int end = element.indexOf("=");
            if (end > 0 && end < element.length() - 1) {
                parameters.add(element.substring(0, end), l.b(element.substring(end + 1), org.apache.commons.io.a.a("utf-8")));
            }
        }
        return parameters;
    }

    /* compiled from: StandardRequest */
    public static class b implements f {
        private org.apache.httpcore.j a;

        private b(org.apache.httpcore.j entity) {
            this.a = entity;
        }

        public String a() {
            e encoding = this.a.getContentType();
            return encoding == null ? "" : encoding.getValue();
        }

        public long length() {
            return this.a.getContentLength();
        }

        @Nullable
        public h d() {
            e header = this.a.getContentType();
            if (header == null) {
                return null;
            }
            return h.valueOf(header.getValue());
        }

        @NonNull
        public InputStream stream() {
            InputStream stream = this.a.getContent();
            if (a().toLowerCase().contains(GZipContentEncoding.NAME)) {
                return new GZIPInputStream(stream);
            }
            return stream;
        }

        @NonNull
        public String b() {
            i mimeType = d();
            Charset charset = mimeType == null ? null : mimeType.getCharset();
            if (charset == null) {
                return com.yanzhenjie.andserver.util.e.g(stream());
            }
            return com.yanzhenjie.andserver.util.e.h(stream(), charset);
        }
    }
}
