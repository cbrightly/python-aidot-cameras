package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.utils.Charsets;

public class B2CConverterBlocking {
    static final int BUFFER_SIZE = 8192;
    private static final Logger logger = Grizzly.logger(B2CConverterBlocking.class);
    private ReadConverter conv;
    private String encoding;
    private IntermediateInputStream iis;
    final char[] result = new char[8192];

    protected B2CConverterBlocking() {
    }

    public B2CConverterBlocking(String encoding2) {
        this.encoding = encoding2;
        reset();
    }

    public void recycle() {
        this.conv.recycle();
    }

    @Deprecated
    public void convert(ByteChunk bb, CharChunk cb) {
        convert(bb, cb, cb.getBuffer().length - cb.getEnd());
    }

    public void convert(ByteChunk bb, CharChunk cb, int limit) {
        this.iis.setByteChunk(bb);
        while (limit > 0) {
            int size = 8192;
            if (limit < 8192) {
                size = limit;
            }
            try {
                int bbLengthBeforeRead = bb.getLength();
                int cnt = this.conv.read(this.result, 0, size);
                if (cnt > 0) {
                    if (0 > 1) {
                        log("Converted: " + new String(this.result, 0, cnt));
                    }
                    cb.append(this.result, 0, cnt);
                    limit -= bbLengthBeforeRead - bb.getLength();
                } else if (0 > 0) {
                    log("EOF");
                    return;
                } else {
                    return;
                }
            } catch (IOException ex) {
                if (0 > 0) {
                    log("Resetting the converter " + ex.toString());
                }
                reset();
                throw ex;
            }
        }
    }

    public static void convertASCII(MessageBytes mb) {
        if (mb.getType() == 2) {
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
        this.iis = new IntermediateInputStream();
        this.conv = new ReadConverter(this.iis, Charsets.lookupCharset(this.encoding));
    }

    /* access modifiers changed from: package-private */
    public void log(String s) {
        Logger logger2 = logger;
        Level level = Level.FINEST;
        if (logger2.isLoggable(level)) {
            logger2.log(level, "B2CConverter: " + s);
        }
    }
}
