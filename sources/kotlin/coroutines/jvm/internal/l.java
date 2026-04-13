package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.d;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContinuationImpl.kt */
public abstract class l extends d implements g<Object> {
    private final int arity;

    public l(int arity2, @Nullable d<Object> completion) {
        super(completion);
        this.arity = arity2;
    }

    public int getArity() {
        return this.arity;
    }

    public l(int arity2) {
        this(arity2, (d<Object>) null);
    }

    @NotNull
    public String toString() {
        if (getCompletion() != null) {
            return super.toString();
        }
        String i = a0.i(this);
        k.d(i, "Reflection.renderLambdaToString(this)");
        return i;
    }
}
