package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.GOST3410Params;
import org.spongycastle.jce.interfaces.GOST3410PrivateKey;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.spec.GOST3410ParameterSpec;
import org.spongycastle.jce.spec.GOST3410PrivateKeySpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public class BCGOST3410PrivateKey implements GOST3410PrivateKey, PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 8581661527592305464L;
    private transient GOST3410Params c;
    private transient PKCS12BagAttributeCarrier d = new PKCS12BagAttributeCarrierImpl();
    private BigInteger x;

    protected BCGOST3410PrivateKey() {
    }

    BCGOST3410PrivateKey(GOST3410PrivateKey key) {
        this.x = key.getX();
        this.c = key.getParameters();
    }

    BCGOST3410PrivateKey(GOST3410PrivateKeySpec spec) {
        this.x = spec.d();
        this.c = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(spec.b(), spec.c(), spec.a()));
    }

    BCGOST3410PrivateKey(PrivateKeyInfo info) {
        GOST3410PublicKeyAlgParameters params = new GOST3410PublicKeyAlgParameters((ASN1Sequence) info.e().h());
        byte[] keyEnc = ASN1OctetString.o(info.h()).q();
        byte[] keyBytes = new byte[keyEnc.length];
        for (int i = 0; i != keyEnc.length; i++) {
            keyBytes[i] = keyEnc[(keyEnc.length - 1) - i];
        }
        this.x = new BigInteger(1, keyBytes);
        this.c = GOST3410ParameterSpec.a(params);
    }

    BCGOST3410PrivateKey(GOST3410PrivateKeyParameters params, GOST3410ParameterSpec spec) {
        this.x = params.c();
        this.c = spec;
        if (spec == null) {
            throw new IllegalArgumentException("spec is null");
        }
    }

    public String getAlgorithm() {
        return "GOST3410";
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        byte[] keyBytes;
        PrivateKeyInfo info;
        byte[] keyEnc = getX().toByteArray();
        if (keyEnc[0] == 0) {
            keyBytes = new byte[(keyEnc.length - 1)];
        } else {
            keyBytes = new byte[keyEnc.length];
        }
        for (int i = 0; i != keyBytes.length; i++) {
            keyBytes[i] = keyEnc[(keyEnc.length - 1) - i];
        }
        try {
            if (this.c instanceof GOST3410ParameterSpec) {
                info = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.l, new GOST3410PublicKeyAlgParameters(new ASN1ObjectIdentifier(this.c.getPublicKeyParamSetOID()), new ASN1ObjectIdentifier(this.c.getDigestParamSetOID()))), new DEROctetString(keyBytes));
            } else {
                info = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.l), new DEROctetString(keyBytes));
            }
            return info.getEncoded("DER");
        } catch (IOException e) {
            return null;
        }
    }

    public GOST3410Params getParameters() {
        return this.c;
    }

    public BigInteger getX() {
        return this.x;
    }

    public boolean equals(Object o) {
        if (!(o instanceof GOST3410PrivateKey)) {
            return false;
        }
        GOST3410PrivateKey other = (GOST3410PrivateKey) o;
        if (!getX().equals(other.getX()) || !getParameters().getPublicKeyParameters().equals(other.getParameters().getPublicKeyParameters()) || !getParameters().getDigestParamSetOID().equals(other.getParameters().getDigestParamSetOID()) || !a(getParameters().getEncryptionParamSetOID(), other.getParameters().getEncryptionParamSetOID())) {
            return false;
        }
        return true;
    }

    private boolean a(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null) {
            return false;
        }
        return o1.equals(o2);
    }

    public int hashCode() {
        return getX().hashCode() ^ this.c.hashCode();
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
        String publicKeyParamSetOID = (String) in.readObject();
        if (publicKeyParamSetOID != null) {
            this.c = new GOST3410ParameterSpec(publicKeyParamSetOID, (String) in.readObject(), (String) in.readObject());
        } else {
            this.c = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec((BigInteger) in.readObject(), (BigInteger) in.readObject(), (BigInteger) in.readObject()));
            in.readObject();
            in.readObject();
        }
        this.d = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        if (this.c.getPublicKeyParamSetOID() != null) {
            out.writeObject(this.c.getPublicKeyParamSetOID());
            out.writeObject(this.c.getDigestParamSetOID());
            out.writeObject(this.c.getEncryptionParamSetOID());
            return;
        }
        out.writeObject((Object) null);
        out.writeObject(this.c.getPublicKeyParameters().b());
        out.writeObject(this.c.getPublicKeyParameters().c());
        out.writeObject(this.c.getPublicKeyParameters().a());
        out.writeObject(this.c.getDigestParamSetOID());
        out.writeObject(this.c.getEncryptionParamSetOID());
    }
}
