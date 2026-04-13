package retrofit2;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

/* compiled from: ServiceMethod */
public abstract class u<T> {
    /* access modifiers changed from: package-private */
    @Nullable
    public abstract T a(Object[] objArr);

    u() {
    }

    static <T> u<T> b(t retrofit, Method method) {
        r requestFactory = r.b(retrofit, method);
        Type returnType = method.getGenericReturnType();
        if (x.j(returnType)) {
            throw x.m(method, "Method return type must not include a type variable or wildcard: %s", returnType);
        } else if (returnType != Void.TYPE) {
            return j.f(retrofit, method, requestFactory);
        } else {
            throw x.m(method, "Service methods cannot return void.", new Object[0]);
        }
    }
}
