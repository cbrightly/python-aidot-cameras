package kotlinx.coroutines.flow;

import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.l;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.flow.internal.k;
import kotlinx.coroutines.n;
import kotlinx.coroutines.q;
import kotlinx.coroutines.s0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000f\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0012\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00042\b\u0012\u0004\u0012\u0002H\u00010\u00052\b\u0012\u0004\u0012\u0002H\u00010\u0006:\u0001hB\u001d\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0019\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0003H@ø\u0001\u0000¢\u0006\u0002\u0010.J\u0010\u0010/\u001a\u00020,2\u0006\u00100\u001a\u000201H\u0002J\b\u00102\u001a\u00020,H\u0002J\u001f\u00103\u001a\u0002042\f\u00105\u001a\b\u0012\u0004\u0012\u00028\u000006H@ø\u0001\u0000¢\u0006\u0002\u00107J\u0010\u00108\u001a\u00020,2\u0006\u00109\u001a\u00020\u0012H\u0002J\b\u0010:\u001a\u00020\u0003H\u0014J\u001d\u0010;\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000e2\u0006\u0010<\u001a\u00020\bH\u0014¢\u0006\u0002\u0010=J\b\u0010>\u001a\u00020,H\u0002J\u0019\u0010?\u001a\u00020,2\u0006\u0010@\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010AJ\u0019\u0010B\u001a\u00020,2\u0006\u0010@\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010AJ\u0012\u0010C\u001a\u00020,2\b\u0010D\u001a\u0004\u0018\u00010\u000fH\u0002J1\u0010E\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020,\u0018\u00010F0\u000e2\u0014\u0010G\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020,\u0018\u00010F0\u000eH\u0002¢\u0006\u0002\u0010HJ&\u0010I\u001a\b\u0012\u0004\u0012\u00028\u00000J2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010N\u001a\u0004\u0018\u00010\u000f2\u0006\u0010O\u001a\u00020\u0012H\u0002J7\u0010P\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e2\u0010\u0010Q\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\u000e2\u0006\u0010R\u001a\u00020\b2\u0006\u0010S\u001a\u00020\bH\u0002¢\u0006\u0002\u0010TJ\b\u0010U\u001a\u00020,H\u0016J\u0015\u0010V\u001a\u00020W2\u0006\u0010@\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010XJ\u0015\u0010Y\u001a\u00020W2\u0006\u0010@\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010XJ\u0015\u0010Z\u001a\u00020W2\u0006\u0010@\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010XJ\u0010\u0010[\u001a\u00020\u00122\u0006\u0010-\u001a\u00020\u0003H\u0002J\u0012\u0010\\\u001a\u0004\u0018\u00010\u000f2\u0006\u0010-\u001a\u00020\u0003H\u0002J(\u0010]\u001a\u00020,2\u0006\u0010^\u001a\u00020\u00122\u0006\u0010_\u001a\u00020\u00122\u0006\u0010`\u001a\u00020\u00122\u0006\u0010a\u001a\u00020\u0012H\u0002J%\u0010b\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020,\u0018\u00010F0\u000e2\u0006\u0010c\u001a\u00020\u0012H\u0000¢\u0006\u0004\bd\u0010eJ\r\u0010f\u001a\u00020\u0012H\u0000¢\u0006\u0002\bgR\u001a\u0010\r\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\u000eX\u000e¢\u0006\u0004\n\u0002\u0010\u0010R\u000e\u0010\t\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00128BX\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0014R\u001a\u0010\u0018\u001a\u00028\u00008DX\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u0012X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u00128BX\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0014R\u000e\u0010 \u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000\"8VX\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020\u0012X\u000e¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\u00020\b8BX\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0014\u0010)\u001a\u00020\b8BX\u0004¢\u0006\u0006\u001a\u0004\b*\u0010(\u0002\u0004\n\u0002\b\u0019¨\u0006i"}, d2 = {"Lkotlinx/coroutines/flow/SharedFlowImpl;", "T", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "Lkotlinx/coroutines/flow/SharedFlowSlot;", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lkotlinx/coroutines/flow/CancellableFlow;", "Lkotlinx/coroutines/flow/internal/FusibleFlow;", "replay", "", "bufferCapacity", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(IILkotlinx/coroutines/channels/BufferOverflow;)V", "buffer", "", "", "[Ljava/lang/Object;", "bufferEndIndex", "", "getBufferEndIndex", "()J", "bufferSize", "head", "getHead", "lastReplayedLocked", "getLastReplayedLocked$annotations", "()V", "getLastReplayedLocked", "()Ljava/lang/Object;", "minCollectorIndex", "queueEndIndex", "getQueueEndIndex", "queueSize", "replayCache", "", "getReplayCache", "()Ljava/util/List;", "replayIndex", "replaySize", "getReplaySize", "()I", "totalSize", "getTotalSize", "awaitValue", "", "slot", "(Lkotlinx/coroutines/flow/SharedFlowSlot;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelEmitter", "emitter", "Lkotlinx/coroutines/flow/SharedFlowImpl$Emitter;", "cleanupTailLocked", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "correctCollectorIndexesOnDropOldest", "newHead", "createSlot", "createSlotArray", "size", "(I)[Lkotlinx/coroutines/flow/SharedFlowSlot;", "dropOldestLocked", "emit", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitSuspend", "enqueueLocked", "item", "findSlotsToResumeLocked", "Lkotlin/coroutines/Continuation;", "resumesIn", "([Lkotlin/coroutines/Continuation;)[Lkotlin/coroutines/Continuation;", "fuse", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "getPeekedValueLockedAt", "index", "growBuffer", "curBuffer", "curSize", "newSize", "([Ljava/lang/Object;II)[Ljava/lang/Object;", "resetReplayCache", "tryEmit", "", "(Ljava/lang/Object;)Z", "tryEmitLocked", "tryEmitNoCollectorsLocked", "tryPeekLocked", "tryTakeValue", "updateBufferLocked", "newReplayIndex", "newMinCollectorIndex", "newBufferEndIndex", "newQueueEndIndex", "updateCollectorIndexLocked", "oldIndex", "updateCollectorIndexLocked$kotlinx_coroutines_core", "(J)[Lkotlin/coroutines/Continuation;", "updateNewCollectorIndexLocked", "updateNewCollectorIndexLocked$kotlinx_coroutines_core", "Emitter", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: SharedFlow.kt */
public class u<T> extends kotlinx.coroutines.flow.internal.a<w> implements p<T>, k<T>, k {
    private long a1;
    private int a2;
    @Nullable
    private Object[] p0;
    private long p1;
    /* access modifiers changed from: private */
    public int p2;
    private final int x;
    /* access modifiers changed from: private */
    public final int y;
    @NotNull
    private final h z;

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SharedFlow.kt */
    public final /* synthetic */ class b {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[h.values().length];
            iArr[h.SUSPEND.ordinal()] = 1;
            iArr[h.DROP_LATEST.ordinal()] = 2;
            iArr[h.DROP_OLDEST.ordinal()] = 3;
            a = iArr;
        }
    }

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.flow.SharedFlowImpl", f = "SharedFlow.kt", l = {373, 380, 383}, m = "collect$suspendImpl")
    /* compiled from: SharedFlow.kt */
    public static final class c extends d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ u<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(u<T> uVar, kotlin.coroutines.d<? super c> dVar) {
            super(dVar);
            this.this$0 = uVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return u.x(this.this$0, (d) null, this);
        }
    }

    @Nullable
    public Object a(@NotNull d<? super T> dVar, @NotNull kotlin.coroutines.d<?> dVar2) {
        return x(this, dVar, dVar2);
    }

    @Nullable
    public Object emit(T t, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return C(this, t, dVar);
    }

    public u(int replay, int bufferCapacity, @NotNull h onBufferOverflow) {
        this.x = replay;
        this.y = bufferCapacity;
        this.z = onBufferOverflow;
    }

    /* access modifiers changed from: private */
    public final long H() {
        return Math.min(this.p1, this.a1);
    }

    private final int K() {
        return (int) ((H() + ((long) this.a2)) - this.a1);
    }

    /* access modifiers changed from: private */
    public final int L() {
        return this.a2 + this.p2;
    }

    private final long G() {
        return H() + ((long) this.a2);
    }

    private final long J() {
        return H() + ((long) this.a2) + ((long) this.p2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: kotlinx.coroutines.flow.w} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: kotlinx.coroutines.flow.d} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: kotlinx.coroutines.flow.u} */
    /* JADX WARNING: type inference failed for: r6v8, types: [kotlinx.coroutines.flow.internal.c] */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x008d, code lost:
        r6 = (kotlinx.coroutines.z1) r8.getContext().get(kotlinx.coroutines.z1.g);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x009b, code lost:
        r4 = r3.R(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a2, code lost:
        if (r4 == kotlinx.coroutines.flow.v.a) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a4, code lost:
        if (r6 != null) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a7, code lost:
        kotlinx.coroutines.c2.j(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00aa, code lost:
        r8.L$0 = r3;
        r8.L$1 = r2;
        r8.L$2 = r7;
        r8.L$3 = r6;
        r8.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b9, code lost:
        if (r2.emit(r4, r8) != r1) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bb, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00bd, code lost:
        r8.L$0 = r3;
        r8.L$1 = r2;
        r8.L$2 = r7;
        r8.L$3 = r6;
        r8.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00cc, code lost:
        if (r3.u(r7, r8) != r1) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ce, code lost:
        return r1;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object x(kotlinx.coroutines.flow.u r6, kotlinx.coroutines.flow.d r7, kotlin.coroutines.d r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.u.c
            if (r0 == 0) goto L_0x0013
            r0 = r8
            kotlinx.coroutines.flow.u$c r0 = (kotlinx.coroutines.flow.u.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            kotlinx.coroutines.flow.u$c r0 = new kotlinx.coroutines.flow.u$c
            r0.<init>(r6, r8)
        L_0x0018:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x0069;
                case 1: goto L_0x0056;
                case 2: goto L_0x0041;
                case 3: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002c:
            java.lang.Object r6 = r8.L$3
            kotlinx.coroutines.z1 r6 = (kotlinx.coroutines.z1) r6
            java.lang.Object r7 = r8.L$2
            kotlinx.coroutines.flow.w r7 = (kotlinx.coroutines.flow.w) r7
            java.lang.Object r2 = r8.L$1
            kotlinx.coroutines.flow.d r2 = (kotlinx.coroutines.flow.d) r2
            java.lang.Object r3 = r8.L$0
            kotlinx.coroutines.flow.u r3 = (kotlinx.coroutines.flow.u) r3
            kotlin.p.b(r0)     // Catch:{ all -> 0x00d0 }
            goto L_0x00bc
        L_0x0041:
            java.lang.Object r6 = r8.L$3
            kotlinx.coroutines.z1 r6 = (kotlinx.coroutines.z1) r6
            java.lang.Object r7 = r8.L$2
            kotlinx.coroutines.flow.w r7 = (kotlinx.coroutines.flow.w) r7
            java.lang.Object r2 = r8.L$1
            kotlinx.coroutines.flow.d r2 = (kotlinx.coroutines.flow.d) r2
            java.lang.Object r3 = r8.L$0
            kotlinx.coroutines.flow.u r3 = (kotlinx.coroutines.flow.u) r3
            kotlin.p.b(r0)     // Catch:{ all -> 0x00d0 }
            goto L_0x00cf
        L_0x0056:
            java.lang.Object r6 = r8.L$2
            r7 = r6
            kotlinx.coroutines.flow.w r7 = (kotlinx.coroutines.flow.w) r7
            java.lang.Object r6 = r8.L$1
            r2 = r6
            kotlinx.coroutines.flow.d r2 = (kotlinx.coroutines.flow.d) r2
            java.lang.Object r6 = r8.L$0
            r3 = r6
            kotlinx.coroutines.flow.u r3 = (kotlinx.coroutines.flow.u) r3
            kotlin.p.b(r0)     // Catch:{ all -> 0x00d0 }
            goto L_0x008d
        L_0x0069:
            kotlin.p.b(r0)
            r3 = r6
            r2 = r7
            kotlinx.coroutines.flow.internal.c r6 = r3.e()
            r7 = r6
            kotlinx.coroutines.flow.w r7 = (kotlinx.coroutines.flow.w) r7
            boolean r6 = r2 instanceof kotlinx.coroutines.flow.b0     // Catch:{ all -> 0x00d0 }
            if (r6 == 0) goto L_0x008d
            r6 = r2
            kotlinx.coroutines.flow.b0 r6 = (kotlinx.coroutines.flow.b0) r6     // Catch:{ all -> 0x00d0 }
            r8.L$0 = r3     // Catch:{ all -> 0x00d0 }
            r8.L$1 = r2     // Catch:{ all -> 0x00d0 }
            r8.L$2 = r7     // Catch:{ all -> 0x00d0 }
            r4 = 1
            r8.label = r4     // Catch:{ all -> 0x00d0 }
            java.lang.Object r6 = r6.a(r8)     // Catch:{ all -> 0x00d0 }
            if (r6 != r1) goto L_0x008d
            return r1
        L_0x008d:
            r6 = 0
            kotlin.coroutines.g r4 = r8.getContext()     // Catch:{ all -> 0x00d0 }
            kotlinx.coroutines.z1$b r6 = kotlinx.coroutines.z1.g     // Catch:{ all -> 0x00d0 }
            kotlin.coroutines.g$b r6 = r4.get(r6)     // Catch:{ all -> 0x00d0 }
            kotlinx.coroutines.z1 r6 = (kotlinx.coroutines.z1) r6     // Catch:{ all -> 0x00d0 }
        L_0x009a:
        L_0x009b:
            java.lang.Object r4 = r3.R(r7)     // Catch:{ all -> 0x00d0 }
            kotlinx.coroutines.internal.f0 r5 = kotlinx.coroutines.flow.v.a     // Catch:{ all -> 0x00d0 }
            if (r4 == r5) goto L_0x00bd
            if (r6 != 0) goto L_0x00a7
            goto L_0x00aa
        L_0x00a7:
            kotlinx.coroutines.c2.j(r6)     // Catch:{ all -> 0x00d0 }
        L_0x00aa:
            r8.L$0 = r3     // Catch:{ all -> 0x00d0 }
            r8.L$1 = r2     // Catch:{ all -> 0x00d0 }
            r8.L$2 = r7     // Catch:{ all -> 0x00d0 }
            r8.L$3 = r6     // Catch:{ all -> 0x00d0 }
            r5 = 3
            r8.label = r5     // Catch:{ all -> 0x00d0 }
            java.lang.Object r5 = r2.emit(r4, r8)     // Catch:{ all -> 0x00d0 }
            if (r5 != r1) goto L_0x00bc
            return r1
        L_0x00bc:
            goto L_0x009a
        L_0x00bd:
            r8.L$0 = r3     // Catch:{ all -> 0x00d0 }
            r8.L$1 = r2     // Catch:{ all -> 0x00d0 }
            r8.L$2 = r7     // Catch:{ all -> 0x00d0 }
            r8.L$3 = r6     // Catch:{ all -> 0x00d0 }
            r4 = 2
            r8.label = r4     // Catch:{ all -> 0x00d0 }
            java.lang.Object r4 = r3.u(r7, r8)     // Catch:{ all -> 0x00d0 }
            if (r4 != r1) goto L_0x00cf
            return r1
        L_0x00cf:
            goto L_0x009b
        L_0x00d0:
            r6 = move-exception
            r3.h(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.u.x(kotlinx.coroutines.flow.u, kotlinx.coroutines.flow.d, kotlin.coroutines.d):java.lang.Object");
    }

    public boolean N(T value) {
        int i;
        boolean z2;
        kotlin.coroutines.d[] dVarArr = kotlinx.coroutines.flow.internal.b.a;
        synchronized (this) {
            i = 0;
            if (O(value)) {
                dVarArr = F(dVarArr);
                z2 = true;
            } else {
                z2 = false;
            }
        }
        boolean emitted = z2;
        int length = dVarArr.length;
        while (i < length) {
            kotlin.coroutines.d cont = dVarArr[i];
            i++;
            if (cont != null) {
                o.a aVar = o.Companion;
                cont.resumeWith(o.m17constructorimpl(x.a));
            }
        }
        return emitted;
    }

    static /* synthetic */ Object C(u uVar, Object value, kotlin.coroutines.d $completion) {
        if (uVar.N(value)) {
            return x.a;
        }
        Object D = uVar.D(value, $completion);
        return D == kotlin.coroutines.intrinsics.c.d() ? D : x.a;
    }

    /* access modifiers changed from: private */
    public final boolean O(T value) {
        if (i() == 0) {
            return P(value);
        }
        if (this.a2 >= this.y && this.p1 <= this.a1) {
            switch (b.a[this.z.ordinal()]) {
                case 1:
                    return false;
                case 2:
                    return true;
            }
        }
        E(value);
        int i = this.a2 + 1;
        this.a2 = i;
        if (i > this.y) {
            B();
        }
        if (K() > this.x) {
            S(this.a1 + 1, this.p1, G(), J());
        }
        return true;
    }

    private final boolean P(T value) {
        if (s0.a()) {
            if (!(i() == 0)) {
                throw new AssertionError();
            }
        }
        if (this.x == 0) {
            return true;
        }
        E(value);
        int i = this.a2 + 1;
        this.a2 = i;
        if (i > this.x) {
            B();
        }
        this.p1 = H() + ((long) this.a2);
        return true;
    }

    private final void B() {
        Object[] objArr = this.p0;
        kotlin.jvm.internal.k.c(objArr);
        v.g(objArr, H(), (Object) null);
        this.a2--;
        long newHead = H() + 1;
        if (this.a1 < newHead) {
            this.a1 = newHead;
        }
        if (this.p1 < newHead) {
            y(newHead);
        }
        if (s0.a()) {
            if (!(H() == newHead)) {
                throw new AssertionError();
            }
        }
    }

    private final void y(long newHead) {
        kotlinx.coroutines.flow.internal.c[] $this$forEach$iv$iv;
        long j = newHead;
        if (!(this.d == 0 || ($this$forEach$iv$iv = this.c) == null)) {
            int i = 0;
            int length = $this$forEach$iv$iv.length;
            while (i < length) {
                kotlinx.coroutines.flow.internal.c element$iv$iv = $this$forEach$iv$iv[i];
                i++;
                kotlinx.coroutines.flow.internal.c slot$iv = element$iv$iv;
                if (slot$iv != null) {
                    w slot = (w) slot$iv;
                    long j2 = slot.a;
                    if (j2 >= 0 && j2 < j) {
                        slot.a = j;
                    }
                }
            }
        }
        this.p1 = j;
    }

    /* access modifiers changed from: private */
    public final void E(Object item) {
        int curSize = L();
        Object[] curBuffer = this.p0;
        if (curBuffer == null) {
            curBuffer = M((Object[]) null, 0, 2);
        } else if (curSize >= curBuffer.length) {
            curBuffer = M(curBuffer, curSize, curBuffer.length * 2);
        }
        v.g(curBuffer, H() + ((long) curSize), item);
    }

    private final Object[] M(Object[] curBuffer, int curSize, int newSize) {
        int i = 0;
        if (newSize > 0) {
            Object[] newBuffer = new Object[newSize];
            this.p0 = newBuffer;
            if (curBuffer == null) {
                return newBuffer;
            }
            long head = H();
            while (i < curSize) {
                int i2 = i;
                i++;
                v.g(newBuffer, ((long) i2) + head, v.f(curBuffer, ((long) i2) + head));
            }
            return newBuffer;
        }
        throw new IllegalStateException("Buffer size overflow".toString());
    }

    private final Object D(T value, kotlin.coroutines.d<? super x> $completion) {
        a aVar;
        kotlin.coroutines.d[] dVarArr;
        kotlinx.coroutines.o cancellable$iv = new kotlinx.coroutines.o(kotlin.coroutines.intrinsics.b.c($completion), 1);
        cancellable$iv.w();
        n cont = cancellable$iv;
        kotlin.coroutines.d[] dVarArr2 = kotlinx.coroutines.flow.internal.b.a;
        synchronized (this) {
            if (O(value)) {
                o.a aVar2 = o.Companion;
                cont.resumeWith(o.m17constructorimpl(x.a));
                dVarArr = F(dVarArr2);
                aVar = null;
            } else {
                a it = new a(this, ((long) L()) + H(), value, cont);
                E(it);
                this.p2 = this.p2 + 1;
                if (this.y == 0) {
                    dVarArr2 = F(dVarArr2);
                }
                dVarArr = dVarArr2;
                aVar = it;
            }
        }
        a emitter = aVar;
        if (emitter != null) {
            q.a(cont, emitter);
        }
        int i = 0;
        int length = dVarArr.length;
        while (i < length) {
            kotlin.coroutines.d r = dVarArr[i];
            i++;
            if (r != null) {
                o.a aVar3 = o.Companion;
                r.resumeWith(o.m17constructorimpl(x.a));
            }
        }
        Object t = cancellable$iv.t();
        if (t == kotlin.coroutines.intrinsics.c.d()) {
            kotlin.coroutines.jvm.internal.h.c($completion);
        }
        return t == kotlin.coroutines.intrinsics.c.d() ? t : x.a;
    }

    /* access modifiers changed from: private */
    public final void v(a emitter) {
        synchronized (this) {
            if (emitter.d >= H()) {
                Object[] buffer = this.p0;
                kotlin.jvm.internal.k.c(buffer);
                if (v.f(buffer, emitter.d) == emitter) {
                    v.g(buffer, emitter.d, v.a);
                    w();
                    x xVar = x.a;
                }
            }
        }
    }

    public final long U() {
        long index = this.a1;
        if (index < this.p1) {
            this.p1 = index;
        }
        return index;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x016d  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0170  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0177  */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.coroutines.d<kotlin.x>[] T(long r27) {
        /*
            r26 = this;
            r9 = r26
            boolean r0 = kotlinx.coroutines.s0.a()
            if (r0 == 0) goto L_0x001b
            r0 = 0
            long r1 = r9.p1
            int r1 = (r27 > r1 ? 1 : (r27 == r1 ? 0 : -1))
            if (r1 < 0) goto L_0x0011
            r0 = 1
            goto L_0x0012
        L_0x0011:
            r0 = 0
        L_0x0012:
            if (r0 == 0) goto L_0x0015
            goto L_0x001b
        L_0x0015:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x001b:
            long r0 = r9.p1
            int r0 = (r27 > r0 ? 1 : (r27 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x0024
            kotlin.coroutines.d<kotlin.x>[] r0 = kotlinx.coroutines.flow.internal.b.a
            return r0
        L_0x0024:
            long r12 = r26.H()
            r0 = 0
            int r2 = r9.a2
            long r2 = (long) r2
            long r2 = r2 + r12
            int r0 = r9.y
            r4 = 1
            if (r0 != 0) goto L_0x0039
            int r0 = r9.p2
            if (r0 <= 0) goto L_0x0039
            long r2 = r2 + r4
        L_0x0039:
            r0 = r26
            r1 = 0
            int r6 = r0.d
            if (r6 != 0) goto L_0x0045
            r20 = r12
            goto L_0x007c
        L_0x0045:
            kotlinx.coroutines.flow.internal.c[] r6 = r0.c
            if (r6 != 0) goto L_0x004e
            r20 = r12
            goto L_0x007b
        L_0x004e:
            r7 = 0
            int r8 = r6.length
            r14 = 0
        L_0x0051:
            if (r14 >= r8) goto L_0x0079
            r15 = r6[r14]
            int r14 = r14 + 1
            r16 = r15
            r17 = 0
            if (r16 == 0) goto L_0x0074
            r10 = r16
            kotlinx.coroutines.flow.w r10 = (kotlinx.coroutines.flow.w) r10
            r19 = 0
            r20 = r12
            long r11 = r10.a
            r22 = 0
            int r13 = (r11 > r22 ? 1 : (r11 == r22 ? 0 : -1))
            if (r13 < 0) goto L_0x0073
            int r11 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r11 >= 0) goto L_0x0073
            long r2 = r10.a
        L_0x0073:
            goto L_0x0076
        L_0x0074:
            r20 = r12
        L_0x0076:
            r12 = r20
            goto L_0x0051
        L_0x0079:
            r20 = r12
        L_0x007b:
        L_0x007c:
            boolean r0 = kotlinx.coroutines.s0.a()
            if (r0 == 0) goto L_0x0095
            r0 = 0
            long r6 = r9.p1
            int r1 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r1 < 0) goto L_0x008b
            r0 = 1
            goto L_0x008c
        L_0x008b:
            r0 = 0
        L_0x008c:
            if (r0 == 0) goto L_0x008f
            goto L_0x0095
        L_0x008f:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x0095:
            long r0 = r9.p1
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 > 0) goto L_0x009e
            kotlin.coroutines.d<kotlin.x>[] r0 = kotlinx.coroutines.flow.internal.b.a
            return r0
        L_0x009e:
            long r0 = r26.G()
            int r6 = r26.i()
            if (r6 <= 0) goto L_0x00b5
            long r6 = r0 - r2
            int r6 = (int) r6
            int r7 = r9.p2
            int r8 = r9.y
            int r8 = r8 - r6
            int r6 = java.lang.Math.min(r7, r8)
            goto L_0x00b7
        L_0x00b5:
            int r6 = r9.p2
        L_0x00b7:
            r10 = r6
            kotlin.coroutines.d<kotlin.x>[] r6 = kotlinx.coroutines.flow.internal.b.a
            int r7 = r9.p2
            long r7 = (long) r7
            long r11 = r0 + r7
            if (r10 <= 0) goto L_0x011a
            kotlin.coroutines.d[] r6 = new kotlin.coroutines.d[r10]
            r7 = 0
            java.lang.Object[] r8 = r9.p0
            kotlin.jvm.internal.k.c(r8)
            r13 = r0
        L_0x00ca:
            int r15 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r15 >= 0) goto L_0x0115
            r15 = r0
            long r0 = r0 + r4
            r4 = r15
            java.lang.Object r15 = kotlinx.coroutines.flow.v.f(r8, r4)
            r16 = r0
            kotlinx.coroutines.internal.f0 r0 = kotlinx.coroutines.flow.v.a
            if (r15 == r0) goto L_0x010e
            if (r15 == 0) goto L_0x0106
            r1 = r15
            kotlinx.coroutines.flow.u$a r1 = (kotlinx.coroutines.flow.u.a) r1
            int r1 = r7 + 1
            r24 = r2
            r2 = r15
            kotlinx.coroutines.flow.u$a r2 = (kotlinx.coroutines.flow.u.a) r2
            kotlin.coroutines.d<kotlin.x> r2 = r2.q
            r6[r7] = r2
            kotlinx.coroutines.flow.v.g(r8, r4, r0)
            r0 = r15
            kotlinx.coroutines.flow.u$a r0 = (kotlinx.coroutines.flow.u.a) r0
            java.lang.Object r0 = r0.f
            kotlinx.coroutines.flow.v.g(r8, r13, r0)
            r2 = 1
            long r13 = r13 + r2
            if (r1 < r10) goto L_0x00fe
            r0 = r13
            r13 = r6
            goto L_0x011d
        L_0x00fe:
            r7 = r1
            r0 = r16
            r2 = r24
            r4 = 1
            goto L_0x00ca
        L_0x0106:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "null cannot be cast to non-null type kotlinx.coroutines.flow.SharedFlowImpl.Emitter"
            r0.<init>(r1)
            throw r0
        L_0x010e:
            r24 = r2
            r0 = r16
            r4 = 1
            goto L_0x00ca
        L_0x0115:
            r24 = r2
            r0 = r13
            r13 = r6
            goto L_0x011d
        L_0x011a:
            r24 = r2
            r13 = r6
        L_0x011d:
            long r2 = r0 - r20
            int r14 = (int) r2
            int r2 = r26.i()
            if (r2 != 0) goto L_0x0129
            r2 = r0
            r24 = r2
        L_0x0129:
            long r2 = r9.a1
            int r4 = r9.x
            int r4 = java.lang.Math.min(r4, r14)
            long r4 = (long) r4
            long r4 = r0 - r4
            long r2 = java.lang.Math.max(r2, r4)
            int r4 = r9.y
            if (r4 != 0) goto L_0x0159
            int r4 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
            if (r4 >= 0) goto L_0x0159
            java.lang.Object[] r4 = r9.p0
            kotlin.jvm.internal.k.c(r4)
            java.lang.Object r4 = kotlinx.coroutines.flow.v.f(r4, r2)
            kotlinx.coroutines.internal.f0 r5 = kotlinx.coroutines.flow.v.a
            boolean r4 = kotlin.jvm.internal.k.a(r4, r5)
            if (r4 == 0) goto L_0x0159
            r4 = 1
            long r0 = r0 + r4
            long r2 = r2 + r4
            r15 = r0
            r22 = r2
            goto L_0x015c
        L_0x0159:
            r15 = r0
            r22 = r2
        L_0x015c:
            r0 = r26
            r1 = r22
            r3 = r24
            r5 = r15
            r7 = r11
            r0.S(r1, r3, r5, r7)
            r26.w()
            int r0 = r13.length
            if (r0 != 0) goto L_0x0170
            r18 = 1
            goto L_0x0172
        L_0x0170:
            r18 = 0
        L_0x0172:
            r0 = 1
            r0 = r18 ^ 1
            if (r0 == 0) goto L_0x017b
            kotlin.coroutines.d[] r13 = r9.F(r13)
        L_0x017b:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.u.T(long):kotlin.coroutines.d[]");
    }

    private final void S(long newReplayIndex, long newMinCollectorIndex, long newBufferEndIndex, long newQueueEndIndex) {
        long j = newReplayIndex;
        long j2 = newMinCollectorIndex;
        long newHead = Math.min(j2, j);
        boolean z2 = true;
        if (s0.a()) {
            if ((newHead >= H() ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        long H = H();
        while (H < newHead) {
            long index = H;
            H++;
            Object[] objArr = this.p0;
            kotlin.jvm.internal.k.c(objArr);
            v.g(objArr, index, (Object) null);
        }
        this.a1 = j;
        this.p1 = j2;
        this.a2 = (int) (newBufferEndIndex - newHead);
        this.p2 = (int) (newQueueEndIndex - newBufferEndIndex);
        if (s0.a()) {
            if ((this.a2 >= 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (s0.a()) {
            if ((this.p2 >= 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (s0.a()) {
            if (this.a1 > H() + ((long) this.a2)) {
                z2 = false;
            }
            if (!z2) {
                throw new AssertionError();
            }
        }
    }

    private final void w() {
        if (this.y != 0 || this.p2 > 1) {
            Object[] buffer = this.p0;
            kotlin.jvm.internal.k.c(buffer);
            while (this.p2 > 0 && v.f(buffer, (H() + ((long) L())) - 1) == v.a) {
                this.p2--;
                v.g(buffer, H() + ((long) L()), (Object) null);
            }
        }
    }

    private final Object R(w slot) {
        Object obj;
        kotlin.coroutines.d[] dVarArr = kotlinx.coroutines.flow.internal.b.a;
        synchronized (this) {
            long index = Q(slot);
            if (index < 0) {
                obj = v.a;
            } else {
                long oldIndex = slot.a;
                Object newValue = I(index);
                slot.a = 1 + index;
                dVarArr = T(oldIndex);
                obj = newValue;
            }
        }
        Object value = obj;
        int i = 0;
        int length = dVarArr.length;
        while (i < length) {
            kotlin.coroutines.d resume = dVarArr[i];
            i++;
            if (resume != null) {
                o.a aVar = o.Companion;
                resume.resumeWith(o.m17constructorimpl(x.a));
            }
        }
        return value;
    }

    /* access modifiers changed from: private */
    public final long Q(w slot) {
        long index = slot.a;
        if (index < G()) {
            return index;
        }
        if (this.y <= 0 && index <= H() && this.p2 != 0) {
            return index;
        }
        return -1;
    }

    private final Object I(long index) {
        Object[] objArr = this.p0;
        kotlin.jvm.internal.k.c(objArr);
        Object item = v.f(objArr, index);
        if (item instanceof a) {
            return ((a) item).f;
        }
        return item;
    }

    private final Object u(w slot, kotlin.coroutines.d<? super x> $completion) {
        x xVar;
        kotlinx.coroutines.o cancellable$iv = new kotlinx.coroutines.o(kotlin.coroutines.intrinsics.b.c($completion), 1);
        cancellable$iv.w();
        n cont = cancellable$iv;
        synchronized (this) {
            if (Q(slot) < 0) {
                slot.b = cont;
                slot.b = cont;
            } else {
                o.a aVar = o.Companion;
                cont.resumeWith(o.m17constructorimpl(x.a));
            }
            xVar = x.a;
        }
        Object t = cancellable$iv.t();
        if (t == kotlin.coroutines.intrinsics.c.d()) {
            kotlin.coroutines.jvm.internal.h.c($completion);
        }
        return t == kotlin.coroutines.intrinsics.c.d() ? t : xVar;
    }

    /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object[], java.lang.Object] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.coroutines.d<kotlin.x>[] F(kotlin.coroutines.d<kotlin.x>[] r21) {
        /*
            r20 = this;
            r0 = 0
            r0 = r21
            r1 = 0
            r2 = r21
            int r1 = r2.length
            r3 = r20
            r4 = 0
            int r5 = r3.d
            if (r5 != 0) goto L_0x0014
            r15 = r20
            goto L_0x007b
        L_0x0014:
            kotlinx.coroutines.flow.internal.c[] r5 = r3.c
            if (r5 != 0) goto L_0x001f
            r15 = r20
            r16 = r3
            goto L_0x007a
        L_0x001f:
            r6 = 0
            r7 = 0
            int r8 = r5.length
        L_0x0022:
            if (r7 >= r8) goto L_0x0076
            r9 = r5[r7]
            int r7 = r7 + 1
            r10 = r9
            r11 = 0
            if (r10 == 0) goto L_0x006d
            r12 = r10
            kotlinx.coroutines.flow.w r12 = (kotlinx.coroutines.flow.w) r12
            r13 = 0
            kotlin.coroutines.d<? super kotlin.x> r14 = r12.b
            if (r14 != 0) goto L_0x0039
            r15 = r20
            r16 = r3
            goto L_0x0071
        L_0x0039:
            r15 = r20
            long r16 = r15.Q(r12)
            r18 = 0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 >= 0) goto L_0x0048
            r16 = r3
            goto L_0x0071
        L_0x0048:
            int r2 = r0.length
            if (r1 < r2) goto L_0x005f
            int r2 = r0.length
            r16 = r3
            r3 = 2
            int r2 = r2 * r3
            int r2 = java.lang.Math.max(r3, r2)
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r0, r2)
            java.lang.String r3 = "copyOf(this, newSize)"
            kotlin.jvm.internal.k.d(r2, r3)
            r0 = r2
            goto L_0x0061
        L_0x005f:
            r16 = r3
        L_0x0061:
            r2 = r0
            kotlin.coroutines.d[] r2 = (kotlin.coroutines.d[]) r2
            int r3 = r1 + 1
            r2[r1] = r14
            r1 = 0
            r12.b = r1
            r1 = r3
            goto L_0x0071
        L_0x006d:
            r15 = r20
            r16 = r3
        L_0x0071:
            r2 = r21
            r3 = r16
            goto L_0x0022
        L_0x0076:
            r15 = r20
            r16 = r3
        L_0x007a:
        L_0x007b:
            r2 = r0
            kotlin.coroutines.d[] r2 = (kotlin.coroutines.d[]) r2
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.u.F(kotlin.coroutines.d[]):kotlin.coroutines.d[]");
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: z */
    public w f() {
        return new w();
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: A */
    public w[] g(int size) {
        return new w[size];
    }

    @NotNull
    public c<T> b(@NotNull g context, int capacity, @NotNull h onBufferOverflow) {
        return v.e(this, context, capacity, onBufferOverflow);
    }

    @l(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B1\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\nH\u0016R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00038\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lkotlinx/coroutines/flow/SharedFlowImpl$Emitter;", "Lkotlinx/coroutines/DisposableHandle;", "flow", "Lkotlinx/coroutines/flow/SharedFlowImpl;", "index", "", "value", "", "cont", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/SharedFlowImpl;JLjava/lang/Object;Lkotlin/coroutines/Continuation;)V", "dispose", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SharedFlow.kt */
    public static final class a implements f1 {
        @NotNull
        public final u<?> c;
        public long d;
        @Nullable
        public final Object f;
        @NotNull
        public final kotlin.coroutines.d<x> q;

        public a(@NotNull u<?> flow, long index, @Nullable Object value, @NotNull kotlin.coroutines.d<? super x> cont) {
            this.c = flow;
            this.d = index;
            this.f = value;
            this.q = cont;
        }

        public void dispose() {
            this.c.v(this);
        }
    }
}
