package org.spongycastle.asn1.x509;

import java.io.IOException;
import java.util.StringTokenizer;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.util.IPAddress;

public class GeneralName extends ASN1Object implements ASN1Choice {
    private ASN1Encodable c;
    private int d;

    public GeneralName(X500Name dirName) {
        this.c = dirName;
        this.d = 4;
    }

    public GeneralName(int tag, ASN1Encodable name) {
        this.c = name;
        this.d = tag;
    }

    public GeneralName(int tag, String name) {
        this.d = tag;
        if (tag == 1 || tag == 2 || tag == 6) {
            this.c = new DERIA5String(name);
        } else if (tag == 8) {
            this.c = new ASN1ObjectIdentifier(name);
        } else if (tag == 4) {
            this.c = new X500Name(name);
        } else if (tag == 7) {
            byte[] enc = q(name);
            if (enc != null) {
                this.c = new DEROctetString(enc);
                return;
            }
            throw new IllegalArgumentException("IP Address is invalid");
        } else {
            throw new IllegalArgumentException("can't process String for tag: " + tag);
        }
    }

    public static GeneralName f(Object obj) {
        if (obj == null || (obj instanceof GeneralName)) {
            return (GeneralName) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            ASN1TaggedObject tagObj = (ASN1TaggedObject) obj;
            int tag = tagObj.r();
            switch (tag) {
                case 0:
                    return new GeneralName(tag, (ASN1Encodable) ASN1Sequence.p(tagObj, false));
                case 1:
                    return new GeneralName(tag, (ASN1Encodable) DERIA5String.p(tagObj, false));
                case 2:
                    return new GeneralName(tag, (ASN1Encodable) DERIA5String.p(tagObj, false));
                case 3:
                    throw new IllegalArgumentException("unknown tag: " + tag);
                case 4:
                    return new GeneralName(tag, (ASN1Encodable) X500Name.f(tagObj, true));
                case 5:
                    return new GeneralName(tag, (ASN1Encodable) ASN1Sequence.p(tagObj, false));
                case 6:
                    return new GeneralName(tag, (ASN1Encodable) DERIA5String.p(tagObj, false));
                case 7:
                    return new GeneralName(tag, (ASN1Encodable) ASN1OctetString.p(tagObj, false));
                case 8:
                    return new GeneralName(tag, (ASN1Encodable) ASN1ObjectIdentifier.u(tagObj, false));
            }
        }
        if (obj instanceof byte[]) {
            try {
                return f(ASN1Primitive.h((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("unable to parse encoded general name");
            }
        } else {
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    public static GeneralName g(ASN1TaggedObject tagObj, boolean explicit) {
        return f(ASN1TaggedObject.p(tagObj, true));
    }

    public int i() {
        return this.d;
    }

    public ASN1Encodable h() {
        return this.c;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.d);
        buf.append(": ");
        switch (this.d) {
            case 1:
            case 2:
            case 6:
                buf.append(DERIA5String.o(this.c).a());
                break;
            case 4:
                buf.append(X500Name.e(this.c).toString());
                break;
            default:
                buf.append(this.c.toString());
                break;
        }
        return buf.toString();
    }

    private byte[] q(String ip) {
        int[] parsedIp;
        if (IPAddress.e(ip) || IPAddress.d(ip)) {
            int slashIndex = ip.indexOf(47);
            if (slashIndex < 0) {
                byte[] addr = new byte[16];
                e(o(ip), addr, 0);
                return addr;
            }
            byte[] addr2 = new byte[32];
            e(o(ip.substring(0, slashIndex)), addr2, 0);
            String mask = ip.substring(slashIndex + 1);
            if (mask.indexOf(58) > 0) {
                parsedIp = o(mask);
            } else {
                parsedIp = p(mask);
            }
            e(parsedIp, addr2, 16);
            return addr2;
        } else if (!IPAddress.c(ip) && !IPAddress.b(ip)) {
            return null;
        } else {
            int slashIndex2 = ip.indexOf(47);
            if (slashIndex2 < 0) {
                byte[] addr3 = new byte[4];
                k(ip, addr3, 0);
                return addr3;
            }
            byte[] addr4 = new byte[8];
            k(ip.substring(0, slashIndex2), addr4, 0);
            String mask2 = ip.substring(slashIndex2 + 1);
            if (mask2.indexOf(46) > 0) {
                k(mask2, addr4, 4);
            } else {
                n(mask2, addr4, 4);
            }
            return addr4;
        }
    }

    private void n(String mask, byte[] addr, int offset) {
        int maskVal = Integer.parseInt(mask);
        for (int i = 0; i != maskVal; i++) {
            int i2 = (i / 8) + offset;
            addr[i2] = (byte) (addr[i2] | (1 << (7 - (i % 8))));
        }
    }

    private void k(String ip, byte[] addr, int offset) {
        StringTokenizer sTok = new StringTokenizer(ip, "./");
        int index = 0;
        while (sTok.hasMoreTokens()) {
            addr[index + offset] = (byte) Integer.parseInt(sTok.nextToken());
            index++;
        }
    }

    private int[] p(String mask) {
        int[] res = new int[8];
        int maskVal = Integer.parseInt(mask);
        for (int i = 0; i != maskVal; i++) {
            int i2 = i / 16;
            res[i2] = res[i2] | (1 << (15 - (i % 16)));
        }
        return res;
    }

    private void e(int[] parsedIp, byte[] addr, int offSet) {
        for (int i = 0; i != parsedIp.length; i++) {
            addr[(i * 2) + offSet] = (byte) (parsedIp[i] >> 8);
            addr[(i * 2) + 1 + offSet] = (byte) parsedIp[i];
        }
    }

    private int[] o(String ip) {
        StringTokenizer sTok = new StringTokenizer(ip, ":", true);
        int index = 0;
        int[] val = new int[8];
        if (ip.charAt(0) == ':' && ip.charAt(1) == ':') {
            sTok.nextToken();
        }
        int doubleColon = -1;
        while (sTok.hasMoreTokens()) {
            String e = sTok.nextToken();
            if (e.equals(":")) {
                doubleColon = index;
                val[index] = 0;
                index++;
            } else if (e.indexOf(46) < 0) {
                int index2 = index + 1;
                val[index] = Integer.parseInt(e, 16);
                if (sTok.hasMoreTokens()) {
                    sTok.nextToken();
                }
                index = index2;
            } else {
                StringTokenizer eTok = new StringTokenizer(e, ".");
                int index3 = index + 1;
                val[index] = (Integer.parseInt(eTok.nextToken()) << 8) | Integer.parseInt(eTok.nextToken());
                index = index3 + 1;
                val[index3] = (Integer.parseInt(eTok.nextToken()) << 8) | Integer.parseInt(eTok.nextToken());
            }
        }
        if (index != val.length) {
            System.arraycopy(val, doubleColon, val, val.length - (index - doubleColon), index - doubleColon);
            for (int i = doubleColon; i != val.length - (index - doubleColon); i++) {
                val[i] = 0;
            }
        }
        return val;
    }

    public ASN1Primitive toASN1Primitive() {
        if (this.d == 4) {
            return new DERTaggedObject(true, this.d, this.c);
        }
        return new DERTaggedObject(false, this.d, this.c);
    }
}
