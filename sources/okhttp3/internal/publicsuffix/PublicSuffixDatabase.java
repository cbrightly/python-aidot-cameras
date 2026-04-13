package okhttp3.internal.publicsuffix;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import java.net.IDN;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.TypeCastException;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.sequences.o;
import kotlin.text.x;
import okhttp3.internal.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.e;

/* compiled from: PublicSuffixDatabase.kt */
public final class PublicSuffixDatabase {
    private static final byte[] a = {(byte) 42};
    private static final List<String> b = p.b(e.ANY_MARKER);
    /* access modifiers changed from: private */
    public static final PublicSuffixDatabase c = new PublicSuffixDatabase();
    public static final a d = new a((DefaultConstructorMarker) null);
    private final AtomicBoolean e = new AtomicBoolean(false);
    private final CountDownLatch f = new CountDownLatch(1);
    private byte[] g;
    private byte[] h;

    @Nullable
    public final String c(@NotNull String domain) {
        int firstLabelOffset;
        k.f(domain, SerializableCookie.DOMAIN);
        String unicodeDomain = IDN.toUnicode(domain);
        k.b(unicodeDomain, "unicodeDomain");
        List domainLabels = f(unicodeDomain);
        List rule = b(domainLabels);
        if (domainLabels.size() == rule.size() && rule.get(0).charAt(0) != '!') {
            return null;
        }
        if (rule.get(0).charAt(0) == '!') {
            firstLabelOffset = domainLabels.size() - rule.size();
        } else {
            firstLabelOffset = domainLabels.size() - (rule.size() + 1);
        }
        return o.v(o.n(y.L(f(domain)), firstLabelOffset), ".", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 62, (Object) null);
    }

    private final List<String> f(String domain) {
        List domainLabels = x.E0(domain, new char[]{'.'}, false, 0, 6, (Object) null);
        if (k.a((String) y.d0(domainLabels), "")) {
            return y.P(domainLabels, 1);
        }
        return domainLabels;
    }

