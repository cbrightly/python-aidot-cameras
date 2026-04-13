package org.spongycastle.crypto.engines;

import java.security.SecureRandom;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;

public class DESedeWrapEngine implements Wrapper {
    private static final byte[] a = {74, -35, -94, Constants.COMMA, 121, -24, 33, 5};
    private CBCBlockCipher b;
    private KeyParameter c;
    private ParametersWithIV d;
    private byte[] e;
    private boolean f;
    Digest g = DigestFactory.b();
    byte[] h = new byte[20];

    public void a(boolean forWrapping, CipherParameters param) {
        SecureRandom sr;
        this.f = forWrapping;
        this.b = new CBCBlockCipher(new DESedeEngine());
        if (param instanceof ParametersWithRandom) {
            ParametersWithRandom pr = (ParametersWithRandom) param;
            param = pr.a();
            sr = pr.b();
        } else {
            sr = new SecureRandom();
        }
        if (param instanceof KeyParameter) {
            this.c = (KeyParameter) param;
            if (this.f) {
                byte[] bArr = new byte[8];
                this.e = bArr;
                sr.nextBytes(bArr);
                this.d = new ParametersWithIV(this.c, this.e);
            }
        } else if (param instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) param;
            this.d = parametersWithIV;
            this.e = parametersWithIV.a();
            this.c = (KeyParameter) this.d.b();
            if (this.f) {
                byte[] bArr2 = this.e;
                if (bArr2 == null || bArr2.length != 8) {
                    throw new IllegalArgumentException("IV is not 8 octets");
                }
                return;
            }
            throw new IllegalArgumentException("You should not supply an IV for unwrapping");
        }
    }

    public String b() {
        return "DESede";
    }

    public byte[] wrap(byte[] in, int inOff, int inLen) {
        if (this.f) {
            byte[] keyToBeWrapped = new byte[inLen];
            System.arraycopy(in, inOff, keyToBeWrapped, 0, inLen);
            byte[] CKS = d(keyToBeWrapped);
            byte[] WKCKS = new byte[(keyToBeWrapped.length + CKS.length)];
            System.arraycopy(keyToBeWrapped, 0, WKCKS, 0, keyToBeWrapped.length);
            System.arraycopy(CKS, 0, WKCKS, keyToBeWrapped.length, CKS.length);
            int blockSize = this.b.c();
            if (WKCKS.length % blockSize == 0) {
                this.b.a(true, this.d);
                byte[] TEMP1 = new byte[WKCKS.length];
                for (int currentBytePos = 0; currentBytePos != WKCKS.length; currentBytePos += blockSize) {
                    this.b.f(WKCKS, currentBytePos, TEMP1, currentBytePos);
                }
                byte[] bArr = this.e;
                byte[] TEMP2 = new byte[(bArr.length + TEMP1.length)];
                System.arraycopy(bArr, 0, TEMP2, 0, bArr.length);
                System.arraycopy(TEMP1, 0, TEMP2, this.e.length, TEMP1.length);
                byte[] TEMP3 = f(TEMP2);
                this.b.a(true, new ParametersWithIV(this.c, a));
                for (int currentBytePos2 = 0; currentBytePos2 != TEMP3.length; currentBytePos2 += blockSize) {
                    this.b.f(TEMP3, currentBytePos2, TEMP3, currentBytePos2);
                }
                return TEMP3;
            }
            throw new IllegalStateException("Not multiple of block length");
        }
        throw new IllegalStateException("Not initialized for wrapping");
    }

    public byte[] c(byte[] in, int inOff, int inLen) {
        if (this.f) {
            throw new IllegalStateException("Not set for unwrapping");
        } else if (in != null) {
            int blockSize = this.b.c();
            if (inLen % blockSize == 0) {
                this.b.a(false, new ParametersWithIV(this.c, a));
                byte[] TEMP3 = new byte[inLen];
                for (int currentBytePos = 0; currentBytePos != inLen; currentBytePos += blockSize) {
                    this.b.f(in, inOff + currentBytePos, TEMP3, currentBytePos);
                }
                byte[] TEMP2 = f(TEMP3);
                byte[] bArr = new byte[8];
                this.e = bArr;
                byte[] TEMP1 = new byte[(TEMP2.length - 8)];
                System.arraycopy(TEMP2, 0, bArr, 0, 8);
                System.arraycopy(TEMP2, 8, TEMP1, 0, TEMP2.length - 8);
                ParametersWithIV parametersWithIV = new ParametersWithIV(this.c, this.e);
                this.d = parametersWithIV;
                this.b.a(false, parametersWithIV);
                byte[] WKCKS = new byte[TEMP1.length];
                for (int currentBytePos2 = 0; currentBytePos2 != WKCKS.length; currentBytePos2 += blockSize) {
                    this.b.f(TEMP1, currentBytePos2, WKCKS, currentBytePos2);
                }
                byte[] result = new byte[(WKCKS.length - 8)];
                byte[] CKStoBeVerified = new byte[8];
                System.arraycopy(WKCKS, 0, result, 0, WKCKS.length - 8);
                System.arraycopy(WKCKS, WKCKS.length - 8, CKStoBeVerified, 0, 8);
                if (e(result, CKStoBeVerified)) {
                    return result;
                }
                throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
            }
            throw new InvalidCipherTextException("Ciphertext not multiple of " + blockSize);
        } else {
            throw new InvalidCipherTextException("Null pointer as ciphertext");
        }
    }

    private byte[] d(byte[] key) {
        byte[] result = new byte[8];
        this.g.update(key, 0, key.length);
        this.g.c(this.h, 0);
        System.arraycopy(this.h, 0, result, 0, 8);
        return result;
    }

    private boolean e(byte[] key, byte[] checksum) {
        return Arrays.u(d(key), checksum);
    }

    private static byte[] f(byte[] bs) {
        byte[] result = new byte[bs.length];
        for (int i = 0; i < bs.length; i++) {
            result[i] = bs[bs.length - (i + 1)];
        }
        return result;
    }
}
