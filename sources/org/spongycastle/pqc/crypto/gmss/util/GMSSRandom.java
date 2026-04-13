package org.spongycastle.pqc.crypto.gmss.util;

import org.spongycastle.crypto.Digest;

public class GMSSRandom {
    private Digest a;

    public GMSSRandom(Digest messDigestTree2) {
        this.a = messDigestTree2;
    }

    public byte[] c(byte[] outseed) {
        byte[] bArr = new byte[outseed.length];
        this.a.update(outseed, 0, outseed.length);
        byte[] rand = new byte[this.a.e()];
        this.a.c(rand, 0);
        a(outseed, rand);
        b(outseed);
        return rand;
    }

    private void a(byte[] a2, byte[] b) {
        byte overflow = 0;
        for (int i = 0; i < a2.length; i++) {
            int temp = (a2[i] & 255) + (b[i] & 255) + overflow;
            a2[i] = (byte) temp;
            overflow = (byte) (temp >> 8);
        }
    }

    private void b(byte[] a2) {
        byte overflow = 1;
        for (int i = 0; i < a2.length; i++) {
            int temp = (a2[i] & 255) + overflow;
            a2[i] = (byte) temp;
            overflow = (byte) (temp >> 8);
        }
    }
}
