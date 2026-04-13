package org.spongycastle.pqc.crypto.sphincs;

import com.alibaba.fastjson.asm.Opcodes;
import com.tutk.IOTC.AVIOCTRLDEFs;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.crypto.MessageSigner;
import org.spongycastle.pqc.crypto.sphincs.Tree;
import org.spongycastle.util.Pack;

public class SPHINCS256Signer implements MessageSigner {
    private final HashFunctions a;
    private byte[] b;

    public SPHINCS256Signer(Digest nDigest, Digest twoNDigest) {
        if (nDigest.e() != 32) {
            throw new IllegalArgumentException("n-digest needs to produce 32 bytes of output");
        } else if (twoNDigest.e() == 64) {
            this.a = new HashFunctions(nDigest, twoNDigest);
        } else {
            throw new IllegalArgumentException("2n-digest needs to produce 64 bytes of output");
        }
    }

    public void a(boolean forSigning, CipherParameters param) {
        if (forSigning) {
            this.b = ((SPHINCSPrivateKeyParameters) param).b();
        } else {
            this.b = ((SPHINCSPublicKeyParameters) param).b();
        }
    }

    public byte[] b(byte[] message) {
        return e(this.a, message, this.b);
    }

    public boolean c(byte[] message, byte[] signature) {
        return g(this.a, message, signature, this.b);
    }

    static void f(HashFunctions hs, byte[] root, byte[] leaf, int leafidx, byte[] authpath, int auOff, byte[] masks, int height) {
        int j;
        int j2;
        byte[] buffer = new byte[64];
        if ((leafidx & 1) != 0) {
            for (int j3 = 0; j3 < 32; j3++) {
                buffer[j3 + 32] = leaf[j3];
            }
            j = 0;
            while (j < 32) {
                buffer[j] = authpath[auOff + j];
                j++;
            }
        } else {
            for (int j4 = 0; j4 < 32; j4++) {
                buffer[j4] = leaf[j4];
            }
            int j5 = 0;
            while (j < 32) {
                buffer[j + 32] = authpath[auOff + j];
                j5 = j + 1;
            }
        }
        int leafidx2 = leafidx;
        int i = j;
        int authOff = auOff + 32;
        for (int i2 = 0; i2 < height - 1; i2++) {
            leafidx2 >>>= 1;
            if ((leafidx2 & 1) != 0) {
                hs.c(buffer, 32, buffer, 0, masks, (i2 + 7) * 2 * 32);
                j2 = 0;
                while (j2 < 32) {
                    buffer[j2] = authpath[authOff + j2];
                    j2++;
                }
            } else {
                hs.c(buffer, 0, buffer, 0, masks, (i2 + 7) * 2 * 32);
                int j6 = 0;
                while (j2 < 32) {
                    buffer[j2 + 32] = authpath[authOff + j2];
                    j6 = j2 + 1;
                }
            }
            int i3 = j2;
            authOff += 32;
        }
        hs.c(root, 0, buffer, 0, masks, ((height + 7) - 1) * 2 * 32);
    }

    static void d(HashFunctions hs, byte[] root, byte[] authpath, int authOff, Tree.leafaddr a2, byte[] sk, byte[] masks, int height) {
        Tree.leafaddr leafaddr = a2;
        Tree.leafaddr ta = new Tree.leafaddr(leafaddr);
        byte[] tree = new byte[2048];
        byte[] seed = new byte[1024];
        byte[] pk = new byte[68608];
        ta.c = 0;
        while (true) {
            long j = ta.c;
            if (j >= 32) {
                break;
            }
            Seed.a(hs, seed, (int) (j * 32), sk, ta);
            ta.c++;
        }
        HashFunctions hashFunctions = hs;
        byte[] bArr = sk;
        Wots w = new Wots();
        ta.c = 0;
        while (true) {
            long j2 = ta.c;
            if (j2 >= 32) {
                break;
            }
            w.d(hs, pk, (int) (67 * j2 * 32), seed, (int) (j2 * 32), masks, 0);
            ta.c++;
        }
        ta.c = 0;
        while (true) {
            long j3 = ta.c;
            if (j3 >= 32) {
                break;
            }
            Tree.b(hs, tree, (int) (1024 + (j3 * 32)), pk, (int) (j3 * 67 * 32), masks, 0);
            ta.c++;
            HashFunctions hashFunctions2 = hs;
            byte[] bArr2 = sk;
            pk = pk;
            seed = seed;
        }
        byte[] bArr3 = seed;
        int level = 0;
        for (int i = 32; i > 0; i >>>= 1) {
            int j4 = 0;
            while (j4 < i) {
                hs.c(tree, ((i >>> 1) * 32) + ((j4 >>> 1) * 32), tree, (i * 32) + (j4 * 32), masks, (level + 7) * 2 * 32);
                j4 += 2;
            }
            int i2 = j4;
            level++;
        }
        int idx = (int) leafaddr.c;
        for (int i3 = 0; i3 < height; i3++) {
            System.arraycopy(tree, ((32 >>> i3) * 32) + (((idx >>> i3) ^ 1) * 32), authpath, authOff + (i3 * 32), 32);
        }
        byte[] bArr4 = authpath;
        System.arraycopy(tree, 32, root, 0, 32);
    }

