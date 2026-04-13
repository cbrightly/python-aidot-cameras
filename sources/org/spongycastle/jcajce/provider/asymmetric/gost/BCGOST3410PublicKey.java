package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.GOST3410PublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jce.interfaces.GOST3410Params;
import org.spongycastle.jce.interfaces.GOST3410PublicKey;
import org.spongycastle.jce.spec.GOST3410ParameterSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeySpec;
import org.spongycastle.util.Strings;

public class BCGOST3410PublicKey implements GOST3410PublicKey {
    static final long serialVersionUID = -6251023343619275990L;
    private transient GOST3410Params c;
    private BigInteger y;

    BCGOST3410PublicKey(GOST3410PublicKeySpec spec) {
        this.y = spec.d();
        this.c = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(spec.b(), spec.c(), spec.a()));
    }

    BCGOST3410PublicKey(GOST3410PublicKey key) {
        this.y = key.getY();
        this.c = key.getParameters();
    }

    BCGOST3410PublicKey(GOST3410PublicKeyParameters params, GOST3410ParameterSpec spec) {
        this.y = params.c();
        this.c = spec;
    }

    BCGOST3410PublicKey(BigInteger y2, GOST3410ParameterSpec gost3410Spec) {
        this.y = y2;
        this.c = gost3410Spec;
    }

    BCGOST3410PublicKey(SubjectPublicKeyInfo info) {
        GOST3410PublicKeyAlgParameters params = new GOST3410PublicKeyAlgParameters((ASN1Sequence) info.f().h());
        try {
            byte[] keyEnc = ((DEROctetString) info.i()).q();
            byte[] keyBytes = new byte[keyEnc.length];
            for (int i = 0; i != keyEnc.length; i++) {
                keyBytes[i] = keyEnc[(keyEnc.length - 1) - i];
            }
            this.y = new BigInteger(1, keyBytes);
            this.c = GOST3410ParameterSpec.a(params);
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid info structure in GOST3410 public key");
        }
    }

    public String getAlgorithm() {
        return "GOST3410";
    }

    public String getFormat() {
        return "X.509";
    }

    public byte[] getEncoded() {
        byte[] keyBytes;
        SubjectPublicKeyInfo info;
        byte[] keyEnc = getY().toByteArray();
        if (keyEnc[0] == 0) {
            keyBytes = new byte[(keyEnc.length - 1)];
        } else {
            keyBytes = new byte[keyEnc.length];
        }
        for (int i = 0; i != keyBytes.length; i++) {
            keyBytes[i] = keyEnc[(keyEnc.length - 1) - i];
        }
        try {
            GOST3410Params gOST3410Params = this.c;
            if (!(gOST3410Params instanceof GOST3410ParameterSpec)) {
                info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.l), (ASN1Encodable) new DEROctetString(keyBytes));
            } else if (gOST3410Params.getEncryptionParamSetOID() != null) {
                info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.l, new GOST3410PublicKeyAlgParameters(new ASN1ObjectIdentifier(this.c.getPublicKeyParamSetOID()), new ASN1ObjectIdentifier(this.c.getDigestParamSetOID()), new ASN1ObjectIdentifier(this.c.getEncryptionParamSetOID()))), (ASN1Encodable) new DEROctetString(keyBytes));
            } else {
                info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.l, new GOST3410PublicKeyAlgParameters(new ASN1ObjectIdentifier(this.c.getPublicKeyParamSetOID()), new ASN1ObjectIdentifier(this.c.getDigestParamSetOID()))), (ASN1Encodable) new DEROctetString(keyBytes));
            }
            return KeyUtil.d(info);
        } catch (IOException e) {
            return null;
        }
    }

    public GOST3410Params getParameters() {
        return this.c;
    }

    public BigInteger getY() {
        return this.y;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        buf.append("GOST3410 Public Key");
        buf.append(nl);
        buf.append("            y: ");
        buf.append(getY().toString(16));
        buf.append(nl);
        return buf.toString();
    }

    public boolean equals(Object o) {
        if (!(o instanceof BCGOST3410PublicKey)) {
            return false;
        }
        BCGOST3410PublicKey other = (BCGOST3410PublicKey) o;
        if (!this.y.equals(other.y) || !this.c.equals(other.c)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.y.hashCode() ^ this.c.hashCode();
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        String publicKeyParamSetOID = (String) in.readObject();
        if (publicKeyParamSetOID != null) {
            this.c = new GOST3410ParameterSpec(publicKeyParamSetOID, (String) in.readObject(), (String) in.readObject());
            return;
        }
        this.c = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec((BigInteger) in.readObject(), (BigInteger) in.readObject(), (BigInteger) in.readObject()));
        in.readObject();
        in.readObject();
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
