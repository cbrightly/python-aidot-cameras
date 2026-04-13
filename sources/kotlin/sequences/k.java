package kotlin.sequences;

import java.util.Iterator;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.b;
import kotlin.jvm.functions.p;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: SequenceBuilder.kt */
public class k {

    /* compiled from: Sequences.kt */
    public static final class a implements h<T> {
        final /* synthetic */ p a;

        public a(p pVar) {
            this.a = pVar;
        }

        @NotNull
        public Iterator<T> iterator() {
            return k.a(this.a);
        }
    }

    @NotNull
    public static final <T> h<T> b(@NotNull p<? super j<? super T>, ? super d<? super x>, ? extends Object> block) {
        kotlin.jvm.internal.k.e(block, "block");
        return new a(block);
    }

    @NotNull
    public static final <T> Iterator<T> a(@NotNull p<? super j<? super T>, ? super d<? super x>, ? extends Object> block) {
        kotlin.jvm.internal.k.e(block, "block");
        i iterator = new i();
        iterator.m(b.b(block, iterator, iterator));
        return iterator;
    }
}
