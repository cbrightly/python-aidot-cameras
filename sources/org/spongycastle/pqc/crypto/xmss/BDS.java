package org.spongycastle.pqc.crypto.xmss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import org.spongycastle.pqc.crypto.xmss.HashTreeAddress;
import org.spongycastle.pqc.crypto.xmss.LTreeAddress;
import org.spongycastle.pqc.crypto.xmss.OTSHashAddress;

public final class BDS implements Serializable {
    private static final long serialVersionUID = 1;
    private List<XMSSNode> authenticationPath;
    private transient WOTSPlus c;
    private int index;
    private int k;
    private Map<Integer, XMSSNode> keep;
    private Map<Integer, LinkedList<XMSSNode>> retain;
    private XMSSNode root;
    private Stack<XMSSNode> stack;
    private final List<BDSTreeHash> treeHashInstances;
    private final int treeHeight;
    private boolean used;

    BDS(XMSSParameters params, int index2) {
        this(params.f(), params.d(), params.e());
        this.index = index2;
        this.used = true;
    }

    BDS(XMSSParameters params, byte[] publicSeed, byte[] secretKeySeed, OTSHashAddress otsHashAddress) {
        this(params.f(), params.d(), params.e());
        b(publicSeed, secretKeySeed, otsHashAddress);
    }

    BDS(XMSSParameters params, byte[] publicSeed, byte[] secretKeySeed, OTSHashAddress otsHashAddress, int index2) {
        this(params.f(), params.d(), params.e());
        b(publicSeed, secretKeySeed, otsHashAddress);
        while (this.index < index2) {
            c(publicSeed, secretKeySeed, otsHashAddress);
            this.used = false;
        }
    }

    private BDS(WOTSPlus wotsPlus, int treeHeight2, int k2) {
        this.c = wotsPlus;
        this.treeHeight = treeHeight2;
        this.k = k2;
        if (k2 > treeHeight2 || k2 < 2 || (treeHeight2 - k2) % 2 != 0) {
            throw new IllegalArgumentException("illegal value for BDS parameter k");
        }
        this.authenticationPath = new ArrayList();
        this.retain = new TreeMap();
        this.stack = new Stack<>();
        this.treeHashInstances = new ArrayList();
        for (int height = 0; height < treeHeight2 - k2; height++) {
            this.treeHashInstances.add(new BDSTreeHash(height));
        }
        this.keep = new TreeMap();
        this.index = 0;
        this.used = false;
    }

    private BDS(BDS last, byte[] publicSeed, byte[] secretKeySeed, OTSHashAddress otsHashAddress) {
        this.c = last.c;
        this.treeHeight = last.treeHeight;
        this.k = last.k;
        this.root = last.root;
        this.authenticationPath = new ArrayList(last.authenticationPath);
        this.retain = last.retain;
        this.stack = (Stack) last.stack.clone();
        this.treeHashInstances = last.treeHashInstances;
        this.keep = new TreeMap(last.keep);
        this.index = last.index;
        c(publicSeed, secretKeySeed, otsHashAddress);
        last.used = true;
    }

    public BDS getNextState(byte[] publicSeed, byte[] secretKeySeed, OTSHashAddress otsHashAddress) {
        return new BDS(this, publicSeed, secretKeySeed, otsHashAddress);
    }

