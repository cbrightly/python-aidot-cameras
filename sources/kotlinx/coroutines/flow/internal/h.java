package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.z;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.flow.c;
import kotlinx.coroutines.flow.d;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.s0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003Bx\u0012B\u0010\u0004\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0006\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0005¢\u0006\u0002\b\r\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015ø\u0001\u0000¢\u0006\u0002\u0010\u0016J&\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00010\u00192\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\u001f\u0010\u001a\u001a\u00020\u000b2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006H@ø\u0001\u0000¢\u0006\u0002\u0010\u001cRO\u0010\u0004\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0006\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0005¢\u0006\u0002\b\rX\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0017\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, d2 = {"Lkotlinx/coroutines/flow/internal/ChannelFlowTransformLatest;", "T", "R", "Lkotlinx/coroutines/flow/internal/ChannelFlowOperator;", "transform", "Lkotlin/Function3;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/ParameterName;", "name", "value", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "flow", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlin/jvm/functions/Function3;Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "Lkotlin/jvm/functions/Function3;", "create", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flowCollect", "collector", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Merge.kt */
public final class h<T, R> extends f<T, R> {
    /* access modifiers changed from: private */
    @NotNull
    public final q<d<? super R>, T, kotlin.coroutines.d<? super x>, Object> x;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ h(kotlin.jvm.functions.q r7, kotlinx.coroutines.flow.c r8, kotlin.coroutines.g r9, int r10, kotlinx.coroutines.channels.h r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r13 = r12 & 4
            if (r13 == 0) goto L_0x0008
            kotlin.coroutines.h r9 = kotlin.coroutines.h.INSTANCE
            r3 = r9
            goto L_0x0009
        L_0x0008:
            r3 = r9
        L_0x0009:
            r9 = r12 & 8
            if (r9 == 0) goto L_0x0010
            r10 = -2
            r4 = r10
            goto L_0x0011
        L_0x0010:
            r4 = r10
        L_0x0011:
            r9 = r12 & 16
            if (r9 == 0) goto L_0x0019
            kotlinx.coroutines.channels.h r11 = kotlinx.coroutines.channels.h.SUSPEND
            r5 = r11
            goto L_0x001a
        L_0x0019:
            r5 = r11
        L_0x001a:
            r0 = r6
            r1 = r7
            r2 = r8
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.h.<init>(kotlin.jvm.functions.q, kotlinx.coroutines.flow.c, kotlin.coroutines.g, int, kotlinx.coroutines.channels.h, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public h(@NotNull q<? super d<? super R>, ? super T, ? super kotlin.coroutines.d<? super x>, ? extends Object> transform, @NotNull c<? extends T> flow, @NotNull g context, int capacity, @NotNull kotlinx.coroutines.channels.h onBufferOverflow) {
        super(flow, context, capacity, onBufferOverflow);
        this.x = transform;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public d<R> f(@NotNull g context, int capacity, @NotNull kotlinx.coroutines.channels.h onBufferOverflow) {
        return new h(this.x, this.q, context, capacity, onBufferOverflow);
    }

    @l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u00020\u0004H@"}, d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3", f = "Merge.kt", l = {27}, m = "invokeSuspend")
    /* compiled from: Merge.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ d<R> $collector;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ h<T, R> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(h<T, R> hVar, d<? super R> dVar, kotlin.coroutines.d<? super a> dVar2) {
            super(2, dVar2);
            this.this$0 = hVar;
            this.$collector = dVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            a aVar = new a(this.this$0, this.$collector, dVar);
            aVar.L$0 = obj;
            return aVar;
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    z previousFlow = new z();
                    h<T, R> hVar = this.this$0;
                    c<S> cVar = hVar.q;
                    C0443a aVar = new C0443a(previousFlow, (o0) this.L$0, hVar, this.$collector);
                    this.label = 1;
                    if (cVar.a(aVar, this) != d) {
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

        @l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "T", "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = 48)
        /* renamed from: kotlinx.coroutines.flow.internal.h$a$a  reason: collision with other inner class name */
        /* compiled from: Merge.kt */
        public static final class C0443a<T> implements d {
            final /* synthetic */ z<z1> c;
            final /* synthetic */ o0 d;
            final /* synthetic */ h<T, R> f;
            final /* synthetic */ d<R> q;

            @l(k = 3, mv = {1, 6, 0}, xi = 48)
            @f(c = "kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1", f = "Merge.kt", l = {30}, m = "emit")
            /* renamed from: kotlinx.coroutines.flow.internal.h$a$a$b */
            /* compiled from: Merge.kt */
            public static final class b extends kotlin.coroutines.jvm.internal.d {
                Object L$0;
                Object L$1;
                Object L$2;
                int label;
                /* synthetic */ Object result;
                final /* synthetic */ C0443a<T> this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                b(C0443a<? super T> aVar, kotlin.coroutines.d<? super b> dVar) {
                    super(dVar);
                    this.this$0 = aVar;
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object obj) {
                    this.result = obj;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.emit(null, this);
                }
            }

            C0443a(z<z1> zVar, o0 o0Var, h<T, R> hVar, d<? super R> dVar) {
                this.c = zVar;
                this.d = o0Var;
                this.f = hVar;
                this.q = dVar;
            }

            /* JADX WARNING: Can't fix incorrect switch cases order */
            /* JADX WARNING: Incorrect type for immutable var: ssa=T, code=java.lang.Object, for r11v0, types: [T, java.lang.Object] */
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r11, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r12) {
                /*
                    r10 = this;
                    boolean r0 = r12 instanceof kotlinx.coroutines.flow.internal.h.a.C0443a.b
                    if (r0 == 0) goto L_0x0013
                    r0 = r12
                    kotlinx.coroutines.flow.internal.h$a$a$b r0 = (kotlinx.coroutines.flow.internal.h.a.C0443a.b) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L_0x0013
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0018
                L_0x0013:
                    kotlinx.coroutines.flow.internal.h$a$a$b r0 = new kotlinx.coroutines.flow.internal.h$a$a$b
                    r0.<init>(r10, r12)
                L_0x0018:
                    r12 = r0
                    java.lang.Object r0 = r12.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                    int r2 = r12.label
                    switch(r2) {
                        case 0: goto L_0x003b;
                        case 1: goto L_0x002c;
                        default: goto L_0x0024;
                    }
                L_0x0024:
                    java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                    java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                    r11.<init>(r12)
                    throw r11
                L_0x002c:
                    r11 = 0
                    java.lang.Object r1 = r12.L$2
                    kotlinx.coroutines.z1 r1 = (kotlinx.coroutines.z1) r1
                    java.lang.Object r1 = r12.L$1
                    java.lang.Object r2 = r12.L$0
                    kotlinx.coroutines.flow.internal.h$a$a r2 = (kotlinx.coroutines.flow.internal.h.a.C0443a) r2
                    kotlin.p.b(r0)
                    goto L_0x0064
                L_0x003b:
                    kotlin.p.b(r0)
                    r2 = r10
                    kotlin.jvm.internal.z<kotlinx.coroutines.z1> r3 = r2.c
                    T r3 = r3.element
                    kotlinx.coroutines.z1 r3 = (kotlinx.coroutines.z1) r3
                    if (r3 != 0) goto L_0x0048
                    goto L_0x0066
                L_0x0048:
                    r4 = r3
                    r5 = 0
                    kotlinx.coroutines.flow.internal.ChildCancelledException r6 = new kotlinx.coroutines.flow.internal.ChildCancelledException
                    r6.<init>()
                    r4.c(r6)
                    r12.L$0 = r2
                    r12.L$1 = r11
                    r12.L$2 = r3
                    r3 = 1
                    r12.label = r3
                    java.lang.Object r3 = r4.J(r12)
                    if (r3 != r1) goto L_0x0062
                    return r1
                L_0x0062:
                    r1 = r11
                    r11 = r5
                L_0x0064:
                    r11 = r1
                L_0x0066:
                    kotlin.jvm.internal.z<kotlinx.coroutines.z1> r1 = r2.c
                    kotlinx.coroutines.o0 r3 = r2.d
                    r4 = 0
                    kotlinx.coroutines.q0 r5 = kotlinx.coroutines.q0.UNDISPATCHED
                    kotlinx.coroutines.flow.internal.h$a$a$a r6 = new kotlinx.coroutines.flow.internal.h$a$a$a
                    kotlinx.coroutines.flow.internal.h<T, R> r7 = r2.f
                    kotlinx.coroutines.flow.d<R> r8 = r2.q
                    r9 = 0
                    r6.<init>(r7, r8, r11, r9)
                    r7 = 1
                    r8 = 0
                    kotlinx.coroutines.z1 r3 = kotlinx.coroutines.j.d(r3, r4, r5, r6, r7, r8)
                    r1.element = r3
                    kotlin.x r1 = kotlin.x.a
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.h.a.C0443a.emit(java.lang.Object, kotlin.coroutines.d):java.lang.Object");
            }

            @l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u00020\u0004H@"}, d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
            @f(c = "kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$2", f = "Merge.kt", l = {34}, m = "invokeSuspend")
            /* renamed from: kotlinx.coroutines.flow.internal.h$a$a$a  reason: collision with other inner class name */
            /* compiled from: Merge.kt */
            public static final class C0444a extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
                final /* synthetic */ d<R> $collector;
                final /* synthetic */ T $value;
                int label;
                final /* synthetic */ h<T, R> this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0444a(h<T, R> hVar, d<? super R> dVar, T t, kotlin.coroutines.d<? super C0444a> dVar2) {
                    super(2, dVar2);
                    this.this$0 = hVar;
                    this.$collector = dVar;
                    this.$value = t;
                }

                @NotNull
                public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                    return new C0444a(this.this$0, this.$collector, this.$value, dVar);
                }

                @Nullable
                public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
                    return ((C0444a) create(o0Var, dVar)).invokeSuspend(x.a);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    Object d = kotlin.coroutines.intrinsics.c.d();
                    switch (this.label) {
                        case 0:
                            kotlin.p.b($result);
                            q n = this.this$0.x;
                            d<R> dVar = this.$collector;
                            T t = this.$value;
                            this.label = 1;
                            if (n.invoke(dVar, t, this) != d) {
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
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Object m(@NotNull d<? super R> collector, @NotNull kotlin.coroutines.d<? super x> $completion) {
        if (!s0.a() || (collector instanceof r) != 0) {
            Object e = p0.e(new a(this, collector, (kotlin.coroutines.d<? super a>) null), $completion);
            return e == kotlin.coroutines.intrinsics.c.d() ? e : x.a;
        }
        throw new AssertionError();
    }
}
