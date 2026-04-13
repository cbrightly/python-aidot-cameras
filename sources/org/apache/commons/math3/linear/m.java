package org.apache.commons.math3.linear;

/* compiled from: RealMatrix */
public interface m extends b {
    m add(m mVar);

    void copySubMatrix(int i, int i2, int i3, int i4, double[][] dArr);

    double[][] getData();

    double getEntry(int i, int i2);

    m multiply(m mVar);

    q operate(q qVar);

    m scalarMultiply(double d);

    void setEntry(int i, int i2, double d);

    m subtract(m mVar);

    m transpose();

    double walkInOptimizedOrder(n nVar);
}
