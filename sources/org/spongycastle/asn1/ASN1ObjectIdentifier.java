package org.spongycastle.asn1;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.spongycastle.util.Arrays;

public class ASN1ObjectIdentifier extends ASN1Primitive {
    private static final ConcurrentMap<OidHandle, ASN1ObjectIdentifier> c = new ConcurrentHashMap();
    private final String d;
    private byte[] f;

    public static ASN1ObjectIdentifier t(Object obj) {
        if (obj == null || (obj instanceof ASN1ObjectIdentifier)) {
            return (ASN1ObjectIdentifier) obj;
        }
        if ((obj instanceof ASN1Encodable) && (((ASN1Encodable) obj).toASN1Primitive() instanceof ASN1ObjectIdentifier)) {
            return (ASN1ObjectIdentifier) ((ASN1Encodable) obj).toASN1Primitive();
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1ObjectIdentifier) ASN1Primitive.h((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct object identifier from byte[]: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1ObjectIdentifier u(ASN1TaggedObject obj, boolean explicit) {
        ASN1Primitive o = obj.q();
        if (explicit || (o instanceof ASN1ObjectIdentifier)) {
            return t(o);
        }
        return q(ASN1OctetString.o(obj.q()).q());
    }

    ASN1ObjectIdentifier(byte[] bytes) {
        byte[] bArr = bytes;
        StringBuffer objId = new StringBuffer();
        long value = 0;
        BigInteger bigValue = null;
        boolean first = true;
        for (int i = 0; i != bArr.length; i++) {
            int b = bArr[i] & 255;
            if (value <= 72057594037927808L) {
                long value2 = value + ((long) (b & NeedPermissionEvent.PER_IPC_SPEAK_PERM));
                if ((b & 128) == 0) {
                    if (first) {
                        if (value2 < 40) {
                            objId.append('0');
                        } else if (value2 < 80) {
                            objId.append('1');
                            value2 -= 40;
                        } else {
                            objId.append('2');
                            value2 -= 80;
                        }
                        first = false;
                    }
                    objId.append('.');
                    objId.append(value2);
                    value = 0;
                } else {
                    value = value2 << 7;
                }
            } else {
                BigInteger bigValue2 = (bigValue == null ? BigInteger.valueOf(value) : bigValue).or(BigInteger.valueOf((long) (b & NeedPermissionEvent.PER_IPC_SPEAK_PERM)));
                if ((b & 128) == 0) {
                    if (first) {
                        objId.append('2');
                        bigValue2 = bigValue2.subtract(BigInteger.valueOf(80));
                        first = false;
                    }
                    objId.append('.');
                    objId.append(bigValue2);
                    bigValue = null;
                    value = 0;
                } else {
                    bigValue = bigValue2.shiftLeft(7);
                }
            }
        }
        this.d = objId.toString();
        this.f = Arrays.h(bytes);
    }

    public ASN1ObjectIdentifier(String identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("'identifier' cannot be null");
        } else if (x(identifier)) {
            this.d = identifier;
        } else {
            throw new IllegalArgumentException("string " + identifier + " not an OID");
        }
    }

    ASN1ObjectIdentifier(ASN1ObjectIdentifier oid, String branchID) {
        if (w(branchID, 0)) {
            this.d = oid.s() + "." + branchID;
            return;
        }
        throw new IllegalArgumentException("string " + branchID + " not a valid OID branch");
    }

    public String s() {
        return this.d;
    }

    public ASN1ObjectIdentifier o(String branchID) {
        return new ASN1ObjectIdentifier(this, branchID);
    }

    public boolean y(ASN1ObjectIdentifier stem) {
        String id = s();
        String stemId = stem.s();
        return id.length() > stemId.length() && id.charAt(stemId.length()) == '.' && id.startsWith(stemId);
    }

    private void z(ByteArrayOutputStream out, long fieldValue) {
        byte[] result = new byte[9];
        int pos = 8;
        result[8] = (byte) (((int) fieldValue) & NeedPermissionEvent.PER_IPC_SPEAK_PERM);
        while (fieldValue >= 128) {
            fieldValue >>= 7;
            pos--;
            result[pos] = (byte) ((((int) fieldValue) & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
        }
        out.write(result, pos, 9 - pos);
    }

    private void A(ByteArrayOutputStream out, BigInteger fieldValue) {
        int byteCount = (fieldValue.bitLength() + 6) / 7;
        if (byteCount == 0) {
            out.write(0);
            return;
        }
        BigInteger tmpValue = fieldValue;
        byte[] tmp = new byte[byteCount];
        for (int i = byteCount - 1; i >= 0; i--) {
            tmp[i] = (byte) ((tmpValue.intValue() & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
            tmpValue = tmpValue.shiftRight(7);
        }
        int i2 = byteCount - 1;
        tmp[i2] = (byte) (tmp[i2] & Byte.MAX_VALUE);
        out.write(tmp, 0, tmp.length);
    }

    private void p(ByteArrayOutputStream aOut) {
        OIDTokenizer tok = new OIDTokenizer(this.d);
        int first = Integer.parseInt(tok.b()) * 40;
        String secondToken = tok.b();
        if (secondToken.length() <= 18) {
            z(aOut, ((long) first) + Long.parseLong(secondToken));
        } else {
            A(aOut, new BigInteger(secondToken).add(BigInteger.valueOf((long) first)));
        }
        while (tok.a()) {
            String token = tok.b();
            if (token.length() <= 18) {
                z(aOut, Long.parseLong(token));
            } else {
                A(aOut, new BigInteger(token));
            }
        }
    }

    private synchronized byte[] r() {
        if (this.f == null) {
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            p(bOut);
            this.f = bOut.toByteArray();
        }
        return this.f;
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        int length = r().length;
        return StreamUtil.a(length) + 1 + length;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        byte[] enc = r();
        out.c(6);
        out.i(enc.length);
        out.d(enc);
    }

    public int hashCode() {
        return this.d.hashCode();
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ASN1ObjectIdentifier)) {
            return false;
        }
        return this.d.equals(((ASN1ObjectIdentifier) o).d);
    }

    public String toString() {
        return s();
    }

    private static boolean w(String branchID, int start) {
        boolean periodAllowed = false;
        int pos = branchID.length();
        while (true) {
            pos--;
            if (pos < start) {
                return periodAllowed;
            }
            char ch = branchID.charAt(pos);
            if ('0' <= ch && ch <= '9') {
                periodAllowed = true;
            } else if (ch != '.' || !periodAllowed) {
                return false;
            } else {
                periodAllowed = false;
            }
        }
    }

    private static boolean x(String identifier) {
        char first;
        if (identifier.length() < 3 || identifier.charAt(1) != '.' || (first = identifier.charAt(0)) < '0' || first > '2') {
            return false;
        }
        return w(identifier, 2);
    }

    public ASN1ObjectIdentifier v() {
        OidHandle hdl = new OidHandle(r());
        ConcurrentMap<OidHandle, ASN1ObjectIdentifier> concurrentMap = c;
        ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) concurrentMap.get(hdl);
        if (oid != null) {
            return oid;
        }
        ASN1ObjectIdentifier oid2 = concurrentMap.putIfAbsent(hdl, this);
        if (oid2 == null) {
            return this;
        }
        return oid2;
    }

    public static class OidHandle {
        private final int a;
        private final byte[] b;

        OidHandle(byte[] enc) {
            this.a = Arrays.J(enc);
            this.b = enc;
        }

        public int hashCode() {
            return this.a;
        }

        public boolean equals(Object o) {
            if (o instanceof OidHandle) {
                return Arrays.b(this.b, ((OidHandle) o).b);
            }
            return false;
        }
    }

    static ASN1ObjectIdentifier q(byte[] enc) {
        ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) c.get(new OidHandle(enc));
        if (oid == null) {
            return new ASN1ObjectIdentifier(enc);
        }
        return oid;
    }
}
