package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: Iterators.kt */
public abstract class o implements Iterator<Character>, a {
    public abstract char e();

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @NotNull
    /* renamed from: b */
    public final Character next() {
        return Character.valueOf(e());
    }
}
