package kotlin.reflect.jvm.internal.impl.types;

import org.slf4j.e;

/* compiled from: TypeProjectionBase */
public abstract class x0 implements w0 {
    public String toString() {
        if (b()) {
            return e.ANY_MARKER;
        }
        if (c() == h1.INVARIANT) {
            return getType().toString();
        }
        return c() + " " + getType();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof w0)) {
            return false;
        }
        w0 that = (w0) o;
        if (b() == that.b() && c() == that.c() && getType().equals(that.getType())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = c().hashCode();
        if (c1.v(getType())) {
            return (result * 31) + 19;
        }
        return (result * 31) + (b() ? 17 : getType().hashCode());
    }
}
