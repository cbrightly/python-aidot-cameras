package kotlin.jvm.internal;

import kotlin.reflect.c;
import kotlin.reflect.e;

/* compiled from: PropertyReference1Impl */
public class u extends t {
    public u(e owner, String name, String signature) {
        super(c.NO_RECEIVER, ((d) owner).b(), name, signature, (owner instanceof c) ^ true ? 1 : 0);
    }

    public u(Class owner, String name, String signature, int flags) {
        super(c.NO_RECEIVER, owner, name, signature, flags);
    }

    public u(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    public Object get(Object receiver) {
        return getGetter().call(receiver);
    }
}
