package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AnnotationDescriptor.kt */
public interface c {
    @NotNull
    Map<f, g<?>> a();

    @Nullable
    b e();

    @NotNull
    b0 getType();

    @NotNull
    o0 n();

    /* compiled from: AnnotationDescriptor.kt */
    public static final class a {
        @Nullable
        public static b a(c $this) {
            m p1 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.g($this);
            if (p1 == null) {
                return null;
            }
            if (u.r(p1)) {
                p1 = null;
            }
            if (p1 != null) {
                return kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.f(p1);
            }
            return null;
        }
    }
}
