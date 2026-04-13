package org.spongycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPublicKeySpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DSAParameter;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.util.Strings;

public class JDKDSAPublicKey implements DSAPublicKey {
    private static final long serialVersionUID = 1752452449903495175L;
    private DSAParams dsaSpec;
    private BigInteger y;

    JDKDSAPublicKey(DSAPublicKeySpec spec) {
        this.y = spec.getY();
        this.dsaSpec = new DSAParameterSpec(spec.getP(), spec.getQ(), spec.getG());
    }

    JDKDSAPublicKey(DSAPublicKey key) {
        this.y = key.getY();
        this.dsaSpec = key.getParams();
    }

    JDKDSAPublicKey(DSAPublicKeyParameters params) {
        this.y = params.c();
        this.dsaSpec = new DSAParameterSpec(params.b().b(), params.b().c(), params.b().a());
    }

    JDKDSAPublicKey(BigInteger y2, DSAParameterSpec dsaSpec2) {
        this.y = y2;
        this.dsaSpec = dsaSpec2;
    }

    JDKDSAPublicKey(SubjectPublicKeyInfo info) {
        try {
            this.y = ((ASN1Integer) info.i()).r();
            if (a(info.e().h())) {
                DSAParameter params = DSAParameter.f(info.e().h());
                this.dsaSpec = new DSAParameterSpec(params.g(), params.h(), params.e());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid info structure in DSA public key");
        }
    }

    private boolean a(ASN1Encodable parameters) {
        return parameters != null && !DERNull.c.equals(parameters);
    }

    public String getAlgorithm() {
        return "DSA";
    }

    public String getFormat() {
        return "X.509";
    }

    public byte[] getEncoded() {
        try {
            DSAParams dSAParams = this.dsaSpec;
            if (dSAParams == null) {
                return new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.d4), (ASN1Encodable) new ASN1Integer(this.y)).getEncoded("DER");
            }
            return new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.d4, new DSAParameter(dSAParams.getP(), this.dsaSpec.getQ(), this.dsaSpec.getG())), (ASN1Encodable) new ASN1Integer(this.y)).getEncoded("DER");
        } catch (IOException e) {
            return null;
        }
    }

    public DSAParams getParams() {
        return this.dsaSpec;
    }

    public BigInteger getY() {
        return this.y;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        buf.append("DSA Public Key");
        buf.append(nl);
        buf.append("            y: ");
        buf.append(getY().toString(16));
        buf.append(nl);
        return buf.toString();
    }

    public int hashCode() {
        return ((getY().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getQ().hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof DSAPublicKey)) {
            return false;
        }
        DSAPublicKey other = (DSAPublicKey) o;
        if (!getY().equals(other.getY()) || !getParams().getG().equals(other.getParams().getG()) || !getParams().getP().equals(other.getParams().getP()) || !getParams().getQ().equals(other.getParams().getQ())) {
            return false;
        }
        return true;
    }

    private void readObject(ObjectInputStream in) {
        this.y = (BigInteger) in.readObject();
        this.dsaSpec = new DSAParameterSpec((BigInteger) in.readObject(), (BigInteger) in.readObject(), (BigInteger) in.readObject());
    }

    private void writeObject(ObjectOutputStream out) {
        out.writeObject(this.y);
        out.writeObject(this.dsaSpec.getP());
        out.writeObject(this.dsaSpec.getQ());
        out.writeObject(this.dsaSpec.getG());
    }
}