    /* access modifiers changed from: package-private */
    public byte[] e(HashFunctions hs, byte[] m, byte[] sk) {
        HashFunctions hashFunctions = hs;
        byte[] bArr = m;
        byte[] sm = new byte[41000];
        byte[] R = new byte[32];
        byte[] m_h = new byte[64];
        long[] rnd = new long[8];
        byte[] root = new byte[32];
        byte[] seed = new byte[32];
        byte[] masks = new byte[1024];
        byte[] tsk = new byte[AVIOCTRLDEFs.IOTYPE_PRESET_SETPRESET_REQ];
        int i = 0;
        while (i < 1088) {
            tsk[i] = sk[i];
            i++;
        }
        System.arraycopy(tsk, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETGUARD_REQ, sm, 40968, 32);
        Digest d = hs.a();
        byte[] bRnd = new byte[d.e()];
        d.update(sm, 40968, 32);
        byte[] masks2 = masks;
        d.update(bArr, 0, bArr.length);
        d.c(bRnd, 0);
        h(sm, 40968, 32);
        for (int j = 0; j != rnd.length; j++) {
            rnd[j] = Pack.m(bRnd, j * 8);
        }
        long leafidx = rnd[0] & 1152921504606846975L;
        int i2 = i;
        System.arraycopy(bRnd, 16, R, 0, 32);
        System.arraycopy(R, 0, sm, 39912, 32);
        Tree.leafaddr b2 = new Tree.leafaddr();
        b2.a = 11;
        byte[] bRnd2 = bRnd;
        b2.b = 0;
        b2.c = 0;
        int pk = 39912 + 32;
        System.arraycopy(tsk, 32, sm, pk, 1024);
        byte[] tsk2 = tsk;
        Digest digest = d;
        byte[] masks3 = masks2;
        byte[] bArr2 = bRnd2;
        byte[] seed2 = seed;
        byte[] root2 = root;
        long[] jArr = rnd;
        Tree.c(hs, sm, pk + 1024, 5, tsk2, b2, sm, pk);
        Digest d2 = hs.a();
        d2.update(sm, 39912, AVIOCTRLDEFs.IOTYPE_PRESET_SETPRESET_REQ);
        d2.update(bArr, 0, bArr.length);
        d2.c(m_h, 0);
        Tree.leafaddr a2 = new Tree.leafaddr();
        a2.a = 12;
        a2.c = (long) ((int) (leafidx & 31));
        a2.b = leafidx >>> 5;
        for (int i3 = 0; i3 < 32; i3++) {
            sm[i3] = R[i3];
        }
        byte[] tsk3 = tsk2;
        byte[] masks4 = masks3;
        System.arraycopy(tsk3, 32, masks4, 0, 1024);
        int i4 = 0;
        while (i4 < 8) {
            sm[32 + i4] = (byte) ((int) ((leafidx >>> (i4 * 8)) & 255));
            i4++;
        }
        int smOff = 32 + 8;
        byte[] seed3 = seed2;
        Seed.a(hashFunctions, seed3, 0, tsk3, a2);
        new Horst();
        int i5 = i4;
        byte[] tsk4 = tsk3;
        byte[] masks5 = masks4;
        byte[] seed4 = seed3;
        byte[] bArr3 = R;
        Wots w = new Wots();
        int smOff2 = smOff + Horst.b(hs, sm, smOff, root2, seed3, masks4, m_h);
        int smOff3 = 0;
        for (int i6 = 12; smOff3 < i6; i6 = 12) {
            a2.a = smOff3;
            Seed.a(hashFunctions, seed4, 0, tsk4, a2);
            HashFunctions hashFunctions2 = hs;
            w.e(hashFunctions2, sm, smOff2, root2, seed4, masks5);
            int smOff4 = smOff2 + 2144;
            Wots w2 = w;
            Tree.leafaddr a3 = a2;
            d(hashFunctions2, root2, sm, smOff4, a2, tsk4, masks5, 5);
            smOff2 = smOff4 + Opcodes.IF_ICMPNE;
            long j2 = a3.b;
            a3.c = (long) ((int) (j2 & 31));
            a3.b = j2 >>> 5;
            smOff3++;
            a2 = a3;
            w = w2;
        }
        h(tsk4, 0, AVIOCTRLDEFs.IOTYPE_PRESET_SETPRESET_REQ);
        return sm;
    }

