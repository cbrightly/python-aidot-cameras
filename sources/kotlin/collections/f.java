package kotlin.collections;

import java.util.AbstractList;
import java.util.List;
import kotlin.jvm.internal.markers.c;

/* compiled from: AbstractMutableList.kt */
public abstract class f<E> extends AbstractList<E> implements List<E>, c {
    public abstract int a();

    public abstract E b(int i);

    protected f() {
    }

    public final /* bridge */ E remove(int i) {
        return b(i);
    }

    public final /* bridge */ int size() {
        return a();
    }
}
