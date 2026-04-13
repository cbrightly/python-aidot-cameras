package kotlin.reflect.jvm.internal;

import kotlin.reflect.jvm.internal.impl.types.h1;

public final /* synthetic */ class v {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[h1.values().length];
        a = iArr;
        iArr[h1.INVARIANT.ordinal()] = 1;
        iArr[h1.IN_VARIANCE.ordinal()] = 2;
        iArr[h1.OUT_VARIANCE.ordinal()] = 3;
    }
}
