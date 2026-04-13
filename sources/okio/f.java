package okio;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.io.EOFException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import okio.internal.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteString.kt */
public class f implements Serializable, Comparable<f> {
    @NotNull
    public static final a Companion = new a((DefaultConstructorMarker) null);
    @NotNull
    public static final f EMPTY = new f(new byte[0]);
    private static final long serialVersionUID = 1;
    private transient int c;
    @Nullable
    private transient String d;
    @NotNull
    private final byte[] data;

    @Nullable
    public static final f decodeBase64(@NotNull String str) {
        return Companion.a(str);
    }

    @NotNull
    public static final f decodeHex(@NotNull String str) {
        return Companion.b(str);
    }

    @NotNull
    public static final f encodeString(@NotNull String str, @NotNull Charset charset) {
        return Companion.c(str, charset);
    }

    @NotNull
    public static final f encodeUtf8(@NotNull String str) {
        return Companion.d(str);
    }

    public static /* synthetic */ int indexOf$default(f fVar, f fVar2, int i, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                i = 0;
            }
            return fVar.indexOf(fVar2, i);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: indexOf");
    }

    public static /* synthetic */ int indexOf$default(f fVar, byte[] bArr, int i, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                i = 0;
            }
            return fVar.indexOf(bArr, i);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: indexOf");
    }

    public static /* synthetic */ int lastIndexOf$default(f fVar, f fVar2, int i, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                i = fVar.size();
            }
            return fVar.lastIndexOf(fVar2, i);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lastIndexOf");
    }

    public static /* synthetic */ int lastIndexOf$default(f fVar, byte[] bArr, int i, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                i = fVar.size();
            }
            return fVar.lastIndexOf(bArr, i);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lastIndexOf");
    }

    @NotNull
    public static final f of(@NotNull ByteBuffer byteBuffer) {
        return Companion.e(byteBuffer);
    }

    @NotNull
    public static final f of(@NotNull byte... bArr) {
        return Companion.f(bArr);
    }

    @NotNull
    public static final f of(@NotNull byte[] bArr, int i, int i2) {
        return Companion.g(bArr, i, i2);
    }

    @NotNull
    public static final f read(@NotNull InputStream inputStream, int i) {
        return Companion.i(inputStream, i);
    }

    public static /* synthetic */ f substring$default(f fVar, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 1) != 0) {
                i = 0;
            }
            if ((i3 & 2) != 0) {
                i2 = fVar.size();
            }
            return fVar.substring(i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: substring");
    }

    public final int indexOf(@NotNull f fVar) {
        return indexOf$default(this, fVar, 0, 2, (Object) null);
    }

    public final int indexOf(@NotNull byte[] bArr) {
        return indexOf$default(this, bArr, 0, 2, (Object) null);
    }

    public final int lastIndexOf(@NotNull f fVar) {
        return lastIndexOf$default(this, fVar, 0, 2, (Object) null);
    }

    public final int lastIndexOf(@NotNull byte[] bArr) {
        return lastIndexOf$default(this, bArr, 0, 2, (Object) null);
    }

    @NotNull
    public final f substring() {
        return substring$default(this, 0, 0, 3, (Object) null);
    }

    @NotNull
    public final f substring(int i) {
        return substring$default(this, i, 0, 2, (Object) null);
    }

    public f(@NotNull byte[] data2) {
        k.e(data2, "data");
        this.data = data2;
    }

    @NotNull
    public final byte[] getData$okio() {
        return this.data;
    }

    public final int getHashCode$okio() {
        return this.c;
    }

    public final void setHashCode$okio(int i) {
        this.c = i;
    }

    @Nullable
    public final String getUtf8$okio() {
        return this.d;
    }

    public final void setUtf8$okio(@Nullable String str) {
        this.d = str;
    }

    @NotNull
    public String utf8() {
        String result$iv = getUtf8$okio();
        if (result$iv != null) {
            return result$iv;
        }
        String result$iv2 = h0.b(internalArray$okio());
        setUtf8$okio(result$iv2);
        return result$iv2;
    }

    @NotNull
    public String string(@NotNull Charset charset) {
        k.e(charset, "charset");
        return new String(this.data, charset);
    }

    @NotNull
    public String base64() {
        return g0.c(getData$okio(), (byte[]) null, 1, (Object) null);
    }

    @NotNull
    public final f md5() {
        return digest$okio("MD5");
    }

    @NotNull
    public final f sha1() {
        return digest$okio("SHA-1");
    }

    @NotNull
    public final f sha256() {
        return digest$okio("SHA-256");
    }

    @NotNull
    public final f sha512() {
        return digest$okio("SHA-512");
    }

    @NotNull
    public f digest$okio(@NotNull String algorithm) {
        k.e(algorithm, "algorithm");
        MessageDigest $this$run = MessageDigest.getInstance(algorithm);
        $this$run.update(this.data, 0, size());
        byte[] digestBytes = $this$run.digest();
        k.d(digestBytes, "digestBytes");
        return new f(digestBytes);
    }

    @NotNull
    public f hmacSha1(@NotNull f key) {
        k.e(key, CacheEntity.KEY);
        return hmac$okio("HmacSHA1", key);
    }

    @NotNull
    public f hmacSha256(@NotNull f key) {
        k.e(key, CacheEntity.KEY);
        return hmac$okio("HmacSHA256", key);
    }

    @NotNull
    public f hmacSha512(@NotNull f key) {
        k.e(key, CacheEntity.KEY);
        return hmac$okio("HmacSHA512", key);
    }

    @NotNull
    public f hmac$okio(@NotNull String algorithm, @NotNull f key) {
        k.e(algorithm, "algorithm");
        k.e(key, CacheEntity.KEY);
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key.toByteArray(), algorithm));
            byte[] doFinal = mac.doFinal(this.data);
            k.d(doFinal, "mac.doFinal(data)");
            return new f(doFinal);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @NotNull
    public String base64Url() {
        return g0.b(getData$okio(), g0.d());
    }

    @NotNull
    public String hex() {
        char[] result$iv = new char[(getData$okio().length * 2)];
        int c$iv = 0;
        for (int $this$and$iv$iv : getData$okio()) {
            int c$iv2 = c$iv + 1;
            result$iv[c$iv] = c.f()[($this$and$iv$iv >> 4) & 15];
            c$iv = c$iv2 + 1;
            result$iv[c$iv2] = c.f()[15 & $this$and$iv$iv];
        }
        return new String(result$iv);
    }

    @NotNull
    public f toAsciiLowercase() {
        byte b;
        int i$iv = 0;
        while (i$iv < getData$okio().length) {
            byte c$iv = getData$okio()[i$iv];
            byte b2 = (byte) 65;
            if (c$iv < b2 || c$iv > (b = (byte) 90)) {
                i$iv++;
            } else {
                byte[] data$okio = getData$okio();
                byte[] lowercase$iv = Arrays.copyOf(data$okio, data$okio.length);
                k.d(lowercase$iv, "java.util.Arrays.copyOf(this, size)");
                int i$iv2 = i$iv + 1;
                lowercase$iv[i$iv] = (byte) (c$iv + 32);
                while (i$iv2 < lowercase$iv.length) {
                    byte c$iv2 = lowercase$iv[i$iv2];
                    if (c$iv2 < b2 || c$iv2 > b) {
                        i$iv2++;
                    } else {
                        lowercase$iv[i$iv2] = (byte) (c$iv2 + 32);
                        i$iv2++;
                    }
                }
                return new f(lowercase$iv);
            }
        }
        return this;
    }

    @NotNull
    public f toAsciiUppercase() {
        byte b;
        int i$iv = 0;
        while (i$iv < getData$okio().length) {
            byte c$iv = getData$okio()[i$iv];
            byte b2 = (byte) 97;
            if (c$iv < b2 || c$iv > (b = (byte) 122)) {
                i$iv++;
            } else {
                byte[] data$okio = getData$okio();
                byte[] lowercase$iv = Arrays.copyOf(data$okio, data$okio.length);
                k.d(lowercase$iv, "java.util.Arrays.copyOf(this, size)");
                int i$iv2 = i$iv + 1;
                lowercase$iv[i$iv] = (byte) (c$iv - 32);
                while (i$iv2 < lowercase$iv.length) {
                    byte c$iv2 = lowercase$iv[i$iv2];
                    if (c$iv2 < b2 || c$iv2 > b) {
                        i$iv2++;
                    } else {
                        lowercase$iv[i$iv2] = (byte) (c$iv2 - 32);
                        i$iv2++;
                    }
                }
                return new f(lowercase$iv);
            }
        }
        return this;
    }

    @NotNull
    public f substring(int beginIndex, int endIndex) {
        boolean z = true;
        if (beginIndex >= 0) {
            if (endIndex <= getData$okio().length) {
                if (endIndex - beginIndex < 0) {
                    z = false;
                }
                if (z) {
                    return (beginIndex == 0 && endIndex == getData$okio().length) ? this : new f(kotlin.collections.k.h(getData$okio(), beginIndex, endIndex));
                }
                throw new IllegalArgumentException("endIndex < beginIndex".toString());
            }
            throw new IllegalArgumentException(("endIndex > length(" + getData$okio().length + ')').toString());
        }
        throw new IllegalArgumentException("beginIndex < 0".toString());
    }

    public byte internalGet$okio(int pos) {
        return getData$okio()[pos];
    }

    public final byte getByte(int index) {
        return internalGet$okio(index);
    }

    public final int size() {
        return getSize$okio();
    }

    public int getSize$okio() {
        return getData$okio().length;
    }

    @NotNull
    public byte[] toByteArray() {
        byte[] data$okio = getData$okio();
        byte[] copyOf = Arrays.copyOf(data$okio, data$okio.length);
        k.d(copyOf, "java.util.Arrays.copyOf(this, size)");
        return copyOf;
    }

    @NotNull
    public byte[] internalArray$okio() {
        return getData$okio();
    }

    @NotNull
    public ByteBuffer asByteBuffer() {
        ByteBuffer asReadOnlyBuffer = ByteBuffer.wrap(this.data).asReadOnlyBuffer();
        k.d(asReadOnlyBuffer, "ByteBuffer.wrap(data).asReadOnlyBuffer()");
        return asReadOnlyBuffer;
    }

    public void write(@NotNull OutputStream out) {
        k.e(out, "out");
        out.write(this.data);
    }

    public void write$okio(@NotNull c buffer, int offset, int byteCount) {
        k.e(buffer, "buffer");
        c.d(this, buffer, offset, byteCount);
    }

    public boolean rangeEquals(int offset, @NotNull f other, int otherOffset, int byteCount) {
        k.e(other, "other");
        return other.rangeEquals(otherOffset, getData$okio(), offset, byteCount);
    }

    public boolean rangeEquals(int offset, @NotNull byte[] other, int otherOffset, int byteCount) {
        k.e(other, "other");
        return offset >= 0 && offset <= getData$okio().length - byteCount && otherOffset >= 0 && otherOffset <= other.length - byteCount && i0.a(getData$okio(), offset, other, otherOffset, byteCount);
    }

    public final boolean startsWith(@NotNull f prefix) {
        k.e(prefix, "prefix");
        return rangeEquals(0, prefix, 0, prefix.size());
    }

    public final boolean startsWith(@NotNull byte[] prefix) {
        k.e(prefix, "prefix");
        return rangeEquals(0, prefix, 0, prefix.length);
    }

    public final boolean endsWith(@NotNull f suffix) {
        k.e(suffix, "suffix");
        return rangeEquals(size() - suffix.size(), suffix, 0, suffix.size());
    }

    public final boolean endsWith(@NotNull byte[] suffix) {
        k.e(suffix, "suffix");
        return rangeEquals(size() - suffix.length, suffix, 0, suffix.length);
    }

    public final int indexOf(@NotNull f other, int fromIndex) {
        k.e(other, "other");
        return indexOf(other.internalArray$okio(), fromIndex);
    }

    public int indexOf(@NotNull byte[] other, int fromIndex) {
        k.e(other, "other");
        int limit$iv = getData$okio().length - other.length;
        int i$iv = Math.max(fromIndex, 0);
        if (i$iv <= limit$iv) {
            while (!i0.a(getData$okio(), i$iv, other, 0, other.length)) {
                if (i$iv != limit$iv) {
                    i$iv++;
                }
            }
            return i$iv;
        }
        return -1;
    }

    public final int lastIndexOf(@NotNull f other, int fromIndex) {
        k.e(other, "other");
        return lastIndexOf(other.internalArray$okio(), fromIndex);
    }

    public int lastIndexOf(@NotNull byte[] other, int fromIndex) {
        k.e(other, "other");
        for (int i$iv = Math.min(fromIndex, getData$okio().length - other.length); i$iv >= 0; i$iv--) {
            if (i0.a(getData$okio(), i$iv, other, 0, other.length)) {
                return i$iv;
            }
        }
        return -1;
    }

    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof f)) {
            return false;
        }
        if (((f) other).size() != getData$okio().length || !((f) other).rangeEquals(0, getData$okio(), 0, getData$okio().length)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result$iv = getHashCode$okio();
        if (result$iv != 0) {
            return result$iv;
        }
        int it$iv = Arrays.hashCode(getData$okio());
        setHashCode$okio(it$iv);
        return it$iv;
    }

    public int compareTo(@NotNull f other) {
        k.e(other, "other");
        int sizeA$iv = size();
        int sizeB$iv = other.size();
        int i$iv = 0;
        int size$iv = Math.min(sizeA$iv, sizeB$iv);
        while (i$iv < size$iv) {
            byte $this$and$iv$iv = getByte(i$iv) & 255;
            byte $this$and$iv$iv2 = other.getByte(i$iv) & 255;
            if ($this$and$iv$iv == $this$and$iv$iv2) {
                i$iv++;
            } else if ($this$and$iv$iv < $this$and$iv$iv2) {
                return -1;
            } else {
                return 1;
            }
        }
        if (sizeA$iv == sizeB$iv) {
            return 0;
        }
        if (sizeA$iv < sizeB$iv) {
            return -1;
        }
        return 1;
    }

    @NotNull
    public String toString() {
        boolean z = true;
        if (getData$okio().length == 0) {
            return "[size=0]";
        }
        int i$iv = c.c(getData$okio(), 64);
        if (i$iv != -1) {
            String text$iv = utf8();
            if (text$iv != null) {
                String substring = text$iv.substring(0, i$iv);
                k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                String safeText$iv = w.H(w.H(w.H(substring, "\\", "\\\\", false, 4, (Object) null), "\n", "\\n", false, 4, (Object) null), "\r", "\\r", false, 4, (Object) null);
                if (i$iv < text$iv.length()) {
                    return "[size=" + getData$okio().length + " text=" + safeText$iv + "…]";
                }
                return "[text=" + safeText$iv + ']';
            }
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        } else if (getData$okio().length <= 64) {
            return "[hex=" + hex() + ']';
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("[size=");
            sb.append(getData$okio().length);
            sb.append(" hex=");
            f $this$commonSubstring$iv$iv = this;
            if (64 <= $this$commonSubstring$iv$iv.getData$okio().length) {
                if (64 - 0 < 0) {
                    z = false;
                }
                if (z) {
                    if (64 != $this$commonSubstring$iv$iv.getData$okio().length) {
                        $this$commonSubstring$iv$iv = new f(kotlin.collections.k.h($this$commonSubstring$iv$iv.getData$okio(), 0, 64));
                    }
                    sb.append($this$commonSubstring$iv$iv.hex());
                    sb.append("…]");
                    return sb.toString();
                }
                throw new IllegalArgumentException("endIndex < beginIndex".toString());
            }
            throw new IllegalArgumentException(("endIndex > length(" + $this$commonSubstring$iv$iv.getData$okio().length + ')').toString());
        }
    }

    private final void readObject(ObjectInputStream in) {
        f byteString = Companion.i(in, in.readInt());
        Field field = f.class.getDeclaredField("data");
        k.d(field, "field");
        field.setAccessible(true);
        field.set(this, byteString.data);
    }

    private final void writeObject(ObjectOutputStream out) {
        out.writeInt(this.data.length);
        out.write(this.data);
    }

    /* renamed from: -deprecated_getByte  reason: not valid java name */
    public final byte m27deprecated_getByte(int index) {
        return getByte(index);
    }

    /* renamed from: -deprecated_size  reason: not valid java name */
    public final int m28deprecated_size() {
        return size();
    }

    /* compiled from: ByteString.kt */
    public static final class a {
        public static /* synthetic */ f h(a aVar, byte[] bArr, int i, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                i = 0;
            }
            if ((i3 & 2) != 0) {
                i2 = bArr.length;
            }
            return aVar.g(bArr, i, i2);
        }

        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final f f(@NotNull byte... data) {
            k.e(data, "data");
            byte[] copyOf = Arrays.copyOf(data, data.length);
            k.d(copyOf, "java.util.Arrays.copyOf(this, size)");
            return new f(copyOf);
        }

        @NotNull
        public final f g(@NotNull byte[] $this$toByteString, int offset, int byteCount) {
            k.e($this$toByteString, "$this$toByteString");
            byte[] $this$commonToByteString$iv = $this$toByteString;
            i0.b((long) $this$commonToByteString$iv.length, (long) offset, (long) byteCount);
            return new f(kotlin.collections.k.h($this$commonToByteString$iv, offset, offset + byteCount));
        }

        @NotNull
        public final f e(@NotNull ByteBuffer $this$toByteString) {
            k.e($this$toByteString, "$this$toByteString");
            byte[] copy = new byte[$this$toByteString.remaining()];
            $this$toByteString.get(copy);
            return new f(copy);
        }

        @NotNull
        public final f d(@NotNull String $this$encodeUtf8) {
            k.e($this$encodeUtf8, "$this$encodeUtf8");
            String $this$commonEncodeUtf8$iv = $this$encodeUtf8;
            f byteString$iv = new f(h0.a($this$commonEncodeUtf8$iv));
            byteString$iv.setUtf8$okio($this$commonEncodeUtf8$iv);
            return byteString$iv;
        }

        @NotNull
        public final f c(@NotNull String $this$encode, @NotNull Charset charset) {
            k.e($this$encode, "$this$encode");
            k.e(charset, "charset");
            byte[] bytes = $this$encode.getBytes(charset);
            k.d(bytes, "(this as java.lang.String).getBytes(charset)");
            return new f(bytes);
        }

        @Nullable
        public final f a(@NotNull String $this$decodeBase64) {
            k.e($this$decodeBase64, "$this$decodeBase64");
            byte[] decoded$iv = g0.a($this$decodeBase64);
            if (decoded$iv != null) {
                return new f(decoded$iv);
            }
            return null;
        }

        @NotNull
        public final f b(@NotNull String $this$decodeHex) {
            k.e($this$decodeHex, "$this$decodeHex");
            String $this$commonDecodeHex$iv = $this$decodeHex;
            if ($this$commonDecodeHex$iv.length() % 2 == 0) {
                byte[] result$iv = new byte[($this$commonDecodeHex$iv.length() / 2)];
                int length = result$iv.length;
                for (int i$iv = 0; i$iv < length; i$iv++) {
                    result$iv[i$iv] = (byte) ((c.e($this$commonDecodeHex$iv.charAt(i$iv * 2)) << 4) + c.e($this$commonDecodeHex$iv.charAt((i$iv * 2) + 1)));
                }
                return new f(result$iv);
            }
            throw new IllegalArgumentException(("Unexpected hex string: " + $this$commonDecodeHex$iv).toString());
        }

        @NotNull
        public final f i(@NotNull InputStream $this$readByteString, int byteCount) {
            k.e($this$readByteString, "$this$readByteString");
            if (byteCount >= 0) {
                byte[] result = new byte[byteCount];
                int offset = 0;
                while (offset < byteCount) {
                    int read = $this$readByteString.read(result, offset, byteCount - offset);
                    if (read != -1) {
                        offset += read;
                    } else {
                        throw new EOFException();
                    }
                }
                return new f(result);
            }
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        }
    }
}
