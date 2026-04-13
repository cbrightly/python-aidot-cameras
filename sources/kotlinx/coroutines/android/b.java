package kotlinx.coroutines.android;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.c2;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.m2;
import kotlinx.coroutines.n;
import kotlinx.coroutines.y0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u001c\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015H\u0002J\u001c\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015H\u0016J\u0013\u0010\u0017\u001a\u00020\t2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0016J$\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\n\u0010\u0013\u001a\u00060\u0014j\u0002`\u00152\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010 \u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u001e\u0010!\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00100#H\u0016J\b\u0010$\u001a\u00020\u0006H\u0016R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0000X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\u0000X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\b\u001a\u00020\tX\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lkotlinx/coroutines/android/HandlerContext;", "Lkotlinx/coroutines/android/HandlerDispatcher;", "Lkotlinx/coroutines/Delay;", "handler", "Landroid/os/Handler;", "name", "", "(Landroid/os/Handler;Ljava/lang/String;)V", "invokeImmediately", "", "(Landroid/os/Handler;Ljava/lang/String;Z)V", "_immediate", "immediate", "getImmediate", "()Lkotlinx/coroutines/android/HandlerContext;", "cancelOnRejection", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatch", "equals", "other", "", "hashCode", "", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "", "isDispatchNeeded", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "toString", "kotlinx-coroutines-android"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: HandlerDispatcher.kt */
public final class b extends c implements y0 {
    @Nullable
    private volatile b _immediate;
    /* access modifiers changed from: private */
    @NotNull
    public final Handler c;
    @Nullable
    private final String d;
    private final boolean f;
    @NotNull
    private final b q;

    @l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0003"}, d2 = {"<anonymous>", "", "run", "kotlinx/coroutines/RunnableKt$Runnable$1"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: Runnable.kt */
    public static final class a implements Runnable {
        final /* synthetic */ n c;
        final /* synthetic */ b d;

        public a(n nVar, b bVar) {
            this.c = nVar;
            this.d = bVar;
        }

        public final void run() {
            this.c.B(this.d, x.a);
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private b(Handler handler, String name, boolean invokeImmediately) {
        super((DefaultConstructorMarker) null);
        b bVar = null;
        this.c = handler;
        this.d = name;
        this.f = invokeImmediately;
        this._immediate = invokeImmediately ? this : bVar;
        b it = this._immediate;
        if (it == null) {
            it = new b(handler, name, true);
            this._immediate = it;
        }
        this.q = it;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ b(Handler handler, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(handler, (i & 2) != 0 ? null : str);
    }

    public b(@NotNull Handler handler, @Nullable String name) {
        this(handler, name, false);
    }

    @NotNull
    /* renamed from: P0 */
    public b W() {
        return this.q;
    }

    public boolean isDispatchNeeded(@NotNull g context) {
        return !this.f || !k.a(Looper.myLooper(), this.c.getLooper());
    }

    public void dispatch(@NotNull g context, @NotNull Runnable block) {
        if (!this.c.post(block)) {
            F0(context, block);
        }
    }

    public void j(long timeMillis, @NotNull n<? super x> continuation) {
        Runnable block = new a(continuation, this);
        if (this.c.postDelayed(block, kotlin.ranges.n.f(timeMillis, 4611686018427387903L))) {
            continuation.f(new C0439b(this, block));
        } else {
            F0(continuation.getContext(), block);
        }
    }

    @l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* renamed from: kotlinx.coroutines.android.b$b  reason: collision with other inner class name */
    /* compiled from: HandlerDispatcher.kt */
    public static final class C0439b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ Runnable $block;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0439b(b bVar, Runnable runnable) {
            super(1);
            this.this$0 = bVar;
            this.$block = runnable;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((Throwable) p1);
            return x.a;
        }

        public final void invoke(@Nullable Throwable it) {
            this.this$0.c.removeCallbacks(this.$block);
        }
    }

    @NotNull
    public f1 l(long timeMillis, @NotNull Runnable block, @NotNull g context) {
        if (this.c.postDelayed(block, kotlin.ranges.n.f(timeMillis, 4611686018427387903L))) {
            return new a(this, block);
        }
        F0(context, block);
        return m2.c;
    }

    /* access modifiers changed from: private */
    public static final void a1(b this$0, Runnable $block) {
        this$0.c.removeCallbacks($block);
    }

    private final void F0(g context, Runnable block) {
        c2.c(context, new CancellationException("The task was rejected, the handler underlying the dispatcher '" + this + "' was closed"));
        d1.b().dispatch(context, block);
    }

    @NotNull
    public String toString() {
        String o0 = o0();
        if (o0 != null) {
            return o0;
        }
        String str = this.d;
        if (str == null) {
            str = this.c.toString();
        }
        return this.f ? k.l(str, ".immediate") : str;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof b) && ((b) other).c == this.c;
    }

    public int hashCode() {
        return System.identityHashCode(this.c);
    }
}
