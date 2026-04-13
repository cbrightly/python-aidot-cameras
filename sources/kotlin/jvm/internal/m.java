package kotlin.jvm.internal;

import kotlin.reflect.b;
import kotlin.reflect.h;
import kotlin.reflect.l;

/* compiled from: MutablePropertyReference0 */
public abstract class m extends p implements h {
    public abstract /* synthetic */ V get();

    public abstract /* synthetic */ void set(V v);

    public m() {
    }

    public m(Object receiver) {
        super(receiver);
    }

    public m(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    /* access modifiers changed from: protected */
    public b computeReflected() {
        return a0.e(this);
    }

    public Object invoke() {
        return get();
    }

    public l.a getGetter() {
        return ((h) getReflected()).getGetter();
    }

    public h.a getSetter() {
        return ((h) getReflected()).getSetter();
    }

    public Object getDelegate() {
        return ((h) getReflected()).getDelegate();
    }
}
