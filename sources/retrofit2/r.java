package retrofit2;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import kotlin.coroutines.d;
import okhttp3.RequestBody;
import okhttp3.b0;
import okhttp3.u;
import okhttp3.v;
import okhttp3.x;
import okhttp3.y;
import org.glassfish.grizzly.http.server.Constants;
import retrofit2.http.b;
import retrofit2.http.c;
import retrofit2.http.e;
import retrofit2.http.f;
import retrofit2.http.g;
import retrofit2.http.h;
import retrofit2.http.i;
import retrofit2.http.j;
import retrofit2.http.k;
import retrofit2.http.l;
import retrofit2.http.m;
import retrofit2.http.n;
import retrofit2.http.o;
import retrofit2.http.p;
import retrofit2.http.q;
import retrofit2.http.s;
import retrofit2.http.t;
import retrofit2.o;

/* compiled from: RequestFactory */
public final class r {
    private final Method a;
    private final v b;
    final String c;
    @Nullable
    private final String d;
    @Nullable
    private final u e;
    @Nullable
    private final x f;
    private final boolean g;
    private final boolean h;
    private final boolean i;
    private final o<?>[] j;
    final boolean k;

    static r b(t retrofit, Method method) {
        return new a(retrofit, method).b();
    }

    r(a builder) {
        this.a = builder.d;
        this.b = builder.c.c;
        this.c = builder.p;
        this.d = builder.t;
        this.e = builder.u;
        this.f = builder.v;
        this.g = builder.q;
        this.h = builder.r;
        this.i = builder.s;
        this.j = builder.x;
        this.k = builder.y;
    }

    /* access modifiers changed from: package-private */
    public b0 a(Object[] args) {
        ParameterHandler<Object>[] handlers = this.j;
        int argumentCount = args.length;
        if (argumentCount == handlers.length) {
            q qVar = new q(this.c, this.b, this.d, this.e, this.f, this.g, this.h, this.i);
            if (this.k) {
                argumentCount--;
            }
            List<Object> argumentList = new ArrayList<>(argumentCount);
            for (int p = 0; p < argumentCount; p++) {
                argumentList.add(args[p]);
                handlers[p].a(qVar, args[p]);
            }
            return qVar.k().n(k.class, new k(this.a, argumentList)).b();
        }
        throw new IllegalArgumentException("Argument count (" + argumentCount + ") doesn't match expected count (" + handlers.length + ")");
    }

