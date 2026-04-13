package kotlin.text;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.ranges.i;
import kotlin.sequences.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Strings.kt */
public final class d implements h<i> {
    /* access modifiers changed from: private */
    public final CharSequence a;
    /* access modifiers changed from: private */
    public final int b;
    /* access modifiers changed from: private */
    public final int c;
    /* access modifiers changed from: private */
    public final p<CharSequence, Integer, n<Integer, Integer>> d;

    public d(@NotNull CharSequence input, int startIndex, int limit, @NotNull p<? super CharSequence, ? super Integer, n<Integer, Integer>> getNextMatch) {
        k.e(input, "input");
        k.e(getNextMatch, "getNextMatch");
        this.a = input;
        this.b = startIndex;
        this.c = limit;
        this.d = getNextMatch;
    }

    /* compiled from: Strings.kt */
    public static final class a implements Iterator<i>, kotlin.jvm.internal.markers.a {
        private int c = -1;
        private int d;
        private int f;
        @Nullable
        private i q;
        private int x;
        final /* synthetic */ d y;

        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        a(d this$0) {
            this.y = this$0;
            int h = kotlin.ranges.n.h(this$0.b, 0, this$0.a.length());
            this.d = h;
            this.f = h;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0021, code lost:
            if (r0 < kotlin.text.d.d(r6.y)) goto L_0x0023;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final void b() {
            /*
                r6 = this;
                int r0 = r6.f
                r1 = 0
                if (r0 >= 0) goto L_0x000c
                r6.c = r1
                r0 = 0
                r6.q = r0
                goto L_0x00a1
            L_0x000c:
                kotlin.text.d r0 = r6.y
                int r0 = r0.c
                r2 = -1
                r3 = 1
                if (r0 <= 0) goto L_0x0023
                int r0 = r6.x
                int r0 = r0 + r3
                r6.x = r0
                kotlin.text.d r4 = r6.y
                int r4 = r4.c
                if (r0 >= r4) goto L_0x0031
            L_0x0023:
                int r0 = r6.f
                kotlin.text.d r4 = r6.y
                java.lang.CharSequence r4 = r4.a
                int r4 = r4.length()
                if (r0 <= r4) goto L_0x0047
            L_0x0031:
                int r0 = r6.d
                kotlin.ranges.i r1 = new kotlin.ranges.i
                kotlin.text.d r4 = r6.y
                java.lang.CharSequence r4 = r4.a
                int r4 = kotlin.text.x.Z(r4)
                r1.<init>(r0, r4)
                r6.q = r1
                r6.f = r2
                goto L_0x009d
            L_0x0047:
                kotlin.text.d r0 = r6.y
                kotlin.jvm.functions.p r0 = r0.d
                kotlin.text.d r4 = r6.y
                java.lang.CharSequence r4 = r4.a
                int r5 = r6.f
                java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
                java.lang.Object r0 = r0.invoke(r4, r5)
                kotlin.n r0 = (kotlin.n) r0
                if (r0 != 0) goto L_0x0077
                int r1 = r6.d
                kotlin.ranges.i r4 = new kotlin.ranges.i
                kotlin.text.d r5 = r6.y
                java.lang.CharSequence r5 = r5.a
                int r5 = kotlin.text.x.Z(r5)
                r4.<init>(r1, r5)
                r6.q = r4
                r6.f = r2
                goto L_0x009d
            L_0x0077:
                java.lang.Object r2 = r0.component1()
                java.lang.Number r2 = (java.lang.Number) r2
                int r2 = r2.intValue()
                java.lang.Object r4 = r0.component2()
                java.lang.Number r4 = (java.lang.Number) r4
                int r4 = r4.intValue()
                int r5 = r6.d
                kotlin.ranges.i r5 = kotlin.ranges.n.l(r5, r2)
                r6.q = r5
                int r5 = r2 + r4
                r6.d = r5
                if (r4 != 0) goto L_0x009a
                r1 = r3
            L_0x009a:
                int r5 = r5 + r1
                r6.f = r5
            L_0x009d:
                r6.c = r3
            L_0x00a1:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.text.d.a.b():void");
        }

        @NotNull
        /* renamed from: e */
        public i next() {
            if (this.c == -1) {
                b();
            }
            if (this.c != 0) {
                i result = this.q;
                if (result != null) {
                    this.q = null;
                    this.c = -1;
                    return result;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.ranges.IntRange");
            }
            throw new NoSuchElementException();
        }

        public boolean hasNext() {
            if (this.c == -1) {
                b();
            }
            return this.c == 1;
        }
    }

    @NotNull
    public Iterator<i> iterator() {
        return new a(this);
    }
}
