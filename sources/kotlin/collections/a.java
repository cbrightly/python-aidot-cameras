package kotlin.collections;

import com.meituan.robust.Constants;
import java.util.Collection;
import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;

/* compiled from: AbstractCollection.kt */
public abstract class a<E> implements Collection<E>, kotlin.jvm.internal.markers.a {
    public abstract int a();

    public boolean add(E e) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    protected a() {
    }

    public final /* bridge */ int size() {
        return a();
    }

    public boolean contains(Object element) {
        if (isEmpty()) {
            return false;
        }
        for (Object element$iv : this) {
            if (k.a(element$iv, element)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(@NotNull Collection<? extends Object> elements) {
        k.e(elements, "elements");
        Collection<? extends Object> collection = elements;
        if (collection.isEmpty()) {
            return true;
        }
        for (Object it : collection) {
            if (!contains(it)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /* renamed from: kotlin.collections.a$a  reason: collision with other inner class name */
    /* compiled from: AbstractCollection.kt */
    public static final class C0316a extends l implements kotlin.jvm.functions.l<E, CharSequence> {
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0316a(a aVar) {
            super(1);
            this.this$0 = aVar;
        }

        @NotNull
        public final CharSequence invoke(E it) {
            return it == this.this$0 ? "(this Collection)" : String.valueOf(it);
        }
    }

    @NotNull
    public String toString() {
        return y.b0(this, ", ", Constants.ARRAY_TYPE, "]", 0, (CharSequence) null, new C0316a(this), 24, (Object) null);
    }

    @NotNull
    public Object[] toArray() {
        return f.a(this);
    }

    @NotNull
    public <T> T[] toArray(@NotNull T[] array) {
        k.e(array, "array");
        T[] b = f.b(this, array);
        if (b != null) {
            return b;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
    }
}
