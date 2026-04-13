package org.spongycastle.asn1.x500.style;

import androidx.exifinterface.media.ExifInterface;
import io.netty.util.internal.StringUtil;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.X500NameStyle;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;

public class BCStyle extends AbstractX500NameStyle {
    public static final ASN1ObjectIdentifier A = new ASN1ObjectIdentifier("2.5.4.54").v();
    public static final ASN1ObjectIdentifier B;
    public static final ASN1ObjectIdentifier C;
    public static final ASN1ObjectIdentifier D;
    public static final ASN1ObjectIdentifier E;
    public static final ASN1ObjectIdentifier F;
    public static final ASN1ObjectIdentifier G;
    public static final ASN1ObjectIdentifier H;
    public static final ASN1ObjectIdentifier I;
    public static final ASN1ObjectIdentifier J;
    private static final Hashtable K;
    private static final Hashtable L;
    public static final X500NameStyle M = new BCStyle();
    public static final ASN1ObjectIdentifier a;
    public static final ASN1ObjectIdentifier b;
    public static final ASN1ObjectIdentifier c;
    public static final ASN1ObjectIdentifier d;
    public static final ASN1ObjectIdentifier e;
    public static final ASN1ObjectIdentifier f;
    public static final ASN1ObjectIdentifier g;
    public static final ASN1ObjectIdentifier h;
    public static final ASN1ObjectIdentifier i;
    public static final ASN1ObjectIdentifier j;
    public static final ASN1ObjectIdentifier k;
    public static final ASN1ObjectIdentifier l;
    public static final ASN1ObjectIdentifier m;
    public static final ASN1ObjectIdentifier n;
    public static final ASN1ObjectIdentifier o;
    public static final ASN1ObjectIdentifier p;
    public static final ASN1ObjectIdentifier q;
    public static final ASN1ObjectIdentifier r;
    public static final ASN1ObjectIdentifier s;
    public static final ASN1ObjectIdentifier t;
    public static final ASN1ObjectIdentifier u;
    public static final ASN1ObjectIdentifier v;
    public static final ASN1ObjectIdentifier w;
    public static final ASN1ObjectIdentifier x;
    public static final ASN1ObjectIdentifier y;
    public static final ASN1ObjectIdentifier z;
    protected final Hashtable N = AbstractX500NameStyle.h(L);
    protected final Hashtable O = AbstractX500NameStyle.h(K);

