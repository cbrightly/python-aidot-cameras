package org.spongycastle.jce.provider;

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
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.ElGamalPrivateKey;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.spec.ElGamalParameterSpec;
import org.spongycastle.jce.spec.ElGamalPrivateKeySpec;

public class JCEElGamalPrivateKey implements ElGamalPrivateKey, DHPrivateKey, PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 4819350091141529678L;
    private PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
    ElGamalParameterSpec elSpec;
    BigInteger x;

    protected JCEElGamalPrivateKey() {
    }

    JCEElGamalPrivateKey(ElGamalPrivateKey key) {
        this.x = key.getX();
        this.elSpec = key.getParameters();
    }

    JCEElGamalPrivateKey(DHPrivateKey key) {
        this.x = key.getX();
        this.elSpec = new ElGamalParameterSpec(key.getParams().getP(), key.getParams().getG());
    }

    JCEElGamalPrivateKey(ElGamalPrivateKeySpec spec) {
        this.x = spec.b();
        this.elSpec = new ElGamalParameterSpec(spec.a().b(), spec.a().a());
    }

    JCEElGamalPrivateKey(DHPrivateKeySpec spec) {
        this.x = spec.getX();
        this.elSpec = new ElGamalParameterSpec(spec.getP(), spec.getG());
    }

    JCEElGamalPrivateKey(PrivateKeyInfo info) {
        ElGamalParameter params = ElGamalParameter.f(info.g().h());
        this.x = ASN1Integer.o(info.h()).r();
        this.elSpec = new ElGamalParameterSpec(params.g(), params.e());
    }

    JCEElGamalPrivateKey(ElGamalPrivateKeyParameters params) {
        this.x = params.c();
        this.elSpec = new ElGamalParameterSpec(params.b().c(), params.b().a());
    }

    public String getAlgorithm() {
        return "ElGamal";
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        return KeyUtil.b(new AlgorithmIdentifier(OIWObjectIdentifiers.l, new ElGamalParameter(this.elSpec.b(), this.elSpec.a())), new ASN1Integer(getX()));
    }

    public ElGamalParameterSpec getParameters() {
        return this.elSpec;
    }

    public DHParameterSpec getParams() {
        return new DHParameterSpec(this.elSpec.b(), this.elSpec.a());
    }

    public BigInteger getX() {
        return this.x;
    }

    private void readObject(ObjectInputStream in) {
        this.x = (BigInteger) in.readObject();
        this.elSpec = new ElGamalParameterSpec((BigInteger) in.readObject(), (BigInteger) in.readObject());
    }

    private void writeObject(ObjectOutputStream out) {
        out.writeObject(getX());
        out.writeObject(this.elSpec.b());
        out.writeObject(this.elSpec.a());
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
