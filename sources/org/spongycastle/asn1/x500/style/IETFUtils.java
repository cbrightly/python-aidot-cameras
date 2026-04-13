package org.spongycastle.asn1.x500.style;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.DERUniversalString;
import org.spongycastle.asn1.x500.AttributeTypeAndValue;
import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.X500NameBuilder;
import org.spongycastle.asn1.x500.X500NameStyle;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class IETFUtils {
    private static String o(String elt) {
        String str = elt;
        if (elt.length() == 0 || (str.indexOf(92) < 0 && str.indexOf(34) < 0)) {
            return elt.trim();
        }
        char[] elts = elt.toCharArray();
        boolean escaped = false;
        boolean quoted = false;
        StringBuffer buf = new StringBuffer(elt.length());
        int start = 0;
        if (elts[0] == '\\' && elts[1] == '#') {
            start = 2;
            buf.append("\\#");
        }
        boolean nonWhiteSpaceEncountered = false;
        int lastEscaped = 0;
        char hex1 = 0;
        for (int i = start; i != elts.length; i++) {
            char c = elts[i];
            if (c != ' ') {
                nonWhiteSpaceEncountered = true;
            }
            if (c == '\"') {
                if (!escaped) {
                    quoted = !quoted;
                } else {
                    buf.append(c);
                }
                escaped = false;
            } else if (c == '\\' && !escaped && !quoted) {
                escaped = true;
                lastEscaped = buf.length();
            } else if (c != ' ' || escaped || nonWhiteSpaceEncountered) {
                if (!escaped || !i(c)) {
                    buf.append(c);
                    escaped = false;
                } else if (hex1 != 0) {
                    buf.append((char) ((f(hex1) * 16) + f(c)));
                    escaped = false;
                    hex1 = 0;
                } else {
                    hex1 = c;
                }
            }
        }
        if (buf.length() > 0) {
            while (buf.charAt(buf.length() - 1) == ' ' && lastEscaped != buf.length() - 1) {
                buf.setLength(buf.length() - 1);
            }
        }
        return buf.toString();
    }

    private static boolean i(char c) {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
    }

    private static int f(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if ('a' > c || c > 'f') {
            return (c - 'A') + 10;
        }
        return (c - 'a') + 10;
    }

    public static RDN[] k(String name, X500NameStyle x500Style) {
        X500NameTokenizer nTok = new X500NameTokenizer(name);
        X500NameBuilder builder = new X500NameBuilder(x500Style);
        while (nTok.a()) {
            String token = nTok.b();
            if (token.indexOf(43) > 0) {
                X500NameTokenizer pTok = new X500NameTokenizer(token, '+');
                X500NameTokenizer vTok = new X500NameTokenizer(pTok.b(), '=');
                String attr = vTok.b();
                if (vTok.a()) {
                    String value = vTok.b();
                    ASN1ObjectIdentifier oid = x500Style.c(attr.trim());
                    if (pTok.a()) {
                        Vector oids = new Vector();
                        Vector values = new Vector();
                        oids.addElement(oid);
                        values.addElement(o(value));
                        while (pTok.a()) {
                            X500NameTokenizer vTok2 = new X500NameTokenizer(pTok.b(), '=');
                            String attr2 = vTok2.b();
                            if (vTok2.a()) {
                                String value2 = vTok2.b();
                                oids.addElement(x500Style.c(attr2.trim()));
                                values.addElement(o(value2));
                            } else {
                                throw new IllegalArgumentException("badly formatted directory string");
                            }
                        }
                        builder.a(m(oids), n(values));
                    } else {
                        builder.d(oid, o(value));
                    }
                } else {
                    throw new IllegalArgumentException("badly formatted directory string");
                }
            } else {
                X500NameTokenizer vTok3 = new X500NameTokenizer(token, '=');
                String attr3 = vTok3.b();
                if (vTok3.a()) {
                    builder.d(x500Style.c(attr3.trim()), o(vTok3.b()));
                } else {
                    throw new IllegalArgumentException("badly formatted directory string");
                }
            }
        }
        return builder.f().h();
    }

    private static String[] n(Vector values) {
        String[] tmp = new String[values.size()];
        for (int i = 0; i != tmp.length; i++) {
            tmp[i] = (String) values.elementAt(i);
        }
        return tmp;
    }

    private static ASN1ObjectIdentifier[] m(Vector oids) {
        ASN1ObjectIdentifier[] tmp = new ASN1ObjectIdentifier[oids.size()];
        for (int i = 0; i != tmp.length; i++) {
            tmp[i] = (ASN1ObjectIdentifier) oids.elementAt(i);
        }
        return tmp;
    }

    public static ASN1ObjectIdentifier g(String name, Hashtable lookUp) {
        if (Strings.l(name).startsWith("OID.")) {
            return new ASN1ObjectIdentifier(name.substring(4));
        }
        if (name.charAt(0) >= '0' && name.charAt(0) <= '9') {
            return new ASN1ObjectIdentifier(name);
        }
        ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) lookUp.get(Strings.h(name));
        if (oid != null) {
            return oid;
        }
        throw new IllegalArgumentException("Unknown object id - " + name + " - passed to distinguished name");
    }

    public static ASN1Encodable p(String str, int off) {
        byte[] data = new byte[((str.length() - off) / 2)];
        for (int index = 0; index != data.length; index++) {
            data[index] = (byte) ((f(str.charAt((index * 2) + off)) << 4) | f(str.charAt((index * 2) + off + 1)));
        }
        return ASN1Primitive.h(data);
    }

    public static void a(StringBuffer buf, RDN rdn, Hashtable oidSymbols) {
        if (rdn.h()) {
            AttributeTypeAndValue[] atv = rdn.g();
            boolean firstAtv = true;
            for (int j = 0; j != atv.length; j++) {
                if (firstAtv) {
                    firstAtv = false;
                } else {
                    buf.append('+');
                }
                b(buf, atv[j], oidSymbols);
            }
        } else if (rdn.e() != null) {
            b(buf, rdn.e(), oidSymbols);
        }
    }

    public static void b(StringBuffer buf, AttributeTypeAndValue typeAndValue, Hashtable oidSymbols) {
        String sym = (String) oidSymbols.get(typeAndValue.f());
        if (sym != null) {
            buf.append(sym);
        } else {
            buf.append(typeAndValue.f().s());
        }
        buf.append('=');
        buf.append(q(typeAndValue.g()));
    }

    public static String q(ASN1Encodable value) {
        StringBuffer vBuf = new StringBuffer();
        if (!(value instanceof ASN1String) || (value instanceof DERUniversalString)) {
            try {
                vBuf.append("#" + d(Hex.b(value.toASN1Primitive().getEncoded("DER"))));
            } catch (IOException e) {
                throw new IllegalArgumentException("Other value has no encoded form");
            }
        } else {
            String v = ((ASN1String) value).a();
            if (v.length() <= 0 || v.charAt(0) != '#') {
                vBuf.append(v);
            } else {
                vBuf.append("\\" + v);
            }
        }
        int end = vBuf.length();
        int index = 0;
        if (vBuf.length() >= 2 && vBuf.charAt(0) == '\\' && vBuf.charAt(1) == '#') {
            index = 0 + 2;
        }
        while (index != end) {
            if (vBuf.charAt(index) == ',' || vBuf.charAt(index) == '\"' || vBuf.charAt(index) == '\\' || vBuf.charAt(index) == '+' || vBuf.charAt(index) == '=' || vBuf.charAt(index) == '<' || vBuf.charAt(index) == '>' || vBuf.charAt(index) == ';') {
                vBuf.insert(index, "\\");
                index++;
                end++;
            }
            index++;
        }
        int start = 0;
        if (vBuf.length() > 0) {
            while (vBuf.length() > start && vBuf.charAt(start) == ' ') {
                vBuf.insert(start, "\\");
                start += 2;
            }
        }
        int endBuf = vBuf.length() - 1;
        while (endBuf >= 0 && vBuf.charAt(endBuf) == ' ') {
            vBuf.insert(endBuf, '\\');
            endBuf--;
        }
        return vBuf.toString();
    }

    private static String d(byte[] data) {
        char[] cs = new char[data.length];
        for (int i = 0; i != cs.length; i++) {
            cs[i] = (char) (data[i] & 255);
        }
        return new String(cs);
    }

    public static String e(String s) {
        String value = Strings.h(s);
        if (value.length() > 0 && value.charAt(0) == '#') {
            ASN1Primitive obj = h(value);
            if (obj instanceof ASN1String) {
                value = Strings.h(((ASN1String) obj).a());
            }
        }
        if (value.length() > 1) {
            int start = 0;
            while (start + 1 < value.length() && value.charAt(start) == '\\' && value.charAt(start + 1) == ' ') {
                start += 2;
            }
            int end = value.length() - 1;
            while (end - 1 > 0 && value.charAt(end - 1) == '\\' && value.charAt(end) == ' ') {
                end -= 2;
            }
            if (start > 0 || end < value.length() - 1) {
                value = value.substring(start, end + 1);
            }
        }
        return l(value);
    }

    private static ASN1Primitive h(String oValue) {
        try {
            return ASN1Primitive.h(Hex.a(oValue.substring(1)));
        } catch (IOException e) {
            throw new IllegalStateException("unknown encoding in name: " + e);
        }
    }

    public static String l(String str) {
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

    public static boolean j(RDN rdn1, RDN rdn2) {
        if (rdn1.h()) {
            if (!rdn2.h()) {
                return false;
            }
            AttributeTypeAndValue[] atvs1 = rdn1.g();
            AttributeTypeAndValue[] atvs2 = rdn2.g();
            if (atvs1.length != atvs2.length) {
                return false;
            }
            for (int i = 0; i != atvs1.length; i++) {
                if (!c(atvs1[i], atvs2[i])) {
                    return false;
                }
            }
            return true;
        } else if (!rdn2.h()) {
            return c(rdn1.e(), rdn2.e());
        } else {
            return false;
        }
    }

    private static boolean c(AttributeTypeAndValue atv1, AttributeTypeAndValue atv2) {
        if (atv1 == atv2) {
            return true;
        }
        if (atv1 != null && atv2 != null && atv1.f().equals(atv2.f()) && e(q(atv1.g())).equals(e(q(atv2.g())))) {
            return true;
        }
        return false;
    }
}
