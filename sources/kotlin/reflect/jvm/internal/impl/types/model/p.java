package kotlin.reflect.jvm.internal.impl.types.model;

import org.jetbrains.annotations.NotNull;

/* compiled from: TypeSystemContext.kt */
public enum p {
    IN("in"),
    OUT("out"),
    INV("");
    
    @NotNull
    private final String presentation;

    private p(String presentation2) {
        this.presentation = presentation2;
    }

    @NotNull
    public String toString() {
        return this.presentation;
    }
}
