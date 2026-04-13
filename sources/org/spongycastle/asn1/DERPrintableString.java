package org.spongycastle.asn1;

import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERPrintableString extends ASN1Primitive implements ASN1String {
    private final byte[] c;

    DERPrintableString(byte[] string) {
        this.c = string;
    }

    public DERPrintableString(String string) {
        this(string, false);
    }

    public DERPrintableString(String string, boolean validate) {
        if (!validate || o(string)) {
            this.c = Strings.f(string);
            return;
        }
        throw new IllegalArgumentException("string contains illegal characters");
    }

    public String a() {
        return Strings.b(this.c);
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return StreamUtil.a(this.c.length) + 1 + this.c.length;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        out.g(19, this.c);
    }

    public int hashCode() {
        return Arrays.J(this.c);
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof DERPrintableString)) {
            return false;
        }
        return Arrays.b(this.c, ((DERPrintableString) o).c);
    }

    public String toString() {
        return a();
    }

    public static boolean o(String str) {
        for (int i = str.length() - 1; i >= 0; i--) {
            char ch = str.charAt(i);
            if (ch > 127) {
                return false;
            }
            if (('a' > ch || ch > 'z') && (('A' > ch || ch > 'Z') && ('0' > ch || ch > '9'))) {
                switch (ch) {
                    case ' ':
                    case '\'':
                    case '(':
                    case ')':
                    case '+':
                    case ',':
                    case '-':
                    case '.':
                    case '/':
                    case ':':
                    case '=':
                    case '?':
                        break;
                    default:
                        return false;
                }
            }
        }
        return true;
    }
}
