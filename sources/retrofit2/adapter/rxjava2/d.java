package retrofit2.adapter.rxjava2;

import javax.annotation.Nullable;
import retrofit2.s;

/* compiled from: Result */
public final class d<T> {
    @Nullable
    private final s<T> a;
    @Nullable
    private final Throwable b;

    public static <T> d<T> a(Throwable error) {
        if (error != null) {
            return new d<>((s) null, error);
        }
        throw new NullPointerException("error == null");
    }

    public static <T> d<T> b(s<T> response) {
        if (response != null) {
            return new d<>(response, (Throwable) null);
        }
        throw new NullPointerException("response == null");
    }

    private d(@Nullable s<T> response, @Nullable Throwable error) {
        this.a = response;
        this.b = error;
    }
}
