package org.spongycastle.asn1;

import java.io.InputStream;

public class ConstructedOctetStream extends InputStream {
    private final ASN1StreamParser c;
    private boolean d = true;
    private InputStream f;

    ConstructedOctetStream(ASN1StreamParser parser) {
        this.c = parser;
    }

    public int read(byte[] b, int off, int len) {
        ASN1OctetStringParser s;
        if (this.f == null) {
            if (!this.d || (s = (ASN1OctetStringParser) this.c.b()) == null) {
                return -1;
            }
            this.d = false;
            this.f = s.b();
        }
        int totalRead = 0;
        while (true) {
            int numRead = this.f.read(b, off + totalRead, len - totalRead);
            if (numRead >= 0) {
                totalRead += numRead;
                if (totalRead == len) {
                    return totalRead;
                }
            } else {
                ASN1OctetStringParser aos = (ASN1OctetStringParser) this.c.b();
                if (aos == null) {
                    this.f = null;
                    if (totalRead < 1) {
                        return -1;
                    }
                    return totalRead;
                }
                this.f = aos.b();
            }
        }
    }

    public int read() {
        ASN1OctetStringParser s;
        if (this.f == null) {
            if (!this.d || (s = (ASN1OctetStringParser) this.c.b()) == null) {
                return -1;
            }
            this.d = false;
            this.f = s.b();
        }
        while (true) {
            int b = this.f.read();
            if (b >= 0) {
                return b;
            }
            ASN1OctetStringParser s2 = (ASN1OctetStringParser) this.c.b();
            if (s2 == null) {
                this.f = null;
                return -1;
            }
            this.f = s2.b();
        }
    }
}
