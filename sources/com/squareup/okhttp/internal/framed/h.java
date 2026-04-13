package com.squareup.okhttp.internal.framed;

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
import okio.ByteString;
import okio.c;
import okio.e;
import okio.e0;
import okio.f;
import okio.p;
import org.apache.http.l;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: Hpack */
public final class h {
    /* access modifiers changed from: private */
    public static final f[] a;
    /* access modifiers changed from: private */
    public static final Map<f, Integer> b = e();

    static {
        f fVar = f.b;
        f fVar2 = f.c;
        f fVar3 = f.d;
        f fVar4 = f.a;
        a = new f[]{new f(f.e, ""), new f(fVar, (String) Constants.GET), new f(fVar, (String) Constants.POST), new f(fVar2, "/"), new f(fVar2, "/index.html"), new f(fVar3, (String) l.DEFAULT_SCHEME_NAME), new f(fVar3, "https"), new f(fVar4, "200"), new f(fVar4, "204"), new f(fVar4, "206"), new f(fVar4, "304"), new f(fVar4, "400"), new f(fVar4, "404"), new f(fVar4, "500"), new f("accept-charset", ""), new f("accept-encoding", (String) HttpHeaders.HEAD_VALUE_ACCEPT_ENCODING), new f("accept-language", ""), new f("accept-ranges", ""), new f("accept", ""), new f("access-control-allow-origin", ""), new f("age", ""), new f("allow", ""), new f((String) Constants.AUTHORIZATION_HEADER, ""), new f("cache-control", ""), new f("content-disposition", ""), new f("content-encoding", ""), new f("content-language", ""), new f("content-length", ""), new f("content-location", ""), new f("content-range", ""), new f("content-type", ""), new f((String) SerializableCookie.COOKIE, ""), new f((String) Progress.DATE, ""), new f("etag", ""), new f("expect", ""), new f("expires", ""), new f("from", ""), new f((String) SerializableCookie.HOST, ""), new f("if-match", ""), new f("if-modified-since", ""), new f("if-none-match", ""), new f("if-range", ""), new f("if-unmodified-since", ""), new f("last-modified", ""), new f("link", ""), new f((String) FirebaseAnalytics.Param.LOCATION, ""), new f("max-forwards", ""), new f("proxy-authenticate", ""), new f("proxy-authorization", ""), new f("range", ""), new f("referer", ""), new f("refresh", ""), new f("retry-after", ""), new f("server", ""), new f("set-cookie", ""), new f("strict-transport-security", ""), new f("transfer-encoding", ""), new f("user-agent", ""), new f("vary", ""), new f("via", ""), new f("www-authenticate", "")};
    }

    /* compiled from: Hpack */
    public static final class a {
        private final List<f> a = new ArrayList();
        private final e b;
        private int c;
        private int d;
        f[] e;
        int f;
        int g;
        int h;

        a(int headerTableSizeSetting, e0 source) {
            f[] fVarArr = new f[8];
            this.e = fVarArr;
            this.f = fVarArr.length - 1;
            this.g = 0;
            this.h = 0;
            this.c = headerTableSizeSetting;
            this.d = headerTableSizeSetting;
            this.b = p.d(source);
        }

        /* access modifiers changed from: package-private */
        public void g(int headerTableSizeSetting) {
            this.c = headerTableSizeSetting;
            this.d = headerTableSizeSetting;
            a();
        }

        private void a() {
            int i = this.d;
            int i2 = this.h;
            if (i >= i2) {
                return;
            }
            if (i == 0) {
                b();
            } else {
                d(i2 - i);
            }
        }

        private void b() {
            this.a.clear();
            Arrays.fill(this.e, (Object) null);
            this.f = this.e.length - 1;
            this.g = 0;
            this.h = 0;
        }

        private int d(int bytesToRecover) {
            int i;
            int entriesToEvict = 0;
            if (bytesToRecover > 0) {
                int j = this.e.length;
                while (true) {
                    j--;
                    i = this.f;
                    if (j < i || bytesToRecover <= 0) {
                        f[] fVarArr = this.e;
                        System.arraycopy(fVarArr, i + 1, fVarArr, i + 1 + entriesToEvict, this.g);
                        this.f += entriesToEvict;
                    } else {
                        f[] fVarArr2 = this.e;
                        bytesToRecover -= fVarArr2[j].j;
                        this.h -= fVarArr2[j].j;
                        this.g--;
                        entriesToEvict++;
                    }
                }
                f[] fVarArr3 = this.e;
                System.arraycopy(fVarArr3, i + 1, fVarArr3, i + 1 + entriesToEvict, this.g);
                this.f += entriesToEvict;
            }
            return entriesToEvict;
        }

        /* access modifiers changed from: package-private */
        public void l() {
            while (!this.b.r0()) {
                int b2 = this.b.readByte() & 255;
                if (b2 == 128) {
                    throw new IOException("index == 0");
                } else if ((b2 & 128) == 128) {
                    m(n(b2, NeedPermissionEvent.PER_IPC_SPEAK_PERM) - 1);
                } else if (b2 == 64) {
                    p();
                } else if ((b2 & 64) == 64) {
                    o(n(b2, 63) - 1);
                } else if ((b2 & 32) == 32) {
                    int n = n(b2, 31);
                    this.d = n;
                    if (n < 0 || n > this.c) {
                        throw new IOException("Invalid dynamic table size update " + this.d);
                    }
                    a();
                } else if (b2 == 16 || b2 == 0) {
                    r();
                } else {
                    q(n(b2, 15) - 1);
                }
            }
        }

