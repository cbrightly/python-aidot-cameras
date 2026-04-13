package okhttp3.internal.http2;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okio.e;
import okio.e0;
import okio.f;
import okio.p;
import org.apache.http.l;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;

/* compiled from: Hpack.kt */
public final class c {
    @NotNull
    private static final b[] a;
    @NotNull
    private static final Map<f, Integer> b;
    public static final c c;

    static {
        c cVar = new c();
        c = cVar;
        f fVar = b.c;
        f fVar2 = b.d;
        f fVar3 = b.e;
        f fVar4 = b.b;
        a = new b[]{new b(b.f, ""), new b(fVar, (String) Constants.GET), new b(fVar, (String) Constants.POST), new b(fVar2, "/"), new b(fVar2, "/index.html"), new b(fVar3, (String) l.DEFAULT_SCHEME_NAME), new b(fVar3, "https"), new b(fVar4, "200"), new b(fVar4, "204"), new b(fVar4, "206"), new b(fVar4, "304"), new b(fVar4, "400"), new b(fVar4, "404"), new b(fVar4, "500"), new b("accept-charset", ""), new b("accept-encoding", (String) HttpHeaders.HEAD_VALUE_ACCEPT_ENCODING), new b("accept-language", ""), new b("accept-ranges", ""), new b("accept", ""), new b("access-control-allow-origin", ""), new b("age", ""), new b("allow", ""), new b((String) Constants.AUTHORIZATION_HEADER, ""), new b("cache-control", ""), new b("content-disposition", ""), new b("content-encoding", ""), new b("content-language", ""), new b("content-length", ""), new b("content-location", ""), new b("content-range", ""), new b("content-type", ""), new b((String) SerializableCookie.COOKIE, ""), new b((String) Progress.DATE, ""), new b("etag", ""), new b("expect", ""), new b("expires", ""), new b("from", ""), new b((String) SerializableCookie.HOST, ""), new b("if-match", ""), new b("if-modified-since", ""), new b("if-none-match", ""), new b("if-range", ""), new b("if-unmodified-since", ""), new b("last-modified", ""), new b("link", ""), new b((String) FirebaseAnalytics.Param.LOCATION, ""), new b("max-forwards", ""), new b("proxy-authenticate", ""), new b("proxy-authorization", ""), new b("range", ""), new b("referer", ""), new b("refresh", ""), new b("retry-after", ""), new b("server", ""), new b("set-cookie", ""), new b("strict-transport-security", ""), new b("transfer-encoding", ""), new b("user-agent", ""), new b("vary", ""), new b("via", ""), new b("www-authenticate", "")};
        b = cVar.d();
    }

    private c() {
    }

    @NotNull
    public final b[] c() {
        return a;
    }

    @NotNull
    public final Map<f, Integer> b() {
        return b;
    }

    /* compiled from: Hpack.kt */
    public static final class a {
        private final List<b> a;
        private final e b;
        @NotNull
        public b[] c;
        private int d;
        public int e;
        public int f;
        private final int g;
        private int h;

