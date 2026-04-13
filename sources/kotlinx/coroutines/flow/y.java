package kotlinx.coroutines.flow;

import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.flow.internal.k;
import kotlinx.coroutines.flow.internal.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u0010062\b\u0012\u0004\u0012\u00028\u0000072\b\u0012\u0004\u0012\u00028\u0000082\b\u0012\u0004\u0012\u00028\u000009B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J!\u0010\t\u001a\u00020\b2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006H@ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00028\u00002\u0006\u0010\f\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0011\u001a\u00020\u0010H\u0014¢\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u00152\u0006\u0010\u0014\u001a\u00020\u0013H\u0014¢\u0006\u0004\b\u0016\u0010\u0017J\u001b\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001bJ-\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000!2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\u001fH\u0016¢\u0006\u0004\b\"\u0010#J\u000f\u0010$\u001a\u00020\u0019H\u0016¢\u0006\u0004\b$\u0010%J\u0017\u0010&\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00028\u0000H\u0016¢\u0006\u0004\b&\u0010'J!\u0010*\u001a\u00020\r2\b\u0010(\u001a\u0004\u0018\u00010\u00022\u0006\u0010)\u001a\u00020\u0002H\u0002¢\u0006\u0004\b*\u0010\u000fR\u001a\u0010.\u001a\b\u0012\u0004\u0012\u00028\u00000+8VX\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0016\u0010/\u001a\u00020\u00138\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\b/\u00100R*\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00028\u00008V@VX\u000e¢\u0006\u0012\u0012\u0004\b4\u0010%\u001a\u0004\b1\u00102\"\u0004\b3\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u00065"}, d2 = {"Lkotlinx/coroutines/flow/StateFlowImpl;", "T", "", "initialState", "<init>", "(Ljava/lang/Object;)V", "Lkotlinx/coroutines/flow/FlowCollector;", "collector", "", "collect", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "expect", "update", "", "compareAndSet", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "Lkotlinx/coroutines/flow/StateFlowSlot;", "createSlot", "()Lkotlinx/coroutines/flow/StateFlowSlot;", "", "size", "", "createSlotArray", "(I)[Lkotlinx/coroutines/flow/StateFlowSlot;", "value", "", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlin/coroutines/CoroutineContext;", "context", "capacity", "Lkotlinx/coroutines/channels/BufferOverflow;", "onBufferOverflow", "Lkotlinx/coroutines/flow/Flow;", "fuse", "(Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)Lkotlinx/coroutines/flow/Flow;", "resetReplayCache", "()V", "tryEmit", "(Ljava/lang/Object;)Z", "expectedState", "newState", "updateState", "", "getReplayCache", "()Ljava/util/List;", "replayCache", "sequence", "I", "getValue", "()Ljava/lang/Object;", "setValue", "getValue$annotations", "kotlinx-coroutines-core", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lkotlinx/coroutines/flow/CancellableFlow;", "Lkotlinx/coroutines/flow/internal/FusibleFlow;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: StateFlow.kt */
public final class y<T> extends kotlinx.coroutines.flow.internal.a<a0> implements q<T>, k<T>, k {
    @NotNull
    private volatile /* synthetic */ Object _state;
    private int x;

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.flow.StateFlowImpl", f = "StateFlow.kt", l = {386, 398, 403}, m = "collect")
    /* compiled from: StateFlow.kt */
    public static final class a extends d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ y<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(y<T> yVar, kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
            this.this$0 = yVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a((d) null, this);
        }
    }

    public y(@NotNull Object initialState) {
        this._state = initialState;
    }

    public T getValue() {
        Object this_$iv = n.a;
        Object value$iv = this._state;
        if (value$iv == this_$iv) {
            return null;
        }
        return value$iv;
    }

    public void setValue(T value) {
        m((Object) null, value == null ? n.a : value);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0036, code lost:
        r2 = (kotlinx.coroutines.flow.a0[]) r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
        if (r2 != null) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003d, code lost:
        r4 = r2.length;
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0040, code lost:
        if (r6 >= r4) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0042, code lost:
        r8 = r2[r6];
        r6 = r6 + 1;
        r9 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0048, code lost:
        if (r9 != null) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004b, code lost:
        r9.f();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0052, code lost:
        monitor-enter(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r4 = r11.x;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0056, code lost:
        if (r4 != r0) goto L_0x005f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0058, code lost:
        r11.x = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x005d, code lost:
        monitor-exit(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x005e, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x005f, code lost:
        r0 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r1 = j();
        r3 = kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0068, code lost:
        monitor-exit(r11);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean m(java.lang.Object r12, java.lang.Object r13) {
        /*
            r11 = this;
            r0 = 0
            r1 = 0
            kotlinx.coroutines.flow.internal.c[] r1 = r11.j()
            r2 = 0
            monitor-enter(r11)
            r3 = 0
            java.lang.Object r4 = r11._state     // Catch:{ all -> 0x0075 }
            r5 = 0
            if (r12 == 0) goto L_0x0016
            boolean r6 = kotlin.jvm.internal.k.a(r4, r12)     // Catch:{ all -> 0x0075 }
            if (r6 != 0) goto L_0x0016
            monitor-exit(r11)
            return r5
        L_0x0016:
            boolean r6 = kotlin.jvm.internal.k.a(r4, r13)     // Catch:{ all -> 0x0075 }
            r7 = 1
            if (r6 == 0) goto L_0x001f
            monitor-exit(r11)
            return r7
        L_0x001f:
            r11._state = r13     // Catch:{ all -> 0x0075 }
            int r6 = r11.x     // Catch:{ all -> 0x0075 }
            r0 = r6
            r6 = r0 & 1
            if (r6 != 0) goto L_0x006e
            int r0 = r0 + 1
            r11.x = r0     // Catch:{ all -> 0x0075 }
            kotlinx.coroutines.flow.internal.c[] r6 = r11.j()     // Catch:{ all -> 0x0075 }
            r1 = r6
            kotlin.x r3 = kotlin.x.a     // Catch:{ all -> 0x0075 }
            monitor-exit(r11)
        L_0x0036:
            r2 = r1
            kotlinx.coroutines.flow.a0[] r2 = (kotlinx.coroutines.flow.a0[]) r2
            if (r2 != 0) goto L_0x003d
            goto L_0x0051
        L_0x003d:
            r3 = 0
            int r4 = r2.length
            r6 = r5
        L_0x0040:
            if (r6 >= r4) goto L_0x0050
            r8 = r2[r6]
            int r6 = r6 + 1
            r9 = r8
            r10 = 0
            if (r9 != 0) goto L_0x004b
            goto L_0x004e
        L_0x004b:
            r9.f()
        L_0x004e:
            goto L_0x0040
        L_0x0050:
        L_0x0051:
            r2 = 0
            monitor-enter(r11)
            r3 = 0
            int r4 = r11.x     // Catch:{ all -> 0x006b }
            if (r4 != r0) goto L_0x005f
            int r4 = r0 + 1
            r11.x = r4     // Catch:{ all -> 0x006b }
            monitor-exit(r11)
            return r7
        L_0x005f:
            r0 = r4
            kotlinx.coroutines.flow.internal.c[] r4 = r11.j()     // Catch:{ all -> 0x006b }
            r1 = r4
            kotlin.x r3 = kotlin.x.a     // Catch:{ all -> 0x006b }
            monitor-exit(r11)
            goto L_0x0036
        L_0x006b:
            r3 = move-exception
            monitor-exit(r11)
            throw r3
        L_0x006e:
            int r5 = r0 + 2
            r11.x = r5     // Catch:{ all -> 0x0075 }
            monitor-exit(r11)
            return r7
        L_0x0075:
            r3 = move-exception
            monitor-exit(r11)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.y.m(java.lang.Object, java.lang.Object):boolean");
    }

    @Nullable
    public Object emit(T value, @NotNull kotlin.coroutines.d<? super x> $completion) {
        setValue(value);
        return x.a;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: kotlinx.coroutines.flow.a0} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: kotlinx.coroutines.flow.d<? super T>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: kotlinx.coroutines.flow.y} */
    /* JADX WARNING: type inference failed for: r11v15, types: [kotlinx.coroutines.flow.internal.c] */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0091, code lost:
        r2 = (kotlinx.coroutines.z1) r12.getContext().get(kotlinx.coroutines.z1.g);
        r11 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a2, code lost:
        r6 = r5._state;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a5, code lost:
        if (r2 != null) goto L_0x00a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a8, code lost:
        kotlinx.coroutines.c2.j(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ab, code lost:
        if (r11 == null) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b1, code lost:
        if (kotlin.jvm.internal.k.a(r11, r6) != false) goto L_0x00d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b6, code lost:
        if (r6 != kotlinx.coroutines.flow.internal.n.a) goto L_0x00ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b8, code lost:
        r8 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ba, code lost:
        r8 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00bb, code lost:
        r12.L$0 = r5;
        r12.L$1 = r4;
        r12.L$2 = r3;
        r12.L$3 = r2;
        r12.L$4 = r6;
        r12.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00cc, code lost:
        if (r4.emit(r8, r12) != r1) goto L_0x00cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ce, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00cf, code lost:
        r11 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00d0, code lost:
        r11 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d6, code lost:
        if (r3.g() != false) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d8, code lost:
        r12.L$0 = r5;
        r12.L$1 = r4;
        r12.L$2 = r3;
        r12.L$3 = r2;
        r12.L$4 = r11;
        r12.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00e9, code lost:
        if (r3.d(r12) != r1) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00eb, code lost:
        return r1;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(@org.jetbrains.annotations.NotNull kotlinx.coroutines.flow.d<? super T> r11, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<?> r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof kotlinx.coroutines.flow.y.a
            if (r0 == 0) goto L_0x0013
            r0 = r12
            kotlinx.coroutines.flow.y$a r0 = (kotlinx.coroutines.flow.y.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            kotlinx.coroutines.flow.y$a r0 = new kotlinx.coroutines.flow.y$a
            r0.<init>(r10, r12)
        L_0x0018:
            r12 = r0
            java.lang.Object r0 = r12.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r12.label
            switch(r2) {
                case 0: goto L_0x006d;
                case 1: goto L_0x005a;
                case 2: goto L_0x0043;
                case 3: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002c:
            java.lang.Object r11 = r12.L$4
            java.lang.Object r2 = r12.L$3
            kotlinx.coroutines.z1 r2 = (kotlinx.coroutines.z1) r2
            java.lang.Object r3 = r12.L$2
            kotlinx.coroutines.flow.a0 r3 = (kotlinx.coroutines.flow.a0) r3
            java.lang.Object r4 = r12.L$1
            kotlinx.coroutines.flow.d r4 = (kotlinx.coroutines.flow.d) r4
            java.lang.Object r5 = r12.L$0
            kotlinx.coroutines.flow.y r5 = (kotlinx.coroutines.flow.y) r5
            kotlin.p.b(r0)     // Catch:{ all -> 0x00ed }
            goto L_0x00ec
        L_0x0043:
            java.lang.Object r11 = r12.L$4
            java.lang.Object r2 = r12.L$3
            kotlinx.coroutines.z1 r2 = (kotlinx.coroutines.z1) r2
            java.lang.Object r3 = r12.L$2
            kotlinx.coroutines.flow.a0 r3 = (kotlinx.coroutines.flow.a0) r3
            java.lang.Object r4 = r12.L$1
            kotlinx.coroutines.flow.d r4 = (kotlinx.coroutines.flow.d) r4
            java.lang.Object r5 = r12.L$0
            kotlinx.coroutines.flow.y r5 = (kotlinx.coroutines.flow.y) r5
            kotlin.p.b(r0)     // Catch:{ all -> 0x00ed }
            goto L_0x00d0
        L_0x005a:
            java.lang.Object r11 = r12.L$2
            r3 = r11
            kotlinx.coroutines.flow.a0 r3 = (kotlinx.coroutines.flow.a0) r3
            java.lang.Object r11 = r12.L$1
            r4 = r11
            kotlinx.coroutines.flow.d r4 = (kotlinx.coroutines.flow.d) r4
            java.lang.Object r11 = r12.L$0
            r5 = r11
            kotlinx.coroutines.flow.y r5 = (kotlinx.coroutines.flow.y) r5
            kotlin.p.b(r0)     // Catch:{ all -> 0x00ed }
            goto L_0x0091
        L_0x006d:
            kotlin.p.b(r0)
            r5 = r10
            r4 = r11
            kotlinx.coroutines.flow.internal.c r11 = r5.e()
            r3 = r11
            kotlinx.coroutines.flow.a0 r3 = (kotlinx.coroutines.flow.a0) r3
            boolean r11 = r4 instanceof kotlinx.coroutines.flow.b0     // Catch:{ all -> 0x00ed }
            if (r11 == 0) goto L_0x0091
            r11 = r4
            kotlinx.coroutines.flow.b0 r11 = (kotlinx.coroutines.flow.b0) r11     // Catch:{ all -> 0x00ed }
            r12.L$0 = r5     // Catch:{ all -> 0x00ed }
            r12.L$1 = r4     // Catch:{ all -> 0x00ed }
            r12.L$2 = r3     // Catch:{ all -> 0x00ed }
            r2 = 1
            r12.label = r2     // Catch:{ all -> 0x00ed }
            java.lang.Object r11 = r11.a(r12)     // Catch:{ all -> 0x00ed }
            if (r11 != r1) goto L_0x0091
            return r1
        L_0x0091:
            r11 = 0
            kotlin.coroutines.g r2 = r12.getContext()     // Catch:{ all -> 0x00ed }
            kotlinx.coroutines.z1$b r11 = kotlinx.coroutines.z1.g     // Catch:{ all -> 0x00ed }
            kotlin.coroutines.g$b r11 = r2.get(r11)     // Catch:{ all -> 0x00ed }
            kotlinx.coroutines.z1 r11 = (kotlinx.coroutines.z1) r11     // Catch:{ all -> 0x00ed }
            r2 = 0
            r9 = r2
            r2 = r11
            r11 = r9
        L_0x00a2:
            java.lang.Object r6 = r5._state     // Catch:{ all -> 0x00ed }
            if (r2 != 0) goto L_0x00a8
            goto L_0x00ab
        L_0x00a8:
            kotlinx.coroutines.c2.j(r2)     // Catch:{ all -> 0x00ed }
        L_0x00ab:
            if (r11 == 0) goto L_0x00b3
            boolean r7 = kotlin.jvm.internal.k.a(r11, r6)     // Catch:{ all -> 0x00ed }
            if (r7 != 0) goto L_0x00d2
        L_0x00b3:
            kotlinx.coroutines.internal.f0 r11 = kotlinx.coroutines.flow.internal.n.a     // Catch:{ all -> 0x00ed }
            r7 = 0
            if (r6 != r11) goto L_0x00ba
            r8 = 0
            goto L_0x00bb
        L_0x00ba:
            r8 = r6
        L_0x00bb:
            r12.L$0 = r5     // Catch:{ all -> 0x00ed }
            r12.L$1 = r4     // Catch:{ all -> 0x00ed }
            r12.L$2 = r3     // Catch:{ all -> 0x00ed }
            r12.L$3 = r2     // Catch:{ all -> 0x00ed }
            r12.L$4 = r6     // Catch:{ all -> 0x00ed }
            r11 = 2
            r12.label = r11     // Catch:{ all -> 0x00ed }
            java.lang.Object r11 = r4.emit(r8, r12)     // Catch:{ all -> 0x00ed }
            if (r11 != r1) goto L_0x00cf
            return r1
        L_0x00cf:
            r11 = r6
        L_0x00d0:
            r6 = r11
            r11 = r6
        L_0x00d2:
            boolean r6 = r3.g()     // Catch:{ all -> 0x00ed }
            if (r6 != 0) goto L_0x00a2
            r12.L$0 = r5     // Catch:{ all -> 0x00ed }
            r12.L$1 = r4     // Catch:{ all -> 0x00ed }
            r12.L$2 = r3     // Catch:{ all -> 0x00ed }
            r12.L$3 = r2     // Catch:{ all -> 0x00ed }
            r12.L$4 = r11     // Catch:{ all -> 0x00ed }
            r6 = 3
            r12.label = r6     // Catch:{ all -> 0x00ed }
            java.lang.Object r6 = r3.d(r12)     // Catch:{ all -> 0x00ed }
            if (r6 != r1) goto L_0x00ec
            return r1
        L_0x00ec:
            goto L_0x00a2
        L_0x00ed:
            r11 = move-exception
            r5.h(r3)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.y.a(kotlinx.coroutines.flow.d, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: k */
    public a0 f() {
        return new a0();
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: l */
    public a0[] g(int size) {
        return new a0[size];
    }

    @NotNull
    public c<T> b(@NotNull g context, int capacity, @NotNull h onBufferOverflow) {
        return z.d(this, context, capacity, onBufferOverflow);
    }
}
