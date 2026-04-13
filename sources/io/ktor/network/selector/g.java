package io.ktor.network.selector;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.i;
import kotlin.x;
import kotlinx.coroutines.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: InterestSuspensionsMap.kt */
public final class g {
    /* access modifiers changed from: private */
    public static final AtomicReferenceFieldUpdater<g, n<x>>[] a;
    public static final a b = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public volatile n<? super x> acceptHandlerReference;
    /* access modifiers changed from: private */
    public volatile n<? super x> connectHandlerReference;
    /* access modifiers changed from: private */
    public volatile n<? super x> readHandlerReference;
    /* access modifiers changed from: private */
    public volatile n<? super x> writeHandlerReference;

    public final void j(@NotNull j interest, @NotNull n<? super x> continuation) {
        k.f(interest, "interest");
        k.f(continuation, "continuation");
        if (!b.b(interest).compareAndSet(this, (Object) null, continuation)) {
            throw new IllegalStateException("Handler for " + interest.name() + " is already registered");
        }
    }

    @Nullable
    public final n<x> l(@NotNull j interest) {
        k.f(interest, "interest");
        return (n) b.b(interest).getAndSet(this, (Object) null);
    }

    @Nullable
    public final n<x> k(int interestOrdinal) {
        return a[interestOrdinal].getAndSet(this, (Object) null);
    }

    @NotNull
    public String toString() {
        return "R " + this.readHandlerReference + " W " + this.writeHandlerReference + " C " + this.connectHandlerReference + " A " + this.acceptHandlerReference;
    }

    /* compiled from: InterestSuspensionsMap.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* access modifiers changed from: private */
        public final AtomicReferenceFieldUpdater<g, n<x>> b(j interest) {
            return g.a[interest.ordinal()];
        }
    }

    static {
        i property;
        j[] a2 = j.Companion.a();
        ArrayList arrayList = new ArrayList(a2.length);
        j[] jVarArr = a2;
        int length = jVarArr.length;
        int i = 0;
        while (i < length) {
            switch (b.a[jVarArr[i].ordinal()]) {
                case 1:
                    property = c.INSTANCE;
                    break;
                case 2:
                    property = d.INSTANCE;
                    break;
                case 3:
                    property = e.INSTANCE;
                    break;
                case 4:
                    property = f.INSTANCE;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            AtomicReferenceFieldUpdater<U, W> newUpdater = AtomicReferenceFieldUpdater.newUpdater(g.class, n.class, property.getName());
            if (newUpdater != null) {
                arrayList.add(newUpdater);
                i++;
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.util.concurrent.atomic.AtomicReferenceFieldUpdater<io.ktor.network.selector.InterestSuspensionsMap, kotlinx.coroutines.CancellableContinuation<kotlin.Unit>?>");
            }
        }
        ArrayList arrayList2 = arrayList;
        Object[] array = arrayList.toArray(new AtomicReferenceFieldUpdater[0]);
        if (array != null) {
            a = (AtomicReferenceFieldUpdater[]) array;
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }
}
