package kotlin.reflect.jvm.internal.impl.builtins.functions;

import kotlin.reflect.jvm.internal.impl.builtins.functions.b;

public final /* synthetic */ class c {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[b.d.values().length];
        a = iArr;
        iArr[b.d.Function.ordinal()] = 1;
        iArr[b.d.KFunction.ordinal()] = 2;
        iArr[b.d.SuspendFunction.ordinal()] = 3;
        iArr[b.d.KSuspendFunction.ordinal()] = 4;
    }
}
