package kotlin.jvm.internal;

import kotlin.reflect.r;

public final /* synthetic */ class f0 {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[r.values().length];
        a = iArr;
        iArr[r.INVARIANT.ordinal()] = 1;
        iArr[r.IN.ordinal()] = 2;
        iArr[r.OUT.ordinal()] = 3;
    }
}
