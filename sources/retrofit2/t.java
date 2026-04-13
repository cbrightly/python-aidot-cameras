package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.c0;
import okhttp3.e;
import okhttp3.e0;
import okhttp3.v;
import okhttp3.z;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.c;
import retrofit2.e;
import retrofit2.h;

/* compiled from: Retrofit */
public final class t {
    private final Map<Method, u<?>> a = new ConcurrentHashMap();
    final e.a b;
    final v c;
    final List<h.a> d;
    final List<e.a> e;
    @Nullable
    final Executor f;
    final boolean g;

    t(e.a callFactory, v baseUrl, List<h.a> converterFactories, List<e.a> callAdapterFactories, @Nullable Executor callbackExecutor, boolean validateEagerly) {
        this.b = callFactory;
        this.c = baseUrl;
        this.d = converterFactories;
        this.e = callAdapterFactories;
        this.f = callbackExecutor;
        this.g = validateEagerly;
    }

    public <T> T b(Class<T> service) {
        j(service);
        return Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new a(service));
    }

    /* compiled from: Retrofit */
    public class a implements InvocationHandler {
        private final p c = p.f();
        private final Object[] d = new Object[0];
        final /* synthetic */ Class f;

        a(Class cls) {
            this.f = cls;
        }

        @Nullable
        public Object invoke(Object proxy, Method method, @Nullable Object[] args) {
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            }
            Object[] args2 = args != null ? args : this.d;
            if (this.c.h(method)) {
                return this.c.g(method, this.f, proxy, args2);
            }
            return t.this.c(method).a(args2);
        }
    }

    private void j(Class<?> service) {
        if (service.isInterface()) {
            Deque<Class<?>> check = new ArrayDeque<>(1);
            check.add(service);
            while (!check.isEmpty()) {
                Class<?> candidate = check.removeFirst();
                if (candidate.getTypeParameters().length != 0) {
                    StringBuilder message = new StringBuilder("Type parameters are unsupported on ").append(candidate.getName());
                    if (candidate != service) {
                        message.append(" which is an interface of ");
                        message.append(service.getName());
                    }
                    throw new IllegalArgumentException(message.toString());
                }
                Collections.addAll(check, candidate.getInterfaces());
            }
            if (this.g) {
                p platform = p.f();
                for (Method method : service.getDeclaredMethods()) {
                    if (!platform.h(method) && !Modifier.isStatic(method.getModifiers())) {
                        c(method);
                    }
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("API declarations must be interfaces.");
    }

    /* access modifiers changed from: package-private */
    public u<?> c(Method method) {
        ServiceMethod<?> result;
        ServiceMethod<?> result2 = (u) this.a.get(method);
        if (result2 != null) {
            return result2;
        }
        synchronized (this.a) {
            result = (u) this.a.get(method);
            if (result == null) {
                result = u.b(this, method);
                this.a.put(method, result);
            }
        }
        return result;
    }

    public e<?, ?> a(Type returnType, Annotation[] annotations) {
        return d((e.a) null, returnType, annotations);
    }

    public e<?, ?> d(@Nullable e.a skipPast, Type returnType, Annotation[] annotations) {
        Objects.requireNonNull(returnType, "returnType == null");
        Objects.requireNonNull(annotations, "annotations == null");
        int start = this.e.indexOf(skipPast) + 1;
        int count = this.e.size();
        for (int i = start; i < count; i++) {
            CallAdapter<?, ?> adapter = this.e.get(i).a(returnType, annotations, this);
            if (adapter != null) {
                return adapter;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate call adapter for ");
        sb.append(returnType);
        StringBuilder builder = sb.append(".\n");
        if (skipPast != null) {
            builder.append("  Skipped:");
            for (int i2 = 0; i2 < start; i2++) {
                builder.append("\n   * ");
                builder.append(this.e.get(i2).getClass().getName());
            }
            builder.append(10);
        }
        builder.append("  Tried:");
        int count2 = this.e.size();
        for (int i3 = start; i3 < count2; i3++) {
            builder.append("\n   * ");
            builder.append(this.e.get(i3).getClass().getName());
        }
        throw new IllegalArgumentException(builder.toString());
    }

    public <T> h<T, c0> g(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations) {
        return e((h.a) null, type, parameterAnnotations, methodAnnotations);
    }

    public <T> h<T, c0> e(@Nullable h.a skipPast, Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations) {
        Objects.requireNonNull(type, "type == null");
        Objects.requireNonNull(parameterAnnotations, "parameterAnnotations == null");
        Objects.requireNonNull(methodAnnotations, "methodAnnotations == null");
        int start = this.d.indexOf(skipPast) + 1;
        int count = this.d.size();
        for (int i = start; i < count; i++) {
            Converter<?, RequestBody> converter = this.d.get(i).requestBodyConverter(type, parameterAnnotations, methodAnnotations, this);
            if (converter != null) {
                return converter;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate RequestBody converter for ");
        sb.append(type);
        StringBuilder builder = sb.append(".\n");
        if (skipPast != null) {
            builder.append("  Skipped:");
            for (int i2 = 0; i2 < start; i2++) {
                builder.append("\n   * ");
                builder.append(this.d.get(i2).getClass().getName());
            }
            builder.append(10);
        }
        builder.append("  Tried:");
        int count2 = this.d.size();
        for (int i3 = start; i3 < count2; i3++) {
            builder.append("\n   * ");
            builder.append(this.d.get(i3).getClass().getName());
        }
        throw new IllegalArgumentException(builder.toString());
    }

    public <T> h<e0, T> h(Type type, Annotation[] annotations) {
        return f((h.a) null, type, annotations);
    }

    public <T> h<e0, T> f(@Nullable h.a skipPast, Type type, Annotation[] annotations) {
        Objects.requireNonNull(type, "type == null");
        Objects.requireNonNull(annotations, "annotations == null");
        int start = this.d.indexOf(skipPast) + 1;
        int count = this.d.size();
        for (int i = start; i < count; i++) {
            Converter<ResponseBody, ?> converter = this.d.get(i).responseBodyConverter(type, annotations, this);
            if (converter != null) {
                return converter;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate ResponseBody converter for ");
        sb.append(type);
        StringBuilder builder = sb.append(".\n");
        if (skipPast != null) {
            builder.append("  Skipped:");
            for (int i2 = 0; i2 < start; i2++) {
                builder.append("\n   * ");
                builder.append(this.d.get(i2).getClass().getName());
            }
            builder.append(10);
        }
        builder.append("  Tried:");
        int count2 = this.d.size();
        for (int i3 = start; i3 < count2; i3++) {
            builder.append("\n   * ");
            builder.append(this.d.get(i3).getClass().getName());
        }
        throw new IllegalArgumentException(builder.toString());
    }

    public <T> h<T, String> i(Type type, Annotation[] annotations) {
        Objects.requireNonNull(type, "type == null");
        Objects.requireNonNull(annotations, "annotations == null");
        int count = this.d.size();
        for (int i = 0; i < count; i++) {
            Converter<?, String> converter = this.d.get(i).stringConverter(type, annotations, this);
            if (converter != null) {
                return converter;
            }
        }
        return c.d.a;
    }

    /* compiled from: Retrofit */
    public static final class b {
        private final p a;
        @Nullable
        private e.a b;
        @Nullable
        private v c;
        private final List<h.a> d;
        private final List<e.a> e;
        @Nullable
        private Executor f;
        private boolean g;

        b(p platform) {
            this.d = new ArrayList();
            this.e = new ArrayList();
            this.a = platform;
        }

        public b() {
            this(p.f());
        }

        public b g(z client) {
            Objects.requireNonNull(client, "client == null");
            return f(client);
        }

        public b f(e.a factory) {
            Objects.requireNonNull(factory, "factory == null");
            this.b = factory;
            return this;
        }

        public b c(String baseUrl) {
            Objects.requireNonNull(baseUrl, "baseUrl == null");
            return d(v.i(baseUrl));
        }

        public b d(v baseUrl) {
            Objects.requireNonNull(baseUrl, "baseUrl == null");
            List<String> pathSegments = baseUrl.o();
            if ("".equals(pathSegments.get(pathSegments.size() - 1))) {
                this.c = baseUrl;
                return this;
            }
            throw new IllegalArgumentException("baseUrl must end in /: " + baseUrl);
        }

        public b b(h.a factory) {
            List<h.a> list = this.d;
            Objects.requireNonNull(factory, "factory == null");
            list.add(factory);
            return this;
        }

        public b a(e.a factory) {
            List<e.a> list = this.e;
            Objects.requireNonNull(factory, "factory == null");
            list.add(factory);
            return this;
        }

        public t e() {
            Executor callbackExecutor;
            if (this.c != null) {
                e.a callFactory = this.b;
                if (callFactory == null) {
                    callFactory = new z();
                }
                Executor callbackExecutor2 = this.f;
                if (callbackExecutor2 == null) {
                    callbackExecutor = this.a.b();
                } else {
                    callbackExecutor = callbackExecutor2;
                }
                List<CallAdapter.Factory> callAdapterFactories = new ArrayList<>(this.e);
                callAdapterFactories.addAll(this.a.a(callbackExecutor));
                List<Converter.Factory> converterFactories = new ArrayList<>(this.d.size() + 1 + this.a.d());
                converterFactories.add(new c());
                converterFactories.addAll(this.d);
                converterFactories.addAll(this.a.c());
                return new t(callFactory, this.c, Collections.unmodifiableList(converterFactories), Collections.unmodifiableList(callAdapterFactories), callbackExecutor, this.g);
            }
            throw new IllegalStateException("Base URL required.");
        }
    }
}
