package kotlin.jvm.internal;

import kotlin.reflect.c;
import kotlin.reflect.e;

/* compiled from: FunctionReferenceImpl */
public class i extends h {
    public i(int arity, e owner, String name, String signature) {
        super(arity, c.NO_RECEIVER, ((d) owner).b(), name, signature, (owner instanceof c) ^ true ? 1 : 0);
    }

    public i(int arity, Class owner, String name, String signature, int flags) {
        super(arity, c.NO_RECEIVER, owner, name, signature, flags);
    }

    public i(int arity, Object receiver, Class owner, String name, String signature, int flags) {
        super(arity, receiver, owner, name, signature, flags);
    }
}
