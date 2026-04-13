package org.spongycastle.asn1;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.IOException;
import org.spongycastle.util.Arrays;

public abstract class ASN1ApplicationSpecific extends ASN1Primitive {
    protected final boolean c;
    protected final int d;
    protected final byte[] f;

    ASN1ApplicationSpecific(boolean isConstructed, int tag, byte[] octets) {
        this.c = isConstructed;
        this.d = tag;
        this.f = Arrays.h(octets);
    }

    public static ASN1ApplicationSpecific q(Object obj) {
        if (obj == null || (obj instanceof ASN1ApplicationSpecific)) {
            return (ASN1ApplicationSpecific) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return q(ASN1Primitive.h((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to construct object from byte[]: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    protected static int r(byte[] data) {
        int length = data[1] & 255;
        if (length == 128 || length <= 127) {
            return 2;
        }
        int size = length & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
        if (size <= 4) {
            return size + 2;
        }
        throw new IllegalStateException("DER length more than 4 bytes: " + size);
    }

    public boolean i() {
        return this.c;
    }

    public byte[] p() {
        return Arrays.h(this.f);
    }

    public int o() {
        return this.d;
    }

    public ASN1Primitive s(int derTagNo) {
        if (derTagNo < 31) {
            byte[] orig = getEncoded();
            byte[] tmp = t(derTagNo, orig);
            if ((orig[0] & 32) != 0) {
                tmp[0] = (byte) (tmp[0] | 32);
            }
            return ASN1Primitive.h(tmp);
        }
        throw new IOException("unsupported tag number");
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return StreamUtil.b(this.d) + StreamUtil.a(this.f.length) + this.f.length;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        int classBits = 64;
        if (this.c) {
            classBits = 64 | 32;
        }
        out.f(classBits, this.d, this.f);
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1ApplicationSpecific)) {
            return false;
        }
        ASN1ApplicationSpecific other = (ASN1ApplicationSpecific) o;
        if (this.c == other.c && this.d == other.d && Arrays.b(this.f, other.f)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.c ^ this.d) ^ Arrays.J(this.f) ? 1 : 0;
    }

    private byte[] t(int newTag, byte[] input) {
        int b = 1;
        if ((input[0] & 31) == 31) {
            int tagNo = 0;
            int index = 1 + 1;
            int b2 = input[1] & 255;
            if ((b2 & NeedPermissionEvent.PER_IPC_SPEAK_PERM) != 0) {
                while (b2 >= 0 && (b2 & 128) != 0) {
                    tagNo = (tagNo | (b2 & NeedPermissionEvent.PER_IPC_SPEAK_PERM)) << 7;
                    b2 = input[index] & 255;
                    index++;
                }
                b = index;
            } else {
                throw new ASN1ParsingException("corrupted stream - invalid high tag number found");
            }
        }
        byte[] tmp = new byte[((input.length - b) + 1)];
        System.arraycopy(input, b, tmp, 1, tmp.length - 1);
        tmp[0] = (byte) newTag;
        return tmp;
    }
}
