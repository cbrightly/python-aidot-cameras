package org.spongycastle.pqc.crypto.xmss;

import java.util.ArrayList;
import java.util.List;
import org.spongycastle.pqc.crypto.xmss.OTSHashAddress;

public final class WOTSPlus {
    private final WOTSPlusParameters a;
    private final KeyedHashFunctions b;
    private byte[] c;
    private byte[] d;

    protected WOTSPlus(WOTSPlusParameters params) {
        if (params != null) {
            this.a = params;
            int n = params.b();
            this.b = new KeyedHashFunctions(params.a(), n);
            this.c = new byte[n];
            this.d = new byte[n];
            return;
        }
        throw new NullPointerException("params == null");
    }

    /* access modifiers changed from: package-private */
    public void j(byte[] secretKeySeed, byte[] publicSeed) {
        if (secretKeySeed == null) {
            throw new NullPointerException("secretKeySeed == null");
        } else if (secretKeySeed.length != this.a.b()) {
            throw new IllegalArgumentException("size of secretKeySeed needs to be equal to size of digest");
        } else if (publicSeed == null) {
            throw new NullPointerException("publicSeed == null");
        } else if (publicSeed.length == this.a.b()) {
            this.c = secretKeySeed;
            this.d = publicSeed;
        } else {
            throw new IllegalArgumentException("size of publicSeed needs to be equal to size of digest");
        }
    }

