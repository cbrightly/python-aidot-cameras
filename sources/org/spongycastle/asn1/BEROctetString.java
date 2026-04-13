package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public class BEROctetString extends ASN1OctetString {
    /* access modifiers changed from: private */
    public ASN1OctetString[] d;

    private static byte[] v(ASN1OctetString[] octs) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        int i = 0;
        while (i != octs.length) {
            try {
                bOut.write(octs[i].q());
                i++;
            } catch (ClassCastException e) {
                throw new IllegalArgumentException(octs[i].getClass().getName() + " found in input should only contain DEROctetString");
            } catch (IOException e2) {
                throw new IllegalArgumentException("exception converting octets " + e2.toString());
            }
        }
        return bOut.toByteArray();
    }

    public BEROctetString(byte[] string) {
        super(string);
    }

    public BEROctetString(ASN1OctetString[] octs) {
        super(v(octs));
        this.d = octs;
    }

    public byte[] q() {
        return this.c;
    }

    public Enumeration u() {
        if (this.d == null) {
            return t().elements();
        }
        return new Enumeration() {
            int a = 0;

            public boolean hasMoreElements() {
                return this.a < BEROctetString.this.d.length;
            }

            public Object nextElement() {
                ASN1OctetString[] r = BEROctetString.this.d;
                int i = this.a;
                this.a = i + 1;
                return r[i];
            }
        };
    }

    private Vector t() {
        int end;
        Vector vec = new Vector();
        int i = 0;
        while (true) {
            byte[] bArr = this.c;
            if (i >= bArr.length) {
                return vec;
            }
            if (i + 1000 > bArr.length) {
                end = bArr.length;
            } else {
                end = i + 1000;
            }
            byte[] nStr = new byte[(end - i)];
            System.arraycopy(bArr, i, nStr, 0, nStr.length);
            vec.addElement(new DEROctetString(nStr));
            i += 1000;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        int length = 0;
        Enumeration e = u();
        while (e.hasMoreElements()) {
            length += ((ASN1Encodable) e.nextElement()).toASN1Primitive().g();
        }
        return length + 2 + 2;
    }

    public void f(ASN1OutputStream out) {
        out.c(36);
        out.c(128);
        Enumeration e = u();
        while (e.hasMoreElements()) {
            out.j((ASN1Encodable) e.nextElement());
        }
        out.c(0);
        out.c(0);
    }

    static BEROctetString s(ASN1Sequence seq) {
        ASN1OctetString[] v = new ASN1OctetString[seq.size()];
        Enumeration e = seq.s();
        int index = 0;
        while (e.hasMoreElements()) {
            v[index] = (ASN1OctetString) e.nextElement();
            index++;
        }
        return new BEROctetString(v);
    }
}