    private final List<String> b(List<String> domainLabels) {
        List exactRuleLabels;
        List wildcardRuleLabels;
        if (this.e.get() || !this.e.compareAndSet(false, true)) {
            try {
                this.f.await();
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
            }
        } else {
            e();
        }
        if (this.g != null) {
            int size = domainLabels.size();
            byte[][] bArr = new byte[size][];
            int i = 0;
            while (i < size) {
                String str = domainLabels.get(i);
                Charset charset = StandardCharsets.UTF_8;
                k.b(charset, "UTF_8");
                if (str != null) {
                    byte[] bytes = str.getBytes(charset);
                    k.b(bytes, "(this as java.lang.String).getBytes(charset)");
                    bArr[i] = bytes;
                    i++;
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
            }
            List<String> list = domainLabels;
            byte[][] domainLabelsUtf8Bytes = bArr;
            String exception = null;
            String exactMatch = null;
            int length = domainLabelsUtf8Bytes.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                a aVar = d;
                byte[] bArr2 = this.g;
                if (bArr2 == null) {
                    k.t("publicSuffixListBytes");
                }
                String rule = aVar.b(bArr2, domainLabelsUtf8Bytes, i2);
                if (rule != null) {
                    exactMatch = rule;
                    break;
                }
                i2++;
            }
            String wildcardMatch = null;
            if (domainLabelsUtf8Bytes.length > 1) {
                byte[][] labelsWithWildcard = (byte[][]) domainLabelsUtf8Bytes.clone();
                int length2 = labelsWithWildcard.length - 1;
                int labelIndex = 0;
                while (true) {
                    if (labelIndex >= length2) {
                        break;
                    }
                    labelsWithWildcard[labelIndex] = a;
                    a aVar2 = d;
                    byte[] bArr3 = this.g;
                    if (bArr3 == null) {
                        k.t("publicSuffixListBytes");
                    }
                    String rule2 = aVar2.b(bArr3, labelsWithWildcard, labelIndex);
                    if (rule2 != null) {
                        wildcardMatch = rule2;
                        break;
                    }
                    labelIndex++;
                }
            }
            if (wildcardMatch != null) {
                int length3 = domainLabelsUtf8Bytes.length - 1;
                int labelIndex2 = 0;
                while (true) {
                    if (labelIndex2 >= length3) {
                        break;
                    }
                    a aVar3 = d;
                    byte[] bArr4 = this.h;
                    if (bArr4 == null) {
                        k.t("publicSuffixExceptionListBytes");
                    }
                    String rule3 = aVar3.b(bArr4, domainLabelsUtf8Bytes, labelIndex2);
                    if (rule3 != null) {
                        exception = rule3;
                        break;
                    }
                    labelIndex2++;
                }
            }
            if (exception != null) {
                return x.E0('!' + exception, new char[]{'.'}, false, 0, 6, (Object) null);
            } else if (exactMatch == null && wildcardMatch == null) {
                return b;
            } else {
                if (exactMatch == null || (exactRuleLabels = x.E0(exactMatch, new char[]{'.'}, false, 0, 6, (Object) null)) == null) {
                    exactRuleLabels = q.g();
                }
                if (wildcardMatch == null || (wildcardRuleLabels = x.E0(wildcardMatch, new char[]{'.'}, false, 0, 6, (Object) null)) == null) {
                    wildcardRuleLabels = q.g();
                }
                if (exactRuleLabels.size() > wildcardRuleLabels.size()) {
                    return exactRuleLabels;
                }
                return wildcardRuleLabels;
            }
        } else {
            List<String> list2 = domainLabels;
            throw new IllegalStateException("Unable to load publicsuffixes.gz resource from the classpath.".toString());
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void e() {
        /*
            r5 = this;
            r0 = 0
        L_0x0002:
            r5.d()     // Catch:{  }
            if (r0 == 0) goto L_0x0011
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ InterruptedIOException -> 0x002c, IOException -> 0x0014 }
            r1.interrupt()     // Catch:{ InterruptedIOException -> 0x002c, IOException -> 0x0014 }
        L_0x0011:
            return
        L_0x0012:
            r1 = move-exception
            goto L_0x0033
        L_0x0014:
            r1 = move-exception
            okhttp3.internal.platform.h$a r2 = okhttp3.internal.platform.h.c     // Catch:{ all -> 0x0012 }
            okhttp3.internal.platform.h r2 = r2.g()     // Catch:{ all -> 0x0012 }
            java.lang.String r3 = "Failed to read public suffix list"
            r4 = 5
            r2.k(r3, r4, r1)     // Catch:{ all -> 0x0012 }
            if (r0 == 0) goto L_0x002b
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x002b:
            return
        L_0x002c:
            r1 = move-exception
            java.lang.Thread.interrupted()     // Catch:{ all -> 0x0012 }
            r0 = 1
            goto L_0x0002
        L_0x0033:
            if (r0 == 0) goto L_0x003c
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x003c:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.publicsuffix.PublicSuffixDatabase.e():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0056, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0057, code lost:
        kotlin.io.b.a(r4, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005a, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void d() {
        /*
            r11 = this;
            r0 = 0
            r1 = r0
            r2 = r0
            java.lang.Class<okhttp3.internal.publicsuffix.PublicSuffixDatabase> r3 = okhttp3.internal.publicsuffix.PublicSuffixDatabase.class
            java.lang.String r4 = "publicsuffixes.gz"
            java.io.InputStream r3 = r3.getResourceAsStream(r4)
            if (r3 == 0) goto L_0x005b
            okio.m r4 = new okio.m
            okio.e0 r5 = okio.p.l(r3)
            r4.<init>(r5)
            okio.e r4 = okio.p.d(r4)
            r5 = r4
            r6 = 0
            int r7 = r5.readInt()     // Catch:{ all -> 0x0054 }
            long r8 = (long) r7     // Catch:{ all -> 0x0054 }
            byte[] r8 = r5.g0(r8)     // Catch:{ all -> 0x0054 }
            r1 = r8
            int r8 = r5.readInt()     // Catch:{ all -> 0x0054 }
            long r9 = (long) r8     // Catch:{ all -> 0x0054 }
            byte[] r9 = r5.g0(r9)     // Catch:{ all -> 0x0054 }
            r2 = r9
            kotlin.x r5 = kotlin.x.a     // Catch:{ all -> 0x0054 }
            kotlin.io.b.a(r4, r0)
            monitor-enter(r11)
            r0 = 0
            if (r1 != 0) goto L_0x0040
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x0051 }
        L_0x0040:
            r11.g = r1     // Catch:{ all -> 0x0051 }
            if (r2 != 0) goto L_0x0047
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x0051 }
        L_0x0047:
            r11.h = r2     // Catch:{ all -> 0x0051 }
            monitor-exit(r11)
            java.util.concurrent.CountDownLatch r0 = r11.f
            r0.countDown()
            return
        L_0x0051:
            r0 = move-exception
            monitor-exit(r11)
            throw r0
        L_0x0054:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0056 }
        L_0x0056:
            r5 = move-exception
            kotlin.io.b.a(r4, r0)
            throw r5
        L_0x005b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.publicsuffix.PublicSuffixDatabase.d():void");
    }

    /* compiled from: PublicSuffixDatabase.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final PublicSuffixDatabase c() {
            return PublicSuffixDatabase.c;
        }

        /* access modifiers changed from: private */
        public final String b(@NotNull byte[] $this$binarySearch, byte[][] labels, int labelIndex) {
            int byte0;
            int compareResult;
            int low;
            byte[] bArr = $this$binarySearch;
            byte[][] bArr2 = labels;
            int low2 = 0;
            int high = bArr.length;
            while (low2 < high) {
                int mid = (low2 + high) / 2;
                while (mid > -1 && bArr[mid] != ((byte) 10)) {
                    mid--;
                }
                int mid2 = mid + 1;
                int end = 1;
                while (bArr[mid2 + end] != ((byte) 10)) {
                    end++;
                }
                int publicSuffixLength = (mid2 + end) - mid2;
                int currentLabelIndex = labelIndex;
                int currentLabelByteIndex = 0;
                int publicSuffixByteIndex = 0;
                boolean expectDot = false;
                while (true) {
                    if (expectDot) {
                        byte0 = 46;
                        expectDot = false;
                    } else {
                        byte0 = b.b(bArr2[currentLabelIndex][currentLabelByteIndex], 255);
                    }
                    compareResult = byte0 - b.b(bArr[mid2 + publicSuffixByteIndex], 255);
                    if (compareResult == 0) {
                        publicSuffixByteIndex++;
                        currentLabelByteIndex++;
                        if (publicSuffixByteIndex == publicSuffixLength) {
                            break;
                        }
                        if (bArr2[currentLabelIndex].length != currentLabelByteIndex) {
                            low = low2;
                        } else if (currentLabelIndex == bArr2.length - 1) {
                            break;
                        } else {
                            low = low2;
                            currentLabelIndex++;
                            expectDot = true;
                            currentLabelByteIndex = -1;
                        }
                        low2 = low;
                    } else {
                        break;
                    }
                }
                if (compareResult < 0) {
                    high = mid2 - 1;
                } else if (compareResult > 0) {
                    low2 = mid2 + end + 1;
                } else {
                    int publicSuffixBytesLeft = publicSuffixLength - publicSuffixByteIndex;
                    int labelBytesLeft = bArr2[currentLabelIndex].length - currentLabelByteIndex;
                    int i = currentLabelIndex + 1;
                    int low3 = low2;
                    for (int low4 = bArr2.length; i < low4; low4 = low4) {
                        labelBytesLeft += bArr2[i].length;
                        i++;
                    }
                    if (labelBytesLeft < publicSuffixBytesLeft) {
                        high = mid2 - 1;
                        low2 = low3;
                    } else if (labelBytesLeft > publicSuffixBytesLeft) {
                        low2 = mid2 + end + 1;
                    } else {
                        Charset charset = StandardCharsets.UTF_8;
                        k.b(charset, "UTF_8");
                        return new String(bArr, mid2, publicSuffixLength, charset);
                    }
                }
            }
            return null;
        }
    }
}
