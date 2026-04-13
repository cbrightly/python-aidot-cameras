package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import retrofit2.e;

@IgnoreJRERequirement
/* compiled from: CompletableFutureCallAdapterFactory */
public final class g extends e.a {
    static final e.a a = new g();

    g() {
    }

    @Nullable
    public e<?, ?> a(Type returnType, Annotation[] annotations, t retrofit) {
        if (e.a.c(returnType) != CompletableFuture.class) {
            return null;
        }
        if (returnType instanceof ParameterizedType) {
            Type innerType = e.a.b(0, (ParameterizedType) returnType);
            if (e.a.c(innerType) != s.class) {
                return new a(innerType);
            }
            if (innerType instanceof ParameterizedType) {
                return new c(e.a.b(0, (ParameterizedType) innerType));
            }
            throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>");
        }
        throw new IllegalStateException("CompletableFuture return type must be parameterized as CompletableFuture<Foo> or CompletableFuture<? extends Foo>");
    }

    @IgnoreJRERequirement
    /* compiled from: CompletableFutureCallAdapterFactory */
    public static final class a<R> implements e<R, CompletableFuture<R>> {
        private final Type a;

        a(Type responseType) {
            this.a = responseType;
        }

        public Type a() {
            return this.a;
        }

        /* renamed from: c */
        public CompletableFuture<R> b(d<R> call) {
            CompletableFuture<R> future = new b<>(call);
            call.T(new C0490a(future));
            return future;
        }

        @IgnoreJRERequirement
        /* renamed from: retrofit2.g$a$a  reason: collision with other inner class name */
        /* compiled from: CompletableFutureCallAdapterFactory */
        public class C0490a implements f<R> {
            private final CompletableFuture<R> c;

            public C0490a(CompletableFuture<R> future) {
                this.c = future;
            }

            public void b(d<R> dVar, s<R> response) {
                if (response.e()) {
                    this.c.complete(response.a());
                } else {
                    this.c.completeExceptionally(new HttpException(response));
                }
            }

            public void a(d<R> dVar, Throwable t) {
                this.c.completeExceptionally(t);
            }
        }
    }

    @IgnoreJRERequirement
    /* compiled from: CompletableFutureCallAdapterFactory */
    public static final class c<R> implements e<R, CompletableFuture<s<R>>> {
        private final Type a;

        c(Type responseType) {
            this.a = responseType;
        }

        public Type a() {
            return this.a;
        }

        /* renamed from: c */
        public CompletableFuture<s<R>> b(d<R> call) {
            CompletableFuture<Response<R>> future = new b<>(call);
            call.T(new a(future));
            return future;
        }

        @IgnoreJRERequirement
        /* compiled from: CompletableFutureCallAdapterFactory */
        public class a implements f<R> {
            private final CompletableFuture<s<R>> c;

            public a(CompletableFuture<s<R>> future) {
                this.c = future;
            }

            public void b(d<R> dVar, s<R> response) {
                this.c.complete(response);
            }

            public void a(d<R> dVar, Throwable t) {
                this.c.completeExceptionally(t);
            }
        }
    }

    @IgnoreJRERequirement
    /* compiled from: CompletableFutureCallAdapterFactory */
    public static final class b<T> extends CompletableFuture<T> {
        private final d<?> c;

        b(d<?> call) {
            this.c = call;
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            if (mayInterruptIfRunning) {
                this.c.cancel();
            }
            return super.cancel(mayInterruptIfRunning);
        }
    }
}
