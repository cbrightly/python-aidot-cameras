package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.pkcs.ContentInfo;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.SignedData;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.util.io.pem.PemObject;
import org.spongycastle.util.io.pem.PemWriter;

public class PKIXCertPath extends CertPath {
    static final List certPathEncodings;
    private List certificates;
    private final JcaJceHelper helper;

    static {
        List encodings = new ArrayList();
        encodings.add("PkiPath");
        encodings.add("PEM");
        encodings.add("PKCS7");
        certPathEncodings = Collections.unmodifiableList(encodings);
    }

    private List a(List certs) {
        if (certs.size() < 2) {
            return certs;
        }
        X500Principal issuer = ((X509Certificate) certs.get(0)).getIssuerX500Principal();
        boolean okay = true;
        int i = 1;
        while (true) {
            if (i != certs.size()) {
                if (!issuer.equals(((X509Certificate) certs.get(i)).getSubjectX500Principal())) {
                    okay = false;
                    break;
                }
                issuer = ((X509Certificate) certs.get(i)).getIssuerX500Principal();
                i++;
            } else {
                break;
            }
        }
        if (okay) {
            return certs;
        }
        List retList = new ArrayList(certs.size());
        List orig = new ArrayList(certs);
        for (int i2 = 0; i2 < certs.size(); i2++) {
            X509Certificate cert = (X509Certificate) certs.get(i2);
            boolean found = false;
            X500Principal subject = cert.getSubjectX500Principal();
            int j = 0;
            while (true) {
                if (j == certs.size()) {
                    break;
                } else if (((X509Certificate) certs.get(j)).getIssuerX500Principal().equals(subject)) {
                    found = true;
                    break;
                } else {
                    j++;
                }
            }
            if (!found) {
                retList.add(cert);
                certs.remove(i2);
            }
        }
        if (retList.size() > 1) {
            return orig;
        }
        for (int i3 = 0; i3 != retList.size(); i3++) {
            X500Principal issuer2 = ((X509Certificate) retList.get(i3)).getIssuerX500Principal();
            int j2 = 0;
            while (true) {
                if (j2 >= certs.size()) {
                    break;
                }
                X509Certificate c = (X509Certificate) certs.get(j2);
                if (issuer2.equals(c.getSubjectX500Principal())) {
                    retList.add(c);
                    certs.remove(j2);
                    break;
                }
                j2++;
            }
        }
        if (certs.size() > 0) {
            return orig;
        }
        return retList;
    }

    PKIXCertPath(List certificates2) {
        super("X.509");
        this.helper = new BCJcaJceHelper();
        this.certificates = a(new ArrayList(certificates2));
    }

    PKIXCertPath(InputStream inStream, String encoding) {
        super("X.509");
        BCJcaJceHelper bCJcaJceHelper = new BCJcaJceHelper();
        this.helper = bCJcaJceHelper;
        try {
            if (!encoding.equalsIgnoreCase("PkiPath")) {
                if (!encoding.equalsIgnoreCase("PKCS7")) {
                    if (!encoding.equalsIgnoreCase("PEM")) {
                        throw new CertificateException("unsupported encoding: " + encoding);
                    }
                }
                InputStream inStream2 = new BufferedInputStream(inStream);
                this.certificates = new ArrayList();
                CertificateFactory certFactory = bCJcaJceHelper.b("X.509");
                while (true) {
                    Certificate generateCertificate = certFactory.generateCertificate(inStream2);
                    Certificate cert = generateCertificate;
                    if (generateCertificate == null) {
                        break;
                    }
                    this.certificates.add(cert);
                }
            } else {
                ASN1Primitive derObject = new ASN1InputStream(inStream).r();
                if (derObject instanceof ASN1Sequence) {
                    Enumeration e = ((ASN1Sequence) derObject).s();
                    this.certificates = new ArrayList();
                    CertificateFactory certFactory2 = bCJcaJceHelper.b("X.509");
                    while (e.hasMoreElements()) {
                        this.certificates.add(0, certFactory2.generateCertificate(new ByteArrayInputStream(((ASN1Encodable) e.nextElement()).toASN1Primitive().getEncoded("DER"))));
                    }
                } else {
                    throw new CertificateException("input stream does not contain a ASN1 SEQUENCE while reading PkiPath encoded data to load CertPath");
                }
            }
            this.certificates = a(this.certificates);
        } catch (IOException ex) {
            throw new CertificateException("IOException throw while decoding CertPath:\n" + ex.toString());
        } catch (NoSuchProviderException ex2) {
            throw new CertificateException("BouncyCastle provider not found while trying to get a CertificateFactory:\n" + ex2.toString());
        }
    }

    public Iterator getEncodings() {
        return certPathEncodings.iterator();
    }

    public byte[] getEncoded() {
        Iterator iter = getEncodings();
        if (!iter.hasNext()) {
            return null;
        }
        Object enc = iter.next();
        if (enc instanceof String) {
            return getEncoded((String) enc);
        }
        return null;
    }

    public byte[] getEncoded(String encoding) {
        if (encoding.equalsIgnoreCase("PkiPath")) {
            ASN1EncodableVector v = new ASN1EncodableVector();
            List list = this.certificates;
            ListIterator iter = list.listIterator(list.size());
            while (iter.hasPrevious()) {
                v.a(b((X509Certificate) iter.previous()));
            }
            return c(new DERSequence(v));
        } else if (encoding.equalsIgnoreCase("PKCS7")) {
            ContentInfo encInfo = new ContentInfo(PKCSObjectIdentifiers.A0, (ASN1Encodable) null);
            ASN1EncodableVector v2 = new ASN1EncodableVector();
            for (int i = 0; i != this.certificates.size(); i++) {
                v2.a(b((X509Certificate) this.certificates.get(i)));
            }
            return c(new ContentInfo(PKCSObjectIdentifiers.B0, new SignedData(new ASN1Integer(1), new DERSet(), encInfo, new DERSet(v2), (ASN1Set) null, new DERSet())));
        } else if (encoding.equalsIgnoreCase("PEM")) {
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            PemWriter pWrt = new PemWriter(new OutputStreamWriter(bOut));
            int i2 = 0;
            while (i2 != this.certificates.size()) {
                try {
                    pWrt.c(new PemObject("CERTIFICATE", ((X509Certificate) this.certificates.get(i2)).getEncoded()));
                    i2++;
                } catch (Exception e) {
                    throw new CertificateEncodingException("can't encode certificate for PEM encoded path");
                }
            }
            pWrt.close();
            return bOut.toByteArray();
        } else {
            throw new CertificateEncodingException("unsupported encoding: " + encoding);
        }
    }

    public List getCertificates() {
        return Collections.unmodifiableList(new ArrayList(this.certificates));
    }

    private ASN1Primitive b(X509Certificate cert) {
        try {
            return new ASN1InputStream(cert.getEncoded()).r();
        } catch (Exception e) {
            throw new CertificateEncodingException("Exception while encoding certificate: " + e.toString());
        }
    }

    private byte[] c(ASN1Encodable obj) {
        try {
            return obj.toASN1Primitive().getEncoded("DER");
        } catch (IOException e) {
            throw new CertificateEncodingException("Exception thrown: " + e);
        }
    }
}
