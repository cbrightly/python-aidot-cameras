package kotlin.text;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StringNumberConversions.kt */
public class v extends u {
    @Nullable
    public static final Byte n(@NotNull String $this$toByteOrNull, int radix) {
        int intValue;
        k.e($this$toByteOrNull, "$this$toByteOrNull");
        Integer p = p($this$toByteOrNull, radix);
        if (p == null || (intValue = p.intValue()) < -128 || intValue > 127) {
            return null;
        }
        return Byte.valueOf((byte) intValue);
    }

    @Nullable
    public static final Short s(@NotNull String $this$toShortOrNull, int radix) {
        int intValue;
        k.e($this$toShortOrNull, "$this$toShortOrNull");
        Integer p = p($this$toShortOrNull, radix);
        if (p == null || (intValue = p.intValue()) < -32768 || intValue > 32767) {
            return null;
        }
        return Short.valueOf((short) intValue);
    }

    @Nullable
    public static final Integer o(@NotNull String $this$toIntOrNull) {
        k.e($this$toIntOrNull, "$this$toIntOrNull");
        return p($this$toIntOrNull, 10);
    }

    @Nullable
    public static final Integer p(@NotNull String $this$toIntOrNull, int radix) {
        int limit;
        boolean isNegative;
        int start;
        int result;
        k.e($this$toIntOrNull, "$this$toIntOrNull");
        a.a(radix);
        int length = $this$toIntOrNull.length();
        if (length == 0) {
            return null;
        }
        char firstChar = $this$toIntOrNull.charAt(0);
        if (k.g(firstChar, 48) >= 0) {
            start = 0;
            isNegative = false;
            limit = -2147483647;
        } else if (length == 1) {
            return null;
        } else {
            start = 1;
            if (firstChar == '-') {
                isNegative = true;
                limit = Integer.MIN_VALUE;
            } else if (firstChar != '+') {
                return null;
            } else {
                isNegative = false;
                limit = -2147483647;
            }
        }
        int limitBeforeMul = -59652323;
        int result2 = 0;
        for (int i = start; i < length; i++) {
            int digit = a.b($this$toIntOrNull.charAt(i), radix);
            if (digit < 0) {
                return null;
            }
            if ((result2 < limitBeforeMul && (limitBeforeMul != -59652323 || result2 < (limitBeforeMul = limit / radix))) || (result = result2 * radix) < limit + digit) {
                return null;
            }
            result2 = result - digit;
        }
        return isNegative ? Integer.valueOf(result2) : Integer.valueOf(-result2);
    }

    @Nullable
    public static final Long q(@NotNull String $this$toLongOrNull) {
        k.e($this$toLongOrNull, "$this$toLongOrNull");
        return r($this$toLongOrNull, 10);
    }

    @Nullable
    public static final Long r(@NotNull String $this$toLongOrNull, int radix) {
        long limit;
        boolean isNegative;
        int start;
        long limitForMaxRadix;
        char firstChar;
        String str = $this$toLongOrNull;
        int i = radix;
        k.e(str, "$this$toLongOrNull");
        a.a(radix);
        int length = $this$toLongOrNull.length();
        if (length == 0) {
            return null;
        }
        char firstChar2 = str.charAt(0);
        if (k.g(firstChar2, 48) >= 0) {
            start = 0;
            isNegative = false;
            limit = -9223372036854775807L;
        } else if (length == 1) {
            return null;
        } else {
            start = 1;
            if (firstChar2 == '-') {
                isNegative = true;
                limit = Long.MIN_VALUE;
            } else if (firstChar2 != '+') {
                return null;
            } else {
                isNegative = false;
                limit = -9223372036854775807L;
            }
        }
        long limitForMaxRadix2 = -256204778801521550L;
        long limitBeforeMul = -256204778801521550L;
        long result = 0;
        int i2 = start;
        while (i2 < length) {
            int digit = a.b(str.charAt(i2), i);
            if (digit < 0) {
                return null;
            }
            if (result >= limitBeforeMul) {
                firstChar = firstChar2;
                limitForMaxRadix = limitForMaxRadix2;
            } else if (limitBeforeMul != limitForMaxRadix2) {
                return null;
            } else {
                firstChar = firstChar2;
                limitForMaxRadix = limitForMaxRadix2;
                long limitBeforeMul2 = limit / ((long) i);
                if (result < limitBeforeMul2) {
                    return null;
                }
                limitBeforeMul = limitBeforeMul2;
            }
            long result2 = result * ((long) i);
            if (result2 < ((long) digit) + limit) {
                return null;
            }
            result = result2 - ((long) digit);
            i2++;
            firstChar2 = firstChar;
            limitForMaxRadix2 = limitForMaxRadix;
        }
        long j = limitForMaxRadix2;
        return isNegative ? Long.valueOf(result) : Long.valueOf(-result);
    }
}