        public a(@NotNull e0 source, int headerTableSizeSetting, int maxDynamicTableByteCount) {
            k.f(source, "source");
            this.g = headerTableSizeSetting;
            this.h = maxDynamicTableByteCount;
            this.a = new ArrayList();
            this.b = p.d(source);
            b[] bVarArr = new b[8];
            this.c = bVarArr;
            this.d = bVarArr.length - 1;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ a(e0 e0Var, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(e0Var, i, (i3 & 4) != 0 ? i : i2);
        }

        @NotNull
        public final List<b> e() {
            List result = y.C0(this.a);
            this.a.clear();
            return result;
        }

        private final void a() {
            int i = this.h;
            int i2 = this.f;
            if (i >= i2) {
                return;
            }
            if (i == 0) {
                b();
            } else {
                d(i2 - i);
            }
        }

        private final void b() {
            kotlin.collections.k.l(this.c, (Object) null, 0, 0, 6, (Object) null);
            this.d = this.c.length - 1;
            this.e = 0;
            this.f = 0;
        }

        private final int d(int bytesToRecover) {
            int i;
            int bytesToRecover2 = bytesToRecover;
            int entriesToEvict = 0;
            if (bytesToRecover2 > 0) {
                int j = this.c.length;
                while (true) {
                    j--;
                    i = this.d;
                    if (j < i || bytesToRecover2 <= 0) {
                        b[] bVarArr = this.c;
                        System.arraycopy(bVarArr, i + 1, bVarArr, i + 1 + entriesToEvict, this.e);
                        this.d += entriesToEvict;
                    } else {
                        b toEvict = this.c[j];
                        if (toEvict == null) {
                            k.n();
                        }
                        int i2 = toEvict.h;
                        bytesToRecover2 -= i2;
                        this.f -= i2;
                        this.e--;
                        entriesToEvict++;
                    }
                }
                b[] bVarArr2 = this.c;
                System.arraycopy(bVarArr2, i + 1, bVarArr2, i + 1 + entriesToEvict, this.e);
                this.d += entriesToEvict;
            }
            return entriesToEvict;
        }

        public final void k() {
            while (!this.b.r0()) {
                int b2 = okhttp3.internal.b.b(this.b.readByte(), 255);
                if (b2 == 128) {
                    throw new IOException("index == 0");
                } else if ((b2 & 128) == 128) {
                    l(m(b2, NeedPermissionEvent.PER_IPC_SPEAK_PERM) - 1);
                } else if (b2 == 64) {
                    o();
                } else if ((b2 & 64) == 64) {
                    n(m(b2, 63) - 1);
                } else if ((b2 & 32) == 32) {
                    int m = m(b2, 31);
                    this.h = m;
                    if (m < 0 || m > this.g) {
                        throw new IOException("Invalid dynamic table size update " + this.h);
                    }
                    a();
                } else if (b2 == 16 || b2 == 0) {
                    q();
                } else {
                    p(m(b2, 15) - 1);
                }
            }
        }

        private final void l(int index) {
            if (h(index)) {
                this.a.add(c.c.c()[index]);
                return;
            }
            int dynamicTableIndex = c(index - c.c.c().length);
            if (dynamicTableIndex >= 0) {
                b[] bVarArr = this.c;
                if (dynamicTableIndex < bVarArr.length) {
                    List<b> list = this.a;
                    b bVar = bVarArr[dynamicTableIndex];
                    if (bVar == null) {
                        k.n();
                    }
                    list.add(bVar);
                    return;
                }
            }
            throw new IOException("Header index too large " + (index + 1));
        }

        private final int c(int index) {
            return this.d + 1 + index;
        }

        private final void p(int index) {
            this.a.add(new b(f(index), j()));
        }

        private final void q() {
            this.a.add(new b(c.c.a(j()), j()));
        }

        private final void n(int nameIndex) {
            g(-1, new b(f(nameIndex), j()));
        }

        private final void o() {
            g(-1, new b(c.c.a(j()), j()));
        }

        private final f f(int index) {
            if (h(index)) {
                return c.c.c()[index].i;
            }
            int dynamicTableIndex = c(index - c.c.c().length);
            if (dynamicTableIndex >= 0) {
                b[] bVarArr = this.c;
                if (dynamicTableIndex < bVarArr.length) {
                    b bVar = bVarArr[dynamicTableIndex];
                    if (bVar == null) {
                        k.n();
                    }
                    return bVar.i;
                }
            }
            throw new IOException("Header index too large " + (index + 1));
        }

        private final boolean h(int index) {
            return index >= 0 && index <= c.c.c().length - 1;
        }

        private final void g(int index, b entry) {
            int index2 = index;
            this.a.add(entry);
            int delta = entry.h;
            if (index2 != -1) {
                b bVar = this.c[c(index2)];
                if (bVar == null) {
                    k.n();
                }
                delta -= bVar.h;
            }
            int i = this.h;
            if (delta > i) {
                b();
                return;
            }
            int entriesEvicted = d((this.f + delta) - i);
            if (index2 == -1) {
                int i2 = this.e + 1;
                b[] bVarArr = this.c;
                if (i2 > bVarArr.length) {
                    b[] doubled = new b[(bVarArr.length * 2)];
                    System.arraycopy(bVarArr, 0, doubled, bVarArr.length, bVarArr.length);
                    this.d = this.c.length - 1;
                    this.c = doubled;
                }
                int index3 = this.d;
                this.d = index3 - 1;
                this.c[index3] = entry;
                this.e++;
            } else {
                this.c[index2 + c(index2) + entriesEvicted] = entry;
            }
            this.f += delta;
        }

        private final int i() {
            return okhttp3.internal.b.b(this.b.readByte(), 255);
        }

        public final int m(int firstByte, int prefixMask) {
            int prefix = firstByte & prefixMask;
            if (prefix < prefixMask) {
                return prefix;
            }
            int result = prefixMask;
            int shift = 0;
            while (true) {
                int b2 = i();
                if ((b2 & 128) == 0) {
                    return result + (b2 << shift);
                }
                result += (b2 & NeedPermissionEvent.PER_IPC_SPEAK_PERM) << shift;
                shift += 7;
            }
        }

        @NotNull
        public final f j() {
            int firstByte = i();
            boolean huffmanDecode = (firstByte & 128) == 128;
            long length = (long) m(firstByte, NeedPermissionEvent.PER_IPC_SPEAK_PERM);
            if (!huffmanDecode) {
                return this.b.m0(length);
            }
            okio.c decodeBuffer = new okio.c();
            j.d.b(this.b, length, decodeBuffer);
            return decodeBuffer.D0();
        }
    }

