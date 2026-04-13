package org.spongycastle.pqc.crypto.sphincs;

public class Wots {
    Wots() {
    }

    static void b(byte[] outseeds, int outOff, byte[] inseed, int inOff) {
        a(outseeds, outOff, 2144);
        Seed.b(outseeds, outOff, 2144, inseed, inOff);
    }

    private static void a(byte[] bytes, int offSet, int length) {
        for (int i = 0; i != length; i++) {
            bytes[i + offSet] = 0;
        }
    }

    static void c(HashFunctions hs, byte[] out, int outOff, byte[] seed, int seedOff, byte[] masks, int masksOff, int chainlen) {
        for (int j = 0; j < 32; j++) {
            out[j + outOff] = seed[j + seedOff];
        }
        int i = 0;
        while (i < chainlen && i < 16) {
            hs.e(out, outOff, out, outOff, masks, masksOff + (i * 32));
            i++;
        }
    }

    /* access modifiers changed from: package-private */
    public void d(HashFunctions hs, byte[] pk, int pkOff, byte[] sk, int skOff, byte[] masks, int masksOff) {
        b(pk, pkOff, sk, skOff);
        for (int i = 0; i < 67; i++) {
            c(hs, pk, pkOff + (i * 32), pk, pkOff + (i * 32), masks, masksOff, 15);
        }
    }

    /* access modifiers changed from: package-private */
    public void e(HashFunctions hs, byte[] sig, int sigOff, byte[] msg, byte[] sk, byte[] masks) {
        int i = sigOff;
        int[] basew = new int[67];
        int c = 0;
        int i2 = 0;
        while (i2 < 64) {
            basew[i2] = msg[i2 / 2] & 15;
            basew[i2 + 1] = (msg[i2 / 2] & 255) >>> 4;
            c = c + (15 - basew[i2]) + (15 - basew[i2 + 1]);
            i2 += 2;
        }
        while (i2 < 67) {
            basew[i2] = c & 15;
            c >>>= 4;
            i2++;
        }
        b(sig, i, sk, 0);
        for (int i3 = 0; i3 < 67; i3++) {
            c(hs, sig, i + (i3 * 32), sig, i + (i3 * 32), masks, 0, basew[i3]);
        }
    }

    /* access modifiers changed from: package-private */
    public void f(HashFunctions hs, byte[] pk, byte[] sig, int sigOff, byte[] msg, byte[] masks) {
        int[] basew = new int[67];
        int c = 0;
        int i = 0;
        while (i < 64) {
            basew[i] = msg[i / 2] & 15;
            basew[i + 1] = (msg[i / 2] & 255) >>> 4;
            c = c + (15 - basew[i]) + (15 - basew[i + 1]);
            i += 2;
        }
        while (i < 67) {
            basew[i] = c & 15;
            c >>>= 4;
            i++;
        }
        for (int i2 = 0; i2 < 67; i2++) {
            HashFunctions hashFunctions = hs;
            byte[] bArr = pk;
            c(hashFunctions, bArr, i2 * 32, sig, sigOff + (i2 * 32), masks, basew[i2] * 32, 15 - basew[i2]);
        }
    }
}