        public List<f> e() {
            List<Header> result = new ArrayList<>(this.a);
            this.a.clear();
            return result;
        }

        private void m(int index) {
            if (i(index)) {
                this.a.add(h.a[index]);
                return;
            }
            int dynamicTableIndex = c(index - h.a.length);
            if (dynamicTableIndex >= 0) {
                f[] fVarArr = this.e;
                if (dynamicTableIndex <= fVarArr.length - 1) {
                    this.a.add(fVarArr[dynamicTableIndex]);
                    return;
                }
            }
            throw new IOException("Header index too large " + (index + 1));
        }

        private int c(int index) {
            return this.f + 1 + index;
        }

        private void q(int index) {
            this.a.add(new f(f(index), k()));
        }

        private void r() {
            this.a.add(new f(h.d(k()), k()));
        }

        private void o(int nameIndex) {
            h(-1, new f(f(nameIndex), k()));
        }

        private void p() {
            h(-1, new f(h.d(k()), k()));
        }

        private f f(int index) {
            if (i(index)) {
                return h.a[index].h;
            }
            return this.e[c(index - h.a.length)].h;
        }

        private boolean i(int index) {
            return index >= 0 && index <= h.a.length - 1;
        }

        private void h(int index, f entry) {
            this.a.add(entry);
            int delta = entry.j;
            if (index != -1) {
                delta -= this.e[c(index)].j;
            }
            int i = this.d;
            if (delta > i) {
                b();
                return;
            }
            int entriesEvicted = d((this.h + delta) - i);
            if (index == -1) {
                int i2 = this.g + 1;
                f[] fVarArr = this.e;
                if (i2 > fVarArr.length) {
                    f[] doubled = new f[(fVarArr.length * 2)];
                    System.arraycopy(fVarArr, 0, doubled, fVarArr.length, fVarArr.length);
                    this.f = this.e.length - 1;
                    this.e = doubled;
                }
                int index2 = this.f;
                this.f = index2 - 1;
                this.e[index2] = entry;
                this.g++;
            } else {
                this.e[index + c(index) + entriesEvicted] = entry;
            }
            this.h += delta;
        }

        private int j() {
            return this.b.readByte() & 255;
        }

        /* access modifiers changed from: package-private */
        public int n(int firstByte, int prefixMask) {
            int prefix = firstByte & prefixMask;
            if (prefix < prefixMask) {
                return prefix;
            }
            int result = prefixMask;
            int shift = 0;
            while (true) {
                int b2 = j();
                if ((b2 & 128) == 0) {
                    return result + (b2 << shift);
                }
                result += (b2 & NeedPermissionEvent.PER_IPC_SPEAK_PERM) << shift;
                shift += 7;
            }
        }

        /* access modifiers changed from: package-private */
        public f k() {
            int firstByte = j();
            boolean huffmanDecode = (firstByte & 128) == 128;
            int length = n(firstByte, NeedPermissionEvent.PER_IPC_SPEAK_PERM);
            if (huffmanDecode) {
                return f.of(j.d().c(this.b.g0((long) length)));
            }
            return this.b.m0((long) length);
        }
    }

    private static Map<f, Integer> e() {
        Map<ByteString, Integer> result = new LinkedHashMap<>(a.length);
        int i = 0;
        while (true) {
            f[] fVarArr = a;
            if (i >= fVarArr.length) {
                return Collections.unmodifiableMap(result);
            }
            if (!result.containsKey(fVarArr[i].h)) {
                result.put(fVarArr[i].h, Integer.valueOf(i));
            }
            i++;
        }
    }

    /* compiled from: Hpack */
    public static final class b {
        private final c a;

        b(c out) {
            this.a = out;
        }

        /* access modifiers changed from: package-private */
        public void b(List<f> headerBlock) {
            int size = headerBlock.size();
            for (int i = 0; i < size; i++) {
                f name = headerBlock.get(i).h.toAsciiLowercase();
                Integer staticIndex = (Integer) h.b.get(name);
                if (staticIndex != null) {
                    c(staticIndex.intValue() + 1, 15, 0);
                    a(headerBlock.get(i).i);
                } else {
                    this.a.writeByte(0);
                    a(name);
                    a(headerBlock.get(i).i);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void c(int value, int prefixMask, int bits) {
            if (value < prefixMask) {
                this.a.writeByte(bits | value);
                return;
            }
            this.a.writeByte(bits | prefixMask);
            int value2 = value - prefixMask;
            while (value2 >= 128) {
                this.a.writeByte((value2 & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
                value2 >>>= 7;
            }
            this.a.writeByte(value2);
        }

        /* access modifiers changed from: package-private */
        public void a(f data) {
            c(data.size(), NeedPermissionEvent.PER_IPC_SPEAK_PERM, 0);
            this.a.write(data);
        }
    }

    /* access modifiers changed from: private */
    public static f d(f name) {
        int i = 0;
        int length = name.size();
        while (i < length) {
            byte c = name.getByte(i);
            if (c < 65 || c > 90) {
                i++;
            } else {
                throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + name.utf8());
            }
        }
        return name;
    }
}
