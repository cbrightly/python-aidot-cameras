package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.channels.a0;
import kotlinx.coroutines.channels.u;
import kotlinx.coroutines.flow.c;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@f(c = "androidx.lifecycle.FlowExtKt$flowWithLifecycle$1", f = "FlowExt.kt", l = {91}, m = "invokeSuspend")
/* compiled from: FlowExt.kt */
public final class FlowExtKt$flowWithLifecycle$1 extends kotlin.coroutines.jvm.internal.l implements p<u<? super T>, d<? super x>, Object> {
    final /* synthetic */ Lifecycle $lifecycle;
    final /* synthetic */ Lifecycle.State $minActiveState;
    final /* synthetic */ c<T> $this_flowWithLifecycle;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowExtKt$flowWithLifecycle$1(Lifecycle lifecycle, Lifecycle.State state, c<? extends T> cVar, d<? super FlowExtKt$flowWithLifecycle$1> dVar) {
        super(2, dVar);
        this.$lifecycle = lifecycle;
        this.$minActiveState = state;
        this.$this_flowWithLifecycle = cVar;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        FlowExtKt$flowWithLifecycle$1 flowExtKt$flowWithLifecycle$1 = new FlowExtKt$flowWithLifecycle$1(this.$lifecycle, this.$minActiveState, this.$this_flowWithLifecycle, dVar);
        flowExtKt$flowWithLifecycle$1.L$0 = obj;
        return flowExtKt$flowWithLifecycle$1;
    }

    @Nullable
    public final Object invoke(@NotNull u<? super T> uVar, @Nullable d<? super x> dVar) {
        return ((FlowExtKt$flowWithLifecycle$1) create(uVar, dVar)).invokeSuspend(x.a);
    }

    @l(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@"}, d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "androidx.lifecycle.FlowExtKt$flowWithLifecycle$1$1", f = "FlowExt.kt", l = {92}, m = "invokeSuspend")
    /* renamed from: androidx.lifecycle.FlowExtKt$flowWithLifecycle$1$1  reason: invalid class name */
    /* compiled from: FlowExt.kt */
    public static final class AnonymousClass1 extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super x>, Object> {
        int label;

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new AnonymousClass1(cVar, $this$callbackFlow2, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((AnonymousClass1) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    c<T> cVar = cVar;
                    final u<T> uVar = $this$callbackFlow2;
                    AnonymousClass1 r3 = new kotlinx.coroutines.flow.d() {
                        @Nullable
                        public final Object emit(T it, @NotNull d<? super x> $completion) {
                            Object D = uVar.D(it, $completion);
                            return D == kotlin.coroutines.intrinsics.c.d() ? D : x.a;
                        }
                    };
                    this.label = 1;
                    if (cVar.a(r3, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    @Nullable
    public final Object invokeSuspend(@NotNull Object $result) {
        u $this$callbackFlow;
        Object d = kotlin.coroutines.intrinsics.c.d();
        switch (this.label) {
            case 0:
                kotlin.p.b($result);
                final u $this$callbackFlow2 = (u) this.L$0;
                Lifecycle lifecycle = this.$lifecycle;
                Lifecycle.State state = this.$minActiveState;
                final c<T> cVar = this.$this_flowWithLifecycle;
                AnonymousClass1 r7 = new AnonymousClass1((d<? super AnonymousClass1>) null);
                this.L$0 = $this$callbackFlow2;
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycle, state, (p<? super o0, ? super d<? super x>, ? extends Object>) r7, (d<? super x>) this) != d) {
                    $this$callbackFlow = $this$callbackFlow2;
                    break;
                } else {
                    return d;
                }
            case 1:
                $this$callbackFlow = (u) this.L$0;
                kotlin.p.b($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        a0.a.a($this$callbackFlow, (Throwable) null, 1, (Object) null);
        return x.a;
    }
}