    /* JADX WARNING: type inference failed for: r6v13, types: [org.spongycastle.pqc.crypto.xmss.XMSSAddress] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.spongycastle.pqc.crypto.xmss.WOTSPlusSignature k(byte[] r10, org.spongycastle.pqc.crypto.xmss.OTSHashAddress r11) {
        /*
            r9 = this;
            if (r10 == 0) goto L_0x0105
            int r0 = r10.length
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r1 = r9.a
            int r1 = r1.b()
            if (r0 != r1) goto L_0x00fd
            if (r11 == 0) goto L_0x00f5
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r0 = r9.a
            int r0 = r0.f()
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r1 = r9.a
            int r1 = r1.d()
            java.util.List r0 = r9.b(r10, r0, r1)
            r1 = 0
            r2 = 0
        L_0x001f:
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r3 = r9.a
            int r3 = r3.d()
            if (r2 >= r3) goto L_0x003e
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r3 = r9.a
            int r3 = r3.f()
            int r3 = r3 + -1
            java.lang.Object r4 = r0.get(r2)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            int r3 = r3 - r4
            int r1 = r1 + r3
            int r2 = r2 + 1
            goto L_0x001f
        L_0x003e:
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r2 = r9.a
            int r2 = r2.e()
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r3 = r9.a
            int r3 = r3.f()
            int r3 = org.spongycastle.pqc.crypto.xmss.XMSSUtil.o(r3)
            int r2 = r2 * r3
            int r2 = r2 % 8
            int r2 = 8 - r2
            int r1 = r1 << r2
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r2 = r9.a
            int r2 = r2.e()
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r3 = r9.a
            int r3 = r3.f()
            int r3 = org.spongycastle.pqc.crypto.xmss.XMSSUtil.o(r3)
            int r2 = r2 * r3
            double r2 = (double) r2
            r4 = 4620693217682128896(0x4020000000000000, double:8.0)
            double r2 = r2 / r4
            double r2 = java.lang.Math.ceil(r2)
            int r2 = (int) r2
            long r3 = (long) r1
            byte[] r3 = org.spongycastle.pqc.crypto.xmss.XMSSUtil.q(r3, r2)
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r4 = r9.a
            int r4 = r4.f()
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r5 = r9.a
            int r5 = r5.e()
            java.util.List r3 = r9.b(r3, r4, r5)
            r0.addAll(r3)
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r4 = r9.a
            int r4 = r4.c()
            byte[][] r4 = new byte[r4][]
            r5 = 0
        L_0x008f:
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r6 = r9.a
            int r6 = r6.c()
            if (r5 >= r6) goto L_0x00ed
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = new org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder
            r6.<init>()
            int r7 = r11.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.g(r7)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r6
            long r7 = r11.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.h(r7)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r6
            int r7 = r11.g()
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = r6.p(r7)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = r6.n(r5)
            int r7 = r11.f()
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = r6.o(r7)
            int r7 = r11.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.f(r7)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r6
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r6 = r6.l()
            r11 = r6
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress r11 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress) r11
            byte[] r6 = r9.c(r5)
            r7 = 0
            java.lang.Object r8 = r0.get(r5)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            byte[] r6 = r9.a(r6, r7, r8, r11)
            r4[r5] = r6
            int r5 = r5 + 1
            goto L_0x008f
        L_0x00ed:
            org.spongycastle.pqc.crypto.xmss.WOTSPlusSignature r5 = new org.spongycastle.pqc.crypto.xmss.WOTSPlusSignature
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r6 = r9.a
            r5.<init>(r6, r4)
            return r5
        L_0x00f5:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "otsHashAddress == null"
            r0.<init>(r1)
            throw r0
        L_0x00fd:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "size of messageDigest needs to be equal to size of digest"
            r0.<init>(r1)
            throw r0
        L_0x0105:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "messageDigest == null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.xmss.WOTSPlus.k(byte[], org.spongycastle.pqc.crypto.xmss.OTSHashAddress):org.spongycastle.pqc.crypto.xmss.WOTSPlusSignature");
    }

    /* JADX WARNING: type inference failed for: r6v13, types: [org.spongycastle.pqc.crypto.xmss.XMSSAddress] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters g(byte[] r11, org.spongycastle.pqc.crypto.xmss.WOTSPlusSignature r12, org.spongycastle.pqc.crypto.xmss.OTSHashAddress r13) {
        /*
            r10 = this;
            if (r11 == 0) goto L_0x0123
            int r0 = r11.length
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r1 = r10.a
            int r1 = r1.b()
            if (r0 != r1) goto L_0x011b
            if (r12 == 0) goto L_0x0113
            if (r13 == 0) goto L_0x010b
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r0 = r10.a
            int r0 = r0.f()
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r1 = r10.a
            int r1 = r1.d()
            java.util.List r0 = r10.b(r11, r0, r1)
            r1 = 0
            r2 = 0
        L_0x0021:
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r3 = r10.a
            int r3 = r3.d()
            if (r2 >= r3) goto L_0x0040
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r3 = r10.a
            int r3 = r3.f()
            int r3 = r3 + -1
            java.lang.Object r4 = r0.get(r2)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            int r3 = r3 - r4
            int r1 = r1 + r3
            int r2 = r2 + 1
            goto L_0x0021
        L_0x0040:
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r2 = r10.a
            int r2 = r2.e()
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r3 = r10.a
            int r3 = r3.f()
            int r3 = org.spongycastle.pqc.crypto.xmss.XMSSUtil.o(r3)
            int r2 = r2 * r3
            int r2 = r2 % 8
            int r2 = 8 - r2
            int r1 = r1 << r2
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r2 = r10.a
            int r2 = r2.e()
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r3 = r10.a
            int r3 = r3.f()
            int r3 = org.spongycastle.pqc.crypto.xmss.XMSSUtil.o(r3)
            int r2 = r2 * r3
            double r2 = (double) r2
            r4 = 4620693217682128896(0x4020000000000000, double:8.0)
            double r2 = r2 / r4
            double r2 = java.lang.Math.ceil(r2)
            int r2 = (int) r2
            long r3 = (long) r1
            byte[] r3 = org.spongycastle.pqc.crypto.xmss.XMSSUtil.q(r3, r2)
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r4 = r10.a
            int r4 = r4.f()
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r5 = r10.a
            int r5 = r5.e()
            java.util.List r3 = r10.b(r3, r4, r5)
            r0.addAll(r3)
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r4 = r10.a
            int r4 = r4.c()
            byte[][] r4 = new byte[r4][]
            r5 = 0
        L_0x0091:
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r6 = r10.a
            int r6 = r6.c()
            if (r5 >= r6) goto L_0x0103
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = new org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder
            r6.<init>()
            int r7 = r13.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.g(r7)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r6
            long r7 = r13.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.h(r7)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r6
            int r7 = r13.g()
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = r6.p(r7)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = r6.n(r5)
            int r7 = r13.f()
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = r6.o(r7)
            int r7 = r13.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.f(r7)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r6
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r6 = r6.l()
            r13 = r6
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress r13 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress) r13
            byte[][] r6 = r12.a()
            r6 = r6[r5]
            java.lang.Object r7 = r0.get(r5)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r8 = r10.a
            int r8 = r8.f()
            int r8 = r8 + -1
            java.lang.Object r9 = r0.get(r5)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r8 = r8 - r9
            byte[] r6 = r10.a(r6, r7, r8, r13)
            r4[r5] = r6
            int r5 = r5 + 1
            goto L_0x0091
        L_0x0103:
            org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters r5 = new org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r6 = r10.a
            r5.<init>(r6, r4)
            return r5
        L_0x010b:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "otsHashAddress == null"
            r0.<init>(r1)
            throw r0
        L_0x0113:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "signature == null"
            r0.<init>(r1)
            throw r0
        L_0x011b:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "size of messageDigest needs to be equal to size of digest"
            r0.<init>(r1)
            throw r0
        L_0x0123:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "messageDigest == null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.xmss.WOTSPlus.g(byte[], org.spongycastle.pqc.crypto.xmss.WOTSPlusSignature, org.spongycastle.pqc.crypto.xmss.OTSHashAddress):org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters");
    }

    private byte[] a(byte[] startHash, int startIndex, int steps, OTSHashAddress otsHashAddress) {
        int n = this.a.b();
        if (startHash == null) {
            throw new NullPointerException("startHash == null");
        } else if (startHash.length != n) {
            throw new IllegalArgumentException("startHash needs to be " + n + "bytes");
        } else if (otsHashAddress == null) {
            throw new NullPointerException("otsHashAddress == null");
        } else if (otsHashAddress.d() == null) {
            throw new NullPointerException("otsHashAddress byte array == null");
        } else if (startIndex + steps > this.a.f() - 1) {
            throw new IllegalArgumentException("max chain length must not be greater than w");
        } else if (steps == 0) {
            return startHash;
        } else {
            byte[] tmp = a(startHash, startIndex, steps - 1, otsHashAddress);
            OTSHashAddress otsHashAddress2 = (OTSHashAddress) ((OTSHashAddress.Builder) ((OTSHashAddress.Builder) ((OTSHashAddress.Builder) new OTSHashAddress.Builder().g(otsHashAddress.b())).h(otsHashAddress.c())).p(otsHashAddress.g()).n(otsHashAddress.e()).o((startIndex + steps) - 1).f(0)).l();
            byte[] key = this.b.d(this.d, otsHashAddress2.d());
            byte[] bitmask = this.b.d(this.d, ((OTSHashAddress) ((OTSHashAddress.Builder) ((OTSHashAddress.Builder) ((OTSHashAddress.Builder) new OTSHashAddress.Builder().g(otsHashAddress2.b())).h(otsHashAddress2.c())).p(otsHashAddress2.g()).n(otsHashAddress2.e()).o(otsHashAddress2.f()).f(1)).l()).d());
            byte[] tmpMasked = new byte[n];
            for (int i = 0; i < n; i++) {
                tmpMasked[i] = (byte) (tmp[i] ^ bitmask[i]);
            }
            return this.b.a(key, tmpMasked);
        }
    }

    private List<Integer> b(byte[] messageDigest, int w, int outLength) {
        if (messageDigest == null) {
            throw new NullPointerException("msg == null");
        } else if (w == 4 || w == 16) {
            int logW = XMSSUtil.o(w);
            if (outLength <= (messageDigest.length * 8) / logW) {
                ArrayList<Integer> res = new ArrayList<>();
                for (int i = 0; i < messageDigest.length; i++) {
                    for (int j = 8 - logW; j >= 0; j -= logW) {
                        res.add(Integer.valueOf((messageDigest[i] >> j) & (w - 1)));
                        if (res.size() == outLength) {
                            return res;
                        }
                    }
                }
                return res;
            }
            throw new IllegalArgumentException("outLength too big");
        } else {
            throw new IllegalArgumentException("w needs to be 4 or 16");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] i(byte[] secretKeySeed, OTSHashAddress otsHashAddress) {
        return this.b.d(secretKeySeed, ((OTSHashAddress) ((OTSHashAddress.Builder) ((OTSHashAddress.Builder) new OTSHashAddress.Builder().g(otsHashAddress.b())).h(otsHashAddress.c())).p(otsHashAddress.g()).l()).d());
    }

    private byte[] c(int index) {
        if (index >= 0 && index < this.a.c()) {
            return this.b.d(this.c, XMSSUtil.q((long) index, 32));
        }
        throw new IllegalArgumentException("index out of bounds");
    }

    /* access modifiers changed from: protected */
    public WOTSPlusParameters e() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public KeyedHashFunctions d() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public byte[] h() {
        return XMSSUtil.c(this.d);
    }

