package okio;

import kotlin.jvm.internal.k;
import okio.internal.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: -Util.kt */
public final class i0 {
    public static final void b(long size, long offset, long byteCount) {
        if ((offset | byteCount) < 0 || offset > size || size - offset < byteCount) {
            throw new ArrayIndexOutOfBoundsException("size=" + size + " offset=" + offset + " byteCount=" + byteCount);
        }
    }

    public static final short e(short $this$reverseBytes) {
        int i = 65535 & $this$reverseBytes;
        return (short) (((65280 & i) >>> 8) | ((i & 255) << 8));
    }

    public static final int c(int $this$reverseBytes) {
        return ((-16777216 & $this$reverseBytes) >>> 24) | ((16711680 & $this$reverseBytes) >>> 8) | ((65280 & $this$reverseBytes) << 8) | (($this$reverseBytes & 255) << 24);
    }

    public static final long d(long $this$reverseBytes) {
        return ((-72057594037927936L & $this$reverseBytes) >>> 56) | ((71776119061217280L & $this$reverseBytes) >>> 40) | ((280375465082880L & $this$reverseBytes) >>> 24) | ((1095216660480L & $this$reverseBytes) >>> 8) | ((4278190080L & $this$reverseBytes) << 8) | ((16711680 & $this$reverseBytes) << 24) | ((65280 & $this$reverseBytes) << 40) | ((255 & $this$reverseBytes) << 56);
    }

    public static final boolean a(@NotNull byte[] a, int aOffset, @NotNull byte[] b, int bOffset, int byteCount) {
        k.e(a, "a");
        k.e(b, "b");
        for (int i = 0; i < byteCount; i++) {
            if (a[i + aOffset] != b[i + bOffset]) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public static final String f(byte $this$toHexString) {
        int $this$shr$iv = $this$toHexString;
        return new String(new char[]{c.f()[($this$shr$iv >> 4) & 15], c.f()[15 & $this$shr$iv]});
    }

    @NotNull
    public static final String g(int $this$toHexString) {
        if ($this$toHexString == 0) {
            return "0";
        }
        char[] result = {c.f()[($this$toHexString >> 28) & 15], c.f()[($this$toHexString >> 24) & 15], c.f()[($this$toHexString >> 20) & 15], c.f()[($this$toHexString >> 16) & 15], c.f()[($this$toHexString >> 12) & 15], c.f()[($this$toHexString >> 8) & 15], c.f()[($this$toHexString >> 4) & 15], c.f()[$this$toHexString & 15]};
        int i = 0;
        while (i < result.length && result[i] == '0') {
            i++;
        }
        return new String(result, i, result.length - i);
    }
}