    private void h(byte[] tsk, int off, int cryptoSecretkeybytes) {
        for (int i = 0; i != cryptoSecretkeybytes; i++) {
            tsk[off + i] = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean g(HashFunctions hs, byte[] m, byte[] sm, byte[] pk) {
        byte[] bArr = m;
        byte[] bArr2 = sm;
        int smlen = bArr2.length;
        long leafidx = 0;
        byte[] wots_pk = new byte[2144];
        byte[] pkhash = new byte[32];
        byte[] root = new byte[32];
        byte[] sig = new byte[41000];
        byte[] tpk = new byte[AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETGUARD_REQ];
        if (smlen == 41000) {
            byte[] m_h = new byte[64];
            for (int i = 0; i < 1056; i++) {
                tpk[i] = pk[i];
            }
            byte[] R = new byte[32];
            for (int i2 = 0; i2 < 32; i2++) {
                R[i2] = bArr2[i2];
            }
            System.arraycopy(bArr2, 0, sig, 0, 41000);
            Digest mHash = hs.a();
            mHash.update(R, 0, 32);
            mHash.update(tpk, 0, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETGUARD_REQ);
            mHash.update(bArr, 0, bArr.length);
            mHash.c(m_h, 0);
            int sigp = 0 + 32;
            int smlen2 = smlen - 32;
            int i3 = 0;
            while (i3 < 8) {
                leafidx ^= ((long) (sig[sigp + i3] & 255)) << (i3 * 8);
                i3++;
            }
            new Horst();
            int i4 = i3;
            byte[] tpk2 = tpk;
            Horst.c(hs, root, sig, sigp + 8, tpk, m_h);
            int smlen3 = (smlen2 - 8) - 13312;
            Wots w = new Wots();
            int sigp2 = sigp + 8 + 13312;
            int i5 = 0;
            while (i5 < 12) {
                HashFunctions hashFunctions = hs;
                byte[] sig2 = sig;
                byte[] sig3 = tpk2;
                w.f(hashFunctions, wots_pk, sig, sigp2, root, sig3);
                int sigp3 = sigp2 + 2144;
                byte[] root2 = root;
                Tree.b(hashFunctions, pkhash, 0, wots_pk, 0, sig3, 0);
                byte[] pkhash2 = pkhash;
                f(hs, root2, pkhash2, (int) (31 & leafidx), sig2, sigp3, tpk2, 5);
                leafidx >>= 5;
                sigp2 = sigp3 + Opcodes.IF_ICMPNE;
                smlen3 = (smlen3 - 2144) - 160;
                i5++;
                byte[] bArr3 = m;
                pkhash = pkhash2;
                sig = sig2;
                root = root2;
            }
            int i6 = sigp2;
            byte[] bArr4 = sig;
            byte[] root3 = root;
            byte[] bArr5 = pkhash;
            boolean verified = true;
            for (int i7 = 0; i7 < 32; i7++) {
                if (root3[i7] != tpk2[i7 + 1024]) {
                    verified = false;
                }
            }
            return verified;
        }
        throw new IllegalArgumentException("signature wrong size");
    }
}
