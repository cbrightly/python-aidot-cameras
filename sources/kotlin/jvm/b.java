package kotlin.jvm;

import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinReflectionNotSupportedError.kt */
public class b extends Error {
    public b() {
        super("Kotlin reflection implementation is not found at runtime. Make sure you have kotlin-reflect.jar in the classpath");
    }

    public b(@Nullable String message) {
        super(message);
    }

    public b(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    public b(@Nullable Throwable cause) {
        super(cause);
    }
}
