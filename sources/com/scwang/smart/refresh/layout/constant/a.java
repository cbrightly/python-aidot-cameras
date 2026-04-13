package com.scwang.smart.refresh.layout.constant;

/* compiled from: DimensionStatus */
public class a {
    public static final a a;
    public static final a b;
    public static final a c;
    public static final a d;
    public static final a e;
    public static final a f;
    public static final a g;
    public static final a h;
    public static final a i;
    public static final a j;
    public static final a k;
    public static final a l;
    public static final a[] m;
    public final int n;
    public final boolean o;

    static {
        a aVar = new a(0, false);
        a = aVar;
        a aVar2 = new a(1, true);
        b = aVar2;
        a aVar3 = new a(2, false);
        c = aVar3;
        a aVar4 = new a(3, true);
        d = aVar4;
        a aVar5 = new a(4, false);
        e = aVar5;
        a aVar6 = new a(5, true);
        f = aVar6;
        a aVar7 = new a(6, false);
        g = aVar7;
        a aVar8 = new a(7, true);
        h = aVar8;
        a aVar9 = new a(8, false);
        i = aVar9;
        a aVar10 = new a(9, true);
        j = aVar10;
        a aVar11 = new a(10, false);
        k = aVar11;
        a aVar12 = new a(10, true);
        l = aVar12;
        m = new a[]{aVar, aVar2, aVar3, aVar4, aVar5, aVar6, aVar7, aVar8, aVar9, aVar10, aVar11, aVar12};
    }

    private a(int ordinal, boolean notified) {
        this.n = ordinal;
        this.o = notified;
    }

    public a b() {
        if (!this.o) {
            return m[this.n + 1];
        }
        return this;
    }

    public boolean a(a status) {
        int i2 = this.n;
        int i3 = status.n;
        return i2 < i3 || ((!this.o || j == this) && i2 == i3);
    }
}
