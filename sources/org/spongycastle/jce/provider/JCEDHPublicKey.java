package org.spongycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.pkcs.DHParameter;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.DHDomainParameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;

public class JCEDHPublicKey implements DHPublicKey {
    static final long serialVersionUID = -216691575254424324L;
    private DHParameterSpec dhSpec;
    private SubjectPublicKeyInfo info;
    private BigInteger y;

    JCEDHPublicKey(DHPublicKeySpec spec) {
        this.y = spec.getY();
        this.dhSpec = new DHParameterSpec(spec.getP(), spec.getG());
    }

    JCEDHPublicKey(DHPublicKey key) {
        this.y = key.getY();
        this.dhSpec = key.getParams();
    }

    JCEDHPublicKey(DHPublicKeyParameters params) {
        this.y = params.c();
        this.dhSpec = new DHParameterSpec(params.b().e(), params.b().b(), params.b().c());
    }

    JCEDHPublicKey(BigInteger y2, DHParameterSpec dhSpec2) {
        this.y = y2;
        this.dhSpec = dhSpec2;
    }

    JCEDHPublicKey(SubjectPublicKeyInfo info2) {
        this.info = info2;
        try {
            this.y = ((ASN1Integer) info2.i()).r();
            ASN1Sequence seq = ASN1Sequence.o(info2.f().h());
            ASN1ObjectIdentifier id = info2.f().e();
            if (id.equals(PKCSObjectIdentifiers.b0) || a(seq)) {
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
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid info structure in DH public key");
        }
    }

    public String getAlgorithm() {
        return "DH";
    }

    public String getFormat() {
        return "X.509";
    }

    public byte[] getEncoded() {
        SubjectPublicKeyInfo subjectPublicKeyInfo = this.info;
        if (subjectPublicKeyInfo != null) {
            return KeyUtil.d(subjectPublicKeyInfo);
        }
        return KeyUtil.c(new AlgorithmIdentifier(PKCSObjectIdentifiers.b0, new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL())), new ASN1Integer(this.y));
    }

    public DHParameterSpec getParams() {
        return this.dhSpec;
    }

    public BigInteger getY() {
        return this.y;
    }

    private boolean a(ASN1Sequence seq) {
        if (seq.size() == 2) {
            return true;
        }
        if (seq.size() > 3) {
            return false;
        }
        if (ASN1Integer.o(seq.r(2)).r().compareTo(BigInteger.valueOf((long) ASN1Integer.o(seq.r(0)).r().bitLength())) > 0) {
            return false;
        }
        return true;
    }

    private void readObject(ObjectInputStream in) {
        this.y = (BigInteger) in.readObject();
        this.dhSpec = new DHParameterSpec((BigInteger) in.readObject(), (BigInteger) in.readObject(), in.readInt());
    }

    private void writeObject(ObjectOutputStream out) {
        out.writeObject(getY());
        out.writeObject(this.dhSpec.getP());
        out.writeObject(this.dhSpec.getG());
        out.writeInt(this.dhSpec.getL());
    }
}
