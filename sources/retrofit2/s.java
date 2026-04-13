package retrofit2;

import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.d0;
import okhttp3.e0;

/* compiled from: Response */
public final class s<T> {
    private final d0 a;
    @Nullable
    private final T b;
    @Nullable
    private final e0 c;

    public static <T> s<T> g(@Nullable T body, d0 rawResponse) {
        Objects.requireNonNull(rawResponse, "rawResponse == null");
        if (rawResponse.h0()) {
            return new s<>(rawResponse, body, (e0) null);
        }
        throw new IllegalArgumentException("rawResponse must be successful response");
    }

    public static <T> s<T> c(e0 body, d0 rawResponse) {
        Objects.requireNonNull(body, "body == null");
        Objects.requireNonNull(rawResponse, "rawResponse == null");
        if (!rawResponse.h0()) {
            return new s<>(rawResponse, (Object) null, body);
        }
        throw new IllegalArgumentException("rawResponse should not be successful response");
    }

    private s(d0 rawResponse, @Nullable T body, @Nullable e0 errorBody) {
        this.a = rawResponse;
        this.b = body;
        this.c = errorBody;
    }

    public int b() {
        return this.a.j();
    }

    public String f() {
        return this.a.t();
    }

    public boolean e() {
        return this.a.h0();
    }

    @Nullable
    public T a() {
        return this.b;
    }

    @Nullable
    public e0 d() {
        return this.c;
    }

    public String toString() {
        return this.a.toString();
    }
}
