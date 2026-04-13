package kotlin.collections;

import java.util.AbstractCollection;
import java.util.Collection;
import kotlin.jvm.internal.markers.b;

/* compiled from: AbstractMutableCollection.kt */
public abstract class e<E> extends AbstractCollection<E> implements Collection<E>, b {
    public abstract int a();

    protected e() {
    }

    public final /* bridge */ int size() {
        return a();
    }
}
