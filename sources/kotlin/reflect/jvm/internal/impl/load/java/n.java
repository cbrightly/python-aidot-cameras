package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.d;
import org.jetbrains.annotations.NotNull;

/* compiled from: JavaClassesTracker.kt */
public interface n {
    void a(@NotNull d dVar);

    /* compiled from: JavaClassesTracker.kt */
    public static final class a implements n {
        public static final a a = new a();

        private a() {
        }

        public void a(@NotNull d classDescriptor) {
            k.f(classDescriptor, "classDescriptor");
        }
    }
}
