package io.ktor.http;

import io.ktor.http.m;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.x;
import org.glassfish.grizzly.http.GZipContentEncoding;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.e;

/* compiled from: ContentTypes.kt */
public final class c extends m {
    /* access modifiers changed from: private */
    @NotNull
    public static final c d = new c(e.ANY_MARKER, e.ANY_MARKER, (List) null, 4, (DefaultConstructorMarker) null);
    public static final b e = new b((DefaultConstructorMarker) null);
    @NotNull
    private final String f;
    @NotNull
    private final String g;

    private c(String contentType, String contentSubtype, String existingContent, List<l> parameters) {
        super(existingContent, parameters);
        this.f = contentType;
        this.g = contentSubtype;
    }

    @NotNull
    public final String e() {
        return this.g;
    }

    @NotNull
    public final String f() {
        return this.f;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public c(@NotNull String contentType, @NotNull String contentSubtype, @NotNull List<l> parameters) {
        this(contentType, contentSubtype, contentType + '/' + contentSubtype, parameters);
        k.f(contentType, "contentType");
        k.f(contentSubtype, "contentSubtype");
        k.f(parameters, "parameters");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ c(String str, String str2, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? q.g() : list);
    }

    @NotNull
    public final c i(@NotNull String name, @NotNull String value) {
        k.f(name, "name");
        k.f(value, "value");
        if (g(name, value)) {
            return this;
        }
        return new c(this.f, this.g, a(), y.o0(b(), new l(name, value)));
    }

    private final boolean g(String name, String value) {
        l it;
        switch (b().size()) {
            case 0:
                return false;
            case 1:
                l it2 = b().get(0);
                if (!w.y(it2.c(), name, true) || !w.y(it2.d(), value, true)) {
                    return false;
                }
                return true;
            default:
                List<l> b2 = b();
                if ((b2 instanceof Collection) && b2.isEmpty()) {
                    return false;
                }
                for (l it3 : b2) {
                    if (!w.y(it3.c(), name, true) || !w.y(it3.d(), value, true)) {
                        it = null;
                        continue;
                    } else {
                        it = 1;
                        continue;
                    }
                    if (it != null) {
                        return true;
                    }
                }
                return false;
        }
    }

    @NotNull
    public final c j() {
        return new c(this.f, this.g, (List) null, 4, (DefaultConstructorMarker) null);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean h(@org.jetbrains.annotations.NotNull io.ktor.http.c r14) {
        /*
            r13 = this;
            java.lang.String r0 = "pattern"
            kotlin.jvm.internal.k.f(r14, r0)
            java.lang.String r0 = r14.f
            java.lang.String r1 = "*"
            boolean r0 = kotlin.jvm.internal.k.a(r0, r1)
            r2 = 1
            r0 = r0 ^ r2
            r3 = 0
            if (r0 == 0) goto L_0x001e
            java.lang.String r0 = r14.f
            java.lang.String r4 = r13.f
            boolean r0 = kotlin.text.w.y(r0, r4, r2)
            if (r0 != 0) goto L_0x001e
            return r3
        L_0x001e:
            java.lang.String r0 = r14.g
            boolean r0 = kotlin.jvm.internal.k.a(r0, r1)
            r0 = r0 ^ r2
            if (r0 == 0) goto L_0x0032
            java.lang.String r0 = r14.g
            java.lang.String r4 = r13.g
            boolean r0 = kotlin.text.w.y(r0, r4, r2)
            if (r0 != 0) goto L_0x0032
            return r3
        L_0x0032:
            java.util.List r0 = r14.b()
            java.util.Iterator r0 = r0.iterator()
        L_0x003a:
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto L_0x00c1
            java.lang.Object r4 = r0.next()
            io.ktor.http.l r4 = (io.ktor.http.l) r4
            java.lang.String r5 = r4.a()
            java.lang.String r4 = r4.b()
            int r6 = r5.hashCode()
            switch(r6) {
                case 42: goto L_0x0056;
                default: goto L_0x0055;
            }
        L_0x0055:
            goto L_0x009d
        L_0x0056:
            boolean r6 = r5.equals(r1)
            if (r6 == 0) goto L_0x009d
            int r6 = r4.hashCode()
            switch(r6) {
                case 42: goto L_0x0064;
                default: goto L_0x0063;
            }
        L_0x0063:
            goto L_0x006c
        L_0x0064:
            boolean r6 = r4.equals(r1)
            if (r6 == 0) goto L_0x006c
            r6 = r2
            goto L_0x00ba
        L_0x006c:
            java.util.List r6 = r13.b()
            r7 = 0
            boolean r8 = r6 instanceof java.util.Collection
            if (r8 == 0) goto L_0x007d
            boolean r8 = r6.isEmpty()
            if (r8 == 0) goto L_0x007d
            r6 = r3
            goto L_0x009c
        L_0x007d:
            java.util.Iterator r8 = r6.iterator()
        L_0x0081:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x009b
            java.lang.Object r9 = r8.next()
            r10 = r9
            io.ktor.http.l r10 = (io.ktor.http.l) r10
            r11 = 0
            java.lang.String r12 = r10.d()
            boolean r10 = kotlin.text.w.y(r12, r4, r2)
            if (r10 == 0) goto L_0x0081
            r6 = r2
            goto L_0x009c
        L_0x009b:
            r6 = r3
        L_0x009c:
            goto L_0x00ba
        L_0x009d:
            java.lang.String r6 = r13.c(r5)
            int r7 = r4.hashCode()
            switch(r7) {
                case 42: goto L_0x00a9;
                default: goto L_0x00a8;
            }
        L_0x00a8:
            goto L_0x00b5
        L_0x00a9:
            boolean r7 = r4.equals(r1)
            if (r7 == 0) goto L_0x00b5
            if (r6 == 0) goto L_0x00b3
            r6 = r2
            goto L_0x00ba
        L_0x00b3:
            r6 = r3
            goto L_0x00ba
        L_0x00b5:
            boolean r7 = kotlin.text.w.y(r6, r4, r2)
            r6 = r7
        L_0x00ba:
            if (r6 != 0) goto L_0x00bf
            return r3
        L_0x00bf:
            goto L_0x003a
        L_0x00c1:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.c.h(io.ktor.http.c):boolean");
    }

    public boolean equals(@Nullable Object other) {
        if (!(other instanceof c) || !w.y(this.f, ((c) other).f, true) || !w.y(this.g, ((c) other).g, true) || !k.a(b(), ((c) other).b())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.f;
        if (str != null) {
            String lowerCase = str.toLowerCase();
            k.b(lowerCase, "(this as java.lang.String).toLowerCase()");
            int result = lowerCase.hashCode();
            int i = result * 31;
            String str2 = this.g;
            if (str2 != null) {
                String lowerCase2 = str2.toLowerCase();
                k.b(lowerCase2, "(this as java.lang.String).toLowerCase()");
                return result + i + lowerCase2.hashCode() + (b().hashCode() * 31);
            }
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    /* compiled from: ContentTypes.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final c b(@NotNull String value) {
            k.f(value, "value");
            m.a aVar = m.a;
            k headerValue$iv = (k) y.q0(r.c(value));
            String parts = headerValue$iv.d();
            List parameters = headerValue$iv.b();
            int slash = x.e0(parts, '/', 0, false, 6, (Object) null);
            if (slash == -1) {
                if (parts == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                } else if (k.a(x.e1(parts).toString(), e.ANY_MARKER)) {
                    return c.e.a();
                } else {
                    throw new BadContentTypeFormatException(value);
                }
            } else if (parts != null) {
                String substring = parts.substring(0, slash);
                k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                if (substring != null) {
                    String type = x.e1(substring).toString();
                    boolean z = true;
                    if (!(type.length() == 0)) {
                        String substring2 = parts.substring(slash + 1);
                        k.b(substring2, "(this as java.lang.String).substring(startIndex)");
                        if (substring2 != null) {
                            String subtype = x.e1(substring2).toString();
                            if (subtype.length() != 0) {
                                z = false;
                            }
                            if (!z && !x.R(subtype, '/', false, 2, (Object) null)) {
                                return new c(type, subtype, parameters);
                            }
                            throw new BadContentTypeFormatException(value);
                        }
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                    }
                    throw new BadContentTypeFormatException(value);
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
        }

        @NotNull
        public final c a() {
            return c.d;
        }
    }

    /* compiled from: ContentTypes.kt */
    public static final class a {
        @NotNull
        private static final c a = new c("application", e.ANY_MARKER, (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c b = new c("application", "atom+xml", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c c = new c("application", "cbor", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c d = new c("application", "json", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c e = new c("application", "hal+json", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c f = new c("application", "javascript", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c g = new c("application", "octet-stream", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c h = new c("application", "font-woff", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c i = new c("application", "rss+xml", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c j = new c("application", "xml", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c k = new c("application", "xml-dtd", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c l = new c("application", "zip", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c m = new c("application", GZipContentEncoding.NAME, (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c n = new c("application", "x-www-form-urlencoded", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c o = new c("application", "pdf", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c p = new c("application", "protobuf", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c q = new c("application", "wasm", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c r = new c("application", "problem+json", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c s = new c("application", "problem+xml", (List) null, 4, (DefaultConstructorMarker) null);
        public static final a t = new a();

        private a() {
        }

        @NotNull
        public final c b() {
            return d;
        }

        @NotNull
        public final c c() {
            return g;
        }

        @NotNull
        public final c a() {
            return n;
        }
    }

    /* renamed from: io.ktor.http.c$c  reason: collision with other inner class name */
    /* compiled from: ContentTypes.kt */
    public static final class C0247c {
        @NotNull
        private static final c a = new c("multipart", e.ANY_MARKER, (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c b = new c("multipart", "mixed", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c c = new c("multipart", "alternative", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c d = new c("multipart", "related", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c e = new c("multipart", "form-data", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c f = new c("multipart", "signed", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c g = new c("multipart", "encrypted", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c h = new c("multipart", "byteranges", (List) null, 4, (DefaultConstructorMarker) null);
        public static final C0247c i = new C0247c();

        private C0247c() {
        }

        @NotNull
        public final c a() {
            return e;
        }
    }

    /* compiled from: ContentTypes.kt */
    public static final class d {
        @NotNull
        private static final c a = new c("text", e.ANY_MARKER, (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c b = new c("text", "plain", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c c = new c("text", "css", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c d = new c("text", "csv", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c e = new c("text", "html", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c f = new c("text", "javascript", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c g = new c("text", "vcard", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c h = new c("text", "xml", (List) null, 4, (DefaultConstructorMarker) null);
        @NotNull
        private static final c i = new c("text", "event-stream", (List) null, 4, (DefaultConstructorMarker) null);
        public static final d j = new d();

        private d() {
        }

        @NotNull
        public final c a() {
            return b;
        }
    }
}
