package kotlinx.coroutines;

import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\b\u0018\u00002\u00020\u0001BZ\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012%\b\u0002\u0010\u0005\u001a\u001f\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\u000eJ\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0004HÆ\u0003J&\u0010\u0015\u001a\u001f\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0007HÆ\u0003J`\u0010\u0018\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042%\b\u0002\u0010\u0005\u001a\u001f\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u00102\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\u001a\u0010\u001d\u001a\u00020\u000b2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001f2\u0006\u0010\n\u001a\u00020\u0007J\t\u0010 \u001a\u00020!HÖ\u0001R\u0012\u0010\r\u001a\u0004\u0018\u00010\u00078\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\f\u001a\u0004\u0018\u00010\u00018\u0006X\u0004¢\u0006\u0002\n\u0000R-\u0010\u0005\u001a\u001f\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00018\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lkotlinx/coroutines/CompletedContinuation;", "", "result", "cancelHandler", "Lkotlinx/coroutines/CancelHandler;", "onCancellation", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "idempotentResume", "cancelCause", "(Ljava/lang/Object;Lkotlinx/coroutines/CancelHandler;Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelled", "", "getCancelled", "()Z", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "invokeHandlers", "cont", "Lkotlinx/coroutines/CancellableContinuationImpl;", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: CancellableContinuationImpl.kt */
public final class a0 {
    @Nullable
    public final Object a;
    @Nullable
    public final l b;
    @Nullable
    public final kotlin.jvm.functions.l<Throwable, x> c;
    @Nullable
    public final Object d;
    @Nullable
    public final Throwable e;

    public static /* synthetic */ a0 b(a0 a0Var, Object obj, l lVar, kotlin.jvm.functions.l<Throwable, x> lVar2, Object obj2, Throwable th, int i, Object obj3) {
        if ((i & 1) != 0) {
            obj = a0Var.a;
        }
        if ((i & 2) != 0) {
            lVar = a0Var.b;
        }
        l lVar3 = lVar;
        if ((i & 4) != 0) {
            lVar2 = a0Var.c;
        }
        kotlin.jvm.functions.l<Throwable, x> lVar4 = lVar2;
        if ((i & 8) != 0) {
            obj2 = a0Var.d;
        }
        Object obj4 = obj2;
        if ((i & 16) != 0) {
            th = a0Var.e;
        }
        return a0Var.a(obj, lVar3, lVar4, obj4, th);
    }

    @NotNull
    public final a0 a(@Nullable Object obj, @Nullable l lVar, @Nullable kotlin.jvm.functions.l<? super Throwable, x> lVar2, @Nullable Object obj2, @Nullable Throwable th) {
        return new a0(obj, lVar, lVar2, obj2, th);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a0)) {
            return false;
        }
        a0 a0Var = (a0) obj;
        return k.a(this.a, a0Var.a) && k.a(this.b, a0Var.b) && k.a(this.c, a0Var.c) && k.a(this.d, a0Var.d) && k.a(this.e, a0Var.e);
    }

    public int hashCode() {
        Object obj = this.a;
        int i = 0;
        int hashCode = (obj == null ? 0 : obj.hashCode()) * 31;
        l lVar = this.b;
        int hashCode2 = (hashCode + (lVar == null ? 0 : lVar.hashCode())) * 31;
        kotlin.jvm.functions.l<Throwable, x> lVar2 = this.c;
        int hashCode3 = (hashCode2 + (lVar2 == null ? 0 : lVar2.hashCode())) * 31;
        Object obj2 = this.d;
        int hashCode4 = (hashCode3 + (obj2 == null ? 0 : obj2.hashCode())) * 31;
        Throwable th = this.e;
        if (th != null) {
            i = th.hashCode();
        }
        return hashCode4 + i;
    }

    @NotNull
    public String toString() {
        return "CompletedContinuation(result=" + this.a + ", cancelHandler=" + this.b + ", onCancellation=" + this.c + ", idempotentResume=" + this.d + ", cancelCause=" + this.e + ')';
    }

    public a0(@Nullable Object result, @Nullable l cancelHandler, @Nullable kotlin.jvm.functions.l<? super Throwable, x> onCancellation, @Nullable Object idempotentResume, @Nullable Throwable cancelCause) {
        this.a = result;
        this.b = cancelHandler;
        this.c = onCancellation;
        this.d = idempotentResume;
        this.e = cancelCause;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ a0(java.lang.Object r8, kotlinx.coroutines.l r9, kotlin.jvm.functions.l r10, java.lang.Object r11, java.lang.Throwable r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r7 = this;
            r14 = r13 & 2
            r0 = 0
            if (r14 == 0) goto L_0x0007
            r3 = r0
            goto L_0x0008
        L_0x0007:
            r3 = r9
        L_0x0008:
            r9 = r13 & 4
            if (r9 == 0) goto L_0x000e
            r4 = r0
            goto L_0x000f
        L_0x000e:
            r4 = r10
        L_0x000f:
            r9 = r13 & 8
            if (r9 == 0) goto L_0x0015
            r5 = r0
            goto L_0x0016
        L_0x0015:
            r5 = r11
        L_0x0016:
            r9 = r13 & 16
            if (r9 == 0) goto L_0x001c
            r6 = r0
            goto L_0x001d
        L_0x001c:
            r6 = r12
        L_0x001d:
            r1 = r7
            r2 = r8
            r1.<init>(r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.a0.<init>(java.lang.Object, kotlinx.coroutines.l, kotlin.jvm.functions.l, java.lang.Object, java.lang.Throwable, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final boolean c() {
        return this.e != null;
    }

    public final void d(@NotNull o<?> cont, @NotNull Throwable cause) {
        l it = this.b;
        if (it != null) {
            cont.m(it, cause);
        }
        kotlin.jvm.functions.l it2 = this.c;
        if (it2 != null) {
            cont.n(it2, cause);
        }
    }
}
