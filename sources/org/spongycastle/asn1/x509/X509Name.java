package org.spongycastle.asn1.x509;

import androidx.exifinterface.media.ExifInterface;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.DERUniversalString;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class X509Name extends ASN1Object {
    public static final ASN1ObjectIdentifier A4;
    public static final ASN1ObjectIdentifier B4;
    public static final ASN1ObjectIdentifier C4;
    public static final ASN1ObjectIdentifier D4;
    public static final ASN1ObjectIdentifier E4;
    public static final ASN1ObjectIdentifier F4;
    public static final ASN1ObjectIdentifier G4;
    public static final ASN1ObjectIdentifier H4;
    public static final ASN1ObjectIdentifier I4;
    public static final ASN1ObjectIdentifier J4;
    public static final ASN1ObjectIdentifier K4;
    public static final ASN1ObjectIdentifier L4 = new ASN1ObjectIdentifier("2.5.4.54");
    public static final ASN1ObjectIdentifier M4;
    public static final ASN1ObjectIdentifier N4;
    public static final ASN1ObjectIdentifier O4;
    public static final ASN1ObjectIdentifier P4;
    public static final ASN1ObjectIdentifier Q4;
    public static final ASN1ObjectIdentifier R4;
    public static final ASN1ObjectIdentifier S4;
    public static final ASN1ObjectIdentifier T4;
    public static boolean U4 = false;
    public static final Hashtable V4;
    public static final Hashtable W4;
    public static final Hashtable X4;
    public static final Hashtable Y4;
    public static final Hashtable Z4;
    public static final ASN1ObjectIdentifier a1;
    public static final ASN1ObjectIdentifier a2;
    public static final Hashtable a5;
    private static final Boolean b5 = new Boolean(true);
    public static final ASN1ObjectIdentifier c;
    private static final Boolean c5 = new Boolean(false);
    public static final ASN1ObjectIdentifier d;
    public static final ASN1ObjectIdentifier f;
    public static final ASN1ObjectIdentifier p0;
    public static final ASN1ObjectIdentifier p1;
    public static final ASN1ObjectIdentifier p2;
    public static final ASN1ObjectIdentifier p3;
    public static final ASN1ObjectIdentifier p4;
    public static final ASN1ObjectIdentifier q;
    public static final ASN1ObjectIdentifier x;
    public static final ASN1ObjectIdentifier y;
    public static final ASN1ObjectIdentifier z;
    public static final ASN1ObjectIdentifier z4;
    private X509NameEntryConverter d5 = null;
    private Vector e5 = new Vector();
    private Vector f5 = new Vector();
    private Vector g5 = new Vector();
    private ASN1Sequence h5;
    private boolean i5;
    private int j5;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.5.4.6");
        c = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = new ASN1ObjectIdentifier("2.5.4.10");
        d = aSN1ObjectIdentifier2;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = new ASN1ObjectIdentifier("2.5.4.11");
        f = aSN1ObjectIdentifier3;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = new ASN1ObjectIdentifier("2.5.4.12");
        q = aSN1ObjectIdentifier4;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = new ASN1ObjectIdentifier("2.5.4.3");
        x = aSN1ObjectIdentifier5;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = new ASN1ObjectIdentifier("2.5.4.5");
        y = aSN1ObjectIdentifier6;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = new ASN1ObjectIdentifier("2.5.4.9");
        z = aSN1ObjectIdentifier7;
        p0 = aSN1ObjectIdentifier6;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = new ASN1ObjectIdentifier("2.5.4.7");
        a1 = aSN1ObjectIdentifier8;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = new ASN1ObjectIdentifier("2.5.4.8");
        p1 = aSN1ObjectIdentifier9;
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = new ASN1ObjectIdentifier("2.5.4.4");
        a2 = aSN1ObjectIdentifier10;
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = new ASN1ObjectIdentifier("2.5.4.42");
        p2 = aSN1ObjectIdentifier11;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = new ASN1ObjectIdentifier("2.5.4.43");
        p3 = aSN1ObjectIdentifier12;
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = new ASN1ObjectIdentifier("2.5.4.44");
        p4 = aSN1ObjectIdentifier13;
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = new ASN1ObjectIdentifier("2.5.4.45");
        z4 = aSN1ObjectIdentifier14;
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = new ASN1ObjectIdentifier("2.5.4.15");
        A4 = aSN1ObjectIdentifier15;
        ASN1ObjectIdentifier aSN1ObjectIdentifier16 = aSN1ObjectIdentifier15;
        ASN1ObjectIdentifier aSN1ObjectIdentifier17 = new ASN1ObjectIdentifier("2.5.4.17");
        B4 = aSN1ObjectIdentifier17;
        ASN1ObjectIdentifier aSN1ObjectIdentifier18 = aSN1ObjectIdentifier17;
        ASN1ObjectIdentifier aSN1ObjectIdentifier19 = new ASN1ObjectIdentifier("2.5.4.46");
        C4 = aSN1ObjectIdentifier19;
        ASN1ObjectIdentifier aSN1ObjectIdentifier20 = aSN1ObjectIdentifier19;
        ASN1ObjectIdentifier aSN1ObjectIdentifier21 = new ASN1ObjectIdentifier("2.5.4.65");
        D4 = aSN1ObjectIdentifier21;
        ASN1ObjectIdentifier aSN1ObjectIdentifier22 = aSN1ObjectIdentifier21;
        ASN1ObjectIdentifier aSN1ObjectIdentifier23 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1");
        E4 = aSN1ObjectIdentifier23;
        ASN1ObjectIdentifier aSN1ObjectIdentifier24 = aSN1ObjectIdentifier23;
        ASN1ObjectIdentifier aSN1ObjectIdentifier25 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2");
        F4 = aSN1ObjectIdentifier25;
        ASN1ObjectIdentifier aSN1ObjectIdentifier26 = aSN1ObjectIdentifier25;
        ASN1ObjectIdentifier aSN1ObjectIdentifier27 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3");
        G4 = aSN1ObjectIdentifier27;
        ASN1ObjectIdentifier aSN1ObjectIdentifier28 = aSN1ObjectIdentifier27;
        ASN1ObjectIdentifier aSN1ObjectIdentifier29 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4");
        H4 = aSN1ObjectIdentifier29;
        ASN1ObjectIdentifier aSN1ObjectIdentifier30 = aSN1ObjectIdentifier29;
        ASN1ObjectIdentifier aSN1ObjectIdentifier31 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5");
        I4 = aSN1ObjectIdentifier31;
        ASN1ObjectIdentifier aSN1ObjectIdentifier32 = aSN1ObjectIdentifier31;
        ASN1ObjectIdentifier aSN1ObjectIdentifier33 = new ASN1ObjectIdentifier("1.3.36.8.3.14");
        J4 = aSN1ObjectIdentifier33;
        ASN1ObjectIdentifier aSN1ObjectIdentifier34 = aSN1ObjectIdentifier33;
        ASN1ObjectIdentifier aSN1ObjectIdentifier35 = new ASN1ObjectIdentifier("2.5.4.16");
        K4 = aSN1ObjectIdentifier35;
        ASN1ObjectIdentifier aSN1ObjectIdentifier36 = aSN1ObjectIdentifier35;
        ASN1ObjectIdentifier aSN1ObjectIdentifier37 = X509ObjectIdentifiers.I2;
        M4 = aSN1ObjectIdentifier37;
        ASN1ObjectIdentifier aSN1ObjectIdentifier38 = X509ObjectIdentifiers.J2;
        N4 = aSN1ObjectIdentifier38;
        ASN1ObjectIdentifier aSN1ObjectIdentifier39 = aSN1ObjectIdentifier38;
        ASN1ObjectIdentifier aSN1ObjectIdentifier40 = PKCSObjectIdentifiers.H0;
        O4 = aSN1ObjectIdentifier40;
        ASN1ObjectIdentifier aSN1ObjectIdentifier41 = aSN1ObjectIdentifier37;
        ASN1ObjectIdentifier aSN1ObjectIdentifier42 = PKCSObjectIdentifiers.I0;
        P4 = aSN1ObjectIdentifier42;
        ASN1ObjectIdentifier aSN1ObjectIdentifier43 = aSN1ObjectIdentifier14;
        ASN1ObjectIdentifier aSN1ObjectIdentifier44 = PKCSObjectIdentifiers.O0;
        Q4 = aSN1ObjectIdentifier44;
        R4 = aSN1ObjectIdentifier40;
        ASN1ObjectIdentifier aSN1ObjectIdentifier45 = aSN1ObjectIdentifier42;
        ASN1ObjectIdentifier aSN1ObjectIdentifier46 = aSN1ObjectIdentifier44;
        ASN1ObjectIdentifier aSN1ObjectIdentifier47 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
        S4 = aSN1ObjectIdentifier47;
        ASN1ObjectIdentifier aSN1ObjectIdentifier48 = aSN1ObjectIdentifier13;
        ASN1ObjectIdentifier aSN1ObjectIdentifier49 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
        T4 = aSN1ObjectIdentifier49;
        Hashtable hashtable = new Hashtable();
        V4 = hashtable;
        ASN1ObjectIdentifier aSN1ObjectIdentifier50 = aSN1ObjectIdentifier12;
        Hashtable hashtable2 = new Hashtable();
        W4 = hashtable2;
        Hashtable hashtable3 = hashtable2;
        Hashtable hashtable4 = new Hashtable();
        X4 = hashtable4;
        Hashtable hashtable5 = hashtable4;
        Hashtable hashtable6 = new Hashtable();
        Y4 = hashtable6;
        Z4 = hashtable;
        a5 = hashtable6;
        ASN1ObjectIdentifier aSN1ObjectIdentifier51 = aSN1ObjectIdentifier11;
        hashtable.put(aSN1ObjectIdentifier, "C");
        hashtable.put(aSN1ObjectIdentifier2, "O");
        hashtable.put(aSN1ObjectIdentifier4, ExifInterface.GPS_DIRECTION_TRUE);
        hashtable.put(aSN1ObjectIdentifier3, "OU");
        ASN1ObjectIdentifier aSN1ObjectIdentifier52 = aSN1ObjectIdentifier4;
        hashtable.put(aSN1ObjectIdentifier5, "CN");
        hashtable.put(aSN1ObjectIdentifier8, "L");
        Object obj = "L";
        hashtable.put(aSN1ObjectIdentifier9, "ST");
        hashtable.put(aSN1ObjectIdentifier6, "SERIALNUMBER");
        hashtable.put(aSN1ObjectIdentifier40, ExifInterface.LONGITUDE_EAST);
        hashtable.put(aSN1ObjectIdentifier47, "DC");
        hashtable.put(aSN1ObjectIdentifier49, "UID");
        ASN1ObjectIdentifier aSN1ObjectIdentifier53 = aSN1ObjectIdentifier6;
        hashtable.put(aSN1ObjectIdentifier7, "STREET");
        ASN1ObjectIdentifier aSN1ObjectIdentifier54 = aSN1ObjectIdentifier49;
        hashtable.put(aSN1ObjectIdentifier10, "SURNAME");
        ASN1ObjectIdentifier aSN1ObjectIdentifier55 = aSN1ObjectIdentifier10;
        hashtable.put(aSN1ObjectIdentifier51, "GIVENNAME");
        hashtable.put(aSN1ObjectIdentifier50, "INITIALS");
        hashtable.put(aSN1ObjectIdentifier48, "GENERATION");
        hashtable.put(aSN1ObjectIdentifier46, "unstructuredAddress");
        hashtable.put(aSN1ObjectIdentifier45, "unstructuredName");
        hashtable.put(aSN1ObjectIdentifier43, "UniqueIdentifier");
        hashtable.put(aSN1ObjectIdentifier20, "DN");
        hashtable.put(aSN1ObjectIdentifier22, "Pseudonym");
        hashtable.put(aSN1ObjectIdentifier36, "PostalAddress");
        hashtable.put(aSN1ObjectIdentifier34, "NameAtBirth");
        hashtable.put(aSN1ObjectIdentifier30, "CountryOfCitizenship");
        hashtable.put(aSN1ObjectIdentifier32, "CountryOfResidence");
        hashtable.put(aSN1ObjectIdentifier28, "Gender");
        hashtable.put(aSN1ObjectIdentifier26, "PlaceOfBirth");
        hashtable.put(aSN1ObjectIdentifier24, "DateOfBirth");
        hashtable.put(aSN1ObjectIdentifier18, "PostalCode");
        hashtable.put(aSN1ObjectIdentifier16, "BusinessCategory");
        hashtable.put(aSN1ObjectIdentifier41, "TelephoneNumber");
        hashtable.put(aSN1ObjectIdentifier39, "Name");
        Hashtable hashtable7 = hashtable3;
        hashtable7.put(aSN1ObjectIdentifier, "C");
        ASN1ObjectIdentifier aSN1ObjectIdentifier56 = aSN1ObjectIdentifier2;
        hashtable7.put(aSN1ObjectIdentifier56, "O");
        hashtable7.put(aSN1ObjectIdentifier3, "OU");
        Object obj2 = "CN";
        hashtable7.put(aSN1ObjectIdentifier5, obj2);
        ASN1ObjectIdentifier aSN1ObjectIdentifier57 = aSN1ObjectIdentifier5;
        hashtable7.put(aSN1ObjectIdentifier8, obj);
        Object obj3 = "ST";
        hashtable7.put(aSN1ObjectIdentifier9, obj3);
        hashtable7.put(aSN1ObjectIdentifier7, "STREET");
        hashtable7.put(aSN1ObjectIdentifier47, "DC");
        ASN1ObjectIdentifier aSN1ObjectIdentifier58 = aSN1ObjectIdentifier54;
        hashtable7.put(aSN1ObjectIdentifier58, "UID");
        Hashtable hashtable8 = hashtable5;
        hashtable8.put(aSN1ObjectIdentifier, "C");
        hashtable8.put(aSN1ObjectIdentifier56, "O");
        hashtable8.put(aSN1ObjectIdentifier3, "OU");
        ASN1ObjectIdentifier aSN1ObjectIdentifier59 = aSN1ObjectIdentifier57;
        hashtable8.put(aSN1ObjectIdentifier59, obj2);
        hashtable8.put(aSN1ObjectIdentifier8, obj);
        hashtable8.put(aSN1ObjectIdentifier9, obj3);
        hashtable8.put(aSN1ObjectIdentifier7, "STREET");
        Hashtable hashtable9 = hashtable6;
        hashtable9.put("c", aSN1ObjectIdentifier);
        hashtable9.put("o", aSN1ObjectIdentifier56);
        hashtable9.put("t", aSN1ObjectIdentifier52);
        hashtable9.put("ou", aSN1ObjectIdentifier3);
        hashtable9.put("cn", aSN1ObjectIdentifier59);
        hashtable9.put("l", aSN1ObjectIdentifier8);
        hashtable9.put("st", aSN1ObjectIdentifier9);
        ASN1ObjectIdentifier aSN1ObjectIdentifier60 = aSN1ObjectIdentifier53;
        hashtable9.put("sn", aSN1ObjectIdentifier60);
        hashtable9.put("serialnumber", aSN1ObjectIdentifier60);
        hashtable9.put("street", aSN1ObjectIdentifier7);
        ASN1ObjectIdentifier aSN1ObjectIdentifier61 = aSN1ObjectIdentifier40;
        hashtable9.put("emailaddress", aSN1ObjectIdentifier61);
        hashtable9.put("dc", aSN1ObjectIdentifier47);
        hashtable9.put("e", aSN1ObjectIdentifier61);
        hashtable9.put("uid", aSN1ObjectIdentifier58);
        hashtable9.put("surname", aSN1ObjectIdentifier55);
        hashtable9.put("givenname", aSN1ObjectIdentifier51);
        hashtable9.put("initials", aSN1ObjectIdentifier50);
        hashtable9.put("generation", aSN1ObjectIdentifier48);
        hashtable9.put("unstructuredaddress", aSN1ObjectIdentifier46);
        hashtable9.put("unstructuredname", aSN1ObjectIdentifier45);
        hashtable9.put("uniqueidentifier", aSN1ObjectIdentifier43);
        hashtable9.put("dn", aSN1ObjectIdentifier20);
        hashtable9.put("pseudonym", aSN1ObjectIdentifier22);
        hashtable9.put("postaladdress", aSN1ObjectIdentifier36);
        hashtable9.put("nameofbirth", aSN1ObjectIdentifier34);
        hashtable9.put("countryofcitizenship", aSN1ObjectIdentifier30);
        hashtable9.put("countryofresidence", aSN1ObjectIdentifier32);
        hashtable9.put("gender", aSN1ObjectIdentifier28);
        hashtable9.put("placeofbirth", aSN1ObjectIdentifier26);
        hashtable9.put("dateofbirth", aSN1ObjectIdentifier24);
        hashtable9.put("postalcode", aSN1ObjectIdentifier18);
        hashtable9.put("businesscategory", aSN1ObjectIdentifier16);
        hashtable9.put("telephonenumber", aSN1ObjectIdentifier41);
        hashtable9.put("name", aSN1ObjectIdentifier39);
    }

    public static X509Name k(Object obj) {
        if (obj == null || (obj instanceof X509Name)) {
            return (X509Name) obj;
        }
        if (obj instanceof X500Name) {
            return new X509Name(ASN1Sequence.o(((X500Name) obj).toASN1Primitive()));
        }
        return new X509Name(ASN1Sequence.o(obj));
    }

    protected X509Name() {
    }

    public X509Name(ASN1Sequence seq) {
        this.h5 = seq;
        Enumeration e = seq.s();
        while (e.hasMoreElements()) {
            ASN1Set set = ASN1Set.p(((ASN1Encodable) e.nextElement()).toASN1Primitive());
            int i = 0;
            while (true) {
                if (i < set.size()) {
                    ASN1Sequence s = ASN1Sequence.o(set.s(i).toASN1Primitive());
                    if (s.size() == 2) {
                        this.e5.addElement(ASN1ObjectIdentifier.t(s.r(0)));
                        ASN1Encodable value = s.r(1);
                        if (!(value instanceof ASN1String) || (value instanceof DERUniversalString)) {
                            try {
                                Vector vector = this.f5;
                                vector.addElement("#" + f(Hex.b(value.toASN1Primitive().getEncoded("DER"))));
                            } catch (IOException e2) {
                                throw new IllegalArgumentException("cannot encode value");
                            }
                        } else {
                            String v = ((ASN1String) value).a();
                            if (v.length() <= 0 || v.charAt(0) != '#') {
                                this.f5.addElement(v);
                            } else {
                                Vector vector2 = this.f5;
                                vector2.addElement("\\" + v);
                            }
                        }
                        this.g5.addElement(i != 0 ? b5 : c5);
                        i++;
                    } else {
                        throw new IllegalArgumentException("badly sized pair");
                    }
                }
            }
        }
    }

    public ASN1Primitive toASN1Primitive() {
        if (this.h5 == null) {
            ASN1EncodableVector vec = new ASN1EncodableVector();
            ASN1EncodableVector sVec = new ASN1EncodableVector();
            ASN1ObjectIdentifier lstOid = null;
            for (int i = 0; i != this.e5.size(); i++) {
                ASN1EncodableVector v = new ASN1EncodableVector();
                ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) this.e5.elementAt(i);
                v.a(oid);
                v.a(this.d5.b(oid, (String) this.f5.elementAt(i)));
                if (lstOid == null || ((Boolean) this.g5.elementAt(i)).booleanValue()) {
                    sVec.a(new DERSequence(v));
                } else {
                    vec.a(new DERSet(sVec));
                    sVec = new ASN1EncodableVector();
                    sVec.a(new DERSequence(v));
                }
                lstOid = oid;
            }
            vec.a(new DERSet(sVec));
            this.h5 = new DERSequence(vec);
        }
        return this.h5;
    }

    public int hashCode() {
        if (this.i5) {
            return this.j5;
        }
        this.i5 = true;
        for (int i = 0; i != this.e5.size(); i++) {
            String value = n(g((String) this.f5.elementAt(i)));
            int hashCode = this.j5 ^ this.e5.elementAt(i).hashCode();
            this.j5 = hashCode;
            this.j5 = hashCode ^ value.hashCode();
        }
        return this.j5;
    }

    /* JADX WARNING: type inference failed for: r20v0, types: [java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r20) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            r0 = 1
            if (r2 != r1) goto L_0x0008
            return r0
        L_0x0008:
            boolean r3 = r2 instanceof org.spongycastle.asn1.x509.X509Name
            r4 = 0
            if (r3 != 0) goto L_0x0012
            boolean r3 = r2 instanceof org.spongycastle.asn1.ASN1Sequence
            if (r3 != 0) goto L_0x0012
            return r4
        L_0x0012:
            r3 = r2
            org.spongycastle.asn1.ASN1Encodable r3 = (org.spongycastle.asn1.ASN1Encodable) r3
            org.spongycastle.asn1.ASN1Primitive r3 = r3.toASN1Primitive()
            org.spongycastle.asn1.ASN1Primitive r5 = r19.toASN1Primitive()
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x0024
            return r0
        L_0x0024:
            org.spongycastle.asn1.x509.X509Name r5 = k(r20)     // Catch:{ IllegalArgumentException -> 0x00a1 }
            java.util.Vector r6 = r1.e5
            int r6 = r6.size()
            java.util.Vector r7 = r5.e5
            int r7 = r7.size()
            if (r6 == r7) goto L_0x0038
            return r4
        L_0x0038:
            boolean[] r7 = new boolean[r6]
            java.util.Vector r8 = r1.e5
            java.lang.Object r8 = r8.elementAt(r4)
            java.util.Vector r9 = r5.e5
            java.lang.Object r9 = r9.elementAt(r4)
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x0050
            r8 = 0
            r9 = r6
            r10 = 1
            goto L_0x0054
        L_0x0050:
            int r8 = r6 + -1
            r9 = -1
            r10 = -1
        L_0x0054:
            r11 = r8
        L_0x0055:
            if (r11 == r9) goto L_0x009f
            r12 = 0
            java.util.Vector r13 = r1.e5
            java.lang.Object r13 = r13.elementAt(r11)
            org.spongycastle.asn1.ASN1ObjectIdentifier r13 = (org.spongycastle.asn1.ASN1ObjectIdentifier) r13
            java.util.Vector r14 = r1.f5
            java.lang.Object r14 = r14.elementAt(r11)
            java.lang.String r14 = (java.lang.String) r14
            r15 = 0
        L_0x0069:
            if (r15 >= r6) goto L_0x0097
            boolean r16 = r7[r15]
            if (r16 == 0) goto L_0x0070
            goto L_0x0092
        L_0x0070:
            java.util.Vector r4 = r5.e5
            java.lang.Object r4 = r4.elementAt(r15)
            org.spongycastle.asn1.ASN1ObjectIdentifier r4 = (org.spongycastle.asn1.ASN1ObjectIdentifier) r4
            boolean r17 = r13.equals(r4)
            if (r17 == 0) goto L_0x0092
            java.util.Vector r0 = r5.f5
            java.lang.Object r0 = r0.elementAt(r15)
            java.lang.String r0 = (java.lang.String) r0
            boolean r18 = r1.i(r14, r0)
            if (r18 == 0) goto L_0x0092
            r17 = 1
            r7[r15] = r17
            r12 = 1
            goto L_0x0097
        L_0x0092:
            int r15 = r15 + 1
            r0 = 1
            r4 = 0
            goto L_0x0069
        L_0x0097:
            if (r12 != 0) goto L_0x009b
            r4 = 0
            return r4
        L_0x009b:
            int r11 = r11 + r10
            r0 = 1
            r4 = 0
            goto L_0x0055
        L_0x009f:
            r0 = 1
            return r0
        L_0x00a1:
            r0 = move-exception
            r4 = r0
            r0 = r4
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.asn1.x509.X509Name.equals(java.lang.Object):boolean");
    }

    private boolean i(String s1, String s2) {
        String value = g(s1);
        String oValue = g(s2);
        if (value.equals(oValue) || n(value).equals(n(oValue))) {
            return true;
        }
        return false;
    }

    private String g(String s) {
        String value = Strings.h(s.trim());
        if (value.length() <= 0 || value.charAt(0) != '#') {
            return value;
        }
        ASN1Primitive obj = h(value);
        if (obj instanceof ASN1String) {
            return Strings.h(((ASN1String) obj).a().trim());
        }
        return value;
    }

    private ASN1Primitive h(String oValue) {
        try {
            return ASN1Primitive.h(Hex.a(oValue.substring(1)));
        } catch (IOException e) {
            throw new IllegalStateException("unknown encoding in name: " + e);
        }
    }

    private String n(String str) {
        StringBuffer res = new StringBuffer();
        if (str.length() != 0) {
            char c1 = str.charAt(0);
            res.append(c1);
            for (int k = 1; k < str.length(); k++) {
                char c2 = str.charAt(k);
                if (c1 != ' ' || c2 != ' ') {
                    res.append(c2);
                }
                c1 = c2;
            }
        }
        return res.toString();
    }

    private void e(StringBuffer buf, Hashtable oidSymbols, ASN1ObjectIdentifier oid, String value) {
        int start;
        String sym = (String) oidSymbols.get(oid);
        if (sym != null) {
            buf.append(sym);
        } else {
            buf.append(oid.s());
        }
        buf.append('=');
        int start2 = buf.length();
        buf.append(value);
        int end = buf.length();
        if (value.length() >= 2 && value.charAt(0) == '\\' && value.charAt(1) == '#') {
            start2 += 2;
        }
        while (start < end && buf.charAt(start) == ' ') {
            buf.insert(start, "\\");
            start2 = start + 2;
            end++;
        }
        while (true) {
            end--;
            if (end > start && buf.charAt(end) == ' ') {
                buf.insert(end, '\\');
            }
        }
        while (start <= end) {
            switch (buf.charAt(start)) {
                case '\"':
                case '+':
                case ',':
                case ';':
                case '<':
                case '=':
                case '>':
                case '\\':
                    buf.insert(start, "\\");
                    start += 2;
                    end++;
                    break;
                default:
                    start++;
                    break;
            }
        }
    }

    public String o(boolean reverse, Hashtable oidSymbols) {
        StringBuffer buf = new StringBuffer();
        Vector components = new Vector();
        boolean first = true;
        StringBuffer ava = null;
        for (int i = 0; i < this.e5.size(); i++) {
            if (((Boolean) this.g5.elementAt(i)).booleanValue()) {
                ava.append('+');
                e(ava, oidSymbols, (ASN1ObjectIdentifier) this.e5.elementAt(i), (String) this.f5.elementAt(i));
            } else {
                ava = new StringBuffer();
                e(ava, oidSymbols, (ASN1ObjectIdentifier) this.e5.elementAt(i), (String) this.f5.elementAt(i));
                components.addElement(ava);
            }
        }
        if (reverse) {
            for (int i2 = components.size() - 1; i2 >= 0; i2--) {
                if (first) {
                    first = false;
                } else {
                    buf.append(StringUtil.COMMA);
                }
                buf.append(components.elementAt(i2).toString());
            }
        } else {
            for (int i3 = 0; i3 < components.size(); i3++) {
                if (first) {
                    first = false;
                } else {
                    buf.append(StringUtil.COMMA);
                }
                buf.append(components.elementAt(i3).toString());
            }
        }
        return buf.toString();
    }

    private String f(byte[] data) {
        char[] cs = new char[data.length];
        for (int i = 0; i != cs.length; i++) {
            cs[i] = (char) (data[i] & 255);
        }
        return new String(cs);
    }

    public String toString() {
        return o(U4, V4);
    }
}
