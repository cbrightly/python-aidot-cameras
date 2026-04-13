package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.io.IOException;
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
import org.spongycastle.asn1.oiw.ElGamalParameter;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.ElGamalPrivateKey;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.spec.ElGamalParameterSpec;
import org.spongycastle.jce.spec.ElGamalPrivateKeySpec;

public class BCElGamalPrivateKey implements ElGamalPrivateKey, DHPrivateKey, PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 4819350091141529678L;
    private transient ElGamalParameterSpec c;
    private transient PKCS12BagAttributeCarrierImpl d = new PKCS12BagAttributeCarrierImpl();
    private BigInteger x;

    protected BCElGamalPrivateKey() {
    }

    BCElGamalPrivateKey(ElGamalPrivateKey key) {
        this.x = key.getX();
        this.c = key.getParameters();
    }

    BCElGamalPrivateKey(DHPrivateKey key) {
        this.x = key.getX();
        this.c = new ElGamalParameterSpec(key.getParams().getP(), key.getParams().getG());
    }

    BCElGamalPrivateKey(ElGamalPrivateKeySpec spec) {
        this.x = spec.b();
        this.c = new ElGamalParameterSpec(spec.a().b(), spec.a().a());
    }

    BCElGamalPrivateKey(DHPrivateKeySpec spec) {
        this.x = spec.getX();
        this.c = new ElGamalParameterSpec(spec.getP(), spec.getG());
    }

    BCElGamalPrivateKey(PrivateKeyInfo info) {
        ElGamalParameter params = ElGamalParameter.f(info.g().h());
        this.x = ASN1Integer.o(info.h()).r();
        this.c = new ElGamalParameterSpec(params.g(), params.e());
    }

    BCElGamalPrivateKey(ElGamalPrivateKeyParameters params) {
        this.x = params.c();
        this.c = new ElGamalParameterSpec(params.b().c(), params.b().a());
    }

    public String getAlgorithm() {
        return "ElGamal";
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        try {
            return new PrivateKeyInfo(new AlgorithmIdentifier(OIWObjectIdentifiers.l, new ElGamalParameter(this.c.b(), this.c.a())), new ASN1Integer(getX())).getEncoded("DER");
        } catch (IOException e) {
            return null;
        }
    }

    public ElGamalParameterSpec getParameters() {
        return this.c;
    }

    public DHParameterSpec getParams() {
        return new DHParameterSpec(this.c.b(), this.c.a());
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

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        this.c = new ElGamalParameterSpec((BigInteger) in.readObject(), (BigInteger) in.readObject());
        this.d = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(this.c.b());
        out.writeObject(this.c.a());
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
}
