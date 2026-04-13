package org.spongycastle.jcajce.provider.asymmetric.ecgost12;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.agreement.ECVKOAgreement;
import org.spongycastle.crypto.digests.GOST3411_2012_256Digest;
import org.spongycastle.crypto.digests.GOST3411_2012_512Digest;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ParametersWithUKM;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.spec.UserKeyingMaterialSpec;
import org.spongycastle.jce.interfaces.ECPrivateKey;
import org.spongycastle.jce.interfaces.ECPublicKey;

public class KeyAgreementSpi extends BaseAgreementSpi {
    private static final X9IntegerConverter i = new X9IntegerConverter();
    private String j;
    private ECDomainParameters k;
    private ECVKOAgreement l;
    private byte[] m;

    protected KeyAgreementSpi(String kaAlgorithm, ECVKOAgreement agreement, DerivationFunction kdf) {
        super(kaAlgorithm, kdf);
        this.j = kaAlgorithm;
        this.l = agreement;
    }

    /* access modifiers changed from: protected */
    public Key engineDoPhase(Key key, boolean lastPhase) {
        if (this.k == null) {
            throw new IllegalStateException(this.j + " not initialised.");
        } else if (!lastPhase) {
            throw new IllegalStateException(this.j + " can only be between two parties.");
        } else if (key instanceof PublicKey) {
            try {
                this.m = this.l.a(e((PublicKey) key));
                return null;
            } catch (Exception e) {
                throw new InvalidKeyException("calculation failed: " + e.getMessage()) {
                    public Throwable getCause() {
                        return e;
                    }
                };
            }
        } else {
            throw new InvalidKeyException(this.j + " key agreement requires " + f(ECPublicKey.class) + " for doPhase");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, AlgorithmParameterSpec params, SecureRandom random) {
        if (params == null || (params instanceof UserKeyingMaterialSpec)) {
            g(key, params);
            return;
        }
        throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, SecureRandom random) {
        g(key, (AlgorithmParameterSpec) null);
    }

    private void g(Key key, AlgorithmParameterSpec parameterSpec) {
        if (key instanceof PrivateKey) {
            ECPrivateKeyParameters privKey = (ECPrivateKeyParameters) ECUtil.d((PrivateKey) key);
            this.k = privKey.b();
            byte[] a = parameterSpec instanceof UserKeyingMaterialSpec ? ((UserKeyingMaterialSpec) parameterSpec).a() : null;
            this.h = a;
            this.l.c(new ParametersWithUKM(privKey, a));
            return;
        }
        throw new InvalidKeyException(this.j + " key agreement requires " + f(ECPrivateKey.class) + " for initialisation");
    }

    private static String f(Class clazz) {
        String fullName = clazz.getName();
        return fullName.substring(fullName.lastIndexOf(46) + 1);
    }

    static AsymmetricKeyParameter e(PublicKey key) {
        return key instanceof BCECGOST3410_2012PublicKey ? ((BCECGOST3410_2012PublicKey) key).engineGetKeyParameters() : ECUtil.e(key);
    }

    /* access modifiers changed from: protected */
    public byte[] a() {
        return this.m;
    }

    public static class ECVKO256 extends KeyAgreementSpi {
        public ECVKO256() {
            super("ECGOST3410-2012-256", new ECVKOAgreement(new GOST3411_2012_256Digest()), (DerivationFunction) null);
        }
    }

    public static class ECVKO512 extends KeyAgreementSpi {
        public ECVKO512() {
            super("ECGOST3410-2012-512", new ECVKOAgreement(new GOST3411_2012_512Digest()), (DerivationFunction) null);
        }
    }
}
