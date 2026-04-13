package kotlin.text;

import kotlin.ranges.i;

/* compiled from: CharJVM.kt */
public class a {
    public static final boolean c(char $this$isWhitespace) {
        return Character.isWhitespace($this$isWhitespace) || Character.isSpaceChar($this$isWhitespace);
    }

    public static final int b(char c, int radix) {
        return Character.digit(c, radix);
    }

    public static final int a(int radix) {
        if (2 <= radix && 36 >= radix) {
            return radix;
        }
        throw new IllegalArgumentException("radix " + radix + " was not in valid range " + new i(2, 36));
    }
}
