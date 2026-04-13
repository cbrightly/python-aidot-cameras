package org.spongycastle.asn1.util;

import com.didichuxing.doraemonkit.widget.JustifyTextView;
import java.io.IOException;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1ApplicationSpecific;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.ASN1UTCTime;
import org.spongycastle.asn1.BERApplicationSpecific;
import org.spongycastle.asn1.BEROctetString;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERSet;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DERBMPString;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERExternal;
import org.spongycastle.asn1.DERGraphicString;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERT61String;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.DERVideotexString;
import org.spongycastle.asn1.DERVisibleString;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class ASN1Dump {
    static void a(String indent, boolean verbose, ASN1Primitive obj, StringBuffer buf) {
        String nl = Strings.d();
        if (obj instanceof ASN1Sequence) {
            Enumeration e = ((ASN1Sequence) obj).s();
            String tab = indent + "    ";
            buf.append(indent);
            if (obj instanceof BERSequence) {
                buf.append("BER Sequence");
            } else if (obj instanceof DERSequence) {
                buf.append("DER Sequence");
            } else {
                buf.append("Sequence");
            }
            buf.append(nl);
            while (e.hasMoreElements()) {
                Object o = e.nextElement();
                if (o == null || o.equals(DERNull.c)) {
                    buf.append(tab);
                    buf.append("NULL");
                    buf.append(nl);
                } else if (o instanceof ASN1Primitive) {
                    a(tab, verbose, (ASN1Primitive) o, buf);
                } else {
                    a(tab, verbose, ((ASN1Encodable) o).toASN1Primitive(), buf);
                }
            }
        } else if (obj instanceof ASN1TaggedObject) {
            String tab2 = indent + "    ";
            buf.append(indent);
            if (obj instanceof BERTaggedObject) {
                buf.append("BER Tagged [");
            } else {
                buf.append("Tagged [");
            }
            ASN1TaggedObject o2 = (ASN1TaggedObject) obj;
            buf.append(Integer.toString(o2.r()));
            buf.append(']');
            if (!o2.s()) {
                buf.append(" IMPLICIT ");
            }
            buf.append(nl);
            if (o2.isEmpty()) {
                buf.append(tab2);
                buf.append("EMPTY");
                buf.append(nl);
                return;
            }
            a(tab2, verbose, o2.q(), buf);
        } else if (obj instanceof ASN1Set) {
            Enumeration e2 = ((ASN1Set) obj).t();
            String tab3 = indent + "    ";
            buf.append(indent);
            if (obj instanceof BERSet) {
                buf.append("BER Set");
            } else {
                buf.append("DER Set");
            }
            buf.append(nl);
            while (e2.hasMoreElements()) {
                Object o3 = e2.nextElement();
                if (o3 == null) {
                    buf.append(tab3);
                    buf.append("NULL");
                    buf.append(nl);
                } else if (o3 instanceof ASN1Primitive) {
                    a(tab3, verbose, (ASN1Primitive) o3, buf);
                } else {
                    a(tab3, verbose, ((ASN1Encodable) o3).toASN1Primitive(), buf);
                }
            }
        } else if (obj instanceof ASN1OctetString) {
            ASN1OctetString oct = (ASN1OctetString) obj;
            if (obj instanceof BEROctetString) {
                buf.append(indent + "BER Constructed Octet String[" + oct.q().length + "] ");
            } else {
                buf.append(indent + "DER Octet String[" + oct.q().length + "] ");
            }
            if (verbose) {
                buf.append(e(indent, oct.q()));
            } else {
                buf.append(nl);
            }
        } else if (obj instanceof ASN1ObjectIdentifier) {
            buf.append(indent + "ObjectIdentifier(" + ((ASN1ObjectIdentifier) obj).s() + ")" + nl);
        } else if (obj instanceof ASN1Boolean) {
            buf.append(indent + "Boolean(" + ((ASN1Boolean) obj).s() + ")" + nl);
        } else if (obj instanceof ASN1Integer) {
            buf.append(indent + "Integer(" + ((ASN1Integer) obj).r() + ")" + nl);
        } else if (obj instanceof DERBitString) {
            DERBitString bt = (DERBitString) obj;
            buf.append(indent + "DER Bit String[" + bt.q().length + ", " + bt.t() + "] ");
            if (verbose) {
                buf.append(e(indent, bt.q()));
            } else {
                buf.append(nl);
            }
        } else if (obj instanceof DERIA5String) {
            buf.append(indent + "IA5String(" + ((DERIA5String) obj).a() + ") " + nl);
        } else if (obj instanceof DERUTF8String) {
            buf.append(indent + "UTF8String(" + ((DERUTF8String) obj).a() + ") " + nl);
        } else if (obj instanceof DERPrintableString) {
            buf.append(indent + "PrintableString(" + ((DERPrintableString) obj).a() + ") " + nl);
        } else if (obj instanceof DERVisibleString) {
            buf.append(indent + "VisibleString(" + ((DERVisibleString) obj).a() + ") " + nl);
        } else if (obj instanceof DERBMPString) {
            buf.append(indent + "BMPString(" + ((DERBMPString) obj).a() + ") " + nl);
        } else if (obj instanceof DERT61String) {
            buf.append(indent + "T61String(" + ((DERT61String) obj).a() + ") " + nl);
        } else if (obj instanceof DERGraphicString) {
            buf.append(indent + "GraphicString(" + ((DERGraphicString) obj).a() + ") " + nl);
        } else if (obj instanceof DERVideotexString) {
            buf.append(indent + "VideotexString(" + ((DERVideotexString) obj).a() + ") " + nl);
        } else if (obj instanceof ASN1UTCTime) {
            buf.append(indent + "UTCTime(" + ((ASN1UTCTime) obj).q() + ") " + nl);
        } else if (obj instanceof ASN1GeneralizedTime) {
            buf.append(indent + "GeneralizedTime(" + ((ASN1GeneralizedTime) obj).s() + ") " + nl);
        } else if (obj instanceof BERApplicationSpecific) {
            buf.append(f("BER", indent, verbose, obj, nl));
        } else if (obj instanceof DERApplicationSpecific) {
            buf.append(f("DER", indent, verbose, obj, nl));
        } else if (obj instanceof ASN1Enumerated) {
            buf.append(indent + "DER Enumerated(" + ((ASN1Enumerated) obj).q() + ")" + nl);
        } else if (obj instanceof DERExternal) {
            DERExternal ext = (DERExternal) obj;
            buf.append(indent + "External " + nl);
            StringBuilder sb = new StringBuilder();
            sb.append(indent);
            sb.append("    ");
            String tab4 = sb.toString();
            if (ext.p() != null) {
                buf.append(tab4 + "Direct Reference: " + ext.p().s() + nl);
            }
            if (ext.s() != null) {
                buf.append(tab4 + "Indirect Reference: " + ext.s().toString() + nl);
            }
            if (ext.o() != null) {
                a(tab4, verbose, ext.o(), buf);
            }
            buf.append(tab4 + "Encoding: " + ext.q() + nl);
            a(tab4, verbose, ext.r(), buf);
        } else {
            buf.append(indent + obj.toString() + nl);
        }
    }

    private static String f(String type, String indent, boolean verbose, ASN1Primitive obj, String nl) {
        ASN1ApplicationSpecific app = ASN1ApplicationSpecific.q(obj);
        StringBuffer buf = new StringBuffer();
        if (app.i()) {
            try {
                ASN1Sequence s = ASN1Sequence.o(app.s(16));
                buf.append(indent + type + " ApplicationSpecific[" + app.o() + "]" + nl);
                Enumeration e = s.s();
                while (e.hasMoreElements()) {
                    a(indent + "    ", verbose, (ASN1Primitive) e.nextElement(), buf);
                }
            } catch (IOException e2) {
                buf.append(e2);
            }
            return buf.toString();
        }
        return indent + type + " ApplicationSpecific[" + app.o() + "] (" + Strings.b(Hex.b(app.p())) + ")" + nl;
    }

    public static String c(Object obj) {
        return d(obj, false);
    }

    public static String d(Object obj, boolean verbose) {
        StringBuffer buf = new StringBuffer();
        if (obj instanceof ASN1Primitive) {
            a("", verbose, (ASN1Primitive) obj, buf);
        } else if (obj instanceof ASN1Encodable) {
            a("", verbose, ((ASN1Encodable) obj).toASN1Primitive(), buf);
        } else {
            return "unknown object type " + obj.toString();
        }
        return buf.toString();
    }

    private static String e(String indent, byte[] bytes) {
        String nl = Strings.d();
        StringBuffer buf = new StringBuffer();
        String indent2 = indent + "    ";
        buf.append(nl);
        for (int i = 0; i < bytes.length; i += 32) {
            if (bytes.length - i > 32) {
                buf.append(indent2);
                buf.append(Strings.b(Hex.c(bytes, i, 32)));
                buf.append("    ");
                buf.append(b(bytes, i, 32));
                buf.append(nl);
            } else {
                buf.append(indent2);
                buf.append(Strings.b(Hex.c(bytes, i, bytes.length - i)));
                for (int j = bytes.length - i; j != 32; j++) {
                    buf.append(JustifyTextView.TWO_CHINESE_BLANK);
                }
                buf.append("    ");
                buf.append(b(bytes, i, bytes.length - i));
                buf.append(nl);
            }
        }
        return buf.toString();
    }

    private static String b(byte[] bytes, int off, int len) {
        StringBuffer buf = new StringBuffer();
        for (int i = off; i != off + len; i++) {
            if (bytes[i] >= 32 && bytes[i] <= 126) {
                buf.append((char) bytes[i]);
            }
        }
        return buf.toString();
    }
}
