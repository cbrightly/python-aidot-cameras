package org.spongycastle.asn1;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.util.io.Streams;

public class ASN1InputStream extends FilterInputStream implements BERTags {
    private final int c;
    private final boolean d;
    private final byte[][] f;

    public ASN1InputStream(InputStream is) {
        this(is, StreamUtil.c(is));
    }

    public ASN1InputStream(byte[] input) {
        this((InputStream) new ByteArrayInputStream(input), input.length);
    }

    public ASN1InputStream(byte[] input, boolean lazyEvaluate) {
        this(new ByteArrayInputStream(input), input.length, lazyEvaluate);
    }

    public ASN1InputStream(InputStream input, int limit) {
        this(input, limit, false);
    }

    public ASN1InputStream(InputStream input, boolean lazyEvaluate) {
        this(input, StreamUtil.c(input), lazyEvaluate);
    }

    public ASN1InputStream(InputStream input, int limit, boolean lazyEvaluate) {
        super(input);
        this.c = limit;
        this.d = lazyEvaluate;
        this.f = new byte[11][];
    }

    /* access modifiers changed from: package-private */
    public int m() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public int n() {
        return o(this, this.c);
    }

    /* access modifiers changed from: protected */
    public ASN1Primitive g(int tag, int tagNo, int length) {
        boolean isConstructed = (tag & 32) != 0;
        DefiniteLengthInputStream defIn = new DefiniteLengthInputStream(this, length);
        if ((tag & 64) != 0) {
            return new DERApplicationSpecific(isConstructed, tagNo, defIn.g());
        }
        if ((tag & 128) != 0) {
            return new ASN1StreamParser(defIn).c(isConstructed, tagNo);
        }
        if (!isConstructed) {
            return i(tagNo, defIn, this.f);
        }
        switch (tagNo) {
            case 4:
                ASN1EncodableVector v = a(defIn);
                ASN1OctetString[] strings = new ASN1OctetString[v.c()];
                for (int i = 0; i != strings.length; i++) {
                    strings[i] = (ASN1OctetString) v.b(i);
                }
                return new BEROctetString(strings);
            case 8:
                return new DERExternal(a(defIn));
            case 16:
                if (this.d) {
                    return new LazyEncodedSequence(defIn.g());
                }
                return DERFactory.a(a(defIn));
            case 17:
                return DERFactory.b(a(defIn));
            default:
                throw new IOException("unknown tag " + tagNo + " encountered");
        }
    }

