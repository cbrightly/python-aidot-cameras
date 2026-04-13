package org.spongycastle.pqc.crypto.gmss;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.Vector;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.spongycastle.pqc.crypto.gmss.util.WinternitzOTSVerify;
import org.spongycastle.pqc.crypto.gmss.util.WinternitzOTSignature;

public class GMSSKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private GMSSRandom g;
    private byte[][] h;
    private byte[][] i;
    private byte[][] j;
    private GMSSDigestProvider k;
    private int l;
    private int m;
    private boolean n;
    private GMSSParameters o;
    private int[] p;
    private int[] q;
    private int[] r;
    private GMSSKeyGenerationParameters s;

    private AsymmetricCipherKeyPair b() {
        int i2;
        int i3;
        Class<byte> cls = byte.class;
        if (!this.n) {
            f();
        }
        int i4 = this.m;
        byte[][][] currentAuthPaths = new byte[i4][][];
        byte[][][] nextAuthPaths = new byte[(i4 - 1)][][];
        Treehash[][] currentTreehash = new Treehash[i4][];
        Treehash[][] nextTreehash = new Treehash[(i4 - 1)][];
        Vector[] currentStack = new Vector[i4];
        Vector[] nextStack = new Vector[(i4 - 1)];
        Vector[][] currentRetain = new Vector[i4][];
        int i5 = 1;
        Vector[][] nextRetain = new Vector[(i4 - 1)][];
        int i6 = 0;
        while (true) {
            i2 = this.m;
            if (i6 >= i2) {
                break;
            }
            int i7 = this.p[i6];
            Vector[][] nextRetain2 = nextRetain;
            int[] iArr = new int[2];
            iArr[1] = this.l;
            iArr[0] = i7;
            currentAuthPaths[i6] = (byte[][]) Array.newInstance(cls, iArr);
            int[] iArr2 = this.p;
            currentTreehash[i6] = new Treehash[(iArr2[i6] - this.r[i6])];
            if (i6 > 0) {
                int i8 = iArr2[i6];
                int[] iArr3 = new int[2];
                iArr3[1] = this.l;
                iArr3[0] = i8;
                nextAuthPaths[i6 - 1] = (byte[][]) Array.newInstance(cls, iArr3);
                nextTreehash[i6 - 1] = new Treehash[(this.p[i6] - this.r[i6])];
            }
            currentStack[i6] = new Vector();
            if (i6 > 0) {
                nextStack[i6 - 1] = new Vector();
            }
            i6++;
            nextRetain = nextRetain2;
        }
        Vector[][] nextRetain3 = nextRetain;
        int[] iArr4 = new int[2];
        iArr4[1] = this.l;
        iArr4[0] = i2;
        byte[][] currentRoots = (byte[][]) Array.newInstance(cls, iArr4);
        int[] iArr5 = new int[2];
        iArr5[1] = this.l;
        iArr5[0] = this.m - 1;
        byte[][] nextRoots = (byte[][]) Array.newInstance(cls, iArr5);
        int i9 = this.m;
        int[] iArr6 = new int[2];
        iArr6[1] = this.l;
        iArr6[0] = i9;
        byte[][] seeds = (byte[][]) Array.newInstance(cls, iArr6);
        int i10 = 0;
        while (true) {
            i3 = this.m;
            if (i10 >= i3) {
                break;
            }
            System.arraycopy(this.h[i10], 0, seeds[i10], 0, this.l);
            i10++;
        }
        int[] iArr7 = new int[2];
        iArr7[1] = this.l;
        iArr7[0] = i3 - 1;
        this.j = (byte[][]) Array.newInstance(cls, iArr7);
        int h2 = this.m - 1;
        while (h2 >= 0) {
            GMSSRootCalc tree = new GMSSRootCalc(this.p[h2], this.r[h2], this.k);
            try {
                if (h2 == this.m - i5) {
                    tree = c((byte[]) null, currentStack[h2], seeds[h2], h2);
                } else {
                    tree = c(currentRoots[h2 + 1], currentStack[h2], seeds[h2], h2);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            for (int i11 = 0; i11 < this.p[h2]; i11++) {
                System.arraycopy(tree.a()[i11], 0, currentAuthPaths[h2][i11], 0, this.l);
            }
            currentRetain[h2] = tree.b();
            currentTreehash[h2] = tree.g();
            System.arraycopy(tree.c(), 0, currentRoots[h2], 0, this.l);
            h2--;
            i5 = 1;
        }
        for (int h3 = this.m - 2; h3 >= 0; h3--) {
            GMSSRootCalc tree2 = d(nextStack[h3], seeds[h3 + 1], h3 + 1);
            for (int i12 = 0; i12 < this.p[h3 + 1]; i12++) {
                System.arraycopy(tree2.a()[i12], 0, nextAuthPaths[h3][i12], 0, this.l);
            }
            nextRetain3[h3] = tree2.b();
            nextTreehash[h3] = tree2.g();
            System.arraycopy(tree2.c(), 0, nextRoots[h3], 0, this.l);
            System.arraycopy(seeds[h3 + 1], 0, this.i[h3], 0, this.l);
        }
        Vector[] vectorArr = currentStack;
        Treehash[][] treehashArr = nextTreehash;
        Treehash[][] treehashArr2 = currentTreehash;
        byte[][][] bArr = nextAuthPaths;
        byte[][][] bArr2 = currentAuthPaths;
        return new AsymmetricCipherKeyPair(new GMSSPublicKeyParameters(currentRoots[0], this.o), new GMSSPrivateKeyParameters(this.h, this.i, currentAuthPaths, nextAuthPaths, currentTreehash, nextTreehash, currentStack, nextStack, currentRetain, nextRetain3, nextRoots, this.j, this.o, this.k));
    }

    private GMSSRootCalc c(byte[] lowerRoot, Vector currentStack, byte[] seed, int h2) {
        byte[] help;
        int i2 = this.l;
        byte[] bArr = new byte[i2];
        byte[] bArr2 = new byte[i2];
        byte[] OTSseed = this.g.c(seed);
        GMSSRootCalc treeToConstruct = new GMSSRootCalc(this.p[h2], this.r[h2], this.k);
        treeToConstruct.h(currentStack);
        if (h2 == this.m - 1) {
            help = new WinternitzOTSignature(OTSseed, this.k.get(), this.q[h2]).b();
        } else {
            this.j[h2] = new WinternitzOTSignature(OTSseed, this.k.get(), this.q[h2]).c(lowerRoot);
            help = new WinternitzOTSVerify(this.k.get(), this.q[h2]).a(lowerRoot, this.j[h2]);
        }
        treeToConstruct.j(help);
        int seedForTreehashIndex = 3;
        int count = 0;
        int i3 = 1;
        while (true) {
            int[] iArr = this.p;
            if (i3 >= (1 << iArr[h2])) {
                break;
            }
            if (i3 == seedForTreehashIndex && count < iArr[h2] - this.r[h2]) {
                treeToConstruct.i(seed, count);
                seedForTreehashIndex *= 2;
                count++;
            }
            treeToConstruct.j(new WinternitzOTSignature(this.g.c(seed), this.k.get(), this.q[h2]).b());
            i3++;
        }
        if (treeToConstruct.l()) {
            return treeToConstruct;
        }
        System.err.println("Baum noch nicht fertig konstruiert!!!");
        return null;
    }

    private GMSSRootCalc d(Vector nextStack, byte[] seed, int h2) {
        byte[] bArr = new byte[this.m];
        GMSSRootCalc treeToConstruct = new GMSSRootCalc(this.p[h2], this.r[h2], this.k);
        treeToConstruct.h(nextStack);
        int seedForTreehashIndex = 3;
        int count = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.p;
            if (i2 >= (1 << iArr[h2])) {
                break;
            }
            if (i2 == seedForTreehashIndex && count < iArr[h2] - this.r[h2]) {
                treeToConstruct.i(seed, count);
                seedForTreehashIndex *= 2;
                count++;
            }
            treeToConstruct.j(new WinternitzOTSignature(this.g.c(seed), this.k.get(), this.q[h2]).b());
            i2++;
        }
        if (treeToConstruct.l() != 0) {
            return treeToConstruct;
        }
        System.err.println("N�chster Baum noch nicht fertig konstruiert!!!");
        return null;
    }

    public void e(KeyGenerationParameters param) {
        Class<byte> cls = byte.class;
        GMSSKeyGenerationParameters gMSSKeyGenerationParameters = (GMSSKeyGenerationParameters) param;
        this.s = gMSSKeyGenerationParameters;
        GMSSParameters gMSSParameters = new GMSSParameters(gMSSKeyGenerationParameters.c().c(), this.s.c().a(), this.s.c().d(), this.s.c().b());
        this.o = gMSSParameters;
        this.m = gMSSParameters.c();
        this.p = this.o.a();
        this.q = this.o.d();
        this.r = this.o.b();
        int i2 = this.m;
        int[] iArr = new int[2];
        iArr[1] = this.l;
        iArr[0] = i2;
        this.h = (byte[][]) Array.newInstance(cls, iArr);
        int[] iArr2 = new int[2];
        iArr2[1] = this.l;
        iArr2[0] = this.m - 1;
        this.i = (byte[][]) Array.newInstance(cls, iArr2);
        SecureRandom secRan = new SecureRandom();
        for (int i3 = 0; i3 < this.m; i3++) {
            secRan.nextBytes(this.h[i3]);
            this.g.c(this.h[i3]);
        }
        this.n = true;
    }

    private void f() {
        int[] defh = {10, 10, 10, 10};
        e(new GMSSKeyGenerationParameters(new SecureRandom(), new GMSSParameters(defh.length, defh, new int[]{3, 3, 3, 3}, new int[]{2, 2, 2, 2})));
    }

    public AsymmetricCipherKeyPair a() {
        return b();
    }
}
