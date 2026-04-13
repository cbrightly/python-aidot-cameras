package io.ktor.http;

import io.netty.util.internal.StringUtil;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: CookieUtils.kt */
public final class InvalidCookieDateException extends IllegalStateException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public InvalidCookieDateException(@NotNull String data, @NotNull String reason) {
        super("Failed to parse date string: \"" + data + "\". Reason: \"" + reason + StringUtil.DOUBLE_QUOTE);
        k.f(data, "data");
        k.f(reason, "reason");
    }
}
