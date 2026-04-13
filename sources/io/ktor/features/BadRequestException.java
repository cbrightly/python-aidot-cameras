package io.ktor.features;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Errors.kt */
public class BadRequestException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BadRequestException(@NotNull String message, @Nullable Throwable cause) {
        super(message, cause);
        k.f(message, "message");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BadRequestException(String str, Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : th);
    }
}
