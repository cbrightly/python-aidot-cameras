package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import kotlin.reflect.jvm.internal.impl.builtins.jvm.h;

public final /* synthetic */ class i {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[h.b.values().length];
        a = iArr;
        iArr[h.b.BLACK_LIST.ordinal()] = 1;
        iArr[h.b.NOT_CONSIDERED.ordinal()] = 2;
        iArr[h.b.DROP.ordinal()] = 3;
        iArr[h.b.WHITE_LIST.ordinal()] = 4;
    }
}
