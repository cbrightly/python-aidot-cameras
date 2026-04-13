package kotlin.reflect.full;

import kotlin.reflect.r;

public final /* synthetic */ class c {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[r.values().length];
        a = iArr;
        iArr[r.INVARIANT.ordinal()] = 1;
        iArr[r.IN.ordinal()] = 2;
        iArr[r.OUT.ordinal()] = 3;
    }
}
