package zendesk.ui.android.conversation.imagecell;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageRoundedCorner.kt */
public final class d {
    private float a;
    private float b;
    private float c;
    private float d;

    public /* synthetic */ d(float f, float f2, float f3, float f4, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4);
    }

    private d(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        this.a = topLeft;
        this.b = topRight;
        this.c = bottomRight;
        this.d = bottomLeft;
    }

    public final float c() {
        return this.a;
    }

    public final void g(float f) {
        this.a = f;
    }

    public final float d() {
        return this.b;
    }

    public final void h(float f) {
        this.b = f;
    }

    public final float b() {
        return this.c;
    }

    public final void f(float f) {
        this.c = f;
    }

    public final float a() {
        return this.d;
    }

    public final void e(float f) {
        this.d = f;
    }

    /* compiled from: ImageRoundedCorner.kt */
    public static final class a {
        private final float a;
        private final float b;
        @NotNull
        private final a c;
        private final boolean d;
        @NotNull
        private final d e;

        /* renamed from: zendesk.ui.android.conversation.imagecell.d$a$a  reason: collision with other inner class name */
        /* compiled from: ImageRoundedCorner.kt */
        public final /* synthetic */ class C0569a {
            public static final /* synthetic */ int[] a;

            static {
                int[] iArr = new int[a.values().length];
                iArr[a.INBOUND_SINGLE.ordinal()] = 1;
                iArr[a.INBOUND_TOP.ordinal()] = 2;
                iArr[a.INBOUND_MIDDLE.ordinal()] = 3;
                iArr[a.INBOUND_BOTTOM.ordinal()] = 4;
                iArr[a.OUTBOUND_SINGLE.ordinal()] = 5;
                iArr[a.OUTBOUND_TOP.ordinal()] = 6;
                iArr[a.OUTBOUND_MIDDLE.ordinal()] = 7;
                iArr[a.OUTBOUND_BOTTOM.ordinal()] = 8;
                a = iArr;
            }
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return k.a(Float.valueOf(this.a), Float.valueOf(aVar.a)) && k.a(Float.valueOf(this.b), Float.valueOf(aVar.b)) && this.c == aVar.c && this.d == aVar.d;
        }

        public int hashCode() {
            int floatToIntBits = ((((Float.floatToIntBits(this.a) * 31) + Float.floatToIntBits(this.b)) * 31) + this.c.hashCode()) * 31;
            boolean z = this.d;
            if (z) {
                z = true;
            }
            return floatToIntBits + (z ? 1 : 0);
        }

        @NotNull
        public String toString() {
            return "Builder(cellRadius=" + this.a + ", smallCellRadius=" + this.b + ", direction=" + this.c + ", isLayoutDirectionLTR=" + this.d + ')';
        }

        public a(float cellRadius, float smallCellRadius, @NotNull a direction, boolean isLayoutDirectionLTR) {
            k.e(direction, "direction");
            this.a = cellRadius;
            this.b = smallCellRadius;
            this.c = direction;
            this.d = isLayoutDirectionLTR;
            this.e = new d(cellRadius, cellRadius, cellRadius, cellRadius, (DefaultConstructorMarker) null);
        }

        public final float b() {
            return this.b;
        }

        public final boolean g() {
            return this.d;
        }

        private final d e() {
            return j();
        }

        private final d f() {
            d dVar = this.e;
            d $this$inboundTop_u24lambda_u2d0 = dVar;
            if (g()) {
                $this$inboundTop_u24lambda_u2d0.e(b());
            } else {
                $this$inboundTop_u24lambda_u2d0.f(b());
            }
            return dVar;
        }

        private final d d() {
            d dVar = this.e;
            d $this$inboundMiddle_u24lambda_u2d1 = dVar;
            if (g()) {
                $this$inboundMiddle_u24lambda_u2d1.g(b());
                $this$inboundMiddle_u24lambda_u2d1.e(b());
            } else {
                $this$inboundMiddle_u24lambda_u2d1.h(b());
                $this$inboundMiddle_u24lambda_u2d1.f(b());
            }
            return dVar;
        }

        private final d c() {
            d dVar = this.e;
            d $this$inboundBottom_u24lambda_u2d2 = dVar;
            if (g()) {
                $this$inboundBottom_u24lambda_u2d2.g(b());
            } else {
                $this$inboundBottom_u24lambda_u2d2.h(b());
            }
            return dVar;
        }

        private final d j() {
            return this.e;
        }

        private final d k() {
            d dVar = this.e;
            d $this$outboundTop_u24lambda_u2d3 = dVar;
            if (g()) {
                $this$outboundTop_u24lambda_u2d3.f(b());
            } else {
                $this$outboundTop_u24lambda_u2d3.e(b());
            }
            return dVar;
        }

        private final d i() {
            d dVar = this.e;
            d $this$outboundMiddle_u24lambda_u2d4 = dVar;
            if (g()) {
                $this$outboundMiddle_u24lambda_u2d4.h(b());
                $this$outboundMiddle_u24lambda_u2d4.f(b());
            } else {
                $this$outboundMiddle_u24lambda_u2d4.g(b());
                $this$outboundMiddle_u24lambda_u2d4.e(b());
            }
            return dVar;
        }

        private final d h() {
            d dVar = this.e;
            d $this$outboundBottom_u24lambda_u2d5 = dVar;
            if (g()) {
                $this$outboundBottom_u24lambda_u2d5.h(b());
            } else {
                $this$outboundBottom_u24lambda_u2d5.g(b());
            }
            return dVar;
        }

        @NotNull
        public final d a() {
            switch (C0569a.a[this.c.ordinal()]) {
                case 1:
                    return e();
                case 2:
                    return f();
                case 3:
                    return d();
                case 4:
                    return c();
                case 5:
                    return j();
                case 6:
                    return k();
                case 7:
                    return i();
                case 8:
                    return h();
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
    }
}
