package kotlin.coroutines;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SafeContinuationJvm.kt */
public final class i<T> implements d<T>, e {
    @Deprecated
    private static final AtomicReferenceFieldUpdater<i<?>, Object> c = AtomicReferenceFieldUpdater.newUpdater(i.class, Object.class, "result");
    @NotNull
    private static final a d = new a((DefaultConstructorMarker) null);
    private final d<T> f;
    private volatile Object result;

    public i(@NotNull d<? super T> delegate, @Nullable Object initialResult) {
        k.e(delegate, "delegate");
        this.f = delegate;
        this.result = initialResult;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public i(@NotNull d<? super T> delegate) {
        this(delegate, kotlin.coroutines.intrinsics.a.UNDECIDED);
        k.e(delegate, "delegate");
    }

    @NotNull
    public g getContext() {
        return this.f.getContext();
    }

    /* compiled from: SafeContinuationJvm.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    public void resumeWith(@NotNull Object result2) {
        while (true) {
            Object cur = this.result;
            kotlin.coroutines.intrinsics.a aVar = kotlin.coroutines.intrinsics.a.UNDECIDED;
            if (cur == aVar) {
                if (c.compareAndSet(this, aVar, result2)) {
                    return;
                }
            } else if (cur != c.d()) {
                throw new IllegalStateException("Already resumed");
            } else if (c.compareAndSet(this, c.d(), kotlin.coroutines.intrinsics.a.RESUMED)) {
                this.f.resumeWith(result2);
                return;
            }
        }
    }

    @Nullable
    public final Object a() {
        Object result2 = this.result;
        kotlin.coroutines.intrinsics.a aVar = kotlin.coroutines.intrinsics.a.UNDECIDED;
        if (result2 == aVar) {
            if (c.compareAndSet(this, aVar, c.d())) {
                return c.d();
            }
            result2 = this.result;
        }
        if (result2 == kotlin.coroutines.intrinsics.a.RESUMED) {
            return c.d();
        }
        if (!(result2 instanceof o.b)) {
            return result2;
        }
        throw ((o.b) result2).exception;
    }

    @Nullable
    public e getCallerFrame() {
        d<T> dVar = this.f;
        if (!(dVar instanceof e)) {
            dVar = null;
        }
        return (e) dVar;
    }

    @Nullable
    public StackTraceElement getStackTraceElement() {
        return null;
    }

    @NotNull
    public String toString() {
        return "SafeContinuation for " + this.f;
    }
}
