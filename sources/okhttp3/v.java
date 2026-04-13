package okhttp3;

import com.alibaba.fastjson.asm.Opcodes;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.github.druk.dnssd.NSType;
import io.netty.util.internal.StringUtil;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.ranges.g;
import kotlin.ranges.n;
import kotlin.text.j;
import kotlin.text.w;
import kotlin.text.x;
import okio.c;
import org.apache.http.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpUrl.kt */
public final class v {
    /* access modifiers changed from: private */
    public static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final b b = new b((DefaultConstructorMarker) null);
    private final boolean c;
    @NotNull
    private final String d;
    @NotNull
    private final String e;
    @NotNull
    private final String f;
    @NotNull
    private final String g;
    private final int h;
    @NotNull
    private final List<String> i;
    private final List<String> j;
    @Nullable
    private final String k;
    private final String l;

    public static final int b(@NotNull String str) {
        return b.c(str);
    }

    @NotNull
    public static final v i(@NotNull String str) {
        return b.d(str);
    }

    @Nullable
    public static final v n(@NotNull String str) {
        return b.f(str);
    }

    public v(@NotNull String scheme, @NotNull String username, @NotNull String password, @NotNull String host, int port, @NotNull List<String> pathSegments, @Nullable List<String> queryNamesAndValues, @Nullable String fragment, @NotNull String url) {
        k.f(scheme, "scheme");
        k.f(username, "username");
        k.f(password, "password");
        k.f(host, SerializableCookie.HOST);
        k.f(pathSegments, "pathSegments");
        k.f(url, "url");
        this.d = scheme;
        this.e = username;
        this.f = password;
        this.g = host;
        this.h = port;
        this.i = pathSegments;
        this.j = queryNamesAndValues;
        this.k = fragment;
        this.l = url;
        this.c = k.a(scheme, "https");
    }

    @NotNull
    public final String t() {
        return this.d;
    }

    @NotNull
    public final String j() {
        return this.g;
    }

    public final int p() {
        return this.h;
    }

    @NotNull
    public final List<String> o() {
        return this.i;
    }

    public final boolean k() {
        return this.c;
    }

    @NotNull
    public final URL v() {
        try {
            return new URL(this.l);
        } catch (MalformedURLException e2) {
            throw new RuntimeException(e2);
        }
    }

    @NotNull
    public final URI u() {
        String uri = l().o().toString();
        try {
            return new URI(uri);
        } catch (URISyntaxException e2) {
            try {
                URI create = URI.create(new j("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]").replace((CharSequence) uri, ""));
                k.b(create, "URI.create(stripped)");
                return create;
            } catch (Exception e3) {
                throw new RuntimeException(e2);
            }
        }
    }

