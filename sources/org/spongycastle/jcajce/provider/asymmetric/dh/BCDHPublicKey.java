package org.spongycastle.jcajce.provider.asymmetric.dh;

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
import org.spongycastle.asn1.x9.DomainParameters;
import org.spongycastle.asn1.x9.ValidationParams;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.DHValidationParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;

public class BCDHPublicKey implements DHPublicKey {
    static final long serialVersionUID = -216691575254424324L;
    private transient DHPublicKeyParameters c;
    private transient DHParameterSpec d;
    private transient SubjectPublicKeyInfo f;
    private BigInteger y;

    BCDHPublicKey(DHPublicKeySpec spec) {
        this.y = spec.getY();
        this.d = new DHParameterSpec(spec.getP(), spec.getG());
        this.c = new DHPublicKeyParameters(this.y, new DHParameters(spec.getP(), spec.getG()));
    }

    BCDHPublicKey(DHPublicKey key) {
        this.y = key.getY();
        this.d = key.getParams();
        this.c = new DHPublicKeyParameters(this.y, new DHParameters(this.d.getP(), this.d.getG()));
    }

    BCDHPublicKey(DHPublicKeyParameters params) {
        this.y = params.c();
        this.d = new DHParameterSpec(params.b().e(), params.b().b(), params.b().c());
        this.c = params;
    }

    BCDHPublicKey(BigInteger y2, DHParameterSpec dhSpec) {
        this.y = y2;
        this.d = dhSpec;
        this.c = new DHPublicKeyParameters(y2, new DHParameters(dhSpec.getP(), dhSpec.getG()));
    }

    public BCDHPublicKey(SubjectPublicKeyInfo info) {
        this.f = info;
        try {
            ASN1Integer derY = (ASN1Integer) info.i();
            this.y = derY.r();
            ASN1Sequence seq = ASN1Sequence.o(info.e().h());
            ASN1ObjectIdentifier id = info.e().e();
            if (id.equals(PKCSObjectIdentifiers.b0)) {
            } else if (a(seq)) {
                ASN1Integer aSN1Integer = derY;
            } else if (id.equals(X9ObjectIdentifiers.k4)) {
                DomainParameters params = DomainParameters.f(seq);
                this.d = new DHParameterSpec(params.i(), params.e());
                ValidationParams validationParams = params.n();
                if (validationParams != null) {
                    ASN1Integer aSN1Integer2 = derY;
                    this.c = new DHPublicKeyParameters(this.y, new DHParameters(params.i(), params.e(), params.k(), params.g(), new DHValidationParameters(validationParams.getSeed(), validationParams.getPgenCounter().intValue())));
                    return;
                }
                this.c = new DHPublicKeyParameters(this.y, new DHParameters(params.i(), params.e(), params.k(), params.g(), (DHValidationParameters) null));
                return;
            } else {
                throw new IllegalArgumentException("unknown algorithm type: " + id);
            }
            DHParameter params2 = DHParameter.f(seq);
            if (params2.g() != null) {
                this.d = new DHParameterSpec(params2.h(), params2.e(), params2.g().intValue());
            } else {
                this.d = new DHParameterSpec(params2.h(), params2.e());
            }
            this.c = new DHPublicKeyParameters(this.y, new DHParameters(this.d.getP(), this.d.getG()));
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
        SubjectPublicKeyInfo subjectPublicKeyInfo = this.f;
        if (subjectPublicKeyInfo != null) {
            return KeyUtil.d(subjectPublicKeyInfo);
        }
        return KeyUtil.c(new AlgorithmIdentifier(PKCSObjectIdentifiers.b0, new DHParameter(this.d.getP(), this.d.getG(), this.d.getL()).toASN1Primitive()), new ASN1Integer(this.y));
    }

    public DHParameterSpec getParams() {
        return this.d;
    }

    public BigInteger getY() {
        return this.y;
    }

    public DHPublicKeyParameters engineGetKeyParameters() {
        return this.c;
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
        this.d = new DHParameterSpec((BigInteger) in.readObject(), (BigInteger) in.readObject(), in.readInt());
        this.f = null;
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(this.d.getP());
        out.writeObject(this.d.getG());
        out.writeInt(this.d.getL());
    }
}
