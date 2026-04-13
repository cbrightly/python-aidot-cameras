package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.oiw.ElGamalParameter;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.jce.interfaces.ElGamalPublicKey;
import org.spongycastle.jce.spec.ElGamalParameterSpec;
import org.spongycastle.jce.spec.ElGamalPublicKeySpec;

public class BCElGamalPublicKey implements ElGamalPublicKey, DHPublicKey {
    static final long serialVersionUID = 8712728417091216948L;
    private transient ElGamalParameterSpec c;
    private BigInteger y;

    BCElGamalPublicKey(ElGamalPublicKeySpec spec) {
        this.y = spec.b();
        this.c = new ElGamalParameterSpec(spec.a().b(), spec.a().a());
    }

    BCElGamalPublicKey(DHPublicKeySpec spec) {
        this.y = spec.getY();
        this.c = new ElGamalParameterSpec(spec.getP(), spec.getG());
    }

    BCElGamalPublicKey(ElGamalPublicKey key) {
        this.y = key.getY();
        this.c = key.getParameters();
    }

    BCElGamalPublicKey(DHPublicKey key) {
        this.y = key.getY();
        this.c = new ElGamalParameterSpec(key.getParams().getP(), key.getParams().getG());
    }

    BCElGamalPublicKey(ElGamalPublicKeyParameters params) {
        this.y = params.c();
        this.c = new ElGamalParameterSpec(params.b().c(), params.b().a());
    }

    BCElGamalPublicKey(BigInteger y2, ElGamalParameterSpec elSpec) {
        this.y = y2;
        this.c = elSpec;
    }

    BCElGamalPublicKey(SubjectPublicKeyInfo info) {
        ElGamalParameter params = ElGamalParameter.f(info.e().h());
        try {
            this.y = ((ASN1Integer) info.i()).r();
            this.c = new ElGamalParameterSpec(params.g(), params.e());
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid info structure in DSA public key");
        }
    }

    public String getAlgorithm() {
        return "ElGamal";
    }

    public String getFormat() {
        return "X.509";
    }

    public byte[] getEncoded() {
        try {
            return new SubjectPublicKeyInfo(new AlgorithmIdentifier(OIWObjectIdentifiers.l, new ElGamalParameter(this.c.b(), this.c.a())), (ASN1Encodable) new ASN1Integer(this.y)).getEncoded("DER");
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

    public BigInteger getY() {
        return this.y;
    }

    public int hashCode() {
        return ((getY().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getL();
    }

    public boolean equals(Object o) {
        if (!(o instanceof DHPublicKey)) {
            return false;
        }
        DHPublicKey other = (DHPublicKey) o;
        if (!getY().equals(other.getY()) || !getParams().getG().equals(other.getParams().getG()) || !getParams().getP().equals(other.getParams().getP()) || getParams().getL() != other.getParams().getL()) {
            return false;
        }
        return true;
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        this.c = new ElGamalParameterSpec((BigInteger) in.readObject(), (BigInteger) in.readObject());
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(this.c.b());
        out.writeObject(this.c.a());
    }
}
