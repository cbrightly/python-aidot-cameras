package retrofit2.adapter.rxjava2;

import io.reactivex.Observable;
import io.reactivex.a;
import io.reactivex.r;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import retrofit2.Response;
import retrofit2.d;
import retrofit2.e;

/* compiled from: RxJava2CallAdapter */
public final class f<R> implements e<R, Object> {
    private final Type a;
    @Nullable
    private final r b;
    private final boolean c;
    private final boolean d;
    private final boolean e;
    private final boolean f;
    private final boolean g;
    private final boolean h;
    private final boolean i;

    f(Type responseType, @Nullable r scheduler, boolean isAsync, boolean isResult, boolean isBody, boolean isFlowable, boolean isSingle, boolean isMaybe, boolean isCompletable) {
        this.a = responseType;
        this.b = scheduler;
        this.c = isAsync;
        this.d = isResult;
        this.e = isBody;
        this.f = isFlowable;
        this.g = isSingle;
        this.h = isMaybe;
        this.i = isCompletable;
    }

    public Type a() {
        return this.a;
    }

    public Object b(d<R> call) {
        Observable<Response<R>> observable;
        Observable<Response<R>> responseObservable = this.c ? new b<>(call) : new c<>(call);
        if (this.d) {
            observable = new e<>(responseObservable);
        } else if (this.e) {
            observable = new a<>(responseObservable);
        } else {
            observable = responseObservable;
        }
        r rVar = this.b;
        if (rVar != null) {
            observable = observable.b0(rVar);
        }
        if (this.f) {
            return observable.i0(a.LATEST);
        }
        if (this.g) {
            return observable.U();
        }
        if (this.h) {
            return observable.T();
        }
        if (this.i) {
            return observable.A();
        }
        return io.reactivex.plugins.a.m(observable);
    }
}
