package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import org.jetbrains.annotations.NotNull;

/* compiled from: ErrorReporter */
public interface r {
    public static final r a = new a();

    void a(@NotNull b bVar);

    void b(@NotNull e eVar, @NotNull List<String> list);

    /* compiled from: ErrorReporter */
    public static final class a implements r {
        private static /* synthetic */ void c(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "unresolvedSuperClasses";
                    break;
                default:
                    objArr[0] = "descriptor";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/serialization/deserialization/ErrorReporter$1";
            switch (i) {
                case 2:
                    objArr[2] = "reportCannotInferVisibility";
                    break;
                default:
                    objArr[2] = "reportIncompleteHierarchy";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        a() {
        }

        public void b(@NotNull e descriptor, @NotNull List<String> unresolvedSuperClasses) {
            if (descriptor == null) {
                c(0);
            }
            if (unresolvedSuperClasses == null) {
                c(1);
            }
        }

        public void a(@NotNull b descriptor) {
            if (descriptor == null) {
                c(2);
            }
        }
    }
}
