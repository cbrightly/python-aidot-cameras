package kotlin.reflect;

import java.lang.annotation.Annotation;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: KAnnotatedElement.kt */
public interface a {
    @NotNull
    List<Annotation> getAnnotations();
}
