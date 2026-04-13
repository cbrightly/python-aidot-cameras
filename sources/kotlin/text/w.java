package kotlin.text;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import kotlin.collections.g0;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import kotlin.ranges.i;
import kotlin.ranges.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StringsJVM.kt */
public class w extends v {
    public static final boolean y(@Nullable String $this$equals, @Nullable String other, boolean ignoreCase) {
        if ($this$equals == null) {
            return other == null;
        }
        if (!ignoreCase) {
            return $this$equals.equals(other);
        }
        return $this$equals.equalsIgnoreCase(other);
    }

    public static /* synthetic */ String G(String str, char c, char c2, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return E(str, c, c2, z);
    }

    @NotNull
    public static final String E(@NotNull String $this$replace, char oldChar, char newChar, boolean ignoreCase) {
        k.e($this$replace, "$this$replace");
        if (!ignoreCase) {
            String replace = $this$replace.replace(oldChar, newChar);
            k.d(replace, "(this as java.lang.Strin…replace(oldChar, newChar)");
            return replace;
        }
        StringBuilder sb = new StringBuilder($this$replace.length());
        StringBuilder $this$buildString = sb;
        CharSequence $this$forEach$iv = $this$replace;
        for (int i = 0; i < $this$forEach$iv.length(); i++) {
            char c = $this$forEach$iv.charAt(i);
            $this$buildString.append(b.d(c, oldChar, ignoreCase) ? newChar : c);
        }
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    public static /* synthetic */ String H(String str, String str2, String str3, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return F(str, str2, str3, z);
    }

    @NotNull
    public static final String F(@NotNull String $this$replace, @NotNull String oldValue, @NotNull String newValue, boolean ignoreCase) {
        k.e($this$replace, "$this$replace");
        k.e(oldValue, "oldValue");
        k.e(newValue, "newValue");
        String $this$run = $this$replace;
        int occurrenceIndex = x.b0($this$run, oldValue, 0, ignoreCase);
        if (occurrenceIndex < 0) {
            return $this$run;
        }
        int oldValueLength = oldValue.length();
        int searchStep = n.b(oldValueLength, 1);
        int newLengthHint = ($this$run.length() - oldValueLength) + newValue.length();
        if (newLengthHint >= 0) {
            StringBuilder stringBuilder = new StringBuilder(newLengthHint);
            int i = 0;
            do {
                stringBuilder.append($this$run, i, occurrenceIndex);
                stringBuilder.append(newValue);
                i = occurrenceIndex + oldValueLength;
                if (occurrenceIndex >= $this$run.length() || (occurrenceIndex = x.b0($this$run, oldValue, occurrenceIndex + searchStep, ignoreCase)) <= 0) {
                    stringBuilder.append($this$run, i, $this$run.length());
                    String sb = stringBuilder.toString();
                    k.d(sb, "stringBuilder.append(this, i, length).toString()");
                }
                stringBuilder.append($this$run, i, occurrenceIndex);
                stringBuilder.append(newValue);
                i = occurrenceIndex + oldValueLength;
                break;
            } while ((occurrenceIndex = x.b0($this$run, oldValue, occurrenceIndex + searchStep, ignoreCase)) <= 0);
            stringBuilder.append($this$run, i, $this$run.length());
            String sb2 = stringBuilder.toString();
            k.d(sb2, "stringBuilder.append(this, i, length).toString()");
            return sb2;
        }
        throw new OutOfMemoryError();
    }

    public static /* synthetic */ String J(String str, String str2, String str3, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return I(str, str2, str3, z);
    }

    @NotNull
    public static final String I(@NotNull String $this$replaceFirst, @NotNull String oldValue, @NotNull String newValue, boolean ignoreCase) {
        k.e($this$replaceFirst, "$this$replaceFirst");
        k.e(oldValue, "oldValue");
        k.e(newValue, "newValue");
        int index = x.f0($this$replaceFirst, oldValue, 0, ignoreCase, 2, (Object) null);
        return index < 0 ? $this$replaceFirst : x.A0($this$replaceFirst, index, oldValue.length() + index, newValue).toString();
    }

    public static /* synthetic */ boolean N(String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return L(str, str2, z);
    }

    public static final boolean L(@NotNull String $this$startsWith, @NotNull String prefix, boolean ignoreCase) {
        k.e($this$startsWith, "$this$startsWith");
        k.e(prefix, "prefix");
        if (!ignoreCase) {
            return $this$startsWith.startsWith(prefix);
        }
        return B($this$startsWith, 0, prefix, 0, prefix.length(), ignoreCase);
    }

    public static /* synthetic */ boolean M(String str, String str2, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        return K(str, str2, i, z);
    }

    public static final boolean K(@NotNull String $this$startsWith, @NotNull String prefix, int startIndex, boolean ignoreCase) {
        k.e($this$startsWith, "$this$startsWith");
        k.e(prefix, "prefix");
        if (!ignoreCase) {
            return $this$startsWith.startsWith(prefix, startIndex);
        }
        return B($this$startsWith, startIndex, prefix, 0, prefix.length(), ignoreCase);
    }

    public static /* synthetic */ boolean x(String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return w(str, str2, z);
    }

    public static final boolean w(@NotNull String $this$endsWith, @NotNull String suffix, boolean ignoreCase) {
        k.e($this$endsWith, "$this$endsWith");
        k.e(suffix, "suffix");
        if (!ignoreCase) {
            return $this$endsWith.endsWith(suffix);
        }
        return B($this$endsWith, $this$endsWith.length() - suffix.length(), suffix, 0, suffix.length(), true);
    }

    public static final boolean A(@NotNull CharSequence $this$isBlank) {
        Iterable $this$all$iv;
        k.e($this$isBlank, "$this$isBlank");
        if ($this$isBlank.length() != 0) {
            i Y = x.Y($this$isBlank);
            if (!(Y instanceof Collection) || !((Collection) Y).isEmpty()) {
                Iterator it = Y.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (a.c($this$isBlank.charAt(((g0) it).nextInt())) == 0) {
                            $this$all$iv = null;
                            break;
                        }
                    } else {
                        $this$all$iv = 1;
                        break;
                    }
                }
            } else {
                $this$all$iv = 1;
            }
            if ($this$all$iv == null) {
                return false;
            }
        }
        return true;
    }

    public static /* synthetic */ boolean C(String str, int i, String str2, int i2, int i3, boolean z, int i4, Object obj) {
        return B(str, i, str2, i2, i3, (i4 & 16) != 0 ? false : z);
    }

    public static final boolean B(@NotNull String $this$regionMatches, int thisOffset, @NotNull String other, int otherOffset, int length, boolean ignoreCase) {
        k.e($this$regionMatches, "$this$regionMatches");
        k.e(other, "other");
        if (!ignoreCase) {
            return $this$regionMatches.regionMatches(thisOffset, other, otherOffset, length);
        }
        return $this$regionMatches.regionMatches(ignoreCase, thisOffset, other, otherOffset, length);
    }

    @NotNull
    public static final String t(@NotNull String $this$capitalize) {
        k.e($this$capitalize, "$this$capitalize");
        Locale locale = Locale.getDefault();
        k.d(locale, "Locale.getDefault()");
        return u($this$capitalize, locale);
    }

    @NotNull
    public static final String u(@NotNull String $this$capitalize, @NotNull Locale locale) {
        k.e($this$capitalize, "$this$capitalize");
        k.e(locale, "locale");
        if ($this$capitalize.length() > 0) {
            char firstChar = $this$capitalize.charAt(0);
            if (Character.isLowerCase(firstChar)) {
                StringBuilder sb = new StringBuilder();
                StringBuilder $this$buildString = sb;
                char titleChar = Character.toTitleCase(firstChar);
                if (titleChar != Character.toUpperCase(firstChar)) {
                    $this$buildString.append(titleChar);
                } else {
                    String substring = $this$capitalize.substring(0, 1);
                    k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    if (substring != null) {
                        String upperCase = substring.toUpperCase(locale);
                        k.d(upperCase, "(this as java.lang.String).toUpperCase(locale)");
                        $this$buildString.append(upperCase);
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                }
                String substring2 = $this$capitalize.substring(1);
                k.d(substring2, "(this as java.lang.String).substring(startIndex)");
                $this$buildString.append(substring2);
                String sb2 = sb.toString();
                k.d(sb2, "StringBuilder().apply(builderAction).toString()");
                return sb2;
            }
        }
        return $this$capitalize;
    }

    @NotNull
    public static final String v(@NotNull String $this$decapitalize) {
        k.e($this$decapitalize, "$this$decapitalize");
        if (!($this$decapitalize.length() > 0) || Character.isLowerCase($this$decapitalize.charAt(0))) {
            return $this$decapitalize;
        }
        StringBuilder sb = new StringBuilder();
        String substring = $this$decapitalize.substring(0, 1);
        k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        if (substring != null) {
            String lowerCase = substring.toLowerCase();
            k.d(lowerCase, "(this as java.lang.String).toLowerCase()");
            sb.append(lowerCase);
            String substring2 = $this$decapitalize.substring(1);
            k.d(substring2, "(this as java.lang.String).substring(startIndex)");
            sb.append(substring2);
            return sb.toString();
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
    }

    @NotNull
    public static final String D(@NotNull CharSequence $this$repeat, int n) {
        k.e($this$repeat, "$this$repeat");
        int i = 1;
        if (n >= 0) {
            switch (n) {
                case 0:
                    return "";
                case 1:
                    return $this$repeat.toString();
                default:
                    switch ($this$repeat.length()) {
                        case 0:
                            return "";
                        case 1:
                            char charAt = $this$repeat.charAt(0);
                            char[] cArr = new char[n];
                            for (int i2 = 0; i2 < n; i2++) {
                                int i3 = i2;
                                cArr[i2] = charAt;
                            }
                            return new String(cArr);
                        default:
                            StringBuilder sb = new StringBuilder($this$repeat.length() * n);
                            if (1 <= n) {
                                while (true) {
                                    sb.append($this$repeat);
                                    if (i != n) {
                                        i++;
                                    }
                                }
                            }
                            String sb2 = sb.toString();
                            k.d(sb2, "sb.toString()");
                            return sb2;
                    }
            }
        } else {
            throw new IllegalArgumentException(("Count 'n' must be non-negative, but was " + n + '.').toString());
        }
    }

    @NotNull
    public static final Comparator<String> z(@NotNull d0 $this$CASE_INSENSITIVE_ORDER) {
        k.e($this$CASE_INSENSITIVE_ORDER, "$this$CASE_INSENSITIVE_ORDER");
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        k.d(comparator, "java.lang.String.CASE_INSENSITIVE_ORDER");
        return comparator;
    }
}
