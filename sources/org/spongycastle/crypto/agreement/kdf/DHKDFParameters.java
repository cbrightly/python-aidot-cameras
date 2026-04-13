package org.spongycastle.crypto.agreement.kdf;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.crypto.DerivationParameters;

public class DHKDFParameters implements DerivationParameters {
    private ASN1ObjectIdentifier a;
    private int b;
    private byte[] c;
    private byte[] d;

    public DHKDFParameters(ASN1ObjectIdentifier algorithm, int keySize, byte[] z, byte[] extraInfo) {
        this.a = algorithm;
        this.b = keySize;
        this.c = z;
        this.d = extraInfo;
    }

    public ASN1ObjectIdentifier a() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public byte[] d() {
        return this.c;
    }

    public byte[] b() {
        return this.d;
    }
}
