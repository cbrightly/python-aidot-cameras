package kotlin.coroutines.intrinsics;

import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.h;
import kotlin.coroutines.jvm.internal.j;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: IntrinsicsJvm.kt */
public class b {
    @NotNull
    public static final <T> kotlin.coroutines.d<x> a(@NotNull l<? super kotlin.coroutines.d<? super T>, ? extends Object> $this$createCoroutineUnintercepted, @NotNull kotlin.coroutines.d<? super T> completion) {
        k.e($this$createCoroutineUnintercepted, "$this$createCoroutineUnintercepted");
        k.e(completion, "completion");
        kotlin.coroutines.d probeCompletion = h.a(completion);
        if ($this$createCoroutineUnintercepted instanceof kotlin.coroutines.jvm.internal.a) {
            return ((kotlin.coroutines.jvm.internal.a) $this$createCoroutineUnintercepted).create(probeCompletion);
        }
        g context$iv = probeCompletion.getContext();
        if (context$iv == kotlin.coroutines.h.INSTANCE) {
            return new a(probeCompletion, probeCompletion, $this$createCoroutineUnintercepted);
        }
        return new C0323b(probeCompletion, context$iv, probeCompletion, context$iv, $this$createCoroutineUnintercepted);
    }

    @NotNull
    public static final <R, T> kotlin.coroutines.d<x> b(@NotNull p<? super R, ? super kotlin.coroutines.d<? super T>, ? extends Object> $this$createCoroutineUnintercepted, R receiver, @NotNull kotlin.coroutines.d<? super T> completion) {
        k.e($this$createCoroutineUnintercepted, "$this$createCoroutineUnintercepted");
        k.e(completion, "completion");
        kotlin.coroutines.d probeCompletion = h.a(completion);
        if ($this$createCoroutineUnintercepted instanceof kotlin.coroutines.jvm.internal.a) {
            return ((kotlin.coroutines.jvm.internal.a) $this$createCoroutineUnintercepted).create(receiver, probeCompletion);
        }
        g context$iv = probeCompletion.getContext();
        if (context$iv == kotlin.coroutines.h.INSTANCE) {
            return new c(probeCompletion, probeCompletion, $this$createCoroutineUnintercepted, receiver);
        }
        return new d(probeCompletion, context$iv, probeCompletion, context$iv, $this$createCoroutineUnintercepted, receiver);
    }

    @NotNull
    public static final <T> kotlin.coroutines.d<T> c(@NotNull kotlin.coroutines.d<? super T> $this$intercepted) {
        kotlin.coroutines.d<Object> intercepted;
        k.e($this$intercepted, "$this$intercepted");
        kotlin.coroutines.jvm.internal.d dVar = (kotlin.coroutines.jvm.internal.d) (!($this$intercepted instanceof kotlin.coroutines.jvm.internal.d) ? null : $this$intercepted);
        return (dVar == null || (intercepted = dVar.intercepted()) == null) ? $this$intercepted : intercepted;
    }

    /* compiled from: IntrinsicsJvm.kt */
    public static final class a extends j {
        final /* synthetic */ kotlin.coroutines.d $completion;
        final /* synthetic */ l $this_createCoroutineUnintercepted$inlined;
        private int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(kotlin.coroutines.d $captured_local_variable$1, kotlin.coroutines.d $super_call_param$2, l lVar) {
            super($super_call_param$2);
            this.$completion = $captured_local_variable$1;
            this.$this_createCoroutineUnintercepted$inlined = lVar;
        }

        /* access modifiers changed from: protected */
        @Nullable
        public Object invokeSuspend(@NotNull Object result) {
            switch (this.label) {
                case 0:
                    this.label = 1;
                    kotlin.p.b(result);
                    l lVar = this.$this_createCoroutineUnintercepted$inlined;
                    if (lVar != null) {
                        return ((l) e0.e(lVar, 1)).invoke(this);
                    }
                    throw new NullPointerException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                case 1:
                    this.label = 2;
                    kotlin.p.b(result);
                    return result;
                default:
                    throw new IllegalStateException("This coroutine had already completed".toString());
            }
        }
    }

