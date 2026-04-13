package kotlin.jvm.internal;

import kotlin.reflect.c;
import kotlin.reflect.e;

/* compiled from: MutablePropertyReference1Impl */
public class o extends n {
    public o(e owner, String name, String signature) {
        super(c.NO_RECEIVER, ((d) owner).b(), name, signature, (owner instanceof c) ^ true ? 1 : 0);
    }

    public o(Class owner, String name, String signature, int flags) {
        super(c.NO_RECEIVER, owner, name, signature, flags);
    }

    public o(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    public Object get(Object receiver) {
        return getGetter().call(receiver);
    }

    public void set(Object receiver, Object value) {
        getSetter().call(receiver, value);
    }
}
