package kotlin.sequences;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: SequencesJVM.kt */
public final class a<T> implements h<T> {
    private final AtomicReference<h<T>> a;

    public a(@NotNull h<? extends T> sequence) {
        k.e(sequence, "sequence");
        this.a = new AtomicReference<>(sequence);
    }

    @NotNull
    public Iterator<T> iterator() {
        h sequence = this.a.getAndSet((Object) null);
        if (sequence != null) {
            return sequence.iterator();
        }
        throw new IllegalStateException("This sequence can be consumed only once.");
    }
}
