package kotlin.collections;

import java.util.AbstractSet;
import java.util.Set;
import kotlin.jvm.internal.markers.e;

/* compiled from: AbstractMutableSet.kt */
public abstract class g<E> extends AbstractSet<E> implements Set<E>, e {
    public abstract int a();

    protected g() {
    }

    public final /* bridge */ int size() {
        return a();
    }
}
