package kotlin.text;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.l;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.ranges.g;
import kotlin.ranges.i;
import kotlin.ranges.n;
import kotlin.sequences.h;
import kotlin.sequences.o;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Strings.kt */
public class x extends w {
    @NotNull
    public static final CharSequence e1(@NotNull CharSequence $this$trim) {
        k.e($this$trim, "$this$trim");
        CharSequence $this$trim$iv = $this$trim;
        int startIndex$iv = 0;
        int endIndex$iv = $this$trim$iv.length() - 1;
        boolean startFound$iv = false;
        while (startIndex$iv <= endIndex$iv) {
            char p1 = a.c($this$trim$iv.charAt(!startFound$iv ? startIndex$iv : endIndex$iv));
            if (!startFound$iv) {
                if (p1 == 0) {
                    startFound$iv = true;
                } else {
                    startIndex$iv++;
                }
            } else if (p1 == 0) {
                break;
            } else {
                endIndex$iv--;
            }
        }
        return $this$trim$iv.subSequence(startIndex$iv, endIndex$iv + 1);
    }

    @NotNull
    public static final CharSequence p0(@NotNull CharSequence $this$padStart, int length, char padChar) {
        k.e($this$padStart, "$this$padStart");
        if (length < 0) {
            throw new IllegalArgumentException("Desired length " + length + " is less than zero.");
        } else if (length <= $this$padStart.length()) {
            return $this$padStart.subSequence(0, $this$padStart.length());
        } else {
            StringBuilder sb = new StringBuilder(length);
            int length2 = length - $this$padStart.length();
            int i = 1;
            if (1 <= length2) {
                while (true) {
                    sb.append(padChar);
                    if (i == length2) {
                        break;
                    }
                    i++;
                }
            }
            sb.append($this$padStart);
            return sb;
        }
    }

    @NotNull
    public static final String q0(@NotNull String $this$padStart, int length, char padChar) {
        k.e($this$padStart, "$this$padStart");
        return p0($this$padStart, length, padChar).toString();
    }

    @NotNull
    public static final i Y(@NotNull CharSequence $this$indices) {
        k.e($this$indices, "$this$indices");
        return new i(0, $this$indices.length() - 1);
    }

    public static final int Z(@NotNull CharSequence $this$lastIndex) {
        k.e($this$lastIndex, "$this$lastIndex");
        return $this$lastIndex.length() - 1;
    }

    @NotNull
    public static final String N0(@NotNull CharSequence $this$substring, @NotNull i range) {
        k.e($this$substring, "$this$substring");
        k.e(range, "range");
        return $this$substring.subSequence(range.getStart().intValue(), range.getEndInclusive().intValue() + 1).toString();
    }

    public static /* synthetic */ String Y0(String str, char c2, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return W0(str, c2, str2);
    }

