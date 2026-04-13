package io.ktor.utils.io.core;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Buffer.kt */
public final class InsufficientSpaceException extends Exception {
    public InsufficientSpaceException() {
        this((String) null, 1, (DefaultConstructorMarker) null);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public InsufficientSpaceException(@NotNull String message) {
        super(message);
        k.f(message, "message");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ InsufficientSpaceException(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "Not enough free space" : str);
    }

    public InsufficientSpaceException(int size, int availableSpace) {
        this("Not enough free space to write " + size + " bytes, available " + availableSpace + " bytes.");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public InsufficientSpaceException(@NotNull String name, int size, int availableSpace) {
        this("Not enough free space to write " + name + " of " + size + " bytes, available " + availableSpace + " bytes.");
        k.f(name, "name");
    }

    public InsufficientSpaceException(long size, long availableSpace) {
        this("Not enough free space to write " + size + " bytes, available " + availableSpace + " bytes.");
    }
}