    private final Map<f, Integer> d() {
        b[] bVarArr = a;
        LinkedHashMap result = new LinkedHashMap(bVarArr.length);
        int length = bVarArr.length;
        for (int i = 0; i < length; i++) {
            b[] bVarArr2 = a;
            if (!result.containsKey(bVarArr2[i].i)) {
                result.put(bVarArr2[i].i, Integer.valueOf(i));
            }
        }
        Map<f, Integer> unmodifiableMap = Collections.unmodifiableMap(result);
        k.b(unmodifiableMap, "Collections.unmodifiableMap(result)");
        return unmodifiableMap;
    }

    /* compiled from: Hpack.kt */
    public static final class b {
        private int a;
        private boolean b;
        public int c;
        @NotNull
        public b[] d;
        private int e;
        public int f;
        public int g;
        public int h;
        private final boolean i;
        private final okio.c j;

        public b(int headerTableSizeSetting, boolean useCompression, @NotNull okio.c out) {
            k.f(out, "out");
            this.h = headerTableSizeSetting;
            this.i = useCompression;
            this.j = out;
            this.a = Integer.MAX_VALUE;
            this.c = headerTableSizeSetting;
            b[] bVarArr = new b[8];
            this.d = bVarArr;
            this.e = bVarArr.length - 1;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ b(int i2, boolean z, okio.c cVar, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? 4096 : i2, (i3 & 2) != 0 ? true : z, cVar);
        }

        private final void b() {
            kotlin.collections.k.l(this.d, (Object) null, 0, 0, 6, (Object) null);
            this.e = this.d.length - 1;
            this.f = 0;
            this.g = 0;
        }

        private final int c(int bytesToRecover) {
            int i2;
            int bytesToRecover2 = bytesToRecover;
            int entriesToEvict = 0;
            if (bytesToRecover2 > 0) {
                int j2 = this.d.length;
                while (true) {
                    j2--;
                    i2 = this.e;
                    if (j2 < i2 || bytesToRecover2 <= 0) {
                        b[] bVarArr = this.d;
                        System.arraycopy(bVarArr, i2 + 1, bVarArr, i2 + 1 + entriesToEvict, this.f);
                        b[] bVarArr2 = this.d;
                        int i3 = this.e;
                        Arrays.fill(bVarArr2, i3 + 1, i3 + 1 + entriesToEvict, (Object) null);
                        this.e += entriesToEvict;
                    } else {
                        b bVar = this.d[j2];
                        if (bVar == null) {
                            k.n();
                        }
                        bytesToRecover2 -= bVar.h;
                        int i4 = this.g;
                        b bVar2 = this.d[j2];
                        if (bVar2 == null) {
                            k.n();
                        }
                        this.g = i4 - bVar2.h;
                        this.f--;
                        entriesToEvict++;
                    }
                }
                b[] bVarArr3 = this.d;
                System.arraycopy(bVarArr3, i2 + 1, bVarArr3, i2 + 1 + entriesToEvict, this.f);
                b[] bVarArr22 = this.d;
                int i32 = this.e;
                Arrays.fill(bVarArr22, i32 + 1, i32 + 1 + entriesToEvict, (Object) null);
                this.e += entriesToEvict;
            }
            return entriesToEvict;
        }

        private final void d(b entry) {
            int delta = entry.h;
            int i2 = this.c;
            if (delta > i2) {
                b();
                return;
            }
            c((this.g + delta) - i2);
            int i3 = this.f + 1;
            b[] bVarArr = this.d;
            if (i3 > bVarArr.length) {
                b[] doubled = new b[(bVarArr.length * 2)];
                System.arraycopy(bVarArr, 0, doubled, bVarArr.length, bVarArr.length);
                this.e = this.d.length - 1;
                this.d = doubled;
            }
            int index = this.e;
            this.e = index - 1;
            this.d[index] = entry;
            this.f++;
            this.g += delta;
        }

