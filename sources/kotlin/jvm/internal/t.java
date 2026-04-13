package kotlin.jvm.internal;

import kotlin.reflect.b;
import kotlin.reflect.m;

/* compiled from: PropertyReference1 */
public abstract class t extends v implements m {
    public abstract /* synthetic */ V get(T t);

    public t() {
    }

    public t(Object receiver) {
        super(receiver);
    }

    public t(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    /* access modifiers changed from: protected */
    public b computeReflected() {
        return a0.h(this);
    }

    public Object invoke(Object receiver) {
        return get(receiver);
    }

    public m.a getGetter() {
        return ((m) getReflected()).getGetter();
    }

    public Object getDelegate(Object receiver) {
        return ((m) getReflected()).getDelegate(receiver);
    }
}
