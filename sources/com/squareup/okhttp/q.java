package com.squareup.okhttp;

import com.meituan.robust.Constants;
import java.net.IDN;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import okio.c;
import org.apache.http.l;
import org.slf4j.e;

/* compiled from: HttpUrl */
public final class q {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    /* access modifiers changed from: private */
    public final String b;
    private final String c;
    private final String d;
    /* access modifiers changed from: private */
    public final String e;
    /* access modifiers changed from: private */
    public final int f;
    private final List<String> g;
    private final List<String> h;
    private final String i;
    private final String j;

    private q(b builder) {
        this.b = builder.a;
        this.c = x(builder.b, false);
        this.d = x(builder.c, false);
        this.e = builder.d;
        this.f = builder.g();
        this.g = y(builder.f, false);
        List<String> list = builder.g;
        String str = null;
        this.h = list != null ? y(list, true) : null;
        String str2 = builder.h;
        this.i = str2 != null ? x(str2, false) : str;
        this.j = builder.toString();
    }

    public URL G() {
        try {
            return new URL(this.j);
        } catch (MalformedURLException e2) {
            throw new RuntimeException(e2);
        }
    }

    public URI F() {
        try {
            return new URI(t().s().toString());
        } catch (URISyntaxException e2) {
            throw new IllegalStateException("not valid as a java.net.URI: " + this.j);
        }
    }

    public String E() {
        return this.b;
    }

    public boolean r() {
        return this.b.equals("https");
    }

    public String p() {
        if (this.c.isEmpty()) {
            return "";
        }
        int usernameStart = this.b.length() + 3;
        String str = this.j;
        return this.j.substring(usernameStart, j(str, usernameStart, str.length(), ":@"));
    }

    public String l() {
        if (this.d.isEmpty()) {
            return "";
        }
        int passwordEnd = this.j.indexOf(64);
        return this.j.substring(this.j.indexOf(58, this.b.length() + 3) + 1, passwordEnd);
    }

    public String q() {
        return this.e;
    }

    public int A() {
        return this.f;
    }

    public static int i(String scheme) {
        if (scheme.equals(l.DEFAULT_SCHEME_NAME)) {
            return 80;
        }
        if (scheme.equals("https")) {
            return 443;
        }
        return -1;
    }

    public String m() {
        int pathStart = this.j.indexOf(47, this.b.length() + 3);
        String str = this.j;
        return this.j.substring(pathStart, j(str, pathStart, str.length(), "?#"));
    }

    static void v(StringBuilder out, List<String> pathSegments) {
        int size = pathSegments.size();
        for (int i2 = 0; i2 < size; i2++) {
            out.append('/');
            out.append(pathSegments.get(i2));
        }
    }

    public List<String> n() {
        int pathStart = this.j.indexOf(47, this.b.length() + 3);
        String str = this.j;
        int pathEnd = j(str, pathStart, str.length(), "?#");
        List<String> result = new ArrayList<>();
        int i2 = pathStart;
        while (i2 < pathEnd) {
            int i3 = i2 + 1;
            int segmentEnd = j(this.j, i3, pathEnd, "/");
            result.add(this.j.substring(i3, segmentEnd));
            i2 = segmentEnd;
        }
        return result;
    }

    public String o() {
        if (this.h == null) {
            return null;
        }
        int queryStart = this.j.indexOf(63) + 1;
        String str = this.j;
        return this.j.substring(queryStart, j(str, queryStart + 1, str.length(), "#"));
    }

