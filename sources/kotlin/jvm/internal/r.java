package kotlin.jvm.internal;

import kotlin.reflect.b;
import kotlin.reflect.l;

/* compiled from: PropertyReference0 */
public abstract class r extends v implements l {
    public abstract /* synthetic */ V get();

    public r() {
    }

    public r(Object receiver) {
        super(receiver);
    }

    public r(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    /* access modifiers changed from: protected */
    public b computeReflected() {
        return a0.g(this);
    }

    public Object invoke() {
        return get();
    }

    public l.a getGetter() {
        return ((l) getReflected()).getGetter();
    }

    public Object getDelegate() {
        return ((l) getReflected()).getDelegate();
    }
}
