package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.pqc.crypto.xmss.HashTreeAddress;
import org.spongycastle.pqc.crypto.xmss.LTreeAddress;

public class XMSSNodeUtil {
    XMSSNodeUtil() {
    }

    /* JADX WARNING: type inference failed for: r6v27, types: [org.spongycastle.pqc.crypto.xmss.XMSSAddress] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static org.spongycastle.pqc.crypto.xmss.XMSSNode a(org.spongycastle.pqc.crypto.xmss.WOTSPlus r10, org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters r11, org.spongycastle.pqc.crypto.xmss.LTreeAddress r12) {
        /*
            if (r11 == 0) goto L_0x012b
            if (r12 == 0) goto L_0x0123
            org.spongycastle.pqc.crypto.xmss.WOTSPlusParameters r0 = r10.e()
            int r0 = r0.c()
            byte[][] r1 = r11.a()
            int r2 = r1.length
            org.spongycastle.pqc.crypto.xmss.XMSSNode[] r2 = new org.spongycastle.pqc.crypto.xmss.XMSSNode[r2]
            r3 = 0
        L_0x0014:
            int r4 = r1.length
            r5 = 0
            if (r3 >= r4) goto L_0x0024
            org.spongycastle.pqc.crypto.xmss.XMSSNode r4 = new org.spongycastle.pqc.crypto.xmss.XMSSNode
            r6 = r1[r3]
            r4.<init>(r5, r6)
            r2[r3] = r4
            int r3 = r3 + 1
            goto L_0x0014
        L_0x0024:
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = new org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder
            r3.<init>()
            int r4 = r12.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r3 = r3.g(r4)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r3
            long r6 = r12.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r3 = r3.h(r6)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r3
            int r4 = r12.e()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = r3.n(r4)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = r3.o(r5)
            int r4 = r12.g()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = r3.p(r4)
            int r4 = r12.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r3 = r3.f(r4)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r3
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r3 = r3.l()
            r12 = r3
            org.spongycastle.pqc.crypto.xmss.LTreeAddress r12 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress) r12
        L_0x0062:
            r3 = 1
            if (r0 <= r3) goto L_0x0120
            r4 = 0
        L_0x0066:
            int r6 = r0 / 2
            double r6 = (double) r6
            double r6 = java.lang.Math.floor(r6)
            int r6 = (int) r6
            if (r4 >= r6) goto L_0x00c0
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r6 = new org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder
            r6.<init>()
            int r7 = r12.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.g(r7)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r6
            long r7 = r12.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.h(r7)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r6
            int r7 = r12.e()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r6 = r6.n(r7)
            int r7 = r12.f()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r6 = r6.o(r7)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r6 = r6.p(r4)
            int r7 = r12.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.f(r7)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r6
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r6 = r6.l()
            r12 = r6
            org.spongycastle.pqc.crypto.xmss.LTreeAddress r12 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress) r12
            int r6 = r4 * 2
            r6 = r2[r6]
            int r7 = r4 * 2
            int r7 = r7 + r3
            r7 = r2[r7]
            org.spongycastle.pqc.crypto.xmss.XMSSNode r6 = b(r10, r6, r7, r12)
            r2[r4] = r6
            int r4 = r4 + 1
            goto L_0x0066
        L_0x00c0:
            int r4 = r0 % 2
            if (r4 != r3) goto L_0x00d2
            int r4 = r0 / 2
            double r6 = (double) r4
            double r6 = java.lang.Math.floor(r6)
            int r4 = (int) r6
            int r6 = r0 + -1
            r6 = r2[r6]
            r2[r4] = r6
        L_0x00d2:
            double r6 = (double) r0
            r8 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r6 = r6 / r8
            double r6 = java.lang.Math.ceil(r6)
            int r0 = (int) r6
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r4 = new org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder
            r4.<init>()
            int r6 = r12.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.g(r6)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r4
            long r6 = r12.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.h(r6)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r4
            int r6 = r12.e()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r4 = r4.n(r6)
            int r6 = r12.f()
            int r6 = r6 + r3
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = r4.o(r6)
            int r4 = r12.g()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = r3.p(r4)
            int r4 = r12.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r3 = r3.f(r4)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r3 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r3
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r3 = r3.l()
            r12 = r3
            org.spongycastle.pqc.crypto.xmss.LTreeAddress r12 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress) r12
            goto L_0x0062
        L_0x0120:
            r3 = r2[r5]
            return r3
        L_0x0123:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "address == null"
            r0.<init>(r1)
            throw r0
        L_0x012b:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "publicKey == null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.xmss.XMSSNodeUtil.a(org.spongycastle.pqc.crypto.xmss.WOTSPlus, org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters, org.spongycastle.pqc.crypto.xmss.LTreeAddress):org.spongycastle.pqc.crypto.xmss.XMSSNode");
    }

    static XMSSNode b(WOTSPlus wotsPlus, XMSSNode left, XMSSNode right, XMSSAddress address) {
        if (left == null) {
            throw new NullPointerException("left == null");
        } else if (right == null) {
            throw new NullPointerException("right == null");
        } else if (left.getHeight() != right.getHeight()) {
            throw new IllegalStateException("height of both nodes must be equal");
        } else if (address != null) {
            byte[] publicSeed = wotsPlus.h();
            if (address instanceof LTreeAddress) {
                LTreeAddress tmpAddress = (LTreeAddress) address;
                address = (LTreeAddress) ((LTreeAddress.Builder) ((LTreeAddress.Builder) ((LTreeAddress.Builder) new LTreeAddress.Builder().g(tmpAddress.b())).h(tmpAddress.c())).n(tmpAddress.e()).o(tmpAddress.f()).p(tmpAddress.g()).f(0)).l();
            } else if (address instanceof HashTreeAddress) {
                HashTreeAddress tmpAddress2 = (HashTreeAddress) address;
                address = (HashTreeAddress) ((HashTreeAddress.Builder) ((HashTreeAddress.Builder) ((HashTreeAddress.Builder) new HashTreeAddress.Builder().g(tmpAddress2.b())).h(tmpAddress2.c())).m(tmpAddress2.e()).n(tmpAddress2.f()).f(0)).k();
            }
            byte[] key = wotsPlus.d().d(publicSeed, address.d());
            if (address instanceof LTreeAddress) {
                LTreeAddress tmpAddress3 = (LTreeAddress) address;
                address = (LTreeAddress) ((LTreeAddress.Builder) ((LTreeAddress.Builder) ((LTreeAddress.Builder) new LTreeAddress.Builder().g(tmpAddress3.b())).h(tmpAddress3.c())).n(tmpAddress3.e()).o(tmpAddress3.f()).p(tmpAddress3.g()).f(1)).l();
            } else if (address instanceof HashTreeAddress) {
                HashTreeAddress tmpAddress4 = (HashTreeAddress) address;
                address = (HashTreeAddress) ((HashTreeAddress.Builder) ((HashTreeAddress.Builder) ((HashTreeAddress.Builder) new HashTreeAddress.Builder().g(tmpAddress4.b())).h(tmpAddress4.c())).m(tmpAddress4.e()).n(tmpAddress4.f()).f(1)).k();
            }
            byte[] bitmask0 = wotsPlus.d().d(publicSeed, address.d());
            if (address instanceof LTreeAddress) {
                LTreeAddress tmpAddress5 = (LTreeAddress) address;
                address = (LTreeAddress) ((LTreeAddress.Builder) ((LTreeAddress.Builder) ((LTreeAddress.Builder) new LTreeAddress.Builder().g(tmpAddress5.b())).h(tmpAddress5.c())).n(tmpAddress5.e()).o(tmpAddress5.f()).p(tmpAddress5.g()).f(2)).l();
            } else if (address instanceof HashTreeAddress) {
                HashTreeAddress tmpAddress6 = (HashTreeAddress) address;
                address = (HashTreeAddress) ((HashTreeAddress.Builder) ((HashTreeAddress.Builder) ((HashTreeAddress.Builder) new HashTreeAddress.Builder().g(tmpAddress6.b())).h(tmpAddress6.c())).m(tmpAddress6.e()).n(tmpAddress6.f()).f(2)).k();
            }
            byte[] bitmask1 = wotsPlus.d().d(publicSeed, address.d());
            int n = wotsPlus.e().b();
            byte[] tmpMask = new byte[(n * 2)];
            for (int i = 0; i < n; i++) {
                tmpMask[i] = (byte) (left.getValue()[i] ^ bitmask0[i]);
            }
            for (int i2 = 0; i2 < n; i2++) {
                tmpMask[i2 + n] = (byte) (right.getValue()[i2] ^ bitmask1[i2]);
            }
            return new XMSSNode(left.getHeight(), wotsPlus.d().b(key, tmpMask));
        } else {
            throw new NullPointerException("address == null");
        }
    }
}
