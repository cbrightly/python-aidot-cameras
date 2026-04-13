package org.spongycastle.crypto.agreement.kdf;

import java.io.IOException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.DigestDerivationFunction;
import org.spongycastle.crypto.params.KDFParameters;
import org.spongycastle.util.Pack;

public class ECDHKEKGenerator implements DigestDerivationFunction {
    private DigestDerivationFunction a;
    private ASN1ObjectIdentifier b;
    private int c;
    private byte[] d;

    public void b(DerivationParameters param) {
        DHKDFParameters params = (DHKDFParameters) param;
        this.b = params.a();
        this.c = params.c();
        this.d = params.d();
    }

    public int a(byte[] out, int outOff, int len) {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new AlgorithmIdentifier(this.b, DERNull.c));
        v.a(new DERTaggedObject(true, 2, new DEROctetString(Pack.f(this.c))));
        try {
            this.a.b(new KDFParameters(this.d, new DERSequence(v).getEncoded("DER")));
            return this.a.a(out, outOff, len);
        } catch (IOException e) {
            throw new IllegalArgumentException("unable to initialise kdf: " + e.getMessage());
        }
    }
}
