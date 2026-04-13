package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.protobuf.h;

/* compiled from: ExtensionRegistryLite */
public class f {
    private static volatile boolean a = false;
    private static final f b = new f(true);
    private final Map<a, h.f<?, ?>> c;

    public static f d() {
        return new f();
    }

    public static f c() {
        return b;
    }

    public <ContainingType extends o> h.f<ContainingType, ?> b(ContainingType containingTypeDefaultInstance, int fieldNumber) {
        return this.c.get(new a(containingTypeDefaultInstance, fieldNumber));
    }

    public final void a(h.f<?, ?> extension) {
        this.c.put(new a(extension.b(), extension.d()), extension);
    }

    f() {
        this.c = new HashMap();
    }

    private f(boolean empty) {
        this.c = Collections.emptyMap();
    }

    /* compiled from: ExtensionRegistryLite */
    public static final class a {
        private final Object a;
        private final int b;

        a(Object object, int number) {
            this.a = object;
            this.b = number;
        }

        public int hashCode() {
            return (System.identityHashCode(this.a) * 65535) + this.b;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a other = (a) obj;
            if (this.a == other.a && this.b == other.b) {
                return true;
            }
            return false;
        }
    }
}
