package io.ktor.utils.io.internal;

import io.ktor.utils.io.charsets.c;
import io.netty.util.internal.StringUtil;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Strings.kt */
public final class m {
    public static final long a(@NotNull ByteBuffer $this$decodeASCIILine, @NotNull char[] out, int offset, int length) {
        k.f($this$decodeASCIILine, "$this$decodeASCIILine");
        k.f(out, "out");
        if ($this$decodeASCIILine.hasArray()) {
            return b($this$decodeASCIILine, out, offset, length);
        }
        return c($this$decodeASCIILine, out, offset, length);
    }

    private static final long b(@NotNull ByteBuffer $this$decodeASCIILine_array, char[] out, int offset, int length) {
        long j;
        boolean z;
        ByteBuffer byteBuffer = $this$decodeASCIILine_array;
        char[] cArr = out;
        boolean cr = false;
        ByteBuffer $this$decodeASCII3_array$iv = $this$decodeASCIILine_array;
        int pos$iv = offset;
        int end$iv = offset + length;
        byte[] array$iv = $this$decodeASCII3_array$iv.array();
        if (array$iv == null) {
            k.n();
        }
        int srcPos$iv = $this$decodeASCII3_array$iv.arrayOffset() + $this$decodeASCII3_array$iv.position();
        int srcEnd$iv = $this$decodeASCII3_array$iv.remaining() + srcPos$iv;
        int length2 = cArr.length;
        char c = StringUtil.CARRIAGE_RETURN;
        if (end$iv <= length2 && srcEnd$iv <= array$iv.length) {
            while (srcPos$iv < srcEnd$iv) {
                byte b$iv = array$iv[srcPos$iv];
                if (b$iv < 0) {
                    break;
                }
                char ch$iv = (char) b$iv;
                char ch = ch$iv;
                if (ch == c) {
                    cr = true;
                    z = true;
                } else if (ch == 10) {
                    cr = false;
                    z = false;
                } else if (cr) {
                    z = false;
                } else {
                    z = true;
                }
                if (!z) {
                    $this$decodeASCII3_array$iv.position(srcPos$iv - $this$decodeASCII3_array$iv.arrayOffset());
                    j = c.k(pos$iv - offset, -1);
                    break;
                } else if (pos$iv >= end$iv) {
                    break;
                } else {
                    cArr[pos$iv] = ch$iv;
                    pos$iv++;
                    srcPos$iv++;
                    c = StringUtil.CARRIAGE_RETURN;
                }
            }
            $this$decodeASCII3_array$iv.position(srcPos$iv - $this$decodeASCII3_array$iv.arrayOffset());
        }
        j = c.k(pos$iv - offset, 0);
        long rc = j;
        if (((int) (4294967295L & rc)) == -1) {
            int decoded = (int) (rc >> 32);
            if (cr) {
                return c.k(decoded - 1, -1);
            }
            byteBuffer.position($this$decodeASCIILine_array.position() + 1);
            if (decoded > 0 && cArr[decoded - 1] == 13) {
                return c.k(decoded - 1, -1);
            }
        } else if (cr) {
            byteBuffer.position($this$decodeASCIILine_array.position() - 1);
            return c.k(((int) (rc >> 32)) - 1, 2);
        }
        return rc;
    }

    private static final long c(@NotNull ByteBuffer $this$decodeASCIILine_buffer, char[] out, int offset, int length) {
        boolean z;
        ByteBuffer byteBuffer = $this$decodeASCIILine_buffer;
        char[] cArr = out;
        boolean cr = false;
        ByteBuffer $this$decodeASCII3_buffer$iv = $this$decodeASCIILine_buffer;
        int pos$iv = offset;
        int end$iv = offset + length;
        boolean pushBack$iv = false;
        boolean predicateFailed$iv = false;
        if (end$iv <= cArr.length) {
            while (true) {
                if (!$this$decodeASCII3_buffer$iv.hasRemaining()) {
                    break;
                }
                byte b$iv = $this$decodeASCII3_buffer$iv.get();
                if (b$iv < 0) {
                    pushBack$iv = true;
                    break;
                }
                char ch$iv = (char) b$iv;
                char ch = ch$iv;
                if (ch == 13) {
                    cr = true;
                    z = true;
                } else if (ch == 10) {
                    cr = false;
                    z = false;
                } else if (cr) {
                    z = false;
                } else {
                    z = true;
                }
                if (!z) {
                    pushBack$iv = true;
                    predicateFailed$iv = true;
                    break;
                } else if (pos$iv >= end$iv) {
                    pushBack$iv = true;
                    break;
                } else {
                    cArr[pos$iv] = ch$iv;
                    pos$iv++;
                }
            }
        }
        if (pushBack$iv) {
            $this$decodeASCII3_buffer$iv.position($this$decodeASCII3_buffer$iv.position() - 1);
        }
        long rc = c.k(pos$iv - offset, predicateFailed$iv ? -1 : 0);
        if (((int) (4294967295L & rc)) == -1) {
            int decoded = (int) (rc >> 32);
            if (cr) {
                byteBuffer.position($this$decodeASCIILine_buffer.position() - 1);
                return c.k(decoded - 1, -1);
            }
            byteBuffer.position($this$decodeASCIILine_buffer.position() + 1);
            if (decoded > 0 && cArr[decoded - 1] == 13) {
                return c.k(decoded - 1, -1);
            }
        } else if (cr) {
            byteBuffer.position($this$decodeASCIILine_buffer.position() - 1);
            return c.k(((int) (rc >> 32)) - 1, 2);
        }
        return rc;
    }
}
