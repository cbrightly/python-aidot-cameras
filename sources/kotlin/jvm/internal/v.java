package kotlin.jvm.internal;

import kotlin.reflect.b;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: PropertyReference */
public abstract class v extends c implements k {
    @NotNull
    public abstract /* synthetic */ k.a<V> getGetter();

    public v() {
    }

    public v(Object receiver) {
        super(receiver);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public v(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, (flags & 1) != 1 ? false : true);
    }

    /* access modifiers changed from: protected */
    public k getReflected() {
        return (k) super.getReflected();
    }

    public boolean isLateinit() {
        return getReflected().isLateinit();
    }

    public boolean isConst() {
        return getReflected().isConst();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof v) {
            v other = (v) obj;
            if (!getOwner().equals(other.getOwner()) || !getName().equals(other.getName()) || !getSignature().equals(other.getSignature()) || !k.a(getBoundReceiver(), other.getBoundReceiver())) {
                return false;
            }
            return true;
        } else if (obj instanceof k) {
            return obj.equals(compute());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (((getOwner().hashCode() * 31) + getName().hashCode()) * 31) + getSignature().hashCode();
    }

    public String toString() {
        b reflected = compute();
        if (reflected != this) {
            return reflected.toString();
        }
        return "property " + getName() + " (Kotlin reflection is not available)";
    }
}
