package kotlin.jvm.internal;

import kotlin.reflect.c;
import kotlin.reflect.e;

/* compiled from: PropertyReference0Impl */
public class s extends r {
    public s(e owner, String name, String signature) {
        super(c.NO_RECEIVER, ((d) owner).b(), name, signature, (owner instanceof c) ^ true ? 1 : 0);
    }

    public s(Class owner, String name, String signature, int flags) {
        super(c.NO_RECEIVER, owner, name, signature, flags);
    }

    public s(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    public Object get() {
        return getGetter().call(new Object[0]);
    }
}
