package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.model.p;

public final /* synthetic */ class d {
    public static final /* synthetic */ int[] a;
    public static final /* synthetic */ int[] b;

    static {
        int[] iArr = new int[p.values().length];
        a = iArr;
        iArr[p.INV.ordinal()] = 1;
        iArr[p.IN.ordinal()] = 2;
        iArr[p.OUT.ordinal()] = 3;
        int[] iArr2 = new int[h1.values().length];
        b = iArr2;
        iArr2[h1.INVARIANT.ordinal()] = 1;
        iArr2[h1.IN_VARIANCE.ordinal()] = 2;
        iArr2[h1.OUT_VARIANCE.ordinal()] = 3;
    }
}
