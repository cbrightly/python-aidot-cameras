package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmBytecodeBinaryVersion.kt */
public final class c extends kotlin.reflect.jvm.internal.impl.metadata.deserialization.a {
    @NotNull
    public static final c g = new c(1, 0, 3);
    @NotNull
    public static final c h = new c(new int[0]);
    public static final a i = new a((DefaultConstructorMarker) null);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(@NotNull int... numbers) {
        super(Arrays.copyOf(numbers, numbers.length));
        k.f(numbers, "numbers");
    }

    /* compiled from: JvmBytecodeBinaryVersion.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
