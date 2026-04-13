package org.spongycastle.jcajce.provider.asymmetric.dsa;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPrivateKeySpec;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DSAParameter;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.util.Strings;

public class BCDSAPrivateKey implements DSAPrivateKey, PKCS12BagAttributeCarrier {
    private static final long serialVersionUID = -4677259546958385734L;
    private transient DSAParams c;
    private transient PKCS12BagAttributeCarrierImpl d = new PKCS12BagAttributeCarrierImpl();
    private BigInteger x;

    protected BCDSAPrivateKey() {
    }

    BCDSAPrivateKey(DSAPrivateKey key) {
        this.x = key.getX();
        this.c = key.getParams();
    }

    BCDSAPrivateKey(DSAPrivateKeySpec spec) {
        this.x = spec.getX();
        this.c = new DSAParameterSpec(spec.getP(), spec.getQ(), spec.getG());
    }

    public BCDSAPrivateKey(PrivateKeyInfo info) {
        DSAParameter params = DSAParameter.f(info.g().h());
        this.x = ((ASN1Integer) info.h()).r();
        this.c = new DSAParameterSpec(params.g(), params.h(), params.e());
    }

    BCDSAPrivateKey(DSAPrivateKeyParameters params) {
        this.x = params.c();
        this.c = new DSAParameterSpec(params.b().b(), params.b().c(), params.b().a());
    }

    public String getAlgorithm() {
        return "DSA";
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        return KeyUtil.b(new AlgorithmIdentifier(X9ObjectIdentifiers.d4, new DSAParameter(this.c.getP(), this.c.getQ(), this.c.getG()).toASN1Primitive()), new ASN1Integer(getX()));
    }

    public DSAParams getParams() {
        return this.c;
    }

    public BigInteger getX() {
        return this.x;
    }

    public boolean equals(Object o) {
        if (!(o instanceof DSAPrivateKey)) {
            return false;
        }
        DSAPrivateKey other = (DSAPrivateKey) o;
        if (!getX().equals(other.getX()) || !getParams().getG().equals(other.getParams().getG()) || !getParams().getP().equals(other.getParams().getP()) || !getParams().getQ().equals(other.getParams().getQ())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((getX().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getQ().hashCode();
    }

    public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute) {
        this.d.setBagAttribute(oid, attribute);
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid) {
        return this.d.getBagAttribute(oid);
    }

    public Enumeration getBagAttributeKeys() {
        return this.d.getBagAttributeKeys();
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        this.c = new DSAParameterSpec((BigInteger) in.readObject(), (BigInteger) in.readObject(), (BigInteger) in.readObject());
        this.d = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(this.c.getP());
        out.writeObject(this.c.getQ());
        out.writeObject(this.c.getG());
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        BigInteger y = getParams().getG().modPow(this.x, getParams().getP());
        buf.append("DSA Private Key [");
        buf.append(DSAUtil.a(y, getParams()));
        buf.append("]");
        buf.append(nl);
        buf.append("            y: ");
        buf.append(y.toString(16));
        buf.append(nl);
        return buf.toString();
    }
}
