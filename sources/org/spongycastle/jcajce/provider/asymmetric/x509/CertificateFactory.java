package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.SignedData;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.util.io.Streams;

public class CertificateFactory extends CertificateFactorySpi {
    private static final PEMUtil a = new PEMUtil("CERTIFICATE");
    private static final PEMUtil b = new PEMUtil("CRL");
    private static final PEMUtil c = new PEMUtil("PKCS7");
    private final JcaJceHelper d = new BCJcaJceHelper();
    private ASN1Set e = null;
    private int f = 0;
    private InputStream g = null;
    private ASN1Set h = null;
    private int i = 0;
    private InputStream j = null;

    private Certificate g(ASN1InputStream dIn) {
        return e(ASN1Sequence.o(dIn.r()));
    }

    private Certificate i(InputStream in) {
        return e(a.b(in));
    }

    private Certificate e(ASN1Sequence seq) {
        if (seq == null) {
            return null;
        }
        if (seq.size() <= 1 || !(seq.r(0) instanceof ASN1ObjectIdentifier) || !seq.r(0).equals(PKCSObjectIdentifiers.B0)) {
            return new X509CertificateObject(this.d, org.spongycastle.asn1.x509.Certificate.f(seq));
        }
        this.e = SignedData.g(ASN1Sequence.p((ASN1TaggedObject) seq.r(1), true)).f();
        return d();
    }

    private Certificate d() {
        if (this.e == null) {
            return null;
        }
        while (this.f < this.e.size()) {
            ASN1Set aSN1Set = this.e;
            int i2 = this.f;
            this.f = i2 + 1;
            Object obj = aSN1Set.s(i2);
            if (obj instanceof ASN1Sequence) {
                return new X509CertificateObject(this.d, org.spongycastle.asn1.x509.Certificate.f(obj));
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public CRL a(CertificateList c2) {
        return new X509CRLObject(this.d, c2);
    }

    private CRL h(InputStream in) {
        return c(b.b(in));
    }

    private CRL f(ASN1InputStream aIn) {
        return c(ASN1Sequence.o(aIn.r()));
    }

    private CRL c(ASN1Sequence seq) {
        if (seq == null) {
            return null;
        }
        if (seq.size() <= 1 || !(seq.r(0) instanceof ASN1ObjectIdentifier) || !seq.r(0).equals(PKCSObjectIdentifiers.B0)) {
            return a(CertificateList.e(seq));
        }
        this.h = SignedData.g(ASN1Sequence.p((ASN1TaggedObject) seq.r(1), true)).e();
        return b();
    }

    private CRL b() {
        ASN1Set aSN1Set = this.h;
        if (aSN1Set == null || this.i >= aSN1Set.size()) {
            return null;
        }
        ASN1Set aSN1Set2 = this.h;
        int i2 = this.i;
        this.i = i2 + 1;
        return a(CertificateList.e(aSN1Set2.s(i2)));
    }

    public Certificate engineGenerateCertificate(InputStream in) {
        InputStream pis;
        InputStream inputStream = this.g;
        if (inputStream == null) {
            this.g = in;
            this.e = null;
            this.f = 0;
        } else if (inputStream != in) {
            this.g = in;
            this.e = null;
            this.f = 0;
        }
        try {
            ASN1Set aSN1Set = this.e;
            if (aSN1Set == null) {
                if (in.markSupported()) {
                    pis = in;
                } else {
                    pis = new ByteArrayInputStream(Streams.b(in));
                }
                pis.mark(1);
                int tag = pis.read();
                if (tag == -1) {
                    return null;
                }
                pis.reset();
                if (tag != 48) {
                    return i(pis);
                }
                return g(new ASN1InputStream(pis));
            } else if (this.f != aSN1Set.size()) {
                return d();
            } else {
                this.e = null;
                this.f = 0;
                return null;
            }
        } catch (Exception e2) {
            throw new ExCertificateException("parsing issue: " + e2.getMessage(), e2);
        }
    }

    public Collection engineGenerateCertificates(InputStream inStream) {
        BufferedInputStream in = new BufferedInputStream(inStream);
        List certs = new ArrayList();
        while (true) {
            Certificate engineGenerateCertificate = engineGenerateCertificate(in);
            Certificate cert = engineGenerateCertificate;
            if (engineGenerateCertificate == null) {
                return certs;
            }
            certs.add(cert);
        }
    }

    public CRL engineGenerateCRL(InputStream in) {
        InputStream pis;
        InputStream inputStream = this.j;
        if (inputStream == null) {
            this.j = in;
            this.h = null;
            this.i = 0;
        } else if (inputStream != in) {
            this.j = in;
            this.h = null;
            this.i = 0;
        }
        try {
            ASN1Set aSN1Set = this.h;
            if (aSN1Set == null) {
                if (in.markSupported()) {
                    pis = in;
                } else {
                    pis = new ByteArrayInputStream(Streams.b(in));
                }
                pis.mark(1);
                int tag = pis.read();
                if (tag == -1) {
                    return null;
                }
                pis.reset();
                if (tag != 48) {
                    return h(pis);
                }
                return f(new ASN1InputStream(pis, true));
            } else if (this.i != aSN1Set.size()) {
                return b();
            } else {
                this.h = null;
                this.i = 0;
                return null;
            }
        } catch (CRLException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new CRLException(e3.toString());
        }
    }

    public Collection engineGenerateCRLs(InputStream inStream) {
        List crls = new ArrayList();
        BufferedInputStream in = new BufferedInputStream(inStream);
        while (true) {
            CRL engineGenerateCRL = engineGenerateCRL(in);
            CRL crl = engineGenerateCRL;
            if (engineGenerateCRL == null) {
                return crls;
            }
            crls.add(crl);
        }
    }

    public Iterator engineGetCertPathEncodings() {
        return PKIXCertPath.certPathEncodings.iterator();
    }

    public CertPath engineGenerateCertPath(InputStream inStream) {
        return engineGenerateCertPath(inStream, "PkiPath");
    }

    public CertPath engineGenerateCertPath(InputStream inStream, String encoding) {
        return new PKIXCertPath(inStream, encoding);
    }

    public CertPath engineGenerateCertPath(List certificates) {
        for (Object obj : certificates) {
            if (obj != null && !(obj instanceof X509Certificate)) {
                throw new CertificateException("list contains non X509Certificate object while creating CertPath\n" + obj.toString());
            }
        }
        return new PKIXCertPath(certificates);
    }

    public class ExCertificateException extends CertificateException {
        private Throwable cause;

        public ExCertificateException(Throwable cause2) {
            this.cause = cause2;
        }

        public ExCertificateException(String msg, Throwable cause2) {
            super(msg);
            this.cause = cause2;
        }

        public Throwable getCause() {
            return this.cause;
        }
    }
}
