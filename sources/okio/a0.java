package okio;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.jvm.internal.k;
import okio.internal.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SegmentedByteString.kt */
public final class a0 extends f {
    @NotNull
    private final transient byte[][] f;
    @NotNull
    private final transient int[] q;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public a0(@NotNull byte[][] segments, @NotNull int[] directory) {
        super(f.EMPTY.getData$okio());
        k.e(segments, "segments");
        k.e(directory, "directory");
        this.f = segments;
        this.q = directory;
    }

    @NotNull
    public final byte[][] getSegments$okio() {
        return this.f;
    }

    @NotNull
    public final int[] getDirectory$okio() {
        return this.q;
    }

    @NotNull
    public String string(@NotNull Charset charset) {
        k.e(charset, "charset");
        return a().string(charset);
    }

    @NotNull
    public String base64() {
        return a().base64();
    }

    @NotNull
    public String hex() {
        return a().hex();
    }

    @NotNull
    public f toAsciiLowercase() {
        return a().toAsciiLowercase();
    }

    @NotNull
    public f toAsciiUppercase() {
        return a().toAsciiUppercase();
    }

    @NotNull
    public f digest$okio(@NotNull String algorithm) {
        k.e(algorithm, "algorithm");
        MessageDigest $this$run = MessageDigest.getInstance(algorithm);
        int segmentCount$iv = getSegments$okio().length;
        int pos$iv = 0;
        for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
            int segmentPos$iv = getDirectory$okio()[segmentCount$iv + s$iv];
            int nextSegmentOffset$iv = getDirectory$okio()[s$iv];
            $this$run.update(getSegments$okio()[s$iv], segmentPos$iv, nextSegmentOffset$iv - pos$iv);
            pos$iv = nextSegmentOffset$iv;
        }
        byte[] digestBytes = $this$run.digest();
        k.d(digestBytes, "digestBytes");
        return new f(digestBytes);
    }

    @NotNull
    public f hmac$okio(@NotNull String algorithm, @NotNull f key) {
        k.e(algorithm, "algorithm");
        k.e(key, CacheEntity.KEY);
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key.toByteArray(), algorithm));
            int segmentCount$iv = getSegments$okio().length;
            int pos$iv = 0;
            for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
                int segmentPos$iv = getDirectory$okio()[segmentCount$iv + s$iv];
                int nextSegmentOffset$iv = getDirectory$okio()[s$iv];
                mac.update(getSegments$okio()[s$iv], segmentPos$iv, nextSegmentOffset$iv - pos$iv);
                pos$iv = nextSegmentOffset$iv;
            }
            byte[] doFinal = mac.doFinal();
            k.d(doFinal, "mac.doFinal()");
            return new f(doFinal);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @NotNull
    public String base64Url() {
        return a().base64Url();
    }

    @NotNull
    public f substring(int beginIndex, int endIndex) {
        int index$iv;
        int segmentOffset$iv = 0;
        boolean z = true;
        if (beginIndex >= 0) {
            if (endIndex <= size()) {
                int subLen$iv = endIndex - beginIndex;
                if (subLen$iv < 0) {
                    z = false;
                }
                if (!z) {
                    throw new IllegalArgumentException(("endIndex=" + endIndex + " < beginIndex=" + beginIndex).toString());
                } else if (beginIndex == 0 && endIndex == size()) {
                    return this;
                } else {
                    if (beginIndex == endIndex) {
                        return f.EMPTY;
                    }
                    int beginSegment$iv = d.b(this, beginIndex);
                    int endSegment$iv = d.b(this, endIndex - 1);
                    byte[][] newSegments$iv = (byte[][]) kotlin.collections.k.i(getSegments$okio(), beginSegment$iv, endSegment$iv + 1);
                    int[] newDirectory$iv = new int[(newSegments$iv.length * 2)];
                    int index$iv2 = 0;
                    if (beginSegment$iv <= endSegment$iv) {
                        int s$iv = beginSegment$iv;
                        while (true) {
                            newDirectory$iv[index$iv2] = Math.min(getDirectory$okio()[s$iv] - beginIndex, subLen$iv);
                            index$iv = index$iv2 + 1;
                            newDirectory$iv[index$iv2 + newSegments$iv.length] = getDirectory$okio()[getSegments$okio().length + s$iv];
                            if (s$iv == endSegment$iv) {
                                break;
                            }
                            s$iv++;
                            index$iv2 = index$iv;
                        }
                        int i = index$iv;
                    }
                    if (beginSegment$iv != 0) {
                        segmentOffset$iv = getDirectory$okio()[beginSegment$iv - 1];
                    }
                    int length = newSegments$iv.length;
                    newDirectory$iv[length] = newDirectory$iv[length] + (beginIndex - segmentOffset$iv);
                    return new a0(newSegments$iv, newDirectory$iv);
                }
            } else {
                throw new IllegalArgumentException(("endIndex=" + endIndex + " > length(" + size() + ')').toString());
            }
        } else {
            throw new IllegalArgumentException(("beginIndex=" + beginIndex + " < 0").toString());
        }
    }

    public byte internalGet$okio(int pos) {
        i0.b((long) getDirectory$okio()[getSegments$okio().length - 1], (long) pos, 1);
        int segment$iv = d.b(this, pos);
        return getSegments$okio()[segment$iv][(pos - (segment$iv == 0 ? 0 : getDirectory$okio()[segment$iv - 1])) + getDirectory$okio()[getSegments$okio().length + segment$iv]];
    }

    public int getSize$okio() {
        return getDirectory$okio()[getSegments$okio().length - 1];
    }

    @NotNull
    public byte[] toByteArray() {
        byte[] result$iv = new byte[size()];
        int resultPos$iv = 0;
        int segmentCount$iv$iv = getSegments$okio().length;
        int pos$iv$iv = 0;
        for (int s$iv$iv = 0; s$iv$iv < segmentCount$iv$iv; s$iv$iv++) {
            int segmentPos$iv$iv = getDirectory$okio()[segmentCount$iv$iv + s$iv$iv];
            int nextSegmentOffset$iv$iv = getDirectory$okio()[s$iv$iv];
            int byteCount$iv = nextSegmentOffset$iv$iv - pos$iv$iv;
            int offset$iv = segmentPos$iv$iv;
            kotlin.collections.k.d(getSegments$okio()[s$iv$iv], result$iv, resultPos$iv, offset$iv, offset$iv + byteCount$iv);
            resultPos$iv += byteCount$iv;
            pos$iv$iv = nextSegmentOffset$iv$iv;
        }
        return result$iv;
    }

    @NotNull
    public ByteBuffer asByteBuffer() {
        ByteBuffer asReadOnlyBuffer = ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
        k.d(asReadOnlyBuffer, "ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer()");
        return asReadOnlyBuffer;
    }

    public void write(@NotNull OutputStream out) {
        k.e(out, "out");
        int segmentCount$iv = getSegments$okio().length;
        int pos$iv = 0;
        for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
            int segmentPos$iv = getDirectory$okio()[segmentCount$iv + s$iv];
            int nextSegmentOffset$iv = getDirectory$okio()[s$iv];
            out.write(getSegments$okio()[s$iv], segmentPos$iv, nextSegmentOffset$iv - pos$iv);
            pos$iv = nextSegmentOffset$iv;
        }
    }

    public void write$okio(@NotNull c buffer, int offset, int byteCount) {
        a0 $this$commonWrite$iv;
        c cVar = buffer;
        int i = offset;
        int i2 = byteCount;
        k.e(cVar, "buffer");
        a0 $this$commonWrite$iv2 = this;
        int endIndex$iv$iv = i + i2;
        a0 $this$forEachSegment$iv$iv = $this$commonWrite$iv2;
        int s$iv$iv = d.b($this$forEachSegment$iv$iv, i);
        int pos$iv$iv = offset;
        while (pos$iv$iv < endIndex$iv$iv) {
            int segmentOffset$iv$iv = s$iv$iv == 0 ? 0 : $this$forEachSegment$iv$iv.getDirectory$okio()[s$iv$iv - 1];
            int segmentPos$iv$iv = $this$forEachSegment$iv$iv.getDirectory$okio()[$this$forEachSegment$iv$iv.getSegments$okio().length + s$iv$iv];
            int byteCount$iv$iv = Math.min(endIndex$iv$iv, segmentOffset$iv$iv + ($this$forEachSegment$iv$iv.getDirectory$okio()[s$iv$iv] - segmentOffset$iv$iv)) - pos$iv$iv;
            int offset$iv = (pos$iv$iv - segmentOffset$iv$iv) + segmentPos$iv$iv;
            y segment$iv = new y($this$forEachSegment$iv$iv.getSegments$okio()[s$iv$iv], offset$iv, offset$iv + byteCount$iv$iv, true, false);
            y yVar = cVar.c;
            if (yVar == null) {
                y segment$iv2 = segment$iv;
                segment$iv2.h = segment$iv2;
                segment$iv2.g = segment$iv2;
                cVar.c = segment$iv2;
                $this$commonWrite$iv = $this$commonWrite$iv2;
            } else {
                y segment$iv3 = segment$iv;
                $this$commonWrite$iv = $this$commonWrite$iv2;
                k.c(yVar);
                y yVar2 = yVar.h;
                k.c(yVar2);
                yVar2.c(segment$iv3);
            }
            pos$iv$iv += byteCount$iv$iv;
            s$iv$iv++;
            int i3 = offset;
            $this$commonWrite$iv2 = $this$commonWrite$iv;
        }
        cVar.d1(buffer.e1() + ((long) i2));
    }

    public boolean rangeEquals(int offset, @NotNull f other, int otherOffset, int byteCount) {
        int i = offset;
        f fVar = other;
        k.e(fVar, "other");
        a0 $this$commonRangeEquals$iv = this;
        if (i < 0) {
        } else if (i > $this$commonRangeEquals$iv.size() - byteCount) {
            a0 a0Var = $this$commonRangeEquals$iv;
        } else {
            int otherOffset$iv = otherOffset;
            int endIndex$iv$iv = i + byteCount;
            a0 $this$forEachSegment$iv$iv = $this$commonRangeEquals$iv;
            int s$iv$iv = d.b($this$forEachSegment$iv$iv, i);
            int pos$iv$iv = offset;
            while (pos$iv$iv < endIndex$iv$iv) {
                int segmentOffset$iv$iv = s$iv$iv == 0 ? 0 : $this$forEachSegment$iv$iv.getDirectory$okio()[s$iv$iv - 1];
                int segmentPos$iv$iv = $this$forEachSegment$iv$iv.getDirectory$okio()[$this$forEachSegment$iv$iv.getSegments$okio().length + s$iv$iv];
                int byteCount$iv$iv = Math.min(endIndex$iv$iv, segmentOffset$iv$iv + ($this$forEachSegment$iv$iv.getDirectory$okio()[s$iv$iv] - segmentOffset$iv$iv)) - pos$iv$iv;
                int byteCount$iv = byteCount$iv$iv;
                a0 $this$commonRangeEquals$iv2 = $this$commonRangeEquals$iv;
                if (fVar.rangeEquals(otherOffset$iv, $this$forEachSegment$iv$iv.getSegments$okio()[s$iv$iv], (pos$iv$iv - segmentOffset$iv$iv) + segmentPos$iv$iv, byteCount$iv) == 0) {
                    return false;
                }
                otherOffset$iv += byteCount$iv;
                pos$iv$iv += byteCount$iv$iv;
                s$iv$iv++;
                int i2 = offset;
                $this$commonRangeEquals$iv = $this$commonRangeEquals$iv2;
            }
            return true;
        }
        return false;
    }

    public boolean rangeEquals(int offset, @NotNull byte[] other, int otherOffset, int byteCount) {
        int i = offset;
        byte[] bArr = other;
        int i2 = otherOffset;
        k.e(bArr, "other");
        if (i < 0 || i > size() - byteCount || i2 < 0 || i2 > bArr.length - byteCount) {
            return false;
        }
        int otherOffset$iv = otherOffset;
        int endIndex$iv$iv = i + byteCount;
        int s$iv$iv = d.b(this, i);
        int pos$iv$iv = offset;
        while (pos$iv$iv < endIndex$iv$iv) {
            int segmentOffset$iv$iv = s$iv$iv == 0 ? 0 : getDirectory$okio()[s$iv$iv - 1];
            int segmentPos$iv$iv = getDirectory$okio()[getSegments$okio().length + s$iv$iv];
            int byteCount$iv$iv = Math.min(endIndex$iv$iv, segmentOffset$iv$iv + (getDirectory$okio()[s$iv$iv] - segmentOffset$iv$iv)) - pos$iv$iv;
            int byteCount$iv = byteCount$iv$iv;
            if (i0.a(getSegments$okio()[s$iv$iv], segmentPos$iv$iv + (pos$iv$iv - segmentOffset$iv$iv), bArr, otherOffset$iv, byteCount$iv) == 0) {
                return false;
            }
            otherOffset$iv += byteCount$iv;
            pos$iv$iv += byteCount$iv$iv;
            s$iv$iv++;
            int i3 = offset;
            int i4 = otherOffset;
        }
        return true;
    }

    public int indexOf(@NotNull byte[] other, int fromIndex) {
        k.e(other, "other");
        return a().indexOf(other, fromIndex);
    }

    public int lastIndexOf(@NotNull byte[] other, int fromIndex) {
        k.e(other, "other");
        return a().lastIndexOf(other, fromIndex);
    }

    private final f a() {
        return new f(toByteArray());
    }

    @NotNull
    public byte[] internalArray$okio() {
        return toByteArray();
    }

    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof f)) {
            return false;
        }
        if (((f) other).size() != size() || !rangeEquals(0, (f) other, 0, size())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result$iv = getHashCode$okio();
        if (result$iv == 0) {
            result$iv = 1;
            int segmentCount$iv$iv = getSegments$okio().length;
            int pos$iv$iv = 0;
            for (int s$iv$iv = 0; s$iv$iv < segmentCount$iv$iv; s$iv$iv++) {
                int segmentPos$iv$iv = getDirectory$okio()[segmentCount$iv$iv + s$iv$iv];
                int nextSegmentOffset$iv$iv = getDirectory$okio()[s$iv$iv];
                byte[] data$iv = getSegments$okio()[s$iv$iv];
                int offset$iv = segmentPos$iv$iv;
                int limit$iv = offset$iv + (nextSegmentOffset$iv$iv - pos$iv$iv);
                for (int i$iv = offset$iv; i$iv < limit$iv; i$iv++) {
                    result$iv = (result$iv * 31) + data$iv[i$iv];
                }
                pos$iv$iv = nextSegmentOffset$iv$iv;
            }
            setHashCode$okio(result$iv);
        }
        return result$iv;
    }

    @NotNull
    public String toString() {
        return a().toString();
    }

    private final Object writeReplace() {
        f a = a();
        if (a != null) {
            return a;
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
    }
}
