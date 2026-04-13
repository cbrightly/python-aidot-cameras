package okio;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: SegmentPool.kt */
public final class z {
    private static final int a = 65536;
    private static final y b = new y(new byte[0], 0, 0, false, false);
    private static final int c;
    private static final AtomicReference<y>[] d;
    @NotNull
    public static final z e = new z();

    static {
        int highestOneBit = Integer.highestOneBit((Runtime.getRuntime().availableProcessors() * 2) - 1);
        c = highestOneBit;
        AtomicReference<y>[] atomicReferenceArr = new AtomicReference[highestOneBit];
        for (int i = 0; i < highestOneBit; i++) {
            int i2 = i;
            atomicReferenceArr[i] = new AtomicReference<>();
        }
        d = atomicReferenceArr;
    }

    private z() {
    }

    @NotNull
    public static final y c() {
        AtomicReference firstRef = e.a();
        y yVar = b;
        y first = firstRef.getAndSet(yVar);
        if (first == yVar) {
            return new y();
        }
        if (first == null) {
            firstRef.set((Object) null);
            return new y();
        }
        firstRef.set(first.g);
        first.g = null;
        first.d = 0;
        return first;
    }

    public static final void b(@NotNull y segment) {
        AtomicReference firstRef;
        y first;
        k.e(segment, "segment");
        if (!(segment.g == null && segment.h == null)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        } else if (!segment.e && (first = firstRef.get()) != b) {
            int firstLimit = first != null ? first.d : 0;
            if (firstLimit < a) {
                segment.g = first;
                segment.c = 0;
                segment.d = firstLimit + 8192;
                if (!(firstRef = e.a()).compareAndSet(first, segment)) {
                    segment.g = null;
                }
            }
        }
    }

    private final AtomicReference<y> a() {
        Thread currentThread = Thread.currentThread();
        k.d(currentThread, "Thread.currentThread()");
        return d[(int) (currentThread.getId() & (((long) c) - 1))];
    }
}
