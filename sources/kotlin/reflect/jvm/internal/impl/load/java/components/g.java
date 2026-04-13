package kotlin.reflect.jvm.internal.impl.load.java.components;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.load.java.structure.l;
import kotlin.reflect.jvm.internal.impl.load.java.structure.n;
import kotlin.reflect.jvm.internal.impl.load.java.structure.q;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaResolverCache */
public interface g {
    public static final g a = new a();

    void a(@NotNull l lVar, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.l lVar2);

    void b(@NotNull n nVar, @NotNull i0 i0Var);

    void c(@NotNull q qVar, @NotNull n0 n0Var);

    @Nullable
    e d(@NotNull b bVar);

    void e(@NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.g gVar, @NotNull e eVar);

    /* compiled from: JavaResolverCache */
    public static final class a implements g {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = FirebaseAnalytics.Param.METHOD;
                    break;
                case 2:
                case 4:
                case 6:
                case 8:
                    objArr[0] = "descriptor";
                    break;
                case 3:
                    objArr[0] = "element";
                    break;
                case 5:
                    objArr[0] = "field";
                    break;
                case 7:
                    objArr[0] = "javaClass";
                    break;
                default:
                    objArr[0] = "fqName";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/components/JavaResolverCache$1";
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "recordMethod";
                    break;
                case 3:
                case 4:
                    objArr[2] = "recordConstructor";
                    break;
                case 5:
                case 6:
                    objArr[2] = "recordField";
                    break;
                case 7:
                case 8:
                    objArr[2] = "recordClass";
                    break;
                default:
                    objArr[2] = "getClassResolvedFromSource";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        a() {
        }

        @Nullable
        public e d(@NotNull b fqName) {
            if (fqName != null) {
                return null;
            }
            f(0);
            return null;
        }

        public void c(@NotNull q method, @NotNull n0 descriptor) {
            if (method == null) {
                f(1);
            }
            if (descriptor == null) {
                f(2);
            }
        }

        public void a(@NotNull l element, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.l descriptor) {
            if (element == null) {
                f(3);
            }
            if (descriptor == null) {
                f(4);
            }
        }

        public void b(@NotNull n field, @NotNull i0 descriptor) {
            if (field == null) {
                f(5);
            }
            if (descriptor == null) {
                f(6);
            }
        }

        public void e(@NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.g javaClass, @NotNull e descriptor) {
            if (javaClass == null) {
                f(7);
            }
            if (descriptor == null) {
                f(8);
            }
        }
    }
}
