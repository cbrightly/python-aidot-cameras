package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import okhttp3.c0;
import okhttp3.e0;

/* compiled from: Converter */
public interface h<F, T> {
    @Nullable
    T convert(F f);

    /* compiled from: Converter */
    public static abstract class a {
        @Nullable
        public h<e0, ?> responseBodyConverter(Type type, Annotation[] annotations, t retrofit) {
            return null;
        }

        @Nullable
        public h<?, c0> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, t retrofit) {
            return null;
        }

        @Nullable
        public h<?, String> stringConverter(Type type, Annotation[] annotations, t retrofit) {
            return null;
        }

        protected static Type getParameterUpperBound(int index, ParameterizedType type) {
            return x.g(index, type);
        }

        protected static Class<?> getRawType(Type type) {
            return x.h(type);
        }
    }
}
