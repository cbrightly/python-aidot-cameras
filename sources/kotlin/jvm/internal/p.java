package kotlin.jvm.internal;

import kotlin.reflect.g;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MutablePropertyReference */
public abstract class p extends v implements k {
    @NotNull
    public abstract /* synthetic */ k.a<V> getGetter();

    @NotNull
    public abstract /* synthetic */ g<V> getSetter();

    public p() {
    }

    public p(Object receiver) {
        super(receiver);
    }

    public p(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }
}
