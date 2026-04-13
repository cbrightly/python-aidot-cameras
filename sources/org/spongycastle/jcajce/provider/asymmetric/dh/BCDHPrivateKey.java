package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.pkcs.DHParameter;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.DomainParameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;

public class BCDHPrivateKey implements DHPrivateKey, PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 311058815616901812L;
    private transient DHParameterSpec c;
    private transient PrivateKeyInfo d;
    private transient PKCS12BagAttributeCarrierImpl f = new PKCS12BagAttributeCarrierImpl();
    private BigInteger x;

    protected BCDHPrivateKey() {
    }

    BCDHPrivateKey(DHPrivateKey key) {
        this.x = key.getX();
        this.c = key.getParams();
    }

    BCDHPrivateKey(DHPrivateKeySpec spec) {
        this.x = spec.getX();
        this.c = new DHParameterSpec(spec.getP(), spec.getG());
    }

    public BCDHPrivateKey(PrivateKeyInfo info) {
        ASN1Sequence seq = ASN1Sequence.o(info.g().h());
        ASN1ObjectIdentifier id = info.g().e();
        this.d = info;
        this.x = ((ASN1Integer) info.h()).r();
        if (id.equals(PKCSObjectIdentifiers.b0)) {
            DHParameter params = DHParameter.f(seq);
            if (params.g() != null) {
                this.c = new DHParameterSpec(params.h(), params.e(), params.g().intValue());
            } else {
                this.c = new DHParameterSpec(params.h(), params.e());
            }
        } else if (id.equals(X9ObjectIdentifiers.k4)) {
            DomainParameters params2 = DomainParameters.f(seq);
            this.c = new DHParameterSpec(params2.i(), params2.e());
        } else {
            throw new IllegalArgumentException("unknown algorithm type: " + id);
        }
    }

    BCDHPrivateKey(DHPrivateKeyParameters params) {
        this.x = params.c();
        this.c = new DHParameterSpec(params.b().e(), params.b().b(), params.b().c());
    }

    public String getAlgorithm() {
        return "DH";
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        try {
            PrivateKeyInfo privateKeyInfo = this.d;
            if (privateKeyInfo != null) {
                return privateKeyInfo.getEncoded("DER");
            }
            return new PrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.b0, new DHParameter(this.c.getP(), this.c.getG(), this.c.getL()).toASN1Primitive()), new ASN1Integer(getX())).getEncoded("DER");
        } catch (Exception e) {
            return null;
        }
    }

    public DHParameterSpec getParams() {
        return this.c;
    }

    public BigInteger getX() {
        return this.x;
    }

    public boolean equals(Object o) {
        if (!(o instanceof DHPrivateKey)) {
            return false;
        }
        DHPrivateKey other = (DHPrivateKey) o;
        if (!getX().equals(other.getX()) || !getParams().getG().equals(other.getParams().getG()) || !getParams().getP().equals(other.getParams().getP()) || getParams().getL() != other.getParams().getL()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((getX().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getL();
    }

    public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute) {
        this.f.setBagAttribute(oid, attribute);
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid) {
        return this.f.getBagAttribute(oid);
    }

    public Enumeration getBagAttributeKeys() {
        return this.f.getBagAttributeKeys();
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        this.c = new DHParameterSpec((BigInteger) in.readObject(), (BigInteger) in.readObject(), in.readInt());
        this.d = null;
        this.f = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(this.c.getP());
        out.writeObject(this.c.getG());
        out.writeInt(this.c.getL());
    }
}
