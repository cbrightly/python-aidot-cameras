package kotlinx.coroutines.flow.internal;

import java.util.ArrayList;
import kotlin.collections.y;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.channels.s;
import kotlinx.coroutines.channels.u;
import kotlinx.coroutines.channels.w;
import kotlinx.coroutines.flow.e;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.s0;
import kotlinx.coroutines.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\u001f\u0010\u0017\u001a\u00020\u000e2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u0019H@ø\u0001\u0000¢\u0006\u0002\u0010\u001aJ\u001f\u0010\u001b\u001a\u00020\u000e2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\fH¤@ø\u0001\u0000¢\u0006\u0002\u0010\u001dJ&\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH$J\u0010\u0010\u001f\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010 H\u0016J&\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000 2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#2\u0006\u0010\u001c\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020\u0016H\u0016R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R9\u0010\n\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000b8@X\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00068@X\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u0002\u0004\n\u0002\b\u0019¨\u0006&"}, d2 = {"Lkotlinx/coroutines/flow/internal/ChannelFlow;", "T", "Lkotlinx/coroutines/flow/internal/FusibleFlow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "collectToFun", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "", "getCollectToFun$kotlinx_coroutines_core", "()Lkotlin/jvm/functions/Function2;", "produceCapacity", "getProduceCapacity$kotlinx_coroutines_core", "()I", "additionalToStringProps", "", "collect", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectTo", "scope", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "dropChannelOperators", "Lkotlinx/coroutines/flow/Flow;", "fuse", "produceImpl", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/CoroutineScope;", "toString", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ChannelFlow.kt */
public abstract class d<T> implements k<T> {
    @NotNull
    public final g c;
    public final int d;
    @NotNull
    public final h f;

    @Nullable
    public Object a(@NotNull kotlinx.coroutines.flow.d<? super T> dVar, @NotNull kotlin.coroutines.d<? super x> dVar2) {
        return d(this, dVar, dVar2);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public abstract Object e(@NotNull u<? super T> uVar, @NotNull kotlin.coroutines.d<? super x> dVar);

    /* access modifiers changed from: protected */
    @NotNull
    public abstract d<T> f(@NotNull g gVar, int i, @NotNull h hVar);

    public d(@NotNull g context, int capacity, @NotNull h onBufferOverflow) {
        this.c = context;
        this.d = capacity;
        this.f = onBufferOverflow;
        if (s0.a()) {
            if (!(capacity != -1)) {
                throw new AssertionError();
            }
        }
    }

    @l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H@"}, d2 = {"<anonymous>", "", "T", "it", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.flow.internal.ChannelFlow$collectToFun$1", f = "ChannelFlow.kt", l = {60}, m = "invokeSuspend")
    /* compiled from: ChannelFlow.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.l implements p<u<? super T>, kotlin.coroutines.d<? super x>, Object> {
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ d<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(d<T> dVar, kotlin.coroutines.d<? super b> dVar2) {
            super(2, dVar2);
            this.this$0 = dVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            b bVar = new b(this.this$0, dVar);
            bVar.L$0 = obj;
            return bVar;
        }

        @Nullable
        public final Object invoke(@NotNull u<? super T> uVar, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((b) create(uVar, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    d<T> dVar = this.this$0;
                    this.label = 1;
                    if (dVar.e((u) this.L$0, this) != d) {
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

    @NotNull
    public final p<u<? super T>, kotlin.coroutines.d<? super x>, Object> g() {
        return new b(this, (kotlin.coroutines.d<? super b>) null);
    }

    public final int h() {
        int i = this.d;
        if (i == -3) {
            return -2;
        }
        return i;
    }

    @NotNull
    public kotlinx.coroutines.flow.c<T> b(@NotNull g context, int capacity, @NotNull h onBufferOverflow) {
        h newOverflow;
        int newCapacity;
        boolean z = true;
        if (s0.a()) {
            if ((capacity != -1 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        g newContext = context.plus(this.c);
        if (onBufferOverflow != h.SUSPEND) {
            newCapacity = capacity;
            newOverflow = onBufferOverflow;
        } else {
            int sum = this.d;
            if (sum != -3) {
                if (capacity != -3) {
                    if (sum != -2) {
                        if (capacity != -2) {
                            if (s0.a()) {
                                if ((this.d >= 0 ? 1 : 0) == 0) {
                                    throw new AssertionError();
                                }
                            }
                            if (s0.a()) {
                                if (capacity < 0) {
                                    z = false;
                                }
                                if (!z) {
                                    throw new AssertionError();
                                }
                            }
                            sum = this.d + capacity;
                            if (sum < 0) {
                                sum = Integer.MAX_VALUE;
                            }
                        }
                    }
                }
                newCapacity = sum;
                newOverflow = this.f;
            }
            sum = capacity;
            newCapacity = sum;
            newOverflow = this.f;
        }
        if (k.a(newContext, this.c) && newCapacity == this.d && newOverflow == this.f) {
            return this;
        }
        return f(newContext, newCapacity, newOverflow);
    }

    @NotNull
    public w<T> i(@NotNull o0 scope) {
        return s.e(scope, this.c, h(), this.f, q0.ATOMIC, (kotlin.jvm.functions.l) null, g(), 16, (Object) null);
    }

    @l(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@"}, d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.flow.internal.ChannelFlow$collect$2", f = "ChannelFlow.kt", l = {123}, m = "invokeSuspend")
    /* compiled from: ChannelFlow.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ kotlinx.coroutines.flow.d<T> $collector;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ d<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(kotlinx.coroutines.flow.d<? super T> dVar, d<T> dVar2, kotlin.coroutines.d<? super a> dVar3) {
            super(2, dVar3);
            this.$collector = dVar;
            this.this$0 = dVar2;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            a aVar = new a(this.$collector, this.this$0, dVar);
            aVar.L$0 = obj;
            return aVar;
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    kotlinx.coroutines.flow.d<T> dVar = this.$collector;
                    w<T> i = this.this$0.i((o0) this.L$0);
                    this.label = 1;
                    if (e.j(dVar, i, this) != d) {
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

    static /* synthetic */ Object d(d dVar, kotlinx.coroutines.flow.d collector, kotlin.coroutines.d $completion) {
        Object e = p0.e(new a(collector, dVar, (kotlin.coroutines.d<? super a>) null), $completion);
        return e == c.d() ? e : x.a;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public String c() {
        return null;
    }

    @NotNull
    public String toString() {
        ArrayList props = new ArrayList(4);
        String it = c();
        if (it != null) {
            props.add(it);
        }
        g gVar = this.c;
        if (gVar != kotlin.coroutines.h.INSTANCE) {
            props.add(k.l("context=", gVar));
        }
        int i = this.d;
        if (i != -3) {
            props.add(k.l("capacity=", Integer.valueOf(i)));
        }
        h hVar = this.f;
        if (hVar != h.SUSPEND) {
            props.add(k.l("onBufferOverflow=", hVar));
        }
        return t0.a(this) + '[' + y.b0(props, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (kotlin.jvm.functions.l) null, 62, (Object) null) + ']';
    }
}
