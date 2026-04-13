package org.spongycastle.asn1.x500.style;

import io.netty.util.internal.StringUtil;

public class X500NameTokenizer {
    private String a;
    private int b;
    private char c;
    private StringBuffer d;

    public X500NameTokenizer(String oid) {
        this(oid, StringUtil.COMMA);
    }

    public X500NameTokenizer(String oid, char separator) {
        this.d = new StringBuffer();
        this.a = oid;
        this.b = -1;
        this.c = separator;
    }

    public boolean a() {
        return this.b != this.a.length();
    }

    public String b() {
        if (this.b == this.a.length()) {
            return null;
        }
        int end = this.b + 1;
        boolean quoted = false;
        boolean escaped = false;
        this.d.setLength(0);
        while (end != this.a.length()) {
            char c2 = this.a.charAt(end);
            if (c2 == '\"') {
                if (!escaped) {
                    quoted = !quoted;
                }
                this.d.append(c2);
                escaped = false;
            } else if (escaped || quoted) {
                this.d.append(c2);
                escaped = false;
            } else if (c2 == '\\') {
                this.d.append(c2);
                escaped = true;
            } else if (c2 == this.c) {
                break;
            } else {
                this.d.append(c2);
            }
            end++;
        }
        this.b = end;
        return this.d.toString();
    }
}
