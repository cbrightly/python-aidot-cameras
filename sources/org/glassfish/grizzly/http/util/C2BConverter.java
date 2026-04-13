package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.utils.Charsets;

public class C2BConverter {
    private static final Logger logger = Grizzly.logger(C2BConverter.class);
    protected ByteChunk bb;
    protected final String enc;
    protected final CharsetEncoder encoder;

    public C2BConverter(ByteChunk output, String encoding) {
        this.bb = output;
        this.enc = encoding;
        this.encoder = Charsets.lookupCharset(encoding).newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
    }

    public C2BConverter(String encoding) {
        this(new ByteChunk(1024), encoding);
    }

    public static C2BConverter getInstance(ByteChunk output, String encoding) {
        return new C2BConverter(output, encoding);
    }

    public ByteChunk getByteChunk() {
        return this.bb;
    }

    public String getEncoding() {
        return this.enc;
    }

    public void setByteChunk(ByteChunk bb2) {
        this.bb = bb2;
    }

    public void recycle() {
        this.bb.recycle();
    }

    public void convert(char[] c, int off, int len) {
        CharBuffer cb = CharBuffer.wrap(c, off, len);
        byte[] barr = this.bb.getBuffer();
        int boff = this.bb.getEnd();
        ByteBuffer tmp = ByteBuffer.wrap(barr, boff, barr.length - boff);
        CoderResult cr = this.encoder.encode(cb, tmp, true);
        this.bb.setEnd(tmp.position());
        while (cr == CoderResult.OVERFLOW) {
            if (!this.bb.canGrow()) {
                this.bb.flushBuffer();
            }
            int boff2 = this.bb.getEnd();
            byte[] barr2 = this.bb.getBuffer();
            ByteBuffer tmp2 = ByteBuffer.wrap(barr2, boff2, barr2.length - boff2);
            cr = this.encoder.encode(cb, tmp2, true);
            this.bb.setEnd(tmp2.position());
        }
        if (cr != CoderResult.UNDERFLOW) {
            throw new IOException("Encoding error");
        }
    }

    public void convert(String s) {
        convert(s, 0, s.length());
    }

    public void convert(String s, int off, int len) {
        convert(s.toCharArray(), off, len);
    }

    public void convert(char c) {
        convert(new char[]{c}, 0, 1);
    }

    public void convert(MessageBytes mb) {
        int type = mb.getType();
        if (type != 2) {
            ByteChunk orig = this.bb;
            setByteChunk(mb.getByteChunk());
            this.bb.recycle();
            this.bb.allocate(32, -1);
            if (type == 1) {
                convert(mb.getString());
            } else if (type == 3) {
                CharChunk charC = mb.getCharChunk();
                convert(charC.getBuffer(), charC.getStart(), charC.getLength());
            } else {
                Logger logger2 = logger;
                Level level = Level.FINE;
                if (logger2.isLoggable(level)) {
                    logger2.log(level, "XXX unknowon type {0}", Integer.valueOf(type));
                }
            }
            setByteChunk(orig);
        }
    }

    public void flushBuffer() {
        this.bb.flushBuffer();
    }
}