    /* access modifiers changed from: package-private */
    public ASN1EncodableVector c() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        while (true) {
            ASN1Primitive r = r();
            ASN1Primitive o = r;
            if (r == null) {
                return v;
            }
            v.a(o);
        }
    }

    /* access modifiers changed from: package-private */
    public ASN1EncodableVector a(DefiniteLengthInputStream dIn) {
        return new ASN1InputStream((InputStream) dIn).c();
    }

    public ASN1Primitive r() {
        int tag = read();
        if (tag > 0) {
            int tagNo = s(this, tag);
            boolean isConstructed = (tag & 32) != 0;
            int length = n();
            if (length >= 0) {
                try {
                    return g(tag, tagNo, length);
                } catch (IllegalArgumentException e) {
                    throw new ASN1Exception("corrupted stream detected", e);
                }
            } else if (isConstructed) {
                ASN1StreamParser sp = new ASN1StreamParser(new IndefiniteLengthInputStream(this, this.c), this.c);
                if ((tag & 64) != 0) {
                    return new BERApplicationSpecificParser(tagNo, sp).d();
                }
                if ((tag & 128) != 0) {
                    return new BERTaggedObjectParser(true, tagNo, sp).d();
                }
                switch (tagNo) {
                    case 4:
                        return new BEROctetStringParser(sp).d();
                    case 8:
                        return new DERExternalParser(sp).d();
                    case 16:
                        return new BERSequenceParser(sp).d();
                    case 17:
                        return new BERSetParser(sp).d();
                    default:
                        throw new IOException("unknown BER object encountered");
                }
            } else {
                throw new IOException("indefinite-length primitive encoding encountered");
            }
        } else if (tag != 0) {
            return null;
        } else {
            throw new IOException("unexpected end-of-contents marker");
        }
    }

    static int s(InputStream s, int tag) {
        int tagNo = tag & 31;
        if (tagNo != 31) {
            return tagNo;
        }
        int tagNo2 = 0;
        int b = s.read();
        if ((b & NeedPermissionEvent.PER_IPC_SPEAK_PERM) != 0) {
            while (b >= 0 && (b & 128) != 0) {
                tagNo2 = (tagNo2 | (b & NeedPermissionEvent.PER_IPC_SPEAK_PERM)) << 7;
                b = s.read();
            }
            if (b >= 0) {
                return tagNo2 | (b & NeedPermissionEvent.PER_IPC_SPEAK_PERM);
            }
            throw new EOFException("EOF found inside tag value.");
        }
        throw new IOException("corrupted stream - invalid high tag number found");
    }

    static int o(InputStream s, int limit) {
        int length = s.read();
        if (length < 0) {
            throw new EOFException("EOF found when length expected");
        } else if (length == 128) {
            return -1;
        } else {
            if (length > 127) {
                int size = length & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
                if (size <= 4) {
                    length = 0;
                    int i = 0;
                    while (i < size) {
                        int next = s.read();
                        if (next >= 0) {
                            length = (length << 8) + next;
                            i++;
                        } else {
                            throw new EOFException("EOF found reading length");
                        }
                    }
                    if (length < 0) {
                        throw new IOException("corrupted stream - negative length found");
                    } else if (length >= limit) {
                        throw new IOException("corrupted stream - out of bounds length found");
                    }
                } else {
                    throw new IOException("DER length more than 4 bytes: " + size);
                }
            }
            return length;
        }
    }

    private static byte[] l(DefiniteLengthInputStream defIn, byte[][] tmpBuffers) {
        int len = defIn.a();
        if (defIn.a() >= tmpBuffers.length) {
            return defIn.g();
        }
        byte[] buf = tmpBuffers[len];
        if (buf == null) {
            byte[] bArr = new byte[len];
            tmpBuffers[len] = bArr;
            buf = bArr;
        }
        Streams.c(defIn, buf);
        return buf;
    }

    private static char[] j(DefiniteLengthInputStream defIn) {
        int ch2;
        int len = defIn.a() / 2;
        char[] buf = new char[len];
        for (int totalRead = 0; totalRead < len; totalRead++) {
            int ch1 = defIn.read();
            if (ch1 < 0 || (ch2 = defIn.read()) < 0) {
                break;
            }
            buf[totalRead] = (char) ((ch1 << 8) | (ch2 & 255));
        }
        return buf;
    }

    static ASN1Primitive i(int tagNo, DefiniteLengthInputStream defIn, byte[][] tmpBuffers) {
        switch (tagNo) {
            case 1:
                return ASN1Boolean.o(l(defIn, tmpBuffers));
            case 2:
                return new ASN1Integer(defIn.g(), false);
            case 3:
                return ASN1BitString.p(defIn.a(), defIn);
            case 4:
                return new DEROctetString(defIn.g());
            case 5:
                return DERNull.c;
            case 6:
                return ASN1ObjectIdentifier.q(l(defIn, tmpBuffers));
            case 10:
                return ASN1Enumerated.o(l(defIn, tmpBuffers));
            case 12:
                return new DERUTF8String(defIn.g());
            case 18:
                return new DERNumericString(defIn.g());
            case 19:
                return new DERPrintableString(defIn.g());
            case 20:
                return new DERT61String(defIn.g());
            case 21:
                return new DERVideotexString(defIn.g());
            case 22:
                return new DERIA5String(defIn.g());
            case 23:
                return new ASN1UTCTime(defIn.g());
            case 24:
                return new ASN1GeneralizedTime(defIn.g());
            case 25:
                return new DERGraphicString(defIn.g());
            case 26:
                return new DERVisibleString(defIn.g());
            case 27:
                return new DERGeneralString(defIn.g());
            case 28:
                return new DERUniversalString(defIn.g());
            case 30:
                return new DERBMPString(j(defIn));
            default:
                throw new IOException("unknown tag " + tagNo + " encountered");
        }
    }
}