    static void s(StringBuilder out, List<String> namesAndValues) {
        int size = namesAndValues.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            String name = namesAndValues.get(i2);
            String value = namesAndValues.get(i2 + 1);
            if (i2 > 0) {
                out.append('&');
            }
            out.append(name);
            if (value != null) {
                out.append('=');
                out.append(value);
            }
        }
    }

    static List<String> C(String encodedQuery) {
        List<String> result = new ArrayList<>();
        int pos = 0;
        while (pos <= encodedQuery.length()) {
            int ampersandOffset = encodedQuery.indexOf(38, pos);
            if (ampersandOffset == -1) {
                ampersandOffset = encodedQuery.length();
            }
            int equalsOffset = encodedQuery.indexOf(61, pos);
            if (equalsOffset == -1 || equalsOffset > ampersandOffset) {
                result.add(encodedQuery.substring(pos, ampersandOffset));
                result.add((Object) null);
            } else {
                result.add(encodedQuery.substring(pos, equalsOffset));
                result.add(encodedQuery.substring(equalsOffset + 1, ampersandOffset));
            }
            pos = ampersandOffset + 1;
        }
        return result;
    }

    public String B() {
        if (this.h == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        s(result, this.h);
        return result.toString();
    }

    public String k() {
        if (this.i == null) {
            return null;
        }
        return this.j.substring(this.j.indexOf(35) + 1);
    }

    public q D(String link) {
        b builder = new b();
        if (builder.m(this, link) == b.a.SUCCESS) {
            return builder.a();
        }
        return null;
    }

    public b t() {
        b result = new b();
        result.a = this.b;
        result.b = p();
        result.c = l();
        result.d = this.e;
        result.e = this.f != i(this.b) ? this.f : -1;
        result.f.clear();
        result.f.addAll(n());
        result.h(o());
        result.h = k();
        return result;
    }

    public static q u(String url) {
        b builder = new b();
        if (builder.m((q) null, url) == b.a.SUCCESS) {
            return builder.a();
        }
        return null;
    }

    public boolean equals(Object o) {
        return (o instanceof q) && ((q) o).j.equals(this.j);
    }

    public int hashCode() {
        return this.j.hashCode();
    }

    public String toString() {
        return this.j;
    }

    /* compiled from: HttpUrl */
    public static final class b {
        String a;
        String b = "";
        String c = "";
        String d;
        int e = -1;
        final List<String> f;
        List<String> g;
        String h;

        /* compiled from: HttpUrl */
        public enum a {
            SUCCESS,
            MISSING_SCHEME,
            UNSUPPORTED_SCHEME,
            INVALID_PORT,
            INVALID_HOST
        }

        public b() {
            ArrayList arrayList = new ArrayList();
            this.f = arrayList;
            arrayList.add("");
        }

        public b u(String scheme) {
            if (scheme != null) {
                if (scheme.equalsIgnoreCase(l.DEFAULT_SCHEME_NAME)) {
                    this.a = l.DEFAULT_SCHEME_NAME;
                } else if (scheme.equalsIgnoreCase("https")) {
                    this.a = "https";
                } else {
                    throw new IllegalArgumentException("unexpected scheme: " + scheme);
                }
                return this;
            }
            throw new IllegalArgumentException("scheme == null");
        }

        public b i(String host) {
            if (host != null) {
                String encoded = b(host, 0, host.length());
                if (encoded != null) {
                    this.d = encoded;
                    return this;
                }
                throw new IllegalArgumentException("unexpected host: " + host);
            }
            throw new IllegalArgumentException("host == null");
        }

        public b p(int port) {
            if (port <= 0 || port > 65535) {
                throw new IllegalArgumentException("unexpected port: " + port);
            }
            this.e = port;
            return this;
        }

        /* access modifiers changed from: package-private */
        public int g() {
            int i = this.e;
            return i != -1 ? i : q.i(this.a);
        }

        public b h(String encodedQuery) {
            this.g = encodedQuery != null ? q.C(q.f(encodedQuery, " \"'<>#", true, true, true)) : null;
            return this;
        }

        /* access modifiers changed from: package-private */
        public b s() {
            int size = this.f.size();
            for (int i = 0; i < size; i++) {
                this.f.set(i, q.f(this.f.get(i), "[]", true, false, true));
            }
            List<String> list = this.g;
            if (list != null) {
                int size2 = list.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    String component = this.g.get(i2);
                    if (component != null) {
                        this.g.set(i2, q.f(component, "\\^`{|}", true, true, true));
                    }
                }
            }
            String str = this.h;
            if (str != null) {
                this.h = q.f(str, " \"#<>\\^`{|}", true, false, false);
            }
            return this;
        }

        public q a() {
            if (this.a == null) {
                throw new IllegalStateException("scheme == null");
            } else if (this.d != null) {
                return new q(this);
            } else {
                throw new IllegalStateException("host == null");
            }
        }

        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append(this.a);
            result.append("://");
            if (!this.b.isEmpty() || !this.c.isEmpty()) {
                result.append(this.b);
                if (!this.c.isEmpty()) {
                    result.append(':');
                    result.append(this.c);
                }
                result.append('@');
            }
            if (this.d.indexOf(58) != -1) {
                result.append('[');
                result.append(this.d);
                result.append(']');
            } else {
                result.append(this.d);
            }
            int effectivePort = g();
            if (effectivePort != q.i(this.a)) {
                result.append(':');
                result.append(effectivePort);
            }
            q.v(result, this.f);
            if (this.g != null) {
                result.append('?');
                q.s(result, this.g);
            }
            if (this.h != null) {
                result.append('#');
                result.append(this.h);
            }
            return result.toString();
        }

        /* access modifiers changed from: package-private */
        public a m(q base, String input) {
            int schemeDelimiterOffset;
            int i;
            char c2;
            int componentDelimiterOffset;
            String str;
            String str2 = input;
            int pos = w(str2, 0, input.length());
            int limit = x(str2, pos, input.length());
            int schemeDelimiterOffset2 = v(str2, pos, limit);
            int i2 = -1;
            if (schemeDelimiterOffset2 != -1) {
                if (input.regionMatches(true, pos, "https:", 0, 6)) {
                    this.a = "https";
                    pos += "https:".length();
                } else if (!input.regionMatches(true, pos, "http:", 0, 5)) {
                    return a.UNSUPPORTED_SCHEME;
                } else {
                    this.a = l.DEFAULT_SCHEME_NAME;
                    pos += "http:".length();
                }
            } else if (base == null) {
                return a.MISSING_SCHEME;
            } else {
                this.a = base.b;
            }
            int slashCount = y(str2, pos, limit);
            char c3 = '#';
            if (slashCount >= 2 || base == null || !base.b.equals(this.a)) {
                int pos2 = pos + slashCount;
                boolean hasUsername = false;
                boolean hasPassword = false;
                while (true) {
                    int componentDelimiterOffset2 = q.j(str2, pos2, limit, "@/\\?#");
                    switch (componentDelimiterOffset2 != limit ? str2.charAt(componentDelimiterOffset2) : i2) {
                        case -1:
                        case 35:
                        case 47:
                        case 63:
                        case 92:
                            int componentDelimiterOffset3 = componentDelimiterOffset2;
                            int i3 = pos2;
                            int i4 = schemeDelimiterOffset2;
                            int portColonOffset = q(str2, pos2, componentDelimiterOffset3);
                            if (portColonOffset + 1 < componentDelimiterOffset3) {
                                this.d = b(str2, pos2, portColonOffset);
                                int n = n(str2, portColonOffset + 1, componentDelimiterOffset3);
                                this.e = n;
                                if (n == -1) {
                                    return a.INVALID_PORT;
                                }
                            } else {
                                this.d = b(str2, pos2, portColonOffset);
                                this.e = q.i(this.a);
                            }
                            if (this.d != null) {
                                pos = componentDelimiterOffset3;
                                break;
                            } else {
                                return a.INVALID_HOST;
                            }
                        case 64:
                            if (!hasPassword) {
                                int passwordColonOffset = q.j(str2, pos2, componentDelimiterOffset2, ":");
                                int passwordColonOffset2 = passwordColonOffset;
                                String str3 = "%40";
                                int componentDelimiterOffset4 = componentDelimiterOffset2;
                                int i5 = pos2;
                                String canonicalUsername = q.e(input, pos2, passwordColonOffset, " \"':;<=>@[]^`{}|/\\?#", true, false, true);
                                if (hasUsername) {
                                    str = this.b + str3 + canonicalUsername;
                                } else {
                                    str = canonicalUsername;
                                }
                                this.b = str;
                                int componentDelimiterOffset5 = componentDelimiterOffset4;
                                if (passwordColonOffset2 != componentDelimiterOffset5) {
                                    hasPassword = true;
                                    String str4 = canonicalUsername;
                                    this.c = q.e(input, passwordColonOffset2 + 1, componentDelimiterOffset5, " \"':;<=>@[]^`{}|/\\?#", true, false, true);
                                }
                                hasUsername = true;
                                schemeDelimiterOffset = schemeDelimiterOffset2;
                                componentDelimiterOffset = componentDelimiterOffset5;
                            } else {
                                componentDelimiterOffset = componentDelimiterOffset2;
                                int pos3 = pos2;
                                StringBuilder sb = new StringBuilder();
                                sb.append(this.c);
                                sb.append("%40");
                                schemeDelimiterOffset = schemeDelimiterOffset2;
                                StringBuilder sb2 = sb;
                                sb2.append(q.e(input, pos3, componentDelimiterOffset, " \"':;<=>@[]^`{}|/\\?#", true, false, true));
                                this.c = sb2.toString();
                            }
                            pos2 = componentDelimiterOffset + 1;
                            c2 = '#';
                            i = -1;
                            continue;
                        default:
                            schemeDelimiterOffset = schemeDelimiterOffset2;
                            i = i2;
                            c2 = c3;
                            int i6 = componentDelimiterOffset2;
                            continue;
                    }
                    c3 = c2;
                    i2 = i;
                    schemeDelimiterOffset2 = schemeDelimiterOffset;
                }
            } else {
                this.b = base.p();
                this.c = base.l();
                this.d = base.e;
                this.e = base.f;
                this.f.clear();
                this.f.addAll(base.n());
                if (pos == limit || str2.charAt(pos) == '#') {
                    h(base.o());
                }
                int i7 = schemeDelimiterOffset2;
            }
            int pathDelimiterOffset = q.j(str2, pos, limit, "?#");
            t(str2, pos, pathDelimiterOffset);
            int pos4 = pathDelimiterOffset;
            if (pos4 < limit && str2.charAt(pos4) == '?') {
                int queryDelimiterOffset = q.j(str2, pos4, limit, "#");
                this.g = q.C(q.e(input, pos4 + 1, queryDelimiterOffset, " \"'<>#", true, true, true));
                pos4 = queryDelimiterOffset;
            }
            if (pos4 < limit && str2.charAt(pos4) == '#') {
                this.h = q.e(input, pos4 + 1, limit, "", true, false, false);
            }
            return a.SUCCESS;
        }

        private void t(String input, int pos, int limit) {
            if (pos != limit) {
                char c2 = input.charAt(pos);
                if (c2 == '/' || c2 == '\\') {
                    this.f.clear();
                    this.f.add("");
                    pos++;
                } else {
                    List<String> list = this.f;
                    list.set(list.size() - 1, "");
                }
                int i = pos;
                while (i < limit) {
                    int pathSegmentDelimiterOffset = q.j(input, i, limit, "/\\");
                    boolean segmentHasTrailingSlash = pathSegmentDelimiterOffset < limit;
                    r(input, i, pathSegmentDelimiterOffset, segmentHasTrailingSlash, true);
                    i = pathSegmentDelimiterOffset;
                    if (segmentHasTrailingSlash) {
                        i++;
                    }
                }
            }
        }

        private void r(String input, int pos, int limit, boolean addTrailingSlash, boolean alreadyEncoded) {
            String segment = q.e(input, pos, limit, " \"<>^`{}|/\\?#", alreadyEncoded, false, true);
            if (!k(segment)) {
                if (l(segment)) {
                    o();
                    return;
                }
                List<String> list = this.f;
                if (list.get(list.size() - 1).isEmpty()) {
                    List<String> list2 = this.f;
                    list2.set(list2.size() - 1, segment);
                } else {
                    this.f.add(segment);
                }
                if (addTrailingSlash) {
                    this.f.add("");
                }
            }
        }

        private boolean k(String input) {
            return input.equals(".") || input.equalsIgnoreCase("%2e");
        }

        private boolean l(String input) {
            return input.equals("..") || input.equalsIgnoreCase("%2e.") || input.equalsIgnoreCase(".%2e") || input.equalsIgnoreCase("%2e%2e");
        }

        private void o() {
            List<String> list = this.f;
            if (!list.remove(list.size() - 1).isEmpty() || this.f.isEmpty()) {
                this.f.add("");
                return;
            }
            List<String> list2 = this.f;
            list2.set(list2.size() - 1, "");
        }

        private int w(String input, int pos, int limit) {
            int i = pos;
            while (i < limit) {
                switch (input.charAt(i)) {
                    case 9:
                    case 10:
                    case 12:
                    case 13:
                    case ' ':
                        i++;
                    default:
                        return i;
                }
            }
            return limit;
        }

        private int x(String input, int pos, int limit) {
            int i = limit - 1;
            while (i >= pos) {
                switch (input.charAt(i)) {
                    case 9:
                    case 10:
                    case 12:
                    case 13:
                    case ' ':
                        i--;
                    default:
                        return i + 1;
                }
            }
            return pos;
        }

        private static int v(String input, int pos, int limit) {
            if (limit - pos < 2) {
                return -1;
            }
            char c0 = input.charAt(pos);
            if ((c0 < 'a' || c0 > 'z') && (c0 < 'A' || c0 > 'Z')) {
                return -1;
            }
            int i = pos + 1;
            while (i < limit) {
                char c2 = input.charAt(i);
                if ((c2 >= 'a' && c2 <= 'z') || ((c2 >= 'A' && c2 <= 'Z') || ((c2 >= '0' && c2 <= '9') || c2 == '+' || c2 == '-' || c2 == '.'))) {
                    i++;
                } else if (c2 == ':') {
                    return i;
                } else {
                    return -1;
                }
            }
            return -1;
        }

        private static int y(String input, int pos, int limit) {
            int slashCount = 0;
            while (pos < limit) {
                char c2 = input.charAt(pos);
                if (c2 != '\\' && c2 != '/') {
                    break;
                }
                slashCount++;
                pos++;
            }
            return slashCount;
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x000d, code lost:
            if (r0 >= r5) goto L_0x0019;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static int q(java.lang.String r3, int r4, int r5) {
            /*
                r0 = r4
            L_0x0001:
                if (r0 >= r5) goto L_0x001c
                char r1 = r3.charAt(r0)
                switch(r1) {
                    case 58: goto L_0x0018;
                    case 91: goto L_0x000b;
                    default: goto L_0x000a;
                }
            L_0x000a:
                goto L_0x0019
            L_0x000b:
                int r0 = r0 + 1
                if (r0 >= r5) goto L_0x0019
                char r1 = r3.charAt(r0)
                r2 = 93
                if (r1 != r2) goto L_0x000b
                goto L_0x0019
            L_0x0018:
                return r0
            L_0x0019:
                int r0 = r0 + 1
                goto L_0x0001
            L_0x001c:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.q.b.q(java.lang.String, int, int):int");
        }

        private static String b(String input, int pos, int limit) {
            String percentDecoded = q.w(input, pos, limit, false);
            if (!percentDecoded.startsWith(Constants.ARRAY_TYPE) || !percentDecoded.endsWith("]")) {
                return f(percentDecoded);
            }
            InetAddress inetAddress = e(percentDecoded, 1, percentDecoded.length() - 1);
            if (inetAddress == null) {
                return null;
            }
            byte[] address = inetAddress.getAddress();
            if (address.length == 16) {
                return j(address);
            }
            throw new AssertionError();
        }

        private static InetAddress e(String input, int pos, int limit) {
            byte[] address = new byte[16];
            int b2 = 0;
            int compress = -1;
            int groupOffset = -1;
            int i = pos;
            while (true) {
                if (i >= limit) {
                    break;
                } else if (b2 == address.length) {
                    return null;
                } else {
                    if (i + 2 <= limit && input.regionMatches(i, "::", 0, 2)) {
                        if (compress == -1) {
                            i += 2;
                            b2 += 2;
                            compress = b2;
                            if (i == limit) {
                                break;
                            }
                        } else {
                            return null;
                        }
                    } else if (b2 != 0) {
                        if (input.regionMatches(i, ":", 0, 1)) {
                            i++;
                        } else if (!input.regionMatches(i, ".", 0, 1) || !d(input, groupOffset, limit, address, b2 - 2)) {
                            return null;
                        } else {
                            b2 += 2;
                        }
                    }
                    int value = 0;
                    groupOffset = i;
                    while (i < limit) {
                        int hexDigit = q.h(input.charAt(i));
                        if (hexDigit == -1) {
                            break;
                        }
                        value = (value << 4) + hexDigit;
                        i++;
                    }
                    int groupLength = i - groupOffset;
                    if (groupLength == 0 || groupLength > 4) {
                        return null;
                    }
                    int b3 = b2 + 1;
                    address[b2] = (byte) ((value >>> 8) & 255);
                    b2 = b3 + 1;
                    address[b3] = (byte) (value & 255);
                }
            }
            if (b2 != address.length) {
                if (compress == -1) {
                    return null;
                }
                System.arraycopy(address, compress, address, address.length - (b2 - compress), b2 - compress);
                Arrays.fill(address, compress, (address.length - b2) + compress, (byte) 0);
            }
            try {
                return InetAddress.getByAddress(address);
            } catch (UnknownHostException e2) {
                throw new AssertionError();
            }
        }

        private static boolean d(String input, int pos, int limit, byte[] address, int addressOffset) {
            int b2 = addressOffset;
            int i = pos;
            while (i < limit) {
                if (b2 == address.length) {
                    return false;
                }
                if (b2 != addressOffset) {
                    if (input.charAt(i) != '.') {
                        return false;
                    }
                    i++;
                }
                int value = 0;
                int groupOffset = i;
                while (i < limit) {
                    char c2 = input.charAt(i);
                    if (c2 < '0' || c2 > '9') {
                        break;
                    } else if ((value == 0 && groupOffset != i) || ((value * 10) + c2) - 48 > 255) {
                        return false;
                    } else {
                        i++;
                    }
                }
                if (i - groupOffset == 0) {
                    return false;
                }
                address[b2] = (byte) value;
                b2++;
            }
            if (b2 != addressOffset + 4) {
                return false;
            }
            return true;
        }

        private static String f(String input) {
            try {
                String result = IDN.toASCII(input).toLowerCase(Locale.US);
                if (!result.isEmpty() && !c(result)) {
                    return result;
                }
                return null;
            } catch (IllegalArgumentException e2) {
                return null;
            }
        }

        private static boolean c(String hostnameAscii) {
            for (int i = 0; i < hostnameAscii.length(); i++) {
                char c2 = hostnameAscii.charAt(i);
                if (c2 <= 31 || c2 >= 127 || " #%/:?@[\\]".indexOf(c2) != -1) {
                    return true;
                }
            }
            return false;
        }

        private static String j(byte[] address) {
            int longestRunOffset = -1;
            int longestRunLength = 0;
            int i = 0;
            while (i < address.length) {
                int currentRunOffset = i;
                while (i < 16 && address[i] == 0 && address[i + 1] == 0) {
                    i += 2;
                }
                int currentRunLength = i - currentRunOffset;
                if (currentRunLength > longestRunLength) {
                    longestRunOffset = currentRunOffset;
                    longestRunLength = currentRunLength;
                }
                i += 2;
            }
            c result = new c();
            int i2 = 0;
            while (i2 < address.length) {
                if (i2 == longestRunOffset) {
                    result.writeByte(58);
                    i2 += longestRunLength;
                    if (i2 == 16) {
                        result.writeByte(58);
                    }
                } else {
                    if (i2 > 0) {
                        result.writeByte(58);
                    }
                    result.writeHexadecimalUnsignedLong((long) (((address[i2] & 255) << 8) | (address[i2 + 1] & 255)));
                    i2 += 2;
                }
            }
            return result.a1();
        }

        private static int n(String input, int pos, int limit) {
            try {
                int i = Integer.parseInt(q.e(input, pos, limit, "", false, false, true));
                if (i <= 0 || i > 65535) {
                    return -1;
                }
                return i;
            } catch (NumberFormatException e2) {
                return -1;
            }
        }
    }

    /* access modifiers changed from: private */
    public static int j(String input, int pos, int limit, String delimiters) {
        for (int i2 = pos; i2 < limit; i2++) {
            if (delimiters.indexOf(input.charAt(i2)) != -1) {
                return i2;
            }
        }
        return limit;
    }

    static String x(String encoded, boolean plusIsSpace) {
        return w(encoded, 0, encoded.length(), plusIsSpace);
    }

    private List<String> y(List<String> list, boolean plusIsSpace) {
        List<String> result = new ArrayList<>(list.size());
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            result.add(s != null ? x(s, plusIsSpace) : null);
        }
        return Collections.unmodifiableList(result);
    }

    static String w(String encoded, int pos, int limit, boolean plusIsSpace) {
        for (int i2 = pos; i2 < limit; i2++) {
            char c2 = encoded.charAt(i2);
            if (c2 == '%' || (c2 == '+' && plusIsSpace)) {
                c out = new c();
                out.writeUtf8(encoded, pos, i2);
                z(out, encoded, i2, limit, plusIsSpace);
                return out.a1();
            }
        }
        return encoded.substring(pos, limit);
    }

    static void z(c out, String encoded, int pos, int limit, boolean plusIsSpace) {
        int i2 = pos;
        while (i2 < limit) {
            int codePoint = encoded.codePointAt(i2);
            if (codePoint == 37 && i2 + 2 < limit) {
                int d1 = h(encoded.charAt(i2 + 1));
                int d2 = h(encoded.charAt(i2 + 2));
                if (!(d1 == -1 || d2 == -1)) {
                    out.writeByte((d1 << 4) + d2);
                    i2 += 2;
                    i2 += Character.charCount(codePoint);
                }
            } else if (codePoint == 43 && plusIsSpace) {
                out.writeByte(32);
                i2 += Character.charCount(codePoint);
            }
            out.writeUtf8CodePoint(codePoint);
            i2 += Character.charCount(codePoint);
        }
    }

    static int h(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - '0';
        }
        if (c2 >= 'a' && c2 <= 'f') {
            return (c2 - 'a') + 10;
        }
        if (c2 < 'A' || c2 > 'F') {
            return -1;
        }
        return (c2 - 'A') + 10;
    }

    static String e(String input, int pos, int limit, String encodeSet, boolean alreadyEncoded, boolean plusIsSpace, boolean asciiOnly) {
        String str = input;
        int i2 = pos;
        while (i2 < limit) {
            int codePoint = input.codePointAt(i2);
            if (codePoint < 32 || codePoint == 127) {
                String str2 = encodeSet;
            } else if (codePoint >= 128 && asciiOnly) {
                String str3 = encodeSet;
            } else if (encodeSet.indexOf(codePoint) == -1 && ((codePoint != 37 || alreadyEncoded) && (codePoint != 43 || !plusIsSpace))) {
                i2 += Character.charCount(codePoint);
            }
            c cVar = new c();
            c out = cVar;
            out.writeUtf8(input, pos, i2);
            g(cVar, input, i2, limit, encodeSet, alreadyEncoded, plusIsSpace, asciiOnly);
            return out.a1();
        }
        int i3 = pos;
        String str4 = encodeSet;
        return input.substring(pos, limit);
    }

    static void g(c out, String input, int pos, int limit, String encodeSet, boolean alreadyEncoded, boolean plusIsSpace, boolean asciiOnly) {
        c utf8Buffer = null;
        int i2 = pos;
        while (i2 < limit) {
            int codePoint = input.codePointAt(i2);
            if (!alreadyEncoded || !(codePoint == 9 || codePoint == 10 || codePoint == 12 || codePoint == 13)) {
                if (codePoint == 43 && plusIsSpace) {
                    out.writeUtf8(alreadyEncoded ? e.ANY_NON_NULL_MARKER : "%2B");
                } else if (codePoint < 32 || codePoint == 127 || ((codePoint >= 128 && asciiOnly) || encodeSet.indexOf(codePoint) != -1 || (codePoint == 37 && !alreadyEncoded))) {
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

    static String f(String input, String encodeSet, boolean alreadyEncoded, boolean plusIsSpace, boolean asciiOnly) {
        return e(input, 0, input.length(), encodeSet, alreadyEncoded, plusIsSpace, asciiOnly);
    }
}
