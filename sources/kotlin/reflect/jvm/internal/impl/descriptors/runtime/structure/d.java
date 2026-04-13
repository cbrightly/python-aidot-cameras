package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaAnnotationArguments.kt */
public abstract class d implements b {
    public static final a a = new a((DefaultConstructorMarker) null);
    @Nullable
    private final f b;

    public d(@Nullable f name) {
        this.b = name;
    }

    @Nullable
    public f getName() {
        return this.b;
    }

    /* compiled from: ReflectJavaAnnotationArguments.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final d a(@NotNull Object value, @Nullable f name) {
            k.f(value, "value");
            if (b.i(value.getClass())) {
                return new o(name, (Enum) value);
            }
            if (value instanceof Annotation) {
                return new e(name, (Annotation) value);
            }
            if (value instanceof Object[]) {
                return new h(name, (Object[]) value);
            }
            if (value instanceof Class) {
                return new k(name, (Class) value);
            }
            return new q(name, value);
        }
    }
}
