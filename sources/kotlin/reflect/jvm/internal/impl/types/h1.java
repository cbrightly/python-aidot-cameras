package kotlin.reflect.jvm.internal.impl.types;

import org.jetbrains.annotations.NotNull;

/* compiled from: Variance.kt */
public enum h1 {
    INVARIANT("", true, true, 0),
    IN_VARIANCE("in", true, false, -1),
    OUT_VARIANCE("out", false, true, 1);
    
    private final boolean allowsInPosition;
    private final boolean allowsOutPosition;
    @NotNull
    private final String label;
    private final int superpositionFactor;

    private h1(String label2, boolean allowsInPosition2, boolean allowsOutPosition2, int superpositionFactor2) {
        this.label = label2;
        this.allowsInPosition = allowsInPosition2;
        this.allowsOutPosition = allowsOutPosition2;
        this.superpositionFactor = superpositionFactor2;
    }

    @NotNull
    public final String getLabel() {
        return this.label;
    }

    public final boolean getAllowsOutPosition() {
        return this.allowsOutPosition;
    }

    @NotNull
    public String toString() {
        return this.label;
    }
}
