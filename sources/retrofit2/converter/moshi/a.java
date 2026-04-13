package retrofit2.converter.moshi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.r;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import okhttp3.c0;
import okhttp3.e0;
import retrofit2.h;
import retrofit2.t;

/* compiled from: MoshiConverterFactory */
public final class a extends h.a {
    private final r a;
    private final boolean b;
    private final boolean c;
    private final boolean d;

    public static a a(r moshi) {
        if (moshi != null) {
            return new a(moshi, false, false, false);
        }
        throw new NullPointerException("moshi == null");
    }

    private a(r moshi, boolean lenient, boolean failOnUnknown, boolean serializeNulls) {
        this.a = moshi;
        this.b = lenient;
        this.c = failOnUnknown;
        this.d = serializeNulls;
    }

    public h<e0, ?> responseBodyConverter(Type type, Annotation[] annotations, t retrofit) {
        JsonAdapter<?> adapter = this.a.e(type, b(annotations));
        if (this.b) {
            adapter = adapter.e();
        }
        if (this.c) {
            adapter = adapter.a();
        }
        if (this.d) {
            adapter = adapter.g();
        }
        return new c(adapter);
    }

    public h<?, c0> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, t retrofit) {
        JsonAdapter<?> adapter = this.a.e(type, b(parameterAnnotations));
        if (this.b) {
            adapter = adapter.e();
        }
        if (this.c) {
            adapter = adapter.a();
        }
        if (this.d) {
            adapter = adapter.g();
        }
        return new b(adapter);
    }

    private static Set<? extends Annotation> b(Annotation[] annotations) {
        Set<Annotation> result = null;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(com.squareup.moshi.h.class)) {
                if (result == null) {
                    result = new LinkedHashSet<>();
                }
                result.add(annotation);
            }
        }
        return result != null ? Collections.unmodifiableSet(result) : Collections.emptySet();
    }
}
