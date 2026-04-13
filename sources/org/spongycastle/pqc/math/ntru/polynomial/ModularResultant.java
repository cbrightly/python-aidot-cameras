package org.spongycastle.pqc.math.ntru.polynomial;

import java.math.BigInteger;
import org.spongycastle.pqc.math.ntru.euclid.BigIntEuclidean;

public class ModularResultant extends Resultant {
    BigInteger c;

    ModularResultant(BigIntPolynomial rho, BigInteger res, BigInteger modulus) {
        super(rho, res);
        this.c = modulus;
    }

    static ModularResultant a(ModularResultant modRes1, ModularResultant modRes2) {
        BigInteger mod1 = modRes1.c;
        BigInteger mod2 = modRes2.c;
        BigInteger prod = mod1.multiply(mod2);
        BigIntEuclidean er = BigIntEuclidean.a(mod2, mod1);
        BigIntPolynomial rho1 = (BigIntPolynomial) modRes1.a.clone();
        rho1.h(er.a.multiply(mod2));
        BigIntPolynomial rho2 = (BigIntPolynomial) modRes2.a.clone();
        rho2.h(er.b.multiply(mod1));
        rho1.a(rho2);
        rho1.f(prod);
        return new ModularResultant(rho1, (BigInteger) null, prod);
    }
}
