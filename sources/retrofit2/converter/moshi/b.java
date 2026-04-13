package retrofit2.converter.moshi;

import com.squareup.moshi.f;
import com.squareup.moshi.o;
import okhttp3.c0;
import okhttp3.x;
import okio.c;
import retrofit2.h;

/* compiled from: MoshiRequestBodyConverter */
public final class b<T> implements h<T, c0> {
    private static final x a = x.f("application/json; charset=UTF-8");
    private final f<T> b;

    b(f<T> adapter) {
        this.b = adapter;
    }

    /* renamed from: a */
    public c0 convert(T value) {
        c buffer = new c();
        this.b.i(o.t(buffer), value);
        return c0.create(a, buffer.D0());
    }
}