    /* compiled from: IntrinsicsJvm.kt */
    public static final class c extends j {
        final /* synthetic */ kotlin.coroutines.d $completion;
        final /* synthetic */ Object $receiver$inlined;
        final /* synthetic */ p $this_createCoroutineUnintercepted$inlined;
        private int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(kotlin.coroutines.d $captured_local_variable$1, kotlin.coroutines.d $super_call_param$2, p pVar, Object obj) {
            super($super_call_param$2);
            this.$completion = $captured_local_variable$1;
            this.$this_createCoroutineUnintercepted$inlined = pVar;
            this.$receiver$inlined = obj;
        }

        /* access modifiers changed from: protected */
        @Nullable
        public Object invokeSuspend(@NotNull Object result) {
            switch (this.label) {
                case 0:
                    this.label = 1;
                    kotlin.p.b(result);
                    p pVar = this.$this_createCoroutineUnintercepted$inlined;
                    if (pVar != null) {
                        return ((p) e0.e(pVar, 2)).invoke(this.$receiver$inlined, this);
                    }
                    throw new NullPointerException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                case 1:
                    this.label = 2;
                    kotlin.p.b(result);
                    return result;
                default:
                    throw new IllegalStateException("This coroutine had already completed".toString());
            }
        }
    }

    /* renamed from: kotlin.coroutines.intrinsics.b$b  reason: collision with other inner class name */
    /* compiled from: IntrinsicsJvm.kt */
    public static final class C0323b extends kotlin.coroutines.jvm.internal.d {
        final /* synthetic */ kotlin.coroutines.d $completion;
        final /* synthetic */ g $context;
        final /* synthetic */ l $this_createCoroutineUnintercepted$inlined;
        private int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public C0323b(kotlin.coroutines.d $captured_local_variable$1, g $captured_local_variable$2, kotlin.coroutines.d $super_call_param$3, g $super_call_param$4, l lVar) {
            super($super_call_param$3, $super_call_param$4);
            this.$completion = $captured_local_variable$1;
            this.$context = $captured_local_variable$2;
            this.$this_createCoroutineUnintercepted$inlined = lVar;
        }

        /* access modifiers changed from: protected */
        @Nullable
        public Object invokeSuspend(@NotNull Object result) {
            switch (this.label) {
                case 0:
                    this.label = 1;
                    kotlin.p.b(result);
                    l lVar = this.$this_createCoroutineUnintercepted$inlined;
                    if (lVar != null) {
                        return ((l) e0.e(lVar, 1)).invoke(this);
                    }
                    throw new NullPointerException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                case 1:
                    this.label = 2;
                    kotlin.p.b(result);
                    return result;
                default:
                    throw new IllegalStateException("This coroutine had already completed".toString());
            }
        }
    }

    /* compiled from: IntrinsicsJvm.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.d {
        final /* synthetic */ kotlin.coroutines.d $completion;
        final /* synthetic */ g $context;
        final /* synthetic */ Object $receiver$inlined;
        final /* synthetic */ p $this_createCoroutineUnintercepted$inlined;
        private int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(kotlin.coroutines.d $captured_local_variable$1, g $captured_local_variable$2, kotlin.coroutines.d $super_call_param$3, g $super_call_param$4, p pVar, Object obj) {
            super($super_call_param$3, $super_call_param$4);
            this.$completion = $captured_local_variable$1;
            this.$context = $captured_local_variable$2;
            this.$this_createCoroutineUnintercepted$inlined = pVar;
            this.$receiver$inlined = obj;
        }

        /* access modifiers changed from: protected */
        @Nullable
        public Object invokeSuspend(@NotNull Object result) {
            switch (this.label) {
                case 0:
                    this.label = 1;
                    kotlin.p.b(result);
                    p pVar = this.$this_createCoroutineUnintercepted$inlined;
                    if (pVar != null) {
                        return ((p) e0.e(pVar, 2)).invoke(this.$receiver$inlined, this);
                    }
                    throw new NullPointerException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                case 1:
                    this.label = 2;
                    kotlin.p.b(result);
                    return result;
                default:
                    throw new IllegalStateException("This coroutine had already completed".toString());
            }
        }
    }
}
