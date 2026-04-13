package org.spongycastle.asn1.x500.style;

import io.netty.util.internal.StringUtil;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.X500NameStyle;

public class RFC4519Style extends AbstractX500NameStyle {
    public static final ASN1ObjectIdentifier A;
    public static final ASN1ObjectIdentifier B;
    public static final ASN1ObjectIdentifier C;
    public static final ASN1ObjectIdentifier D;
    public static final ASN1ObjectIdentifier E;
    public static final ASN1ObjectIdentifier F;
    public static final ASN1ObjectIdentifier G;
    public static final ASN1ObjectIdentifier H;
    public static final ASN1ObjectIdentifier I;
    public static final ASN1ObjectIdentifier J;
    public static final ASN1ObjectIdentifier K;
    public static final ASN1ObjectIdentifier L;
    public static final ASN1ObjectIdentifier M;
    public static final ASN1ObjectIdentifier N;
    public static final ASN1ObjectIdentifier O;
    public static final ASN1ObjectIdentifier P;
    public static final ASN1ObjectIdentifier Q;
    private static final Hashtable R;
    private static final Hashtable S;
    public static final X500NameStyle T = new RFC4519Style();
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
    protected final Hashtable U = AbstractX500NameStyle.h(S);
    protected final Hashtable V = AbstractX500NameStyle.h(R);

