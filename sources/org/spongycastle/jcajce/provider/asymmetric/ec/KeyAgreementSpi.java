package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.agreement.ECDHBasicAgreement;
import org.spongycastle.crypto.agreement.ECDHCBasicAgreement;
import org.spongycastle.crypto.agreement.ECMQVBasicAgreement;
import org.spongycastle.crypto.agreement.kdf.ConcatenationKDFGenerator;
import org.spongycastle.crypto.generators.KDF2BytesGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.MQVPublicParameters;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.spongycastle.jcajce.spec.MQVParameterSpec;
import org.spongycastle.jcajce.spec.UserKeyingMaterialSpec;
import org.spongycastle.jce.interfaces.ECPublicKey;
import org.spongycastle.jce.interfaces.MQVPublicKey;

public class KeyAgreementSpi extends BaseAgreementSpi {
    private static final X9IntegerConverter i = new X9IntegerConverter();
    private String j;
    private ECDomainParameters k;
    private BasicAgreement l;
    private MQVParameterSpec m;
    private BigInteger n;

    protected KeyAgreementSpi(String kaAlgorithm, BasicAgreement agreement, DerivationFunction kdf) {
        super(kaAlgorithm, kdf);
        this.j = kaAlgorithm;
        this.l = agreement;
    }

    /* access modifiers changed from: protected */
    public byte[] e(BigInteger r) {
        X9IntegerConverter x9IntegerConverter = i;
        return x9IntegerConverter.c(r, x9IntegerConverter.a(this.k.a()));
    }