    @NotNull
    public static final String W0(@NotNull String $this$substringBefore, char delimiter, @NotNull String missingDelimiterValue) {
        k.e($this$substringBefore, "$this$substringBefore");
        k.e(missingDelimiterValue, "missingDelimiterValue");
        int index = e0($this$substringBefore, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringBefore.substring(0, index);
        k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String Z0(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return X0(str, str2, str3);
    }

    @NotNull
    public static final String X0(@NotNull String $this$substringBefore, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        k.e($this$substringBefore, "$this$substringBefore");
        k.e(delimiter, "delimiter");
        k.e(missingDelimiterValue, "missingDelimiterValue");
        int index = f0($this$substringBefore, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringBefore.substring(0, index);
        k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String Q0(String str, char c2, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return O0(str, c2, str2);
    }

    @NotNull
    public static final String O0(@NotNull String $this$substringAfter, char delimiter, @NotNull String missingDelimiterValue) {
        k.e($this$substringAfter, "$this$substringAfter");
        k.e(missingDelimiterValue, "missingDelimiterValue");
        int index = e0($this$substringAfter, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringAfter.substring(index + 1, $this$substringAfter.length());
        k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String R0(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return P0(str, str2, str3);
    }

    @NotNull
    public static final String P0(@NotNull String $this$substringAfter, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        k.e($this$substringAfter, "$this$substringAfter");
        k.e(delimiter, "delimiter");
        k.e(missingDelimiterValue, "missingDelimiterValue");
        int index = f0($this$substringAfter, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringAfter.substring(delimiter.length() + index, $this$substringAfter.length());
        k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String c1(String str, char c2, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return a1(str, c2, str2);
    }

    @NotNull
    public static final String a1(@NotNull String $this$substringBeforeLast, char delimiter, @NotNull String missingDelimiterValue) {
        k.e($this$substringBeforeLast, "$this$substringBeforeLast");
        k.e(missingDelimiterValue, "missingDelimiterValue");
        int index = j0($this$substringBeforeLast, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringBeforeLast.substring(0, index);
        k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String d1(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return b1(str, str2, str3);
    }

    @NotNull
    public static final String b1(@NotNull String $this$substringBeforeLast, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        k.e($this$substringBeforeLast, "$this$substringBeforeLast");
        k.e(delimiter, "delimiter");
        k.e(missingDelimiterValue, "missingDelimiterValue");
        int index = k0($this$substringBeforeLast, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringBeforeLast.substring(0, index);
        k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String U0(String str, char c2, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return S0(str, c2, str2);
    }

    @NotNull
    public static final String S0(@NotNull String $this$substringAfterLast, char delimiter, @NotNull String missingDelimiterValue) {
        k.e($this$substringAfterLast, "$this$substringAfterLast");
        k.e(missingDelimiterValue, "missingDelimiterValue");
        int index = j0($this$substringAfterLast, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringAfterLast.substring(index + 1, $this$substringAfterLast.length());
        k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String V0(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return T0(str, str2, str3);
    }

    @NotNull
    public static final String T0(@NotNull String $this$substringAfterLast, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        k.e($this$substringAfterLast, "$this$substringAfterLast");
        k.e(delimiter, "delimiter");
        k.e(missingDelimiterValue, "missingDelimiterValue");
        int index = k0($this$substringAfterLast, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringAfterLast.substring(delimiter.length() + index, $this$substringAfterLast.length());
        k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    @NotNull
    public static final CharSequence A0(@NotNull CharSequence $this$replaceRange, int startIndex, int endIndex, @NotNull CharSequence replacement) {
        k.e($this$replaceRange, "$this$replaceRange");
        k.e(replacement, "replacement");
        if (endIndex >= startIndex) {
            StringBuilder sb = new StringBuilder();
            sb.append($this$replaceRange, 0, startIndex);
            k.d(sb, "this.append(value, startIndex, endIndex)");
            sb.append(replacement);
            sb.append($this$replaceRange, endIndex, $this$replaceRange.length());
            k.d(sb, "this.append(value, startIndex, endIndex)");
            return sb;
        }
        throw new IndexOutOfBoundsException("End index (" + endIndex + ") is less than start index (" + startIndex + ").");
    }

    @NotNull
    public static final String w0(@NotNull String $this$removePrefix, @NotNull CharSequence prefix) {
        k.e($this$removePrefix, "$this$removePrefix");
        k.e(prefix, "prefix");
        if (!M0($this$removePrefix, prefix, false, 2, (Object) null)) {
            return $this$removePrefix;
        }
        String substring = $this$removePrefix.substring(prefix.length());
        k.d(substring, "(this as java.lang.String).substring(startIndex)");
        return substring;
    }

    @NotNull
    public static final String x0(@NotNull String $this$removeSuffix, @NotNull CharSequence suffix) {
        k.e($this$removeSuffix, "$this$removeSuffix");
        k.e(suffix, "suffix");
        if (!W($this$removeSuffix, suffix, false, 2, (Object) null)) {
            return $this$removeSuffix;
        }
        String substring = $this$removeSuffix.substring(0, $this$removeSuffix.length() - suffix.length());
        k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    @NotNull
    public static final String z0(@NotNull String $this$removeSurrounding, @NotNull CharSequence prefix, @NotNull CharSequence suffix) {
        k.e($this$removeSurrounding, "$this$removeSurrounding");
        k.e(prefix, "prefix");
        k.e(suffix, "suffix");
        if ($this$removeSurrounding.length() < prefix.length() + suffix.length() || !M0($this$removeSurrounding, prefix, false, 2, (Object) null) || !W($this$removeSurrounding, suffix, false, 2, (Object) null)) {
            return $this$removeSurrounding;
        }
        String substring = $this$removeSurrounding.substring(prefix.length(), $this$removeSurrounding.length() - suffix.length());
        k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    @NotNull
    public static final String y0(@NotNull String $this$removeSurrounding, @NotNull CharSequence delimiter) {
        k.e($this$removeSurrounding, "$this$removeSurrounding");
        k.e(delimiter, "delimiter");
        return z0($this$removeSurrounding, delimiter, delimiter);
    }

    public static final boolean v0(@NotNull CharSequence $this$regionMatchesImpl, int thisOffset, @NotNull CharSequence other, int otherOffset, int length, boolean ignoreCase) {
        k.e($this$regionMatchesImpl, "$this$regionMatchesImpl");
        k.e(other, "other");
        if (otherOffset < 0 || thisOffset < 0 || thisOffset > $this$regionMatchesImpl.length() - length || otherOffset > other.length() - length) {
            return false;
        }
        for (int index = 0; index < length; index++) {
            if (!b.d($this$regionMatchesImpl.charAt(thisOffset + index), other.charAt(otherOffset + index), ignoreCase)) {
                return false;
            }
        }
        return true;
    }

    public static /* synthetic */ boolean L0(CharSequence charSequence, char c2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return I0(charSequence, c2, z);
    }

    public static final boolean I0(@NotNull CharSequence $this$startsWith, char c2, boolean ignoreCase) {
        k.e($this$startsWith, "$this$startsWith");
        return $this$startsWith.length() > 0 && b.d($this$startsWith.charAt(0), c2, ignoreCase);
    }

    public static /* synthetic */ boolean V(CharSequence charSequence, char c2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return T(charSequence, c2, z);
    }

    public static final boolean T(@NotNull CharSequence $this$endsWith, char c2, boolean ignoreCase) {
        k.e($this$endsWith, "$this$endsWith");
        return $this$endsWith.length() > 0 && b.d($this$endsWith.charAt(Z($this$endsWith)), c2, ignoreCase);
    }

    public static /* synthetic */ boolean M0(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return K0(charSequence, charSequence2, z);
    }

    public static final boolean K0(@NotNull CharSequence $this$startsWith, @NotNull CharSequence prefix, boolean ignoreCase) {
        k.e($this$startsWith, "$this$startsWith");
        k.e(prefix, "prefix");
        if (!ignoreCase && ($this$startsWith instanceof String) && (prefix instanceof String)) {
            return w.N((String) $this$startsWith, (String) prefix, false, 2, (Object) null);
        }
        return v0($this$startsWith, 0, prefix, 0, prefix.length(), ignoreCase);
    }

    public static final boolean J0(@NotNull CharSequence $this$startsWith, @NotNull CharSequence prefix, int startIndex, boolean ignoreCase) {
        k.e($this$startsWith, "$this$startsWith");
        k.e(prefix, "prefix");
        if (!ignoreCase && ($this$startsWith instanceof String) && (prefix instanceof String)) {
            return w.M((String) $this$startsWith, (String) prefix, startIndex, false, 4, (Object) null);
        }
        return v0($this$startsWith, startIndex, prefix, 0, prefix.length(), ignoreCase);
    }

    public static /* synthetic */ boolean W(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return U(charSequence, charSequence2, z);
    }

    public static final boolean U(@NotNull CharSequence $this$endsWith, @NotNull CharSequence suffix, boolean ignoreCase) {
        k.e($this$endsWith, "$this$endsWith");
        k.e(suffix, "suffix");
        if (!ignoreCase && ($this$endsWith instanceof String) && (suffix instanceof String)) {
            return w.x((String) $this$endsWith, (String) suffix, false, 2, (Object) null);
        }
        return v0($this$endsWith, $this$endsWith.length() - suffix.length(), suffix, 0, suffix.length(), ignoreCase);
    }

    public static final int g0(@NotNull CharSequence $this$indexOfAny, @NotNull char[] chars, int startIndex, boolean ignoreCase) {
        char[] $this$any$iv;
        k.e($this$indexOfAny, "$this$indexOfAny");
        k.e(chars, "chars");
        if (ignoreCase || chars.length != 1 || !($this$indexOfAny instanceof String)) {
            int index = n.b(startIndex, 0);
            int Z = Z($this$indexOfAny);
            if (index > Z) {
                return -1;
            }
            while (true) {
                char charAtIndex = $this$indexOfAny.charAt(index);
                char[] $this$any$iv2 = chars;
                int length = $this$any$iv2.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        $this$any$iv = null;
                        break;
                    } else if (b.d($this$any$iv2[i], charAtIndex, ignoreCase) != 0) {
                        $this$any$iv = 1;
                        break;
                    } else {
                        i++;
                    }
                }
                if ($this$any$iv != null) {
                    return index;
                }
                if (index == Z) {
                    return -1;
                }
                index++;
            }
        } else {
            return ((String) $this$indexOfAny).indexOf(l.I(chars), startIndex);
        }
    }

    public static /* synthetic */ int m0(CharSequence charSequence, char[] cArr, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = Z(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return l0(charSequence, cArr, i, z);
    }

    public static final int l0(@NotNull CharSequence $this$lastIndexOfAny, @NotNull char[] chars, int startIndex, boolean ignoreCase) {
        k.e($this$lastIndexOfAny, "$this$lastIndexOfAny");
        k.e(chars, "chars");
        if (ignoreCase || chars.length != 1 || !($this$lastIndexOfAny instanceof String)) {
            for (int index = n.e(startIndex, Z($this$lastIndexOfAny)); index >= 0; index--) {
                char charAtIndex = $this$lastIndexOfAny.charAt(index);
                char[] $this$any$iv = chars;
                int length = $this$any$iv.length;
                boolean z = false;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    } else if (b.d($this$any$iv[i], charAtIndex, ignoreCase) != 0) {
                        z = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (z) {
                    return index;
                }
            }
            return -1;
        }
        return ((String) $this$lastIndexOfAny).lastIndexOf(l.I(chars), startIndex);
    }

    static /* synthetic */ int d0(CharSequence charSequence, CharSequence charSequence2, int i, int i2, boolean z, boolean z2, int i3, Object obj) {
        return c0(charSequence, charSequence2, i, i2, z, (i3 & 16) != 0 ? false : z2);
    }

    private static final int c0(CharSequence $this$indexOf, CharSequence other, int startIndex, int endIndex, boolean ignoreCase, boolean last) {
        g gVar;
        if (!last) {
            gVar = new i(n.b(startIndex, 0), n.e(endIndex, $this$indexOf.length()));
        } else {
            gVar = n.j(n.e(startIndex, Z($this$indexOf)), n.b(endIndex, 0));
        }
        g indices = gVar;
        if (!($this$indexOf instanceof String) || !(other instanceof String)) {
            int index = indices.a();
            int b2 = indices.b();
            int e = indices.e();
            if (e >= 0) {
                if (index > b2) {
                    return -1;
                }
            } else if (index < b2) {
                return -1;
            }
            while (true) {
                if (v0(other, 0, $this$indexOf, index, other.length(), ignoreCase)) {
                    return index;
                }
                if (index == b2) {
                    return -1;
                }
                index += e;
            }
        } else {
            int index2 = indices.a();
            int b3 = indices.b();
            int e2 = indices.e();
            if (e2 >= 0) {
                if (index2 > b3) {
                    return -1;
                }
            } else if (index2 < b3) {
                return -1;
            }
            while (true) {
                if (w.B((String) other, 0, (String) $this$indexOf, index2, other.length(), ignoreCase)) {
                    return index2;
                }
                if (index2 == b3) {
                    return -1;
                }
                index2 += e2;
            }
        }
    }

    /* access modifiers changed from: private */
    public static final kotlin.n<Integer, String> X(CharSequence $this$findAnyOf, Collection<String> strings, int startIndex, boolean ignoreCase, boolean last) {
        Object element$iv;
        Object element$iv2;
        CharSequence charSequence = $this$findAnyOf;
        int i = startIndex;
        if (ignoreCase || strings.size() != 1) {
            g indices = !last ? new i(n.b(i, 0), $this$findAnyOf.length()) : n.j(n.e(i, Z($this$findAnyOf)), 0);
            if (charSequence instanceof String) {
                int a2 = indices.a();
                int b2 = indices.b();
                int e = indices.e();
                if (e < 0 ? a2 >= b2 : a2 <= b2) {
                    while (true) {
                        int index = a2;
                        Iterator<T> it = strings.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                element$iv2 = null;
                                break;
                            }
                            element$iv2 = it.next();
                            String it2 = (String) element$iv2;
                            if (w.B(it2, 0, (String) charSequence, index, it2.length(), ignoreCase)) {
                                break;
                            }
                        }
                        String matchingString = (String) element$iv2;
                        if (matchingString == null) {
                            if (index == b2) {
                                break;
                            }
                            a2 = index + e;
                        } else {
                            return t.a(Integer.valueOf(index), matchingString);
                        }
                    }
                }
            } else {
                int a3 = indices.a();
                int b3 = indices.b();
                int e2 = indices.e();
                if (e2 < 0 ? a3 >= b3 : a3 <= b3) {
                    while (true) {
                        int index2 = a3;
                        Iterator<T> it3 = strings.iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                element$iv = null;
                                break;
                            }
                            element$iv = it3.next();
                            String it4 = (String) element$iv;
                            if (v0(it4, 0, $this$findAnyOf, index2, it4.length(), ignoreCase)) {
                                break;
                            }
                        }
                        String matchingString2 = (String) element$iv;
                        if (matchingString2 == null) {
                            if (index2 == b3) {
                                break;
                            }
                            a3 = index2 + e2;
                        } else {
                            return t.a(Integer.valueOf(index2), matchingString2);
                        }
                    }
                }
            }
            return null;
        }
        String string = (String) y.p0(strings);
        CharSequence charSequence2 = $this$findAnyOf;
        String str = string;
        int i2 = startIndex;
        int index3 = !last ? f0(charSequence2, str, i2, false, 4, (Object) null) : k0(charSequence2, str, i2, false, 4, (Object) null);
        if (index3 < 0) {
            return null;
        }
        return t.a(Integer.valueOf(index3), string);
    }

    public static /* synthetic */ int e0(CharSequence charSequence, char c2, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return a0(charSequence, c2, i, z);
    }

    public static final int a0(@NotNull CharSequence $this$indexOf, char c2, int startIndex, boolean ignoreCase) {
        k.e($this$indexOf, "$this$indexOf");
        if (!ignoreCase && ($this$indexOf instanceof String)) {
            return ((String) $this$indexOf).indexOf(c2, startIndex);
        }
        return g0($this$indexOf, new char[]{c2}, startIndex, ignoreCase);
    }

    public static /* synthetic */ int f0(CharSequence charSequence, String str, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return b0(charSequence, str, i, z);
    }

    public static final int b0(@NotNull CharSequence $this$indexOf, @NotNull String string, int startIndex, boolean ignoreCase) {
        k.e($this$indexOf, "$this$indexOf");
        k.e(string, TypedValues.Custom.S_STRING);
        if (!ignoreCase && ($this$indexOf instanceof String)) {
            return ((String) $this$indexOf).indexOf(string, startIndex);
        }
        return d0($this$indexOf, string, startIndex, $this$indexOf.length(), ignoreCase, false, 16, (Object) null);
    }

    public static /* synthetic */ int j0(CharSequence charSequence, char c2, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = Z(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return h0(charSequence, c2, i, z);
    }

    public static final int h0(@NotNull CharSequence $this$lastIndexOf, char c2, int startIndex, boolean ignoreCase) {
        k.e($this$lastIndexOf, "$this$lastIndexOf");
        if (!ignoreCase && ($this$lastIndexOf instanceof String)) {
            return ((String) $this$lastIndexOf).lastIndexOf(c2, startIndex);
        }
        return l0($this$lastIndexOf, new char[]{c2}, startIndex, ignoreCase);
    }

    public static /* synthetic */ int k0(CharSequence charSequence, String str, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = Z(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return i0(charSequence, str, i, z);
    }

    public static final int i0(@NotNull CharSequence $this$lastIndexOf, @NotNull String string, int startIndex, boolean ignoreCase) {
        k.e($this$lastIndexOf, "$this$lastIndexOf");
        k.e(string, TypedValues.Custom.S_STRING);
        if (ignoreCase || !($this$lastIndexOf instanceof String)) {
            return c0($this$lastIndexOf, string, startIndex, 0, ignoreCase, true);
        }
        return ((String) $this$lastIndexOf).lastIndexOf(string, startIndex);
    }

    public static /* synthetic */ boolean S(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return Q(charSequence, charSequence2, z);
    }

    public static final boolean Q(@NotNull CharSequence $this$contains, @NotNull CharSequence other, boolean ignoreCase) {
        k.e($this$contains, "$this$contains");
        k.e(other, "other");
        if (other instanceof String) {
            if (f0($this$contains, (String) other, 0, ignoreCase, 2, (Object) null) >= 0) {
                return true;
            }
            return false;
        }
        if (d0($this$contains, other, 0, $this$contains.length(), ignoreCase, false, 16, (Object) null) >= 0) {
            return true;
        }
        return false;
    }

    public static /* synthetic */ boolean R(CharSequence charSequence, char c2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return P(charSequence, c2, z);
    }

    public static final boolean P(@NotNull CharSequence $this$contains, char c2, boolean ignoreCase) {
        k.e($this$contains, "$this$contains");
        return e0($this$contains, c2, 0, ignoreCase, 2, (Object) null) >= 0;
    }

    static /* synthetic */ h t0(CharSequence charSequence, char[] cArr, int i, boolean z, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        if ((i3 & 8) != 0) {
            i2 = 0;
        }
        return r0(charSequence, cArr, i, z, i2);
    }

    private static final h<i> r0(CharSequence $this$rangesDelimitedBy, char[] delimiters, int startIndex, boolean ignoreCase, int limit) {
        if (limit >= 0) {
            return new d($this$rangesDelimitedBy, startIndex, limit, new a(delimiters, ignoreCase));
        }
        throw new IllegalArgumentException(("Limit must be non-negative, but was " + limit + '.').toString());
    }

    /* compiled from: Strings.kt */
    public static final class a extends kotlin.jvm.internal.l implements p<CharSequence, Integer, kotlin.n<? extends Integer, ? extends Integer>> {
        final /* synthetic */ char[] $delimiters;
        final /* synthetic */ boolean $ignoreCase;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(char[] cArr, boolean z) {
            super(2);
            this.$delimiters = cArr;
            this.$ignoreCase = z;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return invoke((CharSequence) obj, ((Number) obj2).intValue());
        }

        @Nullable
        public final kotlin.n<Integer, Integer> invoke(@NotNull CharSequence $receiver, int currentIndex) {
            k.e($receiver, "$receiver");
            int it = x.g0($receiver, this.$delimiters, currentIndex, this.$ignoreCase);
            if (it < 0) {
                return null;
            }
            return t.a(Integer.valueOf(it), 1);
        }
    }

    static /* synthetic */ h u0(CharSequence charSequence, String[] strArr, int i, boolean z, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        if ((i3 & 8) != 0) {
            i2 = 0;
        }
        return s0(charSequence, strArr, i, z, i2);
    }

    private static final h<i> s0(CharSequence $this$rangesDelimitedBy, String[] delimiters, int startIndex, boolean ignoreCase, int limit) {
        if (limit >= 0) {
            return new d($this$rangesDelimitedBy, startIndex, limit, new b(kotlin.collections.k.c(delimiters), ignoreCase));
        }
        throw new IllegalArgumentException(("Limit must be non-negative, but was " + limit + '.').toString());
    }

    /* compiled from: Strings.kt */
    public static final class b extends kotlin.jvm.internal.l implements p<CharSequence, Integer, kotlin.n<? extends Integer, ? extends Integer>> {
        final /* synthetic */ List $delimitersList;
        final /* synthetic */ boolean $ignoreCase;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(List list, boolean z) {
            super(2);
            this.$delimitersList = list;
            this.$ignoreCase = z;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return invoke((CharSequence) obj, ((Number) obj2).intValue());
        }

        @Nullable
        public final kotlin.n<Integer, Integer> invoke(@NotNull CharSequence $receiver, int currentIndex) {
            k.e($receiver, "$receiver");
            kotlin.n it = x.X($receiver, this.$delimitersList, currentIndex, this.$ignoreCase, false);
            if (it != null) {
                return t.a(it.getFirst(), Integer.valueOf(((String) it.getSecond()).length()));
            }
            return null;
        }
    }

    /* compiled from: Strings.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, String> {
        final /* synthetic */ CharSequence $this_splitToSequence;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(CharSequence charSequence) {
            super(1);
            this.$this_splitToSequence = charSequence;
        }

        @NotNull
        public final String invoke(@NotNull i it) {
            k.e(it, "it");
            return x.N0(this.$this_splitToSequence, it);
        }
    }

    public static /* synthetic */ h H0(CharSequence charSequence, String[] strArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return G0(charSequence, strArr, z, i);
    }

    @NotNull
    public static final h<String> G0(@NotNull CharSequence $this$splitToSequence, @NotNull String[] delimiters, boolean ignoreCase, int limit) {
        k.e($this$splitToSequence, "$this$splitToSequence");
        k.e(delimiters, "delimiters");
        return o.w(u0($this$splitToSequence, delimiters, 0, ignoreCase, limit, 2, (Object) null), new c($this$splitToSequence));
    }

    public static /* synthetic */ List F0(CharSequence charSequence, String[] strArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return C0(charSequence, strArr, z, i);
    }

    @NotNull
    public static final List<String> C0(@NotNull CharSequence $this$split, @NotNull String[] delimiters, boolean ignoreCase, int limit) {
        k.e($this$split, "$this$split");
        k.e(delimiters, "delimiters");
        boolean z = true;
        if (delimiters.length == 1) {
            String delimiter = delimiters[0];
            if (delimiter.length() != 0) {
                z = false;
            }
            if (!z) {
                return D0($this$split, delimiter, ignoreCase, limit);
            }
        }
        Iterable<i> $this$map$iv = o.l(u0($this$split, delimiters, 0, ignoreCase, limit, 2, (Object) null));
        ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (i it : $this$map$iv) {
            arrayList.add(N0($this$split, it));
        }
        return arrayList;
    }

    public static /* synthetic */ List E0(CharSequence charSequence, char[] cArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return B0(charSequence, cArr, z, i);
    }

    @NotNull
    public static final List<String> B0(@NotNull CharSequence $this$split, @NotNull char[] delimiters, boolean ignoreCase, int limit) {
        k.e($this$split, "$this$split");
        k.e(delimiters, "delimiters");
        if (delimiters.length == 1) {
            return D0($this$split, String.valueOf(delimiters[0]), ignoreCase, limit);
        }
        Iterable<i> $this$map$iv = o.l(t0($this$split, delimiters, 0, ignoreCase, limit, 2, (Object) null));
        ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (i it : $this$map$iv) {
            arrayList.add(N0($this$split, it));
        }
        return arrayList;
    }

    private static final List<String> D0(CharSequence $this$split, String delimiter, boolean ignoreCase, int limit) {
        boolean isLimited = false;
        if (limit >= 0) {
            int currentOffset = 0;
            int nextIndex = b0($this$split, delimiter, 0, ignoreCase);
            if (nextIndex == -1 || limit == 1) {
                return kotlin.collections.p.b($this$split.toString());
            }
            if (limit > 0) {
                isLimited = true;
            }
            int i = 10;
            if (isLimited) {
                i = n.e(limit, 10);
            }
            ArrayList result = new ArrayList(i);
            do {
                result.add($this$split.subSequence(currentOffset, nextIndex).toString());
                currentOffset = nextIndex + delimiter.length();
                if ((isLimited && result.size() == limit - 1) || (nextIndex = b0($this$split, delimiter, currentOffset, ignoreCase)) == -1) {
                    result.add($this$split.subSequence(currentOffset, $this$split.length()).toString());
                }
                result.add($this$split.subSequence(currentOffset, nextIndex).toString());
                currentOffset = nextIndex + delimiter.length();
                break;
            } while ((nextIndex = b0($this$split, delimiter, currentOffset, ignoreCase)) == -1);
            result.add($this$split.subSequence(currentOffset, $this$split.length()).toString());
            return result;
        }
        throw new IllegalArgumentException(("Limit must be non-negative, but was " + limit + '.').toString());
    }

    @NotNull
    public static final h<String> n0(@NotNull CharSequence $this$lineSequence) {
        k.e($this$lineSequence, "$this$lineSequence");
        return H0($this$lineSequence, new String[]{"\r\n", "\n", "\r"}, false, 0, 6, (Object) null);
    }

    @NotNull
    public static final List<String> o0(@NotNull CharSequence $this$lines) {
        k.e($this$lines, "$this$lines");
        return o.C(n0($this$lines));
    }
}