        public final void g(@NotNull List<b> headerBlock) {
            k.f(headerBlock, "headerBlock");
            if (this.b) {
                int i2 = this.a;
                if (i2 < this.c) {
                    h(i2, 31, 32);
                }
                this.b = false;
                this.a = Integer.MAX_VALUE;
                h(this.c, 31, 32);
            }
            int size = headerBlock.size();
            for (int i3 = 0; i3 < size; i3++) {
                b header = headerBlock.get(i3);
                f name = header.i.toAsciiLowercase();
                f value = header.j;
                int headerIndex = -1;
                int headerNameIndex = -1;
                c cVar = c.c;
                Integer staticIndex = cVar.b().get(name);
                if (staticIndex != null && 2 <= (headerNameIndex = staticIndex.intValue() + 1) && 7 >= headerNameIndex) {
                    if (k.a(cVar.c()[headerNameIndex - 1].j, value)) {
                        headerIndex = headerNameIndex;
                    } else if (k.a(cVar.c()[headerNameIndex].j, value)) {
                        headerIndex = headerNameIndex + 1;
                    }
                }
                if (headerIndex == -1) {
                    int j2 = this.e + 1;
                    int length = this.d.length;
                    while (true) {
                        if (j2 >= length) {
                            break;
                        }
                        b bVar = this.d[j2];
                        if (bVar == null) {
                            k.n();
                        }
                        if (k.a(bVar.i, name)) {
                            b bVar2 = this.d[j2];
                            if (bVar2 == null) {
                                k.n();
                            }
                            if (k.a(bVar2.j, value)) {
                                headerIndex = (j2 - this.e) + c.c.c().length;
                                break;
                            } else if (headerNameIndex == -1) {
                                headerNameIndex = (j2 - this.e) + c.c.c().length;
                            }
                        }
                        j2++;
                    }
                }
                if (headerIndex != -1) {
                    h(headerIndex, NeedPermissionEvent.PER_IPC_SPEAK_PERM, 128);
                } else if (headerNameIndex == -1) {
                    this.j.writeByte(64);
                    f(name);
                    f(value);
                    d(header);
                } else if (!name.startsWith(b.a) || !(!k.a(b.f, name))) {
                    h(headerNameIndex, 63, 64);
                    f(value);
                    d(header);
                } else {
                    h(headerNameIndex, 15, 0);
                    f(value);
                }
            }
        }

        public final void h(int value, int prefixMask, int bits) {
            int value2 = value;
            if (value2 < prefixMask) {
                this.j.writeByte(bits | value2);
                return;
            }
            this.j.writeByte(bits | prefixMask);
            int value3 = value2 - prefixMask;
            while (value3 >= 128) {
                this.j.writeByte((value3 & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
                value3 >>>= 7;
            }
            this.j.writeByte(value3);
        }

        public final void f(@NotNull f data) {
            k.f(data, "data");
            if (this.i) {
                j jVar = j.d;
                if (jVar.d(data) < data.size()) {
                    okio.c huffmanBuffer = new okio.c();
                    jVar.c(data, huffmanBuffer);
                    f huffmanBytes = huffmanBuffer.D0();
                    h(huffmanBytes.size(), NeedPermissionEvent.PER_IPC_SPEAK_PERM, 128);
                    this.j.write(huffmanBytes);
                    return;
                }
            }
            h(data.size(), NeedPermissionEvent.PER_IPC_SPEAK_PERM, 0);
            this.j.write(data);
        }

        public final void e(int headerTableSizeSetting) {
            this.h = headerTableSizeSetting;
            int effectiveHeaderTableSize = Math.min(headerTableSizeSetting, 16384);
            int i2 = this.c;
            if (i2 != effectiveHeaderTableSize) {
                if (effectiveHeaderTableSize < i2) {
                    this.a = Math.min(this.a, effectiveHeaderTableSize);
                }
                this.b = true;
                this.c = effectiveHeaderTableSize;
                a();
            }
        }

        private final void a() {
            int i2 = this.c;
            int i3 = this.g;
            if (i2 >= i3) {
                return;
            }
            if (i2 == 0) {
                b();
            } else {
                c(i3 - i2);
            }
        }
    }

    @NotNull
    public final f a(@NotNull f name) {
        k.f(name, "name");
        int size = name.size();
        for (int i = 0; i < size; i++) {
            byte b2 = (byte) 65;
            byte b3 = (byte) 90;
            byte b4 = name.getByte(i);
            if (b2 <= b4 && b3 >= b4) {
                throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + name.utf8());
            }
        }
        return name;
    }
}
