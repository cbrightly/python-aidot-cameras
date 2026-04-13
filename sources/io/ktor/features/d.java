package io.ktor.features;

import org.slf4j.event.b;

public final /* synthetic */ class d {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[b.values().length];
        a = iArr;
        iArr[b.ERROR.ordinal()] = 1;
        iArr[b.WARN.ordinal()] = 2;
        iArr[b.INFO.ordinal()] = 3;
        iArr[b.DEBUG.ordinal()] = 4;
        iArr[b.TRACE.ordinal()] = 5;
    }
}
