package org.spongycastle.asn1;

import com.meituan.robust.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.spongycastle.util.encoders.Hex;

public class DERApplicationSpecific extends ASN1ApplicationSpecific {
    DERApplicationSpecific(boolean isConstructed, int tag, byte[] octets) {
        super(isConstructed, tag, octets);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DERApplicationSpecific(boolean constructed, int tag, ASN1Encodable object) {
        super(constructed || object.toASN1Primitive().i(), tag, v(constructed, object));
    }

    private static byte[] v(boolean explicit, ASN1Encodable object) {
        byte[] data = object.toASN1Primitive().getEncoded("DER");
        if (explicit) {
            return data;
        }
        int lenBytes = ASN1ApplicationSpecific.r(data);
        byte[] tmp = new byte[(data.length - lenBytes)];
        System.arraycopy(data, lenBytes, tmp, 0, tmp.length);
        return tmp;
    }

    public DERApplicationSpecific(int tagNo, ASN1EncodableVector vec) {
        super(true, tagNo, u(vec));
    }

    private static byte[] u(ASN1EncodableVector vec) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        int i = 0;
        while (i != vec.c()) {
            try {
                bOut.write(((ASN1Object) vec.b(i)).getEncoded("DER"));
                i++;
            } catch (IOException e) {
                throw new ASN1ParsingException("malformed object: " + e, e);
            }
        }
        return bOut.toByteArray();
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        int classBits = 64;
        if (this.c) {
            classBits = 64 | 32;
        }
        out.f(classBits, this.d, this.f);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(Constants.ARRAY_TYPE);
        if (i()) {
            sb.append("CONSTRUCTED ");
        }
        sb.append("APPLICATION ");
        sb.append(Integer.toString(o()));
        sb.append("]");
        if (this.f != null) {
            sb.append(" #");
            sb.append(Hex.d(this.f));
        } else {
            sb.append(" #null");
        }
        sb.append(" ");
        return sb.toString();
    }
}
