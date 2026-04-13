package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.a;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaAnnotationOwner.kt */
public final class g {
    @NotNull
    public static final List<c> b(@NotNull Annotation[] $this$getAnnotations) {
        k.f($this$getAnnotations, "$this$getAnnotations");
        Annotation[] annotationArr = $this$getAnnotations;
        ArrayList arrayList = new ArrayList(annotationArr.length);
        for (Annotation p1 : annotationArr) {
            arrayList.add(new c(p1));
        }
        return arrayList;
    }

    @Nullable
    public static final c a(@NotNull Annotation[] $this$findAnnotation, @NotNull b fqName) {
        Annotation it;
        k.f($this$findAnnotation, "$this$findAnnotation");
        k.f(fqName, "fqName");
        Annotation[] annotationArr = $this$findAnnotation;
        int length = annotationArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                it = null;
                break;
            }
            it = annotationArr[i];
            if (k.a(b.b(a.b(a.a(it))).b(), fqName)) {
                break;
            }
            i++;
        }
        if (it != null) {
            return new c(it);
        }
        return null;
    }
}
