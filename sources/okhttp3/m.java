package okhttp3;

import coil.decode.l;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.j;
import kotlin.text.w;
import kotlin.text.x;
import okhttp3.internal.http.c;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Cookie.kt */
public final class m {
    /* access modifiers changed from: private */
    public static final Pattern a = Pattern.compile("(\\d{2,4})[^\\d]*");
    /* access modifiers changed from: private */
    public static final Pattern b = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
    /* access modifiers changed from: private */
    public static final Pattern c = Pattern.compile("(\\d{1,2})[^\\d]*");
    /* access modifiers changed from: private */
    public static final Pattern d = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
    public static final b e = new b((DefaultConstructorMarker) null);
    @NotNull
    private final String f;
    @NotNull
    private final String g;
    private final long h;
    @NotNull
    private final String i;
    @NotNull
    private final String j;
    private final boolean k;
    private final boolean l;
    private final boolean m;
    private final boolean n;

    private m(String name, String value, long expiresAt, String domain, String path, boolean secure, boolean httpOnly, boolean persistent, boolean hostOnly) {
        this.f = name;
        this.g = value;
        this.h = expiresAt;
        this.i = domain;
        this.j = path;
        this.k = secure;
        this.l = httpOnly;
        this.m = persistent;
        this.n = hostOnly;
    }

    public /* synthetic */ m(String name, String value, long expiresAt, String domain, String path, boolean secure, boolean httpOnly, boolean persistent, boolean hostOnly, DefaultConstructorMarker $constructor_marker) {
        this(name, value, expiresAt, domain, path, secure, httpOnly, persistent, hostOnly);
    }

    @NotNull
    public final String i() {
        return this.f;
    }

    @NotNull
    public final String n() {
        return this.g;
    }

    public final long f() {
        return this.h;
    }

    @NotNull
    public final String e() {
        return this.i;
    }

    @NotNull
    public final String j() {
        return this.j;
    }

    public final boolean l() {
        return this.k;
    }

    public final boolean h() {
        return this.l;
    }

    public final boolean k() {
        return this.m;
    }

