package org.spongycastle.pqc.crypto.xmss;

public class XMSSVerifierUtil {
    XMSSVerifierUtil() {
    }

    /* JADX WARNING: type inference failed for: r12v19, types: [org.spongycastle.pqc.crypto.xmss.XMSSAddress] */
    /* JADX WARNING: type inference failed for: r12v34, types: [org.spongycastle.pqc.crypto.xmss.XMSSAddress] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static org.spongycastle.pqc.crypto.xmss.XMSSNode a(org.spongycastle.pqc.crypto.xmss.WOTSPlus r16, int r17, byte[] r18, org.spongycastle.pqc.crypto.xmss.XMSSReducedSignature r19, org.spongycastle.pqc.crypto.xmss.OTSHashAddress r20, int r21) {
        /*
            r0 = r16
            r1 = r18
            r2 = r20
            int r3 = r1.length
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r4 = r16.e()
            int r4 = r4.b()
            if (r3 != r4) goto L_0x01a7
            if (r19 == 0) goto L_0x019d
            if (r2 == 0) goto L_0x0193
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = new org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder
            r3.<init>()
            int r4 = r20.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r3 = r3.g(r4)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r3
            long r4 = r20.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r3 = r3.h(r4)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r3
            int r4 = r20.g()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = r3.n(r4)
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r3 = r3.l()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress r3 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress) r3
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r4.<init>()
            int r5 = r20.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.g(r5)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            long r5 = r20.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.h(r5)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            int r5 = r20.g()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = r4.n(r5)
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r4 = r4.k()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r4
            org.spongycastle.pqc.crypto.xmss.WOTSPlusSignature r5 = r19.c()
            org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters r5 = r0.g(r1, r5, r2)
            r6 = 2
            org.spongycastle.pqc.crypto.xmss.XMSSNode[] r7 = new org.spongycastle.pqc.crypto.xmss.XMSSNode[r6]
            org.spongycastle.pqc.crypto.xmss.XMSSNode r8 = org.spongycastle.pqc.crypto.xmss.XMSSNodeUtil.a(r0, r5, r3)
            r9 = 0
            r7[r9] = r8
            r8 = 0
        L_0x0077:
            r10 = r17
            if (r8 >= r10) goto L_0x0190
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r11 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r11.<init>()
            int r12 = r4.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r11 = r11.g(r12)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r11 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r11
            long r12 = r4.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r11 = r11.h(r12)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r11 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r11
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r11 = r11.m(r8)
            int r12 = r4.f()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r11 = r11.n(r12)
            int r12 = r4.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r11 = r11.f(r12)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r11 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r11
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r11 = r11.k()
            r4 = r11
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r4
            r11 = 1
            int r12 = r11 << r8
            int r12 = r21 / r12
            double r12 = (double) r12
            double r12 = java.lang.Math.floor(r12)
            r14 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r12 = r12 % r14
            r14 = 0
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 != 0) goto L_0x0126
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r12.<init>()
            int r13 = r4.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r12 = r12.g(r13)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r12
            long r13 = r4.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r12 = r12.h(r13)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r12
            int r13 = r4.e()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = r12.m(r13)
            int r13 = r4.f()
            int r13 = r13 / r6
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = r12.n(r13)
            int r13 = r4.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r12 = r12.f(r13)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r12
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r12 = r12.k()
            r4 = r12
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r4
            r12 = r7[r9]
            java.util.List r13 = r19.a()
            java.lang.Object r13 = r13.get(r8)
            org.spongycastle.pqc.crypto.xmss.XMSSNode r13 = (org.spongycastle.pqc.crypto.xmss.XMSSNode) r13
            org.spongycastle.pqc.crypto.xmss.XMSSNode r12 = org.spongycastle.pqc.crypto.xmss.XMSSNodeUtil.b(r0, r12, r13, r4)
            r7[r11] = r12
            org.spongycastle.pqc.crypto.xmss.XMSSNode r12 = new org.spongycastle.pqc.crypto.xmss.XMSSNode
            r13 = r7[r11]
            int r13 = r13.getHeight()
            int r13 = r13 + r11
            r14 = r7[r11]
            byte[] r14 = r14.getValue()
            r12.<init>(r13, r14)
            r7[r11] = r12
            goto L_0x0188
        L_0x0126:
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r12.<init>()
            int r13 = r4.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r12 = r12.g(r13)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r12
            long r13 = r4.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r12 = r12.h(r13)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r12
            int r13 = r4.e()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = r12.m(r13)
            int r13 = r4.f()
            int r13 = r13 - r11
            int r13 = r13 / r6
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = r12.n(r13)
            int r13 = r4.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r12 = r12.f(r13)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r12 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r12
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r12 = r12.k()
            r4 = r12
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r4
            java.util.List r12 = r19.a()
            java.lang.Object r12 = r12.get(r8)
            org.spongycastle.pqc.crypto.xmss.XMSSNode r12 = (org.spongycastle.pqc.crypto.xmss.XMSSNode) r12
            r13 = r7[r9]
            org.spongycastle.pqc.crypto.xmss.XMSSNode r12 = org.spongycastle.pqc.crypto.xmss.XMSSNodeUtil.b(r0, r12, r13, r4)
            r7[r11] = r12
            org.spongycastle.pqc.crypto.xmss.XMSSNode r12 = new org.spongycastle.pqc.crypto.xmss.XMSSNode
            r13 = r7[r11]
            int r13 = r13.getHeight()
            int r13 = r13 + r11
            r14 = r7[r11]
            byte[] r14 = r14.getValue()
            r12.<init>(r13, r14)
            r7[r11] = r12
        L_0x0188:
            r11 = r7[r11]
            r7[r9] = r11
            int r8 = r8 + 1
            goto L_0x0077
        L_0x0190:
            r6 = r7[r9]
            return r6
        L_0x0193:
            r10 = r17
            java.lang.NullPointerException r3 = new java.lang.NullPointerException
            java.lang.String r4 = "otsHashAddress == null"
            r3.<init>(r4)
            throw r3
        L_0x019d:
            r10 = r17
            java.lang.NullPointerException r3 = new java.lang.NullPointerException
            java.lang.String r4 = "signature == null"
            r3.<init>(r4)
            throw r3
        L_0x01a7:
            r10 = r17
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r4 = "size of messageDigest needs to be equal to size of digest"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.xmss.XMSSVerifierUtil.a(org.spongycastle.pqc.crypto.xmss.WOTSPlus, int, byte[], org.spongycastle.pqc.crypto.xmss.XMSSReducedSignature, org.spongycastle.pqc.crypto.xmss.OTSHashAddress, int):org.spongycastle.pqc.crypto.xmss.XMSSNode");
    }
}
