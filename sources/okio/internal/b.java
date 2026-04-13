package okio.internal;

import kotlin.jvm.internal.k;
import okio.c;
import okio.h0;
import okio.s;
import okio.y;
import org.jetbrains.annotations.NotNull;

/* compiled from: -Buffer.kt */
public final class b {
    @NotNull
    private static final byte[] a = h0.a("0123456789abcdef");

    @NotNull
    public static final byte[] b() {
        return a;
    }

    public static final boolean c(@NotNull y segment, int segmentPos, @NotNull byte[] bytes, int bytesOffset, int bytesLimit) {
        k.e(segment, "segment");
        k.e(bytes, "bytes");
        y segment2 = segment;
        int segmentPos2 = segmentPos;
        int segmentLimit = segment2.d;
        byte[] data = segment2.b;
        for (int i = bytesOffset; i < bytesLimit; i++) {
            if (segmentPos2 == segmentLimit) {
                y yVar = segment2.g;
                k.c(yVar);
                segment2 = yVar;
                data = segment2.b;
                segmentPos2 = segment2.c;
                segmentLimit = segment2.d;
            }
            if (data[segmentPos2] != bytes[i]) {
                return false;
            }
            segmentPos2++;
        }
        return true;
    }

    @NotNull
    public static final String d(@NotNull c $this$readUtf8Line, long newline) {
        k.e($this$readUtf8Line, "$this$readUtf8Line");
        if (newline <= 0 || $this$readUtf8Line.n(newline - 1) != ((byte) 13)) {
            String result = $this$readUtf8Line.b1(newline);
            $this$readUtf8Line.skip(1);
            return result;
        }
        String result2 = $this$readUtf8Line.b1(newline - 1);
        $this$readUtf8Line.skip(2);
        return result2;
    }

    public static /* synthetic */ int f(c cVar, s sVar, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return e(cVar, sVar, z);
    }

    public static final int e(@NotNull c $this$selectPrefix, @NotNull s options, boolean selectTruncated) {
        int nextStep;
        int pos;
        c cVar = $this$selectPrefix;
        k.e(cVar, "$this$selectPrefix");
        k.e(options, "options");
        y head = cVar.c;
        int i = -1;
        if (head == null) {
            return selectTruncated ? -2 : -1;
        }
        y s = head;
        byte[] data = head.b;
        int pos2 = head.c;
        int limit = head.d;
        int[] trie = options.g();
        int scanOrSelect = 0;
        int prefixIndex = -1;
        loop0:
        while (true) {
            int triePos = scanOrSelect + 1;
            int triePos2 = trie[scanOrSelect];
            int triePos3 = triePos + 1;
            int triePos4 = trie[triePos];
            if (triePos4 != i) {
                prefixIndex = triePos4;
            }
            if (s == null) {
                break;
            }
            if (triePos2 < 0) {
                int trieLimit = triePos3 + (triePos2 * -1);
                while (true) {
                    int pos3 = pos2 + 1;
                    int triePos5 = triePos3 + 1;
                    if ((data[pos2] & 255) != trie[triePos3]) {
                        return prefixIndex;
                    }
                    boolean scanComplete = triePos5 == trieLimit;
                    if (pos3 == limit) {
                        k.c(s);
                        y yVar = s.g;
                        k.c(yVar);
                        s = yVar;
                        pos = s.c;
                        data = s.b;
                        limit = s.d;
                        if (s == head) {
                            if (!scanComplete) {
                                int i2 = pos;
                                int i3 = triePos5;
                                break loop0;
                            }
                            s = null;
                        }
                    } else {
                        pos = pos3;
                    }
                    if (scanComplete) {
                        nextStep = trie[triePos5];
                        pos2 = pos;
                        break;
                    }
                    pos2 = pos;
                    triePos3 = triePos5;
                    c cVar2 = $this$selectPrefix;
                }
            } else {
                int selectChoiceCount = triePos2;
                int pos4 = pos2 + 1;
                int $this$and$iv = data[pos2] & 255;
                int selectLimit = triePos3 + selectChoiceCount;
                while (triePos3 != selectLimit) {
                    if ($this$and$iv == trie[triePos3]) {
                        nextStep = trie[triePos3 + selectChoiceCount];
                        if (pos4 == limit) {
                            y yVar2 = s.g;
                            k.c(yVar2);
                            s = yVar2;
                            int pos5 = s.c;
                            data = s.b;
                            limit = s.d;
                            if (s == head) {
                                s = null;
                                pos2 = pos5;
                                int pos6 = triePos3;
                            } else {
                                pos2 = pos5;
                                int pos7 = triePos3;
                            }
                        } else {
                            pos2 = pos4;
                            int pos8 = triePos3;
                        }
                    } else {
                        triePos3++;
                    }
                }
                return prefixIndex;
            }
            if (nextStep >= 0) {
                return nextStep;
            }
            scanOrSelect = -nextStep;
            i = -1;
            c cVar3 = $this$selectPrefix;
        }
        if (selectTruncated) {
            return -2;
        }
        return prefixIndex;
    }

    @NotNull
    public static final c.a a(@NotNull c $this$commonReadAndWriteUnsafe, @NotNull c.a unsafeCursor) {
        k.e($this$commonReadAndWriteUnsafe, "$this$commonReadAndWriteUnsafe");
        k.e(unsafeCursor, "unsafeCursor");
        if (unsafeCursor.c == null) {
            unsafeCursor.c = $this$commonReadAndWriteUnsafe;
            unsafeCursor.d = true;
            return unsafeCursor;
        }
        throw new IllegalStateException("already attached to a buffer".toString());
    }
}
