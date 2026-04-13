package org.spongycastle.asn1.ua;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;

public class DSTU4145Params extends ASN1Object {
    private static final byte[] DEFAULT_DKE = {-87, -42, -21, 69, -15, 60, 112, OTACommand.RESP_ID_SEND_OTA, OTACommand.RESP_ID_VERSION, -60, -106, 123, 35, 31, 94, -83, -10, 88, -21, -92, -64, 55, 41, 29, 56, -39, 107, -16, 37, -54, 78, 23, -8, -23, 114, 13, -58, 21, -76, 58, 40, -105, 95, 11, -63, -34, -93, 100, 56, -75, 100, -22, Constants.COMMA, 23, -97, -48, 18, 62, 109, -72, -6, -59, 121, 4};
    private byte[] dke = DEFAULT_DKE;
    private DSTU4145ECBinary ecbinary;
    private ASN1ObjectIdentifier namedCurve;

    public DSTU4145Params(ASN1ObjectIdentifier namedCurve2) {
        this.namedCurve = namedCurve2;
    }

    public DSTU4145Params(ASN1ObjectIdentifier namedCurve2, byte[] dke2) {
        this.namedCurve = namedCurve2;
        this.dke = Arrays.h(dke2);
    }

    public DSTU4145Params(DSTU4145ECBinary ecbinary2) {
        this.ecbinary = ecbinary2;
    }

    public boolean isNamedCurve() {
        return this.namedCurve != null;
    }

    public DSTU4145ECBinary getECBinary() {
        return this.ecbinary;
    }

    public byte[] getDKE() {
        return this.dke;
    }

    public static byte[] getDefaultDKE() {
        return DEFAULT_DKE;
    }

    public ASN1ObjectIdentifier getNamedCurve() {
        return this.namedCurve;
    }

    public static DSTU4145Params getInstance(Object obj) {
        DSTU4145Params params;
        if (obj instanceof DSTU4145Params) {
            return (DSTU4145Params) obj;
        }
        if (obj != null) {
            ASN1Sequence seq = ASN1Sequence.o(obj);
            if (seq.r(0) instanceof ASN1ObjectIdentifier) {
                params = new DSTU4145Params(ASN1ObjectIdentifier.t(seq.r(0)));
            } else {
                params = new DSTU4145Params(DSTU4145ECBinary.i(seq.r(0)));
            }
            if (seq.size() == 2) {
                byte[] q = ASN1OctetString.o(seq.r(1)).q();
                params.dke = q;
                if (q.length != DEFAULT_DKE.length) {
                    throw new IllegalArgumentException("object parse error");
                }
            }
            return params;
        }
        throw new IllegalArgumentException("object parse error");
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.namedCurve;
        if (aSN1ObjectIdentifier != null) {
            v.a(aSN1ObjectIdentifier);
        } else {
            v.a(this.ecbinary);
        }
        if (!Arrays.b(this.dke, DEFAULT_DKE)) {
            v.a(new DEROctetString(this.dke));
        }
        return new DERSequence(v);
    }
}
