package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.d;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaAnnotationOwner.kt */
public interface f extends d {
    @Nullable
    AnnotatedElement n();

    /* compiled from: ReflectJavaAnnotationOwner.kt */
    public static final class a {
        /* JADX WARNING: Code restructure failed: missing block: B:4:0x000c, code lost:
            r0 = kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.g.b((r0 = r0.getDeclaredAnnotations()));
         */
        @org.jetbrains.annotations.NotNull
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static java.util.List<kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.c> b(kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.f r1) {
            /*
                java.lang.reflect.AnnotatedElement r0 = r1.n()
                if (r0 == 0) goto L_0x0013
                java.lang.annotation.Annotation[] r0 = r0.getDeclaredAnnotations()
                if (r0 == 0) goto L_0x0013
                java.util.List r0 = kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.g.b(r0)
                if (r0 == 0) goto L_0x0013
                goto L_0x0017
            L_0x0013:
                java.util.List r0 = kotlin.collections.q.g()
            L_0x0017:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.f.a.b(kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.f):java.util.List");
        }

        @Nullable
        public static c a(f $this, @NotNull b fqName) {
            Annotation[] declaredAnnotations;
            k.f(fqName, "fqName");
            AnnotatedElement n = $this.n();
            if (n == null || (declaredAnnotations = n.getDeclaredAnnotations()) == null) {
                return null;
            }
            return g.a(declaredAnnotations, fqName);
        }

        public static boolean c(f $this) {
            return false;
        }
    }
}
