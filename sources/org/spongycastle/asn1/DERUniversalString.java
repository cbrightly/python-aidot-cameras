package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.spongycastle.util.Arrays;

public class DERUniversalString extends ASN1Primitive implements ASN1String {
    private static final char[] c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final byte[] d;

    public DERUniversalString(byte[] string) {
        this.d = Arrays.h(string);
    }

    public String a() {
        StringBuffer buf = new StringBuffer("#");
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try {
            new ASN1OutputStream(bOut).j(this);
            byte[] string = bOut.toByteArray();
            for (int i = 0; i != string.length; i++) {
                char[] cArr = c;
                buf.append(cArr[(string[i] >>> 4) & 15]);
                buf.append(cArr[string[i] & 15]);
            }
            return buf.toString();
        } catch (IOException e) {
            throw new ASN1ParsingException("internal error encoding BitString");
        }
    }

    public String toString() {
        return a();
    }

    public byte[] o() {
        return Arrays.h(this.d);
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return StreamUtil.a(this.d.length) + 1 + this.d.length;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        out.g(28, o());
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof DERUniversalString)) {
            return false;
        }
        return Arrays.b(this.d, ((DERUniversalString) o).d);
    }

    public int hashCode() {
        return Arrays.J(this.d);
    }
}
