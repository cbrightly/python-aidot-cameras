package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECKeyParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECPoint;

public class ECNRSigner implements DSA {
    private boolean g;
    private ECKeyParameters h;
    private SecureRandom i;

    public void a(boolean forSigning, CipherParameters param) {
        this.g = forSigning;
        if (!forSigning) {
            this.h = (ECPublicKeyParameters) param;
        } else if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            this.i = rParam.b();
            this.h = (ECPrivateKeyParameters) rParam.a();
        } else {
            this.i = new SecureRandom();
            this.h = (ECPrivateKeyParameters) param;
        }
    }

    public BigInteger[] b(byte[] digest) {
        AsymmetricCipherKeyPair tempPair;
        BigInteger r;
        if (this.g) {
            BigInteger n = ((ECPrivateKeyParameters) this.h).b().d();
            int nBitLength = n.bitLength();
            BigInteger e = new BigInteger(1, digest);
            int eBitLength = e.bitLength();
            ECPrivateKeyParameters privKey = (ECPrivateKeyParameters) this.h;
            if (eBitLength <= nBitLength) {
                do {
                    ECKeyPairGenerator keyGen = new ECKeyPairGenerator();
                    keyGen.c(new ECKeyGenerationParameters(privKey.b(), this.i));
                    tempPair = keyGen.a();
                    r = ((ECPublicKeyParameters) tempPair.b()).c().f().t().add(e).mod(n);
                } while (r.equals(ECConstants.a));
                return new BigInteger[]{r, ((ECPrivateKeyParameters) tempPair.a()).c().subtract(r.multiply(privKey.c())).mod(n)};
            }
            throw new DataLengthException("input too large for ECNR key.");
        }
        throw new IllegalStateException("not initialised for signing");
    }

    public boolean c(byte[] digest, BigInteger r, BigInteger s) {
        if (!this.g) {
            ECPublicKeyParameters pubKey = (ECPublicKeyParameters) this.h;
            BigInteger n = pubKey.b().d();
            int nBitLength = n.bitLength();
            BigInteger e = new BigInteger(1, digest);
            if (e.bitLength() > nBitLength) {
                throw new DataLengthException("input too large for ECNR key.");
            } else if (r.compareTo(ECConstants.b) < 0 || r.compareTo(n) >= 0 || s.compareTo(ECConstants.a) < 0 || s.compareTo(n) >= 0) {
                return false;
            } else {
                ECPoint P = ECAlgorithms.o(pubKey.b().b(), s, pubKey.c(), r).y();
                if (P.t()) {
                    return false;
                }
                return r.subtract(P.f().t()).mod(n).equals(e);
            }
        } else {
            throw new IllegalStateException("not initialised for verifying");
        }
    }
}
