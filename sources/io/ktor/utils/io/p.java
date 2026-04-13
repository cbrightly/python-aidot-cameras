package io.ktor.utils.io;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.TypeCastException;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.b;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.o;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: ConditionJVM.kt */
public final class p {
    /* access modifiers changed from: private */
    public static final AtomicReferenceFieldUpdater<p, d<x>> a;
    public static final a b = new a((DefaultConstructorMarker) null);
    @NotNull
    private final kotlin.jvm.functions.a<Boolean> c;
    private volatile d<? super x> cond;

    @NotNull
    public final kotlin.jvm.functions.a<Boolean> b() {
        return this.c;
    }

    public final void c() {
        d cond2 = this.cond;
        if (cond2 != null && this.c.invoke().booleanValue() && a.compareAndSet(this, cond2, (Object) null)) {
            d<? super x> c2 = b.c(cond2);
            x xVar = x.a;
            o.a aVar = o.Companion;
            c2.resumeWith(o.m17constructorimpl(xVar));
        }
    }

    @NotNull
    public String toString() {
        return "Condition(cond=" + this.cond + ')';
    }

    /* compiled from: ConditionJVM.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        AtomicReferenceFieldUpdater<p, d<x>> newUpdater = AtomicReferenceFieldUpdater.newUpdater(p.class, d.class, "cond");
        if (newUpdater != null) {
            a = newUpdater;
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.util.concurrent.atomic.AtomicReferenceFieldUpdater<io.ktor.utils.io.Condition, kotlin.coroutines.Continuation<kotlin.Unit>?>");
    }
}
