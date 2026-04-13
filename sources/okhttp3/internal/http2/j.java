package okhttp3.internal.http2;

import androidx.core.view.PointerIconCompat;
import com.github.druk.dnssd.NSType;
import kotlin.jvm.internal.k;
import okhttp3.internal.b;
import okio.d;
import okio.e;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Huffman.kt */
public final class j {
    private static final int[] a = {8184, 8388568, 268435426, 268435427, 268435428, 268435429, 268435430, 268435431, 268435432, 16777194, 1073741820, 268435433, 268435434, 1073741821, 268435435, 268435436, 268435437, 268435438, 268435439, 268435440, 268435441, 268435442, 1073741822, 268435443, 268435444, 268435445, 268435446, 268435447, 268435448, 268435449, 268435450, 268435451, 20, PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW, 4090, 8185, 21, 248, 2042, PointerIconCompat.TYPE_ZOOM_IN, PointerIconCompat.TYPE_ZOOM_OUT, NSType.TKEY, 2043, 250, 22, 23, 24, 0, 1, 2, 25, 26, 27, 28, 29, 30, 31, 92, NSType.IXFR, 32764, 32, 4091, PointerIconCompat.TYPE_GRAB, 8186, 33, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, NSType.AXFR, 115, NSType.MAILB, 8187, 524272, 8188, 16380, 34, 32765, 3, 35, 4, 36, 5, 37, 38, 39, 6, 116, 117, 40, 41, 42, 7, 43, 118, 44, 8, 9, 45, 119, 120, 121, 122, 123, 32766, 2044, 16381, 8189, 268435452, 1048550, 4194258, 1048551, 1048552, 4194259, 4194260, 4194261, 8388569, 4194262, 8388570, 8388571, 8388572, 8388573, 8388574, 16777195, 8388575, 16777196, 16777197, 4194263, 8388576, 16777198, 8388577, 8388578, 8388579, 8388580, 2097116, 4194264, 8388581, 4194265, 8388582, 8388583, 16777199, 4194266, 2097117, 1048553, 4194267, 4194268, 8388584, 8388585, 2097118, 8388586, 4194269, 4194270, 16777200, 2097119, 4194271, 8388587, 8388588, 2097120, 2097121, 4194272, 2097122, 8388589, 4194273, 8388590, 8388591, 1048554, 4194274, 4194275, 4194276, 8388592, 4194277, 4194278, 8388593, 67108832, 67108833, 1048555, 524273, 4194279, 8388594, 4194280, 33554412, 67108834, 67108835, 67108836, 134217694, 134217695, 67108837, 16777201, 33554413, 524274, 2097123, 67108838, 134217696, 134217697, 67108839, 134217698, 16777202, 2097124, 2097125, 67108840, 67108841, 268435453, 134217699, 134217700, 134217701, 1048556, 16777203, 1048557, 2097126, 4194281, 2097127, 2097128, 8388595, 4194282, 4194283, 33554414, 33554415, 16777204, 16777205, 67108842, 8388596, 67108843, 134217702, 67108844, 67108845, 134217703, 134217704, 134217705, 134217706, 134217707, 268435454, 134217708, 134217709, 134217710, 134217711, 134217712, 67108846};
    private static final byte[] b;
    private static final a c = new a();
    public static final j d;

