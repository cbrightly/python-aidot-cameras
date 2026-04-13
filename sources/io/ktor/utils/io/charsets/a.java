package io.ktor.utils.io.charsets;

import io.ktor.utils.io.core.c;
import io.ktor.utils.io.core.w;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.MalformedInputException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: CharsetJVM.kt */
public final class a {
    private static final CharBuffer a;
    private static final ByteBuffer b;

    @NotNull
    public static final String h(@NotNull Charset $this$name) {
        k.f($this$name, "$this$name");
        String name = $this$name.name();
        k.b(name, "name()");
        return name;
    }

    @NotNull
    public static final byte[] f(@NotNull CharsetEncoder $this$encodeToByteArray, @NotNull CharSequence input, int fromIndex, int toIndex) {
        k.f($this$encodeToByteArray, "$this$encodeToByteArray");
        k.f(input, "input");
        if (!(input instanceof String)) {
            return g($this$encodeToByteArray, input, fromIndex, toIndex);
        }
        if (fromIndex == 0 && toIndex == input.length()) {
            byte[] bytes = ((String) input).getBytes($this$encodeToByteArray.charset());
            k.b(bytes, "(input as java.lang.String).getBytes(charset())");
            return bytes;
        }
        String substring = ((String) input).substring(fromIndex, toIndex);
        k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        if (substring != null) {
            byte[] bytes2 = substring.getBytes($this$encodeToByteArray.charset());
            k.b(bytes2, "(input.substring(fromInd…ring).getBytes(charset())");
            return bytes2;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    private static final byte[] g(@NotNull CharsetEncoder $this$encodeToByteArraySlow, CharSequence input, int fromIndex, int toIndex) {
        byte[] it;
        ByteBuffer result = $this$encodeToByteArraySlow.encode(CharBuffer.wrap(input, fromIndex, toIndex));
        byte[] bArr = null;
        if (result.hasArray() && result.arrayOffset() == 0 && (it = result.array()) != null) {
            if (it.length == result.remaining()) {
                bArr = it;
            }
        }
        byte[] existingArray = bArr;
        if (existingArray != null) {
            return existingArray;
        }
        byte[] it2 = new byte[result.remaining()];
        result.get(it2);
        return it2;
    }

    public static final int e(@NotNull CharsetEncoder $this$encodeImpl, @NotNull CharSequence input, int fromIndex, int toIndex, @NotNull c dst) {
        CharsetEncoder charsetEncoder = $this$encodeImpl;
        k.f(charsetEncoder, "$this$encodeImpl");
        k.f(input, "input");
        k.f(dst, "dst");
        CharBuffer cb = CharBuffer.wrap(input, fromIndex, toIndex);
        int before = cb.remaining();
        c $this$write$iv$iv = dst;
        ByteBuffer memory$iv = $this$write$iv$iv.n();
        int start$iv = $this$write$iv$iv.s();
        int endExclusive$iv = $this$write$iv$iv.m();
        ByteBuffer nioBuffer$iv = io.ktor.utils.io.bits.c.e(memory$iv, start$iv, endExclusive$iv - start$iv);
        CoderResult result = charsetEncoder.encode(cb, nioBuffer$iv, false);
        k.b(result, "result");
        if (result.isMalformed() || result.isUnmappable()) {
            i(result);
        }
        if (nioBuffer$iv.limit() == endExclusive$iv - start$iv) {
            $this$write$iv$iv.a(nioBuffer$iv.position());
            return before - cb.remaining();
        }
        throw new IllegalStateException("Buffer's limit change is not allowed".toString());
    }

    public static final boolean d(@NotNull CharsetEncoder $this$encodeComplete, @NotNull c dst) {
        boolean completed;
        CharsetEncoder charsetEncoder = $this$encodeComplete;
        k.f(charsetEncoder, "$this$encodeComplete");
        k.f(dst, "dst");
        c $this$write$iv$iv = dst;
        ByteBuffer memory$iv = $this$write$iv$iv.n();
        int start$iv = $this$write$iv$iv.s();
        int endExclusive$iv = $this$write$iv$iv.m();
        ByteBuffer nioBuffer$iv = io.ktor.utils.io.bits.c.e(memory$iv, start$iv, endExclusive$iv - start$iv);
        CoderResult result = charsetEncoder.encode(a, nioBuffer$iv, true);
        k.b(result, "result");
        if (result.isMalformed() || result.isUnmappable()) {
            i(result);
        }
        if (result.isUnderflow()) {
            completed = true;
        } else {
            completed = false;
        }
        if (nioBuffer$iv.limit() == endExclusive$iv - start$iv) {
            $this$write$iv$iv.a(nioBuffer$iv.position());
            return completed;
        }
        throw new IllegalStateException("Buffer's limit change is not allowed".toString());
    }

    @NotNull
    public static final String a(@NotNull CharsetDecoder $this$decodeExactBytes, @NotNull w input, int inputLength) {
        k.f($this$decodeExactBytes, "$this$decodeExactBytes");
        k.f(input, "input");
        if (inputLength == 0) {
            return "";
        }
        if (input instanceof io.ktor.utils.io.core.a) {
            io.ktor.utils.io.core.a this_$iv = (io.ktor.utils.io.core.a) input;
            if (this_$iv.W() - this_$iv.u0() >= inputLength) {
                if (!((io.ktor.utils.io.core.a) input).o0().hasArray()) {
                    return b($this$decodeExactBytes, (io.ktor.utils.io.core.a) input, inputLength);
                }
                ByteBuffer bb = ((io.ktor.utils.io.core.a) input).o0();
                byte[] bytes$iv = bb.array();
                k.b(bytes$iv, "bb.array()");
                int offset$iv = bb.arrayOffset() + bb.position() + ((io.ktor.utils.io.core.a) input).T().o();
                Charset charset$iv = $this$decodeExactBytes.charset();
                k.b(charset$iv, "charset()");
                String text = new String(bytes$iv, offset$iv, inputLength, charset$iv);
                ((io.ktor.utils.io.core.a) input).r(inputLength);
                return text;
            }
        }
        return c($this$decodeExactBytes, input, inputLength);
    }

    private static final String b(@NotNull CharsetDecoder $this$decodeImplByteBuffer, io.ktor.utils.io.core.a input, int inputLength) {
        CharBuffer cb = CharBuffer.allocate(inputLength);
        ByteBuffer bb = io.ktor.utils.io.bits.c.e(input.o0(), input.T().o(), inputLength);
        CoderResult rc = $this$decodeImplByteBuffer.decode(bb, cb, true);
        k.b(rc, "rc");
        if (rc.isMalformed() || rc.isUnmappable()) {
            i(rc);
        }
        cb.flip();
        input.r(bb.position());
        String charBuffer = cb.toString();
        k.b(charBuffer, "cb.toString()");
        return charBuffer;
    }

    /* JADX WARNING: Removed duplicated region for block: B:83:0x019c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final java.lang.String c(@org.jetbrains.annotations.NotNull java.nio.charset.CharsetDecoder r34, io.ktor.utils.io.core.w r35, int r36) {
        /*
            r1 = r34
            r2 = r36
            java.nio.CharBuffer r3 = java.nio.CharBuffer.allocate(r36)
            r0 = r36
            r4 = 0
            r5 = 1
            r6 = r35
            r7 = 1
            r8 = 0
            r9 = 1
            io.ktor.utils.io.core.internal.a r10 = io.ktor.utils.io.core.internal.g.g(r6, r7)
            java.lang.String r11 = "rc"
            if (r10 == 0) goto L_0x01a0
            r13 = r7
            r14 = r13
            r13 = r10
            r10 = r9
            r9 = r5
            r5 = r4
            r4 = r0
        L_0x0021:
            r0 = r13
            r15 = 0
            int r16 = r0.s()     // Catch:{ all -> 0x0191 }
            int r17 = r0.o()     // Catch:{ all -> 0x0191 }
            int r16 = r16 - r17
            r15 = r16
            r16 = 0
            if (r15 < r14) goto L_0x0148
            r0 = r13
            r17 = 0
            boolean r18 = r3.hasRemaining()     // Catch:{ all -> 0x012e }
            r19 = 0
            if (r18 == 0) goto L_0x0114
            if (r4 != 0) goto L_0x004e
            r23 = r0
            r27 = r5
            r30 = r7
            r32 = r8
            r33 = r10
            goto L_0x011e
        L_0x004e:
            r18 = r0
            r20 = 0
            r21 = r18
            r22 = 0
            java.nio.ByteBuffer r23 = r21.n()     // Catch:{ all -> 0x012e }
            int r24 = r21.o()     // Catch:{ all -> 0x012e }
            int r25 = r21.s()     // Catch:{ all -> 0x012e }
            r26 = r23
            r23 = r24
            r24 = r25
            r25 = 0
            r12 = r23
            r23 = r0
            int r0 = r24 - r12
            r27 = r5
            r5 = r26
            java.nio.ByteBuffer r0 = io.ktor.utils.io.bits.c.e(r5, r12, r0)     // Catch:{ all -> 0x010a }
            r26 = r0
            r28 = 0
            int r29 = r26.limit()     // Catch:{ all -> 0x010a }
            r30 = r29
            int r29 = r26.position()     // Catch:{ all -> 0x010a }
            r31 = r5
            r5 = r30
            r30 = r7
            int r7 = r5 - r29
            if (r7 < r4) goto L_0x0094
            r7 = 1
            goto L_0x0096
        L_0x0094:
            r7 = r19
        L_0x0096:
            if (r7 == 0) goto L_0x00a4
            r32 = r8
            int r8 = r29 + r4
            r33 = r10
            r10 = r26
            r10.limit(r8)     // Catch:{ all -> 0x0107 }
            goto L_0x00aa
        L_0x00a4:
            r32 = r8
            r33 = r10
            r10 = r26
        L_0x00aa:
            java.nio.charset.CoderResult r8 = r1.decode(r10, r3, r7)     // Catch:{ all -> 0x0107 }
            kotlin.jvm.internal.k.b(r8, r11)     // Catch:{ all -> 0x0107 }
            boolean r26 = r8.isMalformed()     // Catch:{ all -> 0x0107 }
            if (r26 != 0) goto L_0x00bd
            boolean r26 = r8.isUnmappable()     // Catch:{ all -> 0x0107 }
            if (r26 == 0) goto L_0x00c0
        L_0x00bd:
            i(r8)     // Catch:{ all -> 0x0107 }
        L_0x00c0:
            boolean r26 = r8.isUnderflow()     // Catch:{ all -> 0x0107 }
            if (r26 == 0) goto L_0x00cf
            boolean r26 = r10.hasRemaining()     // Catch:{ all -> 0x0107 }
            if (r26 == 0) goto L_0x00cf
            int r9 = r9 + 1
            goto L_0x00d0
        L_0x00cf:
            r9 = 1
        L_0x00d0:
            r10.limit(r5)     // Catch:{ all -> 0x0107 }
            int r26 = r10.position()     // Catch:{ all -> 0x0107 }
            int r26 = r26 - r29
            int r4 = r4 - r26
            int r5 = r0.limit()     // Catch:{ all -> 0x0107 }
            int r8 = r24 - r12
            if (r5 != r8) goto L_0x00e7
            r19 = 1
        L_0x00e7:
            if (r19 == 0) goto L_0x00f8
            int r5 = r0.position()     // Catch:{ all -> 0x0107 }
            r0 = r5
            r5 = r21
            r5.g(r0)     // Catch:{ all -> 0x0107 }
            r5 = r7
            r19 = r9
            goto L_0x0120
        L_0x00f8:
            r5 = r21
            r8 = 0
            java.lang.String r10 = "Buffer's limit change is not allowed"
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0107 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0107 }
            r8.<init>(r10)     // Catch:{ all -> 0x0107 }
            throw r8     // Catch:{ all -> 0x0107 }
        L_0x0107:
            r0 = move-exception
            r5 = r7
            goto L_0x0137
        L_0x010a:
            r0 = move-exception
            r30 = r7
            r32 = r8
            r33 = r10
            r5 = r27
            goto L_0x0137
        L_0x0114:
            r23 = r0
            r27 = r5
            r30 = r7
            r32 = r8
            r33 = r10
        L_0x011e:
            r5 = r27
        L_0x0120:
            r14 = r19
            r0 = r13
            r7 = 0
            int r8 = r0.s()     // Catch:{ all -> 0x0143 }
            int r10 = r0.o()     // Catch:{ all -> 0x0143 }
            int r8 = r8 - r10
            goto L_0x0151
        L_0x012e:
            r0 = move-exception
            r27 = r5
            r30 = r7
            r32 = r8
            r33 = r10
        L_0x0137:
            r7 = r13
            r8 = 0
            int r10 = r7.s()     // Catch:{ all -> 0x0143 }
            int r11 = r7.o()     // Catch:{ all -> 0x0143 }
            int r10 = r10 - r11
            throw r0     // Catch:{ all -> 0x0143 }
        L_0x0143:
            r0 = move-exception
            r10 = r33
            goto L_0x019a
        L_0x0148:
            r27 = r5
            r30 = r7
            r32 = r8
            r33 = r10
            r8 = r15
        L_0x0151:
            r10 = 0
            if (r8 != 0) goto L_0x015d
            io.ktor.utils.io.core.internal.a r0 = io.ktor.utils.io.core.internal.g.i(r6, r13)     // Catch:{ all -> 0x015b }
            goto L_0x0179
        L_0x015b:
            r0 = move-exception
            goto L_0x019a
        L_0x015d:
            if (r8 < r14) goto L_0x0172
            r0 = r13
            r7 = 0
            int r12 = r0.l()     // Catch:{ all -> 0x015b }
            int r16 = r0.m()     // Catch:{ all -> 0x015b }
            int r12 = r12 - r16
            r0 = 8
            if (r12 >= r0) goto L_0x0170
            goto L_0x0172
        L_0x0170:
            r0 = r13
            goto L_0x0179
        L_0x0172:
            io.ktor.utils.io.core.internal.g.d(r6, r13)     // Catch:{ all -> 0x015b }
            io.ktor.utils.io.core.internal.a r0 = io.ktor.utils.io.core.internal.g.g(r6, r14)     // Catch:{ all -> 0x015b }
        L_0x0179:
            if (r0 != 0) goto L_0x017d
            goto L_0x0181
        L_0x017d:
            r13 = r0
            r10 = 1
            if (r14 > 0) goto L_0x018b
        L_0x0181:
            if (r10 == 0) goto L_0x0186
            io.ktor.utils.io.core.internal.g.d(r6, r13)
        L_0x0186:
            r0 = r4
            r4 = r5
            r5 = r9
            goto L_0x01a4
        L_0x018b:
            r7 = r30
            r8 = r32
            goto L_0x0021
        L_0x0191:
            r0 = move-exception
            r27 = r5
            r30 = r7
            r32 = r8
            r33 = r10
        L_0x019a:
            if (r10 == 0) goto L_0x019f
            io.ktor.utils.io.core.internal.g.d(r6, r13)
        L_0x019f:
            throw r0
        L_0x01a0:
            r30 = r7
            r32 = r8
        L_0x01a4:
            boolean r6 = r3.hasRemaining()
            if (r6 == 0) goto L_0x01c5
            if (r4 != 0) goto L_0x01c5
            java.nio.ByteBuffer r6 = b
            r7 = 1
            java.nio.charset.CoderResult r6 = r1.decode(r6, r3, r7)
            kotlin.jvm.internal.k.b(r6, r11)
            boolean r7 = r6.isMalformed()
            if (r7 != 0) goto L_0x01c2
            boolean r7 = r6.isUnmappable()
            if (r7 == 0) goto L_0x01c5
        L_0x01c2:
            i(r6)
        L_0x01c5:
            if (r0 > 0) goto L_0x01df
            if (r0 < 0) goto L_0x01d6
            r3.flip()
            java.lang.String r6 = r3.toString()
            java.lang.String r7 = "cb.toString()"
            kotlin.jvm.internal.k.b(r6, r7)
            return r6
        L_0x01d6:
            java.lang.AssertionError r6 = new java.lang.AssertionError
            java.lang.String r7 = "remainingInputBytes < 0"
            r6.<init>(r7)
            throw r6
        L_0x01df:
            java.io.EOFException r6 = new java.io.EOFException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Not enough bytes available: had only "
            r7.append(r8)
            int r8 = r2 - r0
            r7.append(r8)
            java.lang.String r8 = " instead of "
            r7.append(r8)
            r7.append(r2)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.charsets.a.c(java.nio.charset.CharsetDecoder, io.ktor.utils.io.core.w, int):java.lang.String");
    }

    private static final void i(@NotNull CoderResult $this$throwExceptionWrapped) {
        try {
            $this$throwExceptionWrapped.throwException();
        } catch (MalformedInputException original) {
            String message = original.getMessage();
            if (message == null) {
                message = "Failed to decode bytes";
            }
            throw new MalformedInputException(message);
        }
    }

    static {
        CharBuffer allocate = CharBuffer.allocate(0);
        if (allocate == null) {
            k.n();
        }
        a = allocate;
        ByteBuffer allocate2 = ByteBuffer.allocate(0);
        if (allocate2 == null) {
            k.n();
        }
        b = allocate2;
    }
}