    static {
        ASN1ObjectIdentifier v2 = new ASN1ObjectIdentifier("2.5.4.6").v();
        a = v2;
        ASN1ObjectIdentifier v3 = new ASN1ObjectIdentifier("2.5.4.10").v();
        b = v3;
        ASN1ObjectIdentifier v4 = new ASN1ObjectIdentifier("2.5.4.11").v();
        c = v4;
        ASN1ObjectIdentifier v5 = new ASN1ObjectIdentifier("2.5.4.12").v();
        d = v5;
        ASN1ObjectIdentifier v6 = new ASN1ObjectIdentifier("2.5.4.3").v();
        e = v6;
        ASN1ObjectIdentifier v7 = new ASN1ObjectIdentifier("2.5.4.5").v();
        f = v7;
        ASN1ObjectIdentifier v8 = new ASN1ObjectIdentifier("2.5.4.9").v();
        g = v8;
        h = v7;
        ASN1ObjectIdentifier v9 = new ASN1ObjectIdentifier("2.5.4.7").v();
        i = v9;
        ASN1ObjectIdentifier v10 = new ASN1ObjectIdentifier("2.5.4.8").v();
        j = v10;
        ASN1ObjectIdentifier v11 = new ASN1ObjectIdentifier("2.5.4.4").v();
        k = v11;
        ASN1ObjectIdentifier v12 = new ASN1ObjectIdentifier("2.5.4.42").v();
        l = v12;
        ASN1ObjectIdentifier v13 = new ASN1ObjectIdentifier("2.5.4.43").v();
        m = v13;
        ASN1ObjectIdentifier v14 = new ASN1ObjectIdentifier("2.5.4.44").v();
        n = v14;
        ASN1ObjectIdentifier v15 = new ASN1ObjectIdentifier("2.5.4.45").v();
        o = v15;
        ASN1ObjectIdentifier v16 = new ASN1ObjectIdentifier("2.5.4.15").v();
        p = v16;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = v16;
        ASN1ObjectIdentifier v17 = new ASN1ObjectIdentifier("2.5.4.17").v();
        q = v17;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = v17;
        ASN1ObjectIdentifier v18 = new ASN1ObjectIdentifier("2.5.4.46").v();
        r = v18;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = v18;
        ASN1ObjectIdentifier v19 = new ASN1ObjectIdentifier("2.5.4.65").v();
        s = v19;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = v19;
        ASN1ObjectIdentifier v20 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1").v();
        t = v20;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = v20;
        ASN1ObjectIdentifier v21 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2").v();
        u = v21;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = v21;
        ASN1ObjectIdentifier v22 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3").v();
        v = v22;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = v22;
        ASN1ObjectIdentifier v23 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4").v();
        w = v23;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = v23;
        ASN1ObjectIdentifier v24 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5").v();
        x = v24;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = v24;
        ASN1ObjectIdentifier v25 = new ASN1ObjectIdentifier("1.3.36.8.3.14").v();
        y = v25;
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = v25;
        ASN1ObjectIdentifier v26 = new ASN1ObjectIdentifier("2.5.4.16").v();
        z = v26;
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = v26;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = X509ObjectIdentifiers.I2;
        B = aSN1ObjectIdentifier12;
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = X509ObjectIdentifiers.J2;
        C = aSN1ObjectIdentifier13;
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = aSN1ObjectIdentifier13;
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = X509ObjectIdentifiers.K2;
        D = aSN1ObjectIdentifier15;
        ASN1ObjectIdentifier aSN1ObjectIdentifier16 = aSN1ObjectIdentifier15;
        ASN1ObjectIdentifier aSN1ObjectIdentifier17 = PKCSObjectIdentifiers.H0;
        E = aSN1ObjectIdentifier17;
        ASN1ObjectIdentifier aSN1ObjectIdentifier18 = aSN1ObjectIdentifier12;
        ASN1ObjectIdentifier aSN1ObjectIdentifier19 = PKCSObjectIdentifiers.I0;
        F = aSN1ObjectIdentifier19;
        ASN1ObjectIdentifier aSN1ObjectIdentifier20 = v15;
        ASN1ObjectIdentifier aSN1ObjectIdentifier21 = PKCSObjectIdentifiers.O0;
        G = aSN1ObjectIdentifier21;
        H = aSN1ObjectIdentifier17;
        ASN1ObjectIdentifier aSN1ObjectIdentifier22 = aSN1ObjectIdentifier19;
        ASN1ObjectIdentifier aSN1ObjectIdentifier23 = aSN1ObjectIdentifier21;
        ASN1ObjectIdentifier aSN1ObjectIdentifier24 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
        I = aSN1ObjectIdentifier24;
        ASN1ObjectIdentifier aSN1ObjectIdentifier25 = v14;
        ASN1ObjectIdentifier aSN1ObjectIdentifier26 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
        J = aSN1ObjectIdentifier26;
        Hashtable hashtable = new Hashtable();
        K = hashtable;
        ASN1ObjectIdentifier aSN1ObjectIdentifier27 = v13;
        Hashtable hashtable2 = new Hashtable();
        L = hashtable2;
        hashtable.put(v2, "C");
        hashtable.put(v3, "O");
        hashtable.put(v5, ExifInterface.GPS_DIRECTION_TRUE);
        hashtable.put(v4, "OU");
        hashtable.put(v6, "CN");
        hashtable.put(v9, "L");
        hashtable.put(v10, "ST");
        hashtable.put(v7, "SERIALNUMBER");
        hashtable.put(aSN1ObjectIdentifier17, ExifInterface.LONGITUDE_EAST);
        hashtable.put(aSN1ObjectIdentifier24, "DC");
        hashtable.put(aSN1ObjectIdentifier26, "UID");
        hashtable.put(v8, "STREET");
        hashtable.put(v11, "SURNAME");
        hashtable.put(v12, "GIVENNAME");
        ASN1ObjectIdentifier aSN1ObjectIdentifier28 = v12;
        hashtable.put(aSN1ObjectIdentifier27, "INITIALS");
        hashtable.put(aSN1ObjectIdentifier25, "GENERATION");
        hashtable.put(aSN1ObjectIdentifier23, "unstructuredAddress");
        hashtable.put(aSN1ObjectIdentifier22, "unstructuredName");
        hashtable.put(aSN1ObjectIdentifier20, "UniqueIdentifier");
        hashtable.put(aSN1ObjectIdentifier3, "DN");
        hashtable.put(aSN1ObjectIdentifier4, "Pseudonym");
        hashtable.put(aSN1ObjectIdentifier11, "PostalAddress");
        hashtable.put(aSN1ObjectIdentifier10, "NameAtBirth");
        hashtable.put(aSN1ObjectIdentifier8, "CountryOfCitizenship");
        hashtable.put(aSN1ObjectIdentifier9, "CountryOfResidence");
        hashtable.put(aSN1ObjectIdentifier7, "Gender");
        hashtable.put(aSN1ObjectIdentifier6, "PlaceOfBirth");
        hashtable.put(aSN1ObjectIdentifier5, "DateOfBirth");
        hashtable.put(aSN1ObjectIdentifier2, "PostalCode");
        hashtable.put(aSN1ObjectIdentifier, "BusinessCategory");
        hashtable.put(aSN1ObjectIdentifier18, "TelephoneNumber");
        hashtable.put(aSN1ObjectIdentifier14, "Name");
        ASN1ObjectIdentifier aSN1ObjectIdentifier29 = aSN1ObjectIdentifier16;
        hashtable.put(aSN1ObjectIdentifier29, "organizationIdentifier");
        Hashtable hashtable3 = hashtable2;
        hashtable3.put("c", v2);
        hashtable3.put("o", v3);
        hashtable3.put("t", v5);
        hashtable3.put("ou", v4);
        hashtable3.put("cn", v6);
        hashtable3.put("l", v9);
        hashtable3.put("st", v10);
        hashtable3.put("sn", v7);
        hashtable3.put("serialnumber", v7);
        hashtable3.put("street", v8);
        hashtable3.put("emailaddress", aSN1ObjectIdentifier17);
        hashtable3.put("dc", aSN1ObjectIdentifier24);
        hashtable3.put("e", aSN1ObjectIdentifier17);
        hashtable3.put("uid", aSN1ObjectIdentifier26);
        hashtable3.put("surname", v11);
        hashtable3.put("givenname", aSN1ObjectIdentifier28);
        hashtable3.put("initials", aSN1ObjectIdentifier27);
        hashtable3.put("generation", aSN1ObjectIdentifier25);
        hashtable3.put("unstructuredaddress", aSN1ObjectIdentifier23);
        hashtable3.put("unstructuredname", aSN1ObjectIdentifier22);
        hashtable3.put("uniqueidentifier", aSN1ObjectIdentifier20);
        hashtable3.put("dn", aSN1ObjectIdentifier3);
        hashtable3.put("pseudonym", aSN1ObjectIdentifier4);
        hashtable3.put("postaladdress", aSN1ObjectIdentifier11);
        hashtable3.put("nameofbirth", aSN1ObjectIdentifier10);
        hashtable3.put("countryofcitizenship", aSN1ObjectIdentifier8);
        hashtable3.put("countryofresidence", aSN1ObjectIdentifier9);
        hashtable3.put("gender", aSN1ObjectIdentifier7);
        hashtable3.put("placeofbirth", aSN1ObjectIdentifier6);
        hashtable3.put("dateofbirth", aSN1ObjectIdentifier5);
        hashtable3.put("postalcode", aSN1ObjectIdentifier2);
        hashtable3.put("businesscategory", aSN1ObjectIdentifier);
        hashtable3.put("telephonenumber", aSN1ObjectIdentifier18);
        hashtable3.put("name", aSN1ObjectIdentifier14);
        hashtable3.put("organizationidentifier", aSN1ObjectIdentifier29);
    }

    protected BCStyle() {
    }

    /* access modifiers changed from: protected */
    public ASN1Encodable i(ASN1ObjectIdentifier oid, String value) {
        if (oid.equals(E) || oid.equals(I)) {
            return new DERIA5String(value);
        }
        if (oid.equals(t)) {
            return new ASN1GeneralizedTime(value);
        }
        if (oid.equals(a) || oid.equals(f) || oid.equals(r) || oid.equals(B)) {
            return new DERPrintableString(value);
        }
        return super.i(oid, value);
    }

    public ASN1ObjectIdentifier c(String attrName) {
        return IETFUtils.g(attrName, this.N);
    }

    public RDN[] b(String dirName) {
        return IETFUtils.k(dirName, this);
    }

    public String a(X500Name name) {
        StringBuffer buf = new StringBuffer();
        boolean first = true;
        RDN[] rdns = name.h();
        for (RDN a2 : rdns) {
            if (first) {
                first = false;
            } else {
                buf.append(StringUtil.COMMA);
            }
            IETFUtils.a(buf, a2, this.O);
        }
        return buf.toString();
    }
}
