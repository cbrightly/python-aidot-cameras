package com.typesafe.config.impl;

import com.alibaba.fastjson.asm.Opcodes;
import com.google.maps.android.BuildConfig;
import com.tencent.bugly.Bugly;
import com.typesafe.config.ConfigException;
import com.typesafe.config.f;
import io.netty.util.internal.StringUtil;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* compiled from: ConfigImplUtil */
public final class g {
    static boolean a(Object a, Object b) {
        if (a == null && b != null) {
            return false;
        }
        if (a != null && b == null) {
            return false;
        }
        if (a == b) {
            return true;
        }
        return a.equals(b);
    }

    static boolean c(int codepoint) {
        return codepoint >= 0 && codepoint <= 31;
    }

    public static String f(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.DOUBLE_QUOTE);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case 8:
                    sb.append("\\b");
                    break;
                case 9:
                    sb.append("\\t");
                    break;
                case 10:
                    sb.append("\\n");
                    break;
                case 12:
                    sb.append("\\f");
                    break;
                case 13:
                    sb.append("\\r");
                    break;
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                default:
                    if (!c(c)) {
                        sb.append(c);
                        break;
                    } else {
                        sb.append(String.format("\\u%04x", new Object[]{Integer.valueOf(c)}));
                        break;
                    }
            }
        }
        sb.append(StringUtil.DOUBLE_QUOTE);
        return sb.toString();
    }

    static String g(String s) {
        if (s.length() == 0) {
            return f(s);
        }
        int first = s.codePointAt(0);
        if (Character.isDigit(first) || first == 45) {
            return f(s);
        }
        if (s.startsWith("include") || s.startsWith("true") || s.startsWith(Bugly.SDK_IS_DEV) || s.startsWith(BuildConfig.TRAVIS) || s.contains("//")) {
            return f(s);
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isLetter(c) && !Character.isDigit(c) && c != '-') {
                return f(s);
            }
        }
        return s;
    }

    static boolean d(int codepoint) {
        switch (codepoint) {
            case 10:
            case 32:
            case Opcodes.IF_ICMPNE:
            case 8199:
            case 8239:
            case 65279:
                return true;
            default:
                return Character.isWhitespace(codepoint);
        }
    }

    public static String i(String s) {
        int delta;
        int cp;
        int length = s.length();
        if (length == 0) {
            return s;
        }
        int start = 0;
        while (start < length) {
            char c = s.charAt(start);
            if (c != ' ' && c != 10) {
                int cp2 = s.codePointAt(start);
                if (!d(cp2)) {
                    break;
                }
                start += Character.charCount(cp2);
            } else {
                start++;
            }
        }
        int end = length;
        while (end > start) {
            char c2 = s.charAt(end - 1);
            if (c2 != ' ' && c2 != 10) {
                if (Character.isLowSurrogate(c2)) {
                    cp = s.codePointAt(end - 2);
                    delta = 2;
                } else {
                    cp = s.codePointAt(end - 1);
                    delta = 1;
                }
                if (!d(cp)) {
                    break;
                }
                end -= delta;
            } else {
                end--;
            }
        }
        return s.substring(start, end);
    }

    public static ConfigException b(ExceptionInInitializerError e) {
        Throwable cause = e.getCause();
        if (cause != null && (cause instanceof ConfigException)) {
            return (ConfigException) cause;
        }
        throw e;
    }

    public static f e(ObjectInputStream in) {
        return b0.readOrigin(in, (f0) null);
    }

    public static void j(ObjectOutputStream out, f origin) {
        b0.writeOrigin(new DataOutputStream(out), (f0) origin, (f0) null);
    }

    static String h(String originalName) {
        String[] words = originalName.split("-+");
        StringBuilder nameBuilder = new StringBuilder(originalName.length());
        for (String word : words) {
            if (nameBuilder.length() == 0) {
                nameBuilder.append(word);
            } else {
                nameBuilder.append(word.substring(0, 1).toUpperCase());
                nameBuilder.append(word.substring(1));
            }
        }
        return nameBuilder.toString();
    }
}
