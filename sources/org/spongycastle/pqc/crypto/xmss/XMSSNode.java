package org.spongycastle.pqc.crypto.xmss;

import java.io.Serializable;

public final class XMSSNode implements Serializable {
    private static final long serialVersionUID = 1;
    private final int height;
    private final byte[] value;

    protected XMSSNode(int height2, byte[] value2) {
        this.height = height2;
        this.value = value2;
    }

    public int getHeight() {
        return this.height;
    }

    public byte[] getValue() {
        return XMSSUtil.c(this.value);
    }

    /* access modifiers changed from: protected */
    public XMSSNode clone() {
        return new XMSSNode(getHeight(), getValue());
    }
}
