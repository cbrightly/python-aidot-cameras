package io.ktor.http.cio.internals;

import io.ktor.http.u;
import io.ktor.utils.io.core.c;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.g0;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.ranges.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: Chars.kt */
public final class d {
    @NotNull
    private static final a<u> a = a.a.b(u.i.a(), a.INSTANCE, b.INSTANCE);
    private static final long[] b;
    @NotNull
    private static final byte[] c;

    public static /* synthetic */ int e(CharSequence charSequence, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = charSequence.length();
        }
        return d(charSequence, i, i2);
    }

    public static final int d(@NotNull CharSequence $this$hashCodeLowerCase, int start, int end) {
        k.f($this$hashCodeLowerCase, "$this$hashCodeLowerCase");
        int hashCode = 0;
        for (int pos = start; pos < end; pos++) {
            int $this$toLowerCase$iv = $this$hashCodeLowerCase.charAt(pos);
            if (65 <= $this$toLowerCase$iv && 90 >= $this$toLowerCase$iv) {
                $this$toLowerCase$iv = ($this$toLowerCase$iv - 65) + 97;
            }
            hashCode = (hashCode * 31) + $this$toLowerCase$iv;
        }
        return hashCode;
    }

    public static /* synthetic */ boolean b(CharSequence charSequence, int i, int i2, CharSequence charSequence2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = charSequence.length();
        }
        return a(charSequence, i, i2, charSequence2);
    }

    public static final boolean a(@NotNull CharSequence $this$equalsLowerCase, int start, int end, @NotNull CharSequence other) {
        k.f($this$equalsLowerCase, "$this$equalsLowerCase");
        k.f(other, "other");
        if (end - start != other.length()) {
            return false;
        }
        for (int pos = start; pos < end; pos++) {
            int $this$toLowerCase$iv = $this$equalsLowerCase.charAt(pos);
            if (65 <= $this$toLowerCase$iv && 90 >= $this$toLowerCase$iv) {
                $this$toLowerCase$iv = ($this$toLowerCase$iv - 65) + 97;
            }
            int $this$toLowerCase$iv2 = other.charAt(pos - start);
            if (65 <= $this$toLowerCase$iv2 && 90 >= $this$toLowerCase$iv2) {
                $this$toLowerCase$iv2 = ($this$toLowerCase$iv2 - 65) + 97;
            }
            if ($this$toLowerCase$iv != $this$toLowerCase$iv2) {
                return false;
            }
        }
        return true;
    }

    /* compiled from: Chars.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<u, Integer> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Integer.valueOf(invoke((u) obj));
        }

        public final int invoke(@NotNull u it) {
            k.f(it, "it");
            return it.i().length();
        }
    }

    /* compiled from: Chars.kt */
    public static final class b extends l implements p<u, Integer, Character> {
        public static final b INSTANCE = new b();

        b() {
            super(2);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return Character.valueOf(invoke((u) obj, ((Number) obj2).intValue()));
        }

        public final char invoke(@NotNull u m, int idx) {
            k.f(m, "m");
            return m.i().charAt(idx);
        }
    }

    @NotNull
    public static final a<u> c() {
        return a;
    }

    static {
        int $i$f$map;
        long j;
        Iterable $this$mapTo$iv$iv = new i(0, 255);
        int $i$f$map2 = 0;
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        Iterator it = $this$mapTo$iv$iv.iterator();
        while (it.hasNext()) {
            int v = ((g0) it).nextInt();
            if (48 <= v && 57 >= v) {
                j = ((long) v) - 48;
                $i$f$map = $i$f$map2;
            } else {
                long j2 = (long) 97;
                if (((long) v) >= j2) {
                    $i$f$map = $i$f$map2;
                    if (((long) v) <= ((long) 102)) {
                        j = ((long) 10) + (((long) v) - j2);
                    }
                } else {
                    $i$f$map = $i$f$map2;
                }
                long j3 = (long) 65;
                if (((long) v) < j3 || ((long) v) > ((long) 70)) {
                    j = -1;
                } else {
                    j = ((long) 10) + (((long) v) - j3);
                }
            }
            destination$iv$iv.add(Long.valueOf(j));
            $i$f$map2 = $i$f$map;
        }
        b = y.D0(destination$iv$iv);
        Iterable $this$mapTo$iv$iv2 = new i(0, 15);
        Collection destination$iv$iv2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
        Iterator it2 = $this$mapTo$iv$iv2.iterator();
        while (it2.hasNext()) {
            int it3 = ((g0) it2).nextInt();
            destination$iv$iv2.add(Byte.valueOf((byte) (it3 < 10 ? it3 + 48 : (char) (((char) (it3 + 97)) - 10))));
        }
        c = y.y0(destination$iv$iv2);
    }

    public static final long k(@NotNull CharSequence $this$parseHexLong) {
        k.f($this$parseHexLong, "$this$parseHexLong");
        long result = 0;
        long[] table = b;
        int length = $this$parseHexLong.length();
        int i = 0;
        while (i < length) {
            int v = $this$parseHexLong.charAt(i) & 65535;
            long digit = v < 255 ? table[v] : -1;
            if (digit != -1) {
                result = (result << 4) | digit;
                i++;
            } else {
                f($this$parseHexLong, i);
                throw null;
            }
        }
        return result;
    }

    public static final long i(@NotNull CharSequence $this$parseDecLong) {
        k.f($this$parseDecLong, "$this$parseDecLong");
        int length = $this$parseDecLong.length();
        if (length > 19) {
            g($this$parseDecLong);
        }
        if (length == 19) {
            return j($this$parseDecLong);
        }
        long result = 0;
        for (int i = 0; i < length; i++) {
            long digit = ((long) $this$parseDecLong.charAt(i)) - 48;
            if (digit < 0 || digit > ((long) 9)) {
                h($this$parseDecLong, i);
            }
            result = (result << 3) + (result << 1) + digit;
        }
        return result;
    }

    private static final long j(@NotNull CharSequence $this$parseDecLongWithCheck) {
        long result = 0;
        int length = $this$parseDecLongWithCheck.length();
        for (int i = 0; i < length; i++) {
            long digit = ((long) $this$parseDecLongWithCheck.charAt(i)) - 48;
            if (digit < 0 || digit > ((long) 9)) {
                h($this$parseDecLongWithCheck, i);
            }
            result = (result << 3) + (result << 1) + digit;
            if (result < 0) {
                g($this$parseDecLongWithCheck);
            }
        }
        return result;
    }

    public static final void l(@NotNull c $this$writeIntHex, int value) {
        int v;
        k.f($this$writeIntHex, "$this$writeIntHex");
        if (value > 0) {
            int current = value;
            byte[] table = c;
            int v2 = 0;
            while (true) {
                v = v2 + 1;
                if (v2 >= 8) {
                    break;
                }
                int v3 = current >>> 28;
                current <<= 4;
                if (v3 != 0) {
                    $this$writeIntHex.writeByte(table[v3]);
                    break;
                }
                v2 = v;
            }
            while (true) {
                int digits = v + 1;
                if (v < 8) {
                    int v4 = current >>> 28;
                    current <<= 4;
                    $this$writeIntHex.writeByte(table[v4]);
                    v = digits;
                } else {
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("Does only work for positive numbers".toString());
        }
    }

    private static final Void f(CharSequence s, int idx) {
        throw new NumberFormatException("Invalid HEX number: " + s + ", wrong digit: " + s.charAt(idx));
    }

    private static final void h(CharSequence cs, int idx) {
        throw new NumberFormatException("Invalid number: " + cs + ", wrong digit: " + cs.charAt(idx) + " at position " + idx);
    }

    private static final void g(CharSequence cs) {
        throw new NumberFormatException("Invalid number " + cs + ": too large for Long type");
    }
}
