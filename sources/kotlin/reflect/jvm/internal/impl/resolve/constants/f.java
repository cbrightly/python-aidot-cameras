package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.a;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClassLiteralValue.kt */
public final class f {
    @NotNull
    private final a a;
    private final int b;

    @NotNull
    public final a a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return k.a(this.a, fVar.a) && this.b == fVar.b;
    }

    public int hashCode() {
        a aVar = this.a;
        return ((aVar != null ? aVar.hashCode() : 0) * 31) + this.b;
    }

    public f(@NotNull a classId, int arrayNestedness) {
        k.f(classId, "classId");
        this.a = classId;
        this.b = arrayNestedness;
    }

    public final int c() {
        return this.b;
    }

    @NotNull
    public final a d() {
        return this.a;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        int i = this.b;
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2;
            $this$buildString.append("kotlin/Array<");
        }
        $this$buildString.append(this.a);
        int i4 = this.b;
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i5;
            $this$buildString.append(">");
        }
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }
}
