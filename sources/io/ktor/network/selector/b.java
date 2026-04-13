package io.ktor.network.selector;

public final /* synthetic */ class b {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[j.values().length];
        a = iArr;
        iArr[j.READ.ordinal()] = 1;
        iArr[j.WRITE.ordinal()] = 2;
        iArr[j.ACCEPT.ordinal()] = 3;
        iArr[j.CONNECT.ordinal()] = 4;
    }
}
