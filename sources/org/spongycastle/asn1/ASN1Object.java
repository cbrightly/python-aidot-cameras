package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import org.spongycastle.util.Encodable;

public abstract class ASN1Object implements ASN1Encodable, Encodable {
    public abstract ASN1Primitive toASN1Primitive();

    public byte[] getEncoded() {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        new ASN1OutputStream(bOut).j(this);
        return bOut.toByteArray();
    }

    public byte[] getEncoded(String encoding) {
        if (encoding.equals("DER")) {
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            new DEROutputStream(bOut).j(this);
            return bOut.toByteArray();
        } else if (!encoding.equals("DL")) {
            return getEncoded();
        } else {
            ByteArrayOutputStream bOut2 = new ByteArrayOutputStream();
            new DLOutputStream(bOut2).j(this);
            return bOut2.toByteArray();
        }
    }

    public int hashCode() {
        return toASN1Primitive().hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ASN1Encodable)) {
            return false;
        }
        return toASN1Primitive().equals(((ASN1Encodable) o).toASN1Primitive());
    }

    public ASN1Primitive toASN1Object() {
        return toASN1Primitive();
    }

    protected static boolean hasEncodedTagValue(Object obj, int tagValue) {
        return (obj instanceof byte[]) && ((byte[]) obj)[0] == tagValue;
    }
}
