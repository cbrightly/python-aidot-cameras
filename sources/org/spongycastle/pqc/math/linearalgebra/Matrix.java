package org.spongycastle.pqc.math.linearalgebra;

public abstract class Matrix {
    protected int a;
    protected int b;

    public int b() {
        return this.a;
    }

    public int a() {
        return this.b;
    }
}
