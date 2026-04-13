package org.spongycastle.pqc.crypto.xmss;

import java.io.Serializable;

public class BDSTreeHash implements Serializable {
    private static final long serialVersionUID = 1;
    private boolean finished = false;
    private int height;
    private final int initialHeight;
    private boolean initialized = false;
    private int nextIndex;
    private XMSSNode tailNode;

    BDSTreeHash(int initialHeight2) {
        this.initialHeight = initialHeight2;
    }

    /* access modifiers changed from: package-private */
    public void initialize(int nextIndex2) {
        this.tailNode = null;
        this.height = this.initialHeight;
        this.nextIndex = nextIndex2;
        this.initialized = true;
        this.finished = false;
    }

    /* JADX WARNING: type inference failed for: r4v58, types: [org.spongycastle.pqc.crypto.xmss.XMSSAddress] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(java.util.Stack<org.spongycastle.pqc.crypto.xmss.XMSSNode> r9, org.spongycastle.pqc.crypto.xmss.WOTSPlus r10, byte[] r11, byte[] r12, org.spongycastle.pqc.crypto.xmss.OTSHashAddress r13) {
        /*
            r8 = this;
            if (r13 == 0) goto L_0x0223
            boolean r0 = r8.finished
            if (r0 != 0) goto L_0x021b
            boolean r0 = r8.initialized
            if (r0 == 0) goto L_0x021b
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r0 = new org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder
            r0.<init>()
            int r1 = r13.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r0 = r0.g(r1)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r0 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r0
            long r1 = r13.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r0 = r0.h(r1)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r0 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r0
            int r1 = r8.nextIndex
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r0 = r0.p(r1)
            int r1 = r13.e()
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r0 = r0.n(r1)
            int r1 = r13.f()
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r0 = r0.o(r1)
            int r1 = r13.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r0 = r0.f(r1)
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress$Builder r0 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress.Builder) r0
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r0 = r0.l()
            r13 = r0
            org.spongycastle.pqc.crypto.xmss.OTSHashAddress r13 = (org.spongycastle.pqc.crypto.xmss.OTSHashAddress) r13
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r0 = new org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder
            r0.<init>()
            int r1 = r13.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r0 = r0.g(r1)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r0 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r0
            long r1 = r13.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r0 = r0.h(r1)
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r0 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress.Builder) r0
            int r1 = r8.nextIndex
            org.spongycastle.pqc.crypto.xmss.LTreeAddress$Builder r0 = r0.n(r1)
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r0 = r0.l()
            org.spongycastle.pqc.crypto.xmss.LTreeAddress r0 = (org.spongycastle.pqc.crypto.xmss.LTreeAddress) r0
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r1 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r1.<init>()
            int r2 = r13.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r1 = r1.g(r2)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r1
            long r2 = r13.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r1 = r1.h(r2)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r1
            int r2 = r8.nextIndex
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r1 = r1.n(r2)
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r1 = r1.k()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r1
            byte[] r2 = r10.i(r12, r13)
            r10.j(r2, r11)
            org.spongycastle.pqc.crypto.xmss.WOTSPlusPublicKeyParameters r2 = r10.f(r13)
            org.spongycastle.pqc.crypto.xmss.XMSSNode r3 = org.spongycastle.pqc.crypto.xmss.XMSSNodeUtil.a(r10, r2, r0)
        L_0x00a3:
            boolean r4 = r9.isEmpty()
            r5 = 1
            if (r4 != 0) goto L_0x015b
            java.lang.Object r4 = r9.peek()
            org.spongycastle.pqc.crypto.xmss.XMSSNode r4 = (org.spongycastle.pqc.crypto.xmss.XMSSNode) r4
            int r4 = r4.getHeight()
            int r6 = r3.getHeight()
            if (r4 != r6) goto L_0x015b
            java.lang.Object r4 = r9.peek()
            org.spongycastle.pqc.crypto.xmss.XMSSNode r4 = (org.spongycastle.pqc.crypto.xmss.XMSSNode) r4
            int r4 = r4.getHeight()
            int r6 = r8.initialHeight
            if (r4 == r6) goto L_0x015b
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r4.<init>()
            int r6 = r1.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.g(r6)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            long r6 = r1.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.h(r6)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            int r6 = r1.e()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = r4.m(r6)
            int r6 = r1.f()
            int r6 = r6 - r5
            int r6 = r6 / 2
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = r4.n(r6)
            int r6 = r1.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.f(r6)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r4 = r4.k()
            r1 = r4
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r1
            java.lang.Object r4 = r9.pop()
            org.spongycastle.pqc.crypto.xmss.XMSSNode r4 = (org.spongycastle.pqc.crypto.xmss.XMSSNode) r4
            org.spongycastle.pqc.crypto.xmss.XMSSNode r3 = org.spongycastle.pqc.crypto.xmss.XMSSNodeUtil.b(r10, r4, r3, r1)
            org.spongycastle.pqc.crypto.xmss.XMSSNode r4 = new org.spongycastle.pqc.crypto.xmss.XMSSNode
            int r6 = r3.getHeight()
            int r6 = r6 + r5
            byte[] r7 = r3.getValue()
            r4.<init>(r6, r7)
            r3 = r4
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r4.<init>()
            int r6 = r1.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.g(r6)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            long r6 = r1.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.h(r6)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            int r6 = r1.e()
            int r6 = r6 + r5
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = r4.m(r6)
            int r5 = r1.f()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = r4.n(r5)
            int r5 = r1.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.f(r5)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r4 = r4.k()
            r1 = r4
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r1
            goto L_0x00a3
        L_0x015b:
            org.spongycastle.pqc.crypto.xmss.XMSSNode r4 = r8.tailNode
            if (r4 != 0) goto L_0x0163
            r8.tailNode = r3
            goto L_0x0202
        L_0x0163:
            int r4 = r4.getHeight()
            int r6 = r3.getHeight()
            if (r4 != r6) goto L_0x01ff
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r4.<init>()
            int r6 = r1.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.g(r6)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            long r6 = r1.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.h(r6)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            int r6 = r1.e()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = r4.m(r6)
            int r6 = r1.f()
            int r6 = r6 - r5
            int r6 = r6 / 2
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = r4.n(r6)
            int r6 = r1.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.f(r6)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r4 = r4.k()
            r1 = r4
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r1
            org.spongycastle.pqc.crypto.xmss.XMSSNode r4 = r8.tailNode
            org.spongycastle.pqc.crypto.xmss.XMSSNode r3 = org.spongycastle.pqc.crypto.xmss.XMSSNodeUtil.b(r10, r4, r3, r1)
            org.spongycastle.pqc.crypto.xmss.XMSSNode r4 = new org.spongycastle.pqc.crypto.xmss.XMSSNode
            org.spongycastle.pqc.crypto.xmss.XMSSNode r6 = r8.tailNode
            int r6 = r6.getHeight()
            int r6 = r6 + r5
            byte[] r7 = r3.getValue()
            r4.<init>(r6, r7)
            r3 = r4
            r8.tailNode = r3
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = new org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder
            r4.<init>()
            int r6 = r1.b()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.g(r6)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            long r6 = r1.c()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.h(r6)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            int r6 = r1.e()
            int r6 = r6 + r5
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = r4.m(r6)
            int r6 = r1.f()
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = r4.n(r6)
            int r6 = r1.a()
            org.spongycastle.pqc.crypto.xmss.XMSSAddress$Builder r4 = r4.f(r6)
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress$Builder r4 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress.Builder) r4
            org.spongycastle.pqc.crypto.xmss.XMSSAddress r4 = r4.k()
            r1 = r4
            org.spongycastle.pqc.crypto.xmss.HashTreeAddress r1 = (org.spongycastle.pqc.crypto.xmss.HashTreeAddress) r1
            goto L_0x0202
        L_0x01ff:
            r9.push(r3)
        L_0x0202:
            org.spongycastle.pqc.crypto.xmss.XMSSNode r4 = r8.tailNode
            int r4 = r4.getHeight()
            int r6 = r8.initialHeight
            if (r4 != r6) goto L_0x020f
            r8.finished = r5
            goto L_0x021a
        L_0x020f:
            int r4 = r3.getHeight()
            r8.height = r4
            int r4 = r8.nextIndex
            int r4 = r4 + r5
            r8.nextIndex = r4
        L_0x021a:
            return
        L_0x021b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "finished or not initialized"
            r0.<init>(r1)
            throw r0
        L_0x0223:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "otsHashAddress == null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.xmss.BDSTreeHash.update(java.util.Stack, org.spongycastle.pqc.crypto.xmss.WOTSPlus, byte[], byte[], org.spongycastle.pqc.crypto.xmss.OTSHashAddress):void");
    }

    /* access modifiers changed from: package-private */
    public int getHeight() {
        if (!this.initialized || this.finished) {
            return Integer.MAX_VALUE;
        }
        return this.height;
    }

    /* access modifiers changed from: package-private */
    public int getIndexLeaf() {
        return this.nextIndex;
    }

    /* access modifiers changed from: package-private */
    public void setNode(XMSSNode node) {
        this.tailNode = node;
        int height2 = node.getHeight();
        this.height = height2;
        if (height2 == this.initialHeight) {
            this.finished = true;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isFinished() {
        return this.finished;
    }

    /* access modifiers changed from: package-private */
    public boolean isInitialized() {
        return this.initialized;
    }

    public XMSSNode getTailNode() {
        return this.tailNode.clone();
    }
}
