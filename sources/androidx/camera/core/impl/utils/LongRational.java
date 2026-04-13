package androidx.camera.core.impl.utils;

import androidx.annotation.NonNull;

public final class LongRational {
    private final long mDenominator;
    private final long mNumerator;

    LongRational(long nominator, long denominator) {
        this.mNumerator = nominator;
        this.mDenominator = denominator;
    }

    LongRational(double value) {
        this((long) (10000.0d * value), 10000);
    }

    /* access modifiers changed from: package-private */
    public long getNumerator() {
        return this.mNumerator;
    }

    /* access modifiers changed from: package-private */
    public long getDenominator() {
        return this.mDenominator;
    }

    /* access modifiers changed from: package-private */
    public double toDouble() {
        return ((double) this.mNumerator) / ((double) this.mDenominator);
    }

    @NonNull
    public String toString() {
        return this.mNumerator + "/" + this.mDenominator;
    }
}
