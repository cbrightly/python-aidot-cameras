package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSAESOAEPparams;
import org.spongycastle.asn1.pkcs.RSASSAPSSparams;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.jcajce.provider.util.DigestFactory;
import org.spongycastle.jcajce.util.MessageDigestUtils;

public abstract class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    /* access modifiers changed from: protected */
    public abstract AlgorithmParameterSpec b(Class cls);

    /* access modifiers changed from: protected */
    public boolean a(String format) {
        return format == null || format.equals("ASN.1");
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec engineGetParameterSpec(Class paramSpec) {
        if (paramSpec != null) {
            return b(paramSpec);
        }
        throw new NullPointerException("argument to getParameterSpec must not be null");
    }

    public static class OAEP extends AlgorithmParametersSpi {
        OAEPParameterSpec a;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            ASN1ObjectIdentifier b = DigestFactory.b(this.a.getDigestAlgorithm());
            DERNull dERNull = DERNull.c;
            try {
                return new RSAESOAEPparams(new AlgorithmIdentifier(b, dERNull), new AlgorithmIdentifier(PKCSObjectIdentifiers.R, new AlgorithmIdentifier(DigestFactory.b(((MGF1ParameterSpec) this.a.getMGFParameters()).getDigestAlgorithm()), dERNull)), new AlgorithmIdentifier(PKCSObjectIdentifiers.S, new DEROctetString(((PSource.PSpecified) this.a.getPSource()).getValue()))).getEncoded("DER");
            } catch (IOException e) {
                throw new RuntimeException("Error encoding OAEPParameters");
            }
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String format) {
            if (a(format) || format.equalsIgnoreCase("X.509")) {
                return engineGetEncoded();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec b(Class paramSpec) {
            if (paramSpec == OAEPParameterSpec.class || paramSpec == AlgorithmParameterSpec.class) {
                return this.a;
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to OAEP parameters object.");
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (paramSpec instanceof OAEPParameterSpec) {
                this.a = (OAEPParameterSpec) paramSpec;
                return;
            }
            throw new InvalidParameterSpecException("OAEPParameterSpec required to initialise an OAEP algorithm parameters object");
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params) {
            try {
                RSAESOAEPparams oaepP = RSAESOAEPparams.f(params);
                if (oaepP.g().e().equals(PKCSObjectIdentifiers.R)) {
                    this.a = new OAEPParameterSpec(MessageDigestUtils.a(oaepP.e().e()), OAEPParameterSpec.DEFAULT.getMGFAlgorithm(), new MGF1ParameterSpec(MessageDigestUtils.a(AlgorithmIdentifier.f(oaepP.g().h()).e())), new PSource.PSpecified(ASN1OctetString.o(oaepP.h().h()).q()));
                    return;
                }
                throw new IOException("unknown mask generation function: " + oaepP.g().e());
            } catch (ClassCastException e) {
                throw new IOException("Not a valid OAEP Parameter encoding.");
            } catch (ArrayIndexOutOfBoundsException e2) {
                throw new IOException("Not a valid OAEP Parameter encoding.");
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params, String format) {
            if (format.equalsIgnoreCase("X.509") || format.equalsIgnoreCase("ASN.1")) {
                engineInit(params);
                return;
            }
            throw new IOException("Unknown parameter format " + format);
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "OAEP Parameters";
        }
    }

    public static class PSS extends AlgorithmParametersSpi {
        PSSParameterSpec a;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            PSSParameterSpec pssSpec = this.a;
            ASN1ObjectIdentifier b = DigestFactory.b(pssSpec.getDigestAlgorithm());
            DERNull dERNull = DERNull.c;
            return new RSASSAPSSparams(new AlgorithmIdentifier(b, dERNull), new AlgorithmIdentifier(PKCSObjectIdentifiers.R, new AlgorithmIdentifier(DigestFactory.b(((MGF1ParameterSpec) pssSpec.getMGFParameters()).getDigestAlgorithm()), dERNull)), new ASN1Integer((long) pssSpec.getSaltLength()), new ASN1Integer((long) pssSpec.getTrailerField())).getEncoded("DER");
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String format) {
            if (format.equalsIgnoreCase("X.509") || format.equalsIgnoreCase("ASN.1")) {
                return engineGetEncoded();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec b(Class paramSpec) {
            PSSParameterSpec pSSParameterSpec;
            if (paramSpec == PSSParameterSpec.class && (pSSParameterSpec = this.a) != null) {
                return pSSParameterSpec;
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PSS parameters object.");
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (paramSpec instanceof PSSParameterSpec) {
                this.a = (PSSParameterSpec) paramSpec;
                return;
            }
            throw new InvalidParameterSpecException("PSSParameterSpec required to initialise an PSS algorithm parameters object");
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params) {
            try {
                RSASSAPSSparams pssP = RSASSAPSSparams.f(params);
                if (pssP.g().e().equals(PKCSObjectIdentifiers.R)) {
                    this.a = new PSSParameterSpec(MessageDigestUtils.a(pssP.e().e()), PSSParameterSpec.DEFAULT.getMGFAlgorithm(), new MGF1ParameterSpec(MessageDigestUtils.a(AlgorithmIdentifier.f(pssP.g().h()).e())), pssP.h().intValue(), pssP.i().intValue());
                    return;
                }
                throw new IOException("unknown mask generation function: " + pssP.g().e());
            } catch (ClassCastException e) {
                throw new IOException("Not a valid PSS Parameter encoding.");
            } catch (ArrayIndexOutOfBoundsException e2) {
                throw new IOException("Not a valid PSS Parameter encoding.");
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params, String format) {
            if (a(format) || format.equalsIgnoreCase("X.509")) {
                engineInit(params);
                return;
            }
            throw new IOException("Unknown parameter format " + format);
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "PSS Parameters";
        }
    }
}