    static {
        ASN1ObjectIdentifier v2 = new ASN1ObjectIdentifier("2.5.4.15").v();
        a = v2;
        ASN1ObjectIdentifier v3 = new ASN1ObjectIdentifier("2.5.4.6").v();
        b = v3;
        ASN1ObjectIdentifier v4 = new ASN1ObjectIdentifier("2.5.4.3").v();
        c = v4;
        ASN1ObjectIdentifier v5 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25").v();
        d = v5;
        ASN1ObjectIdentifier v6 = new ASN1ObjectIdentifier("2.5.4.13").v();
        e = v6;
        ASN1ObjectIdentifier v7 = new ASN1ObjectIdentifier("2.5.4.27").v();
        f = v7;
        ASN1ObjectIdentifier v8 = new ASN1ObjectIdentifier("2.5.4.49").v();
        g = v8;
        ASN1ObjectIdentifier v9 = new ASN1ObjectIdentifier("2.5.4.46").v();
        h = v9;
        ASN1ObjectIdentifier v10 = new ASN1ObjectIdentifier("2.5.4.47").v();
        i = v10;
        ASN1ObjectIdentifier v11 = new ASN1ObjectIdentifier("2.5.4.23").v();
        j = v11;
        ASN1ObjectIdentifier v12 = new ASN1ObjectIdentifier("2.5.4.44").v();
        k = v12;
        ASN1ObjectIdentifier v13 = new ASN1ObjectIdentifier("2.5.4.42").v();
        l = v13;
        ASN1ObjectIdentifier v14 = new ASN1ObjectIdentifier("2.5.4.51").v();
        m = v14;
        ASN1ObjectIdentifier v15 = new ASN1ObjectIdentifier("2.5.4.43").v();
        n = v15;
        ASN1ObjectIdentifier v16 = new ASN1ObjectIdentifier("2.5.4.25").v();
        o = v16;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = v16;
        ASN1ObjectIdentifier v17 = new ASN1ObjectIdentifier("2.5.4.7").v();
        p = v17;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = v17;
        ASN1ObjectIdentifier v18 = new ASN1ObjectIdentifier("2.5.4.31").v();
        q = v18;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = v18;
        ASN1ObjectIdentifier v19 = new ASN1ObjectIdentifier("2.5.4.41").v();
        r = v19;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = v19;
        ASN1ObjectIdentifier v20 = new ASN1ObjectIdentifier("2.5.4.10").v();
        s = v20;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = v20;
        ASN1ObjectIdentifier v21 = new ASN1ObjectIdentifier("2.5.4.11").v();
        t = v21;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = v21;
        ASN1ObjectIdentifier v22 = new ASN1ObjectIdentifier("2.5.4.32").v();
        u = v22;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = v22;
        ASN1ObjectIdentifier v23 = new ASN1ObjectIdentifier("2.5.4.19").v();
        v = v23;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = v23;
        ASN1ObjectIdentifier v24 = new ASN1ObjectIdentifier("2.5.4.16").v();
        w = v24;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = v24;
        ASN1ObjectIdentifier v25 = new ASN1ObjectIdentifier("2.5.4.17").v();
        x = v25;
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = v25;
        ASN1ObjectIdentifier v26 = new ASN1ObjectIdentifier("2.5.4.18").v();
        y = v26;
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = v26;
        ASN1ObjectIdentifier v27 = new ASN1ObjectIdentifier("2.5.4.28").v();
        z = v27;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = v27;
        ASN1ObjectIdentifier v28 = new ASN1ObjectIdentifier("2.5.4.26").v();
        A = v28;
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = v28;
        ASN1ObjectIdentifier v29 = new ASN1ObjectIdentifier("2.5.4.33").v();
        B = v29;
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = v29;
        ASN1ObjectIdentifier v30 = new ASN1ObjectIdentifier("2.5.4.14").v();
        C = v30;
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = v30;
        ASN1ObjectIdentifier v31 = new ASN1ObjectIdentifier("2.5.4.34").v();
        D = v31;
        ASN1ObjectIdentifier aSN1ObjectIdentifier16 = v31;
        ASN1ObjectIdentifier v32 = new ASN1ObjectIdentifier("2.5.4.5").v();
        E = v32;
        ASN1ObjectIdentifier aSN1ObjectIdentifier17 = v32;
        ASN1ObjectIdentifier v33 = new ASN1ObjectIdentifier("2.5.4.4").v();
        F = v33;
        ASN1ObjectIdentifier aSN1ObjectIdentifier18 = v33;
        ASN1ObjectIdentifier v34 = new ASN1ObjectIdentifier("2.5.4.8").v();
        G = v34;
        ASN1ObjectIdentifier aSN1ObjectIdentifier19 = v34;
        ASN1ObjectIdentifier v35 = new ASN1ObjectIdentifier("2.5.4.9").v();
        H = v35;
        ASN1ObjectIdentifier aSN1ObjectIdentifier20 = v35;
        ASN1ObjectIdentifier v36 = new ASN1ObjectIdentifier("2.5.4.20").v();
        I = v36;
        ASN1ObjectIdentifier aSN1ObjectIdentifier21 = v36;
        ASN1ObjectIdentifier v37 = new ASN1ObjectIdentifier("2.5.4.22").v();
        J = v37;
        ASN1ObjectIdentifier aSN1ObjectIdentifier22 = v37;
        ASN1ObjectIdentifier v38 = new ASN1ObjectIdentifier("2.5.4.21").v();
        K = v38;
        ASN1ObjectIdentifier aSN1ObjectIdentifier23 = v38;
        ASN1ObjectIdentifier v39 = new ASN1ObjectIdentifier("2.5.4.12").v();
        L = v39;
        ASN1ObjectIdentifier aSN1ObjectIdentifier24 = v39;
        ASN1ObjectIdentifier v40 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1").v();
        M = v40;
        ASN1ObjectIdentifier aSN1ObjectIdentifier25 = v40;
        ASN1ObjectIdentifier v41 = new ASN1ObjectIdentifier("2.5.4.50").v();
        N = v41;
        ASN1ObjectIdentifier aSN1ObjectIdentifier26 = v41;
        ASN1ObjectIdentifier v42 = new ASN1ObjectIdentifier("2.5.4.35").v();
        O = v42;
        ASN1ObjectIdentifier aSN1ObjectIdentifier27 = v42;
        ASN1ObjectIdentifier v43 = new ASN1ObjectIdentifier("2.5.4.24").v();
        P = v43;
        ASN1ObjectIdentifier aSN1ObjectIdentifier28 = v43;
        ASN1ObjectIdentifier v44 = new ASN1ObjectIdentifier("2.5.4.45").v();
        Q = v44;
        Hashtable hashtable = new Hashtable();
        R = hashtable;
        ASN1ObjectIdentifier aSN1ObjectIdentifier29 = v44;
        Hashtable hashtable2 = new Hashtable();
        S = hashtable2;
        hashtable.put(v2, "businessCategory");
        hashtable.put(v3, "c");
        hashtable.put(v4, "cn");
        hashtable.put(v5, "dc");
        hashtable.put(v6, "description");
        hashtable.put(v7, "destinationIndicator");
        hashtable.put(v8, "distinguishedName");
        hashtable.put(v9, "dnQualifier");
        hashtable.put(v10, "enhancedSearchGuide");
        hashtable.put(v11, "facsimileTelephoneNumber");
        hashtable.put(v12, "generationQualifier");
        hashtable.put(v13, "givenName");
        hashtable.put(v14, "houseIdentifier");
        hashtable.put(v15, "initials");
        ASN1ObjectIdentifier aSN1ObjectIdentifier30 = v15;
        hashtable.put(aSN1ObjectIdentifier, "internationalISDNNumber");
        ASN1ObjectIdentifier aSN1ObjectIdentifier31 = aSN1ObjectIdentifier2;
        hashtable.put(aSN1ObjectIdentifier31, "l");
        ASN1ObjectIdentifier aSN1ObjectIdentifier32 = aSN1ObjectIdentifier31;
        ASN1ObjectIdentifier aSN1ObjectIdentifier33 = aSN1ObjectIdentifier3;
        hashtable.put(aSN1ObjectIdentifier33, "member");
        ASN1ObjectIdentifier aSN1ObjectIdentifier34 = aSN1ObjectIdentifier33;
        ASN1ObjectIdentifier aSN1ObjectIdentifier35 = aSN1ObjectIdentifier4;
        hashtable.put(aSN1ObjectIdentifier35, "name");
        ASN1ObjectIdentifier aSN1ObjectIdentifier36 = aSN1ObjectIdentifier35;
        ASN1ObjectIdentifier aSN1ObjectIdentifier37 = aSN1ObjectIdentifier5;
        hashtable.put(aSN1ObjectIdentifier37, "o");
        ASN1ObjectIdentifier aSN1ObjectIdentifier38 = aSN1ObjectIdentifier37;
        ASN1ObjectIdentifier aSN1ObjectIdentifier39 = aSN1ObjectIdentifier6;
        hashtable.put(aSN1ObjectIdentifier39, "ou");
        ASN1ObjectIdentifier aSN1ObjectIdentifier40 = aSN1ObjectIdentifier39;
        ASN1ObjectIdentifier aSN1ObjectIdentifier41 = aSN1ObjectIdentifier7;
        hashtable.put(aSN1ObjectIdentifier41, "owner");
        ASN1ObjectIdentifier aSN1ObjectIdentifier42 = aSN1ObjectIdentifier41;
        hashtable.put(aSN1ObjectIdentifier8, "physicalDeliveryOfficeName");
        hashtable.put(aSN1ObjectIdentifier9, "postalAddress");
        hashtable.put(aSN1ObjectIdentifier10, "postalCode");
        hashtable.put(aSN1ObjectIdentifier11, "postOfficeBox");
        hashtable.put(aSN1ObjectIdentifier12, "preferredDeliveryMethod");
        hashtable.put(aSN1ObjectIdentifier13, "registeredAddress");
        hashtable.put(aSN1ObjectIdentifier14, "roleOccupant");
        hashtable.put(aSN1ObjectIdentifier15, "searchGuide");
        hashtable.put(aSN1ObjectIdentifier16, "seeAlso");
        hashtable.put(aSN1ObjectIdentifier17, "serialNumber");
        ASN1ObjectIdentifier aSN1ObjectIdentifier43 = aSN1ObjectIdentifier18;
        hashtable.put(aSN1ObjectIdentifier43, "sn");
        ASN1ObjectIdentifier aSN1ObjectIdentifier44 = aSN1ObjectIdentifier43;
        ASN1ObjectIdentifier aSN1ObjectIdentifier45 = aSN1ObjectIdentifier19;
        hashtable.put(aSN1ObjectIdentifier45, "st");
        ASN1ObjectIdentifier aSN1ObjectIdentifier46 = aSN1ObjectIdentifier45;
        ASN1ObjectIdentifier aSN1ObjectIdentifier47 = aSN1ObjectIdentifier20;
        hashtable.put(aSN1ObjectIdentifier47, "street");
        ASN1ObjectIdentifier aSN1ObjectIdentifier48 = aSN1ObjectIdentifier47;
        hashtable.put(aSN1ObjectIdentifier21, "telephoneNumber");
        hashtable.put(aSN1ObjectIdentifier22, "teletexTerminalIdentifier");
        hashtable.put(aSN1ObjectIdentifier23, "telexNumber");
        ASN1ObjectIdentifier aSN1ObjectIdentifier49 = aSN1ObjectIdentifier24;
        hashtable.put(aSN1ObjectIdentifier49, "title");
        ASN1ObjectIdentifier aSN1ObjectIdentifier50 = aSN1ObjectIdentifier49;
        ASN1ObjectIdentifier aSN1ObjectIdentifier51 = aSN1ObjectIdentifier25;
        hashtable.put(aSN1ObjectIdentifier51, "uid");
        ASN1ObjectIdentifier aSN1ObjectIdentifier52 = aSN1ObjectIdentifier51;
        hashtable.put(aSN1ObjectIdentifier26, "uniqueMember");
        hashtable.put(aSN1ObjectIdentifier27, "userPassword");
        hashtable.put(aSN1ObjectIdentifier28, "x121Address");
        ASN1ObjectIdentifier aSN1ObjectIdentifier53 = aSN1ObjectIdentifier29;
        hashtable.put(aSN1ObjectIdentifier53, "x500UniqueIdentifier");
        Hashtable hashtable3 = hashtable2;
        hashtable3.put("businesscategory", v2);
        hashtable3.put("c", v3);
        hashtable3.put("cn", v4);
        hashtable3.put("dc", v5);
        hashtable3.put("description", v6);
        hashtable3.put("destinationindicator", v7);
        hashtable3.put("distinguishedname", v8);
        hashtable3.put("dnqualifier", v9);
        hashtable3.put("enhancedsearchguide", v10);
        hashtable3.put("facsimiletelephonenumber", v11);
        hashtable3.put("generationqualifier", v12);
        hashtable3.put("givenname", v13);
        hashtable3.put("houseidentifier", v14);
        hashtable3.put("initials", aSN1ObjectIdentifier30);
        hashtable3.put("internationalisdnnumber", aSN1ObjectIdentifier);
        hashtable3.put("l", aSN1ObjectIdentifier32);
        hashtable3.put("member", aSN1ObjectIdentifier34);
        hashtable3.put("name", aSN1ObjectIdentifier36);
        hashtable3.put("o", aSN1ObjectIdentifier38);
        hashtable3.put("ou", aSN1ObjectIdentifier40);
        hashtable3.put("owner", aSN1ObjectIdentifier42);
        hashtable3.put("physicaldeliveryofficename", aSN1ObjectIdentifier8);
        hashtable3.put("postaladdress", aSN1ObjectIdentifier9);
        hashtable3.put("postalcode", aSN1ObjectIdentifier10);
        hashtable3.put("postofficebox", aSN1ObjectIdentifier11);
        hashtable3.put("preferreddeliverymethod", aSN1ObjectIdentifier12);
        hashtable3.put("registeredaddress", aSN1ObjectIdentifier13);
        hashtable3.put("roleoccupant", aSN1ObjectIdentifier14);
        hashtable3.put("searchguide", aSN1ObjectIdentifier15);
        hashtable3.put("seealso", aSN1ObjectIdentifier16);
        hashtable3.put("serialnumber", aSN1ObjectIdentifier17);
        hashtable3.put("sn", aSN1ObjectIdentifier44);
        hashtable3.put("st", aSN1ObjectIdentifier46);
        hashtable3.put("street", aSN1ObjectIdentifier48);
        hashtable3.put("telephonenumber", aSN1ObjectIdentifier21);
        hashtable3.put("teletexterminalidentifier", aSN1ObjectIdentifier22);
        hashtable3.put("telexnumber", aSN1ObjectIdentifier23);
        hashtable3.put("title", aSN1ObjectIdentifier50);
        hashtable3.put("uid", aSN1ObjectIdentifier52);
        hashtable3.put("uniquemember", aSN1ObjectIdentifier26);
        hashtable3.put("userpassword", aSN1ObjectIdentifier27);
        hashtable3.put("x121address", aSN1ObjectIdentifier28);
        hashtable3.put("x500uniqueidentifier", aSN1ObjectIdentifier53);
    }

    protected RFC4519Style() {
    }

    /* access modifiers changed from: protected */
    public ASN1Encodable i(ASN1ObjectIdentifier oid, String value) {
        if (oid.equals(d)) {
            return new DERIA5String(value);
        }
        if (oid.equals(b) || oid.equals(E) || oid.equals(h) || oid.equals(I)) {
            return new DERPrintableString(value);
        }
        return super.i(oid, value);
    }

    public ASN1ObjectIdentifier c(String attrName) {
        return IETFUtils.g(attrName, this.U);
    }

    public RDN[] b(String dirName) {
        RDN[] tmp = IETFUtils.k(dirName, this);
        RDN[] res = new RDN[tmp.length];
        for (int i2 = 0; i2 != tmp.length; i2++) {
            res[(res.length - i2) - 1] = tmp[i2];
        }
        return res;
    }

    public String a(X500Name name) {
        StringBuffer buf = new StringBuffer();
        boolean first = true;
        RDN[] rdns = name.h();
        for (int i2 = rdns.length - 1; i2 >= 0; i2--) {
            if (first) {
                first = false;
            } else {
                buf.append(StringUtil.COMMA);
            }
            IETFUtils.a(buf, rdns[i2], this.V);
        }
        return buf.toString();
    }
}