    static {
        j jVar = new j();
        d = jVar;
        byte[] bArr = {13, 23, 28, 28, 28, 28, 28, 28, 28, 24, 30, 28, 28, 30, 28, 28, 28, 28, 28, 28, 28, 28, 30, 28, 28, 28, 28, 28, 28, 28, 28, 28, 6, 10, 10, 12, 13, 6, 8, 11, 10, 10, 8, 11, 8, 6, 6, 6, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 8, 15, 6, 12, 10, 13, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 7, 8, 13, 19, 13, 14, 6, 15, 5, 6, 5, 6, 5, 6, 6, 6, 5, 7, 7, 6, 6, 6, 5, 6, 7, 6, 5, 5, 6, 7, 7, 7, 7, 7, 15, 11, 14, 13, 28, 20, 22, 20, 20, 22, 22, 22, 23, 22, 23, 23, 23, 23, 23, 24, 23, 24, 24, 22, 23, 24, 23, 23, 23, 23, 21, 22, 23, 22, 23, 23, 24, 22, 21, 20, 22, 22, 23, 23, 21, 23, 22, 22, 24, 21, 22, 23, 23, 21, 21, 22, 21, 23, 22, 23, 23, 20, 22, 22, 22, 23, 22, 22, 23, 26, 26, 20, 19, 22, 23, 22, 25, 26, 26, 26, 27, 27, 26, 24, 25, 19, 21, 26, 27, 27, 26, 27, 24, 21, 21, 26, 26, 28, 27, 27, 27, 20, 24, 20, 21, 22, 21, 21, 23, 22, 22, 25, 25, 24, 24, 26, 23, 26, 27, 26, 26, 27, 27, 27, 27, 27, 28, 27, 27, 27, 27, 27, 26};
        b = bArr;
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            jVar.a(i, a[i], b[i]);
        }
    }

    private j() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void c(@org.jetbrains.annotations.NotNull okio.f r13, @org.jetbrains.annotations.NotNull okio.d r14) {
        /*
            r12 = this;
            java.lang.String r0 = "source"
            kotlin.jvm.internal.k.f(r13, r0)
            java.lang.String r0 = "sink"
            kotlin.jvm.internal.k.f(r14, r0)
            r0 = 0
            r2 = 0
            int r3 = r13.size()
            r4 = 0
        L_0x0014:
            if (r4 >= r3) goto L_0x003f
            byte r5 = r13.getByte(r4)
            r6 = 255(0xff, float:3.57E-43)
            int r5 = okhttp3.internal.b.b(r5, r6)
            int[] r6 = a
            r6 = r6[r5]
            byte[] r7 = b
            byte r7 = r7[r5]
            long r8 = r0 << r7
            long r10 = (long) r6
            long r0 = r8 | r10
            int r2 = r2 + r7
        L_0x002e:
            r8 = 8
            if (r2 < r8) goto L_0x003b
            int r2 = r2 + -8
            long r8 = r0 >> r2
            int r8 = (int) r8
            r14.writeByte(r8)
            goto L_0x002e
        L_0x003b:
            int r4 = r4 + 1
            goto L_0x0014
        L_0x003f:
            if (r2 <= 0) goto L_0x004c
            int r3 = 8 - r2
            long r0 = r0 << r3
            r3 = 255(0xff, double:1.26E-321)
            long r3 = r3 >>> r2
            long r0 = r0 | r3
            int r3 = (int) r0
            r14.writeByte(r3)
        L_0x004c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.j.c(okio.f, okio.d):void");
    }

    public final int d(@NotNull f bytes) {
        k.f(bytes, "bytes");
        long bitCount = 0;
        int size = bytes.size();
        for (int i = 0; i < size; i++) {
            bitCount += (long) b[b.b(bytes.getByte(i), 255)];
        }
        return (int) ((((long) 7) + bitCount) >> 3);
    }

    public final void b(@NotNull e source, long byteCount, @NotNull d sink) {
        k.f(source, "source");
        k.f(sink, "sink");
        a node = c;
        int accumulator = 0;
        int accumulatorBitCount = 0;
        for (long i = 0; i < byteCount; i = 1 + i) {
            accumulator = (accumulator << 8) | b.b(source.readByte(), 255);
            accumulatorBitCount += 8;
            while (accumulatorBitCount >= 8) {
                int childIndex = (accumulator >>> (accumulatorBitCount - 8)) & 255;
                a[] a2 = node.a();
                if (a2 == null) {
                    k.n();
                }
                a aVar = a2[childIndex];
                if (aVar == null) {
                    k.n();
                }
                node = aVar;
                if (node.a() == null) {
                    sink.writeByte(node.b());
                    accumulatorBitCount -= node.c();
                    node = c;
                } else {
                    accumulatorBitCount -= 8;
                }
            }
        }
        while (accumulatorBitCount > 0) {
            int childIndex2 = (accumulator << (8 - accumulatorBitCount)) & 255;
            a[] a3 = node.a();
            if (a3 == null) {
                k.n();
            }
            a aVar2 = a3[childIndex2];
            if (aVar2 == null) {
                k.n();
            }
            a node2 = aVar2;
            if (node2.a() == null && node2.c() <= accumulatorBitCount) {
                sink.writeByte(node2.b());
                accumulatorBitCount -= node2.c();
                node = c;
            } else {
                return;
            }
        }
    }

    private final void a(int symbol, int code, int codeBitCount) {
        a terminal = new a(symbol, codeBitCount);
        int accumulatorBitCount = codeBitCount;
        a node = c;
        while (accumulatorBitCount > 8) {
            accumulatorBitCount -= 8;
            int childIndex = (code >>> accumulatorBitCount) & 255;
            a[] children = node.a();
            if (children == null) {
                k.n();
            }
            a child = children[childIndex];
            if (child == null) {
                child = new a();
                children[childIndex] = child;
            }
            node = child;
        }
        int shift = 8 - accumulatorBitCount;
        int start = (code << shift) & 255;
        int end = 1 << shift;
        a[] a2 = node.a();
        if (a2 == null) {
            k.n();
        }
        kotlin.collections.k.k(a2, terminal, start, start + end);
    }

    /* compiled from: Huffman.kt */
    public static final class a {
        @Nullable
        private final a[] a;
        private final int b;
        private final int c;

        @Nullable
        public final a[] a() {
            return this.a;
        }

        public final int b() {
            return this.b;
        }

        public final int c() {
            return this.c;
        }

        public a() {
            this.a = new a[256];
            this.b = 0;
            this.c = 0;
        }

        public a(int symbol, int bits) {
            this.a = null;
            this.b = symbol;
            int b2 = bits & 7;
            this.c = b2 == 0 ? 8 : b2;
        }
    }
}
