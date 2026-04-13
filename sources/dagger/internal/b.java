package dagger.internal;

/* compiled from: Preconditions */
public final class b {
    public static <T> T b(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException();
    }

    public static <T> T c(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static <T> void a(T requirement, Class<T> clazz) {
        if (requirement == null) {
            throw new IllegalStateException(clazz.getCanonicalName() + " must be set");
        }
    }
}