    /* access modifiers changed from: protected */
    public Key engineDoPhase(Key key, boolean lastPhase) {
        CipherParameters pubKey;
        if (this.k == null) {
            throw new IllegalStateException(this.j + " not initialised.");
        } else if (lastPhase) {
            if (this.l instanceof ECMQVBasicAgreement) {
                if (!(key instanceof MQVPublicKey)) {
                    pubKey = new MQVPublicParameters((ECPublicKeyParameters) ECUtils.a((PublicKey) key), (ECPublicKeyParameters) ECUtils.a(this.m.c()));
                } else {
                    MQVPublicKey mqvPubKey = (MQVPublicKey) key;
                    pubKey = new MQVPublicParameters((ECPublicKeyParameters) ECUtils.a(mqvPubKey.getStaticKey()), (ECPublicKeyParameters) ECUtils.a(mqvPubKey.getEphemeralKey()));
                }
            } else if (key instanceof PublicKey) {
                pubKey = ECUtils.a((PublicKey) key);
            } else {
                throw new InvalidKeyException(this.j + " key agreement requires " + f(ECPublicKey.class) + " for doPhase");
            }
            try {
                this.n = this.l.c(pubKey);
                return null;
            } catch (Exception e) {
                throw new InvalidKeyException("calculation failed: " + e.getMessage()) {
                    public Throwable getCause() {
                        return e;
                    }
                };
            }
        } else {
            throw new IllegalStateException(this.j + " can only be between two parties.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, AlgorithmParameterSpec params, SecureRandom random) {
        if (params == null || (params instanceof MQVParameterSpec) || (params instanceof UserKeyingMaterialSpec)) {
            g(key, params);
            return;
        }
        throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, SecureRandom random) {
        g(key, (AlgorithmParameterSpec) null);
    }

    /* JADX WARNING: type inference failed for: r4v6, types: [org.spongycastle.crypto.params.AsymmetricKeyParameter] */
    /* JADX WARNING: type inference failed for: r4v9, types: [org.spongycastle.crypto.params.AsymmetricKeyParameter] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void g(java.security.Key r6, java.security.spec.AlgorithmParameterSpec r7) {
        /*
            r5 = this;
            org.spongycastle.crypto.BasicAgreement r0 = r5.l
            boolean r0 = r0 instanceof org.spongycastle.crypto.agreement.ECMQVBasicAgreement
            java.lang.String r1 = " for initialisation"
            java.lang.String r2 = " key agreement requires "
            r3 = 0
            if (r0 == 0) goto L_0x00ad
            r5.m = r3
            boolean r0 = r6 instanceof org.spongycastle.jce.interfaces.MQVPrivateKey
            if (r0 != 0) goto L_0x0039
            boolean r0 = r7 instanceof org.spongycastle.jcajce.spec.MQVParameterSpec
            if (r0 == 0) goto L_0x0016
            goto L_0x0039
        L_0x0016:
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = r5.j
            r3.append(r4)
            r3.append(r2)
            java.lang.Class<org.spongycastle.jcajce.spec.MQVParameterSpec> r2 = org.spongycastle.jcajce.spec.MQVParameterSpec.class
            java.lang.String r2 = f(r2)
            r3.append(r2)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r0.<init>(r1)
            throw r0
        L_0x0039:
            boolean r0 = r6 instanceof org.spongycastle.jce.interfaces.MQVPrivateKey
            if (r0 == 0) goto L_0x006a
            r0 = r6
            org.spongycastle.jce.interfaces.MQVPrivateKey r0 = (org.spongycastle.jce.interfaces.MQVPrivateKey) r0
            java.security.PrivateKey r1 = r0.getStaticPrivateKey()
            org.spongycastle.crypto.params.AsymmetricKeyParameter r1 = org.spongycastle.jcajce.provider.asymmetric.util.ECUtil.d(r1)
            org.spongycastle.crypto.params.ECPrivateKeyParameters r1 = (org.spongycastle.crypto.params.ECPrivateKeyParameters) r1
            java.security.PrivateKey r2 = r0.getEphemeralPrivateKey()
            org.spongycastle.crypto.params.AsymmetricKeyParameter r2 = org.spongycastle.jcajce.provider.asymmetric.util.ECUtil.d(r2)
            org.spongycastle.crypto.params.ECPrivateKeyParameters r2 = (org.spongycastle.crypto.params.ECPrivateKeyParameters) r2
            r3 = 0
            java.security.PublicKey r4 = r0.getEphemeralPublicKey()
            if (r4 == 0) goto L_0x0069
            java.security.PublicKey r4 = r0.getEphemeralPublicKey()
            org.spongycastle.crypto.params.AsymmetricKeyParameter r4 = org.spongycastle.jcajce.provider.asymmetric.ec.ECUtils.a(r4)
            r3 = r4
            org.spongycastle.crypto.params.ECPublicKeyParameters r3 = (org.spongycastle.crypto.params.ECPublicKeyParameters) r3
        L_0x0069:
            goto L_0x009c
        L_0x006a:
            r0 = r7
            org.spongycastle.jcajce.spec.MQVParameterSpec r0 = (org.spongycastle.jcajce.spec.MQVParameterSpec) r0
            r1 = r6
            java.security.PrivateKey r1 = (java.security.PrivateKey) r1
            org.spongycastle.crypto.params.AsymmetricKeyParameter r1 = org.spongycastle.jcajce.provider.asymmetric.util.ECUtil.d(r1)
            org.spongycastle.crypto.params.ECPrivateKeyParameters r1 = (org.spongycastle.crypto.params.ECPrivateKeyParameters) r1
            java.security.PrivateKey r2 = r0.a()
            org.spongycastle.crypto.params.AsymmetricKeyParameter r2 = org.spongycastle.jcajce.provider.asymmetric.util.ECUtil.d(r2)
            org.spongycastle.crypto.params.ECPrivateKeyParameters r2 = (org.spongycastle.crypto.params.ECPrivateKeyParameters) r2
            r3 = 0
            java.security.PublicKey r4 = r0.b()
            if (r4 == 0) goto L_0x0094
            java.security.PublicKey r4 = r0.b()
            org.spongycastle.crypto.params.AsymmetricKeyParameter r4 = org.spongycastle.jcajce.provider.asymmetric.ec.ECUtils.a(r4)
            r3 = r4
            org.spongycastle.crypto.params.ECPublicKeyParameters r3 = (org.spongycastle.crypto.params.ECPublicKeyParameters) r3
        L_0x0094:
            r5.m = r0
            byte[] r4 = r0.d()
            r5.h = r4
        L_0x009c:
            org.spongycastle.crypto.params.MQVPrivateParameters r0 = new org.spongycastle.crypto.params.MQVPrivateParameters
            r0.<init>(r1, r2, r3)
            org.spongycastle.crypto.params.ECDomainParameters r4 = r1.b()
            r5.k = r4
            org.spongycastle.crypto.BasicAgreement r4 = r5.l
            r4.a(r0)
            goto L_0x00d2
        L_0x00ad:
            boolean r0 = r6 instanceof java.security.PrivateKey
            if (r0 == 0) goto L_0x00d3
            r0 = r6
            java.security.PrivateKey r0 = (java.security.PrivateKey) r0
            org.spongycastle.crypto.params.AsymmetricKeyParameter r0 = org.spongycastle.jcajce.provider.asymmetric.util.ECUtil.d(r0)
            org.spongycastle.crypto.params.ECPrivateKeyParameters r0 = (org.spongycastle.crypto.params.ECPrivateKeyParameters) r0
            org.spongycastle.crypto.params.ECDomainParameters r1 = r0.b()
            r5.k = r1
            boolean r1 = r7 instanceof org.spongycastle.jcajce.spec.UserKeyingMaterialSpec
            if (r1 == 0) goto L_0x00cb
            r1 = r7
            org.spongycastle.jcajce.spec.UserKeyingMaterialSpec r1 = (org.spongycastle.jcajce.spec.UserKeyingMaterialSpec) r1
            byte[] r3 = r1.a()
        L_0x00cb:
            r5.h = r3
            org.spongycastle.crypto.BasicAgreement r1 = r5.l
            r1.a(r0)
        L_0x00d2:
            return
        L_0x00d3:
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = r5.j
            r3.append(r4)
            r3.append(r2)
            java.lang.Class<org.spongycastle.jce.interfaces.ECPrivateKey> r2 = org.spongycastle.jce.interfaces.ECPrivateKey.class
            java.lang.String r2 = f(r2)
            r3.append(r2)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi.g(java.security.Key, java.security.spec.AlgorithmParameterSpec):void");
    }

    private static String f(Class clazz) {
        String fullName = clazz.getName();
        return fullName.substring(fullName.lastIndexOf(46) + 1);
    }

    /* access modifiers changed from: protected */
    public byte[] a() {
        return e(this.n);
    }

    public static class DH extends KeyAgreementSpi {
        public DH() {
            super("ECDH", new ECDHBasicAgreement(), (DerivationFunction) null);
        }
    }

    public static class DHC extends KeyAgreementSpi {
        public DHC() {
            super("ECDHC", new ECDHCBasicAgreement(), (DerivationFunction) null);
        }
    }

    public static class MQV extends KeyAgreementSpi {
        public MQV() {
            super("ECMQV", new ECMQVBasicAgreement(), (DerivationFunction) null);
        }
    }

    public static class DHwithSHA1KDF extends KeyAgreementSpi {
        public DHwithSHA1KDF() {
            super("ECDHwithSHA1KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.b()));
        }
    }

    public static class DHwithSHA1KDFAndSharedInfo extends KeyAgreementSpi {
        public DHwithSHA1KDFAndSharedInfo() {
            super("ECDHwithSHA1KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.b()));
        }
    }

    public static class CDHwithSHA1KDFAndSharedInfo extends KeyAgreementSpi {
        public CDHwithSHA1KDFAndSharedInfo() {
            super("ECCDHwithSHA1KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.b()));
        }
    }

    public static class DHwithSHA224KDFAndSharedInfo extends KeyAgreementSpi {
        public DHwithSHA224KDFAndSharedInfo() {
            super("ECDHwithSHA224KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.c()));
        }
    }

    public static class CDHwithSHA224KDFAndSharedInfo extends KeyAgreementSpi {
        public CDHwithSHA224KDFAndSharedInfo() {
            super("ECCDHwithSHA224KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.c()));
        }
    }

    public static class DHwithSHA256KDFAndSharedInfo extends KeyAgreementSpi {
        public DHwithSHA256KDFAndSharedInfo() {
            super("ECDHwithSHA256KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.d()));
        }
    }

    public static class CDHwithSHA256KDFAndSharedInfo extends KeyAgreementSpi {
        public CDHwithSHA256KDFAndSharedInfo() {
            super("ECCDHwithSHA256KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.d()));
        }
    }

    public static class DHwithSHA384KDFAndSharedInfo extends KeyAgreementSpi {
        public DHwithSHA384KDFAndSharedInfo() {
            super("ECDHwithSHA384KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.e()));
        }
    }

    public static class CDHwithSHA384KDFAndSharedInfo extends KeyAgreementSpi {
        public CDHwithSHA384KDFAndSharedInfo() {
            super("ECCDHwithSHA384KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.e()));
        }
    }

    public static class DHwithSHA512KDFAndSharedInfo extends KeyAgreementSpi {
        public DHwithSHA512KDFAndSharedInfo() {
            super("ECDHwithSHA512KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.j()));
        }
    }

    public static class CDHwithSHA512KDFAndSharedInfo extends KeyAgreementSpi {
        public CDHwithSHA512KDFAndSharedInfo() {
            super("ECCDHwithSHA512KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.j()));
        }
    }

    public static class MQVwithSHA1KDFAndSharedInfo extends KeyAgreementSpi {
        public MQVwithSHA1KDFAndSharedInfo() {
            super("ECMQVwithSHA1KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.b()));
        }
    }

    public static class MQVwithSHA224KDFAndSharedInfo extends KeyAgreementSpi {
        public MQVwithSHA224KDFAndSharedInfo() {
            super("ECMQVwithSHA224KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.c()));
        }
    }

    public static class MQVwithSHA256KDFAndSharedInfo extends KeyAgreementSpi {
        public MQVwithSHA256KDFAndSharedInfo() {
            super("ECMQVwithSHA256KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.d()));
        }
    }

    public static class MQVwithSHA384KDFAndSharedInfo extends KeyAgreementSpi {
        public MQVwithSHA384KDFAndSharedInfo() {
            super("ECMQVwithSHA384KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.e()));
        }
    }

    public static class MQVwithSHA512KDFAndSharedInfo extends KeyAgreementSpi {
        public MQVwithSHA512KDFAndSharedInfo() {
            super("ECMQVwithSHA512KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.j()));
        }
    }

    public static class DHwithSHA1CKDF extends KeyAgreementSpi {
        public DHwithSHA1CKDF() {
            super("ECDHwithSHA1CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.b()));
        }
    }

    public static class DHwithSHA256CKDF extends KeyAgreementSpi {
        public DHwithSHA256CKDF() {
            super("ECDHwithSHA256CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.d()));
        }
    }

    public static class DHwithSHA384CKDF extends KeyAgreementSpi {
        public DHwithSHA384CKDF() {
            super("ECDHwithSHA384CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.e()));
        }
    }

    public static class DHwithSHA512CKDF extends KeyAgreementSpi {
        public DHwithSHA512CKDF() {
            super("ECDHwithSHA512CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.j()));
        }
    }

    public static class MQVwithSHA1CKDF extends KeyAgreementSpi {
        public MQVwithSHA1CKDF() {
            super("ECMQVwithSHA1CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.b()));
        }
    }

    public static class MQVwithSHA224CKDF extends KeyAgreementSpi {
        public MQVwithSHA224CKDF() {
            super("ECMQVwithSHA224CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.c()));
        }
    }

    public static class MQVwithSHA256CKDF extends KeyAgreementSpi {
        public MQVwithSHA256CKDF() {
            super("ECMQVwithSHA256CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.d()));
        }
    }

    public static class MQVwithSHA384CKDF extends KeyAgreementSpi {
        public MQVwithSHA384CKDF() {
            super("ECMQVwithSHA384CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.e()));
        }
    }

    public static class MQVwithSHA512CKDF extends KeyAgreementSpi {
        public MQVwithSHA512CKDF() {
            super("ECMQVwithSHA512CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.j()));
        }
    }
}
