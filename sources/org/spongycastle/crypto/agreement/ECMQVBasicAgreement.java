package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.MQVPrivateParameters;
import org.spongycastle.crypto.params.MQVPublicParameters;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.Properties;

public class ECMQVBasicAgreement implements BasicAgreement {
    MQVPrivateParameters a;

    public void a(CipherParameters key) {
        this.a = (MQVPrivateParameters) key;
    }

    public int b() {
        return (this.a.c().b().a().t() + 7) / 8;
    }

    public BigInteger c(CipherParameters pubKey) {
        if (!Properties.d("org.spongycastle.ec.disable_mqv")) {
            MQVPublicParameters pubParams = (MQVPublicParameters) pubKey;
            ECPrivateKeyParameters staticPrivateKey = this.a.c();
            ECDomainParameters parameters = staticPrivateKey.b();
            if (parameters.equals(pubParams.b().b())) {
                ECPoint agreement = d(parameters, staticPrivateKey, this.a.a(), this.a.b(), pubParams.b(), pubParams.a()).y();
                if (!agreement.t()) {
                    return agreement.f().t();
                }
                throw new IllegalStateException("Infinity is not a valid agreement value for MQV");
            }
            throw new IllegalStateException("ECMQV public key components have wrong domain parameters");
        }
        throw new IllegalStateException("ECMQV explicitly disabled");
    }

    private ECPoint d(ECDomainParameters parameters, ECPrivateKeyParameters d1U, ECPrivateKeyParameters d2U, ECPublicKeyParameters Q2U, ECPublicKeyParameters Q1V, ECPublicKeyParameters Q2V) {
        BigInteger n = parameters.d();
        int e = (n.bitLength() + 1) / 2;
        BigInteger powE = ECConstants.b.shiftLeft(e);
        ECCurve curve = parameters.a();
        ECPoint[] points = {ECAlgorithms.h(curve, Q2U.c()), ECAlgorithms.h(curve, Q1V.c()), ECAlgorithms.h(curve, Q2V.c())};
        curve.A(points);
        ECPoint q2u = points[0];
        ECPoint q1v = points[1];
        ECPoint q2v = points[2];
        BigInteger s = d1U.c().multiply(q2u.f().t().mod(powE).setBit(e)).add(d2U.c()).mod(n);
        BigInteger Q2VBar = q2v.f().t().mod(powE).setBit(e);
        BigInteger hs = parameters.c().multiply(s).mod(n);
        int i = e;
        return ECAlgorithms.o(q1v, Q2VBar.multiply(hs).mod(n), q2v, hs);
    }
}
