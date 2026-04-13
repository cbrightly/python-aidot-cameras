package org.glassfish.grizzly.http.util;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

public final class UEncoder {
    private static final int debug = 0;
    private static final BitSet initialSafeChars = new BitSet(128);
    private static final Logger logger = Grizzly.logger(UEncoder.class);
    private ByteChunk bb;
    private C2BConverter c2b;
    private String encoding = "UTF8";
    private final BitSet safeChars = ((BitSet) initialSafeChars.clone());

    static {
        initSafeChars();
    }

    public void setEncoding(String s) {
        this.encoding = s;
    }

    public void addSafeCharacter(char c) {
        this.safeChars.set(c);
    }

    public void urlEncode(Writer buf, String s) {
        urlEncode(buf, s, false);
    }

    public void urlEncode(Writer buf, String s, boolean toHexUpperCase) {
        int d;
        if (this.c2b == null) {
            ByteChunk byteChunk = new ByteChunk(16);
            this.bb = byteChunk;
            this.c2b = C2BConverter.getInstance(byteChunk, this.encoding);
        }
        int i = 0;
        while (i < s.length()) {
            int c = s.charAt(i);
            if (this.safeChars.get(c)) {
                buf.write((char) c);
            } else {
                this.c2b.convert((char) c);
                if (c >= 55296 && c <= 56319 && i + 1 < s.length() && (d = s.charAt(i + 1)) >= 56320 && d <= 57343) {
                    this.c2b.convert((char) d);
                    i++;
                }
                urlEncode(buf, this.bb.getBuffer(), this.bb.getStart(), this.bb.getLength(), toHexUpperCase);
                this.bb.recycle();
            }
            i++;
        }
    }

    public void urlEncode(Writer buf, byte[] bytes, int off, int len) {
        urlEncode(buf, bytes, off, len, false);
    }

    public void urlEncode(Writer buf, byte[] bytes, int off, int len, boolean toHexUpperCase) {
        for (int j = off; j < len; j++) {
            buf.write(37);
            char ch = Character.forDigit((bytes[j] >> 4) & 15, 16);
            if (toHexUpperCase) {
                ch = Character.toUpperCase(ch);
            }
            buf.write(ch);
            char ch2 = Character.forDigit(bytes[j] & 15, 16);
            if (toHexUpperCase) {
                ch2 = Character.toUpperCase(ch2);
            }
            buf.write(ch2);
        }
    }

    public String encodeURL(String url) {
        return encodeURL(url, false);
    }

    public String encodeURL(String uri, boolean toHexUpperCase) {
        try {
            CharArrayWriter out = new CharArrayWriter();
            urlEncode(out, uri, toHexUpperCase);
            return out.toString();
        } catch (IOException e) {
            return null;
        }
    }

    private static void initSafeChars() {
        for (int i = 97; i <= 122; i++) {
            initialSafeChars.set(i);
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            initialSafeChars.set(i2);
        }
        for (int i3 = 48; i3 <= 57; i3++) {
            initialSafeChars.set(i3);
        }
        BitSet bitSet = initialSafeChars;
        bitSet.set(36);
        bitSet.set(45);
        bitSet.set(95);
        bitSet.set(46);
        bitSet.set(33);
        bitSet.set(42);
        bitSet.set(39);
        bitSet.set(40);
        bitSet.set(41);
        bitSet.set(44);
    }

    private static void log(String s) {
        Logger logger2 = logger;
        if (logger2.isLoggable(Level.FINE)) {
            logger2.fine(s);
        }
    }
}
