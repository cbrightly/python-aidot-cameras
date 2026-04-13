package org.spongycastle.util;

import com.alibaba.fastjson.asm.Opcodes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Vector;

public final class Strings {
    private static String a;

    static {
        try {
            a = (String) AccessController.doPrivileged(new PrivilegedAction<String>() {
                /* renamed from: a */
                public String run() {
                    return System.getProperty("line.separator");
                }
            });
        } catch (Exception e) {
            try {
                a = String.format("%n", new Object[0]);
            } catch (Exception e2) {
                a = "\n";
            }
        }
    }

    public static String c(byte[] bytes) {
        char ch;
        int i = 0;
        int length = 0;
        while (i < bytes.length) {
            length++;
            if ((bytes[i] & 240) == 240) {
                length++;
                i += 4;
            } else if ((bytes[i] & 224) == 224) {
                i += 3;
            } else if ((bytes[i] & 192) == 192) {
                i += 2;
            } else {
                i++;
            }
        }
        char[] cs = new char[length];
        int i2 = 0;
        int length2 = 0;
        while (i2 < bytes.length) {
            if ((bytes[i2] & 240) == 240) {
                int U = (((((bytes[i2] & 3) << 18) | ((bytes[i2 + 1] & 63) << 12)) | ((bytes[i2 + 2] & 63) << 6)) | (bytes[i2 + 3] & 63)) - 65536;
                cs[length2] = (char) (55296 | (U >> 10));
                i2 += 4;
                ch = (char) (56320 | (U & 1023));
                length2++;
            } else if ((bytes[i2] & 224) == 224) {
                ch = (char) (((bytes[i2] & 15) << 12) | ((bytes[i2 + 1] & 63) << 6) | (bytes[i2 + 2] & 63));
                i2 += 3;
            } else if ((bytes[i2] & 208) == 208) {
                ch = (char) (((bytes[i2] & 31) << 6) | (bytes[i2 + 1] & 63));
                i2 += 2;
            } else if ((bytes[i2] & 192) == 192) {
                ch = (char) (((bytes[i2] & 31) << 6) | (bytes[i2 + 1] & 63));
                i2 += 2;
            } else {
                ch = (char) (bytes[i2] & 255);
                i2++;
            }
            cs[length2] = ch;
            length2++;
        }
        return new String(cs);
    }

    public static byte[] j(String string) {
        return k(string.toCharArray());
    }

    public static byte[] k(char[] string) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try {
            i(string, bOut);
            return bOut.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("cannot encode string to byte array!");
        }
    }

    public static void i(char[] string, OutputStream sOut) {
        char[] c = string;
        int i = 0;
        while (i < c.length) {
            char ch = c[i];
            if (ch < 128) {
                sOut.write(ch);
            } else if (ch < 2048) {
                sOut.write((ch >> 6) | Opcodes.CHECKCAST);
                sOut.write(128 | (ch & '?'));
            } else if (ch < 55296 || ch > 57343) {
                sOut.write((ch >> 12) | 224);
                sOut.write(((ch >> 6) & 63) | 128);
                sOut.write(128 | (ch & '?'));
            } else if (i + 1 < c.length) {
                char W1 = ch;
                i++;
                char W2 = c[i];
                if (W1 <= 56319) {
                    int codePoint = (((W1 & 1023) << 10) | (W2 & 1023)) + 0;
                    sOut.write((codePoint >> 18) | 240);
                    sOut.write(((codePoint >> 12) & 63) | 128);
                    sOut.write(((codePoint >> 6) & 63) | 128);
                    sOut.write(128 | (codePoint & 63));
                } else {
                    throw new IllegalStateException("invalid UTF-16 codepoint");
                }
            } else {
                throw new IllegalStateException("invalid UTF-16 codepoint");
            }
            i++;
        }
    }

    public static String l(String string) {
        boolean changed = false;
        char[] chars = string.toCharArray();
        for (int i = 0; i != chars.length; i++) {
            char ch = chars[i];
            if ('a' <= ch && 'z' >= ch) {
                changed = true;
                chars[i] = (char) ((ch - 'a') + 65);
            }
        }
        if (changed) {
            return new String(chars);
        }
        return string;
    }

    public static String h(String string) {
        boolean changed = false;
        char[] chars = string.toCharArray();
        for (int i = 0; i != chars.length; i++) {
            char ch = chars[i];
            if ('A' <= ch && 'Z' >= ch) {
                changed = true;
                chars[i] = (char) ((ch - 'A') + 97);
            }
        }
        if (changed) {
            return new String(chars);
        }
        return string;
    }

    public static byte[] g(char[] chars) {
        byte[] bytes = new byte[chars.length];
        for (int i = 0; i != bytes.length; i++) {
            bytes[i] = (byte) chars[i];
        }
        return bytes;
    }

    public static byte[] f(String string) {
        byte[] bytes = new byte[string.length()];
        for (int i = 0; i != bytes.length; i++) {
            bytes[i] = (byte) string.charAt(i);
        }
        return bytes;
    }

    public static String b(byte[] bytes) {
        return new String(a(bytes));
    }

    public static char[] a(byte[] bytes) {
        char[] chars = new char[bytes.length];
        for (int i = 0; i != chars.length; i++) {
            chars[i] = (char) (bytes[i] & 255);
        }
        return chars;
    }

    public static String[] e(String input, char delimiter) {
        Vector v = new Vector();
        boolean moreTokens = true;
        while (moreTokens) {
            int tokenLocation = input.indexOf(delimiter);
            if (tokenLocation > 0) {
                v.addElement(input.substring(0, tokenLocation));
                input = input.substring(tokenLocation + 1);
            } else {
                moreTokens = false;
                v.addElement(input);
            }
        }
        String[] res = new String[v.size()];
        for (int i = 0; i != res.length; i++) {
            res[i] = (String) v.elementAt(i);
        }
        return res;
    }

    public static String d() {
        return a;
    }

    public static class StringListImpl extends ArrayList<String> implements StringList {
        private StringListImpl() {
        }

        public /* bridge */ /* synthetic */ String get(int x0) {
            return (String) super.get(x0);
        }

        public boolean add(String s) {
            return super.add(s);
        }

        public String set(int index, String element) {
            return (String) super.set(index, element);
        }

        public void add(int index, String element) {
            super.add(index, element);
        }

        public String[] toStringArray() {
            String[] strs = new String[size()];
            for (int i = 0; i != strs.length; i++) {
                strs[i] = (String) get(i);
            }
            return strs;
        }

        public String[] toStringArray(int from, int to) {
            String[] strs = new String[(to - from)];
            int i = from;
            while (i != size() && i != to) {
                strs[i - from] = (String) get(i);
                i++;
            }
            return strs;
        }
    }
}
