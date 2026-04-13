package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DLSequence;

public class AuthenticatedSafe extends ASN1Object {
    private ContentInfo[] c;
    private boolean d = true;

    private AuthenticatedSafe(ASN1Sequence seq) {
        this.c = new ContentInfo[seq.size()];
        int i = 0;
        while (true) {
            ContentInfo[] contentInfoArr = this.c;
            if (i != contentInfoArr.length) {
                contentInfoArr[i] = ContentInfo.g(seq.r(i));
                i++;
            } else {
                this.d = seq instanceof BERSequence;
                return;
            }
        }
    }

    public static AuthenticatedSafe f(Object o) {
        if (o instanceof AuthenticatedSafe) {
            return (AuthenticatedSafe) o;
        }
        if (o != null) {
            return new AuthenticatedSafe(ASN1Sequence.o(o));
        }
        return null;
    }

    public AuthenticatedSafe(ContentInfo[] info) {
        this.c = info;
    }

    public ContentInfo[] e() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        int i = 0;
        while (true) {
            ContentInfo[] contentInfoArr = this.c;
            if (i == contentInfoArr.length) {
                break;
            }
            v.a(contentInfoArr[i]);
            i++;
        }
        if (this.d != 0) {
            return new BERSequence(v);
        }
        return new DLSequence(v);
    }
}
