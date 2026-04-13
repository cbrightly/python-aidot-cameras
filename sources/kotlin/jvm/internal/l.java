package kotlin.jvm.internal;

import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

/* compiled from: Lambda.kt */
public abstract class l<R> implements g<R>, Serializable {
    private final int arity;

    public l(int arity2) {
        this.arity = arity2;
    }

    public int getArity() {
        return this.arity;
    }

    @NotNull
    public String toString() {
        String j = a0.j(this);
        k.d(j, "Reflection.renderLambdaToString(this)");
        return j;
    }
}
