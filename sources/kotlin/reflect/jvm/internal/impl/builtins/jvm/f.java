package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import kotlin.reflect.jvm.internal.impl.builtins.jvm.e;

public final /* synthetic */ class f {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[e.a.values().length];
        a = iArr;
        iArr[e.a.FROM_DEPENDENCIES.ordinal()] = 1;
        iArr[e.a.FROM_CLASS_LOADER.ordinal()] = 2;
        iArr[e.a.FALLBACK.ordinal()] = 3;
    }
}
