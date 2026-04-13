package okhttp3.internal;

import androidx.core.app.NotificationCompat;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import kotlin.TypeCastException;
import kotlin.collections.g0;
import kotlin.collections.l;
import kotlin.collections.l0;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import kotlin.text.c;
import kotlin.text.j;
import okhttp3.c0;
import okhttp3.e0;
import okhttp3.r;
import okhttp3.u;
import okhttp3.v;
import okhttp3.x;
import okhttp3.z;
import okio.d;
import okio.e;
import okio.f;
import okio.s;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: Util.kt */
public final class b {
    @NotNull
    public static final byte[] a;
    @NotNull
    public static final u b = u.c.g(new String[0]);
    @NotNull
    public static final e0 c;
    @NotNull
    public static final c0 d;
    private static final s e;
    @NotNull
    public static final TimeZone f;
    private static final j g = new j("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
    public static final boolean h = false;
    @NotNull
    public static final String i;

    static {
        byte[] bArr = new byte[0];
        a = bArr;
        c = e0.b.i(e0.Companion, bArr, (x) null, 1, (Object) null);
        d = c0.a.k(c0.Companion, bArr, (x) null, 0, 0, 7, (Object) null);
        s.a aVar = s.d;
        f.a aVar2 = f.Companion;
        e = aVar.d(aVar2.b("efbbbf"), aVar2.b("feff"), aVar2.b("fffe"), aVar2.b("0000ffff"), aVar2.b("ffff0000"));
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        if (timeZone == null) {
            k.n();
        }
        f = timeZone;
        Class<z> cls = z.class;
        String name = z.class.getName();
        k.b(name, "OkHttpClient::class.java.name");
        i = kotlin.text.x.x0(kotlin.text.x.w0(name, "okhttp3."), "Client");
    }

    public static final void i(long arrayLength, long offset, long count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /* renamed from: okhttp3.internal.b$b  reason: collision with other inner class name */
    /* compiled from: Util.kt */
    public static final class C0459b implements ThreadFactory {
        final /* synthetic */ String c;
        final /* synthetic */ boolean d;

        C0459b(String str, boolean z) {
            this.c = str;
            this.d = z;
        }

        @NotNull
        public final Thread newThread(Runnable runnable) {
            Thread $this$apply = new Thread(runnable, this.c);
            $this$apply.setDaemon(this.d);
            return $this$apply;
        }
    }

    @NotNull
    public static final ThreadFactory K(@NotNull String name, boolean daemon) {
        k.f(name, "name");
        return new C0459b(name, daemon);
    }

    @NotNull
    public static final String[] B(@NotNull String[] $this$intersect, @NotNull String[] other, @NotNull Comparator<? super String> comparator) {
        k.f($this$intersect, "$this$intersect");
        k.f(other, "other");
        k.f(comparator, "comparator");
        ArrayList thisCollection$iv = new ArrayList();
        for (String a2 : $this$intersect) {
            int length = other.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                } else if (comparator.compare(a2, other[i2]) == 0) {
                    thisCollection$iv.add(a2);
                    break;
                } else {
                    i2++;
                }
            }
        }
        Object[] array = thisCollection$iv.toArray(new String[0]);
        if (array != null) {
            return (String[]) array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    public static final boolean r(@NotNull String[] $this$hasIntersection, @Nullable String[] other, @NotNull Comparator<? super String> comparator) {
        k.f($this$hasIntersection, "$this$hasIntersection");
        k.f(comparator, "comparator");
        if (!($this$hasIntersection.length == 0) && other != null) {
            if (!(other.length == 0)) {
                for (String a2 : $this$hasIntersection) {
                    for (String b2 : other) {
                        if (comparator.compare(a2, b2) == 0) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

    public static /* synthetic */ String Q(v vVar, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z = false;
        }
        return P(vVar, z);
    }

    @NotNull
    public static final String P(@NotNull v $this$toHostHeader, boolean includeDefaultPort) {
        String host;
        k.f($this$toHostHeader, "$this$toHostHeader");
        if (kotlin.text.x.S($this$toHostHeader.j(), ":", false, 2, (Object) null)) {
            host = '[' + $this$toHostHeader.j() + ']';
        } else {
            host = $this$toHostHeader.j();
        }
        if (!includeDefaultPort && $this$toHostHeader.p() == v.b.c($this$toHostHeader.t())) {
            return host;
        }
        return host + ':' + $this$toHostHeader.p();
    }

    public static final int u(@NotNull String[] $this$indexOf, @NotNull String value, @NotNull Comparator<String> comparator) {
        k.f($this$indexOf, "$this$indexOf");
        k.f(value, "value");
        k.f(comparator, "comparator");
        String[] $this$indexOfFirst$iv = $this$indexOf;
        int length = $this$indexOfFirst$iv.length;
        for (int index$iv = 0; index$iv < length; index$iv++) {
            if (comparator.compare($this$indexOfFirst$iv[index$iv], value) == 0) {
                return index$iv;
            }
        }
        return -1;
    }

    @NotNull
    public static final String[] l(@NotNull String[] $this$concat, @NotNull String value) {
        k.f($this$concat, "$this$concat");
        k.f(value, "value");
        Object[] copyOf = Arrays.copyOf($this$concat, $this$concat.length + 1);
        k.b(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        String[] result = (String[]) copyOf;
        result[l.y(result)] = value;
        return result;
    }

    public static /* synthetic */ int x(String str, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = str.length();
        }
        return w(str, i2, i3);
    }

    public static final int w(@NotNull String $this$indexOfFirstNonAsciiWhitespace, int startIndex, int endIndex) {
        k.f($this$indexOfFirstNonAsciiWhitespace, "$this$indexOfFirstNonAsciiWhitespace");
        int i2 = startIndex;
        while (i2 < endIndex) {
            switch ($this$indexOfFirstNonAsciiWhitespace.charAt(i2)) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    i2++;
                default:
                    return i2;
            }
        }
        return endIndex;
    }

    public static /* synthetic */ int z(String str, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = str.length();
        }
        return y(str, i2, i3);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final int y(@org.jetbrains.annotations.NotNull java.lang.String r2, int r3, int r4) {
        /*
            java.lang.String r0 = "$this$indexOfLastNonAsciiWhitespace"
            kotlin.jvm.internal.k.f(r2, r0)
            int r0 = r4 + -1
            if (r0 < r3) goto L_0x001a
        L_0x0009:
            char r1 = r2.charAt(r0)
            switch(r1) {
                case 9: goto L_0x0013;
                case 10: goto L_0x0013;
                case 12: goto L_0x0013;
                case 13: goto L_0x0013;
                case 32: goto L_0x0013;
                default: goto L_0x0010;
            }
        L_0x0010:
            int r1 = r0 + 1
            return r1
        L_0x0013:
            if (r0 == r3) goto L_0x001a
            int r0 = r0 + -1
            goto L_0x0009
        L_0x001a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.b.y(java.lang.String, int, int):int");
    }

    public static /* synthetic */ String W(String str, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = str.length();
        }
        return V(str, i2, i3);
    }

    @NotNull
    public static final String V(@NotNull String $this$trimSubstring, int startIndex, int endIndex) {
        k.f($this$trimSubstring, "$this$trimSubstring");
        int start = w($this$trimSubstring, startIndex, endIndex);
        String substring = $this$trimSubstring.substring(start, y($this$trimSubstring, start, endIndex));
        k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static final int n(@NotNull String $this$delimiterOffset, @NotNull String delimiters, int startIndex, int endIndex) {
        k.f($this$delimiterOffset, "$this$delimiterOffset");
        k.f(delimiters, "delimiters");
        for (int i2 = startIndex; i2 < endIndex; i2++) {
            if (kotlin.text.x.R(delimiters, $this$delimiterOffset.charAt(i2), false, 2, (Object) null)) {
                return i2;
            }
        }
        return endIndex;
    }

    public static /* synthetic */ int o(String str, char c2, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = str.length();
        }
        return m(str, c2, i2, i3);
    }

    public static final int m(@NotNull String $this$delimiterOffset, char delimiter, int startIndex, int endIndex) {
        k.f($this$delimiterOffset, "$this$delimiterOffset");
        for (int i2 = startIndex; i2 < endIndex; i2++) {
            if ($this$delimiterOffset.charAt(i2) == delimiter) {
                return i2;
            }
        }
        return endIndex;
    }

    public static final int v(@NotNull String $this$indexOfControlOrNonAscii) {
        k.f($this$indexOfControlOrNonAscii, "$this$indexOfControlOrNonAscii");
        int length = $this$indexOfControlOrNonAscii.length();
        for (int i2 = 0; i2 < length; i2++) {
            char c2 = $this$indexOfControlOrNonAscii.charAt(i2);
            if (c2 <= 31 || c2 >= 127) {
                return i2;
            }
        }
        return -1;
    }

    public static final boolean f(@NotNull String $this$canParseAsIpAddress) {
        k.f($this$canParseAsIpAddress, "$this$canParseAsIpAddress");
        return g.matches($this$canParseAsIpAddress);
    }

    @NotNull
    public static final String q(@NotNull String format, @NotNull Object... args) {
        k.f(format, IjkMediaMeta.IJKM_KEY_FORMAT);
        k.f(args, "args");
        d0 d0Var = d0.a;
        Locale locale = Locale.US;
        k.b(locale, "Locale.US");
        Object[] copyOf = Arrays.copyOf(args, args.length);
        String format2 = String.format(locale, format, Arrays.copyOf(copyOf, copyOf.length));
        k.b(format2, "java.lang.String.format(locale, format, *args)");
        return format2;
    }

    @NotNull
    public static final Charset F(@NotNull e $this$readBomAsCharset, @NotNull Charset charset) {
        k.f($this$readBomAsCharset, "$this$readBomAsCharset");
        k.f(charset, "default");
        switch ($this$readBomAsCharset.Z0(e)) {
            case -1:
                return charset;
            case 0:
                Charset charset2 = StandardCharsets.UTF_8;
                k.b(charset2, "UTF_8");
                return charset2;
            case 1:
                Charset charset3 = StandardCharsets.UTF_16BE;
                k.b(charset3, "UTF_16BE");
                return charset3;
            case 2:
                Charset charset4 = StandardCharsets.UTF_16LE;
                k.b(charset4, "UTF_16LE");
                return charset4;
            case 3:
                return c.i.a();
            case 4:
                return c.i.b();
            default:
                throw new AssertionError();
        }
    }

    public static final int h(@NotNull String name, long duration, @Nullable TimeUnit unit) {
        k.f(name, "name");
        boolean z = true;
        if (duration >= 0) {
            if (unit != null) {
                long millis = unit.toMillis(duration);
                if (millis <= ((long) Integer.MAX_VALUE)) {
                    if (millis == 0 && duration > 0) {
                        z = false;
                    }
                    if (z) {
                        return (int) millis;
                    }
                    throw new IllegalArgumentException((name + " too small.").toString());
                }
                throw new IllegalArgumentException((name + " too large.").toString());
            }
            throw new IllegalStateException("unit == null".toString());
        }
        throw new IllegalStateException((name + " < 0").toString());
    }

    public static final int E(char $this$parseHexDigit) {
        if ('0' <= $this$parseHexDigit && '9' >= $this$parseHexDigit) {
            return $this$parseHexDigit - '0';
        }
        if ('a' <= $this$parseHexDigit && 'f' >= $this$parseHexDigit) {
            return ($this$parseHexDigit - 'a') + 10;
        }
        if ('A' <= $this$parseHexDigit && 'F' >= $this$parseHexDigit) {
            return ($this$parseHexDigit - 'A') + 10;
        }
        return -1;
    }

    @NotNull
    public static final u M(@NotNull List<okhttp3.internal.http2.b> $this$toHeaders) {
        k.f($this$toHeaders, "$this$toHeaders");
        u.a builder = new u.a();
        for (okhttp3.internal.http2.b next : $this$toHeaders) {
            builder.d(next.a().utf8(), next.b().utf8());
        }
        return builder.f();
    }

    @NotNull
    public static final List<okhttp3.internal.http2.b> L(@NotNull u $this$toHeaderList) {
        k.f($this$toHeaderList, "$this$toHeaderList");
        Iterable $this$mapTo$iv$iv = n.l(0, $this$toHeaderList.size());
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        Iterator it = $this$mapTo$iv$iv.iterator();
        while (it.hasNext()) {
            int it2 = ((g0) it).nextInt();
            arrayList.add(new okhttp3.internal.http2.b($this$toHeaderList.b(it2), $this$toHeaderList.h(it2)));
        }
        return arrayList;
    }

    public static final boolean g(@NotNull v $this$canReuseConnectionFor, @NotNull v other) {
        k.f($this$canReuseConnectionFor, "$this$canReuseConnectionFor");
        k.f(other, "other");
        return k.a($this$canReuseConnectionFor.j(), other.j()) && $this$canReuseConnectionFor.p() == other.p() && k.a($this$canReuseConnectionFor.t(), other.t());
    }

    /* compiled from: Util.kt */
    public static final class a implements r.c {
        final /* synthetic */ okhttp3.r a;

        a(okhttp3.r $receiver) {
            this.a = $receiver;
        }

        @NotNull
        public okhttp3.r a(@NotNull okhttp3.e call) {
            k.f(call, NotificationCompat.CATEGORY_CALL);
            return this.a;
        }
    }

    @NotNull
    public static final r.c e(@NotNull okhttp3.r $this$asFactory) {
        k.f($this$asFactory, "$this$asFactory");
        return new a($this$asFactory);
    }

    public static final int b(byte $this$and, int mask) {
        return $this$and & mask;
    }

    public static final int c(short $this$and, int mask) {
        return $this$and & mask;
    }

    public static final long d(int $this$and, long mask) {
        return ((long) $this$and) & mask;
    }

    public static final void Y(@NotNull d $this$writeMedium, int medium) {
        k.f($this$writeMedium, "$this$writeMedium");
        $this$writeMedium.writeByte((medium >>> 16) & 255);
        $this$writeMedium.writeByte((medium >>> 8) & 255);
        $this$writeMedium.writeByte(medium & 255);
    }

    public static final int H(@NotNull e $this$readMedium) {
        k.f($this$readMedium, "$this$readMedium");
        return (b($this$readMedium.readByte(), 255) << 16) | (b($this$readMedium.readByte(), 255) << 8) | b($this$readMedium.readByte(), 255);
    }

    public static final boolean J(@NotNull okio.e0 $this$skipAll, int duration, @NotNull TimeUnit timeUnit) {
        long originalDurationNs;
        k.f($this$skipAll, "$this$skipAll");
        k.f(timeUnit, "timeUnit");
        long nowNs = System.nanoTime();
        if ($this$skipAll.timeout().e()) {
            originalDurationNs = $this$skipAll.timeout().c() - nowNs;
        } else {
            originalDurationNs = Long.MAX_VALUE;
        }
        $this$skipAll.timeout().d(Math.min(originalDurationNs, timeUnit.toNanos((long) duration)) + nowNs);
        try {
            okio.c skipBuffer = new okio.c();
            while ($this$skipAll.read(skipBuffer, 8192) != -1) {
                skipBuffer.clear();
            }
            if (originalDurationNs == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                $this$skipAll.timeout().a();
                return true;
            }
            $this$skipAll.timeout().d(nowNs + originalDurationNs);
            return true;
        } catch (InterruptedIOException e2) {
            if (originalDurationNs == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                $this$skipAll.timeout().a();
            } else {
                $this$skipAll.timeout().d(nowNs + originalDurationNs);
            }
            return false;
        } catch (Throwable th) {
            if (originalDurationNs == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                $this$skipAll.timeout().a();
            } else {
                $this$skipAll.timeout().d(nowNs + originalDurationNs);
            }
            throw th;
        }
    }

    public static final boolean p(@NotNull okio.e0 $this$discard, int timeout, @NotNull TimeUnit timeUnit) {
        k.f($this$discard, "$this$discard");
        k.f(timeUnit, "timeUnit");
        try {
            return J($this$discard, timeout, timeUnit);
        } catch (IOException e2) {
            return false;
        }
    }

    public static final boolean D(@NotNull Socket $this$isHealthy, @NotNull e source) {
        int readTimeout;
        k.f($this$isHealthy, "$this$isHealthy");
        k.f(source, "source");
        try {
            readTimeout = $this$isHealthy.getSoTimeout();
            $this$isHealthy.setSoTimeout(1);
            boolean z = !source.r0();
            $this$isHealthy.setSoTimeout(readTimeout);
            return z;
        } catch (SocketTimeoutException e2) {
            return true;
        } catch (IOException e3) {
            return false;
        } catch (Throwable th) {
            $this$isHealthy.setSoTimeout(readTimeout);
            throw th;
        }
    }

    public static final int I(@NotNull okio.c $this$skipAll, byte b2) {
        k.f($this$skipAll, "$this$skipAll");
        int count = 0;
        while (!$this$skipAll.r0() && $this$skipAll.n(0) == b2) {
            count++;
            $this$skipAll.readByte();
        }
        return count;
    }

    public static final int A(@NotNull String $this$indexOfNonWhitespace, int startIndex) {
        k.f($this$indexOfNonWhitespace, "$this$indexOfNonWhitespace");
        int length = $this$indexOfNonWhitespace.length();
        for (int i2 = startIndex; i2 < length; i2++) {
            char c2 = $this$indexOfNonWhitespace.charAt(i2);
            if (c2 != ' ' && c2 != 9) {
                return i2;
            }
        }
        return $this$indexOfNonWhitespace.length();
    }

    public static final long s(@NotNull okhttp3.d0 $this$headersContentLength) {
        k.f($this$headersContentLength, "$this$headersContentLength");
        String a2 = $this$headersContentLength.s().a("Content-Length");
        if (a2 != null) {
            return T(a2, -1);
        }
        return -1;
    }

    public static final long T(@NotNull String $this$toLongOrDefault, long defaultValue) {
        k.f($this$toLongOrDefault, "$this$toLongOrDefault");
        try {
            return Long.parseLong($this$toLongOrDefault);
        } catch (NumberFormatException e2) {
            return defaultValue;
        }
    }

    public static final int U(@Nullable String $this$toNonNegativeInt, int defaultValue) {
        if ($this$toNonNegativeInt == null) {
            return defaultValue;
        }
        try {
            long value = Long.parseLong($this$toNonNegativeInt);
            if (value > ((long) Integer.MAX_VALUE)) {
                return Integer.MAX_VALUE;
            }
            if (value < 0) {
                return 0;
            }
            return (int) value;
        } catch (NumberFormatException e2) {
            return defaultValue;
        }
    }

    @NotNull
    public static final <T> List<T> R(@NotNull List<? extends T> $this$toImmutableList) {
        k.f($this$toImmutableList, "$this$toImmutableList");
        List<T> unmodifiableList = Collections.unmodifiableList(y.F0($this$toImmutableList));
        k.b(unmodifiableList, "Collections.unmodifiableList(toMutableList())");
        return unmodifiableList;
    }

    @NotNull
    @SafeVarargs
    public static final <T> List<T> t(@NotNull T... elements) {
        k.f(elements, "elements");
        Object[] objArr = (Object[]) elements.clone();
        List<T> unmodifiableList = Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(objArr, objArr.length)));
        k.b(unmodifiableList, "Collections.unmodifiable…sList(*elements.clone()))");
        return unmodifiableList;
    }

    @NotNull
    public static final <K, V> Map<K, V> S(@NotNull Map<K, ? extends V> $this$toImmutableMap) {
        k.f($this$toImmutableMap, "$this$toImmutableMap");
        if ($this$toImmutableMap.isEmpty()) {
            return l0.f();
        }
        Map<K, V> unmodifiableMap = Collections.unmodifiableMap(new LinkedHashMap($this$toImmutableMap));
        k.b(unmodifiableMap, "Collections.unmodifiableMap(LinkedHashMap(this))");
        return unmodifiableMap;
    }

    public static final void j(@NotNull Closeable $this$closeQuietly) {
        k.f($this$closeQuietly, "$this$closeQuietly");
        try {
            $this$closeQuietly.close();
        } catch (RuntimeException rethrown) {
            throw rethrown;
        } catch (Exception e2) {
        }
    }

    public static final void k(@NotNull Socket $this$closeQuietly) {
        k.f($this$closeQuietly, "$this$closeQuietly");
        try {
            $this$closeQuietly.close();
        } catch (AssertionError e2) {
            throw e2;
        } catch (RuntimeException rethrown) {
            throw rethrown;
        } catch (Exception e3) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002b, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002c, code lost:
        kotlin.io.b.a(r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002f, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final boolean C(@org.jetbrains.annotations.NotNull okhttp3.internal.io.b r5, @org.jetbrains.annotations.NotNull java.io.File r6) {
        /*
            java.lang.String r0 = "$this$isCivilized"
            kotlin.jvm.internal.k.f(r5, r0)
            java.lang.String r0 = "file"
            kotlin.jvm.internal.k.f(r6, r0)
            okio.b0 r0 = r5.f(r6)
            r1 = r0
            r2 = 0
            r3 = 0
            r5.h(r6)     // Catch:{ IOException -> 0x001e }
            r4 = 1
            kotlin.io.b.a(r0, r3)     // Catch:{ IOException -> 0x001a }
            return r4
        L_0x001a:
            r4 = move-exception
            goto L_0x001f
        L_0x001c:
            r1 = move-exception
            goto L_0x002a
        L_0x001e:
            r4 = move-exception
        L_0x001f:
            kotlin.x r1 = kotlin.x.a     // Catch:{ all -> 0x001c }
            kotlin.io.b.a(r0, r3)
            r5.h(r6)
            r0 = 0
            return r0
        L_0x002a:
            throw r1     // Catch:{ all -> 0x002b }
        L_0x002b:
            r2 = move-exception
            kotlin.io.b.a(r0, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.b.C(okhttp3.internal.io.b, java.io.File):boolean");
    }

    @NotNull
    public static final String O(long $this$toHexString) {
        String hexString = Long.toHexString($this$toHexString);
        k.b(hexString, "java.lang.Long.toHexString(this)");
        return hexString;
    }

    @NotNull
    public static final String N(int $this$toHexString) {
        String hexString = Integer.toHexString($this$toHexString);
        k.b(hexString, "Integer.toHexString(this)");
        return hexString;
    }

    @Nullable
    public static final <T> T G(@NotNull Object instance, @NotNull Class<T> fieldType, @NotNull String fieldName) {
        Object delegate;
        Class<Object> cls = Object.class;
        k.f(instance, "instance");
        k.f(fieldType, "fieldType");
        k.f(fieldName, "fieldName");
        Class c2 = instance.getClass();
        while (!k.a(c2, cls)) {
            try {
                Field field = c2.getDeclaredField(fieldName);
                k.b(field, "field");
                field.setAccessible(true);
                Object value = field.get(instance);
                if (!fieldType.isInstance(value)) {
                    return null;
                }
                return fieldType.cast(value);
            } catch (NoSuchFieldException e2) {
                Class superclass = c2.getSuperclass();
                k.b(superclass, "c.superclass");
                c2 = superclass;
            }
        }
        if (!(true ^ k.a(fieldName, "delegate")) || (delegate = G(instance, cls, "delegate")) == null) {
            return null;
        }
        return G(delegate, fieldType, fieldName);
    }

    public static final <E> void a(@NotNull List<E> $this$addIfAbsent, E element) {
        k.f($this$addIfAbsent, "$this$addIfAbsent");
        if (!$this$addIfAbsent.contains(element)) {
            $this$addIfAbsent.add(element);
        }
    }

    @NotNull
    public static final Throwable X(@NotNull Exception $this$withSuppressed, @NotNull List<? extends Exception> suppressed) {
        k.f($this$withSuppressed, "$this$withSuppressed");
        k.f(suppressed, "suppressed");
        Exception $this$apply = $this$withSuppressed;
        if (suppressed.size() > 1) {
            System.out.println(suppressed);
        }
        for (Exception e2 : suppressed) {
            $this$apply.addSuppressed(e2);
        }
        return $this$withSuppressed;
    }
}
