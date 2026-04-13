package org.spongycastle.asn1;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.Enumeration;

public class BERTaggedObject extends ASN1TaggedObject {
    public BERTaggedObject(int tagNo, ASN1Encodable obj) {
        super(true, tagNo, obj);
    }

    public BERTaggedObject(boolean explicit, int tagNo, ASN1Encodable obj) {
        super(explicit, tagNo, obj);
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        if (this.d || this.f) {
            return true;
        }
        return this.q.toASN1Primitive().k().i();
    }

    /* access modifiers changed from: package-private */
    public int g() {
        if (this.d) {
            return StreamUtil.b(this.c) + 1;
        }
        int length = this.q.toASN1Primitive().g();
        if (this.f) {
            return StreamUtil.b(this.c) + StreamUtil.a(length) + length;
        }
        return StreamUtil.b(this.c) + (length - 1);
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        Enumeration e;
        out.k(Opcodes.IF_ICMPNE, this.c);
        out.c(128);
        if (!this.d) {
            if (!this.f) {
                ASN1Encodable aSN1Encodable = this.q;
                if (aSN1Encodable instanceof ASN1OctetString) {
                    if (aSN1Encodable instanceof BEROctetString) {
                        e = ((BEROctetString) aSN1Encodable).u();
                    } else {
                        e = new BEROctetString(((ASN1OctetString) aSN1Encodable).q()).u();
                    }
                } else if (aSN1Encodable instanceof ASN1Sequence) {
                    e = ((ASN1Sequence) aSN1Encodable).s();
                } else if (aSN1Encodable instanceof ASN1Set) {
                    e = ((ASN1Set) aSN1Encodable).t();
                } else {
                    throw new ASN1Exception("not implemented: " + this.q.getClass().getName());
                }
                while (e.hasMoreElements()) {
                    out.j((ASN1Encodable) e.nextElement());
                }
            } else {
                out.j(this.q);
            }
        }
        out.c(0);
        out.c(0);
    }
}
