package org.spongycastle.pqc.crypto.gmss;

import java.util.Vector;
import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.spongycastle.pqc.crypto.gmss.util.WinternitzOTSignature;
import org.spongycastle.util.Arrays;

public class GMSSPrivateKeyParameters extends GMSSKeyParameters {
    private GMSSLeaf[] A4;
    private GMSSLeaf[] B4;
    private int[] C4;
    private GMSSParameters D4;
    private byte[][] E4;
    private GMSSRootCalc[] F4;
    private byte[][] G4;
    private GMSSRootSig[] H4;
    private GMSSDigestProvider I4;
    private boolean J4;
    private int[] K4;
    private int[] L4;
    private int[] M4;
    private int N4;
    private Digest O4;
    private int P4;
    private GMSSRandom Q4;
    private int[] R4;
    private Treehash[][] a1;
    private Vector[] a2;
    private int[] f;
    private Treehash[][] p0;
    private Vector[] p1;
    private Vector[][] p2;
    private Vector[][] p3;
    private byte[][][] p4;
    private byte[][] q;
    private byte[][] x;
    private byte[][][] y;
    private byte[][][] z;
    private GMSSLeaf[] z4;

    public GMSSPrivateKeyParameters(byte[][] currentSeed, byte[][] nextNextSeed, byte[][][] currentAuthPath, byte[][][] nextAuthPath, Treehash[][] currentTreehash, Treehash[][] nextTreehash, Vector[] currentStack, Vector[] nextStack, Vector[][] currentRetain, Vector[][] nextRetain, byte[][] nextRoot, byte[][] currentRootSig, GMSSParameters gmssParameterset, GMSSDigestProvider digestProvider) {
        this((int[]) null, currentSeed, nextNextSeed, currentAuthPath, nextAuthPath, (byte[][][]) null, currentTreehash, nextTreehash, currentStack, nextStack, currentRetain, nextRetain, (GMSSLeaf[]) null, (GMSSLeaf[]) null, (GMSSLeaf[]) null, (int[]) null, nextRoot, (GMSSRootCalc[]) null, currentRootSig, (GMSSRootSig[]) null, gmssParameterset, digestProvider);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GMSSPrivateKeyParameters(int[] r21, byte[][] r22, byte[][] r23, byte[][][] r24, byte[][][] r25, byte[][][] r26, org.spongycastle.pqc.crypto.gmss.Treehash[][] r27, org.spongycastle.pqc.crypto.gmss.Treehash[][] r28, java.util.Vector[] r29, java.util.Vector[] r30, java.util.Vector[][] r31, java.util.Vector[][] r32, org.spongycastle.pqc.crypto.gmss.GMSSLeaf[] r33, org.spongycastle.pqc.crypto.gmss.GMSSLeaf[] r34, org.spongycastle.pqc.crypto.gmss.GMSSLeaf[] r35, int[] r36, byte[][] r37, org.spongycastle.pqc.crypto.gmss.GMSSRootCalc[] r38, byte[][] r39, org.spongycastle.pqc.crypto.gmss.GMSSRootSig[] r40, org.spongycastle.pqc.crypto.gmss.GMSSParameters r41, org.spongycastle.pqc.crypto.gmss.GMSSDigestProvider r42) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            r2 = r22
            r3 = r26
            r4 = r29
            r5 = r30
            r6 = r33
            r7 = r34
            r8 = r35
            r9 = r36
            r10 = r37
            r11 = r38
            r12 = r40
            r13 = r41
            r14 = 1
            r0.<init>(r14, r13)
            r15 = 0
            r0.J4 = r15
            org.spongycastle.crypto.Digest r14 = r42.get()
            r0.O4 = r14
            int r14 = r14.e()
            r0.P4 = r14
            r0.D4 = r13
            int[] r14 = r41.d()
            r0.L4 = r14
            int[] r14 = r41.b()
            r0.M4 = r14
            int[] r14 = r41.a()
            r0.K4 = r14
            org.spongycastle.pqc.crypto.gmss.GMSSParameters r14 = r0.D4
            int r14 = r14.c()
            r0.N4 = r14
            if (r1 != 0) goto L_0x0061
            int[] r14 = new int[r14]
            r0.f = r14
            r14 = 0
        L_0x0052:
            int r15 = r0.N4
            if (r14 >= r15) goto L_0x0060
            int[] r15 = r0.f
            r17 = 0
            r15[r14] = r17
            int r14 = r14 + 1
            r15 = 0
            goto L_0x0052
        L_0x0060:
            goto L_0x0063
        L_0x0061:
            r0.f = r1
        L_0x0063:
            r0.q = r2
            r14 = r23
            r0.x = r14
            r15 = r24
            r0.y = r15
            r1 = r25
            r0.z = r1
            if (r3 != 0) goto L_0x00ae
            int r1 = r0.N4
            byte[][][] r1 = new byte[r1][][]
            r0.p4 = r1
            r1 = 0
        L_0x007a:
            int r13 = r0.N4
            if (r1 >= r13) goto L_0x00ad
            byte[][][] r13 = r0.p4
            int[] r14 = r0.K4
            r14 = r14[r1]
            r15 = 2
            int r14 = r14 / r15
            double r8 = (double) r14
            double r8 = java.lang.Math.floor(r8)
            int r8 = (int) r8
            int r9 = r0.P4
            int[] r14 = new int[r15]
            r15 = 1
            r14[r15] = r9
            r9 = 0
            r14[r9] = r8
            java.lang.Class<byte> r8 = byte.class
            java.lang.Object r8 = java.lang.reflect.Array.newInstance(r8, r14)
            byte[][] r8 = (byte[][]) r8
            r13[r1] = r8
            int r1 = r1 + 1
            r14 = r23
            r15 = r24
            r8 = r35
            r9 = r36
            r13 = r41
            goto L_0x007a
        L_0x00ad:
            goto L_0x00b0
        L_0x00ae:
            r0.p4 = r3
        L_0x00b0:
            if (r4 != 0) goto L_0x00ca
            int r1 = r0.N4
            java.util.Vector[] r1 = new java.util.Vector[r1]
            r0.p1 = r1
            r1 = 0
        L_0x00b9:
            int r8 = r0.N4
            if (r1 >= r8) goto L_0x00c9
            java.util.Vector[] r8 = r0.p1
            java.util.Vector r9 = new java.util.Vector
            r9.<init>()
            r8[r1] = r9
            int r1 = r1 + 1
            goto L_0x00b9
        L_0x00c9:
            goto L_0x00cc
        L_0x00ca:
            r0.p1 = r4
        L_0x00cc:
            if (r5 != 0) goto L_0x00ea
            int r1 = r0.N4
            r8 = 1
            int r1 = r1 - r8
            java.util.Vector[] r1 = new java.util.Vector[r1]
            r0.a2 = r1
            r1 = 0
        L_0x00d7:
            int r9 = r0.N4
            int r9 = r9 - r8
            if (r1 >= r9) goto L_0x00e9
            java.util.Vector[] r8 = r0.a2
            java.util.Vector r9 = new java.util.Vector
            r9.<init>()
            r8[r1] = r9
            int r1 = r1 + 1
            r8 = 1
            goto L_0x00d7
        L_0x00e9:
            goto L_0x00ec
        L_0x00ea:
            r0.a2 = r5
        L_0x00ec:
            r1 = r27
            r0.p0 = r1
            r8 = r28
            r0.a1 = r8
            r9 = r31
            r0.p2 = r9
            r13 = r32
            r0.p3 = r13
            r0.E4 = r10
            r14 = r42
            r0.I4 = r14
            if (r11 != 0) goto L_0x013c
            int r15 = r0.N4
            r16 = 1
            int r15 = r15 + -1
            org.spongycastle.pqc.crypto.gmss.GMSSRootCalc[] r15 = new org.spongycastle.pqc.crypto.gmss.GMSSRootCalc[r15]
            r0.F4 = r15
            r15 = 0
        L_0x010f:
            int r1 = r0.N4
            int r1 = r1 + -1
            if (r15 >= r1) goto L_0x013b
            org.spongycastle.pqc.crypto.gmss.GMSSRootCalc[] r1 = r0.F4
            org.spongycastle.pqc.crypto.gmss.GMSSRootCalc r3 = new org.spongycastle.pqc.crypto.gmss.GMSSRootCalc
            int[] r4 = r0.K4
            int r19 = r15 + 1
            r4 = r4[r19]
            int[] r5 = r0.M4
            int r19 = r15 + 1
            r5 = r5[r19]
            org.spongycastle.pqc.crypto.gmss.GMSSDigestProvider r8 = r0.I4
            r3.<init>(r4, r5, r8)
            r1[r15] = r3
            int r15 = r15 + 1
            r3 = r26
            r1 = r27
            r8 = r28
            r4 = r29
            r5 = r30
            r16 = 1
            goto L_0x010f
        L_0x013b:
            goto L_0x013e
        L_0x013c:
            r0.F4 = r11
        L_0x013e:
            r1 = r39
            r0.G4 = r1
            int r3 = r0.N4
            int[] r3 = new int[r3]
            r0.R4 = r3
            r3 = 0
        L_0x0149:
            int r4 = r0.N4
            if (r3 >= r4) goto L_0x015b
            int[] r4 = r0.R4
            int[] r5 = r0.K4
            r5 = r5[r3]
            r8 = 1
            int r5 = r8 << r5
            r4[r3] = r5
            int r3 = r3 + 1
            goto L_0x0149
        L_0x015b:
            org.spongycastle.pqc.crypto.gmss.util.GMSSRandom r3 = new org.spongycastle.pqc.crypto.gmss.util.GMSSRandom
            org.spongycastle.crypto.Digest r4 = r0.O4
            r3.<init>(r4)
            r0.Q4 = r3
            int r3 = r0.N4
            r4 = 1
            if (r3 <= r4) goto L_0x01a0
            if (r6 != 0) goto L_0x019d
            r4 = 2
            int r3 = r3 - r4
            org.spongycastle.pqc.crypto.gmss.GMSSLeaf[] r3 = new org.spongycastle.pqc.crypto.gmss.GMSSLeaf[r3]
            r0.z4 = r3
            r3 = 0
        L_0x0172:
            int r5 = r0.N4
            int r5 = r5 - r4
            if (r3 >= r5) goto L_0x019c
            org.spongycastle.pqc.crypto.gmss.GMSSLeaf[] r5 = r0.z4
            org.spongycastle.pqc.crypto.gmss.GMSSLeaf r8 = new org.spongycastle.pqc.crypto.gmss.GMSSLeaf
            org.spongycastle.crypto.Digest r15 = r42.get()
            int[] r4 = r0.L4
            int r19 = r3 + 1
            r4 = r4[r19]
            int[] r1 = r0.R4
            int r19 = r3 + 2
            r1 = r1[r19]
            byte[][] r9 = r0.x
            r9 = r9[r3]
            r8.<init>(r15, r4, r1, r9)
            r5[r3] = r8
            int r3 = r3 + 1
            r9 = r31
            r1 = r39
            r4 = 2
            goto L_0x0172
        L_0x019c:
            goto L_0x01a5
        L_0x019d:
            r0.z4 = r6
            goto L_0x01a5
        L_0x01a0:
            r1 = 0
            org.spongycastle.pqc.crypto.gmss.GMSSLeaf[] r3 = new org.spongycastle.pqc.crypto.gmss.GMSSLeaf[r1]
            r0.z4 = r3
        L_0x01a5:
            if (r7 != 0) goto L_0x01d5
            int r1 = r0.N4
            r3 = 1
            int r1 = r1 - r3
            org.spongycastle.pqc.crypto.gmss.GMSSLeaf[] r1 = new org.spongycastle.pqc.crypto.gmss.GMSSLeaf[r1]
            r0.A4 = r1
            r1 = 0
        L_0x01b0:
            int r4 = r0.N4
            int r4 = r4 - r3
            if (r1 >= r4) goto L_0x01d4
            org.spongycastle.pqc.crypto.gmss.GMSSLeaf[] r3 = r0.A4
            org.spongycastle.pqc.crypto.gmss.GMSSLeaf r4 = new org.spongycastle.pqc.crypto.gmss.GMSSLeaf
            org.spongycastle.crypto.Digest r5 = r42.get()
            int[] r8 = r0.L4
            r8 = r8[r1]
            int[] r9 = r0.R4
            int r15 = r1 + 1
            r9 = r9[r15]
            byte[][] r15 = r0.q
            r15 = r15[r1]
            r4.<init>(r5, r8, r9, r15)
            r3[r1] = r4
            int r1 = r1 + 1
            r3 = 1
            goto L_0x01b0
        L_0x01d4:
            goto L_0x01d7
        L_0x01d5:
            r0.A4 = r7
        L_0x01d7:
            r1 = r35
            if (r1 != 0) goto L_0x0205
            int r3 = r0.N4
            r4 = 1
            int r3 = r3 - r4
            org.spongycastle.pqc.crypto.gmss.GMSSLeaf[] r3 = new org.spongycastle.pqc.crypto.gmss.GMSSLeaf[r3]
            r0.B4 = r3
            r3 = 0
        L_0x01e4:
            int r5 = r0.N4
            int r5 = r5 - r4
            if (r3 >= r5) goto L_0x0204
            org.spongycastle.pqc.crypto.gmss.GMSSLeaf[] r4 = r0.B4
            org.spongycastle.pqc.crypto.gmss.GMSSLeaf r5 = new org.spongycastle.pqc.crypto.gmss.GMSSLeaf
            org.spongycastle.crypto.Digest r8 = r42.get()
            int[] r9 = r0.L4
            r9 = r9[r3]
            int[] r15 = r0.R4
            int r18 = r3 + 1
            r15 = r15[r18]
            r5.<init>(r8, r9, r15)
            r4[r3] = r5
            int r3 = r3 + 1
            r4 = 1
            goto L_0x01e4
        L_0x0204:
            goto L_0x0207
        L_0x0205:
            r0.B4 = r1
        L_0x0207:
            r3 = r36
            if (r3 != 0) goto L_0x0223
            int r4 = r0.N4
            r5 = 1
            int r4 = r4 - r5
            int[] r4 = new int[r4]
            r0.C4 = r4
            r4 = 0
        L_0x0214:
            int r8 = r0.N4
            int r8 = r8 - r5
            if (r4 >= r8) goto L_0x0222
            int[] r5 = r0.C4
            r8 = -1
            r5[r4] = r8
            int r4 = r4 + 1
            r5 = 1
            goto L_0x0214
        L_0x0222:
            goto L_0x0225
        L_0x0223:
            r0.C4 = r3
        L_0x0225:
            int r4 = r0.P4
            byte[] r5 = new byte[r4]
            byte[] r4 = new byte[r4]
            if (r12 != 0) goto L_0x0279
            int r8 = r0.N4
            r9 = 1
            int r8 = r8 - r9
            org.spongycastle.pqc.crypto.gmss.GMSSRootSig[] r8 = new org.spongycastle.pqc.crypto.gmss.GMSSRootSig[r8]
            r0.H4 = r8
            r8 = 0
        L_0x0236:
            int r15 = r0.N4
            int r15 = r15 - r9
            if (r8 >= r15) goto L_0x0278
            r15 = r2[r8]
            int r9 = r0.P4
            r1 = 0
            java.lang.System.arraycopy(r15, r1, r5, r1, r9)
            org.spongycastle.pqc.crypto.gmss.util.GMSSRandom r9 = r0.Q4
            r9.c(r5)
            org.spongycastle.pqc.crypto.gmss.util.GMSSRandom r9 = r0.Q4
            byte[] r4 = r9.c(r5)
            org.spongycastle.pqc.crypto.gmss.GMSSRootSig[] r9 = r0.H4
            org.spongycastle.pqc.crypto.gmss.GMSSRootSig r15 = new org.spongycastle.pqc.crypto.gmss.GMSSRootSig
            org.spongycastle.crypto.Digest r1 = r42.get()
            int[] r2 = r0.L4
            r2 = r2[r8]
            int[] r3 = r0.K4
            int r18 = r8 + 1
            r3 = r3[r18]
            r15.<init>(r1, r2, r3)
            r9[r8] = r15
            org.spongycastle.pqc.crypto.gmss.GMSSRootSig[] r1 = r0.H4
            r1 = r1[r8]
            r2 = r10[r8]
            r1.f(r4, r2)
            int r8 = r8 + 1
            r2 = r22
            r1 = r35
            r3 = r36
            r9 = 1
            goto L_0x0236
        L_0x0278:
            goto L_0x027b
        L_0x0279:
            r0.H4 = r12
        L_0x027b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.gmss.GMSSPrivateKeyParameters.<init>(int[], byte[][], byte[][], byte[][][], byte[][][], byte[][][], org.spongycastle.pqc.crypto.gmss.Treehash[][], org.spongycastle.pqc.crypto.gmss.Treehash[][], java.util.Vector[], java.util.Vector[], java.util.Vector[][], java.util.Vector[][], org.spongycastle.pqc.crypto.gmss.GMSSLeaf[], org.spongycastle.pqc.crypto.gmss.GMSSLeaf[], org.spongycastle.pqc.crypto.gmss.GMSSLeaf[], int[], byte[][], org.spongycastle.pqc.crypto.gmss.GMSSRootCalc[], byte[][], org.spongycastle.pqc.crypto.gmss.GMSSRootSig[], org.spongycastle.pqc.crypto.gmss.GMSSParameters, org.spongycastle.pqc.crypto.gmss.GMSSDigestProvider):void");
    }

