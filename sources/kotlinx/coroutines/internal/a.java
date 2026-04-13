package kotlinx.coroutines.internal;

import kotlin.collections.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0010\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0013\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000¢\u0006\u0002\u0010\u0010J\u0006\u0010\u0011\u001a\u00020\u000eJ\b\u0010\u0012\u001a\u00020\u000eH\u0002J\r\u0010\u0013\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0014R\u0018\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0005X\u000e¢\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u000e\u0010\f\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lkotlinx/coroutines/internal/ArrayQueue;", "T", "", "()V", "elements", "", "[Ljava/lang/Object;", "head", "", "isEmpty", "", "()Z", "tail", "addLast", "", "element", "(Ljava/lang/Object;)V", "clear", "ensureCapacity", "removeFirstOrNull", "()Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ArrayQueue.kt */
public class a<T> {
    @NotNull
    private Object[] a = new Object[16];
    private int b;
    private int c;

    public final boolean c() {
        return this.b == this.c;
    }

    public final void a(@NotNull T element) {
        Object[] objArr = this.a;
        int i = this.c;
        objArr[i] = element;
        int length = (objArr.length - 1) & (i + 1);
        this.c = length;
        if (length == this.b) {
            b();
        }
    }

    @Nullable
    public final T d() {
        int i = this.b;
        if (i == this.c) {
            return null;
        }
        Object[] objArr = this.a;
        Object element = objArr[i];
        objArr[i] = null;
        this.b = (i + 1) & (objArr.length - 1);
        if (element != null) {
            return element;
        }
        throw new NullPointerException("null cannot be cast to non-null type T of kotlinx.coroutines.internal.ArrayQueue");
    }

    private final void b() {
        Object[] objArr = this.a;
        int currentSize = objArr.length;
        Object[] newElements = new Object[(currentSize << 1)];
        Object[] newElements2 = newElements;
        k.g(objArr, newElements2, 0, this.b, 0, 10, (Object) null);
        Object[] objArr2 = this.a;
        int length = objArr2.length;
        int i = this.b;
        k.g(objArr2, newElements, length - i, 0, i, 4, (Object) null);
        this.a = newElements2;
        this.b = 0;
        this.c = currentSize;
    }
}
