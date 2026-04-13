package org.apache.http.client.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.b;
import org.apache.http.entity.f;
import org.apache.http.j;
import org.apache.http.message.m;
import org.apache.http.message.v;
import org.apache.http.message.w;
import org.apache.http.u;
import org.apache.http.util.a;
import org.apache.http.util.d;

/* compiled from: URLEncodedUtils */
public class e {
    private static final BitSet a = new BitSet(256);
    private static final BitSet b = new BitSet(256);
    private static final BitSet c = new BitSet(256);
    private static final BitSet d = new BitSet(256);
    private static final BitSet e = new BitSet(256);
    private static final BitSet f = new BitSet(256);
    private static final BitSet g = new BitSet(256);

    public static List<u> i(j entity) {
        a.i(entity, "HTTP entity");
        f contentType = f.get(entity);
        if (contentType == null || !contentType.getMimeType().equalsIgnoreCase("application/x-www-form-urlencoded")) {
            return Collections.emptyList();
        }
        long len = entity.getContentLength();
        a.a(len <= 2147483647L, "HTTP entity is too large");
        Charset charset = contentType.getCharset() != null ? contentType.getCharset() : org.apache.http.protocol.e.a;
        InputStream instream = entity.getContent();
        if (instream == null) {
            return Collections.emptyList();
        }
        try {
            d buf = new d(len > 0 ? (int) len : 1024);
            try {
                Reader reader = new InputStreamReader(instream, charset);
                char[] tmp = new char[1024];
                while (true) {
                    int read = reader.read(tmp);
                    int l = read;
                    if (read == -1) {
                        break;
                    }
                    buf.append(tmp, 0, l);
                }
                instream.close();
                if (buf.length() == 0) {
                    return Collections.emptyList();
                }
                return j(buf, charset, '&');
            } catch (Throwable th) {
                th = th;
                instream.close();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            instream.close();
            throw th;
        }
    }

    public static List<u> h(String s, Charset charset) {
        if (s == null) {
            return Collections.emptyList();
        }
        d buffer = new d(s.length());
        buffer.append(s);
        return j(buffer, charset, '&', ';');
    }

    public static List<u> j(d buf, Charset charset, char... separators) {
        a.i(buf, "Char array buffer");
        w tokenParser = w.a;
        BitSet delimSet = new BitSet();
        for (char separator : separators) {
            delimSet.set(separator);
        }
        v cursor = new v(0, buf.length());
        List<NameValuePair> list = new ArrayList<>();
        while (!cursor.a()) {
            delimSet.set(61);
            String name = tokenParser.f(buf, cursor, delimSet);
            String value = null;
            if (!cursor.a()) {
                int delim = buf.charAt(cursor.b());
                cursor.d(cursor.b() + 1);
                if (delim == 61) {
                    delimSet.clear(61);
                    value = tokenParser.g(buf, cursor, delimSet);
                    if (!cursor.a()) {
                        cursor.d(cursor.b() + 1);
                    }
                }
            }
            if (!name.isEmpty()) {
                list.add(new m(a(name, charset), a(value, charset)));
            }
        }
        return list;
    }

    public static String g(Iterable<? extends u> parameters, Charset charset) {
        return f(parameters, '&', charset);
    }

    public static String f(Iterable<? extends u> parameters, char parameterSeparator, Charset charset) {
        a.i(parameters, "Parameters");
        StringBuilder result = new StringBuilder();
        for (u parameter : parameters) {
            String encodedName = e(parameter.getName(), charset);
            String encodedValue = e(parameter.getValue(), charset);
            if (result.length() > 0) {
                result.append(parameterSeparator);
            }
            result.append(encodedName);
            if (encodedValue != null) {
                result.append("=");
                result.append(encodedValue);
            }
        }
        return result.toString();
    }

    static {
        for (int i = 97; i <= 122; i++) {
            a.set(i);
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            a.set(i2);
        }
        for (int i3 = 48; i3 <= 57; i3++) {
            a.set(i3);
        }
        BitSet bitSet = a;
        bitSet.set(95);
        bitSet.set(45);
        bitSet.set(46);
        bitSet.set(42);
        g.or(bitSet);
        bitSet.set(33);
        bitSet.set(126);
        bitSet.set(39);
        bitSet.set(40);
        bitSet.set(41);
        BitSet bitSet2 = b;
        bitSet2.set(44);
        bitSet2.set(59);
        bitSet2.set(58);
        bitSet2.set(36);
        bitSet2.set(38);
        bitSet2.set(43);
        bitSet2.set(61);
        BitSet bitSet3 = c;
        bitSet3.or(bitSet);
        bitSet3.or(bitSet2);
        BitSet bitSet4 = d;
        bitSet4.or(bitSet);
        bitSet4.set(47);
        bitSet4.set(59);
        bitSet4.set(58);
        bitSet4.set(64);
        bitSet4.set(38);
        bitSet4.set(61);
        bitSet4.set(43);
        bitSet4.set(36);
        bitSet4.set(44);
        BitSet bitSet5 = f;
        bitSet5.set(59);
        bitSet5.set(47);
        bitSet5.set(63);
        bitSet5.set(58);
        bitSet5.set(64);
        bitSet5.set(38);
        bitSet5.set(61);
        bitSet5.set(43);
        bitSet5.set(36);
        bitSet5.set(44);
        bitSet5.set(91);
        bitSet5.set(93);
        BitSet bitSet6 = e;
        bitSet6.or(bitSet5);
        bitSet6.or(bitSet);
    }

    private static String l(String content, Charset charset, BitSet safechars, boolean blankAsPlus) {
        if (content == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        ByteBuffer bb = charset.encode(content);
        while (bb.hasRemaining()) {
            int b2 = bb.get() & 255;
            if (safechars.get(b2)) {
                buf.append((char) b2);
            } else if (!blankAsPlus || b2 != 32) {
                buf.append("%");
                char hex1 = Character.toUpperCase(Character.forDigit((b2 >> 4) & 15, 16));
                char hex2 = Character.toUpperCase(Character.forDigit(b2 & 15, 16));
                buf.append(hex1);
                buf.append(hex2);
            } else {
                buf.append('+');
            }
        }
        return buf.toString();
    }

    private static String k(String content, Charset charset, boolean plusAsBlank) {
        if (content == null) {
            return null;
        }
        ByteBuffer bb = ByteBuffer.allocate(content.length());
        CharBuffer cb = CharBuffer.wrap(content);
        while (cb.hasRemaining()) {
            char c2 = cb.get();
            if (c2 == '%' && cb.remaining() >= 2) {
                char uc = cb.get();
                char lc = cb.get();
                int u = Character.digit(uc, 16);
                int l = Character.digit(lc, 16);
                if (u == -1 || l == -1) {
                    bb.put((byte) 37);
                    bb.put((byte) uc);
                    bb.put((byte) lc);
                } else {
                    bb.put((byte) ((u << 4) + l));
                }
            } else if (!plusAsBlank || c2 != '+') {
                bb.put((byte) c2);
            } else {
                bb.put((byte) 32);
            }
        }
        bb.flip();
        return charset.decode(bb).toString();
    }

    private static String a(String content, Charset charset) {
        if (content == null) {
            return null;
        }
        return k(content, charset != null ? charset : b.a, true);
    }

    private static String e(String content, Charset charset) {
        if (content == null) {
            return null;
        }
        return l(content, charset != null ? charset : b.a, g, true);
    }

    static String d(String content, Charset charset) {
        return l(content, charset, c, false);
    }

    static String c(String content, Charset charset) {
        return l(content, charset, e, false);
    }

    static String b(String content, Charset charset) {
        return l(content, charset, d, false);
    }
}
