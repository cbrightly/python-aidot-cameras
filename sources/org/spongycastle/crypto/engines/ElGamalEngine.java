package org.spongycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.ElGamalKeyParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.BigIntegers;

public class ElGamalEngine implements AsymmetricBlockCipher {
    private static final BigInteger a = BigInteger.valueOf(0);
    private static final BigInteger b = BigInteger.valueOf(1);
    private static final BigInteger c = BigInteger.valueOf(2);
    private ElGamalKeyParameters d;
    private SecureRandom e;
    private boolean f;
    private int g;

    public void a(boolean forEncryption, CipherParameters param) {
        if (param instanceof ParametersWithRandom) {
            ParametersWithRandom p = (ParametersWithRandom) param;
            this.d = (ElGamalKeyParameters) p.a();
            this.e = p.b();
        } else {
            this.d = (ElGamalKeyParameters) param;
            this.e = new SecureRandom();
        }
        this.f = forEncryption;
        this.g = this.d.b().c().bitLength();
        if (forEncryption) {
            if (!(this.d instanceof ElGamalPublicKeyParameters)) {
                throw new IllegalArgumentException("ElGamalPublicKeyParameters are required for encryption.");
            }
        } else if (!(this.d instanceof ElGamalPrivateKeyParameters)) {
            throw new IllegalArgumentException("ElGamalPrivateKeyParameters are required for decryption.");
        }
    }

    public int c() {
        if (this.f) {
            return (this.g - 1) / 8;
        }
        return ((this.g + 7) / 8) * 2;
    }

    public int b() {
        if (this.f) {
            return ((this.g + 7) / 8) * 2;
        }
        return (this.g - 1) / 8;
    }

    public byte[] d(byte[] in, int inOff, int inLen) {
        int maxLength;
        byte[] block;
        byte[] bArr = in;
        int i = inOff;
        int i2 = inLen;
        if (this.d != null) {
            int maxLength2 = 1;
            if (this.f) {
                maxLength = ((this.g - 1) + 7) / 8;
            } else {
                maxLength = c();
            }
            if (i2 <= maxLength) {
                BigInteger p = this.d.b().c();
                boolean z = false;
                if (this.d instanceof ElGamalPrivateKeyParameters) {
                    byte[] in1 = new byte[(i2 / 2)];
                    byte[] in2 = new byte[(i2 / 2)];
                    System.arraycopy(bArr, i, in1, 0, in1.length);
                    System.arraycopy(bArr, in1.length + i, in2, 0, in2.length);
                    BigInteger gamma = new BigInteger(1, in1);
                    return BigIntegers.b(gamma.modPow(p.subtract(b).subtract(((ElGamalPrivateKeyParameters) this.d).c()), p).multiply(new BigInteger(1, in2)).mod(p));
                }
                if (i == 0 && i2 == bArr.length) {
                    block = in;
                } else {
                    block = new byte[i2];
                    System.arraycopy(bArr, i, block, 0, i2);
                }
                BigInteger input = new BigInteger(1, block);
                if (input.compareTo(p) < 0) {
                    ElGamalPublicKeyParameters pub2 = (ElGamalPublicKeyParameters) this.d;
                    int pBitLength = p.bitLength();
                    BigInteger k = new BigInteger(pBitLength, this.e);
                    while (true) {
                        if (!k.equals(a) && k.compareTo(p.subtract(c)) <= 0) {
                            break;
                        }
                        k = new BigInteger(pBitLength, this.e);
                        int i3 = inOff;
                        int i4 = inLen;
                        z = z;
                        maxLength2 = maxLength2;
                        maxLength = maxLength;
                        byte[] bArr2 = in;
                    }
                    BigInteger gamma2 = this.d.b().a().modPow(k, p);
                    BigInteger phi = input.multiply(pub2.c().modPow(k, p)).mod(p);
                    byte[] out1 = gamma2.toByteArray();
                    byte[] out2 = phi.toByteArray();
                    byte[] output = new byte[b()];
                    if (out1.length > output.length / 2) {
                        int i5 = maxLength;
                        System.arraycopy(out1, 1, output, (output.length / 2) - (out1.length - 1), out1.length - 1);
                    } else {
                        System.arraycopy(out1, 0, output, (output.length / 2) - out1.length, out1.length);
                    }
                    if (out2.length > output.length / 2) {
                        System.arraycopy(out2, 1, output, output.length - (out2.length - 1), out2.length - 1);
                    } else {
                        System.arraycopy(out2, 0, output, output.length - out2.length, out2.length);
                    }
                    return output;
                }
                throw new DataLengthException("input too large for ElGamal cipher.\n");
            }
            throw new DataLengthException("input too large for ElGamal cipher.\n");
        }
        throw new IllegalStateException("ElGamal engine not initialised");
    }
}
