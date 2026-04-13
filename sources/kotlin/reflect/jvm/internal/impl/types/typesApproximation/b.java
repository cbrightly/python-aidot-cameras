package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import kotlin.reflect.jvm.internal.impl.types.h1;

public final /* synthetic */ class b {
    public static final /* synthetic */ int[] a;
    public static final /* synthetic */ int[] b;

    static {
        int[] iArr = new int[h1.values().length];
        a = iArr;
        iArr[h1.INVARIANT.ordinal()] = 1;
        h1 h1Var = h1.IN_VARIANCE;
        iArr[h1Var.ordinal()] = 2;
        h1 h1Var2 = h1.OUT_VARIANCE;
        iArr[h1Var2.ordinal()] = 3;
        int[] iArr2 = new int[h1.values().length];
        b = iArr2;
        iArr2[h1Var.ordinal()] = 1;
        iArr2[h1Var2.ordinal()] = 2;
    }
}
