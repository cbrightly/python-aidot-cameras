package kotlin.reflect.jvm.internal;

import kotlin.reflect.j;
import kotlin.reflect.jvm.internal.impl.types.h1;

public final /* synthetic */ class c0 {
    public static final /* synthetic */ int[] a;
    public static final /* synthetic */ int[] b;

    static {
        int[] iArr = new int[j.a.values().length];
        a = iArr;
        iArr[j.a.EXTENSION_RECEIVER.ordinal()] = 1;
        iArr[j.a.INSTANCE.ordinal()] = 2;
        iArr[j.a.VALUE.ordinal()] = 3;
        int[] iArr2 = new int[h1.values().length];
        b = iArr2;
        iArr2[h1.INVARIANT.ordinal()] = 1;
        iArr2[h1.IN_VARIANCE.ordinal()] = 2;
        iArr2[h1.OUT_VARIANCE.ordinal()] = 3;
    }
}
