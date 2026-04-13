package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.c;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: RenderingUtils.kt */
public final class q {
    @NotNull
    public static final String b(@NotNull f $this$render) {
        k.f($this$render, "$this$render");
        if (d($this$render)) {
            StringBuilder sb = new StringBuilder();
            String b = $this$render.b();
            k.b(b, "asString()");
            sb.append(String.valueOf('`') + b);
            sb.append('`');
            return sb.toString();
        }
        String b2 = $this$render.b();
        k.b(b2, "asString()");
        return b2;
    }

    private static final boolean d(@NotNull f $this$shouldBeEscaped) {
        CharSequence $this$any$iv;
        if ($this$shouldBeEscaped.h()) {
            return false;
        }
        CharSequence string = $this$shouldBeEscaped.b();
        k.b(string, "asString()");
        if (!l.a.contains(string)) {
            CharSequence $this$any$iv2 = string;
            int i = 0;
            while (true) {
                if (i >= $this$any$iv2.length()) {
                    $this$any$iv = null;
                    break;
                }
                char it = $this$any$iv2.charAt(i);
                if (((Character.isLetterOrDigit(it) || it == '_') ? (char) 0 : 1) != 0) {
                    $this$any$iv = 1;
                    break;
                }
                i++;
            }
            if ($this$any$iv == null) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public static final String a(@NotNull c $this$render) {
        k.f($this$render, "$this$render");
        List<f> h = $this$render.h();
        k.b(h, "pathSegments()");
        return c(h);
    }

    @NotNull
    public static final String c(@NotNull List<f> pathSegments) {
        k.f(pathSegments, "pathSegments");
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        for (f element : pathSegments) {
            if ($this$buildString.length() > 0) {
                $this$buildString.append(".");
            }
            $this$buildString.append(b(element));
        }
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }
}