    private GMSSPrivateKeyParameters(GMSSPrivateKeyParameters original) {
        super(true, original.b());
        this.J4 = false;
        this.f = Arrays.k(original.f);
        this.q = Arrays.p(original.q);
        this.x = Arrays.p(original.x);
        this.y = Arrays.q(original.y);
        this.z = Arrays.q(original.z);
        this.p0 = original.p0;
        this.a1 = original.a1;
        this.p1 = original.p1;
        this.a2 = original.a2;
        this.p2 = original.p2;
        this.p3 = original.p3;
        this.p4 = Arrays.q(original.p4);
        this.z4 = original.z4;
        this.A4 = original.A4;
        this.B4 = original.B4;
        this.C4 = original.C4;
        this.D4 = original.D4;
        this.E4 = Arrays.p(original.E4);
        this.F4 = original.F4;
        this.G4 = original.G4;
        this.H4 = original.H4;
        this.I4 = original.I4;
        this.K4 = original.K4;
        this.L4 = original.L4;
        this.M4 = original.M4;
        this.N4 = original.N4;
        this.O4 = original.O4;
        this.P4 = original.P4;
        this.Q4 = original.Q4;
        this.R4 = original.R4;
    }

    public boolean l() {
        return this.J4;
    }

    public void m() {
        this.J4 = true;
    }

