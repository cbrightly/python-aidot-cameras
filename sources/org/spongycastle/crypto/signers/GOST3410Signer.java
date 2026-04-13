package org.spongycastle.crypto.signers;

import androidx.exifinterface.media.ExifInterface;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.params.GOST3410KeyParameters;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.spongycastle.crypto.params.GOST3410PublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class GOST3410Signer implements DSA {
    GOST3410KeyParameters g;
    SecureRandom h;

    public void a(boolean forSigning, CipherParameters param) {
        if (!forSigning) {
            this.g = (GOST3410PublicKeyParameters) param;
        } else if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            this.h = rParam.b();
            this.g = (GOST3410PrivateKeyParameters) rParam.a();
        } else {
            this.h = new SecureRandom();
            this.g = (GOST3410PrivateKeyParameters) param;
        }
    }

    public BigInteger[] b(byte[] message) {
        BigInteger k;
        byte[] mRev = new byte[message.length];
        for (int i = 0; i != mRev.length; i++) {
            mRev[i] = message[(mRev.length - 1) - i];
        }
        BigInteger m = new BigInteger(1, mRev);
        GOST3410Parameters params = this.g.b();
        do {
            k = new BigInteger(params.c().bitLength(), this.h);
        } while (k.compareTo(params.c()) >= 0);
        BigInteger r = params.a().modPow(k, params.b()).mod(params.c());
        return new BigInteger[]{r, k.multiply(m).add(((GOST3410PrivateKeyParameters) this.g).c().multiply(r)).mod(params.c())};
    }

    public boolean c(byte[] message, BigInteger r, BigInteger s) {
        byte[] mRev = new byte[message.length];
        for (int i = 0; i != mRev.length; i++) {
            mRev[i] = message[(mRev.length - 1) - i];
        }
        BigInteger m = new BigInteger(1, mRev);
        GOST3410Parameters params = this.g.b();
        BigInteger zero = BigInteger.valueOf(0);
        if (zero.compareTo(r) >= 0 || params.c().compareTo(r) <= 0 || zero.compareTo(s) >= 0 || params.c().compareTo(s) <= 0) {
            return false;
        }
        BigInteger v = m.modPow(params.c().subtract(new BigInteger(ExifInterface.GPS_MEASUREMENT_2D)), params.c());
        return params.a().modPow(s.multiply(v).mod(params.c()), params.b()).multiply(((GOST3410PublicKeyParameters) this.g).c().modPow(params.c().subtract(r).multiply(v).mod(params.c()), params.b())).mod(params.b()).mod(params.c()).equals(r);
    }
}
