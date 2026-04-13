package io.ktor.util;

import io.ktor.util.n;

public final /* synthetic */ class o {
    public static final /* synthetic */ int[] a;
    public static final /* synthetic */ int[] b;

    static {
        int[] iArr = new int[n.a.values().length];
        a = iArr;
        n.a aVar = n.a.None;
        iArr[aVar.ordinal()] = 1;
        int[] iArr2 = new int[n.a.values().length];
        b = iArr2;
        iArr2[aVar.ordinal()] = 1;
        iArr2[n.a.Graceful.ordinal()] = 2;
        iArr2[n.a.Completed.ordinal()] = 3;
    }
}
