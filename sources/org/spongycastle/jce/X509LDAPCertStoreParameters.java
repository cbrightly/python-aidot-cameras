package org.spongycastle.jce;

import java.security.cert.CertStoreParameters;
import org.spongycastle.x509.X509StoreParameters;

public class X509LDAPCertStoreParameters implements X509StoreParameters, CertStoreParameters {
    private String A4;
    private String B4;
    private String C4;
    private String D4;
    private String E4;
    private String F4;
    private String G4;
    private String H4;
    private String I4;
    private String J4;
    private String K4;
    private String L4;
    private String M4;
    private String N4;
    private String O4;
    private String P4;
    private String Q4;
    private String R4;
    private String S4;
    private String T4;
    private String U4;
    private String a1;
    private String a2;
    private String c;
    private String d;
    private String f;
    private String p0;
    private String p1;
    private String p2;
    private String p3;
    private String p4;
    private String q;
    private String x;
    private String y;
    private String z;
    private String z4;

    public static class Builder {
        private String A;
        private String B;
        private String C;
        private String D;
        private String E;
        private String F;
        private String G;
        private String H;
        private String I;
        private String J;
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private String f;
        private String g;
        private String h;
        private String i;
        private String j;
        private String k;
        private String l;
        private String m;
        private String n;
        private String o;
        private String p;
        private String q;
        private String r;
        private String s;
        private String t;
        private String u;
        private String v;
        private String w;
        private String x;
        private String y;
        private String z;

        public Builder() {
            this("ldap://localhost:389", "");
        }

        public Builder(String ldapURL, String baseDN) {
            this.a = ldapURL;
            if (baseDN == null) {
                this.b = "";
            } else {
                this.b = baseDN;
            }
            this.c = "userCertificate";
            this.d = "cACertificate";
            this.e = "crossCertificatePair";
            this.f = "certificateRevocationList";
            this.g = "deltaRevocationList";
            this.h = "authorityRevocationList";
            this.i = "attributeCertificateAttribute";
            this.j = "aACertificate";
            this.k = "attributeDescriptorCertificate";
            this.l = "attributeCertificateRevocationList";
            this.m = "attributeAuthorityRevocationList";
            this.n = "cn";
            this.o = "cn ou o";
            this.p = "cn ou o";
            this.q = "cn ou o";
            this.r = "cn ou o";
            this.s = "cn ou o";
            this.t = "cn";
            this.u = "cn o ou";
            this.v = "cn o ou";
            this.w = "cn o ou";
            this.x = "cn o ou";
            this.y = "cn";
            this.z = "o ou";
            this.A = "o ou";
            this.B = "o ou";
            this.C = "o ou";
            this.D = "o ou";
            this.E = "cn";
            this.F = "o ou";
            this.G = "o ou";
            this.H = "o ou";
            this.I = "o ou";
            this.J = "uid serialNumber cn";
        }
    }

    public Object clone() {
        return this;
    }

    public int hashCode() {
        return a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(0, this.f), this.q), this.x), this.y), this.z), this.p0), this.a1), this.p1), this.a2), this.p2), this.p3), this.p4), this.z4), this.A4), this.B4), this.C4), this.D4), this.E4), this.F4), this.G4), this.H4), this.I4), this.J4), this.K4), this.L4), this.M4), this.N4), this.O4), this.P4), this.Q4), this.R4), this.S4), this.T4), this.U4);
    }

    private int a(int hashCode, Object o) {
        return (hashCode * 29) + (o == null ? 0 : o.hashCode());
    }

    public String b() {
        return this.p1;
    }

    public String c() {
        return this.Q4;
    }

    public String d() {
        return this.p3;
    }

    public String e() {
        return this.T4;
    }

    public String f() {
        return this.a1;
    }

    public String h() {
        return this.P4;
    }

    public String i() {
        return this.p2;
    }

    public String j() {
        return this.S4;
    }

    public String k() {
        return this.a2;
    }

    public String l() {
        return this.R4;
    }

    public String m() {
        return this.p0;
    }

    public String n() {
        return this.O4;
    }

    public String o() {
        return this.d;
    }

    public String p() {
        return this.q;
    }

    public String q() {
        return this.K4;
    }

    public String r() {
        return this.y;
    }

    public String s() {
        return this.M4;
    }

    public String t() {
        return this.x;
    }

    public String u() {
        return this.L4;
    }

    public String v() {
        return this.z;
    }

    public String w() {
        return this.N4;
    }

    public String y() {
        return this.F4;
    }

    public String z() {
        return this.I4;
    }

    public String A() {
        return this.E4;
    }

    public String B() {
        return this.H4;
    }

    public String C() {
        return this.G4;
    }

    public String D() {
        return this.D4;
    }

    public String F() {
        return this.z4;
    }

    public String G() {
        return this.B4;
    }

    public String H() {
        return this.A4;
    }

    public String I() {
        return this.C4;
    }

    public String K() {
        return this.c;
    }

    public String L() {
        return this.p4;
    }

    public String M() {
        return this.U4;
    }

    public String N() {
        return this.f;
    }

    public String O() {
        return this.J4;
    }
}
