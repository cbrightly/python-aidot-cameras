package retrofit2;

import java.util.Objects;
import javax.annotation.Nullable;

public class HttpException extends RuntimeException {
    private final transient s<?> c;
    private final int code;
    private final String message;

    private static String a(s<?> response) {
        Objects.requireNonNull(response, "response == null");
        return "HTTP " + response.b() + " " + response.f();
    }

    public HttpException(s<?> response) {
        super(a(response));
        this.code = response.b();
        this.message = response.f();
        this.c = response;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    @Nullable
    public s<?> response() {
        return this.c;
    }
}
