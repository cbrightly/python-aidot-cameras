package kotlinx.coroutines.flow;

import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.channels.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002BU\u0012-\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0004¢\u0006\u0002\b\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000fø\u0001\u0000¢\u0006\u0002\u0010\u0010J\u001f\u0010\u0012\u001a\u00020\u00072\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0014J&\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014R:\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0004¢\u0006\u0002\b\tX\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0011\u0002\u0004\n\u0002\b\u0019¨\u0006\u0017"}, d2 = {"Lkotlinx/coroutines/flow/CallbackFlowBuilder;", "T", "Lkotlinx/coroutines/flow/ChannelFlowBuilder;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "Lkotlin/jvm/functions/Function2;", "collectTo", "scope", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Builders.kt */
public final class a<T> extends b<T> {
    @NotNull
    private final p<u<? super T>, d<? super x>, Object> x;

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.flow.CallbackFlowBuilder", f = "Builders.kt", l = {336}, m = "collectTo")
    /* renamed from: kotlinx.coroutines.flow.a$a  reason: collision with other inner class name */
    /* compiled from: Builders.kt */
    public static final class C0442a extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0442a(a<T> aVar, d<? super C0442a> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.e((u) null, this);
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(p pVar, g gVar, int i, h hVar, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(pVar, (i2 & 2) != 0 ? kotlin.coroutines.h.INSTANCE : gVar, (i2 & 4) != 0 ? -2 : i, (i2 & 8) != 0 ? h.SUSPEND : hVar);
    }

    public a(@NotNull p<? super u<? super T>, ? super d<? super x>, ? extends Object> block, @NotNull g context, int capacity, @NotNull h onBufferOverflow) {
        super(block, context, capacity, onBufferOverflow);
        this.x = block;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlinx.coroutines.channels.u<? super T>, code=kotlinx.coroutines.channels.u, for r5v0, types: [kotlinx.coroutines.channels.u<? super T>, java.lang.Object, kotlinx.coroutines.channels.u] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object e(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.u r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof kotlinx.coroutines.flow.a.C0442a
            if (r0 == 0) goto L_0x0013
            r0 = r6
            kotlinx.coroutines.flow.a$a r0 = (kotlinx.coroutines.flow.a.C0442a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            kotlinx.coroutines.flow.a$a r0 = new kotlinx.coroutines.flow.a$a
            r0.<init>(r4, r6)
        L_0x0018:
            r6 = r0
            java.lang.Object r0 = r6.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r6.label
            switch(r2) {
                case 0: goto L_0x0034;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002c:
            java.lang.Object r5 = r6.L$0
            kotlinx.coroutines.channels.u r5 = (kotlinx.coroutines.channels.u) r5
            kotlin.p.b(r0)
            goto L_0x0044
        L_0x0034:
            kotlin.p.b(r0)
            r2 = r4
            r6.L$0 = r5
            r3 = 1
            r6.label = r3
            java.lang.Object r2 = super.e(r5, r6)
            if (r2 != r1) goto L_0x0044
            return r1
        L_0x0044:
            boolean r1 = r5.F()
            if (r1 == 0) goto L_0x004d
            kotlin.x r1 = kotlin.x.a
            return r1
        L_0x004d:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "'awaitClose { yourCallbackOrListener.cancel() }' should be used in the end of callbackFlow block.\nOtherwise, a callback/listener may leak in case of external cancellation.\nSee callbackFlow API documentation for the details."
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.a.e(kotlinx.coroutines.channels.u, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    @NotNull
    public kotlinx.coroutines.flow.internal.d<T> f(@NotNull g context, int capacity, @NotNull h onBufferOverflow) {
        return new a(this.x, context, capacity, onBufferOverflow);
    }
}
