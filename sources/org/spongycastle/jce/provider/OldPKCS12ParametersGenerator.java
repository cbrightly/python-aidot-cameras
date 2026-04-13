package org.spongycastle.jce.provider;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

/* compiled from: BrokenPBE */
public class OldPKCS12ParametersGenerator extends PBEParametersGenerator {
    private Digest d;
    private int e;
    private int f;

    private void h(byte[] a, int aOff, byte[] b) {
        int x = (b[b.length - 1] & 255) + (a[(b.length + aOff) - 1] & 255) + 1;
        a[(b.length + aOff) - 1] = (byte) x;
        int x2 = x >>> 8;
        for (int i = b.length - 2; i >= 0; i--) {
            int x3 = x2 + (b[i] & 255) + (a[aOff + i] & 255);
            a[aOff + i] = (byte) x3;
            x2 = x3 >>> 8;
        }
    }

    private byte[] i(int idByte, int n) {
        byte[] S;
        byte[] P;
        int i = n;
        byte[] D = new byte[this.f];
        byte[] dKey = new byte[i];
        for (int i2 = 0; i2 != D.length; i2++) {
            D[i2] = (byte) idByte;
        }
        int i3 = idByte;
        byte[] bArr = this.b;
        if (bArr == null || bArr.length == 0) {
            S = new byte[0];
        } else {
            int i4 = this.f;
            S = new byte[(i4 * (((bArr.length + i4) - 1) / i4))];
            for (int i5 = 0; i5 != S.length; i5++) {
                byte[] bArr2 = this.b;
                S[i5] = bArr2[i5 % bArr2.length];
            }
        }
        byte[] bArr3 = this.a;
        if (bArr3 == null || bArr3.length == 0) {
            P = new byte[0];
        } else {
            int i6 = this.f;
            P = new byte[(i6 * (((bArr3.length + i6) - 1) / i6))];
            for (int i7 = 0; i7 != P.length; i7++) {
                byte[] bArr4 = this.a;
                P[i7] = bArr4[i7 % bArr4.length];
            }
        }
        byte[] I = new byte[(S.length + P.length)];
        System.arraycopy(S, 0, I, 0, S.length);
        System.arraycopy(P, 0, I, S.length, P.length);
        byte[] B = new byte[this.f];
        int i8 = this.e;
        int c = ((i + i8) - 1) / i8;
        for (int i9 = 1; i9 <= c; i9++) {
            byte[] A = new byte[this.e];
            this.d.update(D, 0, D.length);
            this.d.update(I, 0, I.length);
            this.d.c(A, 0);
            for (int j = 1; j != this.c; j++) {
                this.d.update(A, 0, A.length);
                this.d.c(A, 0);
            }
            for (int j2 = 0; j2 != B.length; j2++) {
                B[i9] = A[j2 % A.length];
            }
            int j3 = 0;
            while (true) {
                int length = I.length;
                int i10 = this.f;
                if (j3 == length / i10) {
                    break;
                }
                h(I, i10 * j3, B);
                j3++;
            }
            if (i9 == c) {
                int i11 = this.e;
                System.arraycopy(A, 0, dKey, (i9 - 1) * i11, dKey.length - ((i9 - 1) * i11));
            } else {
                System.arraycopy(A, 0, dKey, (i9 - 1) * this.e, A.length);
            }
        }
        return dKey;
    }

    public CipherParameters e(int keySize) {
        int keySize2 = keySize / 8;
        return new KeyParameter(i(1, keySize2), 0, keySize2);
    }

    public CipherParameters f(int keySize, int ivSize) {
        int keySize2 = keySize / 8;
        int ivSize2 = ivSize / 8;
        byte[] dKey = i(1, keySize2);
        return new ParametersWithIV(new KeyParameter(dKey, 0, keySize2), i(2, ivSize2), 0, ivSize2);
    }

    public CipherParameters d(int keySize) {
        int keySize2 = keySize / 8;
        return new KeyParameter(i(3, keySize2), 0, keySize2);
    }
}
