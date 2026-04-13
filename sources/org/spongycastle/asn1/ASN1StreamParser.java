package org.spongycastle.asn1;

import java.io.IOException;
import java.io.InputStream;

public class ASN1StreamParser {
    private final InputStream a;
    private final int b;
    private final byte[][] c;

    public ASN1StreamParser(InputStream in) {
        this(in, StreamUtil.c(in));
    }

    public ASN1StreamParser(InputStream in, int limit) {
        this.a = in;
        this.b = limit;
        this.c = new byte[11][];
    }

    /* access modifiers changed from: package-private */
    public ASN1Encodable a(int tagValue) {
        switch (tagValue) {
            case 4:
                return new BEROctetStringParser(this);
            case 8:
                return new DERExternalParser(this);
            case 16:
                return new BERSequenceParser(this);
            case 17:
                return new BERSetParser(this);
            default:
                throw new ASN1Exception("unknown BER object encountered: 0x" + Integer.toHexString(tagValue));
        }
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive c(boolean constructed, int tag) {
        if (!constructed) {
            return new DERTaggedObject(false, tag, new DEROctetString(((DefiniteLengthInputStream) this.a).g()));
        }
        ASN1EncodableVector v = d();
        if (this.a instanceof IndefiniteLengthInputStream) {
            if (v.c() == 1) {
                return new BERTaggedObject(true, tag, v.b(0));
            }
            return new BERTaggedObject(false, tag, BERFactory.a(v));
        } else if (v.c() == 1) {
            return new DERTaggedObject(true, tag, v.b(0));
        } else {
            return new DERTaggedObject(false, tag, DERFactory.a(v));
        }
    }

    public ASN1Encodable b() {
        int tag = this.a.read();
        if (tag == -1) {
            return null;
        }
        boolean isConstructed = false;
        e(false);
        int tagNo = ASN1InputStream.s(this.a, tag);
        if ((tag & 32) != 0) {
            isConstructed = true;
        }
        int length = ASN1InputStream.o(this.a, this.b);
        if (length >= 0) {
            DefiniteLengthInputStream defIn = new DefiniteLengthInputStream(this.a, length);
            if ((tag & 64) != 0) {
                return new DERApplicationSpecific(isConstructed, tagNo, defIn.g());
            }
            if ((tag & 128) != 0) {
                return new BERTaggedObjectParser(isConstructed, tagNo, new ASN1StreamParser(defIn));
            }
            if (isConstructed) {
                switch (tagNo) {
                    case 4:
                        return new BEROctetStringParser(new ASN1StreamParser(defIn));
                    case 8:
                        return new DERExternalParser(new ASN1StreamParser(defIn));
                    case 16:
                        return new DERSequenceParser(new ASN1StreamParser(defIn));
                    case 17:
                        return new DERSetParser(new ASN1StreamParser(defIn));
                    default:
                        throw new IOException("unknown tag " + tagNo + " encountered");
                }
            } else {
                switch (tagNo) {
                    case 4:
                        return new DEROctetStringParser(defIn);
                    default:
                        try {
                            return ASN1InputStream.i(tagNo, defIn, this.c);
                        } catch (IllegalArgumentException e) {
                            throw new ASN1Exception("corrupted stream detected", e);
                        }
                }
            }
        } else if (isConstructed) {
            ASN1StreamParser sp = new ASN1StreamParser(new IndefiniteLengthInputStream(this.a, this.b), this.b);
            if ((tag & 64) != 0) {
                return new BERApplicationSpecificParser(tagNo, sp);
            }
            if ((tag & 128) != 0) {
                return new BERTaggedObjectParser(true, tagNo, sp);
            }
            return sp.a(tagNo);
        } else {
            throw new IOException("indefinite-length primitive encoding encountered");
        }
    }

    private void e(boolean enabled) {
        InputStream inputStream = this.a;
        if (inputStream instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream) inputStream).i(enabled);
        }
    }

    /* access modifiers changed from: package-private */
    public ASN1EncodableVector d() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        while (true) {
            ASN1Encodable b2 = b();
            ASN1Encodable obj = b2;
            if (b2 == null) {
                return v;
            }
            if (obj instanceof InMemoryRepresentable) {
                v.a(((InMemoryRepresentable) obj).d());
            } else {
                v.a(obj.toASN1Primitive());
            }
        }
    }
}
