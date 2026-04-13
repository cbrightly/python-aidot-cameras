package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import okhttp3.b0;
import retrofit2.e;

/* compiled from: DefaultCallAdapterFactory */
public final class i extends e.a {
    @Nullable
    private final Executor a;

    i(@Nullable Executor callbackExecutor) {
        this.a = callbackExecutor;
    }

    @Nullable
    public e<?, ?> a(Type returnType, Annotation[] annotations, t retrofit) {
        Executor executor = null;
        if (e.a.c(returnType) != d.class) {
            return null;
        }
        if (returnType instanceof ParameterizedType) {
            Type responseType = x.g(0, (ParameterizedType) returnType);
            if (!x.l(annotations, v.class)) {
                executor = this.a;
            }
            return new a(responseType, executor);
        }
        throw new IllegalArgumentException("Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
    }

    /* compiled from: DefaultCallAdapterFactory */
    public class a implements e<Object, d<?>> {
        final /* synthetic */ Type a;
        final /* synthetic */ Executor b;

        a(Type type, Executor executor) {
            this.a = type;
            this.b = executor;
        }

        public Type a() {
            return this.a;
        }

        /* renamed from: c */
        public d<Object> b(d<Object> call) {
            Executor executor = this.b;
            return executor == null ? call : new b(executor, call);
        }
    }

    /* compiled from: DefaultCallAdapterFactory */
    public static final class b<T> implements d<T> {
        final Executor c;
        final d<T> d;

        b(Executor callbackExecutor, d<T> delegate) {
            this.c = callbackExecutor;
            this.d = delegate;
        }

        public void T(f<T> callback) {
            Objects.requireNonNull(callback, "callback == null");
            this.d.T(new a(callback));
        }

        /* compiled from: DefaultCallAdapterFactory */
        public class a implements f<T> {
            final /* synthetic */ f c;

            a(f fVar) {
                this.c = fVar;
            }

            public void b(d<T> dVar, s<T> response) {
                b.this.c.execute(new b(this, this.c, response));
            }

            /* access modifiers changed from: private */
            /* renamed from: e */
            public /* synthetic */ void f(f callback, s response) {
                if (b.this.d.isCanceled()) {
                    callback.a(b.this, new IOException("Canceled"));
                } else {
                    callback.b(b.this, response);
                }
            }

            /* access modifiers changed from: private */
            /* renamed from: c */
            public /* synthetic */ void d(f callback, Throwable t) {
                callback.a(b.this, t);
            }

            public void a(d<T> dVar, Throwable t) {
                b.this.c.execute(new a(this, this.c, t));
            }
        }

        public s<T> execute() {
            return this.d.execute();
        }

        public void cancel() {
            this.d.cancel();
        }

        public boolean isCanceled() {
            return this.d.isCanceled();
        }

        public d<T> clone() {
            return new b(this.c, this.d.clone());
        }

        public b0 g() {
            return this.d.g();
        }
    }
}
