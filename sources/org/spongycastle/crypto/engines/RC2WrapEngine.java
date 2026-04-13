package org.spongycastle.crypto.engines;

import java.security.SecureRandom;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;

public class RC2WrapEngine implements Wrapper {
    private static final byte[] a = {74, -35, -94, Constants.COMMA, 121, -24, 33, 5};
    private CBCBlockCipher b;
    private CipherParameters c;
    private ParametersWithIV d;
    private byte[] e;
    private boolean f;
    private SecureRandom g;
    Digest h = DigestFactory.b();
    byte[] i = new byte[20];

    public void a(boolean forWrapping, CipherParameters param) {
        this.f = forWrapping;
        this.b = new CBCBlockCipher(new RC2Engine());
        if (param instanceof ParametersWithRandom) {
            ParametersWithRandom pWithR = (ParametersWithRandom) param;
            this.g = pWithR.b();
            param = pWithR.a();
        } else {
            this.g = new SecureRandom();
        }
        if (param instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) param;
            this.d = parametersWithIV;
            this.e = parametersWithIV.a();
            this.c = this.d.b();
            if (this.f) {
                byte[] bArr = this.e;
                if (bArr == null || bArr.length != 8) {
                    throw new IllegalArgumentException("IV is not 8 octets");
                }
                return;
            }
            throw new IllegalArgumentException("You should not supply an IV for unwrapping");
        }
        this.c = param;
        if (this.f) {
            byte[] bArr2 = new byte[8];
            this.e = bArr2;
            this.g.nextBytes(bArr2);
            this.d = new ParametersWithIV(this.c, this.e);
        }
    }

    public String b() {
        return "RC2";
    }

    public byte[] wrap(byte[] in, int inOff, int inLen) {
        int i2 = inLen;
        if (this.f) {
            int length = i2 + 1;
            if (length % 8 != 0) {
                length += 8 - (length % 8);
            }
            byte[] keyToBeWrapped = new byte[length];
            keyToBeWrapped[0] = (byte) i2;
            System.arraycopy(in, inOff, keyToBeWrapped, 1, i2);
            byte[] pad = new byte[((keyToBeWrapped.length - i2) - 1)];
            if (pad.length > 0) {
                this.g.nextBytes(pad);
                System.arraycopy(pad, 0, keyToBeWrapped, i2 + 1, pad.length);
            }
            byte[] CKS = d(keyToBeWrapped);
            byte[] WKCKS = new byte[(keyToBeWrapped.length + CKS.length)];
            System.arraycopy(keyToBeWrapped, 0, WKCKS, 0, keyToBeWrapped.length);
            System.arraycopy(CKS, 0, WKCKS, keyToBeWrapped.length, CKS.length);
            byte[] TEMP1 = new byte[WKCKS.length];
            System.arraycopy(WKCKS, 0, TEMP1, 0, WKCKS.length);
            int noOfBlocks = WKCKS.length / this.b.c();
            if (WKCKS.length % this.b.c() == 0) {
                this.b.a(true, this.d);
                for (int i3 = 0; i3 < noOfBlocks; i3++) {
                    int currentBytePos = this.b.c() * i3;
                    this.b.f(TEMP1, currentBytePos, TEMP1, currentBytePos);
                }
                byte[] bArr = this.e;
                byte[] TEMP2 = new byte[(bArr.length + TEMP1.length)];
                System.arraycopy(bArr, 0, TEMP2, 0, bArr.length);
                System.arraycopy(TEMP1, 0, TEMP2, this.e.length, TEMP1.length);
                byte[] TEMP3 = new byte[TEMP2.length];
                for (int i4 = 0; i4 < TEMP2.length; i4++) {
                    TEMP3[i4] = TEMP2[TEMP2.length - (i4 + 1)];
                }
                ParametersWithIV param2 = new ParametersWithIV(this.c, a);
                this.b.a(true, param2);
                int i5 = 0;
                while (i5 < noOfBlocks + 1) {
                    int currentBytePos2 = this.b.c() * i5;
                    this.b.f(TEMP3, currentBytePos2, TEMP3, currentBytePos2);
                    i5++;
                    param2 = param2;
                }
                return TEMP3;
            }
            throw new IllegalStateException("Not multiple of block length");
        }
        byte[] bArr2 = in;
        int i6 = inOff;
        throw new IllegalStateException("Not initialized for wrapping");
    }

    public byte[] c(byte[] in, int inOff, int inLen) {
        if (this.f) {
            throw new IllegalStateException("Not set for unwrapping");
        } else if (in == null) {
            throw new InvalidCipherTextException("Null pointer as ciphertext");
        } else if (inLen % this.b.c() == 0) {
            this.b.a(false, new ParametersWithIV(this.c, a));
            byte[] TEMP3 = new byte[inLen];
            System.arraycopy(in, inOff, TEMP3, 0, inLen);
            for (int i2 = 0; i2 < TEMP3.length / this.b.c(); i2++) {
                int currentBytePos = this.b.c() * i2;
                this.b.f(TEMP3, currentBytePos, TEMP3, currentBytePos);
            }
            byte[] TEMP2 = new byte[TEMP3.length];
            for (int i3 = 0; i3 < TEMP3.length; i3++) {
                TEMP2[i3] = TEMP3[TEMP3.length - (i3 + 1)];
            }
            byte[] bArr = new byte[8];
            this.e = bArr;
            byte[] TEMP1 = new byte[(TEMP2.length - 8)];
            System.arraycopy(TEMP2, 0, bArr, 0, 8);
            System.arraycopy(TEMP2, 8, TEMP1, 0, TEMP2.length - 8);
            ParametersWithIV parametersWithIV = new ParametersWithIV(this.c, this.e);
            this.d = parametersWithIV;
            this.b.a(false, parametersWithIV);
            byte[] LCEKPADICV = new byte[TEMP1.length];
            System.arraycopy(TEMP1, 0, LCEKPADICV, 0, TEMP1.length);
            for (int i4 = 0; i4 < LCEKPADICV.length / this.b.c(); i4++) {
                int currentBytePos2 = this.b.c() * i4;
                this.b.f(LCEKPADICV, currentBytePos2, LCEKPADICV, currentBytePos2);
            }
            byte[] result = new byte[(LCEKPADICV.length - 8)];
            byte[] CKStoBeVerified = new byte[8];
            System.arraycopy(LCEKPADICV, 0, result, 0, LCEKPADICV.length - 8);
            System.arraycopy(LCEKPADICV, LCEKPADICV.length - 8, CKStoBeVerified, 0, 8);
            if (!e(result, CKStoBeVerified)) {
                throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
            } else if (result.length - ((result[0] & 255) + 1) <= 7) {
                byte[] CEK = new byte[result[0]];
                System.arraycopy(result, 1, CEK, 0, CEK.length);
                return CEK;
            } else {
                throw new InvalidCipherTextException("too many pad bytes (" + (result.length - ((result[0] & 255) + 1)) + ")");
            }
        } else {
            throw new InvalidCipherTextException("Ciphertext not multiple of " + this.b.c());
        }
    }

    private byte[] d(byte[] key) {
        byte[] result = new byte[8];
        this.h.update(key, 0, key.length);
        this.h.c(this.i, 0);
        System.arraycopy(this.i, 0, result, 0, 8);
        return result;
    }

    private boolean e(byte[] key, byte[] checksum) {
        return Arrays.u(d(key), checksum);
    }
}
