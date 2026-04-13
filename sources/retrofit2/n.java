package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import javax.annotation.Nullable;
import okhttp3.e0;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import retrofit2.h;

@IgnoreJRERequirement
/* compiled from: OptionalConverterFactory */
public final class n extends h.a {
    static final h.a a = new n();

    n() {
    }

    @Nullable
    public h<e0, ?> responseBodyConverter(Type type, Annotation[] annotations, t retrofit) {
        if (h.a.getRawType(type) != Optional.class) {
            return null;
        }
        return new a(retrofit.h(h.a.getParameterUpperBound(0, (ParameterizedType) type), annotations));
    }

    @IgnoreJRERequirement
    /* compiled from: OptionalConverterFactory */
    public static final class a<T> implements h<e0, Optional<T>> {
        final h<e0, T> a;

        a(h<e0, T> delegate) {
            this.a = delegate;
        }

        /* renamed from: a */
        public Optional<T> convert(e0 value) {
            return Optional.ofNullable(this.a.convert(value));
        }
    }
}
