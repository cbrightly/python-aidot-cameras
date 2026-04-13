package io.ktor.util;

import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Text.kt */
public final class a0 {
    @NotNull
    public static final String c(@NotNull String $this$toLowerCasePreservingASCIIRules) {
        k.f($this$toLowerCasePreservingASCIIRules, "$this$toLowerCasePreservingASCIIRules");
        CharSequence $this$indexOfFirst$iv = $this$toLowerCasePreservingASCIIRules;
        int length = $this$indexOfFirst$iv.length();
        int index$iv = 0;
        while (true) {
            if (index$iv >= length) {
                index$iv = -1;
                break;
            }
            char it = $this$indexOfFirst$iv.charAt(index$iv);
            if (b(it) != it) {
                break;
            }
            index$iv++;
        }
        int firstIndex = index$iv;
        if (firstIndex == -1) {
            return $this$toLowerCasePreservingASCIIRules;
        }
        String original = $this$toLowerCasePreservingASCIIRules;
        StringBuilder sb = new StringBuilder($this$toLowerCasePreservingASCIIRules.length());
        StringBuilder $this$buildString = sb;
        $this$buildString.append(original, 0, firstIndex);
        int Z = x.Z(original);
        if (firstIndex <= Z) {
            int index = firstIndex;
            while (true) {
                $this$buildString.append(b(original.charAt(index)));
                if (index == Z) {
                    break;
                }
                index++;
            }
        }
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    private static final char b(char ch) {
        if ('A' <= ch && 'Z' >= ch) {
            return (char) (ch + ' ');
        }
        if (ch >= 0 && 127 >= ch) {
            return ch;
        }
        return Character.toLowerCase(ch);
    }

    @NotNull
    public static final h a(@NotNull String $this$caseInsensitive) {
        k.f($this$caseInsensitive, "$this$caseInsensitive");
        return new h($this$caseInsensitive);
    }
}
