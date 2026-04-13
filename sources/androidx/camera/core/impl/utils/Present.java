package androidx.camera.core.impl.utils;

import androidx.annotation.Nullable;
import androidx.core.util.Preconditions;
import androidx.core.util.Supplier;

public final class Present<T> extends Optional<T> {
    private static final long serialVersionUID = 0;
    private final T mReference;

    Present(T reference) {
        this.mReference = reference;
    }

    public boolean isPresent() {
        return true;
    }

    public T get() {
        return this.mReference;
    }

    public T or(T defaultValue) {
        Preconditions.checkNotNull(defaultValue, "use Optional.orNull() instead of Optional.or(null)");
        return this.mReference;
    }

    public Optional<T> or(Optional<? extends T> secondChoice) {
        Preconditions.checkNotNull(secondChoice);
        return this;
    }

    public T or(Supplier<? extends T> supplier) {
        Preconditions.checkNotNull(supplier);
        return this.mReference;
    }

    public T orNull() {
        return this.mReference;
    }

    public boolean equals(@Nullable Object object) {
        if (object instanceof Present) {
            return this.mReference.equals(((Present) object).mReference);
        }
        return false;
    }

    public int hashCode() {
        return this.mReference.hashCode() + 1502476572;
    }

    public String toString() {
        return "Optional.of(" + this.mReference + ")";
    }
}
