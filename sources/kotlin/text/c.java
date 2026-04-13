package kotlin.text;

import java.nio.charset.Charset;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Charsets.kt */
public final class c {
    @NotNull
    public static final Charset a;
    @NotNull
    public static final Charset b;
    @NotNull
    public static final Charset c;
    @NotNull
    public static final Charset d;
    @NotNull
    public static final Charset e;
    @NotNull
    public static final Charset f;
    private static Charset g;
    private static Charset h;
    @NotNull
    public static final c i = new c();

    static {
        Charset forName = Charset.forName("UTF-8");
        k.d(forName, "Charset.forName(\"UTF-8\")");
        a = forName;
        Charset forName2 = Charset.forName("UTF-16");
        k.d(forName2, "Charset.forName(\"UTF-16\")");
        b = forName2;
        Charset forName3 = Charset.forName("UTF-16BE");
        k.d(forName3, "Charset.forName(\"UTF-16BE\")");
        c = forName3;
        Charset forName4 = Charset.forName("UTF-16LE");
        k.d(forName4, "Charset.forName(\"UTF-16LE\")");
        d = forName4;
        Charset forName5 = Charset.forName("US-ASCII");
        k.d(forName5, "Charset.forName(\"US-ASCII\")");
        e = forName5;
        Charset forName6 = Charset.forName("ISO-8859-1");
        k.d(forName6, "Charset.forName(\"ISO-8859-1\")");
        f = forName6;
    }

    private c() {
    }

    @NotNull
    public final Charset b() {
        Charset charset = g;
        if (charset != null) {
            return charset;
        }
        Charset charset2 = Charset.forName("UTF-32LE");
        k.d(charset2, "Charset.forName(\"UTF-32LE\")");
        g = charset2;
        return charset2;
    }

    @NotNull
    public final Charset a() {
        Charset charset = h;
        if (charset != null) {
            return charset;
        }
        Charset charset2 = Charset.forName("UTF-32BE");
        k.d(charset2, "Charset.forName(\"UTF-32BE\")");
        h = charset2;
        return charset2;
    }
}
