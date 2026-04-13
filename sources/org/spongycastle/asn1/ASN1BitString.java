package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.io.Streams;

public abstract class ASN1BitString extends ASN1Primitive implements ASN1String {
    private static final char[] c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    protected final byte[] d;
    protected final int f;

    protected static int u(int bitString) {
        int val = 0;
        int i = 3;
        while (true) {
            if (i < 0) {
                break;
            }
            if (i != 0) {
                if ((bitString >> (i * 8)) != 0) {
                    val = (bitString >> (i * 8)) & 255;
                    break;
                }
            } else if (bitString != 0) {
                val = bitString & 255;
                break;
            }
            i--;
        }
        if (val == 0) {
            return 0;
        }
        int bits = 1;
        while (true) {
            int i2 = val << 1;
            val = i2;
            if ((i2 & 255) == 0) {
                return 8 - bits;
            }
            bits++;
        }
    }

    protected static byte[] r(int bitString) {
        if (bitString == 0) {
            return new byte[0];
        }
        int bytes = 4;
        int i = 3;
        while (i >= 1 && ((255 << (i * 8)) & bitString) == 0) {
            bytes--;
            i--;
        }
        byte[] result = new byte[bytes];
        for (int i2 = 0; i2 < bytes; i2++) {
            result[i2] = (byte) ((bitString >> (i2 * 8)) & 255);
        }
        return result;
    }

    public ASN1BitString(byte[] data, int padBits) {
        if (data == null) {
            throw new NullPointerException("data cannot be null");
        } else if (data.length == 0 && padBits != 0) {
            throw new IllegalArgumentException("zero length data with non-zero pad bits");
        } else if (padBits > 7 || padBits < 0) {
            throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
        } else {
            this.d = Arrays.h(data);
            this.f = padBits;
        }
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
            throw new ASN1ParsingException("Internal error encoding BitString: " + e.getMessage(), e);
        }
    }

    public int v() {
        int value = 0;
        byte[] string = this.d;
        int i = this.f;
        if (i > 0) {
            byte[] bArr = this.d;
            if (bArr.length <= 4) {
                string = o(bArr, i);
            }
        }
        int i2 = 0;
        while (i2 != string.length && i2 != 4) {
            value |= (string[i2] & 255) << (i2 * 8);
            i2++;
        }
        return value;
    }

    public byte[] s() {
        if (this.f == 0) {
            return Arrays.h(this.d);
        }
        throw new IllegalStateException("attempt to get non-octet aligned data from BIT STRING");
    }

    public byte[] q() {
        return o(this.d, this.f);
    }

    public int t() {
        return this.f;
    }

    public String toString() {
        return a();
    }

    public int hashCode() {
        return this.f ^ Arrays.J(q());
    }

    /* access modifiers changed from: protected */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1BitString)) {
            return false;
        }
        ASN1BitString other = (ASN1BitString) o;
        if (this.f != other.f || !Arrays.b(q(), other.q())) {
            return false;
        }
        return true;
    }

    protected static byte[] o(byte[] data, int padBits) {
        byte[] rv = Arrays.h(data);
        if (padBits > 0) {
            int length = data.length - 1;
            rv[length] = (byte) (rv[length] & (255 << padBits));
        }
        return rv;
    }

    static ASN1BitString p(int length, InputStream stream) {
        if (length >= 1) {
            int padBits = stream.read();
            byte[] data = new byte[(length - 1)];
            if (data.length != 0) {
                if (Streams.c(stream, data) != data.length) {
                    throw new EOFException("EOF encountered in middle of BIT STRING");
                } else if (padBits > 0 && padBits < 8 && data[data.length - 1] != ((byte) (data[data.length - 1] & (255 << padBits)))) {
                    return new DLBitString(data, padBits);
                }
            }
            return new DERBitString(data, padBits);
        }
        throw new IllegalArgumentException("truncated BIT STRING detected");
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive k() {
        return new DERBitString(this.d, this.f);
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive n() {
        return new DLBitString(this.d, this.f);
    }
}
