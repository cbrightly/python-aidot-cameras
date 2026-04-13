package kotlinx.coroutines.flow;

import kotlin.coroutines.d;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.channels.u;
import kotlinx.coroutines.channels.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"kotlinx/coroutines/flow/FlowKt__BuildersKt", "kotlinx/coroutines/flow/FlowKt__ChannelsKt", "kotlinx/coroutines/flow/FlowKt__CollectKt", "kotlinx/coroutines/flow/FlowKt__CollectionKt", "kotlinx/coroutines/flow/FlowKt__ContextKt", "kotlinx/coroutines/flow/FlowKt__CountKt", "kotlinx/coroutines/flow/FlowKt__DelayKt", "kotlinx/coroutines/flow/FlowKt__DistinctKt", "kotlinx/coroutines/flow/FlowKt__EmittersKt", "kotlinx/coroutines/flow/FlowKt__ErrorsKt", "kotlinx/coroutines/flow/FlowKt__LimitKt", "kotlinx/coroutines/flow/FlowKt__MergeKt", "kotlinx/coroutines/flow/FlowKt__MigrationKt", "kotlinx/coroutines/flow/FlowKt__ReduceKt", "kotlinx/coroutines/flow/FlowKt__ShareKt", "kotlinx/coroutines/flow/FlowKt__TransformKt", "kotlinx/coroutines/flow/FlowKt__ZipKt"}, k = 4, mv = {1, 6, 0}, xi = 48)
public final class e {
    @NotNull
    public static final <T> t<T> a(@NotNull p<T> $this$asSharedFlow) {
        return n.a($this$asSharedFlow);
    }

    @NotNull
    public static final <T> x<T> b(@NotNull q<T> $this$asStateFlow) {
        return n.b($this$asStateFlow);
    }

    @NotNull
    public static final <T> c<T> c(@NotNull c<? extends T> $this$buffer, int capacity, @NotNull h onBufferOverflow) {
        return i.a($this$buffer, capacity, onBufferOverflow);
    }

    @NotNull
    public static final <T> c<T> e(@NotNull p<? super u<? super T>, ? super d<? super x>, ? extends Object> block) {
        return f.a(block);
    }

    @NotNull
    public static final <T> c<T> f(@NotNull c<? extends T> $this$catch, @NotNull q<? super d<? super T>, ? super Throwable, ? super d<? super x>, ? extends Object> action) {
        return k.a($this$catch, action);
    }

    @Nullable
    public static final <T> Object g(@NotNull c<? extends T> $this$catchImpl, @NotNull d<? super T> collector, @NotNull d<? super Throwable> $completion) {
        return k.b($this$catchImpl, collector, $completion);
    }

    @Nullable
    public static final Object h(@NotNull c<?> $this$collect, @NotNull d<? super x> $completion) {
        return h.a($this$collect, $completion);
    }

    @Nullable
    public static final <T> Object i(@NotNull c<? extends T> $this$collectLatest, @NotNull p<? super T, ? super d<? super x>, ? extends Object> action, @NotNull d<? super x> $completion) {
        return h.b($this$collectLatest, action, $completion);
    }

    @Nullable
    public static final <T> Object j(@NotNull d<? super T> $this$emitAll, @NotNull w<? extends T> channel, @NotNull d<? super x> $completion) {
        return g.b($this$emitAll, channel, $completion);
    }

    public static final void k(@NotNull d<?> $this$ensureActive) {
        j.b($this$ensureActive);
    }

    @Nullable
    public static final <T> Object l(@NotNull c<? extends T> $this$first, @NotNull d<? super T> $completion) {
        return m.a($this$first, $completion);
    }

    @NotNull
    public static final <T, R> c<R> m(@NotNull c<? extends T> $this$mapLatest, @NotNull p<? super T, ? super d<? super R>, ? extends Object> transform) {
        return l.a($this$mapLatest, transform);
    }

    @NotNull
    public static final <T> c<T> n(@NotNull c<? extends T> $this$onCompletion, @NotNull q<? super d<? super T>, ? super Throwable, ? super d<? super x>, ? extends Object> action) {
        return j.d($this$onCompletion, action);
    }

    @NotNull
    public static final <T> c<T> o(@NotNull c<? extends T> $this$onEach, @NotNull p<? super T, ? super d<? super x>, ? extends Object> action) {
        return o.a($this$onEach, action);
    }

    @NotNull
    public static final <T> c<T> p(@NotNull c<? extends T> $this$onStart, @NotNull p<? super d<? super T>, ? super d<? super x>, ? extends Object> action) {
        return j.e($this$onStart, action);
    }

    @NotNull
    public static final <T> t<T> q(@NotNull t<? extends T> $this$onSubscription, @NotNull p<? super d<? super T>, ? super d<? super x>, ? extends Object> action) {
        return n.c($this$onSubscription, action);
    }

    @NotNull
    public static final <T, R> c<R> r(@NotNull c<? extends T> $this$transformLatest, @NotNull q<? super d<? super R>, ? super T, ? super d<? super x>, ? extends Object> transform) {
        return l.b($this$transformLatest, transform);
    }
}