    @NotNull
    public final String h() {
        if (this.e.length() == 0) {
            return "";
        }
        int usernameStart = this.d.length() + 3;
        String str = this.l;
        int usernameEnd = okhttp3.internal.b.n(str, ":@", usernameStart, str.length());
        String str2 = this.l;
        if (str2 != null) {
            String substring = str2.substring(usernameStart, usernameEnd);
            k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return substring;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    @NotNull
    public final String d() {
        if (this.f.length() == 0) {
            return "";
        }
        int passwordStart = x.e0(this.l, ':', this.d.length() + 3, false, 4, (Object) null) + 1;
        int passwordEnd = x.e0(this.l, '@', 0, false, 6, (Object) null);
        String str = this.l;
        if (str != null) {
            String substring = str.substring(passwordStart, passwordEnd);
            k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return substring;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    @NotNull
    public final String e() {
        int pathStart = x.e0(this.l, '/', this.d.length() + 3, false, 4, (Object) null);
        String str = this.l;
        int pathEnd = okhttp3.internal.b.n(str, "?#", pathStart, str.length());
        String str2 = this.l;
        if (str2 != null) {
            String substring = str2.substring(pathStart, pathEnd);
            k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return substring;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    @NotNull
    public final List<String> f() {
        int pathStart = x.e0(this.l, '/', this.d.length() + 3, false, 4, (Object) null);
        String str = this.l;
        int pathEnd = okhttp3.internal.b.n(str, "?#", pathStart, str.length());
        List result = new ArrayList();
        int i2 = pathStart;
        while (i2 < pathEnd) {
            int i3 = i2 + 1;
            int segmentEnd = okhttp3.internal.b.m(this.l, '/', i3, pathEnd);
            String str2 = this.l;
            if (str2 != null) {
                String substring = str2.substring(i3, segmentEnd);
                k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                result.add(substring);
                i2 = segmentEnd;
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
        }
        return result;
    }

    @Nullable
    public final String g() {
        if (this.j == null) {
            return null;
        }
        int queryStart = x.e0(this.l, '?', 0, false, 6, (Object) null) + 1;
        String str = this.l;
        int queryEnd = okhttp3.internal.b.m(str, '#', queryStart, str.length());
        String str2 = this.l;
        if (str2 != null) {
            String substring = str2.substring(queryStart, queryEnd);
            k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return substring;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    @Nullable
    public final String q() {
        if (this.j == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        b.k(this.j, result);
        return result.toString();
    }

    @Nullable
    public final String c() {
        if (this.k == null) {
            return null;
        }
        int fragmentStart = x.e0(this.l, '#', 0, false, 6, (Object) null) + 1;
        String str = this.l;
        if (str != null) {
            String substring = str.substring(fragmentStart);
            k.b(substring, "(this as java.lang.String).substring(startIndex)");
            return substring;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    @NotNull
    public final String r() {
        a m = m("/...");
        if (m == null) {
            k.n();
        }
        return m.x("").k("").c().toString();
    }

    @Nullable
    public final v s(@NotNull String link) {
        k.f(link, "link");
        a m = m(link);
        if (m != null) {
            return m.c();
        }
        return null;
    }

    @NotNull
    public final a l() {
        a result = new a();
        result.w(this.d);
        result.t(h());
        result.s(d());
        result.u(this.g);
        result.v(this.h != b.c(this.d) ? this.h : -1);
        result.f().clear();
        result.f().addAll(f());
        result.e(g());
        result.r(c());
        return result;
    }

    @Nullable
    public final a m(@NotNull String link) {
        k.f(link, "link");
        try {
            return new a().j(this, link);
        } catch (IllegalArgumentException e2) {
            return null;
        }
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof v) && k.a(((v) other).l, this.l);
    }

    public int hashCode() {
        return this.l.hashCode();
    }

    @NotNull
    public String toString() {
        return this.l;
    }

    /* compiled from: HttpUrl.kt */
    public static final class a {
        public static final C0478a a = new C0478a((DefaultConstructorMarker) null);
        @Nullable
        private String b;
        @NotNull
        private String c = "";
        @NotNull
        private String d = "";
        @Nullable
        private String e;
        private int f = -1;
        @NotNull
        private final List<String> g;
        @Nullable
        private List<String> h;
        @Nullable
        private String i;

        public a() {
            ArrayList arrayList = new ArrayList();
            this.g = arrayList;
            arrayList.add("");
        }

        public final void w(@Nullable String str) {
            this.b = str;
        }

        public final void t(@NotNull String str) {
            k.f(str, "<set-?>");
            this.c = str;
        }

        public final void s(@NotNull String str) {
            k.f(str, "<set-?>");
            this.d = str;
        }

        public final void u(@Nullable String str) {
            this.e = str;
        }

        public final void v(int i2) {
            this.f = i2;
        }

        @NotNull
        public final List<String> f() {
            return this.g;
        }

        public final void r(@Nullable String str) {
            this.i = str;
        }

        @NotNull
        public final a q(@NotNull String scheme) {
            k.f(scheme, "scheme");
            if (w.y(scheme, l.DEFAULT_SCHEME_NAME, true)) {
                this.b = l.DEFAULT_SCHEME_NAME;
            } else if (w.y(scheme, "https", true)) {
                this.b = "https";
            } else {
                throw new IllegalArgumentException("unexpected scheme: " + scheme);
            }
            return this;
        }

        @NotNull
        public final a x(@NotNull String username) {
            k.f(username, "username");
            this.c = b.b(v.b, username, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, (Charset) null, NSType.IXFR, (Object) null);
            return this;
        }

        @NotNull
        public final a k(@NotNull String password) {
            k.f(password, "password");
            this.d = b.b(v.b, password, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, (Charset) null, NSType.IXFR, (Object) null);
            return this;
        }

        @NotNull
        public final a g(@NotNull String host) {
            k.f(host, SerializableCookie.HOST);
            String encoded = okhttp3.internal.a.e(b.h(v.b, host, 0, 0, false, 7, (Object) null));
            if (encoded != null) {
                this.e = encoded;
                return this;
            }
            throw new IllegalArgumentException("unexpected host: " + host);
        }

        @NotNull
        public final a m(int port) {
            boolean z = true;
            if (1 > port || 65535 < port) {
                z = false;
            }
            if (z) {
                this.f = port;
                return this;
            }
            throw new IllegalArgumentException(("unexpected port: " + port).toString());
        }

        private final int d() {
            int i2 = this.f;
            if (i2 != -1) {
                return i2;
            }
            b bVar = v.b;
            String str = this.b;
            if (str == null) {
                k.n();
            }
            return bVar.c(str);
        }

        @NotNull
        public final a e(@Nullable String encodedQuery) {
            List<String> list;
            if (encodedQuery != null) {
                b bVar = v.b;
                String b2 = b.b(bVar, encodedQuery, 0, 0, " \"'<>#", true, false, true, false, (Charset) null, 211, (Object) null);
                if (b2 != null) {
                    list = bVar.j(b2);
                    this.h = list;
                    return this;
                }
            }
            list = null;
            this.h = list;
            return this;
        }

        @NotNull
        public final a b(@NotNull String name, @Nullable String value) {
            k.f(name, "name");
            if (this.h == null) {
                this.h = new ArrayList();
            }
            List<String> list = this.h;
            if (list == null) {
                k.n();
            }
            b bVar = v.b;
            list.add(b.b(bVar, name, 0, 0, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, false, (Charset) null, 219, (Object) null));
            List<String> list2 = this.h;
            if (list2 == null) {
                k.n();
            }
            list2.add(value != null ? b.b(bVar, value, 0, 0, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, false, (Charset) null, 219, (Object) null) : null);
            return this;
        }

        @NotNull
        public final a a(@NotNull String encodedName, @Nullable String encodedValue) {
            k.f(encodedName, "encodedName");
            if (this.h == null) {
                this.h = new ArrayList();
            }
            List<String> list = this.h;
            if (list == null) {
                k.n();
            }
            b bVar = v.b;
            list.add(b.b(bVar, encodedName, 0, 0, " \"'<>#&=", true, false, true, false, (Charset) null, 211, (Object) null));
            List<String> list2 = this.h;
            if (list2 == null) {
                k.n();
            }
            list2.add(encodedValue != null ? b.b(bVar, encodedValue, 0, 0, " \"'<>#&=", true, false, true, false, (Charset) null, 211, (Object) null) : null);
            return this;
        }

        @NotNull
        public final a o() {
            String str = this.e;
            String str2 = null;
            this.e = str != null ? new j("[\"<>^`{|}]").replace((CharSequence) str, "") : null;
            int size = this.g.size();
            for (int i2 = 0; i2 < size; i2++) {
                List<String> list = this.g;
                list.set(i2, b.b(v.b, list.get(i2), 0, 0, "[]", true, true, false, false, (Charset) null, 227, (Object) null));
            }
            List encodedQueryNamesAndValues = this.h;
            if (encodedQueryNamesAndValues != null) {
                int size2 = encodedQueryNamesAndValues.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    String str3 = encodedQueryNamesAndValues.get(i3);
                    encodedQueryNamesAndValues.set(i3, str3 != null ? b.b(v.b, str3, 0, 0, "\\^`{|}", true, true, true, false, (Charset) null, 195, (Object) null) : null);
                }
            }
            String str4 = this.i;
            if (str4 != null) {
                str2 = b.b(v.b, str4, 0, 0, " \"#<>\\^`{|}", true, true, false, true, (Charset) null, Opcodes.IF_ICMPGT, (Object) null);
            }
            this.i = str2;
            return this;
        }

        @NotNull
        public final v c() {
            ArrayList arrayList;
            String str = this.b;
            if (str != null) {
                b bVar = v.b;
                String h2 = b.h(bVar, this.c, 0, 0, false, 7, (Object) null);
                String h3 = b.h(bVar, this.d, 0, 0, false, 7, (Object) null);
                String str2 = this.e;
                if (str2 != null) {
                    int d2 = d();
                    Iterable<String> $this$mapTo$iv$iv = this.g;
                    ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                    for (String it : $this$mapTo$iv$iv) {
                        arrayList2.add(b.h(v.b, it, 0, 0, false, 7, (Object) null));
                    }
                    Iterable<String> $this$map$iv = this.h;
                    if ($this$map$iv != null) {
                        arrayList = new ArrayList(r.r($this$map$iv, 10));
                        for (String it2 : $this$map$iv) {
                            arrayList.add(it2 != null ? b.h(v.b, it2, 0, 0, true, 3, (Object) null) : null);
                        }
                    } else {
                        arrayList = null;
                    }
                    String str3 = this.i;
                    return new v(str, h2, h3, str2, d2, arrayList2, arrayList, str3 != null ? b.h(v.b, str3, 0, 0, false, 7, (Object) null) : null, toString());
                }
                throw new IllegalStateException("host == null");
            }
            throw new IllegalStateException("scheme == null");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0036, code lost:
            if ((r8.d.length() > 0) != false) goto L_0x0038;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0099, code lost:
            if (r3 != r5.c(r4)) goto L_0x009b;
         */
        @org.jetbrains.annotations.NotNull
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String toString() {
            /*
                r8 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                r1 = r0
                r2 = 0
                java.lang.String r3 = r8.b
                if (r3 == 0) goto L_0x0014
                r1.append(r3)
                java.lang.String r3 = "://"
                r1.append(r3)
                goto L_0x0019
            L_0x0014:
                java.lang.String r3 = "//"
                r1.append(r3)
            L_0x0019:
                java.lang.String r3 = r8.c
                int r3 = r3.length()
                r4 = 1
                r5 = 0
                if (r3 <= 0) goto L_0x0026
                r3 = r4
                goto L_0x0027
            L_0x0026:
                r3 = r5
            L_0x0027:
                r6 = 58
                if (r3 != 0) goto L_0x0038
                java.lang.String r3 = r8.d
                int r3 = r3.length()
                if (r3 <= 0) goto L_0x0035
                r3 = r4
                goto L_0x0036
            L_0x0035:
                r3 = r5
            L_0x0036:
                if (r3 == 0) goto L_0x0056
            L_0x0038:
                java.lang.String r3 = r8.c
                r1.append(r3)
                java.lang.String r3 = r8.d
                int r3 = r3.length()
                if (r3 <= 0) goto L_0x0046
                goto L_0x0047
            L_0x0046:
                r4 = r5
            L_0x0047:
                if (r4 == 0) goto L_0x0051
                r1.append(r6)
                java.lang.String r3 = r8.d
                r1.append(r3)
            L_0x0051:
                r3 = 64
                r1.append(r3)
            L_0x0056:
                java.lang.String r3 = r8.e
                if (r3 == 0) goto L_0x007c
                if (r3 != 0) goto L_0x005f
                kotlin.jvm.internal.k.n()
            L_0x005f:
                r4 = 2
                r7 = 0
                boolean r3 = kotlin.text.x.R(r3, r6, r5, r4, r7)
                if (r3 == 0) goto L_0x0077
                r3 = 91
                r1.append(r3)
                java.lang.String r3 = r8.e
                r1.append(r3)
                r3 = 93
                r1.append(r3)
                goto L_0x007c
            L_0x0077:
                java.lang.String r3 = r8.e
                r1.append(r3)
            L_0x007c:
                int r3 = r8.f
                r4 = -1
                if (r3 != r4) goto L_0x0086
                java.lang.String r3 = r8.b
                if (r3 == 0) goto L_0x00a1
            L_0x0086:
                int r3 = r8.d()
                java.lang.String r4 = r8.b
                if (r4 == 0) goto L_0x009b
                okhttp3.v$b r5 = okhttp3.v.b
                if (r4 != 0) goto L_0x0095
                kotlin.jvm.internal.k.n()
            L_0x0095:
                int r4 = r5.c(r4)
                if (r3 == r4) goto L_0x00a1
            L_0x009b:
                r1.append(r6)
                r1.append(r3)
            L_0x00a1:
                okhttp3.v$b r3 = okhttp3.v.b
                java.util.List<java.lang.String> r4 = r8.g
                r3.i(r4, r1)
                java.util.List<java.lang.String> r4 = r8.h
                if (r4 == 0) goto L_0x00bb
                r4 = 63
                r1.append(r4)
                java.util.List<java.lang.String> r4 = r8.h
                if (r4 != 0) goto L_0x00b8
                kotlin.jvm.internal.k.n()
            L_0x00b8:
                r3.k(r4, r1)
            L_0x00bb:
                java.lang.String r3 = r8.i
                if (r3 == 0) goto L_0x00c9
                r3 = 35
                r1.append(r3)
                java.lang.String r3 = r8.i
                r1.append(r3)
            L_0x00c9:
                java.lang.String r0 = r0.toString()
                java.lang.String r1 = "StringBuilder().apply(builderAction).toString()"
                kotlin.jvm.internal.k.b(r0, r1)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.v.a.toString():java.lang.String");
        }

        @NotNull
        public final a j(@Nullable v base, @NotNull String input) {
            int pos;
            int c2;
            String str;
            int schemeDelimiterOffset;
            boolean z;
            int slashCount;
            String str2;
            char c3;
            int i2;
            String str3;
            int componentDelimiterOffset;
            String str4;
            String str5 = input;
            k.f(str5, "input");
            int pos2 = okhttp3.internal.b.x(str5, 0, 0, 3, (Object) null);
            int limit = okhttp3.internal.b.z(str5, pos2, 0, 2, (Object) null);
            C0478a aVar = a;
            int schemeDelimiterOffset2 = aVar.g(str5, pos2, limit);
            String str6 = "(this as java.lang.Strin…ing(startIndex, endIndex)";
            int i3 = -1;
            boolean z2 = true;
            if (schemeDelimiterOffset2 != -1) {
                if (w.K(str5, "https:", pos2, true)) {
                    this.b = "https";
                    pos2 += "https:".length();
                } else if (w.K(str5, "http:", pos2, true)) {
                    this.b = l.DEFAULT_SCHEME_NAME;
                    pos2 += "http:".length();
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Expected URL scheme 'http' or 'https' but was '");
                    String substring = str5.substring(0, schemeDelimiterOffset2);
                    k.b(substring, str6);
                    sb.append(substring);
                    sb.append("'");
                    throw new IllegalArgumentException(sb.toString());
                }
            } else if (base != null) {
                this.b = base.t();
            } else {
                throw new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no colon was found");
            }
            int slashCount2 = aVar.h(str5, pos2, limit);
            char c4 = '#';
            if (slashCount2 >= 2 || base == null || (!k.a(base.t(), this.b))) {
                int pos3 = pos2 + slashCount2;
                boolean hasUsername = false;
                boolean hasPassword = false;
                while (true) {
                    int componentDelimiterOffset2 = okhttp3.internal.b.n(str5, "@/\\?#", pos3, limit);
                    if (componentDelimiterOffset2 != limit) {
                        c2 = str5.charAt(componentDelimiterOffset2);
                    } else {
                        c2 = i3;
                    }
                    switch (c2) {
                        case -1:
                        case 35:
                        case 47:
                        case 63:
                        case 92:
                            int componentDelimiterOffset3 = componentDelimiterOffset2;
                            int i4 = slashCount2;
                            boolean z3 = z2;
                            String str7 = str6;
                            int i5 = schemeDelimiterOffset2;
                            C0478a aVar2 = a;
                            int pos4 = pos3;
                            int portColonOffset = aVar2.f(str5, pos4, componentDelimiterOffset3);
                            if (portColonOffset + 1 < componentDelimiterOffset3) {
                                this.e = okhttp3.internal.a.e(b.h(v.b, input, pos4, portColonOffset, false, 4, (Object) null));
                                int a2 = aVar2.e(str5, portColonOffset + 1, componentDelimiterOffset3);
                                this.f = a2;
                                if (a2 != -1 ? z3 : false) {
                                    str = str7;
                                } else {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("Invalid URL port: \"");
                                    String substring2 = str5.substring(portColonOffset + 1, componentDelimiterOffset3);
                                    k.b(substring2, str7);
                                    sb2.append(substring2);
                                    sb2.append(StringUtil.DOUBLE_QUOTE);
                                    throw new IllegalArgumentException(sb2.toString().toString());
                                }
                            } else {
                                str = str7;
                                b bVar = v.b;
                                this.e = okhttp3.internal.a.e(b.h(bVar, input, pos4, portColonOffset, false, 4, (Object) null));
                                String str8 = this.b;
                                if (str8 == null) {
                                    k.n();
                                }
                                this.f = bVar.c(str8);
                            }
                            if (this.e != null ? z3 : false) {
                                pos2 = componentDelimiterOffset3;
                                break;
                            } else {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("Invalid URL host: \"");
                                String substring3 = str5.substring(pos4, portColonOffset);
                                k.b(substring3, str);
                                sb3.append(substring3);
                                sb3.append(StringUtil.DOUBLE_QUOTE);
                                throw new IllegalArgumentException(sb3.toString().toString());
                            }
                        case 64:
                            if (!hasPassword) {
                                int passwordColonOffset = okhttp3.internal.b.m(str5, ':', pos3, componentDelimiterOffset2);
                                b bVar2 = v.b;
                                int passwordColonOffset2 = passwordColonOffset;
                                String str9 = "%40";
                                int componentDelimiterOffset4 = componentDelimiterOffset2;
                                int i6 = pos3;
                                slashCount = slashCount2;
                                z = z2;
                                str3 = str6;
                                schemeDelimiterOffset = schemeDelimiterOffset2;
                                String canonicalUsername = b.b(bVar2, input, pos3, passwordColonOffset2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, (Charset) null, 240, (Object) null);
                                if (hasUsername) {
                                    str4 = this.c + str9 + canonicalUsername;
                                } else {
                                    str4 = canonicalUsername;
                                }
                                this.c = str4;
                                int passwordColonOffset3 = passwordColonOffset2;
                                componentDelimiterOffset = componentDelimiterOffset4;
                                if (passwordColonOffset3 != componentDelimiterOffset) {
                                    hasPassword = true;
                                    int i7 = passwordColonOffset3;
                                    String str10 = canonicalUsername;
                                    this.d = b.b(bVar2, input, passwordColonOffset3 + 1, componentDelimiterOffset, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, (Charset) null, 240, (Object) null);
                                } else {
                                    String str11 = canonicalUsername;
                                }
                                hasUsername = true;
                            } else {
                                slashCount = slashCount2;
                                z = z2;
                                str3 = str6;
                                schemeDelimiterOffset = schemeDelimiterOffset2;
                                int componentDelimiterOffset5 = componentDelimiterOffset2;
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(this.d);
                                sb4.append("%40");
                                componentDelimiterOffset = componentDelimiterOffset5;
                                StringBuilder sb5 = sb4;
                                sb5.append(b.b(v.b, input, pos3, componentDelimiterOffset5, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, (Charset) null, 240, (Object) null));
                                this.d = sb5.toString();
                            }
                            pos3 = componentDelimiterOffset + 1;
                            str2 = str3;
                            i2 = -1;
                            c3 = '#';
                            continue;
                        default:
                            int i8 = componentDelimiterOffset2;
                            c3 = c4;
                            slashCount = slashCount2;
                            z = z2;
                            i2 = i3;
                            str2 = str6;
                            schemeDelimiterOffset = schemeDelimiterOffset2;
                            int i9 = pos3;
                            continue;
                    }
                    i3 = i2;
                    c4 = c3;
                    str6 = str2;
                    slashCount2 = slashCount;
                    z2 = z;
                    schemeDelimiterOffset2 = schemeDelimiterOffset;
                }
            } else {
                this.c = base.h();
                this.d = base.d();
                this.e = base.j();
                this.f = base.p();
                this.g.clear();
                this.g.addAll(base.f());
                if (pos2 == limit || str5.charAt(pos2) == '#') {
                    e(base.g());
                }
                int i10 = slashCount2;
                int i11 = schemeDelimiterOffset2;
            }
            int pathDelimiterOffset = okhttp3.internal.b.n(str5, "?#", pos2, limit);
            p(str5, pos2, pathDelimiterOffset);
            int pos5 = pathDelimiterOffset;
            if (pos5 >= limit || str5.charAt(pos5) != '?') {
                pos = pos5;
            } else {
                int queryDelimiterOffset = okhttp3.internal.b.m(str5, '#', pos5, limit);
                b bVar3 = v.b;
                int i12 = pos5;
                this.h = bVar3.j(b.b(bVar3, input, pos5 + 1, queryDelimiterOffset, " \"'<>#", true, false, true, false, (Charset) null, 208, (Object) null));
                pos = queryDelimiterOffset;
            }
            if (pos >= limit || str5.charAt(pos) != '#') {
            } else {
                int i13 = pos;
                this.i = b.b(v.b, input, pos + 1, limit, "", true, false, false, true, (Charset) null, Opcodes.ARETURN, (Object) null);
            }
            return this;
        }

        private final void p(String input, int startPos, int limit) {
            int pos = startPos;
            if (pos != limit) {
                char c2 = input.charAt(pos);
                if (c2 == '/' || c2 == '\\') {
                    this.g.clear();
                    this.g.add("");
                    pos++;
                } else {
                    List<String> list = this.g;
                    list.set(list.size() - 1, "");
                }
                int i2 = pos;
                while (i2 < limit) {
                    int pathSegmentDelimiterOffset = okhttp3.internal.b.n(input, "/\\", i2, limit);
                    boolean segmentHasTrailingSlash = pathSegmentDelimiterOffset < limit;
                    n(input, i2, pathSegmentDelimiterOffset, segmentHasTrailingSlash, true);
                    i2 = pathSegmentDelimiterOffset;
                    if (segmentHasTrailingSlash) {
                        i2++;
                    }
                }
            }
        }

        private final void n(String input, int pos, int limit, boolean addTrailingSlash, boolean alreadyEncoded) {
            String segment = b.b(v.b, input, pos, limit, " \"<>^`{}|/\\?#", alreadyEncoded, false, false, false, (Charset) null, 240, (Object) null);
            if (!h(segment)) {
                if (i(segment)) {
                    l();
                    return;
                }
                List<String> list = this.g;
                if (list.get(list.size() - 1).length() == 0) {
                    List<String> list2 = this.g;
                    list2.set(list2.size() - 1, segment);
                } else {
                    this.g.add(segment);
                }
                if (addTrailingSlash) {
                    this.g.add("");
                }
            }
        }

        private final boolean h(String input) {
            return k.a(input, ".") || w.y(input, "%2e", true);
        }

        private final boolean i(String input) {
            return k.a(input, "..") || w.y(input, "%2e.", true) || w.y(input, ".%2e", true) || w.y(input, "%2e%2e", true);
        }

        private final void l() {
            List<String> list = this.g;
            if (!(list.remove(list.size() - 1).length() == 0) || !(!this.g.isEmpty())) {
                this.g.add("");
                return;
            }
            List<String> list2 = this.g;
            list2.set(list2.size() - 1, "");
        }

        /* renamed from: okhttp3.v$a$a  reason: collision with other inner class name */
        /* compiled from: HttpUrl.kt */
        public static final class C0478a {
            private C0478a() {
            }

            public /* synthetic */ C0478a(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            /* access modifiers changed from: private */
            public final int g(String input, int pos, int limit) {
                if (limit - pos < 2) {
                    return -1;
                }
                char c0 = input.charAt(pos);
                if ((c0 < 'a' || c0 > 'z') && (c0 < 'A' || c0 > 'Z')) {
                    return -1;
                }
                int i = pos + 1;
                while (i < limit) {
                    char charAt = input.charAt(i);
                    if (('a' <= charAt && 'z' >= charAt) || (('A' <= charAt && 'Z' >= charAt) || (('0' <= charAt && '9' >= charAt) || charAt == '+' || charAt == '-' || charAt == '.'))) {
                        i++;
                    } else if (charAt == ':') {
                        return i;
                    } else {
                        return -1;
                    }
                }
                return -1;
            }

            /* access modifiers changed from: private */
            public final int h(@NotNull String $this$slashCount, int pos, int limit) {
                int slashCount = 0;
                for (int i = pos; i < limit; i++) {
                    char c = $this$slashCount.charAt(i);
                    if (c != '\\' && c != '/') {
                        break;
                    }
                    slashCount++;
                }
                return slashCount;
            }

            /* access modifiers changed from: private */
            /* JADX WARNING: Can't fix incorrect switch cases order */
            /* JADX WARNING: Code restructure failed: missing block: B:5:0x000d, code lost:
                if (r0 >= r6) goto L_0x001a;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final int f(java.lang.String r4, int r5, int r6) {
                /*
                    r3 = this;
                    r0 = r5
                L_0x0001:
                    if (r0 >= r6) goto L_0x001e
                    char r1 = r4.charAt(r0)
                    switch(r1) {
                        case 58: goto L_0x0019;
                        case 91: goto L_0x000b;
                        default: goto L_0x000a;
                    }
                L_0x000a:
                    goto L_0x001a
                L_0x000b:
                    int r0 = r0 + 1
                    if (r0 >= r6) goto L_0x001a
                    char r1 = r4.charAt(r0)
                    r2 = 93
                    if (r1 != r2) goto L_0x0018
                    goto L_0x001a
                L_0x0018:
                    goto L_0x000b
                L_0x0019:
                    return r0
                L_0x001a:
                    int r0 = r0 + 1
                    goto L_0x0001
                L_0x001e:
                    return r6
                */
                throw new UnsupportedOperationException("Method not decompiled: okhttp3.v.a.C0478a.f(java.lang.String, int, int):int");
            }

            /* access modifiers changed from: private */
            public final int e(String input, int pos, int limit) {
                try {
                    int i = Integer.parseInt(b.b(v.b, input, pos, limit, "", false, false, false, false, (Charset) null, 248, (Object) null));
                    if (1 <= i && 65535 >= i) {
                        return i;
                    }
                    return -1;
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
        }
    }

    /* compiled from: HttpUrl.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final int c(@NotNull String scheme) {
            k.f(scheme, "scheme");
            switch (scheme.hashCode()) {
                case 3213448:
                    if (scheme.equals(l.DEFAULT_SCHEME_NAME)) {
                        return 80;
                    }
                    break;
                case 99617003:
                    if (scheme.equals("https")) {
                        return 443;
                    }
                    break;
            }
            return -1;
        }

        public final void i(@NotNull List<String> $this$toPathString, @NotNull StringBuilder out) {
            k.f($this$toPathString, "$this$toPathString");
            k.f(out, "out");
            int size = $this$toPathString.size();
            for (int i = 0; i < size; i++) {
                out.append('/');
                out.append($this$toPathString.get(i));
            }
        }

        public final void k(@NotNull List<String> $this$toQueryString, @NotNull StringBuilder out) {
            k.f($this$toQueryString, "$this$toQueryString");
            k.f(out, "out");
            g k = n.k(n.l(0, $this$toQueryString.size()), 2);
            int i = k.a();
            int b = k.b();
            int e = k.e();
            if (e >= 0) {
                if (i > b) {
                    return;
                }
            } else if (i < b) {
                return;
            }
            while (true) {
                String name = $this$toQueryString.get(i);
                String value = $this$toQueryString.get(i + 1);
                if (i > 0) {
                    out.append('&');
                }
                out.append(name);
                if (value != null) {
                    out.append('=');
                    out.append(value);
                }
                if (i != b) {
                    i += e;
                } else {
                    return;
                }
            }
        }

        @NotNull
        public final List<String> j(@NotNull String $this$toQueryNamesAndValues) {
            k.f($this$toQueryNamesAndValues, "$this$toQueryNamesAndValues");
            List result = new ArrayList();
            int pos = 0;
            while (pos <= $this$toQueryNamesAndValues.length()) {
                int ampersandOffset = x.e0($this$toQueryNamesAndValues, '&', pos, false, 4, (Object) null);
                if (ampersandOffset == -1) {
                    ampersandOffset = $this$toQueryNamesAndValues.length();
                }
                int ampersandOffset2 = ampersandOffset;
                int equalsOffset = x.e0($this$toQueryNamesAndValues, '=', pos, false, 4, (Object) null);
                if (equalsOffset == -1 || equalsOffset > ampersandOffset2) {
                    String substring = $this$toQueryNamesAndValues.substring(pos, ampersandOffset2);
                    k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    result.add(substring);
                    result.add((Object) null);
                } else {
                    String substring2 = $this$toQueryNamesAndValues.substring(pos, equalsOffset);
                    k.b(substring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    result.add(substring2);
                    String substring3 = $this$toQueryNamesAndValues.substring(equalsOffset + 1, ampersandOffset2);
                    k.b(substring3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    result.add(substring3);
                }
                pos = ampersandOffset2 + 1;
            }
            return result;
        }

        @NotNull
        public final v d(@NotNull String $this$toHttpUrl) {
            k.f($this$toHttpUrl, "$this$toHttpUrl");
            return new a().j((v) null, $this$toHttpUrl).c();
        }

        @Nullable
        public final v f(@NotNull String $this$toHttpUrlOrNull) {
            k.f($this$toHttpUrlOrNull, "$this$toHttpUrlOrNull");
            try {
                return d($this$toHttpUrlOrNull);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        public static /* synthetic */ String h(b bVar, String str, int i, int i2, boolean z, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                i = 0;
            }
            if ((i3 & 2) != 0) {
                i2 = str.length();
            }
            if ((i3 & 4) != 0) {
                z = false;
            }
            return bVar.g(str, i, i2, z);
        }

        @NotNull
        public final String g(@NotNull String $this$percentDecode, int pos, int limit, boolean plusIsSpace) {
            k.f($this$percentDecode, "$this$percentDecode");
            for (int i = pos; i < limit; i++) {
                char c = $this$percentDecode.charAt(i);
                if (c == '%' || (c == '+' && plusIsSpace)) {
                    c out = new c();
                    out.writeUtf8($this$percentDecode, pos, i);
                    m(out, $this$percentDecode, i, limit, plusIsSpace);
                    return out.a1();
                }
            }
            String substring = $this$percentDecode.substring(pos, limit);
            k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return substring;
        }

        private final void m(@NotNull c $this$writePercentDecoded, String encoded, int pos, int limit, boolean plusIsSpace) {
            int i = pos;
            while (i < limit) {
                if (encoded != null) {
                    int codePoint = encoded.codePointAt(i);
                    if (codePoint == 37 && i + 2 < limit) {
                        int d1 = okhttp3.internal.b.E(encoded.charAt(i + 1));
                        int d2 = okhttp3.internal.b.E(encoded.charAt(i + 2));
                        if (!(d1 == -1 || d2 == -1)) {
                            $this$writePercentDecoded.writeByte((d1 << 4) + d2);
                            i = i + 2 + Character.charCount(codePoint);
                        }
                    } else if (codePoint == 43 && plusIsSpace) {
                        $this$writePercentDecoded.writeByte(32);
                        i++;
                    }
                    $this$writePercentDecoded.writeUtf8CodePoint(codePoint);
                    i += Character.charCount(codePoint);
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
            }
        }

        private final boolean e(@NotNull String $this$isPercentEncoded, int pos, int limit) {
            return pos + 2 < limit && $this$isPercentEncoded.charAt(pos) == '%' && okhttp3.internal.b.E($this$isPercentEncoded.charAt(pos + 1)) != -1 && okhttp3.internal.b.E($this$isPercentEncoded.charAt(pos + 2)) != -1;
        }

        public static /* synthetic */ String b(b bVar, String str, int i, int i2, String str2, boolean z, boolean z2, boolean z3, boolean z4, Charset charset, int i3, Object obj) {
            int i4;
            boolean z5;
            boolean z6;
            boolean z7;
            boolean z8;
            Charset charset2;
            int i5 = i3;
            int i6 = (i5 & 1) != 0 ? 0 : i;
            if ((i5 & 2) != 0) {
                i4 = str.length();
            } else {
                i4 = i2;
            }
            if ((i5 & 8) != 0) {
                z5 = false;
            } else {
                z5 = z;
            }
            if ((i5 & 16) != 0) {
                z6 = false;
            } else {
                z6 = z2;
            }
            if ((i5 & 32) != 0) {
                z7 = false;
            } else {
                z7 = z3;
            }
            if ((i5 & 64) != 0) {
                z8 = false;
            } else {
                z8 = z4;
            }
            if ((i5 & 128) != 0) {
                charset2 = null;
            } else {
                charset2 = charset;
            }
            return bVar.a(str, i6, i4, str2, z5, z6, z7, z8, charset2);
        }

        @NotNull
        public final String a(@NotNull String $this$canonicalize, int pos, int limit, @NotNull String encodeSet, boolean alreadyEncoded, boolean strict, boolean plusIsSpace, boolean unicodeAllowed, @Nullable Charset charset) {
            String str = $this$canonicalize;
            int i = limit;
            String str2 = encodeSet;
            k.f(str, "$this$canonicalize");
            k.f(str2, "encodeSet");
            int i2 = pos;
            while (i2 < i) {
                int codePoint = str.codePointAt(i2);
                if (codePoint >= 32 && codePoint != 127) {
                    if (codePoint < 128 || unicodeAllowed) {
                        if (!x.R(str2, (char) codePoint, false, 2, (Object) null)) {
                            if (codePoint == 37) {
                                if (alreadyEncoded) {
                                    if (strict) {
                                        if (!e(str, i2, i)) {
                                        }
                                        if (codePoint == 43 || !plusIsSpace) {
                                            i2 += Character.charCount(codePoint);
                                            int i3 = codePoint;
                                        }
                                    }
                                }
                            }
                            if (codePoint == 43) {
                            }
                            i2 += Character.charCount(codePoint);
                            int i32 = codePoint;
                        }
                    }
                }
                c out = new c();
                out.writeUtf8(str, pos, i2);
                c out2 = out;
                l(out, $this$canonicalize, i2, limit, encodeSet, alreadyEncoded, strict, plusIsSpace, unicodeAllowed, charset);
                return out2.a1();
            }
            String substring = $this$canonicalize.substring(pos, limit);
            k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return substring;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0077, code lost:
            if (e(r1, r7, r2) == false) goto L_0x0086;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final void l(@org.jetbrains.annotations.NotNull okio.c r15, java.lang.String r16, int r17, int r18, java.lang.String r19, boolean r20, boolean r21, boolean r22, boolean r23, java.nio.charset.Charset r24) {
            /*
                r14 = this;
                r0 = r15
                r1 = r16
                r2 = r18
                r3 = r24
                r4 = 0
                r5 = r4
                r6 = 0
                r7 = r17
            L_0x000c:
                if (r7 >= r2) goto L_0x00e1
                if (r1 == 0) goto L_0x00d6
                int r6 = r1.codePointAt(r7)
                if (r20 == 0) goto L_0x0032
                r8 = 9
                if (r6 == r8) goto L_0x002d
                r8 = 10
                if (r6 == r8) goto L_0x002d
                r8 = 12
                if (r6 == r8) goto L_0x0028
                r8 = 13
                if (r6 == r8) goto L_0x0028
                goto L_0x0032
            L_0x0028:
                r8 = r14
                r12 = r19
                goto L_0x00ce
            L_0x002d:
                r8 = r14
                r12 = r19
                goto L_0x00ce
            L_0x0032:
                r8 = 43
                if (r6 != r8) goto L_0x0047
                if (r22 == 0) goto L_0x0047
                if (r20 == 0) goto L_0x003d
                java.lang.String r8 = "+"
                goto L_0x003f
            L_0x003d:
                java.lang.String r8 = "%2B"
            L_0x003f:
                r15.writeUtf8(r8)
                r8 = r14
                r12 = r19
                goto L_0x00ce
            L_0x0047:
                r8 = 32
                r9 = 37
                if (r6 < r8) goto L_0x0083
                r8 = 127(0x7f, float:1.78E-43)
                if (r6 == r8) goto L_0x0083
                r8 = 128(0x80, float:1.794E-43)
                if (r6 < r8) goto L_0x0061
                if (r23 == 0) goto L_0x005d
                goto L_0x0061
            L_0x005d:
                r8 = r14
                r12 = r19
                goto L_0x0086
            L_0x0061:
                char r8 = (char) r6
                r10 = 0
                r11 = 2
                r12 = r19
                boolean r8 = kotlin.text.x.R(r12, r8, r10, r11, r4)
                if (r8 != 0) goto L_0x0081
                if (r6 != r9) goto L_0x007c
                if (r20 == 0) goto L_0x007a
                if (r21 == 0) goto L_0x007c
                r8 = r14
                boolean r10 = r14.e(r1, r7, r2)
                if (r10 != 0) goto L_0x007d
                goto L_0x0086
            L_0x007a:
                r8 = r14
                goto L_0x0086
            L_0x007c:
                r8 = r14
            L_0x007d:
                r15.writeUtf8CodePoint(r6)
                goto L_0x00ce
            L_0x0081:
                r8 = r14
                goto L_0x0086
            L_0x0083:
                r8 = r14
                r12 = r19
            L_0x0086:
                if (r5 != 0) goto L_0x008e
                okio.c r10 = new okio.c
                r10.<init>()
                r5 = r10
            L_0x008e:
                if (r3 == 0) goto L_0x00a2
                java.nio.charset.Charset r10 = java.nio.charset.StandardCharsets.UTF_8
                boolean r10 = kotlin.jvm.internal.k.a(r3, r10)
                if (r10 == 0) goto L_0x0099
                goto L_0x00a2
            L_0x0099:
                int r10 = java.lang.Character.charCount(r6)
                int r10 = r10 + r7
                r5.writeString(r1, r7, r10, r3)
                goto L_0x00a5
            L_0x00a2:
                r5.writeUtf8CodePoint(r6)
            L_0x00a5:
                boolean r10 = r5.r0()
                if (r10 != 0) goto L_0x00ce
                byte r10 = r5.readByte()
                r10 = r10 & 255(0xff, float:3.57E-43)
                r15.writeByte(r9)
                char[] r11 = okhttp3.v.a
                int r13 = r10 >> 4
                r13 = r13 & 15
                char r11 = r11[r13]
                r15.writeByte(r11)
                char[] r11 = okhttp3.v.a
                r13 = r10 & 15
                char r11 = r11[r13]
                r15.writeByte(r11)
                goto L_0x00a5
            L_0x00ce:
                int r9 = java.lang.Character.charCount(r6)
                int r7 = r7 + r9
                goto L_0x000c
            L_0x00d6:
                r8 = r14
                r12 = r19
                kotlin.TypeCastException r4 = new kotlin.TypeCastException
                java.lang.String r9 = "null cannot be cast to non-null type java.lang.String"
                r4.<init>(r9)
                throw r4
            L_0x00e1:
                r8 = r14
                r12 = r19
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.v.b.l(okio.c, java.lang.String, int, int, java.lang.String, boolean, boolean, boolean, boolean, java.nio.charset.Charset):void");
        }
    }
}