    public GMSSPrivateKeyParameters n() {
        GMSSPrivateKeyParameters nKey = new GMSSPrivateKeyParameters(this);
        nKey.o(this.D4.c() - 1);
        return nKey;
    }

    private void o(int layer) {
        int i = this.N4;
        if (layer == i - 1) {
            int[] iArr = this.f;
            iArr[layer] = iArr[layer] + 1;
        }
        if (this.f[layer] != this.R4[layer]) {
            q(layer);
        } else if (i != 1) {
            p(layer);
            this.f[layer] = 0;
        }
    }

    private void p(int layer) {
        if (layer > 0) {
            int[] iArr = this.f;
            int i = layer - 1;
            iArr[i] = iArr[i] + 1;
            boolean lastTree = true;
            int z2 = layer;
            do {
                z2--;
                if (this.f[z2] < this.R4[z2]) {
                    lastTree = false;
                }
                if (!lastTree) {
                    break;
                }
            } while (z2 > 0);
            if (!lastTree) {
                this.Q4.c(this.q[layer]);
                this.H4[layer - 1].h();
                if (layer > 1) {
                    GMSSLeaf[] gMSSLeafArr = this.z4;
                    gMSSLeafArr[(layer - 1) - 1] = gMSSLeafArr[(layer - 1) - 1].f();
                }
                GMSSLeaf[] gMSSLeafArr2 = this.A4;
                gMSSLeafArr2[layer - 1] = gMSSLeafArr2[layer - 1].f();
                if (this.C4[layer - 1] >= 0) {
                    GMSSLeaf[] gMSSLeafArr3 = this.B4;
                    gMSSLeafArr3[layer - 1] = gMSSLeafArr3[layer - 1].f();
                    try {
                        this.p0[layer - 1][this.C4[layer - 1]].j(this.Q4, this.B4[layer - 1].a());
                        this.p0[layer - 1][this.C4[layer - 1]].l();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                r(layer);
                this.G4[layer - 1] = this.H4[layer - 1].b();
                for (int i2 = 0; i2 < this.K4[layer] - this.M4[layer]; i2++) {
                    Treehash[] treehashArr = this.p0[layer];
                    Treehash[][] treehashArr2 = this.a1;
                    treehashArr[i2] = treehashArr2[layer - 1][i2];
                    treehashArr2[layer - 1][i2] = this.F4[layer - 1].g()[i2];
                }
                for (int i3 = 0; i3 < this.K4[layer]; i3++) {
                    System.arraycopy(this.z[layer - 1][i3], 0, this.y[layer][i3], 0, this.P4);
                    System.arraycopy(this.F4[layer - 1].a()[i3], 0, this.z[layer - 1][i3], 0, this.P4);
                }
                for (int i4 = 0; i4 < this.M4[layer] - 1; i4++) {
                    Vector[] vectorArr = this.p2[layer];
                    Vector[][] vectorArr2 = this.p3;
                    vectorArr[i4] = vectorArr2[layer - 1][i4];
                    vectorArr2[layer - 1][i4] = this.F4[layer - 1].b()[i4];
                }
                Vector[] vectorArr3 = this.p1;
                Vector[] vectorArr4 = this.a2;
                vectorArr3[layer] = vectorArr4[layer - 1];
                vectorArr4[layer - 1] = this.F4[layer - 1].d();
                this.E4[layer - 1] = this.F4[layer - 1].c();
                int i5 = this.P4;
                byte[] bArr = new byte[i5];
                byte[] dummy = new byte[i5];
                System.arraycopy(this.q[layer - 1], 0, dummy, 0, i5);
                byte[] c = this.Q4.c(dummy);
                byte[] OTSseed = this.Q4.c(dummy);
                this.H4[layer - 1].f(this.Q4.c(dummy), this.E4[layer - 1]);
                o(layer - 1);
            }
        }
    }

    private void q(int layer) {
        c(layer);
        if (layer > 0) {
            if (layer > 1) {
                GMSSLeaf[] gMSSLeafArr = this.z4;
                gMSSLeafArr[(layer - 1) - 1] = gMSSLeafArr[(layer - 1) - 1].f();
            }
            GMSSLeaf[] gMSSLeafArr2 = this.A4;
            gMSSLeafArr2[layer - 1] = gMSSLeafArr2[layer - 1].f();
            int t = (int) Math.floor(((double) (i(layer) * 2)) / ((double) (this.K4[layer - 1] - this.M4[layer - 1])));
            int[] iArr = this.f;
            if (iArr[layer] % t == 1) {
                if (iArr[layer] > 1 && this.C4[layer - 1] >= 0) {
                    try {
                        this.p0[layer - 1][this.C4[layer - 1]].j(this.Q4, this.B4[layer - 1].a());
                        this.p0[layer - 1][this.C4[layer - 1]].l();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                this.C4[layer - 1] = h(layer - 1);
                int[] iArr2 = this.C4;
                if (iArr2[layer - 1] >= 0) {
                    this.B4[layer - 1] = new GMSSLeaf(this.I4.get(), this.L4[layer - 1], t, this.p0[layer - 1][iArr2[layer - 1]].d());
                    GMSSLeaf[] gMSSLeafArr3 = this.B4;
                    gMSSLeafArr3[layer - 1] = gMSSLeafArr3[layer - 1].f();
                }
            } else if (this.C4[layer - 1] >= 0) {
                GMSSLeaf[] gMSSLeafArr4 = this.B4;
                gMSSLeafArr4[layer - 1] = gMSSLeafArr4[layer - 1].f();
            }
            this.H4[layer - 1].h();
            if (this.f[layer] == 1) {
                this.F4[layer - 1].h(new Vector());
            }
            r(layer);
        }
    }

    private int h(int layer) {
        int minTreehash = -1;
        for (int h = 0; h < this.K4[layer] - this.M4[layer]; h++) {
            if (this.p0[layer][h].m() && !this.p0[layer][h].l()) {
                if (minTreehash == -1) {
                    minTreehash = h;
                } else if (this.p0[layer][h].c() < this.p0[layer][minTreehash].c()) {
                    minTreehash = h;
                }
            }
        }
        return minTreehash;
    }

    private void c(int layer) {
        byte[] help;
        int i = layer;
        int Phi = this.f[i];
        int H = this.K4[i];
        int K = this.M4[i];
        for (int i2 = 0; i2 < H - K; i2++) {
            this.p0[i][i2].k(this.Q4);
        }
        int Tau = k(Phi);
        byte[] bArr = new byte[this.P4];
        byte[] OTSseed = this.Q4.c(this.q[i]);
        int L = (Phi >>> (Tau + 1)) & 1;
        int i3 = this.P4;
        byte[] tempKeep = new byte[i3];
        int i4 = 0;
        if (Tau < H - 1 && L == 0) {
            System.arraycopy(this.y[i][Tau], 0, tempKeep, 0, i3);
        }
        int i5 = this.P4;
        byte[] bArr2 = new byte[i5];
        if (Tau == 0) {
            if (i == this.N4 - 1) {
                help = new WinternitzOTSignature(OTSseed, this.I4.get(), this.L4[i]).b();
            } else {
                byte[] dummy = new byte[i5];
                System.arraycopy(this.q[i], 0, dummy, 0, i5);
                this.Q4.c(dummy);
                byte[] help2 = this.A4[i].a();
                this.A4[i].e(dummy);
                help = help2;
            }
            System.arraycopy(help, 0, this.y[i][0], 0, this.P4);
        } else {
            byte[] toBeHashed = new byte[(i5 << 1)];
            System.arraycopy(this.y[i][Tau - 1], 0, toBeHashed, 0, i5);
            byte[] bArr3 = this.p4[i][(int) Math.floor((double) ((Tau - 1) / 2))];
            int i6 = this.P4;
            System.arraycopy(bArr3, 0, toBeHashed, i6, i6);
            this.O4.update(toBeHashed, 0, toBeHashed.length);
            this.y[i][Tau] = new byte[this.O4.e()];
            this.O4.c(this.y[i][Tau], 0);
            for (int i7 = 0; i7 < Tau; i7++) {
                if (i7 < H - K) {
                    if (this.p0[i][i7].l()) {
                        System.arraycopy(this.p0[i][i7].b(), 0, this.y[i][i7], 0, this.P4);
                        this.p0[i][i7].a();
                    } else {
                        System.err.println("Treehash (" + i + "," + i7 + ") not finished when needed in AuthPathComputation");
                    }
                }
                if (i7 < H - 1 && i7 >= H - K && this.p2[i][i7 - (H - K)].size() > 0) {
                    System.arraycopy(this.p2[i][i7 - (H - K)].lastElement(), 0, this.y[i][i7], 0, this.P4);
                    Vector[][] vectorArr = this.p2;
                    vectorArr[i][i7 - (H - K)].removeElementAt(vectorArr[i][i7 - (H - K)].size() - 1);
                }
                if (i7 < H - K && ((1 << i7) * 3) + Phi < this.R4[i]) {
                    this.p0[i][i7].g();
                }
            }
        }
        if (Tau < H - 1 && L == 0) {
            System.arraycopy(tempKeep, 0, this.p4[i][(int) Math.floor((double) (Tau / 2))], 0, this.P4);
        }
        if (i == this.N4 - 1) {
            int tmp = 1;
            while (tmp <= (H - K) / 2) {
                int minTreehash = h(layer);
                if (minTreehash >= 0) {
                    try {
                        byte[] seed = new byte[this.P4];
                        System.arraycopy(this.p0[i][minTreehash].d(), i4, seed, i4, this.P4);
                        byte[] bArr4 = seed;
                        WinternitzOTSignature ots = new WinternitzOTSignature(this.Q4.c(seed), this.I4.get(), this.L4[i]);
                        WinternitzOTSignature winternitzOTSignature = ots;
                        this.p0[i][minTreehash].j(this.Q4, ots.b());
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                tmp++;
                i4 = 0;
            }
            return;
        }
        this.C4[i] = h(layer);
    }

    private int k(int Phi) {
        if (Phi == 0) {
            return -1;
        }
        int Tau = 0;
        int modul = 1;
        while (Phi % modul == 0) {
            modul *= 2;
            Tau++;
        }
        return Tau - 1;
    }

    private void r(int layer) {
        byte[] bArr = new byte[this.P4];
        byte[] OTSseed = this.Q4.c(this.x[layer - 1]);
        if (layer == this.N4 - 1) {
            this.F4[layer - 1].k(this.x[layer - 1], new WinternitzOTSignature(OTSseed, this.I4.get(), this.L4[layer]).b());
            return;
        }
        this.F4[layer - 1].k(this.x[layer - 1], this.z4[layer - 1].a());
        this.z4[layer - 1].e(this.x[layer - 1]);
    }

    public int[] g() {
        return this.f;
    }

    public int f(int i) {
        return this.f[i];
    }

    public byte[][] e() {
        return Arrays.p(this.q);
    }

    public byte[][][] d() {
        return Arrays.q(this.y);
    }

    public byte[] j(int i) {
        return this.G4[i];
    }

    public int i(int i) {
        return this.R4[i];
    }
}
