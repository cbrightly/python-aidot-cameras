package coil.network;

import kotlin.jvm.internal.k;
import okhttp3.d0;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpException.kt */
public final class HttpException extends RuntimeException {
    @NotNull
    private final d0 response;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public HttpException(@NotNull d0 response2) {
        super("HTTP " + response2.j() + ": " + response2.t());
        k.e(response2, "response");
        this.response = response2;
    }

    @NotNull
    public final d0 getResponse() {
        return this.response;
    }
}
