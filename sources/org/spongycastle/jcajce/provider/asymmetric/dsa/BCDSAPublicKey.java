package org.spongycastle.jcajce.provider.asymmetric.dsa;

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
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.util.Strings;

public class BCDSAPublicKey implements DSAPublicKey {
    private static BigInteger c = BigInteger.valueOf(0);
    private static final long serialVersionUID = 1752452449903495175L;
    private transient DSAPublicKeyParameters d;
    private transient DSAParams f;
    private BigInteger y;

    BCDSAPublicKey(DSAPublicKeySpec spec) {
        this.y = spec.getY();
        this.f = new DSAParameterSpec(spec.getP(), spec.getQ(), spec.getG());
        this.d = new DSAPublicKeyParameters(this.y, DSAUtil.e(this.f));
    }

    BCDSAPublicKey(DSAPublicKey key) {
        this.y = key.getY();
        this.f = key.getParams();
        this.d = new DSAPublicKeyParameters(this.y, DSAUtil.e(this.f));
    }

    BCDSAPublicKey(DSAPublicKeyParameters params) {
        this.y = params.c();
        this.f = new DSAParameterSpec(params.b().b(), params.b().c(), params.b().a());
        this.d = params;
    }

    public BCDSAPublicKey(SubjectPublicKeyInfo info) {
        try {
            this.y = ((ASN1Integer) info.i()).r();
            if (a(info.e().h())) {
                DSAParameter params = DSAParameter.f(info.e().h());
                this.f = new DSAParameterSpec(params.g(), params.h(), params.e());
            } else {
                this.f = null;
            }
            this.d = new DSAPublicKeyParameters(this.y, DSAUtil.e(this.f));
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid info structure in DSA public key");
        }
    }

    private boolean a(ASN1Encodable parameters) {
        return parameters != null && !DERNull.c.equals(parameters.toASN1Primitive());
    }

    public String getAlgorithm() {
        return "DSA";
    }

    public String getFormat() {
        return "X.509";
    }

    /* access modifiers changed from: package-private */
    public DSAPublicKeyParameters engineGetKeyParameters() {
        return this.d;
    }

    public byte[] getEncoded() {
        DSAParams dSAParams = this.f;
        if (dSAParams == null) {
            return KeyUtil.c(new AlgorithmIdentifier(X9ObjectIdentifiers.d4), new ASN1Integer(this.y));
        }
        return KeyUtil.c(new AlgorithmIdentifier(X9ObjectIdentifiers.d4, new DSAParameter(dSAParams.getP(), this.f.getQ(), this.f.getG()).toASN1Primitive()), new ASN1Integer(this.y));
    }

    public DSAParams getParams() {
        return this.f;
    }

    public BigInteger getY() {
        return this.y;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        buf.append("DSA Public Key [");
        buf.append(DSAUtil.a(this.y, getParams()));
        buf.append("]");
        buf.append(nl);
        buf.append("            y: ");
        buf.append(getY().toString(16));
        buf.append(nl);
        return buf.toString();
    }

    public int hashCode() {
        if (this.f != null) {
            return ((getY().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getQ().hashCode();
        }
        return getY().hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof DSAPublicKey)) {
            return false;
        }
        DSAPublicKey other = (DSAPublicKey) o;
        if (this.f != null) {
            if (!getY().equals(other.getY()) || other.getParams() == null || !getParams().getG().equals(other.getParams().getG()) || !getParams().getP().equals(other.getParams().getP()) || !getParams().getQ().equals(other.getParams().getQ())) {
                return false;
            }
            return true;
        } else if (!getY().equals(other.getY()) || other.getParams() != null) {
            return false;
        } else {
            return true;
        }
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        BigInteger p = (BigInteger) in.readObject();
        if (p.equals(c)) {
            this.f = null;
        } else {
            this.f = new DSAParameterSpec(p, (BigInteger) in.readObject(), (BigInteger) in.readObject());
        }
        this.d = new DSAPublicKeyParameters(this.y, DSAUtil.e(this.f));
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        DSAParams dSAParams = this.f;
        if (dSAParams == null) {
            out.writeObject(c);
            return;
        }
        out.writeObject(dSAParams.getP());
        out.writeObject(this.f.getQ());
        out.writeObject(this.f.getG());
    }
}
