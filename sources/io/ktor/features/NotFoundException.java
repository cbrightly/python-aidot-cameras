package io.ktor.features;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.Nullable;

/* compiled from: Errors.kt */
public final class NotFoundException extends Exception {
    public NotFoundException() {
        this((String) null, 1, (DefaultConstructorMarker) null);
    }

    public NotFoundException(@Nullable String message) {
        super(message);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ NotFoundException(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "Resource not found" : str);
    }
}
