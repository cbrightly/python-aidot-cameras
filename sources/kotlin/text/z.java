package kotlin.text;

import java.util.NoSuchElementException;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: _Strings.kt */
public class z extends y {
    public static final char h1(@NotNull CharSequence $this$last) {
        k.e($this$last, "$this$last");
        if (!($this$last.length() == 0)) {
            return $this$last.charAt(x.Z($this$last));
        }
        throw new NoSuchElementException("Char sequence is empty.");
    }

    @Nullable
    public static final Character i1(@NotNull CharSequence $this$singleOrNull) {
        k.e($this$singleOrNull, "$this$singleOrNull");
        if ($this$singleOrNull.length() == 1) {
            return Character.valueOf($this$singleOrNull.charAt(0));
        }
        return null;
    }

    @NotNull
    public static final String f1(@NotNull String $this$drop, int n) {
        k.e($this$drop, "$this$drop");
        if (n >= 0) {
            String substring = $this$drop.substring(n.e(n, $this$drop.length()));
            k.d(substring, "(this as java.lang.String).substring(startIndex)");
            return substring;
        }
        throw new IllegalArgumentException(("Requested character count " + n + " is less than zero.").toString());
    }

    @NotNull
    public static final String g1(@NotNull String $this$dropLast, int n) {
        k.e($this$dropLast, "$this$dropLast");
        if (n >= 0) {
            return j1($this$dropLast, n.b($this$dropLast.length() - n, 0));
        }
        throw new IllegalArgumentException(("Requested character count " + n + " is less than zero.").toString());
    }

    @NotNull
    public static final String j1(@NotNull String $this$take, int n) {
        k.e($this$take, "$this$take");
        if (n >= 0) {
            String substring = $this$take.substring(0, n.e(n, $this$take.length()));
            k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return substring;
        }
        throw new IllegalArgumentException(("Requested character count " + n + " is less than zero.").toString());
    }
}
