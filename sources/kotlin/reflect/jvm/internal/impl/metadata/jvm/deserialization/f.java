package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmMetadataVersion.kt */
public final class f extends kotlin.reflect.jvm.internal.impl.metadata.deserialization.a {
    @NotNull
    public static final f g = new f(1, 1, 16);
    @NotNull
    public static final f h = new f(new int[0]);
    public static final a i = new a((DefaultConstructorMarker) null);
    private final boolean j;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public f(@NotNull int[] versionArray, boolean isStrictSemantics) {
        super(Arrays.copyOf(versionArray, versionArray.length));
        k.f(versionArray, "versionArray");
        this.j = isStrictSemantics;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public f(@NotNull int... numbers) {
        this(numbers, false);
        k.f(numbers, "numbers");
    }

    public boolean g() {
        boolean z;
        if (a() == 1 && b() == 0) {
            return false;
        }
        if (this.j) {
            z = e(g);
        } else {
            z = a() == 1 && b() <= 4;
        }
        return z;
    }

    /* compiled from: JvmMetadataVersion.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
