package org.spongycastle.jce.provider;

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
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.pkcs.DHParameter;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.DHDomainParameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;

public class JCEDHPrivateKey implements DHPrivateKey, PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 311058815616901812L;
    private PKCS12BagAttributeCarrier attrCarrier = new PKCS12BagAttributeCarrierImpl();
    private DHParameterSpec dhSpec;
    private PrivateKeyInfo info;
    BigInteger x;

    protected JCEDHPrivateKey() {
    }

    JCEDHPrivateKey(DHPrivateKey key) {
        this.x = key.getX();
        this.dhSpec = key.getParams();
    }

    JCEDHPrivateKey(DHPrivateKeySpec spec) {
        this.x = spec.getX();
        this.dhSpec = new DHParameterSpec(spec.getP(), spec.getG());
    }

    JCEDHPrivateKey(PrivateKeyInfo info2) {
        ASN1Sequence seq = ASN1Sequence.o(info2.e().h());
        ASN1Integer derX = ASN1Integer.o(info2.h());
        ASN1ObjectIdentifier id = info2.e().e();
        this.info = info2;
        this.x = derX.r();
        if (id.equals(PKCSObjectIdentifiers.b0)) {
            DHParameter params = DHParameter.f(seq);
            if (params.g() != null) {
                this.dhSpec = new DHParameterSpec(params.h(), params.e(), params.g().intValue());
            } else {
                this.dhSpec = new DHParameterSpec(params.h(), params.e());
            }
        } else if (id.equals(X9ObjectIdentifiers.k4)) {
            DHDomainParameters params2 = DHDomainParameters.f(seq);
            this.dhSpec = new DHParameterSpec(params2.h().r(), params2.e().r());
        } else {
            throw new IllegalArgumentException("unknown algorithm type: " + id);
        }
    }

    JCEDHPrivateKey(DHPrivateKeyParameters params) {
        this.x = params.c();
        this.dhSpec = new DHParameterSpec(params.b().e(), params.b().b(), params.b().c());
    }

    public String getAlgorithm() {
        return "DH";
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        try {
            PrivateKeyInfo privateKeyInfo = this.info;
            if (privateKeyInfo != null) {
                return privateKeyInfo.getEncoded("DER");
            }
            return new PrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.b0, new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL())), new ASN1Integer(getX())).getEncoded("DER");
        } catch (IOException e) {
            return null;
        }
    }

    public DHParameterSpec getParams() {
        return this.dhSpec;
    }

    public BigInteger getX() {
        return this.x;
    }

    private void readObject(ObjectInputStream in) {
        this.x = (BigInteger) in.readObject();
        this.dhSpec = new DHParameterSpec((BigInteger) in.readObject(), (BigInteger) in.readObject(), in.readInt());
    }

    private void writeObject(ObjectOutputStream out) {
        out.writeObject(getX());
        out.writeObject(this.dhSpec.getP());
        out.writeObject(this.dhSpec.getG());
        out.writeInt(this.dhSpec.getL());
    }

    public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute) {
        this.attrCarrier.setBagAttribute(oid, attribute);
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid) {
        return this.attrCarrier.getBagAttribute(oid);
    }

    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }
}
