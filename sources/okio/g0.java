package okio;

import java.util.Arrays;
import kotlin.jvm.internal.k;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: -Base64.kt */
public final class g0 {
    @NotNull
    private static final byte[] a;
    @NotNull
    private static final byte[] b;

    static {
        f.a aVar = f.Companion;
        a = aVar.d("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/").getData$okio();
        b = aVar.d("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_").getData$okio();
    }

    @NotNull
    public static final byte[] d() {
        return b;
    }

    @Nullable
    public static final byte[] a(@NotNull String $this$decodeBase64ToArray) {
        int bits;
        k.e($this$decodeBase64ToArray, "$this$decodeBase64ToArray");
        int limit = $this$decodeBase64ToArray.length();
        while (limit > 0 && ((c = $this$decodeBase64ToArray.charAt(limit - 1)) == '=' || c == 10 || c == 13 || c == ' ' || c == 9)) {
            limit--;
        }
        byte[] out = new byte[((int) ((((long) limit) * 6) / 8))];
        int outCount = 0;
        int inCount = 0;
        int word = 0;
        for (int pos = 0; pos < limit; pos++) {
            char c = $this$decodeBase64ToArray.charAt(pos);
            if ('A' <= c && 'Z' >= c) {
                bits = c - 'A';
            } else if ('a' <= c && 'z' >= c) {
                bits = c - 'G';
            } else if ('0' <= c && '9' >= c) {
                bits = c + 4;
            } else if (c == '+' || c == '-') {
                bits = 62;
            } else if (c == '/' || c == '_') {
                bits = 63;
            } else {
                if (!(c == 10 || c == 13 || c == ' ' || c == 9)) {
                    return null;
                }
            }
            word = (word << 6) | bits;
            inCount++;
            if (inCount % 4 == 0) {
                int outCount2 = outCount + 1;
                out[outCount] = (byte) (word >> 16);
                int outCount3 = outCount2 + 1;
                out[outCount2] = (byte) (word >> 8);
                out[outCount3] = (byte) word;
                outCount = outCount3 + 1;
            }
        }
        switch (inCount % 4) {
            case 1:
                return null;
            case 2:
                out[outCount] = (byte) ((word << 12) >> 16);
                outCount++;
                break;
            case 3:
                int word2 = word << 6;
                int outCount4 = outCount + 1;
                out[outCount] = (byte) (word2 >> 16);
                outCount = outCount4 + 1;
                out[outCount4] = (byte) (word2 >> 8);
                break;
        }
        if (outCount == out.length) {
            return out;
        }
        byte[] copyOf = Arrays.copyOf(out, outCount);
        k.d(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    public static /* synthetic */ String c(byte[] bArr, byte[] bArr2, int i, Object obj) {
        if ((i & 1) != 0) {
            bArr2 = a;
        }
        return b(bArr, bArr2);
    }

    @NotNull
    public static final String b(@NotNull byte[] $this$encodeBase64, @NotNull byte[] map) {
        k.e($this$encodeBase64, "$this$encodeBase64");
        k.e(map, "map");
        byte[] out = new byte[((($this$encodeBase64.length + 2) / 3) * 4)];
        int index = 0;
        int end = $this$encodeBase64.length - ($this$encodeBase64.length % 3);
        int b0 = 0;
        while (b0 < end) {
            int i = b0 + 1;
            byte b02 = $this$encodeBase64[b0];
            int i2 = i + 1;
            byte i3 = $this$encodeBase64[i];
            int i4 = i2 + 1;
            byte i5 = $this$encodeBase64[i2];
            int index2 = index + 1;
            out[index] = map[(b02 & 255) >> 2];
            int index3 = index2 + 1;
            out[index2] = map[((b02 & 3) << 4) | ((i3 & 255) >> 4)];
            int index4 = index3 + 1;
            out[index3] = map[((i3 & 15) << 2) | ((i5 & 255) >> 6)];
            index = index4 + 1;
            out[index4] = map[i5 & 63];
            b0 = i4;
        }
        switch ($this$encodeBase64.length - end) {
            case 1:
                byte b03 = $this$encodeBase64[b0];
                int index5 = index + 1;
                out[index] = map[(b03 & 255) >> 2];
                int index6 = index5 + 1;
                out[index5] = map[(b03 & 3) << 4];
                int index7 = index6 + 1;
                byte b2 = (byte) 61;
                out[index6] = b2;
                out[index7] = b2;
                int i6 = index7;
                break;
            case 2:
                int i7 = b0 + 1;
                byte b04 = $this$encodeBase64[b0];
                byte b1 = $this$encodeBase64[i7];
                int index8 = index + 1;
                out[index] = map[(b04 & 255) >> 2];
                int index9 = index8 + 1;
                out[index8] = map[((b04 & 3) << 4) | ((b1 & 255) >> 4)];
                int index10 = index9 + 1;
                out[index9] = map[(b1 & 15) << 2];
                out[index10] = (byte) 61;
                int b05 = i7;
                int i8 = index10;
                break;
        }
        return h0.b(out);
    }
}
