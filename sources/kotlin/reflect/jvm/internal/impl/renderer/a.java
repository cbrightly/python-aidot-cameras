package kotlin.reflect.jvm.internal.impl.renderer;

/* compiled from: DescriptorRenderer.kt */
public enum a {
    NO_ARGUMENTS(false, false, 3, (boolean) null),
    UNLESS_EMPTY(true, false, 2, (boolean) null),
    ALWAYS_PARENTHESIZED(true, true);
    
    private final boolean includeAnnotationArguments;
    private final boolean includeEmptyAnnotationArguments;

    private a(boolean includeAnnotationArguments2, boolean includeEmptyAnnotationArguments2) {
        this.includeAnnotationArguments = includeAnnotationArguments2;
        this.includeEmptyAnnotationArguments = includeEmptyAnnotationArguments2;
    }

    public final boolean getIncludeAnnotationArguments() {
        return this.includeAnnotationArguments;
    }

    public final boolean getIncludeEmptyAnnotationArguments() {
        return this.includeEmptyAnnotationArguments;
    }
}
