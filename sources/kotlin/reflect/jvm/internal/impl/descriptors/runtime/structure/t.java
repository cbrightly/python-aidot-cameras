package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Modifier;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.load.java.q;
import kotlin.reflect.jvm.internal.impl.load.java.structure.r;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReflectJavaModifierListOwner.kt */
public interface t extends r {
    int B();

    /* compiled from: ReflectJavaModifierListOwner.kt */
    public static final class a {
        public static boolean b(t $this) {
            return Modifier.isAbstract($this.B());
        }

        public static boolean d(t $this) {
            return Modifier.isStatic($this.B());
        }

        public static boolean c(t $this) {
            return Modifier.isFinal($this.B());
        }

        @NotNull
        public static a1 a(t $this) {
            a1 a1Var;
            int modifiers = $this.B();
            if (Modifier.isPublic(modifiers)) {
                a1 a1Var2 = z0.e;
                k.b(a1Var2, "Visibilities.PUBLIC");
                return a1Var2;
            } else if (Modifier.isPrivate(modifiers)) {
                a1 a1Var3 = z0.a;
                k.b(a1Var3, "Visibilities.PRIVATE");
                return a1Var3;
            } else if (Modifier.isProtected(modifiers)) {
                if (Modifier.isStatic(modifiers)) {
                    a1Var = q.b;
                } else {
                    a1Var = q.c;
                }
                k.b(a1Var, "if (Modifier.isStatic(mo…ies.PROTECTED_AND_PACKAGE");
                return a1Var;
            } else {
                a1 a1Var4 = q.a;
                k.b(a1Var4, "JavaVisibilities.PACKAGE_VISIBILITY");
                return a1Var4;
            }
        }
    }
}
