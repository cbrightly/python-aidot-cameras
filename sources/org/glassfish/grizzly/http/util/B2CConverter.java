package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.utils.Charsets;

public class B2CConverter {
    private static final boolean IS_OLD_IO_MODE;
    private static final int MAX_NUMBER_OF_BYTES_PER_CHARACTER = 16;
    private static final Logger logger;
    private B2CConverterBlocking blockingConverter;
    private CharsetDecoder decoder;
    private final ByteBuffer remainder = ByteBuffer.allocate(16);

    static {
        Class<B2CConverter> cls = B2CConverter.class;
        IS_OLD_IO_MODE = Boolean.getBoolean(cls.getName() + ".blockingMode");
        logger = Grizzly.logger(cls);
    }

    protected B2CConverter() {
        init("US-ASCII");
    }

    public B2CConverter(String encoding) {
        init(encoding);
    }

    /* access modifiers changed from: protected */
    public void init(String encoding) {
        if (IS_OLD_IO_MODE) {
            try {
                this.blockingConverter = new B2CConverterBlocking(encoding);
            } catch (IOException e) {
                throw new IllegalStateException("Can not initialize blocking converter");
            }
        } else {
            this.decoder = Charsets.lookupCharset(encoding).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        }
    }

    public void recycle() {
        if (IS_OLD_IO_MODE) {
            this.blockingConverter.recycle();
        }
    }

    public void convert(ByteChunk bb, CharChunk cb) {
        convert(bb, cb, cb.getBuffer().length - cb.getEnd());
    }

    public void convert(ByteChunk bb, CharChunk cb, int limit) {
        if (IS_OLD_IO_MODE) {
            this.blockingConverter.convert(bb, cb, limit);
            return;
        }
        try {
            int bbAvailable = bb.getEnd() - bb.getStart();
            if (limit > bbAvailable) {
                limit = bbAvailable;
            }
            ByteBuffer tmp_bb = ByteBuffer.wrap(bb.getBuffer(), bb.getStart(), limit);
            char[] carr = cb.getBuffer();
            int coff = cb.getEnd();
            int remain = carr.length - coff;
            int cbLimit = cb.getLimit();
            if (remain < limit && (cbLimit < 0 || cbLimit > carr.length)) {
                cb.makeSpace(limit);
                carr = cb.getBuffer();
                coff = cb.getEnd();
            }
            CharBuffer tmp_cb = CharBuffer.wrap(carr, coff, carr.length - coff);
            if (this.remainder.position() > 0) {
                flushRemainder(tmp_bb, tmp_cb);
            }
            CoderResult cr = this.decoder.decode(tmp_bb, tmp_cb, false);
            cb.setEnd(tmp_cb.position());
            while (cr == CoderResult.OVERFLOW) {
                cb.flushBuffer();
                int coff2 = cb.getEnd();
                char[] carr2 = cb.getBuffer();
                CharBuffer tmp_cb2 = CharBuffer.wrap(carr2, coff2, carr2.length - coff2);
                cr = this.decoder.decode(tmp_bb, tmp_cb2, false);
                cb.setEnd(tmp_cb2.position());
            }
            bb.setStart(tmp_bb.position());
            if (tmp_bb.hasRemaining()) {
                this.remainder.put(tmp_bb);
            }
            if (cr != CoderResult.UNDERFLOW) {
                throw new IOException("Encoding error");
            }
        } catch (IOException ex) {
            if (0 > 0) {
                log("B2CConverter " + ex.toString());
            }
            this.decoder.reset();
            throw ex;
        }
    }

    public static void convertASCII(MessageBytes mb) {
        if (IS_OLD_IO_MODE) {
            B2CConverterBlocking.convertASCII(mb);
        } else if (mb.getType() == 2) {
            ByteChunk bc = mb.getByteChunk();
            CharChunk cc = mb.getCharChunk();
            int length = bc.getLength();
            cc.allocate(length, -1);
            byte[] bbuf = bc.getBuffer();
            char[] cbuf = cc.getBuffer();
            int start = bc.getStart();
            for (int i = 0; i < length; i++) {
                cbuf[i] = (char) (bbuf[i + start] & 255);
            }
            mb.setChars(cbuf, 0, length);
        }
    }

    public void reset() {
        if (IS_OLD_IO_MODE) {
            this.blockingConverter.reset();
            return;
        }
        CharsetDecoder charsetDecoder = this.decoder;
        if (charsetDecoder != null) {
            charsetDecoder.reset();
            this.remainder.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public void log(String s) {
        Logger logger2 = logger;
        Level level = Level.FINEST;
        if (logger2.isLoggable(level)) {
            logger2.log(level, "B2CConverter: " + s);
        }
    }

    private void flushRemainder(ByteBuffer tmp_bb, CharBuffer tmp_cb) {
        while (this.remainder.position() > 0 && tmp_bb.hasRemaining()) {
            this.remainder.put(tmp_bb.get());
            this.remainder.flip();
            if (this.decoder.decode(this.remainder, tmp_cb, false) == CoderResult.OVERFLOW) {
                throw new IllegalStateException("CharChunk is not big enough");
            } else if (!this.remainder.hasRemaining()) {
                this.remainder.clear();
                return;
            } else {
                this.remainder.compact();
            }
        }
    }
}
