package retrofit2;

import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.s;
import okhttp3.u;
import okhttp3.v;
import okhttp3.x;
import okhttp3.y;
import okio.c;
import okio.d;

/* compiled from: RequestBuilder */
public final class q {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final Pattern b = Pattern.compile("(.*/)?(\\.|%2e|%2E){1,2}(/.*)?");
    private final String c;
    private final v d;
    @Nullable
    private String e;
    @Nullable
    private v.a f;
    private final b0.a g = new b0.a();
    private final u.a h;
    @Nullable
    private x i;
    private final boolean j;
    @Nullable
    private y.a k;
    @Nullable
    private s.a l;
    @Nullable
    private c0 m;

    q(String method, v baseUrl, @Nullable String relativeUrl, @Nullable u headers, @Nullable x contentType, boolean hasBody, boolean isFormEncoded, boolean isMultipart) {
        this.c = method;
        this.d = baseUrl;
        this.e = relativeUrl;
        this.i = contentType;
        this.j = hasBody;
        if (headers != null) {
            this.h = headers.f();
        } else {
            this.h = new u.a();
        }
        if (isFormEncoded) {
            this.l = new s.a();
        } else if (isMultipart) {
            y.a aVar = new y.a();
            this.k = aVar;
            aVar.f(y.e);
        }
    }

    /* access modifiers changed from: package-private */
    public void m(Object relativeUrl) {
        this.e = relativeUrl.toString();
    }

    /* access modifiers changed from: package-private */
    public void b(String name, String value) {
        if ("Content-Type".equalsIgnoreCase(name)) {
            try {
                this.i = x.f(value);
            } catch (IllegalArgumentException e2) {
                throw new IllegalArgumentException("Malformed content type: " + value, e2);
            }
        } else {
            this.h.a(name, value);
        }
    }

    /* access modifiers changed from: package-private */
    public void c(u headers) {
        this.h.b(headers);
    }

    /* access modifiers changed from: package-private */
    public void f(String name, String value, boolean encoded) {
        if (this.e != null) {
            String replacement = i(value, encoded);
            String str = this.e;
            String newRelativeUrl = str.replace("{" + name + "}", replacement);
            if (!b.matcher(newRelativeUrl).matches()) {
                this.e = newRelativeUrl;
                return;
            }
            throw new IllegalArgumentException("@Path parameters shouldn't perform path traversal ('.' or '..'): " + value);
        }
        throw new AssertionError();
    }

    private static String i(String input, boolean alreadyEncoded) {
        int i2 = 0;
        int limit = input.length();
        while (i2 < limit) {
            int codePoint = input.codePointAt(i2);
            if (codePoint < 32 || codePoint >= 127 || " \"<>^`{}|\\?#".indexOf(codePoint) != -1 || (!alreadyEncoded && (codePoint == 47 || codePoint == 37))) {
                c out = new c();
                out.writeUtf8(input, 0, i2);
                j(out, input, i2, limit, alreadyEncoded);
                return out.a1();
            }
            i2 += Character.charCount(codePoint);
        }
        return input;
    }

    private static void j(c out, String input, int pos, int limit, boolean alreadyEncoded) {
        c utf8Buffer = null;
        int i2 = pos;
        while (i2 < limit) {
            int codePoint = input.codePointAt(i2);
            if (!alreadyEncoded || !(codePoint == 9 || codePoint == 10 || codePoint == 12 || codePoint == 13)) {
                if (codePoint < 32 || codePoint >= 127 || " \"<>^`{}|\\?#".indexOf(codePoint) != -1 || (!alreadyEncoded && (codePoint == 47 || codePoint == 37))) {
                    if (utf8Buffer == null) {
                        utf8Buffer = new c();
                    }
                    utf8Buffer.writeUtf8CodePoint(codePoint);
                    while (!utf8Buffer.r0()) {
                        int b2 = utf8Buffer.readByte() & 255;
                        out.writeByte(37);
                        char[] cArr = a;
                        out.writeByte(cArr[(b2 >> 4) & 15]);
                        out.writeByte(cArr[b2 & 15]);
                    }
                } else {
                    out.writeUtf8CodePoint(codePoint);
                }
            }
            i2 += Character.charCount(codePoint);
        }
    }

    /* access modifiers changed from: package-private */
    public void g(String name, @Nullable String value, boolean encoded) {
        String str = this.e;
        if (str != null) {
            v.a m2 = this.d.m(str);
            this.f = m2;
            if (m2 != null) {
                this.e = null;
            } else {
                throw new IllegalArgumentException("Malformed URL. Base: " + this.d + ", Relative: " + this.e);
            }
        }
        if (encoded) {
            this.f.a(name, value);
        } else {
            this.f.b(name, value);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String name, String value, boolean encoded) {
        if (encoded) {
            this.l.b(name, value);
        } else {
            this.l.a(name, value);
        }
    }

    /* access modifiers changed from: package-private */
    public void d(u headers, c0 body) {
        this.k.c(headers, body);
    }

    /* access modifiers changed from: package-private */
    public void e(y.c part) {
        this.k.d(part);
    }

    /* access modifiers changed from: package-private */
    public void l(c0 body) {
        this.m = body;
    }

    /* access modifiers changed from: package-private */
    public <T> void h(Class<T> cls, @Nullable T value) {
        this.g.n(cls, value);
    }

    /* access modifiers changed from: package-private */
    public b0.a k() {
        v url;
        v.a urlBuilder = this.f;
        if (urlBuilder != null) {
            url = urlBuilder.c();
        } else {
            url = this.d.s(this.e);
            if (url == null) {
                throw new IllegalArgumentException("Malformed URL. Base: " + this.d + ", Relative: " + this.e);
            }
        }
        c0 body = this.m;
        if (body == null) {
            s.a aVar = this.l;
            if (aVar != null) {
                body = aVar.c();
            } else {
                y.a aVar2 = this.k;
                if (aVar2 != null) {
                    body = aVar2.e();
                } else if (this.j) {
                    body = c0.create((x) null, new byte[0]);
                }
            }
        }
        x contentType = this.i;
        if (contentType != null) {
            if (body != null) {
                body = new a(body, contentType);
            } else {
                this.h.a("Content-Type", contentType.toString());
            }
        }
        return this.g.q(url).h(this.h.f()).i(this.c, body);
    }

    /* compiled from: RequestBuilder */
    public static class a extends c0 {
        private final c0 a;
        private final x b;

        a(c0 delegate, x contentType) {
            this.a = delegate;
            this.b = contentType;
        }

        public x contentType() {
            return this.b;
        }

        public long contentLength() {
            return this.a.contentLength();
        }

        public void writeTo(d sink) {
            this.a.writeTo(sink);
        }
    }
}
