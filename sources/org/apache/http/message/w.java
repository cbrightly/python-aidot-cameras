package org.apache.http.message;

import java.util.BitSet;
import org.apache.http.util.d;

/* compiled from: TokenParser */
public class w {
    public static final w a = new w();

    public static BitSet a(int... b) {
        BitSet bitset = new BitSet();
        for (int aB : b) {
            bitset.set(aB);
        }
        return bitset;
    }

    public static boolean e(char ch) {
        return ch == ' ' || ch == 9 || ch == 13 || ch == 10;
    }

    public String f(d buf, v cursor, BitSet delimiters) {
        StringBuilder dst = new StringBuilder();
        boolean whitespace = false;
        while (!cursor.a()) {
            char current = buf.charAt(cursor.b());
            if (delimiters != null && delimiters.get(current)) {
                break;
            } else if (e(current)) {
                h(buf, cursor);
                whitespace = true;
            } else {
                if (whitespace && dst.length() > 0) {
                    dst.append(' ');
                }
                b(buf, cursor, delimiters, dst);
                whitespace = false;
            }
        }
        return dst.toString();
    }

    public String g(d buf, v cursor, BitSet delimiters) {
        StringBuilder dst = new StringBuilder();
        boolean whitespace = false;
        while (!cursor.a()) {
            char current = buf.charAt(cursor.b());
            if (delimiters != null && delimiters.get(current)) {
                break;
            } else if (e(current)) {
                h(buf, cursor);
                whitespace = true;
            } else if (current == '\"') {
                if (whitespace && dst.length() > 0) {
                    dst.append(' ');
                }
                c(buf, cursor, dst);
                whitespace = false;
            } else {
                if (whitespace && dst.length() > 0) {
                    dst.append(' ');
                }
                d(buf, cursor, delimiters, dst);
                whitespace = false;
            }
        }
        return dst.toString();
    }

    public void h(d buf, v cursor) {
        int pos = cursor.b();
        int indexFrom = cursor.b();
        int indexTo = cursor.c();
        int i = indexFrom;
        while (i < indexTo && e(buf.charAt(i))) {
            pos++;
            i++;
        }
        cursor.d(pos);
    }

    public void b(d buf, v cursor, BitSet delimiters, StringBuilder dst) {
        int pos = cursor.b();
        int indexFrom = cursor.b();
        int indexTo = cursor.c();
        for (int i = indexFrom; i < indexTo; i++) {
            char current = buf.charAt(i);
            if ((delimiters != null && delimiters.get(current)) || e(current)) {
                break;
            }
            pos++;
            dst.append(current);
        }
        cursor.d(pos);
    }

    public void d(d buf, v cursor, BitSet delimiters, StringBuilder dst) {
        int pos = cursor.b();
        int indexFrom = cursor.b();
        int indexTo = cursor.c();
        for (int i = indexFrom; i < indexTo; i++) {
            char current = buf.charAt(i);
            if ((delimiters != null && delimiters.get(current)) || e(current) || current == '\"') {
                break;
            }
            pos++;
            dst.append(current);
        }
        cursor.d(pos);
    }

    public void c(d buf, v cursor, StringBuilder dst) {
        if (!cursor.a()) {
            int pos = cursor.b();
            int indexFrom = cursor.b();
            int indexTo = cursor.c();
            if (buf.charAt(pos) == '\"') {
                int pos2 = pos + 1;
                boolean escaped = false;
                int i = indexFrom + 1;
                while (true) {
                    if (i >= indexTo) {
                        break;
                    }
                    char current = buf.charAt(i);
                    if (escaped) {
                        if (!(current == '\"' || current == '\\')) {
                            dst.append('\\');
                        }
                        dst.append(current);
                        escaped = false;
                    } else if (current == '\"') {
                        pos2++;
                        break;
                    } else if (current == '\\') {
                        escaped = true;
                    } else if (!(current == 13 || current == 10)) {
                        dst.append(current);
                    }
                    i++;
                    pos2++;
                }
                cursor.d(pos2);
            }
        }
    }
}