    public final boolean g() {
        return this.n;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof m) && k.a(((m) other).f, this.f) && k.a(((m) other).g, this.g) && ((m) other).h == this.h && k.a(((m) other).i, this.i) && k.a(((m) other).j, this.j) && ((m) other).k == this.k && ((m) other).l == this.l && ((m) other).m == this.m && ((m) other).n == this.n;
    }

    @IgnoreJRERequirement
    public int hashCode() {
        return (((((((((((((((((17 * 31) + this.f.hashCode()) * 31) + this.g.hashCode()) * 31) + com.google.chip.chiptool.setuppayloadscanner.a.a(this.h)) * 31) + this.i.hashCode()) * 31) + this.j.hashCode()) * 31) + l.a(this.k)) * 31) + l.a(this.l)) * 31) + l.a(this.m)) * 31) + l.a(this.n);
    }

    @NotNull
    public String toString() {
        return m(false);
    }

    @NotNull
    public final String m(boolean forObsoleteRfc2965) {
        StringBuilder $this$buildString = new StringBuilder();
        $this$buildString.append(this.f);
        $this$buildString.append('=');
        $this$buildString.append(this.g);
        if (this.m) {
            if (this.h == Long.MIN_VALUE) {
                $this$buildString.append("; max-age=0");
            } else {
                $this$buildString.append("; expires=");
                $this$buildString.append(c.b(new Date(this.h)));
            }
        }
        if (!this.n) {
            $this$buildString.append("; domain=");
            if (forObsoleteRfc2965) {
                $this$buildString.append(".");
            }
            $this$buildString.append(this.i);
        }
        $this$buildString.append("; path=");
        $this$buildString.append(this.j);
        if (this.k) {
            $this$buildString.append("; secure");
        }
        if (this.l) {
            $this$buildString.append("; httponly");
        }
        String sb = $this$buildString.toString();
        k.b(sb, "toString()");
        return sb;
    }

    /* compiled from: Cookie.kt */
    public static final class a {
        private String a;
        private String b;
        private long c = 253402300799999L;
        private String d;
        private String e = "/";
        private boolean f;
        private boolean g;
        private boolean h;
        private boolean i;

        @NotNull
        public final a g(@NotNull String name) {
            k.f(name, "name");
            if (k.a(x.e1(name).toString(), name)) {
                this.a = name;
                return this;
            }
            throw new IllegalArgumentException("name is not trimmed".toString());
        }

        @NotNull
        public final a j(@NotNull String value) {
            k.f(value, "value");
            if (k.a(x.e1(value).toString(), value)) {
                this.b = value;
                return this;
            }
            throw new IllegalArgumentException("value is not trimmed".toString());
        }

        @NotNull
        public final a d(long expiresAt) {
            long expiresAt2 = expiresAt;
            if (expiresAt2 <= 0) {
                expiresAt2 = Long.MIN_VALUE;
            }
            if (expiresAt2 > 253402300799999L) {
                expiresAt2 = 253402300799999L;
            }
            this.c = expiresAt2;
            this.h = true;
            return this;
        }

        @NotNull
        public final a b(@NotNull String domain) {
            k.f(domain, SerializableCookie.DOMAIN);
            return c(domain, false);
        }

        @NotNull
        public final a e(@NotNull String domain) {
            k.f(domain, SerializableCookie.DOMAIN);
            return c(domain, true);
        }

        private final a c(String domain, boolean hostOnly) {
            String canonicalDomain = okhttp3.internal.a.e(domain);
            if (canonicalDomain != null) {
                this.d = canonicalDomain;
                this.i = hostOnly;
                return this;
            }
            throw new IllegalArgumentException("unexpected domain: " + domain);
        }

        @NotNull
        public final a h(@NotNull String path) {
            k.f(path, "path");
            if (w.N(path, "/", false, 2, (Object) null)) {
                this.e = path;
                return this;
            }
            throw new IllegalArgumentException("path must start with '/'".toString());
        }

        @NotNull
        public final a i() {
            this.f = true;
            return this;
        }

        @NotNull
        public final a f() {
            this.g = true;
            return this;
        }

        @NotNull
        public final m a() {
            String str = this.a;
            if (str != null) {
                String str2 = this.b;
                if (str2 != null) {
                    long j = this.c;
                    String str3 = this.d;
                    if (str3 != null) {
                        return new m(str, str2, j, str3, this.e, this.f, this.g, this.h, this.i, (DefaultConstructorMarker) null);
                    }
                    throw new NullPointerException("builder.domain == null");
                }
                throw new NullPointerException("builder.value == null");
            }
            throw new NullPointerException("builder.name == null");
        }
    }

    /* compiled from: Cookie.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private final boolean b(String urlHost, String domain) {
            if (k.a(urlHost, domain)) {
                return true;
            }
            if (!w.x(urlHost, domain, false, 2, (Object) null) || urlHost.charAt((urlHost.length() - domain.length()) - 1) != '.' || okhttp3.internal.b.f(urlHost)) {
                return false;
            }
            return true;
        }

        @Nullable
        public final m c(@NotNull v url, @NotNull String setCookie) {
            k.f(url, "url");
            k.f(setCookie, "setCookie");
            return d(System.currentTimeMillis(), url, setCookie);
        }

        @Nullable
        public final m d(long currentTimeMillis, @NotNull v url, @NotNull String setCookie) {
            long expiresAt;
            String encodedPath;
            long deltaMilliseconds;
            String str;
            int limit;
            boolean z;
            String str2 = setCookie;
            k.f(url, "url");
            k.f(str2, "setCookie");
            String str3 = setCookie;
            int cookiePairEnd = okhttp3.internal.b.o(str3, ';', 0, 0, 6, (Object) null);
            int pairEqualsSign = okhttp3.internal.b.o(str3, '=', 0, cookiePairEnd, 2, (Object) null);
            if (pairEqualsSign == cookiePairEnd) {
                return null;
            }
            String cookieName = okhttp3.internal.b.W(str2, 0, pairEqualsSign, 1, (Object) null);
            if (cookieName.length() == 0) {
                return null;
            } else if (okhttp3.internal.b.v(cookieName) != -1) {
                int i = pairEqualsSign;
                return null;
            } else {
                String cookieValue = okhttp3.internal.b.V(str2, pairEqualsSign + 1, cookiePairEnd);
                if (okhttp3.internal.b.v(cookieValue) != -1) {
                    return null;
                }
                int limit2 = setCookie.length();
                long deltaSeconds = -1;
                boolean secureOnly = false;
                boolean httpOnly = false;
                boolean hostOnly = true;
                boolean persistent = false;
                int pos = cookiePairEnd + 1;
                long expiresAt2 = 253402300799999L;
                String path = null;
                String domain = null;
                while (pos < limit2) {
                    int attributePairEnd = okhttp3.internal.b.m(str2, ';', pos, limit2);
                    int attributeEqualsSign = okhttp3.internal.b.m(str2, '=', pos, attributePairEnd);
                    String attributeName = okhttp3.internal.b.V(str2, pos, attributeEqualsSign);
                    if (attributeEqualsSign < attributePairEnd) {
                        str = okhttp3.internal.b.V(str2, attributeEqualsSign + 1, attributePairEnd);
                    } else {
                        str = "";
                    }
                    String attributeValue = str;
                    int pairEqualsSign2 = pairEqualsSign;
                    if (w.y(attributeName, "expires", true)) {
                        try {
                            limit = limit2;
                            try {
                                expiresAt2 = g(attributeValue, 0, attributeValue.length());
                                persistent = true;
                                z = true;
                            } catch (IllegalArgumentException e) {
                                z = true;
                                pos = attributePairEnd + 1;
                                boolean z2 = z;
                                pairEqualsSign = pairEqualsSign2;
                                limit2 = limit;
                            }
                        } catch (IllegalArgumentException e2) {
                            limit = limit2;
                            String str4 = attributeValue;
                            z = true;
                            pos = attributePairEnd + 1;
                            boolean z22 = z;
                            pairEqualsSign = pairEqualsSign2;
                            limit2 = limit;
                        }
                    } else {
                        limit = limit2;
                        String attributeValue2 = attributeValue;
                        if (w.y(attributeName, "max-age", true)) {
                            try {
                                persistent = true;
                                deltaSeconds = h(attributeValue2);
                                z = true;
                            } catch (NumberFormatException e3) {
                                z = true;
                            }
                        } else if (w.y(attributeName, SerializableCookie.DOMAIN, true)) {
                            try {
                                domain = f(attributeValue2);
                                hostOnly = false;
                                z = true;
                            } catch (IllegalArgumentException e4) {
                                z = true;
                            }
                        } else {
                            z = true;
                            if (w.y(attributeName, "path", true)) {
                                path = attributeValue2;
                            } else if (w.y(attributeName, "secure", true)) {
                                secureOnly = true;
                            } else if (w.y(attributeName, "httponly", true)) {
                                httpOnly = true;
                            }
                        }
                    }
                    pos = attributePairEnd + 1;
                    boolean z222 = z;
                    pairEqualsSign = pairEqualsSign2;
                    limit2 = limit;
                }
                int i2 = limit2;
                if (deltaSeconds == Long.MIN_VALUE) {
                    expiresAt = Long.MIN_VALUE;
                } else if (deltaSeconds != -1) {
                    if (deltaSeconds <= 9223372036854775L) {
                        deltaMilliseconds = ((long) 1000) * deltaSeconds;
                    } else {
                        deltaMilliseconds = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
                    }
                    long expiresAt3 = currentTimeMillis + deltaMilliseconds;
                    expiresAt = (expiresAt3 < currentTimeMillis || expiresAt3 > 253402300799999L) ? 253402300799999L : expiresAt3;
                } else {
                    expiresAt = expiresAt2;
                }
                String urlHost = url.j();
                if (domain == null) {
                    domain = urlHost;
                } else if (!b(urlHost, domain)) {
                    return null;
                }
                if (urlHost.length() != domain.length() && PublicSuffixDatabase.d.c().c(domain) == null) {
                    return null;
                }
                String path2 = "/";
                if (path == null || !w.N(path, path2, false, 2, (Object) null)) {
                    String encodedPath2 = url.e();
                    int lastSlash = x.j0(encodedPath2, '/', 0, false, 6, (Object) null);
                    if (lastSlash != 0) {
                        if (encodedPath2 != null) {
                            path2 = encodedPath2.substring(0, lastSlash);
                            k.b(path2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                    }
                    encodedPath = path2;
                } else {
                    encodedPath = path;
                }
                int i3 = pos;
                return new m(cookieName, cookieValue, expiresAt, domain, encodedPath, secureOnly, httpOnly, persistent, hostOnly, (DefaultConstructorMarker) null);
            }
        }

        private final long g(String s, int pos, int limit) {
            String str = s;
            int i = limit;
            int pos2 = a(str, pos, i, false);
            int minute = -1;
            int minute2 = -1;
            int second = -1;
            int dayOfMonth = -1;
            int month = -1;
            int year = -1;
            Matcher matcher = m.d.matcher(str);
            while (pos2 < i) {
                int end = a(str, pos2 + 1, i, true);
                matcher.region(pos2, end);
                if (minute == -1 && matcher.usePattern(m.d).matches()) {
                    String group = matcher.group(1);
                    k.b(group, "matcher.group(1)");
                    int hour = Integer.parseInt(group);
                    String group2 = matcher.group(2);
                    k.b(group2, "matcher.group(2)");
                    int minute3 = Integer.parseInt(group2);
                    String group3 = matcher.group(3);
                    k.b(group3, "matcher.group(3)");
                    second = Integer.parseInt(group3);
                    minute2 = minute3;
                    minute = hour;
                } else if (dayOfMonth == -1 && matcher.usePattern(m.c).matches()) {
                    String group4 = matcher.group(1);
                    k.b(group4, "matcher.group(1)");
                    dayOfMonth = Integer.parseInt(group4);
                } else if (month == -1 && matcher.usePattern(m.b).matches()) {
                    String group5 = matcher.group(1);
                    k.b(group5, "matcher.group(1)");
                    Locale locale = Locale.US;
                    k.b(locale, "Locale.US");
                    if (group5 != null) {
                        String lowerCase = group5.toLowerCase(locale);
                        k.b(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
                        String monthString = lowerCase;
                        String pattern = m.b.pattern();
                        k.b(pattern, "MONTH_PATTERN.pattern()");
                        month = x.f0(pattern, monthString, 0, false, 6, (Object) null) / 4;
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                } else if (year == -1 && matcher.usePattern(m.a).matches()) {
                    String group6 = matcher.group(1);
                    k.b(group6, "matcher.group(1)");
                    year = Integer.parseInt(group6);
                }
                pos2 = a(str, end + 1, i, false);
            }
            if (70 <= year && 99 >= year) {
                year += 1900;
            }
            if (year >= 0 && 69 >= year) {
                year += WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS;
            }
            if (year >= 1601) {
                if (month != -1) {
                    if (1 <= dayOfMonth && 31 >= dayOfMonth) {
                        if (minute >= 0 && 23 >= minute) {
                            if (minute2 >= 0 && 59 >= minute2) {
                                if (second >= 0 && 59 >= second) {
                                    GregorianCalendar $this$apply = new GregorianCalendar(okhttp3.internal.b.f);
                                    $this$apply.setLenient(false);
                                    $this$apply.set(1, year);
                                    $this$apply.set(2, month - 1);
                                    $this$apply.set(5, dayOfMonth);
                                    $this$apply.set(11, minute);
                                    $this$apply.set(12, minute2);
                                    $this$apply.set(13, second);
                                    $this$apply.set(14, 0);
                                    return $this$apply.getTimeInMillis();
                                }
                                throw new IllegalArgumentException("Failed requirement.".toString());
                            }
                            throw new IllegalArgumentException("Failed requirement.".toString());
                        }
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        private final int a(String input, int pos, int limit, boolean invert) {
            for (int i = pos; i < limit; i++) {
                int c = input.charAt(i);
                if (((c < 32 && c != 9) || c >= 127 || (48 <= c && 57 >= c) || ((97 <= c && 122 >= c) || ((65 <= c && 90 >= c) || c == 58))) == (!invert)) {
                    return i;
                }
            }
            return limit;
        }

        private final long h(String s) {
            try {
                long parsed = Long.parseLong(s);
                if (parsed <= 0) {
                    return Long.MIN_VALUE;
                }
                return parsed;
            } catch (NumberFormatException e) {
                if (!new j("-?\\d+").matches(s)) {
                    throw e;
                } else if (w.N(s, "-", false, 2, (Object) null)) {
                    return Long.MIN_VALUE;
                } else {
                    return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
                }
            }
        }

        private final String f(String s) {
            if (!w.x(s, ".", false, 2, (Object) null)) {
                String e = okhttp3.internal.a.e(x.w0(s, "."));
                if (e != null) {
                    return e;
                }
                throw new IllegalArgumentException();
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        @NotNull
        public final List<m> e(@NotNull v url, @NotNull u headers) {
            k.f(url, "url");
            k.f(headers, "headers");
            List cookieStrings = headers.i(HttpHeaders.HEAD_KEY_SET_COOKIE);
            List cookies = null;
            int size = cookieStrings.size();
            for (int i = 0; i < size; i++) {
                m cookie = c(url, cookieStrings.get(i));
                if (cookie != null) {
                    if (cookies == null) {
                        cookies = new ArrayList();
                    }
                    cookies.add(cookie);
                }
            }
            if (cookies == null) {
                return q.g();
            }
            List<m> unmodifiableList = Collections.unmodifiableList(cookies);
            k.b(unmodifiableList, "Collections.unmodifiableList(cookies)");
            return unmodifiableList;
        }
    }
}
