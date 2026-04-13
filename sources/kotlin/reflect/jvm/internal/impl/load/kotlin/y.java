package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.h1;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypeMappingMode.kt */
public final class y {
    @NotNull
    public static final y a;
    @NotNull
    public static final y b = new y(false, true, false, false, false, (y) null, false, (y) null, (y) null, 509, (DefaultConstructorMarker) null);
    @NotNull
    public static final y c;
    @NotNull
    public static final y d;
    @NotNull
    public static final y e;
    @NotNull
    public static final y f;
    @NotNull
    public static final y g;
    public static final a h = new a((DefaultConstructorMarker) null);
    private final boolean i;
    private final boolean j;
    private final boolean k;
    private final boolean l;
    private final boolean m;
    private final y n;
    private final boolean o;
    private final y p;
    private final y q;

    private y(boolean needPrimitiveBoxing, boolean needInlineClassWrapping, boolean isForAnnotationParameter, boolean skipDeclarationSiteWildcards, boolean skipDeclarationSiteWildcardsIfPossible, y genericArgumentMode, boolean kotlinCollectionsToJavaCollections, y genericContravariantArgumentMode, y genericInvariantArgumentMode) {
        this.i = needPrimitiveBoxing;
        this.j = needInlineClassWrapping;
        this.k = isForAnnotationParameter;
        this.l = skipDeclarationSiteWildcards;
        this.m = skipDeclarationSiteWildcardsIfPossible;
        this.n = genericArgumentMode;
        this.o = kotlinCollectionsToJavaCollections;
        this.p = genericContravariantArgumentMode;
        this.q = genericInvariantArgumentMode;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* synthetic */ y(boolean r10, boolean r11, boolean r12, boolean r13, boolean r14, kotlin.reflect.jvm.internal.impl.load.kotlin.y r15, boolean r16, kotlin.reflect.jvm.internal.impl.load.kotlin.y r17, kotlin.reflect.jvm.internal.impl.load.kotlin.y r18, int r19, kotlin.jvm.internal.DefaultConstructorMarker r20) {
        /*
            r9 = this;
            r0 = r19
            r1 = r0 & 1
            r2 = 1
            if (r1 == 0) goto L_0x0009
            r1 = r2
            goto L_0x000a
        L_0x0009:
            r1 = r10
        L_0x000a:
            r3 = r0 & 2
            if (r3 == 0) goto L_0x0010
            r3 = r2
            goto L_0x0011
        L_0x0010:
            r3 = r11
        L_0x0011:
            r4 = r0 & 4
            r5 = 0
            if (r4 == 0) goto L_0x0018
            r4 = r5
            goto L_0x0019
        L_0x0018:
            r4 = r12
        L_0x0019:
            r6 = r0 & 8
            if (r6 == 0) goto L_0x001f
            r6 = r5
            goto L_0x0020
        L_0x001f:
            r6 = r13
        L_0x0020:
            r7 = r0 & 16
            if (r7 == 0) goto L_0x0025
            goto L_0x0026
        L_0x0025:
            r5 = r14
        L_0x0026:
            r7 = r0 & 32
            if (r7 == 0) goto L_0x002c
            r7 = 0
            goto L_0x002d
        L_0x002c:
            r7 = r15
        L_0x002d:
            r8 = r0 & 64
            if (r8 == 0) goto L_0x0032
            goto L_0x0034
        L_0x0032:
            r2 = r16
        L_0x0034:
            r8 = r0 & 128(0x80, float:1.794E-43)
            if (r8 == 0) goto L_0x003a
            r8 = r7
            goto L_0x003c
        L_0x003a:
            r8 = r17
        L_0x003c:
            r0 = r0 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto L_0x0042
            r0 = r7
            goto L_0x0044
        L_0x0042:
            r0 = r18
        L_0x0044:
            r10 = r9
            r11 = r1
            r12 = r3
            r13 = r4
            r14 = r6
            r15 = r5
            r16 = r7
            r17 = r2
            r18 = r8
            r19 = r0
            r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18, r19)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.kotlin.y.<init>(boolean, boolean, boolean, boolean, boolean, kotlin.reflect.jvm.internal.impl.load.kotlin.y, boolean, kotlin.reflect.jvm.internal.impl.load.kotlin.y, kotlin.reflect.jvm.internal.impl.load.kotlin.y, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final boolean c() {
        return this.i;
    }

    public final boolean b() {
        return this.j;
    }

    public final boolean d() {
        return this.k;
    }

    public final boolean a() {
        return this.o;
    }

    /* compiled from: TypeMappingMode.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        y yVar = new y(false, false, false, false, false, (y) null, false, (y) null, (y) null, 511, (DefaultConstructorMarker) null);
        a = yVar;
        y yVar2 = yVar;
        c = new y(false, false, false, false, false, yVar2, false, (y) null, (y) null, 476, (DefaultConstructorMarker) null);
        d = new y(false, true, false, false, false, yVar2, false, (y) null, (y) null, 476, (DefaultConstructorMarker) null);
        e = new y(false, false, false, true, false, yVar2, false, (y) null, (y) null, 471, (DefaultConstructorMarker) null);
        f = new y(false, false, false, true, false, yVar2, false, (y) null, (y) null, 407, (DefaultConstructorMarker) null);
        g = new y(false, false, true, false, false, new y(false, false, true, false, false, yVar2, false, (y) null, (y) null, 475, (DefaultConstructorMarker) null), false, (y) null, (y) null, 472, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final y e(@NotNull h1 effectiveVariance) {
        k.f(effectiveVariance, "effectiveVariance");
        switch (z.a[effectiveVariance.ordinal()]) {
            case 1:
                y yVar = this.p;
                if (yVar != null) {
                    return yVar;
                }
                break;
            case 2:
                y yVar2 = this.q;
                if (yVar2 != null) {
                    return yVar2;
                }
                break;
            default:
                y yVar3 = this.n;
                if (yVar3 != null) {
                    return yVar3;
                }
                break;
        }
        return this;
    }

    @NotNull
    public final y f() {
        return new y(this.i, true, this.k, this.l, this.m, this.n, this.o, this.p, this.q);
    }
}
