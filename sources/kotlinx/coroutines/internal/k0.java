package kotlinx.coroutines.internal;

import java.lang.Comparable;
import java.util.Arrays;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.internal.l0;
import kotlinx.coroutines.s0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0018\n\u0002\u0010\u0000\n\u0002\u0018\u0002\b\u0017\u0018\u0000*\u0012\b\u0000\u0010\u0003*\u00020\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00022\u000602j\u0002`3B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00028\u0000H\u0001¢\u0006\u0004\b\b\u0010\tJ\u0015\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0004\b\n\u0010\tJ.\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00028\u00002\u0014\u0010\r\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\f0\u000bH\b¢\u0006\u0004\b\u000e\u0010\u000fJ\r\u0010\u0010\u001a\u00020\u0007¢\u0006\u0004\b\u0010\u0010\u0005J\u0011\u0010\u0011\u001a\u0004\u0018\u00018\u0000H\u0001¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\u0013\u0010\u0012J\u0017\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0014H\u0002¢\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u0019H\u0001¢\u0006\u0004\b\u001b\u0010\u001cJ&\u0010\u001e\u001a\u0004\u0018\u00018\u00002\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000bH\b¢\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010 \u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b \u0010\u0012J\u0018\u0010\"\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0019H\u0010¢\u0006\u0004\b\"\u0010#J\u0018\u0010$\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0019H\u0010¢\u0006\u0004\b$\u0010#J\u001f\u0010&\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\u0019H\u0002¢\u0006\u0004\b&\u0010'R \u0010(\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u00148\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\b(\u0010)R\u0011\u0010*\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b*\u0010+R$\u00100\u001a\u00020\u00192\u0006\u0010,\u001a\u00020\u00198F@BX\u000e¢\u0006\f\u001a\u0004\b-\u0010.\"\u0004\b/\u0010#¨\u00061"}, d2 = {"Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "", "T", "<init>", "()V", "node", "", "addImpl", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)V", "addLast", "Lkotlin/Function1;", "", "cond", "addLastIf", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;Lkotlin/jvm/functions/Function1;)Z", "clear", "firstImpl", "()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "peek", "", "realloc", "()[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "remove", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)Z", "", "index", "removeAtImpl", "(I)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "predicate", "removeFirstIf", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "removeFirstOrNull", "i", "siftDownFrom", "(I)V", "siftUpFrom", "j", "swap", "(II)V", "a", "[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "isEmpty", "()Z", "value", "getSize", "()I", "setSize", "size", "kotlinx-coroutines-core", "", "Lkotlinx/coroutines/internal/SynchronizedObject;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ThreadSafeHeap.kt */
public class k0<T extends l0 & Comparable<? super T>> {
    @NotNull
    private volatile /* synthetic */ int _size = 0;
    @Nullable
    private T[] a;

    public final int c() {
        return this._size;
    }

    private final void j(int value) {
        this._size = value;
    }

    public final boolean d() {
        return c() == 0;
    }

    @Nullable
    public final T e() {
        T b;
        synchronized (this) {
            b = b();
        }
        return b;
    }

    @Nullable
    public final T i() {
        T t;
        synchronized (this) {
            if (c() > 0) {
                t = h(0);
            } else {
                t = null;
            }
        }
        return t;
    }

    public final boolean g(@NotNull T node) {
        boolean z;
        synchronized (this) {
            z = true;
            boolean z2 = false;
            if (node.b() == null) {
                z = false;
            } else {
                int index = node.getIndex();
                if (s0.a()) {
                    if (index >= 0) {
                        z2 = true;
                    }
                    if (!z2) {
                        throw new AssertionError();
                    }
                }
                h(index);
            }
        }
        return z;
    }

    @Nullable
    public final T b() {
        T[] tArr = this.a;
        if (tArr == null) {
            return null;
        }
        return tArr[0];
    }

    @NotNull
    public final T h(int index) {
        boolean z = false;
        if (s0.a()) {
            if ((c() > 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        l0[] a2 = this.a;
        k.c(a2);
        j(c() - 1);
        if (index < c()) {
            m(index, c());
            int j = (index - 1) / 2;
            if (index > 0) {
                l0 l0Var = a2[index];
                k.c(l0Var);
                l0 l0Var2 = a2[j];
                k.c(l0Var2);
                if (((Comparable) l0Var).compareTo(l0Var2) < 0) {
                    m(index, j);
                    l(j);
                }
            }
            k(index);
        }
        l0 result = a2[c()];
        k.c(result);
        if (s0.a()) {
            if (result.b() == this) {
                z = true;
            }
            if (!z) {
                throw new AssertionError();
            }
        }
        result.a((k0<?>) null);
        result.d(-1);
        a2[c()] = null;
        return result;
    }

    public final void a(@NotNull T node) {
        if (s0.a()) {
            if (!(node.b() == null)) {
                throw new AssertionError();
            }
        }
        node.a(this);
        l0[] a2 = f();
        int i = c();
        j(i + 1);
        a2[i] = node;
        node.d(i);
        l(i);
    }

    private final void l(int i) {
        int i2 = i;
        while (i2 > 0) {
            l0[] a2 = this.a;
            k.c(a2);
            int j = (i2 - 1) / 2;
            l0 l0Var = a2[j];
            k.c(l0Var);
            l0 l0Var2 = a2[i2];
            k.c(l0Var2);
            if (((Comparable) l0Var).compareTo(l0Var2) > 0) {
                m(i2, j);
                i2 = j;
            } else {
                return;
            }
        }
    }

    private final void k(int i) {
        int i2 = i;
        while (true) {
            int j = (i2 * 2) + 1;
            if (j < c()) {
                l0[] a2 = this.a;
                k.c(a2);
                if (j + 1 < c()) {
                    l0 l0Var = a2[j + 1];
                    k.c(l0Var);
                    l0 l0Var2 = a2[j];
                    k.c(l0Var2);
                    if (((Comparable) l0Var).compareTo(l0Var2) < 0) {
                        j++;
                    }
                }
                l0 l0Var3 = a2[i2];
                k.c(l0Var3);
                l0 l0Var4 = a2[j];
                k.c(l0Var4);
                if (((Comparable) l0Var3).compareTo(l0Var4) > 0) {
                    m(i2, j);
                    i2 = j;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private final T[] f() {
        l0[] a2 = this.a;
        if (a2 == null) {
            l0[] it = new l0[4];
            this.a = it;
            return it;
        } else if (c() < a2.length) {
            return a2;
        } else {
            T[] it2 = Arrays.copyOf(a2, c() * 2);
            k.d(it2, "copyOf(this, newSize)");
            this.a = it2;
            return (l0[]) it2;
        }
    }

    private final void m(int i, int j) {
        l0[] a2 = this.a;
        k.c(a2);
        l0 ni = a2[j];
        k.c(ni);
        l0 nj = a2[i];
        k.c(nj);
        a2[i] = ni;
        a2[j] = nj;
        ni.d(i);
        nj.d(j);
    }
}
