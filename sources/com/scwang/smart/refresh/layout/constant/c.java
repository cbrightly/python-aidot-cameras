package com.scwang.smart.refresh.layout.constant;

/* compiled from: SpinnerStyle */
public class c {
    public static final c a;
    @Deprecated
    public static final c b;
    public static final c c;
    public static final c d;
    public static final c e;
    public static final c[] f;
    public final int g;
    public final boolean h;
    public final boolean i;

    static {
        c cVar = new c(0, true, false);
        a = cVar;
        c cVar2 = new c(1, true, true);
        b = cVar2;
        c cVar3 = new c(2, false, false);
        c = cVar3;
        c cVar4 = new c(3, true, false);
        d = cVar4;
        c cVar5 = new c(4, true, false);
        e = cVar5;
        f = new c[]{cVar, cVar2, cVar3, cVar4, cVar5};
    }

    protected c(int ordinal, boolean front, boolean scale) {
        this.g = ordinal;
        this.h = front;
        this.i = scale;
    }
}
