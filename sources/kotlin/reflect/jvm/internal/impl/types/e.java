package kotlin.reflect.jvm.internal.impl.types;

import kotlin.reflect.jvm.internal.impl.types.g;
import kotlin.reflect.jvm.internal.impl.types.model.p;

public final /* synthetic */ class e {
    public static final /* synthetic */ int[] a;
    public static final /* synthetic */ int[] b;

    static {
        int[] iArr = new int[p.values().length];
        a = iArr;
        iArr[p.INV.ordinal()] = 1;
        iArr[p.OUT.ordinal()] = 2;
        iArr[p.IN.ordinal()] = 3;
        int[] iArr2 = new int[g.a.values().length];
        b = iArr2;
        iArr2[g.a.CHECK_ONLY_LOWER.ordinal()] = 1;
        iArr2[g.a.CHECK_SUBTYPE_AND_LOWER.ordinal()] = 2;
        iArr2[g.a.SKIP_LOWER.ordinal()] = 3;
    }
}
