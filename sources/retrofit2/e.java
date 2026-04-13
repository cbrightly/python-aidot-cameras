package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

/* compiled from: CallAdapter */
public interface e<R, T> {
    Type a();

    T b(d<R> dVar);

    /* compiled from: CallAdapter */
    public static abstract class a {
        @Nullable
        public abstract e<?, ?> a(Type type, Annotation[] annotationArr, t tVar);

        protected static Type b(int index, ParameterizedType type) {
            return x.g(index, type);
        }

        protected static Class<?> c(Type type) {
            return x.h(type);
        }
    }
}
