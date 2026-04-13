package org.spongycastle.math.field;

import java.math.BigInteger;
import org.spongycastle.util.Integers;

public class GenericPolynomialExtensionField implements PolynomialExtensionField {
    protected final FiniteField a;
    protected final Polynomial b;

    GenericPolynomialExtensionField(FiniteField subfield, Polynomial polynomial) {
        this.a = subfield;
        this.b = polynomial;
    }

    public BigInteger b() {
        return this.a.b();
    }

    public int a() {
        return this.a.a() * this.b.b();
    }

    public Polynomial c() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GenericPolynomialExtensionField)) {
            return false;
        }
        GenericPolynomialExtensionField other = (GenericPolynomialExtensionField) obj;
        if (!this.a.equals(other.a) || !this.b.equals(other.b)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.a.hashCode() ^ Integers.a(this.b.hashCode(), 16);
    }
}
