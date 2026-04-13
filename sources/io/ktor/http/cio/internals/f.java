package io.ktor.http.cio.internals;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Tokenizer.kt */
public final class f {
    @NotNull
    public static final CharSequence b(@NotNull CharSequence text, @NotNull e range) {
        k.f(text, "text");
        k.f(range, "range");
        int spaceOrEnd = a(text, range);
        CharSequence s = text.subSequence(range.b(), spaceOrEnd);
        range.d(spaceOrEnd);
        return s;
    }

    public static final int d(@NotNull b text, int start, int end) {
        k.f(text, "text");
        int index = start;
        while (index < end) {
            char ch = text.charAt(index);
            if (ch != ' ' && ch != 9) {
                break;
            }
            index++;
        }
        return index;
    }

    public static final void c(@NotNull CharSequence text, @NotNull e range) {
        k.f(text, "text");
        k.f(range, "range");
        int idx = range.b();
        int end = range.a();
        if (idx < end && text.charAt(idx) == ' ') {
            int idx2 = idx + 1;
            while (idx2 < end && text.charAt(idx2) == ' ') {
                idx2++;
            }
            range.d(idx2);
        }
    }

    public static final int a(@NotNull CharSequence text, @NotNull e range) {
        k.f(text, "text");
        k.f(range, "range");
        int idx = range.b();
        int end = range.a();
        if (idx >= end || text.charAt(idx) == ' ') {
            return idx;
        }
        int idx2 = idx + 1;
        while (idx2 < end && text.charAt(idx2) != ' ') {
            idx2++;
        }
        return idx2;
    }
}
