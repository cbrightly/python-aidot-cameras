package retrofit2.adapter.rxjava2;

import io.reactivex.b;
import io.reactivex.j;
import io.reactivex.l;
import io.reactivex.r;
import io.reactivex.s;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import retrofit2.e;
import retrofit2.t;

/* compiled from: RxJava2CallAdapterFactory */
public final class g extends e.a {
    @Nullable
    private final r a;
    private final boolean b;

    public static g d() {
        return new g((r) null, false);
    }

    private g(@Nullable r scheduler, boolean isAsync) {
        this.a = scheduler;
        this.b = isAsync;
    }

    @Nullable
    public e<?, ?> a(Type returnType, Annotation[] annotations, t retrofit) {
        boolean isBody;
        boolean isResult;
        Type responseType;
        Type type = returnType;
        Class<?> rawType = e.a.c(returnType);
        if (rawType == b.class) {
            return new f(Void.class, this.a, this.b, false, true, false, false, false, true);
        }
        boolean isMaybe = true;
        boolean isFlowable = rawType == io.reactivex.e.class;
        boolean isSingle = rawType == s.class;
        if (rawType != j.class) {
            isMaybe = false;
        }
        if (rawType != l.class && !isFlowable && !isSingle && !isMaybe) {
            return null;
        }
        if (!(type instanceof ParameterizedType)) {
            String name = !isFlowable ? !isSingle ? isMaybe ? "Maybe" : "Observable" : "Single" : "Flowable";
            throw new IllegalStateException(name + " return type must be parameterized as " + name + "<Foo> or " + name + "<? extends Foo>");
        }
        Type observableType = e.a.b(0, (ParameterizedType) type);
        Class<?> rawObservableType = e.a.c(observableType);
        if (rawObservableType == retrofit2.s.class) {
            if (observableType instanceof ParameterizedType) {
                responseType = e.a.b(0, (ParameterizedType) observableType);
                isResult = false;
                isBody = false;
            } else {
                throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>");
            }
        } else if (rawObservableType != d.class) {
            responseType = observableType;
            isResult = false;
            isBody = true;
        } else if (observableType instanceof ParameterizedType) {
            responseType = e.a.b(0, (ParameterizedType) observableType);
            isResult = true;
            isBody = false;
        } else {
            throw new IllegalStateException("Result must be parameterized as Result<Foo> or Result<? extends Foo>");
        }
        Class<?> cls = rawObservableType;
        Type type2 = observableType;
        return new f(responseType, this.a, this.b, isResult, isBody, isFlowable, isSingle, isMaybe, false);
    }
}