    /* JADX WARNING: type inference failed for: r2v13, types: [org.spongycastle.pqc.crypto.xmss.XMSSAddress] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters f(org.spongycastle.pqc.crypto.xmss.OTSHashAddress r6) {
        /*
            r5 = this;
            if (r6 == 0) goto L_0x006f
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r0 = r5.a
            int r0 = r0.c()
            byte[][] r0 = new byte[r0][]
            r1 = 0
        L_0x000b:
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r2 = r5.a
            int r2 = r2.c()
            if (r1 >= r2) goto L_0x0067
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r2 = new org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder
            r2.<init>()
            int r3 = r6.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r2 = r2.g(r3)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r2 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r2
            long r3 = r6.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r2 = r2.h(r3)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r2 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r2
            int r3 = r6.g()
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r2 = r2.p(r3)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r2 = r2.n(r1)
            int r3 = r6.f()
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r2 = r2.o(r3)
            int r3 = r6.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r2 = r2.f(r3)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r2 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r2
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r2 = r2.l()
            r6 = r2
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress r6 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress) r6
            byte[] r2 = r5.c(r1)
            r3 = 0
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r4 = r5.a
            int r4 = r4.f()
            int r4 = r4 + -1
            byte[] r2 = r5.a(r2, r3, r4, r6)
            r0[r1] = r2
            int r1 = r1 + 1
            goto L_0x000b
        L_0x0067:
            org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters r1 = new org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r2 = r5.a
            r1.<init>(r2, r0)
            return r1
        L_0x006f:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "otsHashAddress == null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.xmss.WOTSPlus.f(org.spongycastle.pqc.crypto.xmss.OTSHashAddress):org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters");
    }
}
