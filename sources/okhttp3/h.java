package okhttp3;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Challenge.kt */
public final class h {
    @NotNull
    private final Map<String, String> a;
    @NotNull
    private final String b;

    public h(@NotNull String scheme, @NotNull Map<String, String> authParams) {
        String newKey;
        k.f(scheme, "scheme");
        k.f(authParams, "authParams");
        this.b = scheme;
        Map newAuthParams = new LinkedHashMap();
        for (Map.Entry next : authParams.entrySet()) {
            String key = (String) next.getKey();
            String value = (String) next.getValue();
            if (key != null) {
                Locale locale = Locale.US;
                k.b(locale, "US");
                newKey = key.toLowerCase(locale);
                k.b(newKey, "(this as java.lang.String).toLowerCase(locale)");
            } else {
                newKey = null;
            }
            newAuthParams.put(newKey, value);
        }
        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(newAuthParams);
        k.b(unmodifiableMap, "unmodifiableMap<String?, String>(newAuthParams)");
        this.a = unmodifiableMap;
    }

    @NotNull
    public final String c() {
        return this.b;
    }

    @Nullable
    public final String b() {
        return this.a.get("realm");
    }

    @NotNull
    public final Charset a() {
        String charset = this.a.get("charset");
        if (charset != null) {
            try {
                Charset forName = Charset.forName(charset);
                k.b(forName, "Charset.forName(charset)");
                return forName;
            } catch (Exception e) {
            }
        }
        Charset charset2 = StandardCharsets.ISO_8859_1;
        k.b(charset2, "ISO_8859_1");
        return charset2;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof h) && k.a(((h) other).b, this.b) && k.a(((h) other).a, this.a);
    }

    public int hashCode() {
        return (((29 * 31) + this.b.hashCode()) * 31) + this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return this.b + " authParams=" + this.a;
    }
}
