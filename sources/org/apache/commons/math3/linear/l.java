package org.apache.commons.math3.linear;

/* compiled from: RealLinearOperator */
public abstract class l {
    public abstract int getColumnDimension();

    public abstract int getRowDimension();

    public abstract q operate(q qVar);

    public q operateTranspose(q x) {
        throw new UnsupportedOperationException();
    }

    public boolean isTransposable() {
        return false;
    }
}
