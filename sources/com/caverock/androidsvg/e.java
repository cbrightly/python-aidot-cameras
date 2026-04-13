package com.caverock.androidsvg;

/* compiled from: PreserveAspectRatio */
public class e {
    public static final e a = new e((a) null, (b) null);
    public static final e b = new e(a.none, (b) null);
    public static final e c;
    public static final e d;
    public static final e e;
    public static final e f;
    public static final e g;
    public static final e h;
    public static final e i;
    private a j;
    private b k;

    /* compiled from: PreserveAspectRatio */
    public enum a {
        none,
        xMinYMin,
        xMidYMin,
        xMaxYMin,
        xMinYMid,
        xMidYMid,
        xMaxYMid,
        xMinYMax,
        xMidYMax,
        xMaxYMax
    }

    /* compiled from: PreserveAspectRatio */
    public enum b {
        meet,
        slice
    }

    static {
        a aVar = a.xMidYMid;
        b bVar = b.meet;
        c = new e(aVar, bVar);
        a aVar2 = a.xMinYMin;
        d = new e(aVar2, bVar);
        e = new e(a.xMaxYMax, bVar);
        f = new e(a.xMidYMin, bVar);
        g = new e(a.xMidYMax, bVar);
        b bVar2 = b.slice;
        h = new e(aVar, bVar2);
        i = new e(aVar2, bVar2);
    }

    e(a alignment, b scale) {
        this.j = alignment;
        this.k = scale;
    }

    public a a() {
        return this.j;
    }

    public b b() {
        return this.k;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        e other = (e) obj;
        if (this.j == other.j && this.k == other.k) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.j + " " + this.k;
    }
}
