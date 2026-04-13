package kotlin.jvm.internal;

import kotlin.reflect.b;
import kotlin.reflect.i;
import kotlin.reflect.m;

/* compiled from: MutablePropertyReference1 */
public abstract class n extends p implements i {
    public abstract /* synthetic */ V get(T t);

    public abstract /* synthetic */ void set(T t, V v);

    public n() {
    }

    public n(Object receiver) {
        super(receiver);
    }

    public n(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    /* access modifiers changed from: protected */
    public b computeReflected() {
        return a0.f(this);
    }

    public Object invoke(Object receiver) {
        return get(receiver);
    }

    public m.a getGetter() {
        return ((i) getReflected()).getGetter();
    }

    public i.a getSetter() {
        return ((i) getReflected()).getSetter();
    }

    public Object getDelegate(Object receiver) {
        return ((i) getReflected()).getDelegate(receiver);
    }
}
