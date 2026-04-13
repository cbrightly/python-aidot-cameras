package org.spongycastle.asn1;

import com.alibaba.fastjson.asm.Opcodes;

public class DLTaggedObject extends ASN1TaggedObject {
    private static final byte[] x = new byte[0];

    public DLTaggedObject(boolean explicit, int tagNo, ASN1Encodable obj) {
        super(explicit, tagNo, obj);
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        if (this.d || this.f) {
            return true;
        }
        return this.q.toASN1Primitive().n().i();
    }

    /* access modifiers changed from: package-private */
    public int g() {
        if (this.d) {
            return StreamUtil.b(this.c) + 1;
        }
        int length = this.q.toASN1Primitive().n().g();
        if (this.f) {
            return StreamUtil.b(this.c) + StreamUtil.a(length) + length;
        }
        return StreamUtil.b(this.c) + (length - 1);
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        int flags;
        if (!this.d) {
            ASN1Primitive primitive = this.q.toASN1Primitive().n();
            if (this.f) {
                out.k(Opcodes.IF_ICMPNE, this.c);
                out.i(primitive.g());
                out.j(primitive);
                return;
            }
            if (primitive.i()) {
                flags = Opcodes.IF_ICMPNE;
            } else {
                flags = 128;
            }
            out.k(flags, this.c);
            out.h(primitive);
            return;
        }
        out.f(Opcodes.IF_ICMPNE, this.c, x);
    }
}
