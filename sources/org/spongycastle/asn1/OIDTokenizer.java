package org.spongycastle.asn1;

public class OIDTokenizer {
    private String a;
    private int b = 0;

    public OIDTokenizer(String oid) {
        this.a = oid;
    }

    public boolean a() {
        return this.b != -1;
    }

    public String b() {
        int i = this.b;
        if (i == -1) {
            return null;
        }
        int end = this.a.indexOf(46, i);
        if (end == -1) {
            String token = this.a.substring(this.b);
            this.b = -1;
            return token;
        }
        String token2 = this.a.substring(this.b, end);
        this.b = end + 1;
        return token2;
    }
}