    /* JADX WARNING: type inference failed for: r3v12, types: [org.spongycastle.pqc.crypto.xmss.XMSSAddress] */
    /* JADX WARNING: type inference failed for: r5v16, types: [org.spongycastle.pqc.crypto.xmss.XMSSAddress] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(byte[] r12, byte[] r13, org.spongycastle.pqc.crypto.xmss.OTSHashAddress r14) {
        /*
            r11 = this;
            if (r14 == 0) goto L_0x0262
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r0 = new org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder
            r0.<init>()
            int r1 = r14.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r0 = r0.g(r1)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r0 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r0
            long r1 = r14.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r0 = r0.h(r1)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r0 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r0
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r0 = r0.l()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress r0 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress) r0
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r1 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r1.<init>()
            int r2 = r14.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r1 = r1.g(r2)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r1
            long r2 = r14.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r1 = r1.h(r2)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r1
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r1 = r1.k()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r1
            r2 = 0
        L_0x0041:
            int r3 = r11.treeHeight
            r4 = 1
            int r3 = r4 << r3
            if (r2 >= r3) goto L_0x0257
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r3 = new org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder
            r3.<init>()
            int r5 = r14.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r3 = r3.g(r5)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r3 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r3
            long r5 = r14.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r3 = r3.h(r5)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r3 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r3
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r3 = r3.p(r2)
            int r5 = r14.e()
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r3 = r3.n(r5)
            int r5 = r14.f()
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r3 = r3.o(r5)
            int r5 = r14.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r3 = r3.f(r5)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r3 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r3
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r3 = r3.l()
            r14 = r3
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress r14 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress) r14
            org.spongycastle.pqc.crypto.xmss.WOTSPlus r3 = r11.c
            byte[] r5 = r3.i(r13, r14)
            r3.j(r5, r12)
            org.spongycastle.pqc.crypto.xmss.WOTSPlus r3 = r11.c
            org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters r3 = r3.f(r14)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r5 = new org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder
            r5.<init>()
            int r6 = r0.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r5 = r5.g(r6)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r5 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r5
            long r6 = r0.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r5 = r5.h(r6)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r5 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r5
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r5 = r5.n(r2)
            int r6 = r0.f()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r5 = r5.o(r6)
            int r6 = r0.g()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r5 = r5.p(r6)
            int r6 = r0.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r5 = r5.f(r6)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r5 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r5
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r5 = r5.l()
            r0 = r5
            org.spongycastle.pqc.crypto.xmss.LTreeAddress r0 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress) r0
            org.spongycastle.pqc.crypto.xmss.WOTSPlus r5 = r11.c
            org.spongycastle.pqc.crypto.xmss.XMSSNode r5 = org.spongycastle.pqc.crypto.xmss.XMSSNodeUtil.a(r5, r3, r0)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r6 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r6.<init>()
            int r7 = r1.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.g(r7)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r6
            long r7 = r1.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.h(r7)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r6
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r6 = r6.n(r2)
            int r7 = r1.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r6 = r6.f(r7)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r6 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r6
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r6 = r6.k()
            r1 = r6
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r1
        L_0x0107:
            java.util.Stack<org.spongycastle.pqc.crypto.xmss.XMSSNode> r6 = r11.stack
            boolean r6 = r6.isEmpty()
            if (r6 != 0) goto L_0x024e
            java.util.Stack<org.spongycastle.pqc.crypto.xmss.XMSSNode> r6 = r11.stack
            java.lang.Object r6 = r6.peek()
            org.spongycastle.pqc.crypto.xmss.XMSSNode r6 = (org.spongycastle.pqc.crypto.xmss.XMSSNode) r6
            int r6 = r6.getHeight()
            int r7 = r5.getHeight()
            if (r6 != r7) goto L_0x024e
            int r6 = r5.getHeight()
            int r6 = r4 << r6
            int r6 = r2 / r6
            double r6 = (double) r6
            double r6 = java.lang.Math.floor(r6)
            int r6 = (int) r6
            if (r6 != r4) goto L_0x013a
            java.util.List<org.spongycastle.pqc.crypto.xmss.XMSSNode> r7 = r11.authenticationPath
            org.spongycastle.pqc.crypto.xmss.XMSSNode r8 = r5.clone()
            r7.add(r8)
        L_0x013a:
            r7 = 3
            if (r6 != r7) goto L_0x015b
            int r8 = r5.getHeight()
            int r9 = r11.treeHeight
            int r10 = r11.k
            int r9 = r9 - r10
            if (r8 >= r9) goto L_0x015b
            java.util.List<org.spongycastle.pqc.crypto.xmss.BDSTreeHash> r8 = r11.treeHashInstances
            int r9 = r5.getHeight()
            java.lang.Object r8 = r8.get(r9)
            org.spongycastle.pqc.crypto.xmss.BDSTreeHash r8 = (org.spongycastle.pqc.crypto.xmss.BDSTreeHash) r8
            org.spongycastle.pqc.crypto.xmss.XMSSNode r9 = r5.clone()
            r8.setNode(r9)
        L_0x015b:
            if (r6 < r7) goto L_0x01b7
            r7 = r6 & 1
            if (r7 != r4) goto L_0x01b7
            int r7 = r5.getHeight()
            int r8 = r11.treeHeight
            int r9 = r11.k
            int r8 = r8 - r9
            if (r7 < r8) goto L_0x01b7
            int r7 = r5.getHeight()
            int r8 = r11.treeHeight
            int r8 = r8 + -2
            if (r7 > r8) goto L_0x01b7
            java.util.Map<java.lang.Integer, java.util.LinkedList<org.spongycastle.pqc.crypto.xmss.XMSSNode>> r7 = r11.retain
            int r8 = r5.getHeight()
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            java.lang.Object r7 = r7.get(r8)
            if (r7 != 0) goto L_0x01a0
            java.util.LinkedList r7 = new java.util.LinkedList
            r7.<init>()
            org.spongycastle.pqc.crypto.xmss.XMSSNode r8 = r5.clone()
            r7.add(r8)
            java.util.Map<java.lang.Integer, java.util.LinkedList<org.spongycastle.pqc.crypto.xmss.XMSSNode>> r8 = r11.retain
            int r9 = r5.getHeight()
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            r8.put(r9, r7)
            goto L_0x01b7
        L_0x01a0:
            java.util.Map<java.lang.Integer, java.util.LinkedList<org.spongycastle.pqc.crypto.xmss.XMSSNode>> r7 = r11.retain
            int r8 = r5.getHeight()
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            java.lang.Object r7 = r7.get(r8)
            java.util.LinkedList r7 = (java.util.LinkedList) r7
            org.spongycastle.pqc.crypto.xmss.XMSSNode r8 = r5.clone()
            r7.add(r8)
        L_0x01b7:
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r7.<init>()
            int r8 = r1.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r7 = r7.g(r8)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r7
            long r8 = r1.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r7 = r7.h(r8)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r7
            int r8 = r1.e()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = r7.m(r8)
            int r8 = r1.f()
            int r8 = r8 - r4
            int r8 = r8 / 2
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = r7.n(r8)
            int r8 = r1.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r7 = r7.f(r8)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r7
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r7 = r7.k()
            r1 = r7
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r1
            org.spongycastle.pqc.crypto.xmss.WOTSPlus r7 = r11.c
            java.util.Stack<org.spongycastle.pqc.crypto.xmss.XMSSNode> r8 = r11.stack
            java.lang.Object r8 = r8.pop()
            org.spongycastle.pqc.crypto.xmss.XMSSNode r8 = (org.spongycastle.pqc.crypto.xmss.XMSSNode) r8
            org.spongycastle.pqc.crypto.xmss.XMSSNode r5 = org.spongycastle.pqc.crypto.xmss.XMSSNodeUtil.b(r7, r8, r5, r1)
            org.spongycastle.pqc.crypto.xmss.XMSSNode r7 = new org.spongycastle.pqc.crypto.xmss.XMSSNode
            int r8 = r5.getHeight()
            int r8 = r8 + r4
            byte[] r9 = r5.getValue()
            r7.<init>(r8, r9)
            r5 = r7
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r7.<init>()
            int r8 = r1.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r7 = r7.g(r8)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r7
            long r8 = r1.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r7 = r7.h(r8)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r7
            int r8 = r1.e()
            int r8 = r8 + r4
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = r7.m(r8)
            int r8 = r1.f()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = r7.n(r8)
            int r8 = r1.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r7 = r7.f(r8)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r7 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r7
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r7 = r7.k()
            r1 = r7
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r1
            goto L_0x0107
        L_0x024e:
            java.util.Stack<org.spongycastle.pqc.crypto.xmss.XMSSNode> r4 = r11.stack
            r4.push(r5)
            int r2 = r2 + 1
            goto L_0x0041
        L_0x0257:
            java.util.Stack<org.spongycastle.pqc.crypto.xmss.XMSSNode> r2 = r11.stack
            java.lang.Object r2 = r2.pop()
            org.spongycastle.pqc.crypto.xmss.XMSSNode r2 = (org.spongycastle.pqc.crypto.xmss.XMSSNode) r2
            r11.root = r2
            return
        L_0x0262:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "otsHashAddress == null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.xmss.BDS.b(byte[], byte[], org.spongycastle.pqc.crypto.xmss.OTSHashAddress):void");
    }

    private void c(byte[] publicSeed, byte[] secretSeed, OTSHashAddress otsHashAddress) {
        OTSHashAddress otsHashAddress2;
        if (otsHashAddress == null) {
            byte[] bArr = publicSeed;
            byte[] bArr2 = secretSeed;
            throw new NullPointerException("otsHashAddress == null");
        } else if (this.used) {
            byte[] bArr3 = publicSeed;
            byte[] bArr4 = secretSeed;
            throw new IllegalStateException("index already used");
        } else if (this.index <= (1 << this.treeHeight) - 2) {
            LTreeAddress lTreeAddress = (LTreeAddress) ((LTreeAddress.Builder) ((LTreeAddress.Builder) new LTreeAddress.Builder().g(otsHashAddress.b())).h(otsHashAddress.c())).l();
            HashTreeAddress hashTreeAddress = (HashTreeAddress) ((HashTreeAddress.Builder) ((HashTreeAddress.Builder) new HashTreeAddress.Builder().g(otsHashAddress.b())).h(otsHashAddress.c())).k();
            int tau = XMSSUtil.b(this.index, this.treeHeight);
            if (((this.index >> (tau + 1)) & 1) == 0 && tau < this.treeHeight - 1) {
                this.keep.put(Integer.valueOf(tau), this.authenticationPath.get(tau).clone());
            }
            if (tau == 0) {
                otsHashAddress2 = (OTSHashAddress) ((OTSHashAddress.Builder) ((OTSHashAddress.Builder) ((OTSHashAddress.Builder) new OTSHashAddress.Builder().g(otsHashAddress.b())).h(otsHashAddress.c())).p(this.index).n(otsHashAddress.e()).o(otsHashAddress.f()).f(otsHashAddress.a())).l();
                WOTSPlus wOTSPlus = this.c;
                wOTSPlus.j(wOTSPlus.i(secretSeed, otsHashAddress2), publicSeed);
                this.authenticationPath.set(0, XMSSNodeUtil.a(this.c, this.c.f(otsHashAddress2), (LTreeAddress) ((LTreeAddress.Builder) ((LTreeAddress.Builder) ((LTreeAddress.Builder) new LTreeAddress.Builder().g(lTreeAddress.b())).h(lTreeAddress.c())).n(this.index).o(lTreeAddress.f()).p(lTreeAddress.g()).f(lTreeAddress.a())).l()));
            } else {
                byte[] bArr5 = publicSeed;
                byte[] bArr6 = secretSeed;
                XMSSNode node = XMSSNodeUtil.b(this.c, this.authenticationPath.get(tau - 1), this.keep.get(Integer.valueOf(tau - 1)), (HashTreeAddress) ((HashTreeAddress.Builder) ((HashTreeAddress.Builder) ((HashTreeAddress.Builder) new HashTreeAddress.Builder().g(hashTreeAddress.b())).h(hashTreeAddress.c())).m(tau - 1).n(this.index >> tau).f(hashTreeAddress.a())).k());
                this.authenticationPath.set(tau, new XMSSNode(node.getHeight() + 1, node.getValue()));
                this.keep.remove(Integer.valueOf(tau - 1));
                for (int height = 0; height < tau; height++) {
                    if (height < this.treeHeight - this.k) {
                        this.authenticationPath.set(height, this.treeHashInstances.get(height).getTailNode());
                    } else {
                        this.authenticationPath.set(height, this.retain.get(Integer.valueOf(height)).removeFirst());
                    }
                }
                int minHeight = Math.min(tau, this.treeHeight - this.k);
                for (int height2 = 0; height2 < minHeight; height2++) {
                    int startIndex = this.index + 1 + ((1 << height2) * 3);
                    if (startIndex < (1 << this.treeHeight)) {
                        this.treeHashInstances.get(height2).initialize(startIndex);
                    }
                }
                otsHashAddress2 = otsHashAddress;
            }
            for (int i = 0; i < ((this.treeHeight - this.k) >> 1); i++) {
                BDSTreeHash treeHash = a();
                if (treeHash != null) {
                    treeHash.update(this.stack, this.c, publicSeed, secretSeed, otsHashAddress2);
                }
            }
            this.index++;
        } else {
            byte[] bArr7 = publicSeed;
            byte[] bArr8 = secretSeed;
            throw new IllegalStateException("index out of bounds");
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isUsed() {
        return this.used;
    }

    private BDSTreeHash a() {
        BDSTreeHash ret = null;
        for (BDSTreeHash treeHash : this.treeHashInstances) {
            if (!treeHash.isFinished() && treeHash.isInitialized()) {
                if (ret == null) {
                    ret = treeHash;
                } else if (treeHash.getHeight() < ret.getHeight()) {
                    ret = treeHash;
                } else if (treeHash.getHeight() == ret.getHeight() && treeHash.getIndexLeaf() < ret.getIndexLeaf()) {
                    ret = treeHash;
                }
            }
        }
        return ret;
    }

    /* access modifiers changed from: protected */
    public void validate() {
        if (this.authenticationPath == null) {
            throw new IllegalStateException("authenticationPath == null");
        } else if (this.retain == null) {
            throw new IllegalStateException("retain == null");
        } else if (this.stack == null) {
            throw new IllegalStateException("stack == null");
        } else if (this.treeHashInstances == null) {
            throw new IllegalStateException("treeHashInstances == null");
        } else if (this.keep == null) {
            throw new IllegalStateException("keep == null");
        } else if (!XMSSUtil.l(this.treeHeight, (long) this.index)) {
            throw new IllegalStateException("index in BDS state out of bounds");
        }
    }

    /* access modifiers changed from: protected */
    public int getTreeHeight() {
        return this.treeHeight;
    }

    /* access modifiers changed from: protected */
    public XMSSNode getRoot() {
        return this.root.clone();
    }

    /* access modifiers changed from: protected */
    public List<XMSSNode> getAuthenticationPath() {
        List<XMSSNode> authenticationPath2 = new ArrayList<>();
        for (XMSSNode node : this.authenticationPath) {
            authenticationPath2.add(node.clone());
        }
        return authenticationPath2;
    }

    /* access modifiers changed from: protected */
    public void setXMSS(XMSSParameters xmss) {
        if (this.treeHeight == xmss.d()) {
            this.c = xmss.f();
            return;
        }
        throw new IllegalStateException("wrong height");
    }

    /* access modifiers changed from: protected */
    public int getIndex() {
        return this.index;
    }
}