    /* compiled from: RequestFactory */
    public static final class a {
        private static final Pattern a = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9_-]*)\\}");
        private static final Pattern b = Pattern.compile("[a-zA-Z][a-zA-Z0-9_-]*");
        final t c;
        final Method d;
        final Annotation[] e;
        final Annotation[][] f;
        final Type[] g;
        boolean h;
        boolean i;
        boolean j;
        boolean k;
        boolean l;
        boolean m;
        boolean n;
        boolean o;
        @Nullable
        String p;
        boolean q;
        boolean r;
        boolean s;
        @Nullable
        String t;
        @Nullable
        u u;
        @Nullable
        x v;
        @Nullable
        Set<String> w;
        @Nullable
        o<?>[] x;
        boolean y;

        a(t retrofit, Method method) {
            this.c = retrofit;
            this.d = method;
            this.e = method.getAnnotations();
            this.g = method.getGenericParameterTypes();
            this.f = method.getParameterAnnotations();
        }

        /* access modifiers changed from: package-private */
        public r b() {
            for (Annotation annotation : this.e) {
                e(annotation);
            }
            if (this.p != null) {
                if (!this.q) {
                    if (this.s) {
                        throw x.m(this.d, "Multipart can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                    } else if (this.r) {
                        throw x.m(this.d, "FormUrlEncoded can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                    }
                }
                int parameterCount = this.f.length;
                this.x = new o[parameterCount];
                int p2 = 0;
                int lastParameter = parameterCount - 1;
                while (true) {
                    boolean z = true;
                    if (p2 >= parameterCount) {
                        break;
                    }
                    o<?>[] oVarArr = this.x;
                    Type type = this.g[p2];
                    Annotation[] annotationArr = this.f[p2];
                    if (p2 != lastParameter) {
                        z = false;
                    }
                    oVarArr[p2] = f(p2, type, annotationArr, z);
                    p2++;
                }
                if (this.t != null || this.o) {
                    boolean z2 = this.r;
                    if (!z2 && !this.s && !this.q && this.j) {
                        throw x.m(this.d, "Non-body HTTP method cannot contain @Body.", new Object[0]);
                    } else if (z2 && !this.h) {
                        throw x.m(this.d, "Form-encoded method must contain at least one @Field.", new Object[0]);
                    } else if (!this.s || this.i) {
                        return new r(this);
                    } else {
                        throw x.m(this.d, "Multipart method must contain at least one @Part.", new Object[0]);
                    }
                } else {
                    throw x.m(this.d, "Missing either @%s URL or @Url parameter.", this.p);
                }
            } else {
                throw x.m(this.d, "HTTP method annotation is required (e.g., @GET, @POST, etc.).", new Object[0]);
            }
        }

        private void e(Annotation annotation) {
            if (annotation instanceof b) {
                d("DELETE", ((b) annotation).value(), false);
            } else if (annotation instanceof f) {
                d(Constants.GET, ((f) annotation).value(), false);
            } else if (annotation instanceof g) {
                d(Constants.HEAD, ((g) annotation).value(), false);
            } else if (annotation instanceof n) {
                d("PATCH", ((n) annotation).value(), true);
            } else if (annotation instanceof o) {
                d(Constants.POST, ((o) annotation).value(), true);
            } else if (annotation instanceof p) {
                d("PUT", ((p) annotation).value(), true);
            } else if (annotation instanceof m) {
                d("OPTIONS", ((m) annotation).value(), false);
            } else if (annotation instanceof h) {
                h http = (h) annotation;
                d(http.method(), http.path(), http.hasBody());
            } else if (annotation instanceof k) {
                String[] headersToParse = ((k) annotation).value();
                if (headersToParse.length != 0) {
                    this.u = c(headersToParse);
                    return;
                }
                throw x.m(this.d, "@Headers annotation is empty.", new Object[0]);
            } else if (annotation instanceof l) {
                if (!this.r) {
                    this.s = true;
                    return;
                }
                throw x.m(this.d, "Only one encoding annotation is allowed.", new Object[0]);
            } else if (!(annotation instanceof e)) {
            } else {
                if (!this.s) {
                    this.r = true;
                    return;
                }
                throw x.m(this.d, "Only one encoding annotation is allowed.", new Object[0]);
            }
        }

        private void d(String httpMethod, String value, boolean hasBody) {
            String str = this.p;
            if (str == null) {
                this.p = httpMethod;
                this.q = hasBody;
                if (!value.isEmpty()) {
                    int question = value.indexOf(63);
                    if (question != -1 && question < value.length() - 1) {
                        String queryParams = value.substring(question + 1);
                        if (a.matcher(queryParams).find()) {
                            throw x.m(this.d, "URL query string \"%s\" must not have replace block. For dynamic query parameters use @Query.", queryParams);
                        }
                    }
                    this.t = value;
                    this.w = h(value);
                    return;
                }
                return;
            }
            throw x.m(this.d, "Only one HTTP method is allowed. Found: %s and %s.", str, httpMethod);
        }

        private u c(String[] headers) {
            u.a builder = new u.a();
            for (String header : headers) {
                int colon = header.indexOf(58);
                if (colon == -1 || colon == 0 || colon == header.length() - 1) {
                    throw x.m(this.d, "@Headers value must be in the form \"Name: Value\". Found: \"%s\"", header);
                }
                String headerName = header.substring(0, colon);
                String headerValue = header.substring(colon + 1).trim();
                if ("Content-Type".equalsIgnoreCase(headerName)) {
                    try {
                        this.v = x.f(headerValue);
                    } catch (IllegalArgumentException e2) {
                        throw x.n(this.d, e2, "Malformed content type: %s", headerValue);
                    }
                } else {
                    builder.a(headerName, headerValue);
                }
            }
            return builder.f();
        }

        @Nullable
        private o<?> f(int p2, Type parameterType, @Nullable Annotation[] annotations, boolean allowContinuation) {
            ParameterHandler<?> result = null;
            if (annotations != null) {
                for (Annotation annotation : annotations) {
                    ParameterHandler<?> annotationAction = g(p2, parameterType, annotations, annotation);
                    if (annotationAction != null) {
                        if (result == null) {
                            result = annotationAction;
                        } else {
                            throw x.o(this.d, p2, "Multiple Retrofit annotations found, only one allowed.", new Object[0]);
                        }
                    }
                }
            }
            if (result != null) {
                return result;
            }
            if (allowContinuation) {
                try {
                    if (x.h(parameterType) == d.class) {
                        this.y = true;
                        return null;
                    }
                } catch (NoClassDefFoundError e2) {
                }
            }
            throw x.o(this.d, p2, "No Retrofit annotation found.", new Object[0]);
        }

        @Nullable
        private o<?> g(int p2, Type type, Annotation[] annotations, Annotation annotation) {
            Class<String> cls = String.class;
            Class<y.c> cls2 = y.c.class;
            if (annotation instanceof retrofit2.http.y) {
                j(p2, type);
                if (this.o) {
                    throw x.o(this.d, p2, "Multiple @Url method annotations found.", new Object[0]);
                } else if (this.k) {
                    throw x.o(this.d, p2, "@Path parameters may not be used with @Url.", new Object[0]);
                } else if (this.l) {
                    throw x.o(this.d, p2, "A @Url parameter must not come after a @Query.", new Object[0]);
                } else if (this.m) {
                    throw x.o(this.d, p2, "A @Url parameter must not come after a @QueryName.", new Object[0]);
                } else if (this.n) {
                    throw x.o(this.d, p2, "A @Url parameter must not come after a @QueryMap.", new Object[0]);
                } else if (this.t == null) {
                    this.o = true;
                    if (type == v.class || type == cls || type == URI.class || ((type instanceof Class) && "android.net.Uri".equals(((Class) type).getName()))) {
                        return new o.p(this.d, p2);
                    }
                    throw x.o(this.d, p2, "@Url must be okhttp3.HttpUrl, String, java.net.URI, or android.net.Uri type.", new Object[0]);
                } else {
                    throw x.o(this.d, p2, "@Url cannot be used with @%s URL", this.p);
                }
            } else if (annotation instanceof s) {
                j(p2, type);
                if (this.l) {
                    throw x.o(this.d, p2, "A @Path parameter must not come after a @Query.", new Object[0]);
                } else if (this.m) {
                    throw x.o(this.d, p2, "A @Path parameter must not come after a @QueryName.", new Object[0]);
                } else if (this.n) {
                    throw x.o(this.d, p2, "A @Path parameter must not come after a @QueryMap.", new Object[0]);
                } else if (this.o) {
                    throw x.o(this.d, p2, "@Path parameters may not be used with @Url.", new Object[0]);
                } else if (this.t != null) {
                    this.k = true;
                    s path = (s) annotation;
                    String name = path.value();
                    i(p2, name);
                    return new o.k(this.d, p2, name, this.c.i(type, annotations), path.encoded());
                } else {
                    throw x.o(this.d, p2, "@Path can only be used with relative url on @%s", this.p);
                }
            } else if (annotation instanceof t) {
                j(p2, type);
                t query = (t) annotation;
                String name2 = query.value();
                boolean encoded = query.encoded();
                Class<?> rawParameterType = x.h(type);
                this.l = true;
                if (Iterable.class.isAssignableFrom(rawParameterType)) {
                    if (type instanceof ParameterizedType) {
                        return new o.l(name2, this.c.i(x.g(0, (ParameterizedType) type), annotations), encoded).c();
                    }
                    throw x.o(this.d, p2, rawParameterType.getSimpleName() + " must include generic type (e.g., " + rawParameterType.getSimpleName() + "<String>)", new Object[0]);
                } else if (!rawParameterType.isArray()) {
                    return new o.l(name2, this.c.i(type, annotations), encoded);
                } else {
                    return new o.l(name2, this.c.i(a(rawParameterType.getComponentType()), annotations), encoded).b();
                }
            } else if (annotation instanceof retrofit2.http.v) {
                j(p2, type);
                boolean encoded2 = ((retrofit2.http.v) annotation).encoded();
                Class<?> rawParameterType2 = x.h(type);
                this.m = true;
                if (Iterable.class.isAssignableFrom(rawParameterType2)) {
                    if (type instanceof ParameterizedType) {
                        return new o.n(this.c.i(x.g(0, (ParameterizedType) type), annotations), encoded2).c();
                    }
                    throw x.o(this.d, p2, rawParameterType2.getSimpleName() + " must include generic type (e.g., " + rawParameterType2.getSimpleName() + "<String>)", new Object[0]);
                } else if (!rawParameterType2.isArray()) {
                    return new o.n(this.c.i(type, annotations), encoded2);
                } else {
                    return new o.n(this.c.i(a(rawParameterType2.getComponentType()), annotations), encoded2).b();
                }
            } else if (annotation instanceof retrofit2.http.u) {
                j(p2, type);
                Class<?> rawParameterType3 = x.h(type);
                this.n = true;
                if (Map.class.isAssignableFrom(rawParameterType3)) {
                    Type mapType = x.i(type, rawParameterType3, Map.class);
                    if (mapType instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) mapType;
                        Type keyType = x.g(0, parameterizedType);
                        if (cls == keyType) {
                            return new o.m(this.d, p2, this.c.i(x.g(1, parameterizedType), annotations), ((retrofit2.http.u) annotation).encoded());
                        }
                        throw x.o(this.d, p2, "@QueryMap keys must be of type String: " + keyType, new Object[0]);
                    }
                    throw x.o(this.d, p2, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                throw x.o(this.d, p2, "@QueryMap parameter type must be Map.", new Object[0]);
            } else if (annotation instanceof i) {
                j(p2, type);
                String name3 = ((i) annotation).value();
                Class<?> rawParameterType4 = x.h(type);
                if (Iterable.class.isAssignableFrom(rawParameterType4)) {
                    if (type instanceof ParameterizedType) {
                        return new o.f(name3, this.c.i(x.g(0, (ParameterizedType) type), annotations)).c();
                    }
                    throw x.o(this.d, p2, rawParameterType4.getSimpleName() + " must include generic type (e.g., " + rawParameterType4.getSimpleName() + "<String>)", new Object[0]);
                } else if (!rawParameterType4.isArray()) {
                    return new o.f(name3, this.c.i(type, annotations));
                } else {
                    return new o.f(name3, this.c.i(a(rawParameterType4.getComponentType()), annotations)).b();
                }
            } else if (annotation instanceof j) {
                if (type == u.class) {
                    return new o.h(this.d, p2);
                }
                j(p2, type);
                Class<?> rawParameterType5 = x.h(type);
                if (Map.class.isAssignableFrom(rawParameterType5)) {
                    Type mapType2 = x.i(type, rawParameterType5, Map.class);
                    if (mapType2 instanceof ParameterizedType) {
                        ParameterizedType parameterizedType2 = (ParameterizedType) mapType2;
                        Type keyType2 = x.g(0, parameterizedType2);
                        if (cls == keyType2) {
                            return new o.g(this.d, p2, this.c.i(x.g(1, parameterizedType2), annotations));
                        }
                        throw x.o(this.d, p2, "@HeaderMap keys must be of type String: " + keyType2, new Object[0]);
                    }
                    throw x.o(this.d, p2, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                throw x.o(this.d, p2, "@HeaderMap parameter type must be Map.", new Object[0]);
            } else if (annotation instanceof c) {
                j(p2, type);
                if (this.r) {
                    c field = (c) annotation;
                    String name4 = field.value();
                    boolean encoded3 = field.encoded();
                    this.h = true;
                    Class<?> rawParameterType6 = x.h(type);
                    if (Iterable.class.isAssignableFrom(rawParameterType6)) {
                        if (type instanceof ParameterizedType) {
                            return new o.d(name4, this.c.i(x.g(0, (ParameterizedType) type), annotations), encoded3).c();
                        }
                        throw x.o(this.d, p2, rawParameterType6.getSimpleName() + " must include generic type (e.g., " + rawParameterType6.getSimpleName() + "<String>)", new Object[0]);
                    } else if (!rawParameterType6.isArray()) {
                        return new o.d(name4, this.c.i(type, annotations), encoded3);
                    } else {
                        return new o.d(name4, this.c.i(a(rawParameterType6.getComponentType()), annotations), encoded3).b();
                    }
                } else {
                    throw x.o(this.d, p2, "@Field parameters can only be used with form encoding.", new Object[0]);
                }
            } else if (annotation instanceof retrofit2.http.d) {
                j(p2, type);
                if (this.r) {
                    Class<?> rawParameterType7 = x.h(type);
                    if (Map.class.isAssignableFrom(rawParameterType7)) {
                        Type mapType3 = x.i(type, rawParameterType7, Map.class);
                        if (mapType3 instanceof ParameterizedType) {
                            ParameterizedType parameterizedType3 = (ParameterizedType) mapType3;
                            Type keyType3 = x.g(0, parameterizedType3);
                            if (cls == keyType3) {
                                Converter<?, String> valueConverter = this.c.i(x.g(1, parameterizedType3), annotations);
                                this.h = true;
                                return new o.e(this.d, p2, valueConverter, ((retrofit2.http.d) annotation).encoded());
                            }
                            throw x.o(this.d, p2, "@FieldMap keys must be of type String: " + keyType3, new Object[0]);
                        }
                        throw x.o(this.d, p2, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                    }
                    throw x.o(this.d, p2, "@FieldMap parameter type must be Map.", new Object[0]);
                }
                throw x.o(this.d, p2, "@FieldMap parameters can only be used with form encoding.", new Object[0]);
            } else if (annotation instanceof q) {
                j(p2, type);
                if (this.s) {
                    q part = (q) annotation;
                    this.i = true;
                    String partName = part.value();
                    Class<?> rawParameterType8 = x.h(type);
                    if (!partName.isEmpty()) {
                        u headers = u.g(HttpHeaders.HEAD_KEY_CONTENT_DISPOSITION, "form-data; name=\"" + partName + "\"", "Content-Transfer-Encoding", part.encoding());
                        if (Iterable.class.isAssignableFrom(rawParameterType8)) {
                            if (type instanceof ParameterizedType) {
                                Type iterableType = x.g(0, (ParameterizedType) type);
                                if (!cls2.isAssignableFrom(x.h(iterableType))) {
                                    return new o.i(this.d, p2, headers, this.c.g(iterableType, annotations, this.e)).c();
                                }
                                throw x.o(this.d, p2, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                            }
                            throw x.o(this.d, p2, rawParameterType8.getSimpleName() + " must include generic type (e.g., " + rawParameterType8.getSimpleName() + "<String>)", new Object[0]);
                        } else if (rawParameterType8.isArray()) {
                            Class<?> arrayComponentType = a(rawParameterType8.getComponentType());
                            if (!cls2.isAssignableFrom(arrayComponentType)) {
                                return new o.i(this.d, p2, headers, this.c.g(arrayComponentType, annotations, this.e)).b();
                            }
                            throw x.o(this.d, p2, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                        } else if (!cls2.isAssignableFrom(rawParameterType8)) {
                            return new o.i(this.d, p2, headers, this.c.g(type, annotations, this.e));
                        } else {
                            throw x.o(this.d, p2, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                        }
                    } else if (Iterable.class.isAssignableFrom(rawParameterType8)) {
                        if (!(type instanceof ParameterizedType)) {
                            throw x.o(this.d, p2, rawParameterType8.getSimpleName() + " must include generic type (e.g., " + rawParameterType8.getSimpleName() + "<String>)", new Object[0]);
                        } else if (cls2.isAssignableFrom(x.h(x.g(0, (ParameterizedType) type)))) {
                            return o.C0491o.a.c();
                        } else {
                            throw x.o(this.d, p2, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                        }
                    } else if (rawParameterType8.isArray()) {
                        if (cls2.isAssignableFrom(rawParameterType8.getComponentType())) {
                            return o.C0491o.a.b();
                        }
                        throw x.o(this.d, p2, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                    } else if (cls2.isAssignableFrom(rawParameterType8)) {
                        return o.C0491o.a;
                    } else {
                        throw x.o(this.d, p2, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                    }
                } else {
                    throw x.o(this.d, p2, "@Part parameters can only be used with multipart encoding.", new Object[0]);
                }
            } else if (annotation instanceof retrofit2.http.r) {
                j(p2, type);
                if (this.s) {
                    this.i = true;
                    Class<?> rawParameterType9 = x.h(type);
                    if (Map.class.isAssignableFrom(rawParameterType9)) {
                        Type mapType4 = x.i(type, rawParameterType9, Map.class);
                        if (mapType4 instanceof ParameterizedType) {
                            ParameterizedType parameterizedType4 = (ParameterizedType) mapType4;
                            Type keyType4 = x.g(0, parameterizedType4);
                            if (cls == keyType4) {
                                Type valueType = x.g(1, parameterizedType4);
                                if (!cls2.isAssignableFrom(x.h(valueType))) {
                                    return new o.j(this.d, p2, this.c.g(valueType, annotations, this.e), ((retrofit2.http.r) annotation).encoding());
                                }
                                throw x.o(this.d, p2, "@PartMap values cannot be MultipartBody.Part. Use @Part List<Part> or a different value type instead.", new Object[0]);
                            }
                            throw x.o(this.d, p2, "@PartMap keys must be of type String: " + keyType4, new Object[0]);
                        }
                        throw x.o(this.d, p2, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                    }
                    throw x.o(this.d, p2, "@PartMap parameter type must be Map.", new Object[0]);
                }
                throw x.o(this.d, p2, "@PartMap parameters can only be used with multipart encoding.", new Object[0]);
            } else if (annotation instanceof retrofit2.http.a) {
                j(p2, type);
                if (this.r || this.s) {
                    throw x.o(this.d, p2, "@Body parameters cannot be used with form or multi-part encoding.", new Object[0]);
                } else if (!this.j) {
                    try {
                        Converter<?, RequestBody> converter = this.c.g(type, annotations, this.e);
                        this.j = true;
                        return new o.c(this.d, p2, converter);
                    } catch (RuntimeException e2) {
                        throw x.p(this.d, e2, p2, "Unable to create @Body converter for %s", type);
                    }
                } else {
                    throw x.o(this.d, p2, "Multiple @Body method annotations found.", new Object[0]);
                }
            } else if (!(annotation instanceof retrofit2.http.x)) {
                return null;
            } else {
                j(p2, type);
                Class<?> tagType = x.h(type);
                int i2 = p2 - 1;
                while (i2 >= 0) {
                    o<?> oVar = this.x[i2];
                    if (!(oVar instanceof o.q) || !((o.q) oVar).a.equals(tagType)) {
                        i2--;
                    } else {
                        throw x.o(this.d, p2, "@Tag type " + tagType.getName() + " is duplicate of parameter #" + (i2 + 1) + " and would always overwrite its value.", new Object[0]);
                    }
                }
                return new o.q(tagType);
            }
        }

        private void j(int p2, Type type) {
            if (x.j(type)) {
                throw x.o(this.d, p2, "Parameter type must not include a type variable or wildcard: %s", type);
            }
        }

        private void i(int p2, String name) {
            if (!b.matcher(name).matches()) {
                throw x.o(this.d, p2, "@Path parameter name must match %s. Found: %s", a.pattern(), name);
            } else if (!this.w.contains(name)) {
                throw x.o(this.d, p2, "URL \"%s\" does not contain \"{%s}\".", this.t, name);
            }
        }

        static Set<String> h(String path) {
            Matcher m2 = a.matcher(path);
            Set<String> patterns = new LinkedHashSet<>();
            while (m2.find()) {
                patterns.add(m2.group(1));
            }
            return patterns;
        }

        private static Class<?> a(Class<?> type) {
            if (Boolean.TYPE == type) {
                return Boolean.class;
            }
            if (Byte.TYPE == type) {
                return Byte.class;
            }
            if (Character.TYPE == type) {
                return Character.class;
            }
            if (Double.TYPE == type) {
                return Double.class;
            }
            if (Float.TYPE == type) {
                return Float.class;
            }
            if (Integer.TYPE == type) {
                return Integer.class;
            }
            if (Long.TYPE == type) {
                return Long.class;
            }
            if (Short.TYPE == type) {
                return Short.class;
            }
            return type;
        }
    }
}
