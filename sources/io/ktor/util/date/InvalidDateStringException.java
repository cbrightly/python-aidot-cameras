package io.ktor.util.date;

import io.netty.util.internal.StringUtil;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: GMTDateParser.kt */
public final class InvalidDateStringException extends IllegalStateException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public InvalidDateStringException(@NotNull String data, int at, @NotNull String pattern) {
        super("Failed to parse date string: \"" + data + "\" at index " + at + ". Pattern: \"" + pattern + StringUtil.DOUBLE_QUOTE);
        k.f(data, "data");
        k.f(pattern, "pattern");
    }
}
