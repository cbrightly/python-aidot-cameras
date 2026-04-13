package kotlin.jvm.internal;

import kotlin.reflect.b;
import kotlin.reflect.f;

/* compiled from: FunctionReference */
public class h extends c implements g, f {
    private final int arity;
    private final int flags;

    public h(int arity2) {
        this(arity2, c.NO_RECEIVER, (Class) null, (String) null, (String) null, 0);
    }

    public h(int arity2, Object receiver) {
        this(arity2, receiver, (Class) null, (String) null, (String) null, 0);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public h(int arity2, Object receiver, Class owner, String name, String signature, int flags2) {
        super(receiver, owner, name, signature, (flags2 & 1) != 1 ? false : true);
        this.arity = arity2;
        this.flags = flags2 >> 1;
    }

    public int getArity() {
        return this.arity;
    }

    /* access modifiers changed from: protected */
    public f getReflected() {
        return (f) super.getReflected();
    }

    /* access modifiers changed from: protected */
    public b computeReflected() {
        return a0.a(this);
    }

    public boolean isInline() {
        return getReflected().isInline();
    }

    public boolean isExternal() {
        return getReflected().isExternal();
    }

    public boolean isOperator() {
        return getReflected().isOperator();
    }

    public boolean isInfix() {
        return getReflected().isInfix();
    }

    public boolean isSuspend() {
        return getReflected().isSuspend();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof h) {
            h other = (h) obj;
            if (!k.a(getOwner(), other.getOwner()) || !getName().equals(other.getName()) || !getSignature().equals(other.getSignature()) || this.flags != other.flags || this.arity != other.arity || !k.a(getBoundReceiver(), other.getBoundReceiver())) {
                return false;
            }
            return true;
        } else if (obj instanceof f) {
            return obj.equals(compute());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (((getOwner() == null ? 0 : getOwner().hashCode() * 31) + getName().hashCode()) * 31) + getSignature().hashCode();
    }

    public String toString() {
        b reflected = compute();
        if (reflected != this) {
            return reflected.toString();
        }
        if ("<init>".equals(getName())) {
            return "constructor (Kotlin reflection is not available)";
        }
        return "function " + getName() + " (Kotlin reflection is not available)";
    }
}
