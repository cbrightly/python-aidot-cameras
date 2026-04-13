package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.d;
import okhttp3.d0;
import okhttp3.e;
import okhttp3.e0;
import org.glassfish.grizzly.http.server.Constants;
import retrofit2.x;

/* compiled from: HttpServiceMethod */
public abstract class j<ResponseT, ReturnT> extends u<ReturnT> {
    private final r a;
    private final e.a b;
    private final h<e0, ResponseT> c;

    /* access modifiers changed from: protected */
    @Nullable
    public abstract ReturnT c(d<ResponseT> dVar, Object[] objArr);

    static <ResponseT, ReturnT> j<ResponseT, ReturnT> f(t retrofit, Method method, r requestFactory) {
        Type adapterType;
        Annotation[] annotations;
        boolean continuationWantsResponse;
        t tVar = retrofit;
        Method method2 = method;
        r rVar = requestFactory;
        Class<s> cls = s.class;
        boolean isKotlinSuspendFunction = rVar.k;
        boolean continuationWantsResponse2 = false;
        Annotation[] annotations2 = method.getAnnotations();
        if (isKotlinSuspendFunction) {
            Type[] parameterTypes = method.getGenericParameterTypes();
            Type responseType = x.f(0, (ParameterizedType) parameterTypes[parameterTypes.length - 1]);
            if (x.h(responseType) == cls && (responseType instanceof ParameterizedType)) {
                responseType = x.g(0, (ParameterizedType) responseType);
                continuationWantsResponse2 = true;
            }
            Type adapterType2 = new x.b((Type) null, d.class, responseType);
            annotations = w.a(annotations2);
            adapterType = adapterType2;
            continuationWantsResponse = continuationWantsResponse2;
        } else {
            annotations = annotations2;
            adapterType = method.getGenericReturnType();
            continuationWantsResponse = false;
        }
        CallAdapter<ResponseT, ReturnT> callAdapter = d(tVar, method2, adapterType, annotations);
        Type responseType2 = callAdapter.a();
        if (responseType2 == d0.class) {
            throw x.m(method2, "'" + x.h(responseType2).getName() + "' is not a valid response body type. Did you mean ResponseBody?", new Object[0]);
        } else if (responseType2 == cls) {
            throw x.m(method2, "Response must include generic type (e.g., Response<String>)", new Object[0]);
        } else if (!rVar.c.equals(Constants.HEAD) || Void.class.equals(responseType2)) {
            h e = e(tVar, method2, responseType2);
            e.a callFactory = tVar.b;
            if (!isKotlinSuspendFunction) {
                return new a(rVar, callFactory, e, callAdapter);
            }
            if (continuationWantsResponse) {
                return new c(rVar, callFactory, e, callAdapter);
            }
            e.a aVar = callFactory;
            h hVar = e;
            return new b(requestFactory, callFactory, e, callAdapter, false);
        } else {
            throw x.m(method2, "HEAD method must use Void as response type.", new Object[0]);
        }
    }

    private static <ResponseT, ReturnT> e<ResponseT, ReturnT> d(t retrofit, Method method, Type returnType, Annotation[] annotations) {
        try {
            return retrofit.a(returnType, annotations);
        } catch (RuntimeException e) {
            throw x.n(method, e, "Unable to create call adapter for %s", returnType);
        }
    }

    private static <ResponseT> h<e0, ResponseT> e(t retrofit, Method method, Type responseType) {
        try {
            return retrofit.h(responseType, method.getAnnotations());
        } catch (RuntimeException e) {
            throw x.n(method, e, "Unable to create converter for %s", responseType);
        }
    }

    j(r requestFactory, e.a callFactory, h<e0, ResponseT> responseConverter) {
        this.a = requestFactory;
        this.b = callFactory;
        this.c = responseConverter;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final ReturnT a(Object[] args) {
        return c(new m<>(this.a, args, this.b, this.c), args);
    }

    /* compiled from: HttpServiceMethod */
    public static final class a<ResponseT, ReturnT> extends j<ResponseT, ReturnT> {
        private final e<ResponseT, ReturnT> d;

        a(r requestFactory, e.a callFactory, h<e0, ResponseT> responseConverter, e<ResponseT, ReturnT> callAdapter) {
            super(requestFactory, callFactory, responseConverter);
            this.d = callAdapter;
        }

        /* JADX WARNING: type inference failed for: r2v0, types: [retrofit2.d, retrofit2.d<ResponseT>] */
        /* access modifiers changed from: protected */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public ReturnT c(retrofit2.d<ResponseT> r2, java.lang.Object[] r3) {
            /*
                r1 = this;
                retrofit2.e<ResponseT, ReturnT> r0 = r1.d
                java.lang.Object r0 = r0.b(r2)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: retrofit2.j.a.c(retrofit2.d, java.lang.Object[]):java.lang.Object");
        }
    }

    /* compiled from: HttpServiceMethod */
    public static final class c<ResponseT> extends j<ResponseT, Object> {
        private final e<ResponseT, d<ResponseT>> d;

        c(r requestFactory, e.a callFactory, h<e0, ResponseT> responseConverter, e<ResponseT, d<ResponseT>> callAdapter) {
            super(requestFactory, callFactory, responseConverter);
            this.d = callAdapter;
        }

        /* access modifiers changed from: protected */
        public Object c(d<ResponseT> call, Object[] args) {
            Call<ResponseT> call2 = (d) this.d.b(call);
            Continuation<Response<ResponseT>> continuation = (d) args[args.length - 1];
            try {
                return l.c(call2, continuation);
            } catch (Exception e) {
                return l.d(e, continuation);
            }
        }
    }

    /* compiled from: HttpServiceMethod */
    public static final class b<ResponseT> extends j<ResponseT, Object> {
        private final e<ResponseT, d<ResponseT>> d;
        private final boolean e;

        b(r requestFactory, e.a callFactory, h<e0, ResponseT> responseConverter, e<ResponseT, d<ResponseT>> callAdapter, boolean isNullable) {
            super(requestFactory, callFactory, responseConverter);
            this.d = callAdapter;
            this.e = isNullable;
        }

        /* access modifiers changed from: protected */
        public Object c(d<ResponseT> call, Object[] args) {
            Call<ResponseT> call2 = (d) this.d.b(call);
            Continuation<ResponseT> continuation = (d) args[args.length - 1];
            try {
                if (this.e) {
                    return l.b(call2, continuation);
                }
                return l.a(call2, continuation);
            } catch (Exception e2) {
                return l.d(e2, continuation);
            }
        }
    }
}
