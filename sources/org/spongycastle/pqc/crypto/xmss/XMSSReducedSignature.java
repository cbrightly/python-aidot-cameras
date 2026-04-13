package org.spongycastle.pqc.crypto.xmss;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class XMSSReducedSignature implements XMSSStoreableObjectInterface {
    private final XMSSParameters c;
    private final WOTSPlusSignature d;
    private final List<XMSSNode> f;

    protected XMSSReducedSignature(Builder builder) {
        XMSSParameters a = builder.a;
        this.c = a;
        if (a != null) {
            int n = a.c();
            int len = a.f().e().c();
            int height = a.d();
            byte[] reducedSignature = builder.d;
            if (reducedSignature != null) {
                if (reducedSignature.length == (len * n) + (height * n)) {
                    int position = 0;
                    byte[][] wotsPlusSignature = new byte[len][];
                    for (int i = 0; i < wotsPlusSignature.length; i++) {
                        wotsPlusSignature[i] = XMSSUtil.g(reducedSignature, position, n);
                        position += n;
                    }
                    this.d = new WOTSPlusSignature(this.c.f().e(), wotsPlusSignature);
                    List<XMSSNode> nodeList = new ArrayList<>();
                    for (int i2 = 0; i2 < height; i2++) {
                        nodeList.add(new XMSSNode(i2, XMSSUtil.g(reducedSignature, position, n)));
                        position += n;
                    }
                    this.f = nodeList;
                    return;
                }
                throw new IllegalArgumentException("signature has wrong size");
            }
            WOTSPlusSignature tmpSignature = builder.b;
            if (tmpSignature != null) {
                this.d = tmpSignature;
            } else {
                WOTSPlusParameters e = a.f().e();
                int[] iArr = new int[2];
                iArr[1] = n;
                iArr[0] = len;
                this.d = new WOTSPlusSignature(e, (byte[][]) Array.newInstance(byte.class, iArr));
            }
            List<XMSSNode> tmpAuthPath = builder.c;
            if (tmpAuthPath == null) {
                this.f = new ArrayList();
            } else if (tmpAuthPath.size() == height) {
                this.f = tmpAuthPath;
            } else {
                throw new IllegalArgumentException("size of authPath needs to be equal to height of tree");
            }
        } else {
            throw new NullPointerException("params == null");
        }
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public final XMSSParameters a;
        /* access modifiers changed from: private */
        public WOTSPlusSignature b = null;
        /* access modifiers changed from: private */
        public List<XMSSNode> c = null;
        /* access modifiers changed from: private */
        public byte[] d = null;

        public Builder(XMSSParameters params) {
            this.a = params;
        }

        public Builder h(WOTSPlusSignature val) {
            this.b = val;
            return this;
        }

        public Builder f(List<XMSSNode> val) {
            this.c = val;
            return this;
        }

        public Builder g(byte[] val) {
            this.d = XMSSUtil.c(val);
            return this;
        }

        public XMSSReducedSignature e() {
            return new XMSSReducedSignature(this);
        }
    }

    public byte[] d() {
        int n = this.c.c();
        byte[] out = new byte[((this.c.f().e().c() * n) + (this.c.d() * n))];
        int position = 0;
        byte[][] signature = this.d.a();
        for (byte[] e : signature) {
            XMSSUtil.e(out, e, position);
            position += n;
        }
        for (int i = 0; i < this.f.size(); i++) {
            XMSSUtil.e(out, this.f.get(i).getValue(), position);
            position += n;
        }
        return out;
    }

    public XMSSParameters b() {
        return this.c;
    }

    public WOTSPlusSignature c() {
        return this.d;
    }

    public List<XMSSNode> a() {
        return this.f;
    }
}
