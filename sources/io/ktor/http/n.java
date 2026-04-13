package io.ktor.http;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: HeaderValueWithParameters.kt */
public final class n {
    /* access modifiers changed from: private */
    public static final boolean b(@NotNull String $this$checkNeedEscape) {
        if ($this$checkNeedEscape.length() == 0) {
            return true;
        }
        int length = $this$checkNeedEscape.length();
        int index = 0;
        while (index < length) {
            switch ($this$checkNeedEscape.charAt(index)) {
                case 10:
                case 13:
                case ' ':
                case '\"':
                case ',':
                case '/':
                case ';':
                case '=':
                case '\\':
                    return true;
                default:
                    index++;
            }
        }
        return false;
    }

    @NotNull
    public static final String c(@NotNull String $this$quote) {
        k.f($this$quote, "$this$quote");
        StringBuilder $this$buildString = new StringBuilder();
        d($this$quote, $this$buildString);
        String sb = $this$buildString.toString();
        k.b(sb, "StringBuilder().apply(builderAction).toString()");
        return sb;
    }

    private static final void d(@NotNull String $this$quoteTo, StringBuilder out) {
        out.append("\"");
        int length = $this$quoteTo.length();
        for (int i = 0; i < length; i++) {
            char ch = $this$quoteTo.charAt(i);
            switch (ch) {
                case 10:
                    out.append("\\n");
                    break;
                case 13:
                    out.append("\\r");
                    break;
                case '\"':
                    out.append("\\\"");
                    break;
                case '\\':
                    out.append("\\\\");
                    break;
                default:
                    out.append(ch);
                    break;
            }
        }
        out.append("\"");
    }
}
