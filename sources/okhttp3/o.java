package okhttp3;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import kotlin.jvm.internal.k;
import okio.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: Credentials.kt */
public final class o {
    public static final o a = new o();

    @NotNull
    public static final String a(@NotNull String str, @NotNull String str2) {
        return c(str, str2, (Charset) null, 4, (Object) null);
    }

    private o() {
    }

    public static /* synthetic */ String c(String str, String str2, Charset charset, int i, Object obj) {
        if ((i & 4) != 0) {
            charset = StandardCharsets.ISO_8859_1;
            k.b(charset, "ISO_8859_1");
        }
        return b(str, str2, charset);
    }

    @NotNull
    public static final String b(@NotNull String username, @NotNull String password, @NotNull Charset charset) {
        k.f(username, "username");
        k.f(password, "password");
        k.f(charset, "charset");
        String encoded = f.Companion.c(username + ':' + password, charset).base64();
        return "Basic " + encoded;
    }
}
