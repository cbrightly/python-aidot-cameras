package org.spongycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.oiw.ElGamalParameter;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jce.interfaces.ElGamalPublicKey;
import org.spongycastle.jce.spec.ElGamalParameterSpec;
import org.spongycastle.jce.spec.ElGamalPublicKeySpec;

public class JCEElGamalPublicKey implements ElGamalPublicKey, DHPublicKey {
    static final long serialVersionUID = 8712728417091216948L;
    private ElGamalParameterSpec elSpec;
    private BigInteger y;

    JCEElGamalPublicKey(ElGamalPublicKeySpec spec) {
        this.y = spec.b();
        this.elSpec = new ElGamalParameterSpec(spec.a().b(), spec.a().a());
    }

    JCEElGamalPublicKey(DHPublicKeySpec spec) {
        this.y = spec.getY();
        this.elSpec = new ElGamalParameterSpec(spec.getP(), spec.getG());
    }

    JCEElGamalPublicKey(ElGamalPublicKey key) {
        this.y = key.getY();
        this.elSpec = key.getParameters();
    }

    JCEElGamalPublicKey(DHPublicKey key) {
        this.y = key.getY();
        this.elSpec = new ElGamalParameterSpec(key.getParams().getP(), key.getParams().getG());
    }

    JCEElGamalPublicKey(ElGamalPublicKeyParameters params) {
        this.y = params.c();
        this.elSpec = new ElGamalParameterSpec(params.b().c(), params.b().a());
    }

    JCEElGamalPublicKey(BigInteger y2, ElGamalParameterSpec elSpec2) {
        this.y = y2;
        this.elSpec = elSpec2;
    }

    JCEElGamalPublicKey(SubjectPublicKeyInfo info) {
        ElGamalParameter params = ElGamalParameter.f(info.e().h());
        try {
            this.y = ((ASN1Integer) info.i()).r();
            this.elSpec = new ElGamalParameterSpec(params.g(), params.e());
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
        return KeyUtil.c(new AlgorithmIdentifier(OIWObjectIdentifiers.l, new ElGamalParameter(this.elSpec.b(), this.elSpec.a())), new ASN1Integer(this.y));
    }

    public ElGamalParameterSpec getParameters() {
        return this.elSpec;
    }

    public DHParameterSpec getParams() {
        return new DHParameterSpec(this.elSpec.b(), this.elSpec.a());
    }

    public BigInteger getY() {
        return this.y;
    }

    private void readObject(ObjectInputStream in) {
        this.y = (BigInteger) in.readObject();
        this.elSpec = new ElGamalParameterSpec((BigInteger) in.readObject(), (BigInteger) in.readObject());
    }

    private void writeObject(ObjectOutputStream out) {
        out.writeObject(getY());
        out.writeObject(this.elSpec.b());
        out.writeObject(this.elSpec.a());
    }
}
