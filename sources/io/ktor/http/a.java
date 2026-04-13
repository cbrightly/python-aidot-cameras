package io.ktor.http;

import io.netty.util.internal.StringUtil;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.ranges.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: Codecs.kt */
public final class a {
    private static final List<Byte> a;
    private static final List<Character> b = y.n0(y.l0(new c('a', 'z'), new c('A', 'Z')), new c('0', '9'));
    private static final List<Character> c = y.n0(y.l0(new c('a', 'f'), new c('A', 'F')), new c('0', '9'));
    private static final List<Byte> d;
    private static final List<Character> e = q.j(':', '@', '!', '$', '&', '\'', '(', ')', '*', '+', Character.valueOf(StringUtil.COMMA), ';', '=', '-', '.', '_', '~');
    private static final List<Byte> f;

    static {
        Iterable<Character> $this$mapTo$iv$iv = y.n0(y.l0(new c('a', 'z'), new c('A', 'Z')), new c('0', '9'));
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Character charValue : $this$mapTo$iv$iv) {
            arrayList.add(Byte.valueOf((byte) charValue.charValue()));
        }
        a = arrayList;
        Iterable<Character> $this$mapTo$iv$iv2 = q.j(':', '/', '?', '#', '[', ']', '@', '!', '$', '&', '\'', '(', ')', '*', Character.valueOf(StringUtil.COMMA), ';', '=', '-', '.', '_', '~', '+');
        ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
        for (Character charValue2 : $this$mapTo$iv$iv2) {
            arrayList2.add(Byte.valueOf((byte) charValue2.charValue()));
        }
        d = arrayList2;
        Iterable<Character> $this$mapTo$iv$iv3 = q.j('-', '.', '_', '~');
        ArrayList arrayList3 = new ArrayList(r.r($this$mapTo$iv$iv3, 10));
        for (Character charValue3 : $this$mapTo$iv$iv3) {
            arrayList3.add(Byte.valueOf((byte) charValue3.charValue()));
        }
        f = arrayList3;
    }

    public static /* synthetic */ String g(String str, int i, int i2, boolean z, Charset charset, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        if ((i3 & 8) != 0) {
            charset = kotlin.text.c.a;
        }
        return f(str, i, i2, z, charset);
    }

    @NotNull
    public static final String f(@NotNull String $this$decodeURLQueryComponent, int start, int end, boolean plusIsSpace, @NotNull Charset charset) {
        k.f($this$decodeURLQueryComponent, "$this$decodeURLQueryComponent");
        k.f(charset, "charset");
        return c($this$decodeURLQueryComponent, start, end, plusIsSpace, charset);
    }

    public static /* synthetic */ String e(String str, int i, int i2, Charset charset, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        if ((i3 & 4) != 0) {
            charset = kotlin.text.c.a;
        }
        return d(str, i, i2, charset);
    }

    @NotNull
    public static final String d(@NotNull String $this$decodeURLPart, int start, int end, @NotNull Charset charset) {
        k.f($this$decodeURLPart, "$this$decodeURLPart");
        k.f(charset, "charset");
        return c($this$decodeURLPart, start, end, false, charset);
    }

    private static final String c(@NotNull String $this$decodeScan, int start, int end, boolean plusIsSpace, Charset charset) {
        for (int index = start; index < end; index++) {
            char ch = $this$decodeScan.charAt(index);
            if (ch == '%' || (plusIsSpace && ch == '+')) {
                return b($this$decodeScan, start, end, index, plusIsSpace, charset);
            }
        }
        if (start == 0 && end == $this$decodeScan.length()) {
            return $this$decodeScan.toString();
        }
        if ($this$decodeScan != null) {
            String substring = $this$decodeScan.substring(start, end);
            k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return substring;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    private static final String b(@NotNull CharSequence $this$decodeImpl, int start, int end, int prefixEnd, boolean plusIsSpace, Charset charset) {
        CharSequence charSequence = $this$decodeImpl;
        int i = start;
        int i2 = end;
        int i3 = prefixEnd;
        int length = i2 - i;
        StringBuilder sb = new StringBuilder(length > 255 ? length / 3 : length);
        if (i3 > i) {
            sb.append(charSequence, i, i3);
        }
        int index = prefixEnd;
        byte[] bytes = null;
        while (index < i2) {
            char c2 = charSequence.charAt(index);
            if (plusIsSpace && c2 == '+') {
                sb.append(' ');
                index++;
                Charset charset2 = charset;
            } else if (c2 == '%') {
                if (bytes == null) {
                    bytes = new byte[((i2 - index) / 3)];
                }
                int count = 0;
                while (index < i2 && charSequence.charAt(index) == '%') {
                    if (index + 2 < i2) {
                        int digit1 = a(charSequence.charAt(index + 1));
                        int digit2 = a(charSequence.charAt(index + 2));
                        if (digit1 == -1 || digit2 == -1) {
                            throw new URLDecodeException("Wrong HEX escape: %" + charSequence.charAt(index + 1) + charSequence.charAt(index + 2) + ", in " + charSequence + ", at " + index);
                        }
                        bytes[count] = (byte) ((digit1 * 16) + digit2);
                        index += 3;
                        count++;
                    } else {
                        throw new URLDecodeException("Incomplete trailing HEX escape: " + charSequence.subSequence(index, $this$decodeImpl.length()).toString() + ", in " + charSequence + " at " + index);
                    }
                }
                sb.append(new String(bytes, 0, count, charset));
            } else {
                Charset charset3 = charset;
                sb.append(c2);
                index++;
            }
            int i4 = start;
        }
        Charset charset4 = charset;
        String sb2 = sb.toString();
        k.b(sb2, "sb.toString()");
        return sb2;
    }

    private static final int a(char c2) {
        if ('0' <= c2 && '9' >= c2) {
            return c2 - '0';
        }
        if ('A' <= c2 && 'F' >= c2) {
            return (c2 - 'A') + 10;
        }
        if ('a' <= c2 && 'f' >= c2) {
            return (c2 - 'a') + 10;
        }
        return -1;
    }
}
