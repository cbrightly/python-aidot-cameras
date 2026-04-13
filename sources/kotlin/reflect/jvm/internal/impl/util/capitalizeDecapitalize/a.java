package kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize;

import java.util.Iterator;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: capitalizeDecapitalize.kt */
public final class a {
    @NotNull
    public static final String c(@NotNull String $this$decapitalizeSmartForCompiler, boolean asciiOnly) {
        Object element$iv;
        k.f($this$decapitalizeSmartForCompiler, "$this$decapitalizeSmartForCompiler");
        if (($this$decapitalizeSmartForCompiler.length() == 0) || !d($this$decapitalizeSmartForCompiler, 0, asciiOnly)) {
            return $this$decapitalizeSmartForCompiler;
        }
        if ($this$decapitalizeSmartForCompiler.length() == 1 || !d($this$decapitalizeSmartForCompiler, 1, asciiOnly)) {
            return asciiOnly ? b($this$decapitalizeSmartForCompiler) : w.v($this$decapitalizeSmartForCompiler);
        }
        Iterator it = x.Y($this$decapitalizeSmartForCompiler).iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            if ((d($this$decapitalizeSmartForCompiler, ((Number) element$iv).intValue(), asciiOnly) ^ 1) != 0) {
                break;
            }
        }
        Integer num = (Integer) element$iv;
        if (num == null) {
            return e($this$decapitalizeSmartForCompiler, asciiOnly);
        }
        int secondWordStart = num.intValue() - 1;
        StringBuilder sb = new StringBuilder();
        String substring = $this$decapitalizeSmartForCompiler.substring(0, secondWordStart);
        k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        sb.append(e(substring, asciiOnly));
        String substring2 = $this$decapitalizeSmartForCompiler.substring(secondWordStart);
        k.b(substring2, "(this as java.lang.String).substring(startIndex)");
        sb.append(substring2);
        return sb.toString();
    }

    private static final boolean d(@NotNull String $this$isUpperCaseCharAt, int index, boolean asciiOnly) {
        char c = $this$isUpperCaseCharAt.charAt(index);
        if (asciiOnly) {
            return 'A' <= c && 'Z' >= c;
        }
        return Character.isUpperCase(c);
    }

    private static final String e(String string, boolean asciiOnly) {
        if (asciiOnly) {
            return f(string);
        }
        if (string != null) {
            String lowerCase = string.toLowerCase();
            k.b(lowerCase, "(this as java.lang.String).toLowerCase()");
            return lowerCase;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    @NotNull
    public static final String a(@NotNull String $this$capitalizeAsciiOnly) {
        k.f($this$capitalizeAsciiOnly, "$this$capitalizeAsciiOnly");
        if ($this$capitalizeAsciiOnly.length() == 0) {
            return $this$capitalizeAsciiOnly;
        }
        char c = $this$capitalizeAsciiOnly.charAt(0);
        if ('a' > c || 'z' < c) {
            return $this$capitalizeAsciiOnly;
        }
        char upperCase = Character.toUpperCase(c);
        String substring = $this$capitalizeAsciiOnly.substring(1);
        k.b(substring, "(this as java.lang.String).substring(startIndex)");
        return String.valueOf(upperCase) + substring;
    }

    @NotNull
    public static final String b(@NotNull String $this$decapitalizeAsciiOnly) {
        k.f($this$decapitalizeAsciiOnly, "$this$decapitalizeAsciiOnly");
        if ($this$decapitalizeAsciiOnly.length() == 0) {
            return $this$decapitalizeAsciiOnly;
        }
        char c = $this$decapitalizeAsciiOnly.charAt(0);
        if ('A' > c || 'Z' < c) {
            return $this$decapitalizeAsciiOnly;
        }
        char lowerCase = Character.toLowerCase(c);
        String substring = $this$decapitalizeAsciiOnly.substring(1);
        k.b(substring, "(this as java.lang.String).substring(startIndex)");
        return String.valueOf(lowerCase) + substring;
    }

    @NotNull
    public static final String f(@NotNull String $this$toLowerCaseAsciiOnly) {
        k.f($this$toLowerCaseAsciiOnly, "$this$toLowerCaseAsciiOnly");
        StringBuilder builder = new StringBuilder($this$toLowerCaseAsciiOnly.length());
        int length = $this$toLowerCaseAsciiOnly.length();
        for (int i = 0; i < length; i++) {
            char c = $this$toLowerCaseAsciiOnly.charAt(i);
            builder.append(('A' <= c && 'Z' >= c) ? Character.toLowerCase(c) : c);
        }
        String sb = builder.toString();
        k.b(sb, "builder.toString()");
        return sb;
    }
}
